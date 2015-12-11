package com.interactuamovil.apps.contactosms.api.client.rest.templates;

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
 * Created by javier on 8/12/15.
 * Template json representation
 * Subject to availability. Check your provider's documentation
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TemplateJson  extends JsonObject{

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("description")
    private String description;

    @JsonProperty("message")
    private String message;

    @JsonProperty("created_on")
    private Date createdOn;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("updated_on")
    private Date updatedOn;

    @JsonProperty("updated_by")
    private String updatedBy;

    @JsonProperty("fields")
    private List<Field> fields;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    public Date getCreatedOn() {
        return createdOn;
    }

    @JsonSerialize(using = JsonDateTimeSerializer.class)
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public static class Field{
        @JsonProperty("name")
        private String name;
        @JsonProperty("value")
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
