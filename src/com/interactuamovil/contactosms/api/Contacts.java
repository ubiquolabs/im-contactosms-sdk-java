package com.interactuamovil.contactosms.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interactuamovil.contactosms.api.responses.ActionMessageResponse;
import com.interactuamovil.contactosms.api.responses.ContactResponse;
import com.interactuamovil.contactosms.api.responses.GroupResponse;
import com.interactuamovil.contactosms.api.responses.ListResponse;
import com.interactuamovil.contactosms.api.responses.Response;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Contacts extends Request {

    private static final Logger logger = Logger.getLogger(Contacts.class);

    public Contacts(String apiKey, String secretKey, String apiUri) {
        super(apiKey, secretKey, apiUri);
    }

    /**
     * Validates de Status sent
     *
     * @param type
     * @return
     */
    private static Integer decodeStatus(int type) {
        int result;

        switch (type) {
            case 0:
                result = 0;
                break;
            case 1:
                result = 1;
                break;
            case 2:
                result = 2;
                break;
            default:
                result = -1;
        }
        return result;
    }

    /**
     * Gets a contact list
     *
     * @param start
     * @param limit
     * @param firstName
     * @param lastName
     * @param statusType
     * @return
     * @throws IOException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public ListResponse<ContactResponse> getList(@Nullable Integer statusType, @Nullable String firstName, @Nullable String lastName, @Nullable Integer start, @Nullable Integer limit) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        int status = decodeStatus(statusType);
        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();
        if (status != -1)
            urlParams.put("status", status);
        if (firstName != null)
            urlParams.put("first_name", firstName);
        if (lastName != null)
            urlParams.put("last_name", lastName);
        if (limit != 0) {
            urlParams.put("limit", limit);
            urlParams.put("start", start);
        }

        ListResponse<ContactResponse> response = new ListResponse<ContactResponse>();
        List<ContactResponse> contactResponse;

        try {
            String serverResponse = doRequest("contacts", "get", urlParams, null, true);
            ObjectMapper mapper = new ObjectMapper();

            contactResponse = mapper.readValue(serverResponse, List.class);
            response.setResult(contactResponse);
        } catch (Exception e) {
            response.setHasError(true);
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    public ListResponse<ContactResponse> getList(@NotNull Integer statusType) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        return getList(statusType, null, null, null, null);
    }

    /**
     * Gets a contact by its msisdn
     *
     * @param msisdn
     * @return
     * @throws IOException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public Response<ContactResponse> getByMsisdn(String msisdn) throws IOException, InvalidKeyException, NoSuchAlgorithmException {

        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();
        urlParams.put("msisdn", msisdn);
        Response<ContactResponse> response = new Response<ContactResponse>();
        ContactResponse contactResponse;

        try {
            String serverResponse = doRequest("contacts/" + msisdn, "get", urlParams, null, false);
            ObjectMapper mapper = new ObjectMapper();

            contactResponse = mapper.readValue(serverResponse, ContactResponse.class);
            response.setResult(contactResponse);
        } catch (Exception e) {
            response.setError(true);
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    /**
     * Updates a contact base on its msisdn
     *
     * @param msisdn
     * @param firstName
     * @param lastName
     * @param newMsisdn
     * @return
     * @throws IOException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public Response<ActionMessageResponse> update(@NotNull String msisdn, @Nullable String firstName, @Nullable String lastName, @Nullable String newMsisdn) throws IOException, InvalidKeyException, NoSuchAlgorithmException {

        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();
        Map<String, Serializable> params = new LinkedHashMap<String, Serializable>();

        urlParams.put("msisdn", msisdn);

        if (firstName != null) {
            params.put("first_name", firstName);
        }
        if (lastName != null) {
            params.put("last_name", lastName);
        }
        if (newMsisdn != null) {
            params.put("msisdn", newMsisdn);
        }

        Response<ActionMessageResponse> response = new Response<ActionMessageResponse>();
        ActionMessageResponse messageResponse;

        try {
            String serverResponse = doRequest("contacts/" + msisdn, "put", urlParams, params, false);
            ObjectMapper mapper = new ObjectMapper();

            messageResponse = mapper.readValue(serverResponse, ActionMessageResponse.class);
            response.setResult(messageResponse);
        } catch (Exception e) {
            response.setError(true);
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    /**
     * Adds a new contact
     *
     * @param msisdn
     * @param firstName
     * @param lastName
     * @return
     * @throws IOException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public Response<ActionMessageResponse> add(String msisdn, @Nullable String firstName, @Nullable String lastName) throws IOException, InvalidKeyException, NoSuchAlgorithmException {

        logger.debug(String.format("Adding contact with msisdn: %s, firstName: %s, lastName: %s", msisdn, firstName, lastName));

        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();
        Map<String, Serializable> params = new LinkedHashMap<String, Serializable>();

        urlParams.put("msisdn", msisdn);

        if (msisdn != null) {
            params.put("msisdn", msisdn);
        }
        if (firstName != null) {
            params.put("first_name", firstName);
        }
        if (lastName != null) {
            params.put("last_name", lastName);
        }

        Response<ActionMessageResponse> response = new Response<ActionMessageResponse>();
        ActionMessageResponse messageResponse;

        try {
            String serverResponse = doRequest("contacts/" + msisdn, "post", urlParams, params, false);
            ObjectMapper mapper = new ObjectMapper();

            messageResponse = mapper.readValue(serverResponse, ActionMessageResponse.class);
            response.setResult(messageResponse);
        } catch (Exception e) {
            response.setError(true);
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    public Response<ActionMessageResponse> add(String msisdn) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        return add(msisdn, null, null);
    }

    /**
     * Deletes a contact
     *
     * @param msisdn
     * @return
     * @throws IOException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public Response<ActionMessageResponse> delete(String msisdn) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();
        urlParams.put("msisdn", msisdn);
        Response<ActionMessageResponse> response = new Response<ActionMessageResponse>();
        ActionMessageResponse messageResponse;

        try {
            String serverResponse = doRequest("contacts/" + msisdn, "delete", urlParams, null, false);
            ObjectMapper mapper = new ObjectMapper();

            messageResponse = mapper.readValue(serverResponse, ActionMessageResponse.class);
            response.setResult(messageResponse);
        } catch (Exception e) {
            response.setError(true);
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    /**
     * Gets contact's group list
     *
     * @param msisdn
     * @return
     * @throws IOException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public ListResponse<GroupResponse> getGroupList(String msisdn) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();
        urlParams.put("msisdn", msisdn);
        ListResponse<GroupResponse> response = new ListResponse<GroupResponse>();
        List<GroupResponse> groupResponse;

        try {
            String serverResponse = doRequest("contacts/" + msisdn + "/groups", "get", urlParams, null, false);
            ObjectMapper mapper = new ObjectMapper();

            groupResponse = mapper.readValue(serverResponse, List.class);
            response.setResult(groupResponse);
        } catch (Exception e) {
            response.setHasError(true);
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }
}