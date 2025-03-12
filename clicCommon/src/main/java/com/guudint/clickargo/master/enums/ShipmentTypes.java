package com.guudint.clickargo.master.enums;

public enum ShipmentTypes {

	IMPORT("IMPORT", "IMPORT"), EXPORT("EXPORT", "EXPORT"), DOMESTIC("DOMESTIC","DOMESTIC");

	private final String id;
	private final String desc;

	ShipmentTypes(String id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	public String getId() {
		return id;
	}

	public String getDesc() {
		return this.desc;
	}
	
	public static ShipmentTypes getByName(String id) {
		for (ShipmentTypes js : ShipmentTypes.values()) {
			if (js.name().equalsIgnoreCase(id))
				return js;
		}

		return null;
	}
}
