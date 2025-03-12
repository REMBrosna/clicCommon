package com.guudint.clickargo.journal.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.guudint.clickargo.journal.model.TCkCreditJournal;
import com.guudint.clickargo.master.dto.CkMstJournalTxnType;
import com.guudint.clickargo.master.dto.CkMstServiceType;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.common.dto.AbstractDTO;
import com.vcc.camelone.master.dto.MstCurrency;

public class CkCreditJournal extends AbstractDTO<CkCreditJournal, TCkCreditJournal> {

	private static final long serialVersionUID = 1L;

	private String cjnId;
	private CkMstServiceType TCkMstServiceType;
	private CoreAccn TCoreAccn;
	private CkMstJournalTxnType TCkMstJournalTxnType;
	private String cjnTxnRef;
	private BigDecimal cjnReserve;
	private BigDecimal cjnUtilized;
	private MstCurrency TMstCurrency;
	private Character cjnStatus;
	private Date cjnDtCreate;
	private String cjnUidCreate;
	private Date cjnDtLupd;
	private String cjnUidLupd;

	private BigDecimal balance;

	public CkCreditJournal() {
	}

	public CkCreditJournal(TCkCreditJournal entity) {
		super(entity);
	}

	public CkCreditJournal(String cjnId, CkMstServiceType TCkMstServiceType, CoreAccn TCoreAccn) {
		this.cjnId = cjnId;
		this.TCkMstServiceType = TCkMstServiceType;
		this.TCoreAccn = TCoreAccn;
	}

	public CkCreditJournal(String cjnId, CkMstServiceType TCkMstServiceType, CoreAccn TCoreAccn,
			CkMstJournalTxnType TCkMstJournalTxnType, String cjnTxnRef, BigDecimal cjnReserve, BigDecimal cjnUtilized,
			MstCurrency TMstCurrency, Character cjnStatus, Date cjnDtCreate, String cjnUidCreate, Date cjnDtLupd,
			String cjnUidLupd) {
		this.cjnId = cjnId;
		this.TCkMstServiceType = TCkMstServiceType;
		this.TCoreAccn = TCoreAccn;
		this.TCkMstJournalTxnType = TCkMstJournalTxnType;
		this.cjnTxnRef = cjnTxnRef;
		this.cjnReserve = cjnReserve;
		this.cjnUtilized = cjnUtilized;
		this.TMstCurrency = TMstCurrency;
		this.cjnStatus = cjnStatus;
		this.cjnDtCreate = cjnDtCreate;
		this.cjnUidCreate = cjnUidCreate;
		this.cjnDtLupd = cjnDtLupd;
		this.cjnUidLupd = cjnUidLupd;
	}

	public String getCjnId() {
		return this.cjnId;
	}

	public void setCjnId(String cjnId) {
		this.cjnId = cjnId;
	}

	public CkMstServiceType getTCkMstServiceType() {
		return this.TCkMstServiceType;
	}

	public void setTCkMstServiceType(CkMstServiceType TCkMstServiceType) {
		this.TCkMstServiceType = TCkMstServiceType;
	}

	public CoreAccn getTCoreAccn() {
		return this.TCoreAccn;
	}

	public void setTCoreAccn(CoreAccn TCoreAccn) {
		this.TCoreAccn = TCoreAccn;
	}

	public CkMstJournalTxnType getTCkMstJournalTxnType() {
		return this.TCkMstJournalTxnType;
	}

	public void setTCkMstJournalTxnType(CkMstJournalTxnType TCkMstJournalTxnType) {
		this.TCkMstJournalTxnType = TCkMstJournalTxnType;
	}

	public String getCjnTxnRef() {
		return this.cjnTxnRef;
	}

	public void setCjnTxnRef(String cjnTxnRef) {
		this.cjnTxnRef = cjnTxnRef;
	}

	public BigDecimal getCjnReserve() {
		return this.cjnReserve;
	}

	public void setCjnReserve(BigDecimal cjnReserve) {
		this.cjnReserve = cjnReserve;
	}

	public BigDecimal getCjnUtilized() {
		return this.cjnUtilized;
	}

	public void setCjnUtilized(BigDecimal cjnUtilized) {
		this.cjnUtilized = cjnUtilized;
	}

	public MstCurrency getTMstCurrency() {
		return this.TMstCurrency;
	}

	public void setTMstCurrency(MstCurrency TMstCurrency) {
		this.TMstCurrency = TMstCurrency;
	}

	public Character getCjnStatus() {
		return this.cjnStatus;
	}

	public void setCjnStatus(Character cjnStatus) {
		this.cjnStatus = cjnStatus;
	}

	public Date getCjnDtCreate() {
		return this.cjnDtCreate;
	}

	public void setCjnDtCreate(Date cjnDtCreate) {
		this.cjnDtCreate = cjnDtCreate;
	}

	public String getCjnUidCreate() {
		return this.cjnUidCreate;
	}

	public void setCjnUidCreate(String cjnUidCreate) {
		this.cjnUidCreate = cjnUidCreate;
	}

	public Date getCjnDtLupd() {
		return this.cjnDtLupd;
	}

	public void setCjnDtLupd(Date cjnDtLupd) {
		this.cjnDtLupd = cjnDtLupd;
	}

	public String getCjnUidLupd() {
		return this.cjnUidLupd;
	}

	public void setCjnUidLupd(String cjnUidLupd) {
		this.cjnUidLupd = cjnUidLupd;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@Override
	public int compareTo(CkCreditJournal o) {
		return 0;
	}

	@Override
	public void init() {
	}

}
