/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interactuamovil.apps.contactosms.api.client.rest.tags;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.interactuamovil.apps.contactosms.api.utils.JsonObject;

import java.io.IOException;

/**
 *
 * @author sergeiw
 */
public class TagJsonObject extends JsonObject  {


    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "count")
    private Integer count;

    public static TagJsonObject fromJson(String json) throws IOException {
        return JsonObject.fromJson(json, TagJsonObject.class);
    }

    public TagJsonObject() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    
}
