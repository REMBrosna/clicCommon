package com.guudint.clickargo.common.enums;

/**
 * Enum for {@code T_CK_MST_FORM_TYPES} 
 */
public enum MstFormTypes {

	EDIT("Editable Form"), RETURN("Return Form"), VIEW("View Only Form");

	private String desc;

	private MstFormTypes(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

}
