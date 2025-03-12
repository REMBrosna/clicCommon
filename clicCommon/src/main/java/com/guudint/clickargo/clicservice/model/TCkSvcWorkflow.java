package com.guudint.clickargo.clicservice.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.guudint.clickargo.common.model.TCkMstFormAction;
import com.guudint.clickargo.master.model.TCkMstJobState;
import com.guudint.clickargo.master.model.TCkMstServiceType;
import com.vcc.camelone.cac.model.TCoreRole;
import com.vcc.camelone.common.COAbstractEntity;
import com.vcc.camelone.core.model.TCoreApps;
import com.vcc.camelone.master.model.TMstAccnType;

@Entity
@Table(name = "T_CK_SVC_WORKFLOW")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })

public class TCkSvcWorkflow extends COAbstractEntity<TCkSvcWorkflow> {

	private static final long serialVersionUID = 1021214316617043829L;

	private String wkflId;
	private TCkMstFormAction TCkMstFormAction;
	private TCkMstJobState TCkMstJobStateByWkflToState;
	private TCkMstJobState TCkMstJobStateByWkflFromState;
	private TCkMstServiceType TCkMstServiceType;
	private TCoreApps TCoreApps;
	private TMstAccnType TMstAccnType;
	private TCoreRole TCoreRole;
	private String wkflUri;
	private char wkflStatus;
	private Date wkflDtCreate;
	private String wkflUidCreate;
	private Date wkflDtLupd;
	private String wkflUidLupd;

	public TCkSvcWorkflow() {
	}

	public TCkSvcWorkflow(String wkflId, TCkMstFormAction TCkMstFormAction, TCkMstJobState TCkMstJobStateByWkflToState,
			TCkMstJobState TCkMstJobStateByWkflFromState, TCkMstServiceType TCkMstServiceType, TCoreApps TCoreApps,
			TMstAccnType TMstAccnType, TCoreRole TCoreRole, char wkflStatus) {
		this.wkflId = wkflId;
		this.TCkMstFormAction = TCkMstFormAction;
		this.TCkMstJobStateByWkflToState = TCkMstJobStateByWkflToState;
		this.TCkMstJobStateByWkflFromState = TCkMstJobStateByWkflFromState;
		this.TCkMstServiceType = TCkMstServiceType;
		this.TCoreApps = TCoreApps;
		this.TMstAccnType = TMstAccnType;
		this.TCoreRole = TCoreRole;
		this.wkflStatus = wkflStatus;
	}

	public TCkSvcWorkflow(String wkflId, TCkMstFormAction TCkMstFormAction, TCkMstJobState TCkMstJobStateByWkflToState,
			TCkMstJobState TCkMstJobStateByWkflFromState, TCkMstServiceType TCkMstServiceType, TCoreApps TCoreApps,
			TMstAccnType TMstAccnType, TCoreRole TCoreRole, String wkflUri, char wkflStatus, Date wkflDtCreate,
			String wkflUidCreate, Date wkflDtLupd, String wkflUidLupd) {
		this.wkflId = wkflId;
		this.TCkMstFormAction = TCkMstFormAction;
		this.TCkMstJobStateByWkflToState = TCkMstJobStateByWkflToState;
		this.TCkMstJobStateByWkflFromState = TCkMstJobStateByWkflFromState;
		this.TCkMstServiceType = TCkMstServiceType;
		this.TCoreApps = TCoreApps;
		this.TMstAccnType = TMstAccnType;
		this.TCoreRole = TCoreRole;
		this.wkflUri = wkflUri;
		this.wkflStatus = wkflStatus;
		this.wkflDtCreate = wkflDtCreate;
		this.wkflUidCreate = wkflUidCreate;
		this.wkflDtLupd = wkflDtLupd;
		this.wkflUidLupd = wkflUidLupd;
	}

	@Id

	@Column(name = "WKFL_ID", unique = true, nullable = false, length = 35)
	public String getWkflId() {
		return this.wkflId;
	}

