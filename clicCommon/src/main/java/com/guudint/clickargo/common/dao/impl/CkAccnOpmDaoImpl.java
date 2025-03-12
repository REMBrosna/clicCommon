package com.guudint.clickargo.common.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.guudint.clickargo.common.dao.CkAccnOpmDao;
import com.guudint.clickargo.common.model.TCkAccnOpm;
import com.vcc.camelone.common.dao.impl.GenericDaoImpl;

@Service
public class CkAccnOpmDaoImpl extends GenericDaoImpl<TCkAccnOpm, String> implements CkAccnOpmDao {

	@Override
	public TCkAccnOpm findByAccnId(String accnId, Character status) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(TCkAccnOpm.class);
		criteria.add(Restrictions.eq("TCoreAccn.accnId", accnId));
		criteria.add(Restrictions.eq("caoStatus", status));
		return getOne(criteria);
	}

	@Override
	public TCkAccnOpm findByAccnId(String accnId) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(TCkAccnOpm.class);
		criteria.add(Restrictions.eq("TCoreAccn.accnId", accnId));
		return getOne(criteria);
	}

	@Override
	public TCkAccnOpm findByAccnId(String accnId, List<Character> status) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(TCkAccnOpm.class);
		criteria.add(Restrictions.eq("TCoreAccn.accnId", accnId));
		criteria.add(Restrictions.in("caoStatus", status));
		return getOne(criteria);
	}

}
