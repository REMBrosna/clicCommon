package com.guudint.clickargo.payment.dto;

import java.util.Date;

import com.guudint.clickargo.payment.model.TCkPaymentTxnLog;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkPaymentTxnLog extends AbstractDTO<CkPaymentTxnLog, TCkPaymentTxnLog> {

	private static final long serialVersionUID = 4273556314833880937L;
	private String ptxlId;
	private CkPaymentTxn TCkPaymentTxn;
	private String ptxlTxnState;
	private String ptxlRemarks;
	private Date ptxlDtCreate;
	private String ptxlUidCreate;
	private Date ptxlDtLupd;
	private String ptxlUidLupd;

	public CkPaymentTxnLog() {
	}

	/**
	 * @param entity
	 */
	public CkPaymentTxnLog(TCkPaymentTxnLog entity) {
		super(entity);
	}

	public CkPaymentTxnLog(String ptxlId) {
		this.ptxlId = ptxlId;
	}

	@Override
	public int compareTo(CkPaymentTxnLog o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the ptxlId
	 */
	public String getPtxlId() {
		return ptxlId;
	}

	/**
	 * @param ptxlId the ptxlId to set
	 */
	public void setPtxlId(String ptxlId) {
		this.ptxlId = ptxlId;
	}

	/**
	 * @return the tCkPaymentTxn
	 */
	public CkPaymentTxn getTCkPaymentTxn() {
		return TCkPaymentTxn;
	}

	/**
	 * @param tCkPaymentTxn the tCkPaymentTxn to set
	 */
	public void setTCkPaymentTxn(CkPaymentTxn tCkPaymentTxn) {
		TCkPaymentTxn = tCkPaymentTxn;
	}

	/**
	 * @return the ptxlTxnState
	 */
	public String getPtxlTxnState() {
		return ptxlTxnState;
	}

	/**
	 * @param ptxlTxnState the ptxlTxnState to set
	 */
	public void setPtxlTxnState(String ptxlTxnState) {
		this.ptxlTxnState = ptxlTxnState;
	}

	/**
	 * @return the ptxlRemarks
	 */
	public String getPtxlRemarks() {
		return ptxlRemarks;
	}

	/**
	 * @param ptxlRemarks the ptxlRemarks to set
	 */
	public void setPtxlRemarks(String ptxlRemarks) {
		this.ptxlRemarks = ptxlRemarks;
	}

	/**
	 * @return the ptxlDtCreate
	 */
	public Date getPtxlDtCreate() {
		return ptxlDtCreate;
	}

	/**
	 * @param ptxlDtCreate the ptxlDtCreate to set
	 */
	public void setPtxlDtCreate(Date ptxlDtCreate) {
		this.ptxlDtCreate = ptxlDtCreate;
	}

	/**
	 * @return the ptxlUidCreate
	 */
	public String getPtxlUidCreate() {
		return ptxlUidCreate;
	}

	/**
	 * @param ptxlUidCreate the ptxlUidCreate to set
	 */
	public void setPtxlUidCreate(String ptxlUidCreate) {
		this.ptxlUidCreate = ptxlUidCreate;
	}

	/**
	 * @return the ptxlDtLupd
	 */
	public Date getPtxlDtLupd() {
		return ptxlDtLupd;
	}

	/**
	 * @param ptxlDtLupd the ptxlDtLupd to set
	 */
	public void setPtxlDtLupd(Date ptxlDtLupd) {
		this.ptxlDtLupd = ptxlDtLupd;
	}

	/**
	 * @return the ptxlUidLupd
	 */
	public String getPtxlUidLupd() {
		return ptxlUidLupd;
	}

	/**
	 * @param ptxlUidLupd the ptxlUidLupd to set
	 */
	public void setPtxlUidLupd(String ptxlUidLupd) {
		this.ptxlUidLupd = ptxlUidLupd;
	}

}
