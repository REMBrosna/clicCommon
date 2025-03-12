package com.guudint.clickargo.manageaccn.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.common.AbstractClickCargoEntityService;
import com.guudint.clickargo.common.dto.CkMstWorkflowType;
import com.guudint.clickargo.common.model.TCkMstWorkflowType;
import com.guudint.clickargo.common.service.ICkSession;
import com.guudint.clickargo.manageaccn.dto.CkMstAccnAttType;
import com.guudint.clickargo.manageaccn.dto.CkMstAccnAttTypeId;
import com.guudint.clickargo.manageaccn.model.TCkMstAccnAttType;
import com.guudint.clickargo.manageaccn.model.TCkMstAccnAttTypeId;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.common.controller.entity.EntityFilterRequest;
import com.vcc.camelone.common.controller.entity.EntityWhere;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.service.ServiceStatus;
import com.vcc.camelone.locale.dto.CoreMstLocale;
import com.vcc.camelone.master.service.impl.AccountTypeService;

public class CkMstAccnAttTypeService extends AbstractClickCargoEntityService<TCkMstAccnAttType, TCkMstAccnAttTypeId, CkMstAccnAttType> {

	// Static Attributes
	////////////////////
	private static Logger log = Logger.getLogger(AccountTypeService.class);
	private static String auditTag = "ACCOUNT ATT TYPE";
	private static String tableName = "T_CK_MST_ACCN_ATT_TYPE";

    @Autowired
    protected ICkSession ckSession;
	
