package com.interactuamovil.apps.contactosms.api.sdk;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interactuamovil.apps.contactosms.api.client.rest.contacts.ContactJsonObject;
import com.interactuamovil.apps.contactosms.api.sdk.responses.GroupResponse;
import com.interactuamovil.apps.contactosms.api.utils.ApiResponse;
import com.interactuamovil.apps.contactosms.api.utils.JsonObjectCollection;
import com.interactuamovil.contactosms.api.responses.ActionMessageResponse;
import com.interactuamovil.contactosms.api.responses.ContactResponse;
import com.interactuamovil.contactosms.api.responses.Response;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Groups extends Request {

    public Groups(String apiKey, String secretKey, String apiUri) {
        super(apiKey, secretKey, apiUri);
    }

    public ApiResponse<List<GroupResponse>> getList() {
        ApiResponse<List<GroupResponse>> response;
        List<GroupResponse> groupResponse;

        try {
            response = doRequest("groups", "get", null, null, false);
            if (response.isOk()) {
                groupResponse = JsonObjectCollection.fromJson(response.getRawResponse(), new TypeReference<List<GroupResponse>>() {});
                response.setResponse(groupResponse);            
            }
        } catch (Exception e) {
            response = new ApiResponse<List<GroupResponse>>();
            response.setErrorCode(-1);
            response.setErrorDescription(e.getMessage());
        }
        return response;
    }

    public ApiResponse<GroupResponse> get(String shortName) {
        ApiResponse<GroupResponse> response;
        GroupResponse groupResponse;
        
        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();
        if (!(shortName == null))
            urlParams.put("short_name", shortName);

        try {
            response = doRequest("groups", "get", urlParams, null, false);
            if (response.isOk()) {
                groupResponse = GroupResponse.fromJson(response.getRawResponse());
                response.setResponse(groupResponse);
            }
        } catch (Exception e) {
            response = new ApiResponse<GroupResponse>();
            response.setErrorCode(-1);
            response.setErrorDescription(e.getMessage());
        }
        return response;
    }

    public ApiResponse<GroupResponse> update(String shortName, String name, String description, String newShortName) {
        ApiResponse<GroupResponse> response;
        GroupResponse groupResponse;
        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();
        Map<String, Serializable> params = new LinkedHashMap<String, Serializable>();

        urlParams.put("short_name", shortName);

        params.put("name", name);
        params.put("description", description);
        if (!(newShortName == null))
            params.put("short_name", shortName);


        try {
            response = doRequest("groups/" + shortName, "put", urlParams, params, false);
            if (response.isOk()) {
                groupResponse = GroupResponse.fromJson(response.getRawResponse());
                response.setResponse(groupResponse);
            }
        } catch (Exception e) {
            response = new ApiResponse<GroupResponse>();
            response.setErrorCode(-1);
            response.setErrorDescription(e.getMessage());
        }
        return response;
    }

    public ApiResponse<GroupResponse> add(String shortName, String name, String description) {
        ApiResponse<GroupResponse> response;
        GroupResponse groupResponse;
        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();
        Map<String, Serializable> params = new LinkedHashMap<String, Serializable>();

        urlParams.put("short_name", shortName);

        params.put("name", name);
        params.put("description", description);
        params.put("short_name", shortName);


        try {
            response = doRequest("groups/" + shortName, "post", urlParams, params, false);
            if (response.isOk()) {
                groupResponse = GroupResponse.fromJson(response.getRawResponse());
                response.setResponse(groupResponse);
            }
        } catch (Exception e) {
            response = new ApiResponse<GroupResponse>();
            response.setErrorCode(-1);
            response.setErrorDescription(e.getMessage());
        }
        return response;
    }

    public ApiResponse<GroupResponse> delete(String shortName) {
        ApiResponse<GroupResponse> response;
        GroupResponse groupResponse;
        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();

        urlParams.put("short_name", shortName);

        try {
            response = doRequest("groups/" + shortName, "put", urlParams, null, false);
            if (response.isOk()) {
                groupResponse = GroupResponse.fromJson(response.getRawResponse());
                response.setResponse(groupResponse);
            }
        } catch (Exception e) {
            response = new ApiResponse<GroupResponse>();
            response.setErrorCode(-1);
            response.setErrorDescription(e.getMessage());
        }
        return response;
    }

    public ApiResponse<List<ContactJsonObject>> getContactList(String shortName) {
        ApiResponse<List<ContactJsonObject>> response;
        List<ContactJsonObject> contactResponses;
        
        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();

        urlParams.put("short_name", shortName);

        try {
            response = doRequest("groups/" + shortName + "/contacts", "get", urlParams, null, false);
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
