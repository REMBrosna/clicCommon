package com.guudint.clickargo.clicservice.dto;
// Generated 31 Oct, 2022 11:55:25 AM by Hibernate Tools 5.2.1.Final

import java.util.Date;

import javax.validation.constraints.Size;

import com.guudint.clickargo.clicservice.model.TCkSvcAuthAttach;
import com.guudint.clickargo.master.dto.CkMstAuthAccnType;
import com.guudint.clickargo.validator.ValidationGroup.CreateValid;
import com.guudint.clickargo.validator.ValidationGroup.SubmitValid;
import com.guudint.clickargo.validator.ValidationGroup.UpdateValid;
import com.vcc.camelone.common.dto.AbstractDTO;
import com.vcc.camelone.master.dto.MstAttType;

public class CkSvcAuthAttach extends AbstractDTO<CkSvcAuthAttach, TCkSvcAuthAttach> {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = -6873023874364422123L;
	
	// Attributes
	/////////////
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 35, message = "{valid.clicservice.attId.maxLength}")
	private String attId;
	private CkMstAuthAccnType TCkMstAuthAccnType;
	private CkSvcAuth TCkSvcAuth;
	private MstAttType TMstAttType;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 256, message = "{valid.clicservice.attName.maxLength}")
	private String attName;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 1024, message = "{valid.clicservice.attLoc.maxLength}")
	private String attLoc;
	private Character attStatus;
	private Date attDtCreate;
	private String attUidCreate;
	private Date attDtLupd;
	private String attUidLupd;

	// Constructor
	//////////////
	public CkSvcAuthAttach() {
	}

	/**
	 * @param entity
	 */
	public CkSvcAuthAttach(TCkSvcAuthAttach entity) {
		super(entity);
	}
	
	
	/**
	 * @param attId
	 * @param TCkMstAuthAccnType
	 * @param TCkSvcAuth
	 * @param TMstAttType
	 */
	public CkSvcAuthAttach(String attId, CkMstAuthAccnType TCkMstAuthAccnType, CkSvcAuth TCkSvcAuth,
			MstAttType TMstAttType) {
		this.attId = attId;
		this.TCkMstAuthAccnType = TCkMstAuthAccnType;
		this.TCkSvcAuth = TCkSvcAuth;
		this.TMstAttType = TMstAttType;
	}

	/**
	 * @param attId
	 * @param TCkMstAuthAccnType
	 * @param TCkSvcAuth
	 * @param TMstAttType
	 * @param attName
	 * @param attLoc
	 * @param attStatus
	 * @param attDtCreate
	 * @param attUidCreate
	 * @param attDtLupd
	 * @param attUidLupd
	 */
	public CkSvcAuthAttach(String attId, CkMstAuthAccnType TCkMstAuthAccnType, CkSvcAuth TCkSvcAuth,
			MstAttType TMstAttType, String attName, String attLoc, Character attStatus, Date attDtCreate,
			String attUidCreate, Date attDtLupd, String attUidLupd) {
		this.attId = attId;
		this.TCkMstAuthAccnType = TCkMstAuthAccnType;
		this.TCkSvcAuth = TCkSvcAuth;
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
	public int compareTo(CkSvcAuthAttach o) {
		// TODO Auto-generated method stub
		return 0;
	}

	// Properties
	/////////////
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
	 * @return the tCkMstAuthAccnType
	 */
	public CkMstAuthAccnType getTCkMstAuthAccnType() {
		return TCkMstAuthAccnType;
	}

	/**
	 * @param tCkMstAuthAccnType the tCkMstAuthAccnType to set
	 */
	public void setTCkMstAuthAccnType(CkMstAuthAccnType tCkMstAuthAccnType) {
		TCkMstAuthAccnType = tCkMstAuthAccnType;
	}

	/**
	 * @return the tCkSvcAuth
	 */
	public CkSvcAuth getTCkSvcAuth() {
		return TCkSvcAuth;
	}

	/**
	 * @param tCkSvcAuth the tCkSvcAuth to set
	 */
	public void setTCkSvcAuth(CkSvcAuth tCkSvcAuth) {
		TCkSvcAuth = tCkSvcAuth;
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
