package com.guudint.clickargo.credit.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.guudint.clickargo.credit.model.TCkCreditSummaryMonth;
import com.guudint.clickargo.master.dto.CkMstServiceType;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.common.dto.AbstractDTO;
import com.vcc.camelone.master.dto.MstCurrency;

public class CkCreditSummaryMonth extends AbstractDTO<CkCreditSummaryMonth, TCkCreditSummaryMonth> {

    private static final long serialVersionUID = 1L;

	private CkCreditSummaryMonthId id;
	private CkMstServiceType TCkMstServiceType;
	private CoreAccn TCoreAccn;
	private MstCurrency TMstCurrency;
	private BigDecimal crsmAmt;
	private BigDecimal crsmReserve;
	private BigDecimal crsmUtilized;
	private BigDecimal crsmBalance;
	private Character crsmStatus;
	private Date crsmDtCreate;
	private String crsmUidCreate;
	private Date crsmDtLupd;
	private String crsmUidLupd;

    public CkCreditSummaryMonth() {
    }

    public CkCreditSummaryMonth(TCkCreditSummaryMonth entity) {
        super(entity);
    }

    public CkCreditSummaryMonth(CkCreditSummaryMonthId id, CoreAccn TCoreAccn) {
		this.id = id;
		this.TCoreAccn = TCoreAccn;
	}

	public CkCreditSummaryMonth(CkCreditSummaryMonthId id, CkMstServiceType TCkMstServiceType, CoreAccn TCoreAccn,
			MstCurrency TMstCurrency, BigDecimal crsmAmt, BigDecimal crsmReserve, BigDecimal crsmUtilized, BigDecimal crsmBalance,
			Character crsmStatus, Date crsmDtCreate, String crsmUidCreate, Date crsmDtLupd, String crsmUidLupd) {
		this.id = id;
		this.TCkMstServiceType = TCkMstServiceType;
		this.TCoreAccn = TCoreAccn;
		this.TMstCurrency = TMstCurrency;
		this.crsmAmt = crsmAmt;
		this.crsmReserve = crsmReserve;
		this.crsmUtilized = crsmUtilized;
		this.crsmBalance = crsmBalance;
		this.crsmStatus = crsmStatus;
		this.crsmDtCreate = crsmDtCreate;
		this.crsmUidCreate = crsmUidCreate;
		this.crsmDtLupd = crsmDtLupd;
		this.crsmUidLupd = crsmUidLupd;
	}

    public CkCreditSummaryMonthId getId() {
        return this.id;
    }

    public void setId(CkCreditSummaryMonthId id) {
        this.id = id;
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

    public MstCurrency getTMstCurrency() {
        return this.TMstCurrency;
    }

    public void setTMstCurrency(MstCurrency TMstCurrency) {
        this.TMstCurrency = TMstCurrency;
    }

    public BigDecimal getCrsmAmt() {
        return this.crsmAmt;
    }

    public void setCrsmAmt(BigDecimal crsmAmt) {
        this.crsmAmt = crsmAmt;
    }

    public BigDecimal getCrsmReserve() {
        return this.crsmReserve;
    }

    public void setCrsmReserve(BigDecimal crsmReserve) {
        this.crsmReserve = crsmReserve;
    }

    public BigDecimal getCrsmUtilized() {
        return this.crsmUtilized;
    }

    public void setCrsmUtilized(BigDecimal crsmUtilized) {
        this.crsmUtilized = crsmUtilized;
    }

    public BigDecimal getCrsmBalance() {
        return this.crsmBalance;
    }

    public void setCrsmBalance(BigDecimal crsmBalance) {
        this.crsmBalance = crsmBalance;
    }

    public Character getCrsmStatus() {
        return this.crsmStatus;
    }

    public void setCrsmStatus(Character crsmStatus) {
        this.crsmStatus = crsmStatus;
    }

    public Date getCrsmDtCreate() {
        return this.crsmDtCreate;
    }

    public void setCrsmDtCreate(Date crsmDtCreate) {
        this.crsmDtCreate = crsmDtCreate;
    }

    public String getCrsmUidCreate() {
        return this.crsmUidCreate;
    }

    public void setCrsmUidCreate(String crsmUidCreate) {
        this.crsmUidCreate = crsmUidCreate;
    }

    public Date getCrsmDtLupd() {
        return this.crsmDtLupd;
    }

    public void setCrsmDtLupd(Date crsmDtLupd) {
        this.crsmDtLupd = crsmDtLupd;
    }

    public String getCrsmUidLupd() {
        return this.crsmUidLupd;
    }

    public void setCrsmUidLupd(String crsmUidLupd) {
        this.crsmUidLupd = crsmUidLupd;
    }

    @Override
    public int compareTo(CkCreditSummaryMonth o) {
        return 0;
    }

    @Override
    public void init() {
    }
    
}
