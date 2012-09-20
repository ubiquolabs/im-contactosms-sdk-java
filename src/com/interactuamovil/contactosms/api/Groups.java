package com.interactuamovil.contactosms.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interactuamovil.contactosms.api.responses.ActionMessageResponse;
import com.interactuamovil.contactosms.api.responses.ContactResponse;
import com.interactuamovil.contactosms.api.responses.GroupResponse;
import com.interactuamovil.contactosms.api.responses.ListResponse;
import com.interactuamovil.contactosms.api.responses.Response;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Groups extends Request {

    public Groups(String apiKey, String secretKey, String apiUri) {
        super(apiKey, secretKey, apiUri);
    }

    public ListResponse<GroupResponse> getList() {
        ListResponse<GroupResponse> response = new ListResponse<GroupResponse>();
        List<GroupResponse> groupResponse;

        try {
            String serverResponse = doRequest("groups", "get", null, null, false);
            ObjectMapper mapper = new ObjectMapper();

            groupResponse = mapper.readValue(serverResponse, List.class);
            response.setResult(groupResponse);
        } catch (Exception e) {
            response.setHasError(true);
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    public Response<GroupResponse> get(String shortName) {
        Response<GroupResponse> response = new Response<GroupResponse>();
        GroupResponse groupResponse;
        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();
        if (!(shortName == null))
            urlParams.put("short_name", shortName);

        try {
            String serverResponse = doRequest("groups", "get", urlParams, null, false);
            ObjectMapper mapper = new ObjectMapper();

            groupResponse = mapper.readValue(serverResponse, GroupResponse.class);
            response.setResult(groupResponse);
        } catch (Exception e) {
            response.setError(true);
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    public Response<ActionMessageResponse> update(String shortName, String name, String description, String newShortName) {
        Response<ActionMessageResponse> response = new Response<ActionMessageResponse>();
        ActionMessageResponse messageResponse;
        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();
        Map<String, Serializable> params = new LinkedHashMap<String, Serializable>();

        urlParams.put("short_name", shortName);

        params.put("name", name);
        params.put("description", description);
        if (!(newShortName == null))
            params.put("short_name", shortName);


        try {
            String serverResponse = doRequest("groups/" + shortName, "put", urlParams, params, false);
            ObjectMapper mapper = new ObjectMapper();

            messageResponse = mapper.readValue(serverResponse, ActionMessageResponse.class);
            response.setResult(messageResponse);
        } catch (Exception e) {
            response.setError(true);
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    public Response<ActionMessageResponse> add(String shortName, String name, String description) {
        Response<ActionMessageResponse> response = new Response<ActionMessageResponse>();
        ActionMessageResponse messageResponse;
        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();
        Map<String, Serializable> params = new LinkedHashMap<String, Serializable>();

        urlParams.put("short_name", shortName);

        params.put("name", name);
        params.put("description", description);
        params.put("short_name", shortName);


        try {
            String serverResponse = doRequest("groups/" + shortName, "post", urlParams, params, false);
            ObjectMapper mapper = new ObjectMapper();

            messageResponse = mapper.readValue(serverResponse, ActionMessageResponse.class);
            response.setResult(messageResponse);
        } catch (Exception e) {
            response.setError(true);
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    public Response<ActionMessageResponse> delete(String shortName) {
        Response<ActionMessageResponse> response = new Response<ActionMessageResponse>();
        ActionMessageResponse messageResponse;
        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();

        urlParams.put("short_name", shortName);

        try {
            String serverResponse = doRequest("groups/" + shortName, "put", urlParams, null, false);
            ObjectMapper mapper = new ObjectMapper();

            messageResponse = mapper.readValue(serverResponse, ActionMessageResponse.class);
            response.setResult(messageResponse);
        } catch (Exception e) {
            response.setError(true);
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    public Response<ContactResponse> getContactList(String shortName) {
        Response<ContactResponse> response = new Response<ContactResponse>();
        ContactResponse contactResponse;
        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();

        urlParams.put("short_name", shortName);

        try {
            String serverResponse = doRequest("groups/" + shortName + "/contacts", "get", urlParams, null, false);
            ObjectMapper mapper = new ObjectMapper();

            contactResponse = mapper.readValue(serverResponse, ContactResponse.class);
            response.setResult(contactResponse);
        } catch (Exception e) {
            response.setError(true);
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    public Response<ActionMessageResponse> addContact(String shortName, String msisdn) {
        Response<ActionMessageResponse> response = new Response<ActionMessageResponse>();
        ActionMessageResponse messageResponse;
        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();

        urlParams.put("short_name", shortName);
        urlParams.put("msisdn", msisdn);

        try {
            String serverResponse = doRequest("groups/" + shortName + "/contacts/" + msisdn, "post", urlParams, null, false);
            ObjectMapper mapper = new ObjectMapper();

            messageResponse = mapper.readValue(serverResponse, ActionMessageResponse.class);
            response.setResult(messageResponse);
        } catch (Exception e) {
            response.setError(true);
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    public Response<ActionMessageResponse> removeContact(String shortName, String msisdn) {
        Response<ActionMessageResponse> response = new Response<ActionMessageResponse>();
        ActionMessageResponse messageResponse;
        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();

        urlParams.put("short_name", shortName);
        urlParams.put("msisdn", msisdn);

        try {
            String serverResponse = doRequest("groups/" + shortName + "/contacts/" + msisdn, "delete", urlParams, null, false);
            ObjectMapper mapper = new ObjectMapper();

            messageResponse = mapper.readValue(serverResponse, ActionMessageResponse.class);
            response.setResult(messageResponse);
        } catch (Exception e) {
            response.setError(true);
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

}