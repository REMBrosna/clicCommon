package com.guudint.clickargo.external.dto;

import java.math.BigDecimal;

public class MakePaymentResponse extends BaseResponse {

	private static final long serialVersionUID = -5628775205068634533L;
	private String status;
	private ResponseData data;

	public MakePaymentResponse() {
		super();
	}

	public MakePaymentResponse(String status) {
		this.status = status;

	}

	public MakePaymentResponse(String status, ErrorData err) {
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

	public class ResponseData {
		String txnID;
		String txnServiceRef;
		String txnNodeRef;
		String va;
		BigDecimal amount;
		String ccy;
		String callback;

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

		public String getCallback() {
			return callback;
		}

		public void setCallback(String callback) {
			this.callback = callback;
		}

	}
}
