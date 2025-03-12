package com.guudint.clickargo.payment.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.common.CkUtil;
import com.guudint.clickargo.common.RecordStatus;
import com.guudint.clickargo.common.service.impl.CkCoreAccnService;
import com.guudint.clickargo.master.dao.CoreAccnDao;
import com.guudint.clickargo.master.dto.CkMstPaymentType;
import com.guudint.clickargo.master.dto.CkMstServiceType;
import com.guudint.clickargo.master.enums.AccountTypes;
import com.guudint.clickargo.master.enums.Currencies;
import com.guudint.clickargo.master.enums.ServiceTypes;
import com.guudint.clickargo.master.model.TCkMstPaymentType;
import com.guudint.clickargo.master.model.TCkMstServiceType;
import com.guudint.clickargo.payment.dto.CkPaymentTxn;
import com.guudint.clickargo.payment.enums.PaymentStates;
import com.guudint.clickargo.payment.enums.PaymentTypes;
import com.guudint.clickargo.payment.model.TCkPaymentTxn;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.ccm.model.TCoreAccn;
import com.vcc.camelone.ccm.util.Constant;
import com.vcc.camelone.common.controller.entity.EntityFilterRequest;
import com.vcc.camelone.common.controller.entity.EntityOrderBy;
import com.vcc.camelone.common.controller.entity.EntityWhere;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.exception.ValidationException;
import com.vcc.camelone.common.service.ServiceStatus;
import com.vcc.camelone.common.service.entity.AbstractEntityService;
import com.vcc.camelone.locale.dto.CoreMstLocale;
import com.vcc.camelone.master.dto.MstCurrency;
import com.vcc.camelone.master.model.TMstCurrency;
import com.vcc.camelone.util.PrincipalUtilService;

public class CkPaymentTxnService extends AbstractEntityService<TCkPaymentTxn, String, CkPaymentTxn> {

	// Static Attributes
	////////////////////
	private static Logger log = Logger.getLogger(CkPaymentAuditService.class);
	private static String auditTag = "PAYMENT TXN";
	private static String tableName = "T_CK_PAYMENT_TXN";

	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	public static final String PREFIX_DO_TXN = "TXNDO";
	public static final String POSTFIX_VA_IDR = "2SP_VA_IDR";

	@Autowired
	private CkCoreAccnService ckCoreAccnService;

	@Autowired
	private CoreAccnDao coreAccnDao;

	@Autowired
	private PrincipalUtilService principalUtilService;

	@Autowired
	private CkPaymentTxnLogService paymentTxnLogService;

