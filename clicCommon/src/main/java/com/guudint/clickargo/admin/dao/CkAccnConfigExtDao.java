package com.guudint.clickargo.admin.dao;

import java.util.List;

import com.guudint.clickargo.admin.dto.CkAccnConfigTypesEnum;
import com.guudint.clickargo.admin.model.TCkAccnConfigExt;
import com.guudint.clickargo.admin.model.TCkAccnConfigExtId;
import com.guudint.clickargo.master.enums.ServiceTypes;
import com.vcc.camelone.common.dao.GenericDao;

public interface CkAccnConfigExtDao extends GenericDao<TCkAccnConfigExt, TCkAccnConfigExtId> {

	public List<TCkAccnConfigExt> getAccnConfigExt(String accnId, CkAccnConfigTypesEnum accnConfigType, List<String> roles,
			ServiceTypes serviceTypes) throws Exception;
}
