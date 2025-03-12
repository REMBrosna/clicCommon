package com.guudint.clickargo.credit.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guudint.clickargo.common.model.ValidationError;
import com.guudint.clickargo.common.service.ICkSession;
import com.guudint.clickargo.credit.dao.CkCreditDao;
import com.guudint.clickargo.credit.dao.CkCreditSummaryDao;
import com.guudint.clickargo.credit.dto.CkCredit;
import com.guudint.clickargo.credit.dto.CkCreditSummary;
import com.guudint.clickargo.credit.model.TCkCredit;
import com.guudint.clickargo.credit.model.TCkCreditSummary;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.common.controller.entity.EntityFilterRequest;
import com.vcc.camelone.common.controller.entity.EntityOrderBy;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.exception.ValidationException;
import com.vcc.camelone.common.service.entity.AbstractEntityService.ACTION;

import io.jsonwebtoken.lang.Collections;

public abstract class AbstractCreditService implements ICreditService {

    private static Logger log = Logger.getLogger(AbstractCreditService.class);

    @Autowired
    protected CkCreditDao dao;

    @Autowired
    protected CkCreditSummaryDao sDao;

    @Autowired
    protected ICreditValidation cValidator;

    @Autowired
    protected ICkSession ckSession;

    protected abstract CkCredit _createCredit(CkCredit dto, Principal principal) throws ParameterException, ValidationException, ProcessingException, Exception;
    
    protected abstract CkCredit _verifyCredit(CkCredit dto, String id, Principal principal) throws ParameterException, ValidationException, ProcessingException, Exception;
    
    protected abstract CkCredit _approveCredit(CkCredit dto, String id, Principal principal) throws ParameterException, ValidationException, ProcessingException, Exception;
    
    protected abstract CkCredit _rejectCredit(CkCredit dto, String id, Principal principal) throws ParameterException, ValidationException, ProcessingException, Exception;
    
    protected abstract CkCredit _suspendedCredit(CkCredit dto, String id, Principal principal) throws ParameterException, ValidationException, ProcessingException, Exception;
    
    protected abstract CkCredit _unsuspendCredit(CkCredit dto, String id, Principal principal) throws ParameterException, ValidationException, ProcessingException, Exception;
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    protected abstract TCkCredit initEnity(TCkCredit entity) throws ParameterException, ProcessingException, Exception;

    protected abstract TCkCredit entityFromDTO(CkCredit dto) throws ParameterException, ProcessingException, Exception;
  
    protected abstract CkCredit dtoFromEntity(TCkCredit entity) throws ParameterException, ProcessingException, Exception;

    protected abstract String entityKeyFromDTO(CkCredit dto) throws ParameterException, ProcessingException, Exception;

    protected abstract CkCredit preSaveUpdateDTO(TCkCredit entity, CkCredit dto) throws ParameterException, ProcessingException, Exception;
    
    protected abstract TCkCredit updateEntity(ACTION action, TCkCredit entity, Principal principal, Date date) throws ParameterException, ProcessingException, Exception;

    protected abstract String formatOrderBy(String attributes) throws ParameterException, ProcessingException, Exception;
    
    protected abstract String getWhereClause(CkCredit dto, boolean wherePrinted) throws ParameterException, ProcessingException, Exception;

    protected abstract HashMap<String, Object> getParameters(CkCredit dto) throws ParameterException, ProcessingException, Exception;

