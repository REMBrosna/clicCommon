package com.guudint.clickargo.common.event;

import com.guudint.clickargo.common.enums.WorkflowTypeEnum;
import com.vcc.camelone.common.dto.AbstractDTO;

public class MonitoringEvent<E, D extends AbstractDTO<D, E>> extends AbstractWorkflowStateChangeEvent<E, D> {

	private static final long serialVersionUID = -175812572950411927L;

	public MonitoringEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

	public MonitoringEvent(Object source, WorkflowTypeEnum wfType, D dto) {
		super(source);
		this.wfType = wfType;
		this.dto = dto;
	}

}
