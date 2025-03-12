package com.guudint.clickargo.master.enums;

public enum CreditRequestState {

    APP("APPROVED", "APPROVED"),
    DEL("DELETED", "DELETED"),
    NEW("NEW", "NEW"),
    REJ("REJECTED", "REJECTED"),
    SUB("SUBMITTED", "SUBMITTED"),
    ACT("ACTIVED","ACTIVED");

    private final String name, desc;

    private CreditRequestState(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

}
