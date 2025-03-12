package com.guudint.clickargo.sage.dto;

public enum CkCtMstSageIntStateEnum {

	SUBMITTED("Submitted"), 
	APPROVE("Approve"), 
	ERROR("Error"),
	COMPLETE("Complete");
	
	private String desc;

	private CkCtMstSageIntStateEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}
