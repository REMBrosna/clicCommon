package com.guudint.clickargo.validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.guudint.clickargo.admin.service.CkAccnConfigExtService;
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
import com.guudint.clickargo.common.ICkConstant;
import com.guudint.clickargo.common.model.ValidationError;
import com.guudint.clickargo.manageaccn.dto.CkManageAccn;
import com.guudint.clickargo.manageaccn.dto.CoreAccnStateEnum;
import com.guudint.clickargo.master.enums.AccountTypes;
import com.guudint.clickargo.master.enums.ServiceTypes;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.ccm.dto.CoreAddress;
import com.vcc.camelone.ccm.model.TCoreAccn;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.master.dto.MstAccnType;

@Component
public class AccountValidator {

	private static Logger LOG = Logger.getLogger(AccountValidator.class);
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	// Assuming dash is allowed, space not allowed
	private static final String PHONE_PATTERN = ICkConstant.PHONE_PATTERN;
	// Assuming 7 is the minimum length
	private static final int PHONE_MIN_LENGTH = 8;

	private static final String ACCOUNT_DETAILS_TAB = "cmpnyDtls";
	private static final String CONFIGURATIONS_TAB = "cnfgrtn";

	Locale locale = LocaleContextHolder.getLocale();

	@Autowired
	MessageSource messageSource;

	@Autowired
	@Qualifier("coreAccDao")
	private GenericDao<TCoreAccn, String> coreAccDao;

	@Autowired
	CkAccnConfigExtService ckAccnConfigExtService;

	public List<ValidationError> validateCreate(CkManageAccn dto, Principal principal)
			throws ParameterException, ProcessingException {

		List<ValidationError> invalidList = new ArrayList<>();
		invalidList.addAll(this.validateMandatoryFields(dto, principal));

		return invalidList;
	}

	public List<ValidationError> validateUpdate(CkManageAccn dto, Principal principal)
			throws ParameterException, ProcessingException {

		List<ValidationError> invalidList = new ArrayList<>();
		invalidList.addAll(this.validateMandatoryFields(dto, principal));
		return invalidList;
	}

	public List<ValidationError> validateSubmit(CkManageAccn dto, Principal principal)
			throws ParameterException, ProcessingException {

		List<ValidationError> invalidList = new ArrayList<>();
		invalidList.addAll(this.validateMandatoryFields(dto, principal));
		invalidList.addAll(this.validateOtherFields(dto, principal));
		return invalidList;
	}

