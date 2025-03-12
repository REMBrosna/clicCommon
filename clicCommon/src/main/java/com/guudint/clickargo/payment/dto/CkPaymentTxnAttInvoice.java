package com.guudint.clickargo.payment.dto;

import java.util.Date;

import com.guudint.clickargo.job.dto.CkJob;
import com.guudint.clickargo.master.dto.CkMstInvoiceType;
import com.guudint.clickargo.payment.model.TCkPaymentTxnAttInvoice;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkPaymentTxnAttInvoice extends AbstractDTO<CkPaymentTxnAttInvoice, TCkPaymentTxnAttInvoice> {

	private static final long serialVersionUID = 1129256436710429770L;
	private String ptxAttInvId;
	private CkJob TCkJob;
	private CkMstInvoiceType TCkMstInvoiceType;
	private String ptxAttInvName;
	private String ptxAttInvLoc;
	private Character ptxAttInvStatus;
	private Date ptxAttInvDtCreate;
	private String ptxAttInvUidCreate;
	private Date ptxAttInvDtLupd;
	private String ptxAttInvUidLupd;
	private byte[] attData;

	// Constructors
	///////////////
	public CkPaymentTxnAttInvoice() {
	}

	/**
	 * @param entity
	 */
	public CkPaymentTxnAttInvoice(TCkPaymentTxnAttInvoice entity) {
		super(entity);
	}

	/**
	 * @return the ptxAttInvId
	 */
	public String getPtxAttInvId() {
		return ptxAttInvId;
	}

	/**
	 * @param ptxAttInvId the ptxAttInvId to set
	 */
	public void setPtxAttInvId(String ptxAttInvId) {
		this.ptxAttInvId = ptxAttInvId;
	}

	/**
	 * @return the tCkJob
	 */
	public CkJob getTCkJob() {
		return TCkJob;
	}

	/**
	 * @param tCkJob the tCkJob to set
	 */
	public void setTCkJob(CkJob tCkJob) {
		TCkJob = tCkJob;
	}

	/**
	 * @return the tCkMstInvoiceType
	 */
	public CkMstInvoiceType getTCkMstInvoiceType() {
		return TCkMstInvoiceType;
	}

	/**
	 * @param tCkMstInvoiceType the tCkMstInvoiceType to set
	 */
	public void setTCkMstInvoiceType(CkMstInvoiceType tCkMstInvoiceType) {
		TCkMstInvoiceType = tCkMstInvoiceType;
	}

	/**
	 * @return the ptxAttInvName
	 */
	public String getPtxAttInvName() {
		return ptxAttInvName;
	}

	/**
	 * @param ptxAttInvName the ptxAttInvName to set
	 */
	public void setPtxAttInvName(String ptxAttInvName) {
		this.ptxAttInvName = ptxAttInvName;
	}

	/**
	 * @return the ptxAttInvLoc
	 */
	public String getPtxAttInvLoc() {
		return ptxAttInvLoc;
	}

	/**
	 * @param ptxAttInvLoc the ptxAttInvLoc to set
	 */
	public void setPtxAttInvLoc(String ptxAttInvLoc) {
		this.ptxAttInvLoc = ptxAttInvLoc;
	}

	/**
	 * @return the ptxAttInvStatus
	 */
	public Character getPtxAttInvStatus() {
		return ptxAttInvStatus;
	}

	/**
	 * @param ptxAttInvStatus the ptxAttInvStatus to set
	 */
	public void setPtxAttInvStatus(Character ptxAttInvStatus) {
		this.ptxAttInvStatus = ptxAttInvStatus;
	}

	/**
	 * @return the ptxAttInvDtCreate
	 */
	public Date getPtxAttInvDtCreate() {
		return ptxAttInvDtCreate;
	}

	/**
	 * @param ptxAttInvDtCreate the ptxAttInvDtCreate to set
	 */
	public void setPtxAttInvDtCreate(Date ptxAttInvDtCreate) {
		this.ptxAttInvDtCreate = ptxAttInvDtCreate;
	}

	/**
	 * @return the ptxAttInvUidCreate
	 */
	public String getPtxAttInvUidCreate() {
		return ptxAttInvUidCreate;
	}

	/**
	 * @param ptxAttInvUidCreate the ptxAttInvUidCreate to set
	 */
	public void setPtxAttInvUidCreate(String ptxAttInvUidCreate) {
		this.ptxAttInvUidCreate = ptxAttInvUidCreate;
	}

	/**
	 * @return the ptxAttInvDtLupd
	 */
	public Date getPtxAttInvDtLupd() {
		return ptxAttInvDtLupd;
	}

	/**
	 * @param ptxAttInvDtLupd the ptxAttInvDtLupd to set
	 */
	public void setPtxAttInvDtLupd(Date ptxAttInvDtLupd) {
		this.ptxAttInvDtLupd = ptxAttInvDtLupd;
	}

	/**
	 * @return the ptxAttInvUidLupd
	 */
	public String getPtxAttInvUidLupd() {
		return ptxAttInvUidLupd;
	}

	/**
	 * @param ptxAttInvUidLupd the ptxAttInvUidLupd to set
	 */
	public void setPtxAttInvUidLupd(String ptxAttInvUidLupd) {
		this.ptxAttInvUidLupd = ptxAttInvUidLupd;
	}

	public byte[] getAttData() {
		return attData;
	}

	public void setAttData(byte[] attData) {
		this.attData = attData;
	}

	@Override
	public int compareTo(CkPaymentTxnAttInvoice o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

}
