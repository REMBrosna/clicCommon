package com.guudint.clickargo.master.enums;

public enum DoDocAttachState {
    NEW("NEW"),
    OUTSTANDING("OUTSTANDING"), 
    REJECT("REJECT"), 
	VERIFY("VERIFY");

	private final String desc;

	DoDocAttachState(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return this.desc;
	}
}
