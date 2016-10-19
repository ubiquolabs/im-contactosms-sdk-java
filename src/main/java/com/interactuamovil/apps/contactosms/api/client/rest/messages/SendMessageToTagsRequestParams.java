/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interactuamovil.apps.contactosms.api.client.rest.messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.interactuamovil.apps.contactosms.api.utils.JsonObject;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author sergeiw
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class SendMessageToTagsRequestParams extends JsonObject {
    
    @JsonProperty(value="tags")
    private List<String> tags;
    @JsonProperty(value="message") 
    private String message;
    @JsonProperty(value="id") 
    private String clientMessageId;
    @JsonProperty(value="from_file")
    private String fromFile;

    /**
     * @return the tags
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
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
    
    public static SendMessageToTagsRequestParams fromJson(String json) throws IOException {
        return JsonObject.fromJson(json, SendMessageToTagsRequestParams.class);
    }
    
    @JsonIgnore
    public String getTagNames() {
        List<String> grpList = getTags();
        String tagNames = grpList.stream().collect(Collectors.joining(","));

        return tagNames;
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

    public String getFromFile() {
        return fromFile;
    }

    public void setFromFile(String fromFile) {
        this.fromFile = fromFile;
    }
}
