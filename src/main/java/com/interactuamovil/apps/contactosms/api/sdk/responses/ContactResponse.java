package com.interactuamovil.contactosms.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ContactResponse {

    private String msisdn = null;
    @JsonProperty("first_name")
    private String firstName = null;
    @JsonProperty("last_name")
    private String lastName = null;
    private String status = null;

    public void setMsisdn(String s) {
        msisdn = s;
    }

    public void setFirstName(String s) {
        firstName = s;
    }

    public void setLastName(String s) {
        lastName = s;
    }

    public void setStatus(String s) {
        status = s;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStatus() {
        return status;
    }

}
