package com.guudint.clickargo.admin.dto;

public enum ClickargoNotifTemplates {
	RESET_PASSWORD("CK_NTL_0001"),
	RESET_PASSWORD_MANAGEUSER("CK_NTL_0002"),
	NEW_USER_CREDENTIALS("CK_NTL_0003");

	private String id;

	private ClickargoNotifTemplates(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}
}
