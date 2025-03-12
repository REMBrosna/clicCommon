/**
 * 
 */
package com.guudint.clickargo.job.service;

import java.util.List;

import com.guudint.clickargo.job.dto.CkJobAttach;
import com.guudint.clickargo.job.dto.CkJobQuery;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.common.dto.AbstractDTO;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.exception.ValidationException;
import com.vcc.camelone.common.service.entity.IEntityService;

/**
 * @author billy
 *
 */
public interface IJobService<D extends AbstractDTO<D, E>, E, K> extends IEntityService<E, K, D> {

	/**
	 * Create a template for the job
	 * 
	 * @param principal
	 * @return
	 * @throws ParameterException
	 * @throws EntityNotFoundException
	 * @throws ProcessingException
	 */
	public D newJob(Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException;

	/**
	 * Create a new job
	 * 
	 * @param dto
	 * @param principal
	 * @return
	 * @throws ParameterException
	 * @throws ValidationException
	 * @throws ProcessingException
	 * @throws Exception
	 */
	public D createJob(D dto, Principal principal)
			throws ParameterException, ValidationException, ProcessingException, Exception;

	/**
	 * Get job by id
	 * 
	 * @param id
	 * @param principal
	 * @return
	 * @throws ParameterException
	 * @throws ValidationException
	 * @throws ProcessingException
	 * @throws Exception
	 */
	public D getJob(String id, Principal principal)
			throws ParameterException, ValidationException, ProcessingException, Exception;

	/**
	 * Update job
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
	public D updateJob(D dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception;

	/**
	 * Submits the job
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
	public D submitJob(D dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception;

	/**
	 * Rejects the job
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
	public D rejectJob(D dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception;

	/**
	 * Cancel the job
	 * 
	 * @param dto
	 * @return
	 * @throws ParameterException
	 * @throws EntityNotFoundException
	 * @throws ProcessingException
	 * @throws Exception
	 */
	public D cancelJob(D dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception;

	/**
	 * Deletes the job
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
	public D deleteJob(D dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception;

	/**
	 * Confirm the job
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
	public D confirmJob(D dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception;

	/**
	 * Pays fo the job
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
	public D payJob(D dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception;

	/**
	 * Job has been paid (notification from bank)
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
	public D paidJob(D dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception;

	/**
	 * Job completed successfully
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
	public D completJob(D dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception;
	
	
	/**
	 * Is permission to Update, Delete and Route. 
	 * @param dto
	 * @param principal
	 * @return
	 */
	public void isPermissionOperateJob(D dto, Principal principal) throws Exception;

	// Attachment
	/////////////
	/**
	 * Create new attachment for the job
	 * 
	 * @param jobAttach
	 * @param principal
	 * @return
	 * @throws ParameterException
	 * @throws EntityNotFoundException
	 * @throws ValidationException
	 * @throws ProcessingException
	 * @throws Exception
	 */
	public CkJobAttach createAttachment(CkJobAttach jobAttach, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception;

	/**
	 * @param jobId
	 * @param principal
	 * @return
	 * @throws ParameterException
	 * @throws EntityNotFoundException
	 * @throws ValidationException
	 * @throws ProcessingException
	 * @throws Exception
	 */
	public List<CkJobAttach> getAttachments(String jobId, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception;

	/**
	 * Delete attachment of job
	 * 
	 * @param jobAttach
	 * @param principal
	 * @return
	 * @throws ParameterException
	 * @throws EntityNotFoundException
	 * @throws ValidationException
	 * @throws ProcessingException
	 * @throws Exception
	 */
	public CkJobAttach deleteAttachment(CkJobAttach jobAttach, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception;

	// Query
	////////
	/**
	 * Create new query for the job
	 * 
	 * @param jobQuery
	 * @param principal
	 * @return
	 * @throws ParameterException
	 * @throws EntityNotFoundException
	 * @throws ValidationException
	 * @throws ProcessingException
	 * @throws Exception
	 */
	public CkJobQuery createQuery(CkJobQuery jobQuery, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception;

	/**
	 * Get queries of job
	 * 
	 * @param jobId
	 * @param principal
	 * @return
	 * @throws ParameterException
	 * @throws EntityNotFoundException
	 * @throws ValidationException
	 * @throws ProcessingException
	 * @throws Exception
	 */
	public List<CkJobQuery> getQueries(String jobId, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception;

	/**
	 * Update the query of the job
	 * 
	 * @param jobQuery
	 * @param principal
	 * @return
	 * @throws ParameterException
	 * @throws EntityNotFoundException
	 * @throws ValidationException
	 * @throws ProcessingException
	 * @throws Exception
	 */
	public CkJobQuery updateQuery(CkJobQuery jobQuery, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception;

	/**
	 * Delete the query of the job
	 * 
	 * @param jobQuery
	 * @param principal
	 * @return
	 * @throws ParameterException
	 * @throws EntityNotFoundException
	 * @throws ValidationException
	 * @throws ProcessingException
	 * @throws Exception
	 */
	public CkJobQuery deleteQuery(CkJobQuery jobQuery, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception;
}
