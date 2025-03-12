package com.guudint.clickargo.external.dto;

import java.math.BigDecimal;

public class MakePaymentRequest extends BaseRequest {

	private static final long serialVersionUID = -1220702441430492340L;
	private String refID;
	private String callback;
	private String va;
	private BigDecimal amount;
	private String ccy;

	public MakePaymentRequest() {
	}

	public String getRefID() {
		return refID;
	}

	public void setRefID(String refID) {
		this.refID = refID;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public String getVa() {
		return va;
	}

	public void setVa(String va) {
		this.va = va;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

}