	public List<ValidationError> validateReject(CkManageAccn dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ValidationError> validateCancel(CkManageAccn dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ValidationError> validateDelete(CkManageAccn dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ValidationError> validateConfirm(CkManageAccn dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ValidationError> validatePay(CkManageAccn dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ValidationError> validatePaid(CkManageAccn dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ValidationError> validateComplete(CkManageAccn dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param dto
	 * @return
	 * @throws ParameterException
	 * @throws ProcessingException
	 */
	private List<ValidationError> validateMandatoryFields(CkManageAccn dto, Principal principal)
			throws ParameterException, ProcessingException {

		if (principal == null)
			throw new ParameterException("param principal null");
		if (dto == null)
			throw new ParameterException("param dto null");

		try {
			List<ValidationError> errorList = new ArrayList<>();
			Set<String> invalidTabs = new HashSet<>();

			CoreAccn coreAccn = dto.getAccnDetails();
			if (null != coreAccn) {
				if (StringUtils.isBlank(coreAccn.getAccnName())) {
					errorList.add(
							newValidationError("accnDetails.accnName", getMessage("ck.account.accnName.required")));
					invalidTabs.add(ACCOUNT_DETAILS_TAB);
				} else if (StringUtils.isNotBlank(coreAccn.getAccnName())) {
					try {
						TCoreAccn crEntity = this.findByAccnName(coreAccn.getAccnName());
						if (null != crEntity && !crEntity.getAccnId().equalsIgnoreCase(coreAccn.getAccnId())) {
							errorList.add(newValidationError("accnDetails.accnNameDuplicate",
									getMessage("ck.account.accnName.duplicate")));
							invalidTabs.add(ACCOUNT_DETAILS_TAB);
						}
					} catch (Exception ex) {
						LOG.error("findByAccnName ", ex);
					}
				}

				MstAccnType mstAccnType = coreAccn.getTMstAccnType();
				if (null == mstAccnType) {
					errorList.add(newValidationError("accnDetails.TMstAccnType.atypId",
							getMessage("ck.account.TMstAccnType.atypId.required")));
					invalidTabs.add(ACCOUNT_DETAILS_TAB);
				} else if (null != mstAccnType && StringUtils.isEmpty(mstAccnType.getAtypId())) {
					errorList.add(newValidationError("accnDetails.TMstAccnType.atypId",
							getMessage("ck.account.TMstAccnType.atypId.required")));
					invalidTabs.add(ACCOUNT_DETAILS_TAB);
				}

				CoreAddress coreAddress = coreAccn.getAccnAddr();
				// Fixed NPE here as coreAddress can be null
				if (null == coreAddress || null == coreAddress.getAddrCtry()) {
					errorList.add(newValidationError("accnDetails.accnAddr.addrCtry.ctyCode",
							getMessage("ck.account.addrCtry.ctyCode.required")));
					invalidTabs.add(ACCOUNT_DETAILS_TAB);
				} else if (null != coreAddress.getAddrCtry()
						&& StringUtils.isEmpty(coreAddress.getAddrCtry().getCtyCode())) {
					errorList.add(newValidationError("accnDetails.accnAddr.addrCtry.ctyCode",
							getMessage("ck.account.addrCtry.ctyCode.required")));
					invalidTabs.add(ACCOUNT_DETAILS_TAB);
				}

				if (!invalidTabs.isEmpty() && invalidTabs.contains(ACCOUNT_DETAILS_TAB)) {
					ObjectMapper mapper = new ObjectMapper();
					errorList.add(newValidationError("invalidTabs.cmpnyDtls", mapper.writeValueAsString(invalidTabs)));
				}
			}

			// check if there is at least one that is checked
			if (ckAccnConfigExtService.isIndonesia()) {
				if (dto.getSvcSubTypes() != null) {
					boolean hasSubscription = dto.getSvcSubTypes().stream().anyMatch(e -> e.getIsSubscribed() == true);
					if (!hasSubscription) {
						errorList.add(
								newValidationError("accnDetails.svcSubs", getMessage("ck.account.svc.subscriptions.required")));
						invalidTabs.add(CONFIGURATIONS_TAB);
					}
				}
			}

			return errorList;
		} catch (Exception ex) {
			throw new ProcessingException(ex);
		}
	}

	/**
	 * 
	 * @param dto
	 * @param principal
	 * @return
	 * @throws ParameterException
	 * @throws ProcessingException
	 */
	private List<ValidationError> validateOtherFields(CkManageAccn dto, Principal principal)
			throws ParameterException, ProcessingException {

		if (principal == null)
			throw new ParameterException("param principal null");
		if (dto == null)
			throw new ParameterException("param dto null");

		List<ValidationError> errorList = new ArrayList<>();
		Set<String> invalidTabs = new HashSet<>();

		CoreAccn coreAccn = dto.getAccnDetails();
		Optional<MstAccnType> accnType = Optional.ofNullable(dto.getAccnDetails().getTMstAccnType());

		try {
			if (StringUtils.isBlank(coreAccn.getAccnCoyRegn())) {
				errorList.add(newValidationError("accnDetails.accnCoyRegn", getMessage("ck.account.tinNo.required")));
				invalidTabs.add(ACCOUNT_DETAILS_TAB);
			}

			if(ckAccnConfigExtService.isIndonesia()){
				if (StringUtils.isBlank(dto.getSageAccpacId())) {
					errorList.add(newValidationError("accnDetails.svcSubs", getMessage("ck.account.svc.subscriptions.required")));
					invalidTabs.add(CONFIGURATIONS_TAB);
				}
			}

			if (accnType.isPresent() && StringUtils.isNotBlank(accnType.get().getAtypId())) {
				if (accnType.get().getAtypId().equalsIgnoreCase(AccountTypes.ACC_TYPE_CO.name())
						|| accnType.get().getAtypId().equalsIgnoreCase(AccountTypes.ACC_TYPE_FF.name())) {

					// only validate this if it's clictruck or if it's not set by default (this is
					// for clictruck)
					if (dto.getAccnServiceType() == null || dto.getAccnServiceType() == ServiceTypes.CLICTRUCK) {
						if (StringUtils.isBlank(dto.getFinanceOptions())) {
							errorList.add(newValidationError("accnDetails.financeOptions",
									getMessage("ck.account.financeOptions.required")));
							invalidTabs.add(CONFIGURATIONS_TAB);
						}
					}

				}
				// Hide for now...
//				if (accnType.get().getAtypId().equalsIgnoreCase(AccountTypes.ACC_TYPE_TO.name())) {
//					if (StringUtils.isBlank(dto.getMobileEnabled())) {
//						errorList.add(newValidationError("accnDetails.mobileEnabled", getMessage("ck.account.mobileEnabled.required")));
//						invalidTabs.add(CONFIGURATIONS_TAB);
//					}
//				}
			}

			if (!ObjectUtils.isEmpty(coreAccn.getAccnContact())) {
				// Company Contact Telephone
				if (StringUtils.isBlank(coreAccn.getAccnContact().getContactTel())) {
					errorList.add(newValidationError("accnDetails.accnContact.contactTel",
							getMessage("ck.account.ctcNo.required")));
					invalidTabs.add(ACCOUNT_DETAILS_TAB);
				} else if (StringUtils.isNotEmpty(coreAccn.getAccnContact().getContactTel())) {
					Pattern pattern = Pattern.compile(PHONE_PATTERN);
					Matcher matcher = pattern.matcher(coreAccn.getAccnContact().getContactTel());
					if (coreAccn.getAccnContact().getContactTel().length() < PHONE_MIN_LENGTH) {
						invalidTabs.add(ACCOUNT_DETAILS_TAB);
						errorList.add(newValidationError("accnDetails.accnContact.minLength",
								getMessage("ck.account.ctcEmail.minLength")));
					} else if (!matcher.matches()) {
						errorList.add(newValidationError("accnDetails.accnContact.telInvalid",
								getMessage("ck.account.ctcEmail.telInvalid")));
						invalidTabs.add(ACCOUNT_DETAILS_TAB);
					}
				}
				// Company Contact Fax
				if (StringUtils.isNotBlank(coreAccn.getAccnContact().getContactFax())) {
					Pattern pattern = Pattern.compile(PHONE_PATTERN);
					Matcher matcher = pattern.matcher(coreAccn.getAccnContact().getContactFax());
					if (coreAccn.getAccnContact().getContactFax().length() < PHONE_MIN_LENGTH) {
						invalidTabs.add(ACCOUNT_DETAILS_TAB);
						errorList.add(newValidationError("accnDetails.accnContact.contactFaxMin",
								getMessage("ck.account.ctcFax.minLength")));
					} else if (!matcher.matches()) {
						errorList.add(newValidationError("accnDetails.accnContact.faxInvalid",
								getMessage("ck.account.ctcFax.telInvalid")));
						invalidTabs.add(ACCOUNT_DETAILS_TAB);
					}
				}
				// Company Contact Email
				if (StringUtils.isBlank(coreAccn.getAccnContact().getContactEmail())) {
					errorList.add(newValidationError("accnDetails.accnContact.contactEmail",
							getMessage("ck.account.ctcEmail.required")));
					invalidTabs.add(ACCOUNT_DETAILS_TAB);
				} else if (StringUtils.isNotEmpty(coreAccn.getAccnContact().getContactEmail())) {
					Pattern pattern = Pattern.compile(EMAIL_PATTERN);
					Matcher matcher = pattern.matcher(coreAccn.getAccnContact().getContactEmail());
					if (!matcher.matches()) {
						errorList.add(newValidationError("accnDetails.accnContact.emailInvalid",
								getMessage("ck.account.ctcEmail.emailInvalid")));
						invalidTabs.add(ACCOUNT_DETAILS_TAB);
					}
				}
			}

			if (!ObjectUtils.isEmpty(coreAccn.getAccnAddr())) {
				if (StringUtils.isBlank(coreAccn.getAccnAddr().getAddrLn1())) {
					errorList.add(newValidationError("accnDetails.accnAddr.addrLn1",
							getMessage("ck.account.addrLn1.required")));
					invalidTabs.add(ACCOUNT_DETAILS_TAB);
				}
				if (StringUtils.isBlank(coreAccn.getAccnAddr().getAddrLn2())) {
					errorList.add(newValidationError("accnDetails.accnAddr.addrLn2",
							getMessage("ck.account.addrLn2.required")));
					invalidTabs.add(ACCOUNT_DETAILS_TAB);
				}
				if (StringUtils.isBlank(coreAccn.getAccnAddr().getAddrProv())) {
					errorList.add(newValidationError("accnDetails.accnAddr.addrProv",
							getMessage("ck.account.addrProv.required")));
					invalidTabs.add(ACCOUNT_DETAILS_TAB);
				}
				if (StringUtils.isBlank(coreAccn.getAccnAddr().getAddrPcode())) {
					errorList.add(newValidationError("accnDetails.accnAddr.addrPcode",
							getMessage("ck.account.addrPcode.required")));
					invalidTabs.add(ACCOUNT_DETAILS_TAB);
				}
				if (StringUtils.isBlank(coreAccn.getAccnAddr().getAddrCity())) {
					errorList.add(newValidationError("accnDetails.accnAddr.addrCity",
							getMessage("ck.account.addrCity.required")));
					invalidTabs.add(ACCOUNT_DETAILS_TAB);
				}
			}

			if (!invalidTabs.isEmpty() && invalidTabs.contains(ACCOUNT_DETAILS_TAB)) {
				ObjectMapper mapper = new ObjectMapper();
				errorList.add(newValidationError("invalidTabs.cmpnyDtls", mapper.writeValueAsString(invalidTabs)));
			}

			if (!invalidTabs.isEmpty() && invalidTabs.contains(CONFIGURATIONS_TAB)) {
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
	 * 
	 * @param accnName
	 * @return
	 * @throws Exception
	 */
	public TCoreAccn findByAccnName(String accnName) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(TCoreAccn.class);
		criteria.add(Restrictions.eq("accnName", accnName));
		criteria.add(Restrictions.not(Restrictions.in("accnStatus",
				Arrays.asList(CoreAccnStateEnum.REG_DELETED.getCode(), CoreAccnStateEnum.REG_REJECTED.getCode()))));
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