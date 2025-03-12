package com.guudint.clickargo.clicservice.dto;

import java.util.Date;

import com.guudint.clickargo.clicservice.model.TCkSuspensionLog;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkSuspensionLog extends AbstractDTO<CkSuspensionLog, TCkSuspensionLog> {

	// Static Attributes
	private static final long serialVersionUID = -2541098645656355128L;
	
	// Attributes
	/////////////
	private String slId;
	private String slEvent;
	private String slAccnId;
	private String slRemarks;
	private String slDetails;
	private Date slDtCreate;
	private String slUidCreate;
	
	// Constructors
	///////////////
	public CkSuspensionLog() {
	}

	public CkSuspensionLog(String slId) {
		this.slId = slId;
	}
	
	public CkSuspensionLog(TCkSuspensionLog entity) {
		super(entity);
	}
	
	/**
	 * @param slId
	 * @param slEvent
	 * @param slAccnId
	 * @param slRemarks
	 * @param slDetails
	 * @param slDtCreate
	 * @param slUidCreate
	 */
	public CkSuspensionLog(String slId, String slEvent, String slAccnId, String slRemarks, String slDetails,
			Date slDtCreate, String slUidCreate) {
		super();
		this.slId = slId;
		this.slEvent = slEvent;
		this.slAccnId = slAccnId;
		this.slRemarks = slRemarks;
		this.slDetails = slDetails;
		this.slDtCreate = slDtCreate;
		this.slUidCreate = slUidCreate;
	}

	// Properties
	/////////////
	/**
	 * @return the slId
	 */
	public String getSlId() {
		return slId;
	}

	/**
	 * @param slId the slId to set
	 */
	public void setSlId(String slId) {
		this.slId = slId;
	}

	/**
	 * @return the slEvent
	 */
	public String getSlEvent() {
		return slEvent;
	}

	/**
	 * @param slEvent the slEvent to set
	 */
	public void setSlEvent(String slEvent) {
		this.slEvent = slEvent;
	}

	/**
	 * @return the slAccnId
	 */
	public String getSlAccnId() {
		return slAccnId;
	}

	/**
	 * @param slAccnId the slAccnId to set
	 */
	public void setSlAccnId(String slAccnId) {
		this.slAccnId = slAccnId;
	}

	/**
	 * @return the slRemarks
	 */
	public String getSlRemarks() {
		return slRemarks;
	}

	/**
	 * @param slRemarks the slRemarks to set
	 */
	public void setSlRemarks(String slRemarks) {
		this.slRemarks = slRemarks;
	}

	/**
	 * @return the slDetails
	 */
	public String getSlDetails() {
		return slDetails;
	}

	/**
	 * @param slDetails the slDetails to set
	 */
	public void setSlDetails(String slDetails) {
		this.slDetails = slDetails;
	}

	/**
	 * @return the slDtCreate
	 */
	public Date getSlDtCreate() {
		return slDtCreate;
	}

	/**
	 * @param slDtCreate the slDtCreate to set
	 */
	public void setSlDtCreate(Date slDtCreate) {
		this.slDtCreate = slDtCreate;
	}

	/**
	 * @return the slUidCreate
	 */
	public String getSlUidCreate() {
		return slUidCreate;
	}

	/**
	 * @param slUidCreate the slUidCreate to set
	 */
	public void setSlUidCreate(String slUidCreate) {
		this.slUidCreate = slUidCreate;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// Override Methods
	///////////////////
	@Override
	public int compareTo(CkSuspensionLog o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

}
