package com.guudint.clickargo.credit.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.guudint.clickargo.credit.model.TCkCredit;
import com.guudint.clickargo.master.dto.CkMstCreditState;
import com.guudint.clickargo.master.dto.CkMstServiceType;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.ccm.dto.CoreUsr;
import com.vcc.camelone.common.dto.AbstractDTO;
import com.vcc.camelone.master.dto.MstCurrency;

public class CkCredit extends AbstractDTO<CkCredit, TCkCredit> {
    
    private static final long serialVersionUID = 1L;

    private String crId;
    private CkMstServiceType TCkMstServiceType;
    private CoreAccn TCoreAccn;
    private CkMstCreditState TCkMstCreditState;
    private BigDecimal crAmt;
    private BigDecimal crTxnCap;
    private MstCurrency TMstCurrency;
    private Date crDtStart;
    private Date crDtEnd;
    private CoreUsr TCoreUsrVerify;
    private Date crDtVerify;
    private CoreUsr TCoreUsrApprove;
    private Date crDtApprove;
    private CoreUsr TCoreUsrReject;
    private Date crDtReject;
    private String crRemarks;
    private Character crStatus;
    private Date crDtCreate;
    private String crUidCreate;
    private Date crDtLupd;
    private String crUidLupd;
    private CkCreditSummary TCkCreditSummary;

    public CkCredit() {
    }

    public CkCredit(TCkCredit entity) {
        super(entity);
    }

    public CkCredit(String crId, CkMstServiceType TCkMstServiceType, CoreAccn TCoreAccn) {
        this.crId = crId;
        this.TCkMstServiceType = TCkMstServiceType;
        this.TCoreAccn = TCoreAccn;
    }

    public CkCredit(String crId, CkMstServiceType TCkMstServiceType, CoreAccn TCoreAccn, CkMstCreditState TCkMstCreditState, BigDecimal crAmt, BigDecimal crTxnCap, MstCurrency TMstCurrency, Date crDtStart, Date crDtEnd, CoreUsr TCoreUsrVerify, Date crDtVerify, CoreUsr TCoreUsrApprove, Date crDtApprove, CoreUsr TCoreUsrReject, Date crDtReject, String crRemarks, Character crStatus, Date crDtCreate, String crUidCreate, Date crDtLupd, String crUidLupd) {
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

    public String getCrId() {
        return this.crId;
    }

    public void setCrId(String crId) {
        this.crId = crId;
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

    public CkMstCreditState getTCkMstCreditState() {
        return this.TCkMstCreditState;
    }

    public void setTCkMstCreditState(CkMstCreditState TCkMstCreditState) {
        this.TCkMstCreditState = TCkMstCreditState;
    }

    public BigDecimal getCrAmt() {
        return this.crAmt;
    }

    public void setCrAmt(BigDecimal crAmt) {
        this.crAmt = crAmt;
    }

    public BigDecimal getCrTxnCap() {
        return this.crTxnCap;
    }

    public void setCrTxnCap(BigDecimal crTxnCap) {
        this.crTxnCap = crTxnCap;
    }

    public MstCurrency getTMstCurrency() {
        return this.TMstCurrency;
    }

    public void setTMstCurrency(MstCurrency TMstCurrency) {
        this.TMstCurrency = TMstCurrency;
    }

    public Date getCrDtStart() {
        return this.crDtStart;
    }

    public void setCrDtStart(Date crDtStart) {
        this.crDtStart = crDtStart;
    }

    public Date getCrDtEnd() {
        return this.crDtEnd;
    }

    public void setCrDtEnd(Date crDtEnd) {
        this.crDtEnd = crDtEnd;
    }

    public CoreUsr getTCoreUsrVerify() {
        return this.TCoreUsrVerify;
    }

    public void setTCoreUsrVerify(CoreUsr TCoreUsrVerify) {
        this.TCoreUsrVerify = TCoreUsrVerify;
    }

    public Date getCrDtVerify() {
        return this.crDtVerify;
    }

    public void setCrDtVerify(Date crDtVerify) {
        this.crDtVerify = crDtVerify;
    }

    public CoreUsr getTCoreUsrApprove() {
        return this.TCoreUsrApprove;
    }

    public void setTCoreUsrApprove(CoreUsr TCoreUsrApprove) {
        this.TCoreUsrApprove = TCoreUsrApprove;
    }

    public Date getCrDtApprove() {
        return this.crDtApprove;
    }

    public void setCrDtApprove(Date crDtApprove) {
        this.crDtApprove = crDtApprove;
    }

    public CoreUsr getTCoreUsrReject() {
        return this.TCoreUsrReject;
    }

    public void setTCoreUsrReject(CoreUsr TCoreUsrReject) {
        this.TCoreUsrReject = TCoreUsrReject;
    }

    public Date getCrDtReject() {
        return this.crDtReject;
    }

    public void setCrDtReject(Date crDtReject) {
        this.crDtReject = crDtReject;
    }

    public String getCrRemarks() {
        return this.crRemarks;
    }

    public void setCrRemarks(String crRemarks) {
        this.crRemarks = crRemarks;
    }

    public Character getCrStatus() {
        return this.crStatus;
    }

    public void setCrStatus(Character crStatus) {
        this.crStatus = crStatus;
    }

    public Date getCrDtCreate() {
        return this.crDtCreate;
    }

    public void setCrDtCreate(Date crDtCreate) {
        this.crDtCreate = crDtCreate;
    }

    public String getCrUidCreate() {
        return this.crUidCreate;
    }

    public void setCrUidCreate(String crUidCreate) {
        this.crUidCreate = crUidCreate;
    }

    public Date getCrDtLupd() {
        return this.crDtLupd;
    }

    public void setCrDtLupd(Date crDtLupd) {
        this.crDtLupd = crDtLupd;
    }

    public String getCrUidLupd() {
        return this.crUidLupd;
    }

    public void setCrUidLupd(String crUidLupd) {
        this.crUidLupd = crUidLupd;
    }

    public CkCreditSummary getTCkCreditSummary() {
        return this.TCkCreditSummary;
    }

    public void setTCkCreditSummary(CkCreditSummary TCkCreditSummary) {
        this.TCkCreditSummary = TCkCreditSummary;
    }

    @Override
    public int compareTo(CkCredit o) {
        return 0;
    }

    @Override
    public void init() {
    }
}
