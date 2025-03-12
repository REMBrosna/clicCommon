package com.guudint.clickargo.clicservice.dto;

import java.util.Date;

import com.guudint.clickargo.clicservice.model.TCkSvcWorkflow;
import com.guudint.clickargo.common.dto.CkMstFormAction;
import com.guudint.clickargo.master.dto.CkMstJobState;
import com.guudint.clickargo.master.dto.CkMstServiceType;
import com.vcc.camelone.cac.dto.CoreRole;
import com.vcc.camelone.common.dto.AbstractDTO;
import com.vcc.camelone.core.dto.CoreApps;

public class CkSvcWorkflow extends AbstractDTO<CkSvcWorkflow, TCkSvcWorkflow> {

	private static final long serialVersionUID = -6454998354815217033L;

	private String wkflId;
	private CkMstFormAction TCkMstFormAction;
	private CkMstJobState TCkMstJobStateByWkflToState;
	private CkMstJobState TCkMstJobStateByWkflFromState;
	private CkMstServiceType TCkMstServiceType;
	private CoreApps TCoreApps;
	private CoreRole TCoreRole;
	private String wkflUri;
	private char wkflStatus;
	private Date wkflDtCreate;
	private String wkflUidCreate;
	private Date wkflDtLupd;
	private String wkflUidLupd;

	public CkSvcWorkflow() {
	}

	public CkSvcWorkflow(TCkSvcWorkflow entity) {
		super(entity);
		// TODO Auto-generated constructor stub
	}

	public CkSvcWorkflow(String wkflId, CkMstFormAction tCkMstFormAction, CkMstJobState tCkMstJobStateByWkflToState,
			CkMstJobState tCkMstJobStateByWkflFromState, CkMstServiceType tCkMstServiceType, CoreApps tCoreApps,
			CoreRole tCoreRole, String wkflUri, char wkflStatus, Date wkflDtCreate, String wkflUidCreate,
			Date wkflDtLupd, String wkflUidLupd) {
		super();
		this.wkflId = wkflId;
		TCkMstFormAction = tCkMstFormAction;
		TCkMstJobStateByWkflToState = tCkMstJobStateByWkflToState;
		TCkMstJobStateByWkflFromState = tCkMstJobStateByWkflFromState;
		TCkMstServiceType = tCkMstServiceType;
		TCoreApps = tCoreApps;
		TCoreRole = tCoreRole;
		this.wkflUri = wkflUri;
		this.wkflStatus = wkflStatus;
		this.wkflDtCreate = wkflDtCreate;
		this.wkflUidCreate = wkflUidCreate;
		this.wkflDtLupd = wkflDtLupd;
		this.wkflUidLupd = wkflUidLupd;
	}

	public String getWkflId() {
		return wkflId;
	}

	public void setWkflId(String wkflId) {
		this.wkflId = wkflId;
	}

	public CkMstFormAction getTCkMstFormAction() {
		return TCkMstFormAction;
	}

	public void setTCkMstFormAction(CkMstFormAction tCkMstFormAction) {
		TCkMstFormAction = tCkMstFormAction;
	}

	public CkMstJobState getTCkMstJobStateByWkflToState() {
		return TCkMstJobStateByWkflToState;
	}

	public void setTCkMstJobStateByWkflToState(CkMstJobState tCkMstJobStateByWkflToState) {
		TCkMstJobStateByWkflToState = tCkMstJobStateByWkflToState;
	}

	public CkMstJobState getTCkMstJobStateByWkflFromState() {
		return TCkMstJobStateByWkflFromState;
	}

	public void setTCkMstJobStateByWkflFromState(CkMstJobState tCkMstJobStateByWkflFromState) {
		TCkMstJobStateByWkflFromState = tCkMstJobStateByWkflFromState;
	}

	public CkMstServiceType getTCkMstServiceType() {
		return TCkMstServiceType;
	}

	public void setTCkMstServiceType(CkMstServiceType tCkMstServiceType) {
		TCkMstServiceType = tCkMstServiceType;
	}

	public CoreApps getTCoreApps() {
		return TCoreApps;
	}

	public void setTCoreApps(CoreApps tCoreApps) {
		TCoreApps = tCoreApps;
	}

	public CoreRole getTCoreRole() {
		return TCoreRole;
	}

	public void setTCoreRole(CoreRole tCoreRole) {
		TCoreRole = tCoreRole;
	}

	public String getWkflUri() {
		return wkflUri;
	}

	public void setWkflUri(String wkflUri) {
		this.wkflUri = wkflUri;
	}

	public char getWkflStatus() {
		return wkflStatus;
	}

	public void setWkflStatus(char wkflStatus) {
		this.wkflStatus = wkflStatus;
	}

	public Date getWkflDtCreate() {
		return wkflDtCreate;
	}

	public void setWkflDtCreate(Date wkflDtCreate) {
		this.wkflDtCreate = wkflDtCreate;
	}

	public String getWkflUidCreate() {
		return wkflUidCreate;
	}

	public void setWkflUidCreate(String wkflUidCreate) {
		this.wkflUidCreate = wkflUidCreate;
	}

	public Date getWkflDtLupd() {
		return wkflDtLupd;
	}

	public void setWkflDtLupd(Date wkflDtLupd) {
		this.wkflDtLupd = wkflDtLupd;
	}

	public String getWkflUidLupd() {
		return wkflUidLupd;
	}

	public void setWkflUidLupd(String wkflUidLupd) {
		this.wkflUidLupd = wkflUidLupd;
	}

	@Override
	public int compareTo(CkSvcWorkflow o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

}
