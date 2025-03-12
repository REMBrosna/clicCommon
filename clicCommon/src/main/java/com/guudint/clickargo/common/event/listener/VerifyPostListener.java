package com.guudint.clickargo.common.event.listener;

import com.guudint.clickargo.common.event.VerifyEvent;
import com.vcc.camelone.common.dto.AbstractDTO;
import com.vcc.camelone.common.exception.COException;
import com.vcc.camelone.common.exception.ErrorCodes;
import com.vcc.camelone.common.exception.ParameterException;

public class VerifyPostListener<E, D extends AbstractDTO<D, E>> extends AbstractEventListener<VerifyEvent<E, D>> {

	@SuppressWarnings({ "unchecked" })
	@Override
	public void onApplicationEvent(VerifyEvent<E, D> event) {

		try {

			if (event.getWfType() == null)
				throw new ParameterException("workflowtype null");

			Object bean = applicationContext.getBean(listeners.get(event.getWfType().name()));
			IWfPostEventListenerService<E, D> listener = (IWfPostEventListenerService<E, D>) bean;
			listener.processVerify(event.getDto(), principalUtilService.getPrincipal());
		} catch (Exception ex) {
			ex.printStackTrace();
			COException.create(COException.ERROR, ErrorCodes.ERR_GEN_DATABASE, ErrorCodes.MSG_GEN_DATABASE,
					"VerifyPostListener", ex);
		}

	}

}
