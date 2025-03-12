package com.guudint.clickargo.common.dto;

import java.util.Date;

import com.guudint.clickargo.common.model.TCkCsAccn;
import com.guudint.clickargo.master.dto.CkMstServiceType;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.ccm.dto.CoreUsr;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkCsAccn extends AbstractDTO<CkCsAccn, TCkCsAccn> {

	private static final long serialVersionUID = -4007358816103350277L;

	private String csaId;
	private CkMstServiceType TCkMstServiceType;
	private CoreAccn TCoreAccn;
	private CoreUsr TCoreUsr;
	private Character csaStatus;
	private Date csaDtCreate;
	private String csaUidCreate;
	private Date csaDtLupd;
	private String csaUidLupd;

	public String getCsaId() {
		return csaId;
	}

	public void setCsaId(String csaId) {
		this.csaId = csaId;
	}

	public CkMstServiceType getTCkMstServiceType() {
		return TCkMstServiceType;
	}

	public void setTCkMstServiceType(CkMstServiceType tCkMstServiceType) {
		TCkMstServiceType = tCkMstServiceType;
	}

	public CoreAccn getTCoreAccn() {
		return TCoreAccn;
	}

	public void setTCoreAccn(CoreAccn tCoreAccn) {
		TCoreAccn = tCoreAccn;
	}

	public CoreUsr getTCoreUsr() {
		return TCoreUsr;
	}

	public void setTCoreUsr(CoreUsr tCoreUsr) {
		TCoreUsr = tCoreUsr;
	}

	public Character getCsaStatus() {
		return csaStatus;
	}

	public void setCsaStatus(Character csaStatus) {
		this.csaStatus = csaStatus;
	}

	public Date getCsaDtCreate() {
		return csaDtCreate;
	}

	public void setCsaDtCreate(Date csaDtCreate) {
		this.csaDtCreate = csaDtCreate;
	}

	public String getCsaUidCreate() {
		return csaUidCreate;
	}

	public void setCsaUidCreate(String csaUidCreate) {
		this.csaUidCreate = csaUidCreate;
	}

	public Date getCsaDtLupd() {
		return csaDtLupd;
	}

	public void setCsaDtLupd(Date csaDtLupd) {
		this.csaDtLupd = csaDtLupd;
	}

	public String getCsaUidLupd() {
		return csaUidLupd;
	}

	public void setCsaUidLupd(String csaUidLupd) {
		this.csaUidLupd = csaUidLupd;
	}

	@Override
	public int compareTo(CkCsAccn o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

}
