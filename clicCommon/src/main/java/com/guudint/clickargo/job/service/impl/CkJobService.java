package com.guudint.clickargo.job.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.guudint.clickargo.common.RecordStatus;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.common.dto.CkRecordDate;
import com.guudint.clickargo.common.model.TCkRecordDate;
import com.guudint.clickargo.job.dto.CkJob;
import com.guudint.clickargo.job.model.TCkJob;
import com.guudint.clickargo.master.dto.CkMstJobState;
import com.guudint.clickargo.master.dto.CkMstJobType;
import com.guudint.clickargo.master.dto.CkMstShipmentType;
import com.guudint.clickargo.master.enums.JobStates;
import com.guudint.clickargo.master.model.TCkMstJobState;
import com.guudint.clickargo.master.model.TCkMstJobType;
import com.guudint.clickargo.master.model.TCkMstShipmentType;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.ccm.model.TCoreAccn;
import com.vcc.camelone.common.controller.entity.EntityFilterRequest;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.service.ServiceStatus;
import com.vcc.camelone.common.service.entity.AbstractEntityService;
import com.vcc.camelone.locale.dto.CoreMstLocale;

@Service
public class CkJobService extends AbstractEntityService<TCkJob, String, CkJob> {

	// Static Attributes
	////////////////////
	private static Logger log = Logger.getLogger(CkJobService.class);
	private static String AUDIT_TAG = "CK_JOB";
	private static String TABLE_NAME = "T_CK_JOB";

	public CkJobService() {
		super("ckJobDao", AUDIT_TAG, TCkJob.class.getName(), TABLE_NAME);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CkJob findById(String id) throws ParameterException, EntityNotFoundException, ProcessingException {
		log.debug("findById");

		try {
			if (StringUtils.isEmpty(id))
				throw new ParameterException("param id null or empty");

			TCkJob entity = dao.find(id);
			if (null == entity)
				throw new EntityNotFoundException("id: " + id);
			this.initEnity(entity);

			return this.dtoFromEntity(entity);
		} catch (ParameterException | EntityNotFoundException ex) {
			log.error("entityFromDTO", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("entityFromDTO", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CkJob deleteById(String id, Principal principal)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		log.debug("deleteById");

		Date now = Calendar.getInstance().getTime();
		try {
			if (StringUtils.isEmpty(id))
				throw new ParameterException("param id null or empty");
			if (null == principal)
				throw new ParameterException("param prinicipal null");

			TCkJob entity = dao.find(id);
			if (null == entity)
				throw new EntityNotFoundException("id: " + id);

			this.updateEntityStatus(entity, 'I');
			this.updateEntity(ACTION.MODIFY, entity, principal, now);

			CkJob dto = dtoFromEntity(entity);
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
	public List<CkJob> filterBy(EntityFilterRequest filterRequest)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	protected TCkJob initEnity(TCkJob entity) throws ParameterException, ProcessingException {
		if (null != entity) {
			Hibernate.initialize(entity.getTCkMstJobState());
			Hibernate.initialize(entity.getTCkMstJobType());
			Hibernate.initialize(entity.getTCkMstShipmentType());
			Hibernate.initialize(entity.getTCkRecordDate());
			Hibernate.initialize(entity.getTCoreAccnByJobCoAccn());
			Hibernate.initialize(entity.getTCoreAccnByJobFfAccn());
			Hibernate.initialize(entity.getTCoreAccnByJobOwnerAccn());
			Hibernate.initialize(entity.getTCoreAccnByJobSlAccn());
			Hibernate.initialize(entity.getTCoreAccnByJobToAccn());
		}
		return entity;
	}

	@Override
	protected TCkJob entityFromDTO(CkJob dto) throws ParameterException, ProcessingException {
		log.debug("entityFromDTO");

		try {
			if (null == dto)
				throw new ParameterException("dto dto null");

			TCkJob entity = new TCkJob();
			entity = dto.toEntity(entity);
			entity.setTCkMstJobState(
					null == dto.getTCkMstJobState() ? null : dto.getTCkMstJobState().toEntity(new TCkMstJobState()));
			entity.setTCkMstJobType(
					null == dto.getTCkMstJobType() ? null : dto.getTCkMstJobType().toEntity(new TCkMstJobType()));
			entity.setTCkMstShipmentType(null == dto.getTCkMstShipmentType() ? null
					: dto.getTCkMstShipmentType().toEntity(new TCkMstShipmentType()));
			entity.setTCkRecordDate(
					null == dto.getTCkRecordDate() ? null : dto.getTCkRecordDate().toEntity(new TCkRecordDate()));
			entity.setTCoreAccnByJobCoAccn(null == dto.getTCoreAccnByJobCoAccn() ? null
					: dto.getTCoreAccnByJobCoAccn().toEntity(new TCoreAccn()));
			entity.setTCoreAccnByJobFfAccn(null == dto.getTCoreAccnByJobFfAccn() ? null
					: dto.getTCoreAccnByJobFfAccn().toEntity(new TCoreAccn()));
			entity.setTCoreAccnByJobOwnerAccn(null == dto.getTCoreAccnByJobOwnerAccn() ? null
					: dto.getTCoreAccnByJobOwnerAccn().toEntity(new TCoreAccn()));
			entity.setTCoreAccnByJobSlAccn(null == dto.getTCoreAccnByJobSlAccn() ? null
					: dto.getTCoreAccnByJobSlAccn().toEntity(new TCoreAccn()));
			entity.setTCoreAccnByJobToAccn(null == dto.getTCoreAccnByJobToAccn() ? null
					: dto.getTCoreAccnByJobToAccn().toEntity(new TCoreAccn()));
			//retrieve TCoreAccnByJobSlAccn if TCoreAccnByJobSlAccn is null
			if (dto.getJobId() != null && entity.getTCoreAccnByJobSlAccn() == null){
				TCkJob tCkJob = findJobByTCoreAccnByJobSlAccn(dto.getJobId());
                if (tCkJob != null && tCkJob.getTCoreAccnByJobSlAccn() != null){
					CoreAccn accnByJobSlAccn = new CoreAccn();
					BeanUtils.copyProperties(tCkJob.getTCoreAccnByJobSlAccn(), accnByJobSlAccn);
					entity.setTCoreAccnByJobSlAccn(accnByJobSlAccn.toEntity(new TCoreAccn()));
				}
			}
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
	protected CkJob dtoFromEntity(TCkJob entity) throws ParameterException, ProcessingException {
		log.debug("dtoFromEntity");

		try {
			if (null == entity)
				throw new ParameterException("param entity null");

			CkJob dto = new CkJob(entity);
			// no deep copy from BeanUtils
			dto.setTCkMstJobState(
					null == entity.getTCkMstJobState() ? null : new CkMstJobState(entity.getTCkMstJobState()));
			dto.setTCkMstJobType(
					null == entity.getTCkMstJobType() ? null : new CkMstJobType(entity.getTCkMstJobType()));
			dto.setTCkMstShipmentType(null == entity.getTCkMstShipmentType() ? null
					: new CkMstShipmentType(entity.getTCkMstShipmentType()));
			dto.setTCkRecordDate(
					null == entity.getTCkRecordDate() ? null : new CkRecordDate(entity.getTCkRecordDate()));

			dto.setTCoreAccnByJobCoAccn(
					null == entity.getTCoreAccnByJobCoAccn() ? null : new CoreAccn(entity.getTCoreAccnByJobCoAccn()));
			dto.setTCoreAccnByJobFfAccn(
					null == entity.getTCoreAccnByJobFfAccn() ? null : new CoreAccn(entity.getTCoreAccnByJobFfAccn()));
			dto.setTCoreAccnByJobOwnerAccn(null == entity.getTCoreAccnByJobOwnerAccn() ? null
					: new CoreAccn(entity.getTCoreAccnByJobOwnerAccn()));
			dto.setTCoreAccnByJobSlAccn(
					null == entity.getTCoreAccnByJobSlAccn() ? null : new CoreAccn(entity.getTCoreAccnByJobSlAccn()));
			dto.setTCoreAccnByJobToAccn(
					null == entity.getTCoreAccnByJobToAccn() ? null : new CoreAccn(entity.getTCoreAccnByJobToAccn()));

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
	protected String entityKeyFromDTO(CkJob dto) throws ParameterException, ProcessingException {
		log.debug("entityKeyFromDTO");

		try {
			if (null == dto)
				throw new ParameterException("dto param null");

			return dto.getJobId();
		} catch (ParameterException ex) {
			log.error("entityKeyFromDTO", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("entityKeyFromDTO", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected TCkJob updateEntity(ACTION attriubte, TCkJob entity, Principal principal, Date date)
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
				entity.setJobUidCreate(opUserId.isPresent() ? opUserId.get() : "SYS");
				entity.setJobDtCreate(date);
				entity.setJobUidLupd(opUserId.isPresent() ? opUserId.get() : "SYS");
				entity.setJobDtLupd(date);
				break;

			case MODIFY:
				entity.setJobUidLupd(opUserId.isPresent() ? opUserId.get() : "SYS");
				entity.setJobDtLupd(date);
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
	protected TCkJob updateEntityStatus(TCkJob entity, char status) throws ParameterException, ProcessingException {
		log.debug("updateEntityStatus");

		try {
			if (null == entity)
				throw new ParameterException("entity param null");

			entity.setJobStatus(status);
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
	protected CkJob preSaveUpdateDTO(TCkJob storedEntity, CkJob dto) throws ParameterException, ProcessingException {
		log.debug("preSaveUpdateDTO");

		try {
			if (null == storedEntity)
				throw new ParameterException("param storedEntity null");
			if (null == dto)
				throw new ParameterException("param dto null");

			dto.setJobUidCreate(storedEntity.getJobUidCreate());
			dto.setJobDtCreate(storedEntity.getJobDtCreate());

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
	protected void preSaveValidation(CkJob dto, Principal principal) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub

	}

	@Override
	protected ServiceStatus preUpdateValidation(CkJob dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getWhereClause(CkJob dto, boolean wherePrinted) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected HashMap<String, Object> getParameters(CkJob dto) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CkJob whereDto(EntityFilterRequest filterRequest) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CoreMstLocale getCoreMstLocale(CkJob dto)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CkJob setCoreMstLocale(CoreMstLocale coreMstLocale, CkJob dto)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Transactional
	public List<TCkJob> findActiveJobsByAccnId(String accnId) {
		List<TCkJob> activeJobs = new ArrayList<TCkJob>();
		try {
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("accnId", accnId);
			parameters.put("jobStates", 
					Arrays.asList(JobStates.CAN.name(), JobStates.TERMINATED.name(), JobStates.COM.name()));

			StringBuilder hql = new StringBuilder("FROM TCkJob o");
			hql.append(" WHERE (o.TCoreAccnByJobSlAccn.accnId = :accnId ");
			hql.append(" OR o.TCoreAccnByJobCoAccn.accnId = :accnId ");
			hql.append(" OR o.TCoreAccnByJobFfAccn.accnId = :accnId ");
			hql.append(" OR o.TCoreAccnByJobToAccn.accnId = :accnId ");
			hql.append(" OR o.TCoreAccnByJobOwnerAccn.accnId = :accnId) ");
			hql.append(" AND o.TCkMstJobState.jbstId NOT IN :jobStates ");

			return activeJobs = dao.getByQuery(hql.toString(), parameters);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return activeJobs;
	}

	private TCkJob findJobByTCoreAccnByJobSlAccn(String jobId) throws Exception {
		String hql = "FROM TCkJob j WHERE j.jobId = :jobId AND j.jobStatus = :status AND j.TCkMstJobState.jbstId NOT IN :jobStates";
		Map<String, Object> params = new HashMap<>();
		params.put("jobId", jobId);
		params.put("status", RecordStatus.ACTIVE.getCode());
		params.put("jobStates", Arrays.asList(JobStates.CAN.name(), JobStates.TERMINATED.name(), JobStates.COM.name()));
		List<TCkJob> jobList = dao.getByQuery(hql, params);
		// If the list is not empty, return the first entity, else return null
		return jobList.isEmpty() ? null : jobList.get(0);
	}



}
