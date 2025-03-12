package com.guudint.clickargo.credit.validator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.common.model.ValidationError;
import com.guudint.clickargo.credit.dao.CkCreditDao;
import com.guudint.clickargo.credit.dao.CkCreditRequestDao;
import com.guudint.clickargo.credit.dao.CkCreditSummaryDao;
import com.guudint.clickargo.credit.dto.CkCreditRequest;
import com.guudint.clickargo.credit.model.TCkCredit;
import com.guudint.clickargo.credit.model.TCkCreditRequest;
import com.guudint.clickargo.job.service.IJobValidate;
import com.guudint.clickargo.master.dto.CkMstServiceType;
import com.guudint.clickargo.master.enums.CreditRequestState;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.master.dto.MstCurrency;

@Component
public class CreditRequestValidator implements IJobValidate<CkCreditRequest> {

    private static Logger LOG = Logger.getLogger(CreditRequestValidator.class);

    @Autowired
    private CkCreditDao ckCreditDao;
    @Autowired
    private CkCreditRequestDao ckCreditRequestDao;
    @Autowired
    private CkCreditSummaryDao ckCreditSummaryDao;

    private List<ValidationError> mandatoryValidation(CkCreditRequest ckCreditRequest, Principal principal)
            throws ParameterException {
        if (principal == null) {
            throw new ParameterException("param principal null");
        }
        if (ckCreditRequest == null) {
            throw new ParameterException("param dto null");
        }
        List<ValidationError> invalidList = new ArrayList<>();
        if (ckCreditRequest.getCruAmt() == null) {
            invalidList.add(new ValidationError("", "cruAmt", "New Limit cannot be empty"));
        }
        if (ckCreditRequest.getCruDtStart() == null) {
            invalidList.add(new ValidationError("", "cruDtStart", "Start Date cannot be empty"));
        }
        if (ckCreditRequest.getCruDtEnd() == null) {
            invalidList.add(new ValidationError("", "cruDtEnd", "End Date cannot be empty"));
        }
        if (StringUtils.isBlank(ckCreditRequest.getCruRemarks())) {
            invalidList.add(new ValidationError("", "cruRemarks", "Comments cannot be empty"));
        }
        if (StringUtils.isBlank(ckCreditRequest.getCruRequester())) {
            invalidList.add(new ValidationError("", "cruRequester", "Requester cannot be empty"));
        }
        return invalidList;
    }

    private List<ValidationError> dataValidation(CkCreditRequest ckCreditRequest, Principal principal)
            throws ProcessingException {
        List<ValidationError> invalidList = new ArrayList<>();
        if (ckCreditRequest.getCruDtStart() == null) {
            invalidList.add(new ValidationError("", "cruDtStart", "Start Date cannot be empty"));
        }
        if (ckCreditRequest.getCruDtEnd() == null) {
            invalidList.add(new ValidationError("", "cruDtEnd", "End Date cannot be empty"));
        }
        CkMstServiceType ckMstServiceType = ckCreditRequest.getTCkMstServiceType();
        CoreAccn coreAccn = ckCreditRequest.getTCoreAccn();
        MstCurrency mstCurrency = ckCreditRequest.getTMstCurrency();
        if (ckMstServiceType != null && coreAccn != null && mstCurrency != null) {
            try {
                TCkCredit tCkCredit = ckCreditDao.getByServiceTypeAndAccnAndCcy(ckMstServiceType, coreAccn, mstCurrency);
                if (tCkCredit != null) {
                    LocalDate startDate = getDateOnly(tCkCredit.getCrDtStart());
                    LocalDate startDateUpdate = getDateOnly(ckCreditRequest.getCruDtStart());
                    LocalDate endDate = getDateOnly(tCkCredit.getCrDtEnd());
                    LocalDate endDateUpdate = getDateOnly(ckCreditRequest.getCruDtEnd());
                    if (startDateUpdate.isBefore(startDate)) {
                        invalidList.add(new ValidationError("", "cruDtStart",
                                "Start Date cannot be less than End Date of Credit Limit Details"));
                    }
                    if (startDateUpdate.isAfter(endDate)) {
                        invalidList.add(new ValidationError("", "cruDtStart",
                                "Start Date cannot be more than End Date of Credit Limit Details"));
                    }
                    if (endDateUpdate.isBefore(endDate)) {
                        invalidList.add(new ValidationError("", "cruDtEnd",
                                "End Date cannot be less than End Date of Credit Limit Details"));
                    }
                    /*-
                    TCkCreditSummary tCkCreditSummary = ckCreditSummaryDao
                            .getByServiceTypeAndAccnAndCcy(ckMstServiceType, coreAccn, mstCurrency);
                    if (tCkCreditSummary != null) {
                        if (ckCreditRequest.getCruAmt().compareTo(tCkCreditSummary.getCrsBalance()) < 0) {
                            invalidList.add(
                                    new ValidationError("", "cruAmt", "New Limit cannot be below current limit"));
                        }
                    }
                    */
                    if (ckCreditRequest.getCruAmt().compareTo(BigDecimal.ZERO) <= 0) {
                        invalidList.add(
                                new ValidationError("", "cruAmt", "Should greater than 0."));
                    }
                }
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
                throw new ProcessingException(e);
            }
        }
        return invalidList;
    }

