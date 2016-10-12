package com.interactuamovil.apps.contactosms.api.sdk.examples;

import com.interactuamovil.apps.contactosms.api.client.rest.contacts.ContactJsonObject;
import com.interactuamovil.apps.contactosms.api.sdk.Contacts;
import com.interactuamovil.apps.contactosms.api.utils.ApiResponse;
import org.apache.commons.configuration.Configuration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;


class ContactsExample extends BaseExample {

    private String testContactCountryCode = null;
    private String testContactMsisdn = null;
    private String testContactFirstName = null;
    private String testContactLastName = null;

    public ContactsExample(String _apiKey, String _apiSecretKey, String _apiUri, Configuration _config) {
        super(_apiKey, _apiSecretKey, _apiUri, _config);
    }

    @Override
    public void configure() {

        testContactCountryCode = getConfig().getString("test_contact_country_code");
        testContactMsisdn = getConfig().getString("test_contact_msisdn");
        testContactFirstName = getConfig().getString("test_contact_first");
        testContactLastName = getConfig().getString("test_contact_last");

        if (null == testContactMsisdn || null == testContactFirstName
            || null == testContactLastName) {
            throw new AssertionError(
                "Please add contact configurations: test_contact_msisdn,"
                    + " test_contact_first_name, test_contact_last_name."
            );
        }

    }

    @Override
    public void test()
        throws IOException, InvalidKeyException, NoSuchAlgorithmException {

        Contacts contactsApi = new Contacts(
            getApiKey(),
            getApiSecretKey(),
            getApiUri()
        );

        // Get contacts list
        //contactsApi.getList(ContactStatus.PENDING.getStatus());
        //contactsApi.getList(ContactStatus.CONFIRMED.getStatus());
        //contactsApi.getList(ContactStatus.CANCELLED.getStatus());

        // Add contact and check it really was created
        testAddingContact(contactsApi);

        // Get one item using limits and check its not the same item
        testContactsList(contactsApi);

        // Update contact and check updated values
        testUpdatingContact(contactsApi);

        // Delete contact and check it does not exists
        testDeleteContact(contactsApi);

    }

    private void testUpdatingContact(Contacts contactsApi) throws IOException, InvalidKeyException, NoSuchAlgorithmException {

        // Update contact with inverted first and last name
        contactsApi.update(
                "",
            testContactMsisdn,
            testContactLastName,
            testContactFirstName
        );

        ApiResponse<ContactJsonObject> contactResponse =
            contactsApi.getByMsisdn(testContactMsisdn);

        if (null == contactResponse) {
            throw new AssertionError("Contact does not exist.");
        }

        ContactJsonObject contact = contactResponse.getResponse();

        if (!contact.getFirstName().equals(testContactLastName)
            && !contact.getLastName().equals(testContactFirstName)) {
            throw new AssertionError("Contact was not updated correctly.");
        }

    }

    private void testDeleteContact(Contacts contactsApi)
        throws IOException, InvalidKeyException, NoSuchAlgorithmException {

        contactsApi.delete(testContactMsisdn);

        ApiResponse<ContactJsonObject> deletedContact =
            contactsApi.getByMsisdn(testContactMsisdn);

        if (null != deletedContact.getResponse()) {
            throw new AssertionError("Contact found after deletion.");
        }

    }

    private void testContactsList(Contacts contactsApi)
        throws IOException, InvalidKeyException, NoSuchAlgorithmException {

        ApiResponse<List<ContactJsonObject>> contactOneFromList =
            contactsApi.getList(null, null, 0, 1, false);

        ApiResponse<List<ContactJsonObject>> contactTwoFromList =
            contactsApi.getList(null, null, 1, 1, false);

        List<ContactJsonObject> firstResult = contactOneFromList.getResponse();
        List<ContactJsonObject> secondResult = contactTwoFromList.getResponse();

        if ((firstResult != null && !firstResult.isEmpty())
                && (secondResult != null && !secondResult.isEmpty())) {
            ContactJsonObject firstContact = firstResult.get(0);
            ContactJsonObject secondContact = secondResult.get(0);
            String msisdn = firstContact.getMsisdn();
            if (msisdn.equals(secondContact.getMsisdn())) {
                throw new AssertionError(
                    "Contact 1 and 2 of list are the same msisdn."
                );
            }
        }

    }

    private void testAddingContact(Contacts contactsApi)
        throws IOException, InvalidKeyException, NoSuchAlgorithmException {

        ApiResponse<ContactJsonObject> response =  contactsApi.add(
            testContactCountryCode,
            testContactMsisdn,
            testContactFirstName,
            testContactLastName
        );

        if (!response.isOk()) {
            throw new AssertionError("Error: " + response.getErrorDescription());
        }

        ApiResponse<ContactJsonObject> contactResponse =
            contactsApi.getByMsisdn(testContactMsisdn);

        if (null == contactResponse) {
            throw new AssertionError("Contact was not added.");
        }

        ContactJsonObject contact = contactResponse.getResponse();

        ApiResponse<List<ContactJsonObject>> contactCheckAddedByFirstName =
            contactsApi.getList(null, testContactFirstName, null, null, false);

        /*ApiResponse<List<ContactJsonObject>> contactCheckAddedByFullName =
            contactsApi.getList(
                null, testContactFirstName, testContactLastName, null, null);*/

        Boolean contactInResponseByFirstName = Boolean.FALSE;

        for (ContactJsonObject contactItem
            : contactCheckAddedByFirstName.getResponse()) {
            if (contactItem.getMsisdn().equals(contact.getMsisdn())
                && contactItem.getFirstName().equals(contact.getFirstName())) {
                contactInResponseByFirstName = Boolean.TRUE;
            }
        }

        if (contactInResponseByFirstName.equals(Boolean.FALSE)) {
            throw new AssertionError("Contact not found in search.");
        }

        /*Boolean contactInResponseByFullName = Boolean.FALSE;

        for (ContactJsonObject contactItem
            : contactCheckAddedByFullName.getResponse()) {
            if (contactItem.getMsisdn().equals(contact.getMsisdn())
                && contactItem.getFirstName().equals(contact.getFirstName())
                && contactItem.getLastName().equals(contact.getLastName())) {
                contactInResponseByFullName = Boolean.TRUE;
            }
        }

        if (contactInResponseByFullName.equals(Boolean.FALSE)) {
            throw new AssertionError("Contact not found in search.");
        }*/

    }

}
