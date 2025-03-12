package com.guudint.clickargo.common.service;

import com.vcc.camelone.cac.model.Principal;

public interface ICkSession {

	/**
	 * Get the principal for the session
	 * @return
	 */
	public Principal getPrincipal();
}
