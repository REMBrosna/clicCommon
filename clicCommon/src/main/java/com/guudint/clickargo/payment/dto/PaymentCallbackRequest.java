package com.guudint.clickargo.payment.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vcc.camelone.common.COAbstractEntity;

/**
 * Payment callback request from {@code clicPay}
 */
public class PaymentCallbackRequest extends COAbstractEntity<PaymentCallbackRequest> {

	private static final long serialVersionUID = 7972341730055849821L;
	
	@JsonProperty("payment_provider")
	private String paymentProvider;

	@JsonProperty("service_code")
	private String serviceCode;

	@JsonProperty("user_ref_no")
	private String userRefNo;

	@JsonProperty("bin_no")
	private String binNo;

	@JsonProperty("bin_title")
	private String binTitle;

	@JsonProperty("va_no")
	private String vaNo;

	@JsonProperty("va_name")
	private String vaName;

	@JsonProperty("bill_amount")
	private BigDecimal billAmount;

	@JsonProperty("pay_amount")
	private BigDecimal payAmount;

	@JsonProperty("pay_account")
	private BigDecimal payAccount;

	@JsonProperty("pay_bank")
	private String payBank;

	@JsonProperty("pay_desc")
	private String payDesc;

	@JsonProperty("pay_refno")
	private String payRefNo;

	@JsonProperty("auth_code")
	private String authCode;

	public String getPaymentProvider() {
		return paymentProvider;
	}

	public void setPaymentProvider(String paymentProvider) {
		this.paymentProvider = paymentProvider;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getUserRefNo() {
		return userRefNo;
	}

	public void setUserRefNo(String userRefNo) {
		this.userRefNo = userRefNo;
	}

	public String getBinNo() {
		return binNo;
	}

	public void setBinNo(String binNo) {
		this.binNo = binNo;
	}

	public String getBinTitle() {
		return binTitle;
	}

	public void setBinTitle(String binTitle) {
		this.binTitle = binTitle;
	}

	public String getVaNo() {
		return vaNo;
	}

	public void setVaNo(String vaNo) {
		this.vaNo = vaNo;
	}

	public String getVaName() {
		return vaName;
	}

	public void setVaName(String vaName) {
		this.vaName = vaName;
	}

	public BigDecimal getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(BigDecimal billAmount) {
		this.billAmount = billAmount;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public BigDecimal getPayAccount() {
		return payAccount;
	}

	public void setPayAccount(BigDecimal payAccount) {
		this.payAccount = payAccount;
	}

	public String getPayBank() {
		return payBank;
	}

	public void setPayBank(String payBank) {
		this.payBank = payBank;
	}

	public String getPayDesc() {
		return payDesc;
	}

	public void setPayDesc(String payDesc) {
		this.payDesc = payDesc;
	}

	public String getPayRefNo() {
		return payRefNo;
	}

	public void setPayRefNo(String payRefNo) {
		this.payRefNo = payRefNo;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	@Override
	public int compareTo(PaymentCallbackRequest arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

}
