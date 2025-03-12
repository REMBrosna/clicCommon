package com.guudint.clickargo.admin.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.admin.dto.ChangePassword;
import com.guudint.clickargo.admin.dto.CkUserNotifPref;
import com.guudint.clickargo.admin.dto.ClickargoUser;
import com.guudint.clickargo.admin.event.ClickargoPostUserUpdateEvent;
import com.guudint.clickargo.admin.event.ClickargoPostUserUpdateEvent.PostUserUpdateAction;
import com.guudint.clickargo.clicservice.dao.CkSvcSubDao;
import com.guudint.clickargo.clicservice.model.TCkSvcSub;
import com.guudint.clickargo.common.RecordStatus;
import com.guudint.clickargo.master.enums.AccountTypes;
import com.guudint.clickargo.master.enums.Roles;
import com.guudint.clickargo.master.enums.ServiceTypes;
import com.vcc.camelone.cac.dto.CoreRole;
import com.vcc.camelone.cac.dto.CoreRoleId;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.cac.model.TCoreRole;
import com.vcc.camelone.cac.model.TCoreRoleId;
import com.vcc.camelone.cac.model.TCoreUsrRole;
import com.vcc.camelone.cac.model.TCoreUsrRoleId;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.ccm.dto.CoreAddress;
import com.vcc.camelone.ccm.dto.CoreContact;
import com.vcc.camelone.ccm.dto.CoreUsr;
import com.vcc.camelone.ccm.dto.PortalUser;
import com.vcc.camelone.ccm.model.TCoreAccn;
import com.vcc.camelone.ccm.model.TCoreUsr;
import com.vcc.camelone.ccm.model.TCoreUsrReset;
import com.vcc.camelone.ccm.model.embed.TCoreAddress;
import com.vcc.camelone.ccm.model.embed.TCoreContact;
import com.vcc.camelone.ccm.service.IPortalUser;
import com.vcc.camelone.ccm.util.Constant;
import com.vcc.camelone.common.attach.dto.CoreAttach;
import com.vcc.camelone.common.attach.model.TCoreAttach;
import com.vcc.camelone.common.controller.entity.EntityFilterRequest;
import com.vcc.camelone.common.controller.entity.EntityFilterResponse;
import com.vcc.camelone.common.controller.entity.EntityOrderBy;
import com.vcc.camelone.common.controller.entity.EntityOrderBy.ORDERED;
import com.vcc.camelone.common.controller.entity.EntityWhere;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.service.entity.IEntityService;
import com.vcc.camelone.core.dto.CoreApps;
import com.vcc.camelone.core.model.TCoreApps;
import com.vcc.camelone.core.model.TCoreSession;
import com.vcc.camelone.master.controller.PathNotFoundException;
import com.vcc.camelone.master.dto.MstAccnType;
import com.vcc.camelone.master.dto.MstCountry;
import com.vcc.camelone.master.dto.MstUclass;
import com.vcc.camelone.master.model.TMstAccnType;
import com.vcc.camelone.master.model.TMstCountry;
import com.vcc.camelone.master.model.TMstUclass;
import com.vcc.camelone.util.PrincipalUtilService;
import com.vcc.camelone.util.crypto.PasswordEncryptor;
import com.vcc.camelone.util.crypto.PasswordGenerator;
import com.vcc.camelone.util.email.SysParam;

import io.jsonwebtoken.lang.Collections;

@Service
public class ClickargoManageUserService {

	// Static Attributes
	////////////////////
	private static Logger log = Logger.getLogger(ClickargoManageUserService.class);
	public static final String appCode = "CONE";
	public static final String USER_CREATION = "SCT_NT_05_CREATE_FIRST_USER";
	public static final String ENTITY_NAME = "TCoreUsr";

	private static String HISTORY = "history";
	private static String DEFAULT = "default";

	public static enum UserAction {
		ACTIVATE("activate"), DEACTIVATE("deactivate"), RESET_PASSWORD("resetPassword"), SAVE("save"),
		SUSPEND("suspend"), UNSUSPEND("unsuspend");

		String action;

		UserAction(String action) {
			this.action = action;
		}

		public String getAction() {
			return this.action;
		}
	}

	@Autowired
	@Qualifier("ccmUserService")
	private IEntityService<TCoreUsr, String, CoreUsr> ccmUserService;

	@Autowired
	@Qualifier("ccmAccnService")
	private IEntityService<TCoreAccn, String, CoreAccn> ccmAccnService;

	@Autowired
	// @Qualifier("coreRoleDao")
	private GenericDao<TCoreRole, TCoreRoleId> coreRoleDao;

	@Autowired
	@Qualifier("coreUserDao")
	private GenericDao<TCoreUsr, String> coreUserDao;

	@Autowired
	@Qualifier("coreUsrResetDao")
	private GenericDao<TCoreUsrReset, String> coreUsrResetDao;

	@Autowired
	// @Qualifier("coreUsrRoleDao")
	private GenericDao<TCoreUsrRole, TCoreUsrRoleId> coreUsrRoleDao;

	@Autowired
	protected PrincipalUtilService principalUtilService;

	@Autowired
	protected ApplicationEventPublisher eventPublisher;

	@Autowired
	@Qualifier("coreAppsDao")
	private GenericDao<TCoreApps, String> coreAppsDao;

	@Autowired
	@Qualifier("attachService")
	IEntityService<TCoreAttach, String, CoreAttach> attachmentService;

	@Autowired
	IPortalUser portalUserService;

	@Autowired
	@Qualifier("coreSessionDao")
	private GenericDao<TCoreSession, String> coreSessionDao;

	@Autowired
	@Qualifier("coreAccDao")
	private GenericDao<TCoreAccn, String> coreAccDao;

	@Autowired
	protected SysParam sysParam;

	@Autowired
	private Environment env;

	@Autowired
	private IPortalUser portalUser;

	@Autowired
	private CkSvcSubDao svcSubDao;

