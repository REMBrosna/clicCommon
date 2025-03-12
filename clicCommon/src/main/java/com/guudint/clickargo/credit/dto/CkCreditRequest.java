package com.guudint.clickargo.credit.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.guudint.clickargo.credit.model.TCkCreditRequest;
import com.guudint.clickargo.master.dto.CkMstServiceType;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.common.dto.AbstractDTO;
import com.vcc.camelone.master.dto.MstCurrency;

public class CkCreditRequest extends AbstractDTO<CkCreditRequest, TCkCreditRequest> {

	private static final long serialVersionUID = 2052192328038306041L;
	private String cruId;
	private CkMstCreditRequestState TCkMstCreditRequestState;
	private CkMstServiceType TCkMstServiceType;
	private CoreAccn TCoreAccn;
	private MstCurrency TMstCurrency;
	private BigDecimal cruAmt;
	private BigDecimal cruTxnCap;
	private Date cruDtStart;
	private Date cruDtEnd;
	private String cruRequester;
	private String cruRemarks;
	private String cruUsrVerify;
	private Date cruDtVerify;
	private String cruUsrApprove;
	private Date cruDtApprove;
	private String cruUsrReject;
	private Date cruDtReject;
	private String cruApproverRemarks;
	private Character cruStatus;
	private Date cruDtCreate;
	private String cruUidCreate;
	private Date cruDtLupd;
	private String cruUidLupd;
	private Date cruDtSubmitted;
	private String cruUidSubmitted;
	private BigDecimal creditLimit;
	private String history;

	public CkCreditRequest() {
	}

	public CkCreditRequest(TCkCreditRequest entity) {
		super(entity);
	}

	public CkCreditRequest(String cruId) {
		this.cruId = cruId;
	}

	public CkCreditRequest(String cruId, CkMstCreditRequestState TCkMstCreditRequestState,
			CkMstServiceType TCkMstServiceType, CoreAccn TCoreAccn, MstCurrency TMstCurrency, BigDecimal cruAmt,
			BigDecimal cruTxnCap, Date cruDtStart, Date cruDtEnd, String cruRemarks, String cruUsrVerify, Date cruDtVerify,
			String cruUsrApprove, Date cruDtApprove, String cruUsrReject, Date cruDtReject, String cruApproverRemarks,
			Character cruStatus, Date cruDtCreate, String cruUidCreate, Date cruDtLupd, String cruUidLupd) {
		this.cruId = cruId;
		this.TCkMstCreditRequestState = TCkMstCreditRequestState;
		this.TCkMstServiceType = TCkMstServiceType;
		this.TCoreAccn = TCoreAccn;
		this.TMstCurrency = TMstCurrency;
		this.cruAmt = cruAmt;
		this.cruTxnCap = cruTxnCap;
		this.cruDtStart = cruDtStart;
		this.cruDtEnd = cruDtEnd;
		this.cruRemarks = cruRemarks;
		this.cruUsrVerify = cruUsrVerify;
		this.cruDtVerify = cruDtVerify;
		this.cruUsrApprove = cruUsrApprove;
		this.cruDtApprove = cruDtApprove;
		this.cruUsrReject = cruUsrReject;
		this.cruDtReject = cruDtReject;
		this.cruApproverRemarks = cruApproverRemarks;
		this.cruStatus = cruStatus;
		this.cruDtCreate = cruDtCreate;
		this.cruUidCreate = cruUidCreate;
		this.cruDtLupd = cruDtLupd;
		this.cruUidLupd = cruUidLupd;
	}

	/**
	 * @return the cruId
	 */
	public String getCruId() {
		return cruId;
	}

	/**
	 * @param cruId the cruId to set
	 */
	public void setCruId(String cruId) {
		this.cruId = cruId;
	}

	/**
	 * @return the tCkMstCreditRequestState
	 */
	public CkMstCreditRequestState getTCkMstCreditRequestState() {
		return TCkMstCreditRequestState;
	}

