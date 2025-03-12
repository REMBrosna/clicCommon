package com.guudint.clickargo.job.service.impl;

import java.text.SimpleDateFormat;
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
import com.guudint.clickargo.job.dto.CkJobQuery;
import com.guudint.clickargo.job.model.TCkJob;
import com.guudint.clickargo.job.model.TCkJobQuery;
import com.guudint.clickargo.master.dto.CkMstJobState;
import com.guudint.clickargo.master.dto.CkMstJobType;
import com.guudint.clickargo.master.dto.CkMstShipmentType;
import com.guudint.clickargo.master.model.TCkMstJobState;
import com.guudint.clickargo.master.model.TCkMstJobType;
import com.guudint.clickargo.master.model.TCkMstShipmentType;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.ccm.dto.CoreUsr;
import com.vcc.camelone.ccm.model.TCoreUsr;
import com.vcc.camelone.common.controller.entity.EntityFilterRequest;
import com.vcc.camelone.common.controller.entity.EntityWhere;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.exception.ValidationException;
import com.vcc.camelone.common.service.ServiceStatus;
import com.vcc.camelone.common.service.entity.AbstractEntityService;
import com.vcc.camelone.locale.dto.CoreMstLocale;

public class CkJobQueryService extends AbstractEntityService<TCkJobQuery, String, CkJobQuery> {

	// Static Attributes
	////////////////////
	private static Logger LOG = Logger.getLogger(CkJobQueryService.class);
	private static String AUDIT_TAG = "JOB QUERY";
	private static String TABLE_NAME = "T_CK_JOB_QUERY";

