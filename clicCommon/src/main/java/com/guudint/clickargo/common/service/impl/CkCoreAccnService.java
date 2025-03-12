package com.guudint.clickargo.common.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.common.RecordStatus;
import com.guudint.clickargo.common.dto.CkAccn;
import com.guudint.clickargo.common.model.TCkAccn;
import com.guudint.clickargo.manageaccn.dto.CkManageAccn;
import com.guudint.clickargo.master.dao.CoreAccnConfigDao;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.ccm.model.TCoreAccnConfig;
import com.vcc.camelone.ccm.model.TCoreAccnConfigId;
import com.vcc.camelone.ccm.util.Constant;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.master.dto.MstAccnType;
import com.vcc.camelone.master.model.TMstBank;

/**
 * All account related services.
 */
@Service
public class CkCoreAccnService {

	// Static Attributes
	////////////////////
	private static Logger log = Logger.getLogger(CkCoreAccnService.class);

	@Autowired
	private CoreAccnConfigDao coreAccnConfigDao;

	@Autowired
	private GenericDao<TCkAccn, String> ckAccnDao;

	@Autowired
	@Qualifier("mstBankDao")
	private GenericDao<TMstBank, String> mstBankDao;



	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public TCoreAccnConfig getAccnConfig(String accnId, String key) throws Exception {
		if (StringUtils.isBlank(accnId))
			throw new ParameterException("param accnId null or empty");

		TCoreAccnConfigId coreAccnConfigId = new TCoreAccnConfigId();
		coreAccnConfigId.setAcfgAccnid(accnId);
		coreAccnConfigId.setAcfgKey(key);
		TCoreAccnConfig accnConfig = coreAccnConfigDao.getByIdAndStatus(coreAccnConfigId, Constant.ACTIVE_STATUS);

		//log.info("accnConfig: " + accnConfig);
		// will not proceed if no record found
		// Do not throw excetpion
		//if (Objects.isNull(accnConfig))
		//	throw new ProcessingException("No active record found in TCoreAccnConfig for key " + key + " for id "
		//			+ accnId + ". Please check the configuration.");
		return accnConfig;
	}

	public String getPrincipalAccountType(Principal principal) throws Exception {
		if (principal == null)
			throw new ProcessingException("principal null");

		CoreAccn accn = principal.getCoreAccn();
		if (accn == null)
			throw new ProcessingException("principal account null");
		Optional<MstAccnType> opAccnType = Optional.ofNullable(accn.getTMstAccnType());
		if (opAccnType.isPresent())
			return opAccnType.get().getAtypId();
		else
			throw new ProcessingException("account type not configured for " + accn.getAccnId());

	}

	public CkAccn updateCkAccnDetails(String accnId, CkManageAccn accnDto, Principal principal, boolean isUpdate)
			throws Exception {

		TCkAccn ckAccnEntity = ckAccnDao.find(accnId);
		if (ckAccnEntity == null) {
			ckAccnEntity = new TCkAccn();
			ckAccnEntity.setCaccnId(accnId);

			ckAccnEntity.setCaccnDtCreate(new Date());
			ckAccnEntity.setCaccnUidCreate(principal.getUserId());

			ckAccnEntity.setCaccnDtLupd(new Date());
			ckAccnEntity.setCaccnUidLupd(principal.getUserId());
			ckAccnEntity.setCaccnStatus(RecordStatus.ACTIVE.getCode());
		} else {
			ckAccnEntity.setCaccnDtLupd(new Date());
			ckAccnEntity.setCaccnUidLupd(principal.getUserId());
		}

		// save for the financing type
		if (StringUtils.isNotBlank(accnDto.getFinanceOptions())) {
			ckAccnEntity.setCaccnFinancingType(accnDto.getFinanceOptions());
		}

		// OC - Cargo Owner; OT - Trucking Operator; OPM - Other People Money
		if (StringUtils.isNotBlank(accnDto.getFinanceOptions())
				&& Arrays.asList("OT", "OC", "OPM").contains(accnDto.getFinanceOptions())
				&& StringUtils.isNotBlank(accnDto.getFinancer())) {
			ckAccnEntity.setCaccnFinancer(accnDto.getFinancer());
		} else {
			ckAccnEntity.setCaccnFinancer(null);
		}

		ckAccnDao.saveOrUpdate(ckAccnEntity);

		return new CkAccn(ckAccnEntity);
	}

	public void loadCkAccnDetails(String accnId, CkManageAccn accnDto) throws Exception {
		TCkAccn ckAccnEntity = ckAccnDao.find(accnId);
		if (ckAccnEntity != null) {
			accnDto.setFinanceOptions(ckAccnEntity.getCaccnFinancingType());
			accnDto.setFinancer(ckAccnEntity.getCaccnFinancer());
			if (StringUtils.isNotBlank(ckAccnEntity.getCaccnFinancer())) {
				TMstBank bank = mstBankDao.find(ckAccnEntity.getCaccnFinancer());
				if (bank != null)
					accnDto.setFinancerUrl(bank.getBankUrl());
			}
		}
	}

	@Transactional()
	public CkAccn getCkAccn(String accnId) throws Exception {

		try {
			TCkAccn entity = ckAccnDao.find(accnId);
			if( null == entity ) {
				return null;
			}
			CkAccn ckAccn = new CkAccn(entity);
			ckAccn.setTCoreAccn( new CoreAccn(entity.getTCoreAccn()));
			
			return ckAccn;
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}

	}
}
