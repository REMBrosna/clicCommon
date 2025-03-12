package com.guudint.clickargo.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetFundTransferStatusResponse extends BaseResponse {
    @JsonProperty("transaction_id")
    private String trxId;
    @JsonProperty("transfer_reference_number")
    private String refNum;
    @JsonProperty("invoice_num")
    private String invNum;
    @JsonProperty("settlement_date")
    private String settlementDate;
    @JsonProperty("beneficiary_account_number")
    private String beneAccnNum;
    @JsonProperty("beneficiary_account_name")
    private String beneAccnName;

    public GetFundTransferStatusResponse() {
        this(null, null);
    }

    public GetFundTransferStatusResponse(String code, String message) {
        super(code, message);
    }

    public String getTrxId() {
        return trxId;
    }

    public void setTrxId(String trxId) {
        this.trxId = trxId;
    }

    public String getRefNum() {
        return refNum;
    }

    public void setRefNum(String refNum) {
        this.refNum = refNum;
    }

    public String getInvNum() {
        return invNum;
    }

    public void setInvNum(String invNum) {
        this.invNum = invNum;
    }

    public String getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(String settlementDate) {
        this.settlementDate = settlementDate;
    }

    public String getBeneAccnNum() {
        return beneAccnNum;
    }

    public void setBeneAccnNum(String beneAccnNum) {
        this.beneAccnNum = beneAccnNum;
    }

    public String getBeneAccnName() {
        return beneAccnName;
    }

    public void setBeneAccnName(String beneAccnName) {
        this.beneAccnName = beneAccnName;
    }
}
