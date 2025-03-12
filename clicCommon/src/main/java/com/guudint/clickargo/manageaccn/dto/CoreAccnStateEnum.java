package com.guudint.clickargo.manageaccn.dto;

public enum CoreAccnStateEnum {

	NEW('N', "NEW", "NEW"),
	REG_DELETED('D', "DEL", "REG_DELETED"),
	REG_SUBMITTED('R', "SUB", "REG_REQ"),
	REG_REJECTED('X', "REJ", "REG_REJECT"),
	REG_APPROVED('A', "APP", "REG_APPROVE"),
	SUS_SUBMITTED('P', "SUB", "SUS_REQ"),
	SUS_APPROVED('S',"SUS", "SUS_APPROVE"),
	SUS_REJECTED('A', "REJ", "SUS_REJECT"),
	RESUMPTION_SUBMITTED('Q', "SUB", "RESUMPT_REQ"),
	RESUMPTION_APPROVED('A',"APP", "RESUMPT_APPROVE"),
	RESUMPTION_REJECTED('S', "REJ", "RESUMPT_REJECT"),
	TER_SUBMITTED('V', "SUB", "TERM_REQ"),
	TER_APPROVED('T', "TERMINATED", "TERM_APPROVE"),
	TER_REJECTED('A', "REJ", "TERM_REJECT");
	
	char code;
	String altCode;
	String desc;

	CoreAccnStateEnum(char code, String altCode, String desc) {
		this.code = code;
		this.altCode = altCode;
		this.desc = desc;
	}

	public char getCode() {
		return this.code;
	}
	
	public String getAltCode() {
		return this.altCode;
	}

	public String getDesc() {
		return desc;
	}

	public static char getStateByAltCodeAndDesc(String altCode, String desc) {

		for (CoreAccnStateEnum e : CoreAccnStateEnum.values()) {
			if (e.getAltCode().equalsIgnoreCase(altCode)
					&& e.getDesc().contains(desc))
				return e.getCode();
		}

		return '\0';
	}
	
	public static String getAltCodeByState(char state) {
		for (CoreAccnStateEnum e : CoreAccnStateEnum.values()) {
			if (e.getCode() == state)
				return e.getAltCode();
		}

		return null;
	}
	
	public static String getDescByState(char state) {
		for (CoreAccnStateEnum e : CoreAccnStateEnum.values()) {
			if (e.getCode() == state)
				return e.getDesc();
		}

		return null;
	}
	
	public static String getDescByStateAndAltCode(char state, String altCode) {
		for (CoreAccnStateEnum e : CoreAccnStateEnum.values()) {
			if (e.getCode() == state 
					&& e.getDesc().contains(altCode))
				return e.getDesc();
		}

		return null;
	}
	public static CoreAccnStateEnum getByState(char state) {
		for (CoreAccnStateEnum e : CoreAccnStateEnum.values()) {
			if (e.getCode() == state)
				return e;
		}

		return null;
	}

}
