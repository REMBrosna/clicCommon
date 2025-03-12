package com.guudint.clickargo.journal.model;

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

import com.guudint.clickargo.master.model.TCkMstJournalTxnType;
import com.guudint.clickargo.master.model.TCkMstServiceType;
import com.vcc.camelone.ccm.model.TCoreAccn;
import com.vcc.camelone.common.COAbstractEntity;
import com.vcc.camelone.master.model.TMstCurrency;

@Entity
@Table(name = "T_CK_CREDIT_JOURNAL")
public class TCkCreditJournal extends COAbstractEntity<TCkCreditJournal> {
    
    private static final long serialVersionUID = 1L;

    private String cjnId;
    private TCkMstServiceType TCkMstServiceType;
    private TCoreAccn TCoreAccn;
    private TCkMstJournalTxnType TCkMstJournalTxnType;
    private String cjnTxnRef;
    private BigDecimal cjnReserve;
    private BigDecimal cjnUtilized;
    private TMstCurrency TMstCurrency;
    private Character cjnStatus;
    private Date cjnDtCreate;
    private String cjnUidCreate;
    private Date cjnDtLupd;
    private String cjnUidLupd;

    public TCkCreditJournal() {
    }

    public TCkCreditJournal(String cjnId, TCkMstServiceType TCkMstServiceType, TCoreAccn TCoreAccn) {
        this.cjnId = cjnId;
        this.TCkMstServiceType = TCkMstServiceType;
        this.TCoreAccn = TCoreAccn;
    }

    public TCkCreditJournal(String cjnId, TCkMstServiceType TCkMstServiceType, TCoreAccn TCoreAccn, TCkMstJournalTxnType TCkMstJournalTxnType, String cjnTxnRef, BigDecimal cjnReserve, BigDecimal cjnUtilized, TMstCurrency TMstCurrency, Character cjnStatus, Date cjnDtCreate, String cjnUidCreate, Date cjnDtLupd, String cjnUidLupd) {
        this.cjnId = cjnId;
        this.TCkMstServiceType = TCkMstServiceType;
        this.TCoreAccn = TCoreAccn;
        this.TCkMstJournalTxnType = TCkMstJournalTxnType;
        this.cjnTxnRef = cjnTxnRef;
        this.cjnReserve = cjnReserve;
        this.cjnUtilized = cjnUtilized;
        this.TMstCurrency = TMstCurrency;
        this.cjnStatus = cjnStatus;
        this.cjnDtCreate = cjnDtCreate;
        this.cjnUidCreate = cjnUidCreate;
        this.cjnDtLupd = cjnDtLupd;
        this.cjnUidLupd = cjnUidLupd;
    }

    @Id
	@Column(name = "CJN_ID", unique = true, nullable = false, length = 35)
    public String getCjnId() {
        return this.cjnId;
    }

    public void setCjnId(String cjnId) {
        this.cjnId = cjnId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CJN_SERVICE_TYPE")
    public TCkMstServiceType getTCkMstServiceType() {
        return this.TCkMstServiceType;
    }

    public void setTCkMstServiceType(TCkMstServiceType TCkMstServiceType) {
        this.TCkMstServiceType = TCkMstServiceType;
    }

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CJN_COMPANY")
    public TCoreAccn getTCoreAccn() {
        return this.TCoreAccn;
    }

    public void setTCoreAccn(TCoreAccn TCoreAccn) {
        this.TCoreAccn = TCoreAccn;
    }

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CJN_TXN_TYPE")
    public TCkMstJournalTxnType getTCkMstJournalTxnType() {
        return this.TCkMstJournalTxnType;
    }

    public void setTCkMstJournalTxnType(TCkMstJournalTxnType TCkMstJournalTxnType) {
        this.TCkMstJournalTxnType = TCkMstJournalTxnType;
    }

    @Column(name = "CJN_TXN_REF")
    public String getCjnTxnRef() {
        return this.cjnTxnRef;
    }

    public void setCjnTxnRef(String cjnTxnRef) {
        this.cjnTxnRef = cjnTxnRef;
    }

    @Column(name = "CJN_RESERVE", precision = 15, scale = 2)
    public BigDecimal getCjnReserve() {
        return this.cjnReserve;
    }

    public void setCjnReserve(BigDecimal cjnReserve) {
        this.cjnReserve = cjnReserve;
    }

    @Column(name = "CJN_UTILIZED", precision = 15, scale = 2)
    public BigDecimal getCjnUtilized() {
        return this.cjnUtilized;
    }

    public void setCjnUtilized(BigDecimal cjnUtilized) {
        this.cjnUtilized = cjnUtilized;
    }

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CJN_CCY")
    public TMstCurrency getTMstCurrency() {
        return this.TMstCurrency;
    }

    public void setTMstCurrency(TMstCurrency TMstCurrency) {
        this.TMstCurrency = TMstCurrency;
    }

    @Column(name = "CJN_STATUS", length = 1)
    public Character getCjnStatus() {
        return this.cjnStatus;
    }

    public void setCjnStatus(Character cjnStatus) {
        this.cjnStatus = cjnStatus;
    }

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CJN_DT_CREATE", length = 19)
    public Date getCjnDtCreate() {
        return this.cjnDtCreate;
    }

    public void setCjnDtCreate(Date cjnDtCreate) {
        this.cjnDtCreate = cjnDtCreate;
    }

    @Column(name = "CJN_UID_CREATE", length = 35)
    public String getCjnUidCreate() {
        return this.cjnUidCreate;
    }

    public void setCjnUidCreate(String cjnUidCreate) {
        this.cjnUidCreate = cjnUidCreate;
    }

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CJN_DT_LUPD", length = 19)
    public Date getCjnDtLupd() {
        return this.cjnDtLupd;
    }

    public void setCjnDtLupd(Date cjnDtLupd) {
        this.cjnDtLupd = cjnDtLupd;
    }

    @Column(name = "CJN_UID_LUPD", length = 35)
    public String getCjnUidLupd() {
        return this.cjnUidLupd;
    }

    public void setCjnUidLupd(String cjnUidLupd) {
        this.cjnUidLupd = cjnUidLupd;
    }

    @Override
    public int compareTo(TCkCreditJournal o) {
        return 0;
    }

    @Override
    public void init() {
    }

}
