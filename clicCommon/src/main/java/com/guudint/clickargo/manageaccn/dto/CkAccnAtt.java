package com.guudint.clickargo.manageaccn.dto;

import java.util.Date;

import com.guudint.clickargo.manageaccn.model.TCkAccnAtt;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkAccnAtt extends AbstractDTO<CkAccnAtt, TCkAccnAtt> {

	private static final long serialVersionUID = 6603496973378241493L;
	private String aatId;
	private CkMstAccnAttType TCkMstAccnAttType;
	private CoreAccn TCoreAccn;
	private String aatName;
	private String aatNo;
	private Date atDtValidility;
	private String aatLoc;
	private Character atStatus;
	private Date atDtCreate;
	private String atUidCreate;
	private Date atDtLupd;
	private String atUidLupd;

	private byte[] aatLocData;
	private boolean isDuplicate;

	public CkAccnAtt() {
	}

	public CkAccnAtt(TCkAccnAtt entity) {
		super(entity);
	}

	public CkAccnAtt(String aatId, CkMstAccnAttType TCkMstAccnAttType, CoreAccn TCoreAccn) {
		this.aatId = aatId;
		this.TCkMstAccnAttType = TCkMstAccnAttType;
		this.TCoreAccn = TCoreAccn;
	}

	public CkAccnAtt(String aatId, CkMstAccnAttType TCkMstAccnAttType, CoreAccn TCoreAccn, String aatName, String aatNo,
			Date atDtValidility, String aatLoc, Character atStatus, Date atDtCreate, String atUidCreate, Date atDtLupd,
			String atUidLupd) {
		this.aatId = aatId;
		this.TCkMstAccnAttType = TCkMstAccnAttType;
		this.TCoreAccn = TCoreAccn;
		this.aatName = aatName;
		this.aatNo = aatNo;
		this.atDtValidility = atDtValidility;
		this.aatLoc = aatLoc;
		this.atStatus = atStatus;
		this.atDtCreate = atDtCreate;
		this.atUidCreate = atUidCreate;
		this.atDtLupd = atDtLupd;
		this.atUidLupd = atUidLupd;
	}

	/**
	 * @return the aatId
	 */
	public String getAatId() {
		return aatId;
	}

	/**
	 * @param aatId the aatId to set
	 */
	public void setAatId(String aatId) {
		this.aatId = aatId;
	}

	/**
	 * @return the tCkMstAccnAttType
	 */
	public CkMstAccnAttType getTCkMstAccnAttType() {
		return TCkMstAccnAttType;
	}

	/**
	 * @param tCkMstAccnAttType the tCkMstAccnAttType to set
	 */
	public void setTCkMstAccnAttType(CkMstAccnAttType tCkMstAccnAttType) {
		TCkMstAccnAttType = tCkMstAccnAttType;
	}

	/**
	 * @return the tCoreAccn
	 */
	public CoreAccn getTCoreAccn() {
		return TCoreAccn;
	}

	/**
	 * @param tCoreAccn the tCoreAccn to set
	 */
	public void setTCoreAccn(CoreAccn tCoreAccn) {
		TCoreAccn = tCoreAccn;
	}

	/**
	 * @return the aatName
	 */
	public String getAatName() {
		return aatName;
	}

	/**
	 * @param aatName the aatName to set
	 */
	public void setAatName(String aatName) {
		this.aatName = aatName;
	}

	/**
	 * @return the aatNo
	 */
	public String getAatNo() {
		return aatNo;
	}

	/**
	 * @param aatNo the aatNo to set
	 */
	public void setAatNo(String aatNo) {
		this.aatNo = aatNo;
	}

	/**
	 * @return the atDtValidility
	 */
	public Date getAtDtValidility() {
		return atDtValidility;
	}

	/**
	 * @param atDtValidility the atDtValidility to set
	 */
	public void setAtDtValidility(Date atDtValidility) {
		this.atDtValidility = atDtValidility;
	}

	/**
	 * @return the aatLoc
	 */
	public String getAatLoc() {
		return aatLoc;
	}

	/**
	 * @param aatLoc the aatLoc to set
	 */
	public void setAatLoc(String aatLoc) {
		this.aatLoc = aatLoc;
	}

	/**
	 * @return the atStatus
	 */
	public Character getAtStatus() {
		return atStatus;
	}

	/**
	 * @param atStatus the atStatus to set
	 */
	public void setAtStatus(Character atStatus) {
		this.atStatus = atStatus;
	}

	/**
	 * @return the atDtCreate
	 */
	public Date getAtDtCreate() {
		return atDtCreate;
	}

	/**
	 * @param atDtCreate the atDtCreate to set
	 */
	public void setAtDtCreate(Date atDtCreate) {
		this.atDtCreate = atDtCreate;
	}

	/**
	 * @return the atUidCreate
	 */
	public String getAtUidCreate() {
		return atUidCreate;
	}

	/**
	 * @param atUidCreate the atUidCreate to set
	 */
	public void setAtUidCreate(String atUidCreate) {
		this.atUidCreate = atUidCreate;
	}

	/**
	 * @return the atDtLupd
	 */
	public Date getAtDtLupd() {
		return atDtLupd;
	}

	/**
	 * @param atDtLupd the atDtLupd to set
	 */
	public void setAtDtLupd(Date atDtLupd) {
		this.atDtLupd = atDtLupd;
	}

	/**
	 * @return the atUidLupd
	 */
	public String getAtUidLupd() {
		return atUidLupd;
	}

	/**
	 * @param atUidLupd the atUidLupd to set
	 */
	public void setAtUidLupd(String atUidLupd) {
		this.atUidLupd = atUidLupd;
	}

	/**
	 * @return the aatLocData
	 */
	public byte[] getAatLocData() {
		return aatLocData;
	}

	/**
	 * @param aatLocData the aatLocData to set
	 */
	public void setAatLocData(byte[] aatLocData) {
		this.aatLocData = aatLocData;
	}

	/**
	 * @return the isDuplicate
	 */
	public boolean isDuplicate() {
		return isDuplicate;
	}

	/**
	 * @param isDuplicate the isDuplicate to set
	 */
	public void setDuplicate(boolean isDuplicate) {
		this.isDuplicate = isDuplicate;
	}

	@Override
	public int compareTo(CkAccnAtt o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

}
