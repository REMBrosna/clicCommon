package com.guudint.clickargo.common.dto;

import java.util.Date;

import com.guudint.clickargo.common.model.TCkWorkflowRemark;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkWorkflowRemark extends AbstractDTO<CkWorkflowRemark, TCkWorkflowRemark> {

	private static final long serialVersionUID = 959544061285262569L;
	private String arId;
	private CkMstRemarkType TCkMstRemarkType;
	private CkMstWorkflowType TCkMstWorkflowType;
	private CoreAccn TCoreAccn;
	private String arRemark;
	private Character atStatus;
	private Date atDtCreate;
	private String atUidCreate;
	private Date atDtLupd;
	private String atUidLupd;

	public CkWorkflowRemark() {
	}
	
	/**
	 * @param entity
	 */
	public CkWorkflowRemark(TCkWorkflowRemark entity) {
		super(entity);
	}

	public CkWorkflowRemark(String arId, CkMstRemarkType TCkMstRemarkType, CkMstWorkflowType TCkMstWorkflowType,
			CoreAccn TCoreAccn, String arRemark) {
		this.arId = arId;
		this.TCkMstRemarkType = TCkMstRemarkType;
		this.TCkMstWorkflowType = TCkMstWorkflowType;
		this.TCoreAccn = TCoreAccn;
		this.arRemark = arRemark;
	}

	public CkWorkflowRemark(String arId, CkMstRemarkType TCkMstRemarkType, CkMstWorkflowType TCkMstWorkflowType,
			CoreAccn TCoreAccn, String arRemark, Character atStatus, Date atDtCreate, String atUidCreate,
			Date atDtLupd, String atUidLupd) {
		this.arId = arId;
		this.TCkMstRemarkType = TCkMstRemarkType;
		this.TCkMstWorkflowType = TCkMstWorkflowType;
		this.TCoreAccn = TCoreAccn;
		this.arRemark = arRemark;
		this.atStatus = atStatus;
		this.atDtCreate = atDtCreate;
		this.atUidCreate = atUidCreate;
		this.atDtLupd = atDtLupd;
		this.atUidLupd = atUidLupd;
	}

	/**
	 * @return the arId
	 */
	public String getArId() {
		return arId;
	}

	/**
	 * @param arId the arId to set
	 */
	public void setArId(String arId) {
		this.arId = arId;
	}

	/**
	 * @return the tCkMstRemarkType
	 */
	public CkMstRemarkType getTCkMstRemarkType() {
		return TCkMstRemarkType;
	}

	/**
	 * @param tCkMstRemarkType the tCkMstRemarkType to set
	 */
	public void setTCkMstRemarkType(CkMstRemarkType tCkMstRemarkType) {
		TCkMstRemarkType = tCkMstRemarkType;
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
	 * @return the tCoreAccn
	 */
	public CoreAccn getTCoreAccn() {
		return TCoreAccn;
	}

	/**
	 * @param tCoreAccn the tCoreAccn to set
	 */
	public void setTCoreAccn(CoreAccn tCoreAccn) {
		TCoreAccn = tCoreAccn;
	}

	/**
	 * @return the arRemark
	 */
	public String getArRemark() {
		return arRemark;
	}

	/**
	 * @param arRemark the arRemark to set
	 */
	public void setArRemark(String arRemark) {
		this.arRemark = arRemark;
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
	public int compareTo(CkWorkflowRemark o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

}
