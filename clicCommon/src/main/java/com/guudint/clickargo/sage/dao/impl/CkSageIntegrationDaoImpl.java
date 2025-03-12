package com.guudint.clickargo.sage.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.guudint.clickargo.sage.dao.CkSageIntegrationDao;
import com.guudint.clickargo.sage.model.TCkSageIntegration;
import com.vcc.camelone.common.dao.impl.GenericDaoImpl;

@Service("ckSageIntegrationDao")
public class CkSageIntegrationDaoImpl extends GenericDaoImpl<TCkSageIntegration, String>
		implements CkSageIntegrationDao {

	@Override
	public List<TCkSageIntegration> findByTypeAndDate(String sageIntTypeId, Date sintDtStart, Date sintDtEnd)
			throws Exception {

		DetachedCriteria criteria = DetachedCriteria.forClass(TCkSageIntegration.class);

		criteria.add(Restrictions.eq("TCkCtMstSageIntType.sitId", sageIntTypeId));
		criteria.add(Restrictions.eq("sintDtStart", sintDtStart));
		criteria.add(Restrictions.eq("sintDtEnd", sintDtEnd));

		return super.getByCriteria(criteria);
	}

}