	public void setWkflId(String wkflId) {
		this.wkflId = wkflId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "WKFL_ACTION", nullable = false)
	public TCkMstFormAction getTCkMstFormAction() {
		return this.TCkMstFormAction;
	}

	public void setTCkMstFormAction(TCkMstFormAction TCkMstFormAction) {
		this.TCkMstFormAction = TCkMstFormAction;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "WKFL_TO_STATE", nullable = false)
	public TCkMstJobState getTCkMstJobStateByWkflToState() {
		return this.TCkMstJobStateByWkflToState;
	}

	public void setTCkMstJobStateByWkflToState(TCkMstJobState TCkMstJobStateByWkflToState) {
		this.TCkMstJobStateByWkflToState = TCkMstJobStateByWkflToState;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "WKFL_FROM_STATE", nullable = false)
	public TCkMstJobState getTCkMstJobStateByWkflFromState() {
		return this.TCkMstJobStateByWkflFromState;
	}

	public void setTCkMstJobStateByWkflFromState(TCkMstJobState TCkMstJobStateByWkflFromState) {
		this.TCkMstJobStateByWkflFromState = TCkMstJobStateByWkflFromState;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "WKFL_SCV_TYPE", nullable = false)
	public TCkMstServiceType getTCkMstServiceType() {
		return this.TCkMstServiceType;
	}

	public void setTCkMstServiceType(TCkMstServiceType TCkMstServiceType) {
		this.TCkMstServiceType = TCkMstServiceType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "WKFL_APPS_CODE", nullable = false)
	public TCoreApps getTCoreApps() {
		return this.TCoreApps;
	}

	public void setTCoreApps(TCoreApps TCoreApps) {
		this.TCoreApps = TCoreApps;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "WKFL_ACCN_TYPE", nullable = false)
	public TMstAccnType getTMstAccnType() {
		return TMstAccnType;
	}

	public void setTMstAccnType(TMstAccnType tMstAccnType) {
		TMstAccnType = tMstAccnType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "WKFL_APPS_CODE", referencedColumnName = "ROLE_APPSCODE", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "WKFL_ROLE", referencedColumnName = "ROLE_ID", nullable = false, insertable = false, updatable = false) })
	public TCoreRole getTCoreRole() {
		return this.TCoreRole;
	}

	public void setTCoreRole(TCoreRole TCoreRole) {
		this.TCoreRole = TCoreRole;
	}

	@Column(name = "WKFL_URI")
	public String getWkflUri() {
		return this.wkflUri;
	}

	public void setWkflUri(String wkflUri) {
		this.wkflUri = wkflUri;
	}

	@Column(name = "WKFL_STATUS", nullable = false, length = 1)
	public char getWkflStatus() {
		return this.wkflStatus;
	}

	public void setWkflStatus(char wkflStatus) {
		this.wkflStatus = wkflStatus;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "WKFL_DT_CREATE", length = 19)
	public Date getWkflDtCreate() {
		return this.wkflDtCreate;
	}

	public void setWkflDtCreate(Date wkflDtCreate) {
		this.wkflDtCreate = wkflDtCreate;
	}

	@Column(name = "WKFL_UID_CREATE", length = 35)
	public String getWkflUidCreate() {
		return this.wkflUidCreate;
	}

	public void setWkflUidCreate(String wkflUidCreate) {
		this.wkflUidCreate = wkflUidCreate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "WKFL_DT_LUPD", length = 19)
	public Date getWkflDtLupd() {
		return this.wkflDtLupd;
	}

	public void setWkflDtLupd(Date wkflDtLupd) {
		this.wkflDtLupd = wkflDtLupd;
	}

	@Column(name = "WKFL_UID_LUPD", length = 35)
	public String getWkflUidLupd() {
		return this.wkflUidLupd;
	}

	public void setWkflUidLupd(String wkflUidLupd) {
		this.wkflUidLupd = wkflUidLupd;
	}

	@Override
	public int compareTo(TCkSvcWorkflow o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

}
