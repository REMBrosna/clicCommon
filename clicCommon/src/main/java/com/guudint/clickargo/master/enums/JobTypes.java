package com.guudint.clickargo.master.enums;

public enum JobTypes {

	DOCE("DO CLAIM EXPORT"), 
	DOCI("DO CLAIM IMPORT"), 
	GINP("GATE IN PASS"), 
	GOTP("GATE OUT PASS"), 
	LIFOF("LIFT OFF"),
	LIFON("LIFT ON"), 
	TDSE("TDS EXPORT"), 
	TDSI("TDS IMPORT"), 
	TRKI("TRUCKING IN"), 
	TRKO("TRUCKING OUT");

	private String desc;

	private JobTypes(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

}
