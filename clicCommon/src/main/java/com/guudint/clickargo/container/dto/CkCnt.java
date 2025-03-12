package com.guudint.clickargo.container.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import com.guudint.clickargo.container.model.TCkCnt;
import com.guudint.clickargo.validator.ValidationGroup.CreateValid;
import com.guudint.clickargo.validator.ValidationGroup.SubmitValid;
import com.guudint.clickargo.validator.ValidationGroup.UpdateValid;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkCnt extends AbstractDTO<CkCnt, TCkCnt> {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = -2009135751913417340L;
	
	// Attributes
	/////////////
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 35, message = "{valid.container.cntId.maxLength}")
	private String cntId;
//	private CkMstCntSize TCkMstCntSize;
	private String cntSize;
//	private CkMstCntType TCkMstCntType;
	private String cntType;
//	private MstPort TMstPortByCntPod;
//	private MstPort TMstPortByCntPol;
	private String cntPod;
	private String cntPol;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 255, message = "{valid.container.cntNo.maxLength}")
	
	private Date cntDtDischarge;
	private Date cntDtLoad;
	private Character cntDangerousGood;
	
	private String cntNo;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 35, message = "{valid.container.cntIsoCode.maxLength}")
	private String cntIsoCode;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 35, message = "{valid.container.cntImoCode.maxLength}")
	private String cntImoCode;
	@DecimalMax(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, value = "8388607", inclusive = true, message = "{valid.container.cntHeight.decimal.max}")
	private Integer cntHeight;
	@DecimalMax(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, value = "8388607", inclusive = true, message = "{valid.container.cntWeight.decimal.max}")
	private Integer cntWeight;
	@DecimalMax(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, value = "8388607", inclusive = true, message = "{valid.container.cntBruto.decimal.max}")
	private Integer cntBruto;
	private Character cntFullEmptyIndr;
	private Character cntImdg;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 255, message = "{valid.container.cntImdgUnNo.maxLength}")
	private String cntImdgUnNo;
	private Byte cntImdgImdgClass;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 255, message = "{valid.container.cntImdgValue.maxLength}")
	private String cntImdgValue;
	private Character cntRfrIndr;
	@DecimalMax(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, value = "32767", inclusive = true, message = "{valid.container.cntRfrRequiredVoltage.decimal.max}")
	private Short cntRfrRequiredVoltage;
	@Digits(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, integer = 3, fraction = 2)
	private BigDecimal cntRfrRequiredTemperature;
	private Date cntRfrStartPlug;
	private Date cntRfrStopPlug;
	@DecimalMax(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, value = "8388607", inclusive = true, message = "{valid.container.cntOogTop.decimal.max}")
	private Integer cntOogTop;
	@DecimalMax(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, value = "8388607", inclusive = true, message = "{valid.container.cntOogLeft.decimal.max}")
	private Integer cntOogLeft;
	@DecimalMax(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, value = "8388607", inclusive = true, message = "{valid.container.cntOogRight.decimal.max}")
	private Integer cntOogRight;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 255, message = "{valid.container.cntSealNo.maxLength}")
	private String cntSealNo;
	private Date cntStacking;
	private Date cntGateIn;
	private Date cntGateOut;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 255, message = "{valid.container.cntLocation.maxLength}")
	private String cntLocation;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 255, message = "{valid.container.cntVesselNo.maxLength}")
	private String cntVesselNo;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 255, message = "{valid.container.cntVesselName.maxLength}")
	private String cntVesselName;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 255, message = "{valid.container.cntVoyageNo.maxLength}")
	private String cntVoyageNo;
	private Date cntVesselEta;
	private Date cntVesselAta;
	private Date cntVesselEtd;
	private Date cntVesselAtd;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 1024, message = "{valid.container.cntMarksNo.maxLength}")
	private String cntMarksNo;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 1024, message = "{valid.container.cntNoPackages.maxLength}")
	private String cntNoPackages;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 16777215, message = "{valid.container.cntDescription.maxLength}")
	private String cntDescription;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 255, message = "{valid.container.cntOwner.maxLength}")
	private String cntOwner;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 255, message = "{valid.container.cntTaxid.maxLength}")
	private String cntTaxid;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 255, message = "{valid.container.cntSender.maxLength}")
	private String cntSender;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 255, message = "{valid.container.cntForwarder.maxLength}")
	private String cntForwarder;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 255, message = "{valid.container.cntCcacNo.maxLength}")
	private String cntCcacNo;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 255, message = "{valid.container.cntBlAwbNo.maxLength}")
	private String cntBlAwbNo;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 255, message = "{valid.container.cntStatusCustoms.maxLength}")
	private String cntStatusCustoms;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 255, message = "{valid.container.cntStatusQuarantine.maxLength}")
	private String cntStatusQuarantine;
	private Date cntValidDate;
	private Date cntDoLastPaidThru;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 512, message = "{valid.container.cntRemarks.maxLength}")
	private String cntRemarks;
	private Character cntStatus;
	private Date cntDtCreate;
	private String cntUidCreate;
	private Date cntDtLupd;
	private String cntUidLupd;

	// Constructors
	///////////////
	public CkCnt() {
	}
	
	/**
	 * @param entity
	 */
	public CkCnt(TCkCnt entity) {
		super(entity);
	}
	

	/**
	 * @param cntId
	 * @param cntNo
	 */
	public CkCnt(String cntId, String cntNo) {
		this.cntId = cntId;
		this.cntNo = cntNo;
	}

	/**
	 * 
	 * @param cntId
	 * @param cntSize
	 * @param cntType
	 * @param cntPod
	 * @param cntPol
	 * @param cntDtDischarge
	 * @param cntDtLoad
	 * @param cntDangerousGood
	 * @param cntNo
	 * @param cntIsoCode
	 * @param cntImoCode
	 * @param cntHeight
	 * @param cntWeight
	 * @param cntBruto
	 * @param cntFullEmptyIndr
	 * @param cntImdg
	 * @param cntImdgUnNo
	 * @param cntImdgImdgClass
	 * @param cntImdgValue
	 * @param cntRfrIndr
	 * @param cntRfrRequiredVoltage
	 * @param cntRfrRequiredTemperature
	 * @param cntRfrStartPlug
	 * @param cntRfrStopPlug
	 * @param cntOogTop
	 * @param cntOogLeft
	 * @param cntOogRight
	 * @param cntSealNo
	 * @param cntStacking
	 * @param cntGateIn
	 * @param cntGateOut
	 * @param cntLocation
	 * @param cntVesselNo
	 * @param cntVesselName
	 * @param cntVoyageNo
	 * @param cntVesselEta
	 * @param cntVesselAta
	 * @param cntVesselEtd
	 * @param cntVesselAtd
	 * @param cntMarksNo
	 * @param cntNoPackages
	 * @param cntDescription
	 * @param cntOwner
	 * @param cntTaxid
	 * @param cntSender
	 * @param cntForwarder
	 * @param cntCcacNo
	 * @param cntBlAwbNo
	 * @param cntStatusCustoms
	 * @param cntStatusQuarantine
	 * @param cntValidDate
	 * @param cntDoLastPaidThru
	 * @param cntRemarks
	 * @param cntStatus
	 * @param cntDtCreate
	 * @param cntUidCreate
	 * @param cntDtLupd
	 * @param cntUidLupd
	 */
	public CkCnt(String cntId, String cntSize, String cntType, String cntPod, String cntPol, Date cntDtDischarge,
			Date cntDtLoad, Character cntDangerousGood, String cntNo, String cntIsoCode, String cntImoCode,
			Integer cntHeight, Integer cntWeight, Integer cntBruto, Character cntFullEmptyIndr, Character cntImdg,
			String cntImdgUnNo, Byte cntImdgImdgClass, String cntImdgValue, Character cntRfrIndr,
			Short cntRfrRequiredVoltage, BigDecimal cntRfrRequiredTemperature, Date cntRfrStartPlug,
			Date cntRfrStopPlug, Integer cntOogTop, Integer cntOogLeft, Integer cntOogRight, String cntSealNo,
			Date cntStacking, Date cntGateIn, Date cntGateOut, String cntLocation, String cntVesselNo,
			String cntVesselName, String cntVoyageNo, Date cntVesselEta, Date cntVesselAta, Date cntVesselEtd,
			Date cntVesselAtd, String cntMarksNo, String cntNoPackages, String cntDescription, String cntOwner,
			String cntTaxid, String cntSender, String cntForwarder, String cntCcacNo, String cntBlAwbNo,
			String cntStatusCustoms, String cntStatusQuarantine, Date cntValidDate, Date cntDoLastPaidThru, String cntRemarks,
			Character cntStatus, Date cntDtCreate, String cntUidCreate, Date cntDtLupd, String cntUidLupd) {
		this.cntId = cntId;
		this.cntSize = cntSize;
		this.cntType = cntType;
		this.cntPod = cntPod;
		this.cntPol = cntPol;
		this.cntDtDischarge = cntDtDischarge;
		this.cntDtLoad = cntDtLoad;
		this.cntDangerousGood = cntDangerousGood;
		this.cntNo = cntNo;
		this.cntIsoCode = cntIsoCode;
		this.cntImoCode = cntImoCode;
		this.cntHeight = cntHeight;
		this.cntWeight = cntWeight;
		this.cntBruto = cntBruto;
		this.cntFullEmptyIndr = cntFullEmptyIndr;
		this.cntImdg = cntImdg;
		this.cntImdgUnNo = cntImdgUnNo;
		this.cntImdgImdgClass = cntImdgImdgClass;
		this.cntImdgValue = cntImdgValue;
		this.cntRfrIndr = cntRfrIndr;
		this.cntRfrRequiredVoltage = cntRfrRequiredVoltage;
		this.cntRfrRequiredTemperature = cntRfrRequiredTemperature;
		this.cntRfrStartPlug = cntRfrStartPlug;
		this.cntRfrStopPlug = cntRfrStopPlug;
		this.cntOogTop = cntOogTop;
		this.cntOogLeft = cntOogLeft;
		this.cntOogRight = cntOogRight;
		this.cntSealNo = cntSealNo;
		this.cntStacking = cntStacking;
		this.cntGateIn = cntGateIn;
		this.cntGateOut = cntGateOut;
		this.cntLocation = cntLocation;
		this.cntVesselNo = cntVesselNo;
		this.cntVesselName = cntVesselName;
		this.cntVoyageNo = cntVoyageNo;
		this.cntVesselEta = cntVesselEta;
		this.cntVesselAta = cntVesselAta;
		this.cntVesselEtd = cntVesselEtd;
		this.cntVesselAtd = cntVesselAtd;
		this.cntMarksNo = cntMarksNo;
		this.cntNoPackages = cntNoPackages;
		this.cntDescription = cntDescription;
		this.cntOwner = cntOwner;
		this.cntTaxid = cntTaxid;
		this.cntSender = cntSender;
		this.cntForwarder = cntForwarder;
		this.cntCcacNo = cntCcacNo;
		this.cntBlAwbNo = cntBlAwbNo;
		this.cntStatusCustoms = cntStatusCustoms;
		this.cntStatusQuarantine = cntStatusQuarantine;
		this.cntValidDate = cntValidDate;
		this.cntDoLastPaidThru = cntDoLastPaidThru;
		this.cntRemarks = cntRemarks;
		this.cntStatus = cntStatus;
		this.cntDtCreate = cntDtCreate;
		this.cntUidCreate = cntUidCreate;
		this.cntDtLupd = cntDtLupd;
		this.cntUidLupd = cntUidLupd;
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
	public int compareTo(CkCnt o) {
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
	 * @return the cntId
	 */
	public String getCntId() {
		return cntId;
	}

	/**
	 * @param cntId the cntId to set
	 */
	public void setCntId(String cntId) {
		this.cntId = cntId;
	}

	/**
	 * @return the cntSize
	 */
	public String getCntSize() {
		return cntSize;
	}

	/**
	 * @param cntSize the cntSize to set
	 */
	public void setCntSize(String cntSize) {
		this.cntSize = cntSize;
	}

	/**
	 * @return the cntType
	 */
	public String getCntType() {
		return cntType;
	}

	/**
	 * @param cntType the cntType to set
	 */
	public void setCntType(String cntType) {
		this.cntType = cntType;
	}

	/**
	 * @return the cntPol
	 */
	public String getCntPod() {
		return cntPod;
	}

	/**
	 * @param cntPod the cntPod to set
	 */
	public void setCntPod(String cntPod) {
		this.cntPod = cntPod;
	}

	/**
	 * @return the cntPol
	 */
	public String getCntPol() {
		return cntPol;
	}

	/**
	 * @return the cntDtDischarge
	 */
	public Date getCntDtDischarge() {
		return cntDtDischarge;
	}

	/**
	 * @param cntDtDischarge the cntDtDischarge to set
	 */
	public void setCntDtDischarge(Date cntDtDischarge) {
		this.cntDtDischarge = cntDtDischarge;
	}

	/**
	 * @return the cntDtLoad
	 */
	public Date getCntDtLoad() {
		return cntDtLoad;
	}

	/**
	 * @param cntDtLoad the cntDtLoad to set
	 */
	public void setCntDtLoad(Date cntDtLoad) {
		this.cntDtLoad = cntDtLoad;
	}

	/**
	 * @return the cntDangerousGood
	 */
	public Character getCntDangerousGood() {
		return cntDangerousGood;
	}

	/**
	 * @param cntDangerousGood the cntDangerousGood to set
	 */
	public void setCntDangerousGood(Character cntDangerousGood) {
		this.cntDangerousGood = cntDangerousGood;
	}

	/**
	 * @param cntPol the cntPol to set
	 */
	public void setCntPol(String cntPol) {
		this.cntPol = cntPol;
	}

	/**
	 * @return the cntNo
	 */
	public String getCntNo() {
		return cntNo;
	}

	/**
	 * @param cntNo the cntNo to set
	 */
	public void setCntNo(String cntNo) {
		this.cntNo = cntNo;
	}

	/**
	 * @return the cntIsoCode
	 */
	public String getCntIsoCode() {
		return cntIsoCode;
	}

	/**
	 * @param cntIsoCode the cntIsoCode to set
	 */
	public void setCntIsoCode(String cntIsoCode) {
		this.cntIsoCode = cntIsoCode;
	}

	/**
	 * @return the cntImoCode
	 */
	public String getCntImoCode() {
		return cntImoCode;
	}

	/**
	 * @param cntImoCode the cntImoCode to set
	 */
	public void setCntImoCode(String cntImoCode) {
		this.cntImoCode = cntImoCode;
	}

	/**
	 * @return the cntHeight
	 */
	public Integer getCntHeight() {
		return cntHeight;
	}

	/**
	 * @param cntHeight the cntHeight to set
	 */
	public void setCntHeight(Integer cntHeight) {
		this.cntHeight = cntHeight;
	}

	/**
	 * @return the cntWeight
	 */
	public Integer getCntWeight() {
		return cntWeight;
	}

	/**
	 * @param cntWeight the cntWeight to set
	 */
	public void setCntWeight(Integer cntWeight) {
		this.cntWeight = cntWeight;
	}

	/**
	 * @return the cntBruto
	 */
	public Integer getCntBruto() {
		return cntBruto;
	}

	/**
	 * @param cntBruto the cntBruto to set
	 */
	public void setCntBruto(Integer cntBruto) {
		this.cntBruto = cntBruto;
	}

	/**
	 * @return the cntFullEmptyIndr
	 */
	public Character getCntFullEmptyIndr() {
		return cntFullEmptyIndr;
	}

	/**
	 * @param cntFullEmptyIndr the cntFullEmptyIndr to set
	 */
	public void setCntFullEmptyIndr(Character cntFullEmptyIndr) {
		this.cntFullEmptyIndr = cntFullEmptyIndr;
	}

	/**
	 * @return the cntImdg
	 */
	public Character getCntImdg() {
		return cntImdg;
	}

	/**
	 * @param cntImdg the cntImdg to set
	 */
	public void setCntImdg(Character cntImdg) {
		this.cntImdg = cntImdg;
	}

	/**
	 * @return the cntImdgUnNo
	 */
	public String getCntImdgUnNo() {
		return cntImdgUnNo;
	}

	/**
	 * @param cntImdgUnNo the cntImdgUnNo to set
	 */
	public void setCntImdgUnNo(String cntImdgUnNo) {
		this.cntImdgUnNo = cntImdgUnNo;
	}

	/**
	 * @return the cntImdgImdgClass
	 */
	public Byte getCntImdgImdgClass() {
		return cntImdgImdgClass;
	}

	/**
	 * @param cntImdgImdgClass the cntImdgImdgClass to set
	 */
	public void setCntImdgImdgClass(Byte cntImdgImdgClass) {
		this.cntImdgImdgClass = cntImdgImdgClass;
	}

	/**
	 * @return the cntImdgValue
	 */
	public String getCntImdgValue() {
		return cntImdgValue;
	}

	/**
	 * @param cntImdgValue the cntImdgValue to set
	 */
	public void setCntImdgValue(String cntImdgValue) {
		this.cntImdgValue = cntImdgValue;
	}

	/**
	 * @return the cntRfrIndr
	 */
	public Character getCntRfrIndr() {
		return cntRfrIndr;
	}

	/**
	 * @param cntRfrIndr the cntRfrIndr to set
	 */
	public void setCntRfrIndr(Character cntRfrIndr) {
		this.cntRfrIndr = cntRfrIndr;
	}

	/**
	 * @return the cntRfrRequiredVoltage
	 */
	public Short getCntRfrRequiredVoltage() {
		return cntRfrRequiredVoltage;
	}

	/**
	 * @param cntRfrRequiredVoltage the cntRfrRequiredVoltage to set
	 */
	public void setCntRfrRequiredVoltage(Short cntRfrRequiredVoltage) {
		this.cntRfrRequiredVoltage = cntRfrRequiredVoltage;
	}

	/**
	 * @return the cntRfrRequiredTemperature
	 */
	public BigDecimal getCntRfrRequiredTemperature() {
		return cntRfrRequiredTemperature;
	}

	/**
	 * @param cntRfrRequiredTemperature the cntRfrRequiredTemperature to set
	 */
	public void setCntRfrRequiredTemperature(BigDecimal cntRfrRequiredTemperature) {
		this.cntRfrRequiredTemperature = cntRfrRequiredTemperature;
	}

	/**
	 * @return the cntRfrStartPlug
	 */
	public Date getCntRfrStartPlug() {
		return cntRfrStartPlug;
	}

	/**
	 * @param cntRfrStartPlug the cntRfrStartPlug to set
	 */
	public void setCntRfrStartPlug(Date cntRfrStartPlug) {
		this.cntRfrStartPlug = cntRfrStartPlug;
	}

	/**
	 * @return the cntRfrStopPlug
	 */
	public Date getCntRfrStopPlug() {
		return cntRfrStopPlug;
	}

	/**
	 * @param cntRfrStopPlug the cntRfrStopPlug to set
	 */
	public void setCntRfrStopPlug(Date cntRfrStopPlug) {
		this.cntRfrStopPlug = cntRfrStopPlug;
	}

	/**
	 * @return the cntOogTop
	 */
	public Integer getCntOogTop() {
		return cntOogTop;
	}

	/**
	 * @param cntOogTop the cntOogTop to set
	 */
	public void setCntOogTop(Integer cntOogTop) {
		this.cntOogTop = cntOogTop;
	}

	/**
	 * @return the cntOogLeft
	 */
	public Integer getCntOogLeft() {
		return cntOogLeft;
	}

	/**
	 * @param cntOogLeft the cntOogLeft to set
	 */
	public void setCntOogLeft(Integer cntOogLeft) {
		this.cntOogLeft = cntOogLeft;
	}

	/**
	 * @return the cntOogRight
	 */
	public Integer getCntOogRight() {
		return cntOogRight;
	}

	/**
	 * @param cntOogRight the cntOogRight to set
	 */
	public void setCntOogRight(Integer cntOogRight) {
		this.cntOogRight = cntOogRight;
	}

	/**
	 * @return the cntSealNo
	 */
	public String getCntSealNo() {
		return cntSealNo;
	}

	/**
	 * @param cntSealNo the cntSealNo to set
	 */
	public void setCntSealNo(String cntSealNo) {
		this.cntSealNo = cntSealNo;
	}

	/**
	 * @return the cntStacking
	 */
	public Date getCntStacking() {
		return cntStacking;
	}

	/**
	 * @param cntStacking the cntStacking to set
	 */
	public void setCntStacking(Date cntStacking) {
		this.cntStacking = cntStacking;
	}

	/**
	 * @return the cntGateIn
	 */
	public Date getCntGateIn() {
		return cntGateIn;
	}

	/**
	 * @param cntGateIn the cntGateIn to set
	 */
	public void setCntGateIn(Date cntGateIn) {
		this.cntGateIn = cntGateIn;
	}

	/**
	 * @return the cntGateOut
	 */
	public Date getCntGateOut() {
		return cntGateOut;
	}

	/**
	 * @param cntGateOut the cntGateOut to set
	 */
	public void setCntGateOut(Date cntGateOut) {
		this.cntGateOut = cntGateOut;
	}

	/**
	 * @return the cntLocation
	 */
	public String getCntLocation() {
		return cntLocation;
	}

	/**
	 * @param cntLocation the cntLocation to set
	 */
	public void setCntLocation(String cntLocation) {
		this.cntLocation = cntLocation;
	}

	/**
	 * @return the cntVesselNo
	 */
	public String getCntVesselNo() {
		return cntVesselNo;
	}

	/**
	 * @param cntVesselNo the cntVesselNo to set
	 */
	public void setCntVesselNo(String cntVesselNo) {
		this.cntVesselNo = cntVesselNo;
	}

	/**
	 * @return the cntVesselName
	 */
	public String getCntVesselName() {
		return cntVesselName;
	}

	/**
	 * @param cntVesselName the cntVesselName to set
	 */
	public void setCntVesselName(String cntVesselName) {
		this.cntVesselName = cntVesselName;
	}

	/**
	 * @return the cntVoyageNo
	 */
	public String getCntVoyageNo() {
		return cntVoyageNo;
	}

	/**
	 * @param cntVoyageNo the cntVoyageNo to set
	 */
	public void setCntVoyageNo(String cntVoyageNo) {
		this.cntVoyageNo = cntVoyageNo;
	}

	/**
	 * @return the cntVesselEta
	 */
	public Date getCntVesselEta() {
		return cntVesselEta;
	}

	/**
	 * @param cntVesselEta the cntVesselEta to set
	 */
	public void setCntVesselEta(Date cntVesselEta) {
		this.cntVesselEta = cntVesselEta;
	}

	/**
	 * @return the cntVesselAta
	 */
	public Date getCntVesselAta() {
		return cntVesselAta;
	}

	/**
	 * @param cntVesselAta the cntVesselAta to set
	 */
	public void setCntVesselAta(Date cntVesselAta) {
		this.cntVesselAta = cntVesselAta;
	}

	/**
	 * @return the cntVesselEtd
	 */
	public Date getCntVesselEtd() {
		return cntVesselEtd;
	}

	/**
	 * @param cntVesselEtd the cntVesselEtd to set
	 */
	public void setCntVesselEtd(Date cntVesselEtd) {
		this.cntVesselEtd = cntVesselEtd;
	}

	/**
	 * @return the cntVesselAtd
	 */
	public Date getCntVesselAtd() {
		return cntVesselAtd;
	}

	/**
	 * @param cntVesselAtd the cntVesselAtd to set
	 */
	public void setCntVesselAtd(Date cntVesselAtd) {
		this.cntVesselAtd = cntVesselAtd;
	}

	/**
	 * @return the cntMarksNo
	 */
	public String getCntMarksNo() {
		return cntMarksNo;
	}

	/**
	 * @param cntMarksNo the cntMarksNo to set
	 */
	public void setCntMarksNo(String cntMarksNo) {
		this.cntMarksNo = cntMarksNo;
	}

	/**
	 * @return the cntNoPackages
	 */
	public String getCntNoPackages() {
		return cntNoPackages;
	}

	/**
	 * @param cntNoPackages the cntNoPackages to set
	 */
	public void setCntNoPackages(String cntNoPackages) {
		this.cntNoPackages = cntNoPackages;
	}

	/**
	 * @return the cntDescription
	 */
	public String getCntDescription() {
		return cntDescription;
	}

	/**
	 * @param cntDescription the cntDescription to set
	 */
	public void setCntDescription(String cntDescription) {
		this.cntDescription = cntDescription;
	}

	/**
	 * @return the cntOwner
	 */
	public String getCntOwner() {
		return cntOwner;
	}

	/**
	 * @param cntOwner the cntOwner to set
	 */
	public void setCntOwner(String cntOwner) {
		this.cntOwner = cntOwner;
	}

	/**
	 * @return the cntTaxid
	 */
	public String getCntTaxid() {
		return cntTaxid;
	}

	/**
	 * @param cntTaxid the cntTaxid to set
	 */
	public void setCntTaxid(String cntTaxid) {
		this.cntTaxid = cntTaxid;
	}

	/**
	 * @return the cntSender
	 */
	public String getCntSender() {
		return cntSender;
	}

	/**
	 * @param cntSender the cntSender to set
	 */
	public void setCntSender(String cntSender) {
		this.cntSender = cntSender;
	}

	/**
	 * @return the cntForwarder
	 */
	public String getCntForwarder() {
		return cntForwarder;
	}

	/**
	 * @param cntForwarder the cntForwarder to set
	 */
	public void setCntForwarder(String cntForwarder) {
		this.cntForwarder = cntForwarder;
	}

	/**
	 * @return the cntCcacNo
	 */
	public String getCntCcacNo() {
		return cntCcacNo;
	}

	/**
	 * @param cntCcacNo the cntCcacNo to set
	 */
	public void setCntCcacNo(String cntCcacNo) {
		this.cntCcacNo = cntCcacNo;
	}

	/**
	 * @return the cntBlAwbNo
	 */
	public String getCntBlAwbNo() {
		return cntBlAwbNo;
	}

	/**
	 * @param cntBlAwbNo the cntBlAwbNo to set
	 */
	public void setCntBlAwbNo(String cntBlAwbNo) {
		this.cntBlAwbNo = cntBlAwbNo;
	}

	/**
	 * @return the cntStatusCustoms
	 */
	public String getCntStatusCustoms() {
		return cntStatusCustoms;
	}

	/**
	 * @param cntStatusCustoms the cntStatusCustoms to set
	 */
	public void setCntStatusCustoms(String cntStatusCustoms) {
		this.cntStatusCustoms = cntStatusCustoms;
	}

	/**
	 * @return the cntStatusQuarantine
	 */
	public String getCntStatusQuarantine() {
		return cntStatusQuarantine;
	}

	/**
	 * @param cntStatusQuarantine the cntStatusQuarantine to set
	 */
	public void setCntStatusQuarantine(String cntStatusQuarantine) {
		this.cntStatusQuarantine = cntStatusQuarantine;
	}

	/**
	 * @return the cntValidDate
	 */
	public Date getCntValidDate() {
		return this.cntValidDate;
	}

	/**
	 * @param cntValidDate the cntValidDate to set
	 */
	public void setCntValidDate(Date cntValidDate) {
		this.cntValidDate = cntValidDate;
	}

	/**
	 * @return the cntDoLastPaidThru
	 */
	public Date getCntDoLastPaidThru() {
		return cntDoLastPaidThru;
	}

	/**
	 * @param cntDoLastPaidThru the cntDoLastPaidThru to set
	 */
	public void setCntDoLastPaidThru(Date cntDoLastPaidThru) {
		this.cntDoLastPaidThru = cntDoLastPaidThru;
	}

	/**
	 * @return the cntRemarks
	 */
	public String getCntRemarks() {
		return cntRemarks;
	}

	/**
	 * @param cntRemarks the cntRemarks to set
	 */
	public void setCntRemarks(String cntRemarks) {
		this.cntRemarks = cntRemarks;
	}

	/**
	 * @return the cntStatus
	 */
	public Character getCntStatus() {
		return cntStatus;
	}

	/**
	 * @param cntStatus the cntStatus to set
	 */
	public void setCntStatus(Character cntStatus) {
		this.cntStatus = cntStatus;
	}

	/**
	 * @return the cntDtCreate
	 */
	public Date getCntDtCreate() {
		return cntDtCreate;
	}

	/**
	 * @param cntDtCreate the cntDtCreate to set
	 */
	public void setCntDtCreate(Date cntDtCreate) {
		this.cntDtCreate = cntDtCreate;
	}

	/**
	 * @return the cntUidCreate
	 */
	public String getCntUidCreate() {
		return cntUidCreate;
	}

	/**
	 * @param cntUidCreate the cntUidCreate to set
	 */
	public void setCntUidCreate(String cntUidCreate) {
		this.cntUidCreate = cntUidCreate;
	}

	/**
	 * @return the cntDtLupd
	 */
	public Date getCntDtLupd() {
		return cntDtLupd;
	}

	/**
	 * @param cntDtLupd the cntDtLupd to set
	 */
	public void setCntDtLupd(Date cntDtLupd) {
		this.cntDtLupd = cntDtLupd;
	}

	/**
	 * @return the cntUidLupd
	 */
	public String getCntUidLupd() {
		return cntUidLupd;
	}

	/**
	 * @param cntUidLupd the cntUidLupd to set
	 */
	public void setCntUidLupd(String cntUidLupd) {
		this.cntUidLupd = cntUidLupd;
	}
}
