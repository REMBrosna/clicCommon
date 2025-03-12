package com.guudint.clickargo.validator;

import java.util.List;

import com.guudint.clickargo.common.model.ValidationError;

public interface IValidate {

	/**
	 * @return list of Error
	 */
	public List<ValidationError> validate();
}
