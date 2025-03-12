package com.guudint.clickargo.common;

public enum RecordStatus {

	ACTIVE('A'), INACTIVE('I'), DEACTIVATE('D'), SUSPENDED('S'), CLOSED('C'), EXPIRED('E');

	private char code;

	RecordStatus(char code) {
		this.code = code;
	}

	public char getCode() {
		return this.code;
	}
}
