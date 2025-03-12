package com.guudint.clickargo.master.enums;

public enum TaskStates {
    COMPLETED("COM", "COMPLETED"),
    DELETED("DEL", "DELETED"),
    NEW("NEW", "NEW"),
    DO_ISSUED_FAILED("ISF", "DO ISSUED FAILED'"),
    DO_ISSUED_SUCCESS("ISS", "DO ISSUED SUCCESS"),
    SUBMITTED("SUB", "SUBMITTED"),
    ;
    private final String id;
    private final String desc;

    TaskStates(String id, String desc) {
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
