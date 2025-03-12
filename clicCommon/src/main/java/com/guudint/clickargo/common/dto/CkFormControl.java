package com.guudint.clickargo.common.dto;

import java.util.Date;

import com.guudint.clickargo.common.model.TCkFormControl;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.common.dto.AbstractDTO;
import com.vcc.camelone.core.dto.CoreApps;
import com.vcc.camelone.master.dto.MstAccnType;

public class CkFormControl extends AbstractDTO<CkFormControl, TCkFormControl> {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = 5162367349172192778L;

	// Attributes
	/////////////
	private String ctrlId;
	private CkMstEntityState TCkMstEntityState;
	private CkMstEntityType TCkMstEntityType;
	private CkMstFormAction TCkMstFormAction;
	private CkMstFormType TCkMstFormType;
	private CoreAccn TCoreAccn;
	private CoreApps TCoreApps;
	private MstAccnType TMstAccnType;
	private String ctrlUsrRole;
	private String ctrlFormActionMenu;
	private char ctrlStatus;
	private Date ctrlDtCreate;
	private String ctrlUidCreate;
	private Date ctrlDtLupd;
	private String ctrlUidLupd;

	// Constructors
	///////////////
	public CkFormControl() {
	}

	/**
	 * @param entity
	 */
	public CkFormControl(TCkFormControl entity) {
		super(entity);
	}

	/**
	 * @param ctrlId
	 * @param TCkMstEntityState
	 * @param TCkMstEntityType
	 * @param TCkMstFormAction
	 * @param TCkMstFormType
	 * @param TMstAccnType
	 * @param ctrlUsrRole
	 * @param ctrlStatus
	 */
	public CkFormControl(String ctrlId, CkMstEntityState TCkMstEntityState, CkMstEntityType TCkMstEntityType,
			CkMstFormAction TCkMstFormAction, CkMstFormType TCkMstFormType, MstAccnType TMstAccnType,
			String ctrlUsrRole, char ctrlStatus) {
		this.ctrlId = ctrlId;
		this.TCkMstEntityState = TCkMstEntityState;
		this.TCkMstEntityType = TCkMstEntityType;
		this.TCkMstFormAction = TCkMstFormAction;
		this.TCkMstFormType = TCkMstFormType;
		this.TMstAccnType = TMstAccnType;
		this.ctrlUsrRole = ctrlUsrRole;
		this.ctrlStatus = ctrlStatus;
	}

	/**
	 * @param ctrlId
	 * @param TCkMstEntityState
	 * @param TCkMstEntityType
	 * @param TCkMstFormAction
	 * @param TCkMstFormType
	 * @param TCoreAccn
	 * @param TMstAccnType
	 * @param ctrlUsrRole
	 * @param ctrlFormActionMenu
	 * @param ctrlStatus
	 * @param ctrlDtCreate
	 * @param ctrlUidCreate
	 * @param ctrlDtLupd
	 * @param ctrlUidLupd
	 */
	public CkFormControl(String ctrlId, CkMstEntityState TCkMstEntityState, CkMstEntityType TCkMstEntityType,
			CkMstFormAction TCkMstFormAction, CkMstFormType TCkMstFormType, CoreAccn TCoreAccn,
			MstAccnType TMstAccnType, String ctrlUsrRole, String ctrlFormActionMenu, char ctrlStatus, Date ctrlDtCreate,
			String ctrlUidCreate, Date ctrlDtLupd, String ctrlUidLupd) {
		this.ctrlId = ctrlId;
		this.TCkMstEntityState = TCkMstEntityState;
		this.TCkMstEntityType = TCkMstEntityType;
		this.TCkMstFormAction = TCkMstFormAction;
		this.TCkMstFormType = TCkMstFormType;
		this.TCoreAccn = TCoreAccn;
		this.TMstAccnType = TMstAccnType;
		this.ctrlUsrRole = ctrlUsrRole;
		this.ctrlFormActionMenu = ctrlFormActionMenu;
		this.ctrlStatus = ctrlStatus;
		this.ctrlDtCreate = ctrlDtCreate;
		this.ctrlUidCreate = ctrlUidCreate;
		this.ctrlDtLupd = ctrlDtLupd;
		this.ctrlUidLupd = ctrlUidLupd;
	}

