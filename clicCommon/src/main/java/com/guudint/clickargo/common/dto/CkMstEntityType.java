package com.guudint.clickargo.common.dto;

import java.util.Date;

import com.guudint.clickargo.common.model.TCkMstEntityType;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkMstEntityType extends AbstractDTO<CkMstEntityType, TCkMstEntityType> {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = -2522081309951222818L;

	// Attributes
	/////////////
	private String entyId;
	private String entyName;
	private String entyDesc;
	private String entyDescOth;
	private Character entyStatus;
	private Date entyDtCreate;
	private String entyUidCreate;
	private Date entyDtLupd;
	private String entyUidLupd;

	// Constructors
	///////////////
	public CkMstEntityType() {
	}

	/**
	 * @param entity
	 */
	public CkMstEntityType(TCkMstEntityType entity) {
		super(entity);
	}

	/**
	 * @param entyId
	 * @param entyName
	 */
	public CkMstEntityType(String entyId, String entyName) {
		this.entyId = entyId;
		this.entyName = entyName;
	}

	/**
	 * @param entyId
	 * @param entyName
	 * @param entyDesc
	 * @param entyDescOth
	 * @param entyStatus
	 * @param entyDtCreate
	 * @param entyUidCreate
	 * @param entyDtLupd
	 * @param entyUidLupd
	 */
	public CkMstEntityType(String entyId, String entyName, String entyDesc, String entyDescOth, Character entyStatus,
			Date entyDtCreate, String entyUidCreate, Date entyDtLupd, String entyUidLupd) {
		this.entyId = entyId;
		this.entyName = entyName;
		this.entyDesc = entyDesc;
		this.entyDescOth = entyDescOth;
		this.entyStatus = entyStatus;
		this.entyDtCreate = entyDtCreate;
		this.entyUidCreate = entyUidCreate;
		this.entyDtLupd = entyDtLupd;
		this.entyUidLupd = entyUidLupd;
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
	public int compareTo(CkMstEntityType o) {
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
	 * @return the entyId
	 */
	public String getEntyId() {
		return entyId;
	}

	/**
	 * @param entyId the entyId to set
	 */
	public void setEntyId(String entyId) {
		this.entyId = entyId;
	}

	/**
	 * @return the entyName
	 */
	public String getEntyName() {
		return entyName;
	}

	/**
	 * @param entyName the entyName to set
	 */
	public void setEntyName(String entyName) {
		this.entyName = entyName;
	}

	/**
	 * @return the entyDesc
	 */
	public String getEntyDesc() {
		return entyDesc;
	}

	/**
	 * @param entyDesc the entyDesc to set
	 */
	public void setEntyDesc(String entyDesc) {
		this.entyDesc = entyDesc;
	}

	/**
	 * @return the entyDescOth
	 */
	public String getEntyDescOth() {
		return entyDescOth;
	}

	/**
	 * @param entyDescOth the entyDescOth to set
	 */
	public void setEntyDescOth(String entyDescOth) {
		this.entyDescOth = entyDescOth;
	}

	/**
	 * @return the entyStatus
	 */
	public Character getEntyStatus() {
		return entyStatus;
	}

	/**
	 * @param entyStatus the entyStatus to set
	 */
	public void setEntyStatus(Character entyStatus) {
		this.entyStatus = entyStatus;
	}

	/**
	 * @return the entyDtCreate
	 */
	public Date getEntyDtCreate() {
		return entyDtCreate;
	}

	/**
	 * @param entyDtCreate the entyDtCreate to set
	 */
	public void setEntyDtCreate(Date entyDtCreate) {
		this.entyDtCreate = entyDtCreate;
	}

	/**
	 * @return the entyUidCreate
	 */
	public String getEntyUidCreate() {
		return entyUidCreate;
	}

	/**
	 * @param entyUidCreate the entyUidCreate to set
	 */
	public void setEntyUidCreate(String entyUidCreate) {
		this.entyUidCreate = entyUidCreate;
	}

	/**
	 * @return the entyDtLupd
	 */
	public Date getEntyDtLupd() {
		return entyDtLupd;
	}

	/**
	 * @param entyDtLupd the entyDtLupd to set
	 */
	public void setEntyDtLupd(Date entyDtLupd) {
		this.entyDtLupd = entyDtLupd;
	}

	/**
	 * @return the entyUidLupd
	 */
	public String getEntyUidLupd() {
		return entyUidLupd;
	}

	/**
	 * @param entyUidLupd the entyUidLupd to set
	 */
	public void setEntyUidLupd(String entyUidLupd) {
		this.entyUidLupd = entyUidLupd;
	}
}
