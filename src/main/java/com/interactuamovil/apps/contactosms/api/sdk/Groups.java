package com.interactuamovil.apps.contactosms.api.sdk;

import com.fasterxml.jackson.core.type.TypeReference;
import com.interactuamovil.apps.contactosms.api.client.rest.contacts.ContactJsonObject;
import com.interactuamovil.apps.contactosms.api.client.rest.groups.GroupJsonObject;
import com.interactuamovil.apps.contactosms.api.sdk.responses.GroupResponse;
import com.interactuamovil.apps.contactosms.api.utils.ApiResponse;
import com.interactuamovil.apps.contactosms.api.utils.JsonObjectCollection;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Groups extends Request {

    public Groups(String apiKey, String secretKey, String apiUri) {
        super(apiKey, secretKey, apiUri);
    }

    public ApiResponse<List<GroupResponse>> getList() {
        return getList(null);
    }
    
    public ApiResponse<List<GroupResponse>> getList(String query) {
        return getList(query, 50);
    }
    
    public ApiResponse<List<GroupResponse>> getList(String query, Integer limit) {
        return getList(query, 0, limit);
    }
    
    public ApiResponse<List<GroupResponse>> getList(String query, Integer start, Integer limit) {
        return getList(query, start, limit, Boolean.FALSE);
    }
    
    public ApiResponse<List<GroupResponse>> getList(String query, Integer start, Integer limit, boolean includeRecipients) {
        ApiResponse<List<GroupResponse>> response;
        List<GroupResponse> groupResponse;
        
        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();
        if (query != null)
            urlParams.put("query", query);
        if (start != null && start >= 0)
            urlParams.put("start", start);
        if (limit != null && limit != 0) {
            urlParams.put("limit", limit);            
        }
        if (includeRecipients) {
            urlParams.put("include_recipients", "true");
        }

        try {
            response = doRequest("groups", "get", urlParams, null, true);
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
            response = doRequest("groups/"+shortName, "get", urlParams, null, false);
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
        GroupJsonObject group = new GroupJsonObject();
        if (newShortName != null && !newShortName.trim().isEmpty()) {
            group.setSmsShortName(newShortName);
        }
        group.setName(name);
        group.setDescription(description);
        
        return update(shortName, group);
    }
    
    public ApiResponse<GroupResponse> update(String shortName, GroupJsonObject group) {
        ApiResponse<GroupResponse> response;
        GroupResponse groupResponse;
        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();        

        urlParams.put("short_name", shortName);        

        try {
            response = doRequest("groups/" + shortName, "put", urlParams, group, false);
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
        GroupJsonObject group = new GroupJsonObject();
        group.setSmsShortName(shortName);
        group.setName(name);
        group.setDescription(description);       
        
        return add(group);
    }
    
    public ApiResponse<GroupResponse> add(GroupJsonObject group) {
        ApiResponse<GroupResponse> response;
        GroupResponse groupResponse;
        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();

        urlParams.put("short_name", group.getSmsShortName());
       
        try {
            response = doRequest("groups/" + group.getSmsShortName(), "post", urlParams, group, false);
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
            response = doRequest("groups/" + shortName, "delete", urlParams, null, false);
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
        return getContactList(shortName, 50);
    }
    
    public ApiResponse<List<ContactJsonObject>> getContactList(String shortName, Integer limit) {
        return getContactList(shortName, 0, limit);
    }
    
    public ApiResponse<List<ContactJsonObject>> getContactList(String shortName, Integer start, Integer limit) {
        ApiResponse<List<ContactJsonObject>> response;
        List<ContactJsonObject> contactResponses;
        
        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();
        urlParams.put("short_name", shortName);
                
        if (start != null && start >= 0)
            urlParams.put("start", start);
        if (limit != null && limit != 0) {
            urlParams.put("limit", limit);            
        }

        try {
            response = doRequest("groups/" + shortName + "/contacts", "get", urlParams, null, true);
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
    
    public ApiResponse<List<ContactJsonObject>> getAvailableContactList(String shortName) {
        return getAvailableContactList(shortName, 50);
    }
    
    public ApiResponse<List<ContactJsonObject>> getAvailableContactList(String shortName, Integer limit) {
        return getAvailableContactList(shortName, 0, limit);
    }
    
    public ApiResponse<List<ContactJsonObject>> getAvailableContactList(String shortName, Integer start, Integer limit) {
        ApiResponse<List<ContactJsonObject>> response;
        List<ContactJsonObject> contactResponses;
        
        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();

        urlParams.put("short_name", shortName);

        if (start != null && start >= 0)
            urlParams.put("start", start);
        if (limit != null && limit != 0) {
            urlParams.put("limit", limit);            
        }
        
        try {
            response = doRequest("groups/" + shortName + "/available_contacts", "get", urlParams, null, true);
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
