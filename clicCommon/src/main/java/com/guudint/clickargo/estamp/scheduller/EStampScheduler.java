package com.guudint.clickargo.estamp.scheduller;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.guudint.clickargo.admin.service.CkAccnConfigExtService;
import com.guudint.clickargo.estamp.dto.AuthTokenResponse;
import com.guudint.clickargo.estamp.dto.CkEstampDoc;
import com.guudint.clickargo.estamp.model.TCkEstampDoc;
import com.guudint.clickargo.external.services.IStampGateway;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.common.service.ServiceStatus;
import com.vcc.camelone.scheduler.dto.CoreScheduleJoblog;
import com.vcc.camelone.scheduler.service.AbstractJob;

import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

/**
 * Scheduler responsible for doing the eStamping. First it will check for
 * records subject for eStamping, and then it will check the status of the
 * estamping process to update each record so that specific services (e.g.
 * clicdo, clictruck) can retrieve the stamped document.
 */
@Component
@EnableAsync
@EnableScheduling
public class EStampScheduler extends AbstractJob {

	private static Logger log = Logger.getLogger(EStampScheduler.class);

	@Autowired
	GenericDao<TCkEstampDoc, String> ckEstampDocDao;

	@Autowired
	IStampGateway stampGateway;

	@Autowired
	CkAccnConfigExtService ckAccnConfigExtService;

	public EStampScheduler() {
		super.setTaskName(this.getClass().getSimpleName());
	}

	@Override
	// This runs only after the first instance is executed
	@Scheduled(fixedDelay = 3 * 60 * 1000)
	@SchedulerLock(name = "EStampScheduler", lockAtLeastFor = "PT1M", lockAtMostFor = "PT5M")
	public void doJob() throws Exception {
		// TODO Auto-generated method stub

		LockAssert.assertLocked();

		String taskNo = super.getTaskNo();
		CoreScheduleJoblog coreScheduleJoblog = null;
		try {

			log.info("EStampScheduler Started: " + InetAddress.getLocalHost().getHostName() + " - "
					+ Calendar.getInstance().getTime().toString());

			coreScheduleJoblog = super.getTask(TASK_STATE.START, taskNo, TASK_STATE.START.toString());

			if (!ckAccnConfigExtService.isIndonesia()) {
				// not Indo
				log.info("Not Indonesia, don't run EStampScheduler");
				coreScheduleJoblog = super.getTask(TASK_STATE.COMPLETE, taskNo, ServiceStatus.STATUS.COMPLETED.toString(),
						"SUCCESS");
				return;
			}

			this.sendForEstamp();

			this.checkEStampDetails();

			coreScheduleJoblog = super.getTask(TASK_STATE.COMPLETE, taskNo, ServiceStatus.STATUS.COMPLETED.toString(),
					"SUCCESS");

			log.info("EStampScheduler Ended: " + Calendar.getInstance().getTime().toString());

		} catch (Exception e) {
			log.error("EStampScheduler", e);

			coreScheduleJoblog = super.getTask(TASK_STATE.EXCEPTION, taskNo, "EXCEPTION",
					ExceptionUtils.getStackTrace(e));

		} finally {
			super.logTask(coreScheduleJoblog);
		}
	}

	private void sendForEstamp() throws Exception {
		log.debug("sendForEstamp");

		try {

			DetachedCriteria criteria = DetachedCriteria.forClass(TCkEstampDoc.class);
			// This will only send records with status = NEW
			criteria.add(Restrictions.in("esdStampStatus", Arrays.asList(CkEstampDoc.Status.NEW.name())));
			List<TCkEstampDoc> tCkEstampDocs = ckEstampDocDao.getByCriteria(criteria);

			// login first before proceed, this is to keep the refresh token updated
			AuthTokenResponse auth = stampGateway.login();

			if (!tCkEstampDocs.isEmpty()) {
				for (TCkEstampDoc tCkEstampDoc : tCkEstampDocs) {
					stampGateway.stampSendDocument(tCkEstampDoc, auth);
				}
			}

		} catch (Exception e) {
			log.error("sendForEstamp");
			throw e;
		}
	}

	private void checkEStampDetails() throws Exception {
		log.debug("checkEStampDetails");
		try {

			DetachedCriteria criteria = DetachedCriteria.forClass(TCkEstampDoc.class);
			// Checks for the document details including the stamping_status from records
			// with status below
			criteria.add(Restrictions.in("esdStampStatus", Arrays.asList(CkEstampDoc.Status.NONE.name(),
					CkEstampDoc.Status.FAILED.name(), CkEstampDoc.Status.IN_PROGRESS.name())));

			List<TCkEstampDoc> tCkEstampDocs = ckEstampDocDao.getByCriteria(criteria);

			// login first before proceed, this is to keep the refreshh token updated
			AuthTokenResponse auth = stampGateway.login();

			if (!tCkEstampDocs.isEmpty()) {
				for (TCkEstampDoc tCkEstampDoc : tCkEstampDocs) {
					stampGateway.stampDocumentDetail(tCkEstampDoc, auth);
				}
			}

		} catch (Exception e) {
			throw e;
		}
	}

}
