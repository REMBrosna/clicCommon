package com.guudint.clickargo.external.services.impl;

import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guudint.clickargo.common.CkUtil;
import com.guudint.clickargo.estamp.dto.AuthTokenRequest;
import com.guudint.clickargo.estamp.dto.AuthTokenResponse;
import com.guudint.clickargo.estamp.dto.CkEstampDoc;
import com.guudint.clickargo.estamp.dto.StampAnnotations;
import com.guudint.clickargo.estamp.dto.StampCallbackRequest;
import com.guudint.clickargo.estamp.dto.StampConfig;
import com.guudint.clickargo.estamp.dto.StampConfigEx;
import com.guudint.clickargo.estamp.dto.StampDownloadResponse;
import com.guudint.clickargo.estamp.dto.StampRequest;
import com.guudint.clickargo.estamp.dto.StampResponse;
import com.guudint.clickargo.estamp.dto.StampResponse.Data;
import com.guudint.clickargo.estamp.model.TCkEstampDoc;
import com.guudint.clickargo.estamp.model.TCkEstampTxn;
import com.guudint.clickargo.external.services.GatewayService;
import com.guudint.clickargo.external.services.IStampGateway;
import com.guudint.clickargo.master.enums.EstampMethod;
import com.guudint.clickargo.master.enums.ServiceTypes;
import com.guudint.clickargo.master.model.TCkMstEstampMethod;
import com.guudint.clickargo.master.model.TCkMstServiceType;
import com.vcc.camelone.ccm.util.Constant;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.config.model.TCoreSysparam;

public class StampGatewayService extends GatewayService implements IStampGateway {

	private static final Logger log = Logger.getLogger(StampGatewayService.class);

	private static final String KEY_STAMP_GATEWAY_CONFIG = "STAMP_GATEWAY_CONFIG";

//	private static final String KEY_STAMP_GATEWAY_REFRESH_TOKEN = "STAMP_GATEWAY_REFRESH_TOKEN";

	private static final String KEY_STAMP_GATEWAY_AUTH_DTLS = "STAMP_GATEWAY_AUTH_DTLS";

	private static final String KEY_STAMP_GATEWAY_CALLBACK = "STAMP_GATEWAY_CALLBACK";

	@Autowired
	private GenericDao<TCkMstEstampMethod, String> ckMstEstampMethodDao;

	@Autowired
	private GenericDao<TCkEstampTxn, String> ckEstampTxnDao;

	@Autowired
	GenericDao<TCkMstServiceType, String> ckMstServiceTypeDao;

	@Autowired
	GenericDao<TCkEstampDoc, String> ckEstampDocDao;

