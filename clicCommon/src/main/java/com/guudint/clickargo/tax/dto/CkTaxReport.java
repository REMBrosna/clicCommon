package com.guudint.clickargo.tax.dto;

import java.util.Date;

import com.guudint.clickargo.tax.model.TCkTaxReport;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkTaxReport extends AbstractDTO<CkTaxReport, TCkTaxReport> {

	public static final String PREFIX_ID = "TXRPT";


	private static final long serialVersionUID = -7009935228773141951L;
	private String trId;
	private String trService;
	private String trName;
	private String trDoc;
	private Integer trNumRecords;
	private Character trStatus;
	private Date trDtCreate;
	private String trUidCreate;
	private Date trDtLupd;
	private String trUidLupd;
	private String history;

	public CkTaxReport() {
	}

	public CkTaxReport(TCkTaxReport tCkTaxReport) {
		super(tCkTaxReport);
	}

	public CkTaxReport(String trId) {
		this.trId = trId;
	}

	public CkTaxReport(String trId, String trName, String trDoc, Integer trNumRecords, Character trStatus,
			Date trDtCreate, String trUidCreate, Date trDtLupd, String trUidLupd, String history) {
		this.trId = trId;
		this.trName = trName;
		this.trDoc = trDoc;
		this.trNumRecords = trNumRecords;
		this.trStatus = trStatus;
		this.trDtCreate = trDtCreate;
		this.trUidCreate = trUidCreate;
		this.trDtLupd = trDtLupd;
		this.trUidLupd = trUidLupd;
		this.history = history;
	}


	public String getTrId() {
		return this.trId;
	}

	public void setTrId(String trId) {
		this.trId = trId;
	}

	public String getTrService() {
		return this.trService;
	}

	public void setTrService(String trService) {
		this.trService = trService;
	}

	public String getTrName() {
		return this.trName;
	}

	public void setTrName(String trName) {
		this.trName = trName;
	}

	public String getTrDoc() {
		return this.trDoc;
	}

	public void setTrDoc(String trDoc) {
		this.trDoc = trDoc;
	}

	public Integer getTrNumRecords() {
		return this.trNumRecords;
	}

	public void setTrNumRecords(Integer trNumRecords) {
		this.trNumRecords = trNumRecords;
	}

	public Character getTrStatus() {
		return this.trStatus;
	}

	public void setTrStatus(Character trStatus) {
		this.trStatus = trStatus;
	}

	public Date getTrDtCreate() {
		return this.trDtCreate;
	}

	public void setTrDtCreate(Date trDtCreate) {
		this.trDtCreate = trDtCreate;
	}

	public String getTrUidCreate() {
		return this.trUidCreate;
	}

	public void setTrUidCreate(String trUidCreate) {
		this.trUidCreate = trUidCreate;
	}

	public Date getTrDtLupd() {
		return this.trDtLupd;
	}

	public void setTrDtLupd(Date trDtLupd) {
		this.trDtLupd = trDtLupd;
	}

	public String getTrUidLupd() {
		return this.trUidLupd;
	}

	public void setTrUidLupd(String trUidLupd) {
		this.trUidLupd = trUidLupd;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	@Override
	public int compareTo(CkTaxReport o) {
		return 0;
	}

	@Override
	public void init() {

	}

}
