package com.guudint.clickargo.external.dto;

import java.io.Serializable;

public class GatewayConfig implements Serializable {

	private static final long serialVersionUID = -1024731153791589322L;
	private String url;
	private String uid;
	private String authPwd;
	private String contentType;
	private String isMock;
	private String mockUrl;
	private String mockUid;
	private String mockPwd;

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the authPwd
	 */
	public String getAuthPwd() {
		return authPwd;
	}

	/**
	 * @param authPwd the authPwd to set
	 */
	public void setAuthPwd(String authPwd) {
		this.authPwd = authPwd;
	}

	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getIsMock() {
		return isMock;
	}

	public void setIsMock(String isMock) {
		this.isMock = isMock;
	}

	public String getMockUrl() {
		return mockUrl;
	}

	public void setMockUrl(String mockUrl) {
		this.mockUrl = mockUrl;
	}

	public String getMockUid() {
		return mockUid;
	}

	public void setMockUid(String mockUid) {
		this.mockUid = mockUid;
	}

	public String getMockPwd() {
		return mockPwd;
	}

	public void setMockPwd(String mockPwd) {
		this.mockPwd = mockPwd;
	}

}