    protected abstract CkCredit whereDto(EntityFilterRequest paramEntityFilterRequest) throws ParameterException, ProcessingException, Exception;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public CkCredit createCredit(CkCredit dto, Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException, Exception {
        log.debug("createCredit");
        try {
            if (null == dto)
                throw new ParameterException("param dto null");

            if (null == principal)
                throw new ParameterException("param principal null");

            if (null == dto.getTCkMstServiceType())
                throw new ParameterException("param tckMstServiceType null");

            if (null == dto.getTCoreAccn())
                throw new ParameterException("param tcoreAccn null");

            if (null == dto.getTMstCurrency())
                throw new ParameterException("param tmstCurrency null");

            if (null == dto.getCrAmt())
                throw new ParameterException("param crAmt null");

            if (null == dto.getCrDtStart())
                throw new ParameterException("param crDtStart null");

            if (null == dto.getCrDtEnd())
                throw new ParameterException("param crDtEnd null");

            List<ValidationError> validationErrors = cValidator.validationCreateCredit(dto, principal);
            if (null != validationErrors && !validationErrors.isEmpty())
                throw new ValidationException(validationErrorMap(validationErrors));

            CkCredit _dto = _createCredit(dto, principal);
            return _dto;
        } catch (ParameterException|EntityNotFoundException|ValidationException e) {
            log.error("createCredit", (Throwable)e);
            throw e;
        } catch (Exception e) {
            log.error("createCredit", e);
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public CkCredit verifyCredit(CkCredit dto, String id, Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException, Exception {
        log.debug("verifyCredit");
        try {
            if (null == dto)
                throw new ParameterException("param dto null");

            if (null == principal)
                throw new ParameterException("param principal null");

            if (StringUtils.isEmpty(id))
                throw new ParameterException("param id null");

            if (null == dto.getTCkMstServiceType())
                throw new ParameterException("param tckMstServiceType null");

            if (null == dto.getTCoreAccn())
                throw new ParameterException("param tcoreAccn null");

            if (null == dto.getTMstCurrency())
                throw new ParameterException("param tmstCurrency null");

            List<ValidationError> validationErrors = cValidator.validationVerifyCredit(dto, principal);
            if (null != validationErrors && !validationErrors.isEmpty())
                throw new ValidationException(validationErrorMap(validationErrors));

            CkCredit _dto = _verifyCredit(dto, id, principal);
            return _dto;
        } catch (ParameterException|EntityNotFoundException|ValidationException e) {
            log.error("verifyCredit", (Throwable)e);
            throw e;
        } catch (Exception e) {
            log.error("verifyCredit", e);
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public CkCredit approveCredit(CkCredit dto, String id, Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException, Exception {
        log.debug("approveCredit");
        try {
            if (null == dto)
                throw new ParameterException("param dto null");

            if (null == principal)
                throw new ParameterException("param principal null");

            if (StringUtils.isEmpty(id))
                throw new ParameterException("param id null");

            if (null == dto.getTCkMstServiceType())
                throw new ParameterException("param tckMstServiceType null");

            if (null == dto.getTCoreAccn())
                throw new ParameterException("param tcoreAccn null");

            if (null == dto.getTMstCurrency())
                throw new ParameterException("param tmstCurrency null");

            List<ValidationError> validationErrors = cValidator.validationApproveCredit(dto, principal);
            if (null != validationErrors && !validationErrors.isEmpty())
                throw new ValidationException(validationErrorMap(validationErrors));

            CkCredit _dto = _approveCredit(dto, id, principal);
            return _dto;
        } catch (ParameterException|EntityNotFoundException|ValidationException e) {
            log.error("approveCredit", (Throwable)e);
            throw e;
        } catch (Exception e) {
            log.error("approveCredit", e);
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public CkCredit rejectCredit(CkCredit dto, String id, Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException, Exception {
        log.debug("rejectCredit");
        try {
            if (null == dto)
                throw new ParameterException("param dto null");

            if (null == principal)
                throw new ParameterException("param principal null");

            if (StringUtils.isEmpty(id))
                throw new ParameterException("param id null");

            if (null == dto.getTCkMstServiceType())
                throw new ParameterException("param tckMstServiceType null");

            if (null == dto.getTCoreAccn())
                throw new ParameterException("param tcoreAccn null");

            if (null == dto.getTMstCurrency())
                throw new ParameterException("param tmstCurrency null");

            List<ValidationError> validationErrors = cValidator.validationRejectCredit(dto, principal);
            if (null != validationErrors && !validationErrors.isEmpty())
                throw new ValidationException(validationErrorMap(validationErrors));

            CkCredit _dto = _rejectCredit(dto, id, principal);
            return _dto;
        } catch (ParameterException|EntityNotFoundException|ValidationException e) {
            log.error("rejectCredit", (Throwable)e);
            throw e;
        } catch (Exception e) {
            log.error("rejectCredit", e);
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public CkCredit suspendCredit(CkCredit dto, String id, Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException, Exception {
        log.debug("suspendCredit");
        try {
            if (null == dto)
                throw new ParameterException("param dto null");

            if (null == principal)
                throw new ParameterException("param principal null");

            if (StringUtils.isEmpty(id))
                throw new ParameterException("param id null");

            if (null == dto.getTCkMstServiceType())
                throw new ParameterException("param tckMstServiceType null");

            if (null == dto.getTCoreAccn())
                throw new ParameterException("param tcoreAccn null");

            if (null == dto.getTMstCurrency())
                throw new ParameterException("param tmstCurrency null");

            List<ValidationError> validationErrors = cValidator.validationSuspendCredit(dto, principal);
            if (null != validationErrors && !validationErrors.isEmpty())
                throw new ValidationException(validationErrorMap(validationErrors));

            CkCredit _dto = _suspendedCredit(dto, id, principal);
            return _dto;
        } catch (ParameterException|EntityNotFoundException|ValidationException e) {
            log.error("suspendCredit", (Throwable)e);
            throw e;
        } catch (Exception e) {
            log.error("suspendCredit", e);
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public CkCredit unsuspendCredit(CkCredit dto, String id, Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException, Exception {
        log.debug("unsuspendCredit");
        try {
            if (null == dto)
                throw new ParameterException("param dto null");

            if (null == principal)
                throw new ParameterException("param principal null");

            if (StringUtils.isEmpty(id))
                throw new ParameterException("param id null");

            if (null == dto.getTCkMstServiceType())
                throw new ParameterException("param tckMstServiceType null");

            if (null == dto.getTCoreAccn())
                throw new ParameterException("param tcoreAccn null");

            if (null == dto.getTMstCurrency())
                throw new ParameterException("param tmstCurrency null");

            List<ValidationError> validationErrors = cValidator.validationUnsuspendCredit(dto, principal);
            if (null != validationErrors && !validationErrors.isEmpty())
                throw new ValidationException(validationErrorMap(validationErrors));

            CkCredit _dto = _unsuspendCredit(dto, id, principal);
            return _dto;
        } catch (ParameterException|EntityNotFoundException|ValidationException e) {
            log.error("unsuspendCredit", (Throwable)e);
            throw e;
        } catch (Exception e) {
            log.error("unsuspendCredit", e);
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public CkCredit find(CkCredit dto, Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException, Exception {
        log.debug("find");
        try {
            if (null == dto)
                throw new ParameterException("param dto null");

            if (null == principal)
                throw new ParameterException("param principal null");

            if (null == dto.getTCkMstServiceType())
                throw new ParameterException("param tckMstServiceType null");

            if (null == dto.getTMstCurrency())
                throw new ParameterException("param tmstCurrency null");

            TCkCredit entity = this.dao.getByServiceTypeAndAccnAndCcy(dto.getTCkMstServiceType(), principal.getCoreAccn(), dto.getTMstCurrency());
            if (null == entity)
                throw new EntityNotFoundException("No credit setup for account: " + principal.getCoreAccn().getAccnId() + " for service " + dto.getTCkMstServiceType().getSvctId());
            initEnity(entity);
            dto = dtoFromEntity(entity);
            if (null == dto)
                throw new ProcessingException("dtoFromEntity");

            TCkCreditSummary summary = sDao.getByServiceTypeAndAccnAndCcy(dto.getTCkMstServiceType(), principal.getCoreAccn(), dto.getTMstCurrency());
            if (null != summary) {
                CkCreditSummary ckCreditSummary = new CkCreditSummary(summary);
                dto.setTCkCreditSummary(ckCreditSummary);
            }
            return dto;
        } catch (ParameterException|EntityNotFoundException e) {
            log.error("find", (Throwable)e);
            throw e;
        } catch (Exception e) {
            log.error("find", e);
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public CkCredit fetch(CkCredit dto) throws ParameterException, EntityNotFoundException, ProcessingException, Exception {
        log.debug("find");
        try {
            if (null == dto)
                throw new ParameterException("param dto null");

            if (null == dto.getTCkMstServiceType())
                throw new ParameterException("param tckMstServiceType null");

            if (null == dto.getTMstCurrency())
                throw new ParameterException("param tmstCurrency null");

            TCkCredit entity = this.dao.getByServiceTypeAndAccnAndCcy(dto.getTCkMstServiceType(), dto.getTCoreAccn(), dto.getTMstCurrency());
            if (null == entity)
                throw new EntityNotFoundException("No credit setup for account: " + dto.getTCoreAccn().getAccnId() + " for service " + dto.getTCkMstServiceType().getSvctId());
            initEnity(entity);
            dto = dtoFromEntity(entity);
            if (null == dto)
                throw new ProcessingException("dtoFromEntity");

            TCkCreditSummary summary = sDao.getByServiceTypeAndAccnAndCcy(dto.getTCkMstServiceType(), dto.getTCoreAccn(), dto.getTMstCurrency());
            if (null != summary) {
                CkCreditSummary ckCreditSummary = new CkCreditSummary(summary);
                dto.setTCkCreditSummary(ckCreditSummary);
            }
            return dto;
        } catch (ParameterException|EntityNotFoundException e) {
            log.error("find", (Throwable)e);
            throw e;
        } catch (Exception e) {
            log.error("find", e);
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public List<CkCredit> findByAnd(CkCredit dto, int iDisplayStart, int iDisplayLength, String selectClause, String orderByClause) throws ParameterException, EntityNotFoundException, ProcessingException, Exception {
        log.debug("findByAnd");
        boolean bThrowExceptionOnEmpty = false;
        try {
            if (null == dto)
                throw new ParameterException("param dto null"); 
            
            if (StringUtils.isEmpty(selectClause))
                throw new ParameterException("param selectClause null or empty"); 
            
            List<TCkCredit> entities = findEntitiesByAnd(dto, selectClause, orderByClause, iDisplayLength, iDisplayStart);
            if (bThrowExceptionOnEmpty && Collections.isEmpty(entities))
                throw new ProcessingException("entities null or empty"); 
            
            List<CkCredit> dtos = new ArrayList<>();
            for (TCkCredit entity : entities) {
                initEnity(entity);
                dtos.add(dtoFromEntity(entity));
            } 
            return dtos;
        } catch (ParameterException e) {
            log.error("findByAnd", (Throwable)e);
            throw e;
        } catch (Exception e) {
            log.error("findByAnd", e);
            throw e;
        } 
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public int countByAnd(CkCredit dto) throws ParameterException, EntityNotFoundException, ProcessingException, Exception {
        log.debug("countByAnd");
        try {
            if (null == dto)
                throw new ParameterException("param dto null"); 
            
            String whereClause = getWhereClause(dto, false);
            HashMap<String, Object> parameters = getParameters(dto);
            int count = 0;
            if (StringUtils.isNotEmpty(whereClause) && null != parameters && parameters.size() > 0) {
                count = this.dao.count("SELECT COUNT(o) FROM " + TCkCredit.class.getName() + " o" + whereClause, parameters);
            } else {
                count = this.dao.count("SELECT COUNT(o) FROM " + TCkCredit.class.getName() + " o");
            } 
            return count;
        } catch (ParameterException|EntityNotFoundException e) {
            log.error("countByAnd", (Throwable)e);
            throw e;
        } catch (Exception e) {
            log.error("countByAnd", e);
            throw e;
        } 
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    protected List<TCkCredit> findEntitiesByAnd(CkCredit dto, String selectClause, String orderByClause, int limit, int offset) throws ParameterException, ProcessingException, Exception {
        log.debug("findEntitesByAnd");
        try {
            if (null == dto)
                throw new ParameterException("param dto null"); 
            
            if (StringUtils.isEmpty(selectClause))
                throw new ParameterException("param selectClause null or empty"); 
        
            if (StringUtils.isEmpty(orderByClause))
                throw new ParameterException("param orderByClause null or empty"); 
        
            String whereClause = getWhereClause(dto, false);
            HashMap<String, Object> parameters = getParameters(dto);
            String hqlQuery = StringUtils.isEmpty(whereClause) ? (selectClause + orderByClause) : (selectClause + whereClause + orderByClause);
            List<TCkCredit> entities = this.dao.getByQuery(hqlQuery, parameters, limit, offset);
            for (TCkCredit entity : entities)
                initEnity(entity); 
            return entities;
        } catch (ParameterException|ProcessingException e) {
            log.error("findEntitiesByAnd", (Throwable)e);
            throw e;
        } catch (Exception e) {
            log.error("findEntitiesByAnd", e);
            throw e;
        } 
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public CkCredit add(CkCredit dto, Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException, Exception {
        log.debug("add");
        Date now = Calendar.getInstance().getTime();
        try {
            if (null == dto)
                throw new ParameterException("param dto null"); 
        
            if (null == principal)
                throw new ParameterException("param principal null"); 
        
            TCkCredit entity = entityFromDTO(dto);
            if (null == entity)
                throw new ProcessingException("entityFromDTO"); 
        
            String key = entityKeyFromDTO(dto);
            if (null != key) {
                TCkCredit storedEntity = this.dao.find(key);
                if (null != storedEntity)
                    throw new ProcessingException("entity exist: " + key.toString()); 
            } 
            entity = updateEntity(ACTION.CREATE, entity, principal, now);
            this.dao.add(entity);
            CkCredit dtoAdded = dtoFromEntity(entity);
            if (null == dtoAdded)
                throw new ProcessingException("dtoFromEntity"); 
            return dtoAdded;
        } catch (ParameterException|EntityNotFoundException|ValidationException e) {
            log.error("add", (Throwable)e);
            throw e;
        } catch (Exception e) {
            log.error("add", e);
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public CkCredit update(CkCredit dto, Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException, Exception {
        log.debug("update");
        Date now = Calendar.getInstance().getTime();

        try {
            if (dto == null) 
                throw new ParameterException("param dto null");

            if (principal == null) 
                throw new ParameterException("param principal null");
            
            String key = this.entityKeyFromDTO(dto);
            if (key == null) 
                throw new ProcessingException("entityKeyFromDTO");

            TCkCredit storedEntity = this.dao.find(key);
            if (storedEntity == null) 
                throw new EntityNotFoundException("key: " + key.toString());
            initEnity(storedEntity);
            preSaveUpdateDTO(storedEntity, dto);
            
            TCkCredit entity = this.entityFromDTO(dto);
            if (entity == null) 
                throw new ProcessingException("entityFromDTO");

            entity = this.updateEntity(ACTION.MODIFY, entity, principal, now);
            BeanUtils.copyProperties(entity, storedEntity);
            dao.update(storedEntity);

            return dto;
        } catch (EntityNotFoundException | ValidationException | ParameterException var7) {
            log.error("update", var7);
            throw var7;
        } catch (Exception var8) {
            log.error("update", var8);
            throw var8;
        }
    }

    private String validationErrorMap(List<ValidationError> validationErrors) throws ParameterException, ProcessingException, Exception {
		log.debug("validationMap");

		if (null == validationErrors)
			throw new ParameterException("param errros null");
            
		if (validationErrors.isEmpty())
			throw new ProcessingException("param errros empty");

		String json;
		try {
			Map<String, String> validationMap = new HashMap<>();
			validationErrors.stream().forEach(ve -> {
				log.error(ve.getErrorType() + " " + ve.getErrorDescription() + "  " + ve);
				validationMap.put(ve.getErrorType().toString(), ve.getErrorDescription());
			});

			json = (new ObjectMapper()).writeValueAsString(validationMap);

		} catch (Exception e) {
			log.error("errorMap", e);
			throw e;
		}
		return json;
	}

    protected EntityOrderBy formatOrderByObj(EntityOrderBy orderBy) throws Exception {
        if (orderBy == null) {
            return null;
        } else if (StringUtils.isEmpty(orderBy.getAttribute())) {
            return null;
        } else {
            String newAttr = formatOrderBy(orderBy.getAttribute());
            if (StringUtils.isEmpty(newAttr)) {
                return orderBy;
            } else {
                orderBy.setAttribute(newAttr);
                return orderBy;
            }
        }
    }

    protected String getOperator(boolean whereprinted) {
      return whereprinted ? " AND " : " WHERE ";
    }
}
