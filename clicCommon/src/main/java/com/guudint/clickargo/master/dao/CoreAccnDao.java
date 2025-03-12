package com.guudint.clickargo.master.dao;

import java.util.Collection;
import java.util.List;

import com.guudint.clickargo.master.enums.AccountTypes;
import com.vcc.camelone.ccm.model.TCoreAccn;

public interface CoreAccnDao extends com.vcc.camelone.ccm.dao.CoreAccnDao {

	TCoreAccn getByAccnIdAndStatus(String accnId, Character status);

	Collection<TCoreAccn> getByAccnTypeAndStatus(AccountTypes accnType, Character status);

	List<TCoreAccn> fetchAccnWithoutInAccnConfig(List<String> accnTypeList, String accnConfigKey) throws Exception;

	TCoreAccn findAccnByUen(String uen) throws Exception;

	List<TCoreAccn> findAllByUen(String uen) throws Exception;
}
