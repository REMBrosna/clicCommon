package com.guudint.clickargo.validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guudint.clickargo.common.model.ValidationError;
import com.guudint.clickargo.manageaccn.dto.CoreAccnStateEnum;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.ccm.model.TCoreAccn;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;

@Component
public class AccountFfCoValidator {

	private static Logger LOG = Logger.getLogger(AccountFfCoValidator.class);
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String PHONE_PATTERN = "^[+]{1}[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$";
	
	Locale locale = LocaleContextHolder.getLocale();

	@Autowired
	MessageSource messageSource;
	
	@Autowired
	@Qualifier("coreAccDao")
	private GenericDao<TCoreAccn, String> coreAccDao;

	public List<ValidationError> validateCreate(CoreAccn dto, Principal principal)
			throws ParameterException, ProcessingException {

		List<ValidationError> invalidList = new ArrayList<>();
		invalidList.addAll(this.validateMandatoryFields(dto, principal));

		return invalidList;
	}

	public List<ValidationError> validateUpdate(CoreAccn dto, Principal principal)
			throws ParameterException, ProcessingException {

		List<ValidationError> invalidList = new ArrayList<>();
		invalidList.addAll(this.validateMandatoryFields(dto, principal));
		invalidList.addAll(this.validateOtherFields(dto, principal));
		return invalidList;
	}

	public List<ValidationError> validateSubmit(CoreAccn dto, Principal principal)
			throws ParameterException, ProcessingException {
		
		List<ValidationError> invalidList = new ArrayList<>();
		invalidList.addAll(this.validateMandatoryFields(dto, principal));
		invalidList.addAll(this.validateOtherFields(dto, principal));
		return invalidList;
	}

