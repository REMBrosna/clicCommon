package com.guudint.clickargo.common.event.listener;

import com.guudint.clickargo.common.event.RejectEvent;
import com.vcc.camelone.common.dto.AbstractDTO;
import com.vcc.camelone.common.exception.COException;
import com.vcc.camelone.common.exception.ErrorCodes;
import com.vcc.camelone.common.exception.ParameterException;

public class RejectPostListener<E, D extends AbstractDTO<D, E>> extends AbstractEventListener<RejectEvent<E, D>> {

	@SuppressWarnings({ "unchecked" })
	@Override
	public void onApplicationEvent(RejectEvent<E, D> event) {

		try {

			if (event.getWfType() == null)
				throw new ParameterException("workflowtype null");

			Object bean = applicationContext.getBean(listeners.get(event.getWfType().name()));
			IWfPostEventListenerService<E, D> listener = (IWfPostEventListenerService<E, D>) bean;
			listener.processReject(event.getDto(), principalUtilService.getPrincipal());
		} catch (Exception ex) {
			ex.printStackTrace();
			COException.create(COException.ERROR, ErrorCodes.ERR_GEN_DATABASE, ErrorCodes.MSG_GEN_DATABASE,
					"RejectPostListener", ex);
		}

	}

}
