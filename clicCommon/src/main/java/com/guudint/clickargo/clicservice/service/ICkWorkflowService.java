package com.guudint.clickargo.clicservice.service;

import com.guudint.clickargo.master.enums.FormActions;
import com.guudint.clickargo.master.enums.ServiceTypes;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.common.dto.AbstractDTO;
import com.vcc.camelone.common.exception.ParameterException;
import com.vcc.camelone.common.exception.ProcessingException;

public interface ICkWorkflowService<E, D extends AbstractDTO<D, E>> {

	public D moveState(FormActions action, D dto, Principal principal, ServiceTypes serviceTypes)
			throws ParameterException, ProcessingException, Exception;
}
