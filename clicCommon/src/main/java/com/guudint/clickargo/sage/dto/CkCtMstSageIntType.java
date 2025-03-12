package com.guudint.clickargo.sage.dto;
// Generated 23 Aug 2023, 9:52:50 am by Hibernate Tools 4.3.6.Final

import java.util.Date;

import com.guudint.clickargo.sage.model.TCkCtMstSageIntType;
import com.vcc.camelone.common.dto.AbstractDTO;


public class CkCtMstSageIntType extends AbstractDTO<CkCtMstSageIntType, TCkCtMstSageIntType> {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = -1L;
	
	private String sitId;
	private String sitName;
	private String sitDesc;
	private String sitDescOth;
	private Character sitStatus;
	private Date sitDtCreate;
	private String sitUidCreate;
	private Date sitDtLupd;
	private String sitUidLupd;

	public CkCtMstSageIntType() {
	}

	public CkCtMstSageIntType(String sitId) {
		this.sitId = sitId;
	}

	public CkCtMstSageIntType(String sitId, String sitName, String sitDesc, String sitDescOth, Character sitStatus,
			Date sitDtCreate, String sitUidCreate, Date sitDtLupd, String sitUidLupd) {
		this.sitId = sitId;
		this.sitName = sitName;
		this.sitDesc = sitDesc;
		this.sitDescOth = sitDescOth;
		this.sitStatus = sitStatus;
		this.sitDtCreate = sitDtCreate;
		this.sitUidCreate = sitUidCreate;
		this.sitDtLupd = sitDtLupd;
		this.sitUidLupd = sitUidLupd;
	}

	// Override Methods
	///////////////////
	@Override
	public int compareTo(CkCtMstSageIntType arg0) {
		return 0;
	}

	@Override
	public void init() {
	}

	public String getSitId() {
		return this.sitId;
	}

	public void setSitId(String sitId) {
		this.sitId = sitId;
	}

	public String getSitName() {
		return this.sitName;
	}

	public void setSitName(String sitName) {
		this.sitName = sitName;
	}

	public String getSitDesc() {
		return this.sitDesc;
	}

	public void setSitDesc(String sitDesc) {
		this.sitDesc = sitDesc;
	}

	public String getSitDescOth() {
		return this.sitDescOth;
	}

	public void setSitDescOth(String sitDescOth) {
		this.sitDescOth = sitDescOth;
	}

	public Character getSitStatus() {
		return this.sitStatus;
	}

	public void setSitStatus(Character sitStatus) {
		this.sitStatus = sitStatus;
	}

	public Date getSitDtCreate() {
		return this.sitDtCreate;
	}

	public void setSitDtCreate(Date sitDtCreate) {
		this.sitDtCreate = sitDtCreate;
	}

	public String getSitUidCreate() {
		return this.sitUidCreate;
	}

	public void setSitUidCreate(String sitUidCreate) {
		this.sitUidCreate = sitUidCreate;
	}

	public Date getSitDtLupd() {
		return this.sitDtLupd;
	}

	public void setSitDtLupd(Date sitDtLupd) {
		this.sitDtLupd = sitDtLupd;
	}

	public String getSitUidLupd() {
		return this.sitUidLupd;
	}

	public void setSitUidLupd(String sitUidLupd) {
		this.sitUidLupd = sitUidLupd;
	}

}
