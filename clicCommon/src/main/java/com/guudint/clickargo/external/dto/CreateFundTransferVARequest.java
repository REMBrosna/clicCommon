package com.guudint.clickargo.external.dto;

import java.math.BigDecimal;

import com.vcc.camelone.common.COAbstractEntity;

public class CreateFundTransferVARequest extends COAbstractEntity<CreateFundTransferVARequest> {

    private static final long serialVersionUID = -7728292600853637585L;
	String node;
	String serviceID;
	String refID;
	String account;
	String va;
	BigDecimal amount;
	String ccy;

    public String getNode() {
        return this.node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getServiceID() {
        return this.serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getRefID() {
        return this.refID;
    }

    public void setRefID(String refID) {
        this.refID = refID;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getVa() {
        return this.va;
    }

    public void setVa(String va) {
        this.va = va;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCcy() {
        return this.ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    @Override
    public int compareTo(CreateFundTransferVARequest o) {
        return 0;
    }

    @Override
    public void init() {
    }
    
}
