package com.guudint.clickargo.master.enums;

public enum AccountTypes {
	
	ACC_TYPE_CAMELONE("ACC_TYPE_CAMELONE"), 
	ACC_TYPE_SL("ACC_TYPE_SL"),
	ACC_TYPE_CK("ACC_TYPE_CK"),
	ACC_TYPE_CO("ACC_TYPE_CO"), 
	ACC_TYPE_FF("ACC_TYPE_FF"),
	ACC_TYPE_FF_CO("ACC_TYPE_FF_CO"), 
	ACC_TYPE_TO("ACC_TYPE_TO"),
	ACC_TYPE_SP("ACC_TYPE_SP"),
	ACC_TYPE_TO_WJ("ACC_TYPE_TO_WJ");

	private final String desc;

	AccountTypes(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return this.desc;
	}
}
