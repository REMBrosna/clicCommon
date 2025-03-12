package com.guudint.clickargo.clicservice.dao;

import java.util.List;
import java.util.Optional;

import com.guudint.clickargo.clicservice.model.TCkSvcActionMask;
import com.vcc.camelone.common.dao.GenericDao;

public interface CkSvcActionMaskDao extends GenericDao<TCkSvcActionMask, String> {

    List<TCkSvcActionMask> findByAppsCodeAndAccnTypeAndRoleAndScvTypeAndStateAndStatus(String appsCode, String accnType,
            String role, String scvType, List<String> state, Character status) throws Exception;
    Optional<TCkSvcActionMask> findByAppsCodeAndAccnTypeAndRoleAndScvTypeAndStateAndStatus(String appsCode, String accnType,
            String role, String scvType, String state, Character status) throws Exception;
}
