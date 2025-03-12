package com.guudint.clickargo.manageaccn.service;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.common.AbstractClickCargoEntityService;
import com.guudint.clickargo.common.ICkConstant;
import com.guudint.clickargo.common.RecordStatus;
import com.guudint.clickargo.common.dto.CkMstWorkflowType;
import com.guudint.clickargo.common.model.TCkMstWorkflowType;
import com.guudint.clickargo.common.service.ICkSession;
import com.guudint.clickargo.manageaccn.dao.CkMstAccnAttTypeDao;
import com.guudint.clickargo.manageaccn.dto.CkAccnAtt;
import com.guudint.clickargo.manageaccn.dto.CkMstAccnAttType;
import com.guudint.clickargo.manageaccn.dto.CkMstAccnAttTypeId;
import com.guudint.clickargo.manageaccn.model.TCkAccnAtt;
import com.guudint.clickargo.manageaccn.model.TCkMstAccnAttType;
import com.guudint.clickargo.manageaccn.model.TCkMstAccnAttTypeId;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.ccm.model.TCoreAccn;
import com.vcc.camelone.common.controller.entity.EntityFilterRequest;
import com.vcc.camelone.common.controller.entity.EntityWhere;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.exception.ValidationException;
import com.vcc.camelone.common.service.ServiceStatus;
import com.vcc.camelone.config.model.TCoreSysparam;
import com.vcc.camelone.locale.dto.CoreMstLocale;
import com.vcc.camelone.master.service.impl.AccountTypeService;
import com.vcc.camelone.util.bean.COBeansUtil;

public class CkAccnAttService extends AbstractClickCargoEntityService<TCkAccnAtt, String, CkAccnAtt> {

	// Static Attributes
	////////////////////
	private static Logger log = Logger.getLogger(AccountTypeService.class);
	private static String auditTag = "ACCOUNT ATTACHMENT";
	private static String tableName = "T_CK_ACCN_ATT";

    @Autowired
    protected ICkSession ckSession;
    
	@Autowired
	private GenericDao<TCoreAccn, String> coreAccnDao;
	
	@Autowired
	protected GenericDao<TCkAccnAtt, String> ckAccnAttDao;
	
	@Autowired
	protected CkMstAccnAttTypeDao ckMstAccnAttTypeDao;
	
