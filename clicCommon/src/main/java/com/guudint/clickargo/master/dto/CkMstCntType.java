package com.guudint.clickargo.master.dto;

import java.util.Date;

import javax.validation.constraints.Size;

import com.guudint.clickargo.master.model.TCkMstCntType;
import com.guudint.clickargo.validator.ValidationGroup.CreateValid;
import com.guudint.clickargo.validator.ValidationGroup.SubmitValid;
import com.guudint.clickargo.validator.ValidationGroup.UpdateValid;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkMstCntType extends AbstractDTO<CkMstCntType, TCkMstCntType> {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = -7794216146006809158L;

	// Attributes
	/////////////
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 35, message = "{valid.master.cnttId.maxLength}")
	private String cnttId;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 100, message = "{valid.master.cnttName.maxLength}")
	private String cnttName;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 255, message = "{valid.master.cnttDesc.maxLength}")
	private String cnttDesc;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 512, message = "{valid.master.cnttDescOth.maxLength}")
	private String cnttDescOth;
	private Character cnttStatus;
	private Date cnttDtCreate;
	private String cnttUidCreate;
	private Date cnttDtLupd;
	private String cnttUidLupd;

	// Constructors
	///////////////
	public CkMstCntType() {
	}

	/**
	 * @param entity
	 */
	public CkMstCntType(TCkMstCntType entity) {
		super(entity);
	}
	
	/**
	 * @param cnttId
	 * @param cnttName
	 */
	public CkMstCntType(String cnttId, String cnttName) {
		this.cnttId = cnttId;
		this.cnttName = cnttName;
	}

	/**
	 * @param cnttId
	 * @param cnttName
	 * @param cnttDesc
	 * @param cnttDescOth
	 * @param cnttStatus
	 * @param cnttDtCreate
	 * @param cnttUidCreate
	 * @param cnttDtLupd
	 * @param cnttUidLupd
	 */
	public CkMstCntType(String cnttId, String cnttName, String cnttDesc, String cnttDescOth, Character cnttStatus,
			Date cnttDtCreate, String cnttUidCreate, Date cnttDtLupd, String cnttUidLupd) {
		this.cnttId = cnttId;
		this.cnttName = cnttName;
		this.cnttDesc = cnttDesc;
		this.cnttDescOth = cnttDescOth;
		this.cnttStatus = cnttStatus;
		this.cnttDtCreate = cnttDtCreate;
		this.cnttUidCreate = cnttUidCreate;
		this.cnttDtLupd = cnttDtLupd;
		this.cnttUidLupd = cnttUidLupd;
	}

	// Override Methods
	///////////////////
	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * 
	 */
	public int compareTo(CkMstCntType o) {
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
	 * @return the cnttId
	 */
	public String getCnttId() {
		return cnttId;
	}

	/**
	 * @param cnttId the cnttId to set
	 */
	public void setCnttId(String cnttId) {
		this.cnttId = cnttId;
	}

	/**
	 * @return the cnttName
	 */
	public String getCnttName() {
		return cnttName;
	}

	/**
	 * @param cnttName the cnttName to set
	 */
	public void setCnttName(String cnttName) {
		this.cnttName = cnttName;
	}

	/**
	 * @return the cnttDesc
	 */
	public String getCnttDesc() {
		return cnttDesc;
	}

	/**
	 * @param cnttDesc the cnttDesc to set
	 */
	public void setCnttDesc(String cnttDesc) {
		this.cnttDesc = cnttDesc;
	}

	/**
	 * @return the cnttDescOth
	 */
	public String getCnttDescOth() {
		return cnttDescOth;
	}

	/**
	 * @param cnttDescOth the cnttDescOth to set
	 */
	public void setCnttDescOth(String cnttDescOth) {
		this.cnttDescOth = cnttDescOth;
	}

	/**
	 * @return the cnttStatus
	 */
	public Character getCnttStatus() {
		return cnttStatus;
	}

	/**
	 * @param cnttStatus the cnttStatus to set
	 */
	public void setCnttStatus(Character cnttStatus) {
		this.cnttStatus = cnttStatus;
	}

	/**
	 * @return the cnttDtCreate
	 */
	public Date getCnttDtCreate() {
		return cnttDtCreate;
	}

	/**
	 * @param cnttDtCreate the cnttDtCreate to set
	 */
	public void setCnttDtCreate(Date cnttDtCreate) {
		this.cnttDtCreate = cnttDtCreate;
	}

	/**
	 * @return the cnttUidCreate
	 */
	public String getCnttUidCreate() {
		return cnttUidCreate;
	}

	/**
	 * @param cnttUidCreate the cnttUidCreate to set
	 */
	public void setCnttUidCreate(String cnttUidCreate) {
		this.cnttUidCreate = cnttUidCreate;
	}

	/**
	 * @return the cnttDtLupd
	 */
	public Date getCnttDtLupd() {
		return cnttDtLupd;
	}

	/**
	 * @param cnttDtLupd the cnttDtLupd to set
	 */
	public void setCnttDtLupd(Date cnttDtLupd) {
		this.cnttDtLupd = cnttDtLupd;
	}

	/**
	 * @return the cnttUidLupd
	 */
	public String getCnttUidLupd() {
		return cnttUidLupd;
	}

	/**
	 * @param cnttUidLupd the cnttUidLupd to set
	 */
	public void setCnttUidLupd(String cnttUidLupd) {
		this.cnttUidLupd = cnttUidLupd;
	}
}
