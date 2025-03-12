package com.guudint.clickargo.common.enums;

/** enums for {@code T_CK_MST_ENTITY_TYPE} */
public enum MstEntityTypes {

	JOB_DOI_CO("JOB_DOI_CO"), 
	JOB_DOI_FF("JOB_DOI_FF"),
	JOB_TRUCK("JOB_TRUCK"),
	JOB_TRUCK_OUT_PAY("JOB_TRUCK_OUT_PAY"),
	TRIP_RATE_TABLE("TRIP_RATE_TABLE"),
	CREDIT_LIMIT("CREDIT_LIMIT"),
	JOB_TERMINATION("JOB_TERMINATION"),
	ACCN_REGISTRATION("ACCN_REGISTRATION"),
	ACCN_SUSPENSION("ACCN_SUSPENSION"),
	ACCN_RESUMPTION("ACCN_RESUMPTION"),
	ACCN_TERMINATION("ACCN_TERMINATION"),
	CONTRACT_REQUEST("CONTRACT_REQUEST"),
	JOB_TRUCK_MOBILE("JOB_TRUCK_MOBILE");

	private String desc;

	private MstEntityTypes(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

}
