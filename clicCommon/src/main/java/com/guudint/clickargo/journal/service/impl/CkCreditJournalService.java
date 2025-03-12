package com.guudint.clickargo.journal.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;

import com.guudint.clickargo.common.CkUtil;
import com.guudint.clickargo.journal.dto.CkCreditJournal;
import com.guudint.clickargo.journal.model.TCkCreditJournal;
import com.guudint.clickargo.journal.service.AbstractJournalService;
import com.guudint.clickargo.master.dto.CkMstJournalTxnType;
import com.guudint.clickargo.master.dto.CkMstServiceType;
import com.guudint.clickargo.master.model.TCkMstJournalTxnType;
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
import com.vcc.camelone.common.service.entity.AbstractEntityService.ACTION;
import com.vcc.camelone.master.dto.MstCurrency;
import com.vcc.camelone.master.model.TMstCurrency;

public class CkCreditJournalService extends AbstractJournalService {

    private static Logger log = LogManager.getLogger(CkCreditJournalService.class);

    @Override
    public CkCreditJournal findById(String id) throws ParameterException, EntityNotFoundException, ProcessingException, Exception {
        log.debug("findById");
        if (StringUtils.isEmpty(id))
            throw new ParameterException("param id null");

        TCkCreditJournal entity = dao.find(id);
        if (null == entity)
            throw new ProcessingException("entity not found: " + id);
        initEnity(entity);
        return dtoFromEntity(entity);
    }

    @Override
    public List<CkCreditJournal> filterBy(EntityFilterRequest filterRequest) throws ParameterException, EntityNotFoundException, ProcessingException, Exception {
        log.debug("filterBy");
        if (null == filterRequest)
            throw new ParameterException("param filterRequest null");

        CkCreditJournal dto = whereDto(filterRequest);
        if (null == dto) 
            throw new ProcessingException("param dto null");

        filterRequest.setTotalRecords(countByAnd(dto));
        List<CkCreditJournal> dtos = new ArrayList<>();
        String orderClause = formatOrderByObj(filterRequest.getOrderBy()).toString();
        List<TCkCreditJournal> query = findEntitiesByAnd(dto, "from TCkCreditJournal o ", orderClause, filterRequest.getDisplayLength(), filterRequest.getDisplayStart());
        for (TCkCreditJournal entity : query) {
            CkCreditJournal newDto = dtoFromEntity(entity);
            if (null != newDto)
                dtos.add(newDto);
        }
        return dtos;
    }

    @Override
    protected CkCreditJournal _reserve(CkCreditJournal dto, Principal principal) throws ParameterException, ValidationException, ProcessingException, Exception, Exception {
        log.debug("_reserve");
        dto.setCjnId(CkUtil.generateId());
        dto.setCjnUtilized(BigDecimal.ZERO);
        dto.setCjnStatus(Constant.ACTIVE_STATUS);
        return add(dto, principal);
    }

    @Override
    protected CkCreditJournal _reverse(CkCreditJournal dto, Principal principal) throws ParameterException, ValidationException, ProcessingException, Exception {
        log.debug("_reverse");
        dto.setCjnId(CkUtil.generateId());
        BigDecimal reverse = dto.getCjnReserve().multiply(new BigDecimal(-1));
        dto.setCjnReserve(reverse);
        dto.setCjnUtilized(BigDecimal.ZERO);
        dto.setCjnStatus(Constant.ACTIVE_STATUS);
        return add(dto, principal);
    }

    @Override
    protected CkCreditJournal _utilize(CkCreditJournal dto, Principal principal) throws ParameterException, ValidationException, ProcessingException, Exception {
        log.debug("_utilize");
        dto.setCjnId(CkUtil.generateId());
        dto.setCjnReserve(BigDecimal.ZERO);
        dto.setCjnStatus(Constant.ACTIVE_STATUS);
        return add(dto, principal);
    }

