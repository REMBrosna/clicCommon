package com.guudint.clickargo.journal.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guudint.clickargo.common.model.ValidationError;
import com.guudint.clickargo.common.service.ICkSession;
import com.guudint.clickargo.journal.dto.CkCreditJournal;
import com.guudint.clickargo.journal.model.TCkCreditJournal;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.common.controller.entity.EntityFilterRequest;
import com.vcc.camelone.common.controller.entity.EntityOrderBy;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.exception.ValidationException;
import com.vcc.camelone.common.service.entity.AbstractEntityService.ACTION;

import io.jsonwebtoken.lang.Collections;

public abstract class AbstractJournalService implements IJournalService {

    private static Logger log = LogManager.getLogger(AbstractJournalService.class);

    @Autowired
    protected GenericDao<TCkCreditJournal, String> dao;

    @Autowired
    protected IJournalValidate jValidator;

    @Autowired
    protected ICkSession ckSession;
    
    protected abstract CkCreditJournal _reserve(CkCreditJournal dto, Principal principal) throws ParameterException, ValidationException, ProcessingException, Exception;

    protected abstract CkCreditJournal _reverse(CkCreditJournal dto, Principal principal) throws ParameterException, ValidationException, ProcessingException, Exception;
    
    protected abstract CkCreditJournal _utilize(CkCreditJournal dto, Principal principal) throws ParameterException, ValidationException, ProcessingException, Exception;

    protected abstract CkCreditJournal _pay(CkCreditJournal dto, Principal principal) throws ParameterException, ValidationException, ProcessingException, Exception;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    protected abstract TCkCreditJournal initEnity(TCkCreditJournal entity) throws ParameterException, ProcessingException, Exception;

    protected abstract TCkCreditJournal entityFromDTO(CkCreditJournal dto) throws ParameterException, ProcessingException, Exception;
  
    protected abstract CkCreditJournal dtoFromEntity(TCkCreditJournal entity) throws ParameterException, ProcessingException, Exception;

    protected abstract String entityKeyFromDTO(CkCreditJournal dto) throws ParameterException, ProcessingException, Exception;

    protected abstract TCkCreditJournal updateEntity(ACTION action, TCkCreditJournal entity, Principal principal, Date date) throws ParameterException, ProcessingException, Exception;

    protected abstract String formatOrderBy(String attributes) throws ParameterException, ProcessingException, Exception;
    
    protected abstract String getWhereClause(CkCreditJournal dto, boolean wherePrinted) throws ParameterException, ProcessingException, Exception;

    protected abstract HashMap<String, Object> getParameters(CkCreditJournal dto) throws ParameterException, ProcessingException, Exception;

