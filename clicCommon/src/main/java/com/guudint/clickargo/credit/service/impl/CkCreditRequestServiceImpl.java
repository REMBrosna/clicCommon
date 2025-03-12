package com.guudint.clickargo.credit.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.clicservice.service.ICkWorkflowService;
import com.guudint.clickargo.common.AbstractClickCargoEntityService;
import com.guudint.clickargo.common.CkUtil;
import com.guudint.clickargo.common.enums.WorkflowTypeEnum;
import com.guudint.clickargo.common.event.ApproveEvent;
import com.guudint.clickargo.common.event.RejectEvent;
import com.guudint.clickargo.common.event.SubmitEvent;
import com.guudint.clickargo.common.model.ValidationError;
import com.guudint.clickargo.credit.dao.CkCreditDao;
import com.guudint.clickargo.credit.dao.CkCreditRequestDao;
import com.guudint.clickargo.credit.dao.CkCreditSummaryDao;
import com.guudint.clickargo.credit.dto.CkCreditRequest;
import com.guudint.clickargo.credit.dto.CkMstCreditRequestState;
import com.guudint.clickargo.credit.model.TCkCredit;
import com.guudint.clickargo.credit.model.TCkCreditRequest;
import com.guudint.clickargo.credit.model.TCkCreditSummary;
import com.guudint.clickargo.credit.model.TCkMstCreditRequestState;
import com.guudint.clickargo.credit.service.ICreditRequestService;
import com.guudint.clickargo.credit.validator.CreditRequestValidator;
import com.guudint.clickargo.master.dto.CkMstServiceType;
import com.guudint.clickargo.master.enums.CreditState;
import com.guudint.clickargo.master.enums.FormActions;
import com.guudint.clickargo.master.enums.Roles;
import com.guudint.clickargo.master.model.TCkMstCreditState;
import com.guudint.clickargo.master.model.TCkMstServiceType;
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
import com.vcc.camelone.locale.dto.CoreMstLocale;
import com.vcc.camelone.master.dto.MstAccnType;
import com.vcc.camelone.master.dto.MstCurrency;
import com.vcc.camelone.master.model.TMstCurrency;

public class CkCreditRequestServiceImpl extends AbstractClickCargoEntityService<TCkCreditRequest, String, CkCreditRequest> implements ICreditRequestService {

    private static Logger LOG = Logger.getLogger(CkCreditRequestServiceImpl.class);
    private static String AUDIT_TAG = "CK CREDIT REQUEST";
    private static String TABLE_NAME = "T_CK_CREDIT_REQUEST";
    private static final String PREFIX_KEY = "CKCRU";
    private static String HISTORY = "history";
    private static String DEFAULT = "default";

    @Autowired
    private CkCreditDao ckCreditDao;
    @Autowired
    private CkCreditRequestDao ckCreditRequestDao;
    @Autowired
    private CkCreditSummaryDao ckCreditSummaryDao;

    @Autowired
    private CreditRequestValidator creditRequestValidator;
    
    @Autowired
    @Qualifier("creditRequestWorkflowService")
    private ICkWorkflowService<TCkCreditRequest, CkCreditRequest> creditRequestWorkflowService;
    
    @Autowired
	protected ApplicationEventPublisher eventPublisher;

    public CkCreditRequestServiceImpl() {
        super("ckCreditRequestDao", AUDIT_TAG, TCkCreditRequest.class.getName(), TABLE_NAME);
    }

    @Override
    public CkCreditRequest newObj(Principal principal)
            throws ParameterException, EntityNotFoundException, ProcessingException {
        return new CkCreditRequest();
    }

