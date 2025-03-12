package com.guudint.clickargo.common.dto;

import com.guudint.clickargo.common.enums.MstEntityStates;
import com.guudint.clickargo.common.enums.MstEntityTypes;
import com.guudint.clickargo.common.enums.MstFormTypes;

/**
 * Simplified {@code CkFormControl} request DTO.
 */
public class CkFormControlReqDto {

	private MstEntityTypes entityType;
	private MstEntityStates entityState;
	private MstFormTypes page;

	public MstEntityTypes getEntityType() {
		return entityType;
	}

	public void setEntityType(MstEntityTypes entityType) {
		this.entityType = entityType;
	}

	public MstEntityStates getEntityState() {
		return entityState;
	}

	public void setEntityState(MstEntityStates entityState) {
		this.entityState = entityState;
	}

	public MstFormTypes getPage() {
		return page;
	}

	public void setPage(MstFormTypes page) {
		this.page = page;
	}

}
