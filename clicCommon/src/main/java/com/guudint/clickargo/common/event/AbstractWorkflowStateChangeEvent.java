package com.guudint.clickargo.common.event;

import org.springframework.context.ApplicationEvent;

import com.guudint.clickargo.common.enums.WorkflowTypeEnum;
import com.vcc.camelone.common.dto.AbstractDTO;

public abstract class AbstractWorkflowStateChangeEvent<E, D extends AbstractDTO<D, E>> extends ApplicationEvent {

	private static final long serialVersionUID = -8282573202695769302L;
	protected WorkflowTypeEnum wfType;
	protected D dto;

	public AbstractWorkflowStateChangeEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

	public AbstractWorkflowStateChangeEvent(Object source, WorkflowTypeEnum wfType, D dto) {
		super(source);
		this.wfType = wfType;
		this.dto = dto;
	}

	/**
	 * @return the wfType
	 */
	public WorkflowTypeEnum getWfType() {
		return wfType;
	}

	/**
	 * @param wfType the wfType to set
	 */
	public void setWfType(WorkflowTypeEnum wfType) {
		this.wfType = wfType;
	}

	/**
	 * @return the dto
	 */
	public D getDto() {
		return dto;
	}

	/**
	 * @param dto the dto to set
	 */
	public void setDto(D dto) {
		this.dto = dto;
	}

}
