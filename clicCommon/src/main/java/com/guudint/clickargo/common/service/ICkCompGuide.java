package com.guudint.clickargo.common.service;

import java.util.List;

import com.guudint.clickargo.common.dto.CkComponentGuide;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;

public interface ICkCompGuide {

	/**
	 * @param componentIdPath
	 * @return
	 * @throws ParameterException
	 * @throws ProcessingException
	 * @throws Exception
	 */
	public List<CkComponentGuide> getCompGuides(String componentIdPath, Principal principal)
			throws ParameterException, ProcessingException, Exception;
}
