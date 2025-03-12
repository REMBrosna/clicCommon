package com.guudint.clickargo.validator;

import java.util.List;

import org.springframework.stereotype.Component;

import com.guudint.clickargo.common.model.ValidationError;
import com.guudint.clickargo.job.service.IJobValidate;
import com.guudint.clickargo.manageaccn.dto.CkCtFfCo;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;

@Component
public class CkCtFfCoValidator implements IJobValidate<CkCtFfCo> {

	@Override
	public List<ValidationError> validateCreate(CkCtFfCo dto, Principal principal)
			throws ParameterException, ProcessingException {
		
		return null;
	}

	@Override
	public List<ValidationError> validateUpdate(CkCtFfCo dto, Principal principal)
			throws ParameterException, ProcessingException {
		
		return null;
	}

	@Override
	public List<ValidationError> validateSubmit(CkCtFfCo dto, Principal principal)
			throws ParameterException, ProcessingException {
		
		return null;
	}

	@Override
	public List<ValidationError> validateReject(CkCtFfCo dto, Principal principal)
			throws ParameterException, ProcessingException {
		
		return null;
	}

	@Override
	public List<ValidationError> validateCancel(CkCtFfCo dto, Principal principal)
			throws ParameterException, ProcessingException {
		
		return null;
	}

	@Override
	public List<ValidationError> validateDelete(CkCtFfCo dto, Principal principal)
			throws ParameterException, ProcessingException {
		
		return null;
	}

	@Override
	public List<ValidationError> validateConfirm(CkCtFfCo dto, Principal principal)
			throws ParameterException, ProcessingException {
		
		return null;
	}

	@Override
	public List<ValidationError> validatePay(CkCtFfCo dto, Principal principal)
			throws ParameterException, ProcessingException {
		
		return null;
	}

	@Override
	public List<ValidationError> validatePaid(CkCtFfCo dto, Principal principal)
			throws ParameterException, ProcessingException {
		
		return null;
	}

	@Override
	public List<ValidationError> validateComplete(CkCtFfCo dto, Principal principal)
			throws ParameterException, ProcessingException {
		
		return null;
	}
}
