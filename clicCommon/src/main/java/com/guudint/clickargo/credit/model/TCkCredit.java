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

import com.guudint.clickargo.master.model.TCkMstCreditState;
import com.guudint.clickargo.master.model.TCkMstServiceType;
import com.vcc.camelone.ccm.model.TCoreAccn;
import com.vcc.camelone.ccm.model.TCoreUsr;
import com.vcc.camelone.common.COAbstractEntity;
import com.vcc.camelone.master.model.TMstCurrency;

@Entity
@Table(name = "T_CK_CREDIT")
public class TCkCredit extends COAbstractEntity<TCkCredit> {
    
    private static final long serialVersionUID = 1L;
	public static final String PREFIX_ID = "CR";

    private String crId;
    private TCkMstServiceType TCkMstServiceType;
    private TCoreAccn TCoreAccn;
    private TCkMstCreditState TCkMstCreditState;
    private BigDecimal crAmt;
    private BigDecimal crTxnCap;
    private TMstCurrency TMstCurrency;
    private Date crDtStart;
    private Date crDtEnd;
    private TCoreUsr TCoreUsrVerify;
    private Date crDtVerify;
    private TCoreUsr TCoreUsrApprove;
    private Date crDtApprove;
    private TCoreUsr TCoreUsrReject;
    private Date crDtReject;
    private String crRemarks;
    private Character crStatus;
    private Date crDtCreate;
    private String crUidCreate;
    private Date crDtLupd;
    private String crUidLupd;

    public TCkCredit() {
    }

    public TCkCredit(String crId, TCkMstServiceType TCkMstServiceType, TCoreAccn TCoreAccn) {
        this.crId = crId;
        this.TCkMstServiceType = TCkMstServiceType;
        this.TCoreAccn = TCoreAccn;
    }

    public TCkCredit(String crId, TCkMstServiceType TCkMstServiceType, TCoreAccn TCoreAccn, TCkMstCreditState TCkMstCreditState, BigDecimal crAmt, BigDecimal crTxnCap, TMstCurrency TMstCurrency, Date crDtStart, Date crDtEnd, TCoreUsr TCoreUsrVerify, Date crDtVerify, TCoreUsr TCoreUsrApprove, Date crDtApprove, TCoreUsr TCoreUsrReject, Date crDtReject, String crRemarks, Character crStatus, Date crDtCreate, String crUidCreate, Date crDtLupd, String crUidLupd) {
        this.crId = crId;
        this.TCkMstServiceType = TCkMstServiceType;
        this.TCoreAccn = TCoreAccn;
        this.TCkMstCreditState = TCkMstCreditState;
        this.crAmt = crAmt;
        this.crTxnCap = crTxnCap;
        this.TMstCurrency = TMstCurrency;
        this.crDtStart = crDtStart;
        this.crDtEnd = crDtEnd;
        this.TCoreUsrVerify = TCoreUsrVerify;
        this.crDtVerify = crDtVerify;
        this.TCoreUsrApprove = TCoreUsrApprove;
        this.crDtApprove = crDtApprove;
        this.TCoreUsrReject = TCoreUsrReject;
        this.crDtReject = crDtReject;
        this.crRemarks = crRemarks;
        this.crStatus = crStatus;
        this.crDtCreate = crDtCreate;
        this.crUidCreate = crUidCreate;
        this.crDtLupd = crDtLupd;
        this.crUidLupd = crUidLupd;
    }

    @Id
	@Column(name = "CR_ID", unique = true, nullable = false, length = 35)
    public String getCrId() {
        return this.crId;
    }

