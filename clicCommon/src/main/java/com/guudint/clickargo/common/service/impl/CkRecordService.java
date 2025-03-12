package com.guudint.clickargo.common.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.guudint.clickargo.common.dto.CkRecordDate;
import com.guudint.clickargo.common.model.TCkRecordDate;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.common.controller.entity.EntityFilterRequest;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.service.ServiceStatus;
import com.vcc.camelone.common.service.entity.AbstractEntityService;
import com.vcc.camelone.locale.dto.CoreMstLocale;

@Service
public class CkRecordService extends AbstractEntityService<TCkRecordDate, String, CkRecordDate> {

	// Static Attributes
	////////////////////
	private static Logger log = Logger.getLogger(CkRecordService.class);
	private static String AUDIT_TAG = "CK_JOB";
	private static String TABLE_NAME = "T_CK_JOB";

	public CkRecordService() {
		super("ckRecordDateDao", AUDIT_TAG, TCkRecordDate.class.getName(), TABLE_NAME);
	}

	@Override
	public CkRecordDate findById(String id) throws ParameterException, EntityNotFoundException, ProcessingException {
		log.debug("findById");

		try {
			if (StringUtils.isEmpty(id))
				throw new ParameterException("param id null or empty");

			TCkRecordDate entity = dao.find(id);
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
	public CkRecordDate deleteById(String id, Principal principal)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		log.debug("deleteById");

		Date now = Calendar.getInstance().getTime();
		try {
			if (StringUtils.isEmpty(id))
				throw new ParameterException("param id null or empty");
			if (null == principal)
				throw new ParameterException("param prinicipal null");

			TCkRecordDate entity = dao.find(id);
			if (null == entity)
				throw new EntityNotFoundException("id: " + id);

			this.updateEntityStatus(entity, 'I');
			this.updateEntity(ACTION.MODIFY, entity, principal, now);

			CkRecordDate dto = dtoFromEntity(entity);
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
	public List<CkRecordDate> filterBy(EntityFilterRequest filterRequest)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected TCkRecordDate initEnity(TCkRecordDate entity) throws ParameterException, ProcessingException {
		return entity;
	}

	@Override
	protected TCkRecordDate entityFromDTO(CkRecordDate dto) throws ParameterException, ProcessingException {
		log.debug("entityFromDTO");

		try {
			if (null == dto)
				throw new ParameterException("dto dto null");

			TCkRecordDate entity = new TCkRecordDate();
			entity = dto.toEntity(entity);
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
	protected CkRecordDate dtoFromEntity(TCkRecordDate entity) throws ParameterException, ProcessingException {
		log.debug("dtoFromEntity");

		try {
			if (null == entity)
				throw new ParameterException("param entity null");

			CkRecordDate dto = new CkRecordDate(entity);

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
	protected String entityKeyFromDTO(CkRecordDate dto) throws ParameterException, ProcessingException {
		log.debug("entityKeyFromDTO");

		try {
			if (null == dto)
				throw new ParameterException("dto param null");

			return dto.getRcdId();
		} catch (ParameterException ex) {
			log.error("entityKeyFromDTO", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("entityKeyFromDTO", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected TCkRecordDate updateEntity(ACTION attriubte, TCkRecordDate entity, Principal principal, Date date)
			throws ParameterException, ProcessingException {
		return entity;
	}

	@Override
	protected TCkRecordDate updateEntityStatus(TCkRecordDate entity, char status)
			throws ParameterException, ProcessingException {
		return entity;
	}

	@Override
	protected CkRecordDate preSaveUpdateDTO(TCkRecordDate storedEntity, CkRecordDate dto)
			throws ParameterException, ProcessingException {
		return dto;
	}

	@Override
	protected void preSaveValidation(CkRecordDate dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected ServiceStatus preUpdateValidation(CkRecordDate dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getWhereClause(CkRecordDate dto, boolean wherePrinted)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected HashMap<String, Object> getParameters(CkRecordDate dto) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CkRecordDate whereDto(EntityFilterRequest filterRequest) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CoreMstLocale getCoreMstLocale(CkRecordDate dto)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CkRecordDate setCoreMstLocale(CoreMstLocale coreMstLocale, CkRecordDate dto)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
