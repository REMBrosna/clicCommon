package com.guudint.clickargo.master.enums;

public enum NotificationChannelTypes {

	HTML("CHN_TYPE_EMAIL");

	private String code;

	private NotificationChannelTypes(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