	// Constructor
	//////////////
	public CkAccnAttService() {
		super("ckAccnAttDao", auditTag, TCkAccnAtt.class.getName(), tableName);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CkAccnAtt findById(String id) throws ParameterException, EntityNotFoundException, ProcessingException {
		log.debug("findById");

		try {
			if (StringUtils.isEmpty(id))
				throw new ParameterException("param id null or empty");

			TCkAccnAtt entity = dao.find(id);
			if (null == entity)
				throw new EntityNotFoundException("id: " + id);
			this.initEnity(entity);

			return this.dtoFromEntity(entity, true);
		} catch (ParameterException | EntityNotFoundException ex) {
			log.error("findById", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("findById", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CkAccnAtt deleteById(String id, Principal principal)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		log.debug("deleteById");

		Date now = Calendar.getInstance().getTime();
		try {
			if (StringUtils.isEmpty(id))
				throw new ParameterException("param id null or empty");
			if (null == principal)
				throw new ParameterException("param prinicipal null");

			TCkAccnAtt entity = dao.find(id);
			if (null == entity)
				throw new EntityNotFoundException("id: " + id);

			this.updateEntityStatus(entity, RecordStatus.INACTIVE.getCode());
			this.updateEntity(ACTION.MODIFY, entity, principal, now);

			CkAccnAtt dto = dtoFromEntity(entity);
			this.delete(dto, principal);
			return dto;
		} catch (ParameterException | EntityNotFoundException ex) {
			log.error("deleteById", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("deleteById", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public List<CkAccnAtt> filterBy(EntityFilterRequest filterRequest)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		log.debug("filterBy");

		try {
			if (null == filterRequest)
				throw new ParameterException("param filterRequest null");

			CkAccnAtt dto = this.whereDto(filterRequest);
			if (null == dto)
				throw new ProcessingException("whereDto null");

			filterRequest.setTotalRecords(super.countByAnd(dto));

			String selectClause = "from TCkAccnAtt o ";
			String orderByClause = filterRequest.getOrderBy().toString();
			List<TCkAccnAtt> entities = super.findEntitiesByAnd(dto, selectClause, orderByClause,
					filterRequest.getDisplayLength(), filterRequest.getDisplayStart());
			List<CkAccnAtt> dtos = entities.stream().map(x -> {
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
	protected TCkAccnAtt initEnity(TCkAccnAtt entity) throws ParameterException, ProcessingException {
		if (null != entity) {
			Hibernate.initialize(entity.getTCkMstAccnAttType());
			Hibernate.initialize(entity.getTCkMstAccnAttType().getId());
			Hibernate.initialize(entity.getTCoreAccn());
		}

		return entity;
	}

	@Override
	protected TCkAccnAtt entityFromDTO(CkAccnAtt dto) throws ParameterException, ProcessingException {
		log.debug("entityFromDTO");

		try {
			if (null == dto)
				throw new ParameterException("dto dto null");

			TCkAccnAtt entity = new TCkAccnAtt();
			entity = dto.toEntity(entity);

			Optional<CoreAccn> opCoreAccn = Optional.ofNullable(dto.getTCoreAccn());
			entity.setTCoreAccn(opCoreAccn.isPresent() ? opCoreAccn.get().toEntity(new TCoreAccn()) : null);

			Optional<CkMstAccnAttType> opMstAttType = Optional.ofNullable(dto.getTCkMstAccnAttType());
			entity.setTCkMstAccnAttType(opMstAttType.isPresent() ? opMstAttType.get().toEntity(new TCkMstAccnAttType()) : null);

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
	protected CkAccnAtt dtoFromEntity(TCkAccnAtt entity) throws ParameterException, ProcessingException {
		log.debug("dtoFromEntity");

		try {
			if (null == entity)
				throw new ParameterException("param entity null");

			CkAccnAtt dto = new CkAccnAtt(entity);
			
			Optional<TCoreAccn> opCoreAccn = Optional.ofNullable(entity.getTCoreAccn());
			dto.setTCoreAccn(opCoreAccn.isPresent() ? new CoreAccn(opCoreAccn.get()) : null);

			Optional<TCkMstAccnAttType> ckMstAccnAttType = Optional.ofNullable(entity.getTCkMstAccnAttType());
			dto.setTCkMstAccnAttType(ckMstAccnAttType.isPresent() ? new CkMstAccnAttType(ckMstAccnAttType.get()) : null);
			
			if (ckMstAccnAttType.isPresent()) {
				Optional<TCkMstAccnAttTypeId> ckMstAccnAttTypeId = Optional.ofNullable(entity.getTCkMstAccnAttType().getId());
				dto.getTCkMstAccnAttType().setId(ckMstAccnAttTypeId.isPresent() ? new CkMstAccnAttTypeId(ckMstAccnAttTypeId.get()) : null);
				
				Optional<TCkMstWorkflowType> ckMstWorkflowType = Optional.ofNullable(entity.getTCkMstAccnAttType().getTCkMstWorkflowType());
				dto.getTCkMstAccnAttType().setTCkMstWorkflowType(ckMstWorkflowType.isPresent() ? new CkMstWorkflowType(ckMstWorkflowType.get()) : null);
			}
			
			return dto;
		} catch (ParameterException ex) {
			log.error("dtoFromEntity", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("dtoFromEntity", ex);
			throw new ProcessingException(ex);
		}
	}

	protected CkAccnAtt dtoFromEntity(TCkAccnAtt entity, boolean withData) throws ParameterException, ProcessingException {
		log.debug("dtoFromEntity");

		try {
			if (null == entity)
				throw new ParameterException("param entity null");

			CkAccnAtt dto = new CkAccnAtt(entity);
			
			Optional<TCoreAccn> opCoreAccn = Optional.ofNullable(entity.getTCoreAccn());
			dto.setTCoreAccn(opCoreAccn.isPresent() ? new CoreAccn(opCoreAccn.get()) : null);

			Optional<TCkMstAccnAttType> ckMstAccnAttType = Optional.ofNullable(entity.getTCkMstAccnAttType());
			dto.setTCkMstAccnAttType(ckMstAccnAttType.isPresent() ? new CkMstAccnAttType(ckMstAccnAttType.get()) : null);
			
			if (ckMstAccnAttType.isPresent()) {
				Optional<TCkMstAccnAttTypeId> ckMstAccnAttTypeId = Optional.ofNullable(entity.getTCkMstAccnAttType().getId());
				dto.getTCkMstAccnAttType().setId(ckMstAccnAttTypeId.isPresent() ? new CkMstAccnAttTypeId(ckMstAccnAttTypeId.get()) : null);
				
				Optional<TCkMstWorkflowType> ckMstWorkflowType = Optional.ofNullable(entity.getTCkMstAccnAttType().getTCkMstWorkflowType());
				dto.getTCkMstAccnAttType().setTCkMstWorkflowType(ckMstWorkflowType.isPresent() ? new CkMstWorkflowType(ckMstWorkflowType.get()) : null);
			}
			
			if(withData) {
				if(!StringUtils.isBlank(dto.getAatLoc())){
					File file = new File(dto.getAatLoc());
					dto.setAatLocData(Files.readAllBytes(file.toPath()));
				}
			}
			
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
	protected String entityKeyFromDTO(CkAccnAtt dto) throws ParameterException, ProcessingException {
		log.debug("entityKeyFromDTO");

		try {
			if (null == dto)
				throw new ParameterException("dto param null");

			return dto.getAatId();
		} catch (ParameterException ex) {
			log.error("entityKeyFromDTO", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("entityKeyFromDTO", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected TCkAccnAtt updateEntity(ACTION attribute, TCkAccnAtt entity, Principal principal, Date date)
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
			switch (attribute) {
			case CREATE:
				entity.setAtUidCreate(opUserId.isPresent() ? opUserId.get() : "SYS");
				entity.setAtDtCreate(date);
				entity.setAtDtLupd(date);
				entity.setAtUidLupd(opUserId.isPresent() ? opUserId.get() : "SYS");
				break;

			case MODIFY:
				entity.setAtDtLupd(date);
				entity.setAtUidLupd(opUserId.isPresent() ? opUserId.get() : "SYS");
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
	protected TCkAccnAtt updateEntityStatus(TCkAccnAtt entity, char status)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		log.debug("updateEntityStatus");

		try {
			if (null == entity)
				throw new ParameterException("entity param null");

			entity.setAtStatus(status);
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
	protected CkAccnAtt preSaveUpdateDTO(TCkAccnAtt storedEntity, CkAccnAtt dto)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		log.debug("preSaveUpdateDTO");

		try {
			if (null == storedEntity)
				throw new ParameterException("param storedEntity null");
			if (null == dto)
				throw new ParameterException("param dto null");

			dto.setAtUidCreate(storedEntity.getAtUidCreate());
			dto.setAtDtCreate(storedEntity.getAtDtCreate());
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
	protected void preSaveValidation(CkAccnAtt dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub

	}

	@Override
	protected ServiceStatus preUpdateValidation(CkAccnAtt dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getWhereClause(CkAccnAtt dto, boolean wherePrinted)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		log.debug("getWhereClause");

		try {
			if (null == dto)
				throw new ParameterException("param dto null");

			StringBuffer searchStatement = new StringBuffer();
			
			if (dto.getTCoreAccn() != null && !StringUtils.isEmpty(dto.getTCoreAccn().getAccnId())) {
				searchStatement.append(getOperator(wherePrinted) + "o.TCoreAccn.accnId LIKE :accnId");
				wherePrinted = true;
			}
	        
			if (StringUtils.isNotBlank(dto.getAatId())) {
				searchStatement.append(getOperator(wherePrinted) + "o.aatId LIKE :aatId");
				wherePrinted = true;
			}

			if (StringUtils.isNotBlank(dto.getAatName())) {
				searchStatement.append(getOperator(wherePrinted) + "o.aatName LIKE :aatName");
				wherePrinted = true;
			}

			if (StringUtils.isNotBlank(dto.getAatNo())) {
				searchStatement.append(getOperator(wherePrinted) + "o.aatNo LIKE :aatNo");
				wherePrinted = true;
			}
			
			if (StringUtils.isNotBlank(dto.getAtUidLupd())) {
				searchStatement.append(getOperator(wherePrinted) + "o.atUidLupd LIKE :atUidLupd");
				wherePrinted = true;
			}
			
			if (null != dto.getAtDtValidility()) {
				searchStatement.append(getOperator(wherePrinted) + "DATE_FORMAT(o.atDtValidility,'%d/%m/%Y') = :atDtValidility");
				wherePrinted = true;
			}
			
			if (null != dto.getAtDtLupd()) {
				searchStatement.append(getOperator(wherePrinted) + "DATE_FORMAT(o.atDtLupd,'%d/%m/%Y') = :atDtLupd");
				wherePrinted = true;
			}

			Optional<CkMstAccnAttType> opCkMstAccnAttType = Optional.ofNullable(dto.getTCkMstAccnAttType());
			if (opCkMstAccnAttType.isPresent() && StringUtils.isNotBlank(opCkMstAccnAttType.get().getAtName())) {
				searchStatement.append(getOperator(wherePrinted) + "o.TCkMstAccnAttType.atName LIKE :atypeName");
				wherePrinted = true;
			}
			
//			Optional<CkMstAccnAttType> opCkMstAccnAttTypeId = Optional.ofNullable(dto.getTCkMstAccnAttType());
//			if (opCkMstAccnAttTypeId.isPresent() && null != opCkMstAccnAttTypeId.get().getId()) {
//				searchStatement.append(getOperator(wherePrinted) + "o.TCkMstAccnAttType.id = :atId");
//				wherePrinted = true;
//			}

			if (dto.getAtStatus() != null && Character.isAlphabetic(dto.getAtStatus())) {
				searchStatement.append(getOperator(wherePrinted) + "o.atStatus = :atStatus");
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
	protected HashMap<String, Object> getParameters(CkAccnAtt dto)
			throws ParameterException, ProcessingException {
		log.debug("getParameters");

		try {
			if (null == dto)
				throw new ParameterException("param dto null");
			
	        Principal principal = ckSession.getPrincipal();
	        if (null == principal)
	            throw new ParameterException("param principal null");

			SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
			HashMap<String, Object> parameters = new HashMap<String, Object>();

			if (dto.getTCoreAccn() != null && !StringUtils.isEmpty(dto.getTCoreAccn().getAccnId()))
				parameters.put("accnId", "%" + dto.getTCoreAccn().getAccnId() + "%");
			
			if (StringUtils.isNotBlank(dto.getAatId()))
				parameters.put("aatId", "%" + dto.getAatId() + "%");

			if (StringUtils.isNotBlank(dto.getAatName()))
				parameters.put("aatName", "%" + dto.getAatName() + "%");
			
			if (StringUtils.isNotBlank(dto.getAatNo()))
				parameters.put("aatNo", "%" + dto.getAatNo() + "%");

			if (null != dto.getAtDtValidility()) {
				parameters.put("atDtValidility", sdfDate.format(dto.getAtDtValidility()));
			}

			if (StringUtils.isNotBlank(dto.getAtUidLupd())) {
				parameters.put("atUidLupd", "%" + dto.getAtUidLupd() + "%");
			}
			
			if (null != dto.getAtDtLupd()) {
				parameters.put("atDtLupd", sdfDate.format(dto.getAtDtLupd()));
			}

			Optional<CkMstAccnAttType> opCkMstAccnAttType = Optional.ofNullable(dto.getTCkMstAccnAttType());
			if (opCkMstAccnAttType.isPresent() && StringUtils.isNotBlank(dto.getTCkMstAccnAttType().getAtName())) {
				parameters.put("atypeName", "%" + opCkMstAccnAttType.get().getAtName() + "%");
			}
			
//			if (opCkMstAccnAttType.isPresent() && null != dto.getTCkMstAccnAttType().getId()) {
//				parameters.put("atId", opCkMstAccnAttType.get().getId());
//			}

			if (dto.getAtStatus() != null && Character.isAlphabetic(dto.getAtStatus()))
				parameters.put("atStatus", dto.getAtStatus());

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
	protected CkAccnAtt whereDto(EntityFilterRequest filterRequest)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		log.debug("whereDto");

		try {
			if (null == filterRequest)
				throw new ParameterException("param filterRequest null");

			CkAccnAtt dto = new CkAccnAtt();
			CoreAccn coreAccn = new CoreAccn();
			CkMstAccnAttType ckMstAccnAttType = new CkMstAccnAttType();
			CkMstAccnAttTypeId atId = new CkMstAccnAttTypeId();
			
			SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
			
			for (EntityWhere entityWhere : filterRequest.getWhereList()) {
				Optional<String> opValue = Optional.ofNullable(entityWhere.getValue());
				if (!opValue.isPresent())
					continue;

				if (entityWhere.getAttribute().equalsIgnoreCase("TCoreAccn.accnId"))
					coreAccn.setAccnId(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("aatId"))
					dto.setAatId(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("aatName"))
					dto.setAatName(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("atDtValidity"))
					dto.setAtDtValidility(sdfDate.parse(opValue.get()));
				if (entityWhere.getAttribute().equalsIgnoreCase("atDtLupd"))
					dto.setAtDtLupd(sdfDate.parse(opValue.get()));
				if (entityWhere.getAttribute().equalsIgnoreCase("atUidLupd"))
					dto.setAtUidLupd(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("TCkMstAccnAttType.atName"))
					ckMstAccnAttType.setAtName(opValue.get());
//				if (entityWhere.getAttribute().equalsIgnoreCase("TCkMstAccnAttType.id"))
//					atId.setAtId(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("atStatus"))
					dto.setAtStatus(opValue.get().charAt(0));
			}

			ckMstAccnAttType.setId(atId);
			dto.setTCoreAccn(coreAccn);
			dto.setTCkMstAccnAttType(ckMstAccnAttType);

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
	protected CoreMstLocale getCoreMstLocale(CkAccnAtt dto)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CkAccnAtt setCoreMstLocale(CoreMstLocale coreMstLocale, CkAccnAtt dto)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CkAccnAtt newObj(Principal principal)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return new CkAccnAtt();
	}

	@Override
	protected void initBusinessValidator() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Logger getLogger() {
		return log;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CkAccnAtt createAttachment(CkAccnAtt ckAccnAtt, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception {
		log.debug("createAttachment");

		if (null == ckAccnAtt)
			throw new ParameterException("ckAccnAtt jobAttach null");
		if (null == principal)
			throw new ParameterException("principal null");

		Optional<String> opAccnId = Optional.ofNullable(ckAccnAtt.getTCoreAccn().getAccnId());
		if (!opAccnId.isPresent())
			throw new ProcessingException("opAccnId null or empty");

		Optional<CkMstAccnAttTypeId> opAttTypeId = Optional.ofNullable(ckAccnAtt.getTCkMstAccnAttType().getId());
		if (!opAttTypeId.isPresent()) {
			throw new ProcessingException("opAttTypeId null or empty");
		}
			
		if (opAttTypeId.isPresent()) {
			Optional<String> opAtId = Optional.ofNullable(ckAccnAtt.getTCkMstAccnAttType().getId().getAtId());
			if (!opAtId.isPresent())
				throw new ProcessingException("opAtId null or empty");
			
			Optional<String> opAtWorkflow = Optional.ofNullable(ckAccnAtt.getTCkMstAccnAttType().getId().getAtWorkflow());
			if (!opAtWorkflow.isPresent())
				throw new ProcessingException("opAtWorkflow null or empty");
		}

		TCoreAccn coreAccn = coreAccnDao.find(opAccnId.get());
		if (null == coreAccn)
			throw new ProcessingException("coreAccn not found: " + opAccnId.get());

		// Validate duplicate account attachment for nw
		if (null != this.findAttByAccnAndAttType(coreAccn.getAccnId(), ckAccnAtt.getTCkMstAccnAttType().getId().getAtWorkflow())) {
			ckAccnAtt.setDuplicate(true);
			return ckAccnAtt;
		}
		
		TCkAccnAtt _ckAccnAtt = new TCkAccnAtt();
		BeanUtils.copyProperties(ckAccnAtt, _ckAccnAtt, COBeansUtil.getNullPropertyNames(ckAccnAtt));
		_ckAccnAtt.setTCoreAccn(coreAccn);
		
		TCkMstAccnAttType ckMstAccnAttType = ckMstAccnAttTypeDao.findById(ckAccnAtt.getTCkMstAccnAttType().getId());
		_ckAccnAtt.setTCkMstAccnAttType(ckMstAccnAttType);

		String fileLocation = this.saveAccnAttachment(ckAccnAtt.getAatId(), ckAccnAtt.getAatName(), ckAccnAtt.getAatLocData());
		if (StringUtils.isNotBlank(fileLocation))
			_ckAccnAtt.setAatLoc(fileLocation);
		ckAccnAttDao.saveOrUpdate(_ckAccnAtt);
		_ckAccnAtt.setAatLoc(fileLocation);

		return ckAccnAtt;

	};
	
	/**
	 * 
	 * @param aatId
	 * @param filename
	 * @param data
	 * @return
	 * @throws Exception
	 */
	protected String saveAccnAttachment(String aatId, String filename, byte[] data) throws Exception {
		if (StringUtils.isBlank(filename))
			throw new ParameterException("param filename null or empty");

		if (data == null)
			throw new ParameterException("param data null or empty");

		String basePath = getSysParam(ICkConstant.KEY_CLICDO_ATTCH_BASE_LOCATION);
		if (StringUtils.isBlank(basePath))
			throw new ProcessingException("basePath is not configured");
		
		Path dir = Paths.get(basePath.concat(aatId));
		if (!Files.exists(dir)) {
			Files.createDirectories(dir);
		}

		File jobDir = new File(basePath.concat(aatId));
		File file = new File(jobDir.getAbsolutePath(), filename);
		FileOutputStream output = new FileOutputStream(file);
		output.write(data);
		output.close();
		return file.getAbsolutePath();
	}
	
	protected String getSysParam(String key) throws Exception {
		if (StringUtils.isBlank(key))
			throw new ParameterException("param key null or empty");

		TCoreSysparam sysParam = coreSysparamDao.find(key);
		if (sysParam != null) {
			return sysParam.getSysVal();
		}

		return null;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int deleteAttachment(String id, Principal principal) throws ParameterException, ProcessingException {
        log.debug("deleteAttachment accnAtt");
        if (null == id) throw new ParameterException("param id is null or empty");
        try {
            TCkAccnAtt tckAccnAtt = ckAccnAttDao.find(id);
            if (null == tckAccnAtt) throw new ProcessingException("tckAccnAtt not found with id : " + id);
            
    		Map<String, Object> params = new HashMap<>();
    		params.put("aatId", id);
    		params.put("atStatus", RecordStatus.ACTIVE.getCode());
    		  		
    		// Delete file from the server [START]
      		String serverFile = tckAccnAtt.getAatLoc();
      		if (null != serverFile && !StringUtils.isEmpty(serverFile)) {
      	      File file = new File(serverFile);
      	      File directory = new File(file.getParent());
      	      this.deleteFolder(directory);
      	      log.debug(serverFile + " successfully deleted from the server!");
      		}
      		// Delete file from the server [END]
    		
    		int result = ckAccnAttDao.executeUpdate("DELETE FROM TCkAccnAtt o WHERE o.aatId = :aatId AND o.atStatus = :atStatus", params);
    		
    		return result;
        } catch (Exception ex) {
            log.error("deleteAttachment accnAtt", ex);
            throw new ProcessingException(ex);
        }
    }
	
	/**
	 * 
	 * @param directory
	 */
	@Transactional
	private void deleteFolder(File directory) {
		for (File subFile : directory.listFiles()) {
			if (subFile.isDirectory()) {
				deleteFolder(subFile);
			} else {
				subFile.delete();
			}
		}
		directory.delete();
	}

    public List<TCkAccnAtt> findAccnAttByAccnId(String accnId) throws Exception {
        DetachedCriteria criteria = DetachedCriteria.forClass(TCkAccnAtt.class);
        criteria.add(Restrictions.eq("TCoreAccn.accnId", accnId));
        return ckAccnAttDao.getByCriteria(criteria);
    }
    
    @Transactional
	public CkAccnAtt findAttByAccnAndAttType(String accnId, String wktId) {
    	CkAccnAtt ckAccnAtt = null;
		try {
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("accnId", accnId);
			parameters.put("wktId", wktId);

			String hql = "FROM TCkAccnAtt o WHERE o.TCoreAccn.accnId = :accnId AND o.TCkMstAccnAttType.TCkMstWorkflowType.wktId = :wktId";
			List<TCkAccnAtt> attach = dao.getByQuery(hql, parameters);
			if (null != attach && attach.size() > 0)
				ckAccnAtt = new CkAccnAtt(attach.get(0));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ckAccnAtt;
	}
    
}
