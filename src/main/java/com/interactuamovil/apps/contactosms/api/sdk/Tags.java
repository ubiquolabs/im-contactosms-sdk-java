package com.interactuamovil.apps.contactosms.api.sdk;

import com.fasterxml.jackson.core.type.TypeReference;
import com.interactuamovil.apps.contactosms.api.client.rest.contacts.ContactJsonObject;
import com.interactuamovil.apps.contactosms.api.client.rest.tags.TagJsonObject;
import com.interactuamovil.apps.contactosms.api.sdk.responses.TagResponse;
import com.interactuamovil.apps.contactosms.api.utils.ApiResponse;
import com.interactuamovil.apps.contactosms.api.utils.JsonObjectCollection;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Tags extends Request {

    public Tags(String apiKey, String secretKey, String apiUri) {
        super(apiKey, secretKey, apiUri);
    }

    public ApiResponse<List<TagJsonObject>> getList() {
        ApiResponse<List<TagJsonObject>> response;
        List<TagJsonObject> tagResponse;
        
        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();

        try {
            response = doRequest("tags", "get", urlParams, null, true);
            if (response.isOk()) {
                tagResponse = JsonObjectCollection.fromJson(response.getRawResponse(), new TypeReference<List<TagJsonObject>>() {});
                response.setResponse(tagResponse);
            }
        } catch (Exception e) {
            response = new ApiResponse<>();
            response.setErrorCode(-1);
            response.setErrorDescription(e.getMessage());
        }
        return response;
    }

    public ApiResponse<TagJsonObject> getTag(String tagName) {
        ApiResponse<TagJsonObject> response;
        TagJsonObject tagResponse;

        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();
        urlParams.put("tag_name", tagName);

        try {
            response = doRequest("tags/"+tagName, "get", urlParams, null, true);
            if (response.isOk()) {
                tagResponse = TagJsonObject.fromJson(response.getRawResponse());
                response.setResponse(tagResponse);
            }
        } catch (Exception e) {
            response = new ApiResponse<>();
            response.setErrorCode(-1);
            response.setErrorDescription(e.getMessage());
        }
        return response;
    }

    public ApiResponse<TagJsonObject> deleteTag(String tagName) {
        ApiResponse<TagJsonObject> response;
        TagJsonObject tagResponse;

        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();
        urlParams.put("tag_name", tagName);

        try {
            response = doRequest("tags/"+tagName, "delete", urlParams, null, true);
            if (response.isOk()) {
                tagResponse = TagJsonObject.fromJson(response.getRawResponse());
                response.setResponse(tagResponse);
            }
        } catch (Exception e) {
            response = new ApiResponse<>();
            response.setErrorCode(-1);
            response.setErrorDescription(e.getMessage());
        }
        return response;
    }

    public ApiResponse<List<ContactJsonObject>> getContactList(String name) {
        return getContactList(name, 50);
    }
    
    public ApiResponse<List<ContactJsonObject>> getContactList(String name, Integer limit) {
        return getContactList(name, 0, limit);
    }
    
    public ApiResponse<List<ContactJsonObject>> getContactList(String name, Integer start, Integer limit) {
        ApiResponse<List<ContactJsonObject>> response;
        List<ContactJsonObject> contactResponses;
        
        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();
        urlParams.put("tag_name", name);
                
        if (start != null && start >= 0)
            urlParams.put("start", start);
        if (limit != null && limit != 0) {
            urlParams.put("limit", limit);            
        }

        try {
            response = doRequest("tags/" + name + "/contacts", "get", urlParams, null, true);
            if (response.isOk()) {
                contactResponses = JsonObjectCollection.fromJson(response.getRawResponse(), new TypeReference<List<ContactJsonObject>>() {});
                response.setResponse(contactResponses);
            }
        } catch (Exception e) {
            response = new ApiResponse<List<ContactJsonObject>>();
            response.setErrorCode(-1);
            response.setErrorDescription(e.getMessage());
        }
        return response;
    }



}
