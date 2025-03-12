package com.guudint.clickargo.security.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.common.dto.CkUnGrantAccountExId;
import com.guudint.clickargo.common.dto.CkUnGrantUserExId;
import com.guudint.clickargo.common.model.TCkUnGrantAccountEx;
import com.guudint.clickargo.common.model.TCkUnGrantAccountExId;
import com.guudint.clickargo.common.model.TCkUnGrantUserEx;
import com.guudint.clickargo.common.model.TCkUnGrantUserExId;
import com.vcc.camelone.cac.dto.CoreModule;
import com.vcc.camelone.cac.dto.CoreModuleService;
import com.vcc.camelone.cac.dto.CorePermEx;
import com.vcc.camelone.cac.dto.CoreRoleId;
import com.vcc.camelone.cac.dto.MstPermAction;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.cac.model.TCoreGrantRoleEx;
import com.vcc.camelone.cac.model.TCoreGrantRoleExId;
import com.vcc.camelone.cac.model.TCoreModule;
import com.vcc.camelone.cac.model.TCoreModuleService;
import com.vcc.camelone.cac.model.TCorePermEx;
import com.vcc.camelone.cac.model.TCoreUsrRole;
import com.vcc.camelone.cac.model.TCoreUsrRoleId;
import com.vcc.camelone.cac.services.IAuthorize;
import com.vcc.camelone.cac.services.PermExMenu;
import com.vcc.camelone.cac.services.PermExUri;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.core.dto.CoreApps;
import com.vcc.camelone.master.dto.MstDocCode;
import com.vcc.camelone.master.dto.MstDocType;
import com.vcc.camelone.menu.dto.CoreMenuEx;
import com.vcc.camelone.menu.model.TCoreMenuEx;
import com.vcc.camelone.util.PrincipalUtilService;

@Service("clickargoAuthService")
public class ClickargoAuthService implements IAuthorize {

	// Static Attributes
	////////////////////
	private static Logger log = Logger.getLogger(ClickargoAuthService.class);
	@Autowired
	private GenericDao<TCoreModule, String> coreModuleDao;
	@Autowired
	private GenericDao<TCoreModuleService, String> coreModuleServiceDao;
	@Autowired
	private GenericDao<TCoreMenuEx, String> coreMenuExDao;
	@Autowired
	private GenericDao<TCorePermEx, String> corePermExDao;
	@Autowired
	private GenericDao<TCoreGrantRoleEx, TCoreGrantRoleExId> coreGrantRoleExDao;
	@Autowired
	private GenericDao<TCoreUsrRole, TCoreUsrRoleId> coreUsrRoleDao;
	@Autowired
	private GenericDao<TCkUnGrantAccountEx, TCkUnGrantAccountExId> ckGrantAccountExDao;
	@Autowired
	private GenericDao<TCkUnGrantUserEx, TCkUnGrantUserExId> ckGrantUserExDao;

	@Autowired
	protected PrincipalUtilService principalUtilService;

	@SuppressWarnings("unused")
	private boolean bInit = false;
	@SuppressWarnings("unused")
	private Optional<Map<String, CoreModule>> opCoreModules;
	@SuppressWarnings("unused")
	private Optional<List<CoreModuleService>> opCoreModuleServices;
	private Optional<List<CoreMenuEx>> opCoreMenuExs;
	@SuppressWarnings("unused")
	private Optional<List<CorePermEx>> opCorePermExs;
	private Optional<Map<CoreRoleId, List<CorePermEx>>> opCoreGrantRoleExs;
	private Optional<Map<CkUnGrantAccountExId, List<CorePermEx>>> opCkGrantAccountExs;
	private Optional<Map<CkUnGrantUserExId, List<CorePermEx>>> opckGrantUserExs;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public List<PermExMenu> getPermMenu(Principal principal, boolean isTranslate)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		log.debug("getPermMenu");

