/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interactuamovil.apps.contactosms.api.client.rest.groups;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.interactuamovil.apps.contactosms.api.utils.JsonObject;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author sergeiw
 */
public class GroupJsonObject extends JsonObject  {
    
    @JsonProperty(value = "short_name")
    private String smsShortName;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "description")
    private String description;
    @JsonProperty(value = "tags")
    private List<String> tags;
    @JsonProperty(value = "members")    
    private GroupMembersJsonObject members;

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

    
    public class GroupMembersJsonObject extends JsonObject  {
        
        @JsonProperty(value = "total")
        private Integer total;
        @JsonProperty(value = "pending")
        private Integer pending;
        @JsonProperty(value = "confirmed")
        private Integer confirmed;

        public GroupMembersJsonObject() {
        }
        
        /**
         * @return the total
         */
        public Integer getTotal() {
            return total;
        }

        /**
         * @param total the total to set
         */
        public void setTotal(Integer total) {
            this.total = total;
        }

        /**
         * @return the pending
         */
        public Integer getPending() {
            return pending;
        }

        /**
         * @param pending the pending to set
         */
        public void setPending(Integer pending) {
            this.pending = pending;
        }

        /**
         * @return the confirmed
         */
        public Integer getConfirmed() {
            return confirmed;
        }

        /**
         * @param confirmed the confirmed to set
         */
        public void setConfirmed(Integer confirmed) {
            this.confirmed = confirmed;
        }
        
    }
    
    public static GroupJsonObject fromJson(String json) throws IOException {
        return JsonObject.fromJson(json, GroupJsonObject.class);        
    }        

    public GroupJsonObject() {
    }
    
    
    /**
     * @return the smsShortName
     */
    public String getSmsShortName() {
        return smsShortName;
    }

    /**
     * @param smsShortName the smsShortName to set
     */
    public void setSmsShortName(String smsShortName) {
        this.smsShortName = smsShortName;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the members
     */
    public GroupMembersJsonObject getMembers() {
        if (members == null) {
            members = new GroupMembersJsonObject();
        }
        return members;
    }

    /**
     * @param members the members to set
     */
    public void setMembers(GroupMembersJsonObject members) {
        this.members = members;
    }
    
    
    
}
