package com.guudint.clickargo.common.util;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.master.enums.ServiceTypes;
import com.vcc.camelone.can.model.TCoreNotificationTemplate;
import com.vcc.camelone.can.model.TCoreNotificationTemplateId;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.common.exception.ParameterException;

/**
 * Utility service related to notifications related services from C1 core.
 */
@Service
public class NotificationsUtil {

	@Autowired
	@Qualifier("coreNotificationTemplateDao")
	private GenericDao<TCoreNotificationTemplate, TCoreNotificationTemplateId> coreNotificationTemplateDao;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public TCoreNotificationTemplate getNotificationTemplate(String notTempId, ServiceTypes serviceTypes)
			throws ParameterException, Exception {
		if (StringUtils.isBlank(notTempId))
			throw new ParameterException("param notTempId null or empty");

		if (serviceTypes == null)
			throw new ParameterException("param serviceTypes is null");

		TCoreNotificationTemplateId keyTemplate = new TCoreNotificationTemplateId();
		keyTemplate.setNtplAppscode(serviceTypes.getAppsCode());
		keyTemplate.setNtplId(notTempId);
		TCoreNotificationTemplate notificationTemplate = coreNotificationTemplateDao.find(keyTemplate);
		if (null == notificationTemplate) {
			throw new Exception("Invalid template");
		}
		Hibernate.initialize(notificationTemplate.getTCoreNotificationChannelType());
		return notificationTemplate;
	}
}
