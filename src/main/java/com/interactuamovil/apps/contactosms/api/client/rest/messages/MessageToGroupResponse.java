package com.interactuamovil.apps.contactosms.api.client.rest.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageToGroupResponse {

    @JsonProperty(value="sms_sent")
    private Integer sent = null;
    @JsonProperty(value="sms_message")
    private String message = null;

    /**
     * @return the sent
     */
    public Integer getSent() {
        return sent;
    }

    /**
     * @param sent the sent to set
     */
    public void setSent(Integer sent) {
        this.sent = sent;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    
}
