package com.guudint.clickargo.master.dto;

import java.util.Date;

import javax.validation.constraints.Size;

import com.guudint.clickargo.master.model.TCkMstAuthAccnType;
import com.guudint.clickargo.validator.ValidationGroup.CreateValid;
import com.guudint.clickargo.validator.ValidationGroup.SubmitValid;
import com.guudint.clickargo.validator.ValidationGroup.UpdateValid;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkMstAuthAccnType extends AbstractDTO<CkMstAuthAccnType, TCkMstAuthAccnType> {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = -6080495113188700019L;

	// Attributes
	//////////////
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 35, message = "{valid.master.auatId.maxLength}")
	private String auatId;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 100, message = "{valid.master.auatName.maxLength}")
	private String auatName;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 255, message = "{valid.master.auatDesc.maxLength}")
	private String auatDesc;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 512, message = "{valid.master.auatDescOth.maxLength}")
	private String auatDescOth;
	private Character auatStatus;
	private Date auatDtCreate;
	private String auatUidCreate;
	private Date auatDtLupd;
	private String auatUidLupd;

	// Constructors
	///////////////
	public CkMstAuthAccnType() {
	}
	
	/**
	 * @param entity
	 */
	public CkMstAuthAccnType(TCkMstAuthAccnType entity) {
		super(entity);
	}	

	/**
	 * @param auatId
	 * @param auatName
	 */
	public CkMstAuthAccnType(String auatId, String auatName) {
		this.auatId = auatId;
		this.auatName = auatName;
	}

	/**
	 * @param auatId
	 * @param auatName
	 * @param auatDesc
	 * @param auatDescOth
	 * @param auatStatus
	 * @param auatDtCreate
	 * @param auatUidCreate
	 * @param auatDtLupd
	 * @param auatUidLupd
	 */
	public CkMstAuthAccnType(String auatId, String auatName, String auatDesc, String auatDescOth, Character auatStatus,
			Date auatDtCreate, String auatUidCreate, Date auatDtLupd, String auatUidLupd) {
		this.auatId = auatId;
		this.auatName = auatName;
		this.auatDesc = auatDesc;
		this.auatDescOth = auatDescOth;
		this.auatStatus = auatStatus;
		this.auatDtCreate = auatDtCreate;
		this.auatUidCreate = auatUidCreate;
		this.auatDtLupd = auatDtLupd;
		this.auatUidLupd = auatUidLupd;
	}

	// Override Methods
	///////////////////
	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * 
	 */
	public int compareTo(CkMstAuthAccnType arg0) {
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
	 * @return the auatId
	 */
	public String getAuatId() {
		return auatId;
	}

	/**
	 * @param auatId the auatId to set
	 */
	public void setAuatId(String auatId) {
		this.auatId = auatId;
	}

	/**
	 * @return the auatName
	 */
	public String getAuatName() {
		return auatName;
	}

	/**
	 * @param auatName the auatName to set
	 */
	public void setAuatName(String auatName) {
		this.auatName = auatName;
	}

	/**
	 * @return the auatDesc
	 */
	public String getAuatDesc() {
		return auatDesc;
	}

	/**
	 * @param auatDesc the auatDesc to set
	 */
	public void setAuatDesc(String auatDesc) {
		this.auatDesc = auatDesc;
	}

	/**
	 * @return the auatDescOth
	 */
	public String getAuatDescOth() {
		return auatDescOth;
	}

	/**
	 * @param auatDescOth the auatDescOth to set
	 */
	public void setAuatDescOth(String auatDescOth) {
		this.auatDescOth = auatDescOth;
	}

	/**
	 * @return the auatStatus
	 */
	public Character getAuatStatus() {
		return auatStatus;
	}

	/**
	 * @param auatStatus the auatStatus to set
	 */
	public void setAuatStatus(Character auatStatus) {
		this.auatStatus = auatStatus;
	}

	/**
	 * @return the auatDtCreate
	 */
	public Date getAuatDtCreate() {
		return auatDtCreate;
	}

	/**
	 * @param auatDtCreate the auatDtCreate to set
	 */
	public void setAuatDtCreate(Date auatDtCreate) {
		this.auatDtCreate = auatDtCreate;
	}

	/**
	 * @return the auatUidCreate
	 */
	public String getAuatUidCreate() {
		return auatUidCreate;
	}

	/**
	 * @param auatUidCreate the auatUidCreate to set
	 */
	public void setAuatUidCreate(String auatUidCreate) {
		this.auatUidCreate = auatUidCreate;
	}

	/**
	 * @return the auatDtLupd
	 */
	public Date getAuatDtLupd() {
		return auatDtLupd;
	}

	/**
	 * @param auatDtLupd the auatDtLupd to set
	 */
	public void setAuatDtLupd(Date auatDtLupd) {
		this.auatDtLupd = auatDtLupd;
	}

	/**
	 * @return the auatUidLupd
	 */
	public String getAuatUidLupd() {
		return auatUidLupd;
	}

	/**
	 * @param auatUidLupd the auatUidLupd to set
	 */
	public void setAuatUidLupd(String auatUidLupd) {
		this.auatUidLupd = auatUidLupd;
	}
}