    protected abstract CkCreditJournal whereDto(EntityFilterRequest paramEntityFilterRequest) throws ParameterException, ProcessingException, Exception;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public CkCreditJournal reserve(CkCreditJournal dto, Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException, Exception {
        log.debug("reserve");
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

            if (null == dto.getTCkMstJournalTxnType())
                throw new ParameterException("param tckMstJournalTxnType null");

            if (null == dto.getCjnTxnRef())
                throw new ParameterException("param cjnTxnRef null");

            if (null == dto.getCjnReserve())
                throw new ParameterException("param cjnReserve null");

            List<ValidationError> validationErrors = jValidator.validateReserve(dto, principal);
            if (null != validationErrors && !validationErrors.isEmpty())
                throw new ValidationException(validationErrorMap(validationErrors));

            CkCreditJournal _dto = _reserve(dto, principal);
            return _dto;
        } catch (ParameterException|EntityNotFoundException|ValidationException e) {
            log.error("reserve", (Throwable)e);
            throw e;
        } catch (Exception e) {
            log.error("reserve", e);
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public CkCreditJournal reverse(CkCreditJournal dto, Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException, Exception {
        log.debug("reverse");
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

            if (null == dto.getTCkMstJournalTxnType())
                throw new ParameterException("param tckMstJournalTxnType null");

            if (null == dto.getCjnTxnRef())
                throw new ParameterException("param cjnTxnRef null");

            if (null == dto.getCjnReserve())
                throw new ParameterException("param cjnReserve null");

            List<ValidationError> validationErrors = jValidator.validateReverse(dto, principal);
            if (null != validationErrors && !validationErrors.isEmpty())
                throw new ValidationException(validationErrorMap(validationErrors));

            CkCreditJournal _dto = _reverse(dto, principal);
            return _dto;
        } catch (ParameterException|EntityNotFoundException|ValidationException e) {
            log.error("reverse", (Throwable)e);
            throw e;
        } catch (Exception e) {
            log.error("reverse", e);
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public CkCreditJournal utilize(CkCreditJournal dto, Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException, Exception {
        log.debug("utilize");
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

            if (null == dto.getTCkMstJournalTxnType())
                throw new ParameterException("param tckMstJournalTxnType null");

            if (null == dto.getCjnTxnRef())
                throw new ParameterException("param cjnTxnRef null");

            if (null == dto.getCjnUtilized())
                throw new ParameterException("param cjnReserve null");

            List<ValidationError> validationErrors = jValidator.validateUtilize(dto, principal);
            if (null != validationErrors && !validationErrors.isEmpty())
                throw new ValidationException(validationErrorMap(validationErrors));

            CkCreditJournal _dto = _utilize(dto, principal);
            return _dto;
        } catch (ParameterException|EntityNotFoundException|ValidationException e) {
            log.error("utilize", (Throwable)e);
            throw e;
        } catch (Exception e) {
            log.error("utilize", e);
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public CkCreditJournal pay(CkCreditJournal dto, Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException, Exception {
        log.debug("pay");
        try {
            if (null == dto)
                throw new ParameterException("param dto null");

            if (null == dto.getTCkMstServiceType())
                throw new ParameterException("param tckMstServiceType null");

            if (null == dto.getTCoreAccn())
                throw new ParameterException("param tcoreAccn null");

            if (null == dto.getTMstCurrency())
                throw new ParameterException("param tmstCurrency null");

            if (null == dto.getTCkMstJournalTxnType())
                throw new ParameterException("param tckMstJournalTxnType null");

            if (null == dto.getCjnTxnRef())
                throw new ParameterException("param cjnTxnRef null");

            if (null == dto.getCjnUtilized())
                throw new ParameterException("param cjnReserve null");

            List<ValidationError> validationErrors = jValidator.validatePay(dto, principal);
            if (null != validationErrors && !validationErrors.isEmpty())
                throw new ValidationException(validationErrorMap(validationErrors));

            CkCreditJournal _dto = _pay(dto, principal);
            return _dto;
        } catch (ParameterException|EntityNotFoundException|ValidationException e) {
            log.error("pay", (Throwable)e);
            throw e;
        } catch (Exception e) {
            log.error("pay", e);
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public CkCreditJournal find(CkCreditJournal dto) throws ParameterException, EntityNotFoundException, ProcessingException, Exception {
        log.debug("find");
        try {
            if (null == dto)
                throw new ParameterException("param dto null");

            String key = entityKeyFromDTO(dto);
            if (null == key)
                throw new ParameterException("entityKeyFromDTO"); 

            TCkCreditJournal entity = this.dao.find(key);
            if (null == entity)
                throw new EntityNotFoundException("key: " + key.toString());
            initEnity(entity);
            dto = dtoFromEntity(entity);
            if (null == dto)
                throw new ProcessingException("dtoFromEntity");
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
    public List<CkCreditJournal> findByAnd(CkCreditJournal dto, int iDisplayStart, int iDisplayLength, String selectClause, String orderByClause) throws ParameterException, EntityNotFoundException, ProcessingException, Exception {
        log.debug("findByAnd");
        boolean bThrowExceptionOnEmpty = false;
        try {
            if (null == dto)
                throw new ParameterException("param dto null"); 
            
            if (StringUtils.isEmpty(selectClause))
                throw new ParameterException("param selectClause null or empty"); 
            
            List<TCkCreditJournal> entities = findEntitiesByAnd(dto, selectClause, orderByClause, iDisplayLength, iDisplayStart);
            if (bThrowExceptionOnEmpty && Collections.isEmpty(entities))
                throw new ProcessingException("entities null or empty"); 
            
            List<CkCreditJournal> dtos = new ArrayList<>();
            for (TCkCreditJournal entity : entities) {
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
    public int countByAnd(CkCreditJournal dto) throws ParameterException, EntityNotFoundException, ProcessingException, Exception {
        log.debug("countByAnd");
        try {
            if (null == dto)
                throw new ParameterException("param dto null"); 
            
            String whereClause = getWhereClause(dto, false);
            HashMap<String, Object> parameters = getParameters(dto);
            int count = 0;
            if (StringUtils.isNotEmpty(whereClause) && null != parameters && parameters.size() > 0) {
                count = this.dao.count("SELECT COUNT(o) FROM " + TCkCreditJournal.class.getName() + " o" + whereClause, parameters);
            } else {
                count = this.dao.count("SELECT COUNT(o) FROM " + TCkCreditJournal.class.getName() + " o");
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
    protected List<TCkCreditJournal> findEntitiesByAnd(CkCreditJournal dto, String selectClause, String orderByClause, int limit, int offset) throws ParameterException, ProcessingException, Exception {
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
            List<TCkCreditJournal> entities = this.dao.getByQuery(hqlQuery, parameters, limit, offset);
            for (TCkCreditJournal entity : entities)
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
    public CkCreditJournal add(CkCreditJournal dto, Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException, Exception {
        log.debug("add");
        Date now = Calendar.getInstance().getTime();
        try {
            if (null == dto)
                throw new ParameterException("param dto null"); 
        
            TCkCreditJournal entity = entityFromDTO(dto);
            if (null == entity)
                throw new ProcessingException("entityFromDTO"); 
        
            String key = entityKeyFromDTO(dto);
            if (null != key) {
                TCkCreditJournal storedEntity = this.dao.find(key);
                if (null != storedEntity)
                    throw new ProcessingException("entity exist: " + key.toString()); 
            } 
            entity = updateEntity(ACTION.CREATE, entity, principal, now);
            this.dao.add(entity);
            CkCreditJournal dtoAdded = dtoFromEntity(entity);
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
