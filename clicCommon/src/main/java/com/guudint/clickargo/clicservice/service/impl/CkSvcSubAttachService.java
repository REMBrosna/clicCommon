package com.guudint.clickargo.clicservice.service.impl;

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

import com.guudint.clickargo.clicservice.dto.CkSvcSub;
import com.guudint.clickargo.clicservice.dto.CkSvcSubAttach;
import com.guudint.clickargo.clicservice.model.TCkSvcSub;
import com.guudint.clickargo.clicservice.model.TCkSvcSubAttach;
import com.guudint.clickargo.common.RecordStatus;
import com.guudint.clickargo.master.dto.CkMstServiceType;
import com.guudint.clickargo.master.dto.CkMstSvcSubState;
import com.guudint.clickargo.master.model.TCkMstServiceType;
import com.guudint.clickargo.master.model.TCkMstSvcSubState;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.ccm.model.TCoreAccn;
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

public class CkSvcSubAttachService extends AbstractEntityService<TCkSvcSubAttach, String, CkSvcSubAttach> {

	// Static Attributes
	////////////////////
	private static Logger log = Logger.getLogger(AccountTypeService.class);
	private static String auditTag = "SERVICE SUBSCRIPTION ATTACH";
	private static String tableName = "T_CK_SVC_SUB_ATTACH";