	public CkJobQueryService() {
		super("ckJobQueryDao", AUDIT_TAG, CkJobQueryService.class.getName(), TABLE_NAME);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CkJobQuery findById(String id) throws ParameterException, EntityNotFoundException, ProcessingException {
		LOG.debug("findById");

		try {
			if (StringUtils.isEmpty(id))
				throw new ParameterException("param id null or empty");

			TCkJobQuery entity = dao.find(id);
			if (null == entity)
				throw new EntityNotFoundException("id: " + id);
			this.initEnity(entity);

			return this.dtoFromEntity(entity);
		} catch (ParameterException | EntityNotFoundException ex) {
			LOG.error("findById", ex);
			throw ex;
		} catch (Exception ex) {
			LOG.error("findById", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CkJobQuery deleteById(String id, Principal principal)
			throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException {
		LOG.debug("deleteById");

		Date now = Calendar.getInstance().getTime();
		try {
			if (StringUtils.isEmpty(id))
				throw new ParameterException("param id null or empty");
			if (null == principal)
				throw new ParameterException("param prinicipal null");

			TCkJobQuery entity = dao.find(id);
			if (null == entity)
				throw new EntityNotFoundException("id: " + id);

			this.updateEntityStatus(entity, RecordStatus.INACTIVE.getCode());
			this.updateEntity(ACTION.MODIFY, entity, principal, now);

			CkJobQuery dto = dtoFromEntity(entity);
			this.delete(dto, principal);
			return dto;
		} catch (ParameterException | EntityNotFoundException ex) {
			LOG.error("deleteById", ex);
			throw ex;
		} catch (Exception ex) {
			LOG.error("deleteById", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public List<CkJobQuery> filterBy(EntityFilterRequest filterRequest)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		LOG.debug("filterBy");

		try {
			if (null == filterRequest)
				throw new ParameterException("param filterRequest null");

			CkJobQuery dto = this.whereDto(filterRequest);
			if (null == dto)
				throw new ProcessingException("whereDto null");

			filterRequest.setTotalRecords(super.countByAnd(dto));

			String selectClause = "from TCkJobAttach o ";
			String orderByClause = filterRequest.getOrderBy().toString();
			List<TCkJobQuery> entities = super.findEntitiesByAnd(dto, selectClause, orderByClause,
					filterRequest.getDisplayLength(), filterRequest.getDisplayStart());
			List<CkJobQuery> dtos = entities.stream().map(x -> {
				try {
					return dtoFromEntity(x);
				} catch (ParameterException | ProcessingException e) {
					LOG.error("filterBy", e);
				}
				return null;

			}).collect(Collectors.toList());

			return dtos;
		} catch (ParameterException | ProcessingException ex) {
			LOG.error("filterBy", ex);
			throw ex;
		} catch (Exception ex) {
			LOG.error("filterBy", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	protected TCkJobQuery initEnity(TCkJobQuery entity) throws ParameterException, ProcessingException {
		if (null != entity) {
			Hibernate.initialize(entity.getTCkJob());
			Hibernate.initialize(entity.getTCoreUsrByQryRequester());
			Hibernate.initialize(entity.getTCoreUsrByQryResponder());
		}

		return entity;
	}

	@Override
	protected TCkJobQuery entityFromDTO(CkJobQuery dto) throws ParameterException, ProcessingException {
		try {
			if (null == dto)
				throw new ParameterException("dto dto null");

			TCkJobQuery entity = new TCkJobQuery();
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

			Optional<CoreUsr> opUsrRequester = Optional.ofNullable(dto.getTCoreUsrByQryRequester());
			entity.setTCoreUsrByQryRequester(
					opUsrRequester.isPresent() ? opUsrRequester.get().toEntity(new TCoreUsr()) : null);

			Optional<CoreUsr> opUsrResponder = Optional.ofNullable(dto.getTCoreUsrByQryResponder());
			entity.setTCoreUsrByQryResponder(
					opUsrResponder.isPresent() ? opUsrResponder.get().toEntity(new TCoreUsr()) : null);

			return entity;
		} catch (ParameterException ex) {
			LOG.error("entityFromDTO", ex);
			throw ex;
		} catch (Exception ex) {
			LOG.error("entityFromDTO", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	public CkJobQuery dtoFromEntity(TCkJobQuery entity) throws ParameterException, ProcessingException {
		LOG.debug("dtoFromEntity");

		try {
			if (null == entity)
				throw new ParameterException("param entity null");

			CkJobQuery dto = new CkJobQuery(entity);

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

			Optional<TCoreUsr> opUsrRequester = Optional.ofNullable(entity.getTCoreUsrByQryRequester());
			opUsrRequester.ifPresent(c->dto.setTCoreUsrByQryRequester(new CoreUsr(c)));

			Optional<TCoreUsr> opUsrResponder = Optional.ofNullable(entity.getTCoreUsrByQryResponder());
			opUsrResponder.ifPresent(c->dto.setTCoreUsrByQryResponder(new CoreUsr(c)));
			
			return dto;
		} catch (ParameterException ex) {
			LOG.error("entityFromDTO", ex);
			throw ex;
		} catch (Exception ex) {
			LOG.error("dtoFromEntity", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected String entityKeyFromDTO(CkJobQuery dto) throws ParameterException, ProcessingException {
		LOG.debug("entityKeyFromDTO");

		try {
			if (null == dto)
				throw new ParameterException("dto param null");

			return dto.getQryId();
		} catch (ParameterException ex) {
			LOG.error("entityKeyFromDTO", ex);
			throw ex;
		} catch (Exception ex) {
			LOG.error("entityKeyFromDTO", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected TCkJobQuery updateEntity(ACTION attriubte, TCkJobQuery entity, Principal principal, Date date)
			throws ParameterException, ProcessingException {
		LOG.debug("updateEntity");

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
				entity.setQryUidCreate(opUserId.isPresent() ? opUserId.get() : "SYS");
				entity.setQryDtCreate(date);
				entity.setQryUidLupd(opUserId.isPresent() ? opUserId.get() : "SYS");
				entity.setQryDtLupd(date);
				break;

			case MODIFY:
				entity.setQryUidLupd(opUserId.isPresent() ? opUserId.get() : "SYS");
				entity.setQryDtLupd(date);
				break;

			default:
				break;
			}

			return entity;
		} catch (ParameterException ex) {
			LOG.error("updateEntity", ex);
			throw ex;
		} catch (Exception ex) {
			LOG.error("updateEntity", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected TCkJobQuery updateEntityStatus(TCkJobQuery entity, char status)
			throws ParameterException, ProcessingException {
		LOG.debug("updateEntityStatus");

		try {
			if (null == entity)
				throw new ParameterException("entity param null");

			entity.setQryStatus(status);
			return entity;
		} catch (ParameterException ex) {
			LOG.error("updateEntity", ex);
			throw ex;
		} catch (Exception ex) {
			LOG.error("updateEntityStatus", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected CkJobQuery preSaveUpdateDTO(TCkJobQuery storedEntity, CkJobQuery dto)
			throws ParameterException, ProcessingException {
		LOG.debug("preSaveUpdateDTO");

		try {
			if (null == storedEntity)
				throw new ParameterException("param storedEntity null");
			if (null == dto)
				throw new ParameterException("param dto null");

			dto.setQryUidCreate(storedEntity.getQryUidCreate());
			dto.setQryDtCreate(storedEntity.getQryDtCreate());

			return dto;
		} catch (ParameterException ex) {
			LOG.error("updateEntity", ex);
			throw ex;
		} catch (Exception ex) {
			LOG.error("preSaveUpdateEntity", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected void preSaveValidation(CkJobQuery dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub

	}

	@Override
	protected ServiceStatus preUpdateValidation(CkJobQuery dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getWhereClause(CkJobQuery dto, boolean wherePrinted)
			throws ParameterException, ProcessingException {
		LOG.debug("getWhereClause");

		try {
			if (null == dto)
				throw new ParameterException("param dto null");

			StringBuffer searchStatement = new StringBuffer();
			searchStatement.append(getOperator(wherePrinted) + "o.qryStatus = :qryStatus");
			wherePrinted = true;

			if (StringUtils.isNotBlank(dto.getQryId())) {
				searchStatement.append(getOperator(wherePrinted) + "o.qryId LIKE :qryId");
				wherePrinted = true;
			}

			Optional<CkJob> opCkJob = Optional.ofNullable(dto.getTCkJob());
			if (opCkJob.isPresent()) {
				if (StringUtils.isNotBlank(opCkJob.get().getJobId())) {
					searchStatement.append(getOperator(wherePrinted) + "o.TCkJob.jobId LIKE :jobId");
					wherePrinted = true;
				}
			}

			Optional<CoreUsr> opQryRequester = Optional.ofNullable(dto.getTCoreUsrByQryRequester());
			if (opQryRequester.isPresent()) {
				if (StringUtils.isNotBlank(opQryRequester.get().getUsrUid())) {
					searchStatement.append(
							getOperator(wherePrinted) + "o.TCoreUsrByQryRequester.usrUid LIKE :requesterUsrUid");
					wherePrinted = true;
				}

				if (StringUtils.isNotBlank(opQryRequester.get().getUsrName())) {
					searchStatement.append(
							getOperator(wherePrinted) + "o.TCoreUsrByQryRequester.usrName LIKE :requesterUsrname");
					wherePrinted = true;
				}

			}

			Optional<CoreUsr> opQryResponder = Optional.ofNullable(dto.getTCoreUsrByQryResponder());
			if (opQryResponder.isPresent()) {
				if (StringUtils.isNotBlank(opQryResponder.get().getUsrUid())) {
					searchStatement.append(
							getOperator(wherePrinted) + "o.TCoreUsrByQryResponder.usrUid LIKE :responderUsrUid");
					wherePrinted = true;
				}

				if (StringUtils.isNotBlank(opQryResponder.get().getUsrName())) {
					searchStatement.append(
							getOperator(wherePrinted) + "o.TCoreUsrByQryResponder.usrName LIKE :responderUsrname");
					wherePrinted = true;
				}

			}

			if (dto.getQryDtQuery() != null) {
				searchStatement.append(
						getOperator(wherePrinted) + "DATE_FORMAT(o.qryDtQuery,'%d/%m/%Y %H:%i:%s') = :qryDtQuery");
				wherePrinted = true;
			}

			if (StringUtils.isNotBlank(dto.getQryQuery())) {
				searchStatement.append(getOperator(wherePrinted) + "o.qryQuery LIKE :qryQuery");
				wherePrinted = true;
			}

			if (dto.getQryDtResponse() != null) {
				searchStatement.append(getOperator(wherePrinted)
						+ "DATE_FORMAT(o.qryDtResponse,'%d/%m/%Y %H:%i:%s') = :qryDtResponse");
				wherePrinted = true;
			}

			if (StringUtils.isNotBlank(dto.getQryResponse())) {
				searchStatement.append(getOperator(wherePrinted) + "o.qryResponse LIKE :qryResponse");
				wherePrinted = true;
			}

			return searchStatement.toString();
		} catch (ParameterException ex) {
			LOG.error("getWhereClause", ex);
			throw ex;
		} catch (Exception ex) {
			LOG.error("getWhereClause", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected HashMap<String, Object> getParameters(CkJobQuery dto) throws ParameterException, ProcessingException {
		LOG.debug("getParameters");

		try {
			if (null == dto)
				throw new ParameterException("param dto null");

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("qryStatus", RecordStatus.ACTIVE.getCode());

			if (StringUtils.isNotBlank(dto.getQryId()))
				parameters.put("qryId", "%" + dto.getQryId() + "%");

			Optional<CkJob> opCkJob = Optional.ofNullable(dto.getTCkJob());
			if (opCkJob.isPresent()) {
				if (StringUtils.isNotBlank(opCkJob.get().getJobId()))
					parameters.put("jobId", "%" + opCkJob.get().getJobId() + "%");

			}

			Optional<CoreUsr> opQryRequester = Optional.ofNullable(dto.getTCoreUsrByQryRequester());
			if (opQryRequester.isPresent()) {
				if (StringUtils.isNotBlank(opQryRequester.get().getUsrUid()))
					parameters.put("requesterUsrUid", "%" + opQryRequester.get().getUsrUid() + "%");

				if (StringUtils.isNotBlank(opQryRequester.get().getUsrName()))
					parameters.put("requesterUsrname", "%" + opQryRequester.get().getUsrName() + "%");
			}

			Optional<CoreUsr> opQryResponder = Optional.ofNullable(dto.getTCoreUsrByQryResponder());
			if (opQryResponder.isPresent()) {
				if (StringUtils.isNotBlank(opQryResponder.get().getUsrUid()))
					parameters.put("responderUsrUid", "%" + opQryResponder.get().getUsrUid() + "%");

				if (StringUtils.isNotBlank(opQryResponder.get().getUsrName()))
					parameters.put("responderUsrname", "%" + opQryResponder.get().getUsrName() + "%");

			}

			if (dto.getQryDtQuery() != null)
				parameters.put("qryDtQuery", sdf.format(dto.getQryDtQuery()));

			if (StringUtils.isNotBlank(dto.getQryQuery()))
				parameters.put("qryQuery", "%" + dto.getQryQuery() + "%");

			if (dto.getQryDtResponse() != null)
				parameters.put("qryDtResponse", sdf.format(dto.getQryDtResponse()));

			if (StringUtils.isNotBlank(dto.getQryResponse()))
				parameters.put("qryResponse", "%" + dto.getQryResponse() + "%");

			return parameters;
		} catch (ParameterException ex) {
			LOG.error("getParameters", ex);
			throw ex;
		} catch (Exception ex) {
			LOG.error("getParameters", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected CkJobQuery whereDto(EntityFilterRequest filterRequest) throws ParameterException, ProcessingException {
		LOG.debug("whereDto");

		try {
			if (null == filterRequest)
				throw new ParameterException("param filterRequest null");

			CkJobQuery dto = new CkJobQuery();
			CkJob ckJob = new CkJob();
			CoreUsr usrRequester = new CoreUsr();
			CoreUsr usrResponder = new CoreUsr();

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			
			for (EntityWhere entityWhere : filterRequest.getWhereList()) {
				Optional<String> opValue = Optional.ofNullable(entityWhere.getValue());
				if (!opValue.isPresent())
					continue;

				if (entityWhere.getAttribute().equalsIgnoreCase("qryId"))
					dto.setQryId(opValue.get());

				if (entityWhere.getAttribute().equals("TCkJob.jobId"))
					ckJob.setJobId(opValue.get());

				if (entityWhere.getAttribute().equalsIgnoreCase("TCoreUsrByQryRequester.usrUid"))
					usrRequester.setUsrUid(opValue.get());

				if (entityWhere.getAttribute().equalsIgnoreCase("TCoreUsrByQryRequester.usrName"))
					usrRequester.setUsrName(opValue.get());

				if (entityWhere.getAttribute().equalsIgnoreCase("TCoreUsrByQryResponder.usrUid"))
					usrResponder.setUsrUid(opValue.get());

				if (entityWhere.getAttribute().equalsIgnoreCase("TCoreUsrByQryResponder.usrName"))
					usrResponder.setUsrName(opValue.get());
				
				if (entityWhere.getAttribute().equalsIgnoreCase("qryDtQuery"))
					dto.setQryDtQuery(sdf.parse(opValue.get()));
				
				if (entityWhere.getAttribute().equalsIgnoreCase("qryQuery"))
					dto.setQryQuery(opValue.get());
				

				if (entityWhere.getAttribute().equalsIgnoreCase("qryDtResponse"))
					dto.setQryDtResponse(sdf.parse(opValue.get()));
				
				if (entityWhere.getAttribute().equalsIgnoreCase("qryResponse"))
					dto.setQryResponse(opValue.get());

			}

			dto.setTCkJob(ckJob);
			dto.setTCoreUsrByQryRequester(usrRequester);
			dto.setTCoreUsrByQryResponder(usrResponder);
			return dto;
		} catch (ParameterException ex) {
			LOG.error("whereDto", ex);
			throw ex;
		} catch (Exception ex) {
			LOG.error("whereDto", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected CoreMstLocale getCoreMstLocale(CkJobQuery dto)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CkJobQuery setCoreMstLocale(CoreMstLocale coreMstLocale, CkJobQuery dto)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

}
