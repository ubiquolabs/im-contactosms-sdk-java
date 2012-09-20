package com.interactuamovil.contactosms.api.responses;

public class ContactResponse {

    private String msisdn = null;
    private String firstName = null;
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
