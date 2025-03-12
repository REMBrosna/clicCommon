package com.guudint.clickargo.credit.service;

import java.util.List;

import com.guudint.clickargo.common.model.ValidationError;
import com.guudint.clickargo.credit.dto.CkCredit;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;

public interface ICreditValidation {
    
    public List<ValidationError> validationCreateCredit(CkCredit dto, Principal principal) throws ParameterException, ProcessingException, Exception;
    
    public List<ValidationError> validationVerifyCredit(CkCredit dto, Principal principal) throws ParameterException, ProcessingException, Exception;
    
    public List<ValidationError> validationApproveCredit(CkCredit dto, Principal principal) throws ParameterException, ProcessingException, Exception;
    
    public List<ValidationError> validationRejectCredit(CkCredit dto, Principal principal) throws ParameterException, ProcessingException, Exception;
    
    public List<ValidationError> validationSuspendCredit(CkCredit dto, Principal principal) throws ParameterException, ProcessingException, Exception;
    
    public List<ValidationError> validationUnsuspendCredit(CkCredit dto, Principal principal) throws ParameterException, ProcessingException, Exception;

}
