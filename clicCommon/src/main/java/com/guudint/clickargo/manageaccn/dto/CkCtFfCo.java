package com.guudint.clickargo.manageaccn.dto;
// Generated 18 Dec 2023, 10:41:15 pm by Hibernate Tools 4.3.6.Final

import java.util.Date;

import com.guudint.clickargo.manageaccn.model.TCkCtFfCo;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkCtFfCo extends AbstractDTO<CkCtFfCo, TCkCtFfCo> {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = 1L;
	public static final String PREFIX_ID = "FFCO";

	// Attributes
	/////////////
	private String ffcoId;
	private CoreAccn TCoreAccnByFfcoCo;
	private CoreAccn TCoreAccnByFfcoFf;
	private String ffcoRef;
	private String ffcoRemarks;
	private Date ffcoDtStart;
	private Date ffcoDtEnd;
	private Character ffcoStatus;
	private Date ffcoDtCreate;
	private String ffcoUidCreate;
	private Date ffcoDtLupd;
	private String ffcoUidLupd;
	

	private String history;

	// Constructors
	///////////////
	public CkCtFfCo() {
	}

	public CkCtFfCo(String ffcoId) {
		this.ffcoId = ffcoId;
	}

	public CkCtFfCo(TCkCtFfCo entity) {
		super(entity);
	}

	public CkCtFfCo(CoreAccn TCoreAccnByFfcoFf, CoreAccn TCoreAccnByFfcoCo) {
		this.TCoreAccnByFfcoFf = TCoreAccnByFfcoFf;
		this.TCoreAccnByFfcoCo = TCoreAccnByFfcoCo;
	}

	public CkCtFfCo(String ffcoId, CoreAccn TCoreAccnByFfcoCo, CoreAccn TCoreAccnByFfcoFf, String ffcoRef,
			String ffcoRemarks, Date ffcoDtStart, Date ffcoDtEnd, Character ffcoStatus, Date ffcoDtCreate,
			String ffcoUidCreate, Date ffcoDtLupd, String ffcoUidLupd) {
		this.ffcoId = ffcoId;
		this.TCoreAccnByFfcoCo = TCoreAccnByFfcoCo;
		this.TCoreAccnByFfcoFf = TCoreAccnByFfcoFf;
		this.ffcoRef = ffcoRef;
		this.ffcoRemarks = ffcoRemarks;
		this.ffcoDtStart = ffcoDtStart;
		this.ffcoDtEnd = ffcoDtEnd;
		this.ffcoStatus = ffcoStatus;
		this.ffcoDtCreate = ffcoDtCreate;
		this.ffcoUidCreate = ffcoUidCreate;
		this.ffcoDtLupd = ffcoDtLupd;
		this.ffcoUidLupd = ffcoUidLupd;
	}

	// Override Methods
	///////////////////
	@Override
	public int compareTo(CkCtFfCo o) {
		return 0;
	}

	@Override
	public void init() {

	}

	// Properties
	/////////////
	public String getFfcoId() {
		return this.ffcoId;
	}

	public void setFfcoId(String ffcoId) {
		this.ffcoId = ffcoId;
	}

	public CoreAccn getTCoreAccnByFfcoCo() {
		return this.TCoreAccnByFfcoCo;
	}

	public void setTCoreAccnByFfcoCo(CoreAccn TCoreAccnByFfcoCo) {
		this.TCoreAccnByFfcoCo = TCoreAccnByFfcoCo;
	}

	public CoreAccn getTCoreAccnByFfcoFf() {
		return this.TCoreAccnByFfcoFf;
	}

	public void setTCoreAccnByFfcoFf(CoreAccn TCoreAccnByFfcoFf) {
		this.TCoreAccnByFfcoFf = TCoreAccnByFfcoFf;
	}

	public String getFfcoRef() {
		return this.ffcoRef;
	}

	public void setFfcoRef(String ffcoRef) {
		this.ffcoRef = ffcoRef;
	}

	public String getFfcoRemarks() {
		return this.ffcoRemarks;
	}

	public void setFfcoRemarks(String ffcoRemarks) {
		this.ffcoRemarks = ffcoRemarks;
	}

	public Date getFfcoDtStart() {
		return this.ffcoDtStart;
	}

	public void setFfcoDtStart(Date ffcoDtStart) {
		this.ffcoDtStart = ffcoDtStart;
	}

	public Date getFfcoDtEnd() {
		return this.ffcoDtEnd;
	}

	public void setFfcoDtEnd(Date ffcoDtEnd) {
		this.ffcoDtEnd = ffcoDtEnd;
	}

	public Character getFfcoStatus() {
		return this.ffcoStatus;
	}

	public void setFfcoStatus(Character ffcoStatus) {
		this.ffcoStatus = ffcoStatus;
	}

	public Date getFfcoDtCreate() {
		return this.ffcoDtCreate;
	}

	public void setFfcoDtCreate(Date ffcoDtCreate) {
		this.ffcoDtCreate = ffcoDtCreate;
	}

	public String getFfcoUidCreate() {
		return this.ffcoUidCreate;
	}

	public void setFfcoUidCreate(String ffcoUidCreate) {
		this.ffcoUidCreate = ffcoUidCreate;
	}

	public Date getFfcoDtLupd() {
		return this.ffcoDtLupd;
	}

	public void setFfcoDtLupd(Date ffcoDtLupd) {
		this.ffcoDtLupd = ffcoDtLupd;
	}

	public String getFfcoUidLupd() {
		return this.ffcoUidLupd;
	}

	public void setFfcoUidLupd(String ffcoUidLupd) {
		this.ffcoUidLupd = ffcoUidLupd;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

}
