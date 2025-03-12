package com.guudint.clickargo.clicservice.dto;

import java.util.Date;

import com.guudint.clickargo.clicservice.model.TCkSvcActionMask;
import com.guudint.clickargo.master.dto.CkMstJobState;
import com.guudint.clickargo.master.dto.CkMstServiceType;
import com.vcc.camelone.cac.dto.CoreRole;
import com.vcc.camelone.common.dto.AbstractDTO;
import com.vcc.camelone.core.dto.CoreApps;
import com.vcc.camelone.master.dto.MstAccnType;

public class CkSvcActionMask extends AbstractDTO<CkSvcActionMask, TCkSvcActionMask> {

	private static final long serialVersionUID = 8060551535176856149L;
	private String samId;
	private CkMstJobState TCkMstJobState;
	private CkMstServiceType TCkMstServiceType;
	private CoreApps TCoreApps;
	private CoreRole TCoreRole;
	private MstAccnType TMstAccnType;
	private Boolean samMask;
	private char samStatus;
	private Date samDtCreate;
	private String samUidCreate;
	private Date samDtLupd;
	private String samUidLupd;

	public CkSvcActionMask() {
	}

	public CkSvcActionMask(TCkSvcActionMask entity) {
		super(entity);
	}

	/**
	 * @return the samId
	 */
	public String getSamId() {
		return samId;
	}

	/**
	 * @param samId the samId to set
	 */
	public void setSamId(String samId) {
		this.samId = samId;
	}

	/**
	 * @return the tCkMstJobState
	 */
	public CkMstJobState getTCkMstJobState() {
		return TCkMstJobState;
	}

	/**
	 * @param tCkMstJobState the tCkMstJobState to set
	 */
	public void setTCkMstJobState(CkMstJobState tCkMstJobState) {
		TCkMstJobState = tCkMstJobState;
	}

	/**
	 * @return the tCkMstServiceType
	 */
	public CkMstServiceType getTCkMstServiceType() {
		return TCkMstServiceType;
	}

	/**
	 * @param tCkMstServiceType the tCkMstServiceType to set
	 */
	public void setTCkMstServiceType(CkMstServiceType tCkMstServiceType) {
		TCkMstServiceType = tCkMstServiceType;
	}

	/**
	 * @return the tCoreApps
	 */
	public CoreApps getTCoreApps() {
		return TCoreApps;
	}

	/**
	 * @param tCoreApps the tCoreApps to set
	 */
	public void setTCoreApps(CoreApps tCoreApps) {
		TCoreApps = tCoreApps;
	}

	/**
	 * @return the tCoreRole
	 */
	public CoreRole getTCoreRole() {
		return TCoreRole;
	}

	/**
	 * @param tCoreRole the tCoreRole to set
	 */
	public void setTCoreRole(CoreRole tCoreRole) {
		TCoreRole = tCoreRole;
	}

	/**
	 * @return the tMstAccnType
	 */
	public MstAccnType getTMstAccnType() {
		return TMstAccnType;
	}

	/**
	 * @param tMstAccnType the tMstAccnType to set
	 */
	public void setTMstAccnType(MstAccnType tMstAccnType) {
		TMstAccnType = tMstAccnType;
	}

	/**
	 * @return the samMask
	 */
	public Boolean isSamMask() {
		return this.samMask;
	}

	/**
	 * @param samMask the samMask to set
	 */
	public void setSamMask(Boolean samMask) {
		this.samMask = samMask;
	}

	/**
	 * @return the samStatus
	 */
	public char getSamStatus() {
		return samStatus;
	}

	/**
	 * @param samStatus the samStatus to set
	 */
	public void setSamStatus(char samStatus) {
		this.samStatus = samStatus;
	}

	/**
	 * @return the samDtCreate
	 */
	public Date getSamDtCreate() {
		return samDtCreate;
	}

	/**
	 * @param samDtCreate the samDtCreate to set
	 */
	public void setSamDtCreate(Date samDtCreate) {
		this.samDtCreate = samDtCreate;
	}

	/**
	 * @return the samUidCreate
	 */
	public String getSamUidCreate() {
		return samUidCreate;
	}

	/**
	 * @param samUidCreate the samUidCreate to set
	 */
	public void setSamUidCreate(String samUidCreate) {
		this.samUidCreate = samUidCreate;
	}

	/**
	 * @return the samDtLupd
	 */
	public Date getSamDtLupd() {
		return samDtLupd;
	}

	/**
	 * @param samDtLupd the samDtLupd to set
	 */
	public void setSamDtLupd(Date samDtLupd) {
		this.samDtLupd = samDtLupd;
	}

	/**
	 * @return the samUidLupd
	 */
	public String getSamUidLupd() {
		return samUidLupd;
	}

	/**
	 * @param samUidLupd the samUidLupd to set
	 */
	public void setSamUidLupd(String samUidLupd) {
		this.samUidLupd = samUidLupd;
	}

	@Override
	public int compareTo(CkSvcActionMask o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

}
