package com.guudint.clickargo.common.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.common.CkUtil;
import com.guudint.clickargo.master.enums.NotificationChannelTypes;
import com.guudint.clickargo.master.enums.NotificationContentTypes;
import com.guudint.clickargo.master.enums.ServiceTypes;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.can.model.TCoreNotificationLog;
import com.vcc.camelone.can.model.TCoreNotificationTemplate;
import com.vcc.camelone.can.model.TCoreNotificationTemplateId;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.constant.NotificationType;
import com.vcc.camelone.constant.NotificationTypeConstant;

@Service
public class CkNotificationUtilService {

	// Static Attributes
	/////////////////////
	private static Logger log = Logger.getLogger(CkNotificationUtilService.class);

	@Autowired
	@Qualifier("coreNotificationTemplateDao")
	private GenericDao<TCoreNotificationTemplate, TCoreNotificationTemplateId> coreNotificationTemplateDao;

	@Autowired
	@Qualifier("coreNotificationLogDao")
	private GenericDao<TCoreNotificationLog, String> coreNotificationLogDao;

	public TCoreNotificationTemplate getNotificationTemplate(String id, ServiceTypes serviceType,
			NotificationChannelTypes channelType, NotificationContentTypes contentType)
			throws ParameterException, EntityNotFoundException, Exception {

		if (StringUtils.isBlank(id))
			throw new ParameterException("param id null or empty");
		if (serviceType == null)
			throw new ParameterException("param serviceType null");

		TCoreNotificationTemplateId notId = new TCoreNotificationTemplateId(id, serviceType.getAppsCode());
		TCoreNotificationTemplate template = coreNotificationTemplateDao.find(notId);
		if (template == null)
			throw new EntityNotFoundException("template " + id + " for " + serviceType.getId() + " not found");

		return template;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public TCoreNotificationLog saveNotificationLog(String jsonParam, Principal principal, boolean isAsync)
			throws Exception {
		log.debug("saveNotificationLog");
		try {

			if (StringUtils.isEmpty(jsonParam))
				throw new ParameterException("nParam is empty or null");

			Date now = new Date();

			TCoreNotificationLog tNLog = new TCoreNotificationLog();
			tNLog.setNlogId(CkUtil.generateIdSynch("NOT"));
			tNLog.setNlogStatus(NotificationTypeConstant.NOT_DEL_STS_NEW);
			tNLog.setNlogParam(jsonParam);
			tNLog.setNlogDelstatus(NotificationTypeConstant.NOT_DEL_STS_NEW);
			tNLog.setNlogNottyp(isAsync ? NotificationType.ASYNCH.name() : NotificationType.SYNCH.name());
			tNLog.setNlogDtCreate(now);
			tNLog.setNlogUidCreate(principal == null ? "SYS" : principal.getUserId());
			tNLog.setNlogDtLupd(now);
			tNLog.setNlogUidLupd(principal == null ? "SYS" : principal.getUserId());

			coreNotificationLogDao.add(tNLog);

			return tNLog;
		} catch (Exception ex) {
			log.error("saveNotificationLog", ex);
			throw ex;
		}
	}
}
