package com.guudint.clickargo.job.dto;

import java.util.Date;

import javax.validation.constraints.Size;

import com.guudint.clickargo.job.model.TCkJobQuery;
import com.guudint.clickargo.validator.ValidationGroup.CreateValid;
import com.guudint.clickargo.validator.ValidationGroup.SubmitValid;
import com.guudint.clickargo.validator.ValidationGroup.UpdateValid;
import com.vcc.camelone.ccm.dto.CoreUsr;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkJobQuery extends AbstractDTO<CkJobQuery, TCkJobQuery> {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = 8557169857806813550L;
	
	// Attributes
	/////////////
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 35, message = "{valid.job.qryId.maxLength}")
	private String qryId;
	private CkJob TCkJob;
	private CoreUsr TCoreUsrByQryRequester;
	private CoreUsr TCoreUsrByQryResponder;
	private Date qryDtQuery;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 16777215, message = "{valid.job.qryQuery.maxLength}")
	private String qryQuery;
	private Date qryDtResponse;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 16777215, message = "{valid.job.qryResponse.maxLength}")
	private String qryResponse;
	private Character qryStatus;
	private Date qryDtCreate;
	private String qryUidCreate;
	private Date qryDtLupd;
	private String qryUidLupd;

	// Constructors
	///////////////
	public CkJobQuery() {
	}
	
	/**
	 * @param entity
	 */
	public CkJobQuery(TCkJobQuery entity) {
		super(entity);
	}
 
	/**
	 * @param qryId
	 * @param TCkJob
	 */
	public CkJobQuery(String qryId, CkJob TCkJob) {
		this.qryId = qryId;
		this.TCkJob = TCkJob;
	}

	/**
	 * @param qryId
	 * @param TCkJob
	 * @param TCoreUsrByQryRequester
	 * @param TCoreUsrByQryResponder
	 * @param qryDtQuery
	 * @param qryQuery
	 * @param qryDtResponse
	 * @param qryResponse
	 * @param qryStatus
	 * @param qryDtCreate
	 * @param qryUidCreate
	 * @param qryDtLupd
	 * @param qryUidLupd
	 */
	public CkJobQuery(String qryId, CkJob TCkJob, CoreUsr TCoreUsrByQryRequester, CoreUsr TCoreUsrByQryResponder,
			Date qryDtQuery, String qryQuery, Date qryDtResponse, String qryResponse, Character qryStatus,
			Date qryDtCreate, String qryUidCreate, Date qryDtLupd, String qryUidLupd) {
		this.qryId = qryId;
		this.TCkJob = TCkJob;
		this.TCoreUsrByQryRequester = TCoreUsrByQryRequester;
		this.TCoreUsrByQryResponder = TCoreUsrByQryResponder;
		this.qryDtQuery = qryDtQuery;
		this.qryQuery = qryQuery;
		this.qryDtResponse = qryDtResponse;
		this.qryResponse = qryResponse;
		this.qryStatus = qryStatus;
		this.qryDtCreate = qryDtCreate;
		this.qryUidCreate = qryUidCreate;
		this.qryDtLupd = qryDtLupd;
		this.qryUidLupd = qryUidLupd;
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
	public int compareTo(CkJobQuery o) {
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
	 * @return the qryId
	 */
	public String getQryId() {
		return qryId;
	}

	/**
	 * @param qryId the qryId to set
	 */
	public void setQryId(String qryId) {
		this.qryId = qryId;
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
	 * @return the tCoreUsrByQryRequester
	 */
	public CoreUsr getTCoreUsrByQryRequester() {
		return TCoreUsrByQryRequester;
	}

	/**
	 * @param tCoreUsrByQryRequester the tCoreUsrByQryRequester to set
	 */
	public void setTCoreUsrByQryRequester(CoreUsr tCoreUsrByQryRequester) {
		TCoreUsrByQryRequester = tCoreUsrByQryRequester;
	}

	/**
	 * @return the tCoreUsrByQryResponder
	 */
	public CoreUsr getTCoreUsrByQryResponder() {
		return TCoreUsrByQryResponder;
	}

	/**
	 * @param tCoreUsrByQryResponder the tCoreUsrByQryResponder to set
	 */
	public void setTCoreUsrByQryResponder(CoreUsr tCoreUsrByQryResponder) {
		TCoreUsrByQryResponder = tCoreUsrByQryResponder;
	}

	/**
	 * @return the qryDtQuery
	 */
	public Date getQryDtQuery() {
		return qryDtQuery;
	}

	/**
	 * @param qryDtQuery the qryDtQuery to set
	 */
	public void setQryDtQuery(Date qryDtQuery) {
		this.qryDtQuery = qryDtQuery;
	}

	/**
	 * @return the qryQuery
	 */
	public String getQryQuery() {
		return qryQuery;
	}

	/**
	 * @param qryQuery the qryQuery to set
	 */
	public void setQryQuery(String qryQuery) {
		this.qryQuery = qryQuery;
	}

	/**
	 * @return the qryDtResponse
	 */
	public Date getQryDtResponse() {
		return qryDtResponse;
	}

	/**
	 * @param qryDtResponse the qryDtResponse to set
	 */
	public void setQryDtResponse(Date qryDtResponse) {
		this.qryDtResponse = qryDtResponse;
	}

	/**
	 * @return the qryResponse
	 */
	public String getQryResponse() {
		return qryResponse;
	}

	/**
	 * @param qryResponse the qryResponse to set
	 */
	public void setQryResponse(String qryResponse) {
		this.qryResponse = qryResponse;
	}

	/**
	 * @return the qryStatus
	 */
	public Character getQryStatus() {
		return qryStatus;
	}

	/**
	 * @param qryStatus the qryStatus to set
	 */
	public void setQryStatus(Character qryStatus) {
		this.qryStatus = qryStatus;
	}

	/**
	 * @return the qryDtCreate
	 */
	public Date getQryDtCreate() {
		return qryDtCreate;
	}

	/**
	 * @param qryDtCreate the qryDtCreate to set
	 */
	public void setQryDtCreate(Date qryDtCreate) {
		this.qryDtCreate = qryDtCreate;
	}

	/**
	 * @return the qryUidCreate
	 */
	public String getQryUidCreate() {
		return qryUidCreate;
	}

	/**
	 * @param qryUidCreate the qryUidCreate to set
	 */
	public void setQryUidCreate(String qryUidCreate) {
		this.qryUidCreate = qryUidCreate;
	}

	/**
	 * @return the qryDtLupd
	 */
	public Date getQryDtLupd() {
		return qryDtLupd;
	}

	/**
	 * @param qryDtLupd the qryDtLupd to set
	 */
	public void setQryDtLupd(Date qryDtLupd) {
		this.qryDtLupd = qryDtLupd;
	}

	/**
	 * @return the qryUidLupd
	 */
	public String getQryUidLupd() {
		return qryUidLupd;
	}

	/**
	 * @param qryUidLupd the qryUidLupd to set
	 */
	public void setQryUidLupd(String qryUidLupd) {
		this.qryUidLupd = qryUidLupd;
	}
}
