package com.guudint.clickargo.master.dto;

import java.util.Date;

import com.guudint.clickargo.master.model.TCkMstDoDocAttachState;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkMstDoDocAttachState extends AbstractDTO<CkMstDoDocAttachState, TCkMstDoDocAttachState> {

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

    public CkMstDoDocAttachState() {
    }

    public CkMstDoDocAttachState(TCkMstDoDocAttachState entity) {
		super(entity);
	}

    public CkMstDoDocAttachState(String doattId, String doattName) {
        this.doattId = doattId;
        this.doattName = doattName;
    }

    public CkMstDoDocAttachState(String doattId, String doattName, String doattDesc, String doattDescOth, Character doattStatus, Date doattDtCreate, String doattUidCreate, Date doattDtLupd, String doattUidLupd) {
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

    public String getDoattId() {
        return this.doattId;
    }

    public void setDoattId(String doattId) {
        this.doattId = doattId;
    }

    public String getDoattName() {
        return this.doattName;
    }

    public void setDoattName(String doattName) {
        this.doattName = doattName;
    }

    public String getDoattDesc() {
        return this.doattDesc;
    }

    public void setDoattDesc(String doattDesc) {
        this.doattDesc = doattDesc;
    }

    public String getDoattDescOth() {
        return this.doattDescOth;
    }

    public void setDoattDescOth(String doattDescOth) {
        this.doattDescOth = doattDescOth;
    }

    public Character getDoattStatus() {
        return this.doattStatus;
    }

    public void setDoattStatus(Character doattStatus) {
        this.doattStatus = doattStatus;
    }

    public Date getDoattDtCreate() {
        return this.doattDtCreate;
    }

    public void setDoattDtCreate(Date doattDtCreate) {
        this.doattDtCreate = doattDtCreate;
    }

    public String getDoattUidCreate() {
        return this.doattUidCreate;
    }

    public void setDoattUidCreate(String doattUidCreate) {
        this.doattUidCreate = doattUidCreate;
    }

    public Date getDoattDtLupd() {
        return this.doattDtLupd;
    }

    public void setDoattDtLupd(Date doattDtLupd) {
        this.doattDtLupd = doattDtLupd;
    }

    public String getDoattUidLupd() {
        return this.doattUidLupd;
    }

    public void setDoattUidLupd(String doattUidLupd) {
        this.doattUidLupd = doattUidLupd;
    }

    @Override
    public int compareTo(CkMstDoDocAttachState o) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub
    }
    
}
