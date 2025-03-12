package com.guudint.clickargo.clicservice.model;

import com.guudint.clickargo.master.model.TCkMstServiceType;
import com.vcc.camelone.common.COAbstractEntity;
import com.vcc.camelone.master.model.TMstCurrency;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "T_CK_SVC_JOURNAL")
public class TCkSvcJournal extends COAbstractEntity<TCkSvcJournal> {
    private static final long serialVersionUID = 6798454027692776652L;

    private String svjlId;

    private Date svjlDate;

    private TCkMstServiceType svjlSvc;

    private String svjlSvcRef;

    private String svjlTxnCode;

    private String svjlDescription;

    private String svjlCoa;

    private String svjlAccnName;

    private TMstCurrency svjlCcy;

    private BigDecimal svjlDebit;

    private BigDecimal svjlCredit;

    private Character svjlStatus;

    private Date svjlDtCreate;

    private String svjlUidCreate;

    private Date svjlDtLupd;

    private String svjlUidLupd;

    public TCkSvcJournal() {
    }

    public TCkSvcJournal(String svjlId) {
        this.svjlId = svjlId;
    }

    public TCkSvcJournal(String svjlId, Date svjlDate, TCkMstServiceType svjlSvc, String svjlSvcRef, String svjlTxnCode, String svjlDescription, String svjlCoa, String svjlAccnName, TMstCurrency svjlCcy, BigDecimal svjlDebit, BigDecimal svjlCredit, Character svjlStatus, Date svjlDtCreate, String svjlUidCreate, Date svjlDtLupd, String svjlUidLupd) {
        this.svjlId = svjlId;
        this.svjlDate = svjlDate;
        this.svjlSvc = svjlSvc;
        this.svjlSvcRef = svjlSvcRef;
        this.svjlTxnCode = svjlTxnCode;
        this.svjlDescription = svjlDescription;
        this.svjlCoa = svjlCoa;
        this.svjlAccnName = svjlAccnName;
        this.svjlCcy = svjlCcy;
        this.svjlDebit = svjlDebit;
        this.svjlCredit = svjlCredit;
        this.svjlStatus = svjlStatus;
        this.svjlDtCreate = svjlDtCreate;
        this.svjlUidCreate = svjlUidCreate;
        this.svjlDtLupd = svjlDtLupd;
        this.svjlUidLupd = svjlUidLupd;
    }

    @Id
    @Column(name = "SVJL_ID", unique = true, nullable = false, length = 35)
    public String getSvjlId() {
        return svjlId;
    }

    public void setSvjlId(String svjlId) {
        this.svjlId = svjlId;
    }

    @Column(name = "SVJL_DATE")
    public Date getSvjlDate() {
        return svjlDate;
    }

    public void setSvjlDate(Date svjlDate) {
        this.svjlDate = svjlDate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SVJL_SVC")
    public TCkMstServiceType getSvjlSvc() {
        return svjlSvc;
    }

    public void setSvjlSvc(TCkMstServiceType svjlSvc) {
        this.svjlSvc = svjlSvc;
    }

    @Column(name = "SVJL_SVC_REF")
    public String getSvjlSvcRef() {
        return svjlSvcRef;
    }

    public void setSvjlSvcRef(String svjlSvcRef) {
        this.svjlSvcRef = svjlSvcRef;
    }

    @Column(name = "SVJL_TXN_CODE")
    public String getSvjlTxnCode() {
        return svjlTxnCode;
    }

    public void setSvjlTxnCode(String svjlTxnCode) {
        this.svjlTxnCode = svjlTxnCode;
    }

    @Column(name = "SVJL_DESCRIPTION", length = 512)
    public String getSvjlDescription() {
        return svjlDescription;
    }

    public void setSvjlDescription(String svjlDescription) {
        this.svjlDescription = svjlDescription;
    }

    @Column(name = "SVJL_COA")
    public String getSvjlCoa() {
        return svjlCoa;
    }

    public void setSvjlCoa(String svjlCoa) {
        this.svjlCoa = svjlCoa;
    }

    @Column(name = "SVJL_ACCN_NAME")
    public String getSvjlAccnName() {
        return svjlAccnName;
    }

    public void setSvjlAccnName(String svjlAccnName) {
        this.svjlAccnName = svjlAccnName;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SVJL_CCY")
    public TMstCurrency getSvjlCcy() {
        return svjlCcy;
    }

    public void setSvjlCcy(TMstCurrency svjlCcy) {
        this.svjlCcy = svjlCcy;
    }

    @Column(name = "SVJL_DEBIT", precision = 15, scale = 2)
    public BigDecimal getSvjlDebit() {
        return svjlDebit;
    }

    public void setSvjlDebit(BigDecimal svjlDebit) {
        this.svjlDebit = svjlDebit;
    }

    @Column(name = "SVJL_CREDIT", precision = 15, scale = 2)
    public BigDecimal getSvjlCredit() {
        return svjlCredit;
    }

    public void setSvjlCredit(BigDecimal svjlCredit) {
        this.svjlCredit = svjlCredit;
    }

    @Column(name = "SVJL_STATUS")
    public Character getSvjlStatus() {
        return svjlStatus;
    }

    public void setSvjlStatus(Character svjlStatus) {
        this.svjlStatus = svjlStatus;
    }

    @Column(name = "SVJL_DT_CREATE")
    public Date getSvjlDtCreate() {
        return svjlDtCreate;
    }

    public void setSvjlDtCreate(Date svjlDtCreate) {
        this.svjlDtCreate = svjlDtCreate;
    }

    @Column(name = "SVJL_UID_CREATE", length = 35)
    public String getSvjlUidCreate() {
        return svjlUidCreate;
    }

    public void setSvjlUidCreate(String svjlUidCreate) {
        this.svjlUidCreate = svjlUidCreate;
    }

    @Column(name = "SVJL_DT_LUPD")
    public Date getSvjlDtLupd() {
        return svjlDtLupd;
    }

    public void setSvjlDtLupd(Date svjlDtLupd) {
        this.svjlDtLupd = svjlDtLupd;
    }

    @Column(name = "SVJL_UID_LUPD", length = 35)
    public String getSvjlUidLupd() {
        return svjlUidLupd;
    }

    public void setSvjlUidLupd(String svjlUidLupd) {
        this.svjlUidLupd = svjlUidLupd;
    }

    @Override
    public void init() {

    }

    @Override
    public int compareTo(TCkSvcJournal o) {
        return 0;
    }
}