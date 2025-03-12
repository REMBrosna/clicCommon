package com.guudint.clickargo.estamp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.vcc.camelone.common.COAbstractEntity;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AuthTokenResponse extends COAbstractEntity<AuthTokenResponse> {

	private static final long serialVersionUID = 1L;

	private String accessToken;
	private String tokenType;
	private Integer expiresIn;
	private String refreshToken;

	@JsonProperty("access_token")
	public String getAccessToken() {
		return this.accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@JsonProperty("token_type")
	public String getTokenType() {
		return this.tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	@JsonProperty("expires_in")
	public Integer getExpiresIn() {
		return this.expiresIn;
	}

	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}

	@JsonProperty("refresh_token")
	public String getRefreshToken() {
		return this.refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Override
	public int compareTo(AuthTokenResponse o) {
		return 0;
	}

	@Override
	public void init() {
	}

}
