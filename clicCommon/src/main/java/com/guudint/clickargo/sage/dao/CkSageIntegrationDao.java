package com.guudint.clickargo.sage.dao;

import java.util.Date;
import java.util.List;

import com.guudint.clickargo.sage.model.TCkSageIntegration;
import com.vcc.camelone.common.dao.GenericDao;

public interface CkSageIntegrationDao extends GenericDao<TCkSageIntegration, String> {
	
	public List<TCkSageIntegration> findByTypeAndDate(String sageIntTypeId, Date sintDtStart, Date sintDtEnd)
			throws Exception;
}