	/**
	 * @param tCkMstCreditRequestState the tCkMstCreditRequestState to set
	 */
	public void setTCkMstCreditRequestState(CkMstCreditRequestState tCkMstCreditRequestState) {
		TCkMstCreditRequestState = tCkMstCreditRequestState;
	}

	/**
	 * @return the tCkMstServiceType
	 */
	public CkMstServiceType getTCkMstServiceType() {
		return TCkMstServiceType;
	}

	/**
	 * @param tCkMstServiceType the tCkMstServiceType to set
	 */
	public void setTCkMstServiceType(CkMstServiceType tCkMstServiceType) {
		TCkMstServiceType = tCkMstServiceType;
	}

	/**
	 * @return the tCoreAccn
	 */
	public CoreAccn getTCoreAccn() {
		return TCoreAccn;
	}

	/**
	 * @param tCoreAccn the tCoreAccn to set
	 */
	public void setTCoreAccn(CoreAccn tCoreAccn) {
		TCoreAccn = tCoreAccn;
	}

	/**
	 * @return the tMstCurrency
	 */
	public MstCurrency getTMstCurrency() {
		return TMstCurrency;
	}

	/**
	 * @param tMstCurrency the tMstCurrency to set
	 */
	public void setTMstCurrency(MstCurrency tMstCurrency) {
		TMstCurrency = tMstCurrency;
	}

	/**
	 * @return the cruAmt
	 */
	public BigDecimal getCruAmt() {
		return cruAmt;
	}

	/**
	 * @param cruAmt the cruAmt to set
	 */
	public void setCruAmt(BigDecimal cruAmt) {
		this.cruAmt = cruAmt;
	}

	/**
	 * @return the cruTxnCap
	 */
	public BigDecimal getCruTxnCap() {
		return cruTxnCap;
	}

	/**
	 * @param cruTxnCap the cruTxnCap to set
	 */
	public void setCruTxnCap(BigDecimal cruTxnCap) {
		this.cruTxnCap = cruTxnCap;
	}

	/**
	 * @return the cruDtStart
	 */
	public Date getCruDtStart() {
		return cruDtStart;
	}

	/**
	 * @param cruDtStart the cruDtStart to set
	 */
	public void setCruDtStart(Date cruDtStart) {
		this.cruDtStart = cruDtStart;
	}

	/**
	 * @return the cruDtEnd
	 */
	public Date getCruDtEnd() {
		return cruDtEnd;
	}

	/**
	 * @param cruDtEnd the cruDtEnd to set
	 */
	public void setCruDtEnd(Date cruDtEnd) {
		this.cruDtEnd = cruDtEnd;
	}

	/**
	 * @return the cruRemarks
	 */
	public String getCruRemarks() {
		return cruRemarks;
	}

	/**
	 * @param cruRemarks the cruRemarks to set
	 */
	public void setCruRemarks(String cruRemarks) {
		this.cruRemarks = cruRemarks;
	}

	/**
	 * @return the cruUsrVerify
	 */
	public String getCruUsrVerify() {
		return cruUsrVerify;
	}

	/**
	 * @param cruUsrVerify the cruUsrVerify to set
	 */
	public void setCruUsrVerify(String cruUsrVerify) {
		this.cruUsrVerify = cruUsrVerify;
	}

	/**
	 * @return the cruDtVerify
	 */
	public Date getCruDtVerify() {
		return cruDtVerify;
	}

	/**
	 * @param cruDtVerify the cruDtVerify to set
	 */
	public void setCruDtVerify(Date cruDtVerify) {
		this.cruDtVerify = cruDtVerify;
	}

	/**
	 * @return the cruUsrApprove
	 */
	public String getCruUsrApprove() {
		return cruUsrApprove;
	}

