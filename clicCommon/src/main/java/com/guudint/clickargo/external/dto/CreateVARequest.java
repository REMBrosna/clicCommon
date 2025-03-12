package com.guudint.clickargo.external.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.function.Consumer;

public class CreateVARequest {
    @JsonProperty("application_id")
    private String appId;
    @JsonProperty("application_reference_num")
    private String appRefNum;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("customer_name")
    private String custName;
    @JsonProperty("customer_email")
    private String custEmail;
    @JsonProperty("customer_phone")
    private String custPhone;
    @JsonProperty("invoice_num")
    private String invNo;
    @JsonProperty("invoice_desc")
    private String invDesc;
    @JsonProperty("callback_url")
    private String callbackUrl;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("amount")
    private BigDecimal amount;
    @JsonProperty("virtual_account")
    private String accnNum;
    @JsonProperty("is_static_va")
    private Boolean isStaticVA;
    @JsonProperty("payment_provider")
    private String paymentProvider;

    @JsonIgnore
    private Runnable postHandler;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppRefNum() {
        return appRefNum;
    }

    public void setAppRefNum(String appRefNum) {
        this.appRefNum = appRefNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public String getInvNo() {
        return invNo;
    }

    public void setInvNo(String invNo) {
        this.invNo = invNo;
    }

    public String getInvDesc() {
        return invDesc;
    }

    public void setInvDesc(String invDesc) {
        this.invDesc = invDesc;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAccnNum() {
        return accnNum;
    }

    public void setAccnNum(String accnNum) {
        this.accnNum = accnNum;
    }

    public Boolean getStaticVA() {
        return isStaticVA;
    }

    public void setStaticVA(Boolean staticVA) {
        isStaticVA = staticVA;
    }

    public String getPaymentProvider() {
        return paymentProvider;
    }

    public void setPaymentProvider(String paymentProvider) {
        this.paymentProvider = paymentProvider;
    }

    public Runnable getPostHandler() {
        return postHandler;
    }

    public void setPostHandler(Runnable postHandler) {
        this.postHandler = postHandler;
    }
}
