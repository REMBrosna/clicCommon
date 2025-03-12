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
@Table(name = "T_CK_MST_JOURNAL_TXN_TYPE")
public class TCkMstJournalTxnType extends COAbstractEntity<TCkMstJournalTxnType> {

    private static final long serialVersionUID = -113774920317902403L;

    private String jttId;
    private String jttName;
    private String jttDesc;
    private String jttDescOth;
    private Character jttStatus;
    private Date jttDtCreate;
    private String jttUidCreate;
    private Date jttDtLupd;
    private String jttUidLupd;

    public TCkMstJournalTxnType() {
    }

    public TCkMstJournalTxnType(String jttId) {
        this.jttId = jttId;
    }

    public TCkMstJournalTxnType(String jttId, String jttName) {
        this.jttId = jttId;
        this.jttName = jttName;
    }

    public TCkMstJournalTxnType(String jttId, String jttName, String jttDesc, String jttDescOth, Character jttStatus, Date jttDtCreate, String jttUidCreate, Date jttDtLupd, String jttUidLupd) {
        this.jttId = jttId;
        this.jttName = jttName;
        this.jttDesc = jttDesc;
        this.jttDescOth = jttDescOth;
        this.jttStatus = jttStatus;
        this.jttDtCreate = jttDtCreate;
        this.jttUidCreate = jttUidCreate;
        this.jttDtLupd = jttDtLupd;
        this.jttUidLupd = jttUidLupd;
    }

    @Id
	@Column(name = "JTT_ID", unique = true, nullable = false, length = 35)
    public String getJttId() {
        return this.jttId;
    }

    public void setJttId(String jttId) {
        this.jttId = jttId;
    }

    @Column(name = "JTT_NAME", nullable = false, length = 100)
    public String getJttName() {
        return this.jttName;
    }

    public void setJttName(String jttName) {
        this.jttName = jttName;
    }

    @Column(name = "JTT_DESC")
    public String getJttDesc() {
        return this.jttDesc;
    }

    public void setJttDesc(String jttDesc) {
        this.jttDesc = jttDesc;
    }

    @Column(name = "JTT_DESC_OTH", length = 512)
    public String getJttDescOth() {
        return this.jttDescOth;
    }

    public void setJttDescOth(String jttDescOth) {
        this.jttDescOth = jttDescOth;
    }

    @Column(name = "JTT_STATUS", length = 1)
    public Character getJttStatus() {
        return this.jttStatus;
    }

    public void setJttStatus(Character jttStatus) {
        this.jttStatus = jttStatus;
    }

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "JTT_DT_CREATE", length = 19)
    public Date getJttDtCreate() {
        return this.jttDtCreate;
    }

    public void setJttDtCreate(Date jttDtCreate) {
        this.jttDtCreate = jttDtCreate;
    }

    @Column(name = "JTT_UID_CREATE", length = 35)
    public String getJttUidCreate() {
        return this.jttUidCreate;
    }

    public void setJttUidCreate(String jttUidCreate) {
        this.jttUidCreate = jttUidCreate;
    }

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "JTT_DT_LUPD", length = 19)
    public Date getJttDtLupd() {
        return this.jttDtLupd;
    }

    public void setJttDtLupd(Date jttDtLupd) {
        this.jttDtLupd = jttDtLupd;
    }

    @Column(name = "JTT_UID_LUPD", length = 35)
    public String getJttUidLupd() {
        return this.jttUidLupd;
    }

    public void setJttUidLupd(String jttUidLupd) {
        this.jttUidLupd = jttUidLupd;
    }
    
    @Override
    public int compareTo(TCkMstJournalTxnType o) {
        return 0;
    }

    @Override
    public void init() {
    }
    
}
