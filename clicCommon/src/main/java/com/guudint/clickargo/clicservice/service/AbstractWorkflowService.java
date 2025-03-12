package com.guudint.clickargo.clicservice.service;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.guudint.clickargo.clicservice.dao.CkSvcWorkflowDao;
import com.guudint.clickargo.common.CkUtil;
import com.guudint.clickargo.master.enums.FormActions;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.common.audit.dao.CoreAuditLogDao;
import com.vcc.camelone.common.audit.model.TCoreAuditlog;
import com.vcc.camelone.common.dto.AbstractDTO;
import com.vcc.camelone.common.exception.ParameterException;

public abstract class AbstractWorkflowService<E, D extends AbstractDTO<D, E>> implements ICkWorkflowService<E, D> {

	@Autowired
	private CoreAuditLogDao auditLogDao;
	
	@Autowired
	protected CkSvcWorkflowDao ckSvcWorkflowDao;
	
	@Autowired
	protected HttpServletRequest request;
	
	protected void audit(String key, Principal principal, FormActions action, String event) {
		Date now = Calendar.getInstance().getTime();
		try {
			if (null == principal)
				throw new ParameterException("param principal null");
			if (action == null)
				throw new ParameterException("param action null");

			TCoreAuditlog auditLog = new TCoreAuditlog();
			auditLog.setAudtId(CkUtil.generateId("AL"));
			auditLog.setAudtReckey(key);
			auditLog.setAudtTimestamp(now);
			auditLog.setAudtEvent(event);
			auditLog.setAudtUid(principal.getUserId());
			auditLog.setAudtRemoteIp(getLocalAddress());
			auditLog.setAudtUname(principal.getUserName());
			auditLog.setAudtRemarks("-");
			auditLogDao.add(auditLog);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getLocalAddress() {
		String ip = request.getHeader("X-FORWARDED-FOR");
		if (StringUtils.isEmpty(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
