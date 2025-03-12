package com.guudint.clickargo.estamp.dto;

import java.util.List;

public class StampRequest extends AbstractRequest {

    private static final long serialVersionUID = 1L;

    private byte[] doc;
    private List<StampAnnotations> annotations;
    private String callbackUrl;

    public byte[] getDoc() {
        return this.doc;
    }

    public void setDoc(byte[] doc) {
        this.doc = doc;
    }

    public List<StampAnnotations> getAnnotations() {
        return this.annotations;
    }

    public void setAnnotations(List<StampAnnotations> annotations) {
        this.annotations = annotations;
    }

    public String getCallbackUrl() {
        return this.callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
}
