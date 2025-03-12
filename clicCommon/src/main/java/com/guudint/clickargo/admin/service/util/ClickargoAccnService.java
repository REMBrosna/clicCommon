package com.guudint.clickargo.admin.service.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.common.RecordStatus;
import com.guudint.clickargo.master.enums.AccountTypes;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.ccm.dto.CoreAddress;
import com.vcc.camelone.ccm.dto.CoreContact;
import com.vcc.camelone.ccm.model.TCoreAccn;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.master.dto.MstAccnType;
import com.vcc.camelone.master.dto.MstCountry;

/**
 * Core account extension for clickargo.
 */
@Service
public class ClickargoAccnService {

	@Autowired
	private GenericDao<TCoreAccn, String> coreAccnDao;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CoreAccn getAccountByType(AccountTypes accnType) throws Exception {
		if (accnType == null)
			throw new ParameterException("param accnType null");

		String hql = "from TCoreAccn o where o.TMstAccnType.atypId=:accnType and o.accnStatus=:status";
		Map<String, Object> params = new HashMap<>();
		params.put("accnType", accnType.name());
		params.put("status", RecordStatus.ACTIVE.getCode());
		List<TCoreAccn> accnList = coreAccnDao.getByQuery(hql, params);
		if (accnList != null && accnList.size() > 0) {
			// expecting only one
			TCoreAccn accnEntity = accnList.get(0);
			Hibernate.initialize(accnEntity.getTMstAccnType());
			Hibernate.initialize(accnEntity.getAccnAddr());
			if (accnEntity.getAccnAddr() != null)
				Hibernate.initialize(accnEntity.getAccnAddr().getAddrCtry());
			Hibernate.initialize(accnEntity.getAccnContact());
			CoreAccn dto = new CoreAccn(accnEntity);
			dto.setTMstAccnType(new MstAccnType(accnEntity.getTMstAccnType()));
			dto.setAccnAddr(new CoreAddress(accnEntity.getAccnAddr()));
			dto.getAccnAddr().setAddrCtry(new MstCountry(accnEntity.getAccnAddr().getAddrCtry()));
			dto.setAccnContact(new CoreContact(accnEntity.getAccnContact()));
			return dto;

		}
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String getServiceProvider() throws Exception {
		StringBuilder str = new StringBuilder();
		CoreAccn spAccn = this.getAccountByType(AccountTypes.ACC_TYPE_SP);
		if (spAccn != null) {
			CoreAddress accnAddr = spAccn.getAccnAddr();
			str.append(spAccn.getAccnName()).append(" ").append(accnAddr.getAddrCity()).append(" ")
					.append(accnAddr.getAddrPcode()).append(", ").append(accnAddr.getAddrCtry().getCtyDescription());
		}

		return str.toString();
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CoreAccn getServiceProviderAccn() throws Exception {
		CoreAccn spAccn = this.getAccountByType(AccountTypes.ACC_TYPE_SP);
		return spAccn;
	}

	public boolean isAccountSuspended(CoreAccn accn) throws Exception {
		if (accn != null)
			return accn.getAccnStatus() == RecordStatus.SUSPENDED.getCode();
		return false;
	}
	
	public boolean isAccountSuspended(String accnId) throws Exception {
		if(StringUtils.isNotBlank(accnId)) {
			TCoreAccn accnE = coreAccnDao.find(accnId);
			if(accnE != null && accnE.getAccnStatus() == RecordStatus.SUSPENDED.getCode()) {
				return true;
			}
		}
		
		return false;
			
	}
}