	@Override
	public AuthTokenResponse login() throws ClientErrorException, ServerErrorException, Exception {
		log.debug("login");

		// Initialize to auth_token
		String grantType = EstampMethod.AUTH_TOKEN.getId();

		// Do not save yet just form the object
		TCkEstampTxn transaction = createEStampTxn();
		boolean proceedToRequest = true;

		// Retrieve token details first
		TCoreSysparam stampRefreshToken = sysParamDao.find(KEY_STAMP_GATEWAY_AUTH_DTLS);
		if (stampRefreshToken == null)
			throw new EntityNotFoundException("STAMP_GATEWAY_REFRESH_TOKEN not configured in sysparam");

		StampConfigEx stampConfigEx = objectMapper.readValue(stampRefreshToken.getSysVal(), StampConfigEx.class);

		try {

			StampConfig config = getGatewayConfigSysParam(KEY_STAMP_GATEWAY_CONFIG);
			if (config == null)
				throw new ProcessingException("stamp gateway details not configured");

			StampGatewayConfigFactory.getInstance().setAuthUrl(config.getAuthUrl());
			StampGatewayConfigFactory.getInstance().setBaseUrl(config.getUrl());
			// code below can only be used once
			StampGatewayConfigFactory.getInstance().setCode(config.getCode());
			StampGatewayConfigFactory.getInstance().setClientId(config.getClientId());
			StampGatewayConfigFactory.getInstance().setClientSecret(config.getClientSecret());

			AuthTokenRequest requestBody = new AuthTokenRequest();
			requestBody.setClientId(StampGatewayConfigFactory.getInstance().getClientId());
			requestBody.setClientSecret(StampGatewayConfigFactory.getInstance().getClientSecret());

			// set granttype to authorization_code, succeeding calls should be refresh_token
			if (StringUtils.isBlank(stampConfigEx.getRefreshToken())) {
				requestBody.setGrantType("authorization_code");
				requestBody.setCode(StampGatewayConfigFactory.getInstance().getCode());

			} else {
				requestBody.setGrantType("refresh_token");
				requestBody.setRefreshToken(stampConfigEx.getRefreshToken());
				grantType = EstampMethod.REFRESH_TOKEN.getId();
			}

			// check if current_time vs last_run_time is greather than the expiry_time
			if (stampConfigEx.getExpiresIn() != null && stampConfigEx.getLastRunTime() != null) {
				// convert last runtime and compare with the expires_in seconds
				long lastRunTimeInSec = TimeUnit.MILLISECONDS.toSeconds(stampConfigEx.getLastRunTime());
				if (lastRunTimeInSec < stampConfigEx.getExpiresIn()) {
					// if seconds is still less than the EXPIRY_TIME, do not proceed to call
					proceedToRequest = false;
				}

			}

			AuthTokenResponse response = new AuthTokenResponse();
			if (proceedToRequest) {
				transaction.setEstReq(InetAddress.getLocalHost() + " " + requestBody.toJson());
				String api = getClient().target(StampGatewayConfigFactory.getInstance().getAuthUrl())
						.path("/auth/oauth2/token").request(MediaType.APPLICATION_JSON)
						.post(Entity.entity(requestBody, MediaType.APPLICATION_JSON), String.class);

				response = objectMapper.readValue(api, AuthTokenResponse.class);

				stampConfigEx.setExpiresIn(response.getExpiresIn());
				stampConfigEx.setRefreshToken(response.getRefreshToken());
				stampConfigEx.setAccessToken(response.getAccessToken());

				transaction.setEstResp(response.toJson());
				transaction.setEstRespCode((short) 200);
				transaction.setEstDtResp(new Date());

			} else {
				response.setAccessToken(stampConfigEx.getAccessToken());
			}

			return response;
		} catch (ClientErrorException | ServerErrorException e) {
			log.error("login", e);
			transaction.setEstResp(e.getResponse().readEntity(Object.class).toString());
			transaction.setEstRespCode((short) 500);
			transaction.setEstDtResp(new Date());
			throw e;
		} catch (Exception e) {
			log.error("login", e);
			transaction.setEstResp(e.getMessage());
			transaction.setEstRespCode((short) 500);
			transaction.setEstDtResp(new Date());
			throw e;
		} finally {
			// log the transaction but only log if there really is a call to request for
			// token
			if (proceedToRequest) {
				TCkMstEstampMethod method = ckMstEstampMethodDao.find(grantType);
				if (null == method)
					throw new ProcessingException("E-Stamp Method null : " + grantType);

				Date date = new Date();
				transaction.setTCkMstEstampMethod(method);
				transaction.setEstDtLupd(date);
				transaction.setEstUidLupd("SYS");
				ckEstampTxnDao.add(transaction);
			}

			// update the refreshToken in sysparam if there are any
			Calendar dtExecuted = Calendar.getInstance();
			// add 20 mins buffer to last execution time so that the next run, comparison
			// will give us true to request for new token
			dtExecuted.add(Calendar.MINUTE, 20);
			stampConfigEx.setLastRunTime(dtExecuted.getTime().getTime());
			stampRefreshToken.setSysVal(objectMapper.writeValueAsString(stampConfigEx));
			stampRefreshToken.setSysDtLupd(new Date());
			stampRefreshToken.setSysUidLupd("SYS");
			sysParamDao.update(stampRefreshToken);

		}
	}

