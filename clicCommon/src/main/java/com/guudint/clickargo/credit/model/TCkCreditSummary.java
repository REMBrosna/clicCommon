package com.guudint.clickargo.credit.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.guudint.clickargo.master.model.TCkMstServiceType;
import com.vcc.camelone.ccm.model.TCoreAccn;
import com.vcc.camelone.common.COAbstractEntity;
import com.vcc.camelone.master.model.TMstCurrency;

@Entity
@Table(name = "T_CK_CREDIT_SUMMARY")
public class TCkCreditSummary extends COAbstractEntity<TCkCreditSummary> {
    
    private static final long serialVersionUID = 1L;
	public static final String PREFIX_ID = "CRS";

    private String crsId;
    private TCkMstServiceType TCkMstServiceType;
    private TCoreAccn TCoreAccn;
    private BigDecimal crsAmt;
    private BigDecimal crsReserve;
    private BigDecimal crsUtilized;
    private BigDecimal crsBalance;
    private TMstCurrency TMstCurrency;
    private Character crsStatus;
    private Date crsDtCreate;
    private String crsUidCreate;
    private Date crsDtLupd;
    private String crsUidLupd;

    public TCkCreditSummary() {
    }

    public TCkCreditSummary(String crsId, TCkMstServiceType TCkMstServiceType, TCoreAccn TCoreAccn) {
        this.crsId = crsId;
        this.TCkMstServiceType = TCkMstServiceType;
        this.TCoreAccn = TCoreAccn;
    }

    public TCkCreditSummary(String crsId, TCkMstServiceType TCkMstServiceType, TCoreAccn TCoreAccn, BigDecimal crsAmt, BigDecimal crsReserve, BigDecimal crsUtilized, BigDecimal crsBalance, TMstCurrency TMstCurrency, Character crsStatus, Date crsDtCreate, String crsUidCreate, Date crsDtLupd, String crsUidLupd) {
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

    @Id
	@Column(name = "CRS_ID", unique = true, nullable = false, length = 35)
    public String getCrsId() {
        return this.crsId;
    }

    public void setCrsId(String crsId) {
        this.crsId = crsId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CRS_SERVICE_TYPE")
    public TCkMstServiceType getTCkMstServiceType() {
        return this.TCkMstServiceType;
    }

    public void setTCkMstServiceType(TCkMstServiceType TCkMstServiceType) {
        this.TCkMstServiceType = TCkMstServiceType;
    }

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CRS_COMPANY")
    public TCoreAccn getTCoreAccn() {
        return this.TCoreAccn;
    }

    public void setTCoreAccn(TCoreAccn TCoreAccn) {
        this.TCoreAccn = TCoreAccn;
    }

    @Column(name = "CRS_AMT", precision = 15, scale = 2)
    public BigDecimal getCrsAmt() {
        return this.crsAmt;
    }

    public void setCrsAmt(BigDecimal crsAmt) {
        this.crsAmt = crsAmt;
    }

    @Column(name = "CRS_RESERVE", precision = 15, scale = 2)
    public BigDecimal getCrsReserve() {
        return this.crsReserve;
    }

    public void setCrsReserve(BigDecimal crsReserve) {
        this.crsReserve = crsReserve;
    }

    @Column(name = "CRS_UTILIZED", precision = 15, scale = 2)
    public BigDecimal getCrsUtilized() {
        return this.crsUtilized;
    }

    public void setCrsUtilized(BigDecimal crsUtilized) {
        this.crsUtilized = crsUtilized;
    }

    @Column(name = "CRS_BALANCE", precision = 15, scale = 2)
    public BigDecimal getCrsBalance() {
        return this.crsBalance;
    }

    public void setCrsBalance(BigDecimal crsBalance) {
        this.crsBalance = crsBalance;
    }

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CRS_CCY")
    public TMstCurrency getTMstCurrency() {
        return this.TMstCurrency;
    }

    public void setTMstCurrency(TMstCurrency TMstCurrency) {
        this.TMstCurrency = TMstCurrency;
    }

    @Column(name = "CRS_STATUS", length = 1)
    public Character getCrsStatus() {
        return this.crsStatus;
    }

    public void setCrsStatus(Character crsStatus) {
        this.crsStatus = crsStatus;
    }

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CRS_DT_CREATE", length = 19)
    public Date getCrsDtCreate() {
        return this.crsDtCreate;
    }

    public void setCrsDtCreate(Date crsDtCreate) {
        this.crsDtCreate = crsDtCreate;
    }

    @Column(name = "CRS_UID_CREATE", length = 35)
    public String getCrsUidCreate() {
        return this.crsUidCreate;
    }

    public void setCrsUidCreate(String crsUidCreate) {
        this.crsUidCreate = crsUidCreate;
    }

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CRS_DT_LUPD", length = 19)
    public Date getCrsDtLupd() {
        return this.crsDtLupd;
    }

    public void setCrsDtLupd(Date crsDtLupd) {
        this.crsDtLupd = crsDtLupd;
    }

    @Column(name = "CRS_UID_LUPD", length = 35)
    public String getCrsUidLupd() {
        return this.crsUidLupd;
    }

    public void setCrsUidLupd(String crsUidLupd) {
        this.crsUidLupd = crsUidLupd;
    }

    @Override
    public int compareTo(TCkCreditSummary o) {
        return 0;
    }

    @Override
    public void init() {
    }
}
