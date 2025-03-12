package com.guudint.clickargo.manageaccn.dto;

import java.util.List;

import com.guudint.clickargo.common.dto.CkAccn;
import com.guudint.clickargo.common.dto.CkCtWhitelabel;
import com.guudint.clickargo.master.dto.CkMstServiceType;
import com.guudint.clickargo.master.enums.FormActions;
import com.guudint.clickargo.master.enums.ServiceTypes;
import com.guudint.clickargo.validator.ValidationGroup.CreateValid;
import com.guudint.clickargo.validator.ValidationGroup.SubmitValid;
import com.guudint.clickargo.validator.ValidationGroup.UpdateValid;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.ccm.dto.CoreAccnAtt;
import com.vcc.camelone.ccm.dto.CoreAccnConfig;
import com.vcc.camelone.common.COAbstractEntity;
import com.vcc.camelone.validate.NotBlankExt;

public class CkManageAccn extends COAbstractEntity<CkManageAccn> {

	private static final long serialVersionUID = -6713078239554545283L;

	@NotBlankExt(groups = { UpdateValid.class,
			SubmitValid.class }, message = "{ck.account.accnId.required}", subFieldName = "accnId")
	@NotBlankExt(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, message = "{ck.account.addrCtry.ctyCode.required}", subFieldName = "accnAddr.addrCtry.ctyCode")
	@NotBlankExt(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, message = "{ck.account.TMstAccnType.atypId.required}", subFieldName = "TMstAccnType.atypId")
	@NotBlankExt(groups = { CreateValid.class, UpdateValid.class,
			SubmitValid.class }, message = "{ck.account.accnName.required}", subFieldName = "accnName")
	@NotBlankExt(groups = { SubmitValid.class }, message = "{ck.account.tinNo.required}", subFieldName = "accnCoyRegn")
	@NotBlankExt(groups = {
			SubmitValid.class }, message = "{ck.account.ctcNo.required}", subFieldName = "accnContact.contactTel")
	@NotBlankExt(groups = {
			SubmitValid.class }, message = "{ck.account.ctcEmail.required}", subFieldName = "accnContact.contactEmail")
	@NotBlankExt(groups = {
			SubmitValid.class }, message = "{ck.account.addrLn1.required}", subFieldName = "accnAddr.addrLn1")
	@NotBlankExt(groups = {
			SubmitValid.class }, message = "{ck.account.addrLn2.required}", subFieldName = "accnAddr.addrLn2")
	@NotBlankExt(groups = {
			SubmitValid.class }, message = "{ck.account.addrPcode.required}", subFieldName = "accnAddr.addrPcode")
	@NotBlankExt(groups = {
			SubmitValid.class }, message = "{ck.account.addrCity.required}", subFieldName = "accnAddr.addrCity")
	@NotBlankExt(groups = {
			SubmitValid.class }, message = "{ck.account.addrProv.required}", subFieldName = "accnAddr.addrProv")
	private CoreAccn accnDetails;

//	private CoreUsr admin;
	private List<CkAccnAtt> accnSuppDocs;

//	private CkAccnExt ckAccnExt;
	private FormActions action;

	private String accnProcessType;

	// place holder for remarks on approve/reject
	private String remarks;
	private boolean isAccnTerminable;

	private String sageAccpacId;
	private CoreAccnConfig bankDetails;
	private CoreAccnConfig staticVa;
	private String financeOptions;
	private String financer;
	private String financerUrl;
	private String mobileEnabled;

	// place holder for service type tied to this account in session
	private ServiceTypes accnServiceType;

	// holder for OPM register button display
	private boolean isOpmRegistered;

	private CkCtWhitelabel bgImageWl;

	private CoreAccnAtt companyLogo;

	private CkAccn ckAccn;

	private List<CkMstServiceType> svcSubTypes;

	public CoreAccn getAccnDetails() {
		return accnDetails;
	}

	public void setAccnDetails(CoreAccn accnDetails) {
		this.accnDetails = accnDetails;
	}

	public List<CkAccnAtt> getAccnSuppDocs() {
		return accnSuppDocs;
	}

	public void setAccnSuppDocs(List<CkAccnAtt> accnSuppDocs) {
		this.accnSuppDocs = accnSuppDocs;
	}

	public FormActions getAction() {
		return action;
	}

	public void setAction(FormActions action) {
		this.action = action;
	}

