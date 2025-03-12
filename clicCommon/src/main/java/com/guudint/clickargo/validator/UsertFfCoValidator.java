package com.guudint.clickargo.validator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guudint.clickargo.admin.service.ClickargoManageUserService;
import com.guudint.clickargo.common.model.ValidationError;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.ccm.dto.CoreUsr;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;

@Component
public class UsertFfCoValidator {

	private static Logger LOG = Logger.getLogger(UsertFfCoValidator.class);
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String PHONE_PATTERN = "^[+]{1}[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$";
	
	Locale locale = LocaleContextHolder.getLocale();

	@Autowired
	MessageSource messageSource;

	@Autowired
	private ClickargoManageUserService clickargoUserService;

	public List<ValidationError> validateCreate(CoreUsr dto, Principal principal)
			throws ParameterException, ProcessingException {

		List<ValidationError> invalidList = new ArrayList<>();
		invalidList.addAll(this.validateMandatoryFields(dto, principal));

		return invalidList;
	}

	public List<ValidationError> validateUpdate(CoreUsr dto, Principal principal)
			throws ParameterException, ProcessingException {

		List<ValidationError> invalidList = new ArrayList<>();
		invalidList.addAll(this.validateMandatoryFields(dto, principal));
		return invalidList;
	}

	public List<ValidationError> validateSubmit(CoreUsr dto, Principal principal)
			throws ParameterException, ProcessingException {
		
		List<ValidationError> invalidList = new ArrayList<>();
		invalidList.addAll(this.validateMandatoryFields(dto, principal));
		invalidList.addAll(this.validateOtherFields(dto, principal));
		return invalidList;
	}

	public List<ValidationError> validateReject(CoreUsr dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ValidationError> validateCancel(CoreUsr dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ValidationError> validateDelete(CoreUsr dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ValidationError> validateConfirm(CoreUsr dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ValidationError> validatePay(CoreUsr dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ValidationError> validatePaid(CoreUsr dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ValidationError> validateComplete(CoreUsr dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @param dto
	 * @return
	 * @throws ParameterException 
	 */
	private List<ValidationError> validateMandatoryFields(CoreUsr dto, Principal principal) throws ParameterException {
		
		
		if (principal == null) {
			throw new ParameterException("param principal null");
		}
		if (dto == null) {
			throw new ParameterException("param dto null");
		}
		
		List<ValidationError> errorList = new ArrayList<>();

		if (StringUtils.isBlank(dto.getUsrName())) {
			errorList.add(newValidationError("coreUsr.usrName", getMessage("ck.user.usrName.required")));
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
	private List<ValidationError> validateOtherFields(CoreUsr dto, Principal principal) throws ParameterException, ProcessingException {

		if (principal == null) {
			throw new ParameterException("param principal null");
		}
		if (dto == null) {
			throw new ParameterException("param dto null");
		}

		List<ValidationError> errorList = new ArrayList<>();
		Set<String> invalidTabs = new HashSet<>();
		
		try {
			
			if (!ObjectUtils.isEmpty(dto.getUsrContact())) {
				if (StringUtils.isBlank(dto.getUsrContact().getContactTel())) {
					errorList.add(newValidationError("coreUsr.usrContact.contactTel",
							getMessage("ck.user.ctcNo.required")));
					invalidTabs.add("userDtls");
				} else if (StringUtils.isNotEmpty(dto.getUsrContact().getContactTel())) {
					Pattern pattern = Pattern.compile(PHONE_PATTERN);
					Matcher matcher = pattern.matcher(dto.getUsrContact().getContactTel());
					if (!matcher.matches()) {
						errorList.add(newValidationError("coreUsr.usrContact.telInvalid",
								getMessage("ck.user.ctcEmail.telInvalid")));
						invalidTabs.add("userDtls");
					}
				}
				if (StringUtils.isBlank(dto.getUsrContact().getContactEmail())) {
					errorList.add(newValidationError("coreUsr.usrContact.contactEmail",
							getMessage("ck.user.ctcEmail.required")));
					invalidTabs.add("userDtls");
				} else if (StringUtils.isNotEmpty(dto.getUsrContact().getContactEmail())) {
					Pattern pattern = Pattern.compile(EMAIL_PATTERN);
					Matcher matcher = pattern.matcher(dto.getUsrContact().getContactEmail());
					if (!matcher.matches()) {
						errorList.add(newValidationError("coreUsr.usrContact.emailInvalid",
								getMessage("ck.user.ctcEmail.emailInvalid")));
						invalidTabs.add("userDtls");
					} else if (clickargoUserService.findByEmail(dto.getUsrContact().getContactEmail()).isPresent()) {
						// duplicate email address.	
						errorList.add(newValidationError("coreUsr.usrContact.emailDuplicate",
								getMessage("ck.user.ctcEmail.emailDuplicate")));
						invalidTabs.add("userDtls");
					}
				}
			}
			
			if (!ObjectUtils.isEmpty(dto.getUsrAddr())) {
				if (StringUtils.isBlank(dto.getUsrAddr().getAddrLn1())) {
					errorList.add(
							newValidationError("coreUsr.usrAddr.addrLn1", getMessage("ck.user.addrLn1.required")));
					invalidTabs.add("userDtls");
				}

				if (StringUtils.isBlank(dto.getUsrAddr().getAddrProv())) {
					errorList.add(
							newValidationError("coreUsr.usrAddr.addrProv", getMessage("ck.user.addrProv.required")));
					invalidTabs.add("userDtls");
				}
				if (StringUtils.isBlank(dto.getUsrAddr().getAddrPcode())) {
					errorList.add(
							newValidationError("coreUsr.usrAddr.addrPcode", getMessage("ck.user.addrPcode.required")));
					invalidTabs.add("userDtls");
				}
				if (StringUtils.isBlank(dto.getUsrAddr().getAddrCity())) {
					errorList.add(
							newValidationError("coreUsr.usrAddr.addrCity", getMessage("ck.user.addrCity.required")));
					invalidTabs.add("userDtls");
				}
			}
			
			if (!invalidTabs.isEmpty() && invalidTabs.contains("userDtls")) {
				ObjectMapper mapper = new ObjectMapper();
				errorList.add(newValidationError("invalidTabs.userDtls", mapper.writeValueAsString(invalidTabs)));
			}
			
			return errorList;
		} catch (Exception ex) {
			throw new ProcessingException(ex);
		}
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