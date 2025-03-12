package com.guudint.clickargo.task.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.common.RecordStatus;
import com.guudint.clickargo.common.dto.CkRecordDate;
import com.guudint.clickargo.common.model.TCkRecordDate;
import com.guudint.clickargo.job.dto.CkJob;
import com.guudint.clickargo.job.model.TCkJob;
import com.guudint.clickargo.master.dto.CkMstShipmentType;
import com.guudint.clickargo.master.dto.CkMstTaskState;
import com.guudint.clickargo.master.dto.CkMstTaskType;
import com.guudint.clickargo.master.model.TCkMstShipmentType;
import com.guudint.clickargo.master.model.TCkMstTaskState;
import com.guudint.clickargo.master.model.TCkMstTaskType;
import com.guudint.clickargo.task.dto.CkTask;
import com.guudint.clickargo.task.dto.CkTaskAttach;
import com.guudint.clickargo.task.model.TCkTask;
import com.guudint.clickargo.task.model.TCkTaskAttach;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.common.controller.entity.EntityFilterRequest;
import com.vcc.camelone.common.controller.entity.EntityWhere;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.service.ServiceStatus;
import com.vcc.camelone.common.service.entity.AbstractEntityService;
import com.vcc.camelone.locale.dto.CoreMstLocale;
import com.vcc.camelone.master.dto.MstAttType;
import com.vcc.camelone.master.model.TMstAttType;
import com.vcc.camelone.master.service.impl.AccountTypeService;

public class CkTaskAttachService extends AbstractEntityService<TCkTaskAttach, String, CkTaskAttach> {

	// Static Attributes
	////////////////////
	private static Logger log = Logger.getLogger(AccountTypeService.class);
	private static String auditTag = "TASK ATTACH";
	private static String tableName = "T_CK_TASK_ATTACH";

