package com.guudint.clickargo.master.dto;
 
import java.util.Date;

import javax.validation.constraints.Size;

import com.guudint.clickargo.master.model.TCkMstAuthState;
import com.guudint.clickargo.validator.ValidationGroup.CreateValid;
import com.guudint.clickargo.validator.ValidationGroup.SubmitValid;
import com.guudint.clickargo.validator.ValidationGroup.UpdateValid;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkMstAuthState extends AbstractDTO<CkMstAuthState, TCkMstAuthState> {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = 3995540877727824544L;
	
	// Attributes
	/////////////
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 35, message = "{valid.master.austId.maxLength}")
	private String austId;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 100, message = "{valid.master.austName.maxLength}")
	private String austName;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 255, message = "{valid.master.austDesc.maxLength}")
	private String austDesc;
	@Size(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, max = 512, message = "{valid.master.austDescOth.maxLength}")
	private String austDescOth;
	private Character austStatus;
	private Date austDtCreate;
	private String austUidCreate;
	private Date austDtLupd;
	private String austUidLupd;

	// Constructors
	////////////////
	public CkMstAuthState() {
	}

	/**
	 * @param entity
	 */
	public CkMstAuthState(TCkMstAuthState entity) {
		super(entity);
	}
	
	/**
	 * @param austId
	 * @param austName
	 */
	public CkMstAuthState(String austId, String austName) {
		this.austId = austId;
		this.austName = austName;
	}

	/**
	 * @param austId
	 * @param austName
	 * @param austDesc
	 * @param austDescOth
	 * @param austStatus
	 * @param austDtCreate
	 * @param austUidCreate
	 * @param austDtLupd
	 * @param austUidLupd
	 */
	public CkMstAuthState(String austId, String austName, String austDesc, String austDescOth, Character austStatus,
			Date austDtCreate, String austUidCreate, Date austDtLupd, String austUidLupd) {
		this.austId = austId;
		this.austName = austName;
		this.austDesc = austDesc;
		this.austDescOth = austDescOth;
		this.austStatus = austStatus;
		this.austDtCreate = austDtCreate;
		this.austUidCreate = austUidCreate;
		this.austDtLupd = austDtLupd;
		this.austUidLupd = austUidLupd;
	}

	// Override Methods
	///////////////////
	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 *      
	 */
	public int compareTo(CkMstAuthState o) {
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

	/**
	 * @return the austId
	 */
	public String getAustId() {
		return austId;
	}

	/**
	 * @param austId the austId to set
	 */
	public void setAustId(String austId) {
		this.austId = austId;
	}

	/**
	 * @return the austName
	 */
	public String getAustName() {
		return austName;
	}

	/**
	 * @param austName the austName to set
	 */
	public void setAustName(String austName) {
		this.austName = austName;
	}

	/**
	 * @return the austDesc
	 */
	public String getAustDesc() {
		return austDesc;
	}

	/**
	 * @param austDesc the austDesc to set
	 */
	public void setAustDesc(String austDesc) {
		this.austDesc = austDesc;
	}

	/**
	 * @return the austDescOth
	 */
	public String getAustDescOth() {
		return austDescOth;
	}

	/**
	 * @param austDescOth the austDescOth to set
	 */
	public void setAustDescOth(String austDescOth) {
		this.austDescOth = austDescOth;
	}

	/**
	 * @return the austStatus
	 */
	public Character getAustStatus() {
		return austStatus;
	}

	/**
	 * @param austStatus the austStatus to set
	 */
	public void setAustStatus(Character austStatus) {
		this.austStatus = austStatus;
	}

	/**
	 * @return the austDtCreate
	 */
	public Date getAustDtCreate() {
		return austDtCreate;
	}

	/**
	 * @param austDtCreate the austDtCreate to set
	 */
	public void setAustDtCreate(Date austDtCreate) {
		this.austDtCreate = austDtCreate;
	}

	/**
	 * @return the austUidCreate
	 */
	public String getAustUidCreate() {
		return austUidCreate;
	}

	/**
	 * @param austUidCreate the austUidCreate to set
	 */
	public void setAustUidCreate(String austUidCreate) {
		this.austUidCreate = austUidCreate;
	}

	/**
	 * @return the austDtLupd
	 */
	public Date getAustDtLupd() {
		return austDtLupd;
	}

	/**
	 * @param austDtLupd the austDtLupd to set
	 */
	public void setAustDtLupd(Date austDtLupd) {
		this.austDtLupd = austDtLupd;
	}

	/**
	 * @return the austUidLupd
	 */
	public String getAustUidLupd() {
		return austUidLupd;
	}

	/**
	 * @param austUidLupd the austUidLupd to set
	 */
	public void setAustUidLupd(String austUidLupd) {
		this.austUidLupd = austUidLupd;
	}
}
