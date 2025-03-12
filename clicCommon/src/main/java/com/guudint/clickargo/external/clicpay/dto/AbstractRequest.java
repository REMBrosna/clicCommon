package com.guudint.clickargo.external.clicpay.dto;

import com.vcc.camelone.common.COAbstractEntity;

public abstract class AbstractRequest extends COAbstractEntity<AbstractRequest> {

	private static final long serialVersionUID = 1L;

	private String node;
	private String serviceID;
	private String refID;
	private String callback;

	@Override
	public int compareTo(AbstractRequest o) {
		return 0;
	}

	@Override
	public void init() {

	}

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

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}
}
