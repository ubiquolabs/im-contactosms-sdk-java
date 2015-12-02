package com.interactuamovil.apps.contactosms.api.client.rest.messages;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interactuamovil.apps.contactosms.api.utils.JsonDateTimeDeserializer;
import com.interactuamovil.apps.contactosms.api.utils.JsonDateTimeSerializer;
import com.interactuamovil.apps.contactosms.api.utils.JsonObject;

import java.util.Date;
import java.util.List;

/**
 * Created by javier on 1/12/15.
 * Json for log information
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageScheduledLogJson extends JsonObject {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("event_name")
    private String eventName;

    @JsonProperty("message")
    private String message;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("groups")
    private List<String> groups;

    @JsonProperty("name")
    private String name;

    @JsonProperty("execution")
    private Date execution;

    @JsonProperty("status_code")
    private int statusCode;

    @JsonProperty("rows_sent")
    private int rowsSent;

    @JsonProperty("rows_error")
    private int rowsError;

    @JsonProperty("status_description")
    private String statusDescription;

    @JsonProperty("type")
    private String type;

    @JsonProperty("created_on")
    private Date createdOn;

    @JsonProperty("created_by")
    private String createdBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonSerialize(using = JsonDateTimeSerializer.class)
    public Date getExecution() {
        return execution;
    }

    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    public void setExecution(Date execution) {
        this.execution = execution;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getRowsSent() {
        return rowsSent;
    }

    public void setRowsSent(int rowsSent) {
        this.rowsSent = rowsSent;
    }

    public int getRowsError() {
        return rowsError;
    }

    public void setRowsError(int rowsError) {
        this.rowsError = rowsError;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
