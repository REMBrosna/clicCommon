package com.guudint.clickargo.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class GetFundTransferStatusRequest {
    @JsonProperty("transfer_provider")
    private String transferProvider;
    @JsonProperty("transaction_id")
    private String trxId;
    @JsonProperty("invoice_number")
    private String invoiceNum;
    @JsonProperty("transfer_reference_number")
    private String refNum;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("account_number")
    private String accnNum;

    public String getTransferProvider() {
        return transferProvider;
    }

    public void setTransferProvider(String transferProvider) {
        this.transferProvider = transferProvider;
    }

    public String getTrxId() {
        return trxId;
    }

    public void setTrxId(String trxId) {
        this.trxId = trxId;
    }

    public String getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(String invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public String getRefNum() {
        return refNum;
    }

    public void setRefNum(String refNum) {
        this.refNum = refNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccnNum() {
        return accnNum;
    }

    public void setAccnNum(String accnNum) {
        this.accnNum = accnNum;
    }

}
