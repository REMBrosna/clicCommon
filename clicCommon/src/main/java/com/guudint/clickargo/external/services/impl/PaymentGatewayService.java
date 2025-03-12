package com.guudint.clickargo.external.services.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.guudint.clickargo.common.CkUtil;
import com.guudint.clickargo.common.ICkConstant;
import com.guudint.clickargo.external.dto.ActivateVARequest;
import com.guudint.clickargo.external.dto.ActivateVAResponse;
import com.guudint.clickargo.external.dto.CancelPaymentRequest;
import com.guudint.clickargo.external.dto.CancelPaymentResponse;
import com.guudint.clickargo.external.dto.CreateFundTransferRequest;
import com.guudint.clickargo.external.dto.CreateFundTransferResponse;
import com.guudint.clickargo.external.dto.CreateFundTransferVARequest;
import com.guudint.clickargo.external.dto.CreateFundTransferVAResponse;
import com.guudint.clickargo.external.dto.ErrorData;
import com.guudint.clickargo.external.dto.GatewayConfig;
import com.guudint.clickargo.external.dto.GetFundTransferStatusRequest;
import com.guudint.clickargo.external.dto.GetFundTransferStatusResponse;
import com.guudint.clickargo.external.dto.GetVAStatusRequest;
import com.guudint.clickargo.external.dto.LoginRequest;
import com.guudint.clickargo.external.dto.LoginResponse;
import com.guudint.clickargo.external.dto.MakePaymentRequest;
import com.guudint.clickargo.external.dto.MakePaymentResponse;
import com.guudint.clickargo.external.dto.VaResponse;
import com.guudint.clickargo.external.services.GatewayService;
import com.guudint.clickargo.external.services.IPaymentGateway;
import com.guudint.clickargo.master.dao.CkMstPaymentAuditTypeDao;
import com.guudint.clickargo.master.model.TCkMstPaymentAuditType;
import com.guudint.clickargo.payment.enums.PaymentAuditTypes;
import com.guudint.clickargo.payment.enums.PaymentStates;
import com.guudint.clickargo.payment.model.TCkPaymentAudit;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.ccm.util.Constant;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;

@Service(value = "paymentGatewayService")
public class PaymentGatewayService extends GatewayService implements IPaymentGateway {

	private static final Logger LOG = Logger.getLogger(PaymentGatewayService.class);

	protected static final ThreadLocal<String> AUTH_TOKEN_HOLDER = new ThreadLocal<>();
	private static final String KEY_PAYMENT_GATEWAY_CONFIG = "PAYMENT_GATEWAY_CONFIG";

	@Autowired
	private CkMstPaymentAuditTypeDao ckMstPaymentAuditTypeDao;
	@Autowired
	protected GenericDao<TCkPaymentAudit, String> paymentAuditDao;

	protected static String PAYMENT_GW_BASE_URL = null;

	@Override
	public LoginResponse login(String node, String serviceID) throws Exception {

		try {
			GatewayConfig config = getGatewayConfigFromSysParam(KEY_PAYMENT_GATEWAY_CONFIG);

			if (config == null)
				throw new ProcessingException("payment gateway details not configured");

			LoginRequest loginRequest = new LoginRequest();
			if (config.getIsMock().equalsIgnoreCase("Y")) {
				loginRequest.setUid(config.getMockUid());
				PAYMENT_GW_BASE_URL = config.getMockUrl();
			} else {
				loginRequest.setUid(config.getUid());
				loginRequest.setNode(node);
				loginRequest.setServiceID(serviceID);
				PAYMENT_GW_BASE_URL = config.getUrl();
			}

			String response = getClient().target(PAYMENT_GW_BASE_URL).path("session")
					.request(MediaType.APPLICATION_JSON)
					.post(Entity.entity(loginRequest, MediaType.APPLICATION_JSON), String.class);

			Map<String, Object> convert = objectMapper.readValue(response,
					new TypeReference<HashMap<String, Object>>() {
					});
			LoginResponse loginResponse = new LoginResponse();
			AUTH_TOKEN_HOLDER.set(String.valueOf(convert.get("data")));
			loginResponse.setToken(String.valueOf(convert.get("data")));
			loginResponse.setCode("200");

			return loginResponse;
		} catch (Exception ex) {
			LOG.error(ex);
			throw ex;
		}

	}

