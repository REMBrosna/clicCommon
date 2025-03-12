package com.guudint.clickargo.master.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guudint.clickargo.master.dto.CkMstShipmentType;
import com.guudint.clickargo.master.model.TCkMstShipmentType;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.common.controller.entity.EntityFilterRequest;
import com.vcc.camelone.common.controller.entity.EntityWhere;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.exception.ValidationException;
import com.vcc.camelone.common.service.ServiceStatus;
import com.vcc.camelone.common.service.entity.AbstractEntityService;
import com.vcc.camelone.locale.dto.CoreMstLocale;

public class CkMstShipmentTypeService extends AbstractEntityService<TCkMstShipmentType, String, CkMstShipmentType>{

	// Static Attributes
	////////////////////
	private static Logger LOG = Logger.getLogger(CkMstShipmentTypeService.class);
	private static String auditType = "CK_MST_SHIPMENT_TYPE";
	private static String tableName = "T_CK_MST_SHIPMENT_TYPE";

	public CkMstShipmentTypeService() {
		super("mstShipmentTypeDao", auditType, TCkMstShipmentType.class.getName(), tableName);
	}

	@Override
	public CkMstShipmentType deleteById(String arg0, Principal arg1)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CkMstShipmentType> filterBy(EntityFilterRequest filterRequest)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		LOG.debug("filterBy");

