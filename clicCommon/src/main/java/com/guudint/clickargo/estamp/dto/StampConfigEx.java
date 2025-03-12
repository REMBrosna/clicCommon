package com.guudint.clickargo.estamp.dto;

import java.io.Serializable;

public class StampConfigEx implements Serializable {

	private static final long serialVersionUID = 2803198194087345894L;

	private String accessToken;
	private String refreshToken;
	private Integer expiresIn;
	private Long lastRunTime;

	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * @return the refreshToken
	 */
	public String getRefreshToken() {
		return refreshToken;
	}

	/**
	 * @param refreshToken the refreshToken to set
	 */
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	/**
	 * @return the expiresIn
	 */
	public Integer getExpiresIn() {
		return expiresIn;
	}

	/**
	 * @param expiresIn the expiresIn to set
	 */
	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}

	/**
	 * @return the lastRunTime
	 */
	public Long getLastRunTime() {
		return lastRunTime;
	}

	/**
	 * @param lastRunTime the lastRunTime to set
	 */
	public void setLastRunTime(Long lastRunTime) {
		this.lastRunTime = lastRunTime;
	}

}
