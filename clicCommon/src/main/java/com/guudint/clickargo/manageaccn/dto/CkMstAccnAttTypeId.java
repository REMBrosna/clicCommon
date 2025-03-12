package com.guudint.clickargo.manageaccn.dto;

import com.guudint.clickargo.manageaccn.model.TCkMstAccnAttTypeId;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkMstAccnAttTypeId extends AbstractDTO<CkMstAccnAttTypeId, TCkMstAccnAttTypeId> {

	private static final long serialVersionUID = 213156541594825469L;
	private String atId;
	private String atWorkflow;

	public CkMstAccnAttTypeId() {
	}

	public CkMstAccnAttTypeId(TCkMstAccnAttTypeId entity) {
		super(entity);
	}

	public CkMstAccnAttTypeId(String atId, String atWorkflow) {
		this.atId = atId;
		this.atWorkflow = atWorkflow;
	}

	/**
	 * @return the atId
	 */
	public String getAtId() {
		return atId;
	}

	/**
	 * @param atId the atId to set
	 */
	public void setAtId(String atId) {
		this.atId = atId;
	}

	/**
	 * @return the atWorkflow
	 */
	public String getAtWorkflow() {
		return atWorkflow;
	}

	/**
	 * @param atWorkflow the atWorkflow to set
	 */
	public void setAtWorkflow(String atWorkflow) {
		this.atWorkflow = atWorkflow;
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public int compareTo(CkMstAccnAttTypeId o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
