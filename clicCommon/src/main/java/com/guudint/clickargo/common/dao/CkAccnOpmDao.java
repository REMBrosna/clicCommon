package com.guudint.clickargo.common.dao;

import java.util.List;

import com.guudint.clickargo.common.model.TCkAccnOpm;
import com.vcc.camelone.common.dao.GenericDao;

public interface CkAccnOpmDao extends GenericDao<TCkAccnOpm, String> {

	TCkAccnOpm findByAccnId(String accnId, Character status) throws Exception;
	
	TCkAccnOpm findByAccnId(String accnId, List<Character> status) throws Exception;

	TCkAccnOpm findByAccnId(String accnId) throws Exception;
}
