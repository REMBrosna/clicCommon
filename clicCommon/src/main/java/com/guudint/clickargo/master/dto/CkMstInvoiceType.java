package com.guudint.clickargo.master.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.guudint.clickargo.master.model.TCkMstInvoiceType;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkMstInvoiceType extends AbstractDTO<CkMstInvoiceType, TCkMstInvoiceType> {

	// Static Attributes
	/////////////////////
	private static final long serialVersionUID = 8563101270234500046L;

	// Attributes
	/////////////
	private String invtId;
	private String invtName;
	private String invtDesc;
	private String invtDescOth;
	private Character invtStatus;
	private Date invtDtCreate;
	private String invtUidCreate;
	private Date invtDtLupd;
	private String invtUidLupd;

	// Constructors
	///////////////
	public CkMstInvoiceType() {
	}

	/**
	 * @param entity
	 */
	public CkMstInvoiceType(TCkMstInvoiceType entity) {
		super(entity);
	}
	
	/**
	 * @param invtId
	 * @param invtName
	 */
	public CkMstInvoiceType(String invtId, String invtName) {
		this.invtId = invtId;
		this.invtName = invtName;
	}

	public CkMstInvoiceType(String invtId, String invtName, String invtDesc, String invtDescOth, Character invtStatus,
			Date invtDtCreate, String invtUidCreate, Date invtDtLupd, String invtUidLupd) {
		this.invtId = invtId;
		this.invtName = invtName;
		this.invtDesc = invtDesc;
		this.invtDescOth = invtDescOth;
		this.invtStatus = invtStatus;
		this.invtDtCreate = invtDtCreate;
		this.invtUidCreate = invtUidCreate;
		this.invtDtLupd = invtDtLupd;
		this.invtUidLupd = invtUidLupd;
	}

	// Override Methods
	///////////////////
	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * 
	 */
	@Override
	public int compareTo(CkMstInvoiceType o) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.vcc.camelone.common.COAbstractEntity#init()
	 * 
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	// Properties
	/////////////
	@Id
	@Column(name = "INVT_ID", unique = true, nullable = false, length = 35)
	public String getInvtId() {
		return this.invtId;
	}

	public void setInvtId(String invtId) {
		this.invtId = invtId;
	}

	@Column(name = "INVT_NAME", nullable = false, length = 100)
	public String getInvtName() {
		return this.invtName;
	}

	public void setInvtName(String invtName) {
		this.invtName = invtName;
	}

	@Column(name = "INVT_DESC")
	public String getInvtDesc() {
		return this.invtDesc;
	}

	public void setInvtDesc(String invtDesc) {
		this.invtDesc = invtDesc;
	}

	@Column(name = "INVT_DESC_OTH", length = 512)
	public String getInvtDescOth() {
		return this.invtDescOth;
	}

	public void setInvtDescOth(String invtDescOth) {
		this.invtDescOth = invtDescOth;
	}

	@Column(name = "INVT_STATUS", length = 1)
	public Character getInvtStatus() {
		return this.invtStatus;
	}

	public void setInvtStatus(Character invtStatus) {
		this.invtStatus = invtStatus;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INVT_DT_CREATE", length = 19)
	public Date getInvtDtCreate() {
		return this.invtDtCreate;
	}

	public void setInvtDtCreate(Date invtDtCreate) {
		this.invtDtCreate = invtDtCreate;
	}

	@Column(name = "INVT_UID_CREATE", length = 35)
	public String getInvtUidCreate() {
		return this.invtUidCreate;
	}

	public void setInvtUidCreate(String invtUidCreate) {
		this.invtUidCreate = invtUidCreate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INVT_DT_LUPD", length = 19)
	public Date getInvtDtLupd() {
		return this.invtDtLupd;
	}

	public void setInvtDtLupd(Date invtDtLupd) {
		this.invtDtLupd = invtDtLupd;
	}

	@Column(name = "INVT_UID_LUPD", length = 35)
	public String getInvtUidLupd() {
		return this.invtUidLupd;
	}

	public void setInvtUidLupd(String invtUidLupd) {
		this.invtUidLupd = invtUidLupd;
	}
}
