package com.guudint.clickargo.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class CreateVAResponse extends BaseResponse {

    @JsonProperty("transaction_id")
    private String trxId;
    @JsonProperty("virtual_account")
    private BigInteger accnId;
    @JsonProperty("expired_date")
    private Date expiredDate;
    @JsonProperty("va_name")
    private String accnName;
    @JsonProperty("bank_name")
    private String bankName;
    @JsonProperty("amount")
    private BigDecimal amount;
    @JsonProperty("payment_date")
    private Date paymentDate;
    @JsonProperty("is_paid")
    private Boolean isPaid;

    public CreateVAResponse() {
        this(null, null);
    }

    public CreateVAResponse(String code, String message) {
        super(code, message);
    }

    public String getTrxId() {
        return trxId;
    }

    public void setTrxId(String trxId) {
        this.trxId = trxId;
    }

    public BigInteger getAccnId() {
        return accnId;
    }

    public void setAccnId(BigInteger accnId) {
        this.accnId = accnId;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getAccnName() {
        return accnName;
    }

    public void setAccnName(String accnName) {
        this.accnName = accnName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }
}
