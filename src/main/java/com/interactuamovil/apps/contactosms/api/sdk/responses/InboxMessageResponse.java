package com.interactuamovil.contactosms.api.responses;

import java.util.Date;

public class InboxMessageResponse {

    private String status;
    private String msisdn;
    private Date datetime;
    private String message;
    private int messageId;
    private String shortNumber;

    public void setStatus(String s) {
        status = s;
    }

    public void setMsisdn(String s) {
        msisdn = s;
    }

    public void setDatetime(Date d) {
        datetime = d;
    }

    public void setMessage(String s) {
        message = s;
    }

    public void setMessageId(int i) {
        messageId = i;
    }

    public void setShortNumber(String s) {
        shortNumber = s;
    }

    public String getStatus() {
        return status;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public Date getDatetime() {
        return datetime;
    }

    public String getMessage() {
        return message;
    }

    public int getMessageId() {
        return messageId;
    }

    public String getShortNumber() {
        return shortNumber;
    }

}
