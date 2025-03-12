package com.guudint.clickargo.admin.model;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vcc.camelone.ccm.model.TCoreUsr;
import com.vcc.camelone.common.COAbstractEntity;

@Entity
@Table(name = "T_CK_USR_EXT")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class TCkUsrExt extends COAbstractEntity<TCkUsrExt> {

	private static final long serialVersionUID = 263953676521412263L;

	// Attributes
	/////////////
	private String uetId;
	private TCoreUsr TCoreUsr;
	private String uetNotifPref;
	private Character uetStatus;
	private Date uetDtCreate;
	private String uetUidCreate;
	private Date uetDtLupd;
	private String uetUidLupd;

	public TCkUsrExt() {
	}

	public TCkUsrExt(String uetId) {
		this.uetId = uetId;
	}

	public TCkUsrExt(String uetId, TCoreUsr TCoreUsr, String uetNotifPref, Character uetStatus, Date uetDtCreate,
			String uetUidCreate, Date uetDtLupd, String uetUidLupd) {
		this.uetId = uetId;
		this.TCoreUsr = TCoreUsr;
		this.uetNotifPref = uetNotifPref;
		this.uetStatus = uetStatus;
		this.uetDtCreate = uetDtCreate;
		this.uetUidCreate = uetUidCreate;
		this.uetDtLupd = uetDtLupd;
		this.uetUidLupd = uetUidLupd;
	}

	@Id

	@Column(name = "UET_ID", unique = true, nullable = false, length = 35)
	public String getUetId() {
		return this.uetId;
	}

	public void setUetId(String uetId) {
		this.uetId = uetId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UET_USR_UID")
	public TCoreUsr getTCoreUsr() {
		return this.TCoreUsr;
	}

	public void setTCoreUsr(TCoreUsr TCoreUsr) {
		this.TCoreUsr = TCoreUsr;
	}

	@Column(name = "UET_NOTIF_PREF", length = 1024)
	public String getUetNotifPref() {
		return this.uetNotifPref;
	}

	public void setUetNotifPref(String uetNotifPref) {
		this.uetNotifPref = uetNotifPref;
	}

	@Column(name = "UET_STATUS", length = 1)
	public Character getUetStatus() {
		return this.uetStatus;
	}

	public void setUetStatus(Character uetStatus) {
		this.uetStatus = uetStatus;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UET_DT_CREATE", length = 19)
	public Date getUetDtCreate() {
		return this.uetDtCreate;
	}

	public void setUetDtCreate(Date uetDtCreate) {
		this.uetDtCreate = uetDtCreate;
	}

	@Column(name = "UET_UID_CREATE", length = 35)
	public String getUetUidCreate() {
		return this.uetUidCreate;
	}

	public void setUetUidCreate(String uetUidCreate) {
		this.uetUidCreate = uetUidCreate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UET_DT_LUPD", length = 19)
	public Date getUetDtLupd() {
		return this.uetDtLupd;
	}

	public void setUetDtLupd(Date uetDtLupd) {
		this.uetDtLupd = uetDtLupd;
	}

	@Column(name = "UET_UID_LUPD", length = 35)
	public String getUetUidLupd() {
		return this.uetUidLupd;
	}

	public void setUetUidLupd(String uetUidLupd) {
		this.uetUidLupd = uetUidLupd;
	}

	@Override
	public int compareTo(TCkUsrExt arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

}
