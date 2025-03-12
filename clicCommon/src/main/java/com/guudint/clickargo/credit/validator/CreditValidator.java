package com.guudint.clickargo.credit.validator;

import java.util.ArrayList;
import java.util.List;

import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guudint.clickargo.common.model.ValidationError;
import com.guudint.clickargo.credit.dao.CkCreditDao;
import com.guudint.clickargo.credit.dto.CkCredit;
import com.guudint.clickargo.credit.model.TCkCredit;
import com.guudint.clickargo.credit.service.ICreditValidation;
import com.guudint.clickargo.master.enums.CreditState;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;

@Component
public class CreditValidator implements ICreditValidation {

    @Autowired
    private CkCreditDao ckCreditDao;

    @Override
    public List<ValidationError> validationCreateCredit(CkCredit dto, Principal principal) throws ParameterException, ProcessingException, Exception {
        Log.debug("validationCreateCredit");
        try {
            List<ValidationError> validationErrors = new ArrayList<>();
            TCkCredit entity = ckCreditDao.getByServiceTypeAndAccnAndCcy(dto.getTCkMstServiceType(), dto.getTCoreAccn(), dto.getTMstCurrency());
            if (null != entity) {
                validationErrors.add(new ValidationError("", "validation", "credit-already-exist"));
                return validationErrors;
            }
            return null;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<ValidationError> validationVerifyCredit(CkCredit dto, Principal principal) throws ParameterException, ProcessingException, Exception {
        Log.debug("validationVerifyCredit");
        try {
            List<ValidationError> validationErrors = new ArrayList<>();
            TCkCredit entity = ckCreditDao.getByServiceTypeAndAccnAndCcy(dto.getTCkMstServiceType(), dto.getTCoreAccn(), dto.getTMstCurrency());
            if (null == entity) {
                validationErrors.add(new ValidationError("", "validation", "credit-not-found"));
                return validationErrors;
            }

            if (entity.getTCkMstCreditState().getCrstId().equals(CreditState.APPROVED.getDesc())) {
                validationErrors.add(new ValidationError("", "validation", "credit-already-approve"));
                return validationErrors;
            }

            return null;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<ValidationError> validationApproveCredit(CkCredit dto, Principal principal) throws ParameterException, ProcessingException, Exception {
        Log.debug("validationApproveCredit");
        try {
            List<ValidationError> validationErrors = new ArrayList<>();
            TCkCredit entity = ckCreditDao.getByServiceTypeAndAccnAndCcy(dto.getTCkMstServiceType(), dto.getTCoreAccn(), dto.getTMstCurrency());
            if (null == entity) {
                validationErrors.add(new ValidationError("", "validation", "credit-not-found"));
                return validationErrors;
            }

            if (entity.getTCkMstCreditState().getCrstId().equals(CreditState.APPROVED.getDesc())) {
                validationErrors.add(new ValidationError("", "validation", "credit-already-approve"));
                return validationErrors;
            }

            return null;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<ValidationError> validationRejectCredit(CkCredit dto, Principal principal) throws ParameterException, ProcessingException, Exception {
        Log.debug("validationRejectCredit");
        try {
            List<ValidationError> validationErrors = new ArrayList<>();
            TCkCredit entity = ckCreditDao.getByServiceTypeAndAccnAndCcy(dto.getTCkMstServiceType(), dto.getTCoreAccn(), dto.getTMstCurrency());
            if (null == entity) {
                validationErrors.add(new ValidationError("", "validation", "credit-not-found"));
                return validationErrors;
            }

            if (entity.getTCkMstCreditState().getCrstId().equals(CreditState.APPROVED.getDesc())) {
                validationErrors.add(new ValidationError("", "validation", "credit-already-approve"));
                return validationErrors;
            }
            return null;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<ValidationError> validationSuspendCredit(CkCredit dto, Principal principal) throws ParameterException, ProcessingException, Exception {
        Log.debug("validationSuspendCredit");
        try {
            List<ValidationError> validationErrors = new ArrayList<>();
            TCkCredit entity = ckCreditDao.getByServiceTypeAndAccnAndCcy(dto.getTCkMstServiceType(), dto.getTCoreAccn(), dto.getTMstCurrency());
            if (null == entity) {
                validationErrors.add(new ValidationError("", "validation", "credit-not-found"));
                return validationErrors;
            }
            return null;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<ValidationError> validationUnsuspendCredit(CkCredit dto, Principal principal) throws ParameterException, ProcessingException, Exception {
        Log.debug("validationUnsuspendCredit");
        try {
            List<ValidationError> validationErrors = new ArrayList<>();
            TCkCredit entity = ckCreditDao.getByServiceTypeAndAccnAndCcy(dto.getTCkMstServiceType(), dto.getTCoreAccn(), dto.getTMstCurrency());
            if (null == entity) {
                validationErrors.add(new ValidationError("", "validation", "credit-not-found"));
                return validationErrors;
            }
            return null;
        } catch (Exception e) {
            throw e;
        }
    }
    
}
