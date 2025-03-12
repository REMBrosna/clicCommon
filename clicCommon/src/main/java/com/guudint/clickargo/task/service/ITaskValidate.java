/**
 * 
 */
package com.guudint.clickargo.task.service;

import java.util.List;

import com.guudint.clickargo.common.model.ValidationError;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.common.dto.AbstractDTO;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;

/**
 * @author billy
 *
 */
public interface ITaskValidate<D extends AbstractDTO<D, ?>> {

	/**
	 * @param dto
	 * @param principal
	 * @return
	 * @throws ParameterException
	 * @throws ProcessingException
	 */
	public List<ValidationError> validateCreate(D dto, Principal principal)
			throws ParameterException, ProcessingException;

	/**
	 * @param dto
	 * @param principal
	 * @return
	 * @throws ParameterException
	 * @throws ProcessingException
	 */
	public List<ValidationError> validateUpdate(D dto, Principal principal)
			throws ParameterException, ProcessingException;

	/**
	 * @param dto
	 * @param principal
	 * @return
	 * @throws ParameterException
	 * @throws ProcessingException
	 */
	public List<ValidationError> validateSubmit(D dto, Principal principal)
			throws ParameterException, ProcessingException;

	/**
	 * @param dto
	 * @param principal
	 * @return
	 * @throws ParameterException
	 * @throws ProcessingException
	 */
	public List<ValidationError> validateDelete(D dto, Principal principal)
			throws ParameterException, ProcessingException;

	/**
	 * @param dto
	 * @param principal
	 * @return
	 * @throws ParameterException
	 * @throws ProcessingException
	 */
	public List<ValidationError> validateVerify(D dto, Principal principal)
			throws ParameterException, ProcessingException;

	/**
	 * @param dto
	 * @param principal
	 * @return
	 * @throws ParameterException
	 * @throws ProcessingException
	 */
	public List<ValidationError> validateComplete(D dto, Principal principal)
			throws ParameterException, ProcessingException;

}
