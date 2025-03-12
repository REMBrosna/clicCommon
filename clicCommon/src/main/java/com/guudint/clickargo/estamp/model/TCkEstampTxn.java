package com.guudint.clickargo.estamp.model;

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

import com.guudint.clickargo.master.model.TCkMstEstampMethod;
import com.vcc.camelone.common.COAbstractEntity;

@Entity
@Table(name = "T_CK_ESTAMP_TXN")
public class TCkEstampTxn extends COAbstractEntity<TCkEstampTxn> {

    private static final long serialVersionUID = 6751239185144820542L;

    private String estId;
    private TCkMstEstampMethod TCkMstEstampMethod;
    private String estReq;
    private Date estDtReq;
    private String estResp;
    private Date estDtResp;
    private Short estRespCode;
    private Character estStatus;
    private Date estDtCreate;
    private String estUidCreate;
    private Date estDtLupd;
    private String estUidLupd;

    public TCkEstampTxn() {
    }

    public TCkEstampTxn(String estId, TCkMstEstampMethod TCkMstEstampMethod) {
        this.estId = estId;
        this.TCkMstEstampMethod = TCkMstEstampMethod;
    }

    public TCkEstampTxn(String estId, TCkMstEstampMethod TCkMstEstampMethod, String estReq, Date estDtReq, String estResp, Date estDtResp, Short estRespCode, Character estStatus, Date estDtCreate, String estUidCreate, Date estDtLupd, String estUidLupd) {
        this.estId = estId;
        this.TCkMstEstampMethod = TCkMstEstampMethod;
        this.estReq = estReq;
        this.estDtReq = estDtReq;
        this.estResp = estResp;
        this.estDtResp = estDtResp;
        this.estRespCode = estRespCode;
        this.estStatus = estStatus;
        this.estDtCreate = estDtCreate;
        this.estUidCreate = estUidCreate;
        this.estDtLupd = estDtLupd;
        this.estUidLupd = estUidLupd;
    }

    @Id
	@Column(name = "EST_ID", unique = true, nullable = false, length = 35)
    public String getEstId() {
        return this.estId;
    }

    public void setEstId(String estId) {
        this.estId = estId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "EST_METHOD")
    public TCkMstEstampMethod getTCkMstEstampMethod() {
        return this.TCkMstEstampMethod;
    }

    public void setTCkMstEstampMethod(TCkMstEstampMethod TCkMstEstampMethod) {
        this.TCkMstEstampMethod = TCkMstEstampMethod;
    }

    @Column(name = "EST_REQ", length = 16777215)
    public String getEstReq() {
        return this.estReq;
    }

    public void setEstReq(String estReq) {
        this.estReq = estReq;
    }

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EST_DT_REQ", length = 19)
    public Date getEstDtReq() {
        return this.estDtReq;
    }

    public void setEstDtReq(Date estDtReq) {
        this.estDtReq = estDtReq;
    }

    @Column(name = "EST_RESP", length = 16777215)
    public String getEstResp() {
        return this.estResp;
    }

    public void setEstResp(String estResp) {
        this.estResp = estResp;
    }

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EST_DT_RESP", length = 19)
    public Date getEstDtResp() {
        return this.estDtResp;
    }

    public void setEstDtResp(Date estDtResp) {
        this.estDtResp = estDtResp;
    }

    @Column(name = "EST_RESP_CODE")
    public Short getEstRespCode() {
        return this.estRespCode;
    }

    public void setEstRespCode(Short estRespCode) {
        this.estRespCode = estRespCode;
    }

    @Column(name = "EST_STATUS", length = 1)
    public Character getEstStatus() {
        return this.estStatus;
    }

    public void setEstStatus(Character estStatus) {
        this.estStatus = estStatus;
    }

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EST_DT_CREATE", length = 19)
    public Date getEstDtCreate() {
        return this.estDtCreate;
    }

    public void setEstDtCreate(Date estDtCreate) {
        this.estDtCreate = estDtCreate;
    }

    @Column(name = "EST_UID_CREATE", length = 35)
    public String getEstUidCreate() {
        return this.estUidCreate;
    }

    public void setEstUidCreate(String estUidCreate) {
        this.estUidCreate = estUidCreate;
    }

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EST_DT_LUPD", length = 19)
    public Date getEstDtLupd() {
        return this.estDtLupd;
    }

    public void setEstDtLupd(Date estDtLupd) {
        this.estDtLupd = estDtLupd;
    }

    @Column(name = "EST_UID_LUPD", length = 35)
    public String getEstUidLupd() {
        return this.estUidLupd;
    }

    public void setEstUidLupd(String estUidLupd) {
        this.estUidLupd = estUidLupd;
    }

    @Override
    public int compareTo(TCkEstampTxn o) {
        return 0;
    }

    @Override
    public void init() {
    }
    
}
