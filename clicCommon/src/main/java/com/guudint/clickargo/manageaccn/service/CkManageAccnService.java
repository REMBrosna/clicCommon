package com.guudint.clickargo.manageaccn.service;

import java.nio.file.Files;
import java.nio.file.Paths;
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
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guudint.clickargo.clicservice.dao.CkSvcSubDao;
import com.guudint.clickargo.clicservice.model.TCkSvcSub;
import com.guudint.clickargo.clicservice.service.ICkWorkflowService;
import com.guudint.clickargo.clicservice.service.impl.CkSvcSerivce;
import com.guudint.clickargo.common.CkFileUtil;
import com.guudint.clickargo.common.CkUtil;
import com.guudint.clickargo.common.RecordStatus;
import com.guudint.clickargo.common.dao.CkAccnDao;
import com.guudint.clickargo.common.dao.CkAccnOpmDao;
import com.guudint.clickargo.common.dao.CkMstRemarkTypeDao;
import com.guudint.clickargo.common.dao.CkMstWorkflowTypeDao;
import com.guudint.clickargo.common.dao.CkWorkflowRemarkDao;
import com.guudint.clickargo.common.dto.CkAccn;
import com.guudint.clickargo.common.dto.CkCtWhitelabel;
import com.guudint.clickargo.common.enums.MstEntityTypes;
import com.guudint.clickargo.common.enums.WorkflowTypeEnum;
import com.guudint.clickargo.common.event.ApproveEvent;
import com.guudint.clickargo.common.event.RejectEvent;
import com.guudint.clickargo.common.event.SubmitEvent;
import com.guudint.clickargo.common.event.VerifyEvent;
import com.guudint.clickargo.common.model.TCkAccn;
import com.guudint.clickargo.common.model.TCkAccnOpm;
import com.guudint.clickargo.common.model.TCkMstRemarkType;
import com.guudint.clickargo.common.model.TCkMstWorkflowType;
import com.guudint.clickargo.common.model.TCkWorkflowRemark;
import com.guudint.clickargo.common.model.ValidationError;
import com.guudint.clickargo.common.service.ICkSeqNoService;
import com.guudint.clickargo.common.service.impl.CkCoreAccnService;
import com.guudint.clickargo.common.service.impl.CkSeqNoServiceImpl;
import com.guudint.clickargo.common.service.impl.CkWhitelabelService;
import com.guudint.clickargo.job.model.TCkJob;
import com.guudint.clickargo.job.service.impl.CkJobService;
import com.guudint.clickargo.manageaccn.dto.CkAccnSuppDocsJson;
import com.guudint.clickargo.manageaccn.dto.CkManageAccn;
import com.guudint.clickargo.manageaccn.dto.CoreAccnStateEnum;
import com.guudint.clickargo.manageaccn.model.TCkAccnAtt;
import com.guudint.clickargo.manageaccn.model.TCkMstAccnAttType;
import com.guudint.clickargo.manageaccn.model.TCkMstAccnAttTypeId;
import com.guudint.clickargo.master.dao.CoreAccnConfigDao;
import com.guudint.clickargo.master.dto.CkMstServiceType;
import com.guudint.clickargo.master.enums.AccountTypes;
import com.guudint.clickargo.master.enums.AttachmentTypes;
import com.guudint.clickargo.master.enums.FormActions;
import com.guudint.clickargo.master.enums.Roles;
import com.guudint.clickargo.master.enums.ServiceTypes;
import com.guudint.clickargo.master.model.TCkMstServiceType;
import com.guudint.clickargo.master.model.TCkMstSvcSubState;
import com.guudint.clickargo.validator.AccountValidator;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.ccm.dto.CoreAccnAtt;
import com.vcc.camelone.ccm.dto.CoreAccnConfig;
import com.vcc.camelone.ccm.dto.CoreContact;
import com.vcc.camelone.ccm.model.TCoreAccn;
import com.vcc.camelone.ccm.model.TCoreAccnAtt;
import com.vcc.camelone.ccm.model.TCoreAccnConfig;
import com.vcc.camelone.ccm.model.TCoreAccnConfigId;
import com.vcc.camelone.ccm.model.embed.TCoreContact;
import com.vcc.camelone.ccm.util.Constant;
import com.vcc.camelone.common.controller.entity.EntityFilterRequest;
import com.vcc.camelone.common.controller.entity.EntityFilterResponse;
import com.vcc.camelone.common.controller.entity.EntityOrderBy;
import com.vcc.camelone.common.controller.entity.EntityOrderBy.ORDERED;
import com.vcc.camelone.common.controller.entity.EntityWhere;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.exception.ValidationException;
import com.vcc.camelone.common.service.entity.IEntityService;
import com.vcc.camelone.master.controller.PathNotFoundException;
import com.vcc.camelone.master.dto.MstAccnType;
import com.vcc.camelone.master.dto.MstAttType;
import com.vcc.camelone.master.model.TMstAccnType;
import com.vcc.camelone.master.model.TMstAttType;
import com.vcc.camelone.util.PrincipalUtilService;
import com.vcc.camelone.util.email.SysParam;

import io.jsonwebtoken.lang.Collections;

@Service
public class CkManageAccnService {

	private static final Logger log = Logger.getLogger(CkManageAccnService.class);

	protected static String HISTORY = "history";
	protected static String DEFAULT = "default";

	private static final String PREFIX_KEY_WORKFLOW_REMARK = "CKCTAR";
	private static final String ACCN_CONFIG_SAGE_ACCN_ID = "SAGE_ACCN_ID";
	private static final String POSTFIX_VA_IDR = "2SP_VA_IDR";
	private static final String CONFIG_BANK_DETAILS = "BANK_DETAIL";
	private static final String ACCN_CONFIG_FINANCE_OPTIONS = "FINANCE_OPTIONS";
	private static final String ACCN_CONFIG_MOBILE_ENABLED = "MOBILE_ENABLED";

	@Autowired
	@Qualifier("coreAccDao")
	protected GenericDao<TCoreAccn, String> coreAccDao;

	@Autowired
	@Qualifier("ckAccnAttDao")
	private GenericDao<TCkAccnAtt, String> ckAccnAttDao;

	@Autowired
	private GenericDao<TCkMstAccnAttType, TCkMstAccnAttTypeId> ckMstAccnAttTypeDao;

	@Autowired
	@Qualifier("ccmAccnService")
	protected IEntityService<TCoreAccn, String, CoreAccn> ccmAccnService;

	@Autowired
	@Qualifier("ccmAccnAttService")
	private IEntityService<TCoreAccnAtt, String, CoreAccnAtt> ccmAccnAttService;

	@Autowired
	@Qualifier("mstAttTypeService")
	private IEntityService<TMstAttType, String, MstAttType> mstAttTypeService;

	@Autowired
	@Qualifier("coreAccnAttDao")
	private GenericDao<TCoreAccnAtt, String> coreAccnAttDao;

	@Autowired
	CkAccnAttService ckAccnAttService;

	@Autowired
	protected CkJobService ckJobService;

	@Autowired
	protected PrincipalUtilService principalUtilService;

	@Autowired
	@Qualifier("AccnRegistrationWorkflowServiceImpl")
	protected ICkWorkflowService<TCoreAccn, CoreAccn> accnRegistrationWorkflowServiceImpl;

	@Autowired
	@Qualifier("AccnSuspensionWorkflowServiceImpl")
	protected ICkWorkflowService<TCoreAccn, CoreAccn> accnSuspensionWorkflowServiceImpl;

	@Autowired
	@Qualifier("AccnResumptionWorkflowServiceImpl")
	protected ICkWorkflowService<TCoreAccn, CoreAccn> accnResumptionWorkflowServiceImpl;

	@Autowired
	@Qualifier("AccnTerminationWorkflowServiceImpl")
	protected ICkWorkflowService<TCoreAccn, CoreAccn> accnTerminationWorkflowServiceImpl;

	@Autowired
	private CkWorkflowRemarkDao ckWorkflowRemarkDao;

	@Autowired
	private CkMstRemarkTypeDao ckMstRemarkTypeDao;

	@Autowired
	private CkMstWorkflowTypeDao ckMstWorkflowTypeDao;

	@Autowired
	private CoreAccnConfigDao coreAccnConfigDao;

	@Autowired
	protected SysParam sysParam;

	@Autowired
	protected Validator validator;

	@Autowired
	protected AccountValidator accnValidator;

	@Autowired
	protected ICkSeqNoService seqNoService;

	@Autowired
	protected ApplicationEventPublisher eventPublisher;

	@Autowired
	private CkWhitelabelService wlService;

	@Autowired
	private CkFileUtil ckFileUtil;

