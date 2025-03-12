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

import com.guudint.clickargo.clicservice.dto.CkSvcAuth;
import com.guudint.clickargo.clicservice.dto.CkSvcAuthAttach;
import com.guudint.clickargo.clicservice.model.TCkSvcAuth;
import com.guudint.clickargo.clicservice.model.TCkSvcAuthAttach;
import com.guudint.clickargo.common.RecordStatus;
import com.guudint.clickargo.master.dto.CkMstAuthAccnType;
import com.guudint.clickargo.master.dto.CkMstAuthState;
import com.guudint.clickargo.master.dto.CkMstServiceType;
import com.guudint.clickargo.master.model.TCkMstAuthAccnType;
import com.guudint.clickargo.master.model.TCkMstAuthState;
import com.guudint.clickargo.master.model.TCkMstServiceType;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.ccm.dto.CoreUsr;
import com.vcc.camelone.ccm.model.TCoreAccn;
import com.vcc.camelone.ccm.model.TCoreUsr;
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

public class CkSvcAuthAttachService extends AbstractEntityService<TCkSvcAuthAttach, String, CkSvcAuthAttach> {

	// Static Attributes
	////////////////////
	private static Logger log = Logger.getLogger(AccountTypeService.class);
	private static String auditTag = "SERVICE AUTH ATTACH";
	private static String tableName = "T_CK_SVC_AUTH_ATTACH";

