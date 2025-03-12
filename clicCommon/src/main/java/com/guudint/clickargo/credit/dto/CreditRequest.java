package com.guudint.clickargo.credit.dto;

import com.guudint.clickargo.master.dto.CkMstServiceType;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.common.COAbstractEntity;
import com.vcc.camelone.master.dto.MstCurrency;;

public class CreditRequest extends COAbstractEntity<CreditRequest> {
    
    private static final long serialVersionUID = 1L;

    private String id;
    private CkMstServiceType serviceType;
    private CoreAccn accn;
    private MstCurrency ccy;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CkMstServiceType getServiceType() {
        return this.serviceType;
    }

    public void setServiceType(CkMstServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public CoreAccn getAccn() {
        return this.accn;
    }

    public void setAccn(CoreAccn accn) {
        this.accn = accn;
    }

    public MstCurrency getCcy() {
        return this.ccy;
    }

    public void setCcy(MstCurrency ccy) {
        this.ccy = ccy;
    }

    @Override
    public int compareTo(CreditRequest o) {
        return 0;
    }

    @Override
    public void init() {
    }
}
