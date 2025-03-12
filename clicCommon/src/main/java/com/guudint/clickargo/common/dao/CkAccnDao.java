package com.guudint.clickargo.common.dao;

import com.guudint.clickargo.common.model.TCkAccn;
import com.vcc.camelone.common.dao.GenericDao;

public interface CkAccnDao extends GenericDao<TCkAccn, String>{

	TCkAccn findByAccnId(String accnId, Character status) throws Exception;
	Boolean findByAccnIdSubscribed (String accnId, Character status, String notificationType) throws Exception;

}
