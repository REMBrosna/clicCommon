package com.guudint.clickargo.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.common.RecordStatus;
import com.guudint.clickargo.common.model.TCkCsAccn;
import com.guudint.clickargo.common.service.ICkCsAccnService;
import com.guudint.clickargo.master.enums.ServiceTypes;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;

@Service
public class CkCsAccnServiceImpl implements ICkCsAccnService {

	@Autowired
	@Qualifier("ckCsAccnDao")
	private GenericDao<TCkCsAccn, String> ckCsAccnDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public List<CoreAccn> getAssociatedAccounts(String csUsrUid, ServiceTypes serviceType)
			throws ParameterException, EntityNotFoundException, Exception {
		
		List<CoreAccn> listCsAccn = new ArrayList<>();
		if (StringUtils.isBlank(csUsrUid))
			throw new ParameterException("param csUsrUid null or empty");

		if (serviceType == null)
			throw new ParameterException("param serviceType null");

		String hql = "from TCkCsAccn o where o.TCkMstServiceType.svctId=:serviceType and o.TCoreUsr.usrUid=:usrUid and o.csaStatus=:status";
		Map<String, Object> params = new HashMap<>();
		params.put("serviceType", serviceType.getId());
		params.put("usrUid", csUsrUid);
		params.put("status", RecordStatus.ACTIVE.getCode());

		List<TCkCsAccn> list = ckCsAccnDao.getByQuery(hql, params);
		if(list != null && list.size() > 0) {
			for(TCkCsAccn csAccn : list) {
				Hibernate.initialize(csAccn.getTCkMstServiceType());
				Hibernate.initialize(csAccn.getTCoreAccn());
				Hibernate.initialize(csAccn.getTCoreUsr());
				
				CoreAccn accn = new CoreAccn(csAccn.getTCoreAccn());
				listCsAccn.add(accn);
			}
		}
		
		return listCsAccn;

	}

}
