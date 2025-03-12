package com.guudint.clickargo.master.enums;

public enum EstampMethod {

    AUTH_TOKEN("AUTH TOKEN"),
    REFRESH_TOKEN("REFRESH TOKEN"),
    STAMP_DOCUMENT("STAMP DOCUMENT"),
    STAMP_DOCUMENT_DETAIL("STAMP DOCUMENT DETAIL"),
    STAMP_DOWNLOAD("STAMP DOWNLOAD"),
	;

	private final String id;

	EstampMethod(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}
}
