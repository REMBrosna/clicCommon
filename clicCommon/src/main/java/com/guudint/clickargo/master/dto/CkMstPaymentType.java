package com.guudint.clickargo.master.dto;

import java.util.Date;

import javax.validation.constraints.Size;

import com.guudint.clickargo.master.model.TCkMstPaymentType;
import com.guudint.clickargo.validator.ValidationGroup.CreateValid;
import com.guudint.clickargo.validator.ValidationGroup.SubmitValid;
import com.guudint.clickargo.validator.ValidationGroup.UpdateValid;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkMstPaymentType extends AbstractDTO<CkMstPaymentType, TCkMstPaymentType> {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = -3645494372993803026L;

	// Attributes
	/////////////
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 35, message = "{valid.master.ptyId.maxLength}")
	private String ptyId;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 100, message = "{valid.master.type.pytName.maxLength}")
	private String pytName;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 255, message = "{valid.master.type.pytDesc.maxLength}")
	private String pytDesc;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 512, message = "{valid.master.type.pytDescOth.maxLength}")
	private String pytDescOth;
	private Character pytStatus;
	private Date pytDtCreate;
	private String pytUidCreate;
	private Date pytDtLupd;
	private String pytUidLupd;

	// Constructors
	////////////////
	public CkMstPaymentType() {
	}

	/**
	 * @param entity
	 */
	public CkMstPaymentType(TCkMstPaymentType entity) {
		super(entity);
	}	
	/**
	 * @param ptyId
	 * @param pytName
	 */
	public CkMstPaymentType(String ptyId, String pytName) {
		this.ptyId = ptyId;
		this.pytName = pytName;
	}

	/**
	 * @param ptyId
	 * @param pytName
	 * @param pytDesc
	 * @param pytDescOth
	 * @param pytStatus
	 * @param pytDtCreate
	 * @param pytUidCreate
	 * @param pytDtLupd
	 * @param pytUidLupd
	 */
	public CkMstPaymentType(String ptyId, String pytName, String pytDesc, String pytDescOth, Character pytStatus,
			Date pytDtCreate, String pytUidCreate, Date pytDtLupd, String pytUidLupd) {
		this.ptyId = ptyId;
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
	public int compareTo(CkMstPaymentType o) {
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
	 * @return the ptyId
	 */
	public String getPtyId() {
		return ptyId;
	}

	/**
	 * @param ptyId the ptyId to set
	 */
	public void setPtyId(String ptyId) {
		this.ptyId = ptyId;
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
