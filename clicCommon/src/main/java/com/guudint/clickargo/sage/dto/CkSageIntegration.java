package com.guudint.clickargo.sage.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.guudint.clickargo.master.dto.CkMstServiceType;
import com.guudint.clickargo.sage.model.TCkSageIntegration;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkSageIntegration extends AbstractDTO<CkSageIntegration, TCkSageIntegration> {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = -1L;
	
	private String sintId;
	private CkCtMstSageIntState TCkCtMstSageIntState;
	private CkCtMstSageIntType TCkCtMstSageIntType;
	private CkMstServiceType TCkMstServiceType;
	private Date sintDtStart;
	private Date sintDtEnd;
	private Integer sintNoRecords;
	private Integer sintNoSuccess;
	private Integer sintNoFail;
	private Date sintDtExport;
	private Date sintDtImport;
	private String sintLocExport;
	private String sintLocImport;
	private Character sintStatus;
	private Date sintDtCreate;
	private String sintUidCreate;
	private Date sintDtLupd;
	private String sintUidLupd;

	public CkSageIntegration() {
	}

	/**
	 * @param entity
	 */
	public CkSageIntegration(TCkSageIntegration entity) {
		super(entity);
	}

	public CkSageIntegration(String sintId, CkCtMstSageIntState TCkCtMstSageIntState) {
		this.sintId = sintId;
		this.TCkCtMstSageIntState = TCkCtMstSageIntState;
	}

	public CkSageIntegration(String sintId, CkCtMstSageIntState TCkCtMstSageIntState,
			CkCtMstSageIntType TCkCtMstSageIntType, CkMstServiceType TCkMstServiceType, Date sintDtStart,
			Date sintDtEnd, Integer sintNoRecords, Integer sintNoSuccess, Integer sintNoFail, Date sintDtExport,
			Date sintDtImport, String sintLocExport, String sintLocImport, Character sintStatus, Date sintDtCreate,
			String sintUidCreate, Date sintDtLupd, String sintUidLupd) {
		this.sintId = sintId;
		this.TCkCtMstSageIntState = TCkCtMstSageIntState;
		this.TCkCtMstSageIntType = TCkCtMstSageIntType;
		this.TCkMstServiceType = TCkMstServiceType;
		this.sintDtStart = sintDtStart;
		this.sintDtEnd = sintDtEnd;
		this.sintNoRecords = sintNoRecords;
		this.sintNoSuccess = sintNoSuccess;
		this.sintNoFail = sintNoFail;
		this.sintDtExport = sintDtExport;
		this.sintDtImport = sintDtImport;
		this.sintLocExport = sintLocExport;
		this.sintLocImport = sintLocImport;
		this.sintStatus = sintStatus;
		this.sintDtCreate = sintDtCreate;
		this.sintUidCreate = sintUidCreate;
		this.sintDtLupd = sintDtLupd;
		this.sintUidLupd = sintUidLupd;
	}

	// Override Methods
	///////////////////
	@Override
	public int compareTo(CkSageIntegration arg0) {
		return 0;
	}

	@Override
	public void init() {
	}

	public String getSintId() {
		return this.sintId;
	}

	public void setSintId(String sintId) {
		this.sintId = sintId;
	}

	@JsonProperty("TCkCtMstSageIntState")
	public CkCtMstSageIntState getTCkCtMstSageIntState() {
		return this.TCkCtMstSageIntState;
	}

	public void setTCkCtMstSageIntState(CkCtMstSageIntState TCkCtMstSageIntState) {
		this.TCkCtMstSageIntState = TCkCtMstSageIntState;
	}

	@JsonProperty("TCkCtMstSageIntType")
	public CkCtMstSageIntType getTCkCtMstSageIntType() {
		return this.TCkCtMstSageIntType;
	}

	public void setTCkCtMstSageIntType(CkCtMstSageIntType TCkCtMstSageIntType) {
		this.TCkCtMstSageIntType = TCkCtMstSageIntType;
	}

	@JsonProperty("TCkMstServiceType")
	public CkMstServiceType getTCkMstServiceType() {
		return this.TCkMstServiceType;
	}

	public void setTCkMstServiceType(CkMstServiceType TCkMstServiceType) {
		this.TCkMstServiceType = TCkMstServiceType;
	}

	public Date getSintDtStart() {
		return this.sintDtStart;
	}

	public void setSintDtStart(Date sintDtStart) {
		this.sintDtStart = sintDtStart;
	}

	public Date getSintDtEnd() {
		return this.sintDtEnd;
	}

	public void setSintDtEnd(Date sintDtEnd) {
		this.sintDtEnd = sintDtEnd;
	}

	public Integer getSintNoRecords() {
		return this.sintNoRecords;
	}

	public void setSintNoRecords(Integer sintNoRecords) {
		this.sintNoRecords = sintNoRecords;
	}

	public Integer getSintNoSuccess() {
		return this.sintNoSuccess;
	}

	public void setSintNoSuccess(Integer sintNoSuccess) {
		this.sintNoSuccess = sintNoSuccess;
	}

	public Integer getSintNoFail() {
		return this.sintNoFail;
	}

	public void setSintNoFail(Integer sintNoFail) {
		this.sintNoFail = sintNoFail;
	}

	public Date getSintDtExport() {
		return this.sintDtExport;
	}

	public void setSintDtExport(Date sintDtExport) {
		this.sintDtExport = sintDtExport;
	}

	public Date getSintDtImport() {
		return this.sintDtImport;
	}

	public void setSintDtImport(Date sintDtImport) {
		this.sintDtImport = sintDtImport;
	}

	public String getSintLocExport() {
		return this.sintLocExport;
	}

	public void setSintLocExport(String sintLocExport) {
		this.sintLocExport = sintLocExport;
	}

	public String getSintLocImport() {
		return this.sintLocImport;
	}

	public void setSintLocImport(String sintLocImport) {
		this.sintLocImport = sintLocImport;
	}

	public Character getSintStatus() {
		return this.sintStatus;
	}

	public void setSintStatus(Character sintStatus) {
		this.sintStatus = sintStatus;
	}

	public Date getSintDtCreate() {
		return this.sintDtCreate;
	}

	public void setSintDtCreate(Date sintDtCreate) {
		this.sintDtCreate = sintDtCreate;
	}

	public String getSintUidCreate() {
		return this.sintUidCreate;
	}

	public void setSintUidCreate(String sintUidCreate) {
		this.sintUidCreate = sintUidCreate;
	}

	public Date getSintDtLupd() {
		return this.sintDtLupd;
	}

	public void setSintDtLupd(Date sintDtLupd) {
		this.sintDtLupd = sintDtLupd;
	}

	public String getSintUidLupd() {
		return this.sintUidLupd;
	}

	public void setSintUidLupd(String sintUidLupd) {
		this.sintUidLupd = sintUidLupd;
	}

}
