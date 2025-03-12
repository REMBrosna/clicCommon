package com.guudint.clickargo.manageaccn.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.guudint.clickargo.manageaccn.dao.CkMstAccnAttTypeDao;
import com.guudint.clickargo.manageaccn.dto.CkMstAccnAttTypeId;
import com.guudint.clickargo.manageaccn.model.TCkMstAccnAttType;
import com.guudint.clickargo.manageaccn.model.TCkMstAccnAttTypeId;
import com.vcc.camelone.common.dao.impl.GenericDaoImpl;

public class CkMstAccnAttTypeDaoImpl extends GenericDaoImpl<TCkMstAccnAttType, TCkMstAccnAttTypeId> implements CkMstAccnAttTypeDao{

	@Override
	public TCkMstAccnAttType findById(CkMstAccnAttTypeId accnAttTypeId) throws Exception {
        try {
            DetachedCriteria criteria = DetachedCriteria.forClass(TCkMstAccnAttType.class);
            criteria.add(Restrictions.eq("id.atId", accnAttTypeId.getAtId()));
            criteria.add(Restrictions.eq("id.atWorkflow", accnAttTypeId.getAtWorkflow()));
            return getOne(criteria);
        } catch (Exception e) {
            return null;
        }
	}
	
}
