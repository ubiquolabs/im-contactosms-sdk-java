package com.interactuamovil.apps.contactosms.api.sdk.examples;

import com.interactuamovil.apps.contactosms.api.client.rest.contacts.ContactJsonObject;
import com.interactuamovil.apps.contactosms.api.client.rest.tags.TagJsonObject;
import com.interactuamovil.apps.contactosms.api.sdk.Contacts;
import com.interactuamovil.apps.contactosms.api.sdk.Tags;
import com.interactuamovil.apps.contactosms.api.sdk.responses.TagResponse;
import com.interactuamovil.apps.contactosms.api.utils.ApiResponse;
import org.apache.commons.configuration2.Configuration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Modern Tags Example using Configuration2
 */
public class TagsExample extends BaseExample {

    private String testTagName = null;
    private String testContactCountryCode = null;
    private String testContactMsisdn = null;
    private String testContactFirstName = null;
    private String testContactLastName = null;

    public TagsExample(String apiKey, String apiSecretKey, String apiUri, Configuration config) {
        super(apiKey, apiSecretKey, apiUri, config);
    }

    @Override
    public void configure() {
        // Configuration setup if needed

        testContactCountryCode = getConfig().getString("test_contact_country_code");
        testContactMsisdn = getConfig().getString("test_contact_msisdn");
        testContactFirstName = getConfig().getString("test_contact_first");
        testContactLastName = getConfig().getString("test_contact_last");

        testTagName = getConfig().getString("test_group_name");

        if (null == testTagName) {
            throw new AssertionError(
                "Please add tag configurations: test_tag_name"
            );
        }
    }

    @Override
    public void test() throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        Tags tagsApi = new Tags(getApiKey(), getApiSecretKey(), getApiUri());
        
        ApiResponse<List<TagJsonObject>> tagsResponse = tagsApi.getList();
        
        if (tagsResponse.isOk()) {
            List<TagJsonObject> tagList = tagsResponse.getResponse();
            System.out.println("Found " + tagList.size() + " tags");
        } else {
            System.err.println("Error: " + tagsResponse.getErrorDescription());
        }

        testAddTag(tagsApi);

        testRemoveTag(tagsApi);

        testDeleteTag(tagsApi);
    }

    private void testAddTag(Tags tagsApi) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        Contacts contactsApi = new Contacts(
                getApiKey(),
                getApiSecretKey(),
                getApiUri()
        );

        ApiResponse<ContactJsonObject> contactResponse =  contactsApi.add(
                testContactCountryCode,
                testContactMsisdn,
                testContactFirstName,
                testContactLastName
        );
        if (contactResponse.isOk()) {
            ApiResponse<ContactJsonObject> addResponse = contactsApi.addTag(testContactMsisdn, testTagName);
            if (addResponse.isOk() && addResponse.getResponse() != null) {
                ContactJsonObject contact = addResponse.getResponse();
                if (contact.getTags() == null || !contact.getTags().contains(testTagName)) {
                    throw new AssertionError("Tag not added to contact");
                }
            } else {
                System.err.println("Could not add tag to contact. Error: " + addResponse.getErrorDescription());
            }
        } else {
            System.err.println("Could not add contact. Error: " + contactResponse.getErrorDescription());
        }
    }

    private void testRemoveTag(Tags tagsApi) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        Contacts contactsApi = new Contacts(
                getApiKey(),
                getApiSecretKey(),
                getApiUri()
        );

        ApiResponse<ContactJsonObject> addResponse = contactsApi.removeTag(testContactMsisdn, testTagName);
        if (addResponse.isOk() && addResponse.getResponse() != null) {
            ContactJsonObject contact = addResponse.getResponse();
            if (contact.getTags() != null && contact.getTags().contains(testTagName)) {
                throw new AssertionError("Tag not removed from contact");
            }
        } else {
            System.err.println("Could not remove tag from contact. Error: " + addResponse.getErrorDescription());
        }
    }

    private void testDeleteTag(Tags tagsApi) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        ApiResponse<TagJsonObject> tag = tagsApi.deleteTag(testTagName);

        if (tag.getHttpCode() != 404) {
            throw new AssertionError("Tag not found on list.");
        }
    }
}
