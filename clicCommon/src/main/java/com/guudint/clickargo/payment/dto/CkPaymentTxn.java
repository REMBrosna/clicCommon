package com.guudint.clickargo.payment.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.guudint.clickargo.master.dto.CkMstPaymentType;
import com.guudint.clickargo.master.dto.CkMstServiceType;
import com.guudint.clickargo.payment.model.TCkPaymentTxn;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.common.dto.AbstractDTO;
import com.vcc.camelone.master.dto.MstCurrency;

public class CkPaymentTxn extends AbstractDTO<CkPaymentTxn, TCkPaymentTxn> {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = 3954550320769517629L;

	// Attributes
	/////////////
	private String ptxId;
	private CkMstPaymentType TCkMstPaymentType;
	private CkMstServiceType TCkMstServiceType;
	private CoreAccn TCoreAccnByPtxPayee;
	private CoreAccn TCoreAccnByPtxPayer;
	private MstCurrency TMstCurrency;
	private String ptxSvcRef;
	private String ptxMerchantBank;
	private String ptxPayerBankAccn;
	private String ptxPayeeBankAccn;
	private String ptxPayeeBankAccnName;
	private BigDecimal ptxAmount;
	private String ptxBankRef;
	private String ptxPaymentState;
	private Date ptxDtPaid;
	private Character ptxStatus;
	private Date ptxDtCreate;
	private String ptxUidCreate;
	private Date ptxDtLupd;
	private String ptxUidLupd;
	private Date ptxDtFinApproved;
	private String ptxUidFinApproved;
	private Date ptxDtFinVerified;
	private String ptxUidFinVerified;
	private Date ptxDtDue;

	private String history;

	// Constructors
	///////////////
	public CkPaymentTxn() {
	}

	/**
	 * @param entity
	 */
	public CkPaymentTxn(TCkPaymentTxn entity) {
		super(entity);
	}

	/**
	 * @param ptxId
	 */
	public CkPaymentTxn(String ptxId) {
		this.ptxId = ptxId;
	}

