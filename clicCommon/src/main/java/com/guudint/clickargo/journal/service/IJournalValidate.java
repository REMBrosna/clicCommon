package com.guudint.clickargo.journal.service;

import java.util.List;

import com.guudint.clickargo.common.model.ValidationError;
import com.guudint.clickargo.journal.dto.CkCreditJournal;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;

public interface IJournalValidate {
    
    public List<ValidationError> validateReserve(CkCreditJournal dto, Principal principal) throws ParameterException, ProcessingException;
    
    public List<ValidationError> validateReverse(CkCreditJournal dto, Principal principal) throws ParameterException, ProcessingException;
    
    public List<ValidationError> validateUtilize(CkCreditJournal dto, Principal principal) throws ParameterException, ProcessingException;
    
    public List<ValidationError> validatePay(CkCreditJournal dto, Principal principal) throws ParameterException, ProcessingException;
}
