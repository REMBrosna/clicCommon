package com.guudint.clickargo.common.event.listener;

import com.guudint.clickargo.common.event.SubmitEvent;
import com.vcc.camelone.common.dto.AbstractDTO;
import com.vcc.camelone.common.exception.COException;
import com.vcc.camelone.common.exception.ErrorCodes;
import com.vcc.camelone.common.exception.ParameterException;

public class SubmitPostListener<E, D extends AbstractDTO<D, E>> extends AbstractEventListener<SubmitEvent<E, D>> {

	@SuppressWarnings({ "unchecked" })
	@Override
	public void onApplicationEvent(SubmitEvent<E, D> event) {

		try {

			if (event.getWfType() == null)
				throw new ParameterException("workflowtype null");

			Object bean = applicationContext.getBean(listeners.get(event.getWfType().name()));
			IWfPostEventListenerService<E, D> listener = (IWfPostEventListenerService<E, D>) bean;
			listener.processSubmit(event.getDto(), principalUtilService.getPrincipal());
		} catch (Exception ex) {
			ex.printStackTrace();
			COException.create(COException.ERROR, ErrorCodes.ERR_GEN_DATABASE, ErrorCodes.MSG_GEN_DATABASE,
					"SubmitPostListener", ex);
		}

	}

}
