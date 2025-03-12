package com.guudint.clickargo.master.service;

import java.util.HashMap;

import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;

public interface ICkMstRecords {

	/**
	 * @param recordType
	 * @return
	 * @throws ProcessingException
	 * @throws Exception
	 */
	public HashMap<String, Object> getRecords(String recordType) throws ParameterException, ProcessingException, Exception; 
}
