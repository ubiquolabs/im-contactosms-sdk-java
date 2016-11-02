package com.interactuamovil.apps.contactosms.api.client.rest.templates;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.interactuamovil.apps.contactosms.api.utils.JsonObject;

import java.util.List;

/**
 * Created by javier on 11/12/15.
 * SendTemplate json representation to call the API
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SendTemplateJson extends JsonObject {

    @JsonProperty("id")
    private Integer templateId;

    @JsonProperty("fields")
    private List<TemplateJson.Field> fields;

    /**
     * Can be SINGLE or GROUP
     */
    @JsonProperty("destination")
    private String destination;

    @JsonProperty("msisdn")
    private String msisdn;

    private List<String> groups;

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public List<TemplateJson.Field> getFields() {
        return fields;
    }

    public void setFields(List<TemplateJson.Field> fields) {
        this.fields = fields;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }
}
