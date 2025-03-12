package com.guudint.clickargo.admin.dto;

import java.util.Date;

import com.guudint.clickargo.admin.model.TCkAccnConfigExt;
import com.vcc.camelone.ccm.model.TCoreAccn;
import com.vcc.camelone.common.dto.AbstractDTO;
import com.vcc.camelone.core.model.TCoreApps;

public class CkAccnConfigExt extends AbstractDTO<CkAccnConfigExt,TCkAccnConfigExt> {

	private static final long serialVersionUID = -3645028557903029308L;
	private CkAccnConfigExtId id;
	private TCoreAccn TCoreAccn;
	private TCoreApps TCoreApps;
	private String caeValue;
	private Character caeStatus;
	private Date caeDtCreate;
	private String caeUidCreate;
	private Date caeDtLupd;
	private String caeUidLupd;

	public CkAccnConfigExt() {
	}
	
	public CkAccnConfigExt(TCkAccnConfigExt entity) {
		super(entity);
	}

	public CkAccnConfigExt(CkAccnConfigExtId id, TCoreAccn TCoreAccn, TCoreApps TCoreApps) {
		this.id = id;
		this.TCoreAccn = TCoreAccn;
		this.TCoreApps = TCoreApps;
	}

	public CkAccnConfigExt(CkAccnConfigExtId id, TCoreAccn TCoreAccn, TCoreApps TCoreApps, String caeValue,
			Character caeStatus, Date caeDtCreate, String caeUidCreate, Date caeDtLupd, String caeUidLupd) {
		this.id = id;
		this.TCoreAccn = TCoreAccn;
		this.TCoreApps = TCoreApps;
		this.caeValue = caeValue;
		this.caeStatus = caeStatus;
		this.caeDtCreate = caeDtCreate;
		this.caeUidCreate = caeUidCreate;
		this.caeDtLupd = caeDtLupd;
		this.caeUidLupd = caeUidLupd;
	}

	public CkAccnConfigExtId getId() {
		return id;
	}

	public void setId(CkAccnConfigExtId id) {
		this.id = id;
	}

	public TCoreAccn getTCoreAccn() {
		return TCoreAccn;
	}

	public void setTCoreAccn(TCoreAccn tCoreAccn) {
		TCoreAccn = tCoreAccn;
	}

	public TCoreApps getTCoreApps() {
		return TCoreApps;
	}

	public void setTCoreApps(TCoreApps tCoreApps) {
		TCoreApps = tCoreApps;
	}

	public String getCaeValue() {
		return caeValue;
	}

	public void setCaeValue(String caeValue) {
		this.caeValue = caeValue;
	}

	public Character getCaeStatus() {
		return caeStatus;
	}

	public void setCaeStatus(Character caeStatus) {
		this.caeStatus = caeStatus;
	}

	public Date getCaeDtCreate() {
		return caeDtCreate;
	}

	public void setCaeDtCreate(Date caeDtCreate) {
		this.caeDtCreate = caeDtCreate;
	}

	public String getCaeUidCreate() {
		return caeUidCreate;
	}

	public void setCaeUidCreate(String caeUidCreate) {
		this.caeUidCreate = caeUidCreate;
	}

	public Date getCaeDtLupd() {
		return caeDtLupd;
	}

	public void setCaeDtLupd(Date caeDtLupd) {
		this.caeDtLupd = caeDtLupd;
	}

	public String getCaeUidLupd() {
		return caeUidLupd;
	}

	public void setCaeUidLupd(String caeUidLupd) {
		this.caeUidLupd = caeUidLupd;
	}

	@Override
	public int compareTo(CkAccnConfigExt o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	

}
