package com.guudint.clickargo.common.dto;

import java.util.Date;

import javax.validation.constraints.Size;

import com.guudint.clickargo.common.model.TCkRecordDate;
import com.guudint.clickargo.validator.ValidationGroup.CreateValid;
import com.guudint.clickargo.validator.ValidationGroup.SubmitValid;
import com.guudint.clickargo.validator.ValidationGroup.UpdateValid;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkRecordDate extends AbstractDTO<CkRecordDate, TCkRecordDate> {

	// Static Attributes
	/////////////////////
	private static final long serialVersionUID = 8427895169209926769L;

	// Attributes
	//////////////
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 35, message = "{valid.common.rcdId.maxLength}")
	private String rcdId;
	private Date rcdDtDrft;
	private Date rcdDtStart;
	private String rcdUidStart;
	private Date rcdDtExpiry;
	private String rcdUidExpiry;
	private Date rcdDtSubmit;
	private String rcdUidSubmit;
	private Date rcdDtAccepted;
	private String rcdUidAccepted;
	private Date rcdDtAssigned;
	private String rcdUidAssigned;
	private Date rcdDtBilled;
	private String rcdUidBilled;
	private Date rcdDtVerified;
	private String rcdUidVerified;
	private Date rcdDtApproved;
	private String rcdUidApproved;
	private Date rcdDtPaid;
	private String rcdUidPaid;
	private Date rcdDtComplete;
	private String rcdUidComplete;
	private Date rcdDtCancel;
	private String rcdUidCancel;
	private Date rcdDtReject;
	private String rcdUidReject;
	private Date rcdDtDelete;
	private String rcdUidDelete;

	private Date rcdDtBillVerified;
	private String rcdUidBillVerified;
	private Date rcdDtBillApproved;
	private String rcdUidBillApproved;
	private Date rcdDtBillRejected;
	private String rcdUidBillRejected;

	private Date rcdDtBillAcknowledged;
	private String rcdUidBillAcknowledged;

	// Constructors
	///////////////
	public CkRecordDate() {
	}

	/**
	 * @param entity
	 */
	public CkRecordDate(TCkRecordDate entity) {
		super(entity);
	}

	/**
	 * @param rcdId
	 */
	public CkRecordDate(String rcdId) {
		this.rcdId = rcdId;
	}

	/**
	 * @param rcdId
	 * @param rcdDtDrft
	 * @param rcdDtStart
	 * @param rcdDtExpiry
	 * @param rcdDtSubmit
	 * @param rcdDtAssigned
	 * @param rcdDtPaid
	 * @param rcdDtComplete
	 * @param rcdDtCancel
	 * @param rcdDtReject
	 * @param rcdDtDelete
	 */
	public CkRecordDate(String rcdId, Date rcdDtDrft, Date rcdDtStart, Date rcdDtExpiry, Date rcdDtSubmit,
			Date rcdDtAssigned, Date rcdDtPaid, Date rcdDtComplete, Date rcdDtCancel, Date rcdDtReject,
			Date rcdDtDelete) {
		this.rcdId = rcdId;
		this.rcdDtDrft = rcdDtDrft;
		this.rcdDtStart = rcdDtStart;
		this.rcdDtExpiry = rcdDtExpiry;
		this.rcdDtSubmit = rcdDtSubmit;
		this.rcdDtAssigned = rcdDtAssigned;
		this.rcdDtPaid = rcdDtPaid;
		this.rcdDtComplete = rcdDtComplete;
		this.rcdDtCancel = rcdDtCancel;
		this.rcdDtReject = rcdDtReject;
		this.rcdDtDelete = rcdDtDelete;
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
	public int compareTo(CkRecordDate arg0) {
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
	 * @return the rcdId
	 */
	public String getRcdId() {
		return rcdId;
	}

	/**
	 * @param rcdId the rcdId to set
	 */
	public void setRcdId(String rcdId) {
		this.rcdId = rcdId;
	}

	/**
	 * @return the rcdDtDrft
	 */
	public Date getRcdDtDrft() {
		return rcdDtDrft;
	}

	/**
	 * @param rcdDtDrft the rcdDtDrft to set
	 */
	public void setRcdDtDrft(Date rcdDtDrft) {
		this.rcdDtDrft = rcdDtDrft;
	}

	/**
	 * @return the rcdDtStart
	 */
	public Date getRcdDtStart() {
		return rcdDtStart;
	}

	/**
	 * @param rcdDtStart the rcdDtStart to set
	 */
	public void setRcdDtStart(Date rcdDtStart) {
		this.rcdDtStart = rcdDtStart;
	}

	/**
	 * @return the rcdDtExpiry
	 */
	public Date getRcdDtExpiry() {
		return rcdDtExpiry;
	}

	/**
	 * @param rcdDtExpiry the rcdDtExpiry to set
	 */
	public void setRcdDtExpiry(Date rcdDtExpiry) {
		this.rcdDtExpiry = rcdDtExpiry;
	}

	/**
	 * @return the rcdDtSubmit
	 */
	public Date getRcdDtSubmit() {
		return rcdDtSubmit;
	}

	/**
	 * @param rcdDtSubmit the rcdDtSubmit to set
	 */
	public void setRcdDtSubmit(Date rcdDtSubmit) {
		this.rcdDtSubmit = rcdDtSubmit;
	}

	/**
	 * @return the rcdDtAssigned
	 */
	public Date getRcdDtAssigned() {
		return this.rcdDtAssigned;
	}

	/**
	 * @param rcdDtAssigned the rcdDtAssigned to set
	 */
	public void setRcdDtAssigned(Date rcdDtAssigned) {
		this.rcdDtAssigned = rcdDtAssigned;
	}

	/**
	 * @return the rcdDtPaid
	 */
	public Date getRcdDtPaid() {
		return this.rcdDtPaid;
	}

	/**
	 * @param rcdDtPaid the rcdDtPaid to set
	 */
	public void setRcdDtPaid(Date rcdDtPaid) {
		this.rcdDtPaid = rcdDtPaid;
	}

	/**
	 * @return the rcdDtComplete
	 */
	public Date getRcdDtComplete() {
		return rcdDtComplete;
	}

	/**
	 * @param rcdDtComplete the rcdDtComplete to set
	 */
	public void setRcdDtComplete(Date rcdDtComplete) {
		this.rcdDtComplete = rcdDtComplete;
	}

	/**
	 * @return the rcdDtCancel
	 */
	public Date getRcdDtCancel() {
		return rcdDtCancel;
	}

	/**
	 * @param rcdDtCancel the rcdDtCancel to set
	 */
	public void setRcdDtCancel(Date rcdDtCancel) {
		this.rcdDtCancel = rcdDtCancel;
	}

	/**
	 * @return the rcdDtReject
	 */
	public Date getRcdDtReject() {
		return rcdDtReject;
	}

	/**
	 * @param rcdDtReject the rcdDtReject to set
	 */
	public void setRcdDtReject(Date rcdDtReject) {
		this.rcdDtReject = rcdDtReject;
	}

	/**
	 * @return the rcdDtDelete
	 */
	public Date getRcdDtDelete() {
		return rcdDtDelete;
	}

	/**
	 * @param rcdDtDelete the rcdDtDelete to set
	 */
	public void setRcdDtDelete(Date rcdDtDelete) {
		this.rcdDtDelete = rcdDtDelete;
	}

	public Date getRcdDtVerified() {
		return rcdDtVerified;
	}

	public void setRcdDtVerified(Date rcdDtVerified) {
		this.rcdDtVerified = rcdDtVerified;
	}

	public Date getRcdDtApproved() {
		return rcdDtApproved;
	}

	public void setRcdDtApproved(Date rcdDtApproved) {
		this.rcdDtApproved = rcdDtApproved;
	}

	public Date getRcdDtAccepted() {
		return rcdDtAccepted;
	}

	public void setRcdDtAccepted(Date rcdDtAccepted) {
		this.rcdDtAccepted = rcdDtAccepted;
	}

	public String getRcdUidStart() {
		return rcdUidStart;
	}

	public void setRcdUidStart(String rcdUidStart) {
		this.rcdUidStart = rcdUidStart;
	}

	public String getRcdUidExpiry() {
		return rcdUidExpiry;
	}

	public void setRcdUidExpiry(String rcdUidExpiry) {
		this.rcdUidExpiry = rcdUidExpiry;
	}

	public String getRcdUidSubmit() {
		return rcdUidSubmit;
	}

	public void setRcdUidSubmit(String rcdUidSubmit) {
		this.rcdUidSubmit = rcdUidSubmit;
	}

	public String getRcdUidAccepted() {
		return rcdUidAccepted;
	}

	public void setRcdUidAccepted(String rcdUidAccepted) {
		this.rcdUidAccepted = rcdUidAccepted;
	}

	public String getRcdUidAssigned() {
		return rcdUidAssigned;
	}

	public void setRcdUidAssigned(String rcdUidAssigned) {
		this.rcdUidAssigned = rcdUidAssigned;
	}

	public Date getRcdDtBilled() {
		return rcdDtBilled;
	}

	public void setRcdDtBilled(Date rcdDtBilled) {
		this.rcdDtBilled = rcdDtBilled;
	}

	public String getRcdUidBilled() {
		return rcdUidBilled;
	}

	public void setRcdUidBilled(String rcdUidBilled) {
		this.rcdUidBilled = rcdUidBilled;
	}

	public String getRcdUidVerified() {
		return rcdUidVerified;
	}

	public void setRcdUidVerified(String rcdUidVerified) {
		this.rcdUidVerified = rcdUidVerified;
	}

	public String getRcdUidApproved() {
		return rcdUidApproved;
	}

	public void setRcdUidApproved(String rcdUidApproved) {
		this.rcdUidApproved = rcdUidApproved;
	}

	public String getRcdUidPaid() {
		return rcdUidPaid;
	}

	public void setRcdUidPaid(String rcdUidPaid) {
		this.rcdUidPaid = rcdUidPaid;
	}

	public String getRcdUidComplete() {
		return rcdUidComplete;
	}

	public void setRcdUidComplete(String rcdUidComplete) {
		this.rcdUidComplete = rcdUidComplete;
	}

	public String getRcdUidCancel() {
		return rcdUidCancel;
	}

	public void setRcdUidCancel(String rcdUidCancel) {
		this.rcdUidCancel = rcdUidCancel;
	}

	public String getRcdUidReject() {
		return rcdUidReject;
	}

	public void setRcdUidReject(String rcdUidReject) {
		this.rcdUidReject = rcdUidReject;
	}

	public String getRcdUidDelete() {
		return rcdUidDelete;
	}

	public void setRcdUidDelete(String rcdUidDelete) {
		this.rcdUidDelete = rcdUidDelete;
	}

	public Date getRcdDtBillVerified() {
		return rcdDtBillVerified;
	}

	public void setRcdDtBillVerified(Date rcdDtBillVerified) {
		this.rcdDtBillVerified = rcdDtBillVerified;
	}

	public String getRcdUidBillVerified() {
		return rcdUidBillVerified;
	}

	public void setRcdUidBillVerified(String rcdUidBillVerified) {
		this.rcdUidBillVerified = rcdUidBillVerified;
	}

	public Date getRcdDtBillApproved() {
		return rcdDtBillApproved;
	}

	public void setRcdDtBillApproved(Date rcdDtBillApproved) {
		this.rcdDtBillApproved = rcdDtBillApproved;
	}

	public String getRcdUidBillApproved() {
		return rcdUidBillApproved;
	}

	public void setRcdUidBillApproved(String rcdUidBillApproved) {
		this.rcdUidBillApproved = rcdUidBillApproved;
	}

	public Date getRcdDtBillRejected() {
		return rcdDtBillRejected;
	}

	public void setRcdDtBillRejected(Date rcdDtBillRejected) {
		this.rcdDtBillRejected = rcdDtBillRejected;
	}

	public String getRcdUidBillRejected() {
		return rcdUidBillRejected;
	}

	public void setRcdUidBillRejected(String rcdUidBillRejected) {
		this.rcdUidBillRejected = rcdUidBillRejected;
	}

	public Date getRcdDtBillAcknowledged() {
		return rcdDtBillAcknowledged;
	}

	public void setRcdDtBillAcknowledged(Date rcdDtBillAcknowledged) {
		this.rcdDtBillAcknowledged = rcdDtBillAcknowledged;
	}

	public String getRcdUidBillAcknowledged() {
		return rcdUidBillAcknowledged;
	}

	public void setRcdUidBillAcknowledged(String rcdUidBillAcknowledged) {
		this.rcdUidBillAcknowledged = rcdUidBillAcknowledged;
	}

}