	@Override
	public void stampDocument(StampRequest request) throws Exception {
		log.debug("stampDocument");

		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(TCkEstampDoc.class);
			criteria.add(Restrictions.eq("TCkMstServiceType.svctId", request.getServiceId()));
			criteria.add(Restrictions.eq("esdInvId", request.getInvId()));
			criteria.add(Restrictions.eq("esdInvType", request.getInvType()));
			Optional<TCkEstampDoc> opTCkEstampDoc = Optional.ofNullable(ckEstampDocDao.getOne(criteria));
			if (opTCkEstampDoc.isPresent())
				throw new ProcessingException("transaction already create:" + request.getInvId());

			Date now = new Date();

			if (StringUtils.isEmpty(request.getServiceId()))
				throw new ParameterException("param serviceId is null");

			if (StringUtils.isEmpty(request.getInvId()))
				throw new ParameterException("param invId is null");

			if (StringUtils.isEmpty(request.getInvType()))
				throw new ParameterException("param invType is null");

			if (StringUtils.isEmpty(request.getPathFile()))
				throw new ParameterException("param pathFile is null");

			TCkMstServiceType tCkMstServiceType = ckMstServiceTypeDao.find(request.getServiceId());
			if (null == tCkMstServiceType)
				throw new ProcessingException("serviceType not found:" + request.getServiceId());

			TCkEstampDoc tCkEstampDoc = new TCkEstampDoc();
			tCkEstampDoc.setEsdId(CkUtil.generateId("ESTMP"));
			tCkEstampDoc.setTCkMstServiceType(tCkMstServiceType);
			tCkEstampDoc.setEsdInvId(request.getInvId());
			tCkEstampDoc.setEsdFilename(request.getFilename());
			tCkEstampDoc.setEsdInvType(request.getInvType().toUpperCase());
			tCkEstampDoc.setEsdStampStatus(CkEstampDoc.Status.NEW.name());
			tCkEstampDoc.setEsdDoc(request.getPathFile());
			tCkEstampDoc.setEsdStatus(Constant.ACTIVE_STATUS);
			tCkEstampDoc.setEsdUidCreate("SYS");
			tCkEstampDoc.setEsdDtCreate(now);
			tCkEstampDoc.setEsdUidLupd("SYS");
			tCkEstampDoc.setEsdDtLupd(now);
			ckEstampDocDao.add(tCkEstampDoc);
		} catch (Exception e) {
			log.error("stampDocument", e);
			throw e;
		}

	}

	@Override
	public void stampSendDocument(TCkEstampDoc tCkEstampDoc, AuthTokenResponse authToken) throws Exception {
		log.debug("stampResendDocument");

		try {
			if (null == tCkEstampDoc)
				throw new ProcessingException("tCkEstampDoc is null");

			StampRequest request = new StampRequest();
			request.setDoc(Files.readAllBytes(Paths.get(tCkEstampDoc.getEsdDoc())));
			request.setFilename(tCkEstampDoc.getEsdFilename());

			List<StampAnnotations> annotations = new ArrayList<>();
			StampAnnotations annotation = new StampAnnotations();
			annotation.setPage(1);
			if (tCkEstampDoc.getTCkMstServiceType().getSvctId().equals(ServiceTypes.CLICDO.getId())) {
				annotation.setPositionX(490);
				annotation.setPositionY(590);
				annotation.setElementWidth(80);
				annotation.setElementHeight(80);
				annotation.setCanvasWidth(595);
				annotation.setCanvasHeight(841);
			} else {
				annotation.setPositionX(490);
				annotation.setPositionY(590);
				annotation.setElementWidth(80);
				annotation.setElementHeight(80);
				annotation.setCanvasWidth(595);
				annotation.setCanvasHeight(841);
			}
			annotation.setTypeOf("meterai");
			annotations.add(annotation);
			request.setAnnotations(annotations);
			StampResponse response = sendDocStampRequest(request, authToken);
			// this is unlikely to happen as in between calls, it throws exception, but ok
			if (null == response) {
				tCkEstampDoc.setEsdStampStatus(CkEstampDoc.Status.FAILED.name());
				ckEstampDocDao.update(tCkEstampDoc);
			} else {
				Optional<String> opStampingStatus = Optional
						.ofNullable(response.getData().getAttributes().getStampingStatus());

				Optional<String> opResponseId = Optional.ofNullable(response.getData().getId());

				// if the stamping_status is not present, check if there is responseId, then set
				// to in_progress, otherwise retain in NEW
				tCkEstampDoc.setEsdStampStatus(opStampingStatus.isPresent() ? opStampingStatus.get().toUpperCase()
						: opResponseId.isPresent() ? CkEstampDoc.Status.IN_PROGRESS.name()
								: CkEstampDoc.Status.NEW.name());
				tCkEstampDoc.setEsdFilename(response.getData().getAttributes().getFilename());
				tCkEstampDoc.setEsdDocId(response.getData().getId());
				tCkEstampDoc.setEsdDocUrl(response.getData().getAttributes().getDocUrl());
				tCkEstampDoc.setEsdDtLupd(new Date());
				ckEstampDocDao.update(tCkEstampDoc);
			}
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public void stampDocumentDetail(TCkEstampDoc tCkEstampDoc, AuthTokenResponse authToken) throws Exception {
		log.debug("stampDocumentDetail");

		try {
			if (null == tCkEstampDoc)
				throw new ProcessingException("tCkEstampDoc is null");

			if (StringUtils.isNotBlank(tCkEstampDoc.getEsdDocId())) {
				StampResponse response = sendStampedDocumentDetailsRequest(tCkEstampDoc.getEsdDocId(), authToken);
				if (null != response) {
					// will update either IN_PROGRESS or SUCCESS from eMeterai stamping_status
					// reponse body
					tCkEstampDoc
							.setEsdStampStatus(response.getData().getAttributes().getStampingStatus().toUpperCase());
					tCkEstampDoc.setEsdDtLupd(new Date());
					ckEstampDocDao.update(tCkEstampDoc);
				}
			}

		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public StampDownloadResponse stampDocumentDownload(TCkEstampDoc tCkEstampDoc, AuthTokenResponse authToken)
			throws Exception {
		log.debug("stampDocumentDownload");

		try {
			if (null == tCkEstampDoc)
				throw new ProcessingException("tCkEstampDoc is null");

			StampDownloadResponse response = sendStampedDocumentDownloadRequest(tCkEstampDoc.getEsdDocId(), authToken);
			if (null != response) {
				tCkEstampDoc.setEsdStampStatus(CkEstampDoc.Status.FINISH.name());
				tCkEstampDoc.setEsdDtLupd(new Date());
				ckEstampDocDao.update(tCkEstampDoc);

				response.setServiceId(tCkEstampDoc.getTCkMstServiceType().getSvctId());
				response.setInvId(tCkEstampDoc.getEsdInvId());
				response.setInvType(tCkEstampDoc.getEsdInvType());
				response.setStampStatus(tCkEstampDoc.getEsdStampStatus());
				response.setFilename(tCkEstampDoc.getEsdFilename());
				response.setDocumentId(tCkEstampDoc.getEsdDocId());

			}
			return response;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public StampResponse stampCallback(StampCallbackRequest request) throws Exception {
		log.debug("stampCallback");

		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(TCkEstampDoc.class);
			criteria.add(Restrictions.eq("esdInvId", request.getDocumentId()));
			Optional<TCkEstampDoc> opTCkEstampDoc = Optional.ofNullable(ckEstampDocDao.getOne(criteria));
			if (!opTCkEstampDoc.isPresent())
				throw new ProcessingException("transaction not found with id:" + request.getDocumentId());

			opTCkEstampDoc.get().setEsdStampStatus(request.getStampingStatus().toUpperCase());
			opTCkEstampDoc.get().setEsdDocUrl(request.getDocUrl());
			opTCkEstampDoc.get().setEsdDtLupd(new Date());
			ckEstampDocDao.update(opTCkEstampDoc.get());

			StampResponse response = new StampResponse();
			Data data = new Data();
			data.setId(request.getDocumentId());
			data.setType("document");
			data.getAttributes().setFilename(opTCkEstampDoc.get().getEsdFilename());
			data.getAttributes().setStampingStatus(opTCkEstampDoc.get().getEsdStampStatus());
			data.getAttributes().setTypeOfMeterai("meterai");
			data.getAttributes().setCreatedAt(new Date());
			data.getAttributes().setUpdatedAt(new Date());
			response.setData(data);
			return response;
		} catch (Exception e) {
			throw e;
		}
	}

	///////////////////
	// HELPER METHODS
	///////////////////

	private StampResponse sendDocStampRequest(StampRequest request, AuthTokenResponse authToken) throws Exception {
		log.debug("sendDocStampRequest");

		TCkEstampTxn transaction = createEStampTxn();
		try {

			TCoreSysparam sysParam = sysParamDao.find(KEY_STAMP_GATEWAY_CALLBACK);
			if (sysParam == null)
				throw new EntityNotFoundException("sysParam " + KEY_STAMP_GATEWAY_CALLBACK + " not configured");

			request.setServiceId(null);
			request.setInvId(null);
			request.setInvType(null);
			request.setCallbackUrl(sysParam.getSysVal());
			transaction.setEstReq(request.toJson());

			StampResponse response = getClient().target(StampGatewayConfigFactory.getInstance().getBaseUrl())
					.path("/documents/stamp").request(MediaType.APPLICATION_JSON)
					.header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken.getAccessToken())
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
					.post(Entity.entity(request, MediaType.APPLICATION_JSON), StampResponse.class);

			transaction.setEstResp(response.toJson());
			transaction.setEstRespCode((short) 200);
			transaction.setEstDtResp(new Date());
			return response;
		} catch (ClientErrorException | ServerErrorException e) {
			log.error("sendDocStampRequest", e);
			transaction.setEstResp(e.getResponse().readEntity(Object.class).toString());
			transaction.setEstDtResp(new Date());
			transaction.setEstRespCode((short) 500);

			throw e;
		} catch (Exception e) {
			log.error("sendDocStampRequest", e);
			transaction.setEstResp(e.getMessage());
			transaction.setEstDtResp(new Date());
			transaction.setEstRespCode((short) 500);

			throw e;
		} finally {
			TCkMstEstampMethod method = ckMstEstampMethodDao.find(EstampMethod.STAMP_DOCUMENT.getId());
			if (null == method)
				throw new ProcessingException("E-Stamp Method null : " + EstampMethod.STAMP_DOCUMENT.getId());

			transaction.setEstDtLupd(new Date());
			transaction.setEstUidLupd("SYS");
			transaction.setTCkMstEstampMethod(method);
			ckEstampTxnDao.add(transaction);

		}
	}

	public StampResponse sendStampedDocumentDetailsRequest(String id, AuthTokenResponse authToken) throws Exception {
		log.debug("sendStampedDocumentDetails");

		TCkEstampTxn transaction = createEStampTxn();

		try {

			String pathUri = "/documents/" + id;
			transaction.setEstReq(StampGatewayConfigFactory.getInstance().getBaseUrl() + pathUri);

			StampResponse response = getClient().target(StampGatewayConfigFactory.getInstance().getBaseUrl())
					.path(pathUri).request(MediaType.MULTIPART_FORM_DATA)
					.header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken.getAccessToken()).get(StampResponse.class);

			transaction.setEstResp(response.toJson());
			transaction.setEstRespCode((short) 200);
			transaction.setEstDtResp(new Date());
			return response;

		} catch (ClientErrorException | ServerErrorException e) {
			log.error("sendStampedDocumentDetails", e);

			transaction.setEstResp(e.getResponse().readEntity(Object.class).toString());
			transaction.setEstDtResp(new Date());
			transaction.setEstRespCode((short) 500);
			throw e;
		} catch (Exception e) {
			log.error("sendStampedDocumentDetailsRequest", e);
			transaction.setEstResp(e.getMessage());
			transaction.setEstDtResp(new Date());
			transaction.setEstRespCode((short) 500);
			throw e;
		} finally {
			TCkMstEstampMethod method = ckMstEstampMethodDao.find(EstampMethod.STAMP_DOCUMENT_DETAIL.getId());
			if (null == method)
				throw new ProcessingException("E-Stamp Method null : " + EstampMethod.STAMP_DOCUMENT_DETAIL.getId());
			transaction.setTCkMstEstampMethod(method);
			transaction.setEstDtLupd(new Date());
			transaction.setEstUidLupd("SYS");
			ckEstampTxnDao.add(transaction);
		}
	}

	public StampDownloadResponse sendStampedDocumentDownloadRequest(String id, AuthTokenResponse authToken)
			throws Exception {
		log.debug("sendStampedDocumentDownloadRequest");

		TCkEstampTxn transaction = createEStampTxn();

		StampDownloadResponse response = new StampDownloadResponse();

		try {

			String url = "/documents/" + id + "/download";
			transaction.setEstReq(StampGatewayConfigFactory.getInstance().getBaseUrl() + url);

			byte[] base64Stream = getClient().target(StampGatewayConfigFactory.getInstance().getBaseUrl()).path(url)
					.request(MediaType.APPLICATION_OCTET_STREAM)
					.header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken.getAccessToken()).get(byte[].class);

			response.setContent(base64Stream);

			transaction.setEstResp(response.toString());
			transaction.setEstRespCode((short) 200);
			ckEstampTxnDao.update(transaction);
			return response;
		} catch (ClientErrorException | ServerErrorException e) {
			log.error("sendStampedDocumentDownloadRequest", e);

			transaction.setEstResp(e.getResponse().readEntity(Object.class).toString());
			transaction.setEstDtResp(new Date());
			transaction.setEstRespCode((short) 500);

			return null;
		} catch (Exception e) {
			log.error("sendStampedDocumentDownloadRequest", e);

			transaction.setEstResp(e.getMessage());
			transaction.setEstDtResp(new Date());
			transaction.setEstRespCode((short) 500);

			throw e;
		} finally {
			TCkMstEstampMethod method = ckMstEstampMethodDao.find(EstampMethod.STAMP_DOWNLOAD.getId());
			if (null == method)
				throw new ProcessingException("E-Stamp Method null : " + EstampMethod.STAMP_DOWNLOAD.getId());
			transaction.setTCkMstEstampMethod(method);
			transaction.setEstDtLupd(new Date());
			transaction.setEstUidLupd("SYS");
			ckEstampTxnDao.add(transaction);
		}
	}

	private StampConfig getGatewayConfigSysParam(String sysParamKey) throws Exception {
		log.debug("getGatewayConfigFromSysParam");
		try {
			if (StringUtils.isEmpty(sysParamKey))
				throw new ParameterException("sysParamKey is null");

			TCoreSysparam sysParam = sysParamDao.find(sysParamKey);

			String gwConfig = null;
			if (sysParam != null) {
				gwConfig = sysParam.getSysVal();
			}

			StampConfig config = null;
			if (StringUtils.isNotBlank(gwConfig)) {
				ObjectMapper mapper = new ObjectMapper();
				config = mapper.readValue(gwConfig, StampConfig.class);
			}
			return config;
		} catch (Exception e) {
			log.error("getGatewayConfigFromSysParam " + e);
			throw e;
		}
	}

	private TCkEstampTxn createEStampTxn() throws Exception {
		log.debug("transaction");

		Date now = new Date();
		TCkEstampTxn tCkEstampTxn = new TCkEstampTxn();
		tCkEstampTxn.setEstId(CkUtil.generateId("CKEST"));
		tCkEstampTxn.setEstDtReq(now);
		tCkEstampTxn.setEstStatus(Constant.ACTIVE_STATUS);
		tCkEstampTxn.setEstUidCreate("SYS");
		tCkEstampTxn.setEstDtCreate(now);
		return tCkEstampTxn;
	}

}
