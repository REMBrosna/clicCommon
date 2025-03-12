package com.guudint.clickargo.common.dto;

import java.util.Date;

import com.guudint.clickargo.common.model.TCkMstFormType;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkMstFormType extends AbstractDTO<CkMstFormType, TCkMstFormType> {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = -55149611497468968L;

	// Attributes
	//////////////
	private String fmtypId;
	private String fmtypName;
	private String fmtypDesc;
	private String fmtypDescOth;
	private Character fmtypStatus;
	private Date fmtypDtCreate;
	private String fmtypUidCreate;
	private Date fmtypDtLupd;
	private String fmtypUidLupd;

	// Constructors
	///////////////
	public CkMstFormType() {
	}

	/**
	 * @param entity
	 */
	public CkMstFormType(TCkMstFormType entity) {
		super(entity);
	}

	/**
	 * @param fmtypId
	 * @param fmtypName
	 */
	public CkMstFormType(String fmtypId, String fmtypName) {
		this.fmtypId = fmtypId;
		this.fmtypName = fmtypName;
	}

	/**
	 * @param fmtypId
	 * @param fmtypName
	 * @param fmtypDesc
	 * @param fmtypDescOth
	 * @param fmtypStatus
	 * @param fmtypDtCreate
	 * @param fmtypUidCreate
	 * @param fmtypDtLupd
	 * @param fmtypUidLupd
	 */
	public CkMstFormType(String fmtypId, String fmtypName, String fmtypDesc, String fmtypDescOth, Character fmtypStatus,
			Date fmtypDtCreate, String fmtypUidCreate, Date fmtypDtLupd, String fmtypUidLupd) {
		this.fmtypId = fmtypId;
		this.fmtypName = fmtypName;
		this.fmtypDesc = fmtypDesc;
		this.fmtypDescOth = fmtypDescOth;
		this.fmtypStatus = fmtypStatus;
		this.fmtypDtCreate = fmtypDtCreate;
		this.fmtypUidCreate = fmtypUidCreate;
		this.fmtypDtLupd = fmtypDtLupd;
		this.fmtypUidLupd = fmtypUidLupd;
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
	public int compareTo(CkMstFormType o) {
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
	 * @return the fmtypId
	 */
	public String getFmtypId() {
		return fmtypId;
	}

	/**
	 * @param fmtypId the fmtypId to set
	 */
	public void setFmtypId(String fmtypId) {
		this.fmtypId = fmtypId;
	}

	/**
	 * @return the fmtypName
	 */
	public String getFmtypName() {
		return fmtypName;
	}

	/**
	 * @param fmtypName the fmtypName to set
	 */
	public void setFmtypName(String fmtypName) {
		this.fmtypName = fmtypName;
	}

	/**
	 * @return the fmtypDesc
	 */
	public String getFmtypDesc() {
		return fmtypDesc;
	}

	/**
	 * @param fmtypDesc the fmtypDesc to set
	 */
	public void setFmtypDesc(String fmtypDesc) {
		this.fmtypDesc = fmtypDesc;
	}

	/**
	 * @return the fmtypDescOth
	 */
	public String getFmtypDescOth() {
		return fmtypDescOth;
	}

	/**
	 * @param fmtypDescOth the fmtypDescOth to set
	 */
	public void setFmtypDescOth(String fmtypDescOth) {
		this.fmtypDescOth = fmtypDescOth;
	}

	/**
	 * @return the fmtypStatus
	 */
	public Character getFmtypStatus() {
		return fmtypStatus;
	}

	/**
	 * @param fmtypStatus the fmtypStatus to set
	 */
	public void setFmtypStatus(Character fmtypStatus) {
		this.fmtypStatus = fmtypStatus;
	}

	/**
	 * @return the fmtypDtCreate
	 */
	public Date getFmtypDtCreate() {
		return fmtypDtCreate;
	}

	/**
	 * @param fmtypDtCreate the fmtypDtCreate to set
	 */
	public void setFmtypDtCreate(Date fmtypDtCreate) {
		this.fmtypDtCreate = fmtypDtCreate;
	}

	/**
	 * @return the fmtypUidCreate
	 */
	public String getFmtypUidCreate() {
		return fmtypUidCreate;
	}

	/**
	 * @param fmtypUidCreate the fmtypUidCreate to set
	 */
	public void setFmtypUidCreate(String fmtypUidCreate) {
		this.fmtypUidCreate = fmtypUidCreate;
	}

	/**
	 * @return the fmtypDtLupd
	 */
	public Date getFmtypDtLupd() {
		return fmtypDtLupd;
	}

	/**
	 * @param fmtypDtLupd the fmtypDtLupd to set
	 */
	public void setFmtypDtLupd(Date fmtypDtLupd) {
		this.fmtypDtLupd = fmtypDtLupd;
	}

	/**
	 * @return the fmtypUidLupd
	 */
	public String getFmtypUidLupd() {
		return fmtypUidLupd;
	}

	/**
	 * @param fmtypUidLupd the fmtypUidLupd to set
	 */
	public void setFmtypUidLupd(String fmtypUidLupd) {
		this.fmtypUidLupd = fmtypUidLupd;
	}
}
