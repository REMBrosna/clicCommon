package com.guudint.clickargo.external.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateFundTransferResponse extends BaseResponse {

	private static final long serialVersionUID = 5541478860162753199L;
	private String status;
	@JsonProperty("data")
	private ResponseData data;

	public CreateFundTransferResponse() {

	}
	
	public CreateFundTransferResponse(String status) {
		this.status = status;
	}

	public CreateFundTransferResponse(String status, ErrorData err) {
		super(status, err);

	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ResponseData getData() {
		return data;
	}

	public void setData(ResponseData data) {
		this.data = data;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public class ResponseData {
		String txnID;
		String txnServiceRef;
		String txnNodeRef;
		String va;
		String account;
		BigDecimal amount;
		String ccy;

		public String getTxnID() {
			return txnID;
		}

		public void setTxnID(String txnID) {
			this.txnID = txnID;
		}

		public String getTxnServiceRef() {
			return txnServiceRef;
		}

		public void setTxnServiceRef(String txnServiceRef) {
			this.txnServiceRef = txnServiceRef;
		}

		public String getTxnNodeRef() {
			return txnNodeRef;
		}

		public void setTxnNodeRef(String txnNodeRef) {
			this.txnNodeRef = txnNodeRef;
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

		public String getAccount() {
			return account;
		}

		public void setAccount(String account) {
			this.account = account;
		}

	}
}
