package com.guudint.clickargo.clicservice.dto;

import java.util.Date;

import javax.validation.constraints.Size;

import com.guudint.clickargo.clicservice.model.TCkSvcSubAttach;
import com.guudint.clickargo.validator.ValidationGroup.CreateValid;
import com.guudint.clickargo.validator.ValidationGroup.SubmitValid;
import com.guudint.clickargo.validator.ValidationGroup.UpdateValid;
import com.vcc.camelone.common.dto.AbstractDTO;
import com.vcc.camelone.master.dto.MstAttType;

public class CkSvcSubAttach extends AbstractDTO<CkSvcSubAttach, TCkSvcSubAttach> {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = -1852540277883470233L;

	// Attributes
	//////////////
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 35, message = "{valid.clicservice.sub.attId.maxLength}")
	private String attId;
	private CkSvcSub TCkSvcSub;
	private MstAttType TMstAttType;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 35, message = "{valid.clicservice.sub.attName.maxLength}")
	private String attName;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 35, message = "{valid.clicservice.sub.attLoc.maxLength}")
	private String attLoc;
	private Character attStatus;
	private Date attDtCreate;
	private String attUidCreate;
	private Date attDtLupd;
	private String attUidLupd;

	// Constructors
	///////////////
	public CkSvcSubAttach() {
	}

	/**
	 * @param entity
	 */
	public CkSvcSubAttach(TCkSvcSubAttach entity) {
		super(entity);
	}
			
	/**
	 * @param attId
	 * @param TCkSvcSub
	 * @param TMstAttType
	 */
	public CkSvcSubAttach(String attId, CkSvcSub TCkSvcSub, MstAttType TMstAttType) {
		this.attId = attId;
		this.TCkSvcSub = TCkSvcSub;
		this.TMstAttType = TMstAttType;
	}

	/**
	 * @param attId
	 * @param TCkSvcSub
	 * @param TMstAttType
	 * @param attName
	 * @param attLoc
	 * @param attStatus
	 * @param attDtCreate
	 * @param attUidCreate
	 * @param attDtLupd
	 * @param attUidLupd
	 */
	public CkSvcSubAttach(String attId, CkSvcSub TCkSvcSub, MstAttType TMstAttType, String attName, String attLoc,
			Character attStatus, Date attDtCreate, String attUidCreate, Date attDtLupd, String attUidLupd) {
		this.attId = attId;
		this.TCkSvcSub = TCkSvcSub;
		this.TMstAttType = TMstAttType;
		this.attName = attName;
		this.attLoc = attLoc;
		this.attStatus = attStatus;
		this.attDtCreate = attDtCreate;
		this.attUidCreate = attUidCreate;
		this.attDtLupd = attDtLupd;
		this.attUidLupd = attUidLupd;
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
	public int compareTo(CkSvcSubAttach o) {
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
	 * @return the attId
	 */
	public String getAttId() {
		return attId;
	}

	/**
	 * @param attId the attId to set
	 */
	public void setAttId(String attId) {
		this.attId = attId;
	}

	/**
	 * @return the tCkSvcSub
	 */
	public CkSvcSub getTCkSvcSub() {
		return TCkSvcSub;
	}

	/**
	 * @param tCkSvcSub the tCkSvcSub to set
	 */
	public void setTCkSvcSub(CkSvcSub tCkSvcSub) {
		TCkSvcSub = tCkSvcSub;
	}

	/**
	 * @return the tMstAttType
	 */
	public MstAttType getTMstAttType() {
		return TMstAttType;
	}

	/**
	 * @param tMstAttType the tMstAttType to set
	 */
	public void setTMstAttType(MstAttType tMstAttType) {
		TMstAttType = tMstAttType;
	}

	/**
	 * @return the attName
	 */
	public String getAttName() {
		return attName;
	}

	/**
	 * @param attName the attName to set
	 */
	public void setAttName(String attName) {
		this.attName = attName;
	}

	/**
	 * @return the attLoc
	 */
	public String getAttLoc() {
		return attLoc;
	}

	/**
	 * @param attLoc the attLoc to set
	 */
	public void setAttLoc(String attLoc) {
		this.attLoc = attLoc;
	}

	/**
	 * @return the attStatus
	 */
	public Character getAttStatus() {
		return attStatus;
	}

	/**
	 * @param attStatus the attStatus to set
	 */
	public void setAttStatus(Character attStatus) {
		this.attStatus = attStatus;
	}

	/**
	 * @return the attDtCreate
	 */
	public Date getAttDtCreate() {
		return attDtCreate;
	}

	/**
	 * @param attDtCreate the attDtCreate to set
	 */
	public void setAttDtCreate(Date attDtCreate) {
		this.attDtCreate = attDtCreate;
	}

	/**
	 * @return the attUidCreate
	 */
	public String getAttUidCreate() {
		return attUidCreate;
	}

	/**
	 * @param attUidCreate the attUidCreate to set
	 */
	public void setAttUidCreate(String attUidCreate) {
		this.attUidCreate = attUidCreate;
	}

	/**
	 * @return the attDtLupd
	 */
	public Date getAttDtLupd() {
		return attDtLupd;
	}

	/**
	 * @param attDtLupd the attDtLupd to set
	 */
	public void setAttDtLupd(Date attDtLupd) {
		this.attDtLupd = attDtLupd;
	}

	/**
	 * @return the attUidLupd
	 */
	public String getAttUidLupd() {
		return attUidLupd;
	}

	/**
	 * @param attUidLupd the attUidLupd to set
	 */
	public void setAttUidLupd(String attUidLupd) {
		this.attUidLupd = attUidLupd;
	}
}
