/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interactuamovil.apps.contactosms.api.sdk;

import junit.framework.TestCase;

/**
 *
 * @author sergeiw
 */
public class TagsTest extends TestCase {

    public TagsTest(String testName) {
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
     * Test of getList method, of class Groups.
     *
    public void testGetList() {
        System.out.println("getList");
        Groups instance = new Groups(
                    "1d4e705080edec039fe580dd26fd1927", 
                    "0b9aa43039efacc16072a9774af72993", 
                    "https://mensajeriacorporativa.tigobusiness.hn/api/");
                    //"http://localhost:8088/api/");
        ApiResponse expResult = null;
        ApiResponse result = instance.getList("", 0, 10, false);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get method, of class Groups.
     *
    public void testGet() {
        System.out.println("get");
        String shortName = "G1";
        Groups instance = new Groups(
                    "61ee667b06f9409ed02e88bd0416abaf", 
                    "ebf9d11ba96c630011216f1fa3c436ca", 
                    "http://localhost:8088/api/");
        ApiResponse expResult = null;
        ApiResponse result = instance.get(shortName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class Groups.
     *
    public void testUpdate_4args() {
        System.out.println("update");
        String shortName = "G1";
        String name = "Grupito1";
        String description = "Este es el grupito 1111";
        String newShortName = "G1";
        Groups instance = new Groups(
                    "1d4e705080edec039fe580dd26fd1927", 
                    "0b9aa43039efacc16072a9774af72993", 
                    "http://localhost:8088/api/");
        ApiResponse expResult = null;
        ApiResponse result = instance.update(shortName, name, description, newShortName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    /**
     * Test of add method, of class Groups.
     *
    public void testAdd_3args() {
        System.out.println("add");
        String shortName = "g1";
        String name = "Grupito1";
        String description = "Este es el grupo 1";
        Groups instance = new Groups(
                    "1d4e705080edec039fe580dd26fd1927", 
                    "0b9aa43039efacc16072a9774af72993", 
                    //"https://apps.interactuamovil.com/tigocorp/api/");
                    "http://localhost:8088/api/");
        ApiResponse expResult = null;
        ApiResponse result = instance.add(shortName, name, description);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");;
    }

    /**
     * Test of add method, of class Groups.
     *
    public void testAdd_GroupJsonObject() {
        System.out.println("add");
        GroupJsonObject group = null;
        Groups instance = new Groups(
                    "61ee667b06f9409ed02e88bd0416abaf", 
                    "ebf9d11ba96c630011216f1fa3c436ca", 
                    "http://localhost:8088/api/");
        ApiResponse expResult = null;
        ApiResponse result = instance.add(group);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class Groups.
     *
    public void testDelete() {
        System.out.println("delete");
        String shortName = "g3";
        Groups instance = new Groups(
                    "61ee667b06f9409ed02e88bd0416abaf", 
                    "ebf9d11ba96c630011216f1fa3c436ca", 
                    "http://localhost:8088/api/");
        ApiResponse expResult = null;
        ApiResponse result = instance.delete(shortName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getContactList method, of class Groups.
     *
    public void testGetContactList() {
        System.out.println("getContactList");
        String shortName = "g1";
        Groups instance = new Groups(
                    "61ee667b06f9409ed02e88bd0416abaf", 
                    "ebf9d11ba96c630011216f1fa3c436ca", 
                    "http://localhost:8088/api/");
        ApiResponse expResult = null;
        ApiResponse result = instance.getContactList(shortName, 0, 5);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAvailableContactList method, of class Groups.
     *
    public void testGetAvailableContactList() {
        System.out.println("getAvailableContactList");
        String shortName = "g1";
        Groups instance = new Groups(
                    "61ee667b06f9409ed02e88bd0416abaf", 
                    "ebf9d11ba96c630011216f1fa3c436ca", 
                    "http://localhost:8088/api/");
        ApiResponse expResult = null;
        ApiResponse result = instance.getAvailableContactList(shortName, 0, 5);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addContact method, of class Groups.
     *
    public void testAddContact() {
        System.out.println("addContact");
        String shortName = "G1";
        String msisdn = "50252017507";
        Groups instance = new Groups(
                    "1d4e705080edec039fe580dd26fd1927", 
                    "0b9aa43039efacc16072a9774af72993", 
                    "http://localhost:8088/api/");
        ApiResponse expResult = null;
        ApiResponse result = instance.addContact(shortName, msisdn);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeContact method, of class Groups.
     *
    public void testRemoveContact() {
        System.out.println("removeContact");
        String shortName = "g1";
        String msisdn = "50252017507";
        Groups instance = new Groups(
                    "1d4e705080edec039fe580dd26fd1927", 
                    "0b9aa43039efacc16072a9774af72993", 
                    "http://localhost:8088/api/");
        ApiResponse expResult = null;
        ApiResponse result = instance.removeContact(shortName, msisdn);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    /* */
}
