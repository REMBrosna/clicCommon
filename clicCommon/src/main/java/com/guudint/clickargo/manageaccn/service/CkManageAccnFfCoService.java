package com.guudint.clickargo.manageaccn.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.admin.event.ClickargoPostUserUpdateEvent;
import com.guudint.clickargo.admin.event.ClickargoPostUserUpdateEvent.PostUserUpdateAction;
import com.guudint.clickargo.clicservice.dao.CkSvcSubDao;
import com.guudint.clickargo.clicservice.model.TCkSvcSub;
import com.guudint.clickargo.common.CkUtil;
import com.guudint.clickargo.common.ICkConstant;
import com.guudint.clickargo.common.model.ValidationError;
import com.guudint.clickargo.common.service.impl.CkSeqNoServiceImpl;
import com.guudint.clickargo.manageaccn.dao.impl.CkCtFfCoDaoImpl;
import com.guudint.clickargo.manageaccn.dto.CkAccnUser;
import com.guudint.clickargo.manageaccn.dto.CoreAccnStateEnum;
import com.guudint.clickargo.manageaccn.model.TCkCtFfCo;
import com.guudint.clickargo.master.enums.AccountTypes;
import com.guudint.clickargo.master.enums.Roles;
import com.guudint.clickargo.master.enums.ServiceTypes;
import com.guudint.clickargo.master.model.TCkMstServiceType;
import com.guudint.clickargo.master.model.TCkMstSvcSubState;
import com.guudint.clickargo.validator.AccountFfCoValidator;
import com.guudint.clickargo.validator.UsertFfCoValidator;
import com.vcc.camelone.cac.dao.CoreUsrRoleDao;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.cac.model.TCoreUsrRole;
import com.vcc.camelone.cac.model.TCoreUsrRoleId;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.ccm.dto.CoreAddress;
import com.vcc.camelone.ccm.dto.CoreContact;
import com.vcc.camelone.ccm.dto.CoreUsr;
import com.vcc.camelone.ccm.model.TCoreAccn;
import com.vcc.camelone.ccm.model.TCoreUsr;
import com.vcc.camelone.ccm.service.impl.UserService;
import com.vcc.camelone.ccm.util.Constant;
import com.vcc.camelone.common.audit.model.TCoreAuditlog;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.exception.ValidationException;
import com.vcc.camelone.common.service.entity.IEntityService;
import com.vcc.camelone.master.dto.MstAccnType;
import com.vcc.camelone.master.dto.MstCountry;
import com.vcc.camelone.util.crypto.PasswordEncryptor;
import com.vcc.camelone.util.crypto.PasswordGenerator;

@Service
public class CkManageAccnFfCoService extends CkManageAccnService {

	private static final Logger log = Logger.getLogger(CkManageAccnFfCoService.class);

	@Autowired
	private GenericDao<TCoreUsr, String> coreUserDao;

	@Autowired
	UserService userService;

	@Autowired
	protected AccountFfCoValidator accnFfCoValidator;

	@Autowired
	protected UsertFfCoValidator usertFfCoValidator;

	@Autowired
	protected CkCtFfCoDaoImpl ckCtFfCoDao;

	@Autowired
	protected IEntityService<TCoreAccn, String, CoreAccn> ccmAccnService;

	@Autowired
	GenericDao<TCoreAuditlog, String> auditLogDao;

	@Autowired
	protected CoreUsrRoleDao coreUsrRoleDao;

	@Autowired
	protected CkSvcSubDao ckSvcSubDao;

	public CkAccnUser getNew() {

		//
		CoreAccn coreAccn = new CoreAccn();
		coreAccn.setAccnStatus(CoreAccnStateEnum.NEW.getCode());

		MstAccnType mstAccnType = new MstAccnType();
		mstAccnType.setAtypId(AccountTypes.ACC_TYPE_FF_CO.name());
		coreAccn.setTMstAccnType(mstAccnType);

		CoreContact accnContact = new CoreContact();
		accnContact.setContactEmail("");
		coreAccn.setAccnContact(accnContact);

		CoreAddress accnAddr = new CoreAddress();
		MstCountry mstCountry = new MstCountry();
		mstCountry.setCtyCode("SG");
		accnAddr.setAddrCtry(mstCountry);

		coreAccn.setAccnAddr(accnAddr);

		//
		CoreUsr coreUsr = new CoreUsr();

		CoreAddress usrAdd = new CoreAddress();
		usrAdd.setAddrCtry(mstCountry);
		coreUsr.setUsrAddr(usrAdd);

		CoreContact usrContact = new CoreContact();
		usrContact.setContactEmail("");
		coreUsr.setUsrContact(usrContact);

		return new CkAccnUser(coreAccn, coreUsr);

	}