	/**
	 * @return the accnProcessType
	 */
	public String getAccnProcessType() {
		return accnProcessType;
	}

	/**
	 * @param accnProcessType the accnProcessType to set
	 */
	public void setAccnProcessType(String accnProcessType) {
		this.accnProcessType = accnProcessType;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the isAccnTerminable
	 */
	public boolean isAccnTerminable() {
		return isAccnTerminable;
	}

	/**
	 * @param isAccnTerminable the isAccnTerminable to set
	 */
	public void setAccnTerminable(boolean isAccnTerminable) {
		this.isAccnTerminable = isAccnTerminable;
	}

	/**
	 * @return the sageAccpacId
	 */
	public String getSageAccpacId() {
		return sageAccpacId;
	}

	/**
	 * @param sageAccpacId the sageAccpacId to set
	 */
	public void setSageAccpacId(String sageAccpacId) {
		this.sageAccpacId = sageAccpacId;
	}

	/**
	 * @return the staticVa
	 */
	public CoreAccnConfig getStaticVa() {
		return staticVa;
	}

	/**
	 * @param staticVa the staticVa to set
	 */
	public void setStaticVa(CoreAccnConfig staticVa) {
		this.staticVa = staticVa;
	}

	/**
	 * @return the bankDetails
	 */
	public CoreAccnConfig getBankDetails() {
		return bankDetails;
	}

	/**
	 * @param bankDetails the bankDetails to set
	 */
	public void setBankDetails(CoreAccnConfig bankDetails) {
		this.bankDetails = bankDetails;
	}

	/**
	 * @return the financeOptions
	 */
	public String getFinanceOptions() {
		return financeOptions;
	}

	/**
	 * @param financeOptions the financeOptions to set
	 */
	public void setFinanceOptions(String financeOptions) {
		this.financeOptions = financeOptions;
	}

	/**
	 * @return the mobileEnabled
	 */
	public String getMobileEnabled() {
		return mobileEnabled;
	}

	/**
	 * @param mobileEnabled the mobileEnabled to set
	 */
	public void setMobileEnabled(String mobileEnabled) {
		this.mobileEnabled = mobileEnabled;
	}

	@Override
	public void init() {

	}

	@Override
	public int compareTo(CkManageAccn o) {
		return 0;
	}

	public CkCtWhitelabel getBgImageWl() {
		return bgImageWl;
	}

	public void setBgImageWl(CkCtWhitelabel bgImageWl) {
		this.bgImageWl = bgImageWl;
	}

	public CoreAccnAtt getCompanyLogo() {
		return companyLogo;
	}

	public void setCompanyLogo(CoreAccnAtt companyLogo) {
		this.companyLogo = companyLogo;
	}

	/**
	 * @return the financer
	 */
	public String getFinancer() {
		return financer;
	}

	/**
	 * @param financer the financer to set
	 */
	public void setFinancer(String financer) {
		this.financer = financer;
	}

	/**
	 * @return the financerUrl
	 */
	public String getFinancerUrl() {
		return financerUrl;
	}

	/**
	 * @param financerUrl the financerUrl to set
	 */
	public void setFinancerUrl(String financerUrl) {
		this.financerUrl = financerUrl;
	}

	/**
	 * @return the isOpmRegistered
	 */
	public boolean isOpmRegistered() {
		return isOpmRegistered;
	}

	/**
	 * @param isOpmRegistered the isOpmRegistered to set
	 */
	public void setOpmRegistered(boolean isOpmRegistered) {
		this.isOpmRegistered = isOpmRegistered;
	}

	public CkAccn getCkAccn() {
		return ckAccn;
	}

	public void setCkAccn(CkAccn ckAccn) {
		this.ckAccn = ckAccn;
	}

	/**
	 * @return the accnServiceType
	 */
	public ServiceTypes getAccnServiceType() {
		return accnServiceType;
	}

	/**
	 * @param accnServiceType the accnServiceType to set
	 */
	public void setAccnServiceType(ServiceTypes accnServiceType) {
		this.accnServiceType = accnServiceType;
	}

	/**
	 * @return the svcSubTypes
	 */
	public List<CkMstServiceType> getSvcSubTypes() {
		return svcSubTypes;
	}

	/**
	 * @param svcSubTypes the svcSubTypes to set
	 */
	public void setSvcSubTypes(List<CkMstServiceType> svcSubTypes) {
		this.svcSubTypes = svcSubTypes;
	}

}
