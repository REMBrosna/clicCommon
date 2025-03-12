package com.guudint.clickargo.common.dto;

import java.util.Date;

import com.guudint.clickargo.common.model.TCkWhitelabel;
import com.vcc.camelone.ccm.dto.CoreAccn;
import com.vcc.camelone.common.dto.AbstractDTO;

public class CkCtWhitelabel extends AbstractDTO<CkCtWhitelabel, TCkWhitelabel> {

	private static final long serialVersionUID = 4095082058047603034L;
	private String wlId;
	private CoreAccn TCoreAccn;
	private String wlName;
	private Character wlStatus;
	private String wlImgLoc;
	private Date wlDtCreate;
	private String wtUidCreate;
	private Date wlDtLupd;
	private String wlUidLupd;

	// place holder for filename, will not be saved
	private String filename;
	// place holder for the byte data, will not be saved in db but in filesystem
	private byte[] data;

	public CkCtWhitelabel() {
	}

	public CkCtWhitelabel(TCkWhitelabel entity) {
		super(entity);
	}

	public CkCtWhitelabel(String wlId, CoreAccn TCoreAccn, String wlName) {
		this.wlId = wlId;
		this.TCoreAccn = TCoreAccn;
		this.wlName = wlName;
	}

	public CkCtWhitelabel(String wlId, CoreAccn TCoreAccn, String wlName, Character wlStatus, String wlImgLoc,
			Date wlDtCreate, String wtUidCreate, Date wlDtLupd, String wlUidLupd) {
		this.wlId = wlId;
		this.TCoreAccn = TCoreAccn;
		this.wlName = wlName;
		this.wlStatus = wlStatus;
		this.wlImgLoc = wlImgLoc;
		this.wlDtCreate = wlDtCreate;
		this.wtUidCreate = wtUidCreate;
		this.wlDtLupd = wlDtLupd;
		this.wlUidLupd = wlUidLupd;
	}

	/**
	 * @return the wlId
	 */
	public String getWlId() {
		return wlId;
	}

	/**
	 * @param wlId the wlId to set
	 */
	public void setWlId(String wlId) {
		this.wlId = wlId;
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
	 * @return the wlName
	 */
	public String getWlName() {
		return wlName;
	}

	/**
	 * @param wlName the wlName to set
	 */
	public void setWlName(String wlName) {
		this.wlName = wlName;
	}

	/**
	 * @return the wlStatus
	 */
	public Character getWlStatus() {
		return wlStatus;
	}

	/**
	 * @param wlStatus the wlStatus to set
	 */
	public void setWlStatus(Character wlStatus) {
		this.wlStatus = wlStatus;
	}

	/**
	 * @return the wlImgLoc
	 */
	public String getWlImgLoc() {
		return wlImgLoc;
	}

	/**
	 * @param wlImgLoc the wlImgLoc to set
	 */
	public void setWlImgLoc(String wlImgLoc) {
		this.wlImgLoc = wlImgLoc;
	}

	/**
	 * @return the wlDtCreate
	 */
	public Date getWlDtCreate() {
		return wlDtCreate;
	}

	/**
	 * @param wlDtCreate the wlDtCreate to set
	 */
	public void setWlDtCreate(Date wlDtCreate) {
		this.wlDtCreate = wlDtCreate;
	}

	/**
	 * @return the wtUidCreate
	 */
	public String getWtUidCreate() {
		return wtUidCreate;
	}

	/**
	 * @param wtUidCreate the wtUidCreate to set
	 */
	public void setWtUidCreate(String wtUidCreate) {
		this.wtUidCreate = wtUidCreate;
	}

	/**
	 * @return the wlDtLupd
	 */
	public Date getWlDtLupd() {
		return wlDtLupd;
	}

	/**
	 * @param wlDtLupd the wlDtLupd to set
	 */
	public void setWlDtLupd(Date wlDtLupd) {
		this.wlDtLupd = wlDtLupd;
	}

	/**
	 * @return the wlUidLupd
	 */
	public String getWlUidLupd() {
		return wlUidLupd;
	}

	/**
	 * @param wlUidLupd the wlUidLupd to set
	 */
	public void setWlUidLupd(String wlUidLupd) {
		this.wlUidLupd = wlUidLupd;
	}

	@Override
	public int compareTo(CkCtWhitelabel o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

}
