package com.interactuamovil.apps.contactosms.api.sdk.examples;

import com.interactuamovil.apps.contactosms.api.client.rest.messages.MessageJson;
import com.interactuamovil.apps.contactosms.api.sdk.Messages;
import com.interactuamovil.apps.contactosms.api.enums.MessageDirection;
import com.interactuamovil.apps.contactosms.api.utils.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Example demonstrating how to use the delivery_status_enable parameter
 */
public class MessagesDeliveryStatusExample extends BaseExample {
    
    private static final Logger logger = LoggerFactory.getLogger(MessagesDeliveryStatusExample.class);
    
    public MessagesDeliveryStatusExample(String apiKey, String apiSecretKey, String apiUri, org.apache.commons.configuration2.Configuration config) {
        super(apiKey, apiSecretKey, apiUri, config);
    }

    @Override
    public void configure() {
        // No additional configuration needed for this example
    }

    @Override
    public void test() {
        var messages = new Messages(getApiKey(), getApiSecretKey(), getApiUri());
        
        // Example 1: Get messages WITHOUT delivery status
        runExample1(messages);
        
        // Example 2: Get messages WITH delivery status
        runExample2(messages);
        
        // Example 3: Get messages with delivery status and specific filters
        runExample3(messages);
        
        // Example 4: Async request with delivery status
        runExample4Async(messages);
        
        // Additional tests
        testDeliveryStatusByMsisdn(messages);
        testDeliveryStatusList(messages);
    }
    
    private void runExample1(Messages messages) {
        logger.info("=== Example 1: Messages WITHOUT delivery status ===");
        
        var startDate = LocalDateTime.now().minusDays(7);
        var endDate = LocalDateTime.now();
        
        var query = Messages.MessageQuery.of(startDate, endDate)
                .withPagination(0, 10);
        
        var response = messages.getList(query);
        
        if (response.isOk() && response.getResponse() != null) {
            logger.info("Retrieved {} messages without delivery status", 
                       response.getResponse().size());
            response.getResponse().forEach(message -> 
                    logger.info("Message ID: {}, Text: {}, Date: {}", 
                               message.getMessageId(), 
                               truncate(message.getMessage(), 30),
                               message.getCreatedOn()));
        } else {
            logger.error("Error: {} - {}", response.getErrorCode(), response.getErrorDescription());
        }
    }
    
    private void runExample2(Messages messages) {
        logger.info("=== Example 2: Messages WITH delivery status ===");
        
        var startDate = LocalDateTime.now().minusDays(7);
        var endDate = LocalDateTime.now();
        
        var response = messages.getListWithDeliveryStatus(startDate, endDate, 0, 10, "50252017507");
        
        if (response.isOk() && response.getResponse() != null) {
            logger.info("Retrieved {} messages with delivery status", 
                       response.getResponse().size());
            response.getResponse().forEach(message -> 
                    logger.info("Message ID: {}, Status: {}, Text: {}, Date: {}", 
                               message.getMessageId(), 
                               message.getMessageStatus(),
                               truncate(message.getMessage(), 30),
                               message.getCreatedOn()));
        } else {
            logger.error("Error: {} - {}", response.getErrorCode(), response.getErrorDescription());
        }
    }
    
    private void runExample3(Messages messages) {
        logger.info("=== Example 3: Messages with delivery status and specific filters ===");
        
        var startDate = LocalDateTime.now().minusDays(7);
        var endDate = LocalDateTime.now();
        
        var response = messages.getListWithDeliveryStatus(startDate, endDate, 0, 10, "50252017507", MessageDirection.MT);
        
        if (response.isOk() && response.getResponse() != null) {
            logger.info("Retrieved {} messages with delivery status and specific filters", 
                       response.getResponse().size());
            response.getResponse().forEach(message -> 
                    logger.info("Message ID: {}, Status: {}, Direction: {}, Text: {}, Date: {}", 
                               message.getMessageId(), 
                               message.getMessageStatus(),
                               message.getMessageDirection(),
                               truncate(message.getMessage(), 30),
                               message.getCreatedOn()));
        } else {
            logger.error("Error: {} - {}", response.getErrorCode(), response.getErrorDescription());
        }
    }
    
    private void runExample4Async(Messages messages) {
        logger.info("=== Example 4: Async request with delivery status ===");
        
        var startDate = LocalDateTime.now().minusDays(7);
        var endDate = LocalDateTime.now();
        
        var query = Messages.MessageQuery.ofWithDeliveryStatus(startDate, endDate, 0, 10)
                .withMsisdn("50252017507")
                .withDirection(MessageDirection.MT);
        
        var futureResponse = messages.getListAsync(query);
        
        try {
            var response = futureResponse.get();
            if (response.isOk() && response.getResponse() != null) {
                logger.info("Retrieved {} messages with delivery status", 
                           response.getResponse().size());
                response.getResponse().forEach(message -> 
                        logger.info("Message ID: {}, Status: {}, Text: {}, Date: {}", 
                                   message.getMessageId(), 
                                   message.getMessageStatus(),
                                   truncate(message.getMessage(), 30),
                                   message.getCreatedOn()));
            } else {
                logger.error("Error: {} - {}", response.getErrorCode(), response.getErrorDescription());
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Error executing async request", e);
        }
    }

    private void testDeliveryStatusByMsisdn(Messages messages) {
        logger.info("=== Test: Delivery status by MSISDN ===");
        
        var startDate = LocalDateTime.now().minusDays(7);
        var endDate = LocalDateTime.now();
        
        Messages.MessageQuery query = Messages.MessageQuery.ofWithDeliveryStatus(startDate, endDate)
                .withDirection(MessageDirection.MT);

        var response = messages.getListWithDeliveryStatus(startDate, endDate, 0, 10, "50252017507", MessageDirection.MT);
        
        if (response.isOk() && response.getResponse() != null) {
            logger.info("Found {} messages for MSISDN with delivery status", 
                       response.getResponse().size());
        } else {
            logger.error("Error: {} - {}", response.getErrorCode(), response.getErrorDescription());
        }
    }

    private void testDeliveryStatusList(Messages messages) {
        logger.info("=== Test: Delivery status list ===");
        
        var startDate = LocalDateTime.now().minusDays(7);
        var endDate = LocalDateTime.now();
        
        ApiResponse<List<MessageJson>> response = messages.getListWithDeliveryStatus(startDate, endDate, 0, 10, null);
        
        if (response.isOk() && response.getResponse() != null) {
            for (MessageJson message : response.getResponse()) {
                System.out.printf("Message ID: %s, Status: %s%n", 
                                message.getMessageId(), 
                                message.getMessageStatus());
            }
        }
        
        Messages.MessageQuery query2 = Messages.MessageQuery.ofWithDeliveryStatus(startDate, endDate)
                .withDirection(MessageDirection.MT)
                .withPagination(0, 10);
        
        var response2 = messages.getList(query2);
        if (response2.isOk() && response2.getResponse() != null) {
            logger.info("Query builder approach returned {} messages", 
                       response2.getResponse().size());
        }
    }
    
    private String truncate(String value, int length) {
        if (value == null) {
            return "";
        }
        if (value.length() > length) {
            return value.substring(0, length);
        }
        return value;
    }
} 