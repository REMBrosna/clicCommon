package com.guudint.clickargo.job.dto;

import java.util.Date;

import com.guudint.clickargo.job.model.TCkJobRemarks;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkJobRemarks extends AbstractDTO<CkJobRemarks, TCkJobRemarks> {

	public static enum RemarkType {
		JOB_REJECTED('J'),VERIFIED('V'), APPROVED('A'), REJECTED('R');

		private char code;

		private RemarkType(char code) {
			this.code = code;
		}

		public char getCode() {
			return this.code;
		}
	}

	private static final long serialVersionUID = 1737346044781496927L;
	private String jobrId;
	private CkJob TCkJob;
	private char jobrRemarkType;
	private Integer jobrSeq;
	private String jobrUidCreated;
	private Date jobrDtRemarks;
	private String jobrReason;

	public CkJobRemarks() {
	}

	public CkJobRemarks(TCkJobRemarks entity) {
		super(entity);
	}

	public CkJobRemarks(String jobrId, char jobrRemarkType) {
		this.jobrId = jobrId;
		this.jobrRemarkType = jobrRemarkType;
	}

	public CkJobRemarks(String jobrId, CkJob TCkJob, char jobrRemarkType, Integer jobrSeq, String jobrUidCreated,
			Date jobrDtRemarks, String jobrReason) {
		this.jobrId = jobrId;
		this.TCkJob = TCkJob;
		this.jobrRemarkType = jobrRemarkType;
		this.jobrSeq = jobrSeq;
		this.jobrUidCreated = jobrUidCreated;
		this.jobrDtRemarks = jobrDtRemarks;
		this.jobrReason = jobrReason;
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

	public char getJobrRemarkType() {
		return jobrRemarkType;
	}

	public void setJobrRemarkType(char jobrRemarkType) {
		this.jobrRemarkType = jobrRemarkType;
	}

	public Integer getJobrSeq() {
		return jobrSeq;
	}

	public void setJobrSeq(Integer jobrSeq) {
		this.jobrSeq = jobrSeq;
	}

	public String getJobrUidCreated() {
		return jobrUidCreated;
	}

	public void setJobrUidCreated(String jobrUidCreated) {
		this.jobrUidCreated = jobrUidCreated;
	}

	public Date getJobrDtRemarks() {
		return jobrDtRemarks;
	}

	public void setJobrDtRemarks(Date jobrDtRemarks) {
		this.jobrDtRemarks = jobrDtRemarks;
	}

	public String getJobrReason() {
		return jobrReason;
	}

	public void setJobrReason(String jobrReason) {
		this.jobrReason = jobrReason;
	}

	@Override
	public int compareTo(CkJobRemarks o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

}