	public List<ValidationError> validateReject(CoreAccn dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ValidationError> validateCancel(CoreAccn dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ValidationError> validateDelete(CoreAccn dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ValidationError> validateConfirm(CoreAccn dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ValidationError> validatePay(CoreAccn dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ValidationError> validatePaid(CoreAccn dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ValidationError> validateComplete(CoreAccn dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @param dto
	 * @return
	 * @throws ParameterException 
	 */
	private List<ValidationError> validateMandatoryFields(CoreAccn dto, Principal principal) throws ParameterException {
		
		
		if (principal == null) {
			throw new ParameterException("param principal null");
		}
		if (dto == null) {
			throw new ParameterException("param dto null");
		}
		
		List<ValidationError> errorList = new ArrayList<>();

		if (StringUtils.isBlank(dto.getAccnName())) {
			errorList.add(newValidationError("coreAccn.accnName", getMessage("ck.account.accnName.required")));
		} else if (StringUtils.isNotBlank(dto.getAccnName())) {
			try {
				TCoreAccn crEntity = this.findByAccnName(dto.getAccnName());
				if (null != crEntity && !crEntity.getAccnId().equalsIgnoreCase(dto.getAccnId())) {
					errorList.add(newValidationError("coreAccn.accnNameDuplicate", getMessage("ck.account.accnName.duplicate")));
				}
			} catch (Exception ex) {
				LOG.error("findByAccnName ", ex);
			}
		}
		if (null == dto.getTMstAccnType()) {
			errorList.add(newValidationError("coreAccn.TMstAccnType.atypId",
					getMessage("ck.account.TMstAccnType.atypId.required")));
		} else if (null != dto.getTMstAccnType() && StringUtils.isEmpty(dto.getTMstAccnType().getAtypId())) {
			errorList.add(newValidationError("coreAccn.TMstAccnType.atypId",
					getMessage("ck.account.TMstAccnType.atypId.required")));
		}
		if (null == dto.getAccnAddr().getAddrCtry()) {
			errorList.add(newValidationError("coreAccn.accnAddr.addrCtry.ctyCode",
					getMessage("ck.account.addrCtry.ctyCode.required")));
		} else if (null != dto.getAccnAddr().getAddrCtry()
				&& StringUtils.isEmpty(dto.getAccnAddr().getAddrCtry().getCtyCode())) {
			errorList.add(newValidationError("coreAccn.accnAddr.addrCtry.ctyCode",
					getMessage("ck.account.addrCtry.ctyCode.required")));
		}

		return errorList;
	}

	/**
	 * 
	 * @param dto
	 * @param principal
	 * @return
	 * @throws ParameterException
	 * @throws ProcessingException 
	 */
	private List<ValidationError> validateOtherFields(CoreAccn dto, Principal principal) throws ParameterException, ProcessingException {

		if (principal == null) {
			throw new ParameterException("param principal null");
		}
		if (dto == null) {
			throw new ParameterException("param dto null");
		}

		List<ValidationError> errorList = new ArrayList<>();
		Set<String> invalidTabs = new HashSet<>();
		
		try {
			if (StringUtils.isBlank(dto.getAccnCoyRegn())) {
				errorList.add(newValidationError("coreAccn.accnCoyRegn", getMessage("ck.account.tinNo.required")));
				invalidTabs.add("cmpnyDtls");
			}
			
			if (!ObjectUtils.isEmpty(dto.getAccnContact())) {
				if (StringUtils.isBlank(dto.getAccnContact().getContactTel())) {
					errorList.add(newValidationError("coreAccn.accnContact.contactTel",
							getMessage("ck.account.ctcNo.required")));
					invalidTabs.add("cmpnyDtls");
				} else if (StringUtils.isNotEmpty(dto.getAccnContact().getContactTel())) {
					Pattern pattern = Pattern.compile(PHONE_PATTERN);
					Matcher matcher = pattern.matcher(dto.getAccnContact().getContactTel());
					if (!matcher.matches()) {
						errorList.add(newValidationError("coreAccn.accnContact.telInvalid",
								getMessage("ck.account.ctcEmail.telInvalid")));
						invalidTabs.add("cmpnyDtls");
					}
				}
				if (StringUtils.isBlank(dto.getAccnContact().getContactEmail())) {
					errorList.add(newValidationError("coreAccn.accnContact.contactEmail",
							getMessage("ck.account.ctcEmail.required")));
					invalidTabs.add("cmpnyDtls");
				} else if (StringUtils.isNotEmpty(dto.getAccnContact().getContactEmail())) {
					Pattern pattern = Pattern.compile(EMAIL_PATTERN);
					Matcher matcher = pattern.matcher(dto.getAccnContact().getContactEmail());
					if (!matcher.matches()) {
						errorList.add(newValidationError("coreAccn.accnContact.emailInvalid",
								getMessage("ck.account.ctcEmail.emailInvalid")));
						invalidTabs.add("cmpnyDtls");
					}
				}
			}
			
			if (!ObjectUtils.isEmpty(dto.getAccnAddr())) {
				if (StringUtils.isBlank(dto.getAccnAddr().getAddrLn1())) {
					errorList.add(
							newValidationError("coreAccn.accnAddr.addrLn1", getMessage("ck.account.addrLn1.required")));
					invalidTabs.add("cmpnyDtls");
				}
				if (StringUtils.isBlank(dto.getAccnAddr().getAddrLn2())) {
					errorList.add(
							newValidationError("coreAccn.accnAddr.addrLn2", getMessage("ck.account.addrLn2.required")));
					invalidTabs.add("cmpnyDtls");
				}
				if (StringUtils.isBlank(dto.getAccnAddr().getAddrProv())) {
					errorList.add(
							newValidationError("coreAccn.accnAddr.addrProv", getMessage("ck.account.addrProv.required")));
					invalidTabs.add("cmpnyDtls");
				}
				if (StringUtils.isBlank(dto.getAccnAddr().getAddrPcode())) {
					errorList.add(
							newValidationError("coreAccn.accnAddr.addrPcode", getMessage("ck.account.addrPcode.required")));
					invalidTabs.add("cmpnyDtls");
				}
				if (StringUtils.isBlank(dto.getAccnAddr().getAddrCity())) {
					errorList.add(
							newValidationError("coreAccn.accnAddr.addrCity", getMessage("ck.account.addrCity.required")));
					invalidTabs.add("cmpnyDtls");
				}
			}
			
			if (!invalidTabs.isEmpty() && invalidTabs.contains("cmpnyDtls")) {
				ObjectMapper mapper = new ObjectMapper();
				errorList.add(newValidationError("invalidTabs.cmpnyDtls", mapper.writeValueAsString(invalidTabs)));
			}
			
			if (!invalidTabs.isEmpty() && invalidTabs.contains("cnfgrtn")) {
				ObjectMapper mapper = new ObjectMapper();
				errorList.add(newValidationError("invalidTabs.cnfgrtn", mapper.writeValueAsString(invalidTabs)));
			}
			
			return errorList;
		} catch (Exception ex) {
			throw new ProcessingException(ex);
		}
	}

	/**
	 * Check for duplicate account name
	 * @param accnName
	 * @return
	 * @throws Exception
	 */
    public TCoreAccn findByAccnName(String accnName) throws Exception {
        DetachedCriteria criteria = DetachedCriteria.forClass(TCoreAccn.class);
        criteria.add(Restrictions.eq("accnName", accnName));
		criteria.add(Restrictions.not(Restrictions.in("accnStatus", Arrays.asList(
				CoreAccnStateEnum.REG_DELETED.getCode(), 
				CoreAccnStateEnum.REG_REJECTED.getCode()
		))));
        return coreAccDao.getOne(criteria);
    }
    
    /**
     * 
     * @param field
     * @param message
     * @return
     */
	private ValidationError newValidationError(String field, String message) {
		return new ValidationError("", field, message);
	}

	/**
	 * 
	 * @param message
	 * @return
	 */
	private String getMessage(String message) {
		return messageSource.getMessage(message, null, locale);
	}


}