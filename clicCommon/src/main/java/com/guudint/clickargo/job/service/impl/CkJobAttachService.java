package com.guudint.clickargo.job.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.common.AbstractClickCargoEntityService;
import com.guudint.clickargo.common.ICkConstant;
import com.guudint.clickargo.common.RecordStatus;
import com.guudint.clickargo.common.dto.CkRecordDate;
import com.guudint.clickargo.common.model.TCkRecordDate;
import com.guudint.clickargo.job.dto.CkJob;
import com.guudint.clickargo.job.dto.CkJobAttach;
import com.guudint.clickargo.job.model.TCkJob;
import com.guudint.clickargo.job.model.TCkJobAttach;
import com.guudint.clickargo.master.dto.CkMstJobState;
import com.guudint.clickargo.master.dto.CkMstJobType;
import com.guudint.clickargo.master.dto.CkMstShipmentType;
import com.guudint.clickargo.master.model.TCkMstJobState;
import com.guudint.clickargo.master.model.TCkMstJobType;
import com.guudint.clickargo.master.model.TCkMstShipmentType;
import com.guudint.clickargo.master.service.ICkMstRecords;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.common.controller.entity.EntityFilterRequest;
import com.vcc.camelone.common.controller.entity.EntityOrderBy;
import com.vcc.camelone.common.controller.entity.EntityWhere;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.exception.ValidationException;
import com.vcc.camelone.common.service.ServiceStatus;
import com.vcc.camelone.config.model.TCoreSysparam;
import com.vcc.camelone.locale.dto.CoreMstLocale;
import com.vcc.camelone.master.dto.MstAttType;
import com.vcc.camelone.master.model.TMstAttType;
import com.vcc.camelone.master.service.impl.AccountTypeService;
import com.vcc.camelone.util.bean.COBeansUtil;

public class CkJobAttachService extends AbstractClickCargoEntityService<TCkJobAttach, String, CkJobAttach> {
	// Static Attributes
	////////////////////
	private static Logger log = Logger.getLogger(AccountTypeService.class);
	private static String auditTag = "JOB ATTACH";
	private static String tableName = "T_CK_JOB_ATTACH";

	// Constructor
	//////////////
	public CkJobAttachService() {
		super("ckJobAttachDao", auditTag, TCkJobAttach.class.getName(), tableName);
	}

	@Autowired
	protected ICkMstRecords clicMasterRecrodsSvc;
	
	@Autowired
	protected GenericDao<TCkJobAttach, String> ckJobAttachDao;
	
	@Autowired
	protected GenericDao<TCkJob, String> ckJobDao;
	
	@Autowired
	@Qualifier("coreSysparamDao")
	protected GenericDao<TCoreSysparam, String> coreSysparamDao;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CkJobAttach findById(String id) throws ParameterException, EntityNotFoundException, ProcessingException {
		log.debug("findById");

		try {
			if (StringUtils.isEmpty(id))
				throw new ParameterException("param id null or empty");

			TCkJobAttach entity = dao.find(id);
			if (null == entity)
				throw new EntityNotFoundException("id: " + id);
			this.initEnity(entity);

			return this.dtoFromEntity(entity, true);
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
	public CkJobAttach deleteById(String id, Principal principal)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		log.debug("deleteById");

		Date now = Calendar.getInstance().getTime();
		try {
			if (StringUtils.isEmpty(id))
				throw new ParameterException("param id null or empty");
			if (null == principal)
				throw new ParameterException("param prinicipal null");

			TCkJobAttach entity = dao.find(id);
			if (null == entity)
				throw new EntityNotFoundException("id: " + id);

			this.updateEntityStatus(entity, RecordStatus.INACTIVE.getCode());
			this.updateEntity(ACTION.MODIFY, entity, principal, now);

			CkJobAttach dto = dtoFromEntity(entity);
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
	public List<CkJobAttach> filterBy(EntityFilterRequest filterRequest)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		log.debug("filterBy");

		try {
			if (null == filterRequest)
				throw new ParameterException("param filterRequest null");

			CkJobAttach dto = this.whereDto(filterRequest);
			if (null == dto)
				throw new ProcessingException("whereDto null");

			filterRequest.setTotalRecords(super.countByAnd(dto));

			String selectClause = "from TCkJobAttach o ";
			String orderByClause = formatOrderByObj(filterRequest.getOrderBy()).toString();
			List<TCkJobAttach> entities = super.findEntitiesByAnd(dto, selectClause, orderByClause,
					filterRequest.getDisplayLength(), filterRequest.getDisplayStart());
			List<CkJobAttach> dtos = entities.stream().map(x -> {
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
	protected TCkJobAttach initEnity(TCkJobAttach entity) throws ParameterException, ProcessingException {
		if (null != entity) {
			Hibernate.initialize(entity.getTCkJob());
			Hibernate.initialize(entity.getTMstAttType());
		}

		return entity;
	}

	@Override
	protected TCkJobAttach entityFromDTO(CkJobAttach dto) throws ParameterException, ProcessingException {
		try {
			if (null == dto)
				throw new ParameterException("dto dto null");

			TCkJobAttach entity = new TCkJobAttach();
			entity = dto.toEntity(entity);

			Optional<CkJob> opCkJob = Optional.ofNullable(dto.getTCkJob());
			entity.setTCkJob(opCkJob.isPresent() ? opCkJob.get().toEntity(new TCkJob()) : null);
			if (opCkJob.isPresent()) {
				Optional<CkMstJobState> opMstJobState = Optional.ofNullable(dto.getTCkJob().getTCkMstJobState());
				Optional<CkMstJobType> opMstJobType = Optional.ofNullable(dto.getTCkJob().getTCkMstJobType());
				Optional<CkMstShipmentType> opMstShpmentType = Optional
						.ofNullable(dto.getTCkJob().getTCkMstShipmentType());
				Optional<CkRecordDate> opRecordDate = Optional.ofNullable(dto.getTCkJob().getTCkRecordDate());

				entity.getTCkJob().setTCkMstJobState(
						opMstJobState.isPresent() ? opMstJobState.get().toEntity(new TCkMstJobState()) : null);
				entity.getTCkJob().setTCkMstJobType(
						opMstJobType.isPresent() ? opMstJobType.get().toEntity(new TCkMstJobType()) : null);
				entity.getTCkJob().setTCkMstShipmentType(
						opMstShpmentType.isPresent() ? opMstShpmentType.get().toEntity(new TCkMstShipmentType())
								: null);
				entity.getTCkJob().setTCkRecordDate(
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
	protected CkJobAttach dtoFromEntity(TCkJobAttach entity) throws ParameterException, ProcessingException {
		log.debug("dtoFromEntity");

		try {
			if (null == entity)
				throw new ParameterException("param entity null");

			CkJobAttach dto = new CkJobAttach(entity);

			Optional<TCkJob> opCkJob = Optional.ofNullable(entity.getTCkJob());

			if (opCkJob.isPresent()) {
				dto.setTCkJob(new CkJob(opCkJob.get()));

				Optional<TCkMstJobState> opMstJobState = Optional.ofNullable(entity.getTCkJob().getTCkMstJobState());
				Optional<TCkMstJobType> opMstJobType = Optional.ofNullable(entity.getTCkJob().getTCkMstJobType());
				Optional<TCkMstShipmentType> opMstShpmentType = Optional
						.ofNullable(entity.getTCkJob().getTCkMstShipmentType());
				Optional<TCkRecordDate> opRecordDate = Optional.ofNullable(entity.getTCkJob().getTCkRecordDate());

				opMstJobState.ifPresent(c -> dto.getTCkJob().setTCkMstJobState(new CkMstJobState(c)));
				opMstJobType.ifPresent(c -> dto.getTCkJob().setTCkMstJobType(new CkMstJobType(c)));
				opMstShpmentType.ifPresent(c -> dto.getTCkJob().setTCkMstShipmentType(new CkMstShipmentType(c)));
				opRecordDate.ifPresent(c -> dto.getTCkJob().setTCkRecordDate(new CkRecordDate(c)));
			}

			Optional<TMstAttType> opMstAttType = Optional.ofNullable(entity.getTMstAttType());
			dto.setTMstAttType(new MstAttType(opMstAttType.get()));
//			if(!StringUtils.isBlank(dto.getAttLoc())){
//				File file = new File(dto.getAttLoc());
//				dto.setAttData(Files.readAllBytes(file.toPath()));
//			}

			return dto;
		} catch (ParameterException ex) {
			log.error("entityFromDTO", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("dtoFromEntity", ex);
			throw new ProcessingException(ex);
		}
	}
	
	/** 
	 * Overloaded to add flag if data is needed. 
	 */
	public CkJobAttach dtoFromEntity(TCkJobAttach entity, boolean withData) throws ParameterException, ProcessingException {
		log.debug("dtoFromEntity");

		try {
			if (null == entity)
				throw new ParameterException("param entity null");

			CkJobAttach dto = new CkJobAttach(entity);

			Optional<TCkJob> opCkJob = Optional.ofNullable(entity.getTCkJob());

			if (opCkJob.isPresent()) {
				dto.setTCkJob(new CkJob(opCkJob.get()));

				Optional<TCkMstJobState> opMstJobState = Optional.ofNullable(entity.getTCkJob().getTCkMstJobState());
				Optional<TCkMstJobType> opMstJobType = Optional.ofNullable(entity.getTCkJob().getTCkMstJobType());
				Optional<TCkMstShipmentType> opMstShpmentType = Optional
						.ofNullable(entity.getTCkJob().getTCkMstShipmentType());
				Optional<TCkRecordDate> opRecordDate = Optional.ofNullable(entity.getTCkJob().getTCkRecordDate());

				opMstJobState.ifPresent(c -> dto.getTCkJob().setTCkMstJobState(new CkMstJobState(c)));
				opMstJobType.ifPresent(c -> dto.getTCkJob().setTCkMstJobType(new CkMstJobType(c)));
				opMstShpmentType.ifPresent(c -> dto.getTCkJob().setTCkMstShipmentType(new CkMstShipmentType(c)));
				opRecordDate.ifPresent(c -> dto.getTCkJob().setTCkRecordDate(new CkRecordDate(c)));
			}

			Optional<TMstAttType> opMstAttType = Optional.ofNullable(entity.getTMstAttType());
			dto.setTMstAttType(new MstAttType(opMstAttType.get()));
			if(withData) {
				if(!StringUtils.isBlank(dto.getAttDoc())){
					File file = new File(dto.getAttDoc());
					dto.setAttData(Files.readAllBytes(file.toPath()));
				}
			}


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
	protected String entityKeyFromDTO(CkJobAttach dto) throws ParameterException, ProcessingException {
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
	protected TCkJobAttach updateEntity(ACTION attriubte, TCkJobAttach entity, Principal principal, Date date)
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
	protected TCkJobAttach updateEntityStatus(TCkJobAttach entity, char status)
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
	protected CkJobAttach preSaveUpdateDTO(TCkJobAttach storedEntity, CkJobAttach dto)
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
	protected void preSaveValidation(CkJobAttach dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub

	}

	@Override
	protected ServiceStatus preUpdateValidation(CkJobAttach dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getWhereClause(CkJobAttach dto, boolean wherePrinted)
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
				searchStatement.append(getOperator(wherePrinted) + "o.TMstAttType.mattName LIKE :svcAttTypeName");
				wherePrinted = true;
			}

			if (dto.getAttStatus() != null && Character.isAlphabetic(dto.getAttStatus())) {
				searchStatement.append(getOperator(wherePrinted) + "o.attStatus = :attStatus");
				wherePrinted = true;
			}
			
			if(StringUtils.isNotBlank(dto.getAttRefNo())) {
				searchStatement.append(getOperator(wherePrinted) + "o.attRefNo LIKE :attRefNo");
				wherePrinted = true;
			}

			Optional<CkJob> opCkJob = Optional.ofNullable(dto.getTCkJob());
			if (opCkJob.isPresent()) {
				if (StringUtils.isNotBlank(dto.getTCkJob().getJobId())) {
					searchStatement.append(getOperator(wherePrinted) + "o.TCkJob.jobId = :jobId");
					wherePrinted = true;
				}

				Optional<CkMstShipmentType> opCkMstShpmentType = Optional
						.ofNullable(dto.getTCkJob().getTCkMstShipmentType());
				if (opCkMstShpmentType.isPresent()) {
					if (StringUtils.isNotBlank(opCkMstShpmentType.get().getShtId())) {
						searchStatement.append(
								getOperator(wherePrinted) + "o.TCkJob.TCkMstShipmentType.shtId = :taskShipmentType");
						wherePrinted = true;
					}
				}

				Optional<CkMstJobState> opCkMstJobState = Optional.ofNullable(dto.getTCkJob().getTCkMstJobState());
				if (opCkMstJobState.isPresent()) {
					if (StringUtils.isNotBlank(opCkMstJobState.get().getJbstId())) {
						searchStatement
								.append(getOperator(wherePrinted) + "o.TCkJob.TCkMstJobState.jbstId = :jobState");
						wherePrinted = true;
					}
				}

				Optional<CkMstJobType> opCkMstJobType = Optional.ofNullable(dto.getTCkJob().getTCkMstJobType());
				if (opCkMstJobType.isPresent()) {
					if (StringUtils.isNotBlank(opCkMstJobType.get().getJbtId())) {
						searchStatement.append(getOperator(wherePrinted) + "o.TCkJob.TCkMstJobType.tsktId = :jobType");
						wherePrinted = true;
					}

				}

			}

			Optional<Date> opAttDtCreate = Optional.ofNullable(dto.getAttDtCreate());
			if (opAttDtCreate.isPresent()) {
				searchStatement.append(getOperator(wherePrinted) + "DATE_FORMAT(o.attDtCreate,'%d/%m/%Y') = :attDtCreate");
				wherePrinted = true;
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
	protected HashMap<String, Object> getParameters(CkJobAttach dto) throws ParameterException, ProcessingException {
		log.debug("getParameters");

		try {
			if (null == dto)
				throw new ParameterException("param dto null");

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
			
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

			Optional<CkJob> opCkJob = Optional.ofNullable(dto.getTCkJob());
			if (opCkJob.isPresent()) {
				if (StringUtils.isNotBlank(opCkJob.get().getJobId()))
					parameters.put("jobId", opCkJob.get().getJobId());

				Optional<CkMstShipmentType> opCkMstShpmentType = Optional
						.ofNullable(dto.getTCkJob().getTCkMstShipmentType());
				if (opCkMstShpmentType.isPresent() && StringUtils.isNotBlank(opCkMstShpmentType.get().getShtId()))
					parameters.put("taskShipmentType", opCkMstShpmentType.get().getShtId());

				Optional<CkMstJobState> opCkMstJobState = Optional.ofNullable(dto.getTCkJob().getTCkMstJobState());
				if (opCkMstJobState.isPresent() && StringUtils.isNotBlank(opCkMstJobState.get().getJbstId()))
					parameters.put("jobState", opCkMstJobState.get().getJbstId());

				Optional<CkMstJobType> opCkMstJobType = Optional.ofNullable(dto.getTCkJob().getTCkMstJobType());
				if (opCkMstJobType.isPresent() && StringUtils.isNotBlank(opCkMstJobType.get().getJbtId()))
					parameters.put("jobType", opCkMstJobType.get().getJbtId());

			}
			
			if(StringUtils.isNotBlank(dto.getAttRefNo())) {
				parameters.put("attRefNo",  "%" + dto.getAttRefNo() + "%");
			}

			Optional<Date> opAttDtCreate = Optional.ofNullable(dto.getAttDtCreate());
			if (opAttDtCreate.isPresent() && null != opAttDtCreate.get())
				parameters.put("attDtCreate", sdfDate.format(opAttDtCreate.get()));
			
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
	protected CkJobAttach whereDto(EntityFilterRequest filterRequest) throws ParameterException, ProcessingException {
		log.debug("whereDto");

		try {
			if (null == filterRequest)
				throw new ParameterException("param filterRequest null");

			CkJobAttach dto = new CkJobAttach();
			CkJob ckJob = new CkJob();
			CkMstShipmentType ckMstShpType = new CkMstShipmentType();
			CkMstJobState ckMstJobState = new CkMstJobState();
			CkMstJobType ckMstJobType = new CkMstJobType();
			MstAttType mstAttType = new MstAttType();

			SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
			
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

				if (entityWhere.getAttribute().equals("TCkJob.jobId"))
					ckJob.setJobId(opValue.get());

				if (entityWhere.getAttribute().equalsIgnoreCase("TCkJob.TCkMstShipmentType.shtId"))
					ckMstShpType.setShtId(opValue.get());

				if (entityWhere.getAttribute().equalsIgnoreCase("TCkJob.TCkMstJobState.jbstId"))
					ckMstJobState.setJbstId(opValue.get());

				if (entityWhere.getAttribute().equalsIgnoreCase("TCkJob.TCkMstJobType.jbtId"))
					ckMstJobType.setJbtId(opValue.get());
				
				if (entityWhere.getAttribute().equalsIgnoreCase("attRefNo"))
					dto.setAttRefNo(opValue.get());
				
				if (entityWhere.getAttribute().equalsIgnoreCase("attDtCreate"))
					dto.setAttDtCreate(sdfDate.parse(opValue.get()));

			}

			ckJob.setTCkMstJobState(ckMstJobState);
			ckJob.setTCkMstJobType(ckMstJobType);
			ckJob.setTCkMstShipmentType(ckMstShpType);
			dto.setTMstAttType(mstAttType);
			dto.setTCkJob(ckJob);
			return dto;
		} catch (ParameterException ex) {
			log.error("whereDto", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("whereDto", ex);
			throw new ProcessingException(ex);
		}
	}

	/**
	 * Check if a document is already present based on jobId, attachment type
	 * @param jobId
	 * @param attType
	 * @return
	 * @throws ParameterException
	 * @throws EntityNotFoundException
	 * @throws ProcessingException
	 */
	@Transactional
	public boolean isAttDuplicate(String jobId, String attType) throws ParameterException, EntityNotFoundException, ProcessingException {

		try {
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("jobId", jobId);
			parameters.put("attStatus", RecordStatus.ACTIVE.getCode());
//			parameters.put("attRefNo", attRefNo);
			parameters.put("attType", attType);

			String hql = "from TCkJobAttach o where o.TCkJob.jobId = :jobId AND o.TMstAttType.mattId = :attType AND o.attStatus = :attStatus";
			List<TCkJobAttach> ckJobAttachList = dao.getByQuery(hql, parameters);

			if (ckJobAttachList.size() > 0) {
				return true;
			}
			
		} catch (ParameterException | EntityNotFoundException ex) {
			log.error("isAttDuplicate", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("isAttDuplicate", ex);
			throw new ProcessingException(ex);
		}
		return false;
	}
	
	@Override
	protected CoreMstLocale getCoreMstLocale(CkJobAttach dto)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CkJobAttach setCoreMstLocale(CoreMstLocale coreMstLocale, CkJobAttach dto)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.guudint.clickargo.job.service.IJobService#createAttachment(com.guudint.clickargo.job.dto.CkJobAttach,
	 *      com.vcc.camelone.cac.model.Principal)
	 * 
	 */
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
			throw new ProcessingException("jobId null or empty");

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

		// Added to check duplicate attachment, needs to add BL no in the future.
//		if (this.isAttDuplicate(_jobAttach.getTCkJob().getJobId(), opMstAttachTypeId.get())) {
//			jobAttach.setDuplicate(true);
//			return jobAttach;
//		} else {
			String fileLocation = this.saveAttachment(jobAttach.getAttName(), jobAttach.getAttData());
			if (StringUtils.isNotBlank(fileLocation))
				_jobAttach.setAttDoc(fileLocation);
	
			ckJobAttachDao.saveOrUpdate(_jobAttach);
	
			jobAttach.setAttDoc(fileLocation);
//		}
		return jobAttach;

	};
		
	/**
	 * Saves the specified file to the base location configured in SysParam
	 * 
	 * @param filename
	 * @param data     - byte
	 */
	protected String saveAttachment(String filename, byte[] data) throws Exception {
		if (StringUtils.isBlank(filename))
			throw new ParameterException("param filename null or empty");

		if (data == null)
			throw new ParameterException("param data null or empty");

		String basePath = getSysParam(ICkConstant.KEY_CLICDO_ATTCH_BASE_LOCATION);
		if (StringUtils.isBlank(basePath))
			throw new ProcessingException("basePath is not configured");

		String file = basePath.concat(filename);
		FileOutputStream output = new FileOutputStream(file);
		output.write(data);
		output.close();
		return file;
	}
	
	protected String getSysParam(String key) throws Exception {
		if (StringUtils.isBlank(key))
			throw new ParameterException("param key null or empty");

		TCoreSysparam sysParam = coreSysparamDao.find(key);
		if (sysParam != null) {
			return sysParam.getSysVal();
		}

		return null;
	}

    private EntityOrderBy formatOrderByObj(EntityOrderBy orderBy) {
        if (orderBy == null) {
            return null;
        }
        if (StringUtils.isEmpty(orderBy.getAttribute())) {
            return null;
        }
        String newAttr = orderBy.getAttribute();
        newAttr = newAttr.replaceAll("tmstAttType", "TMstAttType").
        		replaceAll("ckMstShipmentType", "CkMstShipmentType").
        		replaceAll("ckMstJobState", "CkMstJobState").
        		replaceAll("ckMstJobType", "CkMstJobType");
        orderBy.setAttribute(newAttr);
        return orderBy;
    }
    
	@Override
	public CkJobAttach newObj(Principal principal)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		return new CkJobAttach();
	}

	@Override
	protected void initBusinessValidator() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Logger getLogger() {
		return log;
	}
	
}
