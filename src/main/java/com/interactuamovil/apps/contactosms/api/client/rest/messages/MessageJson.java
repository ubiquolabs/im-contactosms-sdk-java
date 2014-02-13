/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interactuamovil.apps.contactosms.api.client.rest.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.interactuamovil.apps.contactosms.api.enums.MessageDirection;
import com.interactuamovil.apps.contactosms.api.enums.MessageSentFrom;
import com.interactuamovil.apps.contactosms.api.enums.MessageStatus;
import com.interactuamovil.apps.contactosms.api.utils.JsonObject;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author sergeiw
 */
public class MessageJson extends JsonObject {
    
    @JsonProperty(value="message_id")
    private Integer messageId;
    @JsonProperty(value="short_code")
    private String shortCode;
    @JsonProperty(value="type")
    private Integer messageTypeId;
    @JsonProperty(value="direction")
    private MessageDirection messageDirection;
    @JsonProperty(value="status")
    private MessageStatus messageStatus;
    @JsonProperty(value="sent_from")
    private MessageSentFrom sentFrom;
    @JsonProperty(value="id")
    private String clientMessageId;
    @JsonProperty(value="message")
    private String message;
    @JsonProperty(value="sent_count")
    private Integer sentCount;
    @JsonProperty(value="error_count")
    private Integer errorCount;
    @JsonProperty(value="total_recipients")
    private Integer totalRecipients;
    @JsonProperty(value="msisdn")
    private String msisdn;
    @JsonProperty(value="country")
    private String countryCode;
    @JsonProperty(value="is_billable")
    private boolean billable;
    @JsonProperty(value="is_scheduled")
    private boolean scheduled;
    
    @JsonProperty(value="groups")
    private List<String> groups;
    
    @JsonProperty(value="recipients")
    private List<RecipientJson> recipients;

    public static MessageJson fromJson(String json) throws IOException {
        return JsonObject.fromJson(json, MessageJson.class);        
    }
    
    public class RecipientJson extends JsonObject {
        
        @JsonProperty(value="msisdn")
        private String msisdn;
        @JsonProperty(value="country")
        private String countryCode;
        @JsonProperty(value="status")
        private MessageStatus messageStatus;

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

        /**
         * @return the countryCode
         */
        public String getCountryCode() {
            return countryCode;
        }

        /**
         * @param countryCode the countryCode to set
         */
        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        /**
         * @return the messageStatus
         */
        public MessageStatus getMessageStatus() {
            return messageStatus;
        }

        /**
         * @param messageStatus the messageStatus to set
         */
        public void setMessageStatus(MessageStatus messageStatus) {
            this.messageStatus = messageStatus;
        }
        
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
     * @return the shortCode
     */
    public String getShortCode() {
        return shortCode;
    }

    /**
     * @param shortCode the shortCode to set
     */
    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    /**
     * @return the messageTypeId
     */
    public Integer getMessageTypeId() {
        return messageTypeId;
    }

    /**
     * @param messageTypeId the messageTypeId to set
     */
    public void setMessageTypeId(Integer messageTypeId) {
        this.messageTypeId = messageTypeId;
    }

    /**
     * @return the messageDirection
     */
    public MessageDirection getMessageDirection() {
        return messageDirection;
    }

    /**
     * @param messageDirection the messageDirection to set
     */
    public void setMessageDirection(MessageDirection messageDirection) {
        this.messageDirection = messageDirection;
    }

    /**
     * @return the messageStatus
     */
    public MessageStatus getMessageStatus() {
        return messageStatus;
    }

    /**
     * @param messageStatus the messageStatus to set
     */
    public void setMessageStatus(MessageStatus messageStatus) {
        this.messageStatus = messageStatus;
    }

    /**
     * @return the sentFrom
     */
    public MessageSentFrom getSentFrom() {
        return sentFrom;
    }

    /**
     * @param sentFrom the sentFrom to set
     */
    public void setSentFrom(MessageSentFrom sentFrom) {
        this.sentFrom = sentFrom;
    }

    /**
     * @return the clientMessageId
     */
    public String getClientMessageId() {
        return clientMessageId;
    }

    /**
     * @param clientMessageId the clientMessageId to set
     */
    public void setClientMessageId(String clientMessageId) {
        this.clientMessageId = clientMessageId;
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
     * @return the sentCount
     */
    public Integer getSentCount() {
        return sentCount;
    }

    /**
     * @param sentCount the sentCount to set
     */
    public void setSentCount(Integer sentCount) {
        this.sentCount = sentCount;
    }

    /**
     * @return the errorCount
     */
    public Integer getErrorCount() {
        return errorCount;
    }

    /**
     * @param errorCount the errorCount to set
     */
    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    /**
     * @return the totalRecipients
     */
    public Integer getTotalRecipients() {
        return totalRecipients;
    }

    /**
     * @param totalRecipients the totalRecipients to set
     */
    public void setTotalRecipients(Integer totalRecipients) {
        this.totalRecipients = totalRecipients;
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

    /**
     * @return the countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * @param countryCode the countryCode to set
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * @return the billable
     */
    public boolean isBillable() {
        return billable;
    }

    /**
     * @param billable the billable to set
     */
    public void setBillable(boolean billable) {
        this.billable = billable;
    }

    /**
     * @return the scheduled
     */
    public boolean isScheduled() {
        return scheduled;
    }

    /**
     * @param scheduled the scheduled to set
     */
    public void setScheduled(boolean scheduled) {
        this.scheduled = scheduled;
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

    /**
     * @return the recipients
     */
    public List<RecipientJson> getRecipients() {
        return recipients;
    }

    /**
     * @param recipients the recipients to set
     */
    public void setRecipients(List<RecipientJson> recipients) {
        this.recipients = recipients;
    }        
    
    
}
