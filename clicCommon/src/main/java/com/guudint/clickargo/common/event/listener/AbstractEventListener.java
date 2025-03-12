package com.guudint.clickargo.common.event.listener;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.vcc.camelone.util.PrincipalUtilService;

/**
 * Service specific event listener should extend this one. This will process the
 * event listeners specified in the specific service xml config.
 */
public abstract class AbstractEventListener<E extends ApplicationEvent> implements ApplicationListener<E> {

	@Autowired
	protected ApplicationContext applicationContext;

	protected HashMap<String, String> listeners;
	
	@Autowired
	protected PrincipalUtilService principalUtilService;

	public HashMap<String, String> getListeners() {
		return listeners;
	}

	public void setListeners(HashMap<String, String> listeners) {
		this.listeners = listeners;
	}

}
