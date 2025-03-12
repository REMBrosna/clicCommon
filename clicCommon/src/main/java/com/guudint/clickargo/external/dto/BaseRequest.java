package com.guudint.clickargo.external.dto;

import com.vcc.camelone.common.COAbstractEntity;

public class BaseRequest extends COAbstractEntity<BaseRequest> {

	private static final long serialVersionUID = 114347572436229823L;
	private String node;
	private String serviceID;
	private String uid;

	public BaseRequest() {
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

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Override
	public int compareTo(BaseRequest o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

}
