package com.guudint.clickargo.job.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.guudint.clickargo.job.dao.CkJobRejectDao;
import com.guudint.clickargo.job.model.TCkJobReject;
import com.vcc.camelone.common.dao.impl.GenericDaoImpl;
import com.vcc.camelone.common.exception.ParameterException;

public class CkJobRejectDaoImpl extends GenericDaoImpl<TCkJobReject, String> implements CkJobRejectDao {

	@Override
	public int getMaxSeq(String jobId) throws Exception {
		if (StringUtils.isBlank(jobId))
			throw new ParameterException("param jobId null or empty");
		String hql = "from TCkJobReject o where o.TCkJob.jobId=:jobId "
				+ "and o.jobrSeq = (select max(i.jobrSeq) from TCkJobReject i where i.TCkJob.jobId=o.TCkJob.jobId)";
		Map<String, Object> params = new HashMap<>();
		params.put("jobId", jobId);
		List<TCkJobReject> listReject = this.getByQuery(hql, params);
		if (listReject != null && listReject.size() > 0) {
			return listReject.get(0).getJobrSeq() + 1;
		}

		return 1;
	}

	@Override
	public boolean hasRejectRemarks(String jobId) throws Exception {
		if (StringUtils.isBlank(jobId))
			throw new ParameterException("param jobId null or empty");
		String hql = "select count(o) from TCkJobReject o where o.TCkJob.jobId='" + jobId + "'";
		return this.count(hql) > 0;
	}

}
