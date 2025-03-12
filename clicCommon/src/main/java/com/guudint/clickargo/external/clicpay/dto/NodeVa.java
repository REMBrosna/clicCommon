package com.guudint.clickargo.external.clicpay.dto;

public class NodeVa extends AbstractResponse {

	private static final long serialVersionUID = 1L;

	private String va;
	private String name;
	private String email;
	private String ccy;

	public String getVa() {
		return this.va;
	}

	public void setVa(String va) {
		this.va = va;
	}

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
		return this.ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

}
