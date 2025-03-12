package com.guudint.clickargo.master.enums;

public enum TaskTypes {
    DO_CLAIM_EXPORT("DOCE", "DO_CLAIM_EXPORT"),
    DO_CLAIM_IMPORT("DOCI", "DO CLAIM IMPORT"),
    GATE_IN_PASS("GINP", "GATE IN PASS"),
    GATE_OUT_PASS("GOTP", "GATE OUT PASS"),
    LIFT_OFF("LIFOF", "LIFT OFF"),
    LIFT_ON("LIFON", "LIFT ON"),
    TDS_EXPORT("TDSE", "TDS EXPORT"),
    TDS_IMPORT("TDSI", "TDS IMPORT"),
    TRUCKING_IN_FIRST_MIL("TRKIF", "TRUCKING IN FIRST MIL"),
    TRUCKING_OUT_FIRST_MILE("TRKOF", "TRUCKING OUT FIRST MILE"),
    TRUCKING_OUT_MID_MILE("TRKOM", "TRUCKING OUT MID MILE"),
    ;
    private final String id;
    private final String desc;

    TaskTypes(String id, String desc) {
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
