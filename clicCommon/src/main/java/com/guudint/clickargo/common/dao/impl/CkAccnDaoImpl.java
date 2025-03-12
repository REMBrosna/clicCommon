package com.guudint.clickargo.common.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.guudint.clickargo.common.dao.CkAccnDao;
import com.guudint.clickargo.common.model.TCkAccn;
import com.vcc.camelone.common.dao.impl.GenericDaoImpl;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

public class CkAccnDaoImpl extends GenericDaoImpl<TCkAccn, String> implements CkAccnDao {

	@Override
	public TCkAccn findByAccnId(String accnId, Character status) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(TCkAccn.class);
		criteria.add(Restrictions.eq("TCoreAccn.accnId", accnId));
		criteria.add(Restrictions.eq("caccnStatus", status));
		return getOne(criteria);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public Boolean findByAccnIdSubscribed(String accnId, Character status, String notificationType) throws Exception {
		if ("EMAIL".equalsIgnoreCase(notificationType)) {
			return true;
		}

		DetachedCriteria criteria = DetachedCriteria.forClass(TCkAccn.class);
		criteria.createAlias("TCoreAccn", "coreAccn");
		criteria.add(Restrictions.eq("coreAccn.accnId", accnId));
		criteria.add(Restrictions.eq("caccnStatus", status));

		// Retrieve the result list
		TCkAccn result = getOne(criteria);

		if (Objects.nonNull(result)) {
			TCkAccn accn = getOne(criteria);

			// Check the notification type
			if ("WHATSAPP".equalsIgnoreCase(notificationType)) {
				return accn.getCaccnWhatsapp();
			} else if ("SMS".equalsIgnoreCase(notificationType)) {
				return accn.getCaccnSms();
			} else {
				return Boolean.TRUE.equals(accn.getCaccnWhatsapp()) || Boolean.TRUE.equals(accn.getCaccnSms());
			}
		}
		return false;
	}


}
