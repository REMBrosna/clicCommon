package com.guudint.clickargo.master.dto;

import java.util.Date;

import com.guudint.clickargo.master.model.TCkMstDoPartyType;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkMstDoPartyType extends AbstractDTO<CkMstDoPartyType, TCkMstDoPartyType> {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = -113774920317902403L;

	// Attributes
	/////////////
	private String doptId;
	private String doptName;
	private String doptDesc;
	private String doptDescOth;
	private Character doptStatus;
	private Date doptDtCreate;
	private String doptUidCreate;
	private Date doptDtLupd;
	private String doptUidLupd;

	// Constructors
	///////////////
	public CkMstDoPartyType() {
	}

	/**
	 * @param entity
	 */
	public CkMstDoPartyType(TCkMstDoPartyType entity) {
		super(entity);
	}

	/**
	 * @param doptId
	 * @param doptName
	 */
	public CkMstDoPartyType(String doptId, String doptName) {
		this.doptId = doptId;
		this.doptName = doptName;
	}

	/**
	 * @param doptId
	 * @param doptName
	 * @param doptDesc
	 * @param doptDescOth
	 * @param doptStatus
	 * @param doptDtCreate
	 * @param doptUidCreate
	 * @param doptDtLupd
	 * @param doptUidLupd
	 */
	public CkMstDoPartyType(String doptId, String doptName, String doptDesc, String doptDescOth, Character doptStatus,
			Date doptDtCreate, String doptUidCreate, Date doptDtLupd, String doptUidLupd) {
		this.doptId = doptId;
		this.doptName = doptName;
		this.doptDesc = doptDesc;
		this.doptDescOth = doptDescOth;
		this.doptStatus = doptStatus;
		this.doptDtCreate = doptDtCreate;
		this.doptUidCreate = doptUidCreate;
		this.doptDtLupd = doptDtLupd;
		this.doptUidLupd = doptUidLupd;
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
	public int compareTo(CkMstDoPartyType o) {
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
	 * @return the doptId
	 */
	public String getDoptId() {
		return doptId;
	}

	/**
	 * @param doptId the doptId to set
	 */
	public void setDoptId(String doptId) {
		this.doptId = doptId;
	}

	/**
	 * @return the doptName
	 */
	public String getDoptName() {
		return doptName;
	}

	/**
	 * @param doptName the doptName to set
	 */
	public void setDoptName(String doptName) {
		this.doptName = doptName;
	}

	/**
	 * @return the doptDesc
	 */
	public String getDoptDesc() {
		return doptDesc;
	}

	/**
	 * @param doptDesc the doptDesc to set
	 */
	public void setDoptDesc(String doptDesc) {
		this.doptDesc = doptDesc;
	}

	/**
	 * @return the doptDescOth
	 */
	public String getDoptDescOth() {
		return doptDescOth;
	}

	/**
	 * @param doptDescOth the doptDescOth to set
	 */
	public void setDoptDescOth(String doptDescOth) {
		this.doptDescOth = doptDescOth;
	}

	/**
	 * @return the doptStatus
	 */
	public Character getDoptStatus() {
		return doptStatus;
	}

	/**
	 * @param doptStatus the doptStatus to set
	 */
	public void setDoptStatus(Character doptStatus) {
		this.doptStatus = doptStatus;
	}

	/**
	 * @return the doptDtCreate
	 */
	public Date getDoptDtCreate() {
		return doptDtCreate;
	}

	/**
	 * @param doptDtCreate the doptDtCreate to set
	 */
	public void setDoptDtCreate(Date doptDtCreate) {
		this.doptDtCreate = doptDtCreate;
	}

	/**
	 * @return the doptUidCreate
	 */
	public String getDoptUidCreate() {
		return doptUidCreate;
	}

	/**
	 * @param doptUidCreate the doptUidCreate to set
	 */
	public void setDoptUidCreate(String doptUidCreate) {
		this.doptUidCreate = doptUidCreate;
	}

	/**
	 * @return the doptDtLupd
	 */
	public Date getDoptDtLupd() {
		return doptDtLupd;
	}

	/**
	 * @param doptDtLupd the doptDtLupd to set
	 */
	public void setDoptDtLupd(Date doptDtLupd) {
		this.doptDtLupd = doptDtLupd;
	}

	/**
	 * @return the doptUidLupd
	 */
	public String getDoptUidLupd() {
		return doptUidLupd;
	}

	/**
	 * @param doptUidLupd the doptUidLupd to set
	 */
	public void setDoptUidLupd(String doptUidLupd) {
		this.doptUidLupd = doptUidLupd;
	}
}
