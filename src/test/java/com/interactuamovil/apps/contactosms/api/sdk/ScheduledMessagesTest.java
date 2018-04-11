/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interactuamovil.apps.contactosms.api.sdk;

import com.interactuamovil.apps.contactosms.api.client.rest.messages.MessageJson;
import com.interactuamovil.apps.contactosms.api.enums.MessageDirection;
import com.interactuamovil.apps.contactosms.api.enums.RepeatInterval;
import com.interactuamovil.apps.contactosms.api.utils.ApiResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static junit.framework.Assert.assertTrue;
import junit.framework.TestCase;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author sergeiw
 */
public class ScheduledMessagesTest extends TestCase {
    
    public ScheduledMessagesTest(String testName) {
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
     * Test of delete method, of class ScheduledMessages.
     *
    public void testDelete() {
        System.out.println("deleteSchedule");
        int scheduledMessageId = 201;
        ScheduledMessages instance = new ScheduledMessages(
                    "61ee667b06f9409ed02e88bd0416abaf", 
                    "ebf9d11ba96c630011216f1fa3c436ca", 
                    "http://localhost:8088/api/");
        ApiResponse expResult = null;
        ApiResponse result = instance.delete(scheduledMessageId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSchedule method, of class ScheduledMessages.
     *
    public void testGetSchedule() {
        try {
            System.out.println("getSchedule");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date since = formatter.parse("2014-01-01");        
            ScheduledMessages instance = new ScheduledMessages(
                        "1d4e705080edec039fe580dd26fd1927", 
                        "0b9aa43039efacc16072a9774af72993", 
                        "http://localhost:8088/api/");        
            ApiResponse expResult = null;
            ApiResponse result = instance.getSchedule(since);
            assertEquals(expResult, result);
            // TODO review the generated test code and remove the default call to fail.
            fail("The test case is a prototype.");
        } catch (ParseException ex) {
            Logger.getLogger(ScheduledMessagesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of getById method, of class ScheduledMessages.
     *
    public void testGetById() {
        System.out.println("getById");
        int scheduledMessageId = 201;
        ScheduledMessages instance = new ScheduledMessages(
                    "GXAMKU18Jdpe2QkENCeZtN2Vyj4sigYc", 
                    "6Wla201lGdKZjcgFQxWmhnXNLae6Gg4F", 
                    "http://localhost:8088/api/");
        ApiResponse expResult = null;
        ApiResponse result = instance.getById(scheduledMessageId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    */
    /**/
    /**
    public void testGetMessages(){
        System.out.println("getMessages");
        Messages messagesApi = new Messages(
                    "ODT9hreTLUsErGLZcVX9n8dIOkgvVgl0", 
                    "67NDy3ox0DCF0RweAeYMQfz71WmMb09W", 
                    "http://localhost:8088/api/"                
        );
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date startDate = null;
        Date endDate = null;
        int start = 0;
        int limit = 10;
        String msisdn = null;
        MessageDirection direction  = MessageDirection.MT;

        try {
            startDate = sdf.parse("2018-01-01 00:00");
            endDate = sdf.parse("2018-02-01 00:00");

            ApiResponse<List<MessageJson>> response = messagesApi.getList(startDate, endDate, start, limit, msisdn, direction);

            if (!response.isOk()) {
                throw new AssertionError("Error sending message to existing contact: "
                        + response.getErrorDescription());
            } else {
                System.out.println(response.isOk());
                System.out.println(response.getHttpDescription());
                for (MessageJson m : response.getResponse()) {
                    System.out.println(String.format("msg: [%s] [%d] [%s] [%s] [%s] [%s] [%s] [%s]",
                            m.getMessageDirection().name(),
                            m.getMessageTypeId(),
                            m.getClientMessageId(),
                            m.getShortCode(),
                            m.getMsisdn(),
                            m.getMessage(),
                            m.getTags() != null ? StringUtils.join(m.getTags(),",") : "",
                            sdf.format(m.getCreatedOn())
                    ));
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        
    }
    
    */
    /**
     * Test of add method, of class ScheduledMessages.
     *
    public void testAdd() {
        System.out.println("add");
        Date startDate = new Date();
        Date endDate = new Date();
        String eventName = "cal prueba";
        String message = "probando cal";
        String time = "13:32";
        RepeatInterval frequency = RepeatInterval.DAILY;
        String repeatDays = "1";
        String[] groups = new String[] {"G1", "patty"};
        ScheduledMessages instance = new ScheduledMessages(
                    "61ee667b06f9409ed02e88bd0416abaf", 
                    "ebf9d11ba96c630011216f1fa3c436ca", 
                    "http://localhost:8088/api/");
        ApiResponse expResult = null;
        ApiResponse result = instance.add(startDate, endDate, eventName, message, time, frequency, repeatDays, groups);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    /* */
}
