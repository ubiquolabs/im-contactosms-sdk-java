package com.interactuamovil.apps.contactosms.api.sdk.examples;

import com.interactuamovil.apps.contactosms.api.client.rest.messages.MessageJson;
import com.interactuamovil.apps.contactosms.api.enums.MessageDirection;
import com.interactuamovil.apps.contactosms.api.sdk.Messages;
import com.interactuamovil.apps.contactosms.api.utils.ApiResponse;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Modern test case for Messages API using Java 21 features.
 */
public class ModernMessagesExample extends BaseExample {

    private static final Logger logger = LoggerFactory.getLogger(ModernMessagesExample.class);
    private final String testMsisdn;
    private final String testTagName;
    
    public ModernMessagesExample(String apiKey, String apiSecretKey, String apiUri, Configuration config) {
        super(apiKey, apiSecretKey, apiUri, config);
        this.testMsisdn = config.getString("test_contact_msisdn", "50212345678");
        this.testTagName = config.getString("test_tag_name", "TestTag");
    }

    @Override
    public void configure() {
        // Configuration can be expanded here if needed
    }

    @Override
    public void test() {
        System.out.println("? Testing Modern Messages API with Java 21 features");
        System.out.println("============================================================");

        var messagesApi = new Messages(getApiKey(), getApiSecretKey(), getApiUri());

        // Run tests
        testMessagesWithoutDeliveryStatus(messagesApi);
        testMessagesWithDeliveryStatus(messagesApi);
        testAsyncMessages(messagesApi);
        testModernMessageSending(messagesApi, testMsisdn, "Probando");
        testFluentAPI(messagesApi);
    }

    private void testMessagesWithoutDeliveryStatus(Messages messagesApi) {
        System.out.println("\n\uD83D\uDCCB Test 1: Messages WITHOUT delivery status");
        System.out.println("-".repeat(40));
        
        try {
            var startDate = LocalDateTime.now().minusDays(7);
            var endDate = LocalDateTime.now();
            
            // Traditional query without delivery status
            var query = Messages.MessageQuery.of(startDate, endDate);
            ApiResponse<List<MessageJson>> response = messagesApi.getList(query);
            
            if (response.isOk() && response.getResponse() != null) {
                List<MessageJson> messageList = response.getResponse();
                System.out.println("✅ Retrieved " + messageList.size() + " messages (no delivery status)");
                
                messageList.stream()
                    .limit(3)
                    .forEach(msg -> System.out.println("   \uD83D\uDCE8 Message ID: " + msg.getMessageId() + 
                                                     " | Text: " + truncate(msg.getMessage(), 20) + "..."));
            } else {
                System.out.println("❌ Error: " + response.getErrorDescription());
            }
        } catch (Exception e) {
            System.out.println("❌ Exception: " + e.getMessage());
        }
    }
    
    /**
     * Test messages WITH delivery status (NEW FEATURE!)
     */
    private void testMessagesWithDeliveryStatus(Messages messages) {
        System.out.println("\n\uD83C\uDD95 Test 2: Messages WITH delivery status (NEW!)");
        System.out.println("-".repeat(40));
        
        try {
            var startDate = LocalDateTime.now().minusDays(7);
            var endDate = LocalDateTime.now();
            
            // NEW: Query WITH delivery status enabled
            var queryWithDelivery = Messages.MessageQuery.ofWithDeliveryStatus(startDate, endDate);
            ApiResponse<List<MessageJson>> response = messages.getList(queryWithDelivery);
            
            if (response.isOk() && response.getResponse() != null) {
                List<MessageJson> messageList = response.getResponse();
                System.out.println("✅ Retrieved " + messageList.size() + " messages (WITH delivery status)");
                System.out.println("\uD83D\uDCCA Delivery status information should be included!");
                
                messageList.stream()
                    .limit(3)
                    .forEach(msg -> {
                        System.out.println("   \uD83D\uDCE8 Message ID: " + msg.getMessageId());
                        System.out.println("      \uD83D\uDCCB Text: " + truncate(msg.getMessage(), 30) + "...");
                        System.out.println("      \uD83D\uDCC8 Status: " + msg.getMessageStatus());
                        System.out.println("      \uD83D\uDCC5 Date: " + msg.getCreatedOn());
                    });
            } else {
                System.out.println("❌ Error: " + response.getErrorDescription());
            }
        } catch (Exception e) {
            System.out.println("❌ Exception: " + e.getMessage());
        }
    }
    
    /**
     * Test modern async functionality
     */
    private void testAsyncMessages(Messages messages) {
        System.out.println("\n⚡ Test 3: Async Messages (Java 21 CompletableFuture)");
        System.out.println("-".repeat(40));
        
        try {
            var startDate = LocalDateTime.now().minusDays(1);
            var endDate = LocalDateTime.now();
            var query = Messages.MessageQuery.ofWithDeliveryStatus(startDate, endDate, 0, 10);
            
            // Async call
            CompletableFuture<ApiResponse<List<MessageJson>>> future = messages.getListAsync(query);
            
            System.out.println("⏳ Making async request...");
            
            // Use modern CompletableFuture handling
            future.thenAccept(response -> {
                if (response.isOk() && response.getResponse() != null) {
                    System.out.println("✅ Async: Retrieved " + response.getResponse().size() + " messages");
                } else {
                    System.out.println("❌ Async Error: " + response.getErrorDescription());
                }
            }).exceptionally(throwable -> {
                System.out.println("❌ Async Exception: " + throwable.getMessage());
                return null;
            });
            
            // Wait for completion (in real app, you wouldn't block like this)
            Thread.sleep(2000);
            
        } catch (Exception e) {
            System.out.println("❌ Exception: " + e.getMessage());
        }
    }
    
    /**
     * Test modern record-based message sending
     */
    private void testModernMessageSending(Messages messages, String testMsisdn, String testMessage) {
        System.out.println("\n\uD83D\uDCE4 Test 4: Modern Record-based Message Sending");
        System.out.println("-".repeat(40));
        
        try {
            // Modern way using Records - CORRECTED to use factory method
            var messageRequest = Messages.SendMessageRequest.toContact(
                "Hola",
                testMsisdn
            );
            
            System.out.println("📞 Sending to: " + testMsisdn);
            System.out.println("💬 Message: " + messageRequest.message());
            
            ApiResponse<MessageJson> response = messages.sendToContact(messageRequest);
            
            if (response.isOk() && response.getResponse() != null) {
                MessageJson sentMessage = response.getResponse();
                System.out.println("✅ Message sent successfully!");
                System.out.println("   \uD83D\uDCE8 Message ID: " + sentMessage.getMessageId());
                System.out.println("   \uD83D\uDCCB Status: " + sentMessage.getMessageStatus());
            } else {
                System.out.println("❌ Error sending message: " + response.getErrorDescription());
            }
            
        } catch (Exception e) {
            System.out.println("❌ Exception: " + e.getMessage());
        }
    }
    
    /**
     * Test fluent API with method chaining
     */
    private void testFluentAPI(Messages messages) {
        System.out.println("\n\uD83D\uDD17 Test 5: Fluent API with Method Chaining");
        System.out.println("-".repeat(40));
        
        try {
            var startDate = LocalDateTime.now().minusDays(3);
            var endDate = LocalDateTime.now();
            
            // Fluent API using Record methods
            var fluentQuery = Messages.MessageQuery.of(startDate, endDate)
                    .withDeliveryStatus(true)
                    .withPagination(0, 5)
                    .withDirection(MessageDirection.MT);
            
            ApiResponse<List<MessageJson>> response = messages.getList(fluentQuery);
            
            if (response.isOk() && response.getResponse() != null) {
                System.out.println("✅ Fluent query executed successfully!");
                System.out.println("   \uD83D\uDCCA Results: " + response.getResponse().size() + " messages");
                System.out.println("   \uD83D\uDFAF Filters: Delivery status ON, Last 3 days, Outbound only");
            } else {
                System.out.println("❌ Error: " + response.getErrorDescription());
            }
            
        } catch (Exception e) {
            System.out.println("❌ Exception: " + e.getMessage());
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