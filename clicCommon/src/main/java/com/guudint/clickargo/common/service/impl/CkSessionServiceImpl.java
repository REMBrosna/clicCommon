package com.guudint.clickargo.common.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.guudint.clickargo.common.service.ICkSession;
import com.vcc.camelone.cac.model.Principal;
import com.vcc.camelone.cac.services.ICOSession;
import com.vcc.camelone.coas.security.JwtUser;
import com.vcc.camelone.coas.security.authentication.utils.JwtUtil;
import com.vcc.camelone.common.exception.ProcessingException;

@Service("ckSessionService")
public class CkSessionServiceImpl implements ICkSession {

	// Static Attributes
	////////////////////
	private static Logger log = LogManager.getLogger(CkSessionServiceImpl.class);
	private final static String AUTHROIZATRION = "Authorization";

	// Attributes
	/////////////
	@Autowired
	private HttpServletRequest httpRequest;

	@Autowired
	private HttpSession httpSession;

	@Autowired
	@Qualifier("cacSessService")
	private ICOSession sessionService;

	// Interface Methods
	////////////////////
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.guudint.clickargo.common.service.ICkSession#getPrincipal()
	 * 
	 */
	public Principal getPrincipal() {

		Principal principal = getPrincipalViaToken();
		if (principal != null)
			return principal;

		principal = getPrincipalViaSessionId();
		if (principal != null)
			return principal;

		return null;
	}

	// Helper Methods
	//////////////////
	/**
	 * @return
	 * @throws ProcessingException
	 * @throws Exception
	 */
	private String getJWTtoken() throws ProcessingException, Exception {
		log.debug("getJWTtoken");
		
		try {
			String header = httpRequest.getHeader(AUTHROIZATRION);
			if (StringUtils.isEmpty(header))
				throw new ProcessingException("header null or empty");
			
			return header.substring(7);
		} catch (ProcessingException ex) {
			log.error("getJWTtoken", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("getJWTtoken", ex);
			throw ex;
		}
	}

	/**
	 * Get principal via JWT token
	 * 
	 * @return
	 */
	private Principal getPrincipalViaToken() {
		log.debug("getPrincipalViaToken");
		
		try {
			JwtUser jwtUser = new JwtUtil().parseToken(this.getJWTtoken());
			return sessionService.getPrincipal(jwtUser.getVccSessionId());
		} catch (Exception ex) {
			log.error("getPrincipalViaToken", ex);			
			return null;
		}
	}

	/**
	 * Get Principal by session id
	 * 
	 * @return
	 */
	private Principal getPrincipalViaSessionId() {
		log.debug("getPrincipalViaSessionId");

		try {
			return sessionService.getPrincipal(httpSession.getId());
		} catch (Exception ex) {
			log.error("getPrincipalViaToken", ex);	
			return null;
		}
	}

}