	@Autowired
	private CkCoreAccnService ckAccnService;

	@Autowired
	private CkAccnOpmDao accnOpmDao;

	@Autowired
	private CkAccnDao ckAccnDao;

	@Autowired
	private CkSvcSerivce svcSerivce;

	@Autowired
	private CkSvcSubDao svcSubDao;

	public enum AccAction {
		ACTIVATE("activate"), DEACTIVATE("deactivate"), REGISTER("register"), SUSPEND("suspend"),
		RESUMPTION("resumption"), TERMINATE("terminate"), DELETE("delete"), SUBMIT("submit"), APPROVE("approve"),
		REJECT("reject");

		String action;

		AccAction(String action) {
			this.action = action;
		}

		public String getAction() {
			return this.action;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CoreAccn getAccountDetails(String accnId) throws Exception {
		log.debug("getAccountDetails");
		try {

			if (StringUtils.isEmpty(accnId))
				throw new ParameterException("accnId is null");

			Principal principal = principalUtilService.getPrincipal();
			if (principal == null)
				throw new ProcessingException("principal is null");

			CoreAccn coreAccn = ccmAccnService.findById(accnId);
			if (coreAccn == null)
				throw new ProcessingException("account details not found");

			return coreAccn;

		} catch (Exception e) {
			log.error("getAccountDetails", e);
			throw e;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public TCoreAccn getAccountDetailsByRegNo(String companyRegtaxNo) throws Exception {
		log.debug("getAccountDetailsByRegNo");
		try {

			if (StringUtils.isEmpty(companyRegtaxNo))
				throw new ParameterException("companyRegtaxNo is null");

			String hql = "from TCoreAccn o where o.accnCoyRegn = :companyRegTaxNo and o.accnStatus = :status";
			Map<String, Object> params = new HashMap<>();
			params.put("companyRegTaxNo", companyRegtaxNo);
			params.put("status", RecordStatus.ACTIVE.getCode());

			List<TCoreAccn> listCoreAccn = coreAccDao.getByQuery(hql, params);

			if (listCoreAccn != null && listCoreAccn.size() > 0) {
				// expecting only one
				TCoreAccn accnE = listCoreAccn.get(0);
				return accnE;
			}

			return null;

		} catch (Exception e) {
			log.error("getAccountDetailsByRegNo", e);
			throw e;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String checkCompanyRegNoUnique(CoreAccn accn) throws Exception {
		boolean isUnique = true;
		// check if the tax no/company registration no. is uniqe
		String hql = "from TCoreAccn o where o.accnCoyRegn = :crNo and o.accnStatus not in (:status) and o.accnId != :exceptAccnId";
		Map<String, Object> params = new HashMap<>();
		params.put("crNo", accn.getAccnCoyRegn());
		params.put("status",
				Arrays.asList(CoreAccnStateEnum.REG_REJECTED.getCode(), CoreAccnStateEnum.REG_DELETED.getCode(),
						CoreAccnStateEnum.SUS_REJECTED.getCode(), CoreAccnStateEnum.RESUMPTION_REJECTED.getCode(),
						CoreAccnStateEnum.TER_REJECTED.getCode()));
		params.put("exceptAccnId", accn.getAccnId());

		List<TCoreAccn> listAccns = coreAccDao.getByQuery(hql, params);
		// if it returns something, that means it's not unique
		if (listAccns != null && listAccns.size() > 0) {
			isUnique = false;
		}

		return isUnique ? "available" : "not-available";
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CkManageAccn getCKAccountDetails(String accnId)
			throws ParameterException, ProcessingException, EntityNotFoundException, Exception {
		CkManageAccn ckManageAccn = new CkManageAccn();
		String decodedAccnId = new String(Base64.getDecoder().decode(accnId));
		CoreAccn coreAccn = ccmAccnService.findById(decodedAccnId);
		if (coreAccn == null)
			throw new ProcessingException("account details not found");
		ckManageAccn.setAccnDetails(coreAccn);

		// set the accnProcessType based on the accnStatus
		if (Arrays.asList(CoreAccnStateEnum.NEW.getCode(), CoreAccnStateEnum.REG_REJECTED.getCode(),
				CoreAccnStateEnum.REG_SUBMITTED.getCode()).contains(coreAccn.getAccnStatus())) {
			ckManageAccn.setAccnProcessType(MstEntityTypes.ACCN_REGISTRATION.name());
		} else if (Arrays.asList(CoreAccnStateEnum.SUS_SUBMITTED.getCode(), CoreAccnStateEnum.SUS_APPROVED.getCode())
				.contains(coreAccn.getAccnStatus())) {
			ckManageAccn.setAccnProcessType(MstEntityTypes.ACCN_SUSPENSION.name());
		} else if (Arrays.asList(CoreAccnStateEnum.RESUMPTION_SUBMITTED.getCode(),
				CoreAccnStateEnum.RESUMPTION_APPROVED.getCode()).contains(coreAccn.getAccnStatus())) {
			ckManageAccn.setAccnProcessType(MstEntityTypes.ACCN_RESUMPTION.name());
		} else if (Arrays.asList(CoreAccnStateEnum.TER_SUBMITTED.getCode(), CoreAccnStateEnum.TER_APPROVED.getCode())
				.contains(coreAccn.getAccnStatus())) {
			ckManageAccn.setAccnProcessType(MstEntityTypes.ACCN_TERMINATION.name());
		}

		// Updates for sageAccpacId
		this.getAccountConfigDetails(ckManageAccn, decodedAccnId, coreAccn);

		// Load from T_CK_ACCN for other properties
		ckAccnService.loadCkAccnDetails(decodedAccnId, ckManageAccn);

		// check if there's already a record in T_CK_ACCN_OPM then determine to display
		// the register button in FE (fetch for active and suspended)
		TCkAccnOpm ckAccnOpm = accnOpmDao.findByAccnId(decodedAccnId,
				Arrays.asList(RecordStatus.ACTIVE.getCode(), RecordStatus.SUSPENDED.getCode()));
		if (ckAccnOpm != null)
			ckManageAccn.setOpmRegistered(true);

		// Get the background image from white label table
		ckManageAccn.setBgImageWl(this.getLoginBackgroundImage(decodedAccnId));
		ckManageAccn.setCompanyLogo(this.getCompanyLogo(decodedAccnId));

		ckManageAccn.setCkAccn(this.getCkAccn(decodedAccnId));

		// Load the subscribed services
		this.loadSvcSubscriptions(decodedAccnId, ckManageAccn);

		return ckManageAccn;
	}

	public String getLoginBackgroundImageStr(String accnId) throws Exception {
		try {
			CkCtWhitelabel wl = wlService.getDetails(accnId);
			if (wl != null) {
				return wlService.getBackgroundImage(wl.getWlName());
			}

		} catch (Exception ex) {
			log.error("getLoginBackgroundImage", ex);
		}

		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CoreAccnAtt getCompanyLogo(String accnId) throws Exception {
		CoreAccnAtt dto = new CoreAccnAtt();
		if (StringUtils.isNotBlank(accnId)) {
			String hql = "from TCoreAccnAtt o where o.TCoreAccn.accnId = :accnId and o.aattStatus='A' and o.TMstAttType.mattId=:attType";
			Map<String, Object> params = new HashMap<>();
			params.put("accnId", accnId);
			params.put("attType", AttachmentTypes.CLO.getId());
			List<TCoreAccnAtt> accnAttchList = coreAccnAttDao.getByQuery(hql, params);
			if (accnAttchList != null && accnAttchList.size() > 0) {
				// expecting only one active company logo
				TCoreAccnAtt entity = accnAttchList.get(0);
				Hibernate.initialize(entity.getTCoreAccn());
				Hibernate.initialize(entity.getTMstAttType());
				dto = new CoreAccnAtt(entity);
				dto.setTCoreAccn(new CoreAccn(entity.getTCoreAccn()));
				dto.setTMstAttType(new MstAttType(entity.getTMstAttType()));
			}
		}

		return dto;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String getCompanyLogoBase64Str(String accnId) throws Exception {
		CoreAccnAtt att = getCompanyLogo(accnId);
		if (att != null && StringUtils.isNotBlank(att.getAattLoc())) {
			byte[] bytes = Files.readAllBytes(Paths.get(att.getAattLoc()));
			String base64Str = Base64.getEncoder().encodeToString(bytes);

			if (StringUtils.isBlank(base64Str)) {
				throw new Exception("Logo image not set.");
			}

			return base64Str;
		}

		return null;
	}

	/**
	 * Retrieves the supporting documents configured in T_CORE_SYSPARAM.
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public List<CkAccnSuppDocsJson> getSuppDocs() throws ProcessingException, EntityNotFoundException, Exception {
		log.info("getSuppDocs");

		List<CkAccnSuppDocsJson> docs = new ArrayList<CkAccnSuppDocsJson>();
		try {

			DetachedCriteria criteria = DetachedCriteria.forClass(TCkMstAccnAttType.class);
			criteria.add(Restrictions.eq("atStatus", 'A'));
			List<TCkMstAccnAttType> accnAtts = ckMstAccnAttTypeDao.getByCriteria(criteria);

			int seq = 0;
			if (accnAtts.size() > 0) {
				for (TCkMstAccnAttType accnAtt : accnAtts) {
					CkAccnSuppDocsJson obj = new CkAccnSuppDocsJson();
					obj.setSeq(seq);
					obj.setMandatory(accnAtt.getAtMandatory() == 'Y' ? true : false);
					obj.setAttType(accnAtt.getAtName());
					obj.setWfTypeId(accnAtt.getTCkMstWorkflowType().getWktId());
					obj.setWfTypeDesc(accnAtt.getTCkMstWorkflowType().getWktDesc());
					docs.add(obj);
					seq++;
				}
			}

			return docs;
		} catch (Exception e) {
			log.error("getSuppDocs", e);
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

			List<CoreAccn> en = filterBy(filterRequest);
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
	public List<CoreAccn> filterBy(EntityFilterRequest filterRequest)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		log.debug("filterBy");

		try {
			if (null == filterRequest)
				throw new ParameterException("param filterRequest null");

			CkCoreAccn dto = this.whereDto(filterRequest);
			if (null == dto)
				throw new ProcessingException("whereDto null");

			filterRequest.setTotalRecords(this.countByAnd(dto));

			String selectClause = "from TCoreAccn o ";
			String orderByClause = filterRequest.getOrderBy().toString();
			List<TCoreAccn> entities = this.findEntitiesByAnd(dto, selectClause, orderByClause,
					filterRequest.getDisplayLength(), filterRequest.getDisplayStart());
			List<CoreAccn> dtos = entities.stream().map(x -> {
				try {
					CoreAccn accn = dtoFromEntity(x);
					return accn;
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

	protected CkCoreAccn whereDto(EntityFilterRequest filterRequest) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		log.debug("whereDto");

		try {
			if (null == filterRequest)
				throw new ParameterException("param filterRequest null");

			CkCoreAccn dto = new CkCoreAccn();

			MstAccnType accnType = new MstAccnType();
			dto.setTMstAccnType(accnType);

			CoreContact coreContact = new CoreContact();
			dto.setAccnContact(coreContact);

			SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");

			for (EntityWhere entityWhere : filterRequest.getWhereList()) {
				Optional<String> opValue = Optional.ofNullable(entityWhere.getValue());
				if (!opValue.isPresent())
					continue;

				if (entityWhere.getAttribute().equalsIgnoreCase("accnId"))
					dto.setAccnId(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("accnName"))
					dto.setAccnName(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("TMstAccnType.atypId"))
					accnType.setAtypId(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("TMstAccnType.atypDescription"))
					accnType.setAtypDescription(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("accnCoyRegn"))
					dto.setAccnCoyRegn(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("accnContact.contactTel"))
					coreContact.setContactTel(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("accnContact.contactFax"))
					coreContact.setContactFax(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("accnContact.contactEmail"))
					coreContact.setContactEmail(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("accnDtCreate"))
					dto.setAccnDtCreate(sdfDate.parse(opValue.get()));
				if (entityWhere.getAttribute().equalsIgnoreCase("accnDtReg"))
					dto.setAccnDtReg(sdfDate.parse(opValue.get()));
				if (entityWhere.getAttribute().equalsIgnoreCase("accnUidCreate"))
					dto.setAccnUidCreate(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("accnStatus"))
					dto.setAccnStatus(opValue.get().charAt(0));

				// TODO: submitted date and submitted by

				// history toggle
				if (entityWhere.getAttribute().equalsIgnoreCase(HISTORY)) {
					dto.setHistory(opValue.get());
				} else if (entityWhere.getAttribute().equalsIgnoreCase(DEFAULT)) {
					dto.setHistory(opValue.get());
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

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public int countByAnd(CkCoreAccn dto) throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		log.debug("countByAnd");

		try {
			if (null == dto)
				throw new ParameterException("param dto null");

			String whereClause = this.getWhereClause(dto, false); // abstract callback
			HashMap<String, Object> parameters = this.getParameters(dto); // abstract callback

			int count = 0;
			if (StringUtils.isNotEmpty(whereClause) && null != parameters && parameters.size() > 0) {
				count = this.coreAccDao.count("SELECT COUNT(o) FROM TCoreAccn o" + whereClause, parameters);
			} else {
				count = this.coreAccDao.count("SELECT COUNT(o) FROM TCoreAccn o");
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

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	protected List<TCoreAccn> findEntitiesByAnd(CkCoreAccn dto, String selectClause, String orderByClause, int limit,
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

			List<TCoreAccn> entities = coreAccDao.getByQuery(hqlQuery, parameters, limit, offset);
			for (TCoreAccn entity : entities)
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

	public CkCoreAccn dtoFromEntity(TCoreAccn entity) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		log.debug("dtoFromEntity");

		try {
			if (null == entity)
				throw new ParameterException("param entity null");

			CkCoreAccn dto = new CkCoreAccn(entity);
			// no deep copy with BeansUtil

			Optional<TMstAccnType> opMstAccnType = Optional.ofNullable(entity.getTMstAccnType());
			dto.setTMstAccnType(opMstAccnType.isPresent() ? new MstAccnType(opMstAccnType.get()) : null);

			Optional<TCoreContact> opCoreContact = Optional.ofNullable(entity.getAccnContact());
			dto.setAccnContact(opCoreContact.isPresent() ? new CoreContact(opCoreContact.get()) : null);

			return dto;
		} catch (ParameterException ex) {
			log.error("entityFromDTO", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("dtoFromEntity", ex);
			throw new ProcessingException(ex);
		}
	}

	protected String getWhereClause(CkCoreAccn dto, boolean wherePrinted)
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
			// only proceed if it's SP
			if (opAccnType.isPresent() && opAccnType.get().getAtypId().equals(AccountTypes.ACC_TYPE_SP.name())) {

				// Exclude Registration Rejected from filter
				searchStatement.append(getOperator(wherePrinted)).append("o.accnStatus NOT IN :excludeAccnStatus");
				wherePrinted = true;

				// Only include SP/CO/FF/TO
				searchStatement.append(getOperator(wherePrinted)).append("o.TMstAccnType.atypId IN :includeAccnTypes");
				wherePrinted = true;

				searchStatement.append(getOperator(wherePrinted)).append("o.accnStatus IN :accnStatus");
				wherePrinted = true;

				if (dto.getAccnId() != null && !StringUtils.isEmpty(dto.getAccnId())) {
					searchStatement.append(getOperator(wherePrinted) + "o.accnId LIKE :accnId");
					wherePrinted = true;
				}
				if (dto.getAccnName() != null && !StringUtils.isEmpty(dto.getAccnName())) {
					searchStatement.append(getOperator(wherePrinted) + "o.accnName LIKE :accnName");
					wherePrinted = true;
				}

				if (dto.getTMstAccnType() != null) {
					if (StringUtils.isNotBlank(dto.getTMstAccnType().getAtypId())) {
						searchStatement.append(getOperator(wherePrinted) + "o.TMstAccnType.atypId = :atypId");
						wherePrinted = true;
					}
					if (StringUtils.isNotBlank(dto.getTMstAccnType().getAtypDescription())) {
						searchStatement.append(
								getOperator(wherePrinted) + "o.TMstAccnType.atypDescription LIKE :atypDescription");
						wherePrinted = true;
					}
				}

				if (null != dto.getAccnCoyRegn() && !StringUtils.isEmpty(dto.getAccnCoyRegn())) {
					searchStatement.append(getOperator(wherePrinted) + "o.accnCoyRegn LIKE :accnCoyRegn");
					wherePrinted = true;
				}

				if (dto.getAccnContact() != null) {
					if (!StringUtils.isEmpty(dto.getAccnContact().getContactTel())) {
						searchStatement.append(getOperator(wherePrinted) + "o.accnContact.contactTel LIKE :contactTel");
						wherePrinted = true;
					}
					if (!StringUtils.isEmpty(dto.getAccnContact().getContactFax())) {
						searchStatement.append(getOperator(wherePrinted) + "o.accnContact.contactFax LIKE :contactFax");
						wherePrinted = true;
					}
					if (!StringUtils.isEmpty(dto.getAccnContact().getContactEmail())) {
						searchStatement
								.append(getOperator(wherePrinted) + "o.accnContact.contactEmail LIKE :contactEmail");
						wherePrinted = true;
					}
				}

				if (null != dto.getAccnDtCreate()) {
					searchStatement.append(
							getOperator(wherePrinted) + "DATE_FORMAT(o.accnDtCreate,'%d/%m/%Y') = :accnDtCreate");
					wherePrinted = true;
				}

				if (null != dto.getAccnDtReg()) {
					searchStatement
							.append(getOperator(wherePrinted) + "DATE_FORMAT(o.accnDtReg,'%d/%m/%Y') = :accnDtReg");
					wherePrinted = true;
				}

				if (!StringUtils.isEmpty(dto.getAccnUidCreate())) {
					searchStatement.append(getOperator(wherePrinted) + "o.accnUidCreate LIKE :accnUidCreate");
					wherePrinted = true;
				}

				if (Character.isAlphabetic(dto.getAccnStatus())) {
					searchStatement.append(getOperator(wherePrinted) + "o.accnStatus = :accnStatus");
					wherePrinted = true;
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

	protected HashMap<String, Object> getParameters(CkCoreAccn dto) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
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
								AccountTypes.ACC_TYPE_FF.name(), AccountTypes.ACC_TYPE_SP.name(),
								AccountTypes.ACC_TYPE_TO_WJ.name()));

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
									CoreAccnStateEnum.SUS_SUBMITTED.getCode(),
									CoreAccnStateEnum.TER_SUBMITTED.getCode(), CoreAccnStateEnum.REG_APPROVED.getCode(),
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
	protected TCoreAccn initEnity(TCoreAccn entity) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		if (null != entity) {
			Hibernate.initialize(entity.getTMstAccnType());
			Hibernate.initialize(entity.getAccnContact());
		}
		return entity;
	}

	protected String getOperator(boolean whereprinted) {
		return whereprinted ? " AND " : " WHERE ";
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CkManageAccn createCompanyAccount(CkManageAccn ckAccn) throws ValidationException, Exception {
		try {

			Principal principal = principalUtilService.getPrincipal();
			if (principal == null)
				throw new ProcessingException("principal is null");
			if (ckAccn.getAccnDetails() == null)
				throw new ProcessingException("principal is null");

			CoreAccn newAccn = ckAccn.getAccnDetails();
			newAccn.setAccnId(seqNoService.getNextSequence(CkSeqNoServiceImpl.SeqNoCode.CT_ACCN_CODE.name()));
			newAccn.setAccnStatus(CoreAccnStateEnum.NEW.getCode());
			newAccn.setAccnDtReg(new Date());// init to current date this will be updated once approved?

			// Call it here before the actual add
			List<ValidationError> errors = accnValidator.validateCreate(ckAccn, principal);
			if (!errors.isEmpty()) {
				throw new ValidationException(this.validationErrorMap(errors));
			}

			ccmAccnService.add(newAccn, principal);
			// Add or update SAGE_ACCN_ID in T_CORE_ACCN_CONFIG
			this.addorUpdateAccnConfig(ckAccn.getAccnDetails().getAccnId(), ACCN_CONFIG_SAGE_ACCN_ID,
					ckAccn.getSageAccpacId(), principal);

			// Add or update FINANCE_OPTIONS in T_CORE_ACCN_CONFIG
			String accnType = newAccn.getTMstAccnType().getAtypId();

			// Add or update MOBILE_ENABLED in T_CORE_ACCN_CONFIG
			if (accnType.equalsIgnoreCase(AccountTypes.ACC_TYPE_TO.name()))
				this.addorUpdateAccnConfig(ckAccn.getAccnDetails().getAccnId(), ACCN_CONFIG_MOBILE_ENABLED,
						ckAccn.getMobileEnabled(), principal);

			// only applicable for this account types for financing options
			if (accnType.equalsIgnoreCase(AccountTypes.ACC_TYPE_TO.name())
					|| accnType.equalsIgnoreCase(AccountTypes.ACC_TYPE_CO.name())
					|| accnType.equalsIgnoreCase(AccountTypes.ACC_TYPE_FF.name())) {
				ckAccnService.updateCkAccnDetails(ckAccn.getAccnDetails().getAccnId(), ckAccn, principal, false);
			}


			this.updateCkAccn(ckAccn.getAccnDetails().getAccnId(), ckAccn);

			// update the service subscription
			this.saveOrUpdateSvcSubscription(ckAccn.getAccnDetails().getAccnId(), ckAccn, principal);

			return ckAccn;
		} catch (ValidationException ex) {
			throw ex;

		} catch (Exception exception) {
			throw exception;
		}

	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public CkManageAccn updateCompanyAccount(String accnId, CkManageAccn ckManageAccn)
			throws ValidationException, Exception {
		try {

			if (StringUtils.isEmpty(accnId))
				throw new ParameterException("accnId is null");

			Principal principal = principalUtilService.getPrincipal();
			if (principal == null)
				throw new ProcessingException("principal is null");
			if (ckManageAccn.getAccnDetails() == null)
				throw new ProcessingException("ckManageAccn is null");

			if (ckManageAccn.getAction() != null && ckManageAccn.getAction() != FormActions.DELETE) {
				List<ValidationError> errors = accnValidator.validateSubmit(ckManageAccn, principal);
				if (!errors.isEmpty()) {
					throw new ValidationException(this.validationErrorMap(errors));
				}
			}

			if (ckManageAccn.getAction() == null && StringUtils.isBlank(ckManageAccn.getAccnProcessType())) {
				List<ValidationError> errors = accnValidator.validateUpdate(ckManageAccn, principal);
				if (!errors.isEmpty()) {
					throw new ValidationException(this.validationErrorMap(errors));
				}
			}

			if (ckManageAccn.getAction() != null || StringUtils.isNotBlank(ckManageAccn.getAccnProcessType())) {
				// do the state movement here, if not set specially in clictruck, use the below
				// one
				if (ckManageAccn.getAccnServiceType() != null) {
					this.moveAccnState(ckManageAccn, principal, ckManageAccn.getAccnServiceType());
				} else {
					this.moveAccnState(ckManageAccn, principal);
				}

			}

			ccmAccnService.update(ckManageAccn.getAccnDetails(), principal);
			// Add or update SAGE_ACCN_ID in T_CORE_ACCN_CONFIG
			this.addorUpdateAccnConfig(ckManageAccn.getAccnDetails().getAccnId(), ACCN_CONFIG_SAGE_ACCN_ID,
					ckManageAccn.getSageAccpacId(), principal);

			// Add or update FINANCE_OPTIONS in T_
			String accnType = ckManageAccn.getAccnDetails().getTMstAccnType().getAtypId();

			if (accnType.equalsIgnoreCase(AccountTypes.ACC_TYPE_CO.name())
					|| accnType.equalsIgnoreCase(AccountTypes.ACC_TYPE_FF.name())) {
				// Delete existing mobile option
				this.deleteConfig(ckManageAccn.getAccnDetails().getAccnId(), ACCN_CONFIG_MOBILE_ENABLED);
			}

			if (accnType.equalsIgnoreCase(AccountTypes.ACC_TYPE_TO.name())) {
				this.addorUpdateAccnConfig(ckManageAccn.getAccnDetails().getAccnId(), ACCN_CONFIG_MOBILE_ENABLED,
						ckManageAccn.getMobileEnabled(), principal);
				// Delete existing finance option
				this.deleteConfig(ckManageAccn.getAccnDetails().getAccnId(), ACCN_CONFIG_FINANCE_OPTIONS);
			}

			// only applicable for this account types for financing options
			if (accnType.equalsIgnoreCase(AccountTypes.ACC_TYPE_CO.name())
					|| accnType.equalsIgnoreCase(AccountTypes.ACC_TYPE_FF.name())
					|| accnType.equalsIgnoreCase(AccountTypes.ACC_TYPE_TO.name())) {
				ckAccnService.updateCkAccnDetails(ckManageAccn.getAccnDetails().getAccnId(), ckManageAccn, principal,
						true);
			}

			// Update the white label record, if have otherwise add
			if (ckManageAccn.getBgImageWl() != null) {
				// update white label record
				CoreAccn coreAccn = ccmAccnService.findById(ckManageAccn.getAccnDetails().getAccnId());
				if (coreAccn == null)
					throw new ProcessingException("account details not found");
				ckManageAccn.getBgImageWl().setTCoreAccn(coreAccn);
				ckManageAccn.setBgImageWl(wlService.updateDetails(ckManageAccn.getBgImageWl(), principal));
			}

			// Update/Add the company logo
			this.updateCompanyLogo(ckManageAccn, principal);

			this.updateCkAccn(ckManageAccn.getAccnDetails().getAccnId(), ckManageAccn);

			// update the service subscription
			this.saveOrUpdateSvcSubscription(ckManageAccn.getAccnDetails().getAccnId(), ckManageAccn, principal);

			return ckManageAccn;
		} catch (ValidationException ex) {
			throw ex;
		} catch (Exception ex) {
			throw ex;
		}

	}

	/**
	 * 
	 * @param accnId
	 * @param key
	 * @throws Exception
	 */
	private void deleteConfig(String accnId, String key) throws Exception {

		Map<String, Object> params = new HashMap<>();
		params.put("accnId", accnId);
		params.put("key", key);

		int deleteConfig = coreAccnConfigDao.executeUpdate(
				"DELETE FROM TCoreAccnConfig c WHERE c.id.acfgAccnid = :accnId AND c.id.acfgKey = :key", params);
		log.info("delete account config: " + deleteConfig);
	}

	/**
	 * @param accnId
	 * @param ckManageAccn
	 * @throws Exception
	 */
	private void addorUpdateAccnConfig(String accnId, String key, String value, Principal principal) throws Exception {
		TCoreAccnConfigId coreAccnConfigId = new TCoreAccnConfigId();
		coreAccnConfigId.setAcfgAccnid(accnId);
		coreAccnConfigId.setAcfgKey(key);
		TCoreAccnConfig accnConfig = coreAccnConfigDao.getByIdAndStatus(coreAccnConfigId, Constant.ACTIVE_STATUS);
		if (null != accnConfig) {
			accnConfig.setAcfgVal(null != value ? value : StringUtils.EMPTY);
			accnConfig.setAcfgDtLupd(new Date());
			coreAccnConfigDao.update(accnConfig);
		} else {
			TCoreAccn coreAccn = coreAccDao.find(accnId);
			accnConfig = new TCoreAccnConfig(new TCoreAccnConfigId(coreAccn.getAccnId(), key), coreAccn, new Date(),
					new Date(), 1, Constant.ACTIVE_STATUS);
			accnConfig.setAcfgVal(null != value ? value : StringUtils.EMPTY);
			accnConfig.setAcfgDesc(key);
			accnConfig.setAcfgDtCreate(new Date());
			accnConfig.setAcfgUidCreate(principal.getUserId());
			accnConfig.setAcfgDtLupd(new Date());
			accnConfig.setAcfgUidLupd(principal.getUserId());
			coreAccnConfigDao.add(accnConfig);
		}
	}

	public CkManageAccn moveAccnState(CkManageAccn ckManageAccn, Principal principal)
			throws ParameterException, ProcessingException, Exception {
		try {

			if (StringUtils.isEmpty(ckManageAccn.getAccnDetails().getAccnId()))
				throw new ParameterException("accnId is null or empty");

			String action = ckManageAccn.getAction().name();
			if (!isActionValid(action))
				throw new ProcessingException("invalid action");

			CoreAccn coreAccn = ccmAccnService.findById(ckManageAccn.getAccnDetails().getAccnId());

			// check the actionProcessType
			if (ckManageAccn.getAccnProcessType().equalsIgnoreCase(MstEntityTypes.ACCN_REGISTRATION.name())) {
				accnRegistrationWorkflowServiceImpl.moveState(ckManageAccn.getAction(), ckManageAccn.getAccnDetails(),
						principal, ServiceTypes.CLICTRUCK);

				// TODO when a ccount is approved create the admin user here

				// Delete is only for account registration. For suspension/termination to be
				// deleted, it should be rejected by the finance head
				// and account status will be reverted to Active.
				if (ckManageAccn.getAction() != FormActions.DELETE) {
					// when the account is approved, rejected take log the remarks
					this.saveWorkflowRemark(ckManageAccn, WorkflowTypeEnum.ACCN_REG.name(), principal);

					// TODO publish event for email notification
					publishPostEvents(ckManageAccn, coreAccn);

				}

			} else if (ckManageAccn.getAccnProcessType().equalsIgnoreCase(MstEntityTypes.ACCN_SUSPENSION.name())) {
				// for account suspension, there is no need to validate if there are existing
				// payments. The scheduler will do the payment cancellation
				// jobs will be halted, and CO/FF cannot create job as it will be disabled.
				accnSuspensionWorkflowServiceImpl.moveState(ckManageAccn.getAction(), ckManageAccn.getAccnDetails(),
						principal, ServiceTypes.CLICTRUCK);

				// when the account suspension is approved, rejected take log the remarks
				this.saveWorkflowRemark(ckManageAccn, WorkflowTypeEnum.ACCN_SUS.name(), principal);

				// TODO publish event for email notification
				publishPostEvents(ckManageAccn, coreAccn);

			} else if (ckManageAccn.getAccnProcessType().equalsIgnoreCase(MstEntityTypes.ACCN_RESUMPTION.name())) {
				// for account resumption, there is no need to validate if there are existing
				// payments. The scheduler will do the payment cancellation
				// jobs will be halted, and CO/FF cannot create job as it will be disabled.
				accnResumptionWorkflowServiceImpl.moveState(ckManageAccn.getAction(), ckManageAccn.getAccnDetails(),
						principal, ServiceTypes.CLICTRUCK);

				// when the account resumption is approved, rejected take log the remarks
				this.saveWorkflowRemark(ckManageAccn, WorkflowTypeEnum.ACCN_RESUMPT.name(), principal);

				// TODO publish event for email notification
				publishPostEvents(ckManageAccn, coreAccn);

			} else if (ckManageAccn.getAccnProcessType().equalsIgnoreCase(MstEntityTypes.ACCN_TERMINATION.name())) {

				// Validate Termination approval
				List<TCkJob> activeJobs = ckJobService
						.findActiveJobsByAccnId(ckManageAccn.getAccnDetails().getAccnId());
				if (ckManageAccn.getAction().name().equalsIgnoreCase(FormActions.APPROVE.name())
						&& activeJobs.size() > 0) {
					ckManageAccn.setAccnTerminable(true);
					return ckManageAccn;
				}

				accnTerminationWorkflowServiceImpl.moveState(ckManageAccn.getAction(), ckManageAccn.getAccnDetails(),
						principal, ServiceTypes.CLICTRUCK);

				// when the account termination is approved, rejected take log the remarks
				this.saveWorkflowRemark(ckManageAccn, WorkflowTypeEnum.ACCN_TERM.name(), principal);

				// TODO publish event for email notification
				publishPostEvents(ckManageAccn, coreAccn);

			} else {
				if (action.equalsIgnoreCase(AccAction.ACTIVATE.getAction())
						|| action.equalsIgnoreCase(AccAction.DEACTIVATE.getAction())) {
					coreAccn.setAccnStatus(
							action.equalsIgnoreCase(AccAction.ACTIVATE.getAction()) ? RecordStatus.ACTIVE.getCode()
									: RecordStatus.INACTIVE.getCode());
					CoreAccn accn = ccmAccnService.update(coreAccn, principal);
					ckManageAccn.setAccnDetails(accn);
				}
			}

			return ckManageAccn;

		} catch (Exception e) {
			log.error("moveAccnState", e);
			throw e;
		}
	}

	/** Overloaded to set specific service type */
	public CkManageAccn moveAccnState(CkManageAccn ckManageAccn, Principal principal, ServiceTypes serviceType)
			throws ParameterException, ProcessingException, Exception {
		try {

			if (StringUtils.isEmpty(ckManageAccn.getAccnDetails().getAccnId()))
				throw new ParameterException("accnId is null or empty");

			String action = ckManageAccn.getAction().name();
			if (!isActionValid(action))
				throw new ProcessingException("invalid action");

			CoreAccn coreAccn = ccmAccnService.findById(ckManageAccn.getAccnDetails().getAccnId());

			// check the actionProcessType
			if (ckManageAccn.getAccnProcessType().equalsIgnoreCase(MstEntityTypes.ACCN_REGISTRATION.name())) {
				accnRegistrationWorkflowServiceImpl.moveState(ckManageAccn.getAction(), ckManageAccn.getAccnDetails(),
						principal, serviceType);

				// TODO when a ccount is approved create the admin user here

				// Delete is only for account registration. For suspension/termination to be
				// deleted, it should be rejected by the finance head
				// and account status will be reverted to Active.
				if (ckManageAccn.getAction() != FormActions.DELETE) {
					// when the account is approved, rejected take log the remarks
					this.saveWorkflowRemark(ckManageAccn, WorkflowTypeEnum.ACCN_REG.name(), principal);

					// TODO publish event for email notification
					publishPostEvents(ckManageAccn, coreAccn);

				}

			} else if (ckManageAccn.getAccnProcessType().equalsIgnoreCase(MstEntityTypes.ACCN_SUSPENSION.name())) {
				// for account suspension, there is no need to validate if there are existing
				// payments. The scheduler will do the payment cancellation
				// jobs will be halted, and CO/FF cannot create job as it will be disabled.
				accnSuspensionWorkflowServiceImpl.moveState(ckManageAccn.getAction(), ckManageAccn.getAccnDetails(),
						principal, serviceType);

				// when the account suspension is approved, rejected take log the remarks
				this.saveWorkflowRemark(ckManageAccn, WorkflowTypeEnum.ACCN_SUS.name(), principal);

				// TODO publish event for email notification
				publishPostEvents(ckManageAccn, coreAccn);

			} else if (ckManageAccn.getAccnProcessType().equalsIgnoreCase(MstEntityTypes.ACCN_RESUMPTION.name())) {
				// for account resumption, there is no need to validate if there are existing
				// payments. The scheduler will do the payment cancellation
				// jobs will be halted, and CO/FF cannot create job as it will be disabled.
				accnResumptionWorkflowServiceImpl.moveState(ckManageAccn.getAction(), ckManageAccn.getAccnDetails(),
						principal, serviceType);

				// when the account resumption is approved, rejected take log the remarks
				this.saveWorkflowRemark(ckManageAccn, WorkflowTypeEnum.ACCN_RESUMPT.name(), principal);

				// TODO publish event for email notification
				publishPostEvents(ckManageAccn, coreAccn);

			} else if (ckManageAccn.getAccnProcessType().equalsIgnoreCase(MstEntityTypes.ACCN_TERMINATION.name())) {

				// Validate Termination approval
				List<TCkJob> activeJobs = ckJobService
						.findActiveJobsByAccnId(ckManageAccn.getAccnDetails().getAccnId());
				if (ckManageAccn.getAction().name().equalsIgnoreCase(FormActions.APPROVE.name())
						&& activeJobs.size() > 0) {
					ckManageAccn.setAccnTerminable(true);
					return ckManageAccn;
				}

				accnTerminationWorkflowServiceImpl.moveState(ckManageAccn.getAction(), ckManageAccn.getAccnDetails(),
						principal, serviceType);

				// when the account termination is approved, rejected take log the remarks
				this.saveWorkflowRemark(ckManageAccn, WorkflowTypeEnum.ACCN_TERM.name(), principal);

				// TODO publish event for email notification
				publishPostEvents(ckManageAccn, coreAccn);

			} else {
				if (action.equalsIgnoreCase(AccAction.ACTIVATE.getAction())
						|| action.equalsIgnoreCase(AccAction.DEACTIVATE.getAction())) {
					coreAccn.setAccnStatus(
							action.equalsIgnoreCase(AccAction.ACTIVATE.getAction()) ? RecordStatus.ACTIVE.getCode()
									: RecordStatus.INACTIVE.getCode());
					CoreAccn accn = ccmAccnService.update(coreAccn, principal);
					ckManageAccn.setAccnDetails(accn);
				}
			}

			return ckManageAccn;

		} catch (Exception e) {
			log.error("moveAccnState", e);
			throw e;
		}
	}

	private boolean isActionValid(String action) {
		AccAction[] actions = AccAction.values();

		for (AccAction a : actions) {
			if (a.getAction().equalsIgnoreCase(action)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Convert Set<ConstraintViolation<PediAppDosForm>> to Map<String, String>
	 *
	 * @param errors
	 * @return
	 * @throws ProcessingException
	 * @throws JsonProcessingException
	 */
	protected String generateValidationMap(Set<ConstraintViolation<?>> errors) throws ProcessingException, Exception {

		Map<String, String> errorMsg = new HashMap<>();
		errors.stream().forEach(error -> {
			log.error(error.getMessage() + " " + error.getPropertyPath() + "  " + error);
			errorMsg.put(error.getMessage(), error.getMessage());
		});

		String json;
		try {
			json = (new ObjectMapper()).writeValueAsString(errorMsg);
		} catch (JsonProcessingException e) {
			throw new ProcessingException(e.getMessage());
		}

		return json;
	}

	/**
	 * @param dto
	 * @param workflowTypeId
	 * @param principal
	 */
	protected void saveWorkflowRemark(CkManageAccn dto, String workflowTypeId, Principal principal) {
		try {
			if (null == principal)
				throw new ParameterException("param principal null");

			TCkWorkflowRemark tCkWorkflowRemark = new TCkWorkflowRemark();
			tCkWorkflowRemark.setArId(CkUtil.generateId(PREFIX_KEY_WORKFLOW_REMARK));

			String altCode = "";
			switch (dto.getAccnProcessType()) {
			case "ACCN_SUSPENSION":
				altCode = "SUS";
				break;
			case "ACCN_RESUMPTION":
				altCode = "RESUMPT";
				break;
			case "ACCN_TERMINATION":
				altCode = "TER";
				break;
			case "ACCN_REGISTRATION":
				altCode = "REG";
				break;
			default:
				break;
			}

			Optional<TCkMstRemarkType> opCkMstRemarkType = Optional.ofNullable(ckMstRemarkTypeDao
					.find(CoreAccnStateEnum.getDescByStateAndAltCode(dto.getAccnDetails().getAccnStatus(), altCode)));
			tCkWorkflowRemark.setTCkMstRemarkType(opCkMstRemarkType.isPresent() ? opCkMstRemarkType.get() : null);
			Optional<TCkMstWorkflowType> opCkMstWorkflowType = Optional
					.ofNullable(ckMstWorkflowTypeDao.find(workflowTypeId));
			tCkWorkflowRemark.setTCkMstWorkflowType(opCkMstWorkflowType.isPresent() ? opCkMstWorkflowType.get() : null);
			tCkWorkflowRemark.setTCoreAccn(dto.getAccnDetails().toEntity(new TCoreAccn()));
			tCkWorkflowRemark.setArRemark(null != dto.getRemarks() ? dto.getRemarks() : "");
			tCkWorkflowRemark.setAtStatus(RecordStatus.ACTIVE.getCode());
			tCkWorkflowRemark.setAtDtCreate(new Date());
			tCkWorkflowRemark.setAtUidCreate(principal.getUserId());
			tCkWorkflowRemark.setAtDtLupd(new Date());
			tCkWorkflowRemark.setAtUidLupd(principal.getUserId());
			ckWorkflowRemarkDao.add(tCkWorkflowRemark);

		} catch (Exception e) {
			log.error("saveWorkflowRemark ", e);
		}

	}

	private void publishPostEvents(CkManageAccn ckManageAccn, CoreAccn coreAccn) throws Exception {

		ApplicationEvent appEvent = null;

		// for REGISTER
		if (ckManageAccn.getAccnDetails().getAccnStatus() == CoreAccnStateEnum.REG_SUBMITTED.getCode()) {
			appEvent = new SubmitEvent<TCoreAccn, CoreAccn>(this, WorkflowTypeEnum.ACCN_REG, coreAccn);

		} else if (ckManageAccn.getAction() == FormActions.VERIFY) {
			appEvent = new VerifyEvent<TCoreAccn, CoreAccn>(this, WorkflowTypeEnum.ACCN_REG, coreAccn);

		} else if (ckManageAccn.getAccnDetails().getAccnStatus() == CoreAccnStateEnum.REG_APPROVED.getCode()
				&& ckManageAccn.getAccnProcessType().equalsIgnoreCase(MstEntityTypes.ACCN_REGISTRATION.getDesc())) {
			appEvent = new ApproveEvent<TCoreAccn, CoreAccn>(this, WorkflowTypeEnum.ACCN_REG, coreAccn);

		} else if (ckManageAccn.getAccnDetails().getAccnStatus() == CoreAccnStateEnum.REG_REJECTED.getCode()) {
			appEvent = new RejectEvent<TCoreAccn, CoreAccn>(this, WorkflowTypeEnum.ACCN_REG, coreAccn);

		}

		// for SUSPENSION
		else if (ckManageAccn.getAccnDetails().getAccnStatus() == CoreAccnStateEnum.SUS_SUBMITTED.getCode()) {
			appEvent = new SubmitEvent<TCoreAccn, CoreAccn>(this, WorkflowTypeEnum.ACCN_SUS, coreAccn);

		} else if (ckManageAccn.getAction() == FormActions.VERIFY) {
			appEvent = new VerifyEvent<TCoreAccn, CoreAccn>(this, WorkflowTypeEnum.ACCN_SUS, coreAccn);

		} else if (ckManageAccn.getAccnDetails().getAccnStatus() == CoreAccnStateEnum.SUS_APPROVED.getCode()
				&& ckManageAccn.getAccnProcessType().equalsIgnoreCase(MstEntityTypes.ACCN_SUSPENSION.getDesc())) {
			appEvent = new ApproveEvent<TCoreAccn, CoreAccn>(this, WorkflowTypeEnum.ACCN_SUS, coreAccn);

		} else if (ckManageAccn.getAccnDetails().getAccnStatus() == CoreAccnStateEnum.SUS_REJECTED.getCode()
				&& ckManageAccn.getAccnProcessType().equalsIgnoreCase(MstEntityTypes.ACCN_SUSPENSION.getDesc())) {
			appEvent = new RejectEvent<TCoreAccn, CoreAccn>(this, WorkflowTypeEnum.ACCN_SUS, coreAccn);

		}

		// for TERMINATE
		else if (ckManageAccn.getAccnDetails().getAccnStatus() == CoreAccnStateEnum.TER_SUBMITTED.getCode()) {
			appEvent = new SubmitEvent<TCoreAccn, CoreAccn>(this, WorkflowTypeEnum.ACCN_TERM, coreAccn);

		} else if (ckManageAccn.getAction() == FormActions.VERIFY) {
			appEvent = new VerifyEvent<TCoreAccn, CoreAccn>(this, WorkflowTypeEnum.ACCN_TERM, coreAccn);

		} else if (ckManageAccn.getAccnDetails().getAccnStatus() == CoreAccnStateEnum.TER_APPROVED.getCode()) {
			appEvent = new ApproveEvent<TCoreAccn, CoreAccn>(this, WorkflowTypeEnum.ACCN_TERM, coreAccn);

		} else if (ckManageAccn.getAccnDetails().getAccnStatus() == CoreAccnStateEnum.TER_REJECTED.getCode()
				&& ckManageAccn.getAccnProcessType().equalsIgnoreCase(MstEntityTypes.ACCN_TERMINATION.getDesc())) {
			appEvent = new RejectEvent<TCoreAccn, CoreAccn>(this, WorkflowTypeEnum.ACCN_TERM, coreAccn);

		}

		// for RESUMPTION (UNSUSPENSION)
		else if (ckManageAccn.getAccnDetails().getAccnStatus() == CoreAccnStateEnum.RESUMPTION_SUBMITTED.getCode()) {
			appEvent = new SubmitEvent<TCoreAccn, CoreAccn>(this, WorkflowTypeEnum.ACCN_RESUMPT, coreAccn);

		} else if (ckManageAccn.getAction() == FormActions.VERIFY) {
			appEvent = new VerifyEvent<TCoreAccn, CoreAccn>(this, WorkflowTypeEnum.ACCN_RESUMPT, coreAccn);

		} else if (ckManageAccn.getAccnDetails().getAccnStatus() == CoreAccnStateEnum.RESUMPTION_APPROVED.getCode()
				&& ckManageAccn.getAccnProcessType().equalsIgnoreCase(MstEntityTypes.ACCN_RESUMPTION.getDesc())) {
			appEvent = new ApproveEvent<TCoreAccn, CoreAccn>(this, WorkflowTypeEnum.ACCN_RESUMPT, coreAccn);

		} else if (ckManageAccn.getAccnDetails().getAccnStatus() == CoreAccnStateEnum.RESUMPTION_REJECTED.getCode()
				&& ckManageAccn.getAccnProcessType().equalsIgnoreCase(MstEntityTypes.ACCN_RESUMPTION.getDesc())) {
			appEvent = new RejectEvent<TCoreAccn, CoreAccn>(this, WorkflowTypeEnum.ACCN_RESUMPT, coreAccn);

		}

		eventPublisher.publishEvent(appEvent);
	}

	/**
	 * 
	 * @param validationErrors
	 * @return
	 * @throws ParameterException
	 * @throws ProcessingException
	 */
	protected String validationErrorMap(List<ValidationError> validationErrors)
			throws ParameterException, ProcessingException {
		log.debug("validationMap");

		if (null == validationErrors)
			throw new ParameterException("param errros null");
		if (validationErrors.isEmpty())
			throw new ProcessingException("param errros empty");

		String json;
		try {
			Map<String, String> validationMap = new HashMap<>();
			validationErrors.stream().forEach(ve -> {
				log.error(ve.getErrorType() + " " + ve.getErrorDescription() + "  " + ve);
				validationMap.put(ve.getErrorType().toString(), ve.getErrorDescription());
			});

			json = (new ObjectMapper()).writeValueAsString(validationMap);

		} catch (Exception ex) {
			log.error("errorMap", ex);
			throw new ProcessingException(ex.getMessage());
		}
		return json;
	}

	// Helper Methods
	/**
	 * @param ckManageAccn
	 * @param decodedAccnId
	 * @param coreAccn
	 */
	private void getAccountConfigDetails(CkManageAccn ckManageAccn, String decodedAccnId, CoreAccn coreAccn) {

		String accnType = coreAccn.getTMstAccnType().getAtypId();

		// Sage AccpacId
		TCoreAccnConfigId coreAccnConfigId = new TCoreAccnConfigId();
		coreAccnConfigId.setAcfgAccnid(decodedAccnId);
		coreAccnConfigId.setAcfgKey(ACCN_CONFIG_SAGE_ACCN_ID);
		TCoreAccnConfig accnConfig = coreAccnConfigDao.getByIdAndStatus(coreAccnConfigId, Constant.ACTIVE_STATUS);
		ckManageAccn.setSageAccpacId(
				null != accnConfig && null != accnConfig.getAcfgVal() ? accnConfig.getAcfgVal() : StringUtils.EMPTY);

		// Bank Details and Static VA
		StringBuilder keyStr = new StringBuilder();
		if (accnType.equalsIgnoreCase(AccountTypes.ACC_TYPE_FF.name()))
			keyStr.append("FF").append(POSTFIX_VA_IDR);
		else if (accnType.equalsIgnoreCase(AccountTypes.ACC_TYPE_CO.name()))
			keyStr.append("CO").append(POSTFIX_VA_IDR);
		else if (accnType.equalsIgnoreCase(AccountTypes.ACC_TYPE_TO.name()))
			keyStr.append(CONFIG_BANK_DETAILS);

		/* Bank Details for TO */
		TCoreAccnConfigId bankDetailsId = new TCoreAccnConfigId();
		if (accnType.equalsIgnoreCase(AccountTypes.ACC_TYPE_TO.name())) {
			bankDetailsId.setAcfgAccnid(decodedAccnId);
			bankDetailsId.setAcfgKey(keyStr.toString());
			TCoreAccnConfig bankDetailsConfig = coreAccnConfigDao.getByIdAndStatus(bankDetailsId,
					Constant.ACTIVE_STATUS);
			ckManageAccn.setBankDetails(new CoreAccnConfig(bankDetailsConfig));
		}
		/* Static VA for COFF */
		TCoreAccnConfigId staticVaId = new TCoreAccnConfigId();
		if (accnType.equalsIgnoreCase(AccountTypes.ACC_TYPE_FF.name())
				|| accnType.equalsIgnoreCase(AccountTypes.ACC_TYPE_CO.name())) {
			staticVaId.setAcfgAccnid(decodedAccnId);
			staticVaId.setAcfgKey(keyStr.toString());
			TCoreAccnConfig staticVaConfig = coreAccnConfigDao.getByIdAndStatus(staticVaId, Constant.ACTIVE_STATUS);
			ckManageAccn.setStaticVa(new CoreAccnConfig(staticVaConfig));
		}

		// Mobile Enabled for TO
		TCoreAccnConfigId mobileEnId = new TCoreAccnConfigId();
		if (accnType.equalsIgnoreCase(AccountTypes.ACC_TYPE_TO.name())) {
			mobileEnId.setAcfgAccnid(decodedAccnId);
			mobileEnId.setAcfgKey(ACCN_CONFIG_MOBILE_ENABLED);
			TCoreAccnConfig mobileEnConfig = coreAccnConfigDao.getByIdAndStatus(mobileEnId, Constant.ACTIVE_STATUS);
			ckManageAccn.setMobileEnabled(
					null != mobileEnConfig && null != mobileEnConfig.getAcfgVal() ? mobileEnConfig.getAcfgVal()
							: StringUtils.EMPTY);
		}
	}

	private CkCtWhitelabel getLoginBackgroundImage(String accnId) throws Exception {
		try {
			return wlService.getDetails(accnId);
		} catch (Exception ex) {
			log.error("getLoginBackgroundImage", ex);
		}

		return null;

	}

	private void updateCompanyLogo(CkManageAccn ckManageAccn, Principal principal) throws Exception {

		String accnId = ckManageAccn.getAccnDetails().getAccnId();
		if (ckManageAccn.getCompanyLogo() != null
				&& StringUtils.isNotBlank(ckManageAccn.getCompanyLogo().getAattDocId())) {
			// That means there is an already existing record
			if (ckManageAccn.getCompanyLogo().getAattStore() != null) {
				String filePath = ckFileUtil.saveAttachment(accnId, ckManageAccn.getCompanyLogo().getAattName(),
						ckManageAccn.getCompanyLogo().getAattStore());
				ckManageAccn.getCompanyLogo().setAattLoc(filePath);
			}

			ckManageAccn.getCompanyLogo().setAattStore(null);
			ccmAccnAttService.update(ckManageAccn.getCompanyLogo(), principal);
		} else {
			// only save if there is one
			if (ckManageAccn.getCompanyLogo() != null && ckManageAccn.getCompanyLogo().getAattStore() != null) {
				CoreAccn coreAccn = ccmAccnService.findById(accnId);
				if (coreAccn == null)
					throw new ProcessingException("account details not found");
				ckManageAccn.getCompanyLogo().setTCoreAccn(coreAccn);
				String filePath = ckFileUtil.saveAttachment(accnId, ckManageAccn.getCompanyLogo().getAattName(),
						ckManageAccn.getCompanyLogo().getAattStore());

				ckManageAccn.getCompanyLogo().setAattLoc(filePath);
				ckManageAccn.getCompanyLogo().setAattStore(null);
				ckManageAccn.getCompanyLogo().setAattLocType('F');
				ckManageAccn.getCompanyLogo().setAattDocType("image");

				Calendar cal = Calendar.getInstance();
				ckManageAccn.getCompanyLogo().setAattValidFromDt(cal.getTime());
				cal.add(Calendar.YEAR, 10);
				ckManageAccn.getCompanyLogo().setAattValidToDt(cal.getTime());
				ckManageAccn.getCompanyLogo().setAattStatus('A');
				ckManageAccn.getCompanyLogo().setAattCreateDt(new Date());
				ckManageAccn.getCompanyLogo().setAattCreateUid(principal.getUserId());
				MstAttType attType = mstAttTypeService.findById(AttachmentTypes.CLO.getId());
				if (attType == null) {
					attType = new MstAttType();
					attType.setDtoId(AttachmentTypes.CLO.getId());
				}

				ckManageAccn.getCompanyLogo().setTMstAttType(attType);
				ccmAccnAttService.add(ckManageAccn.getCompanyLogo(), principal);
			}

		}
	}

	private void updateCkAccn(String accnId, CkManageAccn ckManageAccn) throws Exception {

		CkAccn ckAccn = ckManageAccn.getCkAccn();

		Principal principal = principalUtilService.getPrincipal();

		if (ckAccn == null) {
			return;
		}

		if (principal == null) {
			throw new ProcessingException("principal is null");
		}
		if (!AccountTypes.ACC_TYPE_SP.name().equalsIgnoreCase(principal.getCoreAccn().getTMstAccnType().getAtypId())) {
			// Only SP: GLI can change
			return;
		}

		TCkAccn tCkAccn = ckAccnDao.find(accnId);

		if (tCkAccn == null) {
			tCkAccn = new TCkAccn();
			// tCkAccn.setTCoreAccn( new TCoreAccn(accnId, null, ' ', null));
			tCkAccn.setCaccnId(accnId);
			tCkAccn.setCaccnStatus(Constant.ACTIVE_STATUS);
			tCkAccn.setCaccnDtCreate(new Date());
			tCkAccn.setCaccnUidCreate(principal.getUserId());
		}

		tCkAccn.setCaccnWhatsapp(ckAccn.getCaccnWhatsapp());
		tCkAccn.setCaccnSms(ckAccn.getCaccnSms());
		tCkAccn.setCaccnCo2x(ckAccn.getCaccnCo2x());
		tCkAccn.setCaccnRoutePlanning(ckAccn.getCaccnRoutePlanning());

		ckAccnDao.saveOrUpdate(tCkAccn);
	}

	private void saveOrUpdateSvcSubscription(String accnId, CkManageAccn ckManageAccn, Principal principal)
			throws Exception {

		if (ckManageAccn.getSvcSubTypes() != null && ckManageAccn.getSvcSubTypes().size() > 0) {
			ckManageAccn.getSvcSubTypes().forEach(e -> {
				try {
					// check if the svcId is scurrently subscribed
					TCkSvcSub svcSubE = findSvcSub(accnId, e.getSvctId());
					// check if accnId - serviceType exist regardless of state
					if (svcSubE != null) {
						// check if dto is not checked, update, otherwise do nothing
						if (!e.getIsSubscribed()) {
							svcSubE.setSubDtLupd(new Date());
							svcSubE.setSubUidLupd(principal.getUserId());
							// just set to DRAFT
							TCkMstSvcSubState state = new TCkMstSvcSubState();
							state.setSsstId("DRF");
							svcSubE.setTCkMstSvcSubState(state);
							svcSubDao.update(svcSubE);
						}

					} else {
						// otherwise create
						if (e.getIsSubscribed()) {
							Date now = new Date();
							svcSubE = new TCkSvcSub();
							svcSubE.setSubId(CkUtil.generateId());
							TCkMstSvcSubState svcSubState = new TCkMstSvcSubState();
							svcSubState.setSsstId("APR");// set to approve for now
							svcSubE.setTCkMstSvcSubState(svcSubState);
							TCkMstServiceType ckMstSvcType = new TCkMstServiceType();
							e.toEntity(ckMstSvcType);
							svcSubE.setTCkMstServiceType(ckMstSvcType);
							TCoreAccn accn = new TCoreAccn();
							accn.setAccnId(accnId);
							svcSubE.setTCoreAccn(accn);
							svcSubE.setSubAutoRenew('N');
							svcSubE.setSubDtApprove(now);

							svcSubE.setSubDtLupd(now);
							svcSubE.setSubDtCreate(now);
							svcSubE.setSubUidApprove(principal.getUserId());
							svcSubE.setSubUidCreate(principal.getUserId());
							svcSubE.setSubUidApprove(principal.getUserId());
							svcSubDao.saveOrUpdate(svcSubE);
						}

					}
				} catch (Exception ex) {
					log.error("updateSvcSubscription error: ", ex);
				}

			});
			;

		}
	}

	public CkAccn getCkAccn(String accnId) throws Exception {

		TCkAccn tCkAccn = ckAccnDao.find(accnId);

		if (null != tCkAccn) {
			return new CkAccn(tCkAccn);
		}
		return null;
	}

	public void loadSvcSubscriptions(String accnId, CkManageAccn ckManageAccn) throws Exception {
		// Load the subscribed services
		List<CkMstServiceType> svcTypes = svcSerivce.getServices(accnId);
		if (svcTypes != null) {
			// check if there's none subscribed
			boolean hasSubscription = svcTypes.stream().anyMatch(e -> e.getIsSubscribed() == true);
			if (!hasSubscription) {
				Principal principal = principalUtilService.getPrincipal();
				// if the svcTypes don't have at least one, default it to the principal appscode
				ckManageAccn.setSvcSubTypes(svcTypes.stream().map(e -> {
					String svcId = ServiceTypes.getServiceTypeByAppsCode(principal.getAppsCode());
					if (e.getSvctId().equalsIgnoreCase(svcId)) {
						e.setIsSubscribed(true);
					}

					return e;

				}).collect(Collectors.toList()));
			} else {
				ckManageAccn.setSvcSubTypes(svcTypes);
			}
		} else {
			ckManageAccn.setSvcSubTypes(svcSerivce.getActiveServices());
		}

	}

	private TCkSvcSub findSvcSub(String accnId, String serviceType) throws Exception {
		if (StringUtils.isNotBlank(accnId) && StringUtils.isNotBlank(serviceType)) {
			String hql = "from TCkSvcSub o where o.TCoreAccn.accnId=:accnId and o.TCkMstServiceType.svctId=:svcType";
			Map<String, Object> params = new HashMap<>();
			params.put("accnId", accnId);
			params.put("svcType", serviceType);
			List<TCkSvcSub> svcSubList = svcSubDao.getByQuery(hql, params);
			if (svcSubList != null && svcSubList.size() > 0) {
				TCkSvcSub svcSub = svcSubList.get(0);
				Hibernate.initialize(svcSub.getTCkMstServiceType());
				Hibernate.initialize(svcSub.getTCkMstSvcSubState());
				Hibernate.initialize(svcSub.getTCoreAccn());
				return svcSub;
			}
		}

		return null;
	}
}
