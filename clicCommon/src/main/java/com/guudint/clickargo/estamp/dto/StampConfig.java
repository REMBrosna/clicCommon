package com.guudint.clickargo.estamp.dto;

import com.vcc.camelone.common.COAbstractEntity;

public class StampConfig extends COAbstractEntity<StampConfig> {

	private static final long serialVersionUID = 1L;

	private String authUrl;
	private String url;
	private String clientId;
	private String clientSecret;
	private String contentType;
	private String code;

	/**
	 * @return the authUrl
	 */
	public String getAuthUrl() {
		return authUrl;
	}

	/**
	 * @param authUrl the authUrl to set
	 */
	public void setAuthUrl(String authUrl) {
		this.authUrl = authUrl;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getClientId() {
		return this.clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return this.clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getContentType() {
		return this.contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public int compareTo(StampConfig o) {
		return 0;
	}

	@Override
	public void init() {
	}

}
