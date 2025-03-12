package com.guudint.clickargo.master.dto;

import java.util.Date;

import javax.validation.constraints.Size;

import com.guudint.clickargo.master.model.TCkMstServiceType;
import com.guudint.clickargo.validator.ValidationGroup.CreateValid;
import com.guudint.clickargo.validator.ValidationGroup.SubmitValid;
import com.guudint.clickargo.validator.ValidationGroup.UpdateValid;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkMstServiceType extends AbstractDTO<CkMstServiceType, TCkMstServiceType> {

	/**
	 * Static Attributes
	 */
	private static final long serialVersionUID = 7547901505198205129L;

	// Attributes
	/////////////
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 35, message = "{valid.master.svctId.maxLength}")
	private String svctId;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 100, message = "{valid.master.svctName.maxLength}")
	private String svctName;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 255, message = "{valid.master.svctDesc.maxLength}")
	private String svctDesc;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 512, message = "{valid.master.svctDescOth.maxLength}")
	private String svctDescOth;
	private Character svctStatus;
	private Date svctDtCreate;
	private String svctUidCreate;
	private Date svctDtLupd;
	private String svctUidLupd;
	private Boolean isSubscribed;

	// Constructors
	///////////////
	public CkMstServiceType() {
	}

	/**
	 * @param entity
	 */
	public CkMstServiceType(TCkMstServiceType entity) {
		super(entity);
	}

	/**
	 * @param svctId
	 * @param svctName
	 */
	public CkMstServiceType(String svctId) {
		this.svctId = svctId;
	}

	/**
	 * @param svctId
	 * @param svctName
	 */
	public CkMstServiceType(String svctId, String svctName) {
		this.svctId = svctId;
		this.svctName = svctName;
	}

	/**
	 * @param svctId
	 * @param svctName
	 * @param svctDesc
	 * @param svctDescOth
	 * @param svctStatus
	 * @param svctDtCreate
	 * @param svctUidCreate
	 * @param svctDtLupd
	 * @param svctUidLupd
	 */
	public CkMstServiceType(String svctId, String svctName, String svctDesc, String svctDescOth, Character svctStatus,
			Date svctDtCreate, String svctUidCreate, Date svctDtLupd, String svctUidLupd) {
		this.svctId = svctId;
		this.svctName = svctName;
		this.svctDesc = svctDesc;
		this.svctDescOth = svctDescOth;
		this.svctStatus = svctStatus;
		this.svctDtCreate = svctDtCreate;
		this.svctUidCreate = svctUidCreate;
		this.svctDtLupd = svctDtLupd;
		this.svctUidLupd = svctUidLupd;
	}

	// Override Methods
	///////////////////
	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * 
	 */
	public int compareTo(CkMstServiceType o) {
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
	 * @return the svctId
	 */
	public String getSvctId() {
		return svctId;
	}

	/**
	 * @param svctId the svctId to set
	 */
	public void setSvctId(String svctId) {
		this.svctId = svctId;
	}

	/**
	 * @return the svctName
	 */
	public String getSvctName() {
		return svctName;
	}

	/**
	 * @param svctName the svctName to set
	 */
	public void setSvctName(String svctName) {
		this.svctName = svctName;
	}

	/**
	 * @return the svctDesc
	 */
	public String getSvctDesc() {
		return svctDesc;
	}

	/**
	 * @param svctDesc the svctDesc to set
	 */
	public void setSvctDesc(String svctDesc) {
		this.svctDesc = svctDesc;
	}

	/**
	 * @return the svctDescOth
	 */
	public String getSvctDescOth() {
		return svctDescOth;
	}

	/**
	 * @param svctDescOth the svctDescOth to set
	 */
	public void setSvctDescOth(String svctDescOth) {
		this.svctDescOth = svctDescOth;
	}

	/**
	 * @return the svctStatus
	 */
	public Character getSvctStatus() {
		return svctStatus;
	}

	/**
	 * @param svctStatus the svctStatus to set
	 */
	public void setSvctStatus(Character svctStatus) {
		this.svctStatus = svctStatus;
	}

	/**
	 * @return the svctDtCreate
	 */
	public Date getSvctDtCreate() {
		return svctDtCreate;
	}

	/**
	 * @param svctDtCreate the svctDtCreate to set
	 */
	public void setSvctDtCreate(Date svctDtCreate) {
		this.svctDtCreate = svctDtCreate;
	}

	/**
	 * @return the svctUidCreate
	 */
	public String getSvctUidCreate() {
		return svctUidCreate;
	}

	/**
	 * @param svctUidCreate the svctUidCreate to set
	 */
	public void setSvctUidCreate(String svctUidCreate) {
		this.svctUidCreate = svctUidCreate;
	}

	/**
	 * @return the svctDtLupd
	 */
	public Date getSvctDtLupd() {
		return svctDtLupd;
	}

	/**
	 * @param svctDtLupd the svctDtLupd to set
	 */
	public void setSvctDtLupd(Date svctDtLupd) {
		this.svctDtLupd = svctDtLupd;
	}

	/**
	 * @return the svctUidLupd
	 */
	public String getSvctUidLupd() {
		return svctUidLupd;
	}

	/**
	 * @param svctUidLupd the svctUidLupd to set
	 */
	public void setSvctUidLupd(String svctUidLupd) {
		this.svctUidLupd = svctUidLupd;
	}

	/**
	 * @return the isSubscribed
	 */
	public Boolean getIsSubscribed() {
		return isSubscribed;
	}

	/**
	 * @param isSubscribed the isSubscribed to set
	 */
	public void setIsSubscribed(Boolean isSubscribed) {
		this.isSubscribed = isSubscribed;
	}

}
