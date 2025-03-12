package com.guudint.clickargo.clicservice.dto;


import java.util.Date;

import javax.validation.constraints.Size;

import com.guudint.clickargo.clicservice.model.TCkSvcSub;
import com.guudint.clickargo.master.dto.CkMstServiceType;
import com.guudint.clickargo.master.dto.CkMstSvcSubState;
import com.guudint.clickargo.validator.ValidationGroup.CreateValid;
import com.guudint.clickargo.validator.ValidationGroup.SubmitValid;
import com.guudint.clickargo.validator.ValidationGroup.UpdateValid;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkSvcSub extends AbstractDTO<CkSvcSub, TCkSvcSub> {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = 3084145577587641490L;
	
	// Attributes
	//////////////
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 35, message = "{valid.clicservice.subId.maxLength}")
	private String subId;
	private CkMstServiceType TCkMstServiceType;
	private CkMstSvcSubState TCkMstSvcSubState;
	private CoreAccn TCoreAccn;
	private Date subDtStart;
	private Date subDtValid;
	private Character subAutoRenew;
	private String subUidVerify;
	private Date subDtVerify;
	private String subUidApprove;
	private Date subDtApprove;
	private Character subStatus;
	private Date subDtCreate;
	private String subUidCreate;
	private Date subDtLupd;
	private String subUidLupd;

	// Constructors
	public CkSvcSub() {
		
	}

	/**
	 * @param entity
	 */
	public CkSvcSub(TCkSvcSub entity) {
		super(entity);
	}
		
	/**
	 * @param subId
	 */
	public CkSvcSub(String subId) {
		this.subId = subId;
	}

	/**
	 * @param subId
	 * @param TCkMstServiceType
	 * @param TCkMstSvcSubState
	 * @param TCoreAccn
	 * @param subDtStart
	 * @param subDtValid
	 * @param subAutoRenew
	 * @param subUidVerify
	 * @param subDtVerify
	 * @param subUidApprove
	 * @param subDtApprove
	 * @param subStatus
	 * @param subDtCreate
	 * @param subUidCreate
	 * @param subDtLupd
	 * @param subUidLupd
	 */
	public CkSvcSub(String subId, CkMstServiceType TCkMstServiceType, CkMstSvcSubState TCkMstSvcSubState,
			CoreAccn TCoreAccn, Date subDtStart, Date subDtValid, Character subAutoRenew, String subUidVerify,
			Date subDtVerify, String subUidApprove, Date subDtApprove, Character subStatus, Date subDtCreate,
			String subUidCreate, Date subDtLupd, String subUidLupd) {
		this.subId = subId;
		this.TCkMstServiceType = TCkMstServiceType;
		this.TCkMstSvcSubState = TCkMstSvcSubState;
		this.TCoreAccn = TCoreAccn;
		this.subDtStart = subDtStart;
		this.subDtValid = subDtValid;
		this.subAutoRenew = subAutoRenew;
		this.subUidVerify = subUidVerify;
		this.subDtVerify = subDtVerify;
		this.subUidApprove = subUidApprove;
		this.subDtApprove = subDtApprove;
		this.subStatus = subStatus;
		this.subDtCreate = subDtCreate;
		this.subUidCreate = subUidCreate;
		this.subDtLupd = subDtLupd;
		this.subUidLupd = subUidLupd;
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
	public int compareTo(CkSvcSub o) {
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
	 * @return the subId
	 */
	public String getSubId() {
		return subId;
	}

	/**
	 * @param subId the subId to set
	 */
	public void setSubId(String subId) {
		this.subId = subId;
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
	 * @return the tCkMstSvcSubState
	 */
	public CkMstSvcSubState getTCkMstSvcSubState() {
		return TCkMstSvcSubState;
	}

	/**
	 * @param tCkMstSvcSubState the tCkMstSvcSubState to set
	 */
	public void setTCkMstSvcSubState(CkMstSvcSubState tCkMstSvcSubState) {
		TCkMstSvcSubState = tCkMstSvcSubState;
	}

	/**
	 * @return the tCoreAccn
	 */
	public CoreAccn getTCoreAccn() {
		return TCoreAccn;
	}

	/**
	 * @param tCoreAccn the tCoreAccn to set
	 */
	public void setTCoreAccn(CoreAccn tCoreAccn) {
		TCoreAccn = tCoreAccn;
	}

	/**
	 * @return the subDtStart
	 */
	public Date getSubDtStart() {
		return subDtStart;
	}

	/**
	 * @param subDtStart the subDtStart to set
	 */
	public void setSubDtStart(Date subDtStart) {
		this.subDtStart = subDtStart;
	}

	/**
	 * @return the subDtValid
	 */
	public Date getSubDtValid() {
		return subDtValid;
	}

	/**
	 * @param subDtValid the subDtValid to set
	 */
	public void setSubDtValid(Date subDtValid) {
		this.subDtValid = subDtValid;
	}

	/**
	 * @return the subAutoRenew
	 */
	public Character getSubAutoRenew() {
		return subAutoRenew;
	}

	/**
	 * @param subAutoRenew the subAutoRenew to set
	 */
	public void setSubAutoRenew(Character subAutoRenew) {
		this.subAutoRenew = subAutoRenew;
	}

	/**
	 * @return the subUidVerify
	 */
	public String getSubUidVerify() {
		return subUidVerify;
	}

	/**
	 * @param subUidVerify the subUidVerify to set
	 */
	public void setSubUidVerify(String subUidVerify) {
		this.subUidVerify = subUidVerify;
	}

	/**
	 * @return the subDtVerify
	 */
	public Date getSubDtVerify() {
		return subDtVerify;
	}

	/**
	 * @param subDtVerify the subDtVerify to set
	 */
	public void setSubDtVerify(Date subDtVerify) {
		this.subDtVerify = subDtVerify;
	}

	/**
	 * @return the subUidApprove
	 */
	public String getSubUidApprove() {
		return subUidApprove;
	}

	/**
	 * @param subUidApprove the subUidApprove to set
	 */
	public void setSubUidApprove(String subUidApprove) {
		this.subUidApprove = subUidApprove;
	}

	/**
	 * @return the subDtApprove
	 */
	public Date getSubDtApprove() {
		return subDtApprove;
	}

	/**
	 * @param subDtApprove the subDtApprove to set
	 */
	public void setSubDtApprove(Date subDtApprove) {
		this.subDtApprove = subDtApprove;
	}

	/**
	 * @return the subStatus
	 */
	public Character getSubStatus() {
		return subStatus;
	}

	/**
	 * @param subStatus the subStatus to set
	 */
	public void setSubStatus(Character subStatus) {
		this.subStatus = subStatus;
	}

	/**
	 * @return the subDtCreate
	 */
	public Date getSubDtCreate() {
		return subDtCreate;
	}

	/**
	 * @param subDtCreate the subDtCreate to set
	 */
	public void setSubDtCreate(Date subDtCreate) {
		this.subDtCreate = subDtCreate;
	}

	/**
	 * @return the subUidCreate
	 */
	public String getSubUidCreate() {
		return subUidCreate;
	}

	/**
	 * @param subUidCreate the subUidCreate to set
	 */
	public void setSubUidCreate(String subUidCreate) {
		this.subUidCreate = subUidCreate;
	}

	/**
	 * @return the subDtLupd
	 */
	public Date getSubDtLupd() {
		return subDtLupd;
	}

	/**
	 * @param subDtLupd the subDtLupd to set
	 */
	public void setSubDtLupd(Date subDtLupd) {
		this.subDtLupd = subDtLupd;
	}

	/**
	 * @return the subUidLupd
	 */
	public String getSubUidLupd() {
		return subUidLupd;
	}

	/**
	 * @param subUidLupd the subUidLupd to set
	 */
	public void setSubUidLupd(String subUidLupd) {
		this.subUidLupd = subUidLupd;
	}
}
