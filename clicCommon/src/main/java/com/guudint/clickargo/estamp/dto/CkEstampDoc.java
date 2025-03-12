package com.guudint.clickargo.estamp.dto;

import java.util.Date;

import com.guudint.clickargo.estamp.model.TCkEstampDoc;
import com.guudint.clickargo.master.dto.CkMstServiceType;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkEstampDoc extends AbstractDTO<CkEstampDoc, TCkEstampDoc> {

    private static final long serialVersionUID = -7368768783027450493L;

    private String esdId;
    private CkMstServiceType TCkMstServiceType;
    private String esdInvId;
    private String esdInvType;
    private String esdStampStatus;
    private String esdDocId;
    private String esdFilename;
    private String esdDoc;
    private String esdDocUrl;
    private Character esdStatus;
    private Date esdDtCreate;
    private String esdUidCreate;
    private Date esdDtLupd;
    private String esdUidLupd;
    
    public static enum Status { 
    	NEW, NONE, IN_PROGRESS, FINISH, FAILED, SUCCESS
    }

    public CkEstampDoc() {
    }

    public CkEstampDoc(TCkEstampDoc entity) {
        super(entity);
    }

    public CkEstampDoc(String esdId, CkMstServiceType TCkMstServiceType, String esdInvId) {
        this.esdId = esdId;
        this.TCkMstServiceType = TCkMstServiceType;
        this.esdInvId = esdInvId;
    }

    public CkEstampDoc(String esdId, CkMstServiceType TCkMstServiceType, String esdInvId, String esdInvType, String esdStampStatus, String esdDocId, String esdFilename, String esdDoc, String esdDocUrl, Character esdStatus, Date esdDtCreate, String esdUidCreate, Date esdDtLupd, String esdUidLupd) {
        this.esdId = esdId;
        this.TCkMstServiceType = TCkMstServiceType;
        this.esdInvId = esdInvId;
        this.esdInvType = esdInvType;
        this.esdStampStatus = esdStampStatus;
        this.esdDocId = esdDocId;
        this.esdFilename = esdFilename;
        this.esdDoc = esdDoc;
        this.esdDocUrl = esdDocUrl;
        this.esdStatus = esdStatus;
        this.esdDtCreate = esdDtCreate;
        this.esdUidCreate = esdUidCreate;
        this.esdDtLupd = esdDtLupd;
        this.esdUidLupd = esdUidLupd;
    }

    public String getEsdId() {
        return this.esdId;
    }

    public void setEsdId(String esdId) {
        this.esdId = esdId;
    }

    public CkMstServiceType getTCkMstServiceType() {
        return this.TCkMstServiceType;
    }

    public void setTCkMstServiceType(CkMstServiceType TCkMstServiceType) {
        this.TCkMstServiceType = TCkMstServiceType;
    }

    public String getEsdInvId() {
        return this.esdInvId;
    }

    public void setEsdInvId(String esdInvId) {
        this.esdInvId = esdInvId;
    }

    public String getEsdInvType() {
        return this.esdInvType;
    }

    public void setEsdInvType(String esdInvType) {
        this.esdInvType = esdInvType;
    }

    public String getEsdStampStatus() {
        return this.esdStampStatus;
    }

    public void setEsdStampStatus(String esdStampStatus) {
        this.esdStampStatus = esdStampStatus;
    }

    public String getEsdDocId() {
        return this.esdDocId;
    }

    public void setEsdDocId(String esdDocId) {
        this.esdDocId = esdDocId;
    }

    public String getEsdFilename() {
        return this.esdFilename;
    }

    public void setEsdFilename(String esdFilename) {
        this.esdFilename = esdFilename;
    }

    public String getEsdDoc() {
        return this.esdDoc;
    }

    public void setEsdDoc(String esdDoc) {
        this.esdDoc = esdDoc;
    }

    public String getEsdDocUrl() {
        return this.esdDocUrl;
    }

    public void setEsdDocUrl(String esdDocUrl) {
        this.esdDocUrl = esdDocUrl;
    }

    public Character getEsdStatus() {
        return this.esdStatus;
    }

    public void setEsdStatus(Character esdStatus) {
        this.esdStatus = esdStatus;
    }

    public Date getEsdDtCreate() {
        return this.esdDtCreate;
    }

    public void setEsdDtCreate(Date esdDtCreate) {
        this.esdDtCreate = esdDtCreate;
    }

    public String getEsdUidCreate() {
        return this.esdUidCreate;
    }

    public void setEsdUidCreate(String esdUidCreate) {
        this.esdUidCreate = esdUidCreate;
    }

    public Date getEsdDtLupd() {
        return this.esdDtLupd;
    }

    public void setEsdDtLupd(Date esdDtLupd) {
        this.esdDtLupd = esdDtLupd;
    }

    public String getEsdUidLupd() {
        return this.esdUidLupd;
    }

    public void setEsdUidLupd(String esdUidLupd) {
        this.esdUidLupd = esdUidLupd;
    }

    @Override
    public int compareTo(CkEstampDoc o) {
        return 0;
    }

    @Override
    public void init() {
    }
    
}
