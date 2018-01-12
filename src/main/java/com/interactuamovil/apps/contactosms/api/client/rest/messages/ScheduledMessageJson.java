/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interactuamovil.apps.contactosms.api.client.rest.messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interactuamovil.apps.contactosms.api.client.rest.templates.TemplateJson;
import com.interactuamovil.apps.contactosms.api.enums.RepeatInterval;
import com.interactuamovil.apps.contactosms.api.utils.*;

import java.io.IOException;
import java.sql.Time;
import java.util.Date;
import java.util.List;

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

    @JsonProperty(value="type")
    private String type;

    @JsonProperty(value = "status")
    private String status;

    @JsonProperty(value = "phone_number")
    private String phoneNumber;

    @JsonProperty(value = "country_code")
    private String countryCode;

    @JsonProperty("rows_found")
    private Integer rowsFound;

    @JsonProperty("rows_processed")
    private Integer rowsProcessed;

    @JsonProperty("rows_duplicated")
    private Integer rowsDuplicated;

    @JsonProperty("rows_error")
    private Integer rowsError;

    @JsonProperty("rows_ok")
    private Integer rowsOk;



    @JsonProperty(value = "days_of_week")
    private String[] daysOfWeek;

    @JsonProperty(value = "file_name")
    private String fileName;

    @JsonProperty(value = "created_by")
    private String createdBy;

    @JsonProperty(value = "created_on")
    private Date createdOn;

    @JsonProperty("fields")
    private List<TemplateJson.Field> fields;

    @JsonProperty("template_id")
    private Integer templateId;

    @JsonProperty("interaction_id")
    private String interactionId;
    
    @JsonProperty("tags")
    private List<String> tags;

    @JsonProperty("error_file")
    private String errorFile;
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
        if (grpList==null) return null;
        return String.join(",", grpList);
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

    /**
     * The type can be: SINGLE, GROUP, TAG, INTERACTION
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * The type can be: SINGLE, GROUP, TAG, INTERACTION
     * @param type Sets the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     *
     * @param phoneNumber The phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     *
     * @return Gets the country code
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     *
     * @param countryCode The country code
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }


    public Integer getRowsFound() {
        return rowsFound;
    }

    public void setRowsFound(Integer rowsFound) {
        this.rowsFound = rowsFound;
    }

    public Integer getRowsProcessed() {
        return rowsProcessed;
    }

    public void setRowsProcessed(Integer rowsProcessed) {
        this.rowsProcessed = rowsProcessed;
    }

    public Integer getRowsDuplicated() {
        return rowsDuplicated;
    }

    public void setRowsDuplicated(Integer rowsDuplicated) {
        this.rowsDuplicated = rowsDuplicated;
    }

    public Integer getRowsError() {
        return rowsError;
    }

    public void setRowsError(Integer rowsError) {
        this.rowsError = rowsError;
    }

    public Integer getRowsOk() {
        return rowsOk;
    }

    public void setRowsOk(Integer rowsOk) {
        this.rowsOk = rowsOk;
    }

    /**
     * Get a list of the days of week separated by commas
     * Used when repeatInterval is weekly
     * @return The days of week, Sun=0,Mon=1...Sat=6
     */
    public String[] getDaysOfWeek() {
        return daysOfWeek;
    }

    /**
     * Sets a list of the days of the week separated by commas
     * Used when repeatInterval is weekly
     * @param daysOfWeek The days of the week, Sun=0,Mon=1...Sat=6
     */
    public void setDaysOfWeek(String[] daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    /**
     * Gets the file name if scheduled message type is file
     * @return File name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the file name if scheduled message type is file
     * @param fileName The file name
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @JsonSerialize(using = JsonDateTimeSerializer.class)
    public Date getCreatedOn() {
        return createdOn;
    }

    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TemplateJson.Field> getFields() {
        return fields;
    }

    public void setFields(List<TemplateJson.Field> fields) {
        this.fields = fields;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public String getInteractionId() {
        return interactionId;
    }

    public void setInteractionId(String interactionId) {
        this.interactionId = interactionId;
    }

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getErrorFile() { return errorFile; }

	public void setErrorFile(String errorFile) { this.errorFile = errorFile; }
    
}
