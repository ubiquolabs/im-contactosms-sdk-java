package com.interactuamovil.apps.contactosms.api.sdk;

import com.fasterxml.jackson.core.type.TypeReference;
import com.interactuamovil.apps.contactosms.api.client.rest.contacts.ContactJsonObject;
import com.interactuamovil.apps.contactosms.api.client.rest.groups.GroupJsonObject;
import com.interactuamovil.apps.contactosms.api.enums.AddedFrom;
import com.interactuamovil.apps.contactosms.api.enums.ContactStatus;
import com.interactuamovil.apps.contactosms.api.sdk.responses.GroupResponse;
import com.interactuamovil.apps.contactosms.api.utils.ApiResponse;
import com.interactuamovil.apps.contactosms.api.utils.JsonObjectCollection;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

public class Contacts extends Request {

    private static final Logger logger = Logger.getLogger(Contacts.class);

    public Contacts(String apiKey, String secretKey, String apiUri) {
        super(apiKey, secretKey, apiUri);
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
    public ApiResponse<List<ContactJsonObject>> getList(List<ContactStatus> contactStatuses, String query, Integer start, Integer limit, boolean shortResults) throws IOException, InvalidKeyException, NoSuchAlgorithmException {        
        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();
        if (contactStatuses != null && !contactStatuses.isEmpty()) {            
            urlParams.put("status", StringUtils.join(contactStatuses, ","));
        }
        if (query != null)
            urlParams.put("query", query);
        if (start != null && start >= 0)
            urlParams.put("start", start);
        if (limit != null && limit != 0) {
            urlParams.put("limit", limit);            
        }
        if (shortResults) {
            urlParams.put("short_results", "true");
        }

        ApiResponse<List<ContactJsonObject>> response;
        List<ContactJsonObject> contactResponses;

        try {
            response = doRequest("contacts", "get", urlParams, null, true);
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

    public ApiResponse<List<ContactJsonObject>> getList(List<ContactStatus> contactStatuses) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        return getList(contactStatuses, null, null, null, Boolean.FALSE);
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
    public ApiResponse<ContactJsonObject> getByMsisdn(String msisdn) throws IOException, InvalidKeyException, NoSuchAlgorithmException {

        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();
        urlParams.put("msisdn", msisdn);
        ApiResponse<ContactJsonObject> response;
        ContactJsonObject contactResponse;

        try {
            response = doRequest("contacts/" + msisdn, "get", urlParams, null, false);
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
    public ApiResponse<ContactJsonObject> update(String countryCode, String msisdn, String firstName, String lastName) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        
        ContactJsonObject contact = new ContactJsonObject();
        contact.setCountryCode(countryCode);
        contact.setMsisdn(msisdn);
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        
        return update(contact);
    }
    
    public ApiResponse<ContactJsonObject> update(ContactJsonObject contact) throws IOException, InvalidKeyException, NoSuchAlgorithmException {

        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();
        Map<String, Serializable> params = new LinkedHashMap<String, Serializable>();

        urlParams.put("msisdn", contact.getMsisdn());
        
        ApiResponse<ContactJsonObject> response;
        ContactJsonObject contactResponse;

        try {
            response = doRequest("contacts/" + contact.getMsisdn(), "put", urlParams, contact, false);
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
    public ApiResponse<ContactJsonObject> add(String countryCode, String msisdn, String firstName, String lastName) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        logger.debug(String.format("Adding contact with countryCode: %s, msisdn: %s, firstName: %s, lastName: %s", countryCode, msisdn, firstName, lastName));

        ContactJsonObject contact = new ContactJsonObject();
        contact.setCountryCode(countryCode);
        contact.setMsisdn(msisdn);
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        
        contact.setAddedFrom(AddedFrom.API);
        
        return add(contact);
    }

    public ApiResponse<ContactJsonObject> add(String countryCode, String msisdn) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        return add(countryCode, msisdn, null, null);
    }
    
    public ApiResponse<ContactJsonObject> add(ContactJsonObject contact) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        logger.debug(String.format("Adding contact"));

        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();
        Map<String, Serializable> params = new LinkedHashMap<String, Serializable>();

        urlParams.put("msisdn", contact.getMsisdn());

        ApiResponse<ContactJsonObject> response;
        ContactJsonObject contactResponse;

        try {
            response = doRequest("contacts/" + contact.getMsisdn(), "post", urlParams, contact, false);
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

    /**
     * Deletes a contact
     *
     * @param msisdn
     * @return
     * @throws IOException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public ApiResponse<ContactJsonObject> delete(String msisdn) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();
        urlParams.put("msisdn", msisdn);
        ApiResponse<ContactJsonObject> response;
        ContactJsonObject contactResponse;

        try {
            response = doRequest("contacts/" + msisdn, "delete", urlParams, null, false);
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

    /**
     * Gets contact's group list
     *
     * @param msisdn
     * @return
     * @throws IOException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public ApiResponse<List<GroupJsonObject>> getGroupList(String msisdn) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();
        urlParams.put("msisdn", msisdn);
        
        ApiResponse<List<GroupJsonObject>> response;
        List<GroupJsonObject> groupResponse;

        try {
            response = doRequest("contacts/" + msisdn + "/groups", "get", urlParams, null, false);
            if (response.isOk()) {
                groupResponse = JsonObjectCollection.fromJson(response.getRawResponse(), new TypeReference<List<GroupJsonObject>>() {});
                response.setResponse(groupResponse);            
            }
        } catch (Exception e) {
            response = new ApiResponse<List<GroupJsonObject>>();
            response.setErrorCode(-1);
            response.setErrorDescription(e.getMessage());
        }
        return response;
    }
}
