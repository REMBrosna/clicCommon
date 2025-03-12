package com.guudint.clickargo.credit.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.guudint.clickargo.credit.model.TCkCreditSummary;
import com.guudint.clickargo.master.dto.CkMstServiceType;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.common.dto.AbstractDTO;
import com.vcc.camelone.master.dto.MstCurrency;

public class CkCreditSummary extends AbstractDTO<CkCreditSummary, TCkCreditSummary> {
    
    private static final long serialVersionUID = 1L;

    private String crsId;
    private CkMstServiceType TCkMstServiceType;
    private CoreAccn TCoreAccn;
    private BigDecimal crsAmt;
    private BigDecimal crsReserve;
    private BigDecimal crsUtilized;
    private BigDecimal crsBalance;
    private MstCurrency TMstCurrency;
    private Character crsStatus;
    private Date crsDtCreate;
    private String crsUidCreate;
    private Date crsDtLupd;
    private String crsUidLupd;

    public CkCreditSummary() {
    }

    public CkCreditSummary(TCkCreditSummary entity) {
        super(entity);
    }

    public CkCreditSummary(String crsId, CkMstServiceType TCkMstServiceType, CoreAccn TCoreAccn) {
        this.crsId = crsId;
        this.TCkMstServiceType = TCkMstServiceType;
        this.TCoreAccn = TCoreAccn;
    }

    public CkCreditSummary(String crsId, CkMstServiceType TCkMstServiceType, CoreAccn TCoreAccn, BigDecimal crsAmt, BigDecimal crsReserve, BigDecimal crsUtilized, BigDecimal crsBalance, MstCurrency TMstCurrency, Character crsStatus, Date crsDtCreate, String crsUidCreate, Date crsDtLupd, String crsUidLupd) {
        this.crsId = crsId;
        this.TCkMstServiceType = TCkMstServiceType;
        this.TCoreAccn = TCoreAccn;
        this.crsAmt = crsAmt;
        this.crsReserve = crsReserve;
        this.crsUtilized = crsUtilized;
        this.crsBalance = crsBalance;
        this.TMstCurrency = TMstCurrency;
        this.crsStatus = crsStatus;
        this.crsDtCreate = crsDtCreate;
        this.crsUidCreate = crsUidCreate;
        this.crsDtLupd = crsDtLupd;
        this.crsUidLupd = crsUidLupd;
    }

    public String getCrsId() {
        return this.crsId;
    }

    public void setCrsId(String crsId) {
        this.crsId = crsId;
    }

    public CkMstServiceType getTCkMstServiceType() {
        return this.TCkMstServiceType;
    }

    public void setTCkMstServiceType(CkMstServiceType TCkMstServiceType) {
        this.TCkMstServiceType = TCkMstServiceType;
    }

    public CoreAccn getTCoreAccn() {
        return this.TCoreAccn;
    }

    public void setTCoreAccn(CoreAccn TCoreAccn) {
        this.TCoreAccn = TCoreAccn;
    }

    public BigDecimal getCrsAmt() {
        return this.crsAmt;
    }

    public void setCrsAmt(BigDecimal crsAmt) {
        this.crsAmt = crsAmt;
    }

    public BigDecimal getCrsReserve() {
        return this.crsReserve;
    }

    public void setCrsReserve(BigDecimal crsReserve) {
        this.crsReserve = crsReserve;
    }

    public BigDecimal getCrsUtilized() {
        return this.crsUtilized;
    }

    public void setCrsUtilized(BigDecimal crsUtilized) {
        this.crsUtilized = crsUtilized;
    }

    public BigDecimal getCrsBalance() {
        return this.crsBalance;
    }

    public void setCrsBalance(BigDecimal crsBalance) {
        this.crsBalance = crsBalance;
    }

    public MstCurrency getTMstCurrency() {
        return this.TMstCurrency;
    }

    public void setTMstCurrency(MstCurrency TMstCurrency) {
        this.TMstCurrency = TMstCurrency;
    }

    public Character getCrsStatus() {
        return this.crsStatus;
    }

    public void setCrsStatus(Character crsStatus) {
        this.crsStatus = crsStatus;
    }

    public Date getCrsDtCreate() {
        return this.crsDtCreate;
    }

    public void setCrsDtCreate(Date crsDtCreate) {
        this.crsDtCreate = crsDtCreate;
    }

    public String getCrsUidCreate() {
        return this.crsUidCreate;
    }

    public void setCrsUidCreate(String crsUidCreate) {
        this.crsUidCreate = crsUidCreate;
    }

    public Date getCrsDtLupd() {
        return this.crsDtLupd;
    }

    public void setCrsDtLupd(Date crsDtLupd) {
        this.crsDtLupd = crsDtLupd;
    }

    public String getCrsUidLupd() {
        return this.crsUidLupd;
    }

    public void setCrsUidLupd(String crsUidLupd) {
        this.crsUidLupd = crsUidLupd;
    }

    @Override
    public int compareTo(CkCreditSummary o) {
        return 0;
    }

    @Override
    public void init() {
    }

}
