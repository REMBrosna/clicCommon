package com.guudint.clickargo.common.model;

import com.vcc.camelone.common.COAbstractEntity;

public class ValidationError extends COAbstractEntity<ValidationError> {

	// Static Attributes
	////////////////////
	private static final long serialVersionUID = -4084625222589694110L;

	// Attributes
	////////////////////
	private String fieldName;
	private String fieldLabel;
	private String errorType;
	private String errorDescription;

	// Constructors
	///////////////
	public ValidationError() {
	}

	/**
	 * @param errorCode
	 * @param fieldName
	 * @param fieldDescription
	 * @param errorType
	 * @param errorDescription
	 */
	public ValidationError(String fieldName, String errorType, String errorDescription) {
		this.fieldName = fieldName;
		this.errorType = errorType;
		this.errorDescription = errorDescription;
	}

	public ValidationError(String fieldName, String fieldLabel, String errorType, String errorDescription) {
		this.fieldName = fieldName;
		this.fieldLabel = fieldLabel;
		this.errorType = errorType;
		this.errorDescription = errorDescription;
	}

	// Override Methods
	///////////////////
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 *
	 */
	@Override
	public int compareTo(ValidationError o) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
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
	//////////////
	/**
	 * @return the errorType
	 */
	public String getErrorType() {
		return errorType;
	}

	/**
	 * @param errorType the errorType to set
	 */
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	/**
	 * @return the errorDescription
	 */
	public String getErrorDescription() {
		return errorDescription;
	}

	/**
	 * @param errorDescription the errorDescription to set
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * @return the fieldLabel
	 */
	public String getFieldLabel() {
		return fieldLabel;
	}

	/**
	 * @param fieldLabel the fieldLabel to set
	 */
	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}

}
