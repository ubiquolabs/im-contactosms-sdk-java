package com.interactuamovil.apps.contactosms.api.sdk.examples;

import com.interactuamovil.apps.contactosms.api.client.rest.contacts.ContactJsonObject;
import com.interactuamovil.apps.contactosms.api.client.rest.tags.TagJsonObject;
import com.interactuamovil.apps.contactosms.api.sdk.Contacts;
import com.interactuamovil.apps.contactosms.api.sdk.Tags;
import com.interactuamovil.apps.contactosms.api.sdk.responses.TagResponse;
import com.interactuamovil.apps.contactosms.api.utils.ApiResponse;
import org.apache.commons.configuration.Configuration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

class TagsExample extends BaseExample {

    private String testTagName = null;
    private String testContactCountryCode = null;
    private String testContactMsisdn = null;
    private String testContactFirstName = null;
    private String testContactLastName = null;

    public TagsExample(String _apiKey, String _apiSecretKey, String _apiUri, Configuration _config) {
        super(_apiKey, _apiSecretKey, _apiUri, _config);
    }

    @Override
    public void configure() {

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

        Contacts contactsApi = new Contacts(
                getApiKey(),
                getApiSecretKey(),
                getApiUri()
        );

        Tags tagsApi = new Tags(
            getApiKey(),
            getApiSecretKey(),
            getApiUri()
        );

        // Get list of tags
        //testTagsList(tagsApi);

        //testGetTag(tagsApi);

        //testTagContactList(contactsApi, tagsApi);

        testAddTag(contactsApi, tagsApi);

        testRemoveTag(contactsApi, tagsApi);

        testDeleteTag(tagsApi);

    }

    private void testTagsList(Tags tagsApi) {

        ApiResponse<List<TagJsonObject>> tags = tagsApi.getList();

        Boolean found = Boolean.FALSE;

        for (TagJsonObject group : tags.getResponse()) {
            if (testTagName.equalsIgnoreCase(group.getName())) {
                found = Boolean.TRUE;
            }
        }

        if (!found) {
            throw new AssertionError("Tag not found on list.");
        }

    }

    private void testGetTag(Tags tagsApi) throws NoSuchAlgorithmException, InvalidKeyException, IOException {

        ApiResponse<TagJsonObject> tag = tagsApi.getTag(testTagName);

        if (!testTagName.equalsIgnoreCase(tag.getResponse().getName())) {
            throw new AssertionError("Tag not found on list.");
        }

    }

    private void testTagContactList(Contacts contactsApi, Tags tagsApi) throws NoSuchAlgorithmException, InvalidKeyException, IOException {

        ApiResponse<List<TagJsonObject>> tags = contactsApi.getTagList(testContactMsisdn);

        Boolean found = Boolean.FALSE;

        for (TagJsonObject group : tags.getResponse()) {
            if (testTagName.equalsIgnoreCase(group.getName())) {
                found = Boolean.TRUE;
            }
        }

        if (!found) {
            throw new AssertionError("Tag not found on list.");
        }

    }

    private void testAddTag(Contacts contactsApi, Tags tagsApi) throws NoSuchAlgorithmException, InvalidKeyException, IOException {

        ApiResponse<ContactJsonObject> contactResponse =  contactsApi.add(
                testContactCountryCode,
                testContactMsisdn,
                testContactFirstName,
                testContactLastName
        );
        if (contactResponse.isOk()) {
            ApiResponse<ContactJsonObject> addResponse = contactsApi.addTag(testContactMsisdn, testTagName);
            if (addResponse.isOk()) {
                ContactJsonObject contact = addResponse.getResponse();
                if (!contact.getTags().contains(testTagName)) {
                    throw new AssertionError("Tag not added to contact");
                }
            }
        }

    }

    private void testRemoveTag(Contacts contactsApi, Tags tagsApi) throws NoSuchAlgorithmException, InvalidKeyException, IOException {

        ApiResponse<ContactJsonObject> addResponse = contactsApi.removeTag(testContactMsisdn, testTagName);
        if (addResponse.isOk()) {
            ContactJsonObject contact = addResponse.getResponse();
            if (contact.getTags().contains(testTagName)) {
                throw new AssertionError("Tag not removed from contact");
            }
        }

    }

    private void testDeleteTag(Tags tagsApi) throws NoSuchAlgorithmException, InvalidKeyException, IOException {

        ApiResponse<TagJsonObject> tag = tagsApi.deleteTag(testTagName);

        if (!testTagName.equalsIgnoreCase(tag.getResponse().getName())) {
            throw new AssertionError("Tag not found on list.");
        }

    }

}
