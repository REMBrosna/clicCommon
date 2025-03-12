package com.guudint.clickargo.clicservice.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vcc.camelone.common.COAbstractEntity;

@Entity
@Table(name = "T_CK_SUSPENSION_LOG")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class TCkSuspensionLog extends COAbstractEntity<TCkSuspensionLog> {

	// Static Attributes
	private static final long serialVersionUID = -3879347689210104434L;
	
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
	public TCkSuspensionLog() {
	}

	public TCkSuspensionLog(String slId) {
		this.slId = slId;
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
	public TCkSuspensionLog(String slId, String slEvent, String slAccnId, String slRemarks, String slDetails,
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
	@Id
	@Column(name = "SL_ID", unique = true, nullable = false, length = 35)
	public String getSlId() {
		return slId;
	}

	public void setSlId(String slId) {
		this.slId = slId;
	}

	@Column(name = "SL_EVENT", length = 35)
	public String getSlEvent() {
		return slEvent;
	}

	public void setSlEvent(String slEvent) {
		this.slEvent = slEvent;
	}

	@Column(name = "SL_ACCNID", length = 35)
	public String getSlAccnId() {
		return slAccnId;
	}

	public void setSlAccnId(String slAccnId) {
		this.slAccnId = slAccnId;
	}

	@Column(name = "SL_REMARKS", length = 2048)
	public String getSlRemarks() {
		return slRemarks;
	}

	public void setSlRemarks(String slRemarks) {
		this.slRemarks = slRemarks;
	}

	@Column(name = "SL_DETAILS", length = 16777215)
	public String getSlDetails() {
		return slDetails;
	}

	public void setSlDetails(String slDetails) {
		this.slDetails = slDetails;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SL_DT_CREATE", length = 19)
	public Date getSlDtCreate() {
		return slDtCreate;
	}

	public void setSlDtCreate(Date slDtCreate) {
		this.slDtCreate = slDtCreate;
	}

	@Column(name = "SL_UID_CREATE", length = 35)
	public String getSlUidCreate() {
		return slUidCreate;
	}

	public void setSlUidCreate(String slUidCreate) {
		this.slUidCreate = slUidCreate;
	}

	// Override Methods
	///////////////////
	@Override
	public int compareTo(TCkSuspensionLog arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

}
