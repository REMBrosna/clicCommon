package com.guudint.clickargo.sage.dto;
// Generated 23 Aug 2023, 9:52:50 am by Hibernate Tools 4.3.6.Final

import java.util.Date;

import com.guudint.clickargo.sage.model.TCkCtMstSageIntState;
import com.vcc.camelone.common.dto.AbstractDTO;


public class CkCtMstSageIntState extends AbstractDTO<CkCtMstSageIntState, TCkCtMstSageIntState> {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = -1L;
	
	private String sisId;
	private String sisName;
	private String sisDesc;
	private String sisDescOth;
	private Character sisStatus;
	private Date sisDtCreate;
	private String sisUidCreate;
	private Date sisDtLupd;
	private String sisUidLupd;

	public CkCtMstSageIntState() {
	}

	public CkCtMstSageIntState(String sisId) {
		this.sisId = sisId;
	}

	public CkCtMstSageIntState(String sisId, String sisName, String sisDesc, String sisDescOth, Character sisStatus,
			Date sisDtCreate, String sisUidCreate, Date sisDtLupd, String sisUidLupd) {
		this.sisId = sisId;
		this.sisName = sisName;
		this.sisDesc = sisDesc;
		this.sisDescOth = sisDescOth;
		this.sisStatus = sisStatus;
		this.sisDtCreate = sisDtCreate;
		this.sisUidCreate = sisUidCreate;
		this.sisDtLupd = sisDtLupd;
		this.sisUidLupd = sisUidLupd;
	}

	// Override Methods
	///////////////////
	@Override
	public int compareTo(CkCtMstSageIntState arg0) {
		return 0;
	}

	@Override
	public void init() {
	}

	public String getSisId() {
		return this.sisId;
	}

	public void setSisId(String sisId) {
		this.sisId = sisId;
	}

	public String getSisName() {
		return this.sisName;
	}

	public void setSisName(String sisName) {
		this.sisName = sisName;
	}

	public String getSisDesc() {
		return this.sisDesc;
	}

	public void setSisDesc(String sisDesc) {
		this.sisDesc = sisDesc;
	}

	public String getSisDescOth() {
		return this.sisDescOth;
	}

	public void setSisDescOth(String sisDescOth) {
		this.sisDescOth = sisDescOth;
	}

	public Character getSisStatus() {
		return this.sisStatus;
	}

	public void setSisStatus(Character sisStatus) {
		this.sisStatus = sisStatus;
	}

	public Date getSisDtCreate() {
		return this.sisDtCreate;
	}

	public void setSisDtCreate(Date sisDtCreate) {
		this.sisDtCreate = sisDtCreate;
	}

	public String getSisUidCreate() {
		return this.sisUidCreate;
	}

	public void setSisUidCreate(String sisUidCreate) {
		this.sisUidCreate = sisUidCreate;
	}

	public Date getSisDtLupd() {
		return this.sisDtLupd;
	}

	public void setSisDtLupd(Date sisDtLupd) {
		this.sisDtLupd = sisDtLupd;
	}

	public String getSisUidLupd() {
		return this.sisUidLupd;
	}

	public void setSisUidLupd(String sisUidLupd) {
		this.sisUidLupd = sisUidLupd;
	}


}