	@Override
	public VaResponse va(GetVAStatusRequest vaRequest) throws Exception {

		VaResponse response = new VaResponse();
		try {

			if (vaRequest == null)
				throw new ParameterException("param vaRequest null");

			// Call login
			if (StringUtils.isBlank(AUTH_TOKEN_HOLDER.get())) {
				login(vaRequest.getNode(), vaRequest.getServiceID());
			}

			Builder builder = initInvocationAuthBuilder(PAYMENT_GW_BASE_URL + "/transfer/va", AUTH_TOKEN_HOLDER.get());

			TCkPaymentAudit audit = createPaymentAudit(PaymentAuditTypes.VA_CALLBACK, vaRequest.getAppRefNum(),
					objectMapper.writeValueAsString(vaRequest));

			Response apiResponse = builder.post(Entity.entity(vaRequest, MediaType.APPLICATION_JSON));

			if (apiResponse == null) {
				audit.setPyaResp("ERROR: no response");
			} else {

				audit.setPyaState(String.valueOf(apiResponse.getStatus()));
				response = apiResponse.readEntity(VaResponse.class);
				audit.setPyaResp(response.toJson());

				LOG.info("Response Data: " + response.toJson());
			}

			// Update audit
			paymentAuditDao.update(audit);

		} catch (Exception ex) {
			throw ex;
		} finally {
			logout();
		}

		return response;
	}

	/**
	 * Calls clicpay payment api to bind the VA and amount. Return the response.
	 */
	@Override
	public MakePaymentResponse makePayment(MakePaymentRequest paymentRequest) throws Exception {
		MakePaymentResponse response = new MakePaymentResponse();
		TCkPaymentAudit paymentAudit = null;
		try {
			if (paymentRequest == null)
				throw new ParameterException("param vaRequest null");

			if (StringUtils.isBlank(paymentRequest.getNode()) && StringUtils.isBlank(paymentRequest.getServiceID()))
				throw new ProcessingException("param node/serviceID empty or blank");

			// Call login if AUTH_TOKEN_HOLDER is still empty
			if (StringUtils.isBlank(AUTH_TOKEN_HOLDER.get())) {
				login(paymentRequest.getNode(), paymentRequest.getServiceID());
			}

			Builder builder = initInvocationAuthBuilder(PAYMENT_GW_BASE_URL + "/payment", AUTH_TOKEN_HOLDER.get());

			paymentAudit = createPaymentAudit(PaymentAuditTypes.PAYMENT_VIA_VA, paymentRequest.getRefID(),
					paymentRequest.toJson());

			Response apiResponse = builder.post(Entity.entity(paymentRequest.toJson(), MediaType.APPLICATION_JSON));
			if (apiResponse == null) {
				paymentAudit.setPyaResp("ERROR: no response");
			} else {

				response = apiResponse.readEntity(MakePaymentResponse.class);
				if (apiResponse.getStatus() == 400) {
					response.setCode(String.valueOf(apiResponse.getStatus()));
					paymentAudit.setPyaState(PaymentStates.FAILED.getCode());
					paymentAudit.setPyaResp(objectMapper.writeValueAsString(response.getErr()));
				} else {

					paymentAudit.setPyaState(PaymentStates.SUCCESS.getCode());
					response.setCode(String.valueOf(apiResponse.getStatus()));
					paymentAudit.setPyaResp(response.toJson());
				}

				LOG.info("Response Data: " + response.toJson());
			}

		} catch (Exception e) {
			LOG.error("Encountered failure while creating payment audit record", e);
			// save the exception in remarks
			paymentAudit.setPyaRemark(e.getMessage());
			// here it may return still 200 but somewhere inside try block, an exception is
			// thrown
			// so it won't return hasError = true
			return new MakePaymentResponse("PROCESSING_ERROR",
					new ErrorData(-100, "Encountered failure while creating payment audit record, exception - ",
							ExceptionUtils.getStackTrace(e)));
		} finally {
			// Update audit
			try {
				paymentAuditDao.update(paymentAudit);
				logout();
			} catch (Exception e) {
				LOG.error("Encountered failure while updating payment audit record", e);

			}
		}

		return response;

	}

