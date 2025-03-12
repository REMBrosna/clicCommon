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
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.common.AbstractClickCargoEntityService;
import com.guudint.clickargo.common.CkDateFormat;
import com.guudint.clickargo.tax.dto.CkTaxInvoice;
import com.guudint.clickargo.tax.model.TCkTaxInvoice;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.common.controller.entity.EntityFilterRequest;
import com.vcc.camelone.common.controller.entity.EntityWhere;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.exception.ValidationException;
import com.vcc.camelone.common.service.ServiceStatus;
import com.vcc.camelone.locale.dto.CoreMstLocale;

public class CkTaxInvoiceEntityServiceImpl extends AbstractClickCargoEntityService<TCkTaxInvoice, String, CkTaxInvoice> {

    private static Logger LOG = Logger.getLogger(CkTaxInvoiceEntityServiceImpl.class);
	
	public static char TAX_INVOICE_STATUS_EXPORTED = 'E';
	public static char TAX_INVOICE_STATUS_COMPLETED = 'C';
	public static char TAX_INVOICE_STATUS_NEW = 'N';

    public CkTaxInvoiceEntityServiceImpl() {
        super("ckTaxInvoiceDao", "CK TAX INVOICE", "TCkTaxInvoice", "T_CK_TAX_INVOICE");
    }

