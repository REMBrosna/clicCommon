package com.guudint.clickargo.task.event;

import org.springframework.context.ApplicationEvent;

import com.guudint.clickargo.task.service.ITaskEvent;

public abstract class AbstractTaskEvent<D> extends ApplicationEvent implements ITaskEvent {

	// Static Attribute
	///////////////////
	private static final long serialVersionUID = -4638741424920284782L;

	// Attributes
	/////////////
	protected TaskEvent taskEvent;
	protected D dto;

	// Constructor
	//////////////
	/**
	 * Constructor
	 * 
	 * @param source
	 */
	public AbstractTaskEvent(Object source) {
		super(source);
	}

	/**
	 * @param source
	 * @param eventType
	 */
	public AbstractTaskEvent(Object source, TaskEvent taskEvent) {
		super(source);
		this.taskEvent = taskEvent;
	}

	/**
	 * @param source
	 * @param eventType
	 */
	public AbstractTaskEvent(Object source, TaskEvent taskEvent, D dto) {
		super(source);
		this.taskEvent = taskEvent;
		this.dto = dto;
	}


	// Properties
	/////////////
	/**
	 * @return the taskEvent
	 */
	public TaskEvent getTaskEvent() {
		return taskEvent;
	}

	/**
	 * @param taskEvent the taskEvent to set
	 */
	public void setTaskEvent(TaskEvent taskEvent) {
		this.taskEvent = taskEvent;
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
