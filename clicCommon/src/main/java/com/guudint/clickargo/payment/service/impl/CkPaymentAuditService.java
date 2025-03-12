package com.guudint.clickargo.payment.service.impl;

import java.text.SimpleDateFormat;
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

import com.guudint.clickargo.master.dto.CkMstPaymentAuditType;
import com.guudint.clickargo.master.model.TCkMstPaymentAuditType;
import com.guudint.clickargo.payment.dto.CkPaymentAudit;
import com.guudint.clickargo.payment.model.TCkPaymentAudit;
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

public class CkPaymentAuditService extends AbstractEntityService<TCkPaymentAudit, String, CkPaymentAudit> {

	// Static Attributes
	////////////////////
	private static Logger log = Logger.getLogger(CkPaymentAuditService.class);
	private static String auditTag = "PAYMENT AUDIT";
	private static String tableName = "T_CK_PAYMENT_AUDIT";

	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	public CkPaymentAuditService() {
		super("ckPaymentAuditDao", auditTag, TCkPaymentAudit.class.getName(), tableName);
	}

	@Override
	public CkPaymentAudit findById(String id) throws ParameterException, EntityNotFoundException, ProcessingException {
		log.debug("findById");

		try {
			if (StringUtils.isEmpty(id))
				throw new ParameterException("param id null or empty");

			TCkPaymentAudit entity = dao.find(id);
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
	public CkPaymentAudit deleteById(String id, Principal principal)
			throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public List<CkPaymentAudit> filterBy(EntityFilterRequest filterRequest)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		log.debug("filterBy");

		try {
			if (null == filterRequest)
				throw new ParameterException("param filterRequest null");

			CkPaymentAudit dto = this.whereDto(filterRequest);
			if (null == dto)
				throw new ProcessingException("whereDto null");

			filterRequest.setTotalRecords(super.countByAnd(dto));

			String selectClause = "from TCkPaymentAudit o ";
			String orderByClause = filterRequest.getOrderBy().toString();
			List<TCkPaymentAudit> entities = super.findEntitiesByAnd(dto, selectClause, orderByClause,
					filterRequest.getDisplayLength(), filterRequest.getDisplayStart());
			List<CkPaymentAudit> dtos = entities.stream().map(x -> {
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
	protected TCkPaymentAudit initEnity(TCkPaymentAudit entity) throws ParameterException, ProcessingException {
		if (entity != null) {
			Hibernate.initialize(entity.getTCkMstPaymentAuditType());
		}

		return entity;
	}

	@Override
	protected TCkPaymentAudit entityFromDTO(CkPaymentAudit dto) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		log.debug("entityFromDTO");
		try {
			if (null == dto)
				throw new ParameterException("dto param null");

			TCkPaymentAudit entity = new TCkPaymentAudit();
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
	protected CkPaymentAudit dtoFromEntity(TCkPaymentAudit entity) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		log.debug("dtoFromEntity");
		try {
			if (null == entity)
				throw new ParameterException("param entity null");

			CkPaymentAudit dto = new CkPaymentAudit(entity);
			Optional<TCkMstPaymentAuditType> opCkMstPaymentAuditType = Optional
					.ofNullable(entity.getTCkMstPaymentAuditType());
			if (opCkMstPaymentAuditType.isPresent()) {
				dto.setTCkMstPaymentAuditType(new CkMstPaymentAuditType(opCkMstPaymentAuditType.get()));
			}

			return dto;
		} catch (ParameterException ex) {
			log.error("dtoFromEntity", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("dtoFromEntity", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected String entityKeyFromDTO(CkPaymentAudit dto) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		log.debug("entityKeyFromDTO");

		try {
			if (null == dto)
				throw new ParameterException("dto param null");

			return dto.getPyaId();
		} catch (ParameterException ex) {
			log.error("entityKeyFromDTO", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("entityKeyFromDTO", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected TCkPaymentAudit updateEntity(ACTION attriubte, TCkPaymentAudit entity, Principal principal, Date date)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		log.debug("updateEntity");

		try {
			if (null == entity)
				throw new ParameterException("param entity null");
			if (null == principal)
				throw new ParameterException("param principal null");
			if (null == date)
				throw new ParameterException("param date null");

			Optional<String> opUserId = Optional.ofNullable(principal.getUserId());

			entity.setPyaUidCreate(opUserId.isPresent() ? opUserId.get() : "SYS");
			entity.setPyaDtCreate(date);

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
	protected TCkPaymentAudit updateEntityStatus(TCkPaymentAudit entity, char status)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		log.debug("updateEntityStatus");

		try {
			if (null == entity)
				throw new ParameterException("param entity null");

			entity.setPyaStatus(status);
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
	protected CkPaymentAudit preSaveUpdateDTO(TCkPaymentAudit storedEntity, CkPaymentAudit dto)
			throws ParameterException, ProcessingException {
		log.debug("preSaveUpdateDTO");
		try {
			if (null == storedEntity)
				throw new ParameterException("param storedEntity null");
			if (null == dto)
				throw new ParameterException("param dto null");

			dto.setPyaUidCreate(storedEntity.getPyaUidCreate());
			dto.setPyaDtCreate(storedEntity.getPyaDtCreate());

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
	protected void preSaveValidation(CkPaymentAudit dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub

	}

	@Override
	protected ServiceStatus preUpdateValidation(CkPaymentAudit dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getWhereClause(CkPaymentAudit dto, boolean wherePrinted)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		log.debug("getWhereClause");

		try {
			if (null == dto)
				throw new ParameterException("param dto null");

			StringBuffer searchStatement = new StringBuffer();
			if (!StringUtils.isEmpty(dto.getPyaId())) {
				searchStatement.append(getOperator(wherePrinted) + "o.pyaId LIKE :pyaId");
				wherePrinted = true;
			}
			Optional<CkMstPaymentAuditType> opAuditType = Optional.ofNullable(dto.getTCkMstPaymentAuditType());
			if (opAuditType.isPresent()) {
				if (StringUtils.isNotBlank(opAuditType.get().getPatyId())) {
					searchStatement.append(getOperator(wherePrinted) + "o.TCkMstPaymentAuditType.patyId LIKE :patyId");
					wherePrinted = true;
				}

				if (StringUtils.isNotBlank(opAuditType.get().getPytName())) {
					searchStatement
							.append(getOperator(wherePrinted) + "o.TCkMstPaymentAuditType.pytName LIKE :pytName");
					wherePrinted = true;
				}
			}

			if (!StringUtils.isEmpty(dto.getPyaReference())) {
				searchStatement.append(getOperator(wherePrinted) + "o.pyaReference LIKE :pyaReference");
				wherePrinted = true;
			}
			if (!StringUtils.isEmpty(dto.getPyaReq())) {
				searchStatement.append(getOperator(wherePrinted) + "o.pyaReq LIKE :pyaReq");
				wherePrinted = true;
			}
			if (!StringUtils.isEmpty(dto.getPyaResp())) {
				searchStatement.append(getOperator(wherePrinted) + "o.pyaResp = :pyaResp");
				wherePrinted = true;
			}

			if (!StringUtils.isEmpty(dto.getPyaCb())) {
				searchStatement.append(getOperator(wherePrinted) + "o.pyaCb LIKE :pyaCb");
				wherePrinted = true;
			}
			if (!StringUtils.isEmpty(dto.getPyaRemark())) {
				searchStatement.append(getOperator(wherePrinted) + "o.pyaRemark LIKE :pyaRemark");
				wherePrinted = true;
			}
			if (!StringUtils.isEmpty(dto.getPyaState())) {
				searchStatement.append(getOperator(wherePrinted) + "o.pyaState LIKE :pyaState");
				wherePrinted = true;
			}

			if (dto.getPyaStatus() != null && Character.isAlphabetic(dto.getPyaStatus())) {
				searchStatement.append(getOperator(wherePrinted) + "o.pyaStatus = :pyaStatus");
				wherePrinted = true;
			}

			if (dto.getPyaDtCreate() != null) {
				searchStatement.append(
						getOperator(wherePrinted) + "DATE_FORMAT(o.pyaDtCreate,'%d/%m/%Y %H:%i') = :pyaDtCreate");
				wherePrinted = true;
			}

			if (dto.getPyaDtLupd() != null) {
				searchStatement
						.append(getOperator(wherePrinted) + "DATE_FORMAT(o.pyaDtLupd,'%d/%m/%Y %H:%i') = :pyaDtLupd");
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
	protected HashMap<String, Object> getParameters(CkPaymentAudit dto) throws ParameterException, ProcessingException {
		log.debug("getParameters");

		try {
			if (null == dto)
				throw new ParameterException("param dto null");

			HashMap<String, Object> parameters = new HashMap<String, Object>();

			if (!StringUtils.isEmpty(dto.getPyaId()))
				parameters.put("pyaId", "%" + dto.getPyaId() + "%");

			Optional<CkMstPaymentAuditType> opAuditType = Optional.ofNullable(dto.getTCkMstPaymentAuditType());
			if (opAuditType.isPresent()) {
				if (StringUtils.isNotBlank(opAuditType.get().getPatyId()))
					parameters.put("patyId", "%" + opAuditType.get().getPatyId() + "%");

				if (StringUtils.isNotBlank(opAuditType.get().getPytName()))
					parameters.put("pytName", "%" + opAuditType.get().getPytName() + "%");
			}

			if (!StringUtils.isEmpty(dto.getPyaReference()))
				parameters.put("pyaReference", "%" + dto.getPyaReference() + "%");

			if (!StringUtils.isEmpty(dto.getPyaReq()))
				parameters.put("pyaReq", "%" + dto.getPyaReq() + "%");

			if (!StringUtils.isEmpty(dto.getPyaResp()))
				parameters.put("pyaResp", "%" + dto.getPyaResp() + "%");

			if (!StringUtils.isEmpty(dto.getPyaCb()))
				parameters.put("pyaCb", "%" + dto.getPyaCb() + "%");

			if (!StringUtils.isEmpty(dto.getPyaRemark()))
				parameters.put("pyaRemark", "%" + dto.getPyaRemark() + "%");

			if (!StringUtils.isEmpty(dto.getPyaState()))
				parameters.put("pyaState", "%" + dto.getPyaState() + "%");

			if (dto.getPyaStatus() != null && Character.isAlphabetic(dto.getPyaStatus()))
				parameters.put("pyaStatus", dto.getPyaStatus());

			if (dto.getPyaDtCreate() != null)
				parameters.put("pyaDtCreate", sdf.format(dto.getPyaDtCreate()));

			if (dto.getPyaDtLupd() != null)
				parameters.put("pyaDtLupd", sdf.format(dto.getPyaDtLupd()));

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
	protected CkPaymentAudit whereDto(EntityFilterRequest filterRequest)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		log.debug("whereDto");

		try {
			if (null == filterRequest)
				throw new ParameterException("param filterRequest null");

			CkPaymentAudit dto = new CkPaymentAudit();
			CkMstPaymentAuditType auditTypeDto = new CkMstPaymentAuditType();
			for (EntityWhere entityWhere : filterRequest.getWhereList()) {
				Optional<String> opValue = Optional.ofNullable(entityWhere.getValue());
				if (!opValue.isPresent())
					continue;

				if (entityWhere.getAttribute().equalsIgnoreCase("pyaId"))
					dto.setPyaId(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("TCkMstPaymentAuditType.patyId"))
					auditTypeDto.setPatyId(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("TCkMstPaymentAuditType.pytName"))
					auditTypeDto.setPytName(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("pyaReference"))
					dto.setPyaReference(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("pyaReq"))
					dto.setPyaReq(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("pyaResp"))
					dto.setPyaResp(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("pyaCb"))
					dto.setPyaCb(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("pyaRemark"))
					dto.setPyaRemark(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("pyaStatus"))
					dto.setPyaStatus(opValue.get().charAt(0));
				if (entityWhere.getAttribute().equalsIgnoreCase("pyaDtCreate"))
					dto.setPyaDtCreate(sdf.parse(opValue.get()));

				if (entityWhere.getAttribute().equalsIgnoreCase("pyaDtLupd"))
					dto.setPyaDtLupd(sdf.parse(opValue.get()));

			}

			dto.setTCkMstPaymentAuditType(auditTypeDto);
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
	protected CoreMstLocale getCoreMstLocale(CkPaymentAudit dto)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CkPaymentAudit setCoreMstLocale(CoreMstLocale coreMstLocale, CkPaymentAudit dto)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

}