    private List<ValidationError> uniqueValidation(CkCreditRequest ckCreditRequest, Principal principal)
            throws ProcessingException {
        List<String> state = Arrays.asList(CreditRequestState.NEW.name(), CreditRequestState.SUB.name(),
                CreditRequestState.APP.name());
        CkMstServiceType ckMstServiceType = ckCreditRequest.getTCkMstServiceType();
        CoreAccn coreAccn = ckCreditRequest.getTCoreAccn();
        List<ValidationError> invalidList = new ArrayList<>();
        if (ckMstServiceType != null && coreAccn != null) {
            try {
                List<TCkCreditRequest> tCkCreditRequests = ckCreditRequestDao
                        .findByServiceTypeAndAccnId(ckMstServiceType.getSvctId(), coreAccn.getAccnId());
                
                // remove current ckCreditRequest himself
                tCkCreditRequests = tCkCreditRequests.stream().filter( cu -> ! cu.getCruId().equalsIgnoreCase(ckCreditRequest.getCruId())).collect(Collectors.toList());
                
                if (!tCkCreditRequests.isEmpty()) {
                    TCkCreditRequest tCkCreditRequest = tCkCreditRequests.get(0);
                    if (state.contains(tCkCreditRequest.getTCkMstCreditRequestState().getStId())) {
                        invalidList.add(new ValidationError("", "cruState",
                                String.format("Credit Limit Update is exists with status [%s]",
                                        getDesc(tCkCreditRequest.getTCkMstCreditRequestState().getStId()))));
                    }
                }
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
                throw new ProcessingException(e);
            }
        }
        return invalidList;
    }

    private String getDesc(String name) {
        for (CreditRequestState creditRequestState : CreditRequestState.values()) {
            if (creditRequestState.name().equals(name)) {
                return creditRequestState.getDesc();
            }
        }
        return "";
    }

    private LocalDate getDateOnly(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ValidationError> validateCreate(CkCreditRequest ckCreditRequest, Principal principal)
            throws ParameterException, ProcessingException {
        List<ValidationError> invalidList = new ArrayList<>();
        invalidList.addAll(mandatoryValidation(ckCreditRequest, principal));
        invalidList.addAll(dataValidation(ckCreditRequest, principal));
        invalidList.addAll(uniqueValidation(ckCreditRequest, principal));
        return invalidList;
    }

    @Override
    public List<ValidationError> validateReject(CkCreditRequest dto, Principal principal)
            throws ParameterException, ProcessingException {
        return null;
    }

    @Override
    public List<ValidationError> validateCancel(CkCreditRequest dto, Principal principal)
            throws ParameterException, ProcessingException {
        return null;
    }

    @Override
    public List<ValidationError> validateDelete(CkCreditRequest dto, Principal principal)
            throws ParameterException, ProcessingException {
        return null;
    }

    @Override
    public List<ValidationError> validateConfirm(CkCreditRequest dto, Principal principal)
            throws ParameterException, ProcessingException {
        return null;
    }

    @Override
    public List<ValidationError> validatePay(CkCreditRequest dto, Principal principal)
            throws ParameterException, ProcessingException {
        return null;
    }

    @Override
    public List<ValidationError> validatePaid(CkCreditRequest dto, Principal principal)
            throws ParameterException, ProcessingException {
        return null;
    }

    @Override
    public List<ValidationError> validateComplete(CkCreditRequest dto, Principal principal)
            throws ParameterException, ProcessingException {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ValidationError> validateUpdate(CkCreditRequest ckCreditRequest, Principal principal)
            throws ParameterException, ProcessingException {
        if (principal == null) {
            throw new ParameterException("param principal null");
        }
        if (ckCreditRequest == null) {
            throw new ParameterException("param dto null");
        }
        List<ValidationError> invalidList = new ArrayList<>();
        if (CreditRequestState.APP.name().equals(ckCreditRequest.getTCkMstCreditRequestState().getStId())) {
            if (StringUtils.isBlank(ckCreditRequest.getCruApproverRemarks())) {
                invalidList.add(new ValidationError("", "cruApproverRemarks", "Approver Comments cannot be empty"));
            }
            try {
                List<TCkCreditRequest> tckCreditRequests = ckCreditRequestDao.findByServiceTypeAndAccnId(
                        ckCreditRequest.getTCkMstServiceType().getSvctId(), ckCreditRequest.getTCoreAccn().getAccnId());
                for (TCkCreditRequest tCkCreditRequest : tckCreditRequests) {
                    if (CreditRequestState.APP.name().equals(tCkCreditRequest.getTCkMstCreditRequestState().getStId())) {
                        invalidList.add(new ValidationError("", "TCkMstCreditRequestState.stId",
                                String.format("%s still have credit limit update with APPROVED state",
                                        ckCreditRequest.getTCoreAccn().getAccnName())));
                        break;
                    }
                }
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
                throw new ProcessingException(e);
            }
        } else if (CreditRequestState.REJ.name().equals(ckCreditRequest.getTCkMstCreditRequestState().getStId())) {
            if (StringUtils.isBlank(ckCreditRequest.getCruApproverRemarks())) {
                invalidList.add(new ValidationError("", "cruApproverRemarks", "Approver Comments cannot be empty"));
            }
        } else if (CreditRequestState.NEW.name().equals(ckCreditRequest.getTCkMstCreditRequestState().getStId())) {
            invalidList.addAll(mandatoryValidation(ckCreditRequest, principal));
            invalidList.addAll(dataValidation(ckCreditRequest, principal));
            invalidList.addAll(uniqueValidation(ckCreditRequest, principal));
        }
        return invalidList;
    }

    @Override
    public List<ValidationError> validateSubmit(CkCreditRequest dto, Principal principal)
            throws ParameterException, ProcessingException {
        return null;
    }
}
