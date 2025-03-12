package com.guudint.clickargo.job.event;

import org.springframework.context.ApplicationEvent;

import com.guudint.clickargo.job.service.IJobEvent;

public abstract class AbstractJobEvent<D> extends ApplicationEvent implements IJobEvent {

	// Static Attribute
	///////////////////
	private static final long serialVersionUID = -4638741424920284782L;

	// Attributes
	/////////////
	protected JobEvent jobEvent;
	protected D dto;

	// Constructor
	//////////////
	/**
	 * Constructor
	 * 
	 * @param source
	 */
	public AbstractJobEvent(Object source) {
		super(source);
	}

	/**
	 * @param source
	 * @param eventType
	 */
	public AbstractJobEvent(Object source, JobEvent jobEvent) {
		super(source);
		this.jobEvent = jobEvent;
	}

	/**
	 * @param source
	 * @param eventType
	 */
	public AbstractJobEvent(Object source, JobEvent jobEvent, D dto) {
		super(source);
		this.jobEvent = jobEvent;
		this.dto = dto;
	}	
	
	// Properties
	/////////////
	/**
	 * @return the jobEvent
	 */
	public JobEvent getJobEvent() {
		return jobEvent;
	}

	/**
	 * @param jobEvent the jobEvent to set
	 */
	public void setJobEvent(JobEvent jobEvent) {
		this.jobEvent = jobEvent;
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
