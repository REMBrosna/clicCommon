package com.guudint.clickargo.common.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.guudint.clickargo.common.RecordStatus;
import com.guudint.clickargo.common.dao.CkWhitelabelDao;
import com.guudint.clickargo.common.model.TCkWhitelabel;
import com.vcc.camelone.common.dao.impl.GenericDaoImpl;

public class CkWhitelabelDaoImpl extends GenericDaoImpl<TCkWhitelabel, String> implements CkWhitelabelDao {

	@Override
	public TCkWhitelabel findByName(String name) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(TCkWhitelabel.class);
		criteria.add(Restrictions.eq("wlName", name));
		criteria.add(Restrictions.eq("wlStatus", RecordStatus.ACTIVE.getCode()));
		return getOne(criteria);

	}

	@Override
	public TCkWhitelabel findByAccnId(String accnId) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(TCkWhitelabel.class);
		criteria.add(Restrictions.eq("TCoreAccn.accnId", accnId));
		criteria.add(Restrictions.eq("wlStatus", RecordStatus.ACTIVE.getCode()));
		return getOne(criteria);
	}

}
