package com.guudint.clickargo.credit.scheduler;

import java.net.InetAddress;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.guudint.clickargo.credit.dto.CkCreditRequest;
import com.guudint.clickargo.credit.event.CreditRequestEvent;
import com.guudint.clickargo.credit.service.ICreditRequestService;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.scheduler.dto.CoreScheduleJoblog;
import com.vcc.camelone.scheduler.service.AbstractJob;

import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

@Component
@EnableScheduling
@EnableAsync
public class CreditRequestScheduler extends AbstractJob {

    @Autowired
    private ICreditRequestService iCreditRequestService;

    private static final Logger LOG = Logger.getLogger(CreditRequestScheduler.class);
    
    @Autowired
	protected ApplicationEventPublisher eventPublisher;

    public CreditRequestScheduler() {
        super.setTaskName(this.getClass().getSimpleName());
    }

    @Override
    @Scheduled(cron = "0 0 0 * * *")
    @SchedulerLock(name = "CreditRequestScheduler", lockAtLeastFor = "30s")
    public void doJob() throws Exception {
        LOG.info("CreditRequestScheduler running in " + InetAddress.getLocalHost().getHostName());
        LockAssert.assertLocked();
        CoreScheduleJoblog coreScheduleJoblog = super.getTask(TASK_STATE.START, super.getTaskName(),
                TASK_STATE.START.toString());
        super.logTask(coreScheduleJoblog);
        LOG.info("CreditRequestScheduler started : " + Calendar.getInstance().getTime().toString());
        
        this.activeCreditLimit(null);
      
        coreScheduleJoblog = super.getTask(TASK_STATE.COMPLETE, super.getTaskName(), TASK_STATE.COMPLETE.toString(),
                "SUCCESS");
        super.logTask(coreScheduleJoblog);
        
    }
    
    public void activeCreditLimit(Date startDate) throws ProcessingException, Exception {

    	if( null == startDate) {
    		startDate = new Date();
    	}
        
        List<CkCreditRequest> ckCreditRequests = iCreditRequestService.getQueueToActive(startDate);
        
        for (CkCreditRequest ckCreditRequest : ckCreditRequests) {
        	
            iCreditRequestService.setActiveCreditLimit(ckCreditRequest);
            
            eventPublisher.publishEvent(new CreditRequestEvent(this, ckCreditRequest));
        }
    }

}
