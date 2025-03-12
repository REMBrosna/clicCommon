package com.guudint.clickargo.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;

public class CreateStaticVAResponse extends BaseResponse {
    @JsonProperty("virtual_account")
    private BigInteger accnNo;

    public CreateStaticVAResponse() {
        this(null, null);
    }

    public CreateStaticVAResponse(String code, String message) {
        super(code, message);
    }

    public BigInteger getAccnNo() {
        return accnNo;
    }

    public void setAccnNo(BigInteger accnNo) {
        this.accnNo = accnNo;
    }
}
