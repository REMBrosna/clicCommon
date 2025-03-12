package com.guudint.clickargo.clicservice.dao.impl;

import java.util.List;
import java.util.Optional;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.guudint.clickargo.clicservice.dao.CkSvcActionMaskDao;
import com.guudint.clickargo.clicservice.model.TCkSvcActionMask;
import com.vcc.camelone.common.dao.impl.GenericDaoImpl;

public class CkSvcActionMaskDaoImpl extends GenericDaoImpl<TCkSvcActionMask, String> implements CkSvcActionMaskDao {

    @Override
    public List<TCkSvcActionMask> findByAppsCodeAndAccnTypeAndRoleAndScvTypeAndStateAndStatus(String appsCode,
            String accnType, String role, String scvType, List<String> state, Character status) throws Exception {
        DetachedCriteria criteria = DetachedCriteria.forClass(TCkSvcActionMask.class);
        criteria.add(Restrictions.eq("TCoreApps.appsCode", appsCode));
        criteria.add(Restrictions.eq("TMstAccnType.atypId", accnType));
        criteria.add(Restrictions.eq("TCoreRole.id.roleId", role));
        criteria.add(Restrictions.eq("TCkMstServiceType.svctId", scvType));
        criteria.add(Restrictions.in("TCkMstJobState.jbstId", state));
        criteria.add(Restrictions.eq("samStatus", status));
        return getByCriteria(criteria);
    }

    @Override
    public Optional<TCkSvcActionMask> findByAppsCodeAndAccnTypeAndRoleAndScvTypeAndStateAndStatus(String appsCode,
            String accnType, String role, String scvType, String state, Character status) throws Exception {
        DetachedCriteria criteria = DetachedCriteria.forClass(TCkSvcActionMask.class);
        criteria.add(Restrictions.eq("TCoreApps.appsCode", appsCode));
        criteria.add(Restrictions.eq("TMstAccnType.atypId", accnType));
        criteria.add(Restrictions.eq("TCoreRole.id.roleId", role));
        criteria.add(Restrictions.eq("TCkMstServiceType.svctId", scvType));
        criteria.add(Restrictions.eq("TCkMstJobState.jbstId", state));
        criteria.add(Restrictions.eq("samStatus", status));
        return Optional.ofNullable(getOne(criteria));
    }

}
