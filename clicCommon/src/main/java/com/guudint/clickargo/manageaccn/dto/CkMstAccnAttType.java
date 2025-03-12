package com.guudint.clickargo.manageaccn.dto;

import java.util.Date;

import com.guudint.clickargo.common.dto.CkMstWorkflowType;
import com.guudint.clickargo.manageaccn.model.TCkMstAccnAttType;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkMstAccnAttType extends AbstractDTO<CkMstAccnAttType, TCkMstAccnAttType> {

	private static final long serialVersionUID = 2320368547015081775L;
	private CkMstAccnAttTypeId id;
	private CkMstWorkflowType TCkMstWorkflowType;
	private String atName;
	private String atDesc;
	private String atDescOth;
	private Character atMandatory;
	private Character atStatus;
	private Date atDtCreate;
	private String atUidCreate;
	private Date atDtLupd;
	private String atUidLupd;

	public CkMstAccnAttType() {
	}
	
	public CkMstAccnAttType(TCkMstAccnAttType entity) {
		super(entity);
	}

	public CkMstAccnAttType(CkMstAccnAttTypeId id, CkMstWorkflowType TCkMstWorkflowType) {
		this.id = id;
		this.TCkMstWorkflowType = TCkMstWorkflowType;
	}

	public CkMstAccnAttType(CkMstAccnAttTypeId id, CkMstWorkflowType TCkMstWorkflowType, String atName, String atDesc,
			String atDescOth, Character atMandatory, Character atStatus, Date atDtCreate, String atUidCreate,
			Date atDtLupd, String atUidLupd) {
		this.id = id;
		this.TCkMstWorkflowType = TCkMstWorkflowType;
		this.atName = atName;
		this.atDesc = atDesc;
		this.atDescOth = atDescOth;
		this.atMandatory = atMandatory;
		this.atStatus = atStatus;
		this.atDtCreate = atDtCreate;
		this.atUidCreate = atUidCreate;
		this.atDtLupd = atDtLupd;
		this.atUidLupd = atUidLupd;
	}

	/**
	 * @return the id
	 */
	public CkMstAccnAttTypeId getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(CkMstAccnAttTypeId id) {
		this.id = id;
	}

	/**
	 * @return the tCkMstWorkflowType
	 */
	public CkMstWorkflowType getTCkMstWorkflowType() {
		return TCkMstWorkflowType;
	}

	/**
	 * @param tCkMstWorkflowType the tCkMstWorkflowType to set
	 */
	public void setTCkMstWorkflowType(CkMstWorkflowType tCkMstWorkflowType) {
		TCkMstWorkflowType = tCkMstWorkflowType;
	}

	/**
	 * @return the atName
	 */
	public String getAtName() {
		return atName;
	}

	/**
	 * @param atName the atName to set
	 */
	public void setAtName(String atName) {
		this.atName = atName;
	}

	/**
	 * @return the atDesc
	 */
	public String getAtDesc() {
		return atDesc;
	}

	/**
	 * @param atDesc the atDesc to set
	 */
	public void setAtDesc(String atDesc) {
		this.atDesc = atDesc;
	}

	/**
	 * @return the atDescOth
	 */
	public String getAtDescOth() {
		return atDescOth;
	}

	/**
	 * @param atDescOth the atDescOth to set
	 */
	public void setAtDescOth(String atDescOth) {
		this.atDescOth = atDescOth;
	}

	/**
	 * @return the atMandatory
	 */
	public Character getAtMandatory() {
		return atMandatory;
	}

	/**
	 * @param atMandatory the atMandatory to set
	 */
	public void setAtMandatory(Character atMandatory) {
		this.atMandatory = atMandatory;
	}

	/**
	 * @return the atStatus
	 */
	public Character getAtStatus() {
		return atStatus;
	}

	/**
	 * @param atStatus the atStatus to set
	 */
	public void setAtStatus(Character atStatus) {
		this.atStatus = atStatus;
	}

	/**
	 * @return the atDtCreate
	 */
	public Date getAtDtCreate() {
		return atDtCreate;
	}

	/**
	 * @param atDtCreate the atDtCreate to set
	 */
	public void setAtDtCreate(Date atDtCreate) {
		this.atDtCreate = atDtCreate;
	}

	/**
	 * @return the atUidCreate
	 */
	public String getAtUidCreate() {
		return atUidCreate;
	}

	/**
	 * @param atUidCreate the atUidCreate to set
	 */
	public void setAtUidCreate(String atUidCreate) {
		this.atUidCreate = atUidCreate;
	}

	/**
	 * @return the atDtLupd
	 */
	public Date getAtDtLupd() {
		return atDtLupd;
	}

	/**
	 * @param atDtLupd the atDtLupd to set
	 */
	public void setAtDtLupd(Date atDtLupd) {
		this.atDtLupd = atDtLupd;
	}

	/**
	 * @return the atUidLupd
	 */
	public String getAtUidLupd() {
		return atUidLupd;
	}

	/**
	 * @param atUidLupd the atUidLupd to set
	 */
	public void setAtUidLupd(String atUidLupd) {
		this.atUidLupd = atUidLupd;
	}

	@Override
	public int compareTo(CkMstAccnAttType o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

}
