package com.guudint.clickargo.admin.dto;

import com.guudint.clickargo.admin.model.TCkAccnConfigExtId;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkAccnConfigExtId extends AbstractDTO<CkAccnConfigExtId, TCkAccnConfigExtId> {

	private static final long serialVersionUID = 5833155530265958316L;
	private String caeAccnId;
	private String caeType;
	private String caeRoleId;
	private String caeAppsCode;

	public CkAccnConfigExtId() {
	}

	public CkAccnConfigExtId(TCkAccnConfigExtId entity) {
		super(entity);
	}

	public CkAccnConfigExtId(String caeAccnId, String caeType, String caeRoleId, String caeAppsCode) {
		this.caeAccnId = caeAccnId;
		this.caeType = caeType;
		this.caeRoleId = caeRoleId;
		this.caeAppsCode = caeAppsCode;
	}

	public String getCaeAccnId() {
		return caeAccnId;
	}

	public void setCaeAccnId(String caeAccnId) {
		this.caeAccnId = caeAccnId;
	}

	public String getCaeType() {
		return caeType;
	}

	public void setCaeType(String caeType) {
		this.caeType = caeType;
	}

	public String getCaeRoleId() {
		return caeRoleId;
	}

	public void setCaeRoleId(String caeRoleId) {
		this.caeRoleId = caeRoleId;
	}

	public String getCaeAppsCode() {
		return caeAppsCode;
	}

	public void setCaeAppsCode(String caeAppsCode) {
		this.caeAppsCode = caeAppsCode;
	}

	@Override
	public int compareTo(CkAccnConfigExtId o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}



}