	/**
	 * Returns empty {@code PortalUser} object.
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public ClickargoUser getNewUser() throws Exception {
		log.debug("getNewUser");
		try {

			Principal principal = principalUtilService.getPrincipal();
			if (principal == null)
				throw new Exception("principal is null");

			if (StringUtils.isEmpty(principal.getAppsCode()))
				throw new Exception("principal appsCode is null or empty");

			CoreAccn accn = principal.getCoreAccn();
			if (accn == null)
				throw new Exception("account details is null");

			CoreUsr usr = new CoreUsr();
			usr.setTCoreAccn(accn);

			usr.setUsrContact(new CoreContact());
			usr.setUsrAddr(new CoreAddress());

			ClickargoUser portalUsr = new ClickargoUser();
			portalUsr.setCoreUsr(usr);
			portalUsr.setNotHoldRoleList(getUserRoles(principal));
			portalUsr.setHoldRoleList(new ArrayList<CoreRole>());
			portalUsr.setUsrNotifProf(new CkUserNotifPref());

			return portalUsr;

		} catch (Exception e) {
			log.error("getNewUser", e);
			throw e;
		}

	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public ClickargoUser getUser(String usrId) throws Exception {
		try {

			if (StringUtils.isEmpty(usrId))
				throw new ParameterException("usrId is empty or null");

			Principal principal = principalUtilService.getPrincipal();
			if (principal == null)
				throw new Exception("principal is null");

			PortalUser portalUser = portalUserService.getUser(usrId, principal);
			TCoreUsr tCoreUsr = coreUserDao.find(usrId);
			if (Objects.nonNull(tCoreUsr) && Objects.nonNull(portalUser)) {
				ClickargoUser usr = new ClickargoUser();
				usr.setCoreUsr(portalUser.getCoreUsr());
				List<CoreRole> coreRoleArrayList = new ArrayList<>();

				// in case the portal hold list does not belong to that principal appscode
				if (portalUser.getHoldRoleList() != null) {
					for (CoreRole portalUserRole : portalUser.getHoldRoleList()) {
						if (portalUserRole.getId().getRoleId().equalsIgnoreCase("OP_ADMIN") && !tCoreUsr.getTCoreAccn()
								.getTMstAccnType().getAtypId().equalsIgnoreCase("ACC_TYPE_TO")) {
							continue;
						}
						coreRoleArrayList.add(portalUserRole);
					}
				}

				// Set the filtered hold role list
				usr.setHoldRoleList(coreRoleArrayList);

				if (usr.getHoldRoleList() != null && usr.getHoldRoleList().size() > 0) {
					List<String> holdRoleListStr = usr.getHoldRoleList().stream().map(CoreRole::getId)
							.map(s -> s.getRoleId()).collect(Collectors.toList());
					// reload the notholdrolelist by account id of each account
					List<CoreRole> noHoldRoleList = findRolesByAccnBySvcSub(tCoreUsr.getTCoreAccn(), principal);

					// remove holdRoleList from notHoldRoleList
					noHoldRoleList.removeAll(usr.getHoldRoleList());

					List<CoreRole> updatedNoHoldRoleList = noHoldRoleList.stream().filter(r -> {
						return !holdRoleListStr.contains(r.getId().getRoleId());
					}).collect(Collectors.toList());

					usr.setNotHoldRoleList(updatedNoHoldRoleList);
				} else {
					usr.setNotHoldRoleList(findRolesByAccnBySvcSub(tCoreUsr.getTCoreAccn(), principal));
				}

				return usr;
			}

			return null;

		} catch (Exception ex) {
			log.error("getUser", ex);
			throw ex;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public ClickargoUser createUser(ClickargoUser portalUser)
			throws ParameterException, ProcessingException, Exception {
		log.debug("updateUser");
		try {

			if (portalUser == null)
				throw new ParameterException("portalUser is null");

			Principal principal = principalUtilService.getPrincipal();
			if (principal == null)
				throw new Exception("principal is null");

			this.createUser(portalUser, principal);

			TCoreUsr usr = new TCoreUsr();
			BeanUtils.copyProperties(portalUser.getCoreUsr(), usr);

			return portalUser;

		} catch (Exception e) {
			log.error("updateUser", e);
			throw e;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public ClickargoUser createUser(ClickargoUser portalUser, Principal principal) throws Exception {

		log.debug("createUser");

		try {

			// 0: validation
			if (null == portalUser)
				throw new Exception(" portalUser is null.");
			if (null == portalUser.getCoreUsr())
				throw new Exception(" CoreUsr is null.");
//			if (StringUtils.isBlank(portalUser.getCoreUsr().getUsrUid()))
//				throw new ProcessingException("usrUid is empty or null");

			CoreUsr coreUsr = portalUser.getCoreUsr();

			TCoreUsr usrExist = coreUserDao.find(coreUsr.getUsrUid());
			if (usrExist != null) {
				throw new ProcessingException(
						"User with user ID ".concat(coreUsr.getUsrUid()).concat(" already exist!"));
			}

//			// 1: create user;
			// 1.1: user password;
			String pswd = PasswordGenerator.generatePassword(8);
			portalUser.setTempPwd(pswd);
			String pswdEncrypt = PasswordEncryptor.encrypt(coreUsr.getUsrUid(), pswd);
			coreUsr.setUsrPwd(pswdEncrypt);
			coreUsr.setUsrPwdForce("Y"); // force change password

			initUser(coreUsr, principal);
			ccmUserService.add(coreUsr, principal);

//			// 2: upload user profile image;
//			if (null != portalUser.getUserProfileImage()) {
//
//				CoreAttach coreAttach = portalUser.getUserProfileImage();
//				initCoreAttach(coreAttach, portalUser, AttachmentServiceImpl.ATTACH_TYPE_USER_PROFILE, principal);
//				attachmentService.add(coreAttach, principal);
//			}

//			// 3: upload user signature;
//			if (null != portalUser.getUserSignature()) {
//
//				CoreAttach coreAttach = portalUser.getUserSignature();
//				initCoreAttach(coreAttach, portalUser, AttachmentServiceImpl.ATTACH_TYPE_USR_SIG, principal);
//
//				attachmentService.add(coreAttach, principal);
//			}

			// 4 add new user's roles;
			List<CoreRole> holdRoleList = portalUser.getHoldRoleList();
			if (holdRoleList != null && holdRoleList.size() > 0) {
				for (CoreRole coreRole : holdRoleList) {
					this.addUsrRole(coreUsr.getUsrUid(), principal.getAppsCode(), coreRole.getId().getRoleId());
				}
			}

			eventPublisher.publishEvent(
					new ClickargoPostUserUpdateEvent(this, coreUsr, false, PostUserUpdateAction.NEW_USER, pswd));

			return portalUser;

		} catch (Exception ex) {
			log.error("createUser", ex);
			throw new ProcessingException(ex.getMessage());
		}
	}

	/**
	 * 
	 * @param usrid
	 * @return
	 * @throws Exception
	 */
	public List<TCoreSession> getCoreSessionByUser(String usrid) throws Exception {
		// Nina Added criteria when checking coresession as sometimes user may not click
		// logout so the records still there.
		String hql = "FROM TCoreSession o WHERE o.sessUid = :usrid AND o.sessTimePrincipalCache >= :calculatedDate ORDER BY o.sessTimePrincipalCache DESC";
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("usrid", usrid);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, -24);
		parameters.put("calculatedDate", cal.getTime());
		return coreSessionDao.getByQuery(hql, parameters);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public PortalUser updateUserProfile(PortalUser portalUser) throws Exception {
		log.debug("updateUserProfile");

		try {

			Principal principal = principalUtilService.getPrincipal();
			if (principal == null)
				throw new Exception("principal is null");

			// 0: validation
			if (null == portalUser)
				throw new Exception(" portalUser is null.");
			if (null == portalUser.getCoreUsr())
				throw new Exception(" CoreUsr is null.");
			if (isExistingUserEmail(portalUser.getCoreUsr().getUsrUid(), portalUser.getCoreUsr())) {
				// throw new Exception("Duplicate Email provided");
			}

			// 1: update user information;
			ccmUserService.updateObj(portalUser.getCoreUsr(), principal);
			/*-
						// 2: upload user profile image;
						if (null != portalUser.getUserProfileImage()) {
			
							CoreAttach coreAttach = portalUser.getUserProfileImage();
			
							if (StringUtils.isEmpty(coreAttach.getAttId())) {
								initCoreAttach(coreAttach, portalUser, AttachmentType.USERPROFILE.toString(), principal);
								attachmentService.add(coreAttach, principal);
							} else {
								attachmentService.update(coreAttach, principal);
							}
						}
			
						// 3: upload user signature;
						if (null != portalUser.getUserSignature()) {
			
							CoreAttach coreAttach = portalUser.getUserSignature();
			
							if (StringUtils.isEmpty(coreAttach.getAttId())) {
								initCoreAttach(coreAttach, portalUser, AttachmentType.SIG.toString(), principal);
								attachmentService.add(coreAttach, principal);
							} else {
								attachmentService.update(coreAttach, principal);
							}
						}
			*/
			String appsCodeByPrincipal = getCoreAppsCode(principal);
			// 4: Delete all roles;
			this.deleteUserRole(portalUser.getCoreUsr().getUsrUid(), appsCodeByPrincipal);

			// 5 add new user's roles;
			List<CoreRole> holdRoleList = portalUser.getHoldRoleList();
			if (holdRoleList != null && holdRoleList.size() > 0) {
				for (CoreRole coreRole : holdRoleList) {
					this.addUsrRole(portalUser.getCoreUsr().getUsrUid(), appsCodeByPrincipal,
							coreRole.getId().getRoleId());
				}
			}

			return portalUser;

		} catch (Exception ex) {
			log.error("updateUserProfile", ex);
			throw new Exception(ex);
		}
	}

