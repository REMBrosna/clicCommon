package com.guudint.clickargo.credit.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.guudint.clickargo.admin.service.util.ClickargoAccnService;
import com.guudint.clickargo.common.enums.ClickargoNotifTemplates;
import com.guudint.clickargo.common.event.listener.AbstractWfPostEventListenerService;
import com.guudint.clickargo.common.service.impl.CkNotificationUtilService;
import com.guudint.clickargo.credit.dto.CkCreditRequest;
import com.guudint.clickargo.credit.model.TCkCreditRequest;
import com.guudint.clickargo.master.enums.AccountTypes;
import com.guudint.clickargo.master.enums.Roles;
import com.guudint.clickargo.master.enums.ServiceTypes;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.can.device.NotificationParam;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.core.model.TCoreApps;
import com.vcc.camelone.util.PrincipalUtilService;

public class CreditRequestPostListenerService extends AbstractWfPostEventListenerService<TCkCreditRequest, CkCreditRequest> {

	private static Logger LOG = Logger.getLogger(CreditRequestPostListenerService.class);

	@Autowired
	protected CkNotificationUtilService notificationUtilService;

	@Autowired
	protected ClickargoAccnService clickargoAccnService;
	
	@Autowired
	protected PrincipalUtilService principalUtilService;

	@Autowired
	@Qualifier("coreAppsDao")
	private GenericDao<TCoreApps, String> coreAppsDao;

	private final String url = "/opadmin/creditform/view/";

	@Override
	public void processSubmit(CkCreditRequest dto, Principal principal) throws Exception {
		LOG.info("processSubmit");

		NotificationParam param = new NotificationParam();
		param.setAppsCode(ServiceTypes.CLICTRUCK.getAppsCode());
		param.setTemplateId(ClickargoNotifTemplates.CREDIT_LIMIT_UPDATE_SUB.getId());

		ArrayList<String> recipients = new ArrayList<>();
		if (null != dto) {
			Optional<CoreAccn> opGliAccn = Optional
					.ofNullable(clickargoAccnService.getAccountByType(AccountTypes.ACC_TYPE_SP));
			if (opGliAccn.isPresent() && StringUtils.isNotBlank(opGliAccn.get().getAccnId())) {
				recipients.addAll(getRecipients(opGliAccn.get().getAccnId(), Roles.SP_FIN_HD));
			}

			HashMap<String, String> contentFields = new HashMap<>();
			contentFields.put(":coFfAccnName", dto.getTCoreAccn() != null ? dto.getTCoreAccn().getAccnName() : "-");
			TCoreApps apps = coreAppsDao.find(ServiceTypes.CLICTRUCK.getAppsCode());
			contentFields.put(":creditLimitLink", apps != null ? apps.getAppsLaunchUrl() + url + dto.getCruId() : "-");
			contentFields.put(":sp_details", clickargoAccnService.getServiceProvider());
			param.setContentFeilds(contentFields);

		}

		param.setRecipients(recipients);
		notificationUtilService.saveNotificationLog(param.toJson(), principal, true);

	}

	@Override
	public void processVerify(CkCreditRequest dto, Principal principal) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void processApprove(CkCreditRequest dto, Principal principal) throws Exception {
		LOG.info("processApprove");

		NotificationParam param = new NotificationParam();
		param.setAppsCode(ServiceTypes.CLICTRUCK.getAppsCode());
		param.setTemplateId(ClickargoNotifTemplates.CREDIT_LIMIT_UPDATE_APP.getId());

		ArrayList<String> recipients = new ArrayList<>();
		if (null != dto) {
			Optional<CoreAccn> opGliAccn = Optional
					.ofNullable(clickargoAccnService.getAccountByType(AccountTypes.ACC_TYPE_SP));
			if (opGliAccn.isPresent() && StringUtils.isNotBlank(opGliAccn.get().getAccnId())) {
				recipients.addAll(getRecipients(opGliAccn.get().getAccnId(), Roles.SP_L1));
			}
			
			Optional<CoreAccn> opCoFfAccn = Optional.ofNullable(dto.getTCoreAccn());
			if (opCoFfAccn.isPresent() && StringUtils.isNotBlank(opCoFfAccn.get().getAccnId())) {
				recipients.addAll(getRecipients(opCoFfAccn.get().getAccnId(), Roles.OFFICER));
			}

			HashMap<String, String> contentFields = new HashMap<>();
			contentFields.put(":coFfAccnName", dto.getTCoreAccn() != null ? dto.getTCoreAccn().getAccnName() : "-");
			TCoreApps apps = coreAppsDao.find(ServiceTypes.CLICTRUCK.getAppsCode());
			contentFields.put(":creditLimitLink", apps != null ? apps.getAppsLaunchUrl() + url + dto.getCruId() : "-");
			contentFields.put(":sp_details", clickargoAccnService.getServiceProvider());
			param.setContentFeilds(contentFields);
			
		}

		param.setRecipients(recipients);
		notificationUtilService.saveNotificationLog(param.toJson(), principal, true);

	}

	@Override
	public void processReject(CkCreditRequest dto, Principal principal) throws Exception {
		LOG.info("processReject");

		NotificationParam param = new NotificationParam();
		param.setAppsCode(ServiceTypes.CLICTRUCK.getAppsCode());
		param.setTemplateId(ClickargoNotifTemplates.CREDIT_LIMIT_UPDATE_REJ.getId());

		ArrayList<String> recipients = new ArrayList<>();
		if (null != dto) {
			Optional<CoreAccn> opGliAccn = Optional
					.ofNullable(clickargoAccnService.getAccountByType(AccountTypes.ACC_TYPE_SP));
			if (opGliAccn.isPresent() && StringUtils.isNotBlank(opGliAccn.get().getAccnId())) {
				recipients.addAll(getRecipients(opGliAccn.get().getAccnId(), Roles.SP_L1));
			}

			HashMap<String, String> contentFields = new HashMap<>();
			contentFields.put(":coFfAccnName", dto.getTCoreAccn() != null ? dto.getTCoreAccn().getAccnName() : "-");
			TCoreApps apps = coreAppsDao.find(ServiceTypes.CLICTRUCK.getAppsCode());
			contentFields.put(":creditLimitLink", apps != null ? apps.getAppsLaunchUrl() + url + dto.getCruId() : "-");
			contentFields.put(":sp_details", clickargoAccnService.getServiceProvider());
			param.setContentFeilds(contentFields);

		}

		param.setRecipients(recipients);
		notificationUtilService.saveNotificationLog(param.toJson(), principal, true);
	}

}