	// Override Methods
	///////////////////
	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * 
	 */
	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * 
	 */
	@Override
	public int compareTo(CkFormControl o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	// Properties
	/////////////
	/**
	 * @return the ctrlId
	 */
	public String getCtrlId() {
		return ctrlId;
	}

	/**
	 * @param ctrlId the ctrlId to set
	 */
	public void setCtrlId(String ctrlId) {
		this.ctrlId = ctrlId;
	}

	/**
	 * @return the tCkMstEntityState
	 */
	public CkMstEntityState getTCkMstEntityState() {
		return TCkMstEntityState;
	}

	/**
	 * @param tCkMstEntityState the tCkMstEntityState to set
	 */
	public void setTCkMstEntityState(CkMstEntityState tCkMstEntityState) {
		TCkMstEntityState = tCkMstEntityState;
	}

	/**
	 * @return the tCkMstEntityType
	 */
	public CkMstEntityType getTCkMstEntityType() {
		return TCkMstEntityType;
	}

	/**
	 * @param tCkMstEntityType the tCkMstEntityType to set
	 */
	public void setTCkMstEntityType(CkMstEntityType tCkMstEntityType) {
		TCkMstEntityType = tCkMstEntityType;
	}

	/**
	 * @return the tCkMstFormAction
	 */
	public CkMstFormAction getTCkMstFormAction() {
		return TCkMstFormAction;
	}

	/**
	 * @param tCkMstFormAction the tCkMstFormAction to set
	 */
	public void setTCkMstFormAction(CkMstFormAction tCkMstFormAction) {
		TCkMstFormAction = tCkMstFormAction;
	}

	/**
	 * @return the tCkMstFormType
	 */
	public CkMstFormType getTCkMstFormType() {
		return TCkMstFormType;
	}

	/**
	 * @param tCkMstFormType the tCkMstFormType to set
	 */
	public void setTCkMstFormType(CkMstFormType tCkMstFormType) {
		TCkMstFormType = tCkMstFormType;
	}

	/**
	 * @return the tCoreAccn
	 */
	public CoreAccn getTCoreAccn() {
		return TCoreAccn;
	}

	/**
	 * @param tCoreAccn the tCoreAccn to set
	 */
	public void setTCoreAccn(CoreAccn tCoreAccn) {
		TCoreAccn = tCoreAccn;
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
	 * @return the ctrlUsrRole
	 */
	public String getCtrlUsrRole() {
		return ctrlUsrRole;
	}

	/**
	 * @param ctrlUsrRole the ctrlUsrRole to set
	 */
	public void setCtrlUsrRole(String ctrlUsrRole) {
		this.ctrlUsrRole = ctrlUsrRole;
	}

	/**
	 * @return the ctrlFormActionMenu
	 */
	public String getCtrlFormActionMenu() {
		return ctrlFormActionMenu;
	}

	/**
	 * @param ctrlFormActionMenu the ctrlFormActionMenu to set
	 */
	public void setCtrlFormActionMenu(String ctrlFormActionMenu) {
		this.ctrlFormActionMenu = ctrlFormActionMenu;
	}

	/**
	 * @return the ctrlStatus
	 */
	public char getCtrlStatus() {
		return ctrlStatus;
	}

	/**
	 * @param ctrlStatus the ctrlStatus to set
	 */
	public void setCtrlStatus(char ctrlStatus) {
		this.ctrlStatus = ctrlStatus;
	}

	/**
	 * @return the ctrlDtCreate
	 */
	public Date getCtrlDtCreate() {
		return ctrlDtCreate;
	}

	/**
	 * @param ctrlDtCreate the ctrlDtCreate to set
	 */
	public void setCtrlDtCreate(Date ctrlDtCreate) {
		this.ctrlDtCreate = ctrlDtCreate;
	}

	/**
	 * @return the ctrlUidCreate
	 */
	public String getCtrlUidCreate() {
		return ctrlUidCreate;
	}

	/**
	 * @param ctrlUidCreate the ctrlUidCreate to set
	 */
	public void setCtrlUidCreate(String ctrlUidCreate) {
		this.ctrlUidCreate = ctrlUidCreate;
	}

	/**
	 * @return the ctrlDtLupd
	 */
	public Date getCtrlDtLupd() {
		return ctrlDtLupd;
	}

	/**
	 * @param ctrlDtLupd the ctrlDtLupd to set
	 */
	public void setCtrlDtLupd(Date ctrlDtLupd) {
		this.ctrlDtLupd = ctrlDtLupd;
	}

	/**
	 * @return the ctrlUidLupd
	 */
	public String getCtrlUidLupd() {
		return ctrlUidLupd;
	}

	/**
	 * @param ctrlUidLupd the ctrlUidLupd to set
	 */
	public void setCtrlUidLupd(String ctrlUidLupd) {
		this.ctrlUidLupd = ctrlUidLupd;
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

}