	/**
	 * @param cruUsrApprove the cruUsrApprove to set
	 */
	public void setCruUsrApprove(String cruUsrApprove) {
		this.cruUsrApprove = cruUsrApprove;
	}

	/**
	 * @return the cruDtApprove
	 */
	public Date getCruDtApprove() {
		return cruDtApprove;
	}

	/**
	 * @param cruDtApprove the cruDtApprove to set
	 */
	public void setCruDtApprove(Date cruDtApprove) {
		this.cruDtApprove = cruDtApprove;
	}

	/**
	 * @return the cruUsrReject
	 */
	public String getCruUsrReject() {
		return cruUsrReject;
	}

	/**
	 * @param cruUsrReject the cruUsrReject to set
	 */
	public void setCruUsrReject(String cruUsrReject) {
		this.cruUsrReject = cruUsrReject;
	}

	/**
	 * @return the cruDtReject
	 */
	public Date getCruDtReject() {
		return cruDtReject;
	}

	/**
	 * @param cruDtReject the cruDtReject to set
	 */
	public void setCruDtReject(Date cruDtReject) {
		this.cruDtReject = cruDtReject;
	}

	/**
	 * @return the cruApproverRemarks
	 */
	public String getCruApproverRemarks() {
		return cruApproverRemarks;
	}

	/**
	 * @param cruApproverRemarks the cruApproverRemarks to set
	 */
	public void setCruApproverRemarks(String cruApproverRemarks) {
		this.cruApproverRemarks = cruApproverRemarks;
	}

	/**
	 * @return the cruStatus
	 */
	public Character getCruStatus() {
		return cruStatus;
	}

	/**
	 * @param cruStatus the cruStatus to set
	 */
	public void setCruStatus(Character cruStatus) {
		this.cruStatus = cruStatus;
	}

	/**
	 * @return the cruDtCreate
	 */
	public Date getCruDtCreate() {
		return cruDtCreate;
	}

	/**
	 * @param cruDtCreate the cruDtCreate to set
	 */
	public void setCruDtCreate(Date cruDtCreate) {
		this.cruDtCreate = cruDtCreate;
	}

	/**
	 * @return the cruUidCreate
	 */
	public String getCruUidCreate() {
		return cruUidCreate;
	}

	/**
	 * @param cruUidCreate the cruUidCreate to set
	 */
	public void setCruUidCreate(String cruUidCreate) {
		this.cruUidCreate = cruUidCreate;
	}

	/**
	 * @return the cruDtLupd
	 */
	public Date getCruDtLupd() {
		return cruDtLupd;
	}

	/**
	 * @param cruDtLupd the cruDtLupd to set
	 */
	public void setCruDtLupd(Date cruDtLupd) {
		this.cruDtLupd = cruDtLupd;
	}

	/**
	 * @return the cruUidLupd
	 */
	public String getCruUidLupd() {
		return cruUidLupd;
	}

	public Date getCruDtSubmitted() {
		return this.cruDtSubmitted;
	}

	public void setCruDtSubmitted(Date cruDtSubmitted) {
		this.cruDtSubmitted = cruDtSubmitted;
	}

	public String getCruUidSubmitted() {
		return this.cruUidSubmitted;
	}

	public void setCruUidSubmitted(String cruUidSubmitted) {
		this.cruUidSubmitted = cruUidSubmitted;
	}

	public String getCruRequester() {
		return this.cruRequester;
	}

	public void setCruRequester(String cruRequester) {
		this.cruRequester = cruRequester;
	}

	public BigDecimal getCreditLimit() {
		return this.creditLimit;
	}

	public void setCreditLimit(BigDecimal creditLimit) {
		this.creditLimit = creditLimit;
	}

	/**
	 * @param cruUidLupd the cruUidLupd to set
	 */
	public void setCruUidLupd(String cruUidLupd) {
		this.cruUidLupd = cruUidLupd;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	@Override
	public int compareTo(CkCreditRequest o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

}