	@Override
	public CancelPaymentResponse cancelPayment(CancelPaymentRequest cancelPaymentRequest) throws Exception {
		CancelPaymentResponse response = new CancelPaymentResponse();

		TCkPaymentAudit paymentAudit = null;
		try {
			if (cancelPaymentRequest == null)
				throw new ParameterException("param cancelPaymentRequest null");

			if (StringUtils.isBlank(cancelPaymentRequest.getNode())
					&& StringUtils.isBlank(cancelPaymentRequest.getServiceID()))
				throw new ProcessingException("param node/serviceID empty or blank");

			// Call login if AUTH_TOKEN_HOLDER is still empty
			if (StringUtils.isBlank(AUTH_TOKEN_HOLDER.get())) {
				login(cancelPaymentRequest.getNode(), cancelPaymentRequest.getServiceID());
			}

			Builder builder = initInvocationAuthBuilder(PAYMENT_GW_BASE_URL + "/deletePayment",
					AUTH_TOKEN_HOLDER.get());

			paymentAudit = createPaymentAudit(PaymentAuditTypes.CANCEL_PAYMENT_REQUEST, cancelPaymentRequest.getRef(),
					cancelPaymentRequest.toJson());

			Response apiResponse = builder
					.post(Entity.entity(cancelPaymentRequest.toJson(), MediaType.APPLICATION_JSON));
			if (apiResponse == null) {
				paymentAudit.setPyaResp("ERROR: no response");
			} else {

				response = apiResponse.readEntity(CancelPaymentResponse.class);
				if (apiResponse.getStatus() == 400) {
					response.setCode(String.valueOf(apiResponse.getStatus()));
					paymentAudit.setPyaState(PaymentStates.FAILED.getCode());
					paymentAudit.setPyaResp(objectMapper.writeValueAsString(response.getErr()));
				} else {

					paymentAudit.setPyaState(PaymentStates.SUCCESS.getCode());
					response.setCode(String.valueOf(apiResponse.getStatus()));
					paymentAudit.setPyaResp(response.toJson());
				}

				LOG.info("Response Data: " + response.toJson());
			}

		} catch (Exception e) {
			LOG.error("Encountered failure while creating payment audit record", e);
			// save the exception in remarks
			paymentAudit.setPyaRemark(e.getMessage());
			// here it may return still 200 but somewhere inside try block, an exception is
			// thrown
			// so it won't return hasError = true
			return new CancelPaymentResponse("PROCESSING_ERROR",
					new ErrorData(-100, "Encountered failure while creating payment audit record, exception - ",
							ExceptionUtils.getStackTrace(e)));
		} finally {
			// Update audit
			try {
				paymentAuditDao.update(paymentAudit);
				logout();
			} catch (Exception e) {
				LOG.error("Encountered failure while updating payment audit record", e);

			}
		}

		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CreateFundTransferVAResponse createFundTransferVA(CreateFundTransferVARequest ftRequest) throws Exception {

		CreateFundTransferVAResponse ftResponse = new CreateFundTransferVAResponse();
		TCkPaymentAudit paymentAudit = null;
		try {

			if (ftRequest == null)
				throw new ParameterException("param ftRequest null");

			if (StringUtils.isBlank(AUTH_TOKEN_HOLDER.get())) {
				login(ftRequest.getNode(), ftRequest.getServiceID());
			}

			Builder builder = initInvocationAuthBuilder(PAYMENT_GW_BASE_URL + "/transfer/va", AUTH_TOKEN_HOLDER.get());

			paymentAudit = createPaymentAudit(PaymentAuditTypes.BANK_TRANSFER_CALLBACK, ftRequest.getRefID(),
					ftRequest.toJson());

			Response apiResponse = builder.post(Entity.entity(ftRequest.toJson(), MediaType.APPLICATION_JSON));
			if (apiResponse == null) {
				paymentAudit.setPyaResp("ERROR: no response");
			} else {
				paymentAudit.setPyaState(String.valueOf(apiResponse.getStatus()));
				ftResponse = apiResponse.readEntity(CreateFundTransferVAResponse.class);
				ftResponse.setCode(String.valueOf(apiResponse.getStatus()));
				paymentAudit.setPyaResp(ftResponse.toJson());

				LOG.info("Response Data: " + ftResponse.toJson());
			}
		} catch (Exception e) {
			LOG.error("Encountered failure while creating payment audit record", e);
			return new CreateFundTransferVAResponse("PROCESSING_ERROR",
					new ErrorData(-100, "Encountered failure while creating payment audit record, exception - ",
							ExceptionUtils.getStackTrace(e)));
		} finally {
			// Update audit
			try {
				paymentAuditDao.update(paymentAudit);
				logout();
			} catch (Exception e) {
				LOG.error("Encountered failure while updating payment audit record", e);
				e.printStackTrace();
				throw e;
			}
		}

		return ftResponse;

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CreateFundTransferResponse createFundTransferBIFast(CreateFundTransferRequest ftRequest)
			throws Exception {
		CreateFundTransferResponse ftResponse = new CreateFundTransferResponse();
		TCkPaymentAudit paymentAudit = null;
		try {

			if (ftRequest == null)
				throw new ParameterException("param ftRequest null");

			paymentAudit = createPaymentAudit(PaymentAuditTypes.BANK_TRANSFER_REQUEST, ftRequest.getRefID(),
					ftRequest.toJson());
			
			if (StringUtils.isBlank(AUTH_TOKEN_HOLDER.get())) {
				login(ftRequest.getNode(), ftRequest.getServiceID());
			}

			Builder builder = initInvocationAuthBuilder(PAYMENT_GW_BASE_URL + "/transfer", AUTH_TOKEN_HOLDER.get());

			LOG.info("Request: "  + ftRequest.toJson());
			Response apiResponse = builder.post(Entity.entity(ftRequest.toJson(), MediaType.APPLICATION_JSON));
			if (apiResponse == null) {
				paymentAudit.setPyaResp("ERROR: no response");
			} else {
				paymentAudit.setPyaState(String.valueOf(apiResponse.getStatus()));
				if(apiResponse.getStatus() >= 200 && apiResponse.getStatus()<=300) {
					ftResponse = apiResponse.readEntity(CreateFundTransferResponse.class);
					ftResponse.setCode(String.valueOf(apiResponse.getStatus()));
					paymentAudit.setPyaResp(ftResponse.toJson());
				} else {
					throw new Exception("Error: " + apiResponse.getStatus());
				}

				LOG.info("Response Data: " + ftResponse.toJson());
			}
		} catch (Exception e) {
			LOG.error("Encountered failure while creating payment audit record", e);
			
			paymentAudit.setPyaRemark("Exception: " + e.getMessage());
			
			return new CreateFundTransferResponse("PROCESSING_ERROR",
					new ErrorData(-100, "Error encountered: " + e.getMessage(),
							ExceptionUtils.getStackTrace(e)));
		} finally {
			// Update audit
			try {
				paymentAuditDao.update(paymentAudit);
				logout();
			} catch (Exception e) {
				LOG.error("Encountered failure while updating payment audit record", e);
				e.printStackTrace();
				// throw e;
			}
		}

		return ftResponse;
	}

	@Override
	public void paymentCallback(Principal principal) {

	}

	@Override
	public void logout() {
		AUTH_TOKEN_HOLDER.remove();
	}

	// Helper Methods
	//////////////////////
	protected TCkPaymentAudit createPaymentAudit(PaymentAuditTypes paymentAuditType, String refNo, String request)
			throws Exception {
		try {

			Date now = new Date();
			TCkMstPaymentAuditType newAuditType = ckMstPaymentAuditTypeDao.findByIdAndStatus(paymentAuditType.getId(),
					Constant.ACTIVE_STATUS);

			TCkPaymentAudit paymentAudit = new TCkPaymentAudit();
			paymentAudit.setPyaId(CkUtil.generateId(ICkConstant.PREFIX_PAYMENT_AUDIT));
			paymentAudit.setTCkMstPaymentAuditType(newAuditType);
			paymentAudit.setPyaReference(refNo);

			paymentAudit.setPyaRemark(paymentAuditType.getDesc());
			paymentAudit.setPyaStatus(Constant.ACTIVE_STATUS);
			paymentAudit.setPyaDtCreate(now);
			paymentAudit.setPyaUidCreate(Constant.ACCN_CREATE_SYS_USER);
			paymentAudit.setPyaDtLupd(now);
			paymentAudit.setPyaUidLupd(Constant.ACCN_CREATE_SYS_USER);
			paymentAudit.setPyaReq(request);

			paymentAuditDao.add(paymentAudit);

			return paymentAudit;

		} catch (Exception ex) {
			LOG.error("createPaymentAudit", ex);
			throw ex;
		}
	}

	@Override
	public GetFundTransferStatusResponse getFundTransferStatus(
			GetFundTransferStatusRequest getFundTransferStatusRequest) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActivateVAResponse activateVA(ActivateVARequest activateVARequest) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createLedger(Principal principal) throws Exception {
		// TODO Auto-generated method stub

	}
}
