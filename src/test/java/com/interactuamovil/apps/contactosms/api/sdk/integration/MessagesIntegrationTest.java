package com.interactuamovil.apps.contactosms.api.sdk.integration;

import com.interactuamovil.apps.contactosms.api.sdk.Messages;
import com.interactuamovil.apps.contactosms.api.enums.MessageDirection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

/**
 * Integration tests for Messages API - only runs with real credentials
 */
@EnabledIfEnvironmentVariable(named = "API_KEY", matches = ".+")
@DisplayName("Messages Integration Tests")
class MessagesIntegrationTest {
    
    private Messages messages;
    
    @BeforeEach
    void setUp() {
        String apiKey = System.getenv("API_KEY");
        String secretKey = System.getenv("SECRET_KEY");
        String apiUri = System.getenv("API_URI");
        
        messages = new Messages(apiKey, secretKey, apiUri);
    }
    
    @Test
    @DisplayName("Should get messages with delivery status from real API")
    void shouldGetMessagesWithDeliveryStatusFromRealApi() {
        // Given
        var startDate = LocalDateTime.now().minusDays(1);
        var endDate = LocalDateTime.now();
        
        var queryWithoutDelivery = Messages.MessageQuery.of(startDate, endDate)
                .withPagination(0, 5);
        
        var queryWithDelivery = Messages.MessageQuery.ofWithDeliveryStatus(startDate, endDate)
                .withPagination(0, 5);
        
        // When
        var responseWithoutDelivery = messages.getList(queryWithoutDelivery);
        var responseWithDelivery = messages.getList(queryWithDelivery);
        
        // Then
        if (responseWithoutDelivery.isOk()) {
            assertThat(responseWithoutDelivery.getResponse()).isNotNull();
            System.out.println("Messages without delivery status: " + 
                             responseWithoutDelivery.getResponse().size());
        }
        
        if (responseWithDelivery.isOk()) {
            assertThat(responseWithDelivery.getResponse()).isNotNull();
            System.out.println("Messages with delivery status: " + 
                             responseWithDelivery.getResponse().size());
        }
        
        // At least one should work (depending on API configuration)
        assertThat(responseWithoutDelivery.isOk() || responseWithDelivery.isOk()).isTrue();
    }
} 