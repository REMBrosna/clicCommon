package com.guudint.clickargo.estamp.dto;

import com.vcc.camelone.common.COAbstractEntity;

public class StampDownloadResponse extends COAbstractEntity<StampDownloadResponse> {

    private static final long serialVersionUID = -6777820683843025709L;

    private String serviceId;
    private String invId;
    private String invType;
    private String stampStatus;
    private String documentId;
    private String filename;
	private byte[] content;

    public String getServiceId() {
        return this.serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getInvId() {
        return this.invId;
    }

    public void setInvId(String invId) {
        this.invId = invId;
    }

    public String getInvType() {
        return this.invType;
    }

    public void setInvType(String invType) {
        this.invType = invType;
    }

    public String getStampStatus() {
        return this.stampStatus;
    }

    public void setStampStatus(String stampStatus) {
        this.stampStatus = stampStatus;
    }

    public String getDocumentId() {
        return this.documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public int compareTo(StampDownloadResponse o) {
        return 0;
    }

    @Override
    public void init() {
    }
    
}
