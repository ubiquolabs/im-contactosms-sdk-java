/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interactuamovil.apps.contactosms.api.client.rest.messages;

import com.interactuamovil.apps.contactosms.api.utils.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interactuamovil.apps.contactosms.api.enums.RepeatInterval;

import java.io.IOException;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author sergeiw
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduledMessageJson extends JsonObject {
    
    @JsonProperty(value="scheduled_message_id") 
    private Integer scheduledMessageId;
    @JsonProperty(value="groups") 
    private List<String> groups;
    @JsonProperty(value="message") 
    private String message;
    @JsonProperty(value="id") 
    private String clientMessageId;
    @JsonProperty(value="event_name") 
    private String eventName;
    @JsonProperty(value="start_date")
    private Date startDate;
    @JsonProperty(value="end_date")
    private Date endDate;
    @JsonProperty(value="execution_time") 
    private Time executionTime;
    @JsonProperty(value="repeat_interval") 
    private RepeatInterval repeatInterval;
    @JsonProperty(value="repeat_days") 
    private String repeatDays;
    

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
    
    public static ScheduledMessageJson fromJson(String json) throws IOException {
        return JsonObject.fromJson(json, ScheduledMessageJson.class);        
    }
    
    @JsonIgnore
    public String getGroupsNames() {
        List<String> grpList = getGroups();
        if (grpList != null) {
            String[] grpArr = new String[grpList.size()];
            grpArr = grpList.toArray(grpArr);
            String groupNames = StringUtils.join(grpArr, ",");
            return groupNames;
        } else {
            return null;
        }
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
     * @return the eventName
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * @param eventName the eventName to set
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * @return the dateBegins
     */    
    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the dateBegins to set
     */
    @JsonDeserialize(using = JsonDateDeserializer.class)
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the dateExpires
     */
    @JsonSerialize(using = JsonDateSerializer.class)    
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the dateExpires to set
     */
    @JsonDeserialize(using = JsonDateDeserializer.class)
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the executionTime
     */
    @JsonSerialize(using = JsonTimeSerializer.class)
    public Time getExecutionTime() {
        return executionTime;
    }

    /**
     * @param executionTime the executionTime to set
     */
    @JsonDeserialize(using = JsonTimeDeserializer.class)
    public void setExecutionTime(Time executionTime) {
        this.executionTime = executionTime;
    }

    /**
     * @return the repeatInterval
     */
    public String getRepeatDays() {
        return repeatDays;
    }

    /**
     * @param repeatDays the repeatInterval to set
     */    
    public void setRepeatDays(String repeatDays) {
        this.repeatDays = repeatDays;
    }

    /**
     * @return the repeatInterval
     */    
    public RepeatInterval getRepeatInterval() {
        return repeatInterval;
    }

    /**
     * @param repeatInterval the repeatInterval to set
     */
    @JsonDeserialize(using = JsonRepeatIntervalDeserializer.class)
    public void setRepeatInterval(RepeatInterval repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    /**
     * @return the scheduledMessageId
     */
    public Integer getScheduledMessageId() {
        return scheduledMessageId;
    }

    /**
     * @param scheduledMessageId the scheduledMessageId to set
     */
    public void setScheduledMessageId(Integer scheduledMessageId) {
        this.scheduledMessageId = scheduledMessageId;
    }
    
}
