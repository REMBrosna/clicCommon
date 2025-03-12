package com.guudint.clickargo.manageaccn.dao;

import com.guudint.clickargo.manageaccn.dto.CkMstAccnAttTypeId;
import com.guudint.clickargo.manageaccn.model.TCkMstAccnAttType;
import com.guudint.clickargo.manageaccn.model.TCkMstAccnAttTypeId;
import com.vcc.camelone.common.dao.GenericDao;

public interface CkMstAccnAttTypeDao extends GenericDao<TCkMstAccnAttType, TCkMstAccnAttTypeId> {
	
	 public TCkMstAccnAttType findById(CkMstAccnAttTypeId accnAttTypeId) throws Exception;
}
