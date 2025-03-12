package com.guudint.clickargo.master.enums;

public enum AuthServiceStates {

	AUTH("AUTHORIZED"), DRFT("DRAFT"), EXP("EXPIRED"), PENA("PENDING AUTHORIZATION"), SUBM("SUBMITTED");

	private String desc;

	private AuthServiceStates(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

}
