package com.guudint.clickargo.master.enums;

public enum BlStates {

	ASSIGNED("ASSIGNED"),
	CANCELLED("CANCELLED"),
	CLAIMED("CLAIMED"),
	CONFIRMED("CONFIRMED"),
	NEW("NEW"),
	SUBMITTED("SUBMITTED"),
	REJECTED("REJECTED"),
	INVALID("INVALID");
	
	private String desc;

	private BlStates(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

}
