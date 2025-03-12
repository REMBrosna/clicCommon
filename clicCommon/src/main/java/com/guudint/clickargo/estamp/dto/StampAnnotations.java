package com.guudint.clickargo.estamp.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.vcc.camelone.common.COAbstractEntity;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class StampAnnotations extends COAbstractEntity<StampAnnotations> {

    private static final long serialVersionUID = 1L;

    private Integer page;
    private Integer positionX;
    private Integer positionY;
    private Integer elementWidth;
    private Integer elementHeight;
    private Integer canvasWidth;
    private Integer canvasHeight;
    private String typeOf;

    public Integer getPage() {
        return this.page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPositionX() {
        return this.positionX;
    }

    public void setPositionX(Integer positionX) {
        this.positionX = positionX;
    }

    public Integer getPositionY() {
        return this.positionY;
    }

    public void setPositionY(Integer positionY) {
        this.positionY = positionY;
    }

    public Integer getElementWidth() {
        return this.elementWidth;
    }

    public void setElementWidth(Integer elementWidth) {
        this.elementWidth = elementWidth;
    }

    public Integer getElementHeight() {
        return this.elementHeight;
    }

    public void setElementHeight(Integer elementHeight) {
        this.elementHeight = elementHeight;
    }

    public Integer getCanvasWidth() {
        return this.canvasWidth;
    }

    public void setCanvasWidth(Integer canvasWidth) {
        this.canvasWidth = canvasWidth;
    }

    public Integer getCanvasHeight() {
        return this.canvasHeight;
    }

    public void setCanvasHeight(Integer canvasHeight) {
        this.canvasHeight = canvasHeight;
    }

    public String getTypeOf() {
        return this.typeOf;
    }

    public void setTypeOf(String typeOf) {
        this.typeOf = typeOf;
    }

    @Override
    public int compareTo(StampAnnotations o) {
        return 0;
    }

    @Override
    public void init() {
    }
    
}
