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
@Table(name = "T_CK_MST_ESTAMP_METHOD")
public class TCkMstEstampMethod extends COAbstractEntity<TCkMstEstampMethod> {

    private static final long serialVersionUID = 6751239185144820542L;

    private String esmId;
    private String esmName;
    private String esmDescription;
    private Character esmStatus;
    private Date esmDtCreate;
    private String esmUidCreate;
    private Date esmDtLupd;
    private String esmUidLupd;

    public TCkMstEstampMethod() {
    }

    public TCkMstEstampMethod(String esmId, String esmName) {
        this.esmId = esmId;
        this.esmName = esmName;
    }

    public TCkMstEstampMethod(String esmId, String esmName, String esmDescription, Character esmStatus, Date esmDtCreate, String esmUidCreate, Date esmDtLupd, String esmUidLupd) {
        this.esmId = esmId;
        this.esmName = esmName;
        this.esmDescription = esmDescription;
        this.esmStatus = esmStatus;
        this.esmDtCreate = esmDtCreate;
        this.esmUidCreate = esmUidCreate;
        this.esmDtLupd = esmDtLupd;
        this.esmUidLupd = esmUidLupd;
    }

    @Id
	@Column(name = "ESM_ID", unique = true, nullable = false, length = 35)
    public String getEsmId() {
        return this.esmId;
    }

    public void setEsmId(String esmId) {
        this.esmId = esmId;
    }

    @Column(name = "ESM_NAME", nullable = false, length = 100)
    public String getEsmName() {
        return this.esmName;
    }

    public void setEsmName(String esmName) {
        this.esmName = esmName;
    }

    @Column(name = "ESM_DESCRIPTION", nullable = false, length = 255)
    public String getEsmDescription() {
        return this.esmDescription;
    }

    public void setEsmDescription(String esmDescription) {
        this.esmDescription = esmDescription;
    }

    @Column(name = "ESM_STATUS", length = 1)
    public Character getEsmStatus() {
        return this.esmStatus;
    }

    public void setEsmStatus(Character esmStatus) {
        this.esmStatus = esmStatus;
    }

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ESM_DT_CREATE", length = 19)
    public Date getEsmDtCreate() {
        return this.esmDtCreate;
    }

    public void setEsmDtCreate(Date esmDtCreate) {
        this.esmDtCreate = esmDtCreate;
    }

    @Column(name = "ESM_UID_CREATE", length = 35)
    public String getEsmUidCreate() {
        return this.esmUidCreate;
    }

    public void setEsmUidCreate(String esmUidCreate) {
        this.esmUidCreate = esmUidCreate;
    }

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ESM_DT_LUPD", length = 19)
    public Date getEsmDtLupd() {
        return this.esmDtLupd;
    }

    public void setEsmDtLupd(Date esmDtLupd) {
        this.esmDtLupd = esmDtLupd;
    }

    @Column(name = "ESM_UID_LUPD", length = 35)
    public String getEsmUidLupd() {
        return this.esmUidLupd;
    }

    public void setEsmUidLupd(String esmUidLupd) {
        this.esmUidLupd = esmUidLupd;
    }

    @Override
    public int compareTo(TCkMstEstampMethod o) {
        return 0;
    }

    @Override
    public void init() {
    }
    
}
