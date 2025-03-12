package com.guudint.clickargo.clicservice.dao;

import java.util.List;
import java.util.Optional;

import com.guudint.clickargo.clicservice.model.TCkSvcWorkflow;
import com.vcc.camelone.common.dao.GenericDao;

public interface CkSvcWorkflowDao extends GenericDao<TCkSvcWorkflow, String> {

    Optional<TCkSvcWorkflow> findToState(String action, String fromState, String serviceType, String appsCode,
            String accnType, List<String> roles, String wkfId) throws Exception;
}
