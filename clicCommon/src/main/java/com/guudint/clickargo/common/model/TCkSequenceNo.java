package com.guudint.clickargo.common.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.vcc.camelone.common.COAbstractEntity;

@Entity
@Table(name = "T_CK_SEQUENCE_NO")
public class TCkSequenceNo extends COAbstractEntity<TCkSequenceNo> {

	private static final long serialVersionUID = 9071607606365900541L;

	private String sqnId;
	private String sqnDesc;
	private String sqnKey;
	private Integer sqnSeq;
	private Date sqnDtCreate;
	private String sqnUidCreate;
	private Date sqnDtLupd;
	private String sqnUidLupd;

	public TCkSequenceNo() {
	}

	public TCkSequenceNo(String sqnId, String sqnDesc, String sqnKey, Integer sqnSeq, Date sqnDtCreate,
			String sqnUidCreate, Date sqnDtLupd, String sqnUidLupd) {
		this.sqnId = sqnId;
		this.sqnDesc = sqnDesc;
		this.sqnKey = sqnKey;
		this.sqnSeq = sqnSeq;
		this.sqnDtCreate = sqnDtCreate;
		this.sqnUidCreate = sqnUidCreate;
		this.sqnDtLupd = sqnDtLupd;
		this.sqnUidLupd = sqnUidLupd;
	}

	@Id
	@Column(name = "SQN_ID", unique = true, nullable = false, length = 35)
	public String getSqnId() {
		return sqnId;
	}

	public void setSqnId(String sqnId) {
		this.sqnId = sqnId;
	}

	@Column(name = "SQN_DESC", length = 255)
	public String getSqnDesc() {
		return sqnDesc;
	}

	public void setSqnDesc(String sqnDesc) {
		this.sqnDesc = sqnDesc;
	}

	@Column(name = "SQN_KEY", length = 35)
	public String getSqnKey() {
		return sqnKey;
	}

	public void setSqnKey(String sqnKey) {
		this.sqnKey = sqnKey;
	}

	@Column(name = "SQN_SEQ")
	public Integer getSqnSeq() {
		return sqnSeq;
	}

	public void setSqnSeq(Integer sqnSeq) {
		this.sqnSeq = sqnSeq;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SQN_DT_CREATE", length = 19)
	public Date getSqnDtCreate() {
		return sqnDtCreate;
	}

	public void setSqnDtCreate(Date sqnDtCreate) {
		this.sqnDtCreate = sqnDtCreate;
	}

	@Column(name = "SQN_UID_CREATE", length = 35)
	public String getSqnUidCreate() {
		return sqnUidCreate;
	}

	public void setSqnUidCreate(String sqnUidCreate) {
		this.sqnUidCreate = sqnUidCreate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SQN_DT_LUPD", length = 19)
	public Date getSqnDtLupd() {
		return sqnDtLupd;
	}

	public void setSqnDtLupd(Date sqnDtLupd) {
		this.sqnDtLupd = sqnDtLupd;
	}

	@Column(name = "SQN_UID_LUPD", length = 35)
	public String getSqnUidLupd() {
		return sqnUidLupd;
	}

	public void setSqnUidLupd(String sqnUidLupd) {
		this.sqnUidLupd = sqnUidLupd;
	}

	@Override
	public int compareTo(TCkSequenceNo o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

}
