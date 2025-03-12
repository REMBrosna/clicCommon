package com.guudint.clickargo.manageaccn.dao;

import java.util.List;
import java.util.Optional;

import com.vcc.camelone.ccm.model.TCoreAccn;
import com.vcc.camelone.common.dao.GenericDao;

public interface CkCoreAccnDao extends GenericDao<TCoreAccn, String> {

    Optional<TCoreAccn> findByEmail(String email)throws Exception;

    List<TCoreAccn> findByAccnName(String accnName)throws Exception;
}
