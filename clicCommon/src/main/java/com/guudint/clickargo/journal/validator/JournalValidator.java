package com.guudint.clickargo.journal.validator;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guudint.clickargo.common.model.ValidationError;
import com.guudint.clickargo.credit.dao.CkCreditDao;
import com.guudint.clickargo.credit.dao.CkCreditSummaryDao;
import com.guudint.clickargo.credit.dao.CkCreditSummaryMonthDao;
import com.guudint.clickargo.credit.model.TCkCredit;
import com.guudint.clickargo.credit.model.TCkCreditSummary;
import com.guudint.clickargo.credit.model.TCkCreditSummaryMonth;
import com.guudint.clickargo.journal.dto.CkCreditJournal;
import com.guudint.clickargo.journal.service.IJournalValidate;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;

@Component
public class JournalValidator implements IJournalValidate {

    private static Logger log = LogManager.getLogger(JournalValidator.class);

    @Autowired
    private CkCreditDao ckCreditDao;

    @Autowired
    private CkCreditSummaryDao ckCreditSummaryDao;

    @Autowired
    private CkCreditSummaryMonthDao ckCreditSummaryMonthDao;

    @Override
    public List<ValidationError> validateReserve(CkCreditJournal dto, Principal principal) throws ParameterException, ProcessingException {
        log.debug("validateReserve");
        try {
            List<ValidationError> validationErrors = new ArrayList<>();
            TCkCredit entity = ckCreditDao.getByServiceTypeAndAccnAndCcy(dto.getTCkMstServiceType(), dto.getTCoreAccn(), dto.getTMstCurrency());
            Optional<TCkCredit> opEntity = Optional.ofNullable(entity);
            if (!opEntity.isPresent()) {
                validationErrors.add(new ValidationError("", "validation", "credit-not-found"));
                return validationErrors;
            } 
            
            if (dto.getCjnReserve().doubleValue() > opEntity.get().getCrAmt().doubleValue()) {
                validationErrors.add(new ValidationError("", "validation", "insufficient-credit"));
                return validationErrors;
            }
            
            if (null != opEntity.get().getCrTxnCap() && BigDecimal.ZERO != opEntity.get().getCrTxnCap()) {
                if (dto.getCjnReserve().doubleValue() > opEntity.get().getCrTxnCap().doubleValue()) {
                    validationErrors.add(new ValidationError("", "validation", "insufficient-txn-cap"));
                    return validationErrors;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
                String now = sdf.format(Calendar.getInstance().getTime());
                TCkCreditSummaryMonth month = ckCreditSummaryMonthDao.getByServiceTypeAndAccnAndCcyAndDate(dto.getTCkMstServiceType(), dto.getTCoreAccn(), dto.getTMstCurrency(), now);
                Optional<TCkCreditSummaryMonth> opMonth = Optional.ofNullable(month);
                if (opMonth.isPresent() && entity.getCrTxnCap().subtract(month.getCrsmReserve().add(month.getCrsmUtilized())).doubleValue() < dto.getCjnReserve().doubleValue()) {
                    validationErrors.add(new ValidationError("", "validation", "insufficient-month-txn-cap"));
                    return validationErrors;
                }
            }

            TCkCreditSummary summary = ckCreditSummaryDao.getByServiceTypeAndAccnAndCcy(dto.getTCkMstServiceType(), dto.getTCoreAccn(), dto.getTMstCurrency());
            Optional<TCkCreditSummary> opSummary = Optional.ofNullable(summary);
            if (!opSummary.isPresent()) {
                validationErrors.add(new ValidationError("", "validation", "credit-summary-not-found"));
                return validationErrors;
            }
            
            if (dto.getCjnReserve().doubleValue() > opSummary.get().getCrsBalance().doubleValue()) {
                validationErrors.add(new ValidationError("", "validation", "insufficient-balance"));
                return validationErrors;
            }

            return null;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<ValidationError> validateReverse(CkCreditJournal dto, Principal principal) throws ParameterException, ProcessingException {
        log.debug("validateReverse");
        try {
            List<ValidationError> validationErrors = new ArrayList<>();
            TCkCredit entity = ckCreditDao.getByServiceTypeAndAccnAndCcy(dto.getTCkMstServiceType(), dto.getTCoreAccn(), dto.getTMstCurrency());
            Optional<TCkCredit> opEntity = Optional.ofNullable(entity);
            if (!opEntity.isPresent()) {
                validationErrors.add(new ValidationError("", "validation", "credit-not-found"));
                return validationErrors;
            } 

            TCkCreditSummary summary = ckCreditSummaryDao.getByServiceTypeAndAccnAndCcy(dto.getTCkMstServiceType(), dto.getTCoreAccn(), dto.getTMstCurrency());
            Optional<TCkCreditSummary> opSummary = Optional.ofNullable(summary);
            if (!opSummary.isPresent()) {
                validationErrors.add(new ValidationError("", "validation", "credit-summary-not-found"));
                return validationErrors;
            }

            return null;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<ValidationError> validateUtilize(CkCreditJournal dto, Principal principal) throws ParameterException, ProcessingException {
        log.debug("validateUtilize");
        try {
            List<ValidationError> validationErrors = new ArrayList<>();
            TCkCredit entity = ckCreditDao.getByServiceTypeAndAccnAndCcy(dto.getTCkMstServiceType(), dto.getTCoreAccn(), dto.getTMstCurrency());
            Optional<TCkCredit> opEntity = Optional.ofNullable(entity);
            if (!opEntity.isPresent()) {
                validationErrors.add(new ValidationError("", "validation", "credit-not-found"));
                return validationErrors;
            } 
            
            if (dto.getCjnUtilized().doubleValue() > opEntity.get().getCrAmt().doubleValue()) {
                validationErrors.add(new ValidationError("", "validation", "insufficient-credit"));
                return validationErrors;
            }
            
            /* if (null != opEntity.get().getCrTxnCap() && BigDecimal.ZERO != opEntity.get().getCrTxnCap()) {
                if (dto.getCjnUtilized().doubleValue() > opEntity.get().getCrTxnCap().doubleValue()) {
                    validationErrors.add(new ValidationError("", "validation", "insufficient-txn-cap"));
                    return validationErrors;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
                String now = sdf.format(Calendar.getInstance().getTime());
                TCkCreditSummaryMonth month = ckCreditSummaryMonthDao.getByServiceTypeAndAccnAndCcyAndDate(dto.getTCkMstServiceType(), dto.getTCoreAccn(), dto.getTMstCurrency(), now);
                Optional<TCkCreditSummaryMonth> opMonth = Optional.ofNullable(month);
                if (opMonth.isPresent() && month.getCrsmReserve().add(month.getCrsmUtilized()).doubleValue() < dto.getCjnUtilized().doubleValue()) {
                    validationErrors.add(new ValidationError("", "validation", "insufficient-month-txn-cap"));
                    return validationErrors;
                }
            } */

            TCkCreditSummary summary = ckCreditSummaryDao.getByServiceTypeAndAccnAndCcy(dto.getTCkMstServiceType(), dto.getTCoreAccn(), dto.getTMstCurrency());
            Optional<TCkCreditSummary> opSummary = Optional.ofNullable(summary);
            if (!opSummary.isPresent()) {
                validationErrors.add(new ValidationError("", "validation", "credit-summary-not-found"));
                return validationErrors;
            }
            
            if (opSummary.get().getCrsBalance().doubleValue() < 0 ) {
                validationErrors.add(new ValidationError("", "validation", "insufficient-balance"));
                return validationErrors;
            }

            return null;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<ValidationError> validatePay(CkCreditJournal dto, Principal principal) throws ParameterException, ProcessingException {
        log.debug("validatePay");
        try {
            List<ValidationError> validationErrors = new ArrayList<>();
            TCkCredit entity = ckCreditDao.getByServiceTypeAndAccnAndCcy(dto.getTCkMstServiceType(), dto.getTCoreAccn(), dto.getTMstCurrency());
            Optional<TCkCredit> opEntity = Optional.ofNullable(entity);
            if (!opEntity.isPresent()) {
                validationErrors.add(new ValidationError("", "validation", "credit-not-found"));
                return validationErrors;
            } 
            
            TCkCreditSummary summary = ckCreditSummaryDao.getByServiceTypeAndAccnAndCcy(dto.getTCkMstServiceType(), dto.getTCoreAccn(), dto.getTMstCurrency());
            Optional<TCkCreditSummary> opSummary = Optional.ofNullable(summary);
            if (!opSummary.isPresent()) {
                validationErrors.add(new ValidationError("", "validation", "credit-summary-not-found"));
                return validationErrors;
            }

            return null;
        } catch (Exception e) {
            throw e;
        }
    }
    
}