	// Constructor
	//////////////
	public CkSvcSubAttachService() {
		super("ckSvcSubAttachDao", auditTag, TCkSvcSubAttach.class.getName(), tableName);
		// TODO Auto-generated constructor stub
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CkSvcSubAttach findById(String id) throws ParameterException, EntityNotFoundException, ProcessingException {
		log.debug("findById");

		try {
			if (StringUtils.isEmpty(id))
				throw new ParameterException("param id null or empty");

			TCkSvcSubAttach entity = dao.find(id);
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
	public CkSvcSubAttach deleteById(String id, Principal principal)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		log.debug("deleteById");

		Date now = Calendar.getInstance().getTime();
		try {
			if (StringUtils.isEmpty(id))
				throw new ParameterException("param id null or empty");
			if (null == principal)
				throw new ParameterException("param prinicipal null");

			TCkSvcSubAttach entity = dao.find(id);
			if (null == entity)
				throw new EntityNotFoundException("id: " + id);

			this.updateEntityStatus(entity, RecordStatus.INACTIVE.getCode());
			this.updateEntity(ACTION.MODIFY, entity, principal, now);

			CkSvcSubAttach dto = dtoFromEntity(entity);
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
	public List<CkSvcSubAttach> filterBy(EntityFilterRequest filterRequest)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		log.debug("filterBy");

		try {
			if (null == filterRequest)
				throw new ParameterException("param filterRequest null");

			CkSvcSubAttach dto = this.whereDto(filterRequest);
			if (null == dto)
				throw new ProcessingException("whereDto null");

			filterRequest.setTotalRecords(super.countByAnd(dto));

			String selectClause = "from TCkSvcSubAttach o ";
			String orderByClause = filterRequest.getOrderBy().toString();
			List<TCkSvcSubAttach> entities = super.findEntitiesByAnd(dto, selectClause, orderByClause,
					filterRequest.getDisplayLength(), filterRequest.getDisplayStart());
			List<CkSvcSubAttach> dtos = entities.stream().map(x -> {
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
	protected TCkSvcSubAttach initEnity(TCkSvcSubAttach entity) throws ParameterException, ProcessingException {
		if (null != entity) {
			Hibernate.initialize(entity.getTCkSvcSub());
			Hibernate.initialize(entity.getTMstAttType());
		}

		return entity;
	}

	@Override
	protected TCkSvcSubAttach entityFromDTO(CkSvcSubAttach dto) throws ParameterException, ProcessingException {
		try {
			if (null == dto)
				throw new ParameterException("dto dto null");

			TCkSvcSubAttach entity = new TCkSvcSubAttach();
			entity = dto.toEntity(entity);

			Optional<CkSvcSub> opSvcSub = Optional.ofNullable(dto.getTCkSvcSub());
			entity.setTCkSvcSub(opSvcSub.isPresent() ? opSvcSub.get().toEntity(new TCkSvcSub()) : null);
			if (opSvcSub.isPresent()) {
				Optional<CkMstServiceType> opSvcType = Optional.ofNullable(dto.getTCkSvcSub().getTCkMstServiceType());
				Optional<CkMstSvcSubState> opSvcSubState = Optional
						.ofNullable(dto.getTCkSvcSub().getTCkMstSvcSubState());
				Optional<CoreAccn> opAccn = Optional.ofNullable(dto.getTCkSvcSub().getTCoreAccn());

				entity.getTCkSvcSub().setTCkMstServiceType(
						opSvcType.isPresent() ? opSvcType.get().toEntity(new TCkMstServiceType()) : null);

				entity.getTCkSvcSub().setTCkMstSvcSubState(
						opSvcSubState.isPresent() ? opSvcSubState.get().toEntity(new TCkMstSvcSubState()) : null);

				entity.getTCkSvcSub().setTCoreAccn(opAccn.isPresent() ? opAccn.get().toEntity(new TCoreAccn()) : null);
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
	protected CkSvcSubAttach dtoFromEntity(TCkSvcSubAttach entity) throws ParameterException, ProcessingException {
		log.debug("dtoFromEntity");

		try {
			if (null == entity)
				throw new ParameterException("param entity null");

			CkSvcSubAttach dto = new CkSvcSubAttach(entity);

			Optional<TCkSvcSub> opSvcSub = Optional.ofNullable(entity.getTCkSvcSub());

			if (opSvcSub.isPresent()) {
				dto.setTCkSvcSub(new CkSvcSub(opSvcSub.get()));

				Optional<TCkMstServiceType> opMstServiceType = Optional
						.ofNullable(entity.getTCkSvcSub().getTCkMstServiceType());
				dto.getTCkSvcSub().setTCkMstServiceType(new CkMstServiceType(opMstServiceType.get()));

				Optional<TCkMstSvcSubState> opMstSvcSubState = Optional
						.ofNullable(entity.getTCkSvcSub().getTCkMstSvcSubState());
				dto.getTCkSvcSub().setTCkMstSvcSubState(new CkMstSvcSubState(opMstSvcSubState.get()));

				Optional<TCoreAccn> opAccn = Optional.ofNullable(entity.getTCkSvcSub().getTCoreAccn());
				dto.getTCkSvcSub().setTCoreAccn(new CoreAccn(opAccn.get()));

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
	protected String entityKeyFromDTO(CkSvcSubAttach dto) throws ParameterException, ProcessingException {
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
	protected TCkSvcSubAttach updateEntity(ACTION attriubte, TCkSvcSubAttach entity, Principal principal, Date date)
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
			switch (attriubte) {
			case CREATE:
				entity.setAttUidCreate(opUserId.isPresent() ? opUserId.get() : "SYS");
				entity.setAttDtCreate(date);
				entity.setAttDtLupd(date);
				entity.setAttUidLupd(opUserId.isPresent() ? opUserId.get() : "SYS");
				break;

			case MODIFY:
				entity.setAttDtLupd(date);
				entity.setAttUidLupd(opUserId.isPresent() ? opUserId.get() : "SYS");
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
	protected TCkSvcSubAttach updateEntityStatus(TCkSvcSubAttach entity, char status)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
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
	protected CkSvcSubAttach preSaveUpdateDTO(TCkSvcSubAttach storedEntity, CkSvcSubAttach dto)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
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
	protected void preSaveValidation(CkSvcSubAttach dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub

	}

	@Override
	protected ServiceStatus preUpdateValidation(CkSvcSubAttach dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getWhereClause(CkSvcSubAttach dto, boolean wherePrinted)
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

			Optional<CkSvcSub> opCkSvcSub = Optional.ofNullable(dto.getTCkSvcSub());
			if (opCkSvcSub.isPresent()) {
				Optional<CkMstSvcSubState> opCkMstSubState = Optional
						.ofNullable(opCkSvcSub.get().getTCkMstSvcSubState());
				if (opCkMstSubState.isPresent()) {
					if (StringUtils.isNotBlank(opCkMstSubState.get().getSsstId())) {
						searchStatement.append(
								getOperator(wherePrinted) + "o.TCkSvcSub.TCkMstSvcSubState.ssstId = :svcSubState");
						wherePrinted = true;
					}
				}

				Optional<CkMstServiceType> opCkMstSvcType = Optional
						.ofNullable(opCkSvcSub.get().getTCkMstServiceType());
				if (opCkMstSvcType.isPresent()) {
					if (StringUtils.isNotBlank(opCkMstSvcType.get().getSvctId())) {
						searchStatement
								.append(getOperator(wherePrinted) + "o.TCkSvcSub.TCkMstServiceType.svctId = :svcType");
						wherePrinted = true;
					}
				}

				Optional<CoreAccn> opCoreAccn = Optional.ofNullable(opCkSvcSub.get().getTCoreAccn());
				if (opCoreAccn.isPresent()) {
					if (StringUtils.isNotBlank(opCoreAccn.get().getAccnId())) {
						searchStatement
								.append(getOperator(wherePrinted) + "o.TCkSvcSub.TCoreAccn.accnId = :svcSubAccnId");
						wherePrinted = true;
					}

					if (StringUtils.isNotBlank(opCoreAccn.get().getAccnName())) {
						searchStatement.append(
								getOperator(wherePrinted) + "o.TCkSvcSub.TCoreAccn.accnName LIKE :svcSubAccnName");
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
	protected HashMap<String, Object> getParameters(CkSvcSubAttach dto) throws ParameterException, ProcessingException {
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

			Optional<CkSvcSub> opCkSvcSub = Optional.ofNullable(dto.getTCkSvcSub());
			if (opCkSvcSub.isPresent()) {
				Optional<CkMstSvcSubState> opCkMstSubState = Optional
						.ofNullable(opCkSvcSub.get().getTCkMstSvcSubState());
				if (opCkMstSubState.isPresent() && StringUtils.isNotBlank(opCkMstSubState.get().getSsstId()))
					parameters.put("svcSubState", opCkMstSubState.get().getSsstId());

				Optional<CkMstServiceType> opCkMstSvcType = Optional
						.ofNullable(opCkSvcSub.get().getTCkMstServiceType());
				if (opCkMstSvcType.isPresent() && StringUtils.isNotBlank(opCkMstSvcType.get().getSvctId()))
					parameters.put("svcType", opCkMstSubState.get().getSsstId());

				Optional<CoreAccn> opCoreAccn = Optional.ofNullable(opCkSvcSub.get().getTCoreAccn());
				if (opCoreAccn.isPresent()) {
					if (StringUtils.isNotBlank(opCoreAccn.get().getAccnId()))
						parameters.put("svcSubAccnId", "%" + opCoreAccn.get().getAccnId() + "%");

					if (StringUtils.isNotBlank(opCoreAccn.get().getAccnName()))
						parameters.put("svcSubAccnName", "%" + opCoreAccn.get().getAccnName() + "%");
				}

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
	protected CkSvcSubAttach whereDto(EntityFilterRequest filterRequest)
			throws ParameterException, ProcessingException {
		log.debug("whereDto");

		try {
			if (null == filterRequest)
				throw new ParameterException("param filterRequest null");

			CkSvcSubAttach dto = new CkSvcSubAttach();
			CkSvcSub svcSub = new CkSvcSub();
			CkMstSvcSubState ckMstSvcSubState = new CkMstSvcSubState();
			CkMstServiceType ckMstServiceType = new CkMstServiceType();
			MstAttType mstAttType = new MstAttType();
			CoreAccn svcSubAccn = new CoreAccn();

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

				if (entityWhere.getAttribute().equalsIgnoreCase("TCkSvcSub.TCkMstSvcSubState.ssstId"))
					ckMstSvcSubState.setSsstId(opValue.get());

				if (entityWhere.getAttribute().equalsIgnoreCase("TCkSvcSub.TCkMstServiceType.austId"))
					ckMstServiceType.setSvctId(opValue.get());

				if (entityWhere.getAttribute().equalsIgnoreCase("TCkSvcSub.TCoreAccn.accnId"))
					svcSubAccn.setAccnId(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("TCkSvcSub.TCoreAccn.accnName"))
					svcSubAccn.setAccnName(opValue.get());

			}

			svcSub.setTCkMstServiceType(ckMstServiceType);
			svcSub.setTCkMstSvcSubState(ckMstSvcSubState);
			svcSub.setTCoreAccn(svcSubAccn);

			dto.setTMstAttType(mstAttType);
			dto.setTCkSvcSub(svcSub);
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
	protected CoreMstLocale getCoreMstLocale(CkSvcSubAttach dto)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CkSvcSubAttach setCoreMstLocale(CoreMstLocale coreMstLocale, CkSvcSubAttach dto)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

}
