package com.guudint.clickargo.task.service;

import java.util.List;
import java.util.Map;

import com.guudint.clickargo.task.dto.CkTaskAttach;
import com.guudint.clickargo.task.service.ITaskVerify.TaskVerifyType;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.common.controller.entity.EntityFilterResponse;
import com.vcc.camelone.common.dto.AbstractDTO;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.exception.ValidationException;
import com.vcc.camelone.common.service.entity.IEntityService;

public interface ITaskService<D extends AbstractDTO<D, E>, E, K> extends IEntityService<E, K, D> {

	/**
	 * Create a template for the task
	 * 
	 * @param principal
	 * @return
	 * @throws ParameterException
	 * @throws EntityNotFoundException
	 * @throws ProcessingException
	 */
	public D newTask(Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException;

	/**
	 * Create a new task
	 * 
	 * @param dto
	 * @param principal
	 * @return
	 * @throws ParameterException
	 * @throws ValidationException
	 * @throws ProcessingException
	 * @throws Exception
	 */
	 D createTask(D dto, Principal principal)
			throws ParameterException, ValidationException, ProcessingException, Exception;

	/**
	 * Get task by id
	 * 
	 * @param id
	 * @param principal
	 * @return
	 * @throws ParameterException
	 * @throws ValidationException
	 * @throws ProcessingException
	 * @throws Exception
	 */
	public D getTask(String id, Principal principal)
			throws ParameterException, ValidationException, ProcessingException, Exception;

	/**
	 * Update task
	 * 
	 * @param dto
	 * @param principal
	 * @return
	 * @throws ParameterException
	 * @throws EntityNotFoundException
	 * @throws ValidationException
	 * @throws ProcessingException
	 * @throws Exception
	 */
	public D updateTask(D dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception;

	/**
	 * Submits the task
	 * 
	 * @param dto
	 * @param principal
	 * @return
	 * @throws ParameterException
	 * @throws EntityNotFoundException
	 * @throws ValidationException
	 * @throws ProcessingException
	 * @throws Exception
	 */
	public D submitTask(D dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception;

	/**
	 * Deletes the task
	 * 
	 * @param dto
	 * @param principal
	 * @return
	 * @throws ParameterException
	 * @throws EntityNotFoundException
	 * @throws ValidationException
	 * @throws ProcessingException
	 * @throws Exception
	 */
	public D deleteTask(D dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception;

	/**
	 * Verifies the task by Shipping Line
	 * 
	 * @param dto
	 * @param verifyType
	 * @param principal
	 * @return
	 * @throws ParameterException
	 * @throws EntityNotFoundException
	 * @throws ValidationException
	 * @throws ProcessingException
	 * @throws Exception
	 */
	public D verifyTask(D dto, TaskVerifyType verifyType, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception;

	/**
	 * Task completed successfully
	 * 
	 * @param dto
	 * @param principal
	 * @return
	 * @throws ParameterException
	 * @throws EntityNotFoundException
	 * @throws ValidationException
	 * @throws ProcessingException
	 * @throws Exception
	 */
	public D completTask(D dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception;

	/**
	 * Filter the task by criteria
	 * 
	 * @param params
	 * @param principal
	 * @return
	 * @throws ParameterException
	 * @throws EntityNotFoundException
	 * @throws ValidationException
	 * @throws ProcessingException
	 * @throws Exception
	 */
	public EntityFilterResponse filterTasks(Map<String, String> params, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception;

	// Attachment
	/////////////
	/**
	 * Create new attachment for the task
	 *
	 * @param taskAttach
	 * @param principal
	 * @return
	 * @throws ParameterException
	 * @throws EntityNotFoundException
	 * @throws ValidationException
	 * @throws ProcessingException
	 * @throws Exception
	 */
	public CkTaskAttach createAttachment(CkTaskAttach taskAttach, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception;

	/**
	 * @param taskId
	 * @param principal
	 * @return
	 * @throws ParameterException
	 * @throws EntityNotFoundException
	 * @throws ValidationException
	 * @throws ProcessingException
	 * @throws Exception
	 */
	public List<CkTaskAttach> getAttachments(String taskId, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception;

	/**
	 * Delete attachment of job
	 *
	 * @param taskAttach
	 * @param principal
	 * @return
	 * @throws ParameterException
	 * @throws EntityNotFoundException
	 * @throws ValidationException
	 * @throws ProcessingException
	 * @throws Exception
	 */
	public CkTaskAttach deleteAttachment(CkTaskAttach taskAttach, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception;

}
