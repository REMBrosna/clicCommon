package com.guudint.clickargo.manageaccn.dao;

import java.util.List;

import com.guudint.clickargo.manageaccn.model.TCkCtFfCo;
import com.vcc.camelone.common.dao.GenericDao;

public interface CkCtFfCoDao extends GenericDao<TCkCtFfCo, String> {
	
	public List<TCkCtFfCo> getAccnByFfAccnId(String ffAccnId) throws Exception;
}
