package com.guudint.clickargo.common.dto;

import com.guudint.clickargo.common.model.TCkUnGrantUserExId;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkUnGrantUserExId extends AbstractDTO<CkUnGrantUserExId, TCkUnGrantUserExId> {

	private static final long serialVersionUID = -6806029758599411578L;
	private String grntueUsrId;
	private String grntueAppsCode;
	private String grntuePermExId;
	private String grntueRoleId;

	public CkUnGrantUserExId() {
	}

	public CkUnGrantUserExId(TCkUnGrantUserExId entity) {
		super(entity);
	}

	public CkUnGrantUserExId(String grntueUsrId, String grntueAppsCode, String grntuePermExId, String grntueRoleId) {
		this.grntueUsrId = grntueUsrId;
		this.grntueAppsCode = grntueAppsCode;
		this.grntuePermExId = grntuePermExId;
		this.grntueRoleId = grntueRoleId;
	}

	public String getGrntueUsrId() {
		return grntueUsrId;
	}

	public void setGrntueUsrId(String grntueUsrId) {
		this.grntueUsrId = grntueUsrId;
	}

	public String getGrntueAppsCode() {
		return grntueAppsCode;
	}

	public void setGrntueAppsCode(String grntueAppsCode) {
		this.grntueAppsCode = grntueAppsCode;
	}

	public String getGrntuePermExId() {
		return grntuePermExId;
	}

	public void setGrntuePermExId(String grntuePermExId) {
		this.grntuePermExId = grntuePermExId;
	}

	public String getGrntueRoleId() {
		return grntueRoleId;
	}

	public void setGrntueRoleId(String grntueRoleId) {
		this.grntueRoleId = grntueRoleId;
	}

	@Override
	public int compareTo(CkUnGrantUserExId o) {
		// TODO Auto-generated method stub
		if (o.grntueAppsCode.equalsIgnoreCase(this.grntueAppsCode) && o.grntueUsrId.equalsIgnoreCase(this.grntueUsrId)
				&& o.grntuePermExId.equalsIgnoreCase(this.grntuePermExId)
				&& o.grntueRoleId.equalsIgnoreCase(this.grntueRoleId))
			return 0;
		return -1;
	}

	@Override
	public boolean equals(Object other) {
		log.debug("equal");
		// TODO Auto-generated method stub
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CkUnGrantUserExId))
			return false;

		CkUnGrantUserExId castOther = (CkUnGrantUserExId) other;
		return ((this.getGrntueUsrId() == castOther.getGrntueUsrId()) || (this.getGrntueUsrId() != null
				&& castOther.getGrntueUsrId() != null && this.getGrntueUsrId().equals(castOther.getGrntueUsrId())))
				&& ((this.getGrntueAppsCode() == castOther.getGrntueAppsCode())
						|| (this.getGrntueAppsCode() != null && castOther.getGrntueAppsCode() != null
								&& this.getGrntueAppsCode().equals(castOther.getGrntueAppsCode())))
				&& ((this.getGrntuePermExId() == castOther.getGrntuePermExId())
						|| (this.getGrntuePermExId() != null && castOther.getGrntuePermExId() != null
								&& this.getGrntuePermExId().equals(castOther.getGrntuePermExId())))
				&& ((this.getGrntueRoleId() == castOther.getGrntueRoleId())
						|| (this.getGrntueRoleId() != null && castOther.getGrntueRoleId() != null
								&& this.getGrntueRoleId().equals(castOther.getGrntueRoleId())));
	}

	@Override
	public int hashCode() {
		log.debug("hashCode");
		int result = 17;

		result = 37 * result + (getGrntueUsrId() == null ? 0 : this.getGrntueUsrId().hashCode());
		result = 37 * result + (getGrntueAppsCode() == null ? 0 : this.getGrntueAppsCode().hashCode());
		result = 37 * result + (getGrntuePermExId() == null ? 0 : this.getGrntuePermExId().hashCode());
		result = 37 * result + (getGrntueRoleId() == null ? 0 : this.getGrntueRoleId().hashCode());
		return result;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return grntueAppsCode + ":" + grntueUsrId + ":" + grntuePermExId;
	}

}
