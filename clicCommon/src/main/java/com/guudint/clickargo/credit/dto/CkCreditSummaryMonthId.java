package com.guudint.clickargo.credit.dto;

import com.guudint.clickargo.credit.model.TCkCreditSummaryMonthId;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkCreditSummaryMonthId extends AbstractDTO<CkCreditSummaryMonthId, TCkCreditSummaryMonthId> {

    private static final long serialVersionUID = 1L;
    private String crsmCompany;
	private String crsmMonth;

    public CkCreditSummaryMonthId() {
	}

	public CkCreditSummaryMonthId(String crsmCompany, String crsmMonth) {
		this.crsmCompany = crsmCompany;
		this.crsmMonth = crsmMonth;
	}

	public String getCrsmCompany() {
		return this.crsmCompany;
	}

	public void setCrsmCompany(String crsmCompany) {
		this.crsmCompany = crsmCompany;
	}

	public String getCrsmMonth() {
		return this.crsmMonth;
	}

	public void setCrsmMonth(String crsmMonth) {
		this.crsmMonth = crsmMonth;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CkCreditSummaryMonthId))
			return false;
		CkCreditSummaryMonthId castOther = (CkCreditSummaryMonthId) other;

		return ((this.getCrsmCompany() == castOther.getCrsmCompany()) || (this.getCrsmCompany() != null
				&& castOther.getCrsmCompany() != null && this.getCrsmCompany().equals(castOther.getCrsmCompany())))
				&& ((this.getCrsmMonth() == castOther.getCrsmMonth()) || (this.getCrsmMonth() != null
						&& castOther.getCrsmMonth() != null && this.getCrsmMonth().equals(castOther.getCrsmMonth())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getCrsmCompany() == null ? 0 : this.getCrsmCompany().hashCode());
		result = 37 * result + (getCrsmMonth() == null ? 0 : this.getCrsmMonth().hashCode());
		return result;
	}

    @Override
    public int compareTo(CkCreditSummaryMonthId o) {
        return 0;
    }

    @Override
    public void init() {
    }
    
}
