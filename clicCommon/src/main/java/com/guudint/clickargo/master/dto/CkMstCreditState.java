package com.guudint.clickargo.master.dto;

import java.util.Date;

import com.guudint.clickargo.master.model.TCkMstCreditState;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkMstCreditState extends AbstractDTO<CkMstCreditState, TCkMstCreditState> {

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

    public CkMstCreditState() {
    }

    public CkMstCreditState(TCkMstCreditState entity) {
        super(entity);
    }

    public CkMstCreditState(String crstId, String crstName) {
        this.crstId = crstId;
        this.crstName = crstName;
    }

    public CkMstCreditState(String crstId, String crstName, String crstDesc, String crstDescOth, Character crstStatus, Date crstDtCreate, String crstUidCreate, Date crstDtLupd, String crstUidLupd) {
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

    public String getCrstId() {
        return this.crstId;
    }

    public void setCrstId(String crstId) {
        this.crstId = crstId;
    }

    public String getCrstName() {
        return this.crstName;
    }

    public void setCrstName(String crstName) {
        this.crstName = crstName;
    }

    public String getCrstDesc() {
        return this.crstDesc;
    }

    public void setCrstDesc(String crstDesc) {
        this.crstDesc = crstDesc;
    }

    public String getCrstDescOth() {
        return this.crstDescOth;
    }

    public void setCrstDescOth(String crstDescOth) {
        this.crstDescOth = crstDescOth;
    }

    public Character getCrstStatus() {
        return this.crstStatus;
    }

    public void setCrstStatus(Character crstStatus) {
        this.crstStatus = crstStatus;
    }

    public Date getCrstDtCreate() {
        return this.crstDtCreate;
    }

    public void setCrstDtCreate(Date crstDtCreate) {
        this.crstDtCreate = crstDtCreate;
    }

    public String getCrstUidCreate() {
        return this.crstUidCreate;
    }

    public void setCrstUidCreate(String crstUidCreate) {
        this.crstUidCreate = crstUidCreate;
    }

    public Date getCrstDtLupd() {
        return this.crstDtLupd;
    }

    public void setCrstDtLupd(Date crstDtLupd) {
        this.crstDtLupd = crstDtLupd;
    }

    public String getCrstUidLupd() {
        return this.crstUidLupd;
    }

    public void setCrstUidLupd(String crstUidLupd) {
        this.crstUidLupd = crstUidLupd;
    }

    @Override
    public int compareTo(CkMstCreditState o) {
        return 0;
    }

    @Override
    public void init() {
    }
    
}
