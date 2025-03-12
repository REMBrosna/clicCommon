package com.guudint.clickargo.common.dto;

import java.util.Date;

import com.guudint.clickargo.common.model.TCkMstFormAction;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkMstFormAction extends AbstractDTO<CkMstFormAction, TCkMstFormAction> {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = -6663354620694253832L;

	// Attributes
	/////////////
	private String fmactId;
	private String fmactName;
	private String fmactDesc;
	private String fmactDescOth;
	private Character fmactStatus;
	private Date fmactDtCreate;
	private String fmactUidCreate;
	private Date fmactDtLupd;
	private String fmactUidLupd;

	// Constructors
	///////////////
	public CkMstFormAction() {
	}

	/**
	 * @param entity
	 */
	public CkMstFormAction(TCkMstFormAction entity) {
		super(entity);
	}

	/**
	 * @param fmactId
	 * @param fmactName
	 */
	public CkMstFormAction(String fmactId, String fmactName) {
		this.fmactId = fmactId;
		this.fmactName = fmactName;
	}

	/**
	 * @param fmactId
	 * @param fmactName
	 * @param fmactDesc
	 * @param fmactDescOth
	 * @param fmactStatus
	 * @param fmactDtCreate
	 * @param fmactUidCreate
	 * @param fmactDtLupd
	 * @param fmactUidLupd
	 */
	public CkMstFormAction(String fmactId, String fmactName, String fmactDesc, String fmactDescOth,
			Character fmactStatus, Date fmactDtCreate, String fmactUidCreate, Date fmactDtLupd, String fmactUidLupd) {
		this.fmactId = fmactId;
		this.fmactName = fmactName;
		this.fmactDesc = fmactDesc;
		this.fmactDescOth = fmactDescOth;
		this.fmactStatus = fmactStatus;
		this.fmactDtCreate = fmactDtCreate;
		this.fmactUidCreate = fmactUidCreate;
		this.fmactDtLupd = fmactDtLupd;
		this.fmactUidLupd = fmactUidLupd;
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
	public int compareTo(CkMstFormAction o) {
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
	 * @return the fmactId
	 */
	public String getFmactId() {
		return fmactId;
	}

	/**
	 * @param fmactId the fmactId to set
	 */
	public void setFmactId(String fmactId) {
		this.fmactId = fmactId;
	}

	/**
	 * @return the fmactName
	 */
	public String getFmactName() {
		return fmactName;
	}

	/**
	 * @param fmactName the fmactName to set
	 */
	public void setFmactName(String fmactName) {
		this.fmactName = fmactName;
	}

	/**
	 * @return the fmactDesc
	 */
	public String getFmactDesc() {
		return fmactDesc;
	}

	/**
	 * @param fmactDesc the fmactDesc to set
	 */
	public void setFmactDesc(String fmactDesc) {
		this.fmactDesc = fmactDesc;
	}

	/**
	 * @return the fmactDescOth
	 */
	public String getFmactDescOth() {
		return fmactDescOth;
	}

	/**
	 * @param fmactDescOth the fmactDescOth to set
	 */
	public void setFmactDescOth(String fmactDescOth) {
		this.fmactDescOth = fmactDescOth;
	}

	/**
	 * @return the fmactStatus
	 */
	public Character getFmactStatus() {
		return fmactStatus;
	}

	/**
	 * @param fmactStatus the fmactStatus to set
	 */
	public void setFmactStatus(Character fmactStatus) {
		this.fmactStatus = fmactStatus;
	}

	/**
	 * @return the fmactDtCreate
	 */
	public Date getFmactDtCreate() {
		return fmactDtCreate;
	}

	/**
	 * @param fmactDtCreate the fmactDtCreate to set
	 */
	public void setFmactDtCreate(Date fmactDtCreate) {
		this.fmactDtCreate = fmactDtCreate;
	}

	/**
	 * @return the fmactUidCreate
	 */
	public String getFmactUidCreate() {
		return fmactUidCreate;
	}

	/**
	 * @param fmactUidCreate the fmactUidCreate to set
	 */
	public void setFmactUidCreate(String fmactUidCreate) {
		this.fmactUidCreate = fmactUidCreate;
	}

	/**
	 * @return the fmactDtLupd
	 */
	public Date getFmactDtLupd() {
		return fmactDtLupd;
	}

	/**
	 * @param fmactDtLupd the fmactDtLupd to set
	 */
	public void setFmactDtLupd(Date fmactDtLupd) {
		this.fmactDtLupd = fmactDtLupd;
	}

	/**
	 * @return the fmactUidLupd
	 */
	public String getFmactUidLupd() {
		return fmactUidLupd;
	}

	/**
	 * @param fmactUidLupd the fmactUidLupd to set
	 */
	public void setFmactUidLupd(String fmactUidLupd) {
		this.fmactUidLupd = fmactUidLupd;
	}
}
