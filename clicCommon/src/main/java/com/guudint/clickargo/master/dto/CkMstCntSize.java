package com.guudint.clickargo.master.dto;

import java.util.Date;

import javax.validation.constraints.Size;

import com.guudint.clickargo.master.model.TCkMstCntSize;
import com.guudint.clickargo.validator.ValidationGroup.CreateValid;
import com.guudint.clickargo.validator.ValidationGroup.SubmitValid;
import com.guudint.clickargo.validator.ValidationGroup.UpdateValid;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkMstCntSize extends AbstractDTO<CkMstCntSize, TCkMstCntSize> {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = 6751239185144820542L;

	// Attributes
	/////////////
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 35, message = "{valid.master.cntzId.maxLength}")
	private String cntzId;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 100, message = "{valid.master.cntzName.maxLength}")
	private String cntzName;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 255, message = "{valid.master.cntzDesc.maxLength}")
	private String cntzDesc;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 512, message = "{valid.master.cntzDescOth.maxLength}")
	private String cntzDescOth;
	private Character cntzStatus;
	private Date cntzDtCreate;
	private String cntzUidCreate;
	private Date cntzDtLupd;
	private String cntzUidLupd;

	// Constructors
	///////////////
	public CkMstCntSize() {
	}

	/**
	 * @param entity
	 */
	public CkMstCntSize(TCkMstCntSize entity) {
		super(entity);
	}

	/**
	 * @param cntzId
	 * @param cntzName
	 */
	public CkMstCntSize(String cntzId, String cntzName) {
		this.cntzId = cntzId;
		this.cntzName = cntzName;
	}

	/**
	 * @param cntzId
	 * @param cntzName
	 * @param cntzDesc
	 * @param cntzDescOth
	 * @param cntzStatus
	 * @param cntzDtCreate
	 * @param cntzUidCreate
	 * @param cntzDtLupd
	 * @param cntzUidLupd
	 */
	public CkMstCntSize(String cntzId, String cntzName, String cntzDesc, String cntzDescOth, Character cntzStatus,
			Date cntzDtCreate, String cntzUidCreate, Date cntzDtLupd, String cntzUidLupd) {
		this.cntzId = cntzId;
		this.cntzName = cntzName;
		this.cntzDesc = cntzDesc;
		this.cntzDescOth = cntzDescOth;
		this.cntzStatus = cntzStatus;
		this.cntzDtCreate = cntzDtCreate;
		this.cntzUidCreate = cntzUidCreate;
		this.cntzDtLupd = cntzDtLupd;
		this.cntzUidLupd = cntzUidLupd;
	}

	// Override Methods
	///////////////////
	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * 
	 */
	public int compareTo(CkMstCntSize o) {
		// TODO Auto-generated method stub
		return 0;
	}

	// Properties
	/////////////
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

	/**
	 * @return the cntzId
	 */
	public String getCntzId() {
		return cntzId;
	}

	/**
	 * @param cntzId the cntzId to set
	 */
	public void setCntzId(String cntzId) {
		this.cntzId = cntzId;
	}

	/**
	 * @return the cntzName
	 */
	public String getCntzName() {
		return cntzName;
	}

	/**
	 * @param cntzName the cntzName to set
	 */
	public void setCntzName(String cntzName) {
		this.cntzName = cntzName;
	}

	/**
	 * @return the cntzDesc
	 */
	public String getCntzDesc() {
		return cntzDesc;
	}

	/**
	 * @param cntzDesc the cntzDesc to set
	 */
	public void setCntzDesc(String cntzDesc) {
		this.cntzDesc = cntzDesc;
	}

	/**
	 * @return the cntzDescOth
	 */
	public String getCntzDescOth() {
		return cntzDescOth;
	}

	/**
	 * @param cntzDescOth the cntzDescOth to set
	 */
	public void setCntzDescOth(String cntzDescOth) {
		this.cntzDescOth = cntzDescOth;
	}

	/**
	 * @return the cntzStatus
	 */
	public Character getCntzStatus() {
		return cntzStatus;
	}

	/**
	 * @param cntzStatus the cntzStatus to set
	 */
	public void setCntzStatus(Character cntzStatus) {
		this.cntzStatus = cntzStatus;
	}

	/**
	 * @return the cntzDtCreate
	 */
	public Date getCntzDtCreate() {
		return cntzDtCreate;
	}

	/**
	 * @param cntzDtCreate the cntzDtCreate to set
	 */
	public void setCntzDtCreate(Date cntzDtCreate) {
		this.cntzDtCreate = cntzDtCreate;
	}

	/**
	 * @return the cntzUidCreate
	 */
	public String getCntzUidCreate() {
		return cntzUidCreate;
	}

	/**
	 * @param cntzUidCreate the cntzUidCreate to set
	 */
	public void setCntzUidCreate(String cntzUidCreate) {
		this.cntzUidCreate = cntzUidCreate;
	}

	/**
	 * @return the cntzDtLupd
	 */
	public Date getCntzDtLupd() {
		return cntzDtLupd;
	}

	/**
	 * @param cntzDtLupd the cntzDtLupd to set
	 */
	public void setCntzDtLupd(Date cntzDtLupd) {
		this.cntzDtLupd = cntzDtLupd;
	}

	/**
	 * @return the cntzUidLupd
	 */
	public String getCntzUidLupd() {
		return cntzUidLupd;
	}

	/**
	 * @param cntzUidLupd the cntzUidLupd to set
	 */
	public void setCntzUidLupd(String cntzUidLupd) {
		this.cntzUidLupd = cntzUidLupd;
	}
}
