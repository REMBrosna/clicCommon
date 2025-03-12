package com.guudint.clickargo.common.enums;

public enum JobActions {

	DELETE("delete"), 
	SUBMIT("submit"),
	REJECT("reject"), 
	CANCEL("cancel"), 
	CONFIRM("confirm"),
	PAY("pay"), 
	PAID("paid"),
	COMPLETE("complete"), 
	VERIFY_DOCS("verify_docs"), 
	REJECT_DOCS("reject_docs"), 
	VERIFY_RETURNED_DOCS("verify_returned_docs"), 
	VERIFY_PAYMENT("verify_payment"),
	VERIFY("verify"),
	APPROVE("approve"),
	CLONE("clone"), 
	WITHDRAW("withdraw"), 
	ACCEPT("accept"),
	ASSIGN("assign"),
	START("start"),
	STOP("stop"),
	BILLJOB("billjob"),
	VERIFY_BILL("verify_bill"),
	ACKNOWLEDGE_BILL("acknowledge_bill"),
	APPROVE_BILL("approve_bill"),
	REJECT_BILL("reject_bill"),
	ACTIVATE("activate"),
	DEACTIVATE("deactivate"),
	SUSPEND("suspend"),
	TERMINATE("terminate");

	public String desc;

	JobActions(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
