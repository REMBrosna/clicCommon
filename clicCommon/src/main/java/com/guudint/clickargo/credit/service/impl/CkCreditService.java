package com.guudint.clickargo.credit.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.common.CkUtil;
import com.guudint.clickargo.credit.dao.CkCreditDao;
import com.guudint.clickargo.credit.dto.CkCredit;
import com.guudint.clickargo.credit.dto.CkCreditSummary;
import com.guudint.clickargo.credit.model.TCkCredit;
import com.guudint.clickargo.credit.model.TCkCreditSummary;
import com.guudint.clickargo.credit.service.AbstractCreditService;
import com.guudint.clickargo.master.dto.CkMstCreditState;
import com.guudint.clickargo.master.dto.CkMstServiceType;
import com.guudint.clickargo.master.enums.CreditState;
import com.guudint.clickargo.master.model.TCkMstCreditState;
import com.guudint.clickargo.master.model.TCkMstServiceType;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.ccm.dto.CoreUsr;
import com.vcc.camelone.ccm.model.TCoreAccn;
import com.vcc.camelone.ccm.model.TCoreUsr;
import com.vcc.camelone.ccm.util.Constant;
import com.vcc.camelone.common.controller.entity.EntityFilterRequest;
import com.vcc.camelone.common.controller.entity.EntityWhere;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.exception.ValidationException;
import com.vcc.camelone.common.service.entity.AbstractEntityService.ACTION;
import com.vcc.camelone.master.dto.MstCurrency;
import com.vcc.camelone.master.model.TMstCurrency;

public class CkCreditService extends AbstractCreditService {

    private static Logger log = Logger.getLogger(CkCreditService.class);

    @Autowired
    private GenericDao<TCkMstCreditState, String> ckMstCreditStateDao;

