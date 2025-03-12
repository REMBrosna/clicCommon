package com.guudint.clickargo.common.enums;

/**
 * Enum for {@code T_CK_MST_ENTITY_STATES} 
 */
public enum MstEntityStates {
	ACP("Accepted"),
	APP("Approved"),
	ASG("Assigned"),
	CAN("Cancelled"), 
	COM("Completed"), 
	CON("Confirmed"), 
	DEL("Deleted"),
	DLV("Delivered"),
	DRF("Draft"), 
	BILLED("Billed"),
	ISF("DO Issued Failed"), 
	ISS("DO Issued Success"),
	NEW("New"), 
	ONGOING("Ongoing"),
	PAID("Paid"), 
	PMV("Payment Verified"), 
	PROG("In Progress"), 
	REJ("Rejected"), 
	SUB("Submitted"), 
	PYG("Paying"),
	VER("Verified"),
	FAIL("Failed"),
	VER_BILL("Billing Verified"),
	APP_BILL("Billing Approved"),
	REJ_BILL("Billing Rejected"),
	ACK_BILL("Billing Verified"),
	TRIP_ACTIVE("Trip Actived"),
	TRIP_DELIVER("Trip Delivered"),
	TRIP_DROP_OFF("Trip Drop Off"),
	TRIP_PICKEDUP("Trip Picked Up"),
	PAUSED("Paused"),
	EX("Exported");
	

	private String desc;

	private MstEntityStates(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

}
