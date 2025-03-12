package com.guudint.clickargo.job.dao;

import com.guudint.clickargo.job.model.TCkJobRemarks;
import com.vcc.camelone.common.dao.GenericDao;

public interface CkJobRemarksDao extends GenericDao<TCkJobRemarks, String> {

	public int getMaxSeq(String jobId) throws Exception;
	
	public boolean hasRemarks(String jobId) throws Exception;
	public String rejectedRemarks(String jobId) throws Exception;
}
