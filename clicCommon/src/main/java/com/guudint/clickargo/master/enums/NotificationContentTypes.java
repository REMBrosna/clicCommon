package com.guudint.clickargo.master.enums;

public enum NotificationContentTypes {

	HTML("CNT_TYPE_HTML");

	private String code;

	private NotificationContentTypes(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
