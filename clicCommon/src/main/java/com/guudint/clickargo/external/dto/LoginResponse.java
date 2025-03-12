package com.guudint.clickargo.external.dto;

public class LoginResponse extends BaseResponse {

    private String token;

    public LoginResponse() {
        this(null, null);
    }

    public LoginResponse(String code, String message) {
        super(code, message);
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
