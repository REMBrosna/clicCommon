package com.guudint.clickargo.tax.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.guudint.clickargo.tax.model.TCkTaxInvoice;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkTaxInvoice extends AbstractDTO<CkTaxInvoice, TCkTaxInvoice> {

	public static final String PREFIX_ID = "TXINV";

	private static final long serialVersionUID = -7517468748698064138L;
	private String tiId;
	private CoreAccn TCoreAccn;
	private String tiService;
	private String tiNo;
	private BigDecimal tiAmt;
	private String tiDoc;
	private Date tiDtUpload;
	private String tiUsrUpload;
	private String tiInvNo;
	private Date tiInvDtIssue;
	private String tiJobNo;
	private Character tiStatus;
	private Date tiDtCreate;
	private String tiUidCreate;
	private Date tiDtLupd;
	private String tiUidLupd;
	public String history;
	
	public CkTaxInvoice() {
	}

	public CkTaxInvoice(TCkTaxInvoice tCkTaxInvoice) {
		super(tCkTaxInvoice);
	}

	public CkTaxInvoice(String tiId, String tiService, String tiNo, String tiDoc, String tiInvNo) {
		this.tiId = tiId;
		this.tiService = tiService;
		this.tiNo = tiNo;
		this.tiDoc = tiDoc;
		this.tiInvNo = tiInvNo;
	}

	public CkTaxInvoice(String tiId, CoreAccn TCoreAccn, String tiService, String tiNo, BigDecimal tiAmt,
			String tiDoc, Date tiDtUpload, String tiUsrUpload, String tiInvNo, Date tiInvDtIssue, String tiJobNo,
			Character tiStatus, Date tiDtCreate, String tiUidCreate, Date tiDtLupd, String tiUidLupd, String history) {
		this.tiId = tiId;
		this.TCoreAccn = TCoreAccn;
		this.tiService = tiService;
		this.tiNo = tiNo;
		this.tiAmt = tiAmt;
		this.tiDoc = tiDoc;
		this.tiDtUpload = tiDtUpload;
		this.tiUsrUpload = tiUsrUpload;
		this.tiInvNo = tiInvNo;
		this.tiInvDtIssue = tiInvDtIssue;
		this.tiJobNo = tiJobNo;
		this.tiStatus = tiStatus;
		this.tiDtCreate = tiDtCreate;
		this.tiUidCreate = tiUidCreate;
		this.tiDtLupd = tiDtLupd;
		this.tiUidLupd = tiUidLupd;
		this.history = history;
	}

	public String getTiId() {
		return this.tiId;
	}

	public void setTiId(String tiId) {
		this.tiId = tiId;
	}

	public CoreAccn getTCoreAccn() {
		return this.TCoreAccn;
	}

	public void setTCoreAccn(CoreAccn TCoreAccn) {
		this.TCoreAccn = TCoreAccn;
	}

	public String getTiService() {
		return this.tiService;
	}

	public void setTiService(String tiService) {
		this.tiService = tiService;
	}

	public String getTiNo() {
		return this.tiNo;
	}

	public void setTiNo(String tiNo) {
		this.tiNo = tiNo;
	}

	public BigDecimal getTiAmt() {
		return this.tiAmt;
	}

	public void setTiAmt(BigDecimal tiAmt) {
		this.tiAmt = tiAmt;
	}

	public String getTiDoc() {
		return this.tiDoc;
	}

	public void setTiDoc(String tiDoc) {
		this.tiDoc = tiDoc;
	}

	public Date getTiDtUpload() {
		return this.tiDtUpload;
	}

	public void setTiDtUpload(Date tiDtUpload) {
		this.tiDtUpload = tiDtUpload;
	}

	public String getTiUsrUpload() {
		return this.tiUsrUpload;
	}

	public void setTiUsrUpload(String tiUsrUpload) {
		this.tiUsrUpload = tiUsrUpload;
	}

	public String getTiInvNo() {
		return this.tiInvNo;
	}

	public void setTiInvNo(String tiInvNo) {
		this.tiInvNo = tiInvNo;
	}

	public Date getTiInvDtIssue() {
		return this.tiInvDtIssue;
	}

	public void setTiInvDtIssue(Date tiInvDtIssue) {
		this.tiInvDtIssue = tiInvDtIssue;
	}

	public String getTiJobNo() {
		return this.tiJobNo;
	}

	public void setTiJobNo(String tiJobNo) {
		this.tiJobNo = tiJobNo;
	}

	public Character getTiStatus() {
		return this.tiStatus;
	}

	public void setTiStatus(Character tiStatus) {
		this.tiStatus = tiStatus;
	}

	public Date getTiDtCreate() {
		return this.tiDtCreate;
	}

	public void setTiDtCreate(Date tiDtCreate) {
		this.tiDtCreate = tiDtCreate;
	}

	public String getTiUidCreate() {
		return this.tiUidCreate;
	}

	public void setTiUidCreate(String tiUidCreate) {
		this.tiUidCreate = tiUidCreate;
	}

	public Date getTiDtLupd() {
		return this.tiDtLupd;
	}

	public void setTiDtLupd(Date tiDtLupd) {
		this.tiDtLupd = tiDtLupd;
	}

	public String getTiUidLupd() {
		return this.tiUidLupd;
	}

	public void setTiUidLupd(String tiUidLupd) {
		this.tiUidLupd = tiUidLupd;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	@Override
	public int compareTo(CkTaxInvoice o) {
		return 0;
	}

	@Override
	public void init() {
		
	}
}
