package com.guudint.clickargo.common.service;

import java.util.List;

import com.guudint.clickargo.master.enums.ServiceTypes;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;

public interface ICkCsAccnService {

	public List<CoreAccn> getAssociatedAccounts(String csUsrUid, ServiceTypes serviceType)
			throws ParameterException, EntityNotFoundException, Exception;
}
