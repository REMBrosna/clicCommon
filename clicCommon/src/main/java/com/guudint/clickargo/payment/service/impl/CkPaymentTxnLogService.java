package com.guudint.clickargo.payment.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.common.CkUtil;
import com.guudint.clickargo.master.dto.CkMstPaymentType;
import com.guudint.clickargo.master.dto.CkMstServiceType;
import com.guudint.clickargo.master.model.TCkMstPaymentType;
import com.guudint.clickargo.master.model.TCkMstServiceType;
import com.guudint.clickargo.payment.dto.CkPaymentTxn;
import com.guudint.clickargo.payment.dto.CkPaymentTxnLog;
import com.guudint.clickargo.payment.model.TCkPaymentTxn;
import com.guudint.clickargo.payment.model.TCkPaymentTxnLog;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.ccm.model.TCoreAccn;
import com.vcc.camelone.ccm.util.Constant;
import com.vcc.camelone.common.controller.entity.EntityFilterRequest;
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

public class CkPaymentTxnLogService extends AbstractEntityService<TCkPaymentTxnLog, String, CkPaymentTxnLog> {

	private static Logger log = Logger.getLogger(CkPaymentTxnLogService.class);
	private static String auditTag = "PAYMENT TXN LOG";
	private static String tableName = "T_CK_PAYMENT_TXN_LOG";