	// Constructor
	//////////////
	public CkSvcAuthAttachService() {
		super("ckSvcAuthAttachDao", auditTag, TCkSvcAuthAttach.class.getName(), tableName);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CkSvcAuthAttach findById(String id) throws ParameterException, EntityNotFoundException, ProcessingException {
		log.debug("findById");

		try {
			if (StringUtils.isEmpty(id))
				throw new ParameterException("param id null or empty");

			TCkSvcAuthAttach entity = dao.find(id);
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
	public CkSvcAuthAttach deleteById(String id, Principal principal)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		log.debug("deleteById");

		Date now = Calendar.getInstance().getTime();
		try {
			if (StringUtils.isEmpty(id))
				throw new ParameterException("param id null or empty");
			if (null == principal)
				throw new ParameterException("param prinicipal null");

			TCkSvcAuthAttach entity = dao.find(id);
			if (null == entity)
				throw new EntityNotFoundException("id: " + id);

			this.updateEntityStatus(entity, RecordStatus.INACTIVE.getCode());
			this.updateEntity(ACTION.MODIFY, entity, principal, now);

			CkSvcAuthAttach dto = dtoFromEntity(entity);
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
	public List<CkSvcAuthAttach> filterBy(EntityFilterRequest filterRequest)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		log.debug("filterBy");

		try {
			if (null == filterRequest)
				throw new ParameterException("param filterRequest null");

			CkSvcAuthAttach dto = this.whereDto(filterRequest);
			if (null == dto)
				throw new ProcessingException("whereDto null");

			filterRequest.setTotalRecords(super.countByAnd(dto));

			String selectClause = "from TCkSvcAuthAttach o ";
			String orderByClause = filterRequest.getOrderBy().toString();
			List<TCkSvcAuthAttach> entities = super.findEntitiesByAnd(dto, selectClause, orderByClause,
					filterRequest.getDisplayLength(), filterRequest.getDisplayStart());
			List<CkSvcAuthAttach> dtos = entities.stream().map(x -> {
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
	protected TCkSvcAuthAttach initEnity(TCkSvcAuthAttach entity) throws ParameterException, ProcessingException {
		if (null != entity) {
			Hibernate.initialize(entity.getTCkMstAuthAccnType());
			Hibernate.initialize(entity.getTCkSvcAuth());
			Hibernate.initialize(entity.getTMstAttType());
		}

		return entity;
	}

	@Override
	protected TCkSvcAuthAttach entityFromDTO(CkSvcAuthAttach dto) throws ParameterException, ProcessingException {
		log.debug("entityFromDTO");

		try {
			if (null == dto)
				throw new ParameterException("dto dto null");

			TCkSvcAuthAttach entity = new TCkSvcAuthAttach();
			entity = dto.toEntity(entity);

			Optional<CkMstAuthAccnType> opMstAuthAccnType = Optional.ofNullable(dto.getTCkMstAuthAccnType());
			entity.setTCkMstAuthAccnType(
					opMstAuthAccnType.isPresent() ? opMstAuthAccnType.get().toEntity(new TCkMstAuthAccnType()) : null);

			Optional<CkSvcAuth> opSvcAuth = Optional.ofNullable(dto.getTCkSvcAuth());
			entity.setTCkSvcAuth(opSvcAuth.isPresent() ? opSvcAuth.get().toEntity(new TCkSvcAuth()) : null);
			if (opSvcAuth.isPresent()) {
				Optional<CkMstAuthState> opMstAuthState = Optional.ofNullable(dto.getTCkSvcAuth().getTCkMstAuthState());
				Optional<CkMstServiceType> opMstSvcType = Optional
						.ofNullable(dto.getTCkSvcAuth().getTCkMstServiceType());
				Optional<CoreAccn> opSvcAuthAccnService = Optional
						.ofNullable(dto.getTCkSvcAuth().getTCoreAccnBySvauAccnService());
				Optional<CoreAccn> opSvcAuthorizedAccn = Optional
						.ofNullable(dto.getTCkSvcAuth().getTCoreAccnBySvauAccnAuthorized());
				Optional<CoreAccn> opSvcAuthorizerAccn = Optional
						.ofNullable(dto.getTCkSvcAuth().getTCoreAccnBySvauAccnAuthorizer());
				Optional<CoreUsr> opSvcAuthorizedUsr = Optional
						.ofNullable(dto.getTCkSvcAuth().getTCoreUsrBySvauUsrAuthorized());
				Optional<CoreUsr> opSvcAuthorizerUsr = Optional
						.ofNullable(dto.getTCkSvcAuth().getTCoreUsrBySvauUsrAuthorizer());

				entity.getTCkSvcAuth().setTCkMstAuthState(
						opMstAuthState.isPresent() ? opMstAuthState.get().toEntity(new TCkMstAuthState()) : null);

				entity.getTCkSvcAuth().setTCkMstServiceType(
						opMstSvcType.isPresent() ? opMstSvcType.get().toEntity(new TCkMstServiceType()) : null);

				entity.getTCkSvcAuth().setTCoreAccnBySvauAccnService(
						opSvcAuthAccnService.isPresent() ? opSvcAuthAccnService.get().toEntity(new TCoreAccn()) : null);

				entity.getTCkSvcAuth().setTCoreAccnBySvauAccnAuthorized(
						opSvcAuthorizedAccn.isPresent() ? opSvcAuthorizedAccn.get().toEntity(new TCoreAccn()) : null);

				entity.getTCkSvcAuth().setTCoreAccnBySvauAccnAuthorizer(
						opSvcAuthorizerAccn.isPresent() ? opSvcAuthorizerAccn.get().toEntity(new TCoreAccn()) : null);

				entity.getTCkSvcAuth().setTCoreUsrBySvauUsrAuthorized(
						opSvcAuthorizedUsr.isPresent() ? opSvcAuthorizedUsr.get().toEntity(new TCoreUsr()) : null);

				entity.getTCkSvcAuth().setTCoreUsrBySvauUsrAuthorizer(
						opSvcAuthorizerUsr.isPresent() ? opSvcAuthorizerUsr.get().toEntity(new TCoreUsr()) : null);
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
	protected CkSvcAuthAttach dtoFromEntity(TCkSvcAuthAttach entity) throws ParameterException, ProcessingException {
		log.debug("dtoFromEntity");

		try {
			if (null == entity)
				throw new ParameterException("param entity null");

			CkSvcAuthAttach dto = new CkSvcAuthAttach(entity);

			Optional<TCkMstAuthAccnType> opMstAccnType = Optional.ofNullable(entity.getTCkMstAuthAccnType());
			dto.setTCkMstAuthAccnType(opMstAccnType.isPresent() ? new CkMstAuthAccnType(opMstAccnType.get()) : null);

			Optional<TCkSvcAuth> opSvcAuth = Optional.ofNullable(entity.getTCkSvcAuth());

			if (opSvcAuth.isPresent()) {
				dto.setTCkSvcAuth(new CkSvcAuth(opSvcAuth.get()));

				Optional<TCkMstServiceType> opMstServiceType = Optional
						.ofNullable(entity.getTCkSvcAuth().getTCkMstServiceType());
				dto.getTCkSvcAuth().setTCkMstServiceType(new CkMstServiceType(opMstServiceType.get()));

				Optional<TCkMstAuthState> opMstAuthState = Optional
						.ofNullable(entity.getTCkSvcAuth().getTCkMstAuthState());
				dto.getTCkSvcAuth().setTCkMstAuthState(new CkMstAuthState(opMstAuthState.get()));

				Optional<TCoreAccn> opSvcAuthAccn = Optional
						.ofNullable(entity.getTCkSvcAuth().getTCoreAccnBySvauAccnService());
				dto.getTCkSvcAuth().setTCoreAccnBySvauAccnService(new CoreAccn(opSvcAuthAccn.get()));

				Optional<TCoreAccn> opSvcAuthAuthorizer = Optional
						.ofNullable(entity.getTCkSvcAuth().getTCoreAccnBySvauAccnAuthorizer());
				dto.getTCkSvcAuth().setTCoreAccnBySvauAccnAuthorizer(new CoreAccn(opSvcAuthAuthorizer.get()));

				Optional<TCoreAccn> opSvcAuthAuthorized = Optional
						.ofNullable(entity.getTCkSvcAuth().getTCoreAccnBySvauAccnAuthorized());
				dto.getTCkSvcAuth().setTCoreAccnBySvauAccnAuthorized(new CoreAccn(opSvcAuthAuthorized.get()));

				Optional<TCoreUsr> opSvcAuthUsrAuthorizer = Optional
						.ofNullable(entity.getTCkSvcAuth().getTCoreUsrBySvauUsrAuthorizer());
				dto.getTCkSvcAuth().setTCoreUsrBySvauUsrAuthorizer(new CoreUsr(opSvcAuthUsrAuthorizer.get()));

				Optional<TCoreUsr> opSvcAuthUsrAuthorized = Optional
						.ofNullable(entity.getTCkSvcAuth().getTCoreUsrBySvauUsrAuthorized());
				dto.getTCkSvcAuth().setTCoreUsrBySvauUsrAuthorized(new CoreUsr(opSvcAuthUsrAuthorized.get()));

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
	protected String entityKeyFromDTO(CkSvcAuthAttach dto) throws ParameterException, ProcessingException {
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
	protected TCkSvcAuthAttach updateEntity(ACTION attriubte, TCkSvcAuthAttach entity, Principal principal, Date date)
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
	protected TCkSvcAuthAttach updateEntityStatus(TCkSvcAuthAttach entity, char status)
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
	protected CkSvcAuthAttach preSaveUpdateDTO(TCkSvcAuthAttach storedEntity, CkSvcAuthAttach dto)
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
	protected void preSaveValidation(CkSvcAuthAttach dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub

	}

	@Override
	protected ServiceStatus preUpdateValidation(CkSvcAuthAttach dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getWhereClause(CkSvcAuthAttach dto, boolean wherePrinted)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
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

			Optional<CkMstAuthAccnType> opMstAuthAccnType = Optional.ofNullable(dto.getTCkMstAuthAccnType());
			if (opMstAuthAccnType.isPresent()) {
				if (StringUtils.isNotBlank(opMstAuthAccnType.get().getAuatId())) {
					searchStatement.append(getOperator(wherePrinted) + "o.TCkMstAuthAccnType.auatId = :authAccnType");
					wherePrinted = true;
				}
			}

			Optional<CkSvcAuth> opCkSvcAuth = Optional.ofNullable(dto.getTCkSvcAuth());
			if (opCkSvcAuth.isPresent()) {
				Optional<CkMstAuthState> opCkMstAuthState = Optional.ofNullable(opCkSvcAuth.get().getTCkMstAuthState());
				if (opCkMstAuthState.isPresent()) {
					if (StringUtils.isNotBlank(opCkMstAuthState.get().getAustId())) {
						searchStatement.append(
								getOperator(wherePrinted) + "o.TCkSvcAuth.TCkMstAuthState.austId = :svcAuthState");
						wherePrinted = true;
					}
				}

				Optional<CkMstServiceType> opCkMstSvcType = Optional
						.ofNullable(opCkSvcAuth.get().getTCkMstServiceType());
				if (opCkMstSvcType.isPresent()) {
					if (StringUtils.isNotBlank(opCkMstSvcType.get().getSvctId())) {
						searchStatement
								.append(getOperator(wherePrinted) + "o.TCkSvcAuth.TCkMstServiceType.svctId = :svcType");
						wherePrinted = true;
					}
				}

				Optional<CoreAccn> opSvcAccn = Optional.ofNullable(opCkSvcAuth.get().getTCoreAccnBySvauAccnService());
				if (opSvcAccn.isPresent()) {
					if (StringUtils.isNotBlank(opSvcAccn.get().getAccnId())) {
						searchStatement
								.append(getOperator(wherePrinted) + "o.TCoreAccnBySvauAccnService.accnId = :svcAccnId");
						wherePrinted = true;
					}

					if (StringUtils.isNotBlank(opSvcAccn.get().getAccnName())) {
						searchStatement.append(
								getOperator(wherePrinted) + "o.TCoreAccnBySvauAccnService.accnName LIKE :svcAccnName");
						wherePrinted = true;
					}
				}

				Optional<CoreAccn> opSvcAuthAuthorizerAccn = Optional
						.ofNullable(opCkSvcAuth.get().getTCoreAccnBySvauAccnAuthorizer());
				if (opSvcAuthAuthorizerAccn.isPresent()) {
					if (StringUtils.isNotBlank(opSvcAuthAuthorizerAccn.get().getAccnId())) {
						searchStatement.append(getOperator(wherePrinted)
								+ "o.TCoreAccnBySvauAccnAuthorizer.accnId = :svcAuthorizerAccnId");
						wherePrinted = true;

					}

					if (StringUtils.isNotBlank(opSvcAuthAuthorizerAccn.get().getAccnName())) {
						searchStatement.append(getOperator(wherePrinted)
								+ "o.TCoreAccnBySvauAccnAuthorizer.accnName LIKE :svcAuthorizerAccnName");
						wherePrinted = true;
					}
				}

				Optional<CoreAccn> opSvcAuthAuthorizedAccn = Optional
						.ofNullable(opCkSvcAuth.get().getTCoreAccnBySvauAccnAuthorizer());
				if (opSvcAuthAuthorizerAccn.isPresent()) {
					if (StringUtils.isNotBlank(opSvcAuthAuthorizedAccn.get().getAccnId())) {
						searchStatement.append(getOperator(wherePrinted)
								+ "o.TCoreAccnBySvauAccnAuthorized.accnId = :svcAuthorizedAccnId");
						wherePrinted = true;

					}

					if (StringUtils.isNotBlank(opSvcAuthAuthorizedAccn.get().getAccnName())) {
						searchStatement.append(getOperator(wherePrinted)
								+ "o.TCoreAccnBySvauAccnAuthorized.accnName LIKE :svcAuthorizedAccnName");
						wherePrinted = true;
					}
				}

				Optional<CoreUsr> opSvcAuthAuthorizerUsr = Optional
						.ofNullable(opCkSvcAuth.get().getTCoreUsrBySvauUsrAuthorizer());
				if (opSvcAuthAuthorizerUsr.isPresent()) {
					if (StringUtils.isNotBlank(opSvcAuthAuthorizerUsr.get().getUsrUid())) {
						searchStatement.append(getOperator(wherePrinted)
								+ "o.TCoreUsrBySvauUsrAuthorizer.usrUid LIKE :svcAuthorizerUsrId");
						wherePrinted = true;

					}

					if (StringUtils.isNotBlank(opSvcAuthAuthorizerUsr.get().getUsrName())) {
						searchStatement.append(getOperator(wherePrinted)
								+ "o.TCoreUsrBySvauUsrAuthorizer.usrName LIKE :svcAuthorizerUsrName");
						wherePrinted = true;
					}
				}

				Optional<CoreUsr> opSvcAuthAuthorizedUsr = Optional
						.ofNullable(opCkSvcAuth.get().getTCoreUsrBySvauUsrAuthorized());
				if (opSvcAuthAuthorizerUsr.isPresent()) {
					if (StringUtils.isNotBlank(opSvcAuthAuthorizedUsr.get().getUsrUid())) {
						searchStatement.append(getOperator(wherePrinted)
								+ "o.TCoreUsrBySvauUsrAuthorized.usrUid LIKE :svcAuthorizedUsrId");
						wherePrinted = true;

					}

					if (StringUtils.isNotBlank(opSvcAuthAuthorizerUsr.get().getUsrName())) {
						searchStatement.append(getOperator(wherePrinted)
								+ "o.TCoreUsrBySvauUsrAuthorized.usrName LIKE :svcAuthorizedUsrName");
						wherePrinted = true;
					}
				}
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
	protected HashMap<String, Object> getParameters(CkSvcAuthAttach dto)
			throws ParameterException, ProcessingException {
		log.debug("getParameters");

		try {
			if (null == dto)
				throw new ParameterException("param dto null");

			HashMap<String, Object> parameters = new HashMap<String, Object>();

			if (StringUtils.isNotBlank(dto.getAttId()))
				parameters.put("attId", "%" + dto.getAttId() + "%");

			if (StringUtils.isNotBlank(dto.getAttName()))
				parameters.put("attName", "%" + dto.getAttName() + "%");

			Optional<CkMstAuthAccnType> opMstAuthAccnType = Optional.ofNullable(dto.getTCkMstAuthAccnType());
			if (opMstAuthAccnType.isPresent() && StringUtils.isNotBlank(opMstAuthAccnType.get().getAuatId()))
				parameters.put("authAccnType", "%" + opMstAuthAccnType.get().getAuatId() + "%");

			Optional<CkSvcAuth> opCkSvcAuth = Optional.ofNullable(dto.getTCkSvcAuth());
			if (opCkSvcAuth.isPresent()) {
				Optional<CkMstAuthState> opCkMstAuthState = Optional.ofNullable(opCkSvcAuth.get().getTCkMstAuthState());

				if (opCkMstAuthState.isPresent() && StringUtils.isNotBlank(opCkMstAuthState.get().getAustId()))
					parameters.put("svcAuthState", opCkMstAuthState.get().getAustId());

				Optional<CkMstServiceType> opCkMstSvcType = Optional
						.ofNullable(opCkSvcAuth.get().getTCkMstServiceType());
				if (opCkMstSvcType.isPresent() && StringUtils.isNotBlank(opCkMstSvcType.get().getSvctId()))
					parameters.put("svcType", opCkMstSvcType.get().getSvctId());

				Optional<CoreAccn> opSvcAccn = Optional.ofNullable(opCkSvcAuth.get().getTCoreAccnBySvauAccnService());
				if (opSvcAccn.isPresent()) {
					if (StringUtils.isNotBlank(opSvcAccn.get().getAccnId()))
						parameters.put("svcAccnId", opSvcAccn.get().getAccnId());

					if (StringUtils.isNotBlank(opSvcAccn.get().getAccnName()))
						parameters.put("svcAccnName", "%" + opSvcAccn.get().getAccnName() + "%");
				}

				Optional<CoreAccn> opSvcAuthAuthorizerAccn = Optional
						.ofNullable(opCkSvcAuth.get().getTCoreAccnBySvauAccnAuthorizer());
				if (opSvcAuthAuthorizerAccn.isPresent()) {
					if (StringUtils.isNotBlank(opSvcAuthAuthorizerAccn.get().getAccnId()))
						parameters.put("svcAuthorizerAccnId", opSvcAuthAuthorizerAccn.get().getAccnId());

					if (StringUtils.isNotBlank(opSvcAuthAuthorizerAccn.get().getAccnName())) {
						parameters.put("svcAuthorizerAccnName",
								"%" + opSvcAuthAuthorizerAccn.get().getAccnName() + "%");
					}

					Optional<CoreAccn> opSvcAuthAuthorizedAccn = Optional
							.ofNullable(opCkSvcAuth.get().getTCoreAccnBySvauAccnAuthorizer());
					if (opSvcAuthAuthorizerAccn.isPresent()) {
						if (StringUtils.isNotBlank(opSvcAuthAuthorizedAccn.get().getAccnId()))
							parameters.put("svcAuthorizedAccnId", opSvcAuthAuthorizedAccn.get().getAccnId());

						if (StringUtils.isNotBlank(opSvcAuthAuthorizedAccn.get().getAccnName()))
							parameters.put("svcAuthorizedAccnName",
									"%" + opSvcAuthAuthorizedAccn.get().getAccnName() + "%");
					}

					Optional<CoreUsr> opSvcAuthAuthorizerUsr = Optional
							.ofNullable(opCkSvcAuth.get().getTCoreUsrBySvauUsrAuthorizer());
					if (opSvcAuthAuthorizerUsr.isPresent()) {
						if (StringUtils.isNotBlank(opSvcAuthAuthorizerUsr.get().getUsrUid()))
							parameters.put("svcAuthorizedAccnName",
									"%" + opSvcAuthAuthorizerUsr.get().getUsrUid() + "%");

						if (StringUtils.isNotBlank(opSvcAuthAuthorizerUsr.get().getUsrName()))
							parameters.put("svcAuthorizerUsrName",
									"%" + opSvcAuthAuthorizerUsr.get().getUsrName() + "%");
					}

					Optional<CoreUsr> opSvcAuthAuthorizedUsr = Optional
							.ofNullable(opCkSvcAuth.get().getTCoreUsrBySvauUsrAuthorized());
					if (opSvcAuthAuthorizerUsr.isPresent()) {
						if (StringUtils.isNotBlank(opSvcAuthAuthorizedUsr.get().getUsrUid()))
							parameters.put("svcAuthorizedUsrId", "%" + opSvcAuthAuthorizedUsr.get().getUsrUid() + "%");

						if (StringUtils.isNotBlank(opSvcAuthAuthorizerUsr.get().getUsrName()))
							parameters.put("svcAuthorizedUsrName",
									"%" + opSvcAuthAuthorizerUsr.get().getUsrName() + "%");
					}
				}
			}
			Optional<MstAttType> opMstAttType = Optional.ofNullable(dto.getTMstAttType());
			if (opMstAttType.isPresent() && StringUtils.isNotBlank(dto.getTMstAttType().getMattName())) {
				parameters.put("svcAttTypeName", "%" + dto.getTMstAttType().getMattName() + "%");
			}

			if (dto.getAttStatus() != null && Character.isAlphabetic(dto.getAttStatus()))
				parameters.put("attStatus", dto.getAttStatus());

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
	protected CkSvcAuthAttach whereDto(EntityFilterRequest filterRequest)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		log.debug("whereDto");

		try {
			if (null == filterRequest)
				throw new ParameterException("param filterRequest null");

			CkSvcAuthAttach dto = new CkSvcAuthAttach();
			CkMstAuthAccnType ckMstAuthAccnType = new CkMstAuthAccnType();
			CkSvcAuth svcAuth = new CkSvcAuth();
			CkMstAuthState ckMstAuthState = new CkMstAuthState();
			CkMstServiceType ckMstServiceType = new CkMstServiceType();
			MstAttType mstAttType = new MstAttType();
			CoreAccn svcAccnService = new CoreAccn();
			CoreAccn svcAccnAuthorizer = new CoreAccn();
			CoreAccn svcAccnAuthorized = new CoreAccn();
			CoreUsr svcUsrAuthorizer = new CoreUsr();
			CoreUsr svcUsrAuthorized = new CoreUsr();

			for (EntityWhere entityWhere : filterRequest.getWhereList()) {
				Optional<String> opValue = Optional.ofNullable(entityWhere.getValue());
				if (!opValue.isPresent())
					continue;

				if (entityWhere.getAttribute().equalsIgnoreCase("attId"))
					dto.setAttId(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("attName"))
					dto.setAttName(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("TCkMstAuthAccnType.auatId"))
					ckMstAuthAccnType.setAuatId(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("TCkSvcAuth.TCkMstAuthState.austId"))
					ckMstAuthState.setAustId(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("TCkSvcAuth.TCkMstServiceType.austId"))
					ckMstServiceType.setSvctId(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("TCkSvcAuth.TCoreAccnBySvauAccnService.accnId"))
					svcAccnService.setAccnId(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("TCkSvcAuth.TCoreAccnBySvauAccnService.accnName"))
					svcAccnService.setAccnName(opValue.get());

				if (entityWhere.getAttribute().equalsIgnoreCase("TCkSvcAuth.TCoreAccnBySvauAccnAuthorizer.accnId"))
					svcAccnAuthorizer.setAccnId(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("TCkSvcAuth.TCoreAccnBySvauAccnAuthorizer.accnName"))
					svcAccnAuthorizer.setAccnName(opValue.get());

				if (entityWhere.getAttribute().equalsIgnoreCase("TCkSvcAuth.TCoreAccnBySvauAccnAuthorized.accnId"))
					svcAccnAuthorized.setAccnId(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("TCkSvcAuth.TCoreAccnBySvauAccnAuthorized.accnName"))
					svcAccnAuthorized.setAccnName(opValue.get());

				if (entityWhere.getAttribute().equalsIgnoreCase("TCkSvcAuth.TCoreUsrBySvauUsrAuthorizer.usrUid"))
					svcUsrAuthorizer.setUsrUid(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("TCkSvcAuth.TCoreUsrBySvauUsrAuthorizer.usrName"))
					svcUsrAuthorizer.setUsrName(opValue.get());

				if (entityWhere.getAttribute().equalsIgnoreCase("TCkSvcAuth.TCoreUsrBySvauUsrAuthorized.usrUid"))
					svcUsrAuthorized.setUsrUid(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("TCkSvcAuth.TCoreUsrBySvauUsrAuthorized.usrName"))
					svcUsrAuthorized.setUsrName(opValue.get());

				if (entityWhere.getAttribute().equalsIgnoreCase("TMstAttType.mattName"))
					mstAttType.setMattName(opValue.get());

				if (entityWhere.getAttribute().equalsIgnoreCase("mattStatus"))
					dto.setAttStatus(opValue.get().charAt(0));
			}

			svcAuth.setTCkMstAuthState(ckMstAuthState);
			svcAuth.setTCkMstServiceType(ckMstServiceType);
			svcAuth.setTCoreAccnBySvauAccnService(svcAccnService);
			svcAuth.setTCoreAccnBySvauAccnAuthorized(svcAccnAuthorized);
			svcAuth.setTCoreAccnBySvauAccnAuthorizer(svcAccnAuthorizer);
			svcAuth.setTCoreUsrBySvauUsrAuthorized(svcUsrAuthorized);
			svcAuth.setTCoreUsrBySvauUsrAuthorizer(svcUsrAuthorizer);

			dto.setTCkMstAuthAccnType(ckMstAuthAccnType);
			dto.setTMstAttType(mstAttType);
			dto.setTCkSvcAuth(svcAuth);
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
	protected CoreMstLocale getCoreMstLocale(CkSvcAuthAttach dto)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CkSvcAuthAttach setCoreMstLocale(CoreMstLocale coreMstLocale, CkSvcAuthAttach dto)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

}
