package com.guudint.clickargo.common;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guudint.clickargo.common.model.ValidationError;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.common.dao.GenericDao;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.exception.ValidationException;
import com.vcc.camelone.common.service.entity.AbstractEntityService;
import com.vcc.camelone.config.model.TCoreSysparam;
import com.vcc.camelone.master.dto.MstAccnType;
import com.vcc.camelone.util.PrincipalUtilService;

public abstract class AbstractClickCargoEntityService<E, ID, D> extends AbstractEntityService<E, ID, D>
		implements IClickargoEntityService<E, ID, D> {

	protected static String HISTORY = "history";
	protected static String DEFAULT = "default";

	@Autowired
	protected Validator validator;

	@Autowired
	protected ApplicationEventPublisher eventPublisher;

	@Autowired
	@Qualifier("coreSysparamDao")
	protected GenericDao<TCoreSysparam, String> coreSysparamDao;

	@Autowired
	protected PrincipalUtilService principalUtilService;

	public AbstractClickCargoEntityService(String daoName, String moduleName, String entityName, String tableName) {
		super(daoName, moduleName, entityName, tableName);
	}

	/**
	 * Initialize the business validator
	 */
	protected abstract void initBusinessValidator();

	// Helper Methods
	/////////////////
	/**
	 * Validate data fields of dto
	 *
	 * @param dto
	 * @param groupClass
	 * @throws ValidationException
	 * @throws ProcessingException
	 * @throws ParameterException
	 */
	protected void validate(D dto, Class<?>... groupClass)
			throws ValidationException, ParameterException, ProcessingException {
//        getLogger().debug("validate");

		if (null == dto)
			throw new ParameterException("param dto null");

		Set<ConstraintViolation<D>> constraintViolations = validator.validate(dto, groupClass);
		Set<ConstraintViolation<?>> constriantViolationSet = new HashSet<>(constraintViolations);
		if (constraintViolations.isEmpty())
			return;

		throw new ValidationException(constraintViolationMap(constriantViolationSet));
	}

	/**
	 * Map constraint validations into JSON key-value format list
	 *
	 * @param constraintViolations
	 * @return
	 * @throws ParameterException
	 * @throws ProcessingException
	 */
	protected String constraintViolationMap(Set<ConstraintViolation<?>> constraintViolations)
			throws ParameterException, ProcessingException {
		getLogger().debug("errorMap");

		if (null == constraintViolations)
			throw new ParameterException("param erorrs null");
		if (constraintViolations.isEmpty())
			throw new ParameterException("param erorrs empty");

		String json;
		try {
			Map<String, String> constraintViolationMap = new HashMap<>();
			constraintViolations.stream().forEach(cv -> {
				getLogger().error(cv.getMessage() + " " + cv.getPropertyPath() + "  " + cv);
				constraintViolationMap.put(cv.getPropertyPath().toString(), cv.getMessage());
			});

			json = (new ObjectMapper()).writeValueAsString(constraintViolationMap);
		} catch (Exception ex) {
			getLogger().error("errorMap", ex);
			throw new ProcessingException(ex.getMessage());
		}
		return json;
	}

	/**
	 * Map business validation errors into JSON key-value format list
	 *
	 * @param validationErrors
	 * @return
	 * @throws ParameterException
	 * @throws ProcessingException
	 */
	protected String validationErrorMap(List<ValidationError> validationErrors)
			throws ParameterException, ProcessingException {
		getLogger().debug("validationMap");

		if (null == validationErrors)
			throw new ParameterException("param errros null");
		if (validationErrors.isEmpty())
			throw new ProcessingException("param errros empty");

		String json;
		try {
			Map<String, String> validationMap = new HashMap<>();
			validationErrors.stream().forEach(ve -> {
				getLogger().error(ve.getErrorType() + " " + ve.getErrorDescription() + "  " + ve);
				validationMap.put(ve.getErrorType().toString(), ve.getErrorDescription());
			});

			json = (new ObjectMapper()).writeValueAsString(validationMap);

		} catch (Exception ex) {
			getLogger().error("errorMap", ex);
			throw new ProcessingException(ex.getMessage());
		}
		return json;
	}

	protected abstract Logger getLogger();

	// Helper Methods
	//////////////////////
	protected String getSysParam(String key) throws Exception {
		getLogger().debug("getSysParam");
		if (StringUtils.isBlank(key))
			throw new ParameterException("param key null or empty");

		TCoreSysparam sysParam = coreSysparamDao.find(key);
		if (sysParam == null)
			throw new EntityNotFoundException("sysParam " + key + " not configured");

		return sysParam.getSysVal();

	}

	/**
	 * Saves the specified file to the base location configured in SysParam
	 * 
	 * @param filename
	 * @param data     - byte
	 */
	protected String saveAttachment(String filename, byte[] data) throws Exception {
		if (StringUtils.isBlank(filename))
			throw new ParameterException("param filename null or empty");

		if (data == null)
			throw new ParameterException("param data null or empty");

		String basePath = getSysParam(ICkConstant.KEY_CLICDO_ATTCH_BASE_LOCATION);
		if (StringUtils.isBlank(basePath))
			throw new ProcessingException("basePath is not configured");

		String file = basePath.concat(filename);
		FileOutputStream output = new FileOutputStream(file);
		output.write(data);
		output.close();
		return file;
	}

	/**
	 * Saves the specified file to the base location.
	 * 
	 * @param basePath - baselocation
	 * @param filename
	 * @param data     - byte
	 */
	protected String saveAttachment(String basePath, String filename, byte[] data) throws Exception {
		if (StringUtils.isBlank(basePath))
			throw new ParameterException("param basePath null or empty");

		if (StringUtils.isBlank(filename))
			throw new ParameterException("param filename null or empty");

		if (data == null)
			throw new ParameterException("param data null or empty");

		return saveAttachment(basePath.concat(filename), data);
	}

	/**
	 * Returns the account type of the principal in session.
	 */
	protected String getPrincipalAccountType() throws Exception {
		Principal principal = principalUtilService.getPrincipal();
		if (principal == null)
			throw new ProcessingException("principal null");

		CoreAccn accn = principal.getCoreAccn();
		if (accn == null)
			throw new ProcessingException("principal account null");
		Optional<MstAccnType> opAccnType = Optional.ofNullable(accn.getTMstAccnType());
		if (opAccnType.isPresent())
			return opAccnType.get().getAtypId();
		else
			throw new ProcessingException("account type not configured for " + accn.getAccnId());

	}

}
