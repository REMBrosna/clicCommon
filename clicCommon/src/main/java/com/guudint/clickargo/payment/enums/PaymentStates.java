package com.guudint.clickargo.payment.enums;

public enum PaymentStates {
	APP("APPROVED", "APPROVED"),
	CAN("CANCELLED", "CANCELLED"),
    NEW("NEW", "NEW"),
    SUCCESS("SUCCESS", "SUCCESS"),
    PAID("PAID", "PAID"),
    PAYING("PAYING", "PAYING"),
    PARTIAL_FAIL("PARTIAL_FAIL", "PARTIAL_FAIL"),
    VER("VERIFIED", "VERIFIED"),
    VER_BILL("VER_BILL", "VER_BILL"),
    APP_BILL("APP_BILL", "APP_BILL"),
    FAILED("FAILED", "FAIL"),
	TERMINATED("TERMINATED","TERMINATED");
	
    private final String code;
    private final String desc;

    PaymentStates(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
