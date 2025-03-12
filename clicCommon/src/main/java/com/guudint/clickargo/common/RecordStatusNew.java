package com.guudint.clickargo.common;

public enum RecordStatusNew {

	ACTIVE('A'), DEACTIVE('I'), DELETE('D');

	private char code;

	RecordStatusNew(char code) {
		this.code = code;
	}

	public char getCode() {
		return this.code;
	}
}