	// Constructor
	//////////////
	public CkMstAccnAttTypeService() {
		super("ckMstAccnAttTypeDao", auditTag, TCkMstAccnAttType.class.getName(), tableName);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CkMstAccnAttType findById(String id) throws ParameterException, EntityNotFoundException, ProcessingException {
		log.debug("findById");
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CkMstAccnAttType deleteById(String id, Principal principal)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		log.debug("deleteById");
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public List<CkMstAccnAttType> filterBy(EntityFilterRequest filterRequest)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		log.debug("filterBy");

		try {
			if (null == filterRequest)
				throw new ParameterException("param filterRequest null");

			CkMstAccnAttType dto = this.whereDto(filterRequest);
			if (null == dto)
				throw new ProcessingException("whereDto null");

			filterRequest.setTotalRecords(super.countByAnd(dto));

			String selectClause = "from TCkMstAccnAttType o ";
			String orderByClause = filterRequest.getOrderBy().toString();
			List<TCkMstAccnAttType> entities = super.findEntitiesByAnd(dto, selectClause, orderByClause,
					filterRequest.getDisplayLength(), filterRequest.getDisplayStart());
			List<CkMstAccnAttType> dtos = entities.stream().map(x -> {
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
	protected TCkMstAccnAttType initEnity(TCkMstAccnAttType entity) throws ParameterException, ProcessingException {
		if (null != entity) {
			Hibernate.initialize(entity.getId());
			Hibernate.initialize(entity.getTCkMstWorkflowType());
		}

		return entity;
	}

	@Override
	protected TCkMstAccnAttType entityFromDTO(CkMstAccnAttType dto) throws ParameterException, ProcessingException {
		log.debug("entityFromDTO");

		try {
			if (null == dto)
				throw new ParameterException("dto dto null");

			TCkMstAccnAttType entity = new TCkMstAccnAttType();
			entity = dto.toEntity(entity);

			Optional<CkMstWorkflowType> opCkMstWorkflowType = Optional.ofNullable(dto.getTCkMstWorkflowType());
			entity.setTCkMstWorkflowType(opCkMstWorkflowType.isPresent() ? opCkMstWorkflowType.get().toEntity(new TCkMstWorkflowType()) : null);

			Optional<CkMstAccnAttTypeId> opCkMstAccnAttTypeId = Optional.ofNullable(dto.getId());
			entity.setId(opCkMstAccnAttTypeId.isPresent() ? opCkMstAccnAttTypeId.get().toEntity(new TCkMstAccnAttTypeId()) : null);

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
	protected CkMstAccnAttType dtoFromEntity(TCkMstAccnAttType entity) throws ParameterException, ProcessingException {
		log.debug("dtoFromEntity");

		try {
			if (null == entity)
				throw new ParameterException("param entity null");

			CkMstAccnAttType dto = new CkMstAccnAttType(entity);
			
			Optional<TCkMstWorkflowType> opCkMstWorkflowType = Optional.ofNullable(entity.getTCkMstWorkflowType());
			dto.setTCkMstWorkflowType(opCkMstWorkflowType.isPresent() ? new CkMstWorkflowType(opCkMstWorkflowType.get()) : null);

			Optional<TCkMstAccnAttTypeId> opCkMstAccnAttTypeId = Optional.ofNullable(entity.getId());
			dto.setId(opCkMstAccnAttTypeId.isPresent() ? new CkMstAccnAttTypeId(opCkMstAccnAttTypeId.get()) : null);

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
	protected TCkMstAccnAttTypeId entityKeyFromDTO(CkMstAccnAttType dto) throws ParameterException, ProcessingException {
		log.debug("entityKeyFromDTO");
		return null;
	}

	@Override
	protected TCkMstAccnAttType updateEntity(ACTION attribute, TCkMstAccnAttType entity, Principal principal, Date date)
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
	protected TCkMstAccnAttType updateEntityStatus(TCkMstAccnAttType entity, char status)
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
	protected CkMstAccnAttType preSaveUpdateDTO(TCkMstAccnAttType storedEntity, CkMstAccnAttType dto)
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
	protected void preSaveValidation(CkMstAccnAttType dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub

	}

	@Override
	protected ServiceStatus preUpdateValidation(CkMstAccnAttType dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getWhereClause(CkMstAccnAttType dto, boolean wherePrinted)
			throws ParameterException, ProcessingException {
		log.debug("getWhereClause");

		try {
			if (null == dto)
				throw new ParameterException("param dto null");
			
			Optional<CkMstAccnAttTypeId> opId = Optional.ofNullable(dto.getId());
			StringBuffer searchStatement = new StringBuffer();
			
    		if (opId.isPresent() && !StringUtils.isEmpty(opId.get().getAtId())) {
				searchStatement.append(getOperator(wherePrinted) + "o.id.atId LIKE :atId");
				wherePrinted = true;
			}
			
            if (opId.isPresent() && !StringUtils.isEmpty(opId.get().getAtWorkflow())) {
				searchStatement.append(getOperator(wherePrinted) + "o.id.atWorkflow LIKE :atWorkflow");
				wherePrinted = true;
			}
			
			if (Objects.nonNull(dto.getAtStatus())) {
				searchStatement.append(getOperator(wherePrinted) + "o.atStatus =:atStatus");
				wherePrinted = true;
			}
			
			if (Objects.nonNull(dto.getAtMandatory())) {
				searchStatement.append(getOperator(wherePrinted) + "o.atMandatory =:atMandatory");
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
	protected HashMap<String, Object> getParameters(CkMstAccnAttType dto)
			throws ParameterException, ProcessingException {
		log.debug("getParameters");

		try {
			if (null == dto)
				throw new ParameterException("param dto null");
			
			Optional<CkMstAccnAttTypeId> opCkMstAccnAttType = Optional.ofNullable(dto.getId());
			
	        Principal principal = ckSession.getPrincipal();
	        if (null == principal)
	            throw new ParameterException("param principal null");

			HashMap<String, Object> parameters = new HashMap<String, Object>();

			if (opCkMstAccnAttType.isPresent() && !StringUtils.isEmpty(opCkMstAccnAttType.get().getAtId()))
				parameters.put("atId", "%" + opCkMstAccnAttType.get().getAtId() + "%");
			if (opCkMstAccnAttType.isPresent() && !StringUtils.isEmpty(opCkMstAccnAttType.get().getAtWorkflow()))
				parameters.put("atWorkflow", "%" + opCkMstAccnAttType.get().getAtWorkflow() + "%");
			if (Objects.nonNull(dto.getAtStatus()))
				parameters.put("atStatus", dto.getAtStatus());
			if (Objects.nonNull(dto.getAtMandatory()))
				parameters.put("atMandatory", dto.getAtMandatory());
			
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
	protected CkMstAccnAttType whereDto(EntityFilterRequest filterRequest)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		log.debug("whereDto");

		try {
			if (null == filterRequest)
				throw new ParameterException("param filterRequest null");

			CkMstAccnAttType dto = new CkMstAccnAttType();
			CkMstAccnAttTypeId ckMstAccnAttTypeId = new CkMstAccnAttTypeId();
			
			for (EntityWhere entityWhere : filterRequest.getWhereList()) {
				Optional<String> opValue = Optional.ofNullable(entityWhere.getValue());
				if (!opValue.isPresent())
					continue;
				
				if (entityWhere.getAttribute().equalsIgnoreCase("id.atId"))
					ckMstAccnAttTypeId.setAtId(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("id.atWorkflow"))
					ckMstAccnAttTypeId.setAtWorkflow(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("atStatus"))
					dto.setAtStatus(opValue.get().charAt(0));
				if (entityWhere.getAttribute().equalsIgnoreCase("atMandatory"))
					dto.setAtMandatory(opValue.get().charAt(0));
			}

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
	protected CoreMstLocale getCoreMstLocale(CkMstAccnAttType dto)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CkMstAccnAttType setCoreMstLocale(CoreMstLocale coreMstLocale, CkMstAccnAttType dto)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CkMstAccnAttType newObj(Principal principal)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return new CkMstAccnAttType();
	}

	@Override
	protected void initBusinessValidator() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Logger getLogger() {
		return log;
	}
	    
}
