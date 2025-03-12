package com.guudint.clickargo.master.dto;

import java.util.Date;

import javax.validation.constraints.Size;

import com.guudint.clickargo.master.model.TCkMstShipmentType;
import com.guudint.clickargo.validator.ValidationGroup.CreateValid;
import com.guudint.clickargo.validator.ValidationGroup.SubmitValid;
import com.guudint.clickargo.validator.ValidationGroup.UpdateValid;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkMstShipmentType extends AbstractDTO<CkMstShipmentType, TCkMstShipmentType> {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = -4539610576731011148L;

	// Attributes
	//////////////
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 35, message = "{valid.master.shtId.maxLength}")
	private String shtId;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 100, message = "{valid.master.shtName.maxLength}")
	private String shtName;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 255, message = "{valid.master.shtDesc.maxLength}")
	private String shtDesc;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 512, message = "{valid.master.shtDescOth.maxLength}")
	private String shtDescOth;
	private Character shtStatus;
	private Date shtDtCreate;
	private String shtUidCreate;
	private Date shtDtLupd;
	private String shtUidLupd;

	// Constructors
	///////////////
	public CkMstShipmentType() {
	}

	/**
	 * @param entity
	 */
	public CkMstShipmentType(TCkMstShipmentType entity) {
		super(entity);
	}

	/**
	 * @param shtId
	 * @param shtName
	 */
	public CkMstShipmentType(String shtId, String shtName) {
		this.shtId = shtId;
		this.shtName = shtName;
	}

	/**
	 * @param shtId
	 * @param shtName
	 * @param shtDesc
	 * @param shtDescOth
	 * @param shtStatus
	 * @param shtDtCreate
	 * @param shtUidCreate
	 * @param shtDtLupd
	 * @param shtUidLupd
	 */
	public CkMstShipmentType(String shtId, String shtName, String shtDesc, String shtDescOth, Character shtStatus,
			Date shtDtCreate, String shtUidCreate, Date shtDtLupd, String shtUidLupd) {
		this.shtId = shtId;
		this.shtName = shtName;
		this.shtDesc = shtDesc;
		this.shtDescOth = shtDescOth;
		this.shtStatus = shtStatus;
		this.shtDtCreate = shtDtCreate;
		this.shtUidCreate = shtUidCreate;
		this.shtDtLupd = shtDtLupd;
		this.shtUidLupd = shtUidLupd;
	}

	// Override Methods
	///////////////////
	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * 
	 */
	public int compareTo(CkMstShipmentType o) {
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
	 * @return the shtId
	 */
	public String getShtId() {
		return shtId;
	}

	/**
	 * @param shtId the shtId to set
	 */
	public void setShtId(String shtId) {
		this.shtId = shtId;
	}

	/**
	 * @return the shtName
	 */
	public String getShtName() {
		return shtName;
	}

	/**
	 * @param shtName the shtName to set
	 */
	public void setShtName(String shtName) {
		this.shtName = shtName;
	}

	/**
	 * @return the shtDesc
	 */
	public String getShtDesc() {
		return shtDesc;
	}

	/**
	 * @param shtDesc the shtDesc to set
	 */
	public void setShtDesc(String shtDesc) {
		this.shtDesc = shtDesc;
	}

	/**
	 * @return the shtDescOth
	 */
	public String getShtDescOth() {
		return shtDescOth;
	}

	/**
	 * @param shtDescOth the shtDescOth to set
	 */
	public void setShtDescOth(String shtDescOth) {
		this.shtDescOth = shtDescOth;
	}

	/**
	 * @return the shtStatus
	 */
	public Character getShtStatus() {
		return shtStatus;
	}

	/**
	 * @param shtStatus the shtStatus to set
	 */
	public void setShtStatus(Character shtStatus) {
		this.shtStatus = shtStatus;
	}

	/**
	 * @return the shtDtCreate
	 */
	public Date getShtDtCreate() {
		return shtDtCreate;
	}

	/**
	 * @param shtDtCreate the shtDtCreate to set
	 */
	public void setShtDtCreate(Date shtDtCreate) {
		this.shtDtCreate = shtDtCreate;
	}

	/**
	 * @return the shtUidCreate
	 */
	public String getShtUidCreate() {
		return shtUidCreate;
	}

	/**
	 * @param shtUidCreate the shtUidCreate to set
	 */
	public void setShtUidCreate(String shtUidCreate) {
		this.shtUidCreate = shtUidCreate;
	}

	/**
	 * @return the shtDtLupd
	 */
	public Date getShtDtLupd() {
		return shtDtLupd;
	}

	/**
	 * @param shtDtLupd the shtDtLupd to set
	 */
	public void setShtDtLupd(Date shtDtLupd) {
		this.shtDtLupd = shtDtLupd;
	}

	/**
	 * @return the shtUidLupd
	 */
	public String getShtUidLupd() {
		return shtUidLupd;
	}

	/**
	 * @param shtUidLupd the shtUidLupd to set
	 */
	public void setShtUidLupd(String shtUidLupd) {
		this.shtUidLupd = shtUidLupd;
	}
}
