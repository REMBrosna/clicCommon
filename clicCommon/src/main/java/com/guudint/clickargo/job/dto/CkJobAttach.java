package com.guudint.clickargo.job.dto;

import java.util.Date;

import javax.validation.constraints.Size;

import com.guudint.clickargo.job.model.TCkJobAttach;
import com.guudint.clickargo.validator.ValidationGroup.CreateValid;
import com.guudint.clickargo.validator.ValidationGroup.SubmitValid;
import com.guudint.clickargo.validator.ValidationGroup.UpdateValid;
import com.vcc.camelone.common.dto.AbstractDTO;
import com.vcc.camelone.master.dto.MstAttType;

public class CkJobAttach extends AbstractDTO<CkJobAttach, TCkJobAttach> {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = 492221142718717628L;

	// Attributes
	/////////////
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 35, message = "{valid.job.attId.maxLength}")
	private String attId;
	private CkJob TCkJob;
	private MstAttType TMstAttType;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 256, message = "{valid.job.attName.maxLength}")
	private String attName;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 1024, message = "{valid.job.attLoc.maxLength}")
	private String attDoc;
	private Character attStatus;
	private String attRemarks;
	private Date attDtCreate;
	private String attUidCreate;
	private Date attDtLupd;
	private String attUidLupd;

	private Character attReqReturn;
	private Date attDtReturn;
	private String attRefNo;
	private Date attDtValid;
	
	private byte[] attData;

	// Extra fields for listing (doc verification)
	private String authorizer;
	private String refNo;
	private String doNo;
	private Date jobDtConfirmed;

	private String relatedDoiFfId;
	
	private boolean isDuplicate;
	
	// For Express
	private String doiBlType;
	
	// Item 34 - Document return need to capture name and date in MSC doc verification
	private String attReturnVerifier;
	private String verifierName;
	
	// For Document Return
	private boolean isPendingReturn;
	private boolean isReturned;
	private boolean isNotVerified;
	
	// Constructors
	///////////////
	public CkJobAttach() {
	}

	/**
	 * @param entity
	 */
	public CkJobAttach(TCkJobAttach entity) {
		super(entity);
	}

	/**
	 * @param attId
	 * @param TCkJob
	 * @param TMstAttType
	 */
	public CkJobAttach(String attId, CkJob TCkJob, MstAttType TMstAttType) {
		this.attId = attId;
		this.TCkJob = TCkJob;
		this.TMstAttType = TMstAttType;
	}

	/**
	 * @param attId
	 * @param TCkJob
	 * @param TMstAttType
	 * @param attName
	 * @param attLoc
	 * @param attStatus
	 * @param attDtCreate
	 * @param attUidCreate
	 * @param attDtLupd
	 * @param attUidLupd
	 * @param attDtValid
	 */
	public CkJobAttach(String attId, CkJob TCkJob, MstAttType TMstAttType, String attName, String attDoc,
			Character attStatus, String attRemarks, Date attDtCreate, String attUidCreate, Date attDtLupd, String attUidLupd,
			Character attReqReturn, Date attDtReturn, Date attDtValid) {
		this.attId = attId;
		this.TCkJob = TCkJob;
		this.TMstAttType = TMstAttType;
		this.attName = attName;
		this.attDoc = attDoc;
		this.attStatus = attStatus;
		this.attRemarks = attRemarks;
		this.attDtCreate = attDtCreate;
		this.attUidCreate = attUidCreate;
		this.attDtLupd = attDtLupd;
		this.attUidLupd = attUidLupd;
		this.attReqReturn = attReqReturn;
		this.attDtReturn = attDtReturn;
		this.attDtValid = attDtValid;
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
	public int compareTo(CkJobAttach o) {
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
	 * @return the attId
	 */
	public String getAttId() {
		return attId;
	}

	/**
	 * @param attId the attId to set
	 */
	public void setAttId(String attId) {
		this.attId = attId;
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
	 * @return the tMstAttType
	 */
	public MstAttType getTMstAttType() {
		return TMstAttType;
	}

	/**
	 * @param tMstAttType the tMstAttType to set
	 */
	public void setTMstAttType(MstAttType tMstAttType) {
		TMstAttType = tMstAttType;
	}

	/**
	 * @return the attName
	 */
	public String getAttName() {
		return attName;
	}

	/**
	 * @param attName the attName to set
	 */
	public void setAttName(String attName) {
		this.attName = attName;
	}

	/**
	 * @return the attDoc
	 */
	public String getAttDoc() {
		return attDoc;
	}

	/**
	 * @param attDoc the attDoc to set
	 */
	public void setAttDoc(String attDoc) {
		this.attDoc = attDoc;
	}

	/**
	 * @return the attStatus
	 */
	public Character getAttStatus() {
		return attStatus;
	}

	/**
	 * @param attStatus the attStatus to set
	 */
	public void setAttStatus(Character attStatus) {
		this.attStatus = attStatus;
	}

	/**
	 * @return the attDtCreate
	 */
	public Date getAttDtCreate() {
		return attDtCreate;
	}

	/**
	 * @param attDtCreate the attDtCreate to set
	 */
	public void setAttDtCreate(Date attDtCreate) {
		this.attDtCreate = attDtCreate;
	}

	/**
	 * @return the attUidCreate
	 */
	public String getAttUidCreate() {
		return attUidCreate;
	}

	/**
	 * @param attUidCreate the attUidCreate to set
	 */
	public void setAttUidCreate(String attUidCreate) {
		this.attUidCreate = attUidCreate;
	}

	/**
	 * @return the attDtLupd
	 */
	public Date getAttDtLupd() {
		return attDtLupd;
	}

	/**
	 * @param attDtLupd the attDtLupd to set
	 */
	public void setAttDtLupd(Date attDtLupd) {
		this.attDtLupd = attDtLupd;
	}

	/**
	 * @return the attUidLupd
	 */
	public String getAttUidLupd() {
		return attUidLupd;
	}

	/**
	 * @param attUidLupd the attUidLupd to set
	 */
	public void setAttUidLupd(String attUidLupd) {
		this.attUidLupd = attUidLupd;
	}

	public byte[] getAttData() {
		return attData;
	}

	public void setAttData(byte[] attData) {
		this.attData = attData;
	}

	/**
	 * @return the authorizer
	 */
	public String getAuthorizer() {
		return authorizer;
	}

	/**
	 * @param authorizer the authorizer to set
	 */
	public void setAuthorizer(String authorizer) {
		this.authorizer = authorizer;
	}

	/**
	 * @return the refNo
	 */
	public String getRefNo() {
		return refNo;
	}

	/**
	 * @param refNo the refNo to set
	 */
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	/**
	 * @return the doNo
	 */
	public String getDoNo() {
		return doNo;
	}

	/**
	 * @param doNo the doNo to set
	 */
	public void setDoNo(String doNo) {
		this.doNo = doNo;
	}

	/**
	 * @return the jobDtConfirmed
	 */
	public Date getJobDtConfirmed() {
		return jobDtConfirmed;
	}

	/**
	 * @param jobDtConfirmed the jobDtConfirmed to set
	 */
	public void setJobDtConfirmed(Date jobDtConfirmed) {
		this.jobDtConfirmed = jobDtConfirmed;
	}

	/**
	 * @return the attReqReturn
	 */
	public Character getAttReqReturn() {
		return attReqReturn;
	}

	/**
	 * @param attReqReturn the attReqReturn to set
	 */
	public void setAttReqReturn(Character attReqReturn) {
		this.attReqReturn = attReqReturn;
	}

	/**
	 * @return the attDtReturn
	 */
	public Date getAttDtReturn() {
		return attDtReturn;
	}

	/**
	 * @param attDtReturn the attDtReturn to set
	 */
	public void setattDtReturn(Date attDtReturn) {
		this.attDtReturn = attDtReturn;
	}

	public String getAttRefNo() {
		return attRefNo;
	}

	public void setAttRefNo(String attRefNo) {
		this.attRefNo = attRefNo;
	}

	/**
	 * @return the relatedDoiFfId
	 */
	public String getRelatedDoiFfId() {
		return relatedDoiFfId;
	}

	/**
	 * @param relatedDoiFfId the relatedDoiFf to set
	 */
	public void setRelatedDoiFfId(String relatedDoiFfId) {
		this.relatedDoiFfId = relatedDoiFfId;
	}

	/**
	 * @return the isDuplicate
	 */
	public boolean isDuplicate() {
		return isDuplicate;
	}

	/**
	 * @param isDuplicate the isDuplicate to set
	 */
	public void setDuplicate(boolean isDuplicate) {
		this.isDuplicate = isDuplicate;
	}

	/**
	 * @return the attRemarks
	 */
	public String getAttRemarks() {
		return attRemarks;
	}

	/**
	 * @param attRemarks the attRemarks to set
	 */
	public void setAttRemarks(String attRemarks) {
		this.attRemarks = attRemarks;
	}

	/**
	 * @return the doiBlType
	 */
	public String getDoiBlType() {
		return doiBlType;
	}

	/**
	 * @param doiBlType the doiBlType to set
	 */
	public void setDoiBlType(String doiBlType) {
		this.doiBlType = doiBlType;
	}

	/**
	 * @return the attDtValid
	 */
	public Date getAttDtValid() {
		return attDtValid;
	}

	/**
	 * @param attDtValid the attDtValid to set
	 */
	public void setAttDtValid(Date attDtValid) {
		this.attDtValid = attDtValid;
	}

	/**
	 * @param attReturnVerifier the attReturnVerifier to set
	 */
	public String getAttReturnVerifier() {
		return attReturnVerifier;
	}

	/**
	 * @return the attReturnVerifier
	 */
	public void setAttReturnVerifier(String attReturnVerifier) {
		this.attReturnVerifier = attReturnVerifier;
	}

	/**
	 * @return the verifierName
	 */
	public String getVerifierName() {
		return verifierName;
	}

	/**
	 * @param verifierName the verifierName to set
	 */
	public void setVerifierName(String verifierName) {
		this.verifierName = verifierName;
	}

	public boolean isPendingReturn() {
		return isPendingReturn;
	}

	public void setPendingReturn(boolean isPendingReturn) {
		this.isPendingReturn = isPendingReturn;
	}

	public boolean isReturned() {
		return isReturned;
	}

	public void setReturned(boolean isReturned) {
		this.isReturned = isReturned;
	}

	public boolean isNotVerified() {
		return isNotVerified;
	}

	public void setNotVerified(boolean isNotVerified) {
		this.isNotVerified = isNotVerified;
	}

}
