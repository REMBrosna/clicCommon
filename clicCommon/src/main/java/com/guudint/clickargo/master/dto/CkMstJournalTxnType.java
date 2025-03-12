package com.guudint.clickargo.master.dto;

import java.util.Date;

import com.guudint.clickargo.master.model.TCkMstJournalTxnType;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkMstJournalTxnType extends AbstractDTO<CkMstJournalTxnType, TCkMstJournalTxnType> {

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

    public CkMstJournalTxnType() {
    }

    public CkMstJournalTxnType(TCkMstJournalTxnType entity) {
        super(entity);
    }

    public CkMstJournalTxnType(String jttId, String jttName) {
        this.jttId = jttId;
        this.jttName = jttName;
    }

    public CkMstJournalTxnType(String jttId, String jttName, String jttDesc, String jttDescOth, Character jttStatus, Date jttDtCreate, String jttUidCreate, Date jttDtLupd, String jttUidLupd) {
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

    public String getJttId() {
        return this.jttId;
    }

    public void setJttId(String jttId) {
        this.jttId = jttId;
    }

    public String getJttName() {
        return this.jttName;
    }

    public void setJttName(String jttName) {
        this.jttName = jttName;
    }

    public String getJttDesc() {
        return this.jttDesc;
    }

    public void setJttDesc(String jttDesc) {
        this.jttDesc = jttDesc;
    }

    public String getJttDescOth() {
        return this.jttDescOth;
    }

    public void setJttDescOth(String jttDescOth) {
        this.jttDescOth = jttDescOth;
    }

    public Character getJttStatus() {
        return this.jttStatus;
    }

    public void setJttStatus(Character jttStatus) {
        this.jttStatus = jttStatus;
    }

    public Date getJttDtCreate() {
        return this.jttDtCreate;
    }

    public void setJttDtCreate(Date jttDtCreate) {
        this.jttDtCreate = jttDtCreate;
    }

    public String getJttUidCreate() {
        return this.jttUidCreate;
    }

    public void setJttUidCreate(String jttUidCreate) {
        this.jttUidCreate = jttUidCreate;
    }

    public Date getJttDtLupd() {
        return this.jttDtLupd;
    }

    public void setJttDtLupd(Date jttDtLupd) {
        this.jttDtLupd = jttDtLupd;
    }

    public String getJttUidLupd() {
        return this.jttUidLupd;
    }

    public void setJttUidLupd(String jttUidLupd) {
        this.jttUidLupd = jttUidLupd;
    }

    @Override
    public int compareTo(CkMstJournalTxnType o) {
        return 0;
    }

    @Override
    public void init() {
    }
    
}
