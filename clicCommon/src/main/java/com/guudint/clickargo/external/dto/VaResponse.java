package com.guudint.clickargo.external.dto;

public class VaResponse extends BaseResponse {

	private Data data;

	public VaResponse() {

	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	class Data {

		private String txnID;
		private String txnServiceRef;
		private String txnNodeRef;
		private String node;
		private String name;
		private String email;
		private String phone;
		private String va;
		private String ccy;

		public Data() {
		}

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

		public String getNode() {
			return node;
		}

		public void setNode(String node) {
			this.node = node;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getVa() {
			return va;
		}

		public void setVa(String va) {
			this.va = va;
		}

		public String getCcy() {
			return ccy;
		}

		public void setCcy(String ccy) {
			this.ccy = ccy;
		}

	}

}
