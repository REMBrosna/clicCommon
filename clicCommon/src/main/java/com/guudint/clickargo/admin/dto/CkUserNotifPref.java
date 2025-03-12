package com.guudint.clickargo.admin.dto;

import com.vcc.camelone.common.COAbstractEntity;

public class CkUserNotifPref extends COAbstractEntity<CkUserNotifPref> {

	private static final long serialVersionUID = -1825736092119830855L;
	private String email;
	private String sms;
	private String telegram;

	public CkUserNotifPref() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSms() {
		return sms;
	}

	public void setSms(String sms) {
		this.sms = sms;
	}

	public String getTelegram() {
		return telegram;
	}

	public void setTelegram(String telegram) {
		this.telegram = telegram;
	}

	@Override
	public int compareTo(CkUserNotifPref arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

}
