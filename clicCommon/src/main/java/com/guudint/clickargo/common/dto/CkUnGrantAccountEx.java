package com.guudint.clickargo.common.dto;

import java.util.Date;

import com.guudint.clickargo.common.model.TCkUnGrantAccountEx;
import com.vcc.camelone.cac.dto.CorePermEx;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.common.dto.AbstractDTO;
import com.vcc.camelone.core.dto.CoreApps;

public class CkUnGrantAccountEx extends AbstractDTO<CkUnGrantAccountEx, TCkUnGrantAccountEx> {

	private static final long serialVersionUID = -6716262864633838109L;
	private CkUnGrantAccountExId id;
	private CoreAccn TCoreAccn;
	private CoreApps TCoreApps;
	private CorePermEx TCorePermEx;
	private char grntaStatus;
	private Date grntaDtCreate;
	private String grntaUidCreate;
	private Date grntaDtLupd;
	private String grntaUidLupd;

	public CkUnGrantAccountEx() {
	}

	public CkUnGrantAccountEx(TCkUnGrantAccountEx entity) {
		super(entity);
	}

	public CkUnGrantAccountEx(CkUnGrantAccountExId id, CoreAccn TCoreAccn, CoreApps TCoreApps, CorePermEx TCorePermEx,
			char grntaStatus, Date grntaDtCreate, String grntaUidCreate) {
		this.id = id;
		this.TCoreAccn = TCoreAccn;
		this.TCoreApps = TCoreApps;
		this.TCorePermEx = TCorePermEx;
		this.grntaStatus = grntaStatus;
		this.grntaDtCreate = grntaDtCreate;
		this.grntaUidCreate = grntaUidCreate;
	}

	public CkUnGrantAccountExId getId() {
		return id;
	}

	public void setId(CkUnGrantAccountExId id) {
		this.id = id;
	}

	public CoreAccn getTCoreAccn() {
		return TCoreAccn;
	}

	public void setTCoreAccn(CoreAccn tCoreAccn) {
		TCoreAccn = tCoreAccn;
	}

	public CoreApps getTCoreApps() {
		return TCoreApps;
	}

	public void setTCoreApps(CoreApps tCoreApps) {
		TCoreApps = tCoreApps;
	}

	public CorePermEx getTCorePermEx() {
		return TCorePermEx;
	}

	public void setTCorePermEx(CorePermEx tCorePermEx) {
		TCorePermEx = tCorePermEx;
	}

	public char getGrntaStatus() {
		return grntaStatus;
	}

	public void setGrntaStatus(char grntaStatus) {
		this.grntaStatus = grntaStatus;
	}

	public Date getGrntaDtCreate() {
		return grntaDtCreate;
	}

	public void setGrntaDtCreate(Date grntaDtCreate) {
		this.grntaDtCreate = grntaDtCreate;
	}

	public String getGrntaUidCreate() {
		return grntaUidCreate;
	}

	public void setGrntaUidCreate(String grntaUidCreate) {
		this.grntaUidCreate = grntaUidCreate;
	}

	public Date getGrntaDtLupd() {
		return grntaDtLupd;
	}

	public void setGrntaDtLupd(Date grntaDtLupd) {
		this.grntaDtLupd = grntaDtLupd;
	}

	public String getGrntaUidLupd() {
		return grntaUidLupd;
	}

	public void setGrntaUidLupd(String grntaUidLupd) {
		this.grntaUidLupd = grntaUidLupd;
	}

	@Override
	public int compareTo(CkUnGrantAccountEx o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

}
