package com.guudint.clickargo.external.clicpay.dto;

public class RequestVA extends AbstractRequest {

	private static final long serialVersionUID = 1L;

	private String name;
	private String email;
	private String ccy;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}
}
