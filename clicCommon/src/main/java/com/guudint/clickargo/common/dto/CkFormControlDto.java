package com.guudint.clickargo.common.dto;

import java.util.Date;

import com.guudint.clickargo.common.model.TCkFormControl;
import com.vcc.camelone.common.dto.AbstractDTO;

/**
 * Simplified {@code CkFormControl} for easier processing in the frontend.
 */
public class CkFormControlDto extends AbstractDTO<CkFormControlDto, TCkFormControl> {

	private static final long serialVersionUID = -399888851066997829L;
	private String ctrlId;
	private String ctrlEntityState;
	private String ctrlEntityType;
	private String ctrlAction;
	private String ctrlViewType;
	private String ctrlAccn;
	private String ctrlAccnType;
	private String ctrlUsrRole;
	private String ctrlFormActionMenu;
	private char ctrlStatus;
	private Date ctrlDtCreate;
	private String ctrlUidCreate;
	private Date ctrlDtLupd;
	private String ctrlUidLupd;

	public String getCtrlId() {
		return ctrlId;
	}

	public void setCtrlId(String ctrlId) {
		this.ctrlId = ctrlId;
	}

	public String getCtrlEntityState() {
		return ctrlEntityState;
	}

	public void setCtrlEntityState(String ctrlEntityState) {
		this.ctrlEntityState = ctrlEntityState;
	}

	public String getCtrlEntityType() {
		return ctrlEntityType;
	}

	public void setCtrlEntityType(String ctrlEntityType) {
		this.ctrlEntityType = ctrlEntityType;
	}

	public String getCtrlAction() {
		return ctrlAction;
	}

	public void setCtrlAction(String ctrlAction) {
		this.ctrlAction = ctrlAction;
	}

	public String getCtrlViewType() {
		return ctrlViewType;
	}

	public void setCtrlViewType(String ctrlViewType) {
		this.ctrlViewType = ctrlViewType;
	}

	public String getCtrlAccn() {
		return ctrlAccn;
	}

	public void setCtrlAccn(String ctrlAccn) {
		this.ctrlAccn = ctrlAccn;
	}

	public String getCtrlAccnType() {
		return ctrlAccnType;
	}

	public void setCtrlAccnType(String ctrlAccnType) {
		this.ctrlAccnType = ctrlAccnType;
	}

	public String getCtrlUsrRole() {
		return ctrlUsrRole;
	}

	public void setCtrlUsrRole(String ctrlUsrRole) {
		this.ctrlUsrRole = ctrlUsrRole;
	}

	public String getCtrlFormActionMenu() {
		return ctrlFormActionMenu;
	}

	public void setCtrlFormActionMenu(String ctrlFormActionMenu) {
		this.ctrlFormActionMenu = ctrlFormActionMenu;
	}

	public char getCtrlStatus() {
		return ctrlStatus;
	}

	public void setCtrlStatus(char ctrlStatus) {
		this.ctrlStatus = ctrlStatus;
	}

	public Date getCtrlDtCreate() {
		return ctrlDtCreate;
	}

	public void setCtrlDtCreate(Date ctrlDtCreate) {
		this.ctrlDtCreate = ctrlDtCreate;
	}

	public String getCtrlUidCreate() {
		return ctrlUidCreate;
	}

	public void setCtrlUidCreate(String ctrlUidCreate) {
		this.ctrlUidCreate = ctrlUidCreate;
	}

	public Date getCtrlDtLupd() {
		return ctrlDtLupd;
	}

	public void setCtrlDtLupd(Date ctrlDtLupd) {
		this.ctrlDtLupd = ctrlDtLupd;
	}

	public String getCtrlUidLupd() {
		return ctrlUidLupd;
	}

	public void setCtrlUidLupd(String ctrlUidLupd) {
		this.ctrlUidLupd = ctrlUidLupd;
	}

	@Override
	public int compareTo(CkFormControlDto o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

}
