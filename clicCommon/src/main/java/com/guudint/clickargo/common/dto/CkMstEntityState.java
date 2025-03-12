package com.guudint.clickargo.common.dto;

import java.util.Date;

import com.guudint.clickargo.common.model.TCkMstEntityState;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkMstEntityState extends AbstractDTO<CkMstEntityState, TCkMstEntityState> {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = 4140723126600218268L;

	// Attributes
	/////////////
	private String enstId;
	private String enstName;
	private String enstDesc;
	private String enstDescOth;
	private Character enstStatus;
	private Date enstDtCreate;
	private String enstUidCreate;
	private Date enstDtLupd;
	private String enstUidLupd;

	// Constructors
	///////////////
	public CkMstEntityState() {
	}

	/**
	 * @param entity
	 */
	public CkMstEntityState(TCkMstEntityState entity) {
		super(entity);
	}

	/**
	 * @param enstId
	 * @param enstName
	 */
	public CkMstEntityState(String enstId, String enstName) {
		this.enstId = enstId;
		this.enstName = enstName;
	}

	/**
	 * @param enstId
	 * @param enstName
	 * @param enstDesc
	 * @param enstDescOth
	 * @param enstStatus
	 * @param enstDtCreate
	 * @param enstUidCreate
	 * @param enstDtLupd
	 * @param enstUidLupd
	 */
	public CkMstEntityState(String enstId, String enstName, String enstDesc, String enstDescOth, Character enstStatus,
			Date enstDtCreate, String enstUidCreate, Date enstDtLupd, String enstUidLupd) {
		this.enstId = enstId;
		this.enstName = enstName;
		this.enstDesc = enstDesc;
		this.enstDescOth = enstDescOth;
		this.enstStatus = enstStatus;
		this.enstDtCreate = enstDtCreate;
		this.enstUidCreate = enstUidCreate;
		this.enstDtLupd = enstDtLupd;
		this.enstUidLupd = enstUidLupd;
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
	public int compareTo(CkMstEntityState o) {
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
	 * @return the enstId
	 */
	public String getEnstId() {
		return enstId;
	}

	/**
	 * @param enstId the enstId to set
	 */
	public void setEnstId(String enstId) {
		this.enstId = enstId;
	}

	/**
	 * @return the enstName
	 */
	public String getEnstName() {
		return enstName;
	}

	/**
	 * @param enstName the enstName to set
	 */
	public void setEnstName(String enstName) {
		this.enstName = enstName;
	}

	/**
	 * @return the enstDesc
	 */
	public String getEnstDesc() {
		return enstDesc;
	}

	/**
	 * @param enstDesc the enstDesc to set
	 */
	public void setEnstDesc(String enstDesc) {
		this.enstDesc = enstDesc;
	}

	/**
	 * @return the enstDescOth
	 */
	public String getEnstDescOth() {
		return enstDescOth;
	}

	/**
	 * @param enstDescOth the enstDescOth to set
	 */
	public void setEnstDescOth(String enstDescOth) {
		this.enstDescOth = enstDescOth;
	}

	/**
	 * @return the enstStatus
	 */
	public Character getEnstStatus() {
		return enstStatus;
	}

	/**
	 * @param enstStatus the enstStatus to set
	 */
	public void setEnstStatus(Character enstStatus) {
		this.enstStatus = enstStatus;
	}

	/**
	 * @return the enstDtCreate
	 */
	public Date getEnstDtCreate() {
		return enstDtCreate;
	}

	/**
	 * @param enstDtCreate the enstDtCreate to set
	 */
	public void setEnstDtCreate(Date enstDtCreate) {
		this.enstDtCreate = enstDtCreate;
	}

	/**
	 * @return the enstUidCreate
	 */
	public String getEnstUidCreate() {
		return enstUidCreate;
	}

	/**
	 * @param enstUidCreate the enstUidCreate to set
	 */
	public void setEnstUidCreate(String enstUidCreate) {
		this.enstUidCreate = enstUidCreate;
	}

	/**
	 * @return the enstDtLupd
	 */
	public Date getEnstDtLupd() {
		return enstDtLupd;
	}

	/**
	 * @param enstDtLupd the enstDtLupd to set
	 */
	public void setEnstDtLupd(Date enstDtLupd) {
		this.enstDtLupd = enstDtLupd;
	}

	/**
	 * @return the enstUidLupd
	 */
	public String getEnstUidLupd() {
		return enstUidLupd;
	}

	/**
	 * @param enstUidLupd the enstUidLupd to set
	 */
	public void setEnstUidLupd(String enstUidLupd) {
		this.enstUidLupd = enstUidLupd;
	}
}
