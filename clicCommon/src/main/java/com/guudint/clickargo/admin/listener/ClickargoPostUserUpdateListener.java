package com.guudint.clickargo.admin.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.admin.dto.ClickargoNotifTemplates;
import com.guudint.clickargo.admin.event.ClickargoPostUserUpdateEvent;
import com.guudint.clickargo.admin.service.util.ClickargoAccnService;
import com.guudint.clickargo.common.service.impl.CkNotificationUtilService;
import com.guudint.clickargo.master.enums.ServiceTypes;
import com.vcc.camelone.can.device.NotificationParam;
import com.vcc.camelone.ccm.dto.CoreContact;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.common.exception.COException;
import com.vcc.camelone.common.exception.ErrorCodes;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.core.model.TCoreApps;

@Component
public class ClickargoPostUserUpdateListener implements ApplicationListener<ClickargoPostUserUpdateEvent> {

	// Static Attributes
	/////////////////////
	private static Logger log = Logger.getLogger(ClickargoPostUserUpdateListener.class);

	@Autowired
	@Qualifier("coreAppsDao")
	private GenericDao<TCoreApps, String> coreAppsDao;

	@Autowired
	protected CkNotificationUtilService notificationUtilService;

	@Autowired
	protected ClickargoAccnService clickargoAccnService;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void onApplicationEvent(ClickargoPostUserUpdateEvent event) {
		log.debug("onApplicationEvent");

		try {

			if (event == null)
				throw new ParameterException("param event null");

			if (event.getUsr() == null)
				throw new ParameterException("param usr null");

			Optional<CoreContact> opUsrContact = Optional.ofNullable(event.getUsr().getUsrContact());
			// proceed only if the user has email
			if (opUsrContact.isPresent() && StringUtils.isNotBlank(opUsrContact.get().getContactEmail())) {
				NotificationParam param = new NotificationParam();
				param.setAppsCode(ServiceTypes.CLICKARGO.getAppsCode());
				HashMap<String, String> contentFields = new HashMap<>();
				contentFields.put(":sp_details", clickargoAccnService.getServiceProvider());

				switch (event.getPostUsrUpdateAction()) {
				case RESET_PWD: {
					if (event.isFromManageUser()) {
						param.setTemplateId(ClickargoNotifTemplates.RESET_PASSWORD_MANAGEUSER.getId());
						contentFields.put(":userId", event.getUsr().getUsrUid());
						contentFields.put(":password", event.getRandomGeneratedPwd());

					} else {
						TCoreApps apps = coreAppsDao.find(ServiceTypes.CLICKARGO.getAppsCode());
						param.setTemplateId(ClickargoNotifTemplates.RESET_PASSWORD.getId());
						contentFields.put(":resetPwdLink",
								apps != null
										? apps.getAppsLaunchUrl() + "/session/reset-password?key="
												+ event.getRandomGeneratedPwd()
										: "#");

					}

					break;
				}
				case NEW_USER: {
					param.setTemplateId(ClickargoNotifTemplates.NEW_USER_CREDENTIALS.getId());
					contentFields.put(":userId", event.getUsr().getUsrUid());
					contentFields.put(":password", event.getRandomGeneratedPwd());
					break;
				}
				default:
					break;
				}

				ArrayList<String> recipients = new ArrayList<>();
				recipients.add(opUsrContact.get().getContactEmail());
				param.setRecipients(recipients);
				param.setContentFeilds(contentFields);

				notificationUtilService.saveNotificationLog(param.toJson(), null, true);

			}

		} catch (Exception ex) {
			log.error("onApplicationEvent", ex);
			COException.create(COException.ERROR, ErrorCodes.ERR_GEN_UNKNOWN, ErrorCodes.MSG_GEN_UNKNOWN,
					"ClickargoResetPwdListener", ex);
		}

	}

}
