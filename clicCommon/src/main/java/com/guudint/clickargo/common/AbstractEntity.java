package com.guudint.clickargo.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guudint.clickargo.common.model.ValidationError;
import com.vcc.camelone.common.dto.AbstractDTO;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.exception.ValidationException;

public abstract class AbstractEntity<D extends AbstractDTO<D, E>, E>  {
	
	private static Logger log = LogManager.getLogger(AbstractEntity.class);
	
	// Attributes
	/////////////
	@Autowired
	protected Validator validator;

	
	// Helper Methods
	/////////////////
	/**
	 * Validate data fields of dto
	 * 
	 * @param dto
	 * @param groupClass
	 * @throws ValidationException
	 * @throws ProcessingException
	 * @throws ParameterException
	 */
	protected void validate(D dto, Class<?>... groupClass)
			throws ValidationException, ParameterException, ProcessingException {
		log.debug("validate");

		if (null == dto)
			throw new ParameterException("param dto null");

		Set<ConstraintViolation<D>> constraintViolations = validator.validate(dto, groupClass);
		Set<ConstraintViolation<?>> constriantViolationSet = new HashSet<>(constraintViolations);
		if (constraintViolations.isEmpty())
			return;

		throw new ValidationException(constraintViolationMap(constriantViolationSet));
	}

	/**
	 * Map constraint validations into JSON key-value format list
	 * 
	 * @param constraintViolations
	 * @return
	 * @throws ParameterException
	 * @throws ProcessingException
	 */
	protected String constraintViolationMap(Set<ConstraintViolation<?>> constraintViolations)
			throws ParameterException, ProcessingException {
		log.debug("errorMap");

		if (null == constraintViolations)
			throw new ParameterException("param erorrs null");
		if (constraintViolations.isEmpty())
			throw new ParameterException("param erorrs empty");

		String json;
		try {
			Map<String, String> constraintViolationMap = new HashMap<>();
			constraintViolations.stream().forEach(cv -> {
				log.error(cv.getMessage() + " " + cv.getPropertyPath() + "  " + cv);
				constraintViolationMap.put(cv.getPropertyPath().toString(), cv.getMessage());
			});

			json = (new ObjectMapper()).writeValueAsString(constraintViolationMap);
		} catch (Exception ex) {
			log.error("errorMap", ex);
			throw new ProcessingException(ex.getMessage());
		}
		return json;
	}

	/**
	 * Map business validation errors into JSON key-value format list
	 * 
	 * @param validationErrors
	 * @return
	 * @throws ParameterException
	 * @throws ProcessingException
	 */
	protected String validationErrorMap(List<ValidationError> validationErrors)
			throws ParameterException, ProcessingException {
		log.debug("validationMap");

		if (null == validationErrors)
			throw new ParameterException("param errros null");
		if (validationErrors.isEmpty())
			throw new ProcessingException("param errros empty");

		String json;
		try {
			Map<String, String> validationMap = new HashMap<>();
			validationErrors.stream().forEach(ve -> {
				log.error(ve.getErrorType() + " " + ve.getErrorDescription() + "  " + ve);
				validationMap.put(ve.getErrorType().toString(), ve.getErrorDescription());
			});

			json = (new ObjectMapper()).writeValueAsString(validationMap);

		} catch (Exception ex) {
			log.error("errorMap", ex);
			throw new ProcessingException(ex.getMessage());
		}
		return json;
	}	

}
