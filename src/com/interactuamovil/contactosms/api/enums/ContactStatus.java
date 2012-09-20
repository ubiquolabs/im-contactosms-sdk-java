package com.interactuamovil.contactosms.api.enums;

public enum ContactStatus {

    CONFIRMED(1),
    PENDING(0),
    CANCELLED(-1);

    private Integer status;

    ContactStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
