package com.guudint.clickargo.admin.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.guudint.clickargo.admin.dao.CkAccnConfigExtDao;
import com.guudint.clickargo.admin.dto.CkAccnConfigTypesEnum;
import com.guudint.clickargo.admin.model.TCkAccnConfigExt;
import com.guudint.clickargo.admin.model.TCkAccnConfigExtId;
import com.guudint.clickargo.common.RecordStatus;
import com.guudint.clickargo.master.enums.ServiceTypes;
import com.vcc.camelone.common.dao.impl.GenericDaoImpl;

public class CkAccnConfigExtDaoImpl extends GenericDaoImpl<TCkAccnConfigExt, TCkAccnConfigExtId>
		implements CkAccnConfigExtDao {

	@Override
	public List<TCkAccnConfigExt> getAccnConfigExt(String accnId, CkAccnConfigTypesEnum accnConfigType,
			List<String> roles, ServiceTypes serviceTypes) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(TCkAccnConfigExt.class);
		criteria.add(Restrictions.eq("id.caeAccnId", accnId));
		criteria.add(Restrictions.eq("id.caeType", accnConfigType.name()));
		criteria.add(Restrictions.in("id.caeRoleId", roles));
		criteria.add(Restrictions.eq("id.caeAppsCode", serviceTypes.getAppsCode()));
		criteria.add(Restrictions.eq("caeStatus", RecordStatus.ACTIVE.getCode()));
		return getByCriteria(criteria);
	}

}
