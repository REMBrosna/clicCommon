package com.guudint.clickargo.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ActivateVARequest {
    @JsonProperty("application_id")
    private String appId;
    @JsonProperty("application_reference_num")
    private String appRefNum;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("customer_name")
    private String customerName;
    @JsonProperty("customer_email")
    private String customerEmail;
    @JsonProperty("customer_phone")
    private String customerPhone;
    @JsonProperty("invoice_num")
    private String invoiceNum;
    @JsonProperty("invoice_desc")
    private String invoiceDesc;
    @JsonProperty("callback_url")
    private String callbackUrl;
    @JsonProperty("payment_provider")
    private String paymentProvider;
    private String currency;
    private BigDecimal amount;
    @JsonProperty("is_static_va")
    private boolean isStaticVA;
    @JsonProperty("virtual_account")
    private BigInteger virtualAccn;

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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(String invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public String getInvoiceDesc() {
        return invoiceDesc;
    }

    public void setInvoiceDesc(String invoiceDesc) {
        this.invoiceDesc = invoiceDesc;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getPaymentProvider() {
        return paymentProvider;
    }

    public void setPaymentProvider(String paymentProvider) {
        this.paymentProvider = paymentProvider;
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

    public boolean isStaticVA() {
        return isStaticVA;
    }

    public void setStaticVA(boolean staticVA) {
        isStaticVA = staticVA;
    }

    public BigInteger getVirtualAccn() {
        return virtualAccn;
    }

    public void setVirtualAccn(BigInteger virtualAccn) {
        this.virtualAccn = virtualAccn;
    }
}
