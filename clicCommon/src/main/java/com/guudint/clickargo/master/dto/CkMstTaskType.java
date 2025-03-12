package com.guudint.clickargo.master.dto;

import java.util.Date;

import javax.validation.constraints.Size;

import com.guudint.clickargo.master.model.TCkMstTaskType;
import com.guudint.clickargo.validator.ValidationGroup.CreateValid;
import com.guudint.clickargo.validator.ValidationGroup.SubmitValid;
import com.guudint.clickargo.validator.ValidationGroup.UpdateValid;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkMstTaskType extends AbstractDTO<CkMstTaskType, TCkMstTaskType> {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = 2695661206759478710L;

	// Attributes
	/////////////
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 35, message = "{valid.master.tsktId.maxLength}")
	private String tsktId;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 100, message = "{valid.master.tsktName.maxLength}")
	private String tsktName;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 255, message = "{valid.master.tsktDesc.maxLength}")
	private String tsktDesc;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 512, message = "{valid.master.tsktDescOth.maxLength}")
	private String tsktDescOth;
	private Character tsktStatus;
	private Date tsktDtCreate;
	private String tsktUidCreate;
	private Date tsktDtLupd;
	private String tsktUidLupd;

	// Constructors
	///////////////
	public CkMstTaskType() {
	}

	/**
	 * @param entity
	 */
	public CkMstTaskType(TCkMstTaskType entity) {
		super(entity);
	}

	/**
	 * @param tsktId
	 * @param tsktName
	 */
	public CkMstTaskType(String tsktId, String tsktName) {
		this.tsktId = tsktId;
		this.tsktName = tsktName;
	}

	/**
	 * @param tsktId
	 * @param tsktName
	 * @param tsktDesc
	 * @param tsktDescOth
	 * @param tsktStatus
	 * @param tsktDtCreate
	 * @param tsktUidCreate
	 * @param tsktDtLupd
	 * @param tsktUidLupd
	 */
	public CkMstTaskType(String tsktId, String tsktName, String tsktDesc, String tsktDescOth, Character tsktStatus,
			Date tsktDtCreate, String tsktUidCreate, Date tsktDtLupd, String tsktUidLupd) {
		this.tsktId = tsktId;
		this.tsktName = tsktName;
		this.tsktDesc = tsktDesc;
		this.tsktDescOth = tsktDescOth;
		this.tsktStatus = tsktStatus;
		this.tsktDtCreate = tsktDtCreate;
		this.tsktUidCreate = tsktUidCreate;
		this.tsktDtLupd = tsktDtLupd;
		this.tsktUidLupd = tsktUidLupd;
	}

	// Override Methods
	///////////////////
	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * 
	 */
	public int compareTo(CkMstTaskType o) {
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
	 * @return the tsktId
	 */
	public String getTsktId() {
		return tsktId;
	}

	/**
	 * @param tsktId the tsktId to set
	 */
	public void setTsktId(String tsktId) {
		this.tsktId = tsktId;
	}

	/**
	 * @return the tsktName
	 */
	public String getTsktName() {
		return tsktName;
	}

	/**
	 * @param tsktName the tsktName to set
	 */
	public void setTsktName(String tsktName) {
		this.tsktName = tsktName;
	}

	/**
	 * @return the tsktDesc
	 */
	public String getTsktDesc() {
		return tsktDesc;
	}

	/**
	 * @param tsktDesc the tsktDesc to set
	 */
	public void setTsktDesc(String tsktDesc) {
		this.tsktDesc = tsktDesc;
	}

	/**
	 * @return the tsktDescOth
	 */
	public String getTsktDescOth() {
		return tsktDescOth;
	}

	/**
	 * @param tsktDescOth the tsktDescOth to set
	 */
	public void setTsktDescOth(String tsktDescOth) {
		this.tsktDescOth = tsktDescOth;
	}

	/**
	 * @return the tsktStatus
	 */
	public Character getTsktStatus() {
		return tsktStatus;
	}

	/**
	 * @param tsktStatus the tsktStatus to set
	 */
	public void setTsktStatus(Character tsktStatus) {
		this.tsktStatus = tsktStatus;
	}

	/**
	 * @return the tsktDtCreate
	 */
	public Date getTsktDtCreate() {
		return tsktDtCreate;
	}

	/**
	 * @param tsktDtCreate the tsktDtCreate to set
	 */
	public void setTsktDtCreate(Date tsktDtCreate) {
		this.tsktDtCreate = tsktDtCreate;
	}

	/**
	 * @return the tsktUidCreate
	 */
	public String getTsktUidCreate() {
		return tsktUidCreate;
	}

	/**
	 * @param tsktUidCreate the tsktUidCreate to set
	 */
	public void setTsktUidCreate(String tsktUidCreate) {
		this.tsktUidCreate = tsktUidCreate;
	}

	/**
	 * @return the tsktDtLupd
	 */
	public Date getTsktDtLupd() {
		return tsktDtLupd;
	}

	/**
	 * @param tsktDtLupd the tsktDtLupd to set
	 */
	public void setTsktDtLupd(Date tsktDtLupd) {
		this.tsktDtLupd = tsktDtLupd;
	}

	/**
	 * @return the tsktUidLupd
	 */
	public String getTsktUidLupd() {
		return tsktUidLupd;
	}

	/**
	 * @param tsktUidLupd the tsktUidLupd to set
	 */
	public void setTsktUidLupd(String tsktUidLupd) {
		this.tsktUidLupd = tsktUidLupd;
	}
}
