package com.guudint.clickargo.common.dto;

import com.guudint.clickargo.common.model.TCkUnGrantAccountExId;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkUnGrantAccountExId extends AbstractDTO<CkUnGrantAccountExId, TCkUnGrantAccountExId> {

	private static final long serialVersionUID = -355985252891929230L;
	private String grntaAccnId;
	private String grntaAppsCode;
	private String grntaPermExId;
	private String grntaRoleId;

	public CkUnGrantAccountExId() {
	}

	public CkUnGrantAccountExId(TCkUnGrantAccountExId entity) {
		super(entity);
	}

	public CkUnGrantAccountExId(String grntaAccnId, String grntaAppsCode, String grntaPermExId, String grntaRoleId) {
		this.grntaAccnId = grntaAccnId;
		this.grntaAppsCode = grntaAppsCode;
		this.grntaPermExId = grntaPermExId;
		this.grntaRoleId = grntaRoleId;
	}

	@Override
	public int compareTo(CkUnGrantAccountExId o) {
		// TODO Auto-generated method stub
		if (o.grntaAccnId.equalsIgnoreCase(this.grntaAccnId) && o.grntaAppsCode.equalsIgnoreCase(this.grntaAppsCode)
				&& o.grntaPermExId.equalsIgnoreCase(this.grntaPermExId)
				&& o.grntaRoleId.equalsIgnoreCase(this.grntaRoleId))
			return 0;
		return -1;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	public String getGrntaAccnId() {
		return grntaAccnId;
	}

	public void setGrntaAccnId(String grntaAccnId) {
		this.grntaAccnId = grntaAccnId;
	}

	public String getGrntaAppsCode() {
		return grntaAppsCode;
	}

	public void setGrntaAppsCode(String grntaAppsCode) {
		this.grntaAppsCode = grntaAppsCode;
	}

	public String getGrntaPermExId() {
		return grntaPermExId;
	}

	public void setGrntaPermExId(String grntaPermExId) {
		this.grntaPermExId = grntaPermExId;
	}

	public String getGrntaRoleId() {
		return grntaRoleId;
	}

	public void setGrntaRoleId(String grntaRoleId) {
		this.grntaRoleId = grntaRoleId;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return grntaAppsCode + ":" + grntaAccnId + ":" + grntaRoleId + ":" + grntaPermExId;
	}

	@Override
	public boolean equals(Object other) {
		log.debug("equal");
		// TODO Auto-generated method stub
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CkUnGrantAccountExId))
			return false;

		CkUnGrantAccountExId castOther = (CkUnGrantAccountExId) other;
		return ((this.getGrntaAppsCode() == castOther.getGrntaAppsCode())
				|| (this.getGrntaAppsCode() != null && castOther.getGrntaAppsCode() != null
						&& this.getGrntaAppsCode().equals(castOther.getGrntaAppsCode())))
				&& ((this.getGrntaAccnId() == castOther.getGrntaAccnId())
						|| (this.getGrntaAccnId() != null && castOther.getGrntaAccnId() != null
								&& this.getGrntaAccnId().equals(castOther.getGrntaAccnId())))
				&& ((this.getGrntaRoleId() == castOther.getGrntaRoleId())
						|| (this.getGrntaRoleId() != null && castOther.getGrntaRoleId() != null
								&& this.getGrntaRoleId().equals(castOther.getGrntaRoleId())))
				&& (this.getGrntaPermExId() == castOther.getGrntaPermExId())
				|| (this.getGrntaPermExId() != null && castOther.getGrntaPermExId() != null
						&& this.getGrntaPermExId().equals(castOther.getGrntaPermExId()));
	}

	@Override
	public int hashCode() {
		log.debug("hashCode");
		int result = 17;

		result = 37 * result + (getGrntaAppsCode() == null ? 0 : this.getGrntaAppsCode().hashCode());
		result = 37 * result + (getGrntaAccnId() == null ? 0 : this.getGrntaAccnId().hashCode());
		result = 37 * result + (getGrntaRoleId() == null ? 0 : this.getGrntaRoleId().hashCode());
		result = 37 * result + (getGrntaPermExId() == null ? 0 : this.getGrntaPermExId().hashCode());
		return result;
	}

}
