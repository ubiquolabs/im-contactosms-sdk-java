package com.interactuamovil.apps.contactosms.api.sdk;

import com.interactuamovil.apps.contactosms.api.enums.MessageDirection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Messages Delivery Status Tests")
class MessagesDeliveryStatusTest {
    
    private static final String TEST_API_KEY = "test-api-key";
    private static final String TEST_SECRET_KEY = "test-secret-key";
    private static final String TEST_API_URI = "https://api.test.com/";
    
    private Messages messages;
    
    @BeforeEach
    void setUp() {
        messages = new Messages(TEST_API_KEY, TEST_SECRET_KEY, TEST_API_URI);
    }
    
    @Test
    @DisplayName("Should create message query with delivery status enabled")
    void shouldCreateMessageQueryWithDeliveryStatusEnabled() {
        // Given
        var startDate = LocalDateTime.now().minusDays(7);
        var endDate = LocalDateTime.now();
        
        // When
        var query = Messages.MessageQuery.ofWithDeliveryStatus(startDate, endDate);
        
        // Then
        assertThat(query.deliveryStatusEnabled()).isTrue();
        assertThat(query.startDate()).isEqualTo(startDate);
        assertThat(query.endDate()).isEqualTo(endDate);
    }
    
    @Test
    @DisplayName("Should create message query with delivery status disabled by default")
    void shouldCreateMessageQueryWithDeliveryStatusDisabledByDefault() {
        // Given
        var startDate = LocalDateTime.now().minusDays(7);
        var endDate = LocalDateTime.now();
        
        // When
        var query = Messages.MessageQuery.of(startDate, endDate);
        
        // Then
        assertThat(query.deliveryStatusEnabled()).isFalse();
    }
    
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    @DisplayName("Should handle delivery status parameter correctly")
    @Disabled("This test times out when connecting to the test endpoint api.test.com")
    void shouldHandleDeliveryStatusParameterCorrectly(boolean deliveryStatusEnabled) {
        // Given
        var startDate = LocalDateTime.now().minusDays(7);
        var endDate = LocalDateTime.now();
        var query = new Messages.MessageQuery(
                startDate, endDate, 0, 50, null, MessageDirection.ALL, deliveryStatusEnabled
        );
        
        // When
        var response = messages.getList(query);
        
        // Then
        assertThat(response).isNotNull();
        // En ambiente de prueba, probablemente fallará por credenciales,
        // pero la estructura debe ser correcta
    }
    
    @Test
    @DisplayName("Should toggle delivery status in existing query")
    void shouldToggleDeliveryStatusInExistingQuery() {
        // Given
        var originalQuery = Messages.MessageQuery.of(
                LocalDateTime.now().minusDays(7), 
                LocalDateTime.now()
        );
        
        // When
        var enabledQuery = originalQuery.withDeliveryStatus(true);
        var disabledQuery = enabledQuery.withDeliveryStatus(false);
        
        // Then
        assertThat(originalQuery.deliveryStatusEnabled()).isFalse();
        assertThat(enabledQuery.deliveryStatusEnabled()).isTrue();
        assertThat(disabledQuery.deliveryStatusEnabled()).isFalse();
    }
    
    @Test
    @DisplayName("Should use convenience methods with delivery status")
    @Disabled("This test times out when connecting to the test endpoint api.test.com")
    void shouldUseConvenienceMethodsWithDeliveryStatus() {
        // Given
        var startDate = LocalDateTime.now().minusDays(7);
        var endDate = LocalDateTime.now();
        var msisdn = "50252017507";
        
        // When
        var response1 = messages.getListWithDeliveryStatus(startDate, endDate, 0, 50, msisdn);
        var response2 = messages.getListWithDeliveryStatus(startDate, endDate, 0, 50, msisdn, MessageDirection.MT);
        
        // Then
        assertThat(response1).isNotNull();
        assertThat(response2).isNotNull();
    }
} 