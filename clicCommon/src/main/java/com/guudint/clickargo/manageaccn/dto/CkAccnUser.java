package com.guudint.clickargo.manageaccn.dto;

import com.guudint.clickargo.validator.ValidationGroup.CreateValid;
import com.guudint.clickargo.validator.ValidationGroup.SubmitValid;
import com.guudint.clickargo.validator.ValidationGroup.UpdateValid;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.ccm.dto.CoreUsr;
import com.vcc.camelone.common.COAbstractEntity;
import com.vcc.camelone.validate.NotBlankExt;

public class CkAccnUser  extends COAbstractEntity<CkAccnUser>{

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = -1L;

	// Attributes
	/////////////
	CoreAccn coreAccn;
	
	CoreUsr coreUsr;
	
	String ffAccnId;

	// Constructors
	///////////////
	public CkAccnUser() {
		super();
	}

	public CkAccnUser(CoreAccn coreAccn, CoreUsr coreUsr) {
		super();
		this.coreAccn = coreAccn;
		this.coreUsr = coreUsr;
	}

	// Override Methods
	///////////////////
	@Override
	public int compareTo(CkAccnUser o) {
		return 0;
	}

	@Override
	public void init() {
		
	}
	
	// Properties
	/////////////
	public CoreAccn getCoreAccn() {
		return coreAccn;
	}

	public void setCoreAccn(CoreAccn coreAccn) {
		this.coreAccn = coreAccn;
	}

	public CoreUsr getCoreUsr() {
		return coreUsr;
	}

	public void setCoreUsr(CoreUsr coreUsr) {
		this.coreUsr = coreUsr;
	}

	public String getFfAccnId() {
		return ffAccnId;
	}

	public void setFfAccnId(String ffAccnId) {
		this.ffAccnId = ffAccnId;
	}
	
}