    public void setCrId(String crId) {
        this.crId = crId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CR_SERVICE_TYPE")
    public TCkMstServiceType getTCkMstServiceType() {
        return this.TCkMstServiceType;
    }

    public void setTCkMstServiceType(TCkMstServiceType TCkMstServiceType) {
        this.TCkMstServiceType = TCkMstServiceType;
    }

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CR_COMPANY")
    public TCoreAccn getTCoreAccn() {
        return this.TCoreAccn;
    }

    public void setTCoreAccn(TCoreAccn TCoreAccn) {
        this.TCoreAccn = TCoreAccn;
    }

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CR_STATE")
    public TCkMstCreditState getTCkMstCreditState() {
        return this.TCkMstCreditState;
    }

    public void setTCkMstCreditState(TCkMstCreditState TCkMstCreditState) {
        this.TCkMstCreditState = TCkMstCreditState;
    }

    @Column(name = "CR_AMT", precision = 15, scale = 2)
    public BigDecimal getCrAmt() {
        return this.crAmt;
    }

    public void setCrAmt(BigDecimal crAmt) {
        this.crAmt = crAmt;
    }

    @Column(name = "CR_TXN_CAP", precision = 15, scale = 2)
    public BigDecimal getCrTxnCap() {
        return this.crTxnCap;
    }

    public void setCrTxnCap(BigDecimal crTxnCap) {
        this.crTxnCap = crTxnCap;
    }

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CR_CCY")
    public TMstCurrency getTMstCurrency() {
        return this.TMstCurrency;
    }

    public void setTMstCurrency(TMstCurrency TMstCurrency) {
        this.TMstCurrency = TMstCurrency;
    }

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CR_DT_START", length = 19)
    public Date getCrDtStart() {
        return this.crDtStart;
    }

    public void setCrDtStart(Date crDtStart) {
        this.crDtStart = crDtStart;
    }

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CR_DT_END", length = 19)
    public Date getCrDtEnd() {
        return this.crDtEnd;
    }

    public void setCrDtEnd(Date crDtEnd) {
        this.crDtEnd = crDtEnd;
    }

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CR_USR_VERIFY")
    public TCoreUsr getTCoreUsrVerify() {
        return this.TCoreUsrVerify;
    }

    public void setTCoreUsrVerify(TCoreUsr TCoreUsrVerify) {
        this.TCoreUsrVerify = TCoreUsrVerify;
    }

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CR_DT_VERIFY", length = 19)
    public Date getCrDtVerify() {
        return this.crDtVerify;
    }

    public void setCrDtVerify(Date crDtVerify) {
        this.crDtVerify = crDtVerify;
    }

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CR_USR_APPROVE")
    public TCoreUsr getTCoreUsrApprove() {
        return this.TCoreUsrApprove;
    }

    public void setTCoreUsrApprove(TCoreUsr TCoreUsrApprove) {
        this.TCoreUsrApprove = TCoreUsrApprove;
    }

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CR_DT_APPROVE", length = 19)
    public Date getCrDtApprove() {
        return this.crDtApprove;
    }

    public void setCrDtApprove(Date crDtApprove) {
        this.crDtApprove = crDtApprove;
    }

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CR_USR_REJECT")
    public TCoreUsr getTCoreUsrReject() {
        return this.TCoreUsrReject;
    }

    public void setTCoreUsrReject(TCoreUsr TCoreUsrReject) {
        this.TCoreUsrReject = TCoreUsrReject;
    }

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CR_DT_REJECT", length = 19)
    public Date getCrDtReject() {
        return this.crDtReject;
    }

    public void setCrDtReject(Date crDtReject) {
        this.crDtReject = crDtReject;
    }

    @Column(name = "CR_REMARKS")
    public String getCrRemarks() {
        return this.crRemarks;
    }

    public void setCrRemarks(String crRemarks) {
        this.crRemarks = crRemarks;
    }

    @Column(name = "CR_STATUS", length = 1)
    public Character getCrStatus() {
        return this.crStatus;
    }

    public void setCrStatus(Character crStatus) {
        this.crStatus = crStatus;
    }

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CR_DT_CREATE", length = 19)
    public Date getCrDtCreate() {
        return this.crDtCreate;
    }

    public void setCrDtCreate(Date crDtCreate) {
        this.crDtCreate = crDtCreate;
    }

    @Column(name = "CR_UID_CREATE", length = 35)
    public String getCrUidCreate() {
        return this.crUidCreate;
    }

    public void setCrUidCreate(String crUidCreate) {
        this.crUidCreate = crUidCreate;
    }

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CR_DT_LUPD", length = 19)
    public Date getCrDtLupd() {
        return this.crDtLupd;
    }

    public void setCrDtLupd(Date crDtLupd) {
        this.crDtLupd = crDtLupd;
    }

    @Column(name = "CR_UID_LUPD", length = 35)
    public String getCrUidLupd() {
        return this.crUidLupd;
    }

    public void setCrUidLupd(String crUidLupd) {
        this.crUidLupd = crUidLupd;
    }

    @Override
    public int compareTo(TCkCredit o) {
        return 0;
    }

    @Override
    public void init() {
    }
}
