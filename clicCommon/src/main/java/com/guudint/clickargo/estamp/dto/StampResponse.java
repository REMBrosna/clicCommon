package com.guudint.clickargo.estamp.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.vcc.camelone.common.COAbstractEntity;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class StampResponse extends COAbstractEntity<StampResponse> {

    private static final long serialVersionUID = 1L;
    
    private Data data;

    public Data getData() {
        return this.data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    // Class for Data
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class Data {
        private String id;
        private String type;
        private Attributes attributes;

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return this.type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Attributes getAttributes() {
            return this.attributes;
        }

        public void setAttributes(Attributes attributes) {
            this.attributes = attributes;
        }
    }

    // Class for Attributes
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Attributes {
        private String filename;
        private String docUrl;
        private String signingStatus;
        private String stampingStatus;
        private String typeOfMeterai;
        private List<Object> signers;
        private Date createdAt;
        private Date updatedAt;

        public String getFilename() {
            return this.filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getDocUrl() {
            return this.docUrl;
        }

        public void setDocUrl(String docUrl) {
            this.docUrl = docUrl;
        }

        public String getSigningStatus() {
            return this.signingStatus;
        }

        public void setSigningStatus(String signingStatus) {
            this.signingStatus = signingStatus;
        }

        public String getStampingStatus() {
            return this.stampingStatus;
        }

        public void setStampingStatus(String stampingStatus) {
            this.stampingStatus = stampingStatus;
        }

        public String getTypeOfMeterai() {
            return this.typeOfMeterai;
        }

        public void setTypeOfMeterai(String typeOfMeterai) {
            this.typeOfMeterai = typeOfMeterai;
        }

        public List<Object> getSigners() {
            return this.signers;
        }

        public void setSigners(List<Object> signers) {
            this.signers = signers;
        }

        public Date getCreatedAt() {
            return this.createdAt;
        }

        public void setCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
        }

        public Date getUpdatedAt() {
            return this.updatedAt;
        }

        public void setUpdatedAt(Date updatedAt) {
            this.updatedAt = updatedAt;
        }   
    }

    @Override
    public int compareTo(StampResponse o) {
        return 0;
    }

    @Override
    public void init() {
    }
    
}
