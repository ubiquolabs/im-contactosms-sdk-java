package com.interactuamovil.apps.contactosms.api.sdk;

import com.fasterxml.jackson.core.type.TypeReference;
import com.interactuamovil.apps.contactosms.api.client.rest.contacts.ContactJsonObject;
import com.interactuamovil.apps.contactosms.api.client.rest.tags.TagJsonObject;
import com.interactuamovil.apps.contactosms.api.enums.AddedFrom;
import com.interactuamovil.apps.contactosms.api.enums.ContactStatus;
import com.interactuamovil.apps.contactosms.api.utils.ApiResponse;
import com.interactuamovil.apps.contactosms.api.utils.JsonObjectCollection;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Contacts extends Request {

    private static final Logger logger = LoggerFactory.getLogger(Contacts.class);

    public Contacts(String apiKey, String secretKey, String apiUri) {
        super(apiKey, secretKey, apiUri);
    }

    /**
     * Get the contacts list
	 *
     * @param contactStatuses The contact status to find
     * @param query The query string
     * @param start The offset
     * @param limit The limit of results
     * @param shortResults true to show short results
     * @return Lists of contacts
     * @throws IOException IOException
     * @throws InvalidKeyException InvalidKeyException
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
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
     * Udpates a contact with the specified msisdn
     * @param countryCode The country code e.g. 502
     * @param msisdn The msisdn i.e. The phone number with the country code
     * @param firstName The contact's first name
     * @param lastName The contact's last name
     * @return The updated contact
     * @throws IOException IOException
     * @throws InvalidKeyException InvalidKeyException
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
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
     * Add a contact
     * @param countryCode the contact country code
     * @param msisdn The msisdn. i.e. the phone number including the country code
     * @param firstName The first name
     * @param lastName The last name
     * @return The contact added
     * @throws IOException IOException
     * @throws InvalidKeyException InvalidKeyException
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
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

    /**
     * Adds a contact
     * @param countryCode The country code
     * @param msisdn The msisdn
     * @return The new contact
     * @throws IOException IOException
     * @throws InvalidKeyException InvalidKeyException
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     */
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
     * @param msisdn The msisdn
     * @return the contact just deleted
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
     * Gets contact's tag list
     *
     * @param msisdn The msisdn
     * @return The list of groups
     * @throws IOException IOException
     * @throws InvalidKeyException InvalidKeyException
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     */
    public ApiResponse<List<TagJsonObject>> getTagList(String msisdn) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();
        urlParams.put("msisdn", msisdn);
        
        ApiResponse<List<TagJsonObject>> response;
        List<TagJsonObject> tagResponse;

        try {
            response = doRequest("contacts/" + msisdn + "/tags", "get", urlParams, null, false);
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

    public ApiResponse<ContactJsonObject> addTag(String msisdn, String tagName) {
        ApiResponse<ContactJsonObject> response;
        ContactJsonObject contactResponse;
        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();

        urlParams.put("tag_name", tagName);
        urlParams.put("msisdn", msisdn);

        try {
            response = doRequest("contacts/"+msisdn + "/tags/" + tagName, "post", urlParams, null, false);
            if (response.isOk()) {
                contactResponse = ContactJsonObject.fromJson(response.getRawResponse());
                response.setResponse(contactResponse);
            }
        } catch (Exception e) {
            response = new ApiResponse<>();
            response.setErrorCode(-1);
            response.setErrorDescription(e.getMessage());
        }
        return response;
    }

    public ApiResponse<ContactJsonObject> removeTag(String msisdn, String shortName) {
        ApiResponse<ContactJsonObject> response;
        ContactJsonObject contactResponse;
        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();

        urlParams.put("tag_name", shortName);
        urlParams.put("msisdn", msisdn);

        try {
            response = doRequest("contacts/"+msisdn + "/tags/" + shortName, "delete", urlParams, null, false);
            if (response.isOk()) {
                contactResponse = ContactJsonObject.fromJson(response.getRawResponse());
                response.setResponse(contactResponse);
            }
        } catch (Exception e) {
            response = new ApiResponse<>();
            response.setErrorCode(-1);
            response.setErrorDescription(e.getMessage());
        }
        return response;
    }

}