	/**
	 * @param ptxId
	 * @param TCkMstPaymentType
	 * @param TCkMstServiceType
	 * @param TCoreAccnByPtxPayee
	 * @param TCoreAccnByPtxPayer
	 * @param TMstCurrency
	 * @param ptxSvcRef
	 * @param ptxMerchantBank
	 * @param ptxPayerBankAccn
	 * @param ptxPayeeBankAccn
	 * @param ptxAmount
	 * @param ptxBankRef
	 * @param ptxPaymentState
	 * @param ptxStatus
	 * @param ptxDtCreate
	 * @param ptxUidCreate
	 * @param ptxDtLupd
	 * @param ptxUidLupd
	 */
	public CkPaymentTxn(String ptxId, CkMstPaymentType TCkMstPaymentType, CkMstServiceType TCkMstServiceType,
			CoreAccn TCoreAccnByPtxPayee, CoreAccn TCoreAccnByPtxPayer, MstCurrency TMstCurrency, String ptxSvcRef,
			String ptxMerchantBank, String ptxPayerBankAccn, String ptxPayeeBankAccn, BigDecimal ptxAmount,
			String ptxBankRef, String ptxPaymentState, Character ptxStatus, Date ptxDtCreate, String ptxUidCreate,
			Date ptxDtLupd, String ptxUidLupd) {
		this.ptxId = ptxId;
		this.TCkMstPaymentType = TCkMstPaymentType;
		this.TCkMstServiceType = TCkMstServiceType;
		this.TCoreAccnByPtxPayee = TCoreAccnByPtxPayee;
		this.TCoreAccnByPtxPayer = TCoreAccnByPtxPayer;
		this.TMstCurrency = TMstCurrency;
		this.ptxSvcRef = ptxSvcRef;
		this.ptxMerchantBank = ptxMerchantBank;
		this.ptxPayerBankAccn = ptxPayerBankAccn;
		this.ptxPayeeBankAccn = ptxPayeeBankAccn;
		this.ptxAmount = ptxAmount;
		this.ptxBankRef = ptxBankRef;
		this.ptxPaymentState = ptxPaymentState;
		this.ptxStatus = ptxStatus;
		this.ptxDtCreate = ptxDtCreate;
		this.ptxUidCreate = ptxUidCreate;
		this.ptxDtLupd = ptxDtLupd;
		this.ptxUidLupd = ptxUidLupd;
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
	public int compareTo(CkPaymentTxn o) {
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
	 * @return the ptxId
	 */
	public String getPtxId() {
		return ptxId;
	}

	/**
	 * @param ptxId the ptxId to set
	 */
	public void setPtxId(String ptxId) {
		this.ptxId = ptxId;
	}

	/**
	 * @return the tCkMstPaymentType
	 */
	public CkMstPaymentType getTCkMstPaymentType() {
		return TCkMstPaymentType;
	}

	/**
	 * @param tCkMstPaymentType the tCkMstPaymentType to set
	 */
	public void setTCkMstPaymentType(CkMstPaymentType tCkMstPaymentType) {
		TCkMstPaymentType = tCkMstPaymentType;
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
	 * @return the tCoreAccnByPtxPayee
	 */
	public CoreAccn getTCoreAccnByPtxPayee() {
		return TCoreAccnByPtxPayee;
	}

	/**
	 * @param tCoreAccnByPtxPayee the tCoreAccnByPtxPayee to set
	 */
	public void setTCoreAccnByPtxPayee(CoreAccn tCoreAccnByPtxPayee) {
		TCoreAccnByPtxPayee = tCoreAccnByPtxPayee;
	}

	/**
	 * @return the tCoreAccnByPtxPayer
	 */
	public CoreAccn getTCoreAccnByPtxPayer() {
		return TCoreAccnByPtxPayer;
	}

	/**
	 * @param tCoreAccnByPtxPayer the tCoreAccnByPtxPayer to set
	 */
	public void setTCoreAccnByPtxPayer(CoreAccn tCoreAccnByPtxPayer) {
		TCoreAccnByPtxPayer = tCoreAccnByPtxPayer;
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
	 * @return the ptxSvcRef
	 */
	public String getPtxSvcRef() {
		return ptxSvcRef;
	}

	/**
	 * @param ptxSvcRef the ptxSvcRef to set
	 */
	public void setPtxSvcRef(String ptxSvcRef) {
		this.ptxSvcRef = ptxSvcRef;
	}

	/**
	 * @return the ptxMerchantBank
	 */
	public String getPtxMerchantBank() {
		return ptxMerchantBank;
	}

	/**
	 * @param ptxMerchantBank the ptxMerchantBank to set
	 */
	public void setPtxMerchantBank(String ptxMerchantBank) {
		this.ptxMerchantBank = ptxMerchantBank;
	}

	/**
	 * @return the ptxPayerBankAccn
	 */
	public String getPtxPayerBankAccn() {
		return ptxPayerBankAccn;
	}

	/**
	 * @param ptxPayerBankAccn the ptxPayerBankAccn to set
	 */
	public void setPtxPayerBankAccn(String ptxPayerBankAccn) {
		this.ptxPayerBankAccn = ptxPayerBankAccn;
	}

	/**
	 * @return the ptxPayeeBankAccn
	 */
	public String getPtxPayeeBankAccn() {
		return ptxPayeeBankAccn;
	}

	/**
	 * @param ptxPayeeBankAccn the ptxPayeeBankAccn to set
	 */
	public void setPtxPayeeBankAccn(String ptxPayeeBankAccn) {
		this.ptxPayeeBankAccn = ptxPayeeBankAccn;
	}

	/**
	 * @return the ptxAmount
	 */
	public BigDecimal getPtxAmount() {
		return ptxAmount;
	}

	/**
	 * @param ptxAmount the ptxAmount to set
	 */
	public void setPtxAmount(BigDecimal ptxAmount) {
		this.ptxAmount = ptxAmount;
	}

	/**
	 * @return the ptxBankRef
	 */
	public String getPtxBankRef() {
		return ptxBankRef;
	}

	/**
	 * @param ptxBankRef the ptxBankRef to set
	 */
	public void setPtxBankRef(String ptxBankRef) {
		this.ptxBankRef = ptxBankRef;
	}

	/**
	 * @return the ptxPaymentState
	 */
	public String getPtxPaymentState() {
		return ptxPaymentState;
	}

	/**
	 * @param ptxPaymentState the ptxPaymentState to set
	 */
	public void setPtxPaymentState(String ptxPaymentState) {
		this.ptxPaymentState = ptxPaymentState;
	}

	/**
	 * @return the ptxStatus
	 */
	public Character getPtxStatus() {
		return ptxStatus;
	}

	/**
	 * @param ptxStatus the ptxStatus to set
	 */
	public void setPtxStatus(Character ptxStatus) {
		this.ptxStatus = ptxStatus;
	}

	/**
	 * @return the ptxDtCreate
	 */
	public Date getPtxDtCreate() {
		return ptxDtCreate;
	}

	/**
	 * @param ptxDtCreate the ptxDtCreate to set
	 */
	public void setPtxDtCreate(Date ptxDtCreate) {
		this.ptxDtCreate = ptxDtCreate;
	}

	/**
	 * @return the ptxUidCreate
	 */
	public String getPtxUidCreate() {
		return ptxUidCreate;
	}

	/**
	 * @param ptxUidCreate the ptxUidCreate to set
	 */
	public void setPtxUidCreate(String ptxUidCreate) {
		this.ptxUidCreate = ptxUidCreate;
	}

	/**
	 * @return the ptxDtLupd
	 */
	public Date getPtxDtLupd() {
		return ptxDtLupd;
	}

	/**
	 * @param ptxDtLupd the ptxDtLupd to set
	 */
	public void setPtxDtLupd(Date ptxDtLupd) {
		this.ptxDtLupd = ptxDtLupd;
	}

	/**
	 * @return the ptxUidLupd
	 */
	public String getPtxUidLupd() {
		return ptxUidLupd;
	}

	/**
	 * @param ptxUidLupd the ptxUidLupd to set
	 */
	public void setPtxUidLupd(String ptxUidLupd) {
		this.ptxUidLupd = ptxUidLupd;
	}

	/**
	 * @return the ptxDtPaid
	 */
	public Date getPtxDtPaid() {
		return ptxDtPaid;
	}

	/**
	 * @param ptxDtPaid the ptxDtPaid to set
	 */
	public void setPtxDtPaid(Date ptxDtPaid) {
		this.ptxDtPaid = ptxDtPaid;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public Date getPtxDtFinApproved() {
		return ptxDtFinApproved;
	}

	public void setPtxDtFinApproved(Date ptxDtFinApproved) {
		this.ptxDtFinApproved = ptxDtFinApproved;
	}

	public String getPtxUidFinApproved() {
		return ptxUidFinApproved;
	}

	public void setPtxUidFinApproved(String ptxUidFinApproved) {
		this.ptxUidFinApproved = ptxUidFinApproved;
	}

	public Date getPtxDtFinVerified() {
		return ptxDtFinVerified;
	}

	public void setPtxDtFinVerified(Date ptxDtFinVerified) {
		this.ptxDtFinVerified = ptxDtFinVerified;
	}

	public String getPtxUidFinVerified() {
		return ptxUidFinVerified;
	}

	public void setPtxUidFinVerified(String ptxUidFinVerified) {
		this.ptxUidFinVerified = ptxUidFinVerified;
	}

	public Date getPtxDtDue() {
		return ptxDtDue;
	}

	public void setPtxDtDue(Date ptxDtDue) {
		this.ptxDtDue = ptxDtDue;
	}

	public String getPtxPayeeBankAccnName() {
		return ptxPayeeBankAccnName;
	}

	public void setPtxPayeeBankAccnName(String ptxPayeeBankAccnName) {
		this.ptxPayeeBankAccnName = ptxPayeeBankAccnName;
	}

}
