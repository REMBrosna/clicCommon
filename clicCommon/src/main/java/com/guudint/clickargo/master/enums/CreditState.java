package com.guudint.clickargo.master.enums;

public enum CreditState {
	DRAFT("DRAFT"),
    VERIFY("VERIFY"), 
    APPROVED("APPROVED"), 
	REJECTED("REJECTED"),
	SUSPENDED("SUSPENDED"),
	UNSUSPENDED("UNSUSPENDED");

	private final String desc;

	CreditState(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return this.desc;
	}
}
