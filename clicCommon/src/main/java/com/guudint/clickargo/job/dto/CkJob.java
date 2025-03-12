package com.guudint.clickargo.job.dto;

import java.util.Date;

import javax.validation.constraints.Size;

import com.guudint.clickargo.common.dto.CkRecordDate;
import com.guudint.clickargo.job.model.TCkJob;
import com.guudint.clickargo.master.dto.CkMstJobState;
import com.guudint.clickargo.master.dto.CkMstJobType;
import com.guudint.clickargo.master.dto.CkMstShipmentType;
import com.guudint.clickargo.validator.ValidationGroup.CreateValid;
import com.guudint.clickargo.validator.ValidationGroup.SubmitValid;
import com.guudint.clickargo.validator.ValidationGroup.UpdateValid;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkJob extends AbstractDTO<CkJob, TCkJob> {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = -1613482284885660216L;
	
	// Attribute
	/////////////
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 35, message = "{valid.job.jobId.maxLength}")
	private String jobId;
	private CkMstJobState TCkMstJobState;
	private CkMstJobType TCkMstJobType;
	private CkMstShipmentType TCkMstShipmentType;
	private CkRecordDate TCkRecordDate;
	
	private CoreAccn TCoreAccnByJobFfAccn;
	private CoreAccn TCoreAccnByJobToAccn;
	private CoreAccn TCoreAccnByJobSlAccn;
	private CoreAccn TCoreAccnByJobCoAccn;
	private CoreAccn TCoreAccnByJobOwnerAccn;
	
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 255, message = "{valid.job.jobReference.maxLength}")
	private String jobReference;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 16777215, message = "{valid.job.jobRemarks.maxLength}")
	private String jobRemarks;
	private Character jobStatus;
	private Date jobDtCreate;
	private String jobUidCreate;
	private Date jobDtLupd;
	private String jobUidLupd;
	private String jobLoading;
	private String jobSubType;

	// Constructors
	///////////////
	public CkJob() {
	}
	
	/**
	 * @param entity
	 */
	public CkJob(TCkJob entity) {
		super(entity);
	}

	/**
	 * @param jobId
	 * @param TCkMstJobState
	 * @param TCkMstJobType
	 * @param TCkMstShipmentType
	 */
	public CkJob(String jobId, CkMstJobState TCkMstJobState, CkMstJobType TCkMstJobType,
			CkMstShipmentType TCkMstShipmentType) {
		this.jobId = jobId;
		this.TCkMstJobState = TCkMstJobState;
		this.TCkMstJobType = TCkMstJobType;
		this.TCkMstShipmentType = TCkMstShipmentType;
	}

	/**
	 * @param jobId
	 * @param TCkMstJobState
	 * @param TCkMstJobType
	 * @param TCkMstShipmentType
	 * @param TCkRecordDate
	 * @param TCoreAccnByJobFfAccn;
	 * @param TCoreAccnByJobToAccn;
	 * @param TCoreAccnByJobSlAccn;
	 * @param TCoreAccnByJobCoAccn;
	 * @param TCoreAccnByJobOwnerAccn;
	 * @param jobReference
	 * @param jobRemarks
	 * @param jobStatus
	 * @param jobDtCreate
	 * @param jobUidCreate
	 * @param jobDtLupd
	 * @param jobUidLupd
	 */
	// Override Methods
	public CkJob(String jobId, CkMstJobState TCkMstJobState, CkMstJobType TCkMstJobType, CkMstShipmentType TCkMstShipmentType, CkRecordDate TCkRecordDate, CoreAccn TCoreAccnByJobFfAccn, CoreAccn TCoreAccnByJobToAccn, CoreAccn TCoreAccnByJobSlAccn, CoreAccn TCoreAccnByJobCoAccn, CoreAccn TCoreAccnByJobOwnerAccn, String jobReference, String jobRemarks, Character jobStatus, Date jobDtCreate, String jobUidCreate, Date jobDtLupd, String jobUidLupd, String jobLoading, String jobSubType) {
		this.jobId = jobId;
		this.TCkMstJobState = TCkMstJobState;
		this.TCkMstJobType = TCkMstJobType;
		this.TCkMstShipmentType = TCkMstShipmentType;
		this.TCkRecordDate = TCkRecordDate;
		this.TCoreAccnByJobFfAccn = TCoreAccnByJobFfAccn;
		this.TCoreAccnByJobToAccn = TCoreAccnByJobToAccn;
		this.TCoreAccnByJobSlAccn = TCoreAccnByJobSlAccn;
		this.TCoreAccnByJobCoAccn = TCoreAccnByJobCoAccn;
		this.TCoreAccnByJobOwnerAccn = TCoreAccnByJobOwnerAccn;
		this.jobReference = jobReference;
		this.jobRemarks = jobRemarks;
		this.jobStatus = jobStatus;
		this.jobDtCreate = jobDtCreate;
		this.jobUidCreate = jobUidCreate;
		this.jobDtLupd = jobDtLupd;
		this.jobUidLupd = jobUidLupd;
		this.jobLoading = jobLoading;
		this.jobSubType = jobSubType;
	}
	///////////////////
	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 *      
	 */
	@Override
	public int compareTo(CkJob o) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.vcc.camelone.common.COAbstractEntity#init()
	 *      
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}


	// Properties
	/////////////
	/**
	 * @return the jobId
	 */
	public String getJobId() {
		return jobId;
	}

	/**
	 * @param jobId the jobId to set
	 */
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	/**
	 * @return the tCkMstJobState
	 */
	public CkMstJobState getTCkMstJobState() {
		return TCkMstJobState;
	}

	/**
	 * @param tCkMstJobState the tCkMstJobState to set
	 */
	public void setTCkMstJobState(CkMstJobState tCkMstJobState) {
		TCkMstJobState = tCkMstJobState;
	}

	/**
	 * @return the tCkMstJobType
	 */
	public CkMstJobType getTCkMstJobType() {
		return TCkMstJobType;
	}

	/**
	 * @param tCkMstJobType the tCkMstJobType to set
	 */
	public void setTCkMstJobType(CkMstJobType tCkMstJobType) {
		TCkMstJobType = tCkMstJobType;
	}

	/**
	 * @return the tCkMstShipmentType
	 */
	public CkMstShipmentType getTCkMstShipmentType() {
		return TCkMstShipmentType;
	}

	/**
	 * @param tCkMstShipmentType the tCkMstShipmentType to set
	 */
	public void setTCkMstShipmentType(CkMstShipmentType tCkMstShipmentType) {
		TCkMstShipmentType = tCkMstShipmentType;
	}

	/**
	 * @return the tCkRecordDate
	 */
	public CkRecordDate getTCkRecordDate() {
		return TCkRecordDate;
	}

	/**
	 * @param tCkRecordDate the tCkRecordDate to set
	 */
	public void setTCkRecordDate(CkRecordDate tCkRecordDate) {
		TCkRecordDate = tCkRecordDate;
	}

	/**
	 * @return the jobReference
	 */
	public String getJobReference() {
		return jobReference;
	}

	/**
	 * @param jobReference the jobReference to set
	 */
	public void setJobReference(String jobReference) {
		this.jobReference = jobReference;
	}

	/**
	 * @return the tCoreAccnByJobFfAccn
	 */
	public CoreAccn getTCoreAccnByJobFfAccn() {
		return TCoreAccnByJobFfAccn;
	}

	/**
	 * @param tCoreAccnByJobFfAccn the tCoreAccnByJobFfAccn to set
	 */
	public void setTCoreAccnByJobFfAccn(CoreAccn tCoreAccnByJobFfAccn) {
		TCoreAccnByJobFfAccn = tCoreAccnByJobFfAccn;
	}

	/**
	 * @return the tCoreAccnByJobToAccn
	 */
	public CoreAccn getTCoreAccnByJobToAccn() {
		return TCoreAccnByJobToAccn;
	}

	/**
	 * @param tCoreAccnByJobToAccn the tCoreAccnByJobToAccn to set
	 */
	public void setTCoreAccnByJobToAccn(CoreAccn tCoreAccnByJobToAccn) {
		TCoreAccnByJobToAccn = tCoreAccnByJobToAccn;
	}

	/**
	 * @return the tCoreAccnByJobSlAccn
	 */
	public CoreAccn getTCoreAccnByJobSlAccn() {
		return TCoreAccnByJobSlAccn;
	}

	/**
	 * @param tCoreAccnByJobSlAccn the tCoreAccnByJobSlAccn to set
	 */
	public void setTCoreAccnByJobSlAccn(CoreAccn tCoreAccnByJobSlAccn) {
		TCoreAccnByJobSlAccn = tCoreAccnByJobSlAccn;
	}

	/**
	 * @return the tCoreAccnByJobCoAccn
	 */
	public CoreAccn getTCoreAccnByJobCoAccn() {
		return TCoreAccnByJobCoAccn;
	}

	/**
	 * @param tCoreAccnByJobCoAccn the tCoreAccnByJobCoAccn to set
	 */
	public void setTCoreAccnByJobCoAccn(CoreAccn tCoreAccnByJobCoAccn) {
		TCoreAccnByJobCoAccn = tCoreAccnByJobCoAccn;
	}

	/**
	 * @return the tCoreAccnByJobOwnerAccn
	 */
	public CoreAccn getTCoreAccnByJobOwnerAccn() {
		return TCoreAccnByJobOwnerAccn;
	}

	/**
	 * @param tCoreAccnByJobOwnerAccn the tCoreAccnByJobOwnerAccn to set
	 */
	public void setTCoreAccnByJobOwnerAccn(CoreAccn tCoreAccnByJobOwnerAccn) {
		TCoreAccnByJobOwnerAccn = tCoreAccnByJobOwnerAccn;
	}

	/**
	 * @return the jobRemarks
	 */
	public String getJobRemarks() {
		return jobRemarks;
	}

	/**
	 * @param jobRemarks the jobRemarks to set
	 */
	public void setJobRemarks(String jobRemarks) {
		this.jobRemarks = jobRemarks;
	}

	/**
	 * @return the jobStatus
	 */
	public Character getJobStatus() {
		return jobStatus;
	}

	/**
	 * @param jobStatus the jobStatus to set
	 */
	public void setJobStatus(Character jobStatus) {
		this.jobStatus = jobStatus;
	}

	/**
	 * @return the jobDtCreate
	 */
	public Date getJobDtCreate() {
		return jobDtCreate;
	}

	/**
	 * @param jobDtCreate the jobDtCreate to set
	 */
	public void setJobDtCreate(Date jobDtCreate) {
		this.jobDtCreate = jobDtCreate;
	}

	/**
	 * @return the jobUidCreate
	 */
	public String getJobUidCreate() {
		return jobUidCreate;
	}

	/**
	 * @param jobUidCreate the jobUidCreate to set
	 */
	public void setJobUidCreate(String jobUidCreate) {
		this.jobUidCreate = jobUidCreate;
	}

	/**
	 * @return the jobDtLupd
	 */
	public Date getJobDtLupd() {
		return jobDtLupd;
	}

	/**
	 * @param jobDtLupd the jobDtLupd to set
	 */
	public void setJobDtLupd(Date jobDtLupd) {
		this.jobDtLupd = jobDtLupd;
	}

	/**
	 * @return the jobUidLupd
	 */
	public String getJobUidLupd() {
		return jobUidLupd;
	}

	/**
	 * @param jobUidLupd the jobUidLupd to set
	 */
	public void setJobUidLupd(String jobUidLupd) {
		this.jobUidLupd = jobUidLupd;
	}

	public String getJobLoading() {
		return jobLoading;
	}

	public void setJobLoading(String jobLoading) {
		this.jobLoading = jobLoading;
	}

	public String getJobSubType() {
		return jobSubType;
	}

	public void setJobSubType(String jobSubType) {
		this.jobSubType = jobSubType;
	}
}