    @Autowired
    private GenericDao<TCoreUsr, String> coreUsrDao;
    @Autowired
    private CkCreditDao ckCreditDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public CkCredit findById(String id)
            throws ParameterException, EntityNotFoundException, ProcessingException, Exception {
        log.debug("findById");
        if (StringUtils.isEmpty(id))
            throw new ParameterException("param id null");

        TCkCredit entity = dao.find(id);
        if (null == entity)
            throw new ProcessingException("entity not found: " + id);
        initEnity(entity);
        CkCredit dto = dtoFromEntity(entity);
        TCkCreditSummary summary = sDao.getByServiceTypeAndAccnAndCcy(dto.getTCkMstServiceType(), dto.getTCoreAccn(),
                dto.getTMstCurrency());
        if (null != summary) {
            CkCreditSummary ckCreditSummary = new CkCreditSummary(summary);
            dto.setTCkCreditSummary(ckCreditSummary);
        }
        return dto;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public List<CkCredit> filterBy(EntityFilterRequest filterRequest)
            throws ParameterException, EntityNotFoundException, ProcessingException, Exception {
        log.debug("filterBy");
        if (null == filterRequest)
            throw new ParameterException("param filterRequest null");

        CkCredit dto = whereDto(filterRequest);
        if (null == dto)
            throw new ProcessingException("param dto null");

        filterRequest.setTotalRecords(countByAnd(dto));
        List<CkCredit> dtos = new ArrayList<>();
        String orderClause = formatOrderByObj(filterRequest.getOrderBy()).toString();
        List<TCkCredit> query = findEntitiesByAnd(dto, "from TCkCredit o ", orderClause,
                filterRequest.getDisplayLength(), filterRequest.getDisplayStart());
        for (TCkCredit entity : query) {
            CkCredit newDto = dtoFromEntity(entity);
            if (null != newDto) {
                TCkCreditSummary summary = sDao.getByServiceTypeAndAccnAndCcy(newDto.getTCkMstServiceType(),
                        newDto.getTCoreAccn(), newDto.getTMstCurrency());
                if (null != summary) {
                    CkCreditSummary ckCreditSummary = new CkCreditSummary(summary);
                    newDto.setTCkCreditSummary(ckCreditSummary);
                }
                dtos.add(newDto);
            }
        }
        return dtos;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    protected CkCredit _createCredit(CkCredit dto, Principal principal)
            throws ParameterException, ValidationException, ProcessingException, Exception {
        log.debug("_createCredit");
        dto.setCrId(CkUtil.generateId());
        TCkMstCreditState tCkMstCreditState = ckMstCreditStateDao.find(CreditState.DRAFT.getDesc());
        if (null == tCkMstCreditState)
            throw new ProcessingException("tckMstCreditState not defined:" + CreditState.DRAFT.getDesc());
        CkMstCreditState ckMstCreditState = new CkMstCreditState(tCkMstCreditState);
        dto.setTCkMstCreditState(ckMstCreditState);
        dto.setCrStatus(Constant.ACTIVE_STATUS);
        return add(dto, principal);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    protected CkCredit _verifyCredit(CkCredit dto, String id, Principal principal)
            throws ParameterException, ValidationException, ProcessingException, Exception {
        log.debug("_verifyCredit");
        CkCredit findById = findById(id);
        if (null == findById)
            throw new ProcessingException("tckCredit not defined:" + id);

        Date now = Calendar.getInstance().getTime();

        TCkMstCreditState tCkMstCreditState = ckMstCreditStateDao.find(CreditState.VERIFY.getDesc());
        if (null == tCkMstCreditState)
            throw new ProcessingException("tckMstCreditState not defined:" + CreditState.VERIFY.getDesc());
        CkMstCreditState ckMstCreditState = new CkMstCreditState(tCkMstCreditState);
        dto.setTCkMstCreditState(ckMstCreditState);
        TCoreUsr tCoreUsr = coreUsrDao.find(principal.getUserId());
        if (null == tCoreUsr)
            throw new ProcessingException("tcoreUsr not defined:" + principal.getUserId());
        CoreUsr coreUsr = new CoreUsr(tCoreUsr);
        dto.setTCoreUsrVerify(coreUsr);
        dto.setCrDtVerify(now);
        return update(dto, principal);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    protected CkCredit _approveCredit(CkCredit dto, String id, Principal principal)
            throws ParameterException, ValidationException, ProcessingException, Exception {
        log.debug("_approveCredit");
        CkCredit findById = findById(id);
        if (null == findById)
            throw new ProcessingException("tckCredit not defined:" + id);

        Date now = Calendar.getInstance().getTime();

        TCkMstCreditState tCkMstCreditState = ckMstCreditStateDao.find(CreditState.APPROVED.getDesc());
        if (null == tCkMstCreditState)
            throw new ProcessingException("tckMstCreditState not defined:" + CreditState.APPROVED.getDesc());
        CkMstCreditState ckMstCreditState = new CkMstCreditState(tCkMstCreditState);
        dto.setTCkMstCreditState(ckMstCreditState);
        TCoreUsr tCoreUsr = coreUsrDao.find(principal.getUserId());
        if (null == tCoreUsr)
            throw new ProcessingException("tcoreUsr not defined:" + principal.getUserId());
        CoreUsr coreUsr = new CoreUsr(tCoreUsr);
        dto.setTCoreUsrApprove(coreUsr);
        dto.setCrDtApprove(now);

        TCkCreditSummary summary = new TCkCreditSummary();
        summary.setCrsId(CkUtil.generateId());
        summary.setTCkMstServiceType(dto.getTCkMstServiceType().toEntity(new TCkMstServiceType()));
        summary.setTCoreAccn(dto.getTCoreAccn().toEntity(new TCoreAccn()));
        summary.setTMstCurrency(dto.getTMstCurrency().toEntity(new TMstCurrency()));
        summary.setCrsAmt(dto.getCrAmt());
        summary.setCrsReserve(BigDecimal.ZERO);
        summary.setCrsUtilized(BigDecimal.ZERO);
        summary.setCrsBalance(dto.getCrAmt());
        summary.setCrsStatus(Constant.ACTIVE_STATUS);
        summary.setCrsDtCreate(now);
        summary.setCrsUidCreate(principal.getUserId());
        summary.setCrsDtLupd(now);
        summary.setCrsUidLupd(principal.getUserId());
        sDao.add(summary);

        dto.setTCkCreditSummary(new CkCreditSummary(summary));

        return update(dto, principal);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    protected CkCredit _rejectCredit(CkCredit dto, String id, Principal principal)
            throws ParameterException, ValidationException, ProcessingException, Exception {
        log.debug("_rejectCredit");
        CkCredit findById = findById(id);
        if (null == findById)
            throw new ProcessingException("tckCredit not defined:" + id);

        Date now = Calendar.getInstance().getTime();

        TCkMstCreditState tCkMstCreditState = ckMstCreditStateDao.find(CreditState.REJECTED.getDesc());
        if (null == tCkMstCreditState)
            throw new ProcessingException("tckMstCreditState not defined:" + CreditState.REJECTED.getDesc());
        CkMstCreditState ckMstCreditState = new CkMstCreditState(tCkMstCreditState);
        dto.setTCkMstCreditState(ckMstCreditState);
        TCoreUsr tCoreUsr = coreUsrDao.find(principal.getUserId());
        if (null == tCoreUsr)
            throw new ProcessingException("tcoreUsr not defined:" + principal.getUserId());
        CoreUsr coreUsr = new CoreUsr(tCoreUsr);
        dto.setTCoreUsrReject(coreUsr);
        dto.setCrDtReject(now);
        return update(dto, principal);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    protected CkCredit _suspendedCredit(CkCredit dto, String id, Principal principal)
            throws ParameterException, ValidationException, ProcessingException, Exception {
        log.debug("_suspendedCredit");
        CkCredit findById = findById(id);
        if (null == findById)
            throw new ProcessingException("tckCredit not defined:" + id);

        dto.setCrRemarks(dto.getCrRemarks());
        TCkMstCreditState tCkMstCreditState = ckMstCreditStateDao.find(CreditState.SUSPENDED.getDesc());
        if (null == tCkMstCreditState)
            throw new ProcessingException("tckMstCreditState not defined:" + CreditState.SUSPENDED.getDesc());
        CkMstCreditState ckMstCreditState = new CkMstCreditState(tCkMstCreditState);
        dto.setTCkMstCreditState(ckMstCreditState);
        return update(dto, principal);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    protected CkCredit _unsuspendCredit(CkCredit dto, String id, Principal principal)
            throws ParameterException, ValidationException, ProcessingException, Exception {
        log.debug("_unsuspendCredit");
        CkCredit findById = findById(id);
        if (null == findById)
            throw new ProcessingException("tckCredit not defined:" + id);

        Date now = Calendar.getInstance().getTime();

        dto.setCrRemarks(dto.getCrRemarks());
        TCkMstCreditState tCkMstCreditState = ckMstCreditStateDao.find(CreditState.APPROVED.getDesc());
        if (null == tCkMstCreditState)
            throw new ProcessingException("tckMstCreditState not defined:" + CreditState.APPROVED.getDesc());
        CkMstCreditState ckMstCreditState = new CkMstCreditState(tCkMstCreditState);
        dto.setTCkMstCreditState(ckMstCreditState);
        TCoreUsr tCoreUsr = coreUsrDao.find(principal.getUserId());
        if (null == tCoreUsr)
            throw new ProcessingException("tcoreUsr not defined:" + principal.getUserId());
        CoreUsr coreUsr = new CoreUsr(tCoreUsr);
        dto.setTCoreUsrApprove(coreUsr);
        dto.setCrDtApprove(now);
        return update(dto, principal);
    }

    @Override
    protected TCkCredit initEnity(TCkCredit entity) throws ParameterException, ProcessingException, Exception {
        log.debug("initEnity");
        if (null != entity) {
            Optional.ofNullable(entity.getTCkMstServiceType()).ifPresent(x -> Hibernate.initialize(x));
            Optional.ofNullable(entity.getTCkMstCreditState()).ifPresent(x -> Hibernate.initialize(x));
            Optional.ofNullable(entity.getTCoreAccn()).ifPresent(x -> Hibernate.initialize(x));
            Optional.ofNullable(entity.getTMstCurrency()).ifPresent(x -> Hibernate.initialize(x));
            Optional.ofNullable(entity.getTCoreUsrVerify()).ifPresent(x -> Hibernate.initialize(x));
            Optional.ofNullable(entity.getTCoreUsrApprove()).ifPresent(x -> Hibernate.initialize(x));
            Optional.ofNullable(entity.getTCoreUsrReject()).ifPresent(x -> Hibernate.initialize(x));
        }
        return entity;
    }

    @Override
    protected TCkCredit entityFromDTO(CkCredit dto) throws ParameterException, ProcessingException, Exception {
        log.debug("entityFromDTO");
        if (null == dto)
            throw new ParameterException("param dto null");

        TCkCredit entity = dto.toEntity(new TCkCredit());
        Optional.ofNullable(dto.getTCkMstServiceType())
                .ifPresent(x -> entity.setTCkMstServiceType(x.toEntity(new TCkMstServiceType())));
        Optional.ofNullable(dto.getTCkMstCreditState())
                .ifPresent(x -> entity.setTCkMstCreditState(x.toEntity(new TCkMstCreditState())));
        Optional.ofNullable(dto.getTCoreAccn()).ifPresent(x -> entity.setTCoreAccn(x.toEntity(new TCoreAccn())));
        Optional.ofNullable(dto.getTMstCurrency())
                .ifPresent(x -> entity.setTMstCurrency(x.toEntity(new TMstCurrency())));
        Optional.ofNullable(dto.getTCoreUsrVerify())
                .ifPresent(x -> entity.setTCoreUsrVerify(x.toEntity(new TCoreUsr())));
        Optional.ofNullable(dto.getTCoreUsrApprove())
                .ifPresent(x -> entity.setTCoreUsrApprove(x.toEntity(new TCoreUsr())));
        Optional.ofNullable(dto.getTCoreUsrReject())
                .ifPresent(x -> entity.setTCoreUsrReject(x.toEntity(new TCoreUsr())));
        return entity;
    }

    @Override
    protected CkCredit dtoFromEntity(TCkCredit entity) throws ParameterException, ProcessingException, Exception {
        log.debug("dtoFromEntity");
        if (null == entity)
            throw new ParameterException("param entity null");

        CkCredit dto = new CkCredit(entity);
        Optional.ofNullable(entity.getTCkMstServiceType())
                .ifPresent(x -> dto.setTCkMstServiceType(new CkMstServiceType(x)));
        Optional.ofNullable(entity.getTCoreAccn()).ifPresent(x -> dto.setTCoreAccn(new CoreAccn(x)));
        Optional.ofNullable(entity.getTCkMstCreditState())
                .ifPresent(x -> dto.setTCkMstCreditState(new CkMstCreditState(x)));
        Optional.ofNullable(entity.getTMstCurrency()).ifPresent(x -> dto.setTMstCurrency(new MstCurrency(x)));
        Optional.ofNullable(entity.getTCoreUsrVerify()).ifPresent(x -> dto.setTCoreUsrVerify(new CoreUsr(x)));
        Optional.ofNullable(entity.getTCoreUsrApprove()).ifPresent(x -> dto.setTCoreUsrApprove(new CoreUsr(x)));
        Optional.ofNullable(entity.getTCoreUsrReject()).ifPresent(x -> dto.setTCoreUsrReject(new CoreUsr(x)));
        return dto;
    }

    @Override
    protected String entityKeyFromDTO(CkCredit dto) throws ParameterException, ProcessingException, Exception {
        log.debug("entityKeyFromDTO");
        if (null == dto)
            throw new ParameterException("param dto null");
        return dto.getCrId();
    }

    @Override
    protected CkCredit preSaveUpdateDTO(TCkCredit entity, CkCredit dto)
            throws ParameterException, ProcessingException, Exception {
        log.debug("preSaveUpdateDTO");
        if (null == entity)
            throw new ParameterException("param entity null");
        if (null == dto)
            throw new ParameterException("param dto null");

        dto.setCrUidCreate(entity.getCrUidCreate());
        dto.setCrDtCreate(entity.getCrDtCreate());
        return dto;
    }

    @Override
    protected TCkCredit updateEntity(ACTION action, TCkCredit entity, Principal principal, Date date)
            throws ParameterException, ProcessingException, Exception {
        log.debug("updateEntity");
        if (null == entity)
            throw new ParameterException("param tCkDoExt is null or empty");
        if (null == principal)
            throw new ParameterException("param principal is null or empty");
        if (null == date)
            throw new ParameterException("param date is null or empty");

        Optional<String> opUserId = Optional.ofNullable(principal.getUserId());
        switch (action) {
            case CREATE:
                entity.setCrUidCreate(opUserId.isPresent() ? opUserId.get() : Constant.DEFAULT_USR);
                entity.setCrDtCreate(date);
                entity.setCrUidLupd(opUserId.isPresent() ? opUserId.get() : Constant.DEFAULT_USR);
                entity.setCrDtLupd(date);
                break;
            case MODIFY:
                entity.setCrUidLupd(opUserId.isPresent() ? opUserId.get() : Constant.DEFAULT_USR);
                entity.setCrDtLupd(date);
                break;
            default:
                break;
        }
        return entity;
    }

    @Override
    protected String formatOrderBy(String attributes) throws ParameterException, ProcessingException, Exception {
        log.debug("formatOrderBy");
        String attribute = attributes;

        if (StringUtils.contains(attribute, "tckMstServiceType"))
            attribute = attribute.replace("tckMstServiceType", "TCkMstServiceType");

        if (StringUtils.contains(attribute, "tcoreAccn"))
            attribute = attribute.replace("tcoreAccn", "TCoreAccn");

        if (StringUtils.contains(attribute, "tckMstCreditState"))
            attribute = attribute.replace("tckMstCreditState", "TCkMstCreditState");

        if (StringUtils.contains(attribute, "tmstCurrency"))
            attribute = attribute.replace("tmstCurrency", "TMstCurrency");

        if (StringUtils.contains(attribute, "tcoreUsrVerify"))
            attribute = attribute.replace("tcoreUsrVerify", "TCoreUsrVerify");

        if (StringUtils.contains(attribute, "tcoreUsrApprove"))
            attribute = attribute.replace("tcoreUsrApprove", "TCoreUsrApprove");

        if (StringUtils.contains(attribute, "tcoreUsrReject"))
            attribute = attribute.replace("tcoreUsrReject", "TCoreUsrReject");

        return attribute;
    }

    @Override
    protected String getWhereClause(CkCredit dto, boolean wherePrinted)
            throws ParameterException, ProcessingException, Exception {
        log.debug("getWhereClause");
        if (null == dto)
            throw new ParameterException("param dto null");

        StringBuffer condition = new StringBuffer();

        Optional<String> opCrId = Optional.ofNullable(dto.getCrId());
        if (opCrId.isPresent()) {
            condition.append(getOperator(wherePrinted)).append("o.crId = :crId");
            wherePrinted = true;
        }

        Optional<BigDecimal> opCrAmt = Optional.ofNullable(dto.getCrAmt());
        if (opCrAmt.isPresent()) {
            condition.append(getOperator(wherePrinted)).append("o.crAmt = :crAmt");
            wherePrinted = true;
        }

        Optional<BigDecimal> opCrTxnCap = Optional.ofNullable(dto.getCrTxnCap());
        if (opCrTxnCap.isPresent()) {
            condition.append(getOperator(wherePrinted)).append("o.crTxnCap = :crTxnCap");
            wherePrinted = true;
        }

        Optional<Date> opCrDtStart = Optional.ofNullable(dto.getCrDtStart());
        if (opCrDtStart.isPresent()) {
            if (null != opCrDtStart.get()) {
                condition.append(getOperator(wherePrinted)).append("DATE_FORMAT(o.crDtStart,'%d/%m/%Y') = :crDtStart");
                wherePrinted = true;
            }
        }

        Optional<Date> opCrDtEnd = Optional.ofNullable(dto.getCrDtEnd());
        if (opCrDtEnd.isPresent()) {
            if (null != opCrDtEnd.get()) {
                condition.append(getOperator(wherePrinted)).append("DATE_FORMAT(o.crDtEnd,'%d/%m/%Y') = :crDtEnd");
                wherePrinted = true;
            }
        }

        Optional<String> opCrRemarks = Optional.ofNullable(dto.getCrRemarks());
        if (opCrRemarks.isPresent()) {
            condition.append(getOperator(wherePrinted)).append("o.crRemarks LIKE :crRemarks");
            wherePrinted = true;
        }

        Optional<CkMstCreditState> opCkMstCreditState = Optional.ofNullable(dto.getTCkMstCreditState());
        if (opCkMstCreditState.isPresent()) {
            Optional<String> opCrstId = Optional.ofNullable(dto.getTCkMstCreditState())
                    .map(CkMstCreditState::getCrstId);
            if (opCrstId.isPresent()) {
                condition.append(getOperator(wherePrinted)).append("o.TCkMstCreditState.crstId = :crstId");
                wherePrinted = true;
            }
        }

        Optional<CkMstServiceType> opCkMstServiceType = Optional.ofNullable(dto.getTCkMstServiceType());
        if (opCkMstServiceType.isPresent()) {
            Optional<String> opSvctId = Optional.ofNullable(dto.getTCkMstServiceType())
                    .map(CkMstServiceType::getSvctId);
            if (opSvctId.isPresent()) {
                condition.append(getOperator(wherePrinted)).append("o.TCkMstServiceType.svctId = :svctId");
                wherePrinted = true;
            }
        }

        /*
         * Optional<CoreAccn> opCoreAccn = Optional.ofNullable(dto.getTCoreAccn());
         * if (opCoreAccn.isPresent()) {
         * Optional<String> opAccnId =
         * Optional.ofNullable(dto.getTCoreAccn()).map(CoreAccn::getAccnId);
         * if (opAccnId.isPresent()) {
         * condition.append(getOperator(wherePrinted)).
         * append("o.TCoreAccn.accnId = :accnId");
         * wherePrinted = true;
         * }
         * }
         */
        condition.append(getOperator(wherePrinted)).append("o.TCoreAccn.accnId = :accnId");
        wherePrinted = true;

        Optional<CoreUsr> opCoreUsrVerify = Optional.ofNullable(dto.getTCoreUsrVerify());
        if (opCoreUsrVerify.isPresent()) {
            Optional<String> opUsrId = Optional.ofNullable(dto.getTCoreUsrVerify()).map(CoreUsr::getUsrUid);
            if (opUsrId.isPresent()) {
                condition.append(getOperator(wherePrinted)).append("o.TCoreUsrVerify.usrUid = :usrUid");
                wherePrinted = true;
            }
        }

        Optional<CoreUsr> opCoreUsrApprove = Optional.ofNullable(dto.getTCoreUsrApprove());
        if (opCoreUsrApprove.isPresent()) {
            Optional<String> opUsrId = Optional.ofNullable(dto.getTCoreUsrApprove()).map(CoreUsr::getUsrUid);
            if (opUsrId.isPresent()) {
                condition.append(getOperator(wherePrinted)).append("o.TCoreUsrApprove.usrUid = :usrUid");
                wherePrinted = true;
            }
        }

        Optional<CoreUsr> opCoreUsrReject = Optional.ofNullable(dto.getTCoreUsrReject());
        if (opCoreUsrReject.isPresent()) {
            Optional<String> opUsrId = Optional.ofNullable(dto.getTCoreUsrReject()).map(CoreUsr::getUsrUid);
            if (opUsrId.isPresent()) {
                condition.append(getOperator(wherePrinted)).append("o.TCoreUsrReject.usrUid = :usrUid");
                wherePrinted = true;
            }
        }

        Optional<MstCurrency> opMstCurrency = Optional.ofNullable(dto.getTMstCurrency());
        if (opMstCurrency.isPresent()) {
            Optional<String> opCcyCode = Optional.ofNullable(dto.getTMstCurrency()).map(MstCurrency::getCcyCode);
            if (opCcyCode.isPresent()) {
                condition.append(getOperator(wherePrinted)).append("o.TMstCurrency.ccyCode = :ccyCode");
                wherePrinted = true;
            }
        }

        return condition.toString();
    }

    @Override
    protected HashMap<String, Object> getParameters(CkCredit dto)
            throws ParameterException, ProcessingException, Exception {
        log.debug("getParameters");
        if (null == dto)
            throw new ParameterException("param dto null");

        Principal principal = ckSession.getPrincipal();
        if (null == principal)
            throw new ParameterException("param principal null");
        CoreAccn coreAccn = principal.getCoreAccn();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        HashMap<String, Object> parameters = new HashMap<>();

        Optional<String> opCrId = Optional.ofNullable(dto.getCrId());
        if (opCrId.isPresent()) {
            parameters.put("crId", opCrId.get());
        }

        Optional<BigDecimal> opCrAmt = Optional.ofNullable(dto.getCrAmt());
        if (opCrAmt.isPresent()) {
            parameters.put("crAmt", opCrAmt.get());
        }

        Optional<BigDecimal> opCrTxnCap = Optional.ofNullable(dto.getCrTxnCap());
        if (opCrTxnCap.isPresent()) {
            parameters.put("crTxnCap", opCrTxnCap.get());
        }

        Optional<Date> opCrDtStart = Optional.ofNullable(dto.getCrDtStart());
        if (opCrDtStart.isPresent()) {
            if (null != opCrDtStart.get()) {
                parameters.put("crDtStart", sdf.format(opCrDtStart.get()));

            }
        }

        Optional<Date> opCrDtEnd = Optional.ofNullable(dto.getCrDtEnd());
        if (opCrDtEnd.isPresent()) {
            if (null != opCrDtEnd.get()) {
                parameters.put("crDtEnd", sdf.format(opCrDtEnd.get()));
            }
        }

        Optional<String> opCrRemarks = Optional.ofNullable(dto.getCrRemarks());
        if (opCrRemarks.isPresent()) {
            parameters.put("crRemarks", "%" + opCrRemarks.get() + "%");
        }

        Optional<CkMstCreditState> opCkMstCreditState = Optional.ofNullable(dto.getTCkMstCreditState());
        if (opCkMstCreditState.isPresent()) {
            Optional<String> opCrstId = Optional.ofNullable(dto.getTCkMstCreditState())
                    .map(CkMstCreditState::getCrstId);
            if (opCrstId.isPresent()) {
                parameters.put("crstId", opCrstId.get());
            }
        }

        Optional<CkMstServiceType> opCkMstServiceType = Optional.ofNullable(dto.getTCkMstServiceType());
        if (opCkMstServiceType.isPresent()) {
            Optional<String> opSvctId = Optional.ofNullable(dto.getTCkMstServiceType())
                    .map(CkMstServiceType::getSvctId);
            if (opSvctId.isPresent()) {
                parameters.put("svctId", opSvctId.get());
            }
        }

        parameters.put("accnId", coreAccn.getAccnId());

        Optional<CoreUsr> opCoreUsrVerify = Optional.ofNullable(dto.getTCoreUsrVerify());
        if (opCoreUsrVerify.isPresent()) {
            Optional<String> opUsrId = Optional.ofNullable(dto.getTCoreUsrVerify()).map(CoreUsr::getUsrUid);
            if (opUsrId.isPresent()) {
                parameters.put("usrUid", opUsrId.get());
            }
        }

        Optional<CoreUsr> opCoreUsrApprove = Optional.ofNullable(dto.getTCoreUsrApprove());
        if (opCoreUsrApprove.isPresent()) {
            Optional<String> opUsrId = Optional.ofNullable(dto.getTCoreUsrApprove()).map(CoreUsr::getUsrUid);
            if (opUsrId.isPresent()) {
                parameters.put("usrUid", opUsrId.get());
            }
        }

        Optional<CoreUsr> opCoreUsrReject = Optional.ofNullable(dto.getTCoreUsrReject());
        if (opCoreUsrReject.isPresent()) {
            Optional<String> opUsrId = Optional.ofNullable(dto.getTCoreUsrReject()).map(CoreUsr::getUsrUid);
            if (opUsrId.isPresent()) {
                parameters.put("usrUid", opUsrId.get());
            }
        }

        Optional<MstCurrency> opMstCurrency = Optional.ofNullable(dto.getTMstCurrency());
        if (opMstCurrency.isPresent()) {
            Optional<String> opCcyCode = Optional.ofNullable(dto.getTMstCurrency()).map(MstCurrency::getCcyCode);
            if (opCcyCode.isPresent()) {
                parameters.put("ccyCode", opCcyCode.get());
            }
        }
        return parameters;
    }

    @Override
    protected CkCredit whereDto(EntityFilterRequest filterRequest)
            throws ParameterException, ProcessingException, Exception {
        log.debug("whereDto");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        CkCredit dto = new CkCredit();
        CkMstServiceType ckMstServiceType = new CkMstServiceType();
        CoreAccn coreAccn = new CoreAccn();
        CkMstCreditState ckMstCreditState = new CkMstCreditState();
        MstCurrency mstCurrency = new MstCurrency();
        CoreUsr coreUsrVerify = new CoreUsr();
        CoreUsr coreUsrApprove = new CoreUsr();
        CoreUsr coreUsrReject = new CoreUsr();

        for (EntityWhere entityWhere : filterRequest.getWhereList()) {
            Optional<String> opValue = Optional.ofNullable(entityWhere.getValue());
            if (!opValue.isPresent())
                continue;

            if (entityWhere.getAttribute().equalsIgnoreCase("crId"))
                dto.setCrId(opValue.get());

            if (entityWhere.getAttribute().equalsIgnoreCase("crAmt"))
                dto.setCrAmt(new BigDecimal(opValue.get()));

            if (entityWhere.getAttribute().equalsIgnoreCase("crTxnCap"))
                dto.setCrTxnCap(new BigDecimal(opValue.get()));

            if (entityWhere.getAttribute().equalsIgnoreCase("crDtStart")) {
                try {
                    dto.setCrDtStart(sdf.parse(opValue.get()));
                } catch (ParseException e) {
                    throw e;
                }
            }

            if (entityWhere.getAttribute().equalsIgnoreCase("crDtEnd")) {
                try {
                    dto.setCrDtEnd(sdf.parse(opValue.get()));
                } catch (ParseException e) {
                    throw e;
                }
            }

            if (entityWhere.getAttribute().equalsIgnoreCase("crRemarks"))
                dto.setCrRemarks(opValue.get());

            if (entityWhere.getAttribute().equalsIgnoreCase("TCkMstServiceType.svctId"))
                ckMstServiceType.setSvctId(opValue.get());

            if (entityWhere.getAttribute().equalsIgnoreCase("TCoreAccn.accnId"))
                coreAccn.setAccnId(opValue.get());

            if (entityWhere.getAttribute().equalsIgnoreCase("TCkMstCreditState.crstId"))
                ckMstCreditState.setCrstId(opValue.get());

            if (entityWhere.getAttribute().equalsIgnoreCase("TMstCurrency.ccyCode"))
                mstCurrency.setCcyCode(opValue.get());

            if (entityWhere.getAttribute().equalsIgnoreCase("TCoreUsrVerify.usrUid"))
                coreUsrVerify.setUsrUid(opValue.get());

            if (entityWhere.getAttribute().equalsIgnoreCase("TCoreUsrApprove.usrUid"))
                coreUsrApprove.setUsrUid(opValue.get());

            if (entityWhere.getAttribute().equalsIgnoreCase("TCoreUsrReject.usrUid"))
                coreUsrReject.setUsrUid(opValue.get());
        }
        dto.setTCkMstServiceType(ckMstServiceType);
        dto.setTCoreAccn(coreAccn);
        dto.setTCkMstCreditState(ckMstCreditState);
        dto.setTMstCurrency(mstCurrency);
        dto.setTCoreUsrVerify(coreUsrVerify);
        dto.setTCoreUsrApprove(coreUsrApprove);
        dto.setTCoreUsrReject(coreUsrReject);
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, String>> findDistinctCompany() {
        try {
            return ckCreditDao.findDistinctCompanyByState(CreditState.APPROVED.name());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findDistinctServiceByCompany(String companyId) {
        try {
            return ckCreditDao.findDistinctServiceByCompanyAndState(companyId, CreditState.APPROVED.name());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    @Override
    @Transactional(readOnly = true)
    public CkCredit findByServiceTypeAndAccnId(String serviceType, String accnId) throws ParameterException {
        Principal principal = ckSession.getPrincipal();
        if (principal == null) {
            throw new ParameterException("param principal null");
        }
        try {
            TCkCredit tCkCredit = ckCreditDao.findByServiceTypeAndAccn(serviceType, accnId);
            if (tCkCredit == null) {
                return null;
            }
            return dtoFromEntity(tCkCredit);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    @Transactional
    public CkCredit update(CkCredit dto) throws ParameterException, ProcessingException, Exception {
        if (dto == null) {
            throw new ParameterException("param dto null");
        }
        TCkCredit tCkCredit = entityFromDTO(dto);
        dao.update(tCkCredit);
        return dto;
    }
}