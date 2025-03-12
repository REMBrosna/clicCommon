package com.guudint.clickargo.common.dto;

import java.util.Date;

import com.guudint.clickargo.common.model.TCkUnGrantUserEx;
import com.vcc.camelone.cac.dto.CorePermEx;
import com.vcc.camelone.ccm.dto.CoreUsr;
import com.vcc.camelone.common.dto.AbstractDTO;
import com.vcc.camelone.core.dto.CoreApps;

public class CkUnGrantUserEx extends AbstractDTO<CkUnGrantUserEx, TCkUnGrantUserEx> {

	private static final long serialVersionUID = 5271095561039265357L;
	private CkUnGrantUserExId id;
	private CoreApps TCoreApps;
	private CorePermEx TCorePermEx;
	private CoreUsr TCoreUsr;
	private char grntueStatus;
	private Date grntueDtCreate;
	private String grntueUidCreate;
	private Date grntueDtLupd;
	private String grntueUidLupd;

	public CkUnGrantUserEx() {
	}

	public CkUnGrantUserEx(TCkUnGrantUserEx entity) {
		super(entity);
	}

	public CkUnGrantUserEx(CkUnGrantUserExId id, CoreApps TCoreApps, CorePermEx TCorePermEx, CoreUsr TCoreUsr,
			char grntueStatus, Date grntueDtCreate, String grntueUidCreate) {
		this.id = id;
		this.TCoreApps = TCoreApps;
		this.TCorePermEx = TCorePermEx;
		this.TCoreUsr = TCoreUsr;
		this.grntueStatus = grntueStatus;
		this.grntueDtCreate = grntueDtCreate;
		this.grntueUidCreate = grntueUidCreate;
	}

	public CkUnGrantUserExId getId() {
		return id;
	}

	public void setId(CkUnGrantUserExId id) {
		this.id = id;
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

	public CoreUsr getTCoreUsr() {
		return TCoreUsr;
	}

	public void setTCoreUsr(CoreUsr tCoreUsr) {
		TCoreUsr = tCoreUsr;
	}

	public char getGrntueStatus() {
		return grntueStatus;
	}

	public void setGrntueStatus(char grntueStatus) {
		this.grntueStatus = grntueStatus;
	}

	public Date getGrntueDtCreate() {
		return grntueDtCreate;
	}

	public void setGrntueDtCreate(Date grntueDtCreate) {
		this.grntueDtCreate = grntueDtCreate;
	}

	public String getGrntueUidCreate() {
		return grntueUidCreate;
	}

	public void setGrntueUidCreate(String grntueUidCreate) {
		this.grntueUidCreate = grntueUidCreate;
	}

	public Date getGrntueDtLupd() {
		return grntueDtLupd;
	}

	public void setGrntueDtLupd(Date grntueDtLupd) {
		this.grntueDtLupd = grntueDtLupd;
	}

	public String getGrntueUidLupd() {
		return grntueUidLupd;
	}

	public void setGrntueUidLupd(String grntueUidLupd) {
		this.grntueUidLupd = grntueUidLupd;
	}

	@Override
	public int compareTo(CkUnGrantUserEx o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

}
