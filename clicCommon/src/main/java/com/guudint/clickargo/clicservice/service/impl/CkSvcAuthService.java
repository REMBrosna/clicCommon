package com.guudint.clickargo.clicservice.service.impl;

import java.util.ArrayList;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.clicservice.dto.CkSvcAuth;
import com.guudint.clickargo.clicservice.model.TCkSvcAuth;
import com.guudint.clickargo.common.RecordStatus;
import com.guudint.clickargo.master.dto.CkMstAuthState;
import com.guudint.clickargo.master.dto.CkMstServiceType;
import com.guudint.clickargo.master.model.TCkMstAuthState;
import com.guudint.clickargo.master.model.TCkMstServiceType;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.ccm.dao.CoreAccnDao;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.ccm.dto.CoreUsr;
import com.vcc.camelone.ccm.model.TCoreAccn;
import com.vcc.camelone.ccm.model.TCoreUsr;
import com.vcc.camelone.common.controller.entity.EntityFilterRequest;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.service.ServiceStatus;
import com.vcc.camelone.common.service.entity.AbstractEntityService;
import com.vcc.camelone.locale.dto.CoreMstLocale;
import com.vcc.camelone.master.service.impl.AccountTypeService;

public class CkSvcAuthService extends AbstractEntityService<TCkSvcAuth, String, CkSvcAuth> {

	// Static Attributes
	////////////////////
	private static Logger log = Logger.getLogger(AccountTypeService.class);
	private static String auditTag = "SERVICE AUTH";
	private static String tableName = "T_CK_SVC_AUTH";

	@Autowired
	private GenericDao<TCkSvcAuth, String> ckSvcAuthDao;

	@Autowired
	private CoreAccnDao accnDao;

