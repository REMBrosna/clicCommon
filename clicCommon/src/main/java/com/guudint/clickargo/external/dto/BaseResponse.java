package com.guudint.clickargo.external.dto;

import org.apache.commons.lang.StringUtils;

import com.vcc.camelone.common.COAbstractEntity;

public class BaseResponse extends COAbstractEntity<BaseResponse> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2486346727840324389L;
	private String status;
	private ErrorData err;
	private String code;
	private String message;

	public BaseResponse() {

	}

	public BaseResponse(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public BaseResponse(String code, ErrorData err) {
		this.code = code;
		this.err = err;
	}

	public boolean hasError() {
		return ((StringUtils.isNotBlank(code) && !StringUtils.equals(code, "200"))
				|| (StringUtils.isNotBlank(status)
						&& (status.equalsIgnoreCase("FAILED") || status.equalsIgnoreCase("ERROR")))
				|| (err != null && err.getCode() != 200));
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ErrorData getErr() {
		return err;
	}

	public void setErr(ErrorData err) {
		this.err = err;
	}

	@Override
	public int compareTo(BaseResponse o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

}
