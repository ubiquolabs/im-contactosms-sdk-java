package com.interactuamovil.contactosms.api.examples;

import com.interactuamovil.contactosms.api.Contacts;
import com.interactuamovil.contactosms.api.enums.ContactStatus;
import com.interactuamovil.contactosms.api.responses.ActionMessageResponse;
import com.interactuamovil.contactosms.api.responses.ContactResponse;
import com.interactuamovil.contactosms.api.responses.ListResponse;
import com.interactuamovil.contactosms.api.responses.Response;
import org.apache.commons.configuration.Configuration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;


public class ContactsExample extends BaseExample {

    private String testContactMsisdn = null;
    private String testContactFirstName = null;
    private String testContactLastName = null;

    public ContactsExample(String _apiKey, String _apiSecretKey, String _apiUri, Configuration _config) {
        super(_apiKey, _apiSecretKey, _apiUri, _config);
    }

    @Override
    public void configure() {

        testContactMsisdn = getConfig().getString("test_contact_msisdn");
        testContactFirstName = getConfig().getString("test_contact_msisdn");
        testContactLastName = getConfig().getString("test_contact_msisdn");

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
        contactsApi.getList(ContactStatus.PENDING.getStatus());
        contactsApi.getList(ContactStatus.CONFIRMED.getStatus());
        contactsApi.getList(ContactStatus.CANCELLED.getStatus());

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
            testContactMsisdn,
            testContactLastName,
            testContactFirstName,
            null
        );

        Response<ContactResponse> contactResponse =
            contactsApi.getByMsisdn(testContactMsisdn);

        if (null == contactResponse) {
            throw new AssertionError("Contact does not exist.");
        }

        ContactResponse contact = contactResponse.getResult();

        if (!contact.getFirstName().equals(testContactLastName)
            && !contact.getLastName().equals(testContactFirstName)) {
            throw new AssertionError("Contact was not updated correctly.");
        }

    }

    private void testDeleteContact(Contacts contactsApi)
        throws IOException, InvalidKeyException, NoSuchAlgorithmException {

        contactsApi.delete(testContactMsisdn);

        Response<ContactResponse> deletedContact =
            contactsApi.getByMsisdn(testContactMsisdn);

        if (null != deletedContact.getResult()) {
            throw new AssertionError("Contact found after deletion.");
        }

    }

    private void testContactsList(Contacts contactsApi)
        throws IOException, InvalidKeyException, NoSuchAlgorithmException {

        ListResponse<ContactResponse> contactOneFromList =
            contactsApi.getList(null, null, null, 0, 1);

        ListResponse<ContactResponse> contactTwoFromList =
            contactsApi.getList(null, null, null, 1, 1);

        List<ContactResponse> firstResult = contactOneFromList.getResult();
        List<ContactResponse> secondResult = contactTwoFromList.getResult();

        if (!firstResult.isEmpty() && !secondResult.isEmpty()) {
            ContactResponse firstContact = firstResult.get(0);
            ContactResponse secondContact = secondResult.get(0);
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

        Response<ActionMessageResponse> response =  contactsApi.add(
            testContactMsisdn,
            testContactFirstName,
            testContactLastName
        );

        if (response.hasError()) {
            throw new AssertionError("Could not add contact: "
                + response.getErrorMessage());
        }

        Response<ContactResponse> contactResponse =
            contactsApi.getByMsisdn(testContactMsisdn);

        if (null == contactResponse) {
            throw new AssertionError("Contact was not added.");
        }

        ContactResponse contact = contactResponse.getResult();

        ListResponse<ContactResponse> contactCheckAddedByFirstName =
            contactsApi.getList(null, testContactFirstName, null, null, null);

        ListResponse<ContactResponse> contactCheckAddedByFullName =
            contactsApi.getList(
                null, testContactFirstName, testContactLastName, null, null);

        Boolean contactInResponseByFirstName = Boolean.FALSE;

        for (ContactResponse contactItem
            : contactCheckAddedByFirstName.getResult()) {
            if (contactItem.getMsisdn().equals(contact.getMsisdn())
                && contactItem.getFirstName().equals(contact.getFirstName())) {
                contactInResponseByFirstName = Boolean.TRUE;
            }
        }

        if (contactInResponseByFirstName.equals(Boolean.FALSE)) {
            throw new AssertionError("Contact not found in search.");
        }

        Boolean contactInResponseByFullName = Boolean.FALSE;

        for (ContactResponse contactItem
            : contactCheckAddedByFullName.getResult()) {
            if (contactItem.getMsisdn().equals(contact.getMsisdn())
                && contactItem.getFirstName().equals(contact.getFirstName())
                && contactItem.getLastName().equals(contact.getLastName())) {
                contactInResponseByFullName = Boolean.TRUE;
            }
        }

        if (contactInResponseByFullName.equals(Boolean.FALSE)) {
            throw new AssertionError("Contact not found in search.");
        }

    }

}
