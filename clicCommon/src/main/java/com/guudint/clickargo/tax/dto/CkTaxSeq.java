package com.guudint.clickargo.tax.dto;
// Generated 21 Jun 2024, 2:16:22 pm by Hibernate Tools 4.3.6.Final

import java.util.Date;

import com.guudint.clickargo.tax.model.TCkTaxSeq;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkTaxSeq extends AbstractDTO<CkTaxSeq, TCkTaxSeq> {

	private static final long serialVersionUID = -1L;
	public static final String PREFIX_ID = "TS";

	private String tsId;
	private String tsService;
	private String tsPrefix;
	private long tsRangeBegin;
	private long tsRangeEnd;
	private long tsRangeCurrent;
	private String tsRangeFormat;
	
	private Character tsStatus;
	private Date tsDtCreate;
	private String tsUidCreate;
	private Date tsDtLupd;
	private String tsUidLupd;
	
	private String history;

	public CkTaxSeq() {
	}

	public CkTaxSeq(TCkTaxSeq tCkTaxSeq) {
		super(tCkTaxSeq);
	}

	public CkTaxSeq(String tsId) {
		this.tsId = tsId;
	}

	public CkTaxSeq(String tsId, String tsPrefix, long tsRangeBegin, long tsRangeEnd, long tsRangeCurrent,
			String tsRangeFormat) {
		this.tsId = tsId;
		this.tsPrefix = tsPrefix;
		this.tsRangeBegin = tsRangeBegin;
		this.tsRangeEnd = tsRangeEnd;
		this.tsRangeCurrent = tsRangeCurrent;
		this.tsRangeFormat = tsRangeFormat;
	}

	public CkTaxSeq(String tsId, String tsPrefix, long tsRangeBegin, long tsRangeEnd, long tsRangeCurrent,
			String tsRangeFormat, Character tsStatus, Date tsDtCreate, String tsUidCreate, Date tsDtLupd,
			String tsUidLupd) {
		this.tsId = tsId;
		this.tsPrefix = tsPrefix;
		this.tsRangeBegin = tsRangeBegin;
		this.tsRangeEnd = tsRangeEnd;
		this.tsRangeCurrent = tsRangeCurrent;
		this.tsRangeFormat = tsRangeFormat;
		this.tsStatus = tsStatus;
		this.tsDtCreate = tsDtCreate;
		this.tsUidCreate = tsUidCreate;
		this.tsDtLupd = tsDtLupd;
		this.tsUidLupd = tsUidLupd;
	}

	@Override
	public int compareTo(CkTaxSeq o) {
		return 0;
	}

	@Override
	public void init() {
	}

	public String getTsId() {
		return this.tsId;
	}

	public void setTsId(String tsId) {
		this.tsId = tsId;
	}

	public String getTsService() {
		return this.tsService;
	}

	public void setTsService(String tsService) {
		this.tsService = tsService;
	}

	public String getTsPrefix() {
		return this.tsPrefix;
	}

	public void setTsPrefix(String tsPrefix) {
		this.tsPrefix = tsPrefix;
	}

	public long getTsRangeBegin() {
		return this.tsRangeBegin;
	}

	public void setTsRangeBegin(long tsRangeBegin) {
		this.tsRangeBegin = tsRangeBegin;
	}

	public long getTsRangeEnd() {
		return this.tsRangeEnd;
	}

	public void setTsRangeEnd(long tsRangeEnd) {
		this.tsRangeEnd = tsRangeEnd;
	}

	public long getTsRangeCurrent() {
		return this.tsRangeCurrent;
	}

	public void setTsRangeCurrent(long tsRangeCurrent) {
		this.tsRangeCurrent = tsRangeCurrent;
	}

	public String getTsRangeFormat() {
		return this.tsRangeFormat;
	}

	public void setTsRangeFormat(String tsRangeFormat) {
		this.tsRangeFormat = tsRangeFormat;
	}

	public Character getTsStatus() {
		return this.tsStatus;
	}

	public void setTsStatus(Character tsStatus) {
		this.tsStatus = tsStatus;
	}

	public Date getTsDtCreate() {
		return this.tsDtCreate;
	}

	public void setTsDtCreate(Date tsDtCreate) {
		this.tsDtCreate = tsDtCreate;
	}

	public String getTsUidCreate() {
		return this.tsUidCreate;
	}

	public void setTsUidCreate(String tsUidCreate) {
		this.tsUidCreate = tsUidCreate;
	}

	public Date getTsDtLupd() {
		return this.tsDtLupd;
	}

	public void setTsDtLupd(Date tsDtLupd) {
		this.tsDtLupd = tsDtLupd;
	}

	public String getTsUidLupd() {
		return this.tsUidLupd;
	}

	public void setTsUidLupd(String tsUidLupd) {
		this.tsUidLupd = tsUidLupd;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}
	
}
