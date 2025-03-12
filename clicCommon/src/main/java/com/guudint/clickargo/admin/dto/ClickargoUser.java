package com.guudint.clickargo.admin.dto;

import com.vcc.camelone.ccm.dto.PortalUser;

public class ClickargoUser extends PortalUser {

	private static final long serialVersionUID = 2404733715177348694L;

	private String alternateEmail;
	private String tempPwd;
	private CkUserNotifPref usrNotifProf;
	private boolean isLoggedIn;

	public String getAlternateEmail() {
		return alternateEmail;
	}

	public void setAlternateEmail(String alternateEmail) {
		this.alternateEmail = alternateEmail;
	}

	public String getTempPwd() {
		return tempPwd;
	}

	public void setTempPwd(String tempPwd) {
		this.tempPwd = tempPwd;
	}

	public CkUserNotifPref getUsrNotifProf() {
		return usrNotifProf;
	}

	public void setUsrNotifProf(CkUserNotifPref usrNotifProf) {
		this.usrNotifProf = usrNotifProf;
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

}