	@Transactional
	public CkAccnUser getCKAccountUser(String accnId)
			throws ParameterException, ProcessingException, EntityNotFoundException, Exception {

		CkAccnUser accnUser = new CkAccnUser();

		String decodedAccnId = new String(Base64.getDecoder().decode(accnId));

		// Account
		CoreAccn coreAccn = ccmAccnService.findById(decodedAccnId);

		if (coreAccn == null)
			throw new ProcessingException("account details not found");
		accnUser.setCoreAccn(coreAccn);

		// User
		List<TCoreUsr> coreUserList = this.findUserByAccnId(decodedAccnId);
		if (coreUserList != null && coreUserList.size() > 0) {
			TCoreUsr tCoreUsr = coreUserList.get(0);
			CoreUsr coreUsr = userService.dtoFromEntity(tCoreUsr);
			accnUser.setCoreUsr(coreUsr);
		}

		return accnUser;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CkAccnUser createAccountUser(CkAccnUser accnUser) throws ValidationException, Exception {

		Principal principal = principalUtilService.getPrincipal();
		if (principal == null)
			throw new ProcessingException("principal is null");

		try {

			if (accnUser.getCoreAccn() == null)
				throw new ProcessingException("account is null");

			if (accnUser.getCoreUsr() == null)
				throw new ProcessingException("user is null");

			Date now = new Date();

			// Account
			CoreAccn newAccn = accnUser.getCoreAccn();
			newAccn.setAccnId(seqNoService.getNextSequence(CkSeqNoServiceImpl.SeqNoCode.CT_ACCN_CODE.name()));
			newAccn.setAccnStatus(Constant.ACTIVE_STATUS);
			newAccn.setAccnDtReg(now);
			newAccn.setAccnDtCreate(now);

			// User
			CoreUsr newUsr = accnUser.getCoreUsr();
			newUsr.setUsrUid(seqNoService.getNextSequence(CkSeqNoServiceImpl.SeqNoCode.CT_USER_CODE.name()));
			newUsr.setTCoreAccn(newAccn);
			newUsr.setUsrDtComm(now);
			newUsr.setUsrDtPwdLupd(now);
			newUsr.setUsrMboxId("");
			newUsr.setUsrLoginInvcnt(0);
			newUsr.setUsrTypeMbox("N");
			newUsr.setUsrTypeOnline("Y");

			String pswd = PasswordGenerator.generatePassword(8);
			log.info("pswd: " + pswd);
			String pswdEncrypt = PasswordEncryptor.encrypt(newUsr.getUsrUid(), pswd);
			newUsr.setUsrPwd(pswdEncrypt);
			newUsr.setUsrPwdForce("Y"); // force change password

			newUsr.setUsrStatus(Constant.ACTIVE_STATUS);
			newUsr.setUsrDtReg(now);
			newUsr.setUsrDtCreate(now);

			// user role
			TCoreUsrRoleId usrRoleAdminId = new TCoreUsrRoleId(newUsr.getUsrUid(), ICkConstant.APP_CODE_CKT,
					Roles.FF_CO_ADMIN.name());

			TCoreUsrRole usrRoleAdmin = new TCoreUsrRole(usrRoleAdminId, null, null, Constant.ACTIVE_STATUS, now,
					principal.getUserId());
			usrRoleAdmin.setUrolTempRole('N');

			// user password

			// validation
			List<ValidationError> accnErrors = accnFfCoValidator.validateSubmit(newAccn, principal);
			// validation
			List<ValidationError> userErrors = usertFfCoValidator.validateSubmit(newUsr, principal);

			List<ValidationError> errors = new ArrayList<>();

			if (!accnErrors.isEmpty()) {
				errors.addAll(accnErrors);
			}
			if (!userErrors.isEmpty()) {
				errors.addAll(userErrors);
			}
			if (!errors.isEmpty()) {
				throw new ValidationException(this.validationErrorMap(errors));
			}

			// Save
			CoreAccn coreAccn = ccmAccnService.add(newAccn, principal);
			accnUser.setCoreAccn(coreAccn);

			// need to flush
			ckCtFfCoDao.getSessionFactory().getCurrentSession().flush();

			CoreUsr coreUsr = userService.add(newUsr, principal);
			accnUser.setCoreUsr(coreUsr);

			// need to flush
			ckCtFfCoDao.getSessionFactory().getCurrentSession().flush();

			coreUsrRoleDao.add(usrRoleAdmin);
			
			//
			TCkSvcSub svcSub = new TCkSvcSub();
			svcSub.setSubId(CkUtil.generateId("CK"));
			svcSub.setTCoreAccn(new TCoreAccn(newAccn.getAccnId(), null, 'A',null));
			svcSub.setTCkMstServiceType(new TCkMstServiceType(ServiceTypes.CLICTRUCK.name(), null));
			svcSub.setTCkMstSvcSubState(new TCkMstSvcSubState("APR", null));
			
			svcSub.setSubDtStart(now);
			svcSub.setSubDtValid( new SimpleDateFormat("yyyyMMdd").parse("20330101"));
			svcSub.setSubAutoRenew('N');
			
			svcSub.setSubUidVerify("sys");
			svcSub.setSubUidApprove("sys");
			svcSub.setSubDtVerify(now);
			svcSub.setSubDtApprove(now);
			
			svcSub.setSubDtCreate(now);
			svcSub.setSubUidCreate("sys");
			
			ckSvcSubDao.add(svcSub);
			

			eventPublisher.publishEvent(
					new ClickargoPostUserUpdateEvent(this, coreUsr, false, PostUserUpdateAction.NEW_USER, pswd));

		} catch (ValidationException ex) {
			throw ex;

		} catch (Exception exception) {
			throw exception;
		}

		//
		TCoreAccn tCoreAccnCo = new TCoreAccn();
		tCoreAccnCo.setAccnId(accnUser.getCoreAccn().getAccnId());

		//
		TCoreAccn tCoreAccnFf = new TCoreAccn();
		if (AccountTypes.ACC_TYPE_FF.name().equalsIgnoreCase(principal.getCoreAccn().getTMstAccnType().getAtypId())) {
			tCoreAccnFf.setAccnId(principal.getUserAccnId());
		} else if (AccountTypes.ACC_TYPE_SP.name()
				.equalsIgnoreCase(principal.getCoreAccn().getTMstAccnType().getAtypId())) {
			
			if (StringUtils.isNotBlank(accnUser.getFfAccnId())) {
				tCoreAccnFf.setAccnId(accnUser.getFfAccnId());
			} else {
				throw new Exception("FF acount id is blank.");
			}
		} else {
			throw new Exception("Only GLI or FF can create CO.");
		}

		TCkCtFfCo ckctFfCo = new TCkCtFfCo();
		ckctFfCo.setFfcoId(CkUtil.generateId(TCkCtFfCo.PREFIX_ID));
		ckctFfCo.setTCoreAccnByFfcoFf(tCoreAccnFf);
		ckctFfCo.setTCoreAccnByFfcoCo(tCoreAccnCo);

		ckctFfCo.setFfcoStatus(Constant.ACTIVE_STATUS);
		ckctFfCo.setFfcoDtCreate(new Date());

		ckCtFfCoDao.add(ckctFfCo);

		return accnUser;
	}

	/**
	 * Only update account
	 * 
	 * @param accnId
	 * @param accnUser
	 * @return
	 * @throws ValidationException
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CkAccnUser updateCompanyAccount(String accnId, CkAccnUser accnUser) throws ValidationException, Exception {
		try {

			if (StringUtils.isEmpty(accnId))
				throw new ParameterException("accnId is null");

			Principal principal = principalUtilService.getPrincipal();
			if (principal == null)
				throw new ProcessingException("principal is null");
			if (accnUser.getCoreAccn() == null)
				throw new ProcessingException("ckManageAccn is null");

			List<ValidationError> errors = accnFfCoValidator.validateUpdate(accnUser.getCoreAccn(), principal);
			if (!errors.isEmpty()) {
				throw new ValidationException(this.validationErrorMap(errors));
			}

			ccmAccnService.update(accnUser.getCoreAccn(), principal);

			return accnUser;
		} catch (ValidationException ex) {
			throw ex;
		} catch (Exception ex) {
			throw ex;
		}
	}

	public void updateCompanyAccountStatus(String accnId, CoreAccnStateEnum accnStatus) throws Exception {

		log.info("updateCompanyAccountStatus: " + accnId + " " + accnStatus);

		try {

			Principal principal = principalUtilService.getPrincipal();
			if (principal == null)
				throw new ProcessingException("principal is null");

			TCoreAccn entity = coreAccDao.find(accnId);

			char newState = accnStatus.getCode();

			entity.setAccnStatus(newState);
			entity.setAccnDtLupd(new Date());
			entity.setAccnUidLupd(principal.getUserId());

			coreAccDao.update(entity);

			audit(accnId, accnStatus.getDesc(), principal.getUserId(), principal.getUserId(), null, null);
		} catch (Exception e) {
			log.error("Fail to update account status ", e);
			throw e;
		}
	}

	public List<TCoreUsr> findUserByAccnId(String accnId) throws Exception {

		Map<String, Object> param = new HashMap<>();
		param.put("accnId", accnId);
		String sql = "FROM TCoreUsr o WHERE o.TCoreAccn.accnId = :accnId order by usrDtCreate asc";

		List<TCoreUsr> tCoreUsrs = coreUserDao.getByQuery(sql, param);

		return tCoreUsrs;
	}

	/////////////////////

	protected String getWhereClause(CkCoreAccn dto, boolean wherePrinted1)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		log.debug("getWhereClause");

		try {
			if (null == dto)
				throw new ParameterException("param dto null");

			Principal principal = principalUtilService.getPrincipal();
			if (principal == null)
				throw new ProcessingException("principal null");

			StringBuffer searchStatement = new StringBuffer();

			CoreAccn accn = principal.getCoreAccn();
			Optional<MstAccnType> opAccnType = Optional.ofNullable(accn.getTMstAccnType());

			searchStatement.append(" where 1=1 ");

			// only proceed if it's SP
			if (opAccnType.isPresent() && opAccnType.get().getAtypId().equals(AccountTypes.ACC_TYPE_SP.name())) {

			} else if (opAccnType.isPresent() && opAccnType.get().getAtypId().equals(AccountTypes.ACC_TYPE_FF.name())) {

				searchStatement.append(" and o IN (select TCoreAccnByFfcoCo from TCkCtFfCo ffco "
						+ " where ffco.TCoreAccnByFfcoFf.accnId = '" + principal.getUserAccnId() + "')");
			}

			// Exclude Registration Rejected from filter
			searchStatement.append(" and o.accnStatus NOT IN :excludeAccnStatus");

			// Only include SP/CO/FF/TO
			searchStatement.append(" and o.TMstAccnType.atypId IN :includeAccnTypes");

			searchStatement.append(" and o.accnStatus IN :accnStatus");

			if (dto.getAccnId() != null && !StringUtils.isEmpty(dto.getAccnId())) {
				searchStatement.append(" and o.accnId LIKE :accnId");

			}
			if (dto.getAccnName() != null && !StringUtils.isEmpty(dto.getAccnName())) {
				searchStatement.append(" and o.accnName LIKE :accnName");
			}

			if (dto.getTMstAccnType() != null) {
				if (StringUtils.isNotBlank(dto.getTMstAccnType().getAtypId())) {
					searchStatement.append(" and o.TMstAccnType.atypId = :atypId");
				}
				if (StringUtils.isNotBlank(dto.getTMstAccnType().getAtypDescription())) {
					searchStatement.append(" and o.TMstAccnType.atypDescription LIKE :atypDescription");
				}
			}

			if (null != dto.getAccnCoyRegn() && !StringUtils.isEmpty(dto.getAccnCoyRegn())) {
				searchStatement.append(" and o.accnCoyRegn LIKE :accnCoyRegn");
			}

			if (dto.getAccnContact() != null) {
				if (!StringUtils.isEmpty(dto.getAccnContact().getContactTel())) {
					searchStatement.append(" and o.accnContact.contactTel LIKE :contactTel");
				}
				if (!StringUtils.isEmpty(dto.getAccnContact().getContactFax())) {
					searchStatement.append(" and o.accnContact.contactFax LIKE :contactFax");
				}
				if (!StringUtils.isEmpty(dto.getAccnContact().getContactEmail())) {
					searchStatement.append(" and o.accnContact.contactEmail LIKE :contactEmail");
				}
			}

			if (null != dto.getAccnDtCreate()) {
				searchStatement.append(" and DATE_FORMAT(o.accnDtCreate,'%d/%m/%Y') = :accnDtCreate");
			}

			if (null != dto.getAccnDtReg()) {
				searchStatement.append(" and DATE_FORMAT(o.accnDtReg,'%d/%m/%Y') = :accnDtReg");
			}

			if (!StringUtils.isEmpty(dto.getAccnUidCreate())) {
				searchStatement.append(" and o.accnUidCreate LIKE :accnUidCreate");
			}

			if (Character.isAlphabetic(dto.getAccnStatus())) {
				searchStatement.append(" and o.accnStatus = :accnStatus");
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

	protected HashMap<String, Object> getParameters(CkCoreAccn dto) throws ParameterException, ProcessingException {

		log.debug("getParameters");

		try {
			if (null == dto)
				throw new ParameterException("param dto null");

			SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
			HashMap<String, Object> parameters = new HashMap<String, Object>();

			Principal principal = principalUtilService.getPrincipal();
			if (principal == null)
				throw new ProcessingException("principal null");

			CoreAccn accn = principal.getCoreAccn();
			Optional<MstAccnType> opAccnType = Optional.ofNullable(accn.getTMstAccnType());
			// only proceed if it's SP
			if (opAccnType.isPresent() && opAccnType.get().getAtypId().equals(AccountTypes.ACC_TYPE_SP.name())) {
				// Exclude Registration Rejected/Deleted from filter
				parameters.put("excludeAccnStatus", Arrays.asList(CoreAccnStateEnum.REG_REJECTED.getCode(),
						CoreAccnStateEnum.REG_DELETED.getCode()));

				// Only include SP/CO/FF/TO
				parameters.put("includeAccnTypes",
						Arrays.asList(AccountTypes.ACC_TYPE_TO.name(), AccountTypes.ACC_TYPE_CO.name(),
								AccountTypes.ACC_TYPE_FF.name(), AccountTypes.ACC_TYPE_SP.name()));

			} else if (opAccnType.isPresent() && opAccnType.get().getAtypId().equals(AccountTypes.ACC_TYPE_FF.name())) {

				// Exclude Registration Rejected/Deleted from filter
				parameters.put("excludeAccnStatus", Arrays.asList(CoreAccnStateEnum.REG_REJECTED.getCode(),
						CoreAccnStateEnum.REG_DELETED.getCode()));

				// Only include SP/CO/FF/TO
				parameters.put("includeAccnTypes", Arrays.asList(AccountTypes.ACC_TYPE_FF_CO.name()));
			}

			if (Character.isAlphabetic(dto.getAccnStatus())) {
				parameters.put("accnStatus", Character.valueOf(dto.getAccnStatus()));
			} else {
				boolean isFinHd = principal.getRoleList().stream()
						.anyMatch(e -> Arrays.asList(Roles.SP_FIN_HD.name()).contains(e));

				List<Character> includeStatus = Arrays.asList(CoreAccnStateEnum.NEW.getCode(),
						CoreAccnStateEnum.REG_SUBMITTED.getCode(), CoreAccnStateEnum.SUS_SUBMITTED.getCode(),
						CoreAccnStateEnum.TER_SUBMITTED.getCode(), CoreAccnStateEnum.REG_APPROVED.getCode(),
						CoreAccnStateEnum.RESUMPTION_SUBMITTED.getCode());

				if (dto.getHistory() != null && dto.getHistory().equalsIgnoreCase(DEFAULT)) {
					if (isFinHd) {
						includeStatus = Arrays.asList(CoreAccnStateEnum.REG_SUBMITTED.getCode(),
								CoreAccnStateEnum.SUS_SUBMITTED.getCode(), CoreAccnStateEnum.TER_SUBMITTED.getCode(),
								CoreAccnStateEnum.REG_APPROVED.getCode(),
								CoreAccnStateEnum.RESUMPTION_SUBMITTED.getCode());
					}

					parameters.put("accnStatus", includeStatus);

				} else if (dto.getHistory() != null && dto.getHistory().equalsIgnoreCase(HISTORY)) {
					parameters.put("accnStatus", Arrays.asList(CoreAccnStateEnum.SUS_APPROVED.getCode(),
							CoreAccnStateEnum.TER_APPROVED.getCode()));
				}
			}

			if (dto.getAccnId() != null && !StringUtils.isEmpty(dto.getAccnId()))
				parameters.put("accnId", "%" + dto.getAccnId() + "%");
			if (dto.getAccnName() != null && !StringUtils.isEmpty(dto.getAccnName()))
				parameters.put("accnName", "%" + dto.getAccnName() + "%");

			if (null != dto.getTMstAccnType()) {
				if (dto.getTMstAccnType().getAtypId() != null)
					parameters.put("atypId", dto.getTMstAccnType().getAtypId());
				if (dto.getTMstAccnType().getAtypDescription() != null)
					parameters.put("atypDescription", "%" + dto.getTMstAccnType().getAtypDescription() + "%");
			}

			if (null != dto.getAccnCoyRegn() && !StringUtils.isEmpty(dto.getAccnCoyRegn()))
				parameters.put("accnCoyRegn", "%" + dto.getAccnCoyRegn() + "%");

			if (dto.getAccnContact() != null) {
				if (!StringUtils.isEmpty(dto.getAccnContact().getContactTel()))
					parameters.put("contactTel", "%" + dto.getAccnContact().getContactTel() + "%");
				if (!StringUtils.isEmpty(dto.getAccnContact().getContactFax()))
					parameters.put("contactFax", "%" + dto.getAccnContact().getContactFax() + "%");
				if (!StringUtils.isEmpty(dto.getAccnContact().getContactEmail()))
					parameters.put("contactEmail", "%" + dto.getAccnContact().getContactEmail() + "%");
			}

			if (null != dto.getAccnDtCreate()) {
				parameters.put("accnDtCreate", sdfDate.format(dto.getAccnDtCreate()));
			}

			if (null != dto.getAccnDtReg()) {
				parameters.put("accnDtReg", sdfDate.format(dto.getAccnDtReg()));
			}

			if (!StringUtils.isEmpty(dto.getAccnUidCreate())) {
				parameters.put("accnUidCreate", "%" + dto.getAccnUidCreate() + "%");
			}

			// TODO: submitted date and submitted by

			return parameters;
		} catch (ParameterException ex) {
			log.error("getParameters", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("getParameters", ex);
			throw new ProcessingException(ex);
		}
	}

	public void audit(String recKey, String auditv, String userId, String userName, String auditRemarks,
			String auditParamKey, String... auditParams) throws Exception {

		Calendar now = Calendar.getInstance();

		if (StringUtils.isNotEmpty(userId)) {

			TCoreAuditlog TCoreAuditlog = new TCoreAuditlog(null, auditv, now.getTime(), userId, recKey);
			TCoreAuditlog.setAudtRemarks(auditRemarks);
			TCoreAuditlog.setAudtUname(userName);

			auditLogDao.add(TCoreAuditlog);

		} else {
			log.error(" " + auditv + " with message '" + auditRemarks + "' at " + now.getTime() + " in " + recKey);
		}

	}
}
