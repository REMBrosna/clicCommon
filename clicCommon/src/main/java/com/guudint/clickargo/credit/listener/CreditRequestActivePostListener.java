package com.guudint.clickargo.credit.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.admin.service.util.ClickargoAccnService;
import com.guudint.clickargo.common.RecordStatus;
import com.guudint.clickargo.common.enums.ClickargoNotifTemplates;
import com.guudint.clickargo.common.service.impl.CkNotificationUtilService;
import com.guudint.clickargo.credit.dto.CkCreditRequest;
import com.guudint.clickargo.credit.event.CreditRequestEvent;
import com.guudint.clickargo.master.enums.AccountTypes;
import com.guudint.clickargo.master.enums.Roles;
import com.guudint.clickargo.master.enums.ServiceTypes;
import com.vcc.camelone.cac.model.TCoreUsrRole;
import com.vcc.camelone.cac.model.TCoreUsrRoleId;
import com.vcc.camelone.can.device.NotificationParam;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.ccm.model.TCoreAccn;
import com.vcc.camelone.ccm.model.embed.TCoreContact;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.common.exception.COException;
import com.vcc.camelone.common.exception.ErrorCodes;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.service.entity.IEntityService;
import com.vcc.camelone.core.model.TCoreApps;

@Component
public class CreditRequestActivePostListener implements ApplicationListener<CreditRequestEvent> {

	// Static Attributes
	/////////////////////
	private static Logger log = Logger.getLogger(CreditRequestActivePostListener.class);

	@Autowired
	@Qualifier("ccmAccnService")
	protected IEntityService<TCoreAccn, String, CoreAccn> ccmAccnService;
	
	@Autowired
	protected CkNotificationUtilService notificationUtilService;

	@Autowired
	protected ClickargoAccnService clickargoAccnService;
	
	@Autowired
	@Qualifier("coreAppsDao")
	private GenericDao<TCoreApps, String> coreAppsDao;
	
	@Autowired
	protected GenericDao<TCoreUsrRole, TCoreUsrRoleId> coreUsrRoleDao;

	private final String url = "/opadmin/creditform/view/";

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void onApplicationEvent(CreditRequestEvent event) {
		log.debug("onApplicationEvent");

		try {

			if (event == null)
				throw new ParameterException("param event is null");

			if (event.getCkCreditRequest() == null)
				throw new ParameterException("param credit limit update is null");

			ArrayList<String> recipients = new ArrayList<>();
			Optional<CoreAccn> opGliAccn = Optional
					.ofNullable(clickargoAccnService.getAccountByType(AccountTypes.ACC_TYPE_SP));
			if (opGliAccn.isPresent() && StringUtils.isNotBlank(opGliAccn.get().getAccnId())) {
				recipients.addAll(getRecipients(opGliAccn.get().getAccnId(), Roles.SP_L1));
			}
			
	        if (Stream.of(AccountTypes.ACC_TYPE_CO.name(), AccountTypes.ACC_TYPE_FF.name())
	                .anyMatch(type -> type.equals(event.getCkCreditRequest().getTCoreAccn().getTMstAccnType().getAtypId()))) {
	            recipients.addAll(getRecipients(event.getCkCreditRequest().getTCoreAccn().getAccnId(), Roles.OFFICER));
	        }
				
				NotificationParam param = new NotificationParam();
				param.setAppsCode(ServiceTypes.CLICTRUCK.getAppsCode());
				param.setTemplateId(ClickargoNotifTemplates.CREDIT_LIMIT_UPDATE_ACTIVE.getId());
				
				HashMap<String, String> contentFields = new HashMap<>();
				contentFields.put(":coFfAccnName", event.getCkCreditRequest().getTCoreAccn() != null ? event.getCkCreditRequest().getTCoreAccn().getAccnName() : "-");
				TCoreApps apps = coreAppsDao.find(ServiceTypes.CLICTRUCK.getAppsCode());
				contentFields.put(":creditLimitLink", apps != null ? apps.getAppsLaunchUrl() + url + event.getCkCreditRequest().getCruId() : "-");
				contentFields.put(":sp_details", clickargoAccnService.getServiceProvider());
				
				param.setRecipients(recipients);
				param.setContentFeilds(contentFields);

				notificationUtilService.saveNotificationLog(param.toJson(), null, true);


		} catch (Exception ex) {
			log.error("onApplicationEvent", ex);
			COException.create(COException.ERROR, ErrorCodes.ERR_GEN_UNKNOWN, ErrorCodes.MSG_GEN_UNKNOWN,
					"CreditRequestActivePostListener", ex);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	protected List<String> getRecipients(String accnId, Roles roleType) throws Exception {
		if (StringUtils.isBlank(accnId))
			throw new ParameterException("param accnId null or empty");

		List<String> emailsList = new ArrayList<>();
		if (roleType != null) {
			String hql = "from TCoreUsrRole o where o.TCoreUsr.TCoreAccn.accnId LIKE :accnId and o.TCoreUsr.usrStatus=:status and o.id.urolRoleid=:roleTypeId and o.id.urolAppscode=:appsCode";
			Map<String, Object> params = new HashMap<>();
			params.put("accnId", "%" + accnId + "%");
			params.put("status", RecordStatus.ACTIVE.getCode());
			params.put("roleTypeId", roleType.name());
			params.put("appsCode", ServiceTypes.CLICTRUCK.getAppsCode());

			List<TCoreUsrRole> usrList = coreUsrRoleDao.getByQuery(hql, params);
			if (usrList != null && usrList.size() > 0) {
				for (TCoreUsrRole usr : usrList) {
					Hibernate.initialize(usr.getTCoreUsr());
					Hibernate.initialize(usr.getTCoreUsr().getUsrContact());
					Optional<TCoreContact> opUsrContact = Optional.ofNullable(usr.getTCoreUsr().getUsrContact());
					if (opUsrContact.isPresent() && StringUtils.isNotBlank(opUsrContact.get().getContactEmail())) {
						if (!emailsList.contains(opUsrContact.get().getContactEmail()))
							emailsList.add(opUsrContact.get().getContactEmail());
					}

				}

			}
		}

		return emailsList;
	}

}