	private void deleteUserRole(String urourolUid, String urolAppscode) throws Exception {

		Map<String, Object> params = new HashMap<>();
		params.put("urourolUid", urourolUid);
		// params.put("urolAppscode", urolAppscode);

		int deleteRst = coreUsrRoleDao.executeUpdate("DELETE from TCoreUsrRole where TCoreUsr.usrUid = :urourolUid ",
				params);
		// coreUsrRoleDao.executeNativeSQL("delete FROM covsew.T_CORE_USR_ROLE where
		// UROL_APPSCODE= '"+urolAppscode + "' and UROL_UID ='"+urourolUid+"'");
		log.info("delete user roles: " + deleteRst);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public ClickargoUser updateUserByAction(String action, String usrId, ClickargoUser portalUser)
			throws ParameterException, ProcessingException, Exception {
		log.debug("updateUser");
		try {

			Principal principal = principalUtilService.getPrincipal();
			if (principal == null)
				throw new Exception("principal is null");

			if (StringUtils.isEmpty(action))
				throw new ParameterException("action is null or empty");

			if (StringUtils.isEmpty(usrId))
				throw new ParameterException("usrId is null or empty");

			if (!isActionValid(action))
				throw new ProcessingException("invalid action");

			String randomPwd = null;
			CoreUsr usr = ccmUserService.findById(usrId);
			if (action.equalsIgnoreCase(UserAction.ACTIVATE.getAction())) {
				usr.setUsrStatus(RecordStatus.ACTIVE.getCode());
				usr.setUsrLoginInvcnt(0);
			} else if (action.equalsIgnoreCase(UserAction.DEACTIVATE.getAction())) {
				usr.setUsrStatus(RecordStatus.INACTIVE.getCode());
			} else if (action.equalsIgnoreCase(UserAction.UNSUSPEND.getAction())) {
				usr.setUsrStatus(RecordStatus.ACTIVE.getCode());
			} else if (action.equalsIgnoreCase(UserAction.SUSPEND.getAction())) {
				usr.setUsrStatus(RecordStatus.SUSPENDED.getCode());
			} else if (action.equalsIgnoreCase(UserAction.RESET_PASSWORD.getAction())) {
				// reset the password here and email
				randomPwd = PasswordGenerator.generatePassword(8);
				usr.setUsrPwd(PasswordEncryptor.encrypt(usr.getUsrUid(), randomPwd));
				usr.setUsrDtPwdLupd(new Date());
				// reset the login count
				usr.setUsrLoginInvcnt(0);
				// force the user to reset the password
				usr.setUsrPwdForce(Constant.USR_PWD_FORCE_ENABLE);
			}

			if (action.equalsIgnoreCase(UserAction.SAVE.getAction())) {
				// just set to the decrypted userid
				portalUser.getCoreUsr().setUsrUid(usrId);
				portalUserService.updateUser(portalUser, principal);

			} else {
				CoreUsr usrUp = ccmUserService.update(usr, principal);

				// publish event if reset password action and just make sure that pwd is not
				// blank
				if (action.equalsIgnoreCase(UserAction.RESET_PASSWORD.getAction())
						&& StringUtils.isNotBlank(randomPwd)) {
					// temporarily set the contact email to the alternate email provided if it's not
					// the same as the contact email
					if (StringUtils.isNotEmpty(portalUser.getAlternateEmail()) && !portalUser.getAlternateEmail()
							.equalsIgnoreCase(usrUp.getUsrContact().getContactEmail())) {
						usrUp.getUsrContact().setContactEmail(portalUser.getAlternateEmail());
					}

					eventPublisher.publishEvent(new ClickargoPostUserUpdateEvent(this, usrUp, true,
							PostUserUpdateAction.RESET_PWD, randomPwd));
					portalUser.setCoreUsr(usrUp);
				}
			}

			return portalUser;

		} catch (Exception e) {
			log.error("updateUser", e);
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Optional<Object> getEntitiesByProxy(Map<String, String> params)
			throws ParameterException, PathNotFoundException, ProcessingException {
		log.debug("getEntitiesProxy");

		try {

			if (Collections.isEmpty(params))
				throw new ParameterException("param params null or empty");

			Principal principal = principalUtilService.getPrincipal();
			if (principal == null) {
				throw new ProcessingException("principal is null");
			}

			EntityFilterRequest filterRequest = new EntityFilterRequest();
			// start and length parameter extraction
			filterRequest.setDisplayStart(
					params.containsKey("iDisplayStart") ? Integer.valueOf(params.get("iDisplayStart")).intValue() : -1);
			filterRequest.setDisplayLength(
					params.containsKey("iDisplayLength") ? Integer.valueOf(params.get("iDisplayLength")).intValue()
							: -1);
			// where parameters extraction
			ArrayList<EntityWhere> whereList = new ArrayList<>();
			List<String> searches = params.keySet().stream().filter(x -> x.contains("sSearch_"))
					.collect(Collectors.toList());
			for (int nIndex = 1; nIndex <= searches.size(); nIndex++) {
				String searchParam = params.get("sSearch_" + String.valueOf(nIndex));
				String valueParam = params.get("mDataProp_" + String.valueOf(nIndex));
				log.info("searchParam: " + searchParam + " valueParam: " + valueParam);
				whereList.add(new EntityWhere(valueParam, searchParam));
			}

			filterRequest.setWhereList(whereList);
			// order by parameters extraction
			Optional<String> opSortAttribute = Optional.ofNullable(params.get("mDataProp_0"));
			Optional<String> opSortOrder = Optional.ofNullable(params.get("sSortDir_0"));
			if (opSortAttribute.isPresent() && opSortOrder.isPresent()) {
				EntityOrderBy orderBy = new EntityOrderBy();
				orderBy.setAttribute(opSortAttribute.get());
				orderBy.setOrdered(opSortOrder.get().equalsIgnoreCase("desc") ? ORDERED.DESC : ORDERED.ASC);
				filterRequest.setOrderBy(orderBy);
			}

			if (!filterRequest.isValid())
				throw new ProcessingException("Invalid request: " + filterRequest.toJson());

			List<CoreUsr> en = filterBy(filterRequest);
			List<Object> entities = List.class.cast(en);
			EntityFilterResponse filterResponse = new EntityFilterResponse();
			filterResponse.setiTotalRecords(entities.size());
			filterResponse.setiTotalDisplayRecords(filterRequest.getTotalRecords());
			filterResponse.setAaData((ArrayList<Object>) entities);

			return Optional.of(filterResponse);
		} catch (ParameterException | PathNotFoundException | ProcessingException ex) {
			log.error("getEntitiesProxy", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("getEntitiesProxy", ex);
			throw new ProcessingException(ex);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public List<CoreUsr> filterBy(EntityFilterRequest filterRequest)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		log.debug("filterBy");

		try {
			if (null == filterRequest)
				throw new ParameterException("param filterRequest null");

			CoreUsr dto = this.whereDto(filterRequest);
			if (null == dto)
				throw new ProcessingException("whereDto null");

			filterRequest.setTotalRecords(this.countByAnd(dto));

			String selectClause = "from TCoreUsr o ";
			String orderByClause = filterRequest.getOrderBy().toString();
			List<TCoreUsr> entities = this.findEntitiesByAnd(dto, selectClause, orderByClause,
					filterRequest.getDisplayLength(), filterRequest.getDisplayStart());
			List<CoreUsr> dtos = entities.stream().map(x -> {
				try {
					CoreUsr usr = dtoFromEntity(x);
					return usr;
				} catch (ParameterException e) {
					log.error("filterBy", e);
				} catch (ProcessingException e) {
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

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	protected List<TCoreUsr> findEntitiesByAnd(CoreUsr dto, String selectClause, String orderByClause, int limit,
			int offset) throws ParameterException, ProcessingException {
		log.debug("findEntitesByAnd");
		try {
			if (null == dto)
				throw new ParameterException("param dto null");
			if (StringUtils.isEmpty(selectClause))
				throw new ParameterException("param selectClause null or empty");
			if (StringUtils.isEmpty(orderByClause))
				throw new ParameterException("param orderByClause null or empty");

			String whereClause = this.getWhereClause(dto, false); // abstract callback
			log.debug("whereClause: " + whereClause);
			HashMap<String, Object> parameters = this.getParameters(dto); // abstract callback

			String hqlQuery = StringUtils.isEmpty(whereClause) ? selectClause + orderByClause
					: selectClause + whereClause + orderByClause;

			List<TCoreUsr> entities = coreUserDao.getByQuery(hqlQuery, parameters, limit, offset);

			for (TCoreUsr entity : entities)
				this.initEnity(entity); // abstract callback
			return entities;
		} catch (ParameterException | ProcessingException ex) {
			log.error("findEntitiesByAnd", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("findEntitiesByAnd", ex);
			throw new ProcessingException(ex);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	protected TCoreUsr initEnity(TCoreUsr entity) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		if (null != entity) {
			Hibernate.initialize(entity.getTCoreAccn());
			Hibernate.initialize(entity.getTCoreAccn().getTMstAccnType());
			Hibernate.initialize(entity.getTCoreAccn().getAccnAddr());
			Hibernate.initialize(entity.getTCoreAccn().getAccnContact());
			Hibernate.initialize(entity.getTMstUclass());
			Hibernate.initialize(entity.getUsrAddr());
			if (null != entity.getUsrAddr()) {
				Hibernate.initialize(entity.getUsrAddr().getAddrCtry());
			}

			Hibernate.initialize(entity.getUsrContact());
		}
		return entity;
	}

	protected HashMap<String, Object> getParameters(CoreUsr dto) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		log.debug("getParameters");

		try {
			if (null == dto)
				throw new ParameterException("param dto null");

			SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
			HashMap<String, Object> parameters = new HashMap<String, Object>();

			Principal principal = principalUtilService.getPrincipal();
			if (dto.getTMstUclass() == null || (dto.getTMstUclass() != null
					&& !StringUtils.equalsIgnoreCase(dto.getTMstUclass().getUclaCode(), "all"))) {
				if (principal != null) {
					parameters.put("accnId", principal.getCoreAccn().getAccnId());
				}
			}

			if (dto.getTMstUclass() != null && StringUtils.equalsIgnoreCase(dto.getTMstUclass().getUclaCode(), "all")) {
				if (dto.getTMstUclass() != null
						&& StringUtils.equalsIgnoreCase(dto.getTMstUclass().getUclaDescription(), HISTORY)) {
					parameters.put("usrStatuses",
							Arrays.asList(RecordStatus.DEACTIVATE.getCode(), RecordStatus.SUSPENDED.getCode(), RecordStatus.INACTIVE.getCode()));
				} else {
					parameters.put("usrStatuses", Arrays.asList(RecordStatus.ACTIVE.getCode()));
				}
			}

			if (!StringUtils.isEmpty(dto.getUsrUid()))
				parameters.put("usrUid", "%" + dto.getUsrUid() + "%");
			if (!StringUtils.isEmpty(dto.getUsrName()))
				parameters.put("usrName", "%" + dto.getUsrName() + "%");

			if (dto.getTCoreAccn() != null && !StringUtils.isEmpty(dto.getTCoreAccn().getAccnId()))
				parameters.put("accnIdFilter", "%" + dto.getTCoreAccn().getAccnId() + "%");

			if (dto.getTCoreAccn() != null && !StringUtils.isEmpty(dto.getTCoreAccn().getAccnName()))
				parameters.put("accnNameFilter", "%" + dto.getTCoreAccn().getAccnName() + "%");

			if (dto.getTCoreAccn() != null && null != dto.getTCoreAccn().getTMstAccnType()) {
				if (dto.getTCoreAccn().getTMstAccnType().getAtypId() != null) {
					parameters.put("atypId", dto.getTCoreAccn().getTMstAccnType().getAtypId());
				}
				if (dto.getTCoreAccn().getTMstAccnType().getAtypDescription() != null) {
					parameters.put("atypDescription",
							"%" + dto.getTCoreAccn().getTMstAccnType().getAtypDescription() + "%");
				}
			}

			if (!StringUtils.isEmpty(dto.getUsrDept()))
				parameters.put("usrDept", "%" + dto.getUsrDept() + "%");

			if (dto.getUsrContact() != null) {
				if (!StringUtils.isEmpty(dto.getUsrContact().getContactEmail())) {
					parameters.put("contactEmail", "%" + dto.getUsrContact().getContactEmail() + "%");
				}
			}
			if (Character.isAlphabetic(dto.getUsrStatus())) {
				parameters.put("usrStatus", dto.getUsrStatus());
			}

			if (null != dto.getUsrDtCreate()) {
				parameters.put("usrDtCreate", sdfDate.format(dto.getUsrDtCreate()));
			}

			if (null != dto.getUsrDtLupd()) {
				parameters.put("usrDtLupd", sdfDate.format(dto.getUsrDtLupd()));
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

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public int countByAnd(CoreUsr dto) throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		log.debug("countByAnd");

		try {
			if (null == dto)
				throw new ParameterException("param dto null");

			String whereClause = this.getWhereClause(dto, false); // abstract callback
			HashMap<String, Object> parameters = this.getParameters(dto); // abstract callback

			int count = 0;
			if (StringUtils.isNotEmpty(whereClause) && null != parameters && parameters.size() > 0) {
				count = this.coreUserDao.count("SELECT COUNT(o) FROM " + ENTITY_NAME + " o" + whereClause, parameters);
			} else {
				count = this.coreUserDao.count("SELECT COUNT(o) FROM " + ENTITY_NAME + " o");
			}
			return count;
		} catch (ParameterException | EntityNotFoundException ex) {
			log.error("countByAnd", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("countByAnd", ex);
			throw new ProcessingException(ex);
		}
	}

	protected String getWhereClause(CoreUsr dto, boolean wherePrinted) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		log.debug("getWhereClause");

		try {
			if (null == dto)
				throw new ParameterException("param dto null");

			StringBuffer searchStatement = new StringBuffer();

			Principal principal = principalUtilService.getPrincipal();

			if (dto.getTMstUclass() == null || (dto.getTMstUclass() != null
					&& !StringUtils.equalsIgnoreCase(dto.getTMstUclass().getUclaCode(), "all"))) {
				if (principal != null) {
					searchStatement.append(getOperator(wherePrinted) + "o.TCoreAccn.accnId = :accnId");
					wherePrinted = true;
				}
			}

			if (dto.getTMstUclass() != null && StringUtils.equalsIgnoreCase(dto.getTMstUclass().getUclaCode(), "all")) {
				if (dto.getTMstUclass() != null
						&& StringUtils.equalsIgnoreCase(dto.getTMstUclass().getUclaDescription(), HISTORY)) {
					searchStatement.append(getOperator(wherePrinted) + "o.usrStatus IN :usrStatuses");
					wherePrinted = true;
				} else {
					searchStatement.append(getOperator(wherePrinted) + "o.usrStatus IN :usrStatuses");
					wherePrinted = true;
				}
			}

			if (!StringUtils.isEmpty(dto.getUsrUid())) {
				searchStatement.append(getOperator(wherePrinted) + "o.usrUid LIKE :usrUid");
				wherePrinted = true;
			}
			if (!StringUtils.isEmpty(dto.getUsrName())) {
				searchStatement.append(getOperator(wherePrinted) + "o.usrName LIKE :usrName");
				wherePrinted = true;
			}

			// if coreAccnId != ALL
			if ((dto.getTCoreAccn() != null) && !StringUtils.isEmpty(dto.getTCoreAccn().getAccnId())) {
				searchStatement.append(getOperator(wherePrinted) + "o.TCoreAccn.accnId LIKE :accnIdFilter");
				wherePrinted = true;
			}
			if ((dto.getTCoreAccn() != null) && !StringUtils.isEmpty(dto.getTCoreAccn().getAccnName())) {
				searchStatement.append(getOperator(wherePrinted) + "o.TCoreAccn.accnName LIKE :accnNameFilter");
				wherePrinted = true;
			}

			if (dto.getTCoreAccn().getTMstAccnType() != null) {
				if (StringUtils.isNotBlank(dto.getTCoreAccn().getTMstAccnType().getAtypId())) {
					searchStatement.append(getOperator(wherePrinted) + "o.TCoreAccn.TMstAccnType.atypId = :atypId");
					wherePrinted = true;
				}
				if (StringUtils.isNotBlank(dto.getTCoreAccn().getTMstAccnType().getAtypDescription())) {
					searchStatement.append(getOperator(wherePrinted)
							+ "o.TCoreAccn.TMstAccnType.atypDescription LIKE :atypDescription");
					wherePrinted = true;
				}
			}

			if (!StringUtils.isEmpty(dto.getUsrDept())) {
				searchStatement.append(getOperator(wherePrinted) + "o.usrDept LIKE :usrDept");
				wherePrinted = true;
			}

			if (dto.getUsrContact() != null) {
				if (!StringUtils.isEmpty(dto.getUsrContact().getContactEmail())) {
					searchStatement.append(getOperator(wherePrinted) + "o.usrContact.contactEmail LIKE :contactEmail");
					wherePrinted = true;
				}
			}

			if (Character.isAlphabetic(dto.getUsrStatus())) {
				searchStatement.append(getOperator(wherePrinted) + "o.usrStatus = :usrStatus");
				wherePrinted = true;
			}

			if (null != dto.getUsrDtCreate()) {
				searchStatement
						.append(getOperator(wherePrinted) + "DATE_FORMAT(o.usrDtCreate,'%d/%m/%Y') = :usrDtCreate");
				wherePrinted = true;
			}

			if (null != dto.getUsrDtLupd()) {
				searchStatement.append(getOperator(wherePrinted) + "DATE_FORMAT(o.usrDtLupd,'%d/%m/%Y') = :usrDtLupd");
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

	protected String getOperator(boolean whereprinted) {
		return whereprinted ? " AND " : " WHERE ";
	}

	protected CoreUsr whereDto(EntityFilterRequest filterRequest) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		log.debug("whereDto");

		try {
			if (null == filterRequest)
				throw new ParameterException("param filterRequest null");

			CoreUsr dto = new CoreUsr();
			CoreAccn coreAccn = new CoreAccn();
			MstAccnType mstAccnType = new MstAccnType();
			coreAccn.setTMstAccnType(mstAccnType);
			dto.setTCoreAccn(coreAccn);

			CoreContact coreContact = new CoreContact();
			dto.setUsrContact(coreContact);

			MstUclass uClass = new MstUclass();
			dto.setTMstUclass(uClass);
			// Use this as placeholder to display all users

			SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");

			for (EntityWhere entityWhere : filterRequest.getWhereList()) {
				Optional<String> opValue = Optional.ofNullable(entityWhere.getValue());
				if (!opValue.isPresent())
					continue;

				if (entityWhere.getAttribute().equalsIgnoreCase("usrUid"))
					dto.setUsrUid(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("usrName"))
					dto.setUsrName(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("TCoreAccn.accnId"))
					coreAccn.setAccnId(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("TCoreAccn.accnName"))
					coreAccn.setAccnName(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("TCoreAccn.TMstAccnType.atypId"))
					mstAccnType.setAtypId(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("TCoreAccn.TMstAccnType.atypDescription"))
					mstAccnType.setAtypDescription(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("usrContact.contactEmail"))
					coreContact.setContactEmail(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("usrStatus"))
					dto.setUsrStatus(opValue.get().charAt(0));
				if (entityWhere.getAttribute().equalsIgnoreCase("usrDtCreate"))
					dto.setUsrDtCreate(sdfDate.parse(opValue.get()));
				if (entityWhere.getAttribute().equalsIgnoreCase("usrDtLupd"))
					dto.setUsrDtLupd(sdfDate.parse(opValue.get()));
				if (entityWhere.getAttribute().equalsIgnoreCase("display"))
					uClass.setUclaCode(opValue.get());
				// history toggle
				if (entityWhere.getAttribute().equalsIgnoreCase(HISTORY)) {
					uClass.setUclaDescription(opValue.get());
				} else if (entityWhere.getAttribute().equalsIgnoreCase(DEFAULT)) {
					uClass.setUclaDescription(opValue.get());
				}

			}

			return dto;
		} catch (ParameterException ex) {
			log.error("whereDto", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("whereDto", ex);
			throw new ProcessingException(ex);
		}
	}

	public CoreUsr dtoFromEntity(TCoreUsr entity) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		log.debug("dtoFromEntity");

		try {
			if (null == entity)
				throw new ParameterException("param entity null");

			CoreUsr dto = new CoreUsr(entity);
			// no deep copy with BeansUtil
			Optional<TCoreAccn> opCoreAccn = Optional.ofNullable(entity.getTCoreAccn());
			dto.setTCoreAccn(opCoreAccn.isPresent() ? new CoreAccn(opCoreAccn.get()) : null);
			if (opCoreAccn.isPresent()) {
				Optional<TMstAccnType> opMstAccnType = Optional.ofNullable(entity.getTCoreAccn().getTMstAccnType());
				Optional<TCoreAddress> opCoreAddr = Optional.ofNullable(entity.getTCoreAccn().getAccnAddr());
				Optional<TCoreContact> opCoreContact = Optional.ofNullable(entity.getTCoreAccn().getAccnContact());
				dto.getTCoreAccn()
						.setTMstAccnType(opMstAccnType.isPresent() ? new MstAccnType(opMstAccnType.get()) : null);
				dto.getTCoreAccn().setAccnAddr(opCoreAddr.isPresent() ? new CoreAddress(opCoreAddr.get()) : null);
				dto.getTCoreAccn()
						.setAccnContact(opCoreContact.isPresent() ? new CoreContact(opCoreContact.get()) : null);

			}

			Optional<TCoreAddress> opCoreAddr = Optional.ofNullable(entity.getUsrAddr());
			dto.setUsrAddr(opCoreAddr.isPresent() ? new CoreAddress(opCoreAddr.get()) : null);
			if (opCoreAddr.isPresent()) {
				Optional<TMstCountry> opMstCountry = Optional.ofNullable(entity.getUsrAddr().getAddrCtry());
				dto.getUsrAddr().setAddrCtry(opMstCountry.isPresent() ? new MstCountry(opMstCountry.get()) : null);
			}

			Optional<TCoreContact> opCoreContact = Optional.ofNullable(entity.getUsrContact());
			dto.setUsrContact(opCoreContact.isPresent() ? new CoreContact(opCoreContact.get()) : null);

			Optional<TMstUclass> opMstUClass = Optional.ofNullable(entity.getTMstUclass());
			dto.setTMstUclass(opMstUClass.isPresent() ? new MstUclass(opMstUClass.get()) : null);

			// set roles
			List<TCoreUsrRole> usrRole = coreUsrRoleDao
					.getByQuery("from TCoreUsrRole t where t.TCoreUsr.usrUid='" + dto.getUsrUid() + "'");
			if (usrRole != null && usrRole.size() > 0) {
				Set<CoreRole> coreRoles = new HashSet<>();
				for (TCoreUsrRole tCoreUsrRole : usrRole) {
					CoreRole cr = new CoreRole(tCoreUsrRole.getTCoreRole());
					cr.setId(new CoreRoleId(tCoreUsrRole.getTCoreRole().getId()));
					coreRoles.add(cr);
				}
				dto.setTCoreRoles(coreRoles);
				dto.setRoles(dto.getRoleString());
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

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public List<TCoreRole> findRoles(String roleAppscode) throws Exception {

		Map<String, Object> params = new HashMap<>();
		params.put("id.roleAppscode", roleAppscode);

		List<TCoreRole> roleList = coreRoleDao.getByCriteria(params, null, null, 0, 0);

		return roleList;

	}

	/**
	 * 
	 * @param tCoreRoleList
	 * @return
	 * @throws Exception
	 */
	public List<CoreRole> convertToRoleDTO(List<TCoreRole> tCoreRoleList) throws Exception {

		if (null == tCoreRoleList || tCoreRoleList.size() == 0) {
			return null;
		}

		for (TCoreRole coreRole : tCoreRoleList) {

			Hibernate.initialize(coreRole.getId());
			Hibernate.initialize(coreRole.getTCoreApps());
		}

		List<CoreRole> coreRoleList = tCoreRoleList.stream().map(entity -> {

			CoreRole dto = new CoreRole(entity);
			dto.setId(null == entity.getId() ? null : new CoreRoleId(entity.getId()));
			dto.setTCoreApps(null == entity.getTCoreApps() ? null : new CoreApps(entity.getTCoreApps()));

			return dto;

		}).collect(Collectors.toList());

		return coreRoleList;

	}

	private boolean isActionValid(String action) {
		UserAction[] actions = UserAction.values();

		for (UserAction a : actions) {
			if (a.getAction().equalsIgnoreCase(action)) {
				return true;
			}
		}

		return false;
	}

	public void forgotPassword(String email) throws Exception {
		TCoreUsr tCoreUsr = getUserByEmail(email);
		if (tCoreUsr == null)
			throw new EntityNotFoundException("user with email does not exist. please try again");

		CoreUsr coreUsr = new CoreUsr();
		BeanUtils.copyProperties(tCoreUsr, coreUsr);
		coreUsr.setUsrContact(new CoreContact(tCoreUsr.getUsrContact()));

		String token = PasswordEncryptor.encrypt(
				coreUsr.getUsrUid().concat(":").concat(coreUsr.getUsrContact().getContactEmail()),
				PasswordGenerator.generatePassword(16));
		TCoreUsrReset tCoreUsrReset = new TCoreUsrReset();
		tCoreUsrReset.setUsrId(coreUsr.getUsrUid());
		tCoreUsrReset.setUsrEmail(coreUsr.getUsrContact().getContactEmail());
		tCoreUsrReset.setUsrToken(token);
		tCoreUsrReset.setUsrExpiredDate(DateUtils.addDays(new Date(), 1));
		tCoreUsrReset.setUsrStatus('A');
		tCoreUsrReset.setUsrDtCreate(Calendar.getInstance().getTime());
		tCoreUsrReset.setUsrUidCreate(coreUsr.getUsrUid());
		tCoreUsrReset.setUsrDtLupd(Calendar.getInstance().getTime());
		tCoreUsrReset.setUsrUidLupd(Constant.DEFAULT_USR);
		coreUsrResetDao.saveOrUpdate(tCoreUsrReset);

		// publish event
		eventPublisher.publishEvent(
				new ClickargoPostUserUpdateEvent(this, coreUsr, false, PostUserUpdateAction.RESET_PWD, token));
	}

	public void changePassword(ChangePassword dto) throws Exception {
		if (StringUtils.isBlank(dto.getNewPassword()))
			throw new ParameterException("param newPassword is null");

		if (dto.getNewPassword().length() < 8)
			throw new ParameterException("New Password length should be more than or equal to 8 characters");

		if (StringUtils.isBlank(dto.getConfirmPassword()))
			throw new ParameterException("param confirmPassword is null");

		if (!dto.getNewPassword().equals(dto.getConfirmPassword()))
			throw new ParameterException("Passwords do not match");

		if (dto.isFromManageUser()) {
			if (StringUtils.isBlank(dto.getCurrentPassword()))
				throw new ParameterException("param currentPassword is null");

			if (StringUtils.isBlank(dto.getUserId()))
				throw new ParameterException("param userId is null");

			Principal principal = principalUtilService.getPrincipal();

			if (!principal.getUserId().equalsIgnoreCase(dto.getUserId())) {
				throw new ParameterException("You only can change yourself password.");
			}

			CoreUsr coreUsr = ccmUserService.findById(dto.getUserId());
			if (coreUsr == null)
				throw new EntityNotFoundException("user does not exist");

			String currentEncryptPwd = PasswordEncryptor.encrypt(coreUsr.getUsrUid(), dto.getCurrentPassword());
			if (!coreUsr.getUsrPwd().equals(currentEncryptPwd))
				throw new ParameterException("Current password do not match");

			// Set user new password
			coreUsr.setUsrPwd(PasswordEncryptor.encrypt(coreUsr.getUsrUid(), dto.getNewPassword()));
			coreUsr.setUsrDtPwdLupd(new Date());
			ccmUserService.update(coreUsr, principal);
		} else if (dto.isForceChangePwd()) {

			if (StringUtils.isBlank(dto.getUserId()))
				throw new ParameterException("user id is null");

			TCoreUsr tCoreUsr = null;
			if (portalUser.isEmailAddress(dto.getUserId())) {
				// userId is email address, user login with email address
				tCoreUsr = portalUser.findUserIdByEmail(dto.getUserId());
			} else {
				tCoreUsr = coreUserDao.find(dto.getUserId());
			}

			if (null == tCoreUsr)
				throw new ParameterException("user id is not correct");

			String currentEncryptPwd = PasswordEncryptor.encrypt(tCoreUsr.getUsrUid(), dto.getCurrentPassword());
			if (!tCoreUsr.getUsrPwd().equals(currentEncryptPwd))
				throw new ParameterException("Current password do not match");

			// Set user new password
			tCoreUsr.setUsrPwdForce(null);
			tCoreUsr.setUsrPwd(PasswordEncryptor.encrypt(tCoreUsr.getUsrUid(), dto.getNewPassword()));
			tCoreUsr.setUsrDtPwdLupd(new Date());

			coreUserDao.update(tCoreUsr);

		} else {
			if (StringUtils.isBlank(dto.getToken()))
				throw new ParameterException("query param key is null");

			TCoreUsrReset tCoreUsrReset = getUserResetByToken(dto.getToken());
			if (tCoreUsrReset == null)
				throw new EntityNotFoundException("Sorry, this change password link is not valid.");

			if (tCoreUsrReset.getUsrStatus().equals('E'))
				throw new EntityNotFoundException("Sorry, this change password link is expired.");

			if (new Date().after(tCoreUsrReset.getUsrExpiredDate())) {
				tCoreUsrReset.setUsrStatus('E');
				tCoreUsrReset.update(tCoreUsrReset);
				throw new EntityNotFoundException("Sorry, this change password link is expired.");
			}

			TCoreUsr tCoreUsr = getUserByEmail(tCoreUsrReset.getUsrEmail());
			if (tCoreUsr == null)
				throw new EntityNotFoundException("user with email does not exist");

			// Updated record to inactive after password changed
			tCoreUsrReset.setUsrStatus('I');
			coreUsrResetDao.update(tCoreUsrReset);

			// Set user new password
			tCoreUsr.setUsrPwd(PasswordEncryptor.encrypt(tCoreUsr.getUsrUid(), dto.getNewPassword()));
			tCoreUsr.setUsrDtPwdLupd(new Date());
			coreUserDao.update(tCoreUsr);
		}
	}

	public ClickargoUser updateUserStatus(String action, String usrId)
			throws ParameterException, ProcessingException, Exception {
		log.debug("updateUserStatus");
		try {
			ClickargoUser portalUser = new ClickargoUser();
			Principal principal = principalUtilService.getPrincipal();
			if (principal == null)
				throw new Exception("principal is null");

			if (StringUtils.isEmpty(action))
				throw new ParameterException("action is null or empty");

			if (StringUtils.isEmpty(usrId))
				throw new ParameterException("usrId is null or empty");

//			if (!isActionValid(action))
//				throw new ProcessingException("invalid action");

			CoreUsr usr = ccmUserService.findById(usrId);
			if (action.equalsIgnoreCase("activate") || action.equalsIgnoreCase("deactivate")) {

				// reset the login no. of retries
				if (usr.getUsrStatus() == 'S') {
					usr.setUsrLoginInvcnt(0);
				}

				usr.setUsrStatus(action.equalsIgnoreCase("activate") ? RecordStatus.ACTIVE.getCode()
						: RecordStatus.INACTIVE.getCode());
				CoreUsr usrUp = ccmUserService.update(usr, principal);
				portalUser.setCoreUsr(usrUp);
			}

			if (action.equalsIgnoreCase("unsuspend") || action.equalsIgnoreCase("suspend")) {

				// reset the login no. of retries
				if (usr.getUsrStatus() == 'S') {
					usr.setUsrLoginInvcnt(0);
				}

				usr.setUsrStatus(action.equalsIgnoreCase("unsuspend") ? RecordStatus.ACTIVE.getCode()
						: RecordStatus.SUSPENDED.getCode());
				CoreUsr usrUp = ccmUserService.update(usr, principal);
				portalUser.setCoreUsr(usrUp);
			}

			return portalUser;
		} catch (Exception e) {
			log.error("updateUserStatus", e);
			throw e;
		}
	}

	/// Helper Methods
	///////////////////////
	private TCoreUsr getUserByEmail(String email) throws EntityNotFoundException {
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("email", email);
			String sql = "SELECT o FROM TCoreUsr o WHERE o.usrContact.contactEmail = :email";
			List<TCoreUsr> tCoreUsrs = coreUserDao.getByQuery(sql, param);
			if (tCoreUsrs == null || tCoreUsrs.size() == 0) {
				throw new Exception("Fail to find email adress " + email);
			} else if (tCoreUsrs.size() > 1) {
				throw new Exception(
						"Can't reset password with this email address, because more users with the email address: "
								+ email);
			} else {
				TCoreUsr coreUsr = tCoreUsrs.get(0);
				if (coreUsr.getUsrStatus() != Constant.ACTIVE_STATUS) {
					throw new Exception(email + " is locked. Please contact administrator.");
				}
				return tCoreUsrs.get(0);
			}
		} catch (Exception ex) {
			log.error("getUserByEmail", ex);
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	private TCoreUsrReset getUserResetByToken(String token) throws EntityNotFoundException {
		try {
			TCoreUsrReset tCoreUsrReset = new TCoreUsrReset();
			Map<String, Object> param = new HashMap<>();
			param.put("token", token);
			String sql = "SELECT o FROM TCoreUsrReset o WHERE o.usrToken = :token AND o.usrStatus = 'A'";
			List<TCoreUsrReset> tCoreUsrResets = coreUsrResetDao.getByQuery(sql, param);
			if (!tCoreUsrResets.isEmpty()) {
				tCoreUsrReset = tCoreUsrResets.get(0);
				return tCoreUsrReset;
			}
			return null;
		} catch (Exception ex) {
			log.error("getUserByEmail", ex);
			throw new EntityNotFoundException(ex);
		}
	}

	private void initUser(CoreUsr coreUsr, Principal principal) {
		coreUsr.setUsrStatus('A');
		coreUsr.setUsrDtReg(new Date());
		coreUsr.setUsrDtComm(new Date());
		coreUsr.setUsrDtPwdLupd(new Date());
		coreUsr.setUsrMboxId("Default");
		coreUsr.setUsrTypeMbox("A");
		coreUsr.setUsrTypeOnline("A");
		coreUsr.setUsrUidCreate(principal.getUserId());
		coreUsr.setUsrDtCreate(new Date());
	}

	private String getCoreAppsCode(Principal principal) throws Exception {
		try {

			if (principal == null)
				throw new Exception("principal is null");

			String hql = "FROM TCoreApps o WHERE o.appsUriBasePath=:appsUriBasePath AND o.appsStatus='A'";
			Map<String, Object> params = new HashMap<>();
			params.put("appsUriBasePath", principal.getContextUrl());
			List<TCoreApps> coreAppsList = coreAppsDao.getByQuery(hql, params);
			if (coreAppsList != null && coreAppsList.size() > 0) {
				// should only return one
				TCoreApps app = coreAppsList.get(0);
				return app.getAppsCode();
			}

		} catch (Exception e) {
			log.error("getCoreAppsCode", e);
			throw e;
		}

		// return default CONE
		return appCode;

	}

	public void addUsrRole(String urourolUid, String urolAppscode, String urolRoleid) throws Exception {

		TCoreUsrRole usrRole = new TCoreUsrRole(
				new TCoreUsrRoleId(urourolUid, urolAppscode, urolRoleid, RecordStatus.ACTIVE.getCode()), null, null,
				RecordStatus.ACTIVE.getCode(), new Date(), "SYS", new Date(), "SYS");
		// Added below to not save a blank urol temp role value
		usrRole.setUrolTempRole('N');

		coreUsrRoleDao.saveOrUpdate(usrRole);
	}

	// Helper Methods
	//////////////////////

	private List<CoreRole> getUserRoles(Principal principal) throws Exception {

		StringBuilder hql = new StringBuilder(
				"FROM TCoreRole o WHERE o.roleStatus = :roleStatus AND o.TCoreApps.appsCode = :appsCode");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleStatus", RecordStatus.ACTIVE.getCode());
		params.put("appsCode", principal.getAppsCode());
		if (principal.getRoleList().contains(Roles.ADMIN.name())) {
			hql.append(" AND o.id.roleId <> :roleId");
			params.put("roleId", Roles.SYS_SUPER_ADMIN.name());
		}

		// Show the roles based on principal account type
		if (principal.getCoreAccn().getTMstAccnType().getAtypId().equalsIgnoreCase(AccountTypes.ACC_TYPE_FF.name())
				|| principal.getCoreAccn().getTMstAccnType().getAtypId()
						.equalsIgnoreCase(AccountTypes.ACC_TYPE_CO.name())) {
			hql.append(" AND o.id.roleId IN :roleIds");
			params.put("roleIds", Arrays.asList(Roles.ADMIN.name(), Roles.FF_FINANCE.name(), Roles.OFFICER.name(),
					Roles.FF_ADMIN.name()));
		}
		if (principal.getCoreAccn().getTMstAccnType().getAtypId().equalsIgnoreCase(AccountTypes.ACC_TYPE_TO.name())) {
			hql.append(" AND o.id.roleId IN :roleIds");
			params.put("roleIds", Arrays.asList(Roles.ADMIN.name(), Roles.OP_ADMIN.name(), Roles.OFFICER.name()));
		}
		if (principal.getCoreAccn().getTMstAccnType().getAtypId()
				.equalsIgnoreCase(AccountTypes.ACC_TYPE_FF_CO.name())) {
			hql.append(" AND o.id.roleId IN :roleIds");
			params.put("roleIds", Arrays.asList(Roles.FF_CO_ADMIN.name()));
		}
		if (principal.getCoreAccn().getTMstAccnType().getAtypId().equalsIgnoreCase(AccountTypes.ACC_TYPE_SP.name())) {
			hql.append(" AND o.id.roleId IN :roleIds");
			params.put("roleIds", Arrays.asList(Roles.ADMIN.name(), Roles.SP_FIN_ADMIN.name(), Roles.SP_FIN_HD.name(),
					Roles.SP_OP_ADMIN.name(), Roles.SP_BZ_HD.name(), Roles.SP_COM.name(), Roles.SP_L1.name()));
		}
		// CT2SG-69
		if (principal.getCoreAccn().getTMstAccnType().getAtypId()
				.equalsIgnoreCase(AccountTypes.ACC_TYPE_TO_WJ.name())) {
			hql.append(" AND o.id.roleId IN :roleIds");
			params.put("roleIds", Arrays.asList(Roles.OP_ADMIN_WJ.name(), Roles.OFFICER.name()));
		}

		List<CoreRole> list = null;
		List<TCoreRole> coreRoleList = coreRoleDao.getByQuery(hql.toString(), params);
		if (coreRoleList != null && coreRoleList.size() > 0) {
			list = coreRoleList.stream().map(e -> {
				Hibernate.isInitialized(e.getId());
				CoreRole role = new CoreRole(e);
				role.setId(new CoreRoleId(e.getId()));
				return role;
			}).collect(Collectors.toList());
		}

		return list;
	}

	private boolean isExistingUserEmail(String usrId, CoreUsr coreUsr) {
		boolean exists = false;
		try {

			Map<String, Object> param = new HashMap<>();
			param.put("email", coreUsr.getUsrContact().getContactEmail());
			String sql = "SELECT o FROM TCoreUsr o WHERE o.usrContact.contactEmail = :email ";
			List<TCoreUsr> tCoreUsrs = coreUserDao.getByQuery(sql, param);
			if (tCoreUsrs != null && tCoreUsrs.size() > 0) {
				for (TCoreUsr tCoreUsr : tCoreUsrs) {
					if (!tCoreUsr.getUsrUid().equals(usrId)) {
						exists = true;
						break;
					}
				}
			}
		} catch (Exception e) {
		}
		return exists;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public List<CoreRole> findRolesByAccId(String accId) throws Exception {
		TCoreAccn tCoreAccn = coreAccDao.find(accId);
		List<TCoreRole> usrRole = new ArrayList<>();
		if (Objects.nonNull(tCoreAccn)) {
			TMstAccnType accType = tCoreAccn.getTMstAccnType();
			usrRole = getRoleListByAccnType(tCoreAccn.getAccnId(), accType.getAtypId());
		}
		return convertToRoleDTO(usrRole);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public List<CoreRole> findRolesByAccnIdAndSvcSub(String accId) throws Exception {
		TCoreAccn tCoreAccn = coreAccDao.find(accId);
		Principal principal = principalUtilService.getPrincipal();

		return findRolesByAccnBySvcSub(tCoreAccn, principal);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public List<CoreRole> findRolesByAccnBySvcSub(TCoreAccn accn, Principal principal) throws Exception {
		if (accn == null)
			throw new ParameterException("param accn null");

		TCoreAccn tCoreAccn = coreAccDao.find(accn.getAccnId());

		// Get the service subscribed to the account
		List<TCkSvcSub> svcSubList = svcSubDao.findSubscribedAppSvc(accn.getAccnId());
		List<String> appsCodeSubList = new ArrayList<>();

		if (svcSubList != null && svcSubList.size() > 0) {
			// Get the corresponding appscode of the subscribed list
			appsCodeSubList = svcSubList.stream().map(e -> {
				Hibernate.initialize(e);
				Hibernate.initialize(e.getTCkMstServiceType());
				return ServiceTypes.getAppsCodeByServiceType(e.getTCkMstServiceType().getSvctId());
			}).filter(e -> e != null).collect(Collectors.toList());
		}

		// Check if the principal appscode is the same as the account subscribed
		// appscode, then pass it to the appscode below to get the roles
		// This is to get the subscribed app (clictruck, clicdo, etc) roles.
		// Default to principal appscode in case the account does not have svc
		// subscription yet. Principal can either be the account or the SP admin
		// but SP_ADMIN will likely go to the specific app as there are no common
		//manage user yet.
		String subAppCode = appsCodeSubList.stream().filter(e -> e.equalsIgnoreCase(principal.getAppsCode())).findAny()
				.orElse(principal.getAppsCode());

		List<TCoreRole> usrRole = new ArrayList<>();
		if (Objects.nonNull(tCoreAccn)) {
			TMstAccnType accType = tCoreAccn.getTMstAccnType();
			usrRole = getRoleListByAccnTypeAndAppsCode(tCoreAccn.getAccnId(), accType.getAtypId(), subAppCode);
		}
		return convertToRoleDTO(usrRole);
	}

	private List<TCoreRole> getRoleListByAccnType(String accnId, String accnType) {
		String roleList = env.getProperty(accnType);
		List<TCoreRole> usrRole = new ArrayList<>();

		if (null == roleList)
			return usrRole;

		Set<String> roleSet = new HashSet<>(Arrays.asList(roleList.split(",")));
		Map<String, String> allRole = getAllRolesMap();
		for (String roleId : roleSet) {
			TCoreRole tCoreRole = new TCoreRole();
			TCoreRoleId id = new TCoreRoleId();
			id.setRoleId(roleId);
			id.setRoleAppscode(ServiceTypes.CLICTRUCK.getAppsCode());
			tCoreRole.setId(id);
			tCoreRole.setRoleDesc(allRole.get(roleId));
			usrRole.add(tCoreRole);
		}

		try {
			Principal principal = principalUtilService.getPrincipal();
			if (principal != null) {
				if (!StringUtils.equalsIgnoreCase(principal.getCoreAccn().getAccnId(), accnId)) {
					if (!isUserSysAdmin(principal.getUserId())) {
						// Remove the sys admin from the list
						return usrRole.stream()
								.filter(el -> !el.getId().getRoleId().equalsIgnoreCase(Roles.SYS_SUPER_ADMIN.name()))
								.collect(Collectors.toList());
					}
				}

			}
		} catch (Exception e) {
			log.error("getRoleListByAccnType", e);
		}

		return usrRole;
	}

	private List<TCoreRole> getRoleListByAccnTypeAndAppsCode(String accnId, String accnType, String appCode) {
		String roleList = env.getProperty(accnType);
		List<TCoreRole> usrRole = new ArrayList<>();

		if (null == roleList)
			return usrRole;

		Set<String> roleSet = new HashSet<>(Arrays.asList(roleList.split(",")));
		Map<String, String> allRole = getAllRolesMap(appCode);
		for (String roleId : roleSet) {
			TCoreRole tCoreRole = new TCoreRole();
			TCoreRoleId id = new TCoreRoleId();
			id.setRoleId(roleId);
			id.setRoleAppscode(appCode);
			tCoreRole.setId(id);
			tCoreRole.setRoleDesc(allRole.get(roleId));
			usrRole.add(tCoreRole);
		}

		try {
			Principal principal = principalUtilService.getPrincipal();
			if (principal != null) {
				if (!StringUtils.equalsIgnoreCase(principal.getCoreAccn().getAccnId(), accnId)) {
					if (!isUserSysAdmin(principal.getUserId())) {
						// Remove the sys admin from the list
						return usrRole.stream()
								.filter(el -> !el.getId().getRoleId().equalsIgnoreCase(Roles.SYS_SUPER_ADMIN.name()))
								.collect(Collectors.toList());
					}
				}

			}
		} catch (Exception e) {
			log.error("getRoleListByAccnType", e);
		}

		return usrRole;
	}

	private boolean isUserSysAdmin(String usrUid) throws Exception {
		if (StringUtils.isEmpty(usrUid))
			throw new ParameterException("usrUid is null or empty");

		String hql = "FROM TCoreUsrRole o WHERE o.id.urolUid=:urolUid AND o.urolStatus=:urolStatus AND o.id.urolRoleid IN (:rolesId)";
		Map<String, Object> params = new HashMap<>();
		params.put("urolUid", usrUid);
		params.put("urolStatus", RecordStatus.ACTIVE.getCode());
		params.put("rolesId", Arrays.asList(Roles.SYS_SUPER_ADMIN.getDesc()));
		List<TCoreUsrRole> usrRolesList = coreUsrRoleDao.getByQuery(hql, params);
		if (usrRolesList != null && usrRolesList.size() > 0)
			return true;
		return false;
	}

	private Map<String, String> getAllRolesMap() {
		Map<String, Object> params = new HashMap<>();
		params.put("id.roleAppscode", ServiceTypes.CLICTRUCK.getAppsCode());
		Map<String, String> map = new HashMap<>();
		try {
			List<TCoreRole> roleList = coreRoleDao.getByCriteria(params, null, null, 0, 0);
			roleList.forEach(tCoreRole -> {
				map.put(tCoreRole.getId().getRoleId(), tCoreRole.getRoleDesc());
			});
		} catch (Exception e) {
			log.error("getAllRolesMap", e);
		}
		return map;
	}

	private Map<String, String> getAllRolesMap(String appsCode) {
		Map<String, Object> params = new HashMap<>();
		params.put("id.roleAppscode", appsCode);
		Map<String, String> map = new HashMap<>();
		try {
			List<TCoreRole> roleList = coreRoleDao.getByCriteria(params, null, null, 0, 0);
			roleList.forEach(tCoreRole -> {
				map.put(tCoreRole.getId().getRoleId(), tCoreRole.getRoleDesc());
			});
		} catch (Exception e) {
			log.error("getAllRolesMap", e);
		}
		return map;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public ClickargoUser getByIdAndEmail(String usrId, String email) throws Exception {
		try {

			if (StringUtils.isEmpty(usrId))
				throw new ParameterException("usrId is empty or null");

			if (StringUtils.isEmpty(email))
				throw new ParameterException("email is empty or null");

			Principal principal = principalUtilService.getPrincipal();
			if (principal == null)
				throw new Exception("principal is null");

			if (portalUserService.isEmailAddress(email)) {
//				TCoreUsr tCoreUsr = portalUserService.findUserIdByEmail(email);
				Optional<TCoreUsr> tCoreUsr = this.findByEmail(email);

				PortalUser portalUser = portalUserService.getUser(tCoreUsr.get().getUsrUid(), principal);
				if (Objects.nonNull(tCoreUsr) && Objects.nonNull(portalUser)) {
					ClickargoUser usr = new ClickargoUser();
					usr.setCoreUsr(portalUser.getCoreUsr());
					return usr;
				}
			}

			return null;

		} catch (Exception ex) {
			log.error("getUser", ex);
			throw ex;
		}
	}

	public Optional<TCoreUsr> findByEmail(String email) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(TCoreUsr.class);
		criteria.add(Restrictions.eq("usrContact.contactEmail", email));
		return Optional.ofNullable(coreUserDao.getOne(criteria));
	}

	// Helper Methods
//	private List<TCoreUsr> filterByAppsCodeViaRole(List<TCoreUsr> usersList) throws Exception {
//		if (usersList != null) {
//
//			Principal principal = principalUtilService.getPrincipal();
//			if (principal == null)
//				throw new ProcessingException("principal null or empty");
//
//			List<String> users = usersList.stream().map(usr -> usr.getUsrUid()).collect(Collectors.toList());
//			String hql = "from TCoreUsrRole o where o.id.urolUid in (:users) and  o.id.urolAppscode=:appsCode";
//			Map<String, Object> params = new HashMap<>();
//			params.put("users", users);
//			params.put("appsCode", principal.getAppsCode());
//
//			List<TCoreUsrRole> usrRoleList = coreUsrRoleDao.getByQuery(hql, params);
//			if (usrRoleList != null && usrRoleList.size() > 0) {
//				// filter the roles by user
//				List<String> strRolesByUser = usrRoleList.stream().map(e -> e.getId().getUrolUid()).distinct()
//						.collect(Collectors.toList());
//				return usersList.stream().filter(e -> strRolesByUser.contains(e.getUsrUid()))
//						.collect(Collectors.toList());
//			}
//		}
//
//		return usersList;
//	}

}
