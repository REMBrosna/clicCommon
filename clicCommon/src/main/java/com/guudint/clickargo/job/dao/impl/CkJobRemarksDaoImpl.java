package com.guudint.clickargo.job.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.guudint.clickargo.job.dao.CkJobRemarksDao;
import com.guudint.clickargo.job.model.TCkJobRemarks;
import com.vcc.camelone.common.dao.impl.GenericDaoImpl;
import com.vcc.camelone.common.exception.ParameterException;

public class CkJobRemarksDaoImpl extends GenericDaoImpl<TCkJobRemarks, String> implements CkJobRemarksDao {

	@Override
	public int getMaxSeq(String jobId) throws Exception {
		if (StringUtils.isBlank(jobId))
			throw new ParameterException("param jobId null or empty");
		String hql = "from TCkJobRemarks o where o.TCkJob.jobId=:jobId "
				+ "and o.jobrSeq = (select max(i.jobrSeq) from TCkJobReject i where i.TCkJob.jobId=o.TCkJob.jobId)";
		Map<String, Object> params = new HashMap<>();
		params.put("jobId", jobId);
		List<TCkJobRemarks> listRemarks = this.getByQuery(hql, params);
		if (listRemarks != null && listRemarks.size() > 0) {
			return listRemarks.get(0).getJobrSeq() + 1;
		}

		return 1;
	}

	@Override
	public boolean hasRemarks(String jobId) throws Exception {
		if (StringUtils.isBlank(jobId))
			throw new ParameterException("param jobId null or empty");
		String hql = "select count(o) from TCkJobRemarks o where o.TCkJob.jobId='" + jobId + "'";
		return this.count(hql) > 0;
	}
	public String rejectedRemarks(String jobId) throws Exception {
		HashMap<String, Object> params = new HashMap<>();
		params.put("jobId", jobId);
		String hql = "SELECT o FROM TCkJobRemarks o WHERE o.TCkJob.jobId = :jobId";
		List<TCkJobRemarks> rejectList = this.getByQuery(hql, params);
		return rejectList.isEmpty() ? "" : rejectList.get(0).getJobrReason();
	}
}
