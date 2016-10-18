package com.interactuamovil.apps.contactosms.api.sdk.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.interactuamovil.apps.contactosms.api.utils.JsonObject;

import java.io.IOException;

public class TagResponse extends JsonObject {

    private String name;
    private int count;

    public void setName(String s) {
        name = s;
    }
    public String getName() {
        return name;
    }

    public void setCount(int i) {
        count = i;
    }
    public int getCount() {
        return count;
    }

    public static TagResponse fromJson(String json) throws IOException {
        return JsonObject.fromJson(json, TagResponse.class);
    }


}
