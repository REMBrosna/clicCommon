package com.guudint.clickargo.master.dto;

import java.util.Date;

import com.guudint.clickargo.master.model.TCkMstEstampMethod;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkMstEstampMethod extends AbstractDTO<CkMstEstampMethod, TCkMstEstampMethod> {

    private static final long serialVersionUID = 6751239185144820542L;

    private String esmId;
    private String esmName;
    private String esmDescription;
    private Character esmStatus;
    private Date esmDtCreate;
    private String esmUidCreate;
    private Date esmDtLupd;
    private String esmUidLupd;

    public CkMstEstampMethod() {
    }

    public CkMstEstampMethod(TCkMstEstampMethod entity) {
        super(entity);
    }

    public CkMstEstampMethod(String esmId, String esmName) {
        this.esmId = esmId;
        this.esmName = esmName;
    }

    public CkMstEstampMethod(String esmId, String esmName, String esmDescription, Character esmStatus, Date esmDtCreate, String esmUidCreate, Date esmDtLupd, String esmUidLupd) {
        this.esmId = esmId;
        this.esmName = esmName;
        this.esmDescription = esmDescription;
        this.esmStatus = esmStatus;
        this.esmDtCreate = esmDtCreate;
        this.esmUidCreate = esmUidCreate;
        this.esmDtLupd = esmDtLupd;
        this.esmUidLupd = esmUidLupd;
    }

    public String getEsmId() {
        return this.esmId;
    }

    public void setEsmId(String esmId) {
        this.esmId = esmId;
    }

    public String getEsmName() {
        return this.esmName;
    }

    public void setEsmName(String esmName) {
        this.esmName = esmName;
    }

    public String getEsmDescription() {
        return this.esmDescription;
    }

    public void setEsmDescription(String esmDescription) {
        this.esmDescription = esmDescription;
    }

    public Character getEsmStatus() {
        return this.esmStatus;
    }

    public void setEsmStatus(Character esmStatus) {
        this.esmStatus = esmStatus;
    }

    public Date getEsmDtCreate() {
        return this.esmDtCreate;
    }

    public void setEsmDtCreate(Date esmDtCreate) {
        this.esmDtCreate = esmDtCreate;
    }

    public String getEsmUidCreate() {
        return this.esmUidCreate;
    }

    public void setEsmUidCreate(String esmUidCreate) {
        this.esmUidCreate = esmUidCreate;
    }

    public Date getEsmDtLupd() {
        return this.esmDtLupd;
    }

    public void setEsmDtLupd(Date esmDtLupd) {
        this.esmDtLupd = esmDtLupd;
    }

    public String getEsmUidLupd() {
        return this.esmUidLupd;
    }

    public void setEsmUidLupd(String esmUidLupd) {
        this.esmUidLupd = esmUidLupd;
    }

    @Override
    public int compareTo(CkMstEstampMethod o) {
        return 0;
    }

    @Override
    public void init() {
    }
    
}
