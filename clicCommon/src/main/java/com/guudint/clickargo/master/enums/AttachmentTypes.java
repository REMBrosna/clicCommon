package com.guudint.clickargo.master.enums;

public enum AttachmentTypes {

	
	BILL_OF_LADING("BL", "BILL OF LADING"),
	POWER_OF_AUTHORITY("POA", "POWER OF AUTHORITY"),
	POA_AUTH("POA", "POWER OF AUTHORITY"),
	POA_UAUTH("POA", "POWER OF AUTHORITY UN-AUTHORIZED"),
	LETTER_OF_ASSIGNMENT("LOA", "LETTER OF ASSIGNMENT"),
	CONTAINER_GUARANTEE("CGA", "CONTAINER GUARANTEE"),
	PROFORMA_INVOICE("PFI", "PROFORMA INVOICE"),
	E_TAX_INVOICE("ETI", "E TAX INVOICE"), // a.k.a e-Faktur
	OTH("OTH", "OTHERS"),
	
	// Create these enums for download attachments
	BL("BL", "BILL OF LADING"),
	POA("POA", "POWER OF AUTHORITY"),
	LOA("LOA", "LETTER OF ASSIGNMENT"),
	CGA("CGA", "CONTAINER GUARANTEE"),
	PFI("PFI", "PROFORMA INVOICE"),
	ETI("ETI", "E TAX INVOICE"),
	
	//For account company logo
	CLO("CLO", "COMPANY LOGO")
	;

	private final String id;
	private final String desc;

	AttachmentTypes(String id, String desc) {
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
