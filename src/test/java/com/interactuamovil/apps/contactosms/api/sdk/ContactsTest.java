/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interactuamovil.apps.contactosms.api.sdk;

import com.interactuamovil.apps.contactosms.api.client.rest.contacts.ContactJsonObject;
import com.interactuamovil.apps.contactosms.api.enums.ContactStatus;
import com.interactuamovil.apps.contactosms.api.utils.ApiResponse;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import junit.framework.TestCase;

/**
 *
 * @author sergeiw
 */
public class ContactsTest extends TestCase {
    
    public ContactsTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testDummy() {
        assertTrue(true);
    }

    /**
     * Test of getList method, of class Contacts.
     *
    public void testGetList_5args() throws Exception {
        System.out.println("getList");
        List<ContactStatus> contactStatuses = null;
        String query = "";
        Integer start = 0;
        Integer limit = 10;
        boolean shortResults = true;        
        Contacts instance = new Contacts(
                    "61ee667b06f9409ed02e88bd0416abaf", 
                    "ebf9d11ba96c630011216f1fa3c436ca", 
                    "http://localhost:8088/api/");
        ApiResponse expResult = null;
        ApiResponse result = instance.getList(contactStatuses, query, start, limit, shortResults);
        assertEquals(result.getHttpCode(), 200);
        
    }

    /**
     * Test of getList method, of class Contacts.
     *
    public void testGetList_List() throws Exception {
        System.out.println("getList");
        List<ContactStatus> contactStatuses = null;
        Contacts instance = new Contacts(
                    "61ee667b06f9409ed02e88bd0416abaf", 
                    "ebf9d11ba96c630011216f1fa3c436ca", 
                    "http://localhost:8088/api/");
        ApiResponse expResult = null;
        ApiResponse result = instance.getList(contactStatuses);
        assertEquals(result.getHttpCode(), 200);
        
    }

    /**
     * Test of getByMsisdn method, of class Contacts.
     *
    public void testGetByMsisdn() throws Exception {
        System.out.println("getByMsisdn");
        String msisdn = "50252017507";
        Contacts instance = new Contacts(
                    "61ee667b06f9409ed02e88bd0416abaf", 
                    "ebf9d11ba96c630011216f1fa3c436ca", 
                    "http://localhost:8088/api/");
        ApiResponse<ContactJsonObject> expResult = null;
        ApiResponse<ContactJsonObject> result = instance.getByMsisdn(msisdn);
        assertEquals(msisdn, result.getResponse().getMsisdn());
        
    }

    /**
     * Test of update method, of class Contacts.
     *
    public void testUpdate() throws Exception {
        System.out.println("update");
        String msisdn = "50252017508";
        String firstName = "GAby";
        String lastName = "Pardo";        
        Contacts instance = new Contacts(
                    "61ee667b06f9409ed02e88bd0416abaf", 
                    "ebf9d11ba96c630011216f1fa3c436ca", 
                    "http://localhost:8088/api/");
        ApiResponse<ContactJsonObject> expResult = null;
        ApiResponse<ContactJsonObject> result = instance.update(msisdn, firstName, lastName);
        assertEquals(expResult, result);                
    }

    /**
     * Test of add method, of class Contacts.
     *
    public void testAdd_3args() throws Exception {
        System.out.println("add");
        String countryCode = "502";
        String msisdn = "50252017509";
        String firstName = "Aram";
        String lastName = "Guerra";
        Contacts instance = new Contacts(
                    "61ee667b06f9409ed02e88bd0416abaf", 
                    "ebf9d11ba96c630011216f1fa3c436ca", 
                    "http://localhost:8088/api/");
        ApiResponse expResult = null;
        ApiResponse result = instance.add(countryCode, msisdn, firstName, lastName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of add method, of class Contacts.
     *
    public void testAdd_String() throws Exception {
        System.out.println("add");
        String countryCode = "502";
        String msisdn = "50252017510";
        Contacts instance = new Contacts(
                    "61ee667b06f9409ed02e88bd0416abaf", 
                    "ebf9d11ba96c630011216f1fa3c436ca", 
                    "http://localhost:8088/api/");
        ApiResponse expResult = null;
        ApiResponse result = instance.add(countryCode, msisdn);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of delete method, opx0ssskclass Contacts.
     *
    public void testDelete() throws Exception {
        System.out.println("delete");
        String msisdn = "50252017508";
        Contacts instance = new Contacts(
                    "61ee667b06f9409ed02e88bd0416abaf", 
                    "ebf9d11ba96c630011216f1fa3c436ca", 
                    "http://localhost:8088/api/");
        ApiResponse expResult = null;
        ApiResponse result = instance.delete(msisdn);
        assertEquals(expResult, result);
    }

    /**
     * Test of getGroupList method, of class Contacts.
     *
    public void testGetGroupList() throws Exception {
        System.out.println("getGroupList");
        String msisdn = "50252055941";
        Contacts instance = new Contacts(
                    "61ee667b06f9409ed02e88bd0416abaf", 
                    "ebf9d11ba96c630011216f1fa3c436ca", 
                    "http://localhost:8088/api/");
        ApiResponse expResult = null;
        ApiResponse result = instance.getGroupList(msisdn);
        assertEquals(expResult, result);        
    }
    /* */
}