    @Override
    @Transactional
    public CkCreditRequest deleteById(String id, Principal principal)
            throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException {
        LOG.debug("deleteById");
        if (StringUtils.isBlank(id)) {
            throw new ParameterException("param id null or empty");
        }
        try {
            TCkCreditRequest tCkCreditRequest = dao.find(id);
            if (tCkCreditRequest != null) {
                dao.remove(tCkCreditRequest);
                return dtoFromEntity(tCkCreditRequest);
            }
        } catch (Exception e) {
            LOG.error("deleteById", e);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CkCreditRequest> filterBy(EntityFilterRequest filterRequest)
            throws ParameterException, EntityNotFoundException, ProcessingException {
        LOG.debug("filterBy");
        if (filterRequest == null) {
            throw new ParameterException("param filterRequest null");
        }
        CkCreditRequest ckCreditRequest = whereDto(filterRequest);
        if (ckCreditRequest == null) {
            throw new ProcessingException("whereDto null result");
        }
        filterRequest.setTotalRecords(countByAnd(ckCreditRequest));
        List<CkCreditRequest> ckCreditRequests = new ArrayList<>();
        try {
            String select = "select o from TCkCreditRequest o ";
            if (Optional.ofNullable(ckCreditRequest.getCreditLimit()).orElse(BigDecimal.ZERO)
                    .compareTo(BigDecimal.ZERO) > 0) {
                select += "TCkCredit t on t.TCkMstServiceType.svctId = o.TCkMstServiceType.svctId and t.TCoreAccn.accnId = o.TCoreAccn.accnId ";
            }
            String orderClause = formatOrderBy(filterRequest.getOrderBy().toString());
            List<TCkCreditRequest> tCkCreditRequests = findEntitiesByAnd(ckCreditRequest, select, orderClause,
                    filterRequest.getDisplayLength(), filterRequest.getDisplayStart());
            for (TCkCreditRequest tCkCreditRequest : tCkCreditRequests) {
                CkCreditRequest dto = dtoFromEntity(tCkCreditRequest);
                if (dto != null) {
                    ckCreditRequests.add(dto);
                }
            }
        } catch (Exception e) {
            LOG.error("filterBy", e);
        }
        return ckCreditRequests;
    }

    @Override
    @Transactional(readOnly = true)
    public CkCreditRequest findById(String id)
            throws ParameterException, EntityNotFoundException, ProcessingException {
        LOG.debug("findById");
        if (StringUtils.isBlank(id)) {
            throw new ParameterException("param id null or empty");
        }
        try {
            TCkCreditRequest tCkCreditRequest = dao.find(id);
            if (tCkCreditRequest == null) {
                throw new EntityNotFoundException("findById -> id:" + id);
            }
            initEnity(tCkCreditRequest);
            return dtoFromEntity(tCkCreditRequest);
        } catch (Exception e) {
            LOG.error("findById" + e);
        }
        return null;
    }

    @Override
    protected void initBusinessValidator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initBusinessValidator'");
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }

    @Override
    @Transactional
    protected CkCreditRequest dtoFromEntity(TCkCreditRequest tCkCreditRequest)
            throws ParameterException, ProcessingException {
        LOG.debug("dtoFromEntity");
        if (tCkCreditRequest == null) {
            throw new ParameterException("param entity null");
        }
        CkCreditRequest ckCreditRequest = new CkCreditRequest(tCkCreditRequest);
        if (tCkCreditRequest.getTCkMstCreditRequestState() != null) {
            ckCreditRequest.setTCkMstCreditRequestState(
                    new CkMstCreditRequestState(tCkCreditRequest.getTCkMstCreditRequestState()));
        }
        if (tCkCreditRequest.getTCkMstServiceType() != null) {
            ckCreditRequest.setTCkMstServiceType(new CkMstServiceType(tCkCreditRequest.getTCkMstServiceType()));
        }
        if (tCkCreditRequest.getTCoreAccn() != null) {
            ckCreditRequest.setTCoreAccn(new CoreAccn(tCkCreditRequest.getTCoreAccn()));
        }
        if (tCkCreditRequest.getTCoreAccn().getTMstAccnType() != null) {
            ckCreditRequest.getTCoreAccn().setTMstAccnType(new MstAccnType(tCkCreditRequest.getTCoreAccn().getTMstAccnType()));
        }
        if (tCkCreditRequest.getTMstCurrency() != null) {
            ckCreditRequest.setTMstCurrency(new MstCurrency(tCkCreditRequest.getTMstCurrency()));
        }
        TCkCredit tCkCredit = ckCreditDao.getByServiceTypeAndAccnAndCcy(ckCreditRequest.getTCkMstServiceType(),
                ckCreditRequest.getTCoreAccn(), ckCreditRequest.getTMstCurrency());
        if (tCkCredit != null) {
            ckCreditRequest.setCreditLimit(tCkCredit.getCrAmt());
        }
        return ckCreditRequest;
    }

    @Override
    protected TCkCreditRequest entityFromDTO(CkCreditRequest ckCreditRequest)
            throws ParameterException, ProcessingException {
        LOG.debug("entityFromDto");
        if (ckCreditRequest == null) {
            throw new ParameterException("param dto null");
        }
        TCkCreditRequest tCkCreditRequest = new TCkCreditRequest(ckCreditRequest);
        if (ckCreditRequest.getTCkMstCreditRequestState() != null) {
            tCkCreditRequest.setTCkMstCreditRequestState(
                    ckCreditRequest.getTCkMstCreditRequestState().toEntity(new TCkMstCreditRequestState()));
        }
        if (ckCreditRequest.getTCkMstServiceType() != null) {
            tCkCreditRequest
                    .setTCkMstServiceType(ckCreditRequest.getTCkMstServiceType().toEntity(new TCkMstServiceType()));
        }
        if (ckCreditRequest.getTCoreAccn() != null) {
            tCkCreditRequest.setTCoreAccn(ckCreditRequest.getTCoreAccn().toEntity(new TCoreAccn()));
        }
        if (ckCreditRequest.getTMstCurrency() != null) {
            tCkCreditRequest.setTMstCurrency(ckCreditRequest.getTMstCurrency().toEntity(new TMstCurrency()));
        }
        return tCkCreditRequest;
    }

    @Override
    protected String entityKeyFromDTO(CkCreditRequest ckCreditRequest) throws ParameterException, ProcessingException {
        LOG.debug("entityKeyFromDTO");
        if (ckCreditRequest == null) {
            throw new ParameterException("dto param null");
        }
        return ckCreditRequest.getCruId();
    }

    @Override
    protected CoreMstLocale getCoreMstLocale(CkCreditRequest ckCreditRequest)
            throws ParameterException, EntityNotFoundException, ProcessingException {
        if (ckCreditRequest == null) {
            throw new ParameterException("dto param null");
        }
        if (ckCreditRequest.getCoreMstLocale() == null) {
            throw new ProcessingException("coreMstLocale null");
        }
        return ckCreditRequest.getCoreMstLocale();
    }

    @Override
    protected HashMap<String, Object> getParameters(CkCreditRequest ckCreditRequest)
            throws ParameterException, ProcessingException {
        LOG.debug("getParameters");
        if (ckCreditRequest == null) {
            throw new ParameterException("param dto null");
        }
        HashMap<String, Object> parameters = new HashMap<>();
        if (StringUtils.isNotBlank(ckCreditRequest.getCruId())) {
            parameters.put("cruId", "%" + ckCreditRequest.getCruId() + "%");
        }
        if (ckCreditRequest.getTCkMstServiceType() != null) {
            CkMstServiceType ckMstServiceType = ckCreditRequest.getTCkMstServiceType();
            if (StringUtils.isNotBlank(ckMstServiceType.getSvctName())) {
                parameters.put("cruServiceType", "%" + ckMstServiceType.getSvctName() + "%");
            }
        }
        if (ckCreditRequest.getTCoreAccn() != null) {
            if (StringUtils.isNotBlank(ckCreditRequest.getTCoreAccn().getAccnId())) {
                parameters.put("cruCompanyId", "%" + ckCreditRequest.getTCoreAccn().getAccnId() + "%");
            }
            if (StringUtils.isNotBlank(ckCreditRequest.getTCoreAccn().getAccnName())) {
                parameters.put("cruCompanyName", "%" + ckCreditRequest.getTCoreAccn().getAccnName() + "%");
            }
        }
        if (Optional.ofNullable(ckCreditRequest.getCruAmt()).orElse(BigDecimal.ZERO).compareTo(BigDecimal.ZERO) > 0) {
            parameters.put("cruAmt", ckCreditRequest.getCruAmt());
        }
        if (Optional.ofNullable(ckCreditRequest.getCreditLimit()).orElse(BigDecimal.ZERO)
                .compareTo(BigDecimal.ZERO) > 0) {
            parameters.put("creditLimit", ckCreditRequest.getCreditLimit());
        }
        if (ckCreditRequest.getTMstCurrency() != null) {
            if (StringUtils.isNotBlank(ckCreditRequest.getTMstCurrency().getCcyCode())) {
                parameters.put("cruCcy", ckCreditRequest.getTMstCurrency().getCcyCode());
            }
        }

        if (StringUtils.isNotBlank(ckCreditRequest.getTCkMstCreditRequestState().getStId())) {
			String[] states = ckCreditRequest.getTCkMstCreditRequestState().getStId().split(",");
			parameters.put("cruState", Arrays.asList(states));
		} else {
			
			Principal principal = principalUtilService.getPrincipal();
			if (principal == null)
				throw new ProcessingException("principal null");
			
			Optional<List<String>> opAuthRoles = Optional.ofNullable(principal.getRoleList());
			if (!opAuthRoles.isPresent())
				throw new ProcessingException("principal roles null or empty");
			
			// only proceed if it's SP
			if (opAuthRoles.isPresent() && opAuthRoles.get().contains(Roles.SP_L1.name())) {
				if (StringUtils.isNotBlank(ckCreditRequest.getHistory())
						&& ckCreditRequest.getHistory().equalsIgnoreCase(DEFAULT)) {
					parameters.put("cruState",
							Arrays.asList(CreditRequestState.NEW.getCode(), CreditRequestState.SUBMIT.getCode()));
				} else if (StringUtils.isNotBlank(ckCreditRequest.getHistory())
						&& ckCreditRequest.getHistory().equalsIgnoreCase(HISTORY)) {
					parameters.put("cruState", Arrays.asList(CreditRequestState.REJECT.getCode(),
							CreditRequestState.DELETE.getCode(), CreditRequestState.APPROVE.getCode()));
				}
			} else if (opAuthRoles.isPresent() && opAuthRoles.get().contains(Roles.SP_FIN_HD.name())) {
				if (StringUtils.isNotBlank(ckCreditRequest.getHistory())
						&& ckCreditRequest.getHistory().equalsIgnoreCase(DEFAULT)) {
					parameters.put("cruState",
							Arrays.asList(CreditRequestState.SUBMIT.getCode()));
				} else if (StringUtils.isNotBlank(ckCreditRequest.getHistory())
						&& ckCreditRequest.getHistory().equalsIgnoreCase(HISTORY)) {
					parameters.put("cruState", Arrays.asList(CreditRequestState.REJECT.getCode(), CreditRequestState.APPROVE.getCode()));
				}
			}
		}
		  
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if (ckCreditRequest.getCruDtCreate() != null) {
            parameters.put("cruDtCreate", sdf.format(ckCreditRequest.getCruDtCreate()));
        }
        if (ckCreditRequest.getCruDtSubmitted() != null) {
            parameters.put("cruDtSubmitted", sdf.format(ckCreditRequest.getCruDtSubmitted()));
        }
        if (StringUtils.isNotBlank(ckCreditRequest.getCruUidCreate())) {
            parameters.put("cruUidCreate", "%" + ckCreditRequest.getCruUidCreate() + "%");
        }
        if (StringUtils.isNotBlank(ckCreditRequest.getCruUidSubmitted())) {
            parameters.put("cruUidSubmitted", "%" + ckCreditRequest.getCruUidSubmitted() + "%");
        }

        return parameters;
    }

    @Override
    protected String getWhereClause(CkCreditRequest ckCreditRequest, boolean wherePrinted)
            throws ParameterException, ProcessingException {
        LOG.debug("getWhereClause");
        String EQUAL = " = :", CONTAIN = " like :", IN = " in (:%s)";
        if (ckCreditRequest == null) {
            throw new ParameterException("param dto null");
        }
        StringBuffer condition = new StringBuffer();
        if (StringUtils.isNotBlank(ckCreditRequest.getCruId())) {
            condition.append(getOperator(wherePrinted) + "o.cruId" + CONTAIN + "cruId");
            wherePrinted = true;
        }
        if (ckCreditRequest.getTCkMstServiceType() != null) {
            CkMstServiceType ckMstServiceType = ckCreditRequest.getTCkMstServiceType();
            if (StringUtils.isNotBlank(ckMstServiceType.getSvctName())) {
                condition.append(getOperator(wherePrinted) + "o.TCkMstServiceType.svctName" + CONTAIN + "cruServiceType");
                wherePrinted = true;
            }
        }
        if (ckCreditRequest.getTCoreAccn() != null) {
            if (StringUtils.isNotBlank(ckCreditRequest.getTCoreAccn().getAccnId())) {
                condition.append(getOperator(wherePrinted) + "o.TCoreAccn.accnId" + CONTAIN + "cruCompanyId");
                wherePrinted = true;
            }
            if (StringUtils.isNotBlank(ckCreditRequest.getTCoreAccn().getAccnName())) {
                condition.append(getOperator(wherePrinted) + "o.TCoreAccn.accnName" + CONTAIN + "cruCompanyName");
                wherePrinted = true;
            }
        }
        if (Optional.ofNullable(ckCreditRequest.getCruAmt()).orElse(BigDecimal.ZERO).compareTo(BigDecimal.ZERO) > 0) {
            condition.append(getOperator(wherePrinted) + "o.cruAmt" + EQUAL + "cruAmt");
            wherePrinted = true;
        }
        if (Optional.ofNullable(ckCreditRequest.getCreditLimit()).orElse(BigDecimal.ZERO)
                .compareTo(BigDecimal.ZERO) > 0) {
            condition.append(getOperator(wherePrinted) + "t.crAmt" + EQUAL + "creditLimit");
            wherePrinted = true;
        }
        if (ckCreditRequest.getTMstCurrency() != null) {
            if (StringUtils.isNotBlank(ckCreditRequest.getTMstCurrency().getCcyCode())) {
                condition.append(getOperator(wherePrinted) + "o.TMstCurrency.ccyCode" + EQUAL + "cruCcy");
                wherePrinted = true;
            }
        }

        if (ckCreditRequest.getCruDtCreate() != null) {
            condition.append(
                    getOperator(wherePrinted) + "DATE_FORMAT(o.cruDtCreate,'%d/%m/%Y')" + EQUAL + "cruDtCreate");
            wherePrinted = true;
        }
        if (ckCreditRequest.getCruDtSubmitted() != null) {
            condition.append(
                    getOperator(wherePrinted) + "DATE_FORMAT(o.cruDtSubmitted,'%d/%m/%Y')" + EQUAL + "cruDtSubmitted");
            wherePrinted = true;
        }
        if (StringUtils.isNotBlank(ckCreditRequest.getCruUidCreate())) {
            condition.append(
                    getOperator(wherePrinted) + "o.cruUidCreate" + CONTAIN + "cruUidCreate");
            wherePrinted = true;
        }
        if (StringUtils.isNotBlank(ckCreditRequest.getCruUidSubmitted())) {
            condition.append(
            		getOperator(wherePrinted) + "o.cruUidSubmitted" + CONTAIN + "cruUidSubmitted");
            wherePrinted = true;
        }

        condition.append(getOperator(wherePrinted) + "o.TCkMstCreditRequestState.stId" + String.format(IN, "cruState"));

        return condition.toString();
    }

    @Override
    protected TCkCreditRequest initEnity(TCkCreditRequest tCkCreditRequest)
            throws ParameterException, ProcessingException {
        LOG.debug("initEntity");
        if (tCkCreditRequest != null) {
            Hibernate.initialize(tCkCreditRequest.getTCkMstCreditRequestState());
            Hibernate.initialize(tCkCreditRequest.getTCkMstServiceType());
            Hibernate.initialize(tCkCreditRequest.getTCoreAccn());
            Hibernate.initialize(tCkCreditRequest.getTCoreAccn().getTMstAccnType());
            Hibernate.initialize(tCkCreditRequest.getTMstCurrency());
        }
        return tCkCreditRequest;
    }

    @Override
    protected CkCreditRequest preSaveUpdateDTO(TCkCreditRequest tCkCreditRequest, CkCreditRequest ckCreditRequest)
            throws ParameterException, ProcessingException {
        LOG.debug("preSaveUpdateDTO");
        if (tCkCreditRequest == null) {
            throw new ParameterException("param entity null");
        }
        if (ckCreditRequest == null) {
            throw new ParameterException("param dto null");
        }
        ckCreditRequest.setCruDtCreate(tCkCreditRequest.getCruDtCreate());
        ckCreditRequest.setCruUidCreate(tCkCreditRequest.getCruUidCreate());
        return ckCreditRequest;
    }

    @Override
    protected void preSaveValidation(CkCreditRequest arg0, Principal arg1)
            throws ParameterException, ProcessingException {

    }

    @Override
    protected ServiceStatus preUpdateValidation(CkCreditRequest arg0, Principal arg1)
            throws ParameterException, ProcessingException {
        return null;
    }

    @Override
    protected CkCreditRequest setCoreMstLocale(CoreMstLocale arg0, CkCreditRequest arg1)
            throws ParameterException, EntityNotFoundException, ProcessingException {
        return null;
    }

    @Override
    protected TCkCreditRequest updateEntity(ACTION action, TCkCreditRequest tCkCreditRequest, Principal principal,
            Date date)
            throws ParameterException, ProcessingException {
        LOG.debug("updateEntity");
        if (tCkCreditRequest == null) {
            throw new ParameterException("param entity null");
        }
        if (principal == null) {
            throw new ParameterException("param principal null");
        }
        if (date == null) {
            throw new ParameterException("param date null");
        }
        Optional<String> opUserId = Optional.ofNullable(principal.getUserId());
        switch (action) {
            case CREATE:
                tCkCreditRequest.setCruId(CkUtil.generateId(PREFIX_KEY));
                tCkCreditRequest.setCruStatus(Constant.ACTIVE_STATUS);
                tCkCreditRequest.setCruUidCreate(opUserId.isPresent() ? opUserId.get() : Constant.DEFAULT_USR);
                tCkCreditRequest.setCruDtCreate(date);
                tCkCreditRequest.setCruDtLupd(date);
                tCkCreditRequest.setCruUidLupd(opUserId.isPresent() ? opUserId.get() : Constant.DEFAULT_USR);
                break;

            case MODIFY:
                tCkCreditRequest.setCruDtLupd(date);
                tCkCreditRequest.setCruUidLupd(opUserId.isPresent() ? opUserId.get() : Constant.DEFAULT_USR);
                break;

            default:
                break;
        }
        return tCkCreditRequest;
    }

    @Override
    protected TCkCreditRequest updateEntityStatus(TCkCreditRequest tCkCreditRequest, char status)
            throws ParameterException, ProcessingException {
        LOG.debug("updateEntityStatus");
        if (tCkCreditRequest == null) {
            throw new ParameterException("entity param null");
        }
        tCkCreditRequest.setCruStatus(status);
        return tCkCreditRequest;
    }

    @Override
    protected CkCreditRequest whereDto(EntityFilterRequest filterRequest)
            throws ParameterException, ProcessingException {
        LOG.debug("whereDto");
        if (filterRequest == null) {
            throw new ParameterException("param filterRequest null");
        }
        CkCreditRequest ckCreditRequest = new CkCreditRequest();
        CoreAccn coreAccn = new CoreAccn();
        CkMstCreditRequestState ckMstCreditRequestState = new CkMstCreditRequestState();
        CkMstServiceType ckMstServiceType = new CkMstServiceType();
        MstCurrency mstCurrency = new MstCurrency();
        ckCreditRequest.setTCkMstCreditRequestState(ckMstCreditRequestState);
        ckCreditRequest.setTCkMstServiceType(ckMstServiceType);
        ckCreditRequest.setTCoreAccn(coreAccn);
        ckCreditRequest.setTMstCurrency(mstCurrency);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (EntityWhere entityWhere : filterRequest.getWhereList()) {
            if (entityWhere == null) {
                continue;
            }
            if ("cruId".equalsIgnoreCase(entityWhere.getAttribute())) {
                ckCreditRequest.setCruId(entityWhere.getValue());
            } else if ("TCkMstServiceType.svctId".equalsIgnoreCase(entityWhere.getAttribute())) {
                ckMstServiceType.setSvctId(entityWhere.getValue());
            } else if ("TCoreAccn.accnId".equalsIgnoreCase(entityWhere.getAttribute())) {
                coreAccn.setAccnId(entityWhere.getValue());
            } else if ("TCoreAccn.accnName".equalsIgnoreCase(entityWhere.getAttribute())) {
                coreAccn.setAccnName(entityWhere.getValue());
            } else if ("cruAmt".equalsIgnoreCase(entityWhere.getAttribute())) {
                BigDecimal value = new BigDecimal(Optional.ofNullable(entityWhere.getValue()).orElse("0"));
                ckCreditRequest.setCruAmt(value);
            } else if ("crAmt".equalsIgnoreCase(entityWhere.getAttribute())) {
                BigDecimal value = new BigDecimal(Optional.ofNullable(entityWhere.getValue()).orElse("0"));
                ckCreditRequest.setCreditLimit(value);
            } else if ("TMstCurrency.ccyCode".equalsIgnoreCase(entityWhere.getAttribute())) {
                mstCurrency.setCcyCode(entityWhere.getValue());
            } else if ("TCkMstCreditRequestState.stId".equalsIgnoreCase(entityWhere.getAttribute())) {
                ckMstCreditRequestState.setStId(entityWhere.getValue());
            } else if ("cruDtCreate".equalsIgnoreCase(entityWhere.getAttribute())) {
                try {
                    ckCreditRequest.setCruDtCreate(sdf.parse(entityWhere.getValue()));
                } catch (ParseException e) {
                    LOG.error(e);
                }
            } else if ("cruDtSubmitted".equalsIgnoreCase(entityWhere.getAttribute())) {
                try {
                    ckCreditRequest.setCruDtSubmitted(sdf.parse(entityWhere.getValue()));
                } catch (ParseException e) {
                    LOG.error(e);
                }
            } else if ("cruUidCreate".equalsIgnoreCase(entityWhere.getAttribute())) {
                ckCreditRequest.setCruUidCreate(entityWhere.getValue());
            } else if ("cruUidSubmitted".equalsIgnoreCase(entityWhere.getAttribute())) {
                ckCreditRequest.setCruUidSubmitted(entityWhere.getValue());
            } else if ("history".equals(entityWhere.getAttribute())) {
                ckCreditRequest.setHistory(entityWhere.getValue());
            } else if("history".equalsIgnoreCase(entityWhere.getAttribute())) {
            	ckCreditRequest.setHistory(entityWhere.getValue());
            }
        }
        return ckCreditRequest;
    }

    private String formatOrderBy(String attribute) throws Exception {
        attribute = Optional.ofNullable(attribute).orElse("");
        return attribute;
    }

    @Override
    @Transactional
    public Object addObj(Object object, Principal principal)
            throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException {
        CkCreditRequest ckCreditRequest = (CkCreditRequest) object;
        List<ValidationError> validationErrors = creditRequestValidator.validateCreate(ckCreditRequest, principal);
        if (!validationErrors.isEmpty()) {
            throw new ValidationException(super.validationErrorMap(validationErrors));
        } else {
            return super.add(ckCreditRequest, principal);
        }
    }

    @Override
    @Transactional
    public CkCreditRequest update(CkCreditRequest ckCreditRequest, Principal principal)
            throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException {
        Map<String, FormActions> stateMap = new HashMap<>();
        stateMap.put("NEW", FormActions.NEW);
        stateMap.put("SUB", FormActions.SUBMIT);
        stateMap.put("APP", FormActions.APPROVE);
        stateMap.put("REJ", FormActions.REJECT);
        stateMap.put("DEL", FormActions.DELETE);
        FormActions formActions = stateMap.get(ckCreditRequest.getTCkMstCreditRequestState().getStId());
        try {
            creditRequestWorkflowService.moveState(formActions, ckCreditRequest, principal, null);
            publishPostEvents(ckCreditRequest);
            return super.update(ckCreditRequest, principal);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new ProcessingException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Object updateObj(Object object, Principal principal)
            throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException {
        CkCreditRequest ckCreditRequest = (CkCreditRequest) object;
        List<ValidationError> validationErrors = creditRequestValidator.validateUpdate(ckCreditRequest, principal);
        if (!validationErrors.isEmpty()) {
            throw new ValidationException(super.validationErrorMap(validationErrors));
        } else {
            return update(ckCreditRequest, principal);
        }
    }

    public enum CreditRequestState {

        NEW("NEW"), SUBMIT("SUB"), APPROVE("APP"), REJECT("REJ"), DELETE("DEL"), ACTIVE("ACT");

        private String code;

        CreditRequestState(String code) {
            this.code = code;
        }

        public String getCode() {
            return this.code;
        }
    }

    @Override
    @Transactional
    public List<CkCreditRequest> getQueueToActive(Date startDate) {
        List<CkCreditRequest> ckCreditRequests = new ArrayList<>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            
            List<TCkCreditRequest> tCkCreditRequests = ckCreditRequestDao
                    .findByStateAndStartDt(CreditRequestState.APPROVE.getCode(), sdf.format(startDate));
            
            for (TCkCreditRequest tCkCreditRequest : tCkCreditRequests) {
                initEnity(tCkCreditRequest);
                CkCreditRequest ckCreditRequest = dtoFromEntity(tCkCreditRequest);
                ckCreditRequests.add(ckCreditRequest);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return ckCreditRequests;
    }

    @Override
    @Transactional
    public void setActiveCreditLimit(CkCreditRequest ckCreditRequest) throws ProcessingException, Exception {

        Date now = new Date();
        
    	// TCkCredit
        TCkCredit tckCredit = ckCreditDao.findByServiceTypeAndAccn(
                ckCreditRequest.getTCkMstServiceType().getSvctId(), ckCreditRequest.getTCoreAccn().getAccnId());
        
        BigDecimal oldBalance = BigDecimal.ZERO;
        
        if (tckCredit != null) {
            if (ckCreditRequest.getCruAmt().compareTo(tckCredit.getCrAmt()) <= 0) {
            	//throw new Exception();
            	// return ;
            }
            oldBalance = tckCredit.getCrAmt();
            
        } else {
        	// create new credit limit
        	tckCredit = new TCkCredit(CkUtil.generateId(TCkCredit.PREFIX_ID)
        			, new TCkMstServiceType(ckCreditRequest.getTCkMstServiceType().getSvctId(), null)
        			, new TCoreAccn(ckCreditRequest.getTCoreAccn().getAccnId(),null,'A',null));
        	
        	tckCredit.setTCkMstCreditState(new TCkMstCreditState(CreditState.APPROVED.name(),null));
        	tckCredit.setCrStatus(Constant.ACTIVE_STATUS);
        	tckCredit.setCrDtCreate(now);
        	tckCredit.setCrUidCreate("SYS");
        }
        
        tckCredit.setCrAmt(ckCreditRequest.getCruAmt());
        tckCredit.setCrDtStart(ckCreditRequest.getCruDtStart());
        tckCredit.setCrDtEnd(ckCreditRequest.getCruDtEnd());
        tckCredit.setCrDtLupd(now);
        tckCredit.setCrUidLupd("SYS");

        // TCkCreditSummary
        TCkCreditSummary tCkCreditSummary = ckCreditSummaryDao.getByServiceTypeAndAccnAndCcy(
                ckCreditRequest.getTCkMstServiceType(), ckCreditRequest.getTCoreAccn(),
                ckCreditRequest.getTMstCurrency());
        
        if (tCkCreditSummary != null) {
        	
            if (ckCreditRequest.getCruAmt().compareTo(tCkCreditSummary.getCrsBalance()) <= 0) {
            	//throw new Exception();
            	// return ;
            }
        	
            BigDecimal subtractAmt = ckCreditRequest.getCruAmt().subtract(oldBalance);
            
            tCkCreditSummary.setCrsAmt(ckCreditRequest.getCruAmt());
            tCkCreditSummary.setCrsBalance(tCkCreditSummary.getCrsBalance().add(subtractAmt));
            tCkCreditSummary.setCrsDtLupd(now);
            tCkCreditSummary.setCrsUidLupd("SYS");
        } else {
        	
        	tCkCreditSummary = new TCkCreditSummary(CkUtil.generateId(TCkCredit.PREFIX_ID)
        			, new TCkMstServiceType(ckCreditRequest.getTCkMstServiceType().getSvctId(), null)
        			, new TCoreAccn(ckCreditRequest.getTCoreAccn().getAccnId(),null,'A',null));

            tCkCreditSummary.setCrsAmt(ckCreditRequest.getCruAmt());
            tCkCreditSummary.setCrsBalance(ckCreditRequest.getCruAmt());
            tCkCreditSummary.setTMstCurrency(new TMstCurrency(ckCreditRequest.getTMstCurrency().getCcyCode() , null, 'A'));
            
            tCkCreditSummary.setCrsStatus(Constant.ACTIVE_STATUS);
            tCkCreditSummary.setCrsDtLupd(now);
            tCkCreditSummary.setCrsUidLupd("SYS");
        }

        // Update Request to ACTIVE Status
        ckCreditRequest.getTCkMstCreditRequestState().setStId(CreditRequestState.ACTIVE.getCode());
        
        ckCreditRequestDao.update(entityFromDTO(ckCreditRequest));
        ckCreditDao.update(tckCredit);
        ckCreditSummaryDao.update(tCkCreditSummary);
    }
    
    private void publishPostEvents(CkCreditRequest dto) throws Exception {

		ApplicationEvent appEvent = null;
		
		if (dto.getTCkMstCreditRequestState().getStId().equals(CreditRequestState.SUBMIT.getCode())) {
			appEvent = new SubmitEvent<TCkCreditRequest, CkCreditRequest>(this, WorkflowTypeEnum.CREDIT_LIMIT_UPDATE, dto);

		} else if (dto.getTCkMstCreditRequestState().getStId().equals(CreditRequestState.APPROVE.getCode())) {
			appEvent = new ApproveEvent<TCkCreditRequest, CkCreditRequest>(this, WorkflowTypeEnum.CREDIT_LIMIT_UPDATE, dto);

		} else if (dto.getTCkMstCreditRequestState().getStId().equals(CreditRequestState.REJECT.getCode())) {
			appEvent = new RejectEvent<TCkCreditRequest, CkCreditRequest>(this, WorkflowTypeEnum.CREDIT_LIMIT_UPDATE, dto);

		} 
		if( null != appEvent) {
			//appEvent is null for Update action.
			eventPublisher.publishEvent(appEvent);
		}
	}
}
