package com.interactuamovil.apps.contactosms.api.client.rest.messages;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interactuamovil.apps.contactosms.api.utils.JsonDateTimeDeserializer;
import com.interactuamovil.apps.contactosms.api.utils.JsonDateTimeSerializer;
import com.interactuamovil.apps.contactosms.api.utils.JsonObject;

import java.util.Date;

/**
 * Created by javier on 1/12/15.
 * Json for log information
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageScheduledLogJson extends JsonObject {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("message")
    private String message;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
