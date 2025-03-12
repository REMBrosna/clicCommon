package com.guudint.clickargo.task.dto;

import java.util.Date;

import javax.validation.constraints.Size;

import com.guudint.clickargo.common.dto.CkRecordDate;
import com.guudint.clickargo.job.dto.CkJob;
import com.guudint.clickargo.master.dto.CkMstShipmentType;
import com.guudint.clickargo.master.dto.CkMstTaskState;
import com.guudint.clickargo.master.dto.CkMstTaskType;
import com.guudint.clickargo.task.model.TCkTask;
import com.guudint.clickargo.validator.ValidationGroup.CreateValid;
import com.guudint.clickargo.validator.ValidationGroup.SubmitValid;
import com.guudint.clickargo.validator.ValidationGroup.UpdateValid;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkTask extends AbstractDTO<CkTask, TCkTask> {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = -6684966441533032174L;

	// Attributes
	/////////////
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 35, message = "{valid.task.tskId.maxLength}")
	private String tskId;
	private CkJob TCkJob;
	private CkMstShipmentType TCkMstShipmentType;
	private CkMstTaskState TCkMstTaskState;
	private CkMstTaskType TCkMstTaskType;
	private CkRecordDate TCkRecordDate;
	private CoreAccn TCoreAccnByTskFfAccn;
	private CoreAccn TCoreAccnByTskCoAccn;
	private CoreAccn TCoreAccnByTskSlAccn;
	private CoreAccn TCoreAccnByTskOwnerAccn;
	private CoreAccn TCoreAccnByTskToAccn;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 255, message = "{valid.task.tskReference.maxLength}")
	private String tskReference;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 16777215, message = "{valid.task.tskRemarks.maxLength}")
	private String tskRemarks;
	private Character tskStatus;
	private Date tskDtCreate;
	private String tskUidCreate;
	private Date tskDtLupd;
	private String tskUidLupd;

	// Constructors
	////////////////
	public CkTask() {
	}

	/**
	 * @param entity
	 */
	public CkTask(TCkTask entity) {
		super(entity);
	}

	/**
	 * @param tskId
	 * @param TCkJob
	 * @param TCkMstShipmentType
	 * @param TCkMstTaskState
	 * @param TCkMstTaskType
	 */
	public CkTask(String tskId, CkJob TCkJob, CkMstShipmentType TCkMstShipmentType, CkMstTaskState TCkMstTaskState,
			CkMstTaskType TCkMstTaskType) {
		this.tskId = tskId;
		this.TCkJob = TCkJob;
		this.TCkMstShipmentType = TCkMstShipmentType;
		this.TCkMstTaskState = TCkMstTaskState;
		this.TCkMstTaskType = TCkMstTaskType;
	}

	/**
	 * @param tskId
	 * @param TCkJob
	 * @param TCkMstShipmentType
	 * @param TCkMstTaskState
	 * @param TCkMstTaskType
	 * @param TCkRecordDate
	 * @param tskReference
	 * @param tskRemarks
	 * @param tskStatus
	 * @param tskDtCreate
	 * @param tskUidCreate
	 * @param tskDtLupd
	 * @param tskUidLupd
	 */
	public CkTask(String tskId, CkJob TCkJob, CkMstShipmentType TCkMstShipmentType, CkMstTaskState TCkMstTaskState,
			CkMstTaskType TCkMstTaskType, CkRecordDate TCkRecordDate, CoreAccn TCoreAccnByTskFfAccn, CoreAccn TCoreAccnByTskCoAccn,
			CoreAccn TCoreAccnByTskSlAccn, CoreAccn TCoreAccnByTskOwnerAccn, CoreAccn TCoreAccnByTskToAccn, String tskReference, String tskRemarks,
			Character tskStatus, Date tskDtCreate, String tskUidCreate, Date tskDtLupd, String tskUidLupd) {
		this.tskId = tskId;
		this.TCkJob = TCkJob;
		this.TCkMstShipmentType = TCkMstShipmentType;
		this.TCkMstTaskState = TCkMstTaskState;
		this.TCkMstTaskType = TCkMstTaskType;
		this.TCkRecordDate = TCkRecordDate;
		this.TCoreAccnByTskFfAccn = TCoreAccnByTskFfAccn;
		this.TCoreAccnByTskCoAccn = TCoreAccnByTskCoAccn;
		this.TCoreAccnByTskSlAccn = TCoreAccnByTskSlAccn;
		this.TCoreAccnByTskOwnerAccn = TCoreAccnByTskOwnerAccn;
		this.TCoreAccnByTskToAccn = TCoreAccnByTskToAccn;
		this.tskReference = tskReference;
		this.tskRemarks = tskRemarks;
		this.tskStatus = tskStatus;
		this.tskDtCreate = tskDtCreate;
		this.tskUidCreate = tskUidCreate;
		this.tskDtLupd = tskDtLupd;
		this.tskUidLupd = tskUidLupd;
	}

	// Override Methods
	///////////////////
	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * 
	 */
	@Override
	public int compareTo(CkTask o) {
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
	 * @return the tskId
	 */
	public String getTskId() {
		return tskId;
	}

	/**
	 * @param tskId the tskId to set
	 */
	public void setTskId(String tskId) {
		this.tskId = tskId;
	}

	/**
	 * @return the tCkJob
	 */
	public CkJob getTCkJob() {
		return TCkJob;
	}

	/**
	 * @param tCkJob the tCkJob to set
	 */
	public void setTCkJob(CkJob tCkJob) {
		TCkJob = tCkJob;
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
	 * @return the tCkMstTaskState
	 */
	public CkMstTaskState getTCkMstTaskState() {
		return TCkMstTaskState;
	}

	/**
	 * @param tCkMstTaskState the tCkMstTaskState to set
	 */
	public void setTCkMstTaskState(CkMstTaskState tCkMstTaskState) {
		TCkMstTaskState = tCkMstTaskState;
	}

	/**
	 * @return the tCkMstTaskType
	 */
	public CkMstTaskType getTCkMstTaskType() {
		return TCkMstTaskType;
	}

	/**
	 * @param tCkMstTaskType the tCkMstTaskType to set
	 */
	public void setTCkMstTaskType(CkMstTaskType tCkMstTaskType) {
		TCkMstTaskType = tCkMstTaskType;
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
	 * @return the tCoreAccnByTskFfAccn
	 */
	public CoreAccn getTCoreAccnByTskFfAccn() {
		return TCoreAccnByTskFfAccn;
	}

	/**
	 * @param tCoreAccnByTskFfAccn the tCoreAccnByTskFfAccn to set
	 */
	public void setTCoreAccnByTskFfAccn(CoreAccn tCoreAccnByTskFfAccn) {
		TCoreAccnByTskFfAccn = tCoreAccnByTskFfAccn;
	}

	/**
	 * @return the tCoreAccnByTskCoAccn
	 */
	public CoreAccn getTCoreAccnByTskCoAccn() {
		return TCoreAccnByTskCoAccn;
	}

	/**
	 * @param tCoreAccnByTskCoAccn the tCoreAccnByTskCoAccn to set
	 */
	public void setTCoreAccnByTskCoAccn(CoreAccn tCoreAccnByTskCoAccn) {
		TCoreAccnByTskCoAccn = tCoreAccnByTskCoAccn;
	}

	/**
	 * @return the tCoreAccnByTskSlAccn
	 */
	public CoreAccn getTCoreAccnByTskSlAccn() {
		return TCoreAccnByTskSlAccn;
	}

	/**
	 * @param tCoreAccnByTskSlAccn the tCoreAccnByTskSlAccn to set
	 */
	public void setTCoreAccnByTskSlAccn(CoreAccn tCoreAccnByTskSlAccn) {
		TCoreAccnByTskSlAccn = tCoreAccnByTskSlAccn;
	}

	/**
	 * @return the tCoreAccnByTskOwnerAccn
	 */
	public CoreAccn getTCoreAccnByTskOwnerAccn() {
		return TCoreAccnByTskOwnerAccn;
	}

	/**
	 * @param tCoreAccnByTskOwnerAccn the tCoreAccnByTskOwnerAccn to set
	 */
	public void setTCoreAccnByTskOwnerAccn(CoreAccn tCoreAccnByTskOwnerAccn) {
		TCoreAccnByTskOwnerAccn = tCoreAccnByTskOwnerAccn;
	}

	/**
	 * @return the tCoreAccnByTskToAccn
	 */
	public CoreAccn getTCoreAccnByTskToAccn() {
		return TCoreAccnByTskToAccn;
	}

	/**
	 * @param tCoreAccnByTskToAccn the tCoreAccnByTskToAccn to set
	 */
	public void setTCoreAccnByTskToAccn(CoreAccn tCoreAccnByTskToAccn) {
		TCoreAccnByTskToAccn = tCoreAccnByTskToAccn;
	}

	/**
	 * @return the tskReference
	 */
	public String getTskReference() {
		return tskReference;
	}

	/**
	 * @param tskReference the tskReference to set
	 */
	public void setTskReference(String tskReference) {
		this.tskReference = tskReference;
	}

	/**
	 * @return the tskRemarks
	 */
	public String getTskRemarks() {
		return tskRemarks;
	}

	/**
	 * @param tskRemarks the tskRemarks to set
	 */
	public void setTskRemarks(String tskRemarks) {
		this.tskRemarks = tskRemarks;
	}

	/**
	 * @return the tskStatus
	 */
	public Character getTskStatus() {
		return tskStatus;
	}

	/**
	 * @param tskStatus the tskStatus to set
	 */
	public void setTskStatus(Character tskStatus) {
		this.tskStatus = tskStatus;
	}

	/**
	 * @return the tskDtCreate
	 */
	public Date getTskDtCreate() {
		return tskDtCreate;
	}

	/**
	 * @param tskDtCreate the tskDtCreate to set
	 */
	public void setTskDtCreate(Date tskDtCreate) {
		this.tskDtCreate = tskDtCreate;
	}

	/**
	 * @return the tskUidCreate
	 */
	public String getTskUidCreate() {
		return tskUidCreate;
	}

	/**
	 * @param tskUidCreate the tskUidCreate to set
	 */
	public void setTskUidCreate(String tskUidCreate) {
		this.tskUidCreate = tskUidCreate;
	}

	/**
	 * @return the tskDtLupd
	 */
	public Date getTskDtLupd() {
		return tskDtLupd;
	}

	/**
	 * @param tskDtLupd the tskDtLupd to set
	 */
	public void setTskDtLupd(Date tskDtLupd) {
		this.tskDtLupd = tskDtLupd;
	}

	/**
	 * @return the tskUidLupd
	 */
	public String getTskUidLupd() {
		return tskUidLupd;
	}

	/**
	 * @param tskUidLupd the tskUidLupd to set
	 */
	public void setTskUidLupd(String tskUidLupd) {
		this.tskUidLupd = tskUidLupd;
	}

}