	// Constructor
	//////////////
	public CkTaskAttachService() {
		super("ckTaskAttachDao", auditTag, TCkTaskAttach.class.getName(), tableName);
		// TODO Auto-generated constructor stub
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CkTaskAttach findById(String id) throws ParameterException, EntityNotFoundException, ProcessingException {
		log.debug("findById");

		try {
			if (StringUtils.isEmpty(id))
				throw new ParameterException("param id null or empty");

			TCkTaskAttach entity = dao.find(id);
			if (null == entity)
				throw new EntityNotFoundException("id: " + id);
			this.initEnity(entity);

			return this.dtoFromEntity(entity);
		} catch (ParameterException | EntityNotFoundException ex) {
			log.error("findById", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("findById", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CkTaskAttach deleteById(String id, Principal principal)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		log.debug("deleteById");

		Date now = Calendar.getInstance().getTime();
		try {
			if (StringUtils.isEmpty(id))
				throw new ParameterException("param id null or empty");
			if (null == principal)
				throw new ParameterException("param prinicipal null");

			TCkTaskAttach entity = dao.find(id);
			if (null == entity)
				throw new EntityNotFoundException("id: " + id);

			this.updateEntityStatus(entity, RecordStatus.INACTIVE.getCode());
			this.updateEntity(ACTION.MODIFY, entity, principal, now);

			CkTaskAttach dto = dtoFromEntity(entity);
			this.delete(dto, principal);
			return dto;
		} catch (ParameterException | EntityNotFoundException ex) {
			log.error("deleteById", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("deleteById", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public List<CkTaskAttach> filterBy(EntityFilterRequest filterRequest)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		log.debug("filterBy");

		try {
			if (null == filterRequest)
				throw new ParameterException("param filterRequest null");

			CkTaskAttach dto = this.whereDto(filterRequest);
			if (null == dto)
				throw new ProcessingException("whereDto null");

			filterRequest.setTotalRecords(super.countByAnd(dto));

			String selectClause = "from TCkTaskAttach o ";
			String orderByClause = filterRequest.getOrderBy().toString();
			List<TCkTaskAttach> entities = super.findEntitiesByAnd(dto, selectClause, orderByClause,
					filterRequest.getDisplayLength(), filterRequest.getDisplayStart());
			List<CkTaskAttach> dtos = entities.stream().map(x -> {
				try {
					return dtoFromEntity(x);
				} catch (ParameterException | ProcessingException e) {
					log.error("filterBy", e);
				}
				return null;
			}).collect(Collectors.toList());

			return dtos;
		} catch (ParameterException | ProcessingException ex) {
			log.error("filterBy", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("filterBy", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	protected TCkTaskAttach initEnity(TCkTaskAttach entity) throws ParameterException, ProcessingException {
		if (null != entity) {
			Hibernate.initialize(entity.getTCkTask());
			Hibernate.initialize(entity.getTMstAttType());
		}

		return entity;
	}

	@Override
	protected TCkTaskAttach entityFromDTO(CkTaskAttach dto) throws ParameterException, ProcessingException {
		try {
			if (null == dto)
				throw new ParameterException("dto dto null");

			TCkTaskAttach entity = new TCkTaskAttach();
			entity = dto.toEntity(entity);

			Optional<CkTask> opTask = Optional.ofNullable(dto.getTCkTask());
			entity.setTCkTask(opTask.isPresent() ? opTask.get().toEntity(new TCkTask()) : null);
			if (opTask.isPresent()) {
				Optional<CkJob> opCkJob = Optional.ofNullable(dto.getTCkTask().getTCkJob());
				Optional<CkMstShipmentType> opMstShpmentType = Optional
						.ofNullable(dto.getTCkTask().getTCkMstShipmentType());
				Optional<CkMstTaskState> opMstTaskState = Optional.ofNullable(dto.getTCkTask().getTCkMstTaskState());
				Optional<CkMstTaskType> opMstTaskType = Optional.ofNullable(dto.getTCkTask().getTCkMstTaskType());
				Optional<CkRecordDate> opRecordDate = Optional.ofNullable(dto.getTCkTask().getTCkRecordDate());

				entity.getTCkTask().setTCkJob(opCkJob.isPresent() ? opCkJob.get().toEntity(new TCkJob()) : null);
				entity.getTCkTask().setTCkMstShipmentType(
						opMstShpmentType.isPresent() ? opMstShpmentType.get().toEntity(new TCkMstShipmentType())
								: null);
				entity.getTCkTask().setTCkMstTaskState(
						opMstTaskState.isPresent() ? opMstTaskState.get().toEntity(new TCkMstTaskState()) : null);
				entity.getTCkTask().setTCkMstTaskType(
						opMstTaskType.isPresent() ? opMstTaskType.get().toEntity(new TCkMstTaskType()) : null);
				entity.getTCkTask().setTCkRecordDate(
						opRecordDate.isPresent() ? opRecordDate.get().toEntity(new TCkRecordDate()) : null);
			}

			Optional<MstAttType> opMstAttType = Optional.ofNullable(dto.getTMstAttType());
			entity.setTMstAttType(opMstAttType.isPresent() ? opMstAttType.get().toEntity(new TMstAttType()) : null);

			return entity;
		} catch (ParameterException ex) {
			log.error("entityFromDTO", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("entityFromDTO", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected CkTaskAttach dtoFromEntity(TCkTaskAttach entity) throws ParameterException, ProcessingException {
		log.debug("dtoFromEntity");

		try {
			if (null == entity)
				throw new ParameterException("param entity null");

			CkTaskAttach dto = new CkTaskAttach(entity);

			Optional<TCkTask> opCkTask = Optional.ofNullable(entity.getTCkTask());

			if (opCkTask.isPresent()) {
				dto.setTCkTask(new CkTask(opCkTask.get()));

				Optional<TCkJob> opCkJob = Optional.ofNullable(entity.getTCkTask().getTCkJob());
				Optional<TCkMstShipmentType> opMstShpmentType = Optional
						.ofNullable(entity.getTCkTask().getTCkMstShipmentType());
				Optional<TCkMstTaskState> opMstTaskState = Optional
						.ofNullable(entity.getTCkTask().getTCkMstTaskState());
				Optional<TCkMstTaskType> opMstTaskType = Optional.ofNullable(entity.getTCkTask().getTCkMstTaskType());
				Optional<TCkRecordDate> opRecordDate = Optional.ofNullable(entity.getTCkTask().getTCkRecordDate());

				opCkJob.ifPresent(c -> dto.getTCkTask().setTCkJob(new CkJob(c)));
				opMstShpmentType.ifPresent(c -> dto.getTCkTask().setTCkMstShipmentType(new CkMstShipmentType(c)));
				opMstTaskState.ifPresent(c -> dto.getTCkTask().setTCkMstTaskState(new CkMstTaskState(c)));
				opMstTaskType.ifPresent(c -> dto.getTCkTask().setTCkMstTaskType(new CkMstTaskType(c)));
				opRecordDate.ifPresent(c -> dto.getTCkTask().setTCkRecordDate(new CkRecordDate(c)));
			}

			Optional<TMstAttType> opMstAttType = Optional.ofNullable(entity.getTMstAttType());
			dto.setTMstAttType(new MstAttType(opMstAttType.get()));

			return dto;
		} catch (ParameterException ex) {
			log.error("entityFromDTO", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("dtoFromEntity", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected String entityKeyFromDTO(CkTaskAttach dto) throws ParameterException, ProcessingException {
		log.debug("entityKeyFromDTO");

		try {
			if (null == dto)
				throw new ParameterException("dto param null");

			return dto.getAttId();
		} catch (ParameterException ex) {
			log.error("entityKeyFromDTO", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("entityKeyFromDTO", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected TCkTaskAttach updateEntity(ACTION attriubte, TCkTaskAttach entity, Principal principal, Date date)
			throws ParameterException, ProcessingException {
		log.debug("updateEntity");

		try {
			if (null == entity)
				throw new ParameterException("param entity null");
			if (null == principal)
				throw new ParameterException("param principal null");
			if (null == date)
				throw new ParameterException("param date null");

			Optional<String> opUserId = Optional.ofNullable(principal.getUserId());
			switch (attriubte) {
			case CREATE:
				entity.setAttUidCreate(opUserId.isPresent() ? opUserId.get() : "SYS");
				entity.setAttDtCreate(date);
				entity.setAttUidLupd(opUserId.isPresent() ? opUserId.get() : "SYS");
				entity.setAttDtLupd(date);
				break;

			case MODIFY:
				entity.setAttUidLupd(opUserId.isPresent() ? opUserId.get() : "SYS");
				entity.setAttDtLupd(date);
				break;

			default:
				break;
			}

			return entity;
		} catch (ParameterException ex) {
			log.error("updateEntity", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("updateEntity", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected TCkTaskAttach updateEntityStatus(TCkTaskAttach entity, char status)
			throws ParameterException, ProcessingException {
		log.debug("updateEntityStatus");

		try {
			if (null == entity)
				throw new ParameterException("entity param null");

			entity.setAttStatus(status);
			return entity;
		} catch (ParameterException ex) {
			log.error("updateEntity", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("updateEntityStatus", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected CkTaskAttach preSaveUpdateDTO(TCkTaskAttach storedEntity, CkTaskAttach dto)
			throws ParameterException, ProcessingException {
		log.debug("preSaveUpdateDTO");

		try {
			if (null == storedEntity)
				throw new ParameterException("param storedEntity null");
			if (null == dto)
				throw new ParameterException("param dto null");

			dto.setAttUidCreate(storedEntity.getAttUidCreate());
			dto.setAttDtCreate(storedEntity.getAttDtCreate());

			return dto;
		} catch (ParameterException ex) {
			log.error("updateEntity", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("preSaveUpdateEntity", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected void preSaveValidation(CkTaskAttach dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub

	}

	@Override
	protected ServiceStatus preUpdateValidation(CkTaskAttach dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getWhereClause(CkTaskAttach dto, boolean wherePrinted)
			throws ParameterException, ProcessingException {
		log.debug("getWhereClause");

		try {
			if (null == dto)
				throw new ParameterException("param dto null");

			StringBuffer searchStatement = new StringBuffer();
			if (StringUtils.isNotBlank(dto.getAttId())) {
				searchStatement.append(getOperator(wherePrinted) + "o.attId LIKE :attId");
				wherePrinted = true;
			}

			if (StringUtils.isNotBlank(dto.getAttName())) {
				searchStatement.append(getOperator(wherePrinted) + "o.attName LIKE :attName");
				wherePrinted = true;
			}
			Optional<MstAttType> opMstAttType = Optional.ofNullable(dto.getTMstAttType());
			if (opMstAttType.isPresent() && StringUtils.isNotBlank(opMstAttType.get().getMattName())) {
				searchStatement.append(getOperator(wherePrinted) + "o.TMstAttType.mattName LIKE :taskAttTypeName");
				wherePrinted = true;
			}

			if (dto.getAttStatus() != null && Character.isAlphabetic(dto.getAttStatus())) {
				searchStatement.append(getOperator(wherePrinted) + "o.attStatus = :attStatus");
				wherePrinted = true;
			}

			Optional<CkTask> opCkTask = Optional.ofNullable(dto.getTCkTask());
			if (opCkTask.isPresent()) {
				Optional<CkJob> opCkJob = Optional.ofNullable(dto.getTCkTask().getTCkJob());
				if (opCkJob.isPresent() && StringUtils.isNotBlank(opCkJob.get().getJobId())) {
					searchStatement.append(getOperator(wherePrinted) + "o.TCkTask.TCkJob.jobId = :jobId");
					wherePrinted = true;
				}

				Optional<CkMstShipmentType> opCkMstShpmentType = Optional
						.ofNullable(dto.getTCkTask().getTCkMstShipmentType());
				if (opCkMstShpmentType.isPresent()) {
					if (StringUtils.isNotBlank(opCkMstShpmentType.get().getShtId())) {
						searchStatement.append(
								getOperator(wherePrinted) + "o.TCkTask.TCkMstShipmentType.shtId = :taskShipmentType");
						wherePrinted = true;
					}
				}

				Optional<CkMstTaskState> opCkMstTaskState = Optional.ofNullable(dto.getTCkTask().getTCkMstTaskState());
				if (opCkMstTaskState.isPresent()) {
					if (StringUtils.isNotBlank(opCkMstTaskState.get().getTskstId())) {
						searchStatement
								.append(getOperator(wherePrinted) + "o.TCkTask.TCkMstTaskState.tskstId = :taskState");
						wherePrinted = true;
					}
				}

				Optional<CkMstTaskType> opCkMstTaskType = Optional.ofNullable(dto.getTCkTask().getTCkMstTaskType());
				if (opCkMstTaskType.isPresent()) {
					if (StringUtils.isNotBlank(opCkMstTaskType.get().getTsktId())) {
						searchStatement
								.append(getOperator(wherePrinted) + "o.TCkTask.TCkMstTaskType.tsktId = :taskType");
						wherePrinted = true;
					}

				}

			}

			return searchStatement.toString();
		} catch (ParameterException ex) {
			log.error("getWhereClause", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("getWhereClause", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected HashMap<String, Object> getParameters(CkTaskAttach dto) throws ParameterException, ProcessingException {
		log.debug("getParameters");

		try {
			if (null == dto)
				throw new ParameterException("param dto null");

			HashMap<String, Object> parameters = new HashMap<String, Object>();

			if (StringUtils.isNotBlank(dto.getAttId()))
				parameters.put("attId", "%" + dto.getAttId() + "%");

			if (StringUtils.isNotBlank(dto.getAttName()))
				parameters.put("attName", "%" + dto.getAttName() + "%");

			Optional<MstAttType> opMstAttType = Optional.ofNullable(dto.getTMstAttType());
			if (opMstAttType.isPresent() && StringUtils.isNotBlank(dto.getTMstAttType().getMattName())) {
				parameters.put("svcAttTypeName", "%" + dto.getTMstAttType().getMattName() + "%");
			}

			if (dto.getAttStatus() != null && Character.isAlphabetic(dto.getAttStatus()))
				parameters.put("attStatus", dto.getAttStatus());

			Optional<CkTask> opCkTask = Optional.ofNullable(dto.getTCkTask());
			if (opCkTask.isPresent()) {
				Optional<CkJob> opCkJob = Optional.ofNullable(dto.getTCkTask().getTCkJob());
				if (opCkJob.isPresent() && StringUtils.isNotBlank(opCkJob.get().getJobId()))
					parameters.put("jobId", opCkJob.get().getJobId());

				Optional<CkMstShipmentType> opCkMstShpmentType = Optional
						.ofNullable(dto.getTCkTask().getTCkMstShipmentType());
				if (opCkMstShpmentType.isPresent() && StringUtils.isNotBlank(opCkMstShpmentType.get().getShtId()))
					parameters.put("taskShipmentType", opCkMstShpmentType.get().getShtId());

				Optional<CkMstTaskState> opCkMstTaskState = Optional.ofNullable(dto.getTCkTask().getTCkMstTaskState());
				if (opCkMstTaskState.isPresent() && StringUtils.isNotBlank(opCkMstTaskState.get().getTskstId()))
					parameters.put("taskState", opCkMstTaskState.get().getTskstId());

				Optional<CkMstTaskType> opCkMstTaskType = Optional.ofNullable(dto.getTCkTask().getTCkMstTaskType());
				if (opCkMstTaskType.isPresent() && StringUtils.isNotBlank(opCkMstTaskType.get().getTsktId()))
					parameters.put("taskType", opCkMstTaskType.get().getTsktId());

			}

			return parameters;
		} catch (ParameterException ex) {
			log.error("getParameters", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("getParameters", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected CkTaskAttach whereDto(EntityFilterRequest filterRequest) throws ParameterException, ProcessingException {
		log.debug("whereDto");

		try {
			if (null == filterRequest)
				throw new ParameterException("param filterRequest null");

			CkTaskAttach dto = new CkTaskAttach();
			CkTask ckTask = new CkTask();
			CkJob ckJob = new CkJob();
			CkMstShipmentType ckMstShpType = new CkMstShipmentType();
			CkMstTaskState ckMstTaskState = new CkMstTaskState();
			CkMstTaskType ckMstTaskType = new CkMstTaskType();
			MstAttType mstAttType = new MstAttType();

			for (EntityWhere entityWhere : filterRequest.getWhereList()) {
				Optional<String> opValue = Optional.ofNullable(entityWhere.getValue());
				if (!opValue.isPresent())
					continue;

				if (entityWhere.getAttribute().equalsIgnoreCase("attId"))
					dto.setAttId(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("attName"))
					dto.setAttName(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("TMstAttType.mattName"))
					mstAttType.setMattName(opValue.get());

				if (entityWhere.getAttribute().equalsIgnoreCase("mattStatus"))
					dto.setAttStatus(opValue.get().charAt(0));

				if (entityWhere.getAttribute().equals("TCkTask.TCkJob.jobId"))
					ckJob.setJobId(opValue.get());

				if (entityWhere.getAttribute().equalsIgnoreCase("TCkTask.TCkMstShipmentType.shtId"))
					ckMstShpType.setShtId(opValue.get());

				if (entityWhere.getAttribute().equalsIgnoreCase("TCkTask.TCkMstTaskState.tskstId"))
					ckMstTaskState.setTskstId(opValue.get());

				if (entityWhere.getAttribute().equalsIgnoreCase("TCkTask.TCkMstTaskType.tsktId"))
					ckMstTaskType.setTsktId(opValue.get());

			}

			ckTask.setTCkJob(ckJob);
			ckTask.setTCkMstShipmentType(ckMstShpType);
			ckTask.setTCkMstTaskState(ckMstTaskState);
			ckTask.setTCkMstTaskType(ckMstTaskType);
			dto.setTMstAttType(mstAttType);
			dto.setTCkTask(ckTask);
			return dto;
		} catch (ParameterException ex) {
			log.error("whereDto", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("whereDto", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected CoreMstLocale getCoreMstLocale(CkTaskAttach dto)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CkTaskAttach setCoreMstLocale(CoreMstLocale coreMstLocale, CkTaskAttach dto)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

}