    @Override
    protected CkCreditJournal _pay(CkCreditJournal dto, Principal principal) throws ParameterException, ValidationException, ProcessingException, Exception {
        log.debug("_pay");
        dto.setCjnId(CkUtil.generateId());
        dto.setCjnReserve(BigDecimal.ZERO);
        BigDecimal pay = dto.getCjnUtilized().multiply(new BigDecimal(-1));
        dto.setCjnUtilized(pay);
        dto.setCjnStatus(Constant.ACTIVE_STATUS);
        return add(dto, principal);
    }

    @Override
    protected TCkCreditJournal initEnity(TCkCreditJournal entity) throws ParameterException, ProcessingException, Exception {
        log.debug("initEnity");
        if (null != entity) {
            Optional.ofNullable(entity.getTCkMstServiceType()).ifPresent(x -> Hibernate.initialize(x));
            Optional.ofNullable(entity.getTCoreAccn()).ifPresent(x -> Hibernate.initialize(x));
            Optional.ofNullable(entity.getTCkMstJournalTxnType()).ifPresent(x -> Hibernate.initialize(x));
            Optional.ofNullable(entity.getTMstCurrency()).ifPresent(x -> Hibernate.initialize(x));
        }
        return entity;
    }

    @Override
    protected TCkCreditJournal entityFromDTO(CkCreditJournal dto) throws ParameterException, ProcessingException, Exception {
        log.debug("entityFromDTO");
        if (null == dto)
            throw new ParameterException("param dto null");

        TCkCreditJournal entity = dto.toEntity(new TCkCreditJournal());
        Optional.ofNullable(dto.getTCkMstServiceType()).ifPresent(x -> entity.setTCkMstServiceType(x.toEntity(new TCkMstServiceType())));
        Optional.ofNullable(dto.getTCkMstJournalTxnType()).ifPresent(x -> entity.setTCkMstJournalTxnType(x.toEntity(new TCkMstJournalTxnType())));
        Optional.ofNullable(dto.getTCoreAccn()).ifPresent(x -> entity.setTCoreAccn(x.toEntity(new TCoreAccn())));
        Optional.ofNullable(dto.getTMstCurrency()).ifPresent(x -> entity.setTMstCurrency(x.toEntity(new TMstCurrency())));
        
        return entity;
    }

    @Override
    protected CkCreditJournal dtoFromEntity(TCkCreditJournal entity) throws ParameterException, ProcessingException, Exception {
        log.debug("dtoFromEntity");
        if (null == entity) 
            throw new ParameterException("param entity null");

        CkCreditJournal dto = new CkCreditJournal(entity);
        Optional.ofNullable(entity.getTCkMstServiceType()).ifPresent(x -> dto.setTCkMstServiceType(new CkMstServiceType(x)));
        Optional.ofNullable(entity.getTCoreAccn()).ifPresent(x -> dto.setTCoreAccn(new CoreAccn(x)));
        Optional.ofNullable(entity.getTCkMstJournalTxnType()).ifPresent(x -> dto.setTCkMstJournalTxnType(new CkMstJournalTxnType(x)));
        Optional.ofNullable(entity.getTMstCurrency()).ifPresent(x -> dto.setTMstCurrency(new MstCurrency(x)));

        return dto;
    }

    @Override
    protected String entityKeyFromDTO(CkCreditJournal dto) throws ParameterException, ProcessingException, Exception {
        log.debug("entityKeyFromDTO");
        if (null == dto) 
            throw new ParameterException("param dto null");
        return dto.getCjnId();
    }

