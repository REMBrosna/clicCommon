package com.guudint.clickargo.estamp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.vcc.camelone.common.COAbstractEntity;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class AbstractRequest extends COAbstractEntity<AbstractRequest> {
    
    private static final long serialVersionUID = 1L;

    private String serviceId;
    private String invId;
    private String invType;
    private String pathFile;
    private String filename;

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

    public String getPathFile() {
        return this.pathFile;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public int compareTo(AbstractRequest o) {
        return 0;
    }
    @Override
    public void init() {
    }
}
