package com.guudint.clickargo.manageaccn.service;

import com.guudint.clickargo.master.enums.FormActions;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.ccm.model.TCoreAccn;

public class CkCoreAccn extends CoreAccn {

	private static final long serialVersionUID = 7495538395021393110L;

	private String history;
	private String remarks;
	private FormActions action;

	public CkCoreAccn() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CkCoreAccn(TCoreAccn entity) {
		super(entity);
		// TODO Auto-generated constructor stub
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public FormActions getAction() {
		return action;
	}

	public void setAction(FormActions action) {
		this.action = action;
	}

}