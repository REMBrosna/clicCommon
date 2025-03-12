package com.guudint.clickargo.admin.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.guudint.clickargo.common.AbstractClickCargoEntityService;
import com.guudint.clickargo.common.RecordStatus;
import com.guudint.clickargo.master.enums.AccountTypes;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.common.controller.entity.EntityFilterRequest;
import com.vcc.camelone.common.controller.entity.EntityWhere;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.exception.ValidationException;
import com.vcc.camelone.common.service.ServiceStatus;
import com.vcc.camelone.config.dto.CoreSysparam;
import com.vcc.camelone.config.model.TCoreSysparam;
import com.vcc.camelone.locale.dto.CoreMstLocale;

public class CkSysParamEntityService extends AbstractClickCargoEntityService<TCoreSysparam, String, CoreSysparam> {

	// Static Attributes
	////////////////////
	private static Logger LOG = Logger.getLogger(CkSysParamEntityService.class);
	private static String AUDIT_TAG = "CORE SYS PARAM";
	private static String TABLE_NAME = "T_CORE_SYSPARAM";

	public CkSysParamEntityService() {
		super("coreSysparamDao", AUDIT_TAG, TCoreSysparam.class.getName(), TABLE_NAME);
	}

	@Override
	public CoreSysparam newObj(Principal principal)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CoreSysparam findById(String id) throws ParameterException, EntityNotFoundException, ProcessingException {
		LOG.debug("findById");

		try {
			if (StringUtils.isEmpty(id))
				throw new ParameterException("param id null or empty");

			TCoreSysparam entity = dao.find(id);
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
	public CoreSysparam deleteById(String id, Principal principal)
			throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException {
		LOG.debug("deleteById");

		Date now = Calendar.getInstance().getTime();
		try {
			if (StringUtils.isEmpty(id))
				throw new ParameterException("param id null or empty");
			if (null == principal)
				throw new ParameterException("param prinicipal null");

			String[] idParts = id.split(":");
			if (idParts.length != 2)
				throw new ParameterException("id not formulated " + id);

			TCoreSysparam entity = dao.find(id);
			if (null == entity)
				throw new EntityNotFoundException("id: " + id);

			this.updateEntityStatus(entity, RecordStatus.INACTIVE.getCode());
			this.updateEntity(ACTION.MODIFY, entity, principal, now);

			CoreSysparam dto = dtoFromEntity(entity);
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
	public CoreSysparam update(CoreSysparam dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException {
		if (principal == null)
			throw new ProcessingException("principal is null");

		CoreAccn accn = principal.getCoreAccn();
		// Only restrict for SP
		if (!accn.getTMstAccnType().getAtypId().equals(AccountTypes.ACC_TYPE_SP.name()))
			throw new ProcessingException("principal not allowed to access this api");

		return super.update(dto, principal);
	}

	@Override
	public List<CoreSysparam> filterBy(EntityFilterRequest filterRequest)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		LOG.debug("filterBy");

		try {
			if (null == filterRequest)
				throw new ParameterException("param filterRequest null");

			Principal principal = principalUtilService.getPrincipal();
			if (principal == null)
				throw new ProcessingException("principal is null");

			CoreAccn accn = principal.getCoreAccn();
			// Only restrict for SP
			if (!accn.getTMstAccnType().getAtypId().equals(AccountTypes.ACC_TYPE_SP.name()))
				throw new ProcessingException("principal not allowed to access this api");

			CoreSysparam dto = this.whereDto(filterRequest);
			if (null == dto)
				throw new ProcessingException("whereDto null");

			filterRequest.setTotalRecords(super.countByAnd(dto));
			String selectClause = "FROM TCoreSysparam o ";
			String orderByClauses = filterRequest.getOrderBy().toString();
			List<TCoreSysparam> entities = this.findEntitiesByAnd(dto, selectClause, orderByClauses,
					filterRequest.getDisplayLength(), filterRequest.getDisplayStart());
			List<CoreSysparam> dtos = entities.stream().map(x -> {
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
	protected void initBusinessValidator() {
		// TODO Auto-generated method stub

	}

	@Override
	protected Logger getLogger() {
		// TODO Auto-generated method stub
		return LOG;
	}

	@Override
	protected TCoreSysparam initEnity(TCoreSysparam entity) throws ParameterException, ProcessingException {
		return entity;
	}

	@Override
	protected TCoreSysparam entityFromDTO(CoreSysparam dto) throws ParameterException, ProcessingException {
		LOG.debug("entityFromDTO");

		try {
			if (null == dto)
				throw new ParameterException("dto dto null");

			TCoreSysparam entity = new TCoreSysparam();
			entity = dto.toEntity(entity);

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
	protected CoreSysparam dtoFromEntity(TCoreSysparam entity) throws ParameterException, ProcessingException {
		LOG.debug("dtoFromEntity");

		try {
			if (null == entity)
				throw new ParameterException("param entity null");

			CoreSysparam dto = new CoreSysparam(entity);

			return dto;
		} catch (ParameterException ex) {
			LOG.error("dtoFromEntity", ex);
			throw ex;
		} catch (Exception ex) {
			LOG.error("dtoFromEntity", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected String entityKeyFromDTO(CoreSysparam dto) throws ParameterException, ProcessingException {
		LOG.debug("entityKeyFromDTO");

		try {
			if (null == dto)
				throw new ParameterException("dto param null");

			return null == dto.getSysKey() ? null : dto.getSysKey();
		} catch (ParameterException ex) {
			LOG.error("entityKeyFromDTO", ex);
			throw ex;
		} catch (Exception ex) {
			LOG.error("entityKeyFromDTO", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected TCoreSysparam updateEntity(ACTION attriubte, TCoreSysparam entity, Principal principal, Date date)
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
				entity.setSysUidCreate(opUserId.isPresent() ? opUserId.get() : "SYS");
				entity.setSysDtCreate(date);
				entity.setSysUidLupd(opUserId.isPresent() ? opUserId.get() : "SYS");
				entity.setSysDtLupd(date);
				break;

			case MODIFY:
				entity.setSysUidLupd(opUserId.isPresent() ? opUserId.get() : "SYS");
				entity.setSysDtLupd(date);
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
	protected TCoreSysparam updateEntityStatus(TCoreSysparam entity, char status)
			throws ParameterException, ProcessingException {
		LOG.debug("updateEntityStatus");

		try {
			if (null == entity)
				throw new ParameterException("entity param null");

			entity.setSysStatus(status);
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
	protected CoreSysparam preSaveUpdateDTO(TCoreSysparam storedEntity, CoreSysparam dto)
			throws ParameterException, ProcessingException {
		LOG.debug("preSaveUpdateDTO");

		try {
			if (null == storedEntity)
				throw new ParameterException("param storedEntity null");
			if (null == dto)
				throw new ParameterException("param dto null");

			dto.setSysUidCreate(storedEntity.getSysUidCreate());
			dto.setSysDtCreate(storedEntity.getSysDtCreate());

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
	protected void preSaveValidation(CoreSysparam dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub

	}

	@Override
	protected ServiceStatus preUpdateValidation(CoreSysparam dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getWhereClause(CoreSysparam dto, boolean wherePrinted)
			throws ParameterException, ProcessingException {
		LOG.debug("getWhereClause");

		try {
			if (null == dto)
				throw new ParameterException("param dto null");

			StringBuffer searchStatement = new StringBuffer();

			if (Character.isAlphabetic(dto.getSysStatus())) {
				searchStatement.append(getOperator(wherePrinted) + "o.sysStatus=:sysStatus");
				wherePrinted = true;
			}

			if (StringUtils.isNotBlank(dto.getSysKey())) {
				searchStatement.append(getOperator(wherePrinted)).append("o.sysKey LIKE :sysKey");
				wherePrinted = true;
			}

			if (StringUtils.isNotBlank(dto.getSysVal())) {
				searchStatement.append(getOperator(wherePrinted)).append("o.sysVal LIKE :sysVal");
				wherePrinted = true;
			}

			if (StringUtils.isNotBlank(dto.getSysDesc())) {
				searchStatement.append(getOperator(wherePrinted)).append("o.sysDesc LIKE :sysDesc");
				wherePrinted = true;
			}

			Optional<Date> opDtCreate = Optional.ofNullable(dto.getSysDtCreate());
			if (opDtCreate.isPresent() && null != opDtCreate.get()) {
				searchStatement
						.append(getOperator(wherePrinted) + "DATE_FORMAT(o.sysDtCreate,'%d/%m/%Y') = :sysDtCreate");
				wherePrinted = true;
			}

			Optional<Date> opDtLupd = Optional.ofNullable(dto.getSysDtLupd());
			if (opDtLupd.isPresent() && null != opDtLupd.get()) {
				searchStatement.append(getOperator(wherePrinted) + "DATE_FORMAT(o.sysDtLupd,'%d/%m/%Y') = :sysDtLupd");
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
	protected HashMap<String, Object> getParameters(CoreSysparam dto) throws ParameterException, ProcessingException {
		LOG.debug("getParameters");

		SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");

		try {
			if (null == dto)
				throw new ParameterException("param dto null");

			HashMap<String, Object> parameters = new HashMap<String, Object>();

			if (Character.isAlphabetic(dto.getSysStatus()))
				parameters.put("sysStatus", dto.getSysStatus());

			if (StringUtils.isNotBlank(dto.getSysKey()))
				parameters.put("sysKey", "%" + dto.getSysKey() + "%");

			if (StringUtils.isNotBlank(dto.getSysVal()))
				parameters.put("sysVal", "%" + dto.getSysVal() + "%");

			if (StringUtils.isNotBlank(dto.getSysDesc()))
				parameters.put("sysDesc", "%" + dto.getSysDesc() + "%");

			Optional<Date> opDtCreate = Optional.ofNullable(dto.getSysDtCreate());
			if (opDtCreate.isPresent() && null != opDtCreate.get())
				parameters.put("sysDtCreate", sdfDate.format(dto.getSysDtCreate()));

			Optional<Date> opDtLupd = Optional.ofNullable(dto.getSysDtLupd());
			if (opDtLupd.isPresent() && null != opDtLupd.get())
				parameters.put("sysDtLupd", sdfDate.format(dto.getSysDtLupd()));

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
	protected CoreSysparam whereDto(EntityFilterRequest filterRequest) throws ParameterException, ProcessingException {
		LOG.debug("whereDto");

		try {
			if (null == filterRequest)
				throw new ParameterException("param filterRequest null");

			SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");

			CoreSysparam dto = new CoreSysparam();

			for (EntityWhere entityWhere : filterRequest.getWhereList()) {
				Optional<String> opValue = Optional.ofNullable(entityWhere.getValue());
				if (!opValue.isPresent())
					continue;

				if (entityWhere.getAttribute().equalsIgnoreCase("sysKey"))
					dto.setSysKey(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("sysVal"))
					dto.setSysVal(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("sysDesc"))
					dto.setSysDesc(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("sysDtCreate"))
					dto.setSysDtCreate(sdfDate.parse(opValue.get()));
				if (entityWhere.getAttribute().equalsIgnoreCase("sysDtLupd"))
					dto.setSysDtLupd(sdfDate.parse(opValue.get()));
			}

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
	protected CoreMstLocale getCoreMstLocale(CoreSysparam dto)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CoreSysparam setCoreMstLocale(CoreMstLocale coreMstLocale, CoreSysparam dto)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

}
