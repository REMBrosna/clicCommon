package com.guudint.clickargo.external.dto;

public class CancelPaymentRequest extends BaseRequest {

	private static final long serialVersionUID = -5851753961149330767L;
	private String refID;
	private String callback;
	private String va;
	private String ref;

	public CancelPaymentRequest() {
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

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

}
