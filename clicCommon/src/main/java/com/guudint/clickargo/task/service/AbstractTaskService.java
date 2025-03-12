/**
 * 
 */
package com.guudint.clickargo.task.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.guudint.clickargo.common.AbstractClickCargoEntityService;
import com.guudint.clickargo.common.model.ValidationError;
import com.guudint.clickargo.master.service.ICkMstRecords;
import com.guudint.clickargo.task.dto.CkTask;
import com.guudint.clickargo.task.dto.CkTaskAttach;
import com.guudint.clickargo.task.event.AbstractTaskEvent;
import com.guudint.clickargo.task.model.TCkTask;
import com.guudint.clickargo.task.model.TCkTaskAttach;
import com.guudint.clickargo.task.service.ITaskVerify.TaskVerifyType;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.ccm.util.Constant;
import com.vcc.camelone.common.controller.entity.EntityFilterRequest;
import com.vcc.camelone.common.controller.entity.EntityFilterResponse;
import com.vcc.camelone.common.controller.entity.EntityOrderBy;
import com.vcc.camelone.common.controller.entity.EntityWhere;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.common.dto.AbstractDTO;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.exception.ValidationException;
import com.vcc.camelone.master.model.TMstAttType;
import com.vcc.camelone.util.bean.COBeansUtil;

/**
 * @author user
 *
 */
