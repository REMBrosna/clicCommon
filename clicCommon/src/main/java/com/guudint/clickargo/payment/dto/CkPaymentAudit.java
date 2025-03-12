package com.guudint.clickargo.payment.dto;

import java.util.Date;

import com.guudint.clickargo.master.dto.CkMstPaymentAuditType;
import com.guudint.clickargo.payment.model.TCkPaymentAudit;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkPaymentAudit extends AbstractDTO<CkPaymentAudit, TCkPaymentAudit> {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = -1532967946262795165L;

	// Attributes
	/////////////
	private String pyaId;
	private CkMstPaymentAuditType TCkMstPaymentAuditType;
	private String pyaReference;
	private String pyaReq;
	private String pyaResp;
	private String pyaCb;
	private String pyaRemark;
	private String pyaState;
	private Character pyaStatus;
	private Date pyaDtCreate;
	private String pyaUidCreate;
	private Date pyaDtLupd;
	private String pyaUidLupd;

	// Constructors
	///////////////
	public CkPaymentAudit() {
	}

	/**
	 * @param entity
	 */
	public CkPaymentAudit(TCkPaymentAudit entity) {
		super(entity);
	}

	/**
	 * @param pyaId
	 * @param TCkMstPaymentAuditType
	 * @param TCkPaymentLedger
	 */
	public CkPaymentAudit(String pyaId, CkMstPaymentAuditType TCkMstPaymentAuditType) {
		this.pyaId = pyaId;
		this.TCkMstPaymentAuditType = TCkMstPaymentAuditType;
	}

	/**
	 * @param pyaId
	 * @param TCkMstPaymentAuditType
	 * @param TCkPaymentLedger
	 * @param pyaReference
	 * @param pyaReq
	 * @param pyaResp
	 * @param pyaCb
	 * @param pyaRemark
	 * @param pyaStatus
	 * @param pyaDtCreate
	 * @param pyaUidCreate
	 * @param pyaDtLupd
	 * @param pyaUidLupd
	 */
	public CkPaymentAudit(String pyaId, CkMstPaymentAuditType TCkMstPaymentAuditType, String pyaReference,
			String pyaReq, String pyaResp, String pyaCb, String pyaRemark, Character pyaStatus, Date pyaDtCreate,
			String pyaUidCreate, Date pyaDtLupd, String pyaUidLupd) {
		this.pyaId = pyaId;
		this.TCkMstPaymentAuditType = TCkMstPaymentAuditType;
		this.pyaReference = pyaReference;
		this.pyaReq = pyaReq;
		this.pyaResp = pyaResp;
		this.pyaCb = pyaCb;
		this.pyaRemark = pyaRemark;
		this.pyaStatus = pyaStatus;
		this.pyaDtCreate = pyaDtCreate;
		this.pyaUidCreate = pyaUidCreate;
		this.pyaDtLupd = pyaDtLupd;
		this.pyaUidLupd = pyaUidLupd;
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
	public int compareTo(CkPaymentAudit o) {
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
	/**
	 * @return the pyaId
	 */
	public String getPyaId() {
		return pyaId;
	}

	/**
	 * @param pyaId the pyaId to set
	 */
	public void setPyaId(String pyaId) {
		this.pyaId = pyaId;
	}

	/**
	 * @return the tCkMstPaymentAuditType
	 */
	public CkMstPaymentAuditType getTCkMstPaymentAuditType() {
		return TCkMstPaymentAuditType;
	}

	/**
	 * @param tCkMstPaymentAuditType the tCkMstPaymentAuditType to set
	 */
	public void setTCkMstPaymentAuditType(CkMstPaymentAuditType tCkMstPaymentAuditType) {
		TCkMstPaymentAuditType = tCkMstPaymentAuditType;
	}

	public String getPyaState() {
		return pyaState;
	}

	public void setPyaState(String pyaState) {
		this.pyaState = pyaState;
	}

	/**
	 * @return the pyaReference
	 */
	public String getPyaReference() {
		return pyaReference;
	}

	/**
	 * @param pyaReference the pyaReference to set
	 */
	public void setPyaReference(String pyaReference) {
		this.pyaReference = pyaReference;
	}

	/**
	 * @return the pyaReq
	 */
	public String getPyaReq() {
		return pyaReq;
	}

	/**
	 * @param pyaReq the pyaReq to set
	 */
	public void setPyaReq(String pyaReq) {
		this.pyaReq = pyaReq;
	}

	/**
	 * @return the pyaResp
	 */
	public String getPyaResp() {
		return pyaResp;
	}

	/**
	 * @param pyaResp the pyaResp to set
	 */
	public void setPyaResp(String pyaResp) {
		this.pyaResp = pyaResp;
	}

	/**
	 * @return the pyaCb
	 */
	public String getPyaCb() {
		return pyaCb;
	}

	/**
	 * @param pyaCb the pyaCb to set
	 */
	public void setPyaCb(String pyaCb) {
		this.pyaCb = pyaCb;
	}

	/**
	 * @return the pyaRemark
	 */
	public String getPyaRemark() {
		return pyaRemark;
	}

	/**
	 * @param pyaRemark the pyaRemark to set
	 */
	public void setPyaRemark(String pyaRemark) {
		this.pyaRemark = pyaRemark;
	}

	/**
	 * @return the pyaStatus
	 */
	public Character getPyaStatus() {
		return pyaStatus;
	}

	/**
	 * @param pyaStatus the pyaStatus to set
	 */
	public void setPyaStatus(Character pyaStatus) {
		this.pyaStatus = pyaStatus;
	}

	/**
	 * @return the pyaDtCreate
	 */
	public Date getPyaDtCreate() {
		return pyaDtCreate;
	}

	/**
	 * @param pyaDtCreate the pyaDtCreate to set
	 */
	public void setPyaDtCreate(Date pyaDtCreate) {
		this.pyaDtCreate = pyaDtCreate;
	}

	/**
	 * @return the pyaUidCreate
	 */
	public String getPyaUidCreate() {
		return pyaUidCreate;
	}

	/**
	 * @param pyaUidCreate the pyaUidCreate to set
	 */
	public void setPyaUidCreate(String pyaUidCreate) {
		this.pyaUidCreate = pyaUidCreate;
	}

	/**
	 * @return the pyaDtLupd
	 */
	public Date getPyaDtLupd() {
		return pyaDtLupd;
	}

	/**
	 * @param pyaDtLupd the pyaDtLupd to set
	 */
	public void setPyaDtLupd(Date pyaDtLupd) {
		this.pyaDtLupd = pyaDtLupd;
	}

	/**
	 * @return the pyaUidLupd
	 */
	public String getPyaUidLupd() {
		return pyaUidLupd;
	}

	/**
	 * @param pyaUidLupd the pyaUidLupd to set
	 */
	public void setPyaUidLupd(String pyaUidLupd) {
		this.pyaUidLupd = pyaUidLupd;
	}
}