	// Constructor
	//////////////
	/**
	 * Constructor
	 */
	public CkSvcAuthService() {
		super("ckSvcAuthDao", auditTag, TCkSvcAuth.class.getCanonicalName(), tableName);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CkSvcAuth findById(String id) throws ParameterException, EntityNotFoundException, ProcessingException {
		log.debug("findById");

		try {
			if (StringUtils.isEmpty(id))
				throw new ParameterException("param id null or empty");

			TCkSvcAuth entity = dao.find(id);
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
	public CkSvcAuth deleteById(String id, Principal principal)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		log.debug("deleteById");

		Date now = Calendar.getInstance().getTime();
		try {
			if (StringUtils.isEmpty(id))
				throw new ParameterException("param id null or empty");
			if (null == principal)
				throw new ParameterException("param prinicipal null");

			TCkSvcAuth entity = dao.find(id);
			if (null == entity)
				throw new EntityNotFoundException("id: " + id);

			this.updateEntityStatus(entity, RecordStatus.INACTIVE.getCode());
			this.updateEntity(ACTION.MODIFY, entity, principal, now);

			CkSvcAuth dto = dtoFromEntity(entity);
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
	public List<CkSvcAuth> filterBy(EntityFilterRequest filterRequest)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		log.debug("filterBy");

		try {
			if (null == filterRequest)
				throw new ParameterException("param filterRequest null");

			CkSvcAuth dto = this.whereDto(filterRequest);
			if (null == dto)
				throw new ProcessingException("whereDto null");

			filterRequest.setTotalRecords(super.countByAnd(dto));

			String selectClause = "from TCkSvcAuth o ";
			String orderByClause = filterRequest.getOrderBy().toString();
			List<TCkSvcAuth> entities = super.findEntitiesByAnd(dto, selectClause, orderByClause,
					filterRequest.getDisplayLength(), filterRequest.getDisplayStart());
			List<CkSvcAuth> dtos = entities.stream().map(x -> new CkSvcAuth(x)).collect(Collectors.toList());

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
	protected TCkSvcAuth initEnity(TCkSvcAuth entity) throws ParameterException, ProcessingException {

		if (null != entity) {
			Hibernate.initialize(entity.getTCkMstAuthState());
			Hibernate.initialize(entity.getTCkMstServiceType());
			Hibernate.initialize(entity.getTCoreAccnBySvauAccnService());
			Hibernate.initialize(entity.getTCoreAccnBySvauAccnAuthorizer());
			Hibernate.initialize(entity.getTCoreAccnBySvauAccnAuthorized());
			Hibernate.initialize(entity.getTCoreUsrBySvauUsrAuthorizer());
			Hibernate.initialize(entity.getTCoreUsrBySvauUsrAuthorized());

		}

		return entity;
	}

	@Override
	protected TCkSvcAuth entityFromDTO(CkSvcAuth dto) throws ParameterException, ProcessingException {
		log.debug("entityFromDTO");

		try {
			if (null == dto)
				throw new ParameterException("dto dto null");

			TCkSvcAuth entity = new TCkSvcAuth();
			entity = dto.toEntity(entity);

			Optional<CkMstAuthState> opMstAuthState = Optional.ofNullable(dto.getTCkMstAuthState());
			entity.setTCkMstAuthState(
					opMstAuthState.isPresent() ? opMstAuthState.get().toEntity(new TCkMstAuthState()) : null);

			Optional<CkMstServiceType> opMstSvcType = Optional.ofNullable(dto.getTCkMstServiceType());
			entity.setTCkMstServiceType(
					opMstSvcType.isPresent() ? opMstSvcType.get().toEntity(new TCkMstServiceType()) : null);

			Optional<CoreAccn> opAccnSvcAuthService = Optional.ofNullable(dto.getTCoreAccnBySvauAccnService());
			entity.setTCoreAccnBySvauAccnService(
					opAccnSvcAuthService.isPresent() ? opAccnSvcAuthService.get().toEntity(new TCoreAccn()) : null);

			Optional<CoreAccn> opAccnSvcAuthorizer = Optional.ofNullable(dto.getTCoreAccnBySvauAccnAuthorizer());
			entity.setTCoreAccnBySvauAccnAuthorizer(
					opAccnSvcAuthorizer.isPresent() ? opAccnSvcAuthorizer.get().toEntity(new TCoreAccn()) : null);

			Optional<CoreAccn> opAccnSvcAuthorized = Optional.ofNullable(dto.getTCoreAccnBySvauAccnAuthorized());
			entity.setTCoreAccnBySvauAccnAuthorized(
					opAccnSvcAuthorized.isPresent() ? opAccnSvcAuthorized.get().toEntity(new TCoreAccn()) : null);

			Optional<CoreUsr> opAccnSvnUsrAuthorizer = Optional.ofNullable(dto.getTCoreUsrBySvauUsrAuthorizer());
			entity.setTCoreUsrBySvauUsrAuthorizer(
					opAccnSvnUsrAuthorizer.isPresent() ? opAccnSvnUsrAuthorizer.get().toEntity(new TCoreUsr()) : null);

			Optional<CoreUsr> opAccnSvnUsrAuthorized = Optional.ofNullable(dto.getTCoreUsrBySvauUsrAuthorized());
			entity.setTCoreUsrBySvauUsrAuthorized(
					opAccnSvnUsrAuthorized.isPresent() ? opAccnSvnUsrAuthorized.get().toEntity(new TCoreUsr()) : null);

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
	protected CkSvcAuth dtoFromEntity(TCkSvcAuth entity) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String entityKeyFromDTO(CkSvcAuth dto) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected TCkSvcAuth updateEntity(ACTION attriubte, TCkSvcAuth entity, Principal principal, Date date)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected TCkSvcAuth updateEntityStatus(TCkSvcAuth entity, char status)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CkSvcAuth preSaveUpdateDTO(TCkSvcAuth storedEntity, CkSvcAuth dto)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void preSaveValidation(CkSvcAuth dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub

	}

	@Override
	protected ServiceStatus preUpdateValidation(CkSvcAuth dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getWhereClause(CkSvcAuth dto, boolean wherePrinted)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected HashMap<String, Object> getParameters(CkSvcAuth dto) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CkSvcAuth whereDto(EntityFilterRequest filterRequest) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CoreMstLocale getCoreMstLocale(CkSvcAuth dto)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CkSvcAuth setCoreMstLocale(CoreMstLocale coreMstLocale, CkSvcAuth dto)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<CoreAccn> getAuthorizedParties(String isAuthorized, String accnId){
		try {

			List<CoreAccn> parties = new ArrayList<CoreAccn>();
			if (StringUtils.isEmpty(isAuthorized))
				throw new ParameterException("isAuthorized is null or empty");
			if (StringUtils.isEmpty(accnId))
				throw new ParameterException("accnId is null or empty");
			
			String hql = StringUtils.EMPTY;
			
			if(isAuthorized.equalsIgnoreCase("Y")) {
				hql = "FROM TCkSvcAuth o WHERE o.TCoreAccnBySvauAccnAuthorizer.accnId = :accnId";
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("accnId", accnId);
				List<TCkSvcAuth> svcAuthList = ckSvcAuthDao.getByQuery(hql, params);
				for(TCkSvcAuth svcAuth : svcAuthList) {
					TCoreAccn tAccn = svcAuth.getTCoreAccnBySvauAccnAuthorized();
					CoreAccn accn = new CoreAccn();
					accn.setAccnId(tAccn.getAccnId());
					accn.setAccnName(tAccn.getAccnName());
					parties.add(accn);
				}
			}else {
				hql = "FROM TCoreAccn o WHERE o.TMstAccnType.atypId = :atypId AND o.accnId NOT IN ("
						+ "select p.TCoreAccnBySvauAccnAuthorized.accnId FROM TCkSvcAuth p WHERE p.TCoreAccnBySvauAccnAuthorizer.accnId = :accnId)";
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("atypId", "ACC_TYPE_FF");
				params.put("accnId", accnId);
				List<TCoreAccn> unauthorizedParties = accnDao.getByQuery(hql, params);
				for (TCoreAccn unauthorizedParty : unauthorizedParties) {
					CoreAccn accn = new CoreAccn();
					accn.setAccnId(unauthorizedParty.getAccnId());
					accn.setAccnName(unauthorizedParty.getAccnName());
					parties.add(accn);
				}
			}
			return parties;
		} catch (Exception e) {
			log.error("getAuthorizedParties", e);
			return null;
		}
	}

	public boolean isAuthorized(String accnId, String authParty){
		try {

			boolean authorized = false;
			if (StringUtils.isEmpty(accnId))
				throw new ParameterException("accnId is null or empty");
			
			String hql = StringUtils.EMPTY;
			
				hql = "FROM TCkSvcAuth o WHERE o.TCoreAccnBySvauAccnAuthorizer.accnId = :accnId "
						+ "AND o.TCoreAccnBySvauAccnAuthorized.accnId = :authParty";
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("accnId", accnId);
				params.put("authParty", authParty);
				List<TCkSvcAuth> svcAuthList = ckSvcAuthDao.getByQuery(hql, params);
				if(svcAuthList.size()>0) {
					authorized = true;
				}
			return authorized;
		} catch (Exception e) {
			log.error("isAuthorized", e);
			return false;
		}
	}

}
