package com.guudint.clickargo.job.dao;

import com.guudint.clickargo.job.model.TCkJobReject;
import com.vcc.camelone.common.dao.GenericDao;

public interface CkJobRejectDao extends GenericDao<TCkJobReject, String> {

	public int getMaxSeq(String jobId) throws Exception;

	public boolean hasRejectRemarks(String jobId) throws Exception;
}