public abstract class AbstractTaskService<D extends AbstractDTO<D, E>, E, K>
		extends AbstractClickCargoEntityService<E, K, D> implements ITaskService<D, E, K>, ITaskEvent {

	// Attributes
	/////////////
	@Autowired
	protected ITaskValidate<D> bzValidator;

	@Autowired
	protected ApplicationEventPublisher eventPublisher;

	@Autowired
	protected GenericDao<TCkTaskAttach, String> ckTaskAttachDao;

	@Autowired
	protected GenericDao<TCkTask, String> ckTaskDao;

	@Autowired
	protected ICkMstRecords clicMasterRecrodsSvc;

	public AbstractTaskService(String daoName, String moduleName, String entityName, String tableName) {
		super(daoName, moduleName, entityName, tableName);
	}

	// Validate Callback
	///////////////////
	/**
	 * @param taskEvent
	 * @return
	 */
	protected abstract Class<?>[] _validateGroupClass(TaskEvent taskEvent);

	// Audits and Error Callback
	////////////////////////////
	/**
	 * Audit the event into T_CORE_AUDIT_LOG
	 * 
	 * @param taskEvent
	 * @param dto
	 * @param principal
	 */
	protected abstract void _auditEvent(TaskEvent taskEvent, D dto, Principal principal);

	/**
	 * Audit the event into T_CORE_EXCEPTIONS
	 * 
	 * @param taskEvent
	 * @param dto
	 * @param principal
	 */
	protected abstract void _auditError(TaskEvent taskEvent, D dto, Principal principal);

	// Task SpringEvent Callback
	///////////////////////////
	/**
	 * Get the event for spring event callback
	 * 
	 * @param taskEvent
	 * @param dto
	 * @param principal
	 * @return
	 */
	protected abstract AbstractTaskEvent<D> _getTaskEvent(TaskEvent taskEvent, D dto, Principal principal);

	// Task Event Callback
	///////////////////////
	/**
	 * Create a new instance of the task DTO
	 * 
	 * @return
	 */
	protected abstract D _newTask(Principal p) throws ParameterException, EntityNotFoundException, ProcessingException;

	/**
	 * Change state of task to Submit
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
	protected abstract D _submitTask(D dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception;

	/**
	 * Update the task to verify
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
	protected abstract D _verifyTask(D dto, Principal principal)
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
	protected abstract D _completeTask(D dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception;

	// Interface Methods
	////////////////////
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.guudint.clickargo.task.service.ITaskService#newTask(com.vcc.camelone.cac.model.Principal)
	 * 
	 */
	@Override
	public D newTask(Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		getLogger().debug("newTask");

		if (null == principal)
			throw new ParameterException("principal null");
		return newObj(principal);
	}

	@Override
	public D newObj(Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException {
		return _newTask(principal);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public D createTask(D dto, Principal principal)
			throws ParameterException, ValidationException, ProcessingException, Exception {
		return (D) addObj(dto, principal);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public Object addObj(Object dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException {
		getLogger().debug("addObj");

		if (null == dto)
			throw new ParameterException("param dto null");
		if (null == principal)
			throw new ParameterException("principal null");

		try {
			// Data constraints validation
			Class<?>[] groupClasses = _validateGroupClass(TaskEvent.CREATE);
			validate((D) dto, groupClasses);

			// Business validation
			List<ValidationError> validationErrors = bzValidator.validateCreate((D) dto, principal);
			if (null != validationErrors && !validationErrors.isEmpty())
				throw new ValidationException(validationErrorMap(validationErrors));
		} catch (ValidationException e) {
			throw new ProcessingException(e);
		}

		D _dto = null;
		try {
			_dto = _createTask((D)dto, principal);
			_auditEvent(TaskEvent.CREATE, _dto, principal);
			AbstractTaskEvent<D> event = _getTaskEvent(TaskEvent.CREATE, _dto, principal);
			if (null != event)
				eventPublisher.publishEvent(event);

			return _dto;
		} catch (Exception ex) {
			getLogger().error("addObj", ex);
			_auditError(TaskEvent.CREATE, _dto, principal);
			throw ex;
		}
	}

	@SuppressWarnings("unchecked")
	protected D _createTask(D dto, Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException {
		return (D) super.addObj(dto, principal);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.guudint.clickargo.task.service.ITaskService#getTask(java.lang.String,
	 *      com.vcc.camelone.cac.model.Principal)
	 * 
	 */
	@Override
	public D getTask(String id, Principal principal)
			throws ParameterException, ValidationException, ProcessingException, Exception {
		return findById(id);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.guudint.clickargo.task.service.ITaskService#updateTask(com.vcc.camelone.common.dto.AbstractDTO,
	 *      com.vcc.camelone.cac.model.Principal)
	 * 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public D updateTask(D dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception {
		getLogger().debug("updateTask");

		if (null == dto)
			throw new ParameterException("param dto null");
		if (null == principal)
			throw new ParameterException("principal null");

		try {
			// Data constraints validation
			Class<?>[] groupClasses = _validateGroupClass(TaskEvent.UPDATE);
			validate((D) dto, groupClasses);

			// Business validation
			List<ValidationError> validationErrors = bzValidator.validateUpdate((D) dto, principal);
			if (null != validationErrors && !validationErrors.isEmpty())
				throw new ValidationException(validationErrorMap(validationErrors));

		} catch (ValidationException e) {
			throw new ProcessingException(e);
		}

		D _dto = null;
		try {
			_dto = _updateTask(dto, principal);
			_auditEvent(TaskEvent.UPDATE, _dto, principal);
			AbstractTaskEvent<D> event = _getTaskEvent(TaskEvent.UPDATE, _dto, principal);
			if (null != event)
				eventPublisher.publishEvent(event);

			return _dto;
		} catch (Exception ex) {
			getLogger().error("updateTask", ex);
			_auditError(TaskEvent.UPDATE, _dto, principal);
			throw new ProcessingException(ex);
		}
	}

	@SuppressWarnings("unchecked")
	protected D _updateTask(D dto, Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException {
		return (D) super.updateObj(dto, principal);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Object updateObj(Object dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		try {
			return updateTask((D) dto, principal);
		} catch (Exception e) {
			throw new ProcessingException(e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.guudint.clickargo.task.service.ITaskService#submitTask(com.vcc.camelone.common.dto.AbstractDTO,
	 *      com.vcc.camelone.cac.model.Principal)
	 * 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public D submitTask(D dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception {
		// TODO Auto-generated method stub
		getLogger().debug("submitTask");

		if (null == dto)
			throw new ParameterException("param dto null");
		if (null == principal)
			throw new ParameterException("principal null");

		// Data constraints validation
		Class<?>[] groupClasses = _validateGroupClass(TaskEvent.SUBMIT);
		validate(dto, groupClasses);

		// Business validation
		List<ValidationError> validationErrors = bzValidator.validateSubmit(dto, principal);
		if (null != validationErrors && !validationErrors.isEmpty())
			throw new ValidationException(validationErrorMap(validationErrors));

		D _dto = null;
		try {
			_dto = _submitTask(dto, principal);
			_auditEvent(TaskEvent.SUBMIT, _dto, principal);

			AbstractTaskEvent<D> event = _getTaskEvent(TaskEvent.SUBMIT, _dto, principal);
			if (null != event)
				eventPublisher.publishEvent(event);

			return _dto;
		} catch (Exception ex) {
			getLogger().error("submitTask", ex);
			_auditError(TaskEvent.SUBMIT, _dto, principal);
			throw ex;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public D deleteTask(D dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception {
		return deleteObj(dto, principal);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public D deleteObj(Object dto, Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException {
		getLogger().debug("deleteObj");

		if (null == dto)
			throw new ParameterException("param dto null");
		if (null == principal)
			throw new ParameterException("principal null");

		try {
			// Data constraints validation
			Class<?>[] groupClasses = _validateGroupClass(TaskEvent.DELETE);
			validate((D)dto, groupClasses);

			// Business validation
			List<ValidationError> validationErrors = bzValidator.validateDelete((D)dto, principal);
			if (null != validationErrors && !validationErrors.isEmpty())
				throw new ValidationException(validationErrorMap(validationErrors));

		} catch (ValidationException e) {
			throw new ProcessingException(e);
		}

		D _dto = null;
		try {
			_dto = _deleteTask((D)dto, principal);
			_auditEvent(TaskEvent.DELETE, _dto, principal);

			AbstractTaskEvent<D> event = _getTaskEvent(TaskEvent.DELETE, _dto, principal);
			if (null != event)
				eventPublisher.publishEvent(event);

			return _dto;
		} catch (Exception ex) {
			getLogger().error("verifyTask", ex);
			_auditError(TaskEvent.VERIFY, _dto, principal);
			throw ex;
		}
	}

	protected D _deleteTask(D dto, Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException {
		return super.deleteObj(dto, principal);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.guudint.clickargo.task.service.ITaskService#verifyTask(com.vcc.camelone.common.dto.AbstractDTO,
	 *      com.guudint.clickargo.task.service.ITaskVerify.TaskVerifyType,
	 *      com.vcc.camelone.cac.model.Principal)
	 * 
	 */
	@Override
	public D verifyTask(D dto, TaskVerifyType verifyType, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception {
		// TODO Auto-generated method stub
		getLogger().debug("verifyTask");

		if (null == dto)
			throw new ParameterException("param dto null");
		if (null == principal)
			throw new ParameterException("principal null");

		// Data constraints validation
		Class<?>[] groupClasses = _validateGroupClass(TaskEvent.VERIFY);
		validate(dto, groupClasses);

		// Business validation
		List<ValidationError> validationErrors = bzValidator.validateVerify(dto, principal);
		if (null != validationErrors && !validationErrors.isEmpty())
			throw new ValidationException(validationErrorMap(validationErrors));

		D _dto = null;
		try {
			_dto = _verifyTask(dto, principal);
			_auditEvent(TaskEvent.VERIFY, _dto, principal);

			AbstractTaskEvent<D> event = _getTaskEvent(TaskEvent.VERIFY, _dto, principal);
			if (null != event)
				eventPublisher.publishEvent(event);

			return _dto;
		} catch (Exception ex) {
			getLogger().error("verifyTask", ex);
			_auditError(TaskEvent.VERIFY, _dto, principal);
			throw ex;
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.guudint.clickargo.task.service.ITaskService#completTask(com.vcc.camelone.common.dto.AbstractDTO,
	 *      com.vcc.camelone.cac.model.Principal)
	 * 
	 */
	@Override
	public D completTask(D dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception {
		// TODO Auto-generated method stub
		getLogger().debug("completTask");

		if (null == dto)
			throw new ParameterException("param dto null");
		if (null == principal)
			throw new ParameterException("principal null");

		// Data constraints validation
		Class<?>[] groupClasses = _validateGroupClass(TaskEvent.COMPLETE);
		validate(dto, groupClasses);

		// Business validation
		List<ValidationError> validationErrors = bzValidator.validateComplete(dto, principal);
		if (null != validationErrors && !validationErrors.isEmpty())
			throw new ValidationException(validationErrorMap(validationErrors));

		D _dto = null;
		try {
			_dto = _completeTask(dto, principal);
			_auditEvent(TaskEvent.COMPLETE, _dto, principal);

			AbstractTaskEvent<D> event = _getTaskEvent(TaskEvent.COMPLETE, _dto, principal);
			if (null != event)
				eventPublisher.publishEvent(event);

			return _dto;
		} catch (Exception ex) {
			getLogger().error("completTask", ex);
			_auditError(TaskEvent.COMPLETE, _dto, principal);
			throw ex;
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.guudint.clickargo.task.service.ITaskService#filterTasks(java.util.Map,
	 *      com.vcc.camelone.cac.model.Principal)
	 * 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class }, readOnly = true)
	public EntityFilterResponse filterTasks(Map<String, String> params, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception {
		// TODO Auto-generated method stub
		getLogger().debug("filterTasks");

		if (null == params)
			throw new ParameterException("param params null");
		if (null == principal)
			throw new ParameterException("principal null");

		EntityFilterRequest filterRequest = new EntityFilterRequest();
		// start and length parameter extraction
		filterRequest.setDisplayStart(
				params.containsKey("iDisplayStart") ? Integer.valueOf(params.get("iDisplayStart")).intValue() : -1);
		filterRequest.setDisplayLength(
				params.containsKey("iDisplayLength") ? Integer.valueOf(params.get("iDisplayLength")).intValue() : -1);
		// where parameters extraction
		ArrayList<EntityWhere> whereList = new ArrayList<>();
		List<String> searches = params.keySet().stream().filter(x -> x.contains("sSearch_"))
				.collect(Collectors.toList());
		for (int nIndex = 1; nIndex <= searches.size(); nIndex++) {
			String searchParam = params.get("sSearch_" + nIndex);
			String valueParam = params.get("mDataProp_" + nIndex);
			whereList.add(new EntityWhere(valueParam, searchParam));
		}

		filterRequest.setWhereList(whereList);
		// order by parameters extraction
		Optional<String> opSortAttribute = Optional.ofNullable(params.get("mDataProp_0"));
		Optional<String> opSortOrder = Optional.ofNullable(params.get("sSortDir_0"));
		if (opSortAttribute.isPresent() && opSortOrder.isPresent()) {
			EntityOrderBy orderBy = new EntityOrderBy();
			orderBy.setAttribute(opSortAttribute.get());
			orderBy.setOrdered(opSortOrder.get().equalsIgnoreCase("desc") ? EntityOrderBy.ORDERED.DESC
					: EntityOrderBy.ORDERED.ASC);
			filterRequest.setOrderBy(orderBy);
		}

		if (!filterRequest.isValid()) {
			throw new ProcessingException("Invalid request: " + filterRequest.toJson());
		}

		List<D> en = filterBy(filterRequest);
		@SuppressWarnings("unchecked")
		List<Object> entities = List.class.cast(en);
		EntityFilterResponse filterResponse = new EntityFilterResponse();
		filterResponse.setiTotalRecords(entities.size());
		filterResponse.setiTotalDisplayRecords(filterRequest.getTotalRecords());
		filterResponse.setAaData((ArrayList<Object>) entities);
		return filterResponse;
	}

	// Interface Methods (Attachments)
	//////////////////////////////////
	/**
	 * (non-Javadoc)
	 *
	 * @see com.guudint.clickargo.task.service.ITaskService#createAttachment(com.guudint.clickargo.task.dto.CkTaskAttach,
	 *      com.vcc.camelone.cac.model.Principal)
	 *
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CkTaskAttach createAttachment(CkTaskAttach taskAttach, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception {
		getLogger().debug("createAttachment");

		if (null == taskAttach)
			throw new ParameterException("param taskAttach null");
		if (null == principal)
			throw new ParameterException("principal null");

		Optional<String> opTaskId = Optional.ofNullable(taskAttach.getTCkTask()).map(CkTask::getTskId);
		if (!opTaskId.isPresent())
			throw new ProcessingException("taskId null or empty");

		TCkTask parentTask = ckTaskDao.find(opTaskId.get());
		if (null == parentTask)
			throw new ProcessingException("parent task not found: " + opTaskId.get());

		TCkTaskAttach _taskAttach = new TCkTaskAttach();
		BeanUtils.copyProperties(taskAttach, _taskAttach, COBeansUtil.getNullPropertyNames(taskAttach));
		_taskAttach.setTCkTask(parentTask);

		Optional<String> opMstAttachTypeId = Optional.ofNullable(taskAttach.getTMstAttType().getMattId());
		if (!opMstAttachTypeId.isPresent())
			throw new ProcessingException("taskAttach.TMstAttType.mattId not found");
		if (StringUtils.isEmpty(opMstAttachTypeId.get()))
			throw new ProcessingException("taskAttach.TMstAttType.mattId null or empty");
		Map<String, Object> hmMstAttachTypes = clicMasterRecrodsSvc.getRecords("mstAttachType");
		if (!hmMstAttachTypes.containsKey(opMstAttachTypeId.get()))
			throw new ProcessingException("attachment master type not found: " + opMstAttachTypeId.get());
		_taskAttach.setTMstAttType((TMstAttType) hmMstAttachTypes.get((opMstAttachTypeId.get())));

		ckTaskAttachDao.saveOrUpdate(_taskAttach);

		try {
			// TODO - how to get the file input stream
			InputStream attachmentInputStream = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest().getInputStream();

			Path attachmentLocationPath = Paths.get(taskAttach.getAttLoc());
			if (Files.notExists(attachmentLocationPath)) {
				Files.createDirectories(attachmentLocationPath);
			}

			StringBuilder taskAttachPathBuilder = new StringBuilder(taskAttach.getAttLoc());
			if (!taskAttach.getAttLoc().endsWith(File.pathSeparator)) {
				taskAttachPathBuilder.append(File.pathSeparator);
			}
			taskAttachPathBuilder.append(taskAttach.getAttName());

			Path taskAttachPath = Paths.get(taskAttachPathBuilder.toString());
			Files.copy(attachmentInputStream, taskAttachPath);
		} catch (IOException ioe) {
			getLogger().error("Encountered IO error while saving file to disk", ioe);
			throw new ProcessingException(ioe);
		}

		return taskAttach;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see com.guudint.clickargo.task.service.ITaskService#getAttachments(java.lang.String,
	 *      com.vcc.camelone.cac.model.Principal)
	 *
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class }, readOnly = true)
	public List<CkTaskAttach> getAttachments(String taskId, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception {
		getLogger().debug("getAttachments");

		if (StringUtils.isEmpty(taskId))
			throw new ParameterException("param taskId null or empty");
		if (null == principal)
			throw new ParameterException("param principal null");

		TCkTask parentTask = ckTaskDao.find(taskId);
		if (null == parentTask)
			throw new ProcessingException("parent task not found: " + taskId);

		String hql = " FROM TCkTaskAttach x where x.TCkTask.tskId = :tskId and attStatus = :attStatus";
		HashMap<String, Object> parameters = new HashMap<>();
		parameters.put("tskId", taskId);
		parameters.put("attStatus", Constant.ACTIVE_STATUS);

		List<TCkTaskAttach> tckTaskAttaches = ckTaskAttachDao.getByQuery(hql, parameters);
		List<CkTaskAttach> ckTaskAttaches = tckTaskAttaches.stream().map(CkTaskAttach::new)
				.collect(Collectors.toList());

		return Collections.unmodifiableList(ckTaskAttaches);
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see com.guudint.clickargo.task.service.ITaskService#deleteAttachment(com.guudint.clickargo.task.dto.CkTaskAttach,
	 *      com.vcc.camelone.cac.model.Principal)
	 *
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CkTaskAttach deleteAttachment(CkTaskAttach taskAttach, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception {
		getLogger().debug("deleteAttachment");

		if (null == taskAttach)
			throw new ParameterException("param taskAttach null");
		if (null == principal)
			throw new ParameterException("principal null");

		Optional<String> opTaskId = Optional.ofNullable(taskAttach.getAttId());
		if (!opTaskId.isPresent())
			throw new ProcessingException("taskAttach.id not present");
		if (StringUtils.isEmpty(opTaskId.get()))
			throw new ProcessingException("taskAttach.id empty or null");

		TCkTaskAttach tckTaskAttach = ckTaskAttachDao.find(taskAttach.getAttId());
		if (null == tckTaskAttach) {
			throw new EntityNotFoundException("record taskAttach null");
		}

		ckTaskAttachDao.remove(tckTaskAttach);
		try {
			StringBuilder tckTaskAttachPathBuilder = new StringBuilder(tckTaskAttach.getAttLoc());
			if (!tckTaskAttach.getAttLoc().endsWith(File.pathSeparator)) {
				tckTaskAttachPathBuilder.append(File.pathSeparator);
			}
			tckTaskAttachPathBuilder.append(tckTaskAttach.getAttName());

			Path tckTaskAttachPath = Paths.get(tckTaskAttachPathBuilder.toString());
			Files.deleteIfExists(tckTaskAttachPath);
		} catch (InvalidPathException ipe) {
			getLogger().error("Invalid task attachment location", ipe);
			throw new ProcessingException(ipe);
		}

		return taskAttach;
	}

}
