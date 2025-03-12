package com.guudint.clickargo.clicservice.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.clicservice.model.TCkSvcWorkflow;
import com.guudint.clickargo.common.CkUtil;
import com.guudint.clickargo.common.RecordStatus;
import com.guudint.clickargo.job.dto.CkJob;
import com.guudint.clickargo.job.model.TCkJob;
import com.guudint.clickargo.master.dto.CkMstJobState;
import com.guudint.clickargo.master.enums.FormActions;
import com.guudint.clickargo.master.enums.ServiceTypes;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.common.audit.model.TCoreAuditlog;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.master.dto.MstAccnType;

/**
 * Workflow state that will determine the next state of the job by action.
 */
@Service
public class CkSvcWorkflowService {

	private static final Logger LOG = Logger.getLogger(CkSvcWorkflowService.class);

	@Autowired
	@Qualifier("ckSvcWorkflowDao")
	private GenericDao<TCkSvcWorkflow, String> ckSvcWorkflowDao;

	@Autowired
	private GenericDao<TCkJob, String> ckJobDao;
	
	@Autowired
	protected HttpServletRequest request;
	
	@Autowired
	GenericDao<TCoreAuditlog, String> auditLogDao;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CkJob moveState(FormActions action, CkJob ckJob, Principal principal, ServiceTypes serviceTypes)
			throws ParameterException, ProcessingException, Exception {
		LOG.info("moveState");
		try {

			if (null == principal)
				throw new ParameterException("param principal null");

			if (null == serviceTypes)
				throw new ParameterException("param serviceTypes null");

			if (null == action)
				throw new ParameterException("param action null");

			if (null == ckJob)
				throw new ParameterException("param ckJob null");

			TCkJob ckJobEntity = ckJobDao.find(ckJob.getJobId());
			if (ckJobEntity == null)
				throw new EntityNotFoundException("job " + ckJob.getJobId() + " not found");

			Hibernate.initialize(ckJobEntity.getTCkMstJobState());

			Optional<MstAccnType> opAccnType = Optional.ofNullable(principal.getCoreAccn().getTMstAccnType());
			if (!opAccnType.isPresent())
				throw new ProcessingException("account type null");

			Optional<List<String>> opAuthRoles = Optional.ofNullable(principal.getRoleList());
			if (!opAuthRoles.isPresent())
				throw new ProcessingException("principal roles null or empty");

			Optional<String> opAppsCode = Optional.ofNullable(principal.getAppsCode());
			if (!opAppsCode.isPresent())
				throw new ProcessingException("principal appscode null or empty");

			StringBuilder str = new StringBuilder("from TCkSvcWorkflow o where o.wkflStatus=:wkflStatus");
			str.append(" and o.TCkMstFormAction.fmactId=:action");
			str.append(" and o.TCkMstJobStateByWkflFromState.jbstId=:fromState");
			str.append(" and o.TCkMstServiceType.svctId=:serviceType");
			str.append(" and o.TCoreApps.appsCode=:appsCode");
			str.append(" and o.TMstAccnType.atypId=:accnType");
			str.append(" and o.TCoreRole.id.roleId in (:roles)");
			str.append(" and o.wkflId LIKE 'JOB%'");

			Map<String, Object> params = new HashMap<>();
			params.put("wkflStatus", RecordStatus.ACTIVE.getCode());
			params.put("action", action.name());
			params.put("fromState", ckJobEntity.getTCkMstJobState().getJbstId());
			params.put("serviceType", serviceTypes.getId());
			params.put("appsCode", opAppsCode.get());
			params.put("accnType", opAccnType.get().getAtypId());
			params.put("roles", opAuthRoles.get());

			List<TCkSvcWorkflow> listWfState = ckSvcWorkflowDao.getByQuery(str.toString(), params);
			if (listWfState == null || listWfState.size() <= 0 || listWfState.isEmpty())
				throw new ProcessingException(
						"no state machine configured for " + ckJobEntity.getTCkMstJobState().getJbstId()
								+ " with action  [" + action.name() + "] for this principal");

			// Expecting only one
			TCkSvcWorkflow toStateWf = listWfState.get(0);
			ckJobEntity.setTCkMstJobState(toStateWf.getTCkMstJobStateByWkflToState());
			ckJobEntity.setJobDtLupd(new Date());
			ckJobEntity.setJobUidLupd(principal.getUserId());
			ckJobDao.update(ckJobEntity);
			
			audit(ckJobEntity.getJobId(), principal, action);
			
			ckJob.setTCkMstJobState(new CkMstJobState(toStateWf.getTCkMstJobStateByWkflToState()));
			return ckJob;

		} catch (Exception ex) {
			LOG.error("moveState", ex);
			throw ex;
		}
	}
	
	public void audit(String key, Principal principal, FormActions action) {
		Date now = Calendar.getInstance().getTime();
		try {
			if (null == principal)
				throw new ParameterException("param principal null");
			if (action == null)
				throw new ParameterException("param action null");

			TCoreAuditlog auditLog = new TCoreAuditlog();
			auditLog.setAudtId(CkUtil.generateId("CT"));
			auditLog.setAudtReckey(key);
			auditLog.setAudtTimestamp(now);
			auditLog.setAudtEvent(action.getAudit());
			auditLog.setAudtUid(principal.getUserId());
			auditLog.setAudtRemoteIp(getLocalAddress());
			auditLog.setAudtUname(principal.getUserName());
			auditLog.setAudtRemarks("-");
			auditLogDao.add(auditLog);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the IP of the requestor
	 * 
	 * @return
	 */
	public String getLocalAddress() {
		String ip = request.getHeader("X-FORWARDED-FOR");
		if (StringUtils.isEmpty(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}
