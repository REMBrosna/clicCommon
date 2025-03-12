package com.guudint.clickargo.payment.enums;

public enum PaymentTypes {

    BANK_TRANSFER("BNKTF", "BANK_TRANSFER"),
    CREDIT_LINE("CRL", "CREDIT_LINE"),
    VIRTUAL_ACCOUNT("VA", "VIRTUAL_ACCOUNT"),
    OPM("OPM", "Other_People_Money"),
    ;
    private final String id;
    private final String desc;

    PaymentTypes(String id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }
}
