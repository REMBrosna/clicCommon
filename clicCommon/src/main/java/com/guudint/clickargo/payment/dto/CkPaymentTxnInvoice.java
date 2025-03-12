package com.guudint.clickargo.payment.dto;

import java.util.Date;

import com.guudint.clickargo.payment.model.TCkPaymentTxnInvoice;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkPaymentTxnInvoice extends AbstractDTO<CkPaymentTxnInvoice, TCkPaymentTxnInvoice> {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = -2964572944756531489L;

	// Attributes
	//////////////
	private String ptiId;
	private CkPaymentTxn TCkPaymentTxn;
	private String ptiInvId;
	private Character ptiStatus;
	private Date ptiDtCreate;
	private String ptiUidCreate;
	private Date ptiDtLupd;
	private String ptiUidLupd;

	// Constructors
	///////////////
	public CkPaymentTxnInvoice() {
	}

	/**
	 * @param entity
	 */
	public CkPaymentTxnInvoice(TCkPaymentTxnInvoice entity) {
		super(entity);
	}

	/**
	 * @param ptiId
	 * @param TCkPaymentTxn
	 * @param ptiInvId
	 */
	public CkPaymentTxnInvoice(String ptiId, CkPaymentTxn TCkPaymentTxn, String ptiInvId) {
		this.ptiId = ptiId;
		this.TCkPaymentTxn = TCkPaymentTxn;
		this.ptiInvId = ptiInvId;
	}

	/**
	 * @param ptiId
	 * @param TCkPaymentTxn
	 * @param ptiInvId
	 * @param ptiStatus
	 * @param ptiDtCreate
	 * @param ptiUidCreate
	 * @param ptiDtLupd
	 * @param ptiUidLupd
	 */
	public CkPaymentTxnInvoice(String ptiId, CkPaymentTxn TCkPaymentTxn, String ptiInvId, Character ptiStatus,
			Date ptiDtCreate, String ptiUidCreate, Date ptiDtLupd, String ptiUidLupd) {
		this.ptiId = ptiId;
		this.TCkPaymentTxn = TCkPaymentTxn;
		this.ptiInvId = ptiInvId;
		this.ptiStatus = ptiStatus;
		this.ptiDtCreate = ptiDtCreate;
		this.ptiUidCreate = ptiUidCreate;
		this.ptiDtLupd = ptiDtLupd;
		this.ptiUidLupd = ptiUidLupd;
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
	public int compareTo(CkPaymentTxnInvoice o) {
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
	//////////////
	/**
	 * @return the ptiId
	 */
	public String getPtiId() {
		return ptiId;
	}

	/**
	 * @param ptiId the ptiId to set
	 */
	public void setPtiId(String ptiId) {
		this.ptiId = ptiId;
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
	 * @return the ptiInvId
	 */
	public String getPtiInvId() {
		return ptiInvId;
	}

	/**
	 * @param ptiInvId the ptiInvId to set
	 */
	public void setPtiInvId(String ptiInvId) {
		this.ptiInvId = ptiInvId;
	}

	/**
	 * @return the ptiStatus
	 */
	public Character getPtiStatus() {
		return ptiStatus;
	}

	/**
	 * @param ptiStatus the ptiStatus to set
	 */
	public void setPtiStatus(Character ptiStatus) {
		this.ptiStatus = ptiStatus;
	}

	/**
	 * @return the ptiDtCreate
	 */
	public Date getPtiDtCreate() {
		return ptiDtCreate;
	}

	/**
	 * @param ptiDtCreate the ptiDtCreate to set
	 */
	public void setPtiDtCreate(Date ptiDtCreate) {
		this.ptiDtCreate = ptiDtCreate;
	}

	/**
	 * @return the ptiUidCreate
	 */
	public String getPtiUidCreate() {
		return ptiUidCreate;
	}

	/**
	 * @param ptiUidCreate the ptiUidCreate to set
	 */
	public void setPtiUidCreate(String ptiUidCreate) {
		this.ptiUidCreate = ptiUidCreate;
	}

	/**
	 * @return the ptiDtLupd
	 */
	public Date getPtiDtLupd() {
		return ptiDtLupd;
	}

	/**
	 * @param ptiDtLupd the ptiDtLupd to set
	 */
	public void setPtiDtLupd(Date ptiDtLupd) {
		this.ptiDtLupd = ptiDtLupd;
	}

	/**
	 * @return the ptiUidLupd
	 */
	public String getPtiUidLupd() {
		return ptiUidLupd;
	}

	/**
	 * @param ptiUidLupd the ptiUidLupd to set
	 */
	public void setPtiUidLupd(String ptiUidLupd) {
		this.ptiUidLupd = ptiUidLupd;
	}
}
