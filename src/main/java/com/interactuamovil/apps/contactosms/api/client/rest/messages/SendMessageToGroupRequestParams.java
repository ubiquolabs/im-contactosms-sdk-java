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
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author sergeiw
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class SendMessageToGroupRequestParams extends JsonObject {
    
    @JsonProperty(value="groups") 
    private List<String> groups;
    @JsonProperty(value="message") 
    private String message;
    @JsonProperty(value="id") 
    private String clientMessageId;

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
    
    public static SendMessageToGroupRequestParams fromJson(String json) throws IOException {
        return JsonObject.fromJson(json, SendMessageToGroupRequestParams.class);        
    }
    
    @JsonIgnore
    public String getGroupsNames() {
        List<String> grpList = getGroups();
        String[] grpArr = new String[grpList.size()];
        grpArr = grpList.toArray(grpArr);
        String groupNames = StringUtils.join(grpArr, ",");
        return groupNames;
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
    
}
