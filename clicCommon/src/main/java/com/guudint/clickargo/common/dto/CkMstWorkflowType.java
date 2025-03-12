package com.guudint.clickargo.common.dto;

import java.util.Date;

import com.guudint.clickargo.common.model.TCkMstWorkflowType;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkMstWorkflowType extends AbstractDTO<CkMstWorkflowType, TCkMstWorkflowType> {

	private static final long serialVersionUID = -6554385349666317412L;
	private String wktId;
	private String wktName;
	private String wktDesc;
	private String wktDescOth;
	private Character wktStatus;
	private Date wktDtCreate;
	private String wktUidCreate;
	private Date wktDtLupd;
	private String wktUidLupd;

	public CkMstWorkflowType() {
	}

	public CkMstWorkflowType(TCkMstWorkflowType entity) {
		super(entity);
	}

	public CkMstWorkflowType(String wktId) {
		this.wktId = wktId;
	}

	public CkMstWorkflowType(String wktId, String wktName, String wktDesc, String wktDescOth, Character wktStatus,
			Date wktDtCreate, String wktUidCreate, Date wktDtLupd, String wktUidLupd) {
		this.wktId = wktId;
		this.wktName = wktName;
		this.wktDesc = wktDesc;
		this.wktDescOth = wktDescOth;
		this.wktStatus = wktStatus;
		this.wktDtCreate = wktDtCreate;
		this.wktUidCreate = wktUidCreate;
		this.wktDtLupd = wktDtLupd;
		this.wktUidLupd = wktUidLupd;
	}

	/**
	 * @return the wktId
	 */
	public String getWktId() {
		return wktId;
	}

	/**
	 * @param wktId the wktId to set
	 */
	public void setWktId(String wktId) {
		this.wktId = wktId;
	}

	/**
	 * @return the wktName
	 */
	public String getWktName() {
		return wktName;
	}

	/**
	 * @param wktName the wktName to set
	 */
	public void setWktName(String wktName) {
		this.wktName = wktName;
	}

	/**
	 * @return the wktDesc
	 */
	public String getWktDesc() {
		return wktDesc;
	}

	/**
	 * @param wktDesc the wktDesc to set
	 */
	public void setWktDesc(String wktDesc) {
		this.wktDesc = wktDesc;
	}

	/**
	 * @return the wktDescOth
	 */
	public String getWktDescOth() {
		return wktDescOth;
	}

	/**
	 * @param wktDescOth the wktDescOth to set
	 */
	public void setWktDescOth(String wktDescOth) {
		this.wktDescOth = wktDescOth;
	}

	/**
	 * @return the wktStatus
	 */
	public Character getWktStatus() {
		return wktStatus;
	}

	/**
	 * @param wktStatus the wktStatus to set
	 */
	public void setWktStatus(Character wktStatus) {
		this.wktStatus = wktStatus;
	}

	/**
	 * @return the wktDtCreate
	 */
	public Date getWktDtCreate() {
		return wktDtCreate;
	}

	/**
	 * @param wktDtCreate the wktDtCreate to set
	 */
	public void setWktDtCreate(Date wktDtCreate) {
		this.wktDtCreate = wktDtCreate;
	}

	/**
	 * @return the wktUidCreate
	 */
	public String getWktUidCreate() {
		return wktUidCreate;
	}

	/**
	 * @param wktUidCreate the wktUidCreate to set
	 */
	public void setWktUidCreate(String wktUidCreate) {
		this.wktUidCreate = wktUidCreate;
	}

	/**
	 * @return the wktDtLupd
	 */
	public Date getWktDtLupd() {
		return wktDtLupd;
	}

	/**
	 * @param wktDtLupd the wktDtLupd to set
	 */
	public void setWktDtLupd(Date wktDtLupd) {
		this.wktDtLupd = wktDtLupd;
	}

	/**
	 * @return the wktUidLupd
	 */
	public String getWktUidLupd() {
		return wktUidLupd;
	}

	/**
	 * @param wktUidLupd the wktUidLupd to set
	 */
	public void setWktUidLupd(String wktUidLupd) {
		this.wktUidLupd = wktUidLupd;
	}

	@Override
	public int compareTo(CkMstWorkflowType o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

}
