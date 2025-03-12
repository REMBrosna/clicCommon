package com.guudint.clickargo.tax.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.common.AbstractClickCargoEntityService;
import com.guudint.clickargo.common.CkDateFormat;
import com.guudint.clickargo.common.CkUtil;
import com.guudint.clickargo.common.RecordStatus;
import com.guudint.clickargo.tax.dto.CkTaxSeq;
import com.guudint.clickargo.tax.model.TCkTaxSeq;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.common.controller.entity.EntityFilterRequest;
import com.vcc.camelone.common.controller.entity.EntityWhere;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.exception.ValidationException;
import com.vcc.camelone.common.service.ServiceStatus;
import com.vcc.camelone.locale.dto.CoreMstLocale;

public class CkTaxSeqEntityServiceImpl extends AbstractClickCargoEntityService<TCkTaxSeq, String, CkTaxSeq> {
    private static Logger LOG = Logger.getLogger(CkTaxSeqEntityServiceImpl.class);

    public CkTaxSeqEntityServiceImpl() {
        super("ckTaxSeqDao", "CK TAX SEQUENCE", "TCkTaxSeq", "T_CK_TAX_SEQ");
    }

    @Override
    public CkTaxSeq newObj(Principal principal)
            throws ParameterException, EntityNotFoundException, ProcessingException {
        LOG.info("newObj");
        CkTaxSeq ckTaxSeq = new CkTaxSeq();
        return ckTaxSeq;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CkTaxSeq deleteById(String id, Principal principal)
            throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException {
        if (StringUtils.isBlank(id)) {
            throw new ParameterException("param id null or empty");
        }
        if (principal == null) {
            throw new ParameterException("param principal null or empty");
        }
        try {
            TCkTaxSeq tckCkTaxSeq = dao.find(id);
            if (tckCkTaxSeq == null) {
                throw new EntityNotFoundException("id::" + id);
            }
            CkTaxSeq ckTaxSeq = dtoFromEntity(tckCkTaxSeq);
            return delete(ckTaxSeq, principal);
        } catch (Exception e) {
            LOG.error(e);
            throw new ProcessingException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public List<CkTaxSeq> filterBy(EntityFilterRequest filterRequest)
            throws ParameterException, EntityNotFoundException, ProcessingException {
        LOG.info("filterBy");
        if (filterRequest == null) {
            throw new ParameterException("param filterRequest null");
        }
        CkTaxSeq ckTaxSeq = whereDto(filterRequest);
        filterRequest.setTotalRecords(countByAnd(ckTaxSeq));
        List<TCkTaxSeq> tCkTaxSeqs = findEntitiesByAnd(ckTaxSeq, "from TCkTaxSeq o",
                filterRequest.getOrderBy().toString(), filterRequest.getDisplayLength(),
                filterRequest.getDisplayStart());
        List<CkTaxSeq> ckTaxSeqs = new ArrayList<>();
        for (TCkTaxSeq tCkTaxSeq : tCkTaxSeqs) {
            CkTaxSeq dto = dtoFromEntity(tCkTaxSeq);
            ckTaxSeqs.add(dto);
        }
        return ckTaxSeqs;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public CkTaxSeq findById(String id) throws ParameterException, EntityNotFoundException, ProcessingException {
        LOG.info("findById");
        if (StringUtils.isBlank(id)) {
            throw new ParameterException("param id null or empty");
        }
        try {
            TCkTaxSeq tCkTaxSeq = dao.find(id);
            if (tCkTaxSeq == null) {
                throw new EntityNotFoundException("id::" + id);
            }
            initEnity(tCkTaxSeq);
            return dtoFromEntity(tCkTaxSeq);
        } catch (Exception e) {
            LOG.error(e);
            throw new ProcessingException(e);
        }
    }

    @Override
    protected void initBusinessValidator() {

    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }

    @Override
    protected CkTaxSeq dtoFromEntity(TCkTaxSeq tCkTaxSeq) throws ParameterException, ProcessingException {
        LOG.info("dtoFromEntity");
        if (tCkTaxSeq == null) {
            throw new ParameterException("param entity null");
        }
        CkTaxSeq ckTaxSeq = new CkTaxSeq(tCkTaxSeq);
        return ckTaxSeq;
    }

    @Override
    protected TCkTaxSeq entityFromDTO(CkTaxSeq ckTaxSeq) throws ParameterException, ProcessingException {
        LOG.info("entityFromDTO");
        if (ckTaxSeq == null) {
            throw new ParameterException("param entity null");
        }
        TCkTaxSeq tCkTaxSeq = new TCkTaxSeq(ckTaxSeq);
        return tCkTaxSeq;
    }

    @Override
    protected String entityKeyFromDTO(CkTaxSeq ckTaxSeq) throws ParameterException, ProcessingException {
        if (ckTaxSeq == null) {
            throw new ParameterException("param dto null");
        }
        return ckTaxSeq.getTsId(); 
    }

    @Override
    protected CoreMstLocale getCoreMstLocale(CkTaxSeq ckTaxSeq)
            throws ParameterException, EntityNotFoundException, ProcessingException {
        if (ckTaxSeq == null) {
            throw new ParameterException("param dto null");
        }
        if (ckTaxSeq.getCoreMstLocale() == null) {
            throw new ProcessingException("coreMstLocal null");
        }
        return ckTaxSeq.getCoreMstLocale();
    }

    @Override
    protected HashMap<String, Object> getParameters(CkTaxSeq ckTaxSeq)
            throws ParameterException, ProcessingException {
		if (ckTaxSeq == null) {
			throw new ParameterException("param dto null");
		}
		SimpleDateFormat sdf = new SimpleDateFormat(CkDateFormat.Java.DD_MM_YYYY);
		HashMap<String, Object> parameters = new HashMap<>();
		if (StringUtils.isNotBlank(ckTaxSeq.getTsId())) {
			parameters.put("tsId", "%" + ckTaxSeq.getTsId() + "%");
		}
		
		if (StringUtils.isNotBlank(ckTaxSeq.getTsPrefix())) {
			parameters.put("tsPrefix", "%" + ckTaxSeq.getTsPrefix() + "%");
		}
		
		if (ckTaxSeq.getTsRangeBegin()!=0) {
		    parameters.put("tsRangeBegin", ckTaxSeq.getTsRangeBegin());
		}
		
		if (ckTaxSeq.getTsRangeEnd()!=0) {
		    parameters.put("tsRangeEnd", ckTaxSeq.getTsRangeEnd());
		}
		
		if (ckTaxSeq.getTsRangeCurrent()!=0) {
		    parameters.put("tsRangeCurrent", ckTaxSeq.getTsRangeCurrent());
		}
		
		if (ckTaxSeq.getTsDtCreate() != null) {
			parameters.put("tsDtCreate", sdf.format(ckTaxSeq.getTsDtCreate()));
		}
		if (ckTaxSeq.getTsDtLupd() != null) {
			parameters.put("tsDtLupd", sdf.format(ckTaxSeq.getTsDtLupd()));
		}
		
		if (ckTaxSeq.getTsStatus() != null) {
			parameters.put("tsStatus", ckTaxSeq.getTsStatus());
			parameters.put("validStatus", ckTaxSeq.getTsStatus());
		} else {
			if (ckTaxSeq.getHistory() != null && ckTaxSeq.getHistory().equalsIgnoreCase("default")) {
				parameters.put("validStatus", Arrays.asList(RecordStatus.ACTIVE.getCode(), RecordStatus.INACTIVE.getCode()));
			} 
			else if (ckTaxSeq.getHistory() != null && ckTaxSeq.getHistory().equalsIgnoreCase("history")) {
				parameters.put("validStatus", Arrays.asList(RecordStatus.DEACTIVATE.getCode(), CkTaxSeqService.TAX_SEQ_STATUS_EXPIRE));
			}
		}
		
		if (StringUtils.isNotBlank(ckTaxSeq.getTsService())) {
			parameters.put("tsService", ckTaxSeq.getTsService());
		}

		return parameters;
	}

    @Override
    protected String getWhereClause(CkTaxSeq ckTaxSeq, boolean wherePrinted)
            throws ParameterException, ProcessingException {
		LOG.debug("getWhereClause");
		String EQUAL = " = :", CONTAIN = " like :";
		if (ckTaxSeq == null) {
			throw new ParameterException("param dto null");
		}
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(ckTaxSeq.getTsId())) {
			condition.append(getOperator(wherePrinted) + "o.tsId" + CONTAIN
					+ "tsId");
			wherePrinted = true;
		}
		if (StringUtils.isNotBlank(ckTaxSeq.getTsPrefix())) {
			condition.append(getOperator(wherePrinted) + "o.tsPrefix" + CONTAIN
					+ "tsPrefix");
			wherePrinted = true;
		}
		if (ckTaxSeq.getTsRangeBegin()!=0) {
			condition.append(getOperator(wherePrinted) + "o.tsRangeBegin" + EQUAL
					+ "tsRangeBegin");
			wherePrinted = true;
		}
		if (ckTaxSeq.getTsRangeEnd()!=0) {
			condition.append(getOperator(wherePrinted) + "o.tsRangeEnd" + EQUAL
					+ "tsRangeEnd");
			wherePrinted = true;
		}
		if (ckTaxSeq.getTsRangeCurrent()!=0) {
			condition.append(getOperator(wherePrinted) + "o.tsRangeCurrent" + EQUAL
					+ "tsRangeCurrent");
			wherePrinted = true;
		}
		if (ckTaxSeq.getTsDtCreate() != null) {
			condition.append(getOperator(wherePrinted) + "DATE_FORMAT(" + "o.tsDtCreate" + ",'"
					+ CkDateFormat.MySql.D_M_Y + "')" + EQUAL + "tsDtCreate");
			wherePrinted = true;
		}
		if (ckTaxSeq.getTsDtLupd() != null) {
			condition.append(getOperator(wherePrinted) + "DATE_FORMAT(" + "o.tsDtLupd" + ",'"
					+ CkDateFormat.MySql.D_M_Y + "')" + EQUAL + "tsDtLupd");
			wherePrinted = true;
		}
		
		if (ckTaxSeq.getTsStatus() != null) {
			condition.append(getOperator(wherePrinted) + "o.tsStatus" + CONTAIN
					+ "tsStatus");
			wherePrinted = true;
		}
		if (ckTaxSeq.getTsService() != null) {
			condition.append(getOperator(wherePrinted) + "o.tsService" + EQUAL
					+ "tsService");
			wherePrinted = true;
		}
		
		condition.append(getOperator(wherePrinted) + "o.tsStatus" + " IN :validStatus");
	
		return condition.toString();
	}

    @Override
    protected TCkTaxSeq initEnity(TCkTaxSeq tCkTaxSeq) throws ParameterException, ProcessingException {
        LOG.info("initEntity");
        return tCkTaxSeq;
    }

    @Override
    protected CkTaxSeq preSaveUpdateDTO(TCkTaxSeq tCkTaxSeq, CkTaxSeq ckTaxSeq)
            throws ParameterException, ProcessingException {
        if (tCkTaxSeq == null) {
            throw new ParameterException("param entity null");
        }
        if (ckTaxSeq == null) {
            throw new ParameterException("param dto null");
        }
        ckTaxSeq.setTsDtCreate(tCkTaxSeq.getTsDtCreate());
        ckTaxSeq.setTsUidCreate(tCkTaxSeq.getTsUidCreate());
        return ckTaxSeq;
    }

    @Override
    protected void preSaveValidation(CkTaxSeq arg0, Principal arg1) throws ParameterException, ProcessingException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'preSaveValidation'");
    }

    @Override
    protected ServiceStatus preUpdateValidation(CkTaxSeq arg0, Principal arg1)
            throws ParameterException, ProcessingException {
        return null;
    }

    @Override
    protected CkTaxSeq setCoreMstLocale(CoreMstLocale coreMstLocale, CkTaxSeq ckTaxSeq)
            throws ParameterException, EntityNotFoundException, ProcessingException {
        ckTaxSeq.setCoreMstLocale(coreMstLocale);
        return ckTaxSeq;
    }

    @Override
    protected TCkTaxSeq updateEntity(ACTION action, TCkTaxSeq tCkTaxSeq, Principal principal, Date date)
            throws ParameterException, ProcessingException {
        LOG.info("updateEntity");
        if (tCkTaxSeq == null) {
            throw new ParameterException("param entity null");
        }
        if (principal == null) {
            throw new ParameterException("param principal null");
        }
        if (date == null) {
            throw new ParameterException("param date null");
        }
        Optional<String> optUserId = Optional.ofNullable(principal.getUserId());
        String userId = optUserId.isPresent() ? optUserId.get() : "SYS";
        switch (action) {
            case CREATE:
            	
            	this.initDataBeforeAdd(tCkTaxSeq);
            	
                tCkTaxSeq.setTsUidCreate(userId);
                tCkTaxSeq.setTsDtCreate(date);
                tCkTaxSeq.setTsUidLupd(userId);
                tCkTaxSeq.setTsDtLupd(date);
                break;
            case MODIFY:
                tCkTaxSeq.setTsUidLupd(userId);
                tCkTaxSeq.setTsDtLupd(date);
            default:
                break;
        }
        return tCkTaxSeq;
    }
    
    private void initDataBeforeAdd(TCkTaxSeq tCkTaxSeq) throws ProcessingException  {
    	
		List<TCkTaxSeq> ctList;
		try {
			ctList = super.dao.getAll();
			
			tCkTaxSeq.setTsId(CkUtil.generateId(CkTaxSeq.PREFIX_ID));
			
			if(ctList.isEmpty()) {
				tCkTaxSeq.setTsStatus(RecordStatus.ACTIVE.getCode());
			} else {
				tCkTaxSeq.setTsStatus(RecordStatus.INACTIVE.getCode());
			}
			
			tCkTaxSeq.setTsRangeCurrent(tCkTaxSeq.getTsRangeBegin());
			
		} catch (Exception e) {
			throw new ProcessingException(e);
		}
    }

    @Override
    protected TCkTaxSeq updateEntityStatus(TCkTaxSeq tCkTaxSeq, char status)
            throws ParameterException, ProcessingException {
        LOG.info("updateEntityStatus");
        if (tCkTaxSeq == null) {
            throw new ParameterException("param entity null");
        }
        tCkTaxSeq.setTsStatus(status);
        return tCkTaxSeq;
    }

    @Override
    protected CkTaxSeq whereDto(EntityFilterRequest filterRequest) throws ParameterException, ProcessingException {
		LOG.debug("whereDto");
		
		try {
			if (filterRequest == null) {
				throw new ParameterException("param filterRequest null");
			}
			SimpleDateFormat sdfDate = new SimpleDateFormat(CkDateFormat.Java.DD_MM_YYYY);
			CkTaxSeq dto = new CkTaxSeq();
			for (EntityWhere entityWhere : filterRequest.getWhereList()) {
				Optional<String> opValue = Optional.ofNullable(entityWhere.getValue());
				if (!opValue.isPresent())
					continue;
				String attribute = "o." + entityWhere.getAttribute();
				if (attribute.equalsIgnoreCase("o.tsId"))
					dto.setTsId(opValue.get());
				else if (attribute.equalsIgnoreCase("o.tsPrefix"))
					dto.setTsPrefix(opValue.get());
				else if (attribute.equalsIgnoreCase("o.tsRangeBegin"))
					dto.setTsRangeBegin(Long.parseLong(opValue.get()));
				else if (attribute.equalsIgnoreCase("o.tsRangeEnd"))
					dto.setTsRangeEnd(Long.parseLong(opValue.get()));
				else if (attribute.equalsIgnoreCase("o.tsRangeCurrent"))
					dto.setTsRangeCurrent(Long.parseLong(opValue.get()));	
				else if (attribute.equalsIgnoreCase("o.tsDtCreate"))
					dto.setTsDtCreate(sdfDate.parse(opValue.get()));
				else if (attribute.equalsIgnoreCase("o.tsDtLupd"))
					dto.setTsDtLupd(sdfDate.parse(opValue.get()));
				else if (attribute.equalsIgnoreCase("o.tsStatus"))
					dto.setTsStatus((opValue.get() == null) ? null : opValue.get().charAt(0));
				else if (attribute.equalsIgnoreCase("o.history"))
					dto.setHistory(opValue.get());
				else if (attribute.equalsIgnoreCase("o.tsService"))
					dto.setTsService(opValue.get());
			}

			return dto;
		} catch (ParameterException ex) {
			LOG.error("whereDto", ex);
			throw ex;
		} catch (Exception ex) {
			LOG.error("whereDto", ex);
			throw new ProcessingException(ex);
		}
	}
}
