package com.guudint.clickargo.common.dto;

import java.util.Date;

import javax.validation.constraints.Size;

import com.guudint.clickargo.common.model.TCkComponentGuide;
import com.guudint.clickargo.master.dto.CkMstServiceType;
import com.guudint.clickargo.validator.ValidationGroup.CreateValid;
import com.guudint.clickargo.validator.ValidationGroup.SubmitValid;
import com.guudint.clickargo.validator.ValidationGroup.UpdateValid;
import com.vcc.camelone.common.dto.AbstractDTO;
import com.vcc.camelone.core.dto.CoreApps;

public class CkComponentGuide extends AbstractDTO<CkComponentGuide, TCkComponentGuide> {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = 7876660134551481326L;
	
	// Attributes
	/////////////
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 35, message = "{valid.common.cmguId.maxLength}")
	private String cmguId;
	private CkMstServiceType TCkMstServiceType;
	private CoreApps TCoreApps;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 1024, message = "{valid.common.cmguComponentId.maxLength}")
	private String cmguComponentId;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 16777215, message = "{valid.common.cmguGuide.maxLength}")
	private String cmguGuide;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 16777215, message = "{valid.common.cmguGuideOth.maxLength}")
	private String cmguGuideOth;
	private Character cmguStatus;
	private Date cmguDtCreate;
	private String cmguUidCreate;
	private Date cmguDtLupd;
	private String cmguUidLupd;

	// Constructors
	///////////////
	public CkComponentGuide() {
	}
	
	/**
	 * @param entity
	 */
	public CkComponentGuide(TCkComponentGuide entity) {
		super(entity);
	}

	/**
	 * @param cmguId
	 * @param TCoreApps
	 * @param cmguComponentId
	 */
	public CkComponentGuide(String cmguId, CoreApps TCoreApps, String cmguComponentId) {
		this.cmguId = cmguId;
		this.TCoreApps = TCoreApps;
		this.cmguComponentId = cmguComponentId;
	}

	/**
	 * @param cmguId
	 * @param TCkMstServiceType
	 * @param TCoreApps
	 * @param cmguComponentId
	 * @param cmguGuide
	 * @param cmguGuideOth
	 * @param cmguStatus
	 * @param cmguDtCreate
	 * @param cmguUidCreate
	 * @param cmguDtLupd
	 * @param cmguUidLupd
	 */
	public CkComponentGuide(String cmguId, CkMstServiceType TCkMstServiceType, CoreApps TCoreApps,
			String cmguComponentId, String cmguGuide, String cmguGuideOth, Character cmguStatus, Date cmguDtCreate,
			String cmguUidCreate, Date cmguDtLupd, String cmguUidLupd) {
		this.cmguId = cmguId;
		this.TCkMstServiceType = TCkMstServiceType;
		this.TCoreApps = TCoreApps;
		this.cmguComponentId = cmguComponentId;
		this.cmguGuide = cmguGuide;
		this.cmguGuideOth = cmguGuideOth;
		this.cmguStatus = cmguStatus;
		this.cmguDtCreate = cmguDtCreate;
		this.cmguUidCreate = cmguUidCreate;
		this.cmguDtLupd = cmguDtLupd;
		this.cmguUidLupd = cmguUidLupd;
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
	public int compareTo(CkComponentGuide o) {
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
	 * @return the cmguId
	 */
	public String getCmguId() {
		return cmguId;
	}

	/**
	 * @param cmguId the cmguId to set
	 */
	public void setCmguId(String cmguId) {
		this.cmguId = cmguId;
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
	 * @return the tCoreApps
	 */
	public CoreApps getTCoreApps() {
		return TCoreApps;
	}

	/**
	 * @param tCoreApps the tCoreApps to set
	 */
	public void setTCoreApps(CoreApps tCoreApps) {
		TCoreApps = tCoreApps;
	}

	/**
	 * @return the cmguComponentId
	 */
	public String getCmguComponentId() {
		return cmguComponentId;
	}

	/**
	 * @param cmguComponentId the cmguComponentId to set
	 */
	public void setCmguComponentId(String cmguComponentId) {
		this.cmguComponentId = cmguComponentId;
	}

	/**
	 * @return the cmguGuide
	 */
	public String getCmguGuide() {
		return cmguGuide;
	}

	/**
	 * @param cmguGuide the cmguGuide to set
	 */
	public void setCmguGuide(String cmguGuide) {
		this.cmguGuide = cmguGuide;
	}

	/**
	 * @return the cmguGuideOth
	 */
	public String getCmguGuideOth() {
		return cmguGuideOth;
	}

	/**
	 * @param cmguGuideOth the cmguGuideOth to set
	 */
	public void setCmguGuideOth(String cmguGuideOth) {
		this.cmguGuideOth = cmguGuideOth;
	}

	/**
	 * @return the cmguStatus
	 */
	public Character getCmguStatus() {
		return cmguStatus;
	}

	/**
	 * @param cmguStatus the cmguStatus to set
	 */
	public void setCmguStatus(Character cmguStatus) {
		this.cmguStatus = cmguStatus;
	}

	/**
	 * @return the cmguDtCreate
	 */
	public Date getCmguDtCreate() {
		return cmguDtCreate;
	}

	/**
	 * @param cmguDtCreate the cmguDtCreate to set
	 */
	public void setCmguDtCreate(Date cmguDtCreate) {
		this.cmguDtCreate = cmguDtCreate;
	}

	/**
	 * @return the cmguUidCreate
	 */
	public String getCmguUidCreate() {
		return cmguUidCreate;
	}

	/**
	 * @param cmguUidCreate the cmguUidCreate to set
	 */
	public void setCmguUidCreate(String cmguUidCreate) {
		this.cmguUidCreate = cmguUidCreate;
	}

	/**
	 * @return the cmguDtLupd
	 */
	public Date getCmguDtLupd() {
		return cmguDtLupd;
	}

	/**
	 * @param cmguDtLupd the cmguDtLupd to set
	 */
	public void setCmguDtLupd(Date cmguDtLupd) {
		this.cmguDtLupd = cmguDtLupd;
	}

	/**
	 * @return the cmguUidLupd
	 */
	public String getCmguUidLupd() {
		return cmguUidLupd;
	}

	/**
	 * @param cmguUidLupd the cmguUidLupd to set
	 */
	public void setCmguUidLupd(String cmguUidLupd) {
		this.cmguUidLupd = cmguUidLupd;
	}
}