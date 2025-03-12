package com.guudint.clickargo.estamp.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.vcc.camelone.common.COAbstractEntity;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class StampCallbackRequest extends COAbstractEntity<StampCallbackRequest> {

    private static final long serialVersionUID = 1L;

    private String documentId;
    private String docUrl;
    private String stampingStatus;

    public String getDocumentId() {
        return this.documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getDocUrl() {
        return this.docUrl;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }

    public String getStampingStatus() {
        return this.stampingStatus;
    }

    public void setStampingStatus(String stampingStatus) {
        this.stampingStatus = stampingStatus;
    }

    @Override
    public int compareTo(StampCallbackRequest o) {
        return 0;
    }

    @Override
    public void init() {
    }
    
}
