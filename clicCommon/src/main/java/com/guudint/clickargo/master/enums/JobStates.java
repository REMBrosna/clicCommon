package com.guudint.clickargo.master.enums;

public enum JobStates {

	ACP("ACCEPTED"),
	APP("APPROVED"),
	ASG("ASSIGNED"),
	BILLED("BILLED"),
	CAN("CANCELLED"),
	CON("CONFIRMED"),
	CLM("CLAIMED"),
	COM("COMPLETED"),
	DEL("DELETED"),
	DLV("DELIVERED"),
	DRF("DRAFT"),
	NEW("NEW"),
	ONGOING("ONGOING"),
	PAID("PAID"),
	PENDING("PENDING"),
	PYG("PAYING"),
	PMV("PAYMENT VERIFIED"),
	PROG("IN PROGRESS"),
	REJ("REJECTED"),
	STRTD("STARTED"),
	SUB("SUBMITTED"),
	VER("VERIFIED"),
	VER_BILL("BILLING VERIFIED"),
	APP_BILL("BILLING APPROVED"),
	ACK_BILL("BILLING ACKNOWLEDGE"),
	REJ_BILL("BILLING REJECTED"),
	TERMINATED("TERMINATED"),
	PAUSED("PAUSED");
	
	private String desc;

	private JobStates(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
	
	public static JobStates getByName(String id) {
		for (JobStates js : JobStates.values()) {
			if (js.name().equalsIgnoreCase(id))
				return js;
		}

		return null;
	}

}
