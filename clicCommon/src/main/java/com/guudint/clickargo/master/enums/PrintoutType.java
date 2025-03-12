package com.guudint.clickargo.master.enums;

public enum PrintoutType {
    
    PLF("PLATRFORMFEE INVOCE"),
    INV("INVOICE"),
    FTR("FAKTUR"),
    DO("DELIVERY ORDER");

    private String desc;

    PrintoutType(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return this.desc;
	}
}
