package com.guudint.clickargo.master.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.vcc.camelone.common.COAbstractEntity;

@Entity
@Table(name = "T_CK_MST_CREDIT_STATE")
public class TCkMstCreditState extends COAbstractEntity<TCkMstCreditState> {

    private static final long serialVersionUID = -113774920317902403L;

    private String crstId;
    private String crstName;
    private String crstDesc;
    private String crstDescOth;
    private Character crstStatus;
    private Date crstDtCreate;
    private String crstUidCreate;
    private Date crstDtLupd;
    private String crstUidLupd;

    public TCkMstCreditState() {
    }

    public TCkMstCreditState(String crstId, String crstName) {
        this.crstId = crstId;
        this.crstName = crstName;
    }

    public TCkMstCreditState(String crstId, String crstName, String crstDesc, String crstDescOth, Character crstStatus, Date crstDtCreate, String crstUidCreate, Date crstDtLupd, String crstUidLupd) {
        this.crstId = crstId;
        this.crstName = crstName;
        this.crstDesc = crstDesc;
        this.crstDescOth = crstDescOth;
        this.crstStatus = crstStatus;
        this.crstDtCreate = crstDtCreate;
        this.crstUidCreate = crstUidCreate;
        this.crstDtLupd = crstDtLupd;
        this.crstUidLupd = crstUidLupd;
    }

    @Id
	@Column(name = "CRST_ID", unique = true, nullable = false, length = 35)
    public String getCrstId() {
        return this.crstId;
    }

    public void setCrstId(String crstId) {
        this.crstId = crstId;
    }

    @Column(name = "CRST_NAME", nullable = false, length = 100)
    public String getCrstName() {
        return this.crstName;
    }

    public void setCrstName(String crstName) {
        this.crstName = crstName;
    }

    @Column(name = "CRST_DESC")
    public String getCrstDesc() {
        return this.crstDesc;
    }

    public void setCrstDesc(String crstDesc) {
        this.crstDesc = crstDesc;
    }

    @Column(name = "CRST_DESC_OTH", length = 512)
    public String getCrstDescOth() {
        return this.crstDescOth;
    }

    public void setCrstDescOth(String crstDescOth) {
        this.crstDescOth = crstDescOth;
    }

    @Column(name = "CRST_STATUS", length = 1)
    public Character getCrstStatus() {
        return this.crstStatus;
    }

    public void setCrstStatus(Character crstStatus) {
        this.crstStatus = crstStatus;
    }

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CRST_DT_CREATE", length = 19)
    public Date getCrstDtCreate() {
        return this.crstDtCreate;
    }

    public void setCrstDtCreate(Date crstDtCreate) {
        this.crstDtCreate = crstDtCreate;
    }

    @Column(name = "CRST_UID_CREATE", length = 35)
    public String getCrstUidCreate() {
        return this.crstUidCreate;
    }

    public void setCrstUidCreate(String crstUidCreate) {
        this.crstUidCreate = crstUidCreate;
    }

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CRST_DT_LUPD", length = 19)
    public Date getCrstDtLupd() {
        return this.crstDtLupd;
    }

    public void setCrstDtLupd(Date crstDtLupd) {
        this.crstDtLupd = crstDtLupd;
    }

    @Column(name = "CRST_UID_LUPD", length = 35)
    public String getCrstUidLupd() {
        return this.crstUidLupd;
    }

    public void setCrstUidLupd(String crstUidLupd) {
        this.crstUidLupd = crstUidLupd;
    }

    @Override
    public int compareTo(TCkMstCreditState o) {
        return 0;
    }

    @Override
    public void init() {
    }
    
}
