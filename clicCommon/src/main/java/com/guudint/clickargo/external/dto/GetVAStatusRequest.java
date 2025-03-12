package com.guudint.clickargo.external.dto;

public class GetVAStatusRequest extends BaseRequest {
	private String appId;
	private String appRefNum;
	private String userId;
	private String paymentProvider;
	private String trxId;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppRefNum() {
		return appRefNum;
	}

	public void setAppRefNum(String appRefNum) {
		this.appRefNum = appRefNum;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPaymentProvider() {
		return paymentProvider;
	}

	public void setPaymentProvider(String paymentProvider) {
		this.paymentProvider = paymentProvider;
	}

	public String getTrxId() {
		return trxId;
	}

	public void setTrxId(String trxId) {
		this.trxId = trxId;
	}
}
