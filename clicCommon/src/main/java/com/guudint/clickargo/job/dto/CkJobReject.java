package com.guudint.clickargo.job.dto;

import java.util.Date;

import com.guudint.clickargo.job.model.TCkJobReject;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkJobReject extends AbstractDTO<CkJobReject, TCkJobReject> {

	private static final long serialVersionUID = 3140369804603705811L;
	
	public static final String PREFIX_ID = "JOBR";
	
	private String jobrId;
	private CkJob TCkJob;
	private Integer jobrSeq;
	private String jobrUidReject;
	private Date jobrDtReject;
	private String jobrReason;

	// Constructors
		///////////////
		public CkJobReject() {
		}
		
		/**
		 * @param entity
		 */
		public CkJobReject(TCkJobReject entity) {
			super(entity);
		}
		
	public String getJobrId() {
		return jobrId;
	}

	public void setJobrId(String jobrId) {
		this.jobrId = jobrId;
	}

	public CkJob getTCkJob() {
		return TCkJob;
	}

	public void setTCkJob(CkJob tCkJob) {
		TCkJob = tCkJob;
	}

	public Integer getJobrSeq() {
		return jobrSeq;
	}

	public void setJobrSeq(Integer jobrSeq) {
		this.jobrSeq = jobrSeq;
	}

	public String getJobrUidReject() {
		return jobrUidReject;
	}

	public void setJobrUidReject(String jobrUidReject) {
		this.jobrUidReject = jobrUidReject;
	}

	public Date getJobrDtReject() {
		return jobrDtReject;
	}

	public void setJobrDtReject(Date jobrDtReject) {
		this.jobrDtReject = jobrDtReject;
	}

	public String getJobrReason() {
		return jobrReason;
	}

	public void setJobrReason(String jobrReason) {
		this.jobrReason = jobrReason;
	}

	@Override
	public int compareTo(CkJobReject o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

}
