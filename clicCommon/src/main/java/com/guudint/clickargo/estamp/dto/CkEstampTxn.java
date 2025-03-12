package com.guudint.clickargo.estamp.dto;

import java.util.Date;

import com.guudint.clickargo.estamp.model.TCkEstampTxn;
import com.guudint.clickargo.master.dto.CkMstEstampMethod;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkEstampTxn extends AbstractDTO<CkEstampTxn, TCkEstampTxn> {

    private static final long serialVersionUID = 6751239185144820542L;

    private String estId;
    private CkMstEstampMethod TCkMstEstampMethod;
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

    public CkEstampTxn() {
    }

    public CkEstampTxn(TCkEstampTxn entity) {
        super(entity);
    }

    public CkEstampTxn(String estId, CkMstEstampMethod TCkMstEstampMethod) {
        this.estId = estId;
        this.TCkMstEstampMethod = TCkMstEstampMethod;
    }

    public CkEstampTxn(String estId, CkMstEstampMethod TCkMstEstampMethod, String estReq, Date estDtReq, String estResp, Date estDtResp, Short estRespCode, Character estStatus, Date estDtCreate, String estUidCreate, Date estDtLupd, String estUidLupd) {
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

    public String getEstId() {
        return this.estId;
    }

    public void setEstId(String estId) {
        this.estId = estId;
    }

    public CkMstEstampMethod getTCkMstEstampMethod() {
        return this.TCkMstEstampMethod;
    }

    public void setTCkMstEstampMethod(CkMstEstampMethod TCkMstEstampMethod) {
        this.TCkMstEstampMethod = TCkMstEstampMethod;
    }

    public String getEstReq() {
        return this.estReq;
    }

    public void setEstReq(String estReq) {
        this.estReq = estReq;
    }

    public Date getEstDtReq() {
        return this.estDtReq;
    }

    public void setEstDtReq(Date estDtReq) {
        this.estDtReq = estDtReq;
    }

    public String getEstResp() {
        return this.estResp;
    }

    public void setEstResp(String estResp) {
        this.estResp = estResp;
    }

    public Date getEstDtResp() {
        return this.estDtResp;
    }

    public void setEstDtResp(Date estDtResp) {
        this.estDtResp = estDtResp;
    }

    public Short getEstRespCode() {
        return this.estRespCode;
    }

    public void setEstRespCode(Short estRespCode) {
        this.estRespCode = estRespCode;
    }

    public Character getEstStatus() {
        return this.estStatus;
    }

    public void setEstStatus(Character estStatus) {
        this.estStatus = estStatus;
    }

    public Date getEstDtCreate() {
        return this.estDtCreate;
    }

    public void setEstDtCreate(Date estDtCreate) {
        this.estDtCreate = estDtCreate;
    }

    public String getEstUidCreate() {
        return this.estUidCreate;
    }

    public void setEstUidCreate(String estUidCreate) {
        this.estUidCreate = estUidCreate;
    }

    public Date getEstDtLupd() {
        return this.estDtLupd;
    }

    public void setEstDtLupd(Date estDtLupd) {
        this.estDtLupd = estDtLupd;
    }

    public String getEstUidLupd() {
        return this.estUidLupd;
    }

    public void setEstUidLupd(String estUidLupd) {
        this.estUidLupd = estUidLupd;
    }

    @Override
    public int compareTo(CkEstampTxn o) {
        return 0;
    }

    @Override
    public void init() {
    }
    
}
