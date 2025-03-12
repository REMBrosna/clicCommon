package com.guudint.clickargo.master.dto;

import java.util.Date;

import javax.validation.constraints.Size;

import com.guudint.clickargo.master.model.TCkMstJobType;
import com.guudint.clickargo.validator.ValidationGroup.CreateValid;
import com.guudint.clickargo.validator.ValidationGroup.SubmitValid;
import com.guudint.clickargo.validator.ValidationGroup.UpdateValid;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkMstJobType extends AbstractDTO<CkMstJobType, TCkMstJobType> {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = -2405736611924834422L;

	// Attributes
	//////////////
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 35, message = "{valid.master.jbtId.maxLength}")
	private String jbtId;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 100, message = "{valid.master.jbtName.maxLength}")
	private String jbtName;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 255, message = "{valid.master.jbtDesc.maxLength}")
	private String jbtDesc;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 512, message = "{valid.master.jbtDescOth.maxLength}")
	private String jbtDescOth;
	private Character jbtStatus;
	private Date jbtDtCreate;
	private String jbtUidCreate;
	private Date jbtDtLupd;
	private String jbtUidLupd;

	// Constructors
	///////////////
	public CkMstJobType() {
	}

	/**
	 * @param entity
	 */
	public CkMstJobType(TCkMstJobType entity) {
		super(entity);
	}

	/**
	 * @param jbtId
	 * @param jbtName
	 */
	public CkMstJobType(String jbtId, String jbtName) {
		this.jbtId = jbtId;
		this.jbtName = jbtName;
	}

	/**
	 * @param jbtId
	 * @param jbtName
	 * @param jbtDesc
	 * @param jbtDescOth
	 * @param jbtStatus
	 * @param jbtDtCreate
	 * @param jbtUidCreate
	 * @param jbtDtLupd
	 * @param jbtUidLupd
	 */
	public CkMstJobType(String jbtId, String jbtName, String jbtDesc, String jbtDescOth, Character jbtStatus,
			Date jbtDtCreate, String jbtUidCreate, Date jbtDtLupd, String jbtUidLupd) {
		this.jbtId = jbtId;
		this.jbtName = jbtName;
		this.jbtDesc = jbtDesc;
		this.jbtDescOth = jbtDescOth;
		this.jbtStatus = jbtStatus;
		this.jbtDtCreate = jbtDtCreate;
		this.jbtUidCreate = jbtUidCreate;
		this.jbtDtLupd = jbtDtLupd;
		this.jbtUidLupd = jbtUidLupd;
	}

	// Override Methods
	///////////////////
	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * 
	 */
	public int compareTo(CkMstJobType o) {
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
	 * @return the jbtId
	 */
	public String getJbtId() {
		return jbtId;
	}

	/**
	 * @param jbtId the jbtId to set
	 */
	public void setJbtId(String jbtId) {
		this.jbtId = jbtId;
	}

	/**
	 * @return the jbtName
	 */
	public String getJbtName() {
		return jbtName;
	}

	/**
	 * @param jbtName the jbtName to set
	 */
	public void setJbtName(String jbtName) {
		this.jbtName = jbtName;
	}

	/**
	 * @return the jbtDesc
	 */
	public String getJbtDesc() {
		return jbtDesc;
	}

	/**
	 * @param jbtDesc the jbtDesc to set
	 */
	public void setJbtDesc(String jbtDesc) {
		this.jbtDesc = jbtDesc;
	}

	/**
	 * @return the jbtDescOth
	 */
	public String getJbtDescOth() {
		return jbtDescOth;
	}

	/**
	 * @param jbtDescOth the jbtDescOth to set
	 */
	public void setJbtDescOth(String jbtDescOth) {
		this.jbtDescOth = jbtDescOth;
	}

	/**
	 * @return the jbtStatus
	 */
	public Character getJbtStatus() {
		return jbtStatus;
	}

	/**
	 * @param jbtStatus the jbtStatus to set
	 */
	public void setJbtStatus(Character jbtStatus) {
		this.jbtStatus = jbtStatus;
	}

	/**
	 * @return the jbtDtCreate
	 */
	public Date getJbtDtCreate() {
		return jbtDtCreate;
	}

	/**
	 * @param jbtDtCreate the jbtDtCreate to set
	 */
	public void setJbtDtCreate(Date jbtDtCreate) {
		this.jbtDtCreate = jbtDtCreate;
	}

	/**
	 * @return the jbtUidCreate
	 */
	public String getJbtUidCreate() {
		return jbtUidCreate;
	}

	/**
	 * @param jbtUidCreate the jbtUidCreate to set
	 */
	public void setJbtUidCreate(String jbtUidCreate) {
		this.jbtUidCreate = jbtUidCreate;
	}

	/**
	 * @return the jbtDtLupd
	 */
	public Date getJbtDtLupd() {
		return jbtDtLupd;
	}

	/**
	 * @param jbtDtLupd the jbtDtLupd to set
	 */
	public void setJbtDtLupd(Date jbtDtLupd) {
		this.jbtDtLupd = jbtDtLupd;
	}

	/**
	 * @return the jbtUidLupd
	 */
	public String getJbtUidLupd() {
		return jbtUidLupd;
	}

	/**
	 * @param jbtUidLupd the jbtUidLupd to set
	 */
	public void setJbtUidLupd(String jbtUidLupd) {
		this.jbtUidLupd = jbtUidLupd;
	}
}
