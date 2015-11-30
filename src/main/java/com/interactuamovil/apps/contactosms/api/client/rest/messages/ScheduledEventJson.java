package com.interactuamovil.apps.contactosms.api.client.rest.messages;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interactuamovil.apps.contactosms.api.utils.JsonDateDeserializer;
import com.interactuamovil.apps.contactosms.api.utils.JsonDateSerializer;

import java.util.Date;

/**
 * Created by javier on 27/11/15.
 * Scheduled event object
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduledEventJson {

    public static final String STATUS_PROJECTED = "projected";
    public static final String STATUS_EXECUTED = "executed";


    @JsonProperty("id")
    private Integer id;

    @JsonProperty("message")
    private String message;

    @JsonProperty("name")
    private String name;

    @JsonProperty("execution")
    private Date execution;

    @JsonProperty("status")
    private String type;

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

    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getExecution() {
        return execution;
    }

    @JsonDeserialize(using = JsonDateDeserializer.class)
    public void setExecution(Date execution) {
        this.execution = execution;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
