package com.interactuamovil.apps.contactosms.api.sdk;

import com.fasterxml.jackson.core.type.TypeReference;
import com.interactuamovil.apps.contactosms.api.client.rest.contacts.ContactJsonObject;
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

    public ApiResponse<List<TagResponse>> getList() {
        ApiResponse<List<TagResponse>> response;
        List<TagResponse> groupResponse;
        
        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();

        try {
            response = doRequest("tags", "get", urlParams, null, true);
            if (response.isOk()) {
                groupResponse = JsonObjectCollection.fromJson(response.getRawResponse(), new TypeReference<List<TagResponse>>() {});
                response.setResponse(groupResponse);            
            }
        } catch (Exception e) {
            response = new ApiResponse<List<TagResponse>>();
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
        urlParams.put("short_name", name);
                
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

    public ApiResponse<ContactJsonObject> addContact(String shortName, String msisdn) {
        ApiResponse<ContactJsonObject> response;
        ContactJsonObject contactResponse;
        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();

        urlParams.put("short_name", shortName);
        urlParams.put("msisdn", msisdn);

        try {
            response = doRequest("groups/" + shortName + "/contacts/" + msisdn, "post", urlParams, null, false);
            if (response.isOk()) {
                contactResponse = ContactJsonObject.fromJson(response.getRawResponse());
                response.setResponse(contactResponse);
            }
        } catch (Exception e) {
            response = new ApiResponse<ContactJsonObject>();
            response.setErrorCode(-1);
            response.setErrorDescription(e.getMessage());
        }
        return response;
    }

    public ApiResponse<ContactJsonObject> removeContact(String shortName, String msisdn) {
        ApiResponse<ContactJsonObject> response;
        ContactJsonObject contactResponse;
        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();

        urlParams.put("short_name", shortName);
        urlParams.put("msisdn", msisdn);

        try {
            response = doRequest("groups/" + shortName + "/contacts/" + msisdn, "delete", urlParams, null, false);
            if (response.isOk()) {
                contactResponse = ContactJsonObject.fromJson(response.getRawResponse());
                response.setResponse(contactResponse);
            }
        } catch (Exception e) {
            response = new ApiResponse<ContactJsonObject>();
            response.setErrorCode(-1);
            response.setErrorDescription(e.getMessage());
        }
        return response;
    }

}