    @Override
    protected TCkCreditJournal updateEntity(ACTION action, TCkCreditJournal entity, Principal principal, Date date) throws ParameterException, ProcessingException, Exception {
        log.debug("updateEntity");
        if (null == entity) 
            throw new ParameterException("param entity is null or empty");

        if (null == date) 
            throw new ParameterException("param date is null or empty");

        Optional<Principal> opUserId = Optional.ofNullable(principal);
        switch (action) {
            case CREATE:
                entity.setCjnUidCreate(opUserId.isPresent() ? opUserId.get().getUserId() : Constant.DEFAULT_USR);
                entity.setCjnDtCreate(date);
                entity.setCjnUidLupd(opUserId.isPresent() ?  opUserId.get().getUserId() : Constant.DEFAULT_USR);
                entity.setCjnDtLupd(date);
            break;
            case MODIFY:
                entity.setCjnUidLupd(opUserId.isPresent() ?  opUserId.get().getUserId() : Constant.DEFAULT_USR);
                entity.setCjnDtLupd(date);
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

        if (StringUtils.contains(attribute, "tckMstJournalTxnType"))
            attribute = attribute.replace("tckMstJournalTxnType", "TCkMstJournalTxnType");

        if (StringUtils.contains(attribute, "tmstCurrency"))
            attribute = attribute.replace("tmstCurrency", "TMstCurrency");

        return attribute;
    }

    @Override
    protected String getWhereClause(CkCreditJournal dto, boolean wherePrinted) throws ParameterException, ProcessingException, Exception {
        log.debug("getWhereClause");
        if (null == dto)
            throw new ParameterException("param dto null");

        StringBuffer condition = new StringBuffer();
        
        Optional<String> opCjnId = Optional.ofNullable(dto.getCjnId());
        if (opCjnId.isPresent()) {
            condition.append(getOperator(wherePrinted)).append("o.cjnId = :cjnId");
            wherePrinted = true;
        }

        Optional<String> opCjnTnxRef = Optional.ofNullable(dto.getCjnTxnRef());
        if (opCjnTnxRef.isPresent()) {
            condition.append(getOperator(wherePrinted)).append("o.cjnTxnRef LIKE :cjnTxnRef");
            wherePrinted = true;
        }

        Optional<BigDecimal> opCjnReserve = Optional.ofNullable(dto.getCjnReserve());
        if (opCjnReserve.isPresent()) {
            condition.append(getOperator(wherePrinted)).append("o.cjnReserve = :cjnReserve");
            wherePrinted = true;
        }

        Optional<BigDecimal> opCjnUtilized = Optional.ofNullable(dto.getCjnUtilized());
        if (opCjnUtilized.isPresent()) {
            condition.append(getOperator(wherePrinted)).append("o.cjnUtilized = :cjnUtilized");
            wherePrinted = true;
        }

        Optional<CkMstServiceType> opCkMstServiceType = Optional.ofNullable(dto.getTCkMstServiceType());
        if (opCkMstServiceType.isPresent()) {
            Optional<String> opSvctId = Optional.ofNullable(dto.getTCkMstServiceType()).map(CkMstServiceType::getSvctId);
            if (opSvctId.isPresent()) {
                condition.append(getOperator(wherePrinted)).append("o.TCkMstServiceType.svctId = :svctId");
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

        Optional<CkMstJournalTxnType> opCkMstJournalTxnType = Optional.ofNullable(dto.getTCkMstJournalTxnType());
        if (opCkMstJournalTxnType.isPresent()) {
            Optional<String> opJttId = Optional.ofNullable(dto.getTCkMstJournalTxnType()).map(CkMstJournalTxnType::getJttId);
            if (opJttId.isPresent()) {
                condition.append(getOperator(wherePrinted)).append("o.TCkMstJournalTxnType.jttId = :jttId");
                wherePrinted = true;
            }
        }

        condition.append(getOperator(wherePrinted)).append("o.TCoreAccn.accnId = :accnId");
        wherePrinted = true;
        return condition.toString();
    }

    @Override
    protected HashMap<String, Object> getParameters(CkCreditJournal dto) throws ParameterException, ProcessingException, Exception {
        log.debug("getParameters");
        if (null == dto) 
            throw new ParameterException("param dto null");

        Principal principal = ckSession.getPrincipal();
        if (null == principal)
            throw new ParameterException("param principal null");
        CoreAccn coreAccn = principal.getCoreAccn();

        HashMap<String, Object> parameters = new HashMap<>();

        Optional<String> opCjnId = Optional.ofNullable(dto.getCjnId());
        if (opCjnId.isPresent()) {
            parameters.put("cjnId", opCjnId.get());
        }

        Optional<CkMstServiceType> opCkMstServiceType = Optional.ofNullable(dto.getTCkMstServiceType());
        if (opCkMstServiceType.isPresent()) {
            Optional<String> opSvctId = Optional.ofNullable(dto.getTCkMstServiceType()).map(CkMstServiceType::getSvctId);
            if (opSvctId.isPresent()) {
                parameters.put("svctId", opSvctId.get());
            }
        }

        parameters.put("accnId", coreAccn.getAccnId());

        Optional<String> opCjnTnxRef = Optional.ofNullable(dto.getCjnTxnRef());
        if (opCjnTnxRef.isPresent()) {
            parameters.put("cjnTxnRef", "%" + opCjnTnxRef.get() + "%");
        }

        Optional<BigDecimal> opCjnReserve = Optional.ofNullable(dto.getCjnReserve());
        if (opCjnReserve.isPresent()) {
            parameters.put("cjnReserve", opCjnReserve.get());
        }

        Optional<BigDecimal> opCjnUtilized = Optional.ofNullable(dto.getCjnUtilized());
        if (opCjnUtilized.isPresent()) {
            parameters.put("cjnUtilized", opCjnUtilized.get());
        }

        Optional<CkMstJournalTxnType> opCkMstJournalTxnType = Optional.ofNullable(dto.getTCkMstJournalTxnType());
        if (opCkMstJournalTxnType.isPresent()) {
            Optional<String> opJttId = Optional.ofNullable(dto.getTCkMstJournalTxnType()).map(CkMstJournalTxnType::getJttId);
            if (opJttId.isPresent()) {
                parameters.put("jttId", opJttId.get());
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
    protected CkCreditJournal whereDto(EntityFilterRequest filterRequest) throws ParameterException, ProcessingException, Exception {
        log.debug("whereDto");
        CkCreditJournal dto                     = new CkCreditJournal();
        CkMstServiceType ckMstServiceType       = new CkMstServiceType();
        CoreAccn coreAccn                       = new CoreAccn();
        CkMstJournalTxnType ckMstJournalTxnType = new CkMstJournalTxnType();
        MstCurrency mstCurrency                 = new MstCurrency();

        for (EntityWhere entityWhere : filterRequest.getWhereList()) {
            Optional<String> opValue = Optional.ofNullable(entityWhere.getValue());
            if (!opValue.isPresent()) 
                continue;

            if (entityWhere.getAttribute().equalsIgnoreCase("cjnId")) 
                dto.setCjnId(opValue.get());

            if (entityWhere.getAttribute().equalsIgnoreCase("cjnTxnRef")) 
                dto.setCjnTxnRef(opValue.get());

            if (entityWhere.getAttribute().equalsIgnoreCase("cjnReserve")) 
                dto.setCjnReserve(new BigDecimal(opValue.get()));

            if (entityWhere.getAttribute().equalsIgnoreCase("cjnUtilized")) 
                dto.setCjnUtilized(new BigDecimal(opValue.get()));

            if (entityWhere.getAttribute().equalsIgnoreCase("TCkMstServiceType.svctId")) 
                ckMstServiceType.setSvctId(opValue.get());

            if (entityWhere.getAttribute().equalsIgnoreCase("TCoreAccn.accnId")) 
                coreAccn.setAccnId(opValue.get());

            if (entityWhere.getAttribute().equalsIgnoreCase("TCkMstJournalTxnType.jttId")) 
                ckMstJournalTxnType.setJttId(opValue.get());

            if (entityWhere.getAttribute().equalsIgnoreCase("TMstCurrency.ccyCode")) 
                mstCurrency.setCcyCode(opValue.get());
        }
        dto.setTCkMstServiceType(ckMstServiceType);
        dto.setTCoreAccn(coreAccn);
        dto.setTCkMstJournalTxnType(ckMstJournalTxnType);
        dto.setTMstCurrency(mstCurrency);
        return dto;
    }
}
