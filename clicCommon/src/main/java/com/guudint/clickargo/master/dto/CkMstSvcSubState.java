package com.guudint.clickargo.master.dto;

import java.util.Date;

import javax.validation.constraints.Size;

import com.guudint.clickargo.master.model.TCkMstSvcSubState;
import com.guudint.clickargo.validator.ValidationGroup.CreateValid;
import com.guudint.clickargo.validator.ValidationGroup.SubmitValid;
import com.guudint.clickargo.validator.ValidationGroup.UpdateValid;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkMstSvcSubState extends AbstractDTO<CkMstSvcSubState, TCkMstSvcSubState> {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = 5849220816753319974L;

	// Attributes
	/////////////
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 35, message = "{valid.master.ssstId.maxLength}")
	private String ssstId;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 100, message = "{valid.master.ssstName.maxLength}")
	private String ssstName;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 255, message = "{valid.master.ssstDesc.maxLength}")
	private String ssstDesc;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 512, message = "{valid.master.ssstDescOth.maxLength}")
	private String ssstDescOth;
	private Character ssstStatus;
	private Date ssstDtCreate;
	private String ssstUidCreate;
	private Date ssstDtLupd;
	private String ssstUidLupd;

	// Constructors
	///////////////
	public CkMstSvcSubState() {
	}

	/**
	 * @param entity
	 */
	public CkMstSvcSubState(TCkMstSvcSubState entity) {
		super(entity);
	}

	/**
	 * @param ssstId
	 * @param ssstName
	 */
	public CkMstSvcSubState(String ssstId, String ssstName) {
		this.ssstId = ssstId;
		this.ssstName = ssstName;
	}

	/**
	 * @param ssstId
	 * @param ssstName
	 * @param ssstDesc
	 * @param ssstDescOth
	 * @param ssstStatus
	 * @param ssstDtCreate
	 * @param ssstUidCreate
	 * @param ssstDtLupd
	 * @param ssstUidLupd
	 */
	public CkMstSvcSubState(String ssstId, String ssstName, String ssstDesc, String ssstDescOth, Character ssstStatus,
			Date ssstDtCreate, String ssstUidCreate, Date ssstDtLupd, String ssstUidLupd) {
		this.ssstId = ssstId;
		this.ssstName = ssstName;
		this.ssstDesc = ssstDesc;
		this.ssstDescOth = ssstDescOth;
		this.ssstStatus = ssstStatus;
		this.ssstDtCreate = ssstDtCreate;
		this.ssstUidCreate = ssstUidCreate;
		this.ssstDtLupd = ssstDtLupd;
		this.ssstUidLupd = ssstUidLupd;
	}

	// Override Methods
	///////////////////
	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * 
	 */
	public int compareTo(CkMstSvcSubState o) {
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

	/**
	 * @return the ssstId
	 */
	public String getSsstId() {
		return ssstId;
	}

	/**
	 * @param ssstId the ssstId to set
	 */
	public void setSsstId(String ssstId) {
		this.ssstId = ssstId;
	}

	/**
	 * @return the ssstName
	 */
	public String getSsstName() {
		return ssstName;
	}

	/**
	 * @param ssstName the ssstName to set
	 */
	public void setSsstName(String ssstName) {
		this.ssstName = ssstName;
	}

	/**
	 * @return the ssstDesc
	 */
	public String getSsstDesc() {
		return ssstDesc;
	}

	/**
	 * @param ssstDesc the ssstDesc to set
	 */
	public void setSsstDesc(String ssstDesc) {
		this.ssstDesc = ssstDesc;
	}

	/**
	 * @return the ssstDescOth
	 */
	public String getSsstDescOth() {
		return ssstDescOth;
	}

	/**
	 * @param ssstDescOth the ssstDescOth to set
	 */
	public void setSsstDescOth(String ssstDescOth) {
		this.ssstDescOth = ssstDescOth;
	}

	/**
	 * @return the ssstStatus
	 */
	public Character getSsstStatus() {
		return ssstStatus;
	}

	/**
	 * @param ssstStatus the ssstStatus to set
	 */
	public void setSsstStatus(Character ssstStatus) {
		this.ssstStatus = ssstStatus;
	}

	/**
	 * @return the ssstDtCreate
	 */
	public Date getSsstDtCreate() {
		return ssstDtCreate;
	}

	/**
	 * @param ssstDtCreate the ssstDtCreate to set
	 */
	public void setSsstDtCreate(Date ssstDtCreate) {
		this.ssstDtCreate = ssstDtCreate;
	}

	/**
	 * @return the ssstUidCreate
	 */
	public String getSsstUidCreate() {
		return ssstUidCreate;
	}

	/**
	 * @param ssstUidCreate the ssstUidCreate to set
	 */
	public void setSsstUidCreate(String ssstUidCreate) {
		this.ssstUidCreate = ssstUidCreate;
	}

	/**
	 * @return the ssstDtLupd
	 */
	public Date getSsstDtLupd() {
		return ssstDtLupd;
	}

	/**
	 * @param ssstDtLupd the ssstDtLupd to set
	 */
	public void setSsstDtLupd(Date ssstDtLupd) {
		this.ssstDtLupd = ssstDtLupd;
	}

	/**
	 * @return the ssstUidLupd
	 */
	public String getSsstUidLupd() {
		return ssstUidLupd;
	}

	/**
	 * @param ssstUidLupd the ssstUidLupd to set
	 */
	public void setSsstUidLupd(String ssstUidLupd) {
		this.ssstUidLupd = ssstUidLupd;
	}
}
