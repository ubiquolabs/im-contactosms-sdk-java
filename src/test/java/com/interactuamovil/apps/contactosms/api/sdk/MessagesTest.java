/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interactuamovil.apps.contactosms.api.sdk;

import junit.framework.TestCase;

import java.text.SimpleDateFormat;

/**
 *
 * @author sergeiw
 */
public class MessagesTest extends TestCase {
    
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public MessagesTest(String testName) {
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
     * Test of getList method, of class Messages.
     *
    public void testGetList() {
        try {
            System.out.println("getList");        
            Date startDate = formatter.parse("2014-02-01 00:00:00");
            Date endDate = formatter.parse("2015-02-20 00:00:00");
            int start = 0;
            int limit = 50;
            String msisdn = "";
            Messages instance = new Messages(
                        "1d4e705080edec039fe580dd26fd1927", 
                        "0b9aa43039efacc16072a9774af72993",
                        "https://mensajeriacorporativa.tigobusiness.hn/api/");
                        //"http://localhost:8088/api/");
            instance.setCertificatedValidationEnabled(false);
            ApiResponse<List<MessageJson>> expResult = null;
            ApiResponse<List<MessageJson>> result = instance.getList(startDate, endDate, start, limit, msisdn);
            assertEquals(expResult, result);
            // TODO review the generated test code and remove the default call to fail.
            fail("The test case is a prototype.");
        } catch (ParseException ex) {
            Logger.getLogger(MessagesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of sendToGroups method, of class Messages.
     *
    public void testSendToGroups() {
        System.out.println("sendToGroups");
        String[] short_name = new String[] {"G1"};
        String message = "probando a grupo 1";
        Messages instance = new Messages(
                    "1d4e705080edec039fe580dd26fd1927", 
                    "0b9aa43039efacc16072a9774af72993", 
                    "http://localhost:8088/api/");
        ApiResponse<MessageJson> expResult = new ApiResponse();
        ApiResponse<MessageJson> result = instance.sendToGroups(short_name, message);
        assertEquals(expResult.getHttpCode(), result.getHttpCode());
    }

    /**
     * Test of sendToContact method, of class Messages.
     *
    public void testSendToContact() {
        System.out.println("sendToContact");
        String msisdn = "50252017507";
        String message = "probando individual 3";
        String messageId = "1726";
        Messages instance = new Messages(
                    "1d4e705080edec039fe580dd26fd1927", 
                    "0b9aa43039efacc16072a9774af72993", 
                    "http://localhost:8088/api/");
        ApiResponse<MessageJson> expResult = new ApiResponse();
        ApiResponse<MessageJson> result = instance.sendToContact(msisdn, message, messageId);
        assertEquals(expResult.getHttpCode(), result.getHttpCode());        
    }

    /**
     * Test of getSchedule method, of class Messages.
     *
    public void testGetSchedule() {
        System.out.println("getSchedule");
        Messages instance = null;
        ListResponse expResult = null;
        ListResponse result = instance.getSchedule();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteSchedule method, of class Messages.
     *
    public void testDeleteSchedule() {
        System.out.println("deleteSchedule");
        int messageId = 0;
        Messages instance = null;
        Response expResult = null;
        Response result = instance.deleteSchedule(messageId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addSchedule method, of class Messages.
     *
    public void testAddSchedule() {
        System.out.println("addSchedule");
        Date startDate = null;
        Date endDate = null;
        String eventName = "";
        String message = "";
        String time = "";
        String frequency = "";
        String repeatDays = "";
        String[] groups = null;
        Messages instance = null;
        ApiResponse expResult = null;
        ApiResponse result = instance.addSchedule(startDate, endDate, eventName, message, time, frequency, repeatDays, groups);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of inbox method, of class Messages.
     *
    public void testInbox() {
        System.out.println("inbox");
        Date startDate = null;
        Date endDate = null;
        int start = 0;
        int limit = 0;
        String msisdn = "";
        int status = 0;
        Messages instance = null;
        ListResponse expResult = null;
        ListResponse result = instance.inbox(startDate, endDate, start, limit, msisdn, status);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    * */
}
