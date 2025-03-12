package com.guudint.clickargo.common.dao;

import com.guudint.clickargo.common.model.TCkWhitelabel;
import com.vcc.camelone.common.dao.GenericDao;

public interface CkWhitelabelDao extends GenericDao<TCkWhitelabel, String>{

	TCkWhitelabel findByName(String name) throws Exception;
	
	TCkWhitelabel findByAccnId(String accnId) throws Exception;
}
