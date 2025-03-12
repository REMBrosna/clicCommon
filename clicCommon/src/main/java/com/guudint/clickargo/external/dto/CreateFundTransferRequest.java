package com.guudint.clickargo.external.dto;

import java.math.BigDecimal;

import com.vcc.camelone.common.COAbstractEntity;

public class CreateFundTransferRequest extends COAbstractEntity<CreateFundTransferRequest> {
	private static final long serialVersionUID = -7728292600853637585L;
	String node;
	String serviceID;
	String refID;
	String callBack;
	String senderAccount;
	String senderCardNumber;
	String va;
	BigDecimal amount;
	String beneficiaryAccount;
	String bank;
	String ccy;

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getServiceID() {
		return serviceID;
	}

	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}

	public String getRefID() {
		return refID;
	}

	public void setRefID(String refID) {
		this.refID = refID;
	}

	public String getCallBack() {
		return callBack;
	}

	public void setCallBack(String callBack) {
		this.callBack = callBack;
	}

	public String getSenderAccount() {
		return senderAccount;
	}

	public void setSenderAccount(String senderAccount) {
		this.senderAccount = senderAccount;
	}

	public String getSenderCardNumber() {
		return senderCardNumber;
	}

	public void setSenderCardNumber(String senderCardNumber) {
		this.senderCardNumber = senderCardNumber;
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

	public String getBeneficiaryAccount() {
		return beneficiaryAccount;
	}

	public void setBeneficiaryAccount(String beneficiaryAccount) {
		this.beneficiaryAccount = beneficiaryAccount;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	@Override
	public int compareTo(CreateFundTransferRequest o) {
		return 0;
	}

	@Override
	public void init() {

	}
}
