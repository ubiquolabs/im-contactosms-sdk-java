/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interactuamovil.apps.contactosms.api.client.rest.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.interactuamovil.apps.contactosms.api.utils.JsonObject;

/**
 *
 * @author sergeiw
 */
public class ScheduledMessageJsonResponse extends JsonObject {
    
    @JsonProperty(value="scheduled_message_id") 
    private Integer scheduledMessageId;
    @JsonProperty(value="sms_message")
    private String smsMessage;    

    public ScheduledMessageJsonResponse() {
    }

    public ScheduledMessageJsonResponse(Integer scheduledMessageId, String smsMessage) {
        this.scheduledMessageId = scheduledMessageId;
        this.smsMessage = smsMessage;
    }

    public static ScheduledMessageJsonResponse create(Integer scheduledMessageId, String smsMessage) {
        return new ScheduledMessageJsonResponse(scheduledMessageId, smsMessage);
    }
    
    /**
     * @return the smsSent
     */
    public Integer getScheduledMessageId() {
        return scheduledMessageId;
    }

    /**
     * @param scheduledMessageId the smsSent to set
     */
    public void setScheduledMessageId(Integer scheduledMessageId) {
        this.scheduledMessageId = scheduledMessageId;
    }

    /**
     * @return the smsMessage
     */
    public String getSmsMessage() {
        return smsMessage;
    }

    /**
     * @param smsMessage the smsMessage to set
     */
    public void setSmsMessage(String smsMessage) {
        this.smsMessage = smsMessage;
    }
    
    
    
}
