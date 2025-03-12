package com.guudint.clickargo.manageaccn.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guudint.clickargo.common.ICkConstant;
import com.guudint.clickargo.job.dto.CkJob;
import com.guudint.clickargo.job.event.AbstractJobEvent;
import com.guudint.clickargo.job.service.AbstractJobService;
import com.guudint.clickargo.manageaccn.dto.CkCtFfCo;
import com.guudint.clickargo.manageaccn.dto.CoreAccnStateEnum;
import com.guudint.clickargo.manageaccn.model.TCkCtFfCo;
import com.guudint.clickargo.master.enums.AccountTypes;
import com.guudint.clickargo.master.enums.Roles;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.ccm.dto.CoreContact;
import com.vcc.camelone.ccm.model.TCoreAccn;
import com.vcc.camelone.common.controller.entity.EntityFilterRequest;
import com.vcc.camelone.common.controller.entity.EntityWhere;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.exception.ValidationException;
import com.vcc.camelone.common.service.ServiceStatus;
import com.vcc.camelone.locale.dto.CoreMstLocale;
import com.vcc.camelone.master.dto.MstAccnType;

@Service
public class AccnFfCoEntityService extends AbstractJobService<CkCtFfCo, TCkCtFfCo, String >
		implements ICkConstant {

	private static Logger log = Logger.getLogger(AccnFfCoEntityService.class);
	
	private static String AUDIT_TAG = "CK FF CO";
	private static String TABLE_NAME = "T_CK_CT_FF_CO";
	private static String HISTORY = "history";
	private static String DEFAULT = "default";

	public AccnFfCoEntityService() {
		super("ckCtFfCoDao", AUDIT_TAG, TCkCtFfCo.class.getName(), TABLE_NAME);
	}

	@Override
	public CkCtFfCo newObj(Principal principal)
			throws ParameterException, EntityNotFoundException, ProcessingException {

		return null;
	}

	@Override
	public CkCtFfCo findById(String id) throws ParameterException, EntityNotFoundException, ProcessingException {

		return null;
	}

	@Override
	public CkCtFfCo deleteById(String id, Principal principal)
			throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException {

		return null;
	}

	@Override
	@Transactional
	public List<CkCtFfCo> filterBy(EntityFilterRequest filterRequest)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		log.debug("filterBy");
		if (filterRequest == null) {
			throw new ParameterException("param filterRequest null");
		}
		CkCtFfCo ckctFfco = whereDto(filterRequest);
		if (ckctFfco == null) {
			throw new ProcessingException("whereDto null");
		}
		filterRequest.setTotalRecords(countByAnd(ckctFfco));
		List<CkCtFfCo> ffcoList = new ArrayList<>();
		try {
			String orderByClause = formatOrderByObj(filterRequest.getOrderBy()).toString();
			List<TCkCtFfCo> fffCoList = findEntitiesByAnd(ckctFfco, "from TCkCtFfCo o ", orderByClause,
					filterRequest.getDisplayLength(), filterRequest.getDisplayStart());
			for (TCkCtFfCo tffco : fffCoList) {
				CkCtFfCo dto = dtoFromEntity(tffco);
				if (dto != null) {
					ffcoList.add(dto);
				}
			}
		} catch (Exception e) {
			log.error("filterBy", e);
		}
		return ffcoList;
	}

	@Override
	protected void initBusinessValidator() {

	}

	@Override
	protected Logger getLogger() {

		return null;
	}

	@Override
	protected TCkCtFfCo initEnity(TCkCtFfCo entity) throws ParameterException, ProcessingException {

		log.debug("initEntity");
		if (entity != null) {
			Hibernate.initialize(entity.getTCoreAccnByFfcoFf());
			Hibernate.initialize(entity.getTCoreAccnByFfcoCo());
		}
		return entity;
	}

	@Override
	protected TCkCtFfCo entityFromDTO(CkCtFfCo dto) throws ParameterException, ProcessingException {

		log.debug("entityFromDto");
		if (dto == null) {
			throw new ParameterException("param dto null");
		}

		TCkCtFfCo ckCtFfCo = new TCkCtFfCo();

		BeanUtils.copyProperties(dto, ckCtFfCo);

		if (dto.getTCoreAccnByFfcoFf() != null) {
			ckCtFfCo.setTCoreAccnByFfcoFf(dto.getTCoreAccnByFfcoFf().toEntity(new TCoreAccn()));
		}

		if (dto.getTCoreAccnByFfcoCo() != null) {
			ckCtFfCo.setTCoreAccnByFfcoCo(dto.getTCoreAccnByFfcoCo().toEntity(new TCoreAccn()));
		}
		return ckCtFfCo;
	}

	@Override
	protected CkCtFfCo dtoFromEntity(TCkCtFfCo entity) throws ParameterException, ProcessingException {

		CkCtFfCo ffco = new CkCtFfCo(entity);

		if (entity.getTCoreAccnByFfcoFf() != null) {
			TCoreAccn ffAccn = entity.getTCoreAccnByFfcoFf();
			ffco.setTCoreAccnByFfcoFf((new CoreAccn(ffAccn)));
		}

		if (entity.getTCoreAccnByFfcoCo() != null) {
			TCoreAccn coAccn = entity.getTCoreAccnByFfcoCo();
			ffco.setTCoreAccnByFfcoCo((new CoreAccn(coAccn)));
			
			if(coAccn.getAccnContact() != null) {
				ffco.getTCoreAccnByFfcoCo().setAccnContact( new CoreContact(coAccn.getAccnContact()));
			}
		}

		return ffco;
	}

	@Override
	protected String entityKeyFromDTO(CkCtFfCo dto) throws ParameterException, ProcessingException {

		return null;
	}

	@Override
	protected TCkCtFfCo updateEntity(ACTION attriubte, TCkCtFfCo entity, Principal principal, Date date)
			throws ParameterException, ProcessingException {

		return null;
	}

	@Override
	protected TCkCtFfCo updateEntityStatus(TCkCtFfCo entity, char status)
			throws ParameterException, ProcessingException {

		return null;
	}

	@Override
	protected CkCtFfCo preSaveUpdateDTO(TCkCtFfCo storedEntity, CkCtFfCo dto)
			throws ParameterException, ProcessingException {

		return null;
	}

	@Override
	protected void preSaveValidation(CkCtFfCo dto, Principal principal) throws ParameterException, ProcessingException {

	}

	@Override
	protected ServiceStatus preUpdateValidation(CkCtFfCo dto, Principal principal)
			throws ParameterException, ProcessingException {

		return null;
	}
	
	//@Override
	protected String formatOrderBy(String attribute) throws Exception {
		attribute = Optional.ofNullable(attribute).orElse("");
		attribute = attribute.replace("tcoreAccnByFfcoCo", "TCoreAccnByFfcoCo")
				.replace("tcoreAccnByFfcoFf", "TCoreAccnByFfcoFf");
		return attribute;
	}
	
	@Override
	protected String getWhereClause(CkCtFfCo ffco, boolean wherePrinted) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		log.debug("getWhereClause");

		try {
			if (null == ffco)
				throw new ParameterException("param dto null");

			Principal principal = principalUtilService.getPrincipal();
			if (principal == null)
				throw new ProcessingException("principal null");

			StringBuffer searchStatement = new StringBuffer();

			CoreAccn accn = principal.getCoreAccn();
			Optional<MstAccnType> opAccnType = Optional.ofNullable(accn.getTMstAccnType());

			searchStatement.append(" where 1=1 ");
			
			
			CoreAccn ffAccn = ffco.getTCoreAccnByFfcoFf();
			CoreAccn coAccn = ffco.getTCoreAccnByFfcoCo();

			// only proceed if it's SP
			if (opAccnType.isPresent() && opAccnType.get().getAtypId().equals(AccountTypes.ACC_TYPE_SP.name())) {

			} else if (opAccnType.isPresent() && opAccnType.get().getAtypId().equals(AccountTypes.ACC_TYPE_FF.name())) {

				searchStatement.append(" and o.TCoreAccnByFfcoFf.accnId = '" + principal.getUserAccnId() + "'" );
			}

			// Exclude Registration Rejected from filter
			//searchStatement.append(" and o.accnStatus NOT IN :excludeAccnStatus");

			// Only include SP/CO/FF/TO
			//searchStatement.append(" and o.TMstAccnType.atypId IN :includeAccnTypes");

			//searchStatement.append(" and o.accnStatus IN :accnStatus");

			if (Character.isAlphabetic(coAccn.getAccnStatus())) {
				searchStatement.append(" and o.TCoreAccnByFfcoCo.accnStatus = :accnStatus");
			} else {
				searchStatement.append(" and o.TCoreAccnByFfcoCo.accnStatus IN :accnStatus");
			}
			
			if (StringUtils.isNotBlank(ffAccn.getAccnName())) {
				searchStatement.append(" and o.TCoreAccnByFfcoFf.accnName LIKE :ffAccnName");
			}
			
			if (StringUtils.isNotBlank(coAccn.getAccnId())) {
				searchStatement.append(" and o.TCoreAccnByFfcoCo.accnId LIKE :coAccnId");
			}
			
			if (StringUtils.isNotBlank(coAccn.getAccnName())) {
				searchStatement.append(" and o.TCoreAccnByFfcoCo.accnName LIKE :coAccnName");
			}

			if (StringUtils.isNotBlank(coAccn.getAccnCoyRegn())) {
				searchStatement.append(" and o.TCoreAccnByFfcoCo.accnCoyRegn LIKE :accnCoyRegn");
			}

			if (coAccn.getAccnContact() != null) {
				if (StringUtils.isNotBlank(coAccn.getAccnContact().getContactTel())) {
					searchStatement.append(" and o.TCoreAccnByFfcoCo.accnContact.contactTel LIKE :contactTel");
				}
				if (StringUtils.isNotBlank(coAccn.getAccnContact().getContactFax())) {
					searchStatement.append(" and o.TCoreAccnByFfcoCo.accnContact.contactFax LIKE :contactFax");
				}
				if (StringUtils.isNotBlank(coAccn.getAccnContact().getContactEmail())) {
					searchStatement.append(" and o.TCoreAccnByFfcoCo.accnContact.contactEmail LIKE :contactEmail");
				}
			}

			if (null != ffco.getFfcoDtCreate()) {
				searchStatement.append(" and DATE_FORMAT(o.ffcoDtCreate,'%d/%m/%Y') = :ffcoDtCreate");
			}

			if (!StringUtils.isEmpty(ffco.getFfcoUidCreate())) {
				searchStatement.append(" and o.ffcoUidCreate LIKE :ffcoUidCreate");
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
	protected HashMap<String, Object> getParameters(CkCtFfCo ffco) throws ParameterException, ProcessingException {

		log.debug("getParameters");

		try {
			if (null == ffco)
				throw new ParameterException("param dto null");

			SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
			HashMap<String, Object> parameters = new HashMap<String, Object>();

			Principal principal = principalUtilService.getPrincipal();
			if (principal == null)
				throw new ProcessingException("principal null");

			CoreAccn accn = principal.getCoreAccn();
			Optional<MstAccnType> opAccnType = Optional.ofNullable(accn.getTMstAccnType());
			
			CoreAccn ffAccn = ffco.getTCoreAccnByFfcoFf();
			CoreAccn coAccn = ffco.getTCoreAccnByFfcoCo();
			
			
			// only proceed if it's SP
			if (opAccnType.isPresent() && opAccnType.get().getAtypId().equals(AccountTypes.ACC_TYPE_SP.name())) {


			} else if (opAccnType.isPresent() && opAccnType.get().getAtypId().equals(AccountTypes.ACC_TYPE_FF.name())) {

			} else {
				// throw Exception, only for GLI and FF
				return null;
			}

			if (Character.isAlphabetic(coAccn.getAccnStatus())) {
				parameters.put("accnStatus", Character.valueOf(coAccn.getAccnStatus()));
			} else {

				if (DEFAULT.equalsIgnoreCase(ffco.getHistory())){

					parameters.put("accnStatus", Arrays.asList(CoreAccnStateEnum.REG_APPROVED.getCode()));

				} else if (HISTORY.equalsIgnoreCase(ffco.getHistory())) {
					parameters.put("accnStatus", Arrays.asList(CoreAccnStateEnum.SUS_APPROVED.getCode(),
							CoreAccnStateEnum.TER_APPROVED.getCode()));
				}
			}

			if (StringUtils.isNotBlank(coAccn.getAccnId()))
				parameters.put("coAccnId", "%" + coAccn.getAccnId() + "%");
			if (StringUtils.isNotBlank(coAccn.getAccnName()))
				parameters.put("coAccnName", "%" + coAccn.getAccnName() + "%");
			
			if (StringUtils.isNotBlank(ffAccn.getAccnName()))
				parameters.put("ffAccnName", "%" + ffAccn.getAccnName() + "%");


			if (StringUtils.isNotBlank(coAccn.getAccnCoyRegn()))
				parameters.put("accnCoyRegn", "%" + coAccn.getAccnCoyRegn() + "%");

			if (coAccn.getAccnContact() != null) {
				if (StringUtils.isNotBlank(coAccn.getAccnContact().getContactTel()))
					parameters.put("contactTel", "%" + coAccn.getAccnContact().getContactTel() + "%");
				if (StringUtils.isNotBlank(coAccn.getAccnContact().getContactFax()))
					parameters.put("contactFax", "%" + coAccn.getAccnContact().getContactFax() + "%");
				if (StringUtils.isNotBlank(coAccn.getAccnContact().getContactEmail()))
					parameters.put("contactEmail", "%" + coAccn.getAccnContact().getContactEmail() + "%");
			}

			if (null != ffco.getFfcoDtCreate()) {
				parameters.put("ffcoDtCreate", sdfDate.format(ffco.getFfcoDtCreate()));
			}

			if (StringUtils.isNotBlank(ffco.getFfcoUidCreate())) {
				parameters.put("ffcoUidCreate", "%" + ffco.getFfcoUidCreate() + "%");
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
	protected CkCtFfCo whereDto(EntityFilterRequest filterRequest) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		log.debug("whereDto");

		try {
			if (null == filterRequest)
				throw new ParameterException("param filterRequest null");

			CkCoreAccn coAccn = new CkCoreAccn();
			CkCoreAccn ffAccn = new CkCoreAccn();

			CkCtFfCo ffco = new CkCtFfCo(ffAccn, coAccn);

			CoreContact coreContact = new CoreContact();
			coAccn.setAccnContact(coreContact);

			SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");

			for (EntityWhere entityWhere : filterRequest.getWhereList()) {
				Optional<String> opValue = Optional.ofNullable(entityWhere.getValue());
				if (!opValue.isPresent())
					continue;

				if (entityWhere.getAttribute().equalsIgnoreCase("tcoreAccnByFfcoFf.accnName"))
					ffAccn.setAccnName(opValue.get());
				
				if (entityWhere.getAttribute().equalsIgnoreCase("tcoreAccnByFfcoCo.accnId"))
					coAccn.setAccnId(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("tcoreAccnByFfcoCo.accnName"))
					coAccn.setAccnName(opValue.get());
				
				if (entityWhere.getAttribute().equalsIgnoreCase("tcoreAccnByFfcoCo.accnCoyRegn"))
					coAccn.setAccnCoyRegn(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("tcoreAccnByFfcoCo.accnContact.contactTel"))
					coreContact.setContactTel(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("tcoreAccnByFfcoCo.accnContact.contactEmail"))
					coreContact.setContactEmail(opValue.get());
				if (entityWhere.getAttribute().equalsIgnoreCase("tcoreAccnByFfcoCo.accnStatus"))
					coAccn.setAccnStatus(opValue.get().charAt(0));
				if (entityWhere.getAttribute().equalsIgnoreCase("ffcoDtCreate"))
					ffco.setFfcoDtCreate(sdfDate.parse(opValue.get()));
				if (entityWhere.getAttribute().equalsIgnoreCase("ffcoUidCreate"))
					ffco.setFfcoUidCreate(opValue.get());

				// history toggle
				if (entityWhere.getAttribute().equalsIgnoreCase(HISTORY)) {
					ffco.setHistory(opValue.get());
				} else if (entityWhere.getAttribute().equalsIgnoreCase(DEFAULT)) {
					ffco.setHistory(opValue.get());
				}
			}

			return ffco;
		} catch (ParameterException ex) {
			log.error("whereDto", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("whereDto", ex);
			throw new ProcessingException(ex);
		}
	}

	@Override
	protected CoreMstLocale getCoreMstLocale(CkCtFfCo dto)
			throws ParameterException, EntityNotFoundException, ProcessingException {

		return null;
	}

	@Override
	protected CkCtFfCo setCoreMstLocale(CoreMstLocale coreMstLocale, CkCtFfCo dto)
			throws ParameterException, EntityNotFoundException, ProcessingException {

		return null;
	}

	@Override
	protected Class<?>[] _validateGroupClass(JobEvent jobEvent) {
		
		return null;
	}

	@Override
	protected void _auditEvent(JobEvent jobEvent, CkCtFfCo dto, Principal principal) {
		
		
	}

	@Override
	protected void _auditError(JobEvent jobEvent, CkCtFfCo dto, Exception ex, Principal principal) {
		
		
	}

	@Override
	protected AbstractJobEvent<CkCtFfCo> _getJobEvent(JobEvent jobEvent, CkCtFfCo dto, Principal principal) {
		
		return null;
	}

	@Override
	protected CkCtFfCo _newJob(Principal p) throws ParameterException, EntityNotFoundException, ProcessingException {
		
		return null;
	}

	@Override
	protected CkCtFfCo _createJob(CkCtFfCo dto, CkJob parentJob, Principal principal)
			throws ParameterException, ValidationException, ProcessingException, Exception {
		
		return null;
	}

	@Override
	protected CkCtFfCo _submitJob(CkCtFfCo dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception {
		
		return null;
	}

	@Override
	protected CkCtFfCo _rejectJob(CkCtFfCo dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception {
		
		return null;
	}

	@Override
	protected CkCtFfCo _cancelJob(CkCtFfCo dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception {
		
		return null;
	}

	@Override
	protected CkCtFfCo _confirmJob(CkCtFfCo dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception {
		
		return null;
	}

	@Override
	protected CkCtFfCo _payJob(CkCtFfCo dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception {
		
		return null;
	}

	@Override
	protected CkCtFfCo _paidJob(CkCtFfCo dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception {
		
		return null;
	}

	@Override
	protected CkCtFfCo _completeJob(CkCtFfCo dto, Principal principal)
			throws ParameterException, EntityNotFoundException, ValidationException, ProcessingException, Exception {
		
		return null;
	}

}
