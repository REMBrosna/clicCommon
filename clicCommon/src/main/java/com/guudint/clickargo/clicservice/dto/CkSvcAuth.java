package com.guudint.clickargo.clicservice.dto;

import java.util.Date;

import javax.validation.constraints.Size;

import com.guudint.clickargo.clicservice.model.TCkSvcAuth;
import com.guudint.clickargo.master.dto.CkMstAuthState;
import com.guudint.clickargo.master.dto.CkMstServiceType;
import com.guudint.clickargo.validator.ValidationGroup.CreateValid;
import com.guudint.clickargo.validator.ValidationGroup.SubmitValid;
import com.guudint.clickargo.validator.ValidationGroup.UpdateValid;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.ccm.dto.CoreUsr;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkSvcAuth extends AbstractDTO<CkSvcAuth, TCkSvcAuth> {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = -8856087636240107315L;

	// Attributes
	/////////////
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 35, message = "{valid.clicservice.svauId.maxLength}")
	private String svauId;
	private CkMstAuthState TCkMstAuthState;
	private CkMstServiceType TCkMstServiceType;
	private CoreAccn TCoreAccnBySvauAccnService;
	private CoreAccn TCoreAccnBySvauAccnAuthorizer;
	private CoreAccn TCoreAccnBySvauAccnAuthorized;
	private CoreUsr TCoreUsrBySvauUsrAuthorizer;
	private CoreUsr TCoreUsrBySvauUsrAuthorized;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 255, message = "{valid.clicservice.svauPositionAuthorizer.maxLength}")
	private String svauPositionAuthorizer;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 255, message = "{valid.clicservice.svauPositionAuthorized.maxLength}")
	private String svauPositionAuthorized;
	private Date svauDtServiceStart;
	private Date svauDtServiceValid;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 16777215, message = "{valid.clicservice.svauRemarksAuthorizer.maxLength}")
	private String svauRemarksAuthorizer;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 16777215, message = "{valid.clicservice.svauRemarksAuthorized.maxLength}")
	private String svauRemarksAuthorized;
	private Character svauStatus;
	private Date svauDtCreate;
	private String svauUidCreate;
	private Date svauDtLupd;
	private String svauUidLupd;

	// Constructors
	///////////////
	public CkSvcAuth() {
	}
	
	/**
	 * @param entity
	 */
	public CkSvcAuth(TCkSvcAuth entity) {
		super(entity);
	}
	

	/**
	 * @param svauId
	 */
	public CkSvcAuth(String svauId) {
		this.svauId = svauId;
	}

	/**
	 * @param svauId
	 * @param TCkMstAuthState
	 * @param TCkMstServiceType
	 * @param TCoreAccnBySvauAccnService
	 * @param TCoreAccnBySvauAccnAuthorizer
	 * @param TCoreAccnBySvauAccnAuthorized
	 * @param TCoreUsrBySvauUsrAuthorizer
	 * @param TCoreUsrBySvauUsrAuthorized
	 * @param svauPositionAuthorizer
	 * @param svauPositionAuthorized
	 * @param svauDtServiceStart
	 * @param svauDtServiceValid
	 * @param svauRemarksAuthorizer
	 * @param svauRemarksAuthorized
	 * @param svauStatus
	 * @param svauDtCreate
	 * @param svauUidCreate
	 * @param svauDtLupd
	 * @param svauUidLupd
	 */
	public CkSvcAuth(String svauId, CkMstAuthState TCkMstAuthState, CkMstServiceType TCkMstServiceType,
			CoreAccn TCoreAccnBySvauAccnService, CoreAccn TCoreAccnBySvauAccnAuthorizer,
			CoreAccn TCoreAccnBySvauAccnAuthorized, CoreUsr TCoreUsrBySvauUsrAuthorizer,
			CoreUsr TCoreUsrBySvauUsrAuthorized, String svauPositionAuthorizer, String svauPositionAuthorized,
			Date svauDtServiceStart, Date svauDtServiceValid, String svauRemarksAuthorizer,
			String svauRemarksAuthorized, Character svauStatus, Date svauDtCreate, String svauUidCreate,
			Date svauDtLupd, String svauUidLupd) {
		this.svauId = svauId;
		this.TCkMstAuthState = TCkMstAuthState;
		this.TCkMstServiceType = TCkMstServiceType;
		this.TCoreAccnBySvauAccnService = TCoreAccnBySvauAccnService;
		this.TCoreAccnBySvauAccnAuthorizer = TCoreAccnBySvauAccnAuthorizer;
		this.TCoreAccnBySvauAccnAuthorized = TCoreAccnBySvauAccnAuthorized;
		this.TCoreUsrBySvauUsrAuthorizer = TCoreUsrBySvauUsrAuthorizer;
		this.TCoreUsrBySvauUsrAuthorized = TCoreUsrBySvauUsrAuthorized;
		this.svauPositionAuthorizer = svauPositionAuthorizer;
		this.svauPositionAuthorized = svauPositionAuthorized;
		this.svauDtServiceStart = svauDtServiceStart;
		this.svauDtServiceValid = svauDtServiceValid;
		this.svauRemarksAuthorizer = svauRemarksAuthorizer;
		this.svauRemarksAuthorized = svauRemarksAuthorized;
		this.svauStatus = svauStatus;
		this.svauDtCreate = svauDtCreate;
		this.svauUidCreate = svauUidCreate;
		this.svauDtLupd = svauDtLupd;
		this.svauUidLupd = svauUidLupd;
	}

	// Override Methods
	///////////////////
	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * 
	 */
	@Override
	public int compareTo(CkSvcAuth o) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.vcc.camelone.common.COAbstractEntity#init()
	 * 
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	// Properties
	/////////////
	/**
	 * @return the svauId
	 */
	public String getSvauId() {
		return svauId;
	}

	/**
	 * @param svauId the svauId to set
	 */
	public void setSvauId(String svauId) {
		this.svauId = svauId;
	}

	/**
	 * @return the tCkMstAuthState
	 */
	public CkMstAuthState getTCkMstAuthState() {
		return TCkMstAuthState;
	}

	/**
	 * @param tCkMstAuthState the tCkMstAuthState to set
	 */
	public void setTCkMstAuthState(CkMstAuthState tCkMstAuthState) {
		TCkMstAuthState = tCkMstAuthState;
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
	 * @return the tCoreAccnBySvauAccnService
	 */
	public CoreAccn getTCoreAccnBySvauAccnService() {
		return TCoreAccnBySvauAccnService;
	}

	/**
	 * @param tCoreAccnBySvauAccnService the tCoreAccnBySvauAccnService to set
	 */
	public void setTCoreAccnBySvauAccnService(CoreAccn tCoreAccnBySvauAccnService) {
		TCoreAccnBySvauAccnService = tCoreAccnBySvauAccnService;
	}

	/**
	 * @return the tCoreAccnBySvauAccnAuthorizer
	 */
	public CoreAccn getTCoreAccnBySvauAccnAuthorizer() {
		return TCoreAccnBySvauAccnAuthorizer;
	}

	/**
	 * @param tCoreAccnBySvauAccnAuthorizer the tCoreAccnBySvauAccnAuthorizer to set
	 */
	public void setTCoreAccnBySvauAccnAuthorizer(CoreAccn tCoreAccnBySvauAccnAuthorizer) {
		TCoreAccnBySvauAccnAuthorizer = tCoreAccnBySvauAccnAuthorizer;
	}

	/**
	 * @return the tCoreAccnBySvauAccnAuthorized
	 */
	public CoreAccn getTCoreAccnBySvauAccnAuthorized() {
		return TCoreAccnBySvauAccnAuthorized;
	}

	/**
	 * @param tCoreAccnBySvauAccnAuthorized the tCoreAccnBySvauAccnAuthorized to set
	 */
	public void setTCoreAccnBySvauAccnAuthorized(CoreAccn tCoreAccnBySvauAccnAuthorized) {
		TCoreAccnBySvauAccnAuthorized = tCoreAccnBySvauAccnAuthorized;
	}

	/**
	 * @return the tCoreUsrBySvauUsrAuthorizer
	 */
	public CoreUsr getTCoreUsrBySvauUsrAuthorizer() {
		return TCoreUsrBySvauUsrAuthorizer;
	}

	/**
	 * @param tCoreUsrBySvauUsrAuthorizer the tCoreUsrBySvauUsrAuthorizer to set
	 */
	public void setTCoreUsrBySvauUsrAuthorizer(CoreUsr tCoreUsrBySvauUsrAuthorizer) {
		TCoreUsrBySvauUsrAuthorizer = tCoreUsrBySvauUsrAuthorizer;
	}

	/**
	 * @return the tCoreUsrBySvauUsrAuthorized
	 */
	public CoreUsr getTCoreUsrBySvauUsrAuthorized() {
		return TCoreUsrBySvauUsrAuthorized;
	}

	/**
	 * @param tCoreUsrBySvauUsrAuthorized the tCoreUsrBySvauUsrAuthorized to set
	 */
	public void setTCoreUsrBySvauUsrAuthorized(CoreUsr tCoreUsrBySvauUsrAuthorized) {
		TCoreUsrBySvauUsrAuthorized = tCoreUsrBySvauUsrAuthorized;
	}

	/**
	 * @return the svauPositionAuthorizer
	 */
	public String getSvauPositionAuthorizer() {
		return svauPositionAuthorizer;
	}

	/**
	 * @param svauPositionAuthorizer the svauPositionAuthorizer to set
	 */
	public void setSvauPositionAuthorizer(String svauPositionAuthorizer) {
		this.svauPositionAuthorizer = svauPositionAuthorizer;
	}

	/**
	 * @return the svauPositionAuthorized
	 */
	public String getSvauPositionAuthorized() {
		return svauPositionAuthorized;
	}

	/**
	 * @param svauPositionAuthorized the svauPositionAuthorized to set
	 */
	public void setSvauPositionAuthorized(String svauPositionAuthorized) {
		this.svauPositionAuthorized = svauPositionAuthorized;
	}

	/**
	 * @return the svauDtServiceStart
	 */
	public Date getSvauDtServiceStart() {
		return svauDtServiceStart;
	}

	/**
	 * @param svauDtServiceStart the svauDtServiceStart to set
	 */
	public void setSvauDtServiceStart(Date svauDtServiceStart) {
		this.svauDtServiceStart = svauDtServiceStart;
	}

	/**
	 * @return the svauDtServiceValid
	 */
	public Date getSvauDtServiceValid() {
		return svauDtServiceValid;
	}

	/**
	 * @param svauDtServiceValid the svauDtServiceValid to set
	 */
	public void setSvauDtServiceValid(Date svauDtServiceValid) {
		this.svauDtServiceValid = svauDtServiceValid;
	}

	/**
	 * @return the svauRemarksAuthorizer
	 */
	public String getSvauRemarksAuthorizer() {
		return svauRemarksAuthorizer;
	}

	/**
	 * @param svauRemarksAuthorizer the svauRemarksAuthorizer to set
	 */
	public void setSvauRemarksAuthorizer(String svauRemarksAuthorizer) {
		this.svauRemarksAuthorizer = svauRemarksAuthorizer;
	}

	/**
	 * @return the svauRemarksAuthorized
	 */
	public String getSvauRemarksAuthorized() {
		return svauRemarksAuthorized;
	}

	/**
	 * @param svauRemarksAuthorized the svauRemarksAuthorized to set
	 */
	public void setSvauRemarksAuthorized(String svauRemarksAuthorized) {
		this.svauRemarksAuthorized = svauRemarksAuthorized;
	}

	/**
	 * @return the svauStatus
	 */
	public Character getSvauStatus() {
		return svauStatus;
	}

	/**
	 * @param svauStatus the svauStatus to set
	 */
	public void setSvauStatus(Character svauStatus) {
		this.svauStatus = svauStatus;
	}

	/**
	 * @return the svauDtCreate
	 */
	public Date getSvauDtCreate() {
		return svauDtCreate;
	}

	/**
	 * @param svauDtCreate the svauDtCreate to set
	 */
	public void setSvauDtCreate(Date svauDtCreate) {
		this.svauDtCreate = svauDtCreate;
	}

	/**
	 * @return the svauUidCreate
	 */
	public String getSvauUidCreate() {
		return svauUidCreate;
	}

	/**
	 * @param svauUidCreate the svauUidCreate to set
	 */
	public void setSvauUidCreate(String svauUidCreate) {
		this.svauUidCreate = svauUidCreate;
	}

	/**
	 * @return the svauDtLupd
	 */
	public Date getSvauDtLupd() {
		return svauDtLupd;
	}

	/**
	 * @param svauDtLupd the svauDtLupd to set
	 */
	public void setSvauDtLupd(Date svauDtLupd) {
		this.svauDtLupd = svauDtLupd;
	}

	/**
	 * @return the svauUidLupd
	 */
	public String getSvauUidLupd() {
		return svauUidLupd;
	}

	/**
	 * @param svauUidLupd the svauUidLupd to set
	 */
	public void setSvauUidLupd(String svauUidLupd) {
		this.svauUidLupd = svauUidLupd;
	}

}
