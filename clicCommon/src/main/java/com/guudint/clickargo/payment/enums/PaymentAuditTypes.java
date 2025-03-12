package com.guudint.clickargo.payment.enums;

public enum PaymentAuditTypes {
    PAYMENT_CALLBACK("PY_CB", "PAYMENT CALLBACK"),
    BANK_TRANSFER_CALLBACK("TR_CB", "BANK TRANSFER CALLBACK"),
    BANK_TRANSFER_REQUEST("TR_REQ", "BANK TRANSFER REQUEST"),
    PAYMENT_VIA_VA("PY_VA", "PAYMENT VIA STATIC VA"),
    VA_CALLBACK("VA_CB", "VA CALLBACK"),
    CANCEL_PAYMENT_REQUEST("CP_REQ", "CANCEL PAYMENT REQUEST"),
    VA_REQUEST("VA_REQ", "VA REQUEST"),
    ;
    private final String id;
    private final String desc;

    PaymentAuditTypes(String id, String desc) {
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
