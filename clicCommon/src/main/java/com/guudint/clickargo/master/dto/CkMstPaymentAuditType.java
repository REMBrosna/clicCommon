package com.guudint.clickargo.master.dto;

import java.util.Date;

import javax.validation.constraints.Size;

import com.guudint.clickargo.master.model.TCkMstPaymentAuditType;
import com.guudint.clickargo.validator.ValidationGroup.CreateValid;
import com.guudint.clickargo.validator.ValidationGroup.SubmitValid;
import com.guudint.clickargo.validator.ValidationGroup.UpdateValid;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkMstPaymentAuditType extends AbstractDTO<CkMstPaymentAuditType, TCkMstPaymentAuditType> {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = -7493912000476640775L;

	// Attributes
	/////////////
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 35, message = "{valid.master.patyId.maxLength}")
	private String patyId;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 100, message = "{valid.master.pytName.maxLength}")
	private String pytName;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 255, message = "{valid.master.pytDesc.maxLength}")
	private String pytDesc;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 512, message = "{valid.master.pytDescOth.maxLength}")
	private String pytDescOth;
	private Character pytStatus;
	private Date pytDtCreate;
	private String pytUidCreate;
	private Date pytDtLupd;
	private String pytUidLupd;

	// Constructors
	///////////////
	public CkMstPaymentAuditType() {
	}

	/**
	 * @param entity
	 */
	public CkMstPaymentAuditType(TCkMstPaymentAuditType entity) {
		super(entity);
	}

	/**
	 * @param patyId
	 * @param pytName
	 */
	public CkMstPaymentAuditType(String patyId, String pytName) {
		this.patyId = patyId;
		this.pytName = pytName;
	}

	/**
	 * @param patyId
	 * @param pytName
	 * @param pytDesc
	 * @param pytDescOth
	 * @param pytStatus
	 * @param pytDtCreate
	 * @param pytUidCreate
	 * @param pytDtLupd
	 * @param pytUidLupd
	 */
	public CkMstPaymentAuditType(String patyId, String pytName, String pytDesc, String pytDescOth, Character pytStatus,
			Date pytDtCreate, String pytUidCreate, Date pytDtLupd, String pytUidLupd) {
		this.patyId = patyId;
		this.pytName = pytName;
		this.pytDesc = pytDesc;
		this.pytDescOth = pytDescOth;
		this.pytStatus = pytStatus;
		this.pytDtCreate = pytDtCreate;
		this.pytUidCreate = pytUidCreate;
		this.pytDtLupd = pytDtLupd;
		this.pytUidLupd = pytUidLupd;
	}

	// Override Methods
	///////////////////
	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * 
	 */
	public int compareTo(CkMstPaymentAuditType o) {
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
	 * @return the patyId
	 */
	public String getPatyId() {
		return patyId;
	}

	/**
	 * @param patyId the patyId to set
	 */
	public void setPatyId(String patyId) {
		this.patyId = patyId;
	}

	/**
	 * @return the pytName
	 */
	public String getPytName() {
		return pytName;
	}

	/**
	 * @param pytName the pytName to set
	 */
	public void setPytName(String pytName) {
		this.pytName = pytName;
	}

	/**
	 * @return the pytDesc
	 */
	public String getPytDesc() {
		return pytDesc;
	}

	/**
	 * @param pytDesc the pytDesc to set
	 */
	public void setPytDesc(String pytDesc) {
		this.pytDesc = pytDesc;
	}

	/**
	 * @return the pytDescOth
	 */
	public String getPytDescOth() {
		return pytDescOth;
	}

	/**
	 * @param pytDescOth the pytDescOth to set
	 */
	public void setPytDescOth(String pytDescOth) {
		this.pytDescOth = pytDescOth;
	}

	/**
	 * @return the pytStatus
	 */
	public Character getPytStatus() {
		return pytStatus;
	}

	/**
	 * @param pytStatus the pytStatus to set
	 */
	public void setPytStatus(Character pytStatus) {
		this.pytStatus = pytStatus;
	}

	/**
	 * @return the pytDtCreate
	 */
	public Date getPytDtCreate() {
		return pytDtCreate;
	}

	/**
	 * @param pytDtCreate the pytDtCreate to set
	 */
	public void setPytDtCreate(Date pytDtCreate) {
		this.pytDtCreate = pytDtCreate;
	}

	/**
	 * @return the pytUidCreate
	 */
	public String getPytUidCreate() {
		return pytUidCreate;
	}

	/**
	 * @param pytUidCreate the pytUidCreate to set
	 */
	public void setPytUidCreate(String pytUidCreate) {
		this.pytUidCreate = pytUidCreate;
	}

	/**
	 * @return the pytDtLupd
	 */
	public Date getPytDtLupd() {
		return pytDtLupd;
	}

	/**
	 * @param pytDtLupd the pytDtLupd to set
	 */
	public void setPytDtLupd(Date pytDtLupd) {
		this.pytDtLupd = pytDtLupd;
	}

	/**
	 * @return the pytUidLupd
	 */
	public String getPytUidLupd() {
		return pytUidLupd;
	}

	/**
	 * @param pytUidLupd the pytUidLupd to set
	 */
	public void setPytUidLupd(String pytUidLupd) {
		this.pytUidLupd = pytUidLupd;
	}
}