		try {
			if (null == filterRequest)
				throw new ParameterException("param filterRequest null");

			CkMstShipmentType dto = this.whereDto(filterRequest);
			if (null == dto)
				throw new ProcessingException("whereDto null");

			filterRequest.setTotalRecords(super.countByAnd(dto));

			String selectClause = "from TCkMstShipmentType o ";
			String orderByClause = filterRequest.getOrderBy().toString();
			List<TCkMstShipmentType> entities = super.findEntitiesByAnd(dto, selectClause, orderByClause,
					filterRequest.getDisplayLength(), filterRequest.getDisplayStart());

			List<CkMstShipmentType> dtos = entities.stream().map(x -> {
				try {
					return dtoFromEntity(x);
				} catch (ParameterException e) {
					LOG.error("filterBy", e);
				} catch (ProcessingException e) {
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
	public CkMstShipmentType findById(String id) throws ParameterException, EntityNotFoundException, ProcessingException {
		LOG.debug("findById");

		try {
			if (StringUtils.isEmpty(id))
				throw new ParameterException("param id null or empty");

			TCkMstShipmentType entity = dao.find(id);
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
	protected CkMstShipmentType dtoFromEntity(TCkMstShipmentType entity) throws ParameterException, ProcessingException {
		try {
			if (null == entity)
				throw new ParameterException("param entity null");

			CkMstShipmentType dto = new CkMstShipmentType(entity);
			
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
	protected TCkMstShipmentType entityFromDTO(CkMstShipmentType dto) throws ParameterException, ProcessingException {
		LOG.debug("entityFromDTO");

		try {
			if (null == dto)
				throw new ParameterException("param dto null");

			TCkMstShipmentType entity = new TCkMstShipmentType();
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
	protected String entityKeyFromDTO(CkMstShipmentType dto) throws ParameterException, ProcessingException {
		LOG.debug("entityKeyFromDTO");

		try {
			if (null == dto)
				throw new ParameterException("dto param null");

			return dto.getShtId();
		} catch (ParameterException ex) {
			LOG.error("entityKeyFromDTO", ex);
			throw ex;
		} catch (Exception ex) {
			LOG.error("entityKeyFromDTO", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected CoreMstLocale getCoreMstLocale(CkMstShipmentType arg0)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected HashMap<String, Object> getParameters(CkMstShipmentType dto)
			throws ParameterException, ProcessingException {
		LOG.debug("getParameters");

		SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");

		try {
			if (null == dto)
				throw new ParameterException("param dto null");

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			if (!StringUtils.isEmpty(dto.getShtId()))
				parameters.put("shtId", "%" + dto.getShtId() + "%");
			Optional<String> opShtName = Optional.ofNullable(dto.getShtName());
			if (opShtName.isPresent())
				parameters.put("shtName", "%" + opShtName.get() + "%");
			Optional<String> opShtDesc = Optional.ofNullable(dto.getShtDesc());
			if (opShtDesc.isPresent())
				parameters.put("shtDesc", "%" + opShtDesc.get() + "%");
			Optional<String> opShtDescOth = Optional.ofNullable(dto.getShtDescOth());
			if (opShtDescOth.isPresent())
				parameters.put("shtDescOth", "%" + opShtDescOth.get() + "%");
			Optional<Date> opShtDtCreate = Optional.ofNullable(dto.getShtDtCreate());
			if (opShtDtCreate.isPresent() && opShtDtCreate.get() != null)
				parameters.put("shtDtCreate", sdfDate.format(opShtDtCreate.get()));
			Optional<Date> opShtDtLupd = Optional.ofNullable(dto.getShtDtLupd());
			if (opShtDtLupd.isPresent() && opShtDtLupd.get() != null)
				parameters.put("shtDtLupd", sdfDate.format(opShtDtLupd.get()));
			if (Objects.nonNull(dto.getShtStatus()))
				parameters.put("shtStatus", dto.getShtStatus());

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
	protected String getWhereClause(CkMstShipmentType dto, boolean wherePrinted)
			throws ParameterException, ProcessingException {
		LOG.debug("getWhereClause");

		try {
			if (null == dto)
				throw new ParameterException("param dto null");

			StringBuffer searchStatement = new StringBuffer();
			if (!StringUtils.isEmpty(dto.getShtId())) {
				searchStatement.append(getOperator(wherePrinted) + "o.shtId LIKE :shtId");
				wherePrinted = true;
			}
			Optional<String> opShtName = Optional.ofNullable(dto.getShtName());
			if (opShtName.isPresent()) {
				searchStatement.append(getOperator(wherePrinted) + "o.shtName LIKE :shtName");
				wherePrinted = true;
			}
			Optional<String> opShtDesc = Optional.ofNullable(dto.getShtDesc());
			if (opShtDesc.isPresent()) {
				searchStatement.append(getOperator(wherePrinted) + "o.shtDesc LIKE :shtDesc");
				wherePrinted = true;
			}
			Optional<String> opShtDescOth = Optional.ofNullable(dto.getShtDescOth());
			if (opShtDescOth.isPresent()) {
				searchStatement.append(getOperator(wherePrinted) + "o.shtDescOth LIKE :shtDescOth");
				wherePrinted = true;
			}
			Optional<Date> opShtDtCreate = Optional.ofNullable(dto.getShtDtCreate());
			if (opShtDtCreate.isPresent()) {
				searchStatement.append(getOperator(wherePrinted) + "DATE_FORMAT(o.shtDtCreate,'%d/%m/%Y') = :shtDtCreate");
				wherePrinted = true;
			}
			Optional<Date> opShtDtLupd = Optional.ofNullable(dto.getShtDtLupd());
			if (opShtDtLupd.isPresent()) {
				searchStatement.append(getOperator(wherePrinted) + "DATE_FORMAT(o.shtDtLupd,'%d/%m/%Y') = :shtDtLupd");
				wherePrinted = true;
			}
			if (Objects.nonNull(dto.getShtStatus())) {
				searchStatement.append(getOperator(wherePrinted) + "o.shtStatus =:shtStatus");
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
	protected TCkMstShipmentType initEnity(TCkMstShipmentType entity) throws ParameterException, ProcessingException {
		return entity;
	}

	@Override
	protected CkMstShipmentType preSaveUpdateDTO(TCkMstShipmentType arg0, CkMstShipmentType arg1)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void preSaveValidation(CkMstShipmentType arg0, Principal arg1)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected ServiceStatus preUpdateValidation(CkMstShipmentType arg0, Principal arg1)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CkMstShipmentType setCoreMstLocale(CoreMstLocale arg0, CkMstShipmentType arg1)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected TCkMstShipmentType updateEntity(ACTION attriubte, TCkMstShipmentType entity, Principal principal, Date date)
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
				entity.setShtUidCreate(opUserId.isPresent() ? opUserId.get() : "SYS");
				entity.setShtDtCreate(date);
				entity.setShtUidLupd(opUserId.isPresent() ? opUserId.get() : "SYS");
				entity.setShtDtLupd(date);

				break;

			case MODIFY:
				entity.setShtUidLupd(opUserId.isPresent() ? opUserId.get() : "SYS");
				entity.setShtDtLupd(date);
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
	protected TCkMstShipmentType updateEntityStatus(TCkMstShipmentType entity, char status)
			throws ParameterException, ProcessingException {
		LOG.debug("updateEntityStatus");

		try {
			if (null == entity)
				throw new ParameterException("entity param null");

			entity.setShtStatus(status);
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
	protected CkMstShipmentType whereDto(EntityFilterRequest filterRequest)
			throws ParameterException, ProcessingException {
		LOG.debug("whereDto");

		try {
			if (null == filterRequest)
				throw new ParameterException("param filterRequest null");

			SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");

			CkMstShipmentType dto = new CkMstShipmentType();

			for (EntityWhere entityWhere : filterRequest.getWhereList()) {
				Optional<String> opValue = Optional.ofNullable(entityWhere.getValue());
				if (!opValue.isPresent())
					continue;

				if (entityWhere.getAttribute().equalsIgnoreCase("shtId"))
					dto.setShtId(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("shtName"))
					dto.setShtName(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("shtDesc"))
					dto.setShtDesc(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("shtDescOth"))
					dto.setShtDescOth(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("shtDtCreate"))
					dto.setShtDtCreate(sdfDate.parse(opValue.get()));
				if (entityWhere.getAttribute().equalsIgnoreCase("shtDtLupd"))
					dto.setShtDtLupd(sdfDate.parse(opValue.get()));
				if (entityWhere.getAttribute().equalsIgnoreCase("shtStatus"))
					dto.setShtStatus(opValue.get().charAt(0));
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
	public Object addObj(Object object, Principal principal)
			throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException {
		try {
			return this.add((CkMstShipmentType) object, principal);
		} catch (ParameterException | ValidationException | ProcessingException ex) {
			throw ex;

		} catch (Exception ex) {
			throw new ProcessingException(ex);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CkMstShipmentType add(CkMstShipmentType dto, Principal principal) throws ParameterException, ValidationException, ProcessingException, EntityNotFoundException {
		LOG.debug("add");

		if (null == dto)
			throw new ParameterException("param dto null");
		if (null == principal)
			throw new ParameterException("principal null");

		CkMstShipmentType ckMstShipmentType;
		try {
			ckMstShipmentType = this.findById(dto.getShtId());
			if (ckMstShipmentType != null) {
				Map<String, String> errors = new HashMap<>();
				errors.put("shtId", "Ship Type ID " + ckMstShipmentType.getShtId() + " already exist!");
				String json;
				try {
					json = (new ObjectMapper()).writeValueAsString(errors);
				} catch (JsonProcessingException e) {
					throw new ProcessingException(e);
				}
				throw new ValidationException(json);
			}
		} catch (EntityNotFoundException e1) {
			ckMstShipmentType = dto;
		}
		
		return super.add(ckMstShipmentType, principal);

	}
}
