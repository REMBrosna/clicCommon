package com.guudint.clickargo.clicservice.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.guudint.clickargo.common.AbstractClickCargoEntityService;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.ccm.dto.CoreUsr;
import com.vcc.camelone.ccm.model.TCoreUsr;
import com.vcc.camelone.common.controller.entity.EntityFilterRequest;
import com.vcc.camelone.common.exception.EntityNotFoundException;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;
import com.vcc.camelone.common.exception.ValidationException;
import com.vcc.camelone.common.service.ServiceStatus;
import com.vcc.camelone.locale.dto.CoreMstLocale;
import com.vcc.camelone.master.service.impl.AccountTypeService;

public class CkAdminUserService extends AbstractClickCargoEntityService<TCoreUsr, String, CoreUsr> {

	// Static Attributes
	////////////////////
	private static Logger log = Logger.getLogger(AccountTypeService.class);
	private static String auditTag = "CORE USER";
	private static String tableName = "T_CORE_USR";

	public CkAdminUserService() {
		super("coreUserDao", auditTag, TCoreUsr.class.getName(), tableName);
	}

	@Override
	public CoreUsr newObj(Principal principal) throws ParameterException, EntityNotFoundException, ProcessingException {
		CoreUsr user = new CoreUsr();
		user.setTCoreAccn(principal.getCoreAccn());
		return user;
	}

	@Override
	public CoreUsr findById(String id) throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CoreUsr deleteById(String id, Principal principal)
			throws ParameterException, EntityNotFoundException, ProcessingException, ValidationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CoreUsr> filterBy(EntityFilterRequest filterRequest)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void initBusinessValidator() {
		// TODO Auto-generated method stub

	}

	@Override
	protected Logger getLogger() {
		// TODO Auto-generated method stub
		return log;
	}

	@Override
	protected TCoreUsr initEnity(TCoreUsr entity) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected TCoreUsr entityFromDTO(CoreUsr dto) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CoreUsr dtoFromEntity(TCoreUsr entity) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String entityKeyFromDTO(CoreUsr dto) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected TCoreUsr updateEntity(ACTION attriubte, TCoreUsr entity, Principal principal, Date date)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected TCoreUsr updateEntityStatus(TCoreUsr entity, char status) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CoreUsr preSaveUpdateDTO(TCoreUsr storedEntity, CoreUsr dto)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void preSaveValidation(CoreUsr dto, Principal principal) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub

	}

	@Override
	protected ServiceStatus preUpdateValidation(CoreUsr dto, Principal principal)
			throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getWhereClause(CoreUsr dto, boolean wherePrinted) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected HashMap<String, Object> getParameters(CoreUsr dto) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CoreUsr whereDto(EntityFilterRequest filterRequest) throws ParameterException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CoreMstLocale getCoreMstLocale(CoreUsr dto)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CoreUsr setCoreMstLocale(CoreMstLocale coreMstLocale, CoreUsr dto)
			throws ParameterException, EntityNotFoundException, ProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

}
