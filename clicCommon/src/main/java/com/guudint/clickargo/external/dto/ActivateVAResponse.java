package com.guudint.clickargo.external.dto;

public class ActivateVAResponse extends BaseResponse {

    public ActivateVAResponse() {
        this(null ,null);
    }

    public ActivateVAResponse(String code, String message) {
        super(code, message);
    }

}
