package com.guudint.clickargo.common.service;

import java.util.List;

import com.guudint.clickargo.common.dto.CkFormControlDto;
import com.guudint.clickargo.common.dto.CkFormControlReqDto;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;

public interface ICkFormControlService {

	List<CkFormControlDto> getControls(CkFormControlReqDto reqDto, Principal principal)
			throws ParameterException, ProcessingException, Exception;
	
	List<CkFormControlDto> getControls(CkFormControlReqDto reqDto, String accnId, String roles)
			throws ParameterException, ProcessingException, Exception;
}
