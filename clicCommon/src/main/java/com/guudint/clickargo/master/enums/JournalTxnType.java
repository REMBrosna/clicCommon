package com.guudint.clickargo.master.enums;

public enum JournalTxnType {
    JOB_SUBMIT("JOB_SUBMIT"), 
	//JOB_APPROVE("JOB_APPROVE"),
	JOB_SUBMIT_REIMBURSEMENT("JOB_SUBMIT_REIMBURSEMENT"),
	JOB_CANCEL("JOB_CANCEL"),
	JOB_REJECT("JOB_REJECT"), 
	JOB_PAYMENT("JOB_PAYMENT"),	// CO paid to GLI 
	JOB_PAYMENT_APPROVE("JOB_PAYMENT_APPROVE"), // GLI approved JOB
	JOB_TERMINATION("JOB_TERMINATION"),
	JOB_OPM_ACCEPT("JOB_OPM_ACCEPT");
	

	private final String desc;

	JournalTxnType(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return this.desc;
	}
}
