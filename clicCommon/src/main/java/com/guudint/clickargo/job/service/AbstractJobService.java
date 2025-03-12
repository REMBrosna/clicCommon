package com.guudint.clickargo.job.service;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guudint.clickargo.common.AbstractClickCargoEntityService;
import com.guudint.clickargo.common.CkUtil;
import com.guudint.clickargo.common.ICkConstant;
import com.guudint.clickargo.common.RecordStatus;
import com.guudint.clickargo.common.dto.CkRecordDate;
import com.guudint.clickargo.common.model.TCkRecordDate;
import com.guudint.clickargo.common.model.ValidationError;
import com.guudint.clickargo.common.service.ICkSession;
import com.guudint.clickargo.job.dto.CkJob;
import com.guudint.clickargo.job.dto.CkJobAttach;
import com.guudint.clickargo.job.dto.CkJobQuery;
import com.guudint.clickargo.job.event.AbstractJobEvent;
import com.guudint.clickargo.job.model.TCkJob;
import com.guudint.clickargo.job.model.TCkJobAttach;
import com.guudint.clickargo.job.model.TCkJobQuery;
import com.guudint.clickargo.master.dao.CoreAccnDao;
import com.guudint.clickargo.master.dto.CkMstJobState;
import com.guudint.clickargo.master.dto.CkMstJobType;
import com.guudint.clickargo.master.dto.CkMstShipmentType;
import com.guudint.clickargo.master.enums.JobStates;
import com.guudint.clickargo.master.enums.JobTypes;
import com.guudint.clickargo.master.enums.ShipmentTypes;
import com.guudint.clickargo.master.service.ICkMstRecords;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.ccm.model.TCoreAccn;
import com.vcc.camelone.ccm.util.Constant;
import com.vcc.camelone.common.controller.entity.EntityOrderBy;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.common.dto.AbstractDTO;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.exception.ValidationException;
import com.vcc.camelone.common.service.entity.IEntityService;
import com.vcc.camelone.master.dto.MstAttType;
import com.vcc.camelone.master.model.TMstAttType;
import com.vcc.camelone.util.bean.COBeansUtil;
public abstract class AbstractJobService<D extends AbstractDTO<D, E>, E, K>
		extends AbstractClickCargoEntityService<E, K, D> implements IJobService<D, E, K>, IJobEvent {

	// Static Attributes
	////////////////////
	private static Logger log = LogManager.getLogger(AbstractJobService.class);

	// Attributes
	/////////////
	@Autowired
	protected IJobValidate<D> bzValidator;

	@Autowired
	protected ApplicationEventPublisher eventPublisher;

	@Autowired
	protected GenericDao<TCkJobAttach, String> ckJobAttachDao;

	@Autowired
	protected GenericDao<TCkJobQuery, String> ckJobQueryDao;

	@Autowired
	protected GenericDao<TCkJob, String> ckJobDao;

	@Autowired
	protected GenericDao<TCkRecordDate, String> ckRecordDateDao;

	@Autowired
	protected IEntityService<TCkJob, String, CkJob> ckJobService;

	@Autowired
	protected IEntityService<TCkRecordDate, String, CkRecordDate> ckRecordService;

	@Autowired
	protected ICkMstRecords clicMasterRecrodsSvc;

	@Autowired
	protected ICkSession ckSession;

	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private CoreAccnDao coreAccnDao;

	// Constructor
	//////////////

	public AbstractJobService(String daoName, String moduleName, String entityName, String tableName) {
		super(daoName, moduleName, entityName, tableName);
	}

	// Validate Callback
	///////////////////

	/**
	 * @param jobEvent
	 * @return
	 */
	protected abstract Class<?>[] _validateGroupClass(JobEvent jobEvent);

	// Audits and Error Callback
	////////////////////////////
	/**
	 * Audit the event into T_CORE_AUDIT_LOG
	 * 
	 * @param jobEvent
	 * @param dto
	 * @param principal
	 */
	protected abstract void _auditEvent(JobEvent jobEvent, D dto, Principal principal);

	/**
	 * Audit the event into T_CORE_EXCEPTIONS
	 * 
	 * @param jobEvent
	 * @param dto
	 * @param principal
	 */
	protected abstract void _auditError(JobEvent jobEvent, D dto, Exception ex, Principal principal);

	// Job SpringEvent Callback
	///////////////////////////
	/**
	 * Get the spring event for callback
	 * 
	 * @param jobEvent
	 * @param dto
	 * @param principal
	 * @return
	 */
	protected abstract AbstractJobEvent<D> _getJobEvent(JobEvent jobEvent, D dto, Principal principal);

	// Job Event Callback
	///////////////////////
	/**
	 * Create a new instance of the job DTO
	 * 
	 * @return
	 */
	protected abstract D _newJob(Principal p) throws ParameterException, EntityNotFoundException, ProcessingException;

	/**
	 * Create the job and update DTO to entity and persist into DB
	 * 
	 * @param dto
	 * @param principal
	 * @return
	 */
	protected abstract D _createJob(D dto, CkJob parentJob, Principal principal)
			throws ParameterException, ValidationException, ProcessingException, Exception;

	/**
	 * Change state of job to Submit
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
	protected abstract D _submitJob(D dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception;

	/**
	 * Change the state of the job to Reject
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
	protected abstract D _rejectJob(D dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception;

	/**
	 * Change the state of the job to Cancel
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
	protected abstract D _cancelJob(D dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception;

	/**
	 * Change the state of the job to Confirm
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
	protected abstract D _confirmJob(D dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception;

	/**
	 * Change the state of the job to pay
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
	protected abstract D _payJob(D dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception;

	/**
	 * Change the state of the job to paid
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
	protected abstract D _paidJob(D dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception;

	/**
	 * @param dto
	 * @param principal
	 * @return
	 * @throws ParameterException
	 * @throws EntityNotFoundException
	 * @throws ValidationException
	 * @throws ProcessingException
	 * @throws Exception
	 */
	protected abstract D _completeJob(D dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception;

	// Interface Methods (Jobs)
	///////////////////////////
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.guudint.clickargo.job.service.IJobService#newJob(Principal)
	 * 
	 */
	@Override
	public D newJob(Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		log.debug("newJob");

		if (null == principal)
			throw new ParameterException("principal null");
		return newObj(principal);
	}

	@Override
	public D newObj(Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return _newJob(principal);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.guudint.clickargo.job.service.IJobService#createJob(com.vcc.camelone.common.dto.AbstractDTO,
	 *      com.vcc.camelone.cac.model.Principal)
	 * 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public D createJob(D dto, Principal principal)
			throws ParameterException, ValidationException, ProcessingException, Exception {
		// TODO Auto-generated method stub
		log.debug("createJob");

		if (null == dto)
			throw new ParameterException("param dto null");
		if (null == principal)
			throw new ParameterException("principal null");

		// Data constraints validation
		Class<?>[] groupClasses = _validateGroupClass(JobEvent.CREATE);
		validate(dto, groupClasses);

		// Business validation
		List<ValidationError> validationErrors = bzValidator.validateCreate(dto, principal);
		if (null != validationErrors && !validationErrors.isEmpty())
			throw new ValidationException(validationErrorMap(validationErrors));

		D _dto = null;
		CkJob job = null;
		Field field = dto.getClass().getDeclaredField("TCkJob");
		field.setAccessible(true); // Allow access to private fields
		job = (CkJob) field.get(dto);
		try {
			// Create the parent here
			CkJob parentJob = createParentJob(principal, job);

			if (parentJob != null) {
				_dto = _createJob(dto, parentJob, principal);
				_auditEvent(JobEvent.CREATE, _dto, principal);

				AbstractJobEvent<D> event = _getJobEvent(JobEvent.CREATE, _dto, principal);
				if (null != event)
					eventPublisher.publishEvent(event);
			}

			return _dto;
		} catch (Exception ex) {
			log.error("createJob", ex);
			_auditError(JobEvent.CREATE, _dto, ex, principal);
			throw ex;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object addObj(Object object, Principal principal)
			throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException {
		try {
			return createJob((D) object, principal);
		} catch (ParameterException | EntityNotFoundException | ValidationException | ProcessingException ex) {
			throw ex;

		} catch (Exception ex) {
			throw new ProcessingException(ex);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.guudint.clickargo.job.service.IJobService#getJob(java.lang.String,
	 *      com.vcc.camelone.cac.model.Principal)
	 * 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public D getJob(String id, Principal principal)
			throws ParameterException, ValidationException, ProcessingException, Exception {
		return findById(id);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.guudint.clickargo.job.service.IJobService#updateJob(com.vcc.camelone.common.dto.AbstractDTO,
	 *      com.vcc.camelone.cac.model.Principal)
	 * 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public D updateJob(D dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception {
		// TODO Auto-generated method stub
		log.debug("updateJob");

		if (null == dto)
			throw new ParameterException("param dto null");
		if (null == principal)
			throw new ParameterException("principal null");

		// Data constraints validation
		Class<?>[] groupClasses = _validateGroupClass(JobEvent.UPDATE);
		validate(dto, groupClasses);

		// Business validation
		List<ValidationError> validationErrors = bzValidator.validateUpdate(dto, principal);
		if (null != validationErrors && !validationErrors.isEmpty())
			throw new ValidationException(validationErrorMap(validationErrors));

		D _dto = null;
		try {
			_dto = update(dto, principal);

			AbstractJobEvent<D> event = _getJobEvent(JobEvent.UPDATE, _dto, principal);
			if (null != event)
				eventPublisher.publishEvent(event);

			return _dto;
		} catch (Exception ex) {
			log.error("updateJob", ex);
			_auditError(JobEvent.UPDATE, _dto, ex, principal);
			throw ex;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Object updateObj(Object object, Principal principal)
			throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException {
		try {
			return updateJob((D) object, principal);
		} catch (ParameterException | EntityNotFoundException | ValidationException | ProcessingException ex) {
			throw ex;

		} catch (Exception ex) {
			throw new ProcessingException(ex);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.guudint.clickargo.job.service.IJobService#submitJob(com.vcc.camelone.common.dto.AbstractDTO,
	 *      com.vcc.camelone.cac.model.Principal)
	 * 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public D submitJob(D dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception {
		// TODO Auto-generated method stub
		log.debug("submitJob");

		if (null == dto)
			throw new ParameterException("param dto null");
		if (null == principal)
			throw new ParameterException("principal null");

		// Data constraints validation
		Class<?>[] groupClasses = _validateGroupClass(JobEvent.SUBMIT);
		validate(dto, groupClasses);

		// Business validation
		List<ValidationError> validationErrors = bzValidator.validateSubmit(dto, principal);
		if (null != validationErrors && !validationErrors.isEmpty())
			throw new ValidationException(validationErrorMap(validationErrors));

		D _dto = null;
		try {
			_dto = _submitJob(dto, principal);
			_auditEvent(JobEvent.SUBMIT, _dto, principal);

			AbstractJobEvent<D> event = _getJobEvent(JobEvent.SUBMIT, _dto, principal);
			if (null != event)
				eventPublisher.publishEvent(event);

			return _dto;
		} catch (Exception ex) {
			log.error("submitJob", ex);
			_auditError(JobEvent.SUBMIT, _dto, ex, principal);
			throw ex;
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.guudint.clickargo.job.service.IJobService#rejectJob(com.vcc.camelone.common.dto.AbstractDTO,
	 *      com.vcc.camelone.cac.model.Principal)
	 * 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public D rejectJob(D dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception {
		// TODO Auto-generated method stub
		log.debug("rejectJob");

		if (null == dto)
			throw new ParameterException("param dto null");
		if (null == principal)
			throw new ParameterException("principal null");

		// Data constraints validation
		Class<?>[] groupClasses = _validateGroupClass(JobEvent.REJECT);
		validate(dto, groupClasses);

		// Business validation
		List<ValidationError> validationErrors = bzValidator.validateReject(dto, principal);
		if (null != validationErrors && !validationErrors.isEmpty())
			throw new ValidationException(validationErrorMap(validationErrors));

		D _dto = null;
		try {
			_dto = _rejectJob(dto, principal);
			_auditEvent(JobEvent.REJECT, _dto, principal);

			AbstractJobEvent<D> event = _getJobEvent(JobEvent.REJECT, _dto, principal);
			if (null != event)
				eventPublisher.publishEvent(event);

			return _dto;
		} catch (Exception ex) {
			log.error("rejectJob", ex);
			_auditError(JobEvent.REJECT, _dto, ex, principal);
			throw ex;
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.guudint.clickargo.job.service.IJobService#cancelJob(com.vcc.camelone.common.dto.AbstractDTO,
	 *      com.vcc.camelone.cac.model.Principal)
	 * 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public D cancelJob(D dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception {
		// TODO Auto-generated method stub
		log.debug("cancelJob");

		if (null == dto)
			throw new ParameterException("param dto null");
		if (null == principal)
			throw new ParameterException("principal null");

		// Data constraints validation
		Class<?>[] groupClasses = _validateGroupClass(JobEvent.CANCEL);
		validate(dto, groupClasses);

		// Business validation
		List<ValidationError> validationErrors = bzValidator.validateCancel(dto, principal);
		if (null != validationErrors && !validationErrors.isEmpty())
			throw new ValidationException(validationErrorMap(validationErrors));

		D _dto = null;
		try {
			_dto = _cancelJob(dto, principal);
			_auditEvent(JobEvent.CANCEL, _dto, principal);

			AbstractJobEvent<D> event = _getJobEvent(JobEvent.CANCEL, _dto, principal);
			if (null != event)
				eventPublisher.publishEvent(event);

			return _dto;
		} catch (Exception ex) {
			log.error("cancelJob", ex);
			_auditError(JobEvent.CANCEL, _dto, ex, principal);
			throw ex;
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.guudint.clickargo.job.service.IJobService#deleteJob(com.vcc.camelone.common.dto.AbstractDTO,
	 *      com.vcc.camelone.cac.model.Principal)
	 * 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public D deleteJob(D dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception {
		// TODO Auto-generated method stub
		log.debug("deleteJob");

		if (null == dto)
			throw new ParameterException("param dto null");
		if (null == principal)
			throw new ParameterException("principal null");

		// Data constraints validation
		Class<?>[] groupClasses = _validateGroupClass(JobEvent.DELETE);
		validate(dto, groupClasses);

		// Business validation
		List<ValidationError> validationErrors = bzValidator.validateDelete(dto, principal);
		if (null != validationErrors && !validationErrors.isEmpty())
			throw new ValidationException(validationErrorMap(validationErrors));

		D _dto = null;
		try {
			_dto = delete(dto, principal);
			_auditEvent(JobEvent.DELETE, _dto, principal);

			AbstractJobEvent<D> event = _getJobEvent(JobEvent.DELETE, _dto, principal);
			if (null != event)
				eventPublisher.publishEvent(event);

			return _dto;
		} catch (Exception ex) {
			log.error("deleteJob", ex);
			_auditError(JobEvent.DELETE, _dto, ex, principal);
			throw ex;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public D deleteObj(Object obj, Principal principal)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		try {
			return deleteJob((D) obj, principal);
		} catch (Exception ex) {
			throw new ProcessingException(ex);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.guudint.clickargo.job.service.IJobService#confirmJob(com.vcc.camelone.common.dto.AbstractDTO,
	 *      com.vcc.camelone.cac.model.Principal)
	 * 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public D confirmJob(D dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception {
		// TODO Auto-generated method stub
		log.debug("confirmJob");

		if (null == dto)
			throw new ParameterException("param dto null");
		if (null == principal)
			throw new ParameterException("principal null");

		// Data constraints validation
		Class<?>[] groupClasses = _validateGroupClass(JobEvent.CONFIRM);
		validate(dto, groupClasses);

		// Business validation
		List<ValidationError> validationErrors = bzValidator.validateConfirm(dto, principal);
		if (null != validationErrors && !validationErrors.isEmpty())
			throw new ValidationException(validationErrorMap(validationErrors));

		D _dto = null;
		try {
			_dto = _confirmJob(dto, principal);
			_auditEvent(JobEvent.CONFIRM, _dto, principal);

			AbstractJobEvent<D> event = _getJobEvent(JobEvent.CONFIRM, _dto, principal);
			if (null != event)
				eventPublisher.publishEvent(event);

			return _dto;
		} catch (Exception ex) {
			log.error("confirmJob", ex);
			_auditError(JobEvent.CONFIRM, _dto, ex, principal);
			throw ex;
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.guudint.clickargo.job.service.IJobService#payJob(com.vcc.camelone.common.dto.AbstractDTO,
	 *      com.vcc.camelone.cac.model.Principal)
	 * 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public D payJob(D dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception {
		// TODO Auto-generated method stub
		log.debug("payJob");

		if (null == dto)
			throw new ParameterException("param dto null");
		if (null == principal)
			throw new ParameterException("principal null");

		// Data constraints validation
		Class<?>[] groupClasses = _validateGroupClass(JobEvent.PAY);
		validate(dto, groupClasses);

		// Business validation
		List<ValidationError> validationErrors = bzValidator.validatePay(dto, principal);
		if (null != validationErrors && !validationErrors.isEmpty())
			throw new ValidationException(validationErrorMap(validationErrors));

		D _dto = null;
		try {
			_dto = _payJob(dto, principal);
			_auditEvent(JobEvent.PAY, _dto, principal);

			AbstractJobEvent<D> event = _getJobEvent(JobEvent.PAY, _dto, principal);
			if (null != event)
				eventPublisher.publishEvent(event);

			return _dto;
		} catch (Exception ex) {
			log.error("payJob", ex);
			_auditError(JobEvent.PAY, _dto, ex, principal);
			throw ex;
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.guudint.clickargo.job.service.IJobService#paidJob(com.vcc.camelone.common.dto.AbstractDTO,
	 *      com.vcc.camelone.cac.model.Principal)
	 * 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public D paidJob(D dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception {
		// TODO Auto-generated method stub
		log.debug("paidJob");

		if (null == dto)
			throw new ParameterException("param dto null");
		if (null == principal)
			throw new ParameterException("principal null");

		// Data constraints validation
		Class<?>[] groupClasses = _validateGroupClass(JobEvent.PAID);
		validate(dto, groupClasses);

		// Business validation
		List<ValidationError> validationErrors = bzValidator.validatePaid(dto, principal);
		if (null != validationErrors && !validationErrors.isEmpty())
			throw new ValidationException(validationErrorMap(validationErrors));

		D _dto = null;
		try {
			_dto = _paidJob(dto, principal);
			_auditEvent(JobEvent.PAID, _dto, principal);

			AbstractJobEvent<D> event = _getJobEvent(JobEvent.PAID, _dto, principal);
			if (null != event)
				eventPublisher.publishEvent(event);

			return _dto;
		} catch (Exception ex) {
			log.error("paidJob", ex);
			_auditError(JobEvent.PAID, _dto, ex, principal);
			throw ex;
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.guudint.clickargo.job.service.IJobService#completJob(com.vcc.camelone.common.dto.AbstractDTO,
	 *      com.vcc.camelone.cac.model.Principal)
	 * 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public D completJob(D dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception {
		// TODO Auto-generated method stub
		log.debug("completJob");

		if (null == dto)
			throw new ParameterException("param dto null");
		if (null == principal)
			throw new ParameterException("principal null");

		// Data constraints validation
		Class<?>[] groupClasses = _validateGroupClass(JobEvent.COMPLETE);
		validate(dto, groupClasses);

		// Business validation
		List<ValidationError> validationErrors = bzValidator.validateComplete(dto, principal);
		if (null != validationErrors && !validationErrors.isEmpty())
			throw new ValidationException(validationErrorMap(validationErrors));

		D _dto = null;
		try {
			_dto = _completeJob(dto, principal);
			_auditEvent(JobEvent.COMPLETE, _dto, principal);

			AbstractJobEvent<D> event = _getJobEvent(JobEvent.COMPLETE, _dto, principal);
			if (null != event)
				eventPublisher.publishEvent(event);

			return _dto;
		} catch (Exception ex) {
			log.error("completJob", ex);
			_auditError(JobEvent.COMPLETE, _dto, ex, principal);
			throw ex;
		}
	}
	
	/**
	 * Is permission to Update, Delete and Route. 
	 * @param dto
	 * @param principal
	 * @return default is true
	 */
	@Override
	public void isPermissionOperateJob(D dto, Principal principal) throws Exception {

	}


	// Interface Methods (Attachments)
	//////////////////////////////////
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.guudint.clickargo.job.service.IJobService#createAttachment(com.guudint.clickargo.job.dto.CkJobAttach,
	 *      com.vcc.camelone.cac.model.Principal)
	 * 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CkJobAttach createAttachment(CkJobAttach jobAttach, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception {
		log.debug("createAttachment");

		if (null == jobAttach)
			throw new ParameterException("param jobAttach null");
		if (null == principal)
			throw new ParameterException("principal null");

		Optional<String> opJobId = Optional.ofNullable(jobAttach.getTCkJob().getJobId());
		if (!opJobId.isPresent())
			throw new ProcessingException("jobId " + jobAttach.getTCkJob().getJobId() + "null or empty");

		TCkJob parentJob = ckJobDao.find(opJobId.get());
		if (null == parentJob)
			throw new ProcessingException("parent job not found: " + opJobId.get());

		TCkJobAttach _jobAttach = new TCkJobAttach();
		BeanUtils.copyProperties(jobAttach, _jobAttach, COBeansUtil.getNullPropertyNames(jobAttach));
		_jobAttach.setTCkJob(parentJob);

		Optional<String> opMstAttachTypeId = Optional.ofNullable(jobAttach.getTMstAttType().getMattId());
		if (!opMstAttachTypeId.isPresent())
			throw new ProcessingException("jobAttach.TMstAttType.mattId not found");
		if (StringUtils.isEmpty(opMstAttachTypeId.get()))
			throw new ProcessingException("jobAttach.TMstAttType.mattId null or empty");

		HashMap<String, Object> hmMstAttachTypes = clicMasterRecrodsSvc.getRecords("mstAttachType");
		if (!hmMstAttachTypes.containsKey(opMstAttachTypeId.get()))
			throw new ProcessingException("attachment master type not found: " + opMstAttachTypeId.get());

		_jobAttach.setTMstAttType((TMstAttType) hmMstAttachTypes.get((opMstAttachTypeId.get())));

		String fileLocation = saveAttachment(jobAttach.getAttName(), jobAttach.getAttData());
		if (StringUtils.isNotBlank(fileLocation))
			_jobAttach.setAttDoc(fileLocation);

		ckJobAttachDao.saveOrUpdate(_jobAttach);

		jobAttach.setAttDoc(fileLocation);
		return jobAttach;

	};

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.guudint.clickargo.job.service.IJobService#getAttachments(String,
	 *      Principal)
	 * 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public List<CkJobAttach> getAttachments(String jobId, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception {
		log.debug("getAttachments");

		if (StringUtils.isEmpty(jobId))
			throw new ParameterException("param jobId null or empty");
		if (null == principal)
			throw new ParameterException("param principal null");

		TCkJob parentJob = ckJobDao.find(jobId);
		if (null == parentJob)
			throw new ProcessingException("parent job not found: " + jobId);

		String hql = " FROM TCkJobAttach x where x.TCkJob.jobId = :jobId and attStatus = :attStatus";
		HashMap<String, Object> parameters = new HashMap<>();
		parameters.put("jobId", jobId);
		parameters.put("attStatus", Constant.ACTIVE_STATUS);
		List<TCkJobAttach> tckJobAttaches = ckJobAttachDao.getByQuery(hql, parameters);
		List<CkJobAttach> ckJobAttaches = tckJobAttaches.stream().map(x -> {
			Hibernate.initialize(x.getTMstAttType());
			Hibernate.initialize(x.getTCkJob());

			CkJobAttach att = new CkJobAttach(x);
			att.setTMstAttType(new MstAttType(x.getTMstAttType()));
			att.setTCkJob(new CkJob(x.getTCkJob()));
			return att;
		}).collect(Collectors.toList());

		return ckJobAttaches;

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.guudint.clickargo.job.service.IJobService#deleteAttachment(com.guudint.clickargo.job.dto.CkJobAttach,
	 *      com.vcc.camelone.cac.model.Principal)
	 * 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CkJobAttach deleteAttachment(CkJobAttach jobAttach, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception {
		log.debug("deleteAttachment");

		if (null == jobAttach)
			throw new ParameterException("param jobAttach null");
		if (null == principal)
			throw new ParameterException("principal null");

		Optional<String> opJobId = Optional.ofNullable(jobAttach.getAttId());
		if (!opJobId.isPresent())
			throw new ProcessingException("jobAttach.id not present");
		if (StringUtils.isEmpty(opJobId.get()))
			throw new ProcessingException("jobAttach.id empty or null");

		TCkJobAttach tckJobAttach = ckJobAttachDao.find(jobAttach.getAttId());
		if (null == tckJobAttach) {
			throw new EntityNotFoundException("record jobAttach null");
		}

		// Todo: Please delete the file attachment - DONE
		ckJobAttachDao.remove(tckJobAttach);
		try {
			StringBuilder tckJobAttachPathBuilder = new StringBuilder(tckJobAttach.getAttDoc());
			if (!tckJobAttach.getAttDoc().endsWith(File.pathSeparator)) {
				tckJobAttachPathBuilder.append(File.pathSeparator);
			}
			tckJobAttachPathBuilder.append(tckJobAttach.getAttName());

			Path tckJobAttachPath = Paths.get(tckJobAttachPathBuilder.toString());
			Files.deleteIfExists(tckJobAttachPath);
		} catch (InvalidPathException ipe) {
			log.error("Invalid job attachment location", ipe);
			throw new ProcessingException(ipe);
		}

		return jobAttach;
	}

	// Interface Methods (Queries)
	///////////////////////////////
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.guudint.clickargo.job.service.IJobService#createQuery(com.guudint.clickargo.job.dto.CkJobQuery,
	 *      com.vcc.camelone.cac.model.Principal)
	 * 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CkJobQuery createQuery(CkJobQuery jobQuery, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception {
		log.debug("createQuery");

		if (null == jobQuery)
			throw new ParameterException("param jobQuery null");
		if (null == principal)
			throw new ParameterException("principal null");

		Optional<String> opJobId = Optional.ofNullable(jobQuery.getTCkJob()).map(CkJob::getJobId);
		if (!opJobId.isPresent())
			throw new ProcessingException("jobId not present");
		if (StringUtils.isEmpty(opJobId.get()))
			throw new ProcessingException("jobId null or empty");

		TCkJob parentJob = ckJobDao.find(opJobId.get());
		if (null == parentJob)
			throw new ProcessingException("parent job not found: " + opJobId.get());

		TCkJobQuery _jobQuery = new TCkJobQuery();
		BeanUtils.copyProperties(jobQuery, _jobQuery, COBeansUtil.getNullPropertyNames(jobQuery));
		_jobQuery.setTCkJob(parentJob);

		ckJobQueryDao.saveOrUpdate(_jobQuery);

		return jobQuery;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.guudint.clickargo.job.service.IJobService#getQueries(java.lang.String,
	 *      com.vcc.camelone.cac.model.Principal)
	 * 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public List<CkJobQuery> getQueries(String jobId, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception {
		log.debug("getQueries");

		if (StringUtils.isBlank(jobId)) {
			throw new ParameterException("jobId null or empty");
		}
		if (null == principal)
			throw new ParameterException("principal null");

		TCkJob parentJob = ckJobDao.find(jobId);
		if (null == parentJob)
			throw new ProcessingException("parent job not found: " + jobId);

		String hql = " FROM TCkJobQuery x where x.TCkJob.jobId = :jobId and x.qryStatus = :qryStatus";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("jobId", jobId);
		parameters.put("qryStatus", Constant.ACTIVE_STATUS);
		List<TCkJobQuery> tckJobQueries = ckJobQueryDao.getByQuery(hql, parameters);
		List<CkJobQuery> ckJobQueries = tckJobQueries.stream().map(CkJobQuery::new).collect(Collectors.toList());

		return Collections.unmodifiableList(ckJobQueries);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.guudint.clickargo.job.service.IJobService#updateQuery(com.guudint.clickargo.job.dto.CkJobQuery,
	 *      com.vcc.camelone.cac.model.Principal)
	 * 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CkJobQuery updateQuery(CkJobQuery jobQuery, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception {
		log.debug("updateQuery");

		// Todo : CK - DONE
		if (null == jobQuery)
			throw new ParameterException("param jobQuery null");
		if (null == principal)
			throw new ParameterException("principal null");

		Optional<String> jobIdOptional = Optional.ofNullable(jobQuery.getTCkJob()).map(CkJob::getJobId);
		if (!jobIdOptional.isPresent())
			throw new ProcessingException("jobId not present");
		if (StringUtils.isEmpty(jobIdOptional.get()))
			throw new ProcessingException("jobId null or empty");

		String jobId = jobIdOptional.get();
		TCkJob parentJob = ckJobDao.find(jobId);
		if (null == parentJob)
			throw new ProcessingException("parent job not found: " + jobId);

		TCkJobQuery tckJobQuery = ckJobQueryDao.find(jobQuery.getQryId());
		if (null == tckJobQuery) {
			throw new EntityNotFoundException("record jobQuery null");
		}
		BeanUtils.copyProperties(jobQuery, tckJobQuery, COBeansUtil.getNullPropertyNames(jobQuery));
		ckJobQueryDao.update(tckJobQuery);

		return jobQuery;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.guudint.clickargo.job.service.IJobService#deleteQuery(com.guudint.clickargo.job.dto.CkJobQuery,
	 *      com.vcc.camelone.cac.model.Principal)
	 * 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CkJobQuery deleteQuery(CkJobQuery jobQuery, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception {
		log.debug("deleteQuery");

		if (null == jobQuery)
			throw new ParameterException("param jobQuery null");
		if (null == principal)
			throw new ParameterException("principal null");

		TCkJobQuery tckJobQuery = ckJobQueryDao.find(jobQuery.getQryId());
		if (null == tckJobQuery) {
			throw new EntityNotFoundException("record jobQuery null");
		}

		ckJobQueryDao.remove(tckJobQuery);
		return jobQuery;
	}

	// Helper Methods
	/////////////////
	protected String getOperator(boolean whereprinted) {
		return whereprinted ? " AND " : " WHERE ";
	}

	protected CkJob checkJobState(String jobId, JobStates jobState) throws Exception {
		CkJob job = ckJobService.findById(jobId);
		if (null != job) {
			log.info("--------- checkjobstate ------" + job.getTCkMstJobState().getJbstId());
			if (job.getTCkMstJobState().getJbstId().equalsIgnoreCase(jobState.name()))
				throw new ProcessingException("Truck Job is already  " + jobState.getDesc());
		}

		return job;
	}

	private CkJob createParentJob(Principal principal, CkJob parentJob)
			throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException {
		parentJob.setJobId(CkUtil.generateId(ICkConstant.PREFIX_PARENT_JOB));
		parentJob.setJobStatus(RecordStatus.ACTIVE.getCode());

		CkMstJobState jobState = new CkMstJobState();
		jobState.setJbstId(JobStates.NEW.name());
		parentJob.setTCkMstJobState(jobState);

		CkMstJobType jobType = new CkMstJobType();
		jobType.setJbtId(JobTypes.DOCI.name());
		parentJob.setTCkMstJobType(jobType);

		CkMstShipmentType shipmentType = new CkMstShipmentType();
		shipmentType.setShtId(ShipmentTypes.IMPORT.name());
		parentJob.setTCkMstShipmentType(shipmentType);

		CkRecordDate jobRecordDate = new CkRecordDate();
		jobRecordDate.setRcdId(CkUtil.generateId());
		jobRecordDate.setRcdDtDrft(new Date());
		parentJob.setTCkRecordDate(ckRecordService.add(jobRecordDate, principal));

		parentJob.setJobSubType(parentJob.getJobSubType());
		parentJob.setJobLoading(parentJob.getJobLoading());

		return ckJobService.add(parentJob, principal);
	}

	/**
	 * Allows formatting to the orderByClause string if the variable used from dto
	 * is different from the entity class
	 * 
	 * e.g. (TPediVcform) TPediAppVcGeneral TPediAppVcGeneral !=
	 * (PediVcForm)PediAppVcGeneral pediAppVcGeneral;
	 * 
	 * If not specified it will return the original orderByClause
	 * 
	 * This is applicable for all the columns visible in the datatable listing.
	 */
	protected abstract String formatOrderBy(String attribute) throws Exception;

	protected EntityOrderBy formatOrderByObj(EntityOrderBy orderBy) throws Exception {

		if (orderBy == null)
			return null;

		if (StringUtils.isEmpty(orderBy.getAttribute()))
			return null;

		String newAttr = formatOrderBy(orderBy.getAttribute());
		if (StringUtils.isEmpty(newAttr))
			return orderBy;

		orderBy.setAttribute(newAttr);
		return orderBy;

	}

	protected void checkSuspendedAccount(Principal principal) throws Exception {
		if (principal != null) {
			Optional<TCoreAccn> coFfAccn = Optional.ofNullable(coreAccnDao.find(principal.getUserAccnId()));
			if (coFfAccn != null && coFfAccn.get().getAccnStatus() == RecordStatus.SUSPENDED.getCode()) {
				Map<String, Object> validateErrParam = new HashMap<>();
				validateErrParam.put("suspended-accn", "Your account is suspended.");
				throw new ValidationException(mapper.writeValueAsString(validateErrParam));
			}

		}
	}

}
