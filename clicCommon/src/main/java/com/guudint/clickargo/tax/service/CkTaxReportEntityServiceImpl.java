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
import com.guudint.clickargo.common.RecordStatus;
import com.guudint.clickargo.tax.dto.CkTaxReport;
import com.guudint.clickargo.tax.model.TCkTaxReport;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.common.controller.entity.EntityFilterRequest;
import com.vcc.camelone.common.controller.entity.EntityWhere;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.exception.ValidationException;
import com.vcc.camelone.common.service.ServiceStatus;
import com.vcc.camelone.locale.dto.CoreMstLocale;

public class CkTaxReportEntityServiceImpl extends AbstractClickCargoEntityService<TCkTaxReport, String, CkTaxReport> {
    private static Logger LOG = Logger.getLogger(CkTaxReportEntityServiceImpl.class);

    public CkTaxReportEntityServiceImpl() {
        super("ckTaxReportDao", "CK TAX REPORT", "TCkTaxReport", "T_CK_TAX_REPORT");
    }

	public static char TAX_REPORT_STATUS_DOWNLOADED = 'D';
	
    @Override
    public CkTaxReport newObj(Principal principal)
            throws ParameterException, EntityNotFoundException, ProcessingException {
        LOG.info("newObj");
        CkTaxReport ckTaxReport = new CkTaxReport();
        return ckTaxReport;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CkTaxReport deleteById(String id, Principal principal)
            throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException {
        if (StringUtils.isBlank(id)) {
            throw new ParameterException("param id null or empty");
        }
        if (principal == null) {
            throw new ParameterException("param principal null or empty");
        }
        try {
            TCkTaxReport tckCkTaxReport = dao.find(id);
            if (tckCkTaxReport == null) {
                throw new EntityNotFoundException("id::" + id);
            }
            CkTaxReport ckTaxReport = dtoFromEntity(tckCkTaxReport);
            return delete(ckTaxReport, principal);
        } catch (Exception e) {
            LOG.error(e);
            throw new ProcessingException(e);
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public List<CkTaxReport> filterBy(EntityFilterRequest filterRequest)
            throws ParameterException, EntityNotFoundException, ProcessingException {
        LOG.info("filterBy");
        if (filterRequest == null) {
            throw new ParameterException("param filterRequest null");
        }
        CkTaxReport ckTaxReport = whereDto(filterRequest);
        filterRequest.setTotalRecords(countByAnd(ckTaxReport));
        List<TCkTaxReport> tCkTaxReports = findEntitiesByAnd(ckTaxReport, "from TCkTaxReport o",
                filterRequest.getOrderBy().toString(), filterRequest.getDisplayLength(),
                filterRequest.getDisplayStart());
        List<CkTaxReport> ckTaxReports = new ArrayList<>();
        for (TCkTaxReport tCkTaxReport : tCkTaxReports) {
            CkTaxReport dto = dtoFromEntity(tCkTaxReport);
            ckTaxReports.add(dto);
        }
        return ckTaxReports;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public CkTaxReport findById(String id) throws ParameterException, EntityNotFoundException, ProcessingException {
        LOG.info("findById");
        if (StringUtils.isBlank(id)) {
            throw new ParameterException("param id null or empty");
        }
        try {
            TCkTaxReport tCkTaxReport = dao.find(id);
            if (tCkTaxReport == null) {
                throw new EntityNotFoundException("id::" + id);
            }
            initEnity(tCkTaxReport);
            return dtoFromEntity(tCkTaxReport);
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
    protected CkTaxReport dtoFromEntity(TCkTaxReport tCkTaxReport) throws ParameterException, ProcessingException {
        LOG.info("dtoFromEntity");
        if (tCkTaxReport == null) {
            throw new ParameterException("param entity null");
        }
        CkTaxReport ckTaxReport = new CkTaxReport(tCkTaxReport);
        return ckTaxReport;
    }

    @Override
    protected TCkTaxReport entityFromDTO(CkTaxReport ckTaxReport) throws ParameterException, ProcessingException {
        LOG.info("entityFromDTO");
        if (ckTaxReport == null) {
            throw new ParameterException("param entity null");
        }
        TCkTaxReport tCkTaxReport = new TCkTaxReport(ckTaxReport);
        return tCkTaxReport;
    }

    @Override
    protected String entityKeyFromDTO(CkTaxReport ckTaxReport) throws ParameterException, ProcessingException {
        if (ckTaxReport == null) {
            throw new ParameterException("param dto null");
        }
        return ckTaxReport.getTrId();
    }

    @Override
    protected CoreMstLocale getCoreMstLocale(CkTaxReport ckTaxReport)
            throws ParameterException, EntityNotFoundException, ProcessingException {
        if (ckTaxReport == null) {
            throw new ParameterException("param dto null");
        }
        if (ckTaxReport.getCoreMstLocale() == null) {
            throw new ProcessingException("coreMstLocal null");
        }
        return ckTaxReport.getCoreMstLocale();
    }

    @Override
    protected HashMap<String, Object> getParameters(CkTaxReport ckTaxReport)
            throws ParameterException, ProcessingException {
		if (ckTaxReport == null) {
			throw new ParameterException("param dto null");
		}
		SimpleDateFormat sdf = new SimpleDateFormat(CkDateFormat.Java.DD_MM_YYYY);
		HashMap<String, Object> parameters = new HashMap<>();
		
		if (StringUtils.isNotBlank(ckTaxReport.getTrId())) {
			parameters.put("trId", "%" + ckTaxReport.getTrId() + "%");
		}
		
		if (StringUtils.isNotBlank(ckTaxReport.getTrName())) {
			parameters.put("trName", "%" + ckTaxReport.getTrName() + "%");
		}
		
		if (ckTaxReport.getTrDtCreate() != null) {
			parameters.put("trDtCreate", sdf.format(ckTaxReport.getTrDtCreate()));
		}
		
		if (ckTaxReport.getTrDtLupd() != null) {
			parameters.put("trDtLupd", sdf.format(ckTaxReport.getTrDtLupd()));
		}
		
		// filter for trNumRecords
		if (ckTaxReport.getTrNumRecords() != null) {
			parameters.put("trNumRecords", ckTaxReport.getTrNumRecords());
		}
		

		if (ckTaxReport.getTrService() != null) {
			parameters.put("trService", ckTaxReport.getTrService());
		}
		
		if (ckTaxReport.getTrStatus() != null) {
			parameters.put("trStatus", ckTaxReport.getTrStatus());
			parameters.put("validStatus", ckTaxReport.getTrStatus());
		} else {
			if (ckTaxReport.getHistory() != null && ckTaxReport.getHistory().equalsIgnoreCase("default")) {
				parameters.put("validStatus", Arrays.asList(RecordStatus.ACTIVE.getCode()));
			} else if (ckTaxReport.getHistory() != null && ckTaxReport.getHistory().equalsIgnoreCase("history")) {
				parameters.put("validStatus", Arrays.asList(TAX_REPORT_STATUS_DOWNLOADED));
			}
		}
		
		return parameters;
	}

    @Override
    protected String getWhereClause(CkTaxReport ckTaxReport, boolean wherePrinted)
            throws ParameterException, ProcessingException {
		LOG.debug("getWhereClause");
		String EQUAL = " = :", CONTAIN = " like :";
		
		if (ckTaxReport == null) {
			throw new ParameterException("param dto null");
		}
		StringBuffer condition = new StringBuffer();
		
		if (StringUtils.isNotBlank(ckTaxReport.getTrId())) {
			condition.append(getOperator(wherePrinted) + "o.trId" + CONTAIN
					+ "trId");
			wherePrinted = true;
		}
		
		if (StringUtils.isNotBlank(ckTaxReport.getTrName())) {
			condition.append(getOperator(wherePrinted) + "o.trName" + CONTAIN
					+ "trName");
			wherePrinted = true;
		}
		
		if (ckTaxReport.getTrDtCreate() != null) {
			condition.append(getOperator(wherePrinted) + "DATE_FORMAT(" + "o.trDtCreate" + ",'"
					+ CkDateFormat.MySql.D_M_Y + "')" + EQUAL + "trDtCreate");
			wherePrinted = true;
		}
		
		if (ckTaxReport.getTrDtLupd() != null) {
			condition.append(getOperator(wherePrinted) + "DATE_FORMAT(" + "o.trDtLupd" + ",'"
					+ CkDateFormat.MySql.D_M_Y + "')" + EQUAL + "trDtLupd");
			wherePrinted = true;
		}
		
		// filter for trNumRecords
		if (ckTaxReport.getTrNumRecords() != null) {
			condition.append(getOperator(wherePrinted) + "o.trNumRecords" + CONTAIN
					+ "trNumRecords");
			wherePrinted = true;
		}
		
		if (ckTaxReport.getTrStatus() != null) {
			condition.append(getOperator(wherePrinted) + "o.trStatus" + CONTAIN
					+ "trStatus");
			wherePrinted = true;
		}
		
		if (ckTaxReport.getTrService() != null) {
			condition.append(getOperator(wherePrinted) + "o.trService" + CONTAIN
					+ "trService");
			wherePrinted = true;
		}
		
		condition.append(getOperator(wherePrinted) + "o.trStatus" + " IN :validStatus");

	
		return condition.toString();
	}

    @Override
    protected TCkTaxReport initEnity(TCkTaxReport tCkTaxReport) throws ParameterException, ProcessingException {
        LOG.info("initEntity");
        return tCkTaxReport;
    }

    @Override
    protected CkTaxReport preSaveUpdateDTO(TCkTaxReport tCkTaxReport, CkTaxReport ckTaxReport)
            throws ParameterException, ProcessingException {
        if (tCkTaxReport == null) {
            throw new ParameterException("param entity null");
        }
        if (ckTaxReport == null) {
            throw new ParameterException("param dto null");
        }
        ckTaxReport.setTrDtCreate(tCkTaxReport.getTrDtCreate());
        ckTaxReport.setTrUidCreate(tCkTaxReport.getTrUidCreate());
        return ckTaxReport;
    }

    @Override
    protected void preSaveValidation(CkTaxReport arg0, Principal arg1) throws ParameterException, ProcessingException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'preSaveValidation'");
    }

    @Override
    protected ServiceStatus preUpdateValidation(CkTaxReport arg0, Principal arg1)
            throws ParameterException, ProcessingException {
        return null;
    }

    @Override
    protected CkTaxReport setCoreMstLocale(CoreMstLocale coreMstLocale, CkTaxReport ckTaxReport)
            throws ParameterException, EntityNotFoundException, ProcessingException {
        ckTaxReport.setCoreMstLocale(coreMstLocale);
        return ckTaxReport;
    }

    @Override
    protected TCkTaxReport updateEntity(ACTION action, TCkTaxReport tCkTaxReport, Principal principal, Date date)
            throws ParameterException, ProcessingException {
        LOG.info("updateEntity");
        if (tCkTaxReport == null) {
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
                tCkTaxReport.setTrUidCreate(userId);
                tCkTaxReport.setTrDtCreate(date);
                tCkTaxReport.setTrUidLupd(userId);
                tCkTaxReport.setTrDtLupd(date);
                break;
            case MODIFY:
                tCkTaxReport.setTrUidLupd(userId);
                tCkTaxReport.setTrDtLupd(date);
            default:
                break;
        }
        return tCkTaxReport;
    }

    @Override
    protected TCkTaxReport updateEntityStatus(TCkTaxReport tCkTaxReport, char status)
            throws ParameterException, ProcessingException {
        LOG.info("updateEntityStatus");
        if (tCkTaxReport == null) {
            throw new ParameterException("param entity null");
        }
        tCkTaxReport.setTrStatus(status);
        return tCkTaxReport;
    }

    @Override
    protected CkTaxReport whereDto(EntityFilterRequest filterRequest) throws ParameterException, ProcessingException {
		LOG.debug("whereDto");
		
		try {
			if (filterRequest == null) {
				throw new ParameterException("param filterRequest null");
			}
			SimpleDateFormat sdfDate = new SimpleDateFormat(CkDateFormat.Java.DD_MM_YYYY);
			CkTaxReport dto = new CkTaxReport();
			
			for (EntityWhere entityWhere : filterRequest.getWhereList()) {
				Optional<String> opValue = Optional.ofNullable(entityWhere.getValue());
				if (!opValue.isPresent())
					continue;
				String attribute = "o." + entityWhere.getAttribute();
				if (attribute.equalsIgnoreCase("o.trId"))
					dto.setTrId(opValue.get());
				else if (attribute.equalsIgnoreCase("o.trName"))
					dto.setTrName(opValue.get());
				// filter for trNumRecords
				else if (attribute.equalsIgnoreCase("o.trNumRecords"))
					if (StringUtils.isNotEmpty(opValue.get()))
						dto.setTrNumRecords(Integer.valueOf(opValue.get()));
					else
						dto.setTrNumRecords(0);
				else if (attribute.equalsIgnoreCase("o.trDtCreate"))
					dto.setTrDtCreate(sdfDate.parse(opValue.get()));
				else if (attribute.equalsIgnoreCase("o.trDtLupd"))
					dto.setTrDtLupd(sdfDate.parse(opValue.get()));
				else if (attribute.equalsIgnoreCase("o.trStatus"))
					dto.setTrStatus((opValue.get() == null) ? null : opValue.get().charAt(0));
				else if (attribute.equalsIgnoreCase("o.trService"))
					dto.setTrService(opValue.get());
				else if (attribute.equalsIgnoreCase("o.history"))
					dto.setHistory(opValue.get());
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
