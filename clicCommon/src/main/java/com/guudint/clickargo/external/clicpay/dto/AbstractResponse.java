package com.guudint.clickargo.external.clicpay.dto;

import com.vcc.camelone.common.COAbstractEntity;

public abstract class AbstractResponse extends COAbstractEntity<AbstractResponse> {

	private static final long serialVersionUID = 1L;

	private String txnID;
	private String txnServiceRef;
	private String txnNodeRef;

	public String getTxnID() {
		return this.txnID;
	}

	public void setTxnID(String txnID) {
		this.txnID = txnID;
	}

	public String getTxnServiceRef() {
		return this.txnServiceRef;
	}

	public void setTxnServiceRef(String txnServiceRef) {
		this.txnServiceRef = txnServiceRef;
	}

	public String getTxnNodeRef() {
		return this.txnNodeRef;
	}

	public void setTxnNodeRef(String txnNodeRef) {
		this.txnNodeRef = txnNodeRef;
	}

	@Override
	public int compareTo(AbstractResponse o) {
		return 0;
	}

	@Override
	public void init() {
	}

}
