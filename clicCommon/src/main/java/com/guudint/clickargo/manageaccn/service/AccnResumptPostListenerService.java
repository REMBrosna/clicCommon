package com.guudint.clickargo.manageaccn.service;

import java.util.ArrayList;
import java.util.Base64;
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
import com.guudint.clickargo.master.enums.AccountTypes;
import com.guudint.clickargo.master.enums.Roles;
import com.guudint.clickargo.master.enums.ServiceTypes;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.can.device.NotificationParam;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.ccm.model.TCoreAccn;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.core.model.TCoreApps;

public class AccnResumptPostListenerService extends AbstractWfPostEventListenerService<TCoreAccn, CoreAccn> {

	private static Logger LOG = Logger.getLogger(AccnResumptPostListenerService.class);

	@Autowired
	protected CkNotificationUtilService notificationUtilService;

	@Autowired
	protected ClickargoAccnService clickargoAccnService;

	@Autowired
	@Qualifier("coreAppsDao")
	private GenericDao<TCoreApps, String> coreAppsDao;

	private final String url = "/manageAccount/view/";

	@Override
	public void processSubmit(CoreAccn dto, Principal principal) throws Exception {
		LOG.info("processSubmit");

		NotificationParam param = new NotificationParam();
		param.setAppsCode(ServiceTypes.CLICTRUCK.getAppsCode());
		param.setTemplateId(ClickargoNotifTemplates.ACCN_RESUMPT_SUB.getId());

		ArrayList<String> recipients = new ArrayList<>();
		if (null != dto) {
			Optional<CoreAccn> opGliAccn = Optional
					.ofNullable(clickargoAccnService.getAccountByType(AccountTypes.ACC_TYPE_SP));
			if (opGliAccn.isPresent() && StringUtils.isNotBlank(opGliAccn.get().getAccnId())) {
				recipients.addAll(getRecipients(opGliAccn.get().getAccnId(), Roles.SP_FIN_HD));
			}

			HashMap<String, String> contentFields = new HashMap<>();
			contentFields.put(":accnName", dto.getAccnName() != null ? dto.getAccnName() : "-");
			TCoreApps apps = coreAppsDao.find(ServiceTypes.CLICTRUCK.getAppsCode());
			contentFields.put(":accnRegLink", apps != null ? apps.getAppsLaunchUrl() + encodeUrlId(dto.getAccnId()) : "-");
			contentFields.put(":sp_details", clickargoAccnService.getServiceProvider());
			param.setContentFeilds(contentFields);

		}

		param.setRecipients(recipients);
		notificationUtilService.saveNotificationLog(param.toJson(), principal, true);

	}

	@Override
	public void processVerify(CoreAccn dto, Principal principal) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void processApprove(CoreAccn dto, Principal principal) throws Exception {
		LOG.info("processApprove");

		NotificationParam param = new NotificationParam();
		param.setAppsCode(ServiceTypes.CLICTRUCK.getAppsCode());
		param.setTemplateId(ClickargoNotifTemplates.ACCN_RESUMPT_APP.getId());

		ArrayList<String> recipients = new ArrayList<>();
		if (null != dto) {
			Optional<CoreAccn> opGliAccn = Optional
					.ofNullable(clickargoAccnService.getAccountByType(AccountTypes.ACC_TYPE_SP));
			if (opGliAccn.isPresent() && StringUtils.isNotBlank(opGliAccn.get().getAccnId())) {
				recipients.addAll(getRecipients(opGliAccn.get().getAccnId(), Roles.SP_L1));
			}

			HashMap<String, String> contentFields = new HashMap<>();
			contentFields.put(":accnName", dto.getAccnName() != null ? dto.getAccnName() : "-");
			TCoreApps apps = coreAppsDao.find(ServiceTypes.CLICTRUCK.getAppsCode());
			contentFields.put(":accnRegLink", apps != null ? apps.getAppsLaunchUrl() + encodeUrlId(dto.getAccnId()) : "-");
			contentFields.put(":sp_details", clickargoAccnService.getServiceProvider());
			param.setContentFeilds(contentFields);

		}

		param.setRecipients(recipients);
		notificationUtilService.saveNotificationLog(param.toJson(), principal, true);

	}

	@Override
	public void processReject(CoreAccn dto, Principal principal) throws Exception {
		LOG.info("processReject");

		NotificationParam param = new NotificationParam();
		param.setAppsCode(ServiceTypes.CLICTRUCK.getAppsCode());
		param.setTemplateId(ClickargoNotifTemplates.ACCN_RESUMPT_REJ.getId());

		ArrayList<String> recipients = new ArrayList<>();
		if (null != dto) {
			Optional<CoreAccn> opGliAccn = Optional
					.ofNullable(clickargoAccnService.getAccountByType(AccountTypes.ACC_TYPE_SP));
			if (opGliAccn.isPresent() && StringUtils.isNotBlank(opGliAccn.get().getAccnId())) {
				recipients.addAll(getRecipients(opGliAccn.get().getAccnId(), Roles.SP_L1));
			}

			HashMap<String, String> contentFields = new HashMap<>();
			contentFields.put(":accnName", dto.getAccnName() != null ? dto.getAccnName() : "-");
			TCoreApps apps = coreAppsDao.find(ServiceTypes.CLICTRUCK.getAppsCode());
			contentFields.put(":accnRegLink", apps != null ? apps.getAppsLaunchUrl() + encodeUrlId(dto.getAccnId()) : "-");
			contentFields.put(":sp_details", clickargoAccnService.getServiceProvider());
			param.setContentFeilds(contentFields);

		}

		param.setRecipients(recipients);
		notificationUtilService.saveNotificationLog(param.toJson(), principal, true);
	}

	protected String encodeUrlId(String id) {
		String newUrl = "";
		try {
			byte[] encodedBytes = Base64.getEncoder().encode(id.getBytes());
			String encodedString = new String(encodedBytes);
			newUrl = url + encodedString;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return newUrl;
	}

}
