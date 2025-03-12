package com.guudint.clickargo.master.dto;

import java.util.Date;

import javax.validation.constraints.Size;

import com.guudint.clickargo.master.model.TCkMstJobState;
import com.guudint.clickargo.validator.ValidationGroup.CreateValid;
import com.guudint.clickargo.validator.ValidationGroup.SubmitValid;
import com.guudint.clickargo.validator.ValidationGroup.UpdateValid;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkMstJobState extends AbstractDTO<CkMstJobState, TCkMstJobState> {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = -511317347528661860L;

	// Attributes
	/////////////
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 35, message = "{valid.master.jbstId.maxLength}")
	private String jbstId;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 100, message = "{valid.master.jbstName.maxLength}")
	private String jbstName;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 255, message = "{valid.master.jbstDesc.maxLength}")
	private String jbstDesc;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 512, message = "{valid.master.jbstDescOth.maxLength}")
	private String jbstDescOth;
	private Character jbstStatus;
	private Date jbstDtCreate;
	private String jbstUidCreate;
	private Date jbstDtLupd;
	private String jbstUidLupd;

	// Constructors
	////////////////
	public CkMstJobState() {
	}

	/**
	 * @param entity
	 */
	public CkMstJobState(TCkMstJobState entity) {
		super(entity);
	}

	/**
	 * @param jbstId
	 * @param jbstName
	 */
	public CkMstJobState(String jbstId, String jbstName) {
		this.jbstId = jbstId;
		this.jbstName = jbstName;
	}

	/**
	 * @param jbstId
	 * @param jbstName
	 * @param jbstDesc
	 * @param jbstDescOth
	 * @param jbstStatus
	 * @param jbstDtCreate
	 * @param jbstUidCreate
	 * @param jbstDtLupd
	 * @param jbstUidLupd
	 */
	public CkMstJobState(String jbstId, String jbstName, String jbstDesc, String jbstDescOth, Character jbstStatus,
			Date jbstDtCreate, String jbstUidCreate, Date jbstDtLupd, String jbstUidLupd) {
		this.jbstId = jbstId;
		this.jbstName = jbstName;
		this.jbstDesc = jbstDesc;
		this.jbstDescOth = jbstDescOth;
		this.jbstStatus = jbstStatus;
		this.jbstDtCreate = jbstDtCreate;
		this.jbstUidCreate = jbstUidCreate;
		this.jbstDtLupd = jbstDtLupd;
		this.jbstUidLupd = jbstUidLupd;
	}

	// Override Methods
	///////////////////
	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * 
	 */
	public int compareTo(CkMstJobState o) {
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
	 * @return the jbstId
	 */
	public String getJbstId() {
		return jbstId;
	}

	/**
	 * @param jbstId the jbstId to set
	 */
	public void setJbstId(String jbstId) {
		this.jbstId = jbstId;
	}

	/**
	 * @return the jbstName
	 */
	public String getJbstName() {
		return jbstName;
	}

	/**
	 * @param jbstName the jbstName to set
	 */
	public void setJbstName(String jbstName) {
		this.jbstName = jbstName;
	}

	/**
	 * @return the jbstDesc
	 */
	public String getJbstDesc() {
		return jbstDesc;
	}

	/**
	 * @param jbstDesc the jbstDesc to set
	 */
	public void setJbstDesc(String jbstDesc) {
		this.jbstDesc = jbstDesc;
	}

	/**
	 * @return the jbstDescOth
	 */
	public String getJbstDescOth() {
		return jbstDescOth;
	}

	/**
	 * @param jbstDescOth the jbstDescOth to set
	 */
	public void setJbstDescOth(String jbstDescOth) {
		this.jbstDescOth = jbstDescOth;
	}

	/**
	 * @return the jbstStatus
	 */
	public Character getJbstStatus() {
		return jbstStatus;
	}

	/**
	 * @param jbstStatus the jbstStatus to set
	 */
	public void setJbstStatus(Character jbstStatus) {
		this.jbstStatus = jbstStatus;
	}

	/**
	 * @return the jbstDtCreate
	 */
	public Date getJbstDtCreate() {
		return jbstDtCreate;
	}

	/**
	 * @param jbstDtCreate the jbstDtCreate to set
	 */
	public void setJbstDtCreate(Date jbstDtCreate) {
		this.jbstDtCreate = jbstDtCreate;
	}

	/**
	 * @return the jbstUidCreate
	 */
	public String getJbstUidCreate() {
		return jbstUidCreate;
	}

	/**
	 * @param jbstUidCreate the jbstUidCreate to set
	 */
	public void setJbstUidCreate(String jbstUidCreate) {
		this.jbstUidCreate = jbstUidCreate;
	}

	/**
	 * @return the jbstDtLupd
	 */
	public Date getJbstDtLupd() {
		return jbstDtLupd;
	}

	/**
	 * @param jbstDtLupd the jbstDtLupd to set
	 */
	public void setJbstDtLupd(Date jbstDtLupd) {
		this.jbstDtLupd = jbstDtLupd;
	}

	/**
	 * @return the jbstUidLupd
	 */
	public String getJbstUidLupd() {
		return jbstUidLupd;
	}

	/**
	 * @param jbstUidLupd the jbstUidLupd to set
	 */
	public void setJbstUidLupd(String jbstUidLupd) {
		this.jbstUidLupd = jbstUidLupd;
	}
}
