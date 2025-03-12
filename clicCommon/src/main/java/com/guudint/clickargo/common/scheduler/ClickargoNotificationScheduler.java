package com.guudint.clickargo.common.scheduler;

import java.util.Calendar;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.vcc.camelone.can.service.INotify;
import com.vcc.camelone.common.service.ServiceStatus;
import com.vcc.camelone.scheduler.dto.CoreScheduleJoblog;
import com.vcc.camelone.scheduler.service.AbstractJob;

import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

@Component
@EnableScheduling
@EnableAsync
public class ClickargoNotificationScheduler extends AbstractJob {

	private static Logger log = Logger.getLogger(ClickargoNotificationScheduler.class);

	// Attributes
	/////////////////
	@Autowired
	@Qualifier("coreNotificationService")
	private INotify notificationService;

	// Constructor
	//////////////
	/**
	 * Constructor
	 */
	public ClickargoNotificationScheduler() {
		super.setTaskName(this.getClass().getSimpleName());
	}

	@Override
	@Scheduled(cron = "0 */5 * * * *") // runs every 5 minutes
	@SchedulerLock(name = "ClickargoNotificationScheduler", lockAtLeastFor = "1m")
	public synchronized void doJob() throws Exception {

		// TODO Auto-generated method stub
		log.debug("doJob");

		LockAssert.assertLocked();
		
		String taskNo = super.getTaskNo();
		CoreScheduleJoblog coreScheduleJoblog = null;
		try {
			coreScheduleJoblog = super.getTask(TASK_STATE.START, taskNo, TASK_STATE.START.toString());
			log.debug("ClickargoNotificationScheduler Started: " + Calendar.getInstance().getTime().toString());

			// Notification Service call
			try {
				notificationService.sendPendingNotifications();
				coreScheduleJoblog = super.getTask(TASK_STATE.COMPLETE, taskNo,
						ServiceStatus.STATUS.COMPLETED.toString(), "SUCCESS");

			} catch (Exception ex) {
				coreScheduleJoblog = super.getTask(TASK_STATE.EXCEPTION, taskNo, ServiceStatus.STATUS.FAILED.toString(),
						ex.getMessage());
			}

			super.logTask(coreScheduleJoblog);
			log.debug("ClickargoNotificationScheduler Ended: " + Calendar.getInstance().getTime().toString());
		} catch (Exception ex) {
			log.error("doTask", ex);

			coreScheduleJoblog = super.getTask(TASK_STATE.EXCEPTION, taskNo, "EXCEPTION",
					ExceptionUtils.getStackTrace(ex));
			super.logTask(coreScheduleJoblog);

		}

	}

}
