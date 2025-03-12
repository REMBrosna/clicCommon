package com.guudint.clickargo.master.dto;

import java.util.Date;

import javax.validation.constraints.Size;

import com.guudint.clickargo.master.model.TCkMstTaskState;
import com.guudint.clickargo.validator.ValidationGroup.CreateValid;
import com.guudint.clickargo.validator.ValidationGroup.SubmitValid;
import com.guudint.clickargo.validator.ValidationGroup.UpdateValid;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkMstTaskState extends AbstractDTO<CkMstTaskState, TCkMstTaskState> {

	// Static Attributes
	///////////////////
	private static final long serialVersionUID = -5635060284122675732L;

	// Attributes
	////////////
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 35, message = "{valid.master.tskstId.maxLength}")
	private String tskstId;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 100, message = "{valid.master.tskstName.maxLength}")
	private String tskstName;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 255, message = "{valid.master.tskstDesc.maxLength}")
	private String tskstDesc;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 512, message = "{valid.master.tskstDescOth.maxLength}")
	private String tskstDescOth;
	private Character tskstStatus;
	private Date tskstDtCreate;
	private String tskstUidCreate;
	private Date tskstDtLupd;
	private String tskstUidLupd;

	// Constructors
	///////////////
	public CkMstTaskState() {
	}

	/**
	 * @param entity
	 */
	public CkMstTaskState(TCkMstTaskState entity) {
		super(entity);
	}

	/**
	 * @param tskstId
	 * @param tskstName
	 */
	public CkMstTaskState(String tskstId, String tskstName) {
		this.tskstId = tskstId;
		this.tskstName = tskstName;
	}

	/**
	 * @param tskstId
	 * @param tskstName
	 * @param tskstDesc
	 * @param tskstDescOth
	 * @param tskstStatus
	 * @param tskstDtCreate
	 * @param tskstUidCreate
	 * @param tskstDtLupd
	 * @param tskstUidLupd
	 */
	public CkMstTaskState(String tskstId, String tskstName, String tskstDesc, String tskstDescOth,
			Character tskstStatus, Date tskstDtCreate, String tskstUidCreate, Date tskstDtLupd, String tskstUidLupd) {
		this.tskstId = tskstId;
		this.tskstName = tskstName;
		this.tskstDesc = tskstDesc;
		this.tskstDescOth = tskstDescOth;
		this.tskstStatus = tskstStatus;
		this.tskstDtCreate = tskstDtCreate;
		this.tskstUidCreate = tskstUidCreate;
		this.tskstDtLupd = tskstDtLupd;
		this.tskstUidLupd = tskstUidLupd;
	}

	// Override Methods
	///////////////////
	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * 
	 */
	public int compareTo(CkMstTaskState o) {
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
	 * @return the tskstId
	 */
	public String getTskstId() {
		return tskstId;
	}

	/**
	 * @param tskstId the tskstId to set
	 */
	public void setTskstId(String tskstId) {
		this.tskstId = tskstId;
	}

	/**
	 * @return the tskstName
	 */
	public String getTskstName() {
		return tskstName;
	}

	/**
	 * @param tskstName the tskstName to set
	 */
	public void setTskstName(String tskstName) {
		this.tskstName = tskstName;
	}

	/**
	 * @return the tskstDesc
	 */
	public String getTskstDesc() {
		return tskstDesc;
	}

	/**
	 * @param tskstDesc the tskstDesc to set
	 */
	public void setTskstDesc(String tskstDesc) {
		this.tskstDesc = tskstDesc;
	}

	/**
	 * @return the tskstDescOth
	 */
	public String getTskstDescOth() {
		return tskstDescOth;
	}

	/**
	 * @param tskstDescOth the tskstDescOth to set
	 */
	public void setTskstDescOth(String tskstDescOth) {
		this.tskstDescOth = tskstDescOth;
	}

	/**
	 * @return the tskstStatus
	 */
	public Character getTskstStatus() {
		return tskstStatus;
	}

	/**
	 * @param tskstStatus the tskstStatus to set
	 */
	public void setTskstStatus(Character tskstStatus) {
		this.tskstStatus = tskstStatus;
	}

	/**
	 * @return the tskstDtCreate
	 */
	public Date getTskstDtCreate() {
		return tskstDtCreate;
	}

	/**
	 * @param tskstDtCreate the tskstDtCreate to set
	 */
	public void setTskstDtCreate(Date tskstDtCreate) {
		this.tskstDtCreate = tskstDtCreate;
	}

	/**
	 * @return the tskstUidCreate
	 */
	public String getTskstUidCreate() {
		return tskstUidCreate;
	}

	/**
	 * @param tskstUidCreate the tskstUidCreate to set
	 */
	public void setTskstUidCreate(String tskstUidCreate) {
		this.tskstUidCreate = tskstUidCreate;
	}

	/**
	 * @return the tskstDtLupd
	 */
	public Date getTskstDtLupd() {
		return tskstDtLupd;
	}

	/**
	 * @param tskstDtLupd the tskstDtLupd to set
	 */
	public void setTskstDtLupd(Date tskstDtLupd) {
		this.tskstDtLupd = tskstDtLupd;
	}

	/**
	 * @return the tskstUidLupd
	 */
	public String getTskstUidLupd() {
		return tskstUidLupd;
	}

	/**
	 * @param tskstUidLupd the tskstUidLupd to set
	 */
	public void setTskstUidLupd(String tskstUidLupd) {
		this.tskstUidLupd = tskstUidLupd;
	}
}
