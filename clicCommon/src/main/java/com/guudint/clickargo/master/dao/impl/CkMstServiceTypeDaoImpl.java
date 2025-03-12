/**
 *
 */
package com.guudint.clickargo.master.dao.impl;

import com.guudint.clickargo.common.RecordStatus;
import com.guudint.clickargo.master.dao.CkMstServiceTypeDao;
import com.guudint.clickargo.master.model.TCkMstServiceType;
import com.vcc.camelone.common.dao.impl.GenericDaoImpl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

public class CkMstServiceTypeDaoImpl extends GenericDaoImpl<TCkMstServiceType, String> implements CkMstServiceTypeDao {

	@Override
	@Transactional(readOnly = true)
	public TCkMstServiceType getByIdAndStatus(String id, Character status) {
		try {
			DetachedCriteria dc = DetachedCriteria.forClass(TCkMstServiceType.class);
			dc.add(Restrictions.eq("svctId", id));
			dc.add(Restrictions.eq("svctStatus", status));
			return getOne(dc);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<TCkMstServiceType> getAllActive() {
		try {
			DetachedCriteria dc = DetachedCriteria.forClass(TCkMstServiceType.class);
			dc.add(Restrictions.eq("svctStatus", RecordStatus.ACTIVE.getCode()));
			return getByCriteria(dc);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