	public static final String PREFIX_TXN_LOG = "TXNL";
	public static final String CREATE_REMARKS = "PAYMENT TRANSACTION CREATED";
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	public CkPaymentTxnLogService() {
		super("ckPaymentTxnLogDao", auditTag, TCkPaymentTxnLog.class.getName(), tableName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public CkPaymentTxnLog findById(String id) throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CkPaymentTxnLog deleteById(String id, Principal principal)
			throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public List<CkPaymentTxnLog> filterBy(EntityFilterRequest filterRequest)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		log.debug("filterBy");
		
		try {
			if(null==filterRequest)
				throw new ParameterException("param filterRequest null");
			
			CkPaymentTxnLog dto = this.whereDto(filterRequest);
			if(null==dto) {
				throw new ProcessingException("whereDto null");
			}
			
			filterRequest.setTotalRecords(super.countByAnd(dto));
			
			String selectClause = "from TCkPaymentTxnLog o ";
			String orderByClause = filterRequest.getOrderBy().toString();
			List<TCkPaymentTxnLog> entities = super.findEntitiesByAnd(dto, selectClause, orderByClause, 
					filterRequest.getDisplayLength(), filterRequest.getDisplayStart());
			List<CkPaymentTxnLog> dtos = entities.stream().map(x -> {
				try {
					return dtoFromEntity(x);
				} catch (ParameterException | ProcessingException e) {
					log.error("filterBy", e);
				}
				return null;
			}).collect(Collectors.toList());

			return dtos;
		} catch (ParameterException | ProcessingException e) {
			log.error("filterBy", e);
			throw e;
		} catch (Exception e) {
			log.error("filterBy", e);
			throw new ProcessingException(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	protected TCkPaymentTxnLog initEnity(TCkPaymentTxnLog entity) throws ParameterException, ProcessingException {
		if (entity != null) {
			Hibernate.initialize(entity.getTCkPaymentTxn());
			Hibernate.initialize(entity.getTCkPaymentTxn().getTCkMstPaymentType());
			Hibernate.initialize(entity.getTCkPaymentTxn().getTCkMstServiceType());
			Hibernate.initialize(entity.getTCkPaymentTxn().getTCoreAccnByPtxPayee());
			Hibernate.initialize(entity.getTCkPaymentTxn().getTCoreAccnByPtxPayer());
			Hibernate.initialize(entity.getTCkPaymentTxn().getTMstCurrency());
		}

		return entity;
	}

	@Override
	protected TCkPaymentTxnLog entityFromDTO(CkPaymentTxnLog dto) throws ParameterException, ProcessingException {
		log.debug("entityFromDTO");
		
		try {
			if(null==dto)
				throw new ParameterException("dto param null");
			
			TCkPaymentTxnLog entity = new TCkPaymentTxnLog();
			entity = dto.toEntity(entity);
			
			Optional<TCkPaymentTxn> opTCkPaymentTxn = Optional.ofNullable(entity.getTCkPaymentTxn());
			if(opTCkPaymentTxn.isPresent()) {
				entity.setTCkPaymentTxn(opTCkPaymentTxn.get());
				
				opTCkPaymentTxn.get().setTCkMstPaymentType(dto.getTCkPaymentTxn().getTCkMstPaymentType().toEntity(new TCkMstPaymentType()));
				opTCkPaymentTxn.get().setTCkMstServiceType(dto.getTCkPaymentTxn().getTCkMstServiceType().toEntity(new TCkMstServiceType()));
				opTCkPaymentTxn.get().setTCoreAccnByPtxPayee(dto.getTCkPaymentTxn().getTCoreAccnByPtxPayee().toEntity(new TCoreAccn()));
				opTCkPaymentTxn.get().setTCoreAccnByPtxPayer(dto.getTCkPaymentTxn().getTCoreAccnByPtxPayer().toEntity(new TCoreAccn()));
				opTCkPaymentTxn.get().setTMstCurrency(dto.getTCkPaymentTxn().getTMstCurrency().toEntity(new TMstCurrency()));
			}
			
			return entity;
		} catch(ParameterException e) {
			log.error("entityFromDto", e);
			throw e;
		} catch (Exception e) {
			log.error("entityFromDto", e);
			throw new ProcessingException(e);
		}
	}

	@Override
	protected CkPaymentTxnLog dtoFromEntity(TCkPaymentTxnLog entity) throws ParameterException, ProcessingException {
		log.debug("dtoFromEntity");
		
		try {
			if(null==entity)
				throw new ParameterException("param entity null");
			
			CkPaymentTxnLog dto = new CkPaymentTxnLog(entity);
			Optional<TCkPaymentTxn> opTCkPaymentTxn = Optional.ofNullable(entity.getTCkPaymentTxn());
			if(opTCkPaymentTxn.isPresent()) {
				dto.setTCkPaymentTxn(new CkPaymentTxn(opTCkPaymentTxn.get()));
				
				Optional<TCkMstPaymentType> opckMstPaymentType = Optional.ofNullable(opTCkPaymentTxn.get().getTCkMstPaymentType());
				if (opckMstPaymentType.isPresent())
					dto.getTCkPaymentTxn().setTCkMstPaymentType(new CkMstPaymentType(opckMstPaymentType.get()));

				Optional<TCkMstServiceType> opCkMstServiceType = Optional.ofNullable(opTCkPaymentTxn.get().getTCkMstServiceType());
				if (opckMstPaymentType.isPresent())
					dto.getTCkPaymentTxn().setTCkMstServiceType(new CkMstServiceType(opCkMstServiceType.get()));

				Optional<TCoreAccn> opAccnPayee = Optional.ofNullable(opTCkPaymentTxn.get().getTCoreAccnByPtxPayee());
				if (opAccnPayee.isPresent())
					dto.getTCkPaymentTxn().setTCoreAccnByPtxPayee(new CoreAccn(opAccnPayee.get()));

				Optional<TCoreAccn> opAccnPayer = Optional.ofNullable(opTCkPaymentTxn.get().getTCoreAccnByPtxPayer());
				if (opAccnPayer.isPresent())
					dto.getTCkPaymentTxn().setTCoreAccnByPtxPayer(new CoreAccn(opAccnPayer.get()));

				Optional<TMstCurrency> opCurrency = Optional.ofNullable(opTCkPaymentTxn.get().getTMstCurrency());
				if (opCurrency.isPresent())
					dto.getTCkPaymentTxn().setTMstCurrency(new MstCurrency(opCurrency.get()));
			}
			
			return dto;	
		} catch(ParameterException e) {
			log.error("entityFromDto", e);
			throw e;
		} catch (Exception e) {
			log.error("entityFromDto", e);
			throw new ProcessingException(e);
		}
	}

	@Override
	protected String entityKeyFromDTO(CkPaymentTxnLog dto) throws ParameterException, ProcessingException {
		log.debug("entityKeyFromDTO");

		try {
			if (null == dto)
				throw new ParameterException("dto param null");

			return dto.getPtxlId();
		} catch (ParameterException ex) {
			log.error("entityKeyFromDTO", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("entityKeyFromDTO", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected TCkPaymentTxnLog updateEntity(ACTION attriubte, TCkPaymentTxnLog entity, Principal principal, Date date)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected TCkPaymentTxnLog updateEntityStatus(TCkPaymentTxnLog entity, char status)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CkPaymentTxnLog preSaveUpdateDTO(TCkPaymentTxnLog storedEntity, CkPaymentTxnLog dto)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void preSaveValidation(CkPaymentTxnLog dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub

	}

	@Override
	protected ServiceStatus preUpdateValidation(CkPaymentTxnLog dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getWhereClause(CkPaymentTxnLog dto, boolean wherePrinted)
			throws ParameterException, ProcessingException {
		log.debug("getWhereClause");
		String EQUAL = " = :", CONTAIN = " like :";
		
		try {
			if(null==dto)
				throw new ParameterException("param dto null");
			
			StringBuffer searchStatement = new StringBuffer();
			if(!StringUtils.isEmpty(dto.getPtxlId())) {
				searchStatement.append(getOperator(wherePrinted) + "o.ptxlId" + CONTAIN 
						+ "ptxlId");
				wherePrinted = true;
			}
			
			Optional<CkPaymentTxn> opCkPaymentTxn = Optional.ofNullable(dto.getTCkPaymentTxn());
			if(opCkPaymentTxn.isPresent()) {
				if(!StringUtils.isEmpty(opCkPaymentTxn.get().getPtxId())) {
					searchStatement.append(getOperator(wherePrinted) + "o.TCkPaymentTxn.ptxId" + CONTAIN
							+ "ptxId");
					wherePrinted = true;
				}
			}
			
			if(!StringUtils.isEmpty(dto.getPtxlTxnState())) {
				searchStatement.append(getOperator(wherePrinted) + "o.ptxlTxnState" + EQUAL + "ptxlTxnState");
				wherePrinted = true;
			}
			
			if(!StringUtils.isEmpty(dto.getPtxlRemarks())) {
				searchStatement.append(getOperator(wherePrinted) + "o.ptxlRemarks" + CONTAIN + "ptxlRemarks");
				wherePrinted = true;
			}
			
			if(dto.getPtxlDtCreate()!= null) {
				searchStatement.append(getOperator(wherePrinted) + "DATE_FORMAT(o.ptxlDtCreate, '%d/%m/%Y %H:%i')" + 
						EQUAL + "ptxlDtCreate");
				wherePrinted = true;
			}
			
			if(dto.getPtxlDtLupd()!= null) {
				searchStatement.append(getOperator(wherePrinted) + "DATE_FORMAT(o.ptxlDtLupd, '%d/%m/%Y %H:%i')" + 
						EQUAL + "ptxlDtLupd");
				wherePrinted = true;
			}
			
			return searchStatement.toString();			
		} catch(ParameterException e){
			log.error("getWhereClause", e);
			throw e;
		} catch (Exception e) {
			log.error("getWhereClause", e);
			throw new ProcessingException(e);
		}
	}

	@Override
	protected HashMap<String, Object> getParameters(CkPaymentTxnLog dto)
			throws ParameterException, ProcessingException {
		
		try {
			if(null==dto)
				throw new ParameterException("param dto null");
			
			HashMap<String, Object> parameters = new HashMap<>();
			
			if(!StringUtils.isEmpty(dto.getPtxlId())) {
				parameters.put("ptxlId", "%" + dto.getPtxlId() + "%");
			}
			
			Optional<CkPaymentTxn> opCkPaymentTxn = Optional.ofNullable(dto.getTCkPaymentTxn());
			if(opCkPaymentTxn.isPresent()) {
				if(!StringUtils.isEmpty(opCkPaymentTxn.get().getPtxId()))
					parameters.put("ptxId", "%" + opCkPaymentTxn.get().getPtxId() + "%");
			}
			
			if(!StringUtils.isEmpty(dto.getPtxlTxnState())) {
				parameters.put("ptxlTxnState", dto.getPtxlTxnState());
			}
			
			if(!StringUtils.isEmpty(dto.getPtxlRemarks())) {
				parameters.put("ptxlRemarks", "%" + dto.getPtxlRemarks() + "%");
			}
			
			if(null!=dto.getPtxlDtCreate()) {
				parameters.put("ptxlDtCreate", sdf.format(dto.getPtxlDtCreate()));
			}
			
			if(null!=dto.getPtxlDtLupd()) {
				parameters.put("ptxlDtLupd", sdf.format(dto.getPtxlDtLupd()));
			}
			
			return parameters;
		} catch (ParameterException e) {
			log.error("getParameters", e);
			throw e;
		} catch (Exception e) {
			log.error("getParameters", e);
			throw new ProcessingException(e);
		}
	}

	@Override
	protected CkPaymentTxnLog whereDto(EntityFilterRequest filterRequest)
			throws ParameterException, ProcessingException {
		log.debug("whereDto");
		
		try {
			if(null==filterRequest)
				throw new ParameterException("param filterRequest null");
			
			CkPaymentTxnLog dto = new CkPaymentTxnLog();
			CkPaymentTxn ckPaymentTxn = new CkPaymentTxn();
			for(EntityWhere entityWhere : filterRequest.getWhereList()) {
				Optional<String> opValue = Optional.ofNullable(entityWhere.getValue());
				if(!opValue.isPresent())
					continue;
				
				if(entityWhere.getAttribute().equalsIgnoreCase("ptxlId"))
					dto.setPtxlId(opValue.get());
				if(entityWhere.getAttribute().equalsIgnoreCase("TCkPaymentTxn.ptxId"))
					ckPaymentTxn.setPtxId(opValue.get());
				if(entityWhere.getAttribute().equalsIgnoreCase("ptxlTxnState"))
					dto.setPtxlTxnState(opValue.get());
				if(entityWhere.getAttribute().equalsIgnoreCase("ptxlRemarks"))
					dto.setPtxlRemarks(opValue.get());
				if(entityWhere.getAttribute().equalsIgnoreCase("ptxlDtCreate"))
					dto.setPtxlDtCreate(sdf.parse(opValue.get()));
				if(entityWhere.getAttribute().equalsIgnoreCase("ptxlDtLupd"))
					dto.setPtxlDtLupd(sdf.parse(opValue.get()));
			}
			
			dto.setTCkPaymentTxn(ckPaymentTxn);
			return dto;
			
		} catch(ParameterException e) {
			log.error("whereDto", e);
			throw e;
		} catch (Exception e) {
			log.error("whereDto", e);
			throw new ProcessingException(e);
		}
	}

	@Override
	protected CoreMstLocale getCoreMstLocale(CkPaymentTxnLog dto)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CkPaymentTxnLog setCoreMstLocale(CoreMstLocale coreMstLocale, CkPaymentTxnLog dto)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	public TCkPaymentTxnLog createPaymentTxnLog(TCkPaymentTxn paymentTxn, String remarks, Principal principal)
			throws Exception {

		if (paymentTxn == null)
			throw new ParameterException("param paymentTxn null");

		Date now = new Date();
		TCkPaymentTxnLog txnLog = new TCkPaymentTxnLog(CkUtil.generateIdSynch(PREFIX_TXN_LOG));
		txnLog.setTCkPaymentTxn(paymentTxn);
		txnLog.setPtxlTxnState(paymentTxn.getPtxPaymentState());
		txnLog.setPtxlRemarks(remarks);
		txnLog.setPtxlDtCreate(now);
		txnLog.setPtxlUidCreate(principal == null ? Constant.ACCN_CREATE_SYS_USER : principal.getUserId());
		txnLog.setPtxlDtLupd(now);
		txnLog.setPtxlUidLupd(principal == null ? Constant.ACCN_CREATE_SYS_USER : principal.getUserId());
		dao.add(txnLog);
		return txnLog;
	}

}
