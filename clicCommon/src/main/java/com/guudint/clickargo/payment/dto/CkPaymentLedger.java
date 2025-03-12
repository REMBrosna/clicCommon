package com.guudint.clickargo.payment.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import com.guudint.clickargo.master.dto.CkMstPaymentType;
import com.guudint.clickargo.master.dto.CkMstServiceType;
import com.guudint.clickargo.payment.model.TCkPaymentLedger;
import com.guudint.clickargo.validator.ValidationGroup.CreateValid;
import com.guudint.clickargo.validator.ValidationGroup.SubmitValid;
import com.guudint.clickargo.validator.ValidationGroup.UpdateValid;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.common.dto.AbstractDTO;
import com.vcc.camelone.master.dto.MstCurrency;

public class CkPaymentLedger extends AbstractDTO<CkPaymentLedger, TCkPaymentLedger> {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = 3103545240167614951L;

	// Attributes
	/////////////
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 35, message = "{valid.payment.pylId.maxLength}")
	private String pylId;
	private CkMstPaymentType TCkMstPaymentType;
	private CkMstServiceType TCkMstServiceType;
	private CoreAccn TCoreAccnByPylPayee;
	private CoreAccn TCoreAccnByPylPayer;
	private MstCurrency TMstCurrency;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 35, message = "{valid.payment.pylServiceReference.maxLength}")
	private String pylServiceReference;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 35, message = "{valid.payment.pylMerchantBank.maxLength}")
	private String pylMerchantBank;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 255, message = "{valid.payment.pylPayerBankAccn.maxLength}")
	private String pylPayerBankAccn;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 255, message = "{valid.payment.pylPayeeBankAccn.maxLength}")
	private String pylPayeeBankAccn;
	@Digits(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, integer = 13, fraction = 2)
	private BigDecimal pylAmount;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 255, message = "{valid.payment.pylBankRef.maxLength}")
	private String pylBankRef;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 35, message = "{valid.payment.pylPaymentState.maxLength}")
	private String pylPaymentState;
	private Character pylStatus;
	private Date pylDtCreate;
	private String pylUidCreate;
	private Date pylDtLupd;
	private String pylUidLupd;

	// Constructors
	///////////////
	public CkPaymentLedger() {
	}

	/**
	 * @param entity
	 */
	public CkPaymentLedger(TCkPaymentLedger entity) {
		super(entity);
	}

	/**
	 * @param pylId
	 */
	public CkPaymentLedger(String pylId) {
		this.pylId = pylId;
	}

	/**
	 * @param pylId
	 * @param TCkMstPaymentType
	 * @param TCkMstServiceType
	 * @param TCoreAccnByPylPayee
	 * @param TCoreAccnByPylPayer
	 * @param TMstCurrency
	 * @param pylServiceReference
	 * @param pylMerchantBank
	 * @param pylPayerBankAccn
	 * @param pylPayeeBankAccn
	 * @param pylAmount
	 * @param pylBankRef
	 * @param pylPaymentState
	 * @param pylStatus
	 * @param pylDtCreate
	 * @param pylUidCreate
	 * @param pylDtLupd
	 * @param pylUidLupd
	 * @param TCkPaymentAudits
	 */
	public CkPaymentLedger(String pylId, CkMstPaymentType TCkMstPaymentType, CkMstServiceType TCkMstServiceType,
			CoreAccn TCoreAccnByPylPayee, CoreAccn TCoreAccnByPylPayer, MstCurrency TMstCurrency,
			String pylServiceReference, String pylMerchantBank, String pylPayerBankAccn, String pylPayeeBankAccn,
			BigDecimal pylAmount, String pylBankRef, String pylPaymentState, Character pylStatus, Date pylDtCreate,
			String pylUidCreate, Date pylDtLupd, String pylUidLupd, Set<CkPaymentAudit> TCkPaymentAudits) {
		this.pylId = pylId;
		this.TCkMstPaymentType = TCkMstPaymentType;
		this.TCkMstServiceType = TCkMstServiceType;
		this.TCoreAccnByPylPayee = TCoreAccnByPylPayee;
		this.TCoreAccnByPylPayer = TCoreAccnByPylPayer;
		this.TMstCurrency = TMstCurrency;
		this.pylServiceReference = pylServiceReference;
		this.pylMerchantBank = pylMerchantBank;
		this.pylPayerBankAccn = pylPayerBankAccn;
		this.pylPayeeBankAccn = pylPayeeBankAccn;
		this.pylAmount = pylAmount;
		this.pylBankRef = pylBankRef;
		this.pylPaymentState = pylPaymentState;
		this.pylStatus = pylStatus;
		this.pylDtCreate = pylDtCreate;
		this.pylUidCreate = pylUidCreate;
		this.pylDtLupd = pylDtLupd;
		this.pylUidLupd = pylUidLupd;
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
	public int compareTo(CkPaymentLedger o) {
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
	 * @return the pylId
	 */
	public String getPylId() {
		return pylId;
	}

	/**
	 * @param pylId the pylId to set
	 */
	public void setPylId(String pylId) {
		this.pylId = pylId;
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
	 * @return the tCoreAccnByPylPayee
	 */
	public CoreAccn getTCoreAccnByPylPayee() {
		return TCoreAccnByPylPayee;
	}

	/**
	 * @param tCoreAccnByPylPayee the tCoreAccnByPylPayee to set
	 */
	public void setTCoreAccnByPylPayee(CoreAccn tCoreAccnByPylPayee) {
		TCoreAccnByPylPayee = tCoreAccnByPylPayee;
	}

	/**
	 * @return the tCoreAccnByPylPayer
	 */
	public CoreAccn getTCoreAccnByPylPayer() {
		return TCoreAccnByPylPayer;
	}

	/**
	 * @param tCoreAccnByPylPayer the tCoreAccnByPylPayer to set
	 */
	public void setTCoreAccnByPylPayer(CoreAccn tCoreAccnByPylPayer) {
		TCoreAccnByPylPayer = tCoreAccnByPylPayer;
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
	 * @return the pylServiceReference
	 */
	public String getPylServiceReference() {
		return pylServiceReference;
	}

	/**
	 * @param pylServiceReference the pylServiceReference to set
	 */
	public void setPylServiceReference(String pylServiceReference) {
		this.pylServiceReference = pylServiceReference;
	}

	/**
	 * @return the pylMerchantBank
	 */
	public String getPylMerchantBank() {
		return pylMerchantBank;
	}

	/**
	 * @param pylMerchantBank the pylMerchantBank to set
	 */
	public void setPylMerchantBank(String pylMerchantBank) {
		this.pylMerchantBank = pylMerchantBank;
	}

	/**
	 * @return the pylPayerBankAccn
	 */
	public String getPylPayerBankAccn() {
		return pylPayerBankAccn;
	}

	/**
	 * @param pylPayerBankAccn the pylPayerBankAccn to set
	 */
	public void setPylPayerBankAccn(String pylPayerBankAccn) {
		this.pylPayerBankAccn = pylPayerBankAccn;
	}

	/**
	 * @return the pylPayeeBankAccn
	 */
	public String getPylPayeeBankAccn() {
		return pylPayeeBankAccn;
	}

	/**
	 * @param pylPayeeBankAccn the pylPayeeBankAccn to set
	 */
	public void setPylPayeeBankAccn(String pylPayeeBankAccn) {
		this.pylPayeeBankAccn = pylPayeeBankAccn;
	}

	/**
	 * @return the pylAmount
	 */
	public BigDecimal getPylAmount() {
		return pylAmount;
	}

	/**
	 * @param pylAmount the pylAmount to set
	 */
	public void setPylAmount(BigDecimal pylAmount) {
		this.pylAmount = pylAmount;
	}

	/**
	 * @return the pylBankRef
	 */
	public String getPylBankRef() {
		return pylBankRef;
	}

	/**
	 * @param pylBankRef the pylBankRef to set
	 */
	public void setPylBankRef(String pylBankRef) {
		this.pylBankRef = pylBankRef;
	}

	/**
	 * @return the pylPaymentState
	 */
	public String getPylPaymentState() {
		return pylPaymentState;
	}

	/**
	 * @param pylPaymentState the pylPaymentState to set
	 */
	public void setPylPaymentState(String pylPaymentState) {
		this.pylPaymentState = pylPaymentState;
	}

	/**
	 * @return the pylStatus
	 */
	public Character getPylStatus() {
		return pylStatus;
	}

	/**
	 * @param pylStatus the pylStatus to set
	 */
	public void setPylStatus(Character pylStatus) {
		this.pylStatus = pylStatus;
	}

	/**
	 * @return the pylDtCreate
	 */
	public Date getPylDtCreate() {
		return pylDtCreate;
	}

	/**
	 * @param pylDtCreate the pylDtCreate to set
	 */
	public void setPylDtCreate(Date pylDtCreate) {
		this.pylDtCreate = pylDtCreate;
	}

	/**
	 * @return the pylUidCreate
	 */
	public String getPylUidCreate() {
		return pylUidCreate;
	}

	/**
	 * @param pylUidCreate the pylUidCreate to set
	 */
	public void setPylUidCreate(String pylUidCreate) {
		this.pylUidCreate = pylUidCreate;
	}

	/**
	 * @return the pylDtLupd
	 */
	public Date getPylDtLupd() {
		return pylDtLupd;
	}

	/**
	 * @param pylDtLupd the pylDtLupd to set
	 */
	public void setPylDtLupd(Date pylDtLupd) {
		this.pylDtLupd = pylDtLupd;
	}

	/**
	 * @return the pylUidLupd
	 */
	public String getPylUidLupd() {
		return pylUidLupd;
	}

	/**
	 * @param pylUidLupd the pylUidLupd to set
	 */
	public void setPylUidLupd(String pylUidLupd) {
		this.pylUidLupd = pylUidLupd;
	}
}
