package com.guudint.clickargo.common.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.guudint.clickargo.master.model.TCkMstServiceType;
import com.vcc.camelone.ccm.model.TCoreAccn;
import com.vcc.camelone.ccm.model.TCoreUsr;
import com.vcc.camelone.common.COAbstractEntity;

@Entity
@Table(name = "T_CK_CS_ACCN")
public class TCkCsAccn extends COAbstractEntity<TCkCsAccn>{

	private static final long serialVersionUID = -4625625726306447285L;
	
	private String csaId;
	private TCkMstServiceType TCkMstServiceType;
	private TCoreAccn TCoreAccn;
	private TCoreUsr TCoreUsr;
	private Character csaStatus;
	private Date csaDtCreate;
	private String csaUidCreate;
	private Date csaDtLupd;
	private String csaUidLupd;

	public TCkCsAccn() {
	}

	public TCkCsAccn(String csaId) {
		this.csaId = csaId;
	}

	public TCkCsAccn(String csaId, TCkMstServiceType TCkMstServiceType, TCoreAccn TCoreAccn, TCoreUsr TCoreUsr,
			Character csaStatus, Date csaDtCreate, String csaUidCreate, Date csaDtLupd, String csaUidLupd) {
		this.csaId = csaId;
		this.TCkMstServiceType = TCkMstServiceType;
		this.TCoreAccn = TCoreAccn;
		this.TCoreUsr = TCoreUsr;
		this.csaStatus = csaStatus;
		this.csaDtCreate = csaDtCreate;
		this.csaUidCreate = csaUidCreate;
		this.csaDtLupd = csaDtLupd;
		this.csaUidLupd = csaUidLupd;
	}

	@Id

	@Column(name = "CSA_ID", unique = true, nullable = false, length = 35)
	public String getCsaId() {
		return this.csaId;
	}

	public void setCsaId(String csaId) {
		this.csaId = csaId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CSA_SERVICE_TYPE")
	public TCkMstServiceType getTCkMstServiceType() {
		return this.TCkMstServiceType;
	}

	public void setTCkMstServiceType(TCkMstServiceType TCkMstServiceType) {
		this.TCkMstServiceType = TCkMstServiceType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CSA_COMPANY")
	public TCoreAccn getTCoreAccn() {
		return this.TCoreAccn;
	}

	public void setTCoreAccn(TCoreAccn TCoreAccn) {
		this.TCoreAccn = TCoreAccn;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CSA_USR")
	public TCoreUsr getTCoreUsr() {
		return this.TCoreUsr;
	}

	public void setTCoreUsr(TCoreUsr TCoreUsr) {
		this.TCoreUsr = TCoreUsr;
	}

	@Column(name = "CSA_STATUS", length = 1)
	public Character getCsaStatus() {
		return this.csaStatus;
	}

	public void setCsaStatus(Character csaStatus) {
		this.csaStatus = csaStatus;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CSA_DT_CREATE", length = 19)
	public Date getCsaDtCreate() {
		return this.csaDtCreate;
	}

	public void setCsaDtCreate(Date csaDtCreate) {
		this.csaDtCreate = csaDtCreate;
	}

	@Column(name = "CSA_UID_CREATE", length = 35)
	public String getCsaUidCreate() {
		return this.csaUidCreate;
	}

	public void setCsaUidCreate(String csaUidCreate) {
		this.csaUidCreate = csaUidCreate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CSA_DT_LUPD", length = 19)
	public Date getCsaDtLupd() {
		return this.csaDtLupd;
	}

	public void setCsaDtLupd(Date csaDtLupd) {
		this.csaDtLupd = csaDtLupd;
	}

	@Column(name = "CSA_UID_LUPD", length = 35)
	public String getCsaUidLupd() {
		return this.csaUidLupd;
	}

	public void setCsaUidLupd(String csaUidLupd) {
		this.csaUidLupd = csaUidLupd;
	}
	
	@Override
	public int compareTo(TCkCsAccn o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

}
