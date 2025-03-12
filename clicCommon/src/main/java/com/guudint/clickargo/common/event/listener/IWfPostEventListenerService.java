package com.guudint.clickargo.common.event.listener;

import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.common.dto.AbstractDTO;

public interface IWfPostEventListenerService<E, D extends AbstractDTO<D, E>> {

	public void processSubmit(D dto, Principal principal) throws Exception;

	public void processVerify(D dto, Principal principal) throws Exception;

	public void processApprove(D dto, Principal principal) throws Exception;

	public void processReject(D dto, Principal principal) throws Exception;
}