	public CkPaymentTxnService() {
		super("ckPaymentTxnDao", auditTag, TCkPaymentTxn.class.getName(), tableName);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CkPaymentTxn findById(String id) throws ParameterException, EntityNotFoundException, ProcessingException {
		log.debug("findById");

		try {
			if (StringUtils.isEmpty(id))
				throw new ParameterException("param id null or empty");

			TCkPaymentTxn entity = dao.find(id);
			if (null == entity)
				throw new EntityNotFoundException("id: " + id);
			this.initEnity(entity);

			return this.dtoFromEntity(entity);
		} catch (ParameterException | EntityNotFoundException ex) {
			log.error("findById", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("findById", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	public CkPaymentTxn deleteById(String id, Principal principal)
			throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public List<CkPaymentTxn> filterBy(EntityFilterRequest filterRequest)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		log.debug("filterBy");

		try {
			if (null == filterRequest)
				throw new ParameterException("param filterRequest null");

			CkPaymentTxn dto = this.whereDto(filterRequest);
			if (null == dto)
				throw new ProcessingException("whereDto null");

			filterRequest.setTotalRecords(super.countByAnd(dto));

			String selectClause = "from TCkPaymentTxn o ";
			String orderByClause = this.formatOrderBy(filterRequest.getOrderBy().toString());
			List<TCkPaymentTxn> entities = super.findEntitiesByAnd(dto, selectClause, orderByClause,
					filterRequest.getDisplayLength(), filterRequest.getDisplayStart());
			List<CkPaymentTxn> dtos = entities.stream().map(x -> {
				try {
					return dtoFromEntity(x);
				} catch (ParameterException | ProcessingException e) {
					log.error("filterBy", e);
				}
				return null;
			}).collect(Collectors.toList());

			return dtos;
		} catch (ParameterException | ProcessingException ex) {
			log.error("filterBy", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("filterBy", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	protected TCkPaymentTxn initEnity(TCkPaymentTxn entity) throws ParameterException, ProcessingException {
		if (entity != null) {
			Hibernate.initialize(entity.getTCkMstPaymentType());
			Hibernate.initialize(entity.getTCkMstServiceType());
			Hibernate.initialize(entity.getTCoreAccnByPtxPayee());
			Hibernate.initialize(entity.getTCoreAccnByPtxPayer());
			Hibernate.initialize(entity.getTMstCurrency());
		}

		return entity;

	}

	@Override
	protected TCkPaymentTxn entityFromDTO(CkPaymentTxn dto) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		log.debug("entityFromDTO");
		try {
			if (null == dto)
				throw new ParameterException("dto param null");

			TCkPaymentTxn entity = new TCkPaymentTxn();
			entity = dto.toEntity(entity);
			entity.setTCkMstPaymentType(dto.getTCkMstPaymentType().toEntity(new TCkMstPaymentType()));
			entity.setTCkMstServiceType(dto.getTCkMstServiceType().toEntity(new TCkMstServiceType()));
			entity.setTCoreAccnByPtxPayee(dto.getTCoreAccnByPtxPayee().toEntity(new TCoreAccn()));
			entity.setTCoreAccnByPtxPayer(dto.getTCoreAccnByPtxPayer().toEntity(new TCoreAccn()));
			entity.setTMstCurrency(dto.getTMstCurrency().toEntity(new TMstCurrency()));

			return entity;
		} catch (ParameterException ex) {
			log.error("entityFromDTO", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("entityFromDTO", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected CkPaymentTxn dtoFromEntity(TCkPaymentTxn entity) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		log.debug("dtoFromEntity");
		try {
			if (null == entity)
				throw new ParameterException("param entity null");

			CkPaymentTxn dto = new CkPaymentTxn(entity);
			Optional<TCkMstPaymentType> opckMstPaymentType = Optional.ofNullable(entity.getTCkMstPaymentType());
			if (opckMstPaymentType.isPresent())
				dto.setTCkMstPaymentType(new CkMstPaymentType(opckMstPaymentType.get()));

			Optional<TCkMstServiceType> opCkMstServiceType = Optional.ofNullable(entity.getTCkMstServiceType());
			if (opckMstPaymentType.isPresent())
				dto.setTCkMstServiceType(new CkMstServiceType(opCkMstServiceType.get()));

			Optional<TCoreAccn> opAccnPayee = Optional.ofNullable(entity.getTCoreAccnByPtxPayee());
			if (opAccnPayee.isPresent())
				dto.setTCoreAccnByPtxPayee(new CoreAccn(opAccnPayee.get()));

			Optional<TCoreAccn> opAccnPayer = Optional.ofNullable(entity.getTCoreAccnByPtxPayer());
			if (opAccnPayer.isPresent())
				dto.setTCoreAccnByPtxPayer(new CoreAccn(opAccnPayer.get()));

			Optional<TMstCurrency> opCurrency = Optional.ofNullable(entity.getTMstCurrency());
			if (opCurrency.isPresent())
				dto.setTMstCurrency(new MstCurrency(opCurrency.get()));

			return dto;
		} catch (ParameterException ex) {
			log.error("dtoFromEntity", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("dtoFromEntity", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected String entityKeyFromDTO(CkPaymentTxn dto) throws ParameterException, ProcessingException {
		log.debug("entityKeyFromDTO");

		try {
			if (null == dto)
				throw new ParameterException("dto param null");

			return dto.getPtxId();
		} catch (ParameterException ex) {
			log.error("entityKeyFromDTO", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("entityKeyFromDTO", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected TCkPaymentTxn updateEntity(ACTION attriubte, TCkPaymentTxn entity, Principal principal, Date date)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		log.debug("updateEntity");

		try {
			if (null == entity)
				throw new ParameterException("param entity null");
			if (null == principal)
				throw new ParameterException("param principal null");
			if (null == date)
				throw new ParameterException("param date null");

			Optional<String> opUserId = Optional.ofNullable(principal.getUserId());
			switch (attriubte) {
			case CREATE:
				entity.setPtxUidCreate(opUserId.isPresent() ? opUserId.get() : "SYS");
				entity.setPtxDtCreate(date);
				entity.setPtxUidLupd(opUserId.isPresent() ? opUserId.get() : "SYS");
				entity.setPtxDtLupd(date);
				break;

			case MODIFY:
				entity.setPtxUidLupd(opUserId.isPresent() ? opUserId.get() : "SYS");
				entity.setPtxDtLupd(date);
				break;

			default:
				break;
			}

			return entity;
		} catch (ParameterException ex) {
			log.error("updateEntity", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("updateEntity", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected TCkPaymentTxn updateEntityStatus(TCkPaymentTxn entity, char status)
			throws ParameterException, ProcessingException {
		log.debug("updateEntityStatus");

		try {
			if (null == entity)
				throw new ParameterException("param entity null");

			entity.setPtxStatus(status);
			return entity;
		} catch (ParameterException ex) {
			log.error("updateEntity", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("updateEntityStatus", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected CkPaymentTxn preSaveUpdateDTO(TCkPaymentTxn storedEntity, CkPaymentTxn dto)
			throws ParameterException, ProcessingException {
		log.debug("preSaveUpdateDTO");
		try {
			if (null == storedEntity)
				throw new ParameterException("param storedEntity null");
			if (null == dto)
				throw new ParameterException("param dto null");

			dto.setPtxUidCreate(storedEntity.getPtxUidCreate());
			dto.setPtxDtCreate(storedEntity.getPtxDtCreate());

			return dto;
		} catch (ParameterException ex) {
			log.error("updateEntity", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("preSaveUpdateEntity", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected void preSaveValidation(CkPaymentTxn dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub

	}

	@Override
	protected ServiceStatus preUpdateValidation(CkPaymentTxn dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getWhereClause(CkPaymentTxn dto, boolean wherePrinted)
			throws ParameterException, ProcessingException {
		log.debug("getWhereClause");

		try {
			if (null == dto)
				throw new ParameterException("param dto null");

			Principal principal = principalUtilService.getPrincipal();
			if (principal == null)
				throw new ProcessingException("principal is null");

			StringBuffer searchStatement = new StringBuffer();
			CoreAccn accn = principal.getCoreAccn();

			searchStatement.append(getOperator(wherePrinted)).append("o.ptxStatus = :ptxStatus");
			wherePrinted = true;

			if (accn.getTMstAccnType().getAtypId().equals(AccountTypes.ACC_TYPE_FF.name())
					|| accn.getTMstAccnType().getAtypId().equals(AccountTypes.ACC_TYPE_CO.name())
					|| accn.getTMstAccnType().getAtypId().equals(AccountTypes.ACC_TYPE_SP.name())) {
				searchStatement.append(getOperator(wherePrinted)).append("o.TCoreAccnByPtxPayer.accnId = :accnId");
				wherePrinted = true;
				searchStatement.append(getOperator(wherePrinted)).append("o.ptxPaymentState IN (:inclTxnState)");
				wherePrinted = true;
			} else if (accn.getTMstAccnType().getAtypId().equals(AccountTypes.ACC_TYPE_TO.name())) {
				searchStatement.append(getOperator(wherePrinted)).append("o.TCoreAccnByPtxPayee.accnId = :accnId");
				wherePrinted = true;
				searchStatement.append(getOperator(wherePrinted)).append("o.ptxPaymentState IN (:inclTxnState)");
				wherePrinted = true;
			}

			if (!StringUtils.isEmpty(dto.getPtxId())) {
				searchStatement.append(getOperator(wherePrinted) + "o.ptxId LIKE :ptxId");
				wherePrinted = true;
			}
			Optional<CkMstPaymentType> opMstPaymentType = Optional.ofNullable(dto.getTCkMstPaymentType());
			if (opMstPaymentType.isPresent()) {
				if (StringUtils.isNotBlank(opMstPaymentType.get().getPtyId())) {
					searchStatement.append(getOperator(wherePrinted) + "o.TCkMstPaymentType.ptyId LIKE :ptyId");
					wherePrinted = true;
				}

				if (StringUtils.isNotBlank(opMstPaymentType.get().getPytName())) {
					searchStatement.append(getOperator(wherePrinted) + "o.TCkMstPaymentType.pytName LIKE :pytName");
					wherePrinted = true;
				}
			}

			Optional<MstCurrency> opMstCurrency = Optional.ofNullable(dto.getTMstCurrency());
			if (opMstCurrency.isPresent()) {
				if (StringUtils.isNotBlank(opMstCurrency.get().getCcyCode())) {
					searchStatement.append(getOperator(wherePrinted) + "o.TMstCurrency.ccyCode LIKE :ccyCode");
					wherePrinted = true;
				}
			}

			// add filter for ptxAmount
			if (dto.getPtxAmount() != null && dto.getPtxAmount().compareTo(BigDecimal.ZERO) > 0) {
				searchStatement.append(getOperator(wherePrinted)).append("o.ptxAmount = :ptxAmount");
				wherePrinted = true;
			}

			Optional<CkMstServiceType> opServiceType = Optional.ofNullable(dto.getTCkMstServiceType());
			if (opServiceType.isPresent()) {
				if (StringUtils.isNotBlank(opServiceType.get().getSvctId())) {
					searchStatement.append(getOperator(wherePrinted) + "o.TCkMstServiceType.svctId LIKE :svctId");
					wherePrinted = true;
				}

				if (StringUtils.isNotBlank(opServiceType.get().getSvctName())) {
					searchStatement.append(getOperator(wherePrinted) + "o.TCkMstServiceType.svctName LIKE :svctName");
					wherePrinted = true;
				}
			}

			Optional<CoreAccn> opPayeeAccn = Optional.ofNullable(dto.getTCoreAccnByPtxPayee());
			if (opPayeeAccn.isPresent()) {
				if (StringUtils.isNotBlank(opPayeeAccn.get().getAccnId())) {
					searchStatement
							.append(getOperator(wherePrinted) + "o.TCoreAccnByPtxPayee.accnId LIKE :payeeAccnId");
					wherePrinted = true;
				}

				if (StringUtils.isNotBlank(opPayeeAccn.get().getAccnName())) {
					searchStatement
							.append(getOperator(wherePrinted) + "o.TCoreAccnByPtxPayee.accnId LIKE :payeeAccnName");
					wherePrinted = true;
				}
			}

			Optional<CoreAccn> opPayerAccn = Optional.ofNullable(dto.getTCoreAccnByPtxPayer());
			if (opPayerAccn.isPresent()) {
				if (StringUtils.isNotBlank(opPayerAccn.get().getAccnId())) {
					searchStatement
							.append(getOperator(wherePrinted) + "o.TCoreAccnByPtxPayer.accnId LIKE :payerAccnId");
					wherePrinted = true;
				}

				if (StringUtils.isNotBlank(opPayerAccn.get().getAccnName())) {
					searchStatement
							.append(getOperator(wherePrinted) + "o.TCoreAccnByPtxPayer.accnId LIKE :payerAccnName");
					wherePrinted = true;
				}
			}

			if (!StringUtils.isEmpty(dto.getPtxSvcRef())) {
				searchStatement.append(getOperator(wherePrinted) + "o.ptxSvcRef LIKE :ptxSvcRef");
				wherePrinted = true;
			}

			if (!StringUtils.isEmpty(dto.getPtxMerchantBank())) {
				searchStatement.append(getOperator(wherePrinted) + "o.ptxMerchantBank LIKE :ptxMerchantBank");
				wherePrinted = true;
			}
			if (!StringUtils.isEmpty(dto.getPtxBankRef())) {
				searchStatement.append(getOperator(wherePrinted) + "o.ptxBankRef LIKE :ptxBankRef");
				wherePrinted = true;
			}

			if (!StringUtils.isEmpty(dto.getPtxPaymentState())) {
				searchStatement.append(getOperator(wherePrinted) + "o.ptxPaymentState LIKE :ptxPaymentState");
				wherePrinted = true;
			}

			if (dto.getPtxStatus() != null && Character.isAlphabetic(dto.getPtxStatus())) {
				searchStatement.append(getOperator(wherePrinted) + "o.ptxStatus = :ptxStatus");
				wherePrinted = true;
			}

			if (dto.getPtxDtCreate() != null) {
				searchStatement
						.append(getOperator(wherePrinted) + "DATE_FORMAT(o.ptxDtCreate,'%d/%m/%Y') = :ptxDtCreate");
				wherePrinted = true;
			}

			if (dto.getPtxDtLupd() != null) {
				searchStatement.append(getOperator(wherePrinted) + "DATE_FORMAT(o.ptxDtLupd,'%d/%m/%Y') = :ptxDtLupd");
				wherePrinted = true;
			}

			if (dto.getPtxDtDue() != null) {
				searchStatement.append(getOperator(wherePrinted) + "DATE_FORMAT(o.ptxDtDue,'%d/%m/%Y') = :ptxDtDue");
				wherePrinted = true;
			}

			if (dto.getPtxDtPaid() != null) {
				searchStatement.append(getOperator(wherePrinted) + "DATE_FORMAT(o.ptxDtPaid,'%d/%m/%Y') = :ptxDtPaid");
				wherePrinted = true;
			}

			return searchStatement.toString();
		} catch (ParameterException ex) {
			log.error("getWhereClause", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("getWhereClause", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected HashMap<String, Object> getParameters(CkPaymentTxn dto) throws ParameterException, ProcessingException {
		log.debug("getParameters");

		try {
			if (null == dto)
				throw new ParameterException("param dto null");

			HashMap<String, Object> parameters = new HashMap<String, Object>();

			Principal principal = principalUtilService.getPrincipal();

			if (principal == null)
				throw new ProcessingException("principal is null");
			parameters.put("accnId", principal.getCoreAccn().getAccnId());
			parameters.put("ptxStatus", RecordStatus.ACTIVE.getCode());

			CoreAccn accn = principal.getCoreAccn();

			if (accn.getTMstAccnType().getAtypId().equals(AccountTypes.ACC_TYPE_FF.name())
					|| accn.getTMstAccnType().getAtypId().equals(AccountTypes.ACC_TYPE_CO.name())) {
				if (StringUtils.isNotBlank(dto.getHistory()) && dto.getHistory().equalsIgnoreCase("history")) {
					parameters.put("inclTxnState", new ArrayList<>(Arrays.asList(PaymentStates.SUCCESS.getCode(),
							PaymentStates.PAID.getCode(), PaymentStates.CAN.getCode())));
				} else if (StringUtils.isNotBlank(dto.getHistory()) && dto.getHistory().equalsIgnoreCase("default")) {
					parameters.put("inclTxnState", new ArrayList<>(
							Arrays.asList(PaymentStates.NEW.getCode(), PaymentStates.PAYING.getCode())));
				}
			} else if (accn.getTMstAccnType().getAtypId().equals(AccountTypes.ACC_TYPE_SP.name())) {
				if (StringUtils.isNotBlank(dto.getHistory()) && dto.getHistory().equalsIgnoreCase("history")) {
					parameters.put("inclTxnState", new ArrayList<>(Arrays.asList(PaymentStates.PAID.getCode(),
							PaymentStates.CAN.getCode(), PaymentStates.FAILED.getCode())));
				} else if (StringUtils.isNotBlank(dto.getHistory()) && dto.getHistory().equalsIgnoreCase("default")) {
					parameters.put("inclTxnState",
							new ArrayList<>(Arrays.asList(PaymentStates.NEW.getCode(), PaymentStates.VER.getCode(),
									PaymentStates.VER_BILL.getCode(), PaymentStates.APP.getCode(),
									PaymentStates.APP_BILL.getCode(), PaymentStates.PAYING.getCode())));
				}
			} else if (accn.getTMstAccnType().getAtypId().equals(AccountTypes.ACC_TYPE_TO.name())) {
				if (StringUtils.isNotBlank(dto.getHistory()) && dto.getHistory().equalsIgnoreCase("history")) {
					parameters.put("inclTxnState", new ArrayList<>(Arrays.asList(PaymentStates.PAID.getCode(),
							PaymentStates.CAN.getCode(), PaymentStates.FAILED.getCode())));
				} else if (StringUtils.isNotBlank(dto.getHistory()) && dto.getHistory().equalsIgnoreCase("default")) {
					parameters.put("inclTxnState", new ArrayList<>(Arrays.asList(PaymentStates.PAYING.getCode())));
				}
			}

			if (!StringUtils.isEmpty(dto.getPtxId()))
				parameters.put("ptxId", "%" + dto.getPtxId() + "%");

			Optional<CkMstPaymentType> opMstPaymentType = Optional.ofNullable(dto.getTCkMstPaymentType());
			if (opMstPaymentType.isPresent()) {
				if (StringUtils.isNotBlank(opMstPaymentType.get().getPtyId()))
					parameters.put("ptyId", "%" + opMstPaymentType.get().getPtyId() + "%");

				if (StringUtils.isNotBlank(opMstPaymentType.get().getPytName()))
					parameters.put("pytName", "%" + opMstPaymentType.get().getPytName() + "%");
			}

			Optional<MstCurrency> opMstCurrency = Optional.ofNullable(dto.getTMstCurrency());
			if (opMstCurrency.isPresent()) {
				if (StringUtils.isNotBlank(opMstCurrency.get().getCcyCode()))
					parameters.put("ccyCode", "%" + opMstCurrency.get().getCcyCode() + "%");
			}

			// add filter for ptxAmount
			if (dto.getPtxAmount() != null && dto.getPtxAmount().compareTo(BigDecimal.ZERO) > 0) {
				parameters.put("ptxAmount", dto.getPtxAmount());
			}

			Optional<CkMstServiceType> opServiceType = Optional.ofNullable(dto.getTCkMstServiceType());
			if (opServiceType.isPresent()) {
				if (StringUtils.isNotBlank(opServiceType.get().getSvctId()))
					parameters.put("svctId", "%" + opServiceType.get().getSvctId() + "%");

				if (StringUtils.isNotBlank(opServiceType.get().getSvctName()))
					parameters.put("svctName", "%" + opServiceType.get().getSvctName() + "%");
			}

			Optional<CoreAccn> opPayeeAccn = Optional.ofNullable(dto.getTCoreAccnByPtxPayee());
			if (opPayeeAccn.isPresent()) {
				if (StringUtils.isNotBlank(opPayeeAccn.get().getAccnId()))
					parameters.put("payeeAccnId", "%" + opPayeeAccn.get().getAccnId() + "%");

				if (StringUtils.isNotBlank(opPayeeAccn.get().getAccnName()))
					parameters.put("payeeAccnName", "%" + opPayeeAccn.get().getAccnName() + "%");
			}

			Optional<CoreAccn> opPayerAccn = Optional.ofNullable(dto.getTCoreAccnByPtxPayer());
			if (opPayerAccn.isPresent()) {
				if (StringUtils.isNotBlank(opPayerAccn.get().getAccnId()))
					parameters.put("payerAccnId", "%" + opPayerAccn.get().getAccnId() + "%");

				if (StringUtils.isNotBlank(opPayerAccn.get().getAccnName()))
					parameters.put("payerAccnName", "%" + opPayerAccn.get().getAccnName() + "%");
			}

			if (!StringUtils.isEmpty(dto.getPtxSvcRef()))
				parameters.put("ptxSvcRef", "%" + dto.getPtxSvcRef() + "%");

			if (!StringUtils.isEmpty(dto.getPtxMerchantBank()))
				parameters.put("ptxMerchantBank", "%" + dto.getPtxMerchantBank() + "%");

			if (!StringUtils.isEmpty(dto.getPtxBankRef()))
				parameters.put("ptxBankRef", "%" + dto.getPtxBankRef() + "%");

			if (!StringUtils.isEmpty(dto.getPtxPaymentState()))
				parameters.put("ptxPaymentState", "%" + dto.getPtxPaymentState() + "%");

			if (dto.getPtxStatus() != null && Character.isAlphabetic(dto.getPtxStatus()))
				parameters.put("ptxStatus", dto.getPtxStatus());

			if (dto.getPtxDtCreate() != null)
				parameters.put("ptxDtCreate", sdf.format(dto.getPtxDtCreate()));

			if (dto.getPtxDtLupd() != null)
				parameters.put("ptxDtLupd", sdf.format(dto.getPtxDtLupd()));

			if (dto.getPtxDtDue() != null)
				parameters.put("ptxDtDue", sdf.format(dto.getPtxDtDue()));

			if (dto.getPtxDtPaid() != null)
				parameters.put("ptxDtPaid", sdf.format(dto.getPtxDtPaid()));

			return parameters;
		} catch (ParameterException ex) {
			log.error("getParameters", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("getParameters", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected CkPaymentTxn whereDto(EntityFilterRequest filterRequest) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		log.debug("whereDto");

		try {
			if (null == filterRequest)
				throw new ParameterException("param filterRequest null");

			CkPaymentTxn dto = new CkPaymentTxn();
			CkMstPaymentType paymentType = new CkMstPaymentType();
			CkMstServiceType serviceType = new CkMstServiceType();
			CoreAccn payeeAccn = new CoreAccn();
			CoreAccn payerAccn = new CoreAccn();
			MstCurrency currency = new MstCurrency();
			for (EntityWhere entityWhere : filterRequest.getWhereList()) {
				Optional<String> opValue = Optional.ofNullable(entityWhere.getValue());
				if (!opValue.isPresent())
					continue;

				if (entityWhere.getAttribute().equalsIgnoreCase("ptxId"))
					dto.setPtxId(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("TCkMstPaymentType.ptyId"))
					paymentType.setPtyId(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("TCkMstPaymentType.pytName"))
					paymentType.setPytName(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("TCkMstServiceType.svctId"))
					serviceType.setSvctId(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("TCkMstServiceType.svctId"))
					serviceType.setSvctName(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("TCoreAccnByPtxPayee.accnId"))
					payeeAccn.setAccnId(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("TCoreAccnByPtxPayee.accnName"))
					payeeAccn.setAccnName(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("TCoreAccnByPtxPayer.accnId"))
					payerAccn.setAccnId(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("TCoreAccnByPtxPayee.accnName"))
					payerAccn.setAccnName(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("TMstCurrency.ccyCode"))
					currency.setCcyCode(opValue.get());
				// add filter for ptxAmount
				if (entityWhere.getAttribute().equalsIgnoreCase("ptxAmount")) {
					BigDecimal amount = new BigDecimal(opValue.get());
					dto.setPtxAmount(amount);
				}
				if (entityWhere.getAttribute().equalsIgnoreCase("ptxSvcRef"))
					dto.setPtxSvcRef(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("ptxMerchantBank"))
					dto.setPtxMerchantBank(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("ptxBankRef"))
					dto.setPtxBankRef(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("ptxPaymentState"))
					dto.setPtxPaymentState(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("ptxStatus"))
					dto.setPtxStatus(opValue.get().charAt(0));
				if (entityWhere.getAttribute().equalsIgnoreCase("ptxDtCreate"))
					dto.setPtxDtCreate(sdf.parse(opValue.get()));
				if (entityWhere.getAttribute().equalsIgnoreCase("ptxDtLupd"))
					dto.setPtxDtLupd(sdf.parse(opValue.get()));
				if (entityWhere.getAttribute().equalsIgnoreCase("ptxDtDue"))
					dto.setPtxDtDue(sdf.parse(opValue.get()));
				if (entityWhere.getAttribute().equalsIgnoreCase("ptxDtPaid"))
					dto.setPtxDtPaid(sdf.parse(opValue.get()));

				// history toggle
				if (entityWhere.getAttribute().equalsIgnoreCase("history")) {
					dto.setHistory(opValue.get());
				} else if (entityWhere.getAttribute().equalsIgnoreCase("default")) {
					dto.setHistory(opValue.get());
				}
			}

			dto.setTCkMstPaymentType(paymentType);
			dto.setTCkMstServiceType(serviceType);
			dto.setTCoreAccnByPtxPayee(payeeAccn);
			dto.setTCoreAccnByPtxPayer(payerAccn);
			dto.setTMstCurrency(currency);
			return dto;
		} catch (ParameterException ex) {
			log.error("whereDto", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("whereDto", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected CoreMstLocale getCoreMstLocale(CkPaymentTxn dto)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CkPaymentTxn setCoreMstLocale(CoreMstLocale coreMstLocale, CkPaymentTxn dto)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public TCkPaymentTxn createPaymentTxn(ServiceTypes serviceTypes, List<String> jobIds, Principal principal)
			throws Exception {
		Date now = new Date();
		TCkPaymentTxn txn = new TCkPaymentTxn();
		txn.setPtxId(CkUtil.generateIdSynch(PREFIX_DO_TXN));
		txn.setPtxDtCreate(now);
		txn.setPtxStatus(RecordStatus.ACTIVE.getCode());
		txn.setPtxUidCreate(principal.getUserId());
		txn.setPtxDtLupd(now);
		txn.setPtxUidLupd(principal.getUserId());
		txn.setPtxSvcRef(StringUtils.join(jobIds, ","));

		txn.setTMstCurrency(
				new TMstCurrency(Currencies.IDR.getCode(), Currencies.IDR.getDesc(), RecordStatus.ACTIVE.getCode()));
		txn.setTCkMstPaymentType(
				new TCkMstPaymentType(PaymentTypes.VIRTUAL_ACCOUNT.getId(), PaymentTypes.VIRTUAL_ACCOUNT.getDesc()));
		txn.setTCkMstServiceType(new TCkMstServiceType(serviceTypes.getId(), serviceTypes.getDesc()));

		// payer is the one who executes the pay
		txn.setTCoreAccnByPtxPayer(principal.getCoreAccn().toEntity(new TCoreAccn()));
		StringBuilder keyStr = new StringBuilder();
		String accnType = ckCoreAccnService.getPrincipalAccountType(principal);
		if (accnType.equalsIgnoreCase(AccountTypes.ACC_TYPE_FF.name()))
			keyStr.append("FF").append(POSTFIX_VA_IDR);
		else if (accnType.equalsIgnoreCase(AccountTypes.ACC_TYPE_CO.name()))
			keyStr.append("CO").append(POSTFIX_VA_IDR);

		txn.setPtxPayerBankAccn(
				ckCoreAccnService.getAccnConfig(principal.getCoreAccn().getAccnId(), keyStr.toString()).getAcfgVal());

		// payee is the service provider
		Collection<TCoreAccn> serviceProviderAccnList = coreAccnDao.getByAccnTypeAndStatus(AccountTypes.ACC_TYPE_SP,
				Constant.ACTIVE_STATUS);
		if (serviceProviderAccnList.size() != 1)
			throw new ProcessingException(
					"None or more than 1 active service provider/platform found. Must have one and only one active service provider/platform at any time!");

		TCoreAccn serviceProviderAccn = serviceProviderAccnList.iterator().next();
		txn.setTCoreAccnByPtxPayee(serviceProviderAccn);
		txn.setPtxMerchantBank(
				ckCoreAccnService.getAccnConfig(serviceProviderAccn.getAccnId(), "DO_SP_BNK").getAcfgVal());
		txn.setPtxPayeeBankAccn(
				ckCoreAccnService.getAccnConfig(serviceProviderAccn.getAccnId(), "DO_SP_BNK_ACCN").getAcfgVal());
		dao.add(txn);

		// Log payment txn
		paymentTxnLogService.createPaymentTxnLog(txn, CkPaymentTxnLogService.CREATE_REMARKS, principal);

		// Retrieve from db to commit?
		return this.findEntityByKey(txn.getPtxId());
	}

	public String getLatestNewPaymentTxn(String va) throws Exception {
		String hql = "from TCkPaymentTxn o where o.ptxPaymentState in (:states) "
				+ "and o.ptxBankRef is not null and o.ptxStatus=:recStatus "
				+ " and o.ptxPayerBankAccn=:va order by o.ptxDtCreate desc";
		Map<String, Object> params = new HashMap<>();
		params.put("states", Arrays.asList(PaymentStates.NEW.getCode(), PaymentStates.PAYING.getCode()));
		params.put("recStatus", RecordStatus.ACTIVE.getCode());
		params.put("va", va);
		List<TCkPaymentTxn> listNewTxn = dao.getByQuery(hql, params);
		if (listNewTxn != null && listNewTxn.size() > 0) {
			return listNewTxn.get(0).getPtxId();
		}

		return null;

	}

	/**
	 * 
	 * @param jobId
	 * @return
	 * @throws Exception
	 */
	public List<TCkPaymentTxn> findBySvcRef(String jobId) throws Exception {
		String hql = "FROM TCkPaymentTxn o where o.ptxSvcRef LIKE :jobId and o.ptxPaymentState = :state";
		Map<String, Object> params = new HashMap<>();
		params.put("jobId", '%' + jobId + '%');
		params.put("state", PaymentStates.NEW.getCode());
		List<TCkPaymentTxn> listNewTxn = dao.getByQuery(hql, params);
		if (listNewTxn != null && listNewTxn.size() > 0) {
			return listNewTxn;
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param orderBy
	 * @return
	 * @throws Exception
	 */
	protected EntityOrderBy formatOrderByObj(EntityOrderBy orderBy) throws Exception {

		if (orderBy == null)
			return null;

		if (StringUtils.isEmpty(orderBy.getAttribute()))
			return null;

		String newAttr = formatOrderBy(orderBy.getAttribute());
		if (StringUtils.isEmpty(newAttr))
			return orderBy;

		orderBy.setAttribute(newAttr);
		return orderBy;

	}

	/**
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.guudint.clickargo.job.service.AbstractJobService#formatOrderBy(java.lang.String)
	 *
	 */
	protected String formatOrderBy(String attribute) throws Exception {

		String newAttr = attribute;
		if (StringUtils.contains(newAttr, "tmstCurrency"))
			newAttr = newAttr.replace("tmstCurrency", "TMstCurrency");

		if (StringUtils.contains(newAttr, "tckMstPaymentType"))
			newAttr = newAttr.replace("tckMstPaymentType", "TCkMstPaymentType");

		if (StringUtils.contains(newAttr, "tckMstServiceType"))
			newAttr = newAttr.replace("tckMstServiceType", "TCkMstServiceType");

		if (StringUtils.contains(newAttr, "tcoreAccnByPtxPayee"))
			newAttr = newAttr.replace("tcoreAccnByPtxPayee", "TCoreAccnByPtxPayee");

		if (StringUtils.contains(newAttr, "tcoreAccnByPtxPayer"))
			newAttr = newAttr.replace("tcoreAccnByPtxPayer", "TCoreAccnByPtxPayer");

		return newAttr;
	}
}