		try {

			if (StringUtils.isEmpty(principal.getUserId()))
				throw new ProcessingException("principal userId null or empty");

			if (StringUtils.isEmpty(principal.getAppsCode()))
				throw new ProcessingException("principal appscode null or empty");

			this.init(principal.getAppsCode());
			if (!opCoreMenuExs.isPresent() || opCoreMenuExs.get().isEmpty())
				throw new ProcessingException("opCoreMenuExs null or empty");

			this.initCoreGrantRoleExs(principal.getAppsCode());
			if (!opCoreGrantRoleExs.isPresent() || opCoreGrantRoleExs.get().isEmpty())
				throw new ProcessingException("opCoreGrantRoleExs null or empty");

			// no need to check if they are empty, because these are optional configurations
			this.initCkGrantAccountExs(principal.getAppsCode());
			this.initCkGrantUserExs(principal.getAppsCode());

			// Get the principal accnId and user
			String pAccnId = principal.getCoreAccn().getAccnId();
			String pUsrId = principal.getUserId();

			Map<CoreRoleId, List<CorePermEx>> filteredGrantRoles = filterGrantRoleEx(pAccnId, pUsrId);

			String hql = "from TCoreUsrRole o where o.id.urolUid = :userId and o.id.urolAppscode=:appsCode";
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("userId", principal.getUserId());
			parameters.put("appsCode", principal.getAppsCode());
			List<TCoreUsrRole> tCoreUsrRoles = coreUsrRoleDao.getByQuery(hql, parameters);

			Set<String> uniqueMenuIds = new TreeSet<>();
			tCoreUsrRoles.stream().forEach(x -> {
				Hibernate.initialize(x.getTCoreRole());
				Hibernate.initialize(x.getTCoreRole().getId());
				CoreRoleId coreRoleId = new CoreRoleId();
				coreRoleId.setRoleAppscode(x.getTCoreRole().getId().getRoleAppscode());
				coreRoleId.setRoleId(x.getTCoreRole().getId().getRoleId());

				// Iterate through the tcoreGrantRoleEx table to load all permissions by
				// matching roleId

				if (filteredGrantRoles.get(coreRoleId) != null) {
					filteredGrantRoles.get(coreRoleId).stream().forEach(y -> {
						if (y.getPermType() != 'M' || null == y.getTCoreMenuEx())
							return;
						try {

							uniqueMenuIds.add(y.getTCoreMenuEx().getMenuId());
							this.getParentMenuIds(y.getTCoreMenuEx().getMenuId()).stream()
									.forEach(z -> uniqueMenuIds.add(z));
							log.debug("Parent: " + uniqueMenuIds.stream().collect(Collectors.joining(":")));
							if (y.getPermGroup() == 'Y' || y.getPermGroup() == 'y')
								this.getChildMenuIds(y.getTCoreMenuEx().getMenuId()).stream()
										.forEach(z -> uniqueMenuIds.add(z));
							log.debug("Child: " + uniqueMenuIds.stream().collect(Collectors.joining(":")));

						} catch (Exception ex) {
							log.error("getPermMenu", ex);
						}
					});
				}

			});
			return this.getMenuNav(uniqueMenuIds, isTranslate);
		} catch (ParameterException ex) {
			log.error("getPermUris", ex);
			throw ex;
		} catch (ProcessingException ex) {
			log.error("initDocTypeDoc", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("initDocTypeDoc", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	public void initPermission() throws ProcessingException {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<PermExUri> getPermUris(String userId) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PermExMenu> getPermMenu(String userId, boolean isTranslate)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PermExMenu> getFullMenu(boolean isNameOthers) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PermExMenu> getPermMenuByRoleId(String roleId, String appCode, boolean isNameOthers)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateMenuByRoleId(String roleId, String appCode, List<String> menuId) throws Exception {
		// TODO Auto-generated method stub

	}

	// Helper Methods
	//////////////////////

	/**
	 * Initalise all the data
	 */
	private void init(String appsCode) {
		try {

			bInit = true;
			this.initCoreModules();
			this.initCoreModuleServices();
			this.initCoreMenuExs(appsCode);
			this.initCorPermExs(appsCode);
			this.initCkGrantAccountExs(appsCode);
			this.initCkGrantUserExs(appsCode);
		} catch (Exception ex) {
			log.error("postConstruct", ex);
		}
	}

	/**
	 * Get all the T_CORE_MODULE
	 * 
	 * @throws ParameterException
	 * @throws ProcessingException
	 */
	private void initCoreModules() throws ProcessingException {
		log.debug("initCoreModules");

		try {
			Optional<List<TCoreModule>> opTCoreModules = Optional.ofNullable(coreModuleDao.getAll());
			if (!opTCoreModules.isPresent() || opTCoreModules.get().isEmpty())
				throw new ProcessingException("TCoreDocTypeDoc empty or null");

			opTCoreModules.get().stream().forEach(tCoreModule -> {
				Hibernate.initialize(tCoreModule.getTCoreApps());
			});
			this.opCoreModules = Optional.ofNullable(opTCoreModules.get().stream()
					.collect(Collectors.toMap(tCoreModule -> tCoreModule.getModId(), tCoreModule -> {
						CoreModule coreModule = new CoreModule(tCoreModule);
						coreModule.setTCoreApps(new CoreApps(tCoreModule.getTCoreApps()));
						return coreModule;
					})));
		} catch (ProcessingException ex) {
			log.error("initCoreModules", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("initCoreModules", ex);
			throw new ProcessingException(ex);
		}
	}

	/**
	 * Get all the T_CORE_MODULE_SERVICE
	 * 
	 * @throws ParameterException
	 * @throws ProcessingException
	 */
	private void initCoreModuleServices() throws ProcessingException {
		log.debug("initCoreModuleServices");

		try {
			Optional<List<TCoreModuleService>> opTCoreModuleServices = Optional
					.ofNullable(coreModuleServiceDao.getAll());
			if (!opTCoreModuleServices.isPresent() || opTCoreModuleServices.get().isEmpty())
				throw new ProcessingException("opTCoreModuleServices empty or null");

			this.opCoreModuleServices = Optional
					.ofNullable(opTCoreModuleServices.get().stream().map(tCoreModuleService -> {
						try {
							CoreModuleService coreModuleService = new CoreModuleService(tCoreModuleService);
							Hibernate.initialize(tCoreModuleService.getTCoreModule());
							CoreModule coreModule = new CoreModule(tCoreModuleService.getTCoreModule());
							Hibernate.initialize(tCoreModuleService.getTCoreModule().getTCoreApps());
							coreModule.setTCoreApps(new CoreApps(tCoreModuleService.getTCoreModule().getTCoreApps()));
							coreModuleService.setTCoreModule(coreModule);
							return coreModuleService;
						} catch (Exception ex) {
							log.error("initCoreModuleServices", ex);
							return null;
						}
					}).collect(Collectors.toList()));

		} catch (ProcessingException ex) {
			log.error("initCoreModuleServices", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("initCoreModuleServices", ex);
			throw new ProcessingException(ex);
		}
	}

	/**
	 * Get all the T_CORE_MENU_EX
	 * 
	 * @throws ParameterException
	 * @throws ProcessingException
	 */
	private void initCoreMenuExs(String appsCode) throws ProcessingException {
		log.debug("initCoreMenuExs");

		try {
			String hql = "from TCoreMenuEx o where o.TCoreApps.appsCode=:appsCode order by o.menuSeq";
			Map<String, Object> params = new HashMap<>();
			params.put("appsCode", appsCode);
			Optional<List<TCoreMenuEx>> opTCoreMenus = Optional.ofNullable(coreMenuExDao.getByQuery(hql, params));
			if (!opTCoreMenus.isPresent() || opTCoreMenus.get().isEmpty())
				return;

			this.opCoreMenuExs = Optional.ofNullable(opTCoreMenus.get().stream().map(tCoreMenuEx -> {
				try {
					CoreMenuEx coreMenuEx = new CoreMenuEx(tCoreMenuEx);
					Hibernate.initialize(tCoreMenuEx.getTCoreApps());
					coreMenuEx.setTCoreApps(new CoreApps(tCoreMenuEx.getTCoreApps()));
					Hibernate.initialize(tCoreMenuEx.getTCoreMenuEx());
					coreMenuEx.setTCoreMenuEx(new CoreMenuEx(tCoreMenuEx.getTCoreMenuEx()));
					return coreMenuEx;
				} catch (Exception ex) {
					log.error("initCoreMenus", ex);
					return null;
				}
			}).sorted((a, b) -> a.getMenuId().compareTo(b.getMenuId())).collect(Collectors.toList()));
		} catch (ProcessingException ex) {
			log.error("initCoreMenuExs", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("initCoreMenuExs", ex);
			throw new ProcessingException(ex);
		}
	}

	/**
	 * Get all the T_CORE_PERM_EX
	 * 
	 * @throws ParameterException
	 * @throws ProcessingException
	 */
	private void initCorPermExs(String appsCode) throws ProcessingException {
		log.debug("initCorPermExs");

		try {

			String hql = " from TCorePermEx o where o.TCoreApps.appsCode=:appsCode";
			Map<String, Object> params = new HashMap<>();
			params.put("appsCode", appsCode);
			Optional<List<TCorePermEx>> opTCorePermExs = Optional.ofNullable(corePermExDao.getByQuery(hql, params));
			if (!opTCorePermExs.isPresent() || opTCorePermExs.get().isEmpty())
				return;

			this.opCorePermExs = Optional.ofNullable(opTCorePermExs.get().stream().map(tCorePermEx -> {
				try {
					CorePermEx corePermEx = new CorePermEx(tCorePermEx);
					Hibernate.initialize(tCorePermEx.getTCoreApps());
					corePermEx.setTCoreApps(new CoreApps(tCorePermEx.getTCoreApps()));
					Hibernate.initialize(tCorePermEx.getTCoreMenuEx());
					corePermEx.setTCoreMenuEx(new CoreMenuEx(tCorePermEx.getTCoreMenuEx()));
					Hibernate.initialize(tCorePermEx.getTCoreModule());
					corePermEx.setTCoreModule(new CoreModule(tCorePermEx.getTCoreModule()));
					Hibernate.initialize(tCorePermEx.getTCoreModuleService());
					corePermEx.setTCoreModuleService(new CoreModuleService(tCorePermEx.getTCoreModuleService()));
					Hibernate.initialize(tCorePermEx.getTMstDocType());
					corePermEx.setTMstDocType(new MstDocType(tCorePermEx.getTMstDocType()));
					Hibernate.initialize(tCorePermEx.getTMstDocCode());
					corePermEx.setTMstDocCode(new MstDocCode(tCorePermEx.getTMstDocCode()));
					Hibernate.initialize(tCorePermEx.getTMstPermAction());
					corePermEx.setTMstPermAction(new MstPermAction(tCorePermEx.getTMstPermAction()));
					return corePermEx;
				} catch (Exception ex) {
					log.error("initCorPermExs", ex);
					return null;
				}
			}).collect(Collectors.toList()));
		} catch (ProcessingException ex) {
			log.error("initCorPermExs", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("initCorPermExs", ex);
			throw new ProcessingException(ex);
		}
	}

	/**
	 * Get all the T_CORE_MODULE
	 * 
	 * @throws ParameterException
	 * @throws ProcessingException
	 */
	private void initCoreGrantRoleExs(String appsCode) throws ProcessingException {
		log.debug("initCoreGrantRoleExs");

		try {
			String hql = "from TCoreGrantRoleEx o where o.id.gntrlexAppsCode=:appsCode";
			Map<String, Object> params = new HashMap<>();
			params.put("appsCode", appsCode);
			Optional<List<TCoreGrantRoleEx>> opTCoreGrantRoleExs = Optional
					.ofNullable(coreGrantRoleExDao.getByQuery(hql, params));
			if (!opTCoreGrantRoleExs.isPresent() || opTCoreGrantRoleExs.get().isEmpty())
				return;

			log.debug("opTCoreGrantRoleExs.size(): " + opTCoreGrantRoleExs.get().size());

			Map<CoreRoleId, List<CorePermEx>> mCoreGrantRoleEx = new HashMap<>();
			opTCoreGrantRoleExs.get().stream().forEach(x -> {
				Hibernate.initialize(x.getTCoreRole());
				Hibernate.initialize(x.getTCoreRole().getId());
				Hibernate.initialize(x.getTCorePermEx());
				Hibernate.initialize(x.getTCorePermEx().getTCoreApps());
				Hibernate.initialize(x.getTCorePermEx().getTCoreModule());
				Hibernate.initialize(x.getTCorePermEx().getTMstDocType());
				Hibernate.initialize(x.getTCorePermEx().getTMstDocCode());
				Hibernate.initialize(x.getTCorePermEx().getTCoreModuleService());
				Hibernate.initialize(x.getTCorePermEx().getTCoreMenuEx());
				Hibernate.initialize(x.getTCorePermEx().getTMstPermAction());

				CoreRoleId coreRoleId = new CoreRoleId();
				coreRoleId.setRoleAppscode(x.getTCoreRole().getId().getRoleAppscode());
				coreRoleId.setRoleId(x.getTCoreRole().getId().getRoleId());
				log.debug("coreRoleId:" + coreRoleId.toString());
				List<CorePermEx> corePermExs = mCoreGrantRoleEx.get(coreRoleId);
				if (null == corePermExs) {
					corePermExs = new ArrayList<>();
					mCoreGrantRoleEx.put(coreRoleId, corePermExs);
				}
				CorePermEx corePermEx = new CorePermEx(x.getTCorePermEx());
				corePermEx.setTCoreApps(new CoreApps((x.getTCorePermEx().getTCoreApps())));
				corePermEx.setTCoreModule(new CoreModule(x.getTCorePermEx().getTCoreModule()));
				corePermEx.setTMstDocType(new MstDocType(x.getTCorePermEx().getTMstDocType()));
				corePermEx.setTMstDocCode(new MstDocCode(x.getTCorePermEx().getTMstDocCode()));
				corePermEx.setTCoreModuleService(new CoreModuleService(x.getTCorePermEx().getTCoreModuleService()));
				corePermEx.setTCoreMenuEx(new CoreMenuEx(x.getTCorePermEx().getTCoreMenuEx()));
				corePermEx.setTMstPermAction(new MstPermAction(x.getTCorePermEx().getTMstPermAction()));
				corePermExs.add(corePermEx);
			});
			this.opCoreGrantRoleExs = Optional.ofNullable(mCoreGrantRoleEx);
			log.debug("opCoreGrantRoleExs.keyset().size(): " + opCoreGrantRoleExs.get().keySet().size());
		} catch (ProcessingException ex) {
			log.error("initCoreGrantRoleExs", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("initCoreGrantRoleExs", ex);
			throw new ProcessingException(ex);
		}
	}

	private void initCkGrantAccountExs(String appsCode) throws ProcessingException {
		log.debug("initCkGrantAccountExs");

		try {
			String hql = "from TCkUnGrantAccountEx o where o.id.grntaAppsCode=:appsCode";
			Map<String, Object> params = new HashMap<>();
			params.put("appsCode", appsCode);
			Optional<List<TCkUnGrantAccountEx>> opTCkGrantAccnExs = Optional
					.ofNullable(ckGrantAccountExDao.getByQuery(hql, params));
			if (!opTCkGrantAccnExs.isPresent() || opTCkGrantAccnExs.get().isEmpty()) {
				if (opCkGrantAccountExs != null)
					opCkGrantAccountExs.get().clear();
				else
					return;
			}

			log.debug("opTCkGrantAccnExs.size(): " + opTCkGrantAccnExs.get().size());

			Map<CkUnGrantAccountExId, List<CorePermEx>> mCkGrantAccnEx = new HashMap<>();
			opTCkGrantAccnExs.get().stream().forEach(x -> {
				Hibernate.initialize(x.getTCoreAccn());
				Hibernate.initialize(x.getTCoreApps());
				Hibernate.initialize(x.getTCorePermEx());
				Hibernate.initialize(x.getId());
				Hibernate.initialize(x.getTCorePermEx().getTCoreApps());
				Hibernate.initialize(x.getTCorePermEx().getTCoreModule());
				Hibernate.initialize(x.getTCorePermEx().getTMstDocType());
				Hibernate.initialize(x.getTCorePermEx().getTMstDocCode());
				Hibernate.initialize(x.getTCorePermEx().getTCoreModuleService());
				Hibernate.initialize(x.getTCorePermEx().getTCoreMenuEx());
				Hibernate.initialize(x.getTCorePermEx().getTMstPermAction());

				CkUnGrantAccountExId ckGrantAccnId = new CkUnGrantAccountExId();
				ckGrantAccnId.setGrntaAppsCode(x.getTCorePermEx().getTCoreApps().getAppsCode());
				ckGrantAccnId.setGrntaAccnId(x.getTCoreAccn().getAccnId());
				ckGrantAccnId.setGrntaPermExId(x.getTCorePermEx().getPermId());
				ckGrantAccnId.setGrntaRoleId(x.getId().getGrntaRoleId());

				log.debug("ckGrantAccnId:" + ckGrantAccnId.toString());
				List<CorePermEx> corePermExs = mCkGrantAccnEx.get(ckGrantAccnId);
				if (null == corePermExs) {
					corePermExs = new ArrayList<>();
					mCkGrantAccnEx.put(ckGrantAccnId, corePermExs);
				}
				CorePermEx corePermEx = new CorePermEx(x.getTCorePermEx());
				corePermEx.setTCoreApps(new CoreApps((x.getTCorePermEx().getTCoreApps())));
				corePermEx.setTCoreModule(new CoreModule(x.getTCorePermEx().getTCoreModule()));
				corePermEx.setTMstDocType(new MstDocType(x.getTCorePermEx().getTMstDocType()));
				corePermEx.setTMstDocCode(new MstDocCode(x.getTCorePermEx().getTMstDocCode()));
				corePermEx.setTCoreModuleService(new CoreModuleService(x.getTCorePermEx().getTCoreModuleService()));
				corePermEx.setTCoreMenuEx(new CoreMenuEx(x.getTCorePermEx().getTCoreMenuEx()));
				corePermEx.setTMstPermAction(new MstPermAction(x.getTCorePermEx().getTMstPermAction()));
				corePermExs.add(corePermEx);
			});
			this.opCkGrantAccountExs = Optional.ofNullable(mCkGrantAccnEx);
			log.debug("opTCkGrantAccnExs.keyset().size(): " + opCkGrantAccountExs.get().keySet().size());
		} catch (ProcessingException ex) {
			log.error("initCkGrantAccountExs", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("initCkGrantAccountExs", ex);
			throw new ProcessingException(ex);
		}
	}

	private void initCkGrantUserExs(String appsCode) throws ProcessingException {
		log.debug("initCkGrantUserExs");

		try {
			String hql = "from TCkUnGrantUserEx o where o.id.grntueAppsCode=:appsCode";
			Map<String, Object> params = new HashMap<>();
			params.put("appsCode", appsCode);
			Optional<List<TCkUnGrantUserEx>> opTCkGrantUserExs = Optional
					.ofNullable(ckGrantUserExDao.getByQuery(hql, params));
			if (!opTCkGrantUserExs.isPresent() || opTCkGrantUserExs.get().isEmpty()) {
				if(opckGrantUserExs != null)
					opckGrantUserExs.get().clear();
				else
					return;
			}
				

			log.debug("opTCkGrantUserExs.size(): " + opTCkGrantUserExs.get().size());

			Map<CkUnGrantUserExId, List<CorePermEx>> mCkGrantUserEx = new HashMap<>();
			opTCkGrantUserExs.get().stream().forEach(x -> {
				Hibernate.initialize(x.getId());
				Hibernate.initialize(x.getTCoreUsr());
				Hibernate.initialize(x.getTCoreApps());
				Hibernate.initialize(x.getTCorePermEx());
				Hibernate.initialize(x.getTCorePermEx().getTCoreApps());
				Hibernate.initialize(x.getTCorePermEx().getTCoreModule());
				Hibernate.initialize(x.getTCorePermEx().getTMstDocType());
				Hibernate.initialize(x.getTCorePermEx().getTMstDocCode());
				Hibernate.initialize(x.getTCorePermEx().getTCoreModuleService());
				Hibernate.initialize(x.getTCorePermEx().getTCoreMenuEx());
				Hibernate.initialize(x.getTCorePermEx().getTMstPermAction());

				CkUnGrantUserExId ckGrantUserId = new CkUnGrantUserExId();
				ckGrantUserId.setGrntueAppsCode(x.getTCorePermEx().getTCoreApps().getAppsCode());
				ckGrantUserId.setGrntueUsrId(x.getTCoreUsr().getUsrUid());
				ckGrantUserId.setGrntuePermExId(x.getTCorePermEx().getPermId());
				ckGrantUserId.setGrntueRoleId(x.getId().getGrntueRoleId());

				log.debug("ckGrantUserId:" + ckGrantUserId.toString());
				List<CorePermEx> corePermExs = mCkGrantUserEx.get(ckGrantUserId);
				if (null == corePermExs) {
					corePermExs = new ArrayList<>();
					mCkGrantUserEx.put(ckGrantUserId, corePermExs);
				}
				CorePermEx corePermEx = new CorePermEx(x.getTCorePermEx());
				corePermEx.setTCoreApps(new CoreApps((x.getTCorePermEx().getTCoreApps())));
				corePermEx.setTCoreModule(new CoreModule(x.getTCorePermEx().getTCoreModule()));
				corePermEx.setTMstDocType(new MstDocType(x.getTCorePermEx().getTMstDocType()));
				corePermEx.setTMstDocCode(new MstDocCode(x.getTCorePermEx().getTMstDocCode()));
				corePermEx.setTCoreModuleService(new CoreModuleService(x.getTCorePermEx().getTCoreModuleService()));
				corePermEx.setTCoreMenuEx(new CoreMenuEx(x.getTCorePermEx().getTCoreMenuEx()));
				corePermEx.setTMstPermAction(new MstPermAction(x.getTCorePermEx().getTMstPermAction()));
				corePermExs.add(corePermEx);
			});
			this.opckGrantUserExs = Optional.ofNullable(mCkGrantUserEx);
			log.debug("opckGrantUserExs.keyset().size(): " + opckGrantUserExs.get().keySet().size());
		} catch (ProcessingException ex) {
			log.error("initCkGrantUserExs", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("initCkGrantUserExs", ex);
			throw new ProcessingException(ex);
		}
	}

	private Map<CoreRoleId, List<CorePermEx>> filterGrantRoleEx(String pAccnId, String pUsrUid) throws Exception {
		// Check if grantRoleEx has elements
		Map<CoreRoleId, List<CorePermEx>> modifiedGrantRoleEx = new HashMap<>();

		if (opCoreGrantRoleExs != null && opCoreGrantRoleExs.isPresent() && !opCoreGrantRoleExs.get().isEmpty()) {
			Map<CoreRoleId, List<CorePermEx>> modifiedGrantRoleExByAccn = new HashMap<>();
			if (opCkGrantAccountExs != null && opCkGrantAccountExs.isPresent()
					&& !opCkGrantAccountExs.get().isEmpty()) {

				opCoreGrantRoleExs.get().forEach((keyRole, val) -> {
					modifiedGrantRoleExByAccn.put(keyRole, val.stream().filter(e -> {
						CkUnGrantAccountExId grntAccnIdKey = new CkUnGrantAccountExId();
						grntAccnIdKey.setGrntaAccnId(pAccnId);
						grntAccnIdKey.setGrntaAppsCode(e.getTCoreApps().getAppsCode());
						grntAccnIdKey.setGrntaRoleId(keyRole.getRoleId());
						grntAccnIdKey.setGrntaPermExId(e.getPermId());

						return !opCkGrantAccountExs.get().containsKey(grntAccnIdKey);

					}).collect(Collectors.toList()));

				});
			}

			// Filter further filteredGrantRoleEx by user?
			Map<CoreRoleId, List<CorePermEx>> modifiedGrantRoleExByUser = new HashMap<>();
			if (opckGrantUserExs != null && opckGrantUserExs.isPresent() && !opckGrantUserExs.get().isEmpty()) {
				modifiedGrantRoleExByAccn.forEach((keyRole, val) -> {
					modifiedGrantRoleExByUser.put(keyRole, val.stream().filter(e -> {
						CkUnGrantUserExId grntUsrIdKey = new CkUnGrantUserExId();
						grntUsrIdKey.setGrntueUsrId(pUsrUid);
						grntUsrIdKey.setGrntueAppsCode(e.getTCoreApps().getAppsCode());
						grntUsrIdKey.setGrntueRoleId(keyRole.getRoleId());
						grntUsrIdKey.setGrntuePermExId(e.getPermId());

						return !opckGrantUserExs.get().containsKey(grntUsrIdKey);
					}).collect(Collectors.toList()));

				});
			}

			if (!modifiedGrantRoleExByUser.isEmpty())
				modifiedGrantRoleEx.putAll(modifiedGrantRoleExByUser);
			else if (!modifiedGrantRoleExByAccn.isEmpty())
				modifiedGrantRoleEx.putAll(modifiedGrantRoleExByAccn);
			else
				modifiedGrantRoleEx.putAll(opCoreGrantRoleExs.get());

		}

		return modifiedGrantRoleEx;

	}

	/**
	 * @param menuId
	 * @return
	 */
	private List<String> getParentMenuIds(String menuId) throws ParameterException, ProcessingException {
		log.debug("getParentMenuIds");

		List<String> parentMenuIds = new ArrayList<>();
		try {
			if (StringUtils.isEmpty(menuId))
				throw new ParameterException("param menuId null or empty");

			Map<String, CoreMenuEx> mCoreMenuExs = opCoreMenuExs.get().stream()
					.collect(Collectors.toMap(x -> x.getMenuId(), x -> x));

			CoreMenuEx menu = mCoreMenuExs.get(menuId);
			if (menu.getTCoreMenuEx().getMenuId() != null) {
				parentMenuIds.add(menu.getTCoreMenuEx().getMenuId());
				parentMenuIds.addAll(this.getParentMenuIds(menu.getTCoreMenuEx().getMenuId()));
			}

			return parentMenuIds;
		} catch (ParameterException ex) {
			log.error("getParentMenuIds", ex);
			throw ex;
		} catch (ProcessingException ex) {
			log.error("getParentMenuIds", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("getParentMenuIds", ex);
			throw new ProcessingException(ex);
		}
	}

	/**
	 * @param menuId
	 * @return
	 * @throws ParameterException
	 * @throws ProcessingException
	 */
	private List<String> getChildMenuIds(String menuId) throws ParameterException, ProcessingException {
		log.debug("getChildMenuIds");

		try {
			if (StringUtils.isEmpty(menuId))
				throw new ParameterException("param menuId null or empty");

			List<CoreMenuEx> childCoreMenuExs = opCoreMenuExs.get().stream()
					.filter(x -> !StringUtils.isEmpty(x.getTCoreMenuEx().getMenuId())
							&& x.getTCoreMenuEx().getMenuId().equalsIgnoreCase(menuId))
					.collect(Collectors.toList());

			return childCoreMenuExs.stream().map(x -> x.getMenuId()).collect(Collectors.toList());
		} catch (ParameterException ex) {
			log.error("getChildMenuIds", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("getChildMenuIds", ex);
			throw new ProcessingException(ex);
		}
	}

	/**
	 * has bug, need to change to recursive function.
	 * 
	 * @return
	 * @throws ProcessingException
	 */
	private List<PermExMenu> getMenuNav(Set<String> menuIds, boolean isTranslate) throws ProcessingException {
		log.debug("getMenuNav");

		List<PermExMenu> menus = new ArrayList<>();
		try {
			if (!opCoreMenuExs.isPresent() || opCoreMenuExs.get().isEmpty())
				throw new ProcessingException("opCoreMenuExs null or empty");

			log.debug("menuIds: " + menuIds.stream().collect(Collectors.joining(":")));

			Map<String, CoreMenuEx> mCoreMenuExs = opCoreMenuExs.get().stream()
					.collect(Collectors.toMap(x -> x.getMenuId(), x -> x));

			Map<String, PermExMenu> mPermExMenus = new HashMap<>();
			opCoreMenuExs.get().stream().forEach(x -> {
				if (!menuIds.contains(x.getMenuId()))
					return;

				CoreMenuEx coreMenuEx = mCoreMenuExs.get(x.getMenuId());
				PermExMenu permExMenu = new PermExMenu();
				permExMenu.setName(isTranslate ? coreMenuEx.getMenuNameOth() : coreMenuEx.getMenuName());
				permExMenu.setIcon(coreMenuEx.getMenuIcon());
				permExMenu.setPath(coreMenuEx.getMenuPath());
				permExMenu.setMenuId(x.getMenuId());
				permExMenu.setLabel(isTranslate ? coreMenuEx.getMenuNameOth() : coreMenuEx.getMenuName());
				permExMenu.setSeq(coreMenuEx.getMenuSeq());

				mPermExMenus.put(x.getMenuId(), permExMenu);

				if (null == coreMenuEx.getTCoreMenuEx().getMenuId()) {
					log.debug("adding into menus");
					menus.add(permExMenu);
				} else {
					PermExMenu permExMenuParent = mPermExMenus.get(coreMenuEx.getTCoreMenuEx().getMenuId());
					if (null == permExMenuParent)
						return;
					if (permExMenuParent.getChildren() == null)
						permExMenuParent.setChildren(new ArrayList<PermExMenu>());
					permExMenuParent.getChildren().add(permExMenu);
					permExMenuParent.getChildren().sort((m1, m2) -> m1.getSeq() - m2.getSeq());
				}
			});
			return menus;
		} catch (ProcessingException ex) {
			log.error("intTPermExMenu", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("intTPermExMenu", ex);
			throw new ProcessingException(ex);
		}
	}

}
