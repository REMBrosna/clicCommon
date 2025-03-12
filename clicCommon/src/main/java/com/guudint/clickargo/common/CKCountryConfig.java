package com.guudint.clickargo.common;

import java.io.Serializable;

public class CKCountryConfig implements Serializable {

	private static final long serialVersionUID = 5214092666343194168L;
	private String country;
	private String currency;
	

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
