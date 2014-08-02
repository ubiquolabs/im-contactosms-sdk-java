/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interactuamovil.apps.contactosms.api.client.rest.messages;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.interactuamovil.apps.contactosms.api.utils.JsonObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author sergeiw
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id","groups","msisdn","recipients_count","message","recipients"})
class MessageLogResponseParams extends JsonObject {
    
    @JsonProperty("id")
    private Integer messageId;
    
    @JsonProperty("groups")
    private List<String> groups;
    
    @JsonProperty("msisdn")
    private String msisdn;
    
    @JsonProperty("message")
    private String message;
    
    @JsonProperty("recipients_count")
    private Integer recipientsCount;
    
    @JsonProperty("recipients")
    private List<String> recipients;
    
    @JsonProperty("username")
    private String username;
    
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT")
    @JsonProperty("sent_on")
    private Date sent_on;
    
    
    
    public static MessageLogResponseParams fromJson(String json) throws IOException {
        return JsonObject.fromJson(json, MessageLogResponseParams.class);        
    }

    /**
     * @return the messageId
     */
    public Integer getMessageId() {
        return messageId;
    }

    /**
     * @param messageId the messageId to set
     */
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    /**
     * @return the groups
     */
    public List<String> getGroups() {
        return groups;
    }

    /**
     * @param groups the groups to set
     */
    public void setGroups(List<String> groups) {
        this.groups = groups;
    }
    
    public void setGroups(String groupList) {
        if (groupList != null) {
            String[] list = groupList.split(",");
            if (list.length > 0) {
                this.groups = new ArrayList<String>();
                for (String g : list) {
                    this.groups.add(g);
                }
            }            
        }
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

    /**
     * @return the recipientsCount
     */
    public Integer getRecipientsCount() {
        return recipientsCount;
    }

    /**
     * @param recipientsCount the recipientsCount to set
     */
    public void setRecipientsCount(Integer recipientsCount) {
        this.recipientsCount = recipientsCount;
    }

    /**
     * @return the recipients
     */
    public List<String> getRecipients() {
        return recipients;
    }

    /**
     * @param recipients the recipients to set
     */
    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    /**
     * @return the msisdn
     */
    public String getMsisdn() {
        return msisdn;
    }

    /**
     * @param msisdn the msisdn to set
     */
    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }
    
    
    
}
