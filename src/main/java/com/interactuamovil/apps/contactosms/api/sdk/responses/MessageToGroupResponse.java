package com.interactuamovil.contactosms.api.responses;

public class MessageToGroupResponse {

    private Integer sms_sent = null;
    private String sms_message = null;

    public Integer getSms_sent() {
        return sms_sent;
    }

    public void setSms_sent(Integer sms_sent) {
        this.sms_sent = sms_sent;
    }

    public String getSms_message() {
        return sms_message;
    }

    public void setSms_message(String sms_message) {
        this.sms_message = sms_message;
    }

}
