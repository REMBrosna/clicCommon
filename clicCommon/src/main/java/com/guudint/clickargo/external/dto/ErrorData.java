package com.guudint.clickargo.external.dto;

import com.vcc.camelone.common.COAbstractEntity;

public class ErrorData extends COAbstractEntity<ErrorData> {
	int code;
	String msg;
	String stack;

	public ErrorData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ErrorData(int code, String msg, String stack) {
		super();
		this.code = code;
		this.msg = msg;
		this.stack = stack;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getStack() {
		return stack;
	}

	public void setStack(String stack) {
		this.stack = stack;
	}

	@Override
	public int compareTo(ErrorData o) {
		return 0;
	}

	@Override
	public void init() {
	}

}
