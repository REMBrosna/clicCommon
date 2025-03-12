package com.guudint.clickargo.common.enums;

/**
 * Enum for {@code T_CK_CT_MST_CARGO_TYPE}
 */
public enum CargoTypes {

	GENERAL("GENERAL"), DANGEROUS("DANGEROUS");

	private String desc;

	private CargoTypes(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

}