    @Override
    public CkTaxInvoice newObj(Principal principal)
            throws ParameterException, EntityNotFoundException, ProcessingException {
        LOG.info("newObj");
        CkTaxInvoice ckTaxInvoice = new CkTaxInvoice();
        ckTaxInvoice.setTCoreAccn(new CoreAccn());
        return ckTaxInvoice;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CkTaxInvoice deleteById(String id, Principal principal)
            throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException {
        if (StringUtils.isBlank(id)) {
            throw new ParameterException("param id null or empty");
        }
        if (principal == null) {
            throw new ParameterException("param principal null or empty");
        }
        try {
            TCkTaxInvoice tckCkTaxInvoice = dao.find(id);
            if (tckCkTaxInvoice == null) {
                throw new EntityNotFoundException("id::" + id);
            }
            CkTaxInvoice ckTaxInvoice = dtoFromEntity(tckCkTaxInvoice);
            return delete(ckTaxInvoice, principal);
        } catch (Exception e) {
            LOG.error(e);
            throw new ProcessingException(e);
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public List<CkTaxInvoice> filterBy(EntityFilterRequest filterRequest)
            throws ParameterException, EntityNotFoundException, ProcessingException {
        LOG.info("filterBy");
        if (filterRequest == null) {
            throw new ParameterException("param filterRequest null");
        }
        CkTaxInvoice ckTaxInvoice = whereDto(filterRequest);
        filterRequest.setTotalRecords(countByAnd(ckTaxInvoice));
        List<TCkTaxInvoice> tCkTaxInvoices = findEntitiesByAnd(ckTaxInvoice, "from TCkTaxInvoice o",
                filterRequest.getOrderBy().toString(), filterRequest.getDisplayLength(),
                filterRequest.getDisplayStart());
        List<CkTaxInvoice> ckTaxInvoices = new ArrayList<>();
        for (TCkTaxInvoice tCkTaxInvoice : tCkTaxInvoices) {
            CkTaxInvoice dto = dtoFromEntity(tCkTaxInvoice);
            ckTaxInvoices.add(dto);
        }
        return ckTaxInvoices;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public CkTaxInvoice findById(String id) throws ParameterException, EntityNotFoundException, ProcessingException {
        LOG.info("findById");
        if (StringUtils.isBlank(id)) {
            throw new ParameterException("param id null or empty");
        }
        try {
            TCkTaxInvoice tCkTaxInvoice = dao.find(id);
            if (tCkTaxInvoice == null) {
                throw new EntityNotFoundException("id::" + id);
            }
            initEnity(tCkTaxInvoice);
            return dtoFromEntity(tCkTaxInvoice);
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
    protected CkTaxInvoice dtoFromEntity(TCkTaxInvoice tCkTaxInvoice) throws ParameterException, ProcessingException {
        LOG.info("dtoFromEntity");
        if (tCkTaxInvoice == null) {
            throw new ParameterException("param entity null");
        }
        CkTaxInvoice ckTaxInvoice = new CkTaxInvoice(tCkTaxInvoice);
        if (tCkTaxInvoice.getTCoreAccn() != null) {
            ckTaxInvoice.setTCoreAccn(new CoreAccn(tCkTaxInvoice.getTCoreAccn()));
        }
        return ckTaxInvoice;
    }

    @Override
    protected TCkTaxInvoice entityFromDTO(CkTaxInvoice ckTaxInvoice) throws ParameterException, ProcessingException {
        LOG.info("entityFromDTO");
        if (ckTaxInvoice == null) {
            throw new ParameterException("param entity null");
        }
        TCkTaxInvoice tCkTaxInvoice = new TCkTaxInvoice(ckTaxInvoice);
        if (ckTaxInvoice.getTCoreAccn() != null) {
            ckTaxInvoice.setTCoreAccn(new CoreAccn(tCkTaxInvoice.getTCoreAccn()));
        }
        return tCkTaxInvoice;
    }

    @Override
    protected String entityKeyFromDTO(CkTaxInvoice ckTaxInvoice) throws ParameterException, ProcessingException {
        if (ckTaxInvoice == null) {
            throw new ParameterException("param dto null");
        }
        return ckTaxInvoice.getTiId();
    }

    @Override
    protected CoreMstLocale getCoreMstLocale(CkTaxInvoice ckTaxInvoice)
            throws ParameterException, EntityNotFoundException, ProcessingException {
        if (ckTaxInvoice == null) {
            throw new ParameterException("param dto null");
        }
        if (ckTaxInvoice.getCoreMstLocale() == null) {
            throw new ProcessingException("coreMstLocal null");
        }
        return ckTaxInvoice.getCoreMstLocale();
    }

    @Override
    protected HashMap<String, Object> getParameters(CkTaxInvoice ckTaxInvoice)
            throws ParameterException, ProcessingException {
		if (ckTaxInvoice == null) {
			throw new ParameterException("param dto null");
		}
		SimpleDateFormat sdf = new SimpleDateFormat(CkDateFormat.Java.DD_MM_YYYY);
		HashMap<String, Object> parameters = new HashMap<>();

		if (StringUtils.isNotBlank(ckTaxInvoice.getTiId())) {
			parameters.put("tiId", "%" + ckTaxInvoice.getTiId() + "%");
		}

		if (StringUtils.isNotBlank(ckTaxInvoice.getTiService())) {
			parameters.put("tiService", "%" + ckTaxInvoice.getTiService() + "%");
		}

		if (StringUtils.isNotBlank(ckTaxInvoice.getTiInvNo())) {
			parameters.put("tiInvNo", ckTaxInvoice.getTiInvNo());
		}

		if (StringUtils.isNotBlank(ckTaxInvoice.getTiNo())) {
			parameters.put("tiNo", ckTaxInvoice.getTiNo());
		}

		Optional<CoreAccn> opCoreAccn = Optional.of(ckTaxInvoice.getTCoreAccn());
		if (opCoreAccn.isPresent()) {
			if (StringUtils.isNotBlank(opCoreAccn.get().getAccnId())) {
				parameters.put("toAccnId", opCoreAccn.get().getAccnId());
			}
			if (StringUtils.isNotBlank(opCoreAccn.get().getAccnName())) {
				parameters.put("toAccnName", "%" + opCoreAccn.get().getAccnName() + "%");
			}
		}

		if (ckTaxInvoice.getTiInvDtIssue() != null) {
			parameters.put("tiInvDtIssue", sdf.format(ckTaxInvoice.getTiInvDtIssue()));
		}

		if (ckTaxInvoice.getTiDtCreate() != null) {
			parameters.put("tiDtCreate", sdf.format(ckTaxInvoice.getTiDtCreate()));
		}

		if (ckTaxInvoice.getTiDtLupd() != null) {
			parameters.put("tiDtLupd", sdf.format(ckTaxInvoice.getTiDtLupd()));
		}

		if (ckTaxInvoice.getTiStatus() != null) {
			parameters.put("tiStatus", ckTaxInvoice.getTiStatus());
			parameters.put("validStatus", ckTaxInvoice.getTiStatus());
		} else {
			if (ckTaxInvoice.getHistory() != null && ckTaxInvoice.getHistory().equalsIgnoreCase("default")) {
				parameters.put("validStatus", Arrays.asList(TAX_INVOICE_STATUS_EXPORTED));
			} else if (ckTaxInvoice.getHistory() != null && ckTaxInvoice.getHistory().equalsIgnoreCase("history")) {
				parameters.put("validStatus", Arrays.asList(TAX_INVOICE_STATUS_COMPLETED));
			}
		}
		/*-
		if (StringUtils.isNotBlank(ckTaxInvoice.getTiService() )) {
			parameters.put("tiService", ckTaxInvoice.getTiService());
		}*/
		if (StringUtils.isNotBlank(ckTaxInvoice.getTiJobNo() )) {
			parameters.put("tiJobNo", ckTaxInvoice.getTiJobNo());
		}
		

		return parameters;
	}

    @Override
    protected String getWhereClause(CkTaxInvoice ckTaxInvoice, boolean wherePrinted)
            throws ParameterException, ProcessingException {
		LOG.debug("getWhereClause");
		String EQUAL = " = :", CONTAIN = " like :";
		if (ckTaxInvoice == null) {
			throw new ParameterException("param dto null");
		}
		StringBuffer condition = new StringBuffer();

		if (StringUtils.isNotBlank(ckTaxInvoice.getTiId())) {
			condition.append(getOperator(wherePrinted) + "o.tiId" + CONTAIN + "tiId");
			wherePrinted = true;
		}

		if (StringUtils.isNotBlank(ckTaxInvoice.getTiService())) {
			condition.append(getOperator(wherePrinted) + "o.tiService" + CONTAIN + "tiService");
			wherePrinted = true;
		}

		if (StringUtils.isNotBlank(ckTaxInvoice.getTiInvNo())) {
			condition.append(getOperator(wherePrinted) + "o.tiInvNo" + CONTAIN + "tiInvNo");
			wherePrinted = true;
		}

		if (StringUtils.isNotBlank(ckTaxInvoice.getTiNo())) {
			condition.append(getOperator(wherePrinted) + "o.tiNo" + CONTAIN + "tiNo");
			wherePrinted = true;
		}

		Optional<CoreAccn> opCoreAccn = Optional.ofNullable(ckTaxInvoice.getTCoreAccn());
		if (opCoreAccn.isPresent()) {
			if (StringUtils.isNotBlank(opCoreAccn.get().getAccnId())) {
				condition.append(getOperator(wherePrinted)).append("o.TCoreAccn.accnId = :toAccnId");
				wherePrinted = true;
			}
			if (StringUtils.isNotBlank(opCoreAccn.get().getAccnName())) {
				condition.append(getOperator(wherePrinted)).append("o.TCoreAccn.accnName LIKE :toAccnName");
				wherePrinted = true;
			}
		}

		if (ckTaxInvoice.getTiInvDtIssue() != null) {
			condition.append(getOperator(wherePrinted) + "DATE_FORMAT(" + "o.tiInvDtIssue" + ",'"
					+ CkDateFormat.MySql.D_M_Y + "')" + EQUAL + "tiInvDtIssue");
			wherePrinted = true;
		}

		if (ckTaxInvoice.getTiDtCreate() != null) {
			condition.append(getOperator(wherePrinted) + "DATE_FORMAT(" + "o.tiDtCreate" + ",'" + CkDateFormat.MySql.D_M_Y
					+ "')" + EQUAL + "tiDtCreate");
			wherePrinted = true;
		}

		if (ckTaxInvoice.getTiDtLupd() != null) {
			condition.append(getOperator(wherePrinted) + "DATE_FORMAT(" + "o.tiDtLupd" + ",'" + CkDateFormat.MySql.D_M_Y
					+ "')" + EQUAL + "tiDtLupd");
			wherePrinted = true;
		}

		if (ckTaxInvoice.getTiStatus() != null) {
			condition.append(getOperator(wherePrinted) + "o.tiStatus" + CONTAIN + "tiStatus");
			wherePrinted = true;
		}

		/*-
		if (ckTaxInvoice.getTiService() != null) {
			condition.append(getOperator(wherePrinted) + "o.tiService" + EQUAL + "tiService");
			wherePrinted = true;
		}*/
		if (ckTaxInvoice.getTiJobNo() != null) {
			condition.append(getOperator(wherePrinted) + "o.tiJobNo" + CONTAIN + "tiJobNo");
			wherePrinted = true;
		}

		condition.append(getOperator(wherePrinted) + "o.tiStatus" + " IN :validStatus");

		return condition.toString();
	}

    @Override
    protected TCkTaxInvoice initEnity(TCkTaxInvoice tCkTaxInvoice) throws ParameterException, ProcessingException {
        LOG.info("initEntity");
        if (tCkTaxInvoice != null) {
            Hibernate.initialize(tCkTaxInvoice.getTCoreAccn());
        }
        return tCkTaxInvoice;
    }

    @Override
    protected CkTaxInvoice preSaveUpdateDTO(TCkTaxInvoice tCkTaxInvoice, CkTaxInvoice ckTaxInvoice)
            throws ParameterException, ProcessingException {
        if (tCkTaxInvoice == null) {
            throw new ParameterException("param entity null");
        }
        if (ckTaxInvoice == null) {
            throw new ParameterException("param dto null");
        }
        ckTaxInvoice.setTiDtCreate(tCkTaxInvoice.getTiDtCreate());
        ckTaxInvoice.setTiUidCreate(tCkTaxInvoice.getTiUidCreate());
        return ckTaxInvoice;
    }

    @Override
    protected void preSaveValidation(CkTaxInvoice arg0, Principal arg1) throws ParameterException, ProcessingException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'preSaveValidation'");
    }

    @Override
    protected ServiceStatus preUpdateValidation(CkTaxInvoice arg0, Principal arg1)
            throws ParameterException, ProcessingException {
        return null;
    }

    @Override
    protected CkTaxInvoice setCoreMstLocale(CoreMstLocale coreMstLocale, CkTaxInvoice ckTaxInvoice)
            throws ParameterException, EntityNotFoundException, ProcessingException {
        ckTaxInvoice.setCoreMstLocale(coreMstLocale);
        return ckTaxInvoice;
    }

    @Override
    protected TCkTaxInvoice updateEntity(ACTION action, TCkTaxInvoice tCkTaxInvoice, Principal principal, Date date)
            throws ParameterException, ProcessingException {
        LOG.info("updateEntity");
        if (tCkTaxInvoice == null) {
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
                tCkTaxInvoice.setTiUidCreate(userId);
                tCkTaxInvoice.setTiDtCreate(date);
                tCkTaxInvoice.setTiUidLupd(userId);
                tCkTaxInvoice.setTiDtLupd(date);
                break;
            case MODIFY:
                tCkTaxInvoice.setTiUidLupd(userId);
                tCkTaxInvoice.setTiDtLupd(date);
            default:
                break;
        }
        return tCkTaxInvoice;
    }

    @Override
    protected TCkTaxInvoice updateEntityStatus(TCkTaxInvoice tCkTaxInvoice, char status)
            throws ParameterException, ProcessingException {
        LOG.info("updateEntityStatus");
        if (tCkTaxInvoice == null) {
            throw new ParameterException("param entity null");
        }
        tCkTaxInvoice.setTiStatus(status);
        return tCkTaxInvoice;
    }

    @Override
    protected CkTaxInvoice whereDto(EntityFilterRequest filterRequest) throws ParameterException, ProcessingException {
		LOG.debug("whereDto");

		try {
			if (filterRequest == null) {
				throw new ParameterException("param filterRequest null");
			}
			SimpleDateFormat sdfDate = new SimpleDateFormat(CkDateFormat.Java.DD_MM_YYYY);
			CkTaxInvoice dto = new CkTaxInvoice();
			CoreAccn tCoreAccn = new CoreAccn();

			for (EntityWhere entityWhere : filterRequest.getWhereList()) {
				Optional<String> opValue = Optional.ofNullable(entityWhere.getValue());
				if (!opValue.isPresent())
					continue;
				String attribute = "o." + entityWhere.getAttribute();
				if (attribute.equalsIgnoreCase("o.tiService"))
					dto.setTiService(opValue.get());
				else if (attribute.equalsIgnoreCase("o.tiInvNo"))
					dto.setTiInvNo(opValue.get());
				else if (attribute.equalsIgnoreCase("o.tiInvDtIssue"))
					dto.setTiInvDtIssue(sdfDate.parse(opValue.get()));
				else if (attribute.equalsIgnoreCase("o.tiDtCreate"))
					dto.setTiDtCreate(sdfDate.parse(opValue.get()));
				else if (attribute.equalsIgnoreCase("o.tiDtLupd"))
					dto.setTiDtLupd(sdfDate.parse(opValue.get()));
				else if (attribute.equalsIgnoreCase("o.tiNo"))
					dto.setTiNo(opValue.get());
				else if (attribute.equalsIgnoreCase("o.tiJobNo"))
					dto.setTiJobNo(opValue.get());
				else if (attribute.equalsIgnoreCase("o.tiStatus"))
					dto.setTiStatus((opValue.get() == null) ? null : opValue.get().charAt(0));
				else if (attribute.equalsIgnoreCase("o.history"))
					dto.setHistory(opValue.get());
				else if (attribute.equalsIgnoreCase("o.TCoreAccn.accnId"))
					tCoreAccn.setAccnId(opValue.get());
				else if (attribute.equalsIgnoreCase("o.TCoreAccn.accnName"))
					tCoreAccn.setAccnName(opValue.get());
				else if (attribute.equalsIgnoreCase("o.tiService"))
					dto.setTiService(opValue.get());
			}

			dto.setTCoreAccn(tCoreAccn);

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
