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
@Table(name = "T_CK_MST_DO_DOC_ATTACH_STATE")
public class TCkMstDoDocAttachState extends COAbstractEntity<TCkMstDoDocAttachState> {
    
    private static final long serialVersionUID = -113774920317902403L;

    private String doattId;
	private String doattName;
	private String doattDesc;
	private String doattDescOth;
	private Character doattStatus;
	private Date doattDtCreate;
	private String doattUidCreate;
	private Date doattDtLupd;
	private String doattUidLupd;

    public TCkMstDoDocAttachState() {
    }

    public TCkMstDoDocAttachState(String doattId, String doattName) {
        this.doattId = doattId;
        this.doattName = doattName;
    }

    public TCkMstDoDocAttachState(String doattId, String doattName, String doattDesc, String doattDescOth, Character doattStatus, Date doattDtCreate, String doattUidCreate, Date doattDtLupd, String doattUidLupd) {
        this.doattId = doattId;
        this.doattName = doattName;
        this.doattDesc = doattDesc;
        this.doattDescOth = doattDescOth;
        this.doattStatus = doattStatus;
        this.doattDtCreate = doattDtCreate;
        this.doattUidCreate = doattUidCreate;
        this.doattDtLupd = doattDtLupd;
        this.doattUidLupd = doattUidLupd;
    }

    @Id
	@Column(name = "DOATT_ID", unique = true, nullable = false, length = 35)
    public String getDoattId() {
        return this.doattId;
    }

    public void setDoattId(String doattId) {
        this.doattId = doattId;
    }

    @Column(name = "DOATT_NAME", nullable = false, length = 100)
    public String getDoattName() {
        return this.doattName;
    }

    public void setDoattName(String doattName) {
        this.doattName = doattName;
    }

    @Column(name = "DOATT_DESC")
    public String getDoattDesc() {
        return this.doattDesc;
    }

    public void setDoattDesc(String doattDesc) {
        this.doattDesc = doattDesc;
    }

    @Column(name = "DOATT_DESC_OTH", length = 512)
    public String getDoattDescOth() {
        return this.doattDescOth;
    }

    public void setDoattDescOth(String doattDescOth) {
        this.doattDescOth = doattDescOth;
    }

    @Column(name = "DOATT_STATUS", length = 1)
    public Character getDoattStatus() {
        return this.doattStatus;
    }

    public void setDoattStatus(Character doattStatus) {
        this.doattStatus = doattStatus;
    }

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DOATT_DT_CREATE", length = 19)
    public Date getDoattDtCreate() {
        return this.doattDtCreate;
    }

    public void setDoattDtCreate(Date doattDtCreate) {
        this.doattDtCreate = doattDtCreate;
    }

    @Column(name = "DOATT_UID_CREATE", length = 35)
    public String getDoattUidCreate() {
        return this.doattUidCreate;
    }

    public void setDoattUidCreate(String doattUidCreate) {
        this.doattUidCreate = doattUidCreate;
    }

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DOATT_DT_LUPD", length = 19)
    public Date getDoattDtLupd() {
        return this.doattDtLupd;
    }

    public void setDoattDtLupd(Date doattDtLupd) {
        this.doattDtLupd = doattDtLupd;
    }

    @Column(name = "DOATT_UID_LUPD", length = 35)
    public String getDoattUidLupd() {
        return this.doattUidLupd;
    }

    public void setDoattUidLupd(String doattUidLupd) {
        this.doattUidLupd = doattUidLupd;
    }

    @Override
    public int compareTo(TCkMstDoDocAttachState o) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub
    }
}
