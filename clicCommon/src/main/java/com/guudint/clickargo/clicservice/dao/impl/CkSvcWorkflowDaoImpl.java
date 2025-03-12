package com.guudint.clickargo.clicservice.dao.impl;

import java.util.List;
import java.util.Optional;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.guudint.clickargo.clicservice.dao.CkSvcWorkflowDao;
import com.guudint.clickargo.clicservice.model.TCkSvcWorkflow;
import com.guudint.clickargo.common.RecordStatus;
import com.vcc.camelone.common.dao.impl.GenericDaoImpl;

public class CkSvcWorkflowDaoImpl extends GenericDaoImpl<TCkSvcWorkflow, String> implements CkSvcWorkflowDao {

    @Override
    public Optional<TCkSvcWorkflow> findToState(String action, String fromState, String serviceType, String appsCode,
            String accnType, List<String> roles, String wkfId) throws Exception {
        DetachedCriteria criteria = DetachedCriteria.forClass(TCkSvcWorkflow.class);
        criteria.add(Restrictions.eq("TCkMstFormAction.fmactId", action));
        criteria.add(Restrictions.eq("TCkMstJobStateByWkflFromState.jbstId", fromState));
        criteria.add(Restrictions.eq("TCkMstServiceType.svctId", serviceType));
        criteria.add(Restrictions.eq("TCoreApps.appsCode", appsCode));
        criteria.add(Restrictions.eq("TMstAccnType.atypId", accnType));
        criteria.add(Restrictions.in("TCoreRole.id.roleId", roles));
        criteria.add(Restrictions.eq("wkflStatus", RecordStatus.ACTIVE.getCode()));
        criteria.add(Restrictions.like("wkflId", wkfId + "%"));
        return Optional.ofNullable(getOne(criteria));
    }

}
