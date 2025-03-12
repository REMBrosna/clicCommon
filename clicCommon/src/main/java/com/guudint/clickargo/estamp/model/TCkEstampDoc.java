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

import com.guudint.clickargo.master.model.TCkMstServiceType;
import com.vcc.camelone.common.COAbstractEntity;

@Entity
@Table(name = "T_CK_ESTAMP_DOC")
public class TCkEstampDoc extends COAbstractEntity<TCkEstampDoc> {

    private static final long serialVersionUID = -7368768783027450493L;

    private String esdId;
    private TCkMstServiceType TCkMstServiceType;
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

    public TCkEstampDoc() {
    }

    public TCkEstampDoc(String esdId, TCkMstServiceType TCkMstServiceType, String esdInvId) {
        this.esdId = esdId;
        this.TCkMstServiceType = TCkMstServiceType;
        this.esdInvId = esdInvId;
    }

    public TCkEstampDoc(String esdId, TCkMstServiceType TCkMstServiceType, String esdInvId, String esdInvType, String esdStampStatus, String esdDocId, String esdFilename, String esdDoc, String esdDocUrl, Character esdStatus, Date esdDtCreate, String esdUidCreate, Date esdDtLupd, String esdUidLupd) {
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

    @Id
	@Column(name = "ESD_ID", unique = true, nullable = false, length = 35)
    public String getEsdId() {
        return this.esdId;
    }

    public void setEsdId(String esdId) {
        this.esdId = esdId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ESD_SVC")
    public TCkMstServiceType getTCkMstServiceType() {
        return this.TCkMstServiceType;
    }

    public void setTCkMstServiceType(TCkMstServiceType TCkMstServiceType) {
        this.TCkMstServiceType = TCkMstServiceType;
    }

    @Column(name = "ESD_INV_ID", length = 35)
    public String getEsdInvId() {
        return this.esdInvId;
    }

    public void setEsdInvId(String esdInvId) {
        this.esdInvId = esdInvId;
    }

    @Column(name = "ESD_INV_TYPE", length = 100)
    public String getEsdInvType() {
        return this.esdInvType;
    }

    public void setEsdInvType(String esdInvType) {
        this.esdInvType = esdInvType;
    }

    @Column(name = "ESD_STAMP_STATUS", length = 100)
    public String getEsdStampStatus() {
        return this.esdStampStatus;
    }

    public void setEsdStampStatus(String esdStampStatus) {
        this.esdStampStatus = esdStampStatus;
    }

    @Column(name = "ESD_DOC_ID", length = 100)
    public String getEsdDocId() {
        return this.esdDocId;
    }

    public void setEsdDocId(String esdDocId) {
        this.esdDocId = esdDocId;
    }

    @Column(name = "ESD_FILENAME", length = 255)
    public String getEsdFilename() {
        return this.esdFilename;
    }

    public void setEsdFilename(String esdFilename) {
        this.esdFilename = esdFilename;
    }

    @Column(name = "ESD_DOC", length = 1024)
    public String getEsdDoc() {
        return this.esdDoc;
    }

    public void setEsdDoc(String esdDoc) {
        this.esdDoc = esdDoc;
    }

    @Column(name = "ESD_DOC_URL", length = 1024)
    public String getEsdDocUrl() {
        return this.esdDocUrl;
    }

    public void setEsdDocUrl(String esdDocUrl) {
        this.esdDocUrl = esdDocUrl;
    }

    @Column(name = "ESD_STATUS", length = 1)
    public Character getEsdStatus() {
        return this.esdStatus;
    }

    public void setEsdStatus(Character esdStatus) {
        this.esdStatus = esdStatus;
    }

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ESD_DT_CREATE", length = 19)
    public Date getEsdDtCreate() {
        return this.esdDtCreate;
    }

    public void setEsdDtCreate(Date esdDtCreate) {
        this.esdDtCreate = esdDtCreate;
    }

    @Column(name = "ESD_UID_CREATE", length = 35)
    public String getEsdUidCreate() {
        return this.esdUidCreate;
    }

    public void setEsdUidCreate(String esdUidCreate) {
        this.esdUidCreate = esdUidCreate;
    }

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ESD_DT_LUPD", length = 19)
    public Date getEsdDtLupd() {
        return this.esdDtLupd;
    }

    public void setEsdDtLupd(Date esdDtLupd) {
        this.esdDtLupd = esdDtLupd;
    }

    @Column(name = "ESD_UID_LUPD", length = 35)
    public String getEsdUidLupd() {
        return this.esdUidLupd;
    }

    public void setEsdUidLupd(String esdUidLupd) {
        this.esdUidLupd = esdUidLupd;
    }

    @Override
    public int compareTo(TCkEstampDoc o) {
        return 0;
    }

    @Override
    public void init() {
    }
    
}
