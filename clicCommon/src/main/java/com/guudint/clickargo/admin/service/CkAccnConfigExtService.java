package com.guudint.clickargo.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guudint.clickargo.admin.dao.CkAccnConfigExtDao;
import com.guudint.clickargo.admin.dto.CkAccnConfigExt;
import com.guudint.clickargo.admin.dto.CkAccnConfigExtId;
import com.guudint.clickargo.admin.dto.CkAccnConfigTypesEnum;
import com.guudint.clickargo.admin.model.TCkAccnConfigExt;
import com.guudint.clickargo.common.CKCountryConfig;
import com.guudint.clickargo.common.ICkConstant;
import com.guudint.clickargo.common.RecordStatus;
import com.guudint.clickargo.common.dao.CkAccnOpmDao;
import com.guudint.clickargo.common.model.TCkAccnOpm;
import com.guudint.clickargo.credit.dao.CkCreditDao;
import com.guudint.clickargo.credit.model.TCkCredit;
import com.guudint.clickargo.master.dto.CkMstServiceType;
import com.guudint.clickargo.master.enums.AccountTypes;
import com.guudint.clickargo.master.enums.ServiceTypes;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.master.dto.MstAccnType;
import com.vcc.camelone.master.dto.MstCurrency;
import com.vcc.camelone.util.email.SysParam;

@Service("ckAccnConfigExtService")
public class CkAccnConfigExtService {

	// Static Attributes
	////////////////////
	private static Logger log = Logger.getLogger(CkAccnConfigExtService.class);

	@Autowired
	protected CkAccnConfigExtDao accnConfigExtDao;

	@Autowired
	protected SysParam sysParam;

	@Autowired
	protected CkCreditDao dao;

	protected ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private CkAccnOpmDao accnOpmDao;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public List<CkAccnConfigExt> getAccnConfigExt(CkAccnConfigTypesEnum type, Principal principal,
			ServiceTypes serviceType, List<String> rolesList) throws ParameterException, Exception {
		log.debug("getAccnConfigExtByType");

		List<CkAccnConfigExt> list = null;

		try {
			if (type == null)
				throw new ParameterException("param type null");
			if (principal == null)
				throw new ParameterException("param principal null");
			if (rolesList == null)
				throw new ParameterException("param rolesList null");
			if (serviceType == null)
				throw new ParameterException("param serviceType null");

			List<TCkAccnConfigExt> accnExtList = accnConfigExtDao.getAccnConfigExt(principal.getCoreAccn().getAccnId(),
					type, rolesList, serviceType);
			if (accnExtList != null && accnExtList.size() > 0) {
				list = new ArrayList<>();
				for (TCkAccnConfigExt e : accnExtList) {
					CkAccnConfigExt dto = new CkAccnConfigExt(e);
					CkAccnConfigExtId id = new CkAccnConfigExtId(e.getId());
					dto.setId(id);
					list.add(dto);
				}

			}

		} catch (Exception ex) {
			log.error("getAccnConfigExt", ex);
		}

		return list;

	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Map<String, Object> isCreditApplicable(ServiceTypes serviceType, Principal principal)
			throws ParameterException, Exception {
		log.debug("getAccnConfigExtByType");
		Map<String, Object> mapResponse = new HashMap<>();
		try {
			if (principal == null)
				throw new ParameterException("param principal null");

			if (serviceType == null)
				throw new ParameterException("param serviceType null");

			String ckCtry = sysParam.getValString(ICkConstant.KEY_CLICKARGO_COUNTRY_CONFIG,
					"{\"country\":\"ID\",\"currency\":\"IDR\"}");
			CKCountryConfig ckCtryConfig = mapper.readValue(ckCtry, CKCountryConfig.class);

			// SG don't have financing for now.
			if (!ckCtryConfig.getCountry().equalsIgnoreCase("SG")) {
				CkMstServiceType ckServiceType = new CkMstServiceType();
				ckServiceType.setSvctId(serviceType.getId());

				MstCurrency currency = new MstCurrency();
				currency.setCcyCode(ckCtryConfig.getCurrency());

				Optional<MstAccnType> opAccnType = Optional.ofNullable(principal.getCoreAccn().getTMstAccnType());
				if (opAccnType.isPresent()
						&& (opAccnType.get().getAtypId().equalsIgnoreCase(AccountTypes.ACC_TYPE_CO.name())
								|| opAccnType.get().getAtypId().equalsIgnoreCase(AccountTypes.ACC_TYPE_FF.name())
								|| opAccnType.get().getAtypId().equalsIgnoreCase(AccountTypes.ACC_TYPE_TO.name()))) {
					// Check if account has OPM financing first
					TCkAccnOpm ckAccnOpm = accnOpmDao.findByAccnId(principal.getCoreAccn().getAccnId(),
							RecordStatus.ACTIVE.getCode());
					if (ckAccnOpm != null) {
						mapResponse.put("isFinanced", true);
						mapResponse.put("isOpm", true);
					} else {
						TCkCredit entity = this.dao.getByServiceTypeAndAccnAndCcy(ckServiceType,
								principal.getCoreAccn(), currency);
						if (entity != null) {
							mapResponse.put("isFinanced", true);
							mapResponse.put("isOpm", false);
						} else {
							mapResponse.put("isFinanced", false);
							mapResponse.put("isOpm", false);
						}

					}

				}
			}

			return mapResponse;

		} catch (Exception ex) {
			throw ex;
		}
	}

	public boolean isIndonesia() throws Exception {

		String ckCtry = sysParam.getValString(ICkConstant.KEY_CLICKARGO_COUNTRY_CONFIG,
				"{\"country\":\"ID\",\"currency\":\"IDR\"}");

		try {

			CKCountryConfig ckCtryConfig = mapper.readValue(ckCtry, CKCountryConfig.class);

			return "ID".equalsIgnoreCase(ckCtryConfig.getCountry());

		} catch (Exception e) {
			log.error("isIndonesia() : " + ckCtry, e);
			throw e;
		}

	}

}
