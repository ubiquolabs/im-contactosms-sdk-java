/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interactuamovil.apps.contactosms.api.sdk;

import com.interactuamovil.apps.contactosms.api.client.rest.messages.MessageJson;
import com.interactuamovil.apps.contactosms.api.client.rest.messages.MessageRecipientsJson;
import com.interactuamovil.apps.contactosms.api.enums.MessageDirection;
import com.interactuamovil.apps.contactosms.api.utils.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Modern unit tests for Messages class using JUnit 5
 * 
 * Features:
 * - JUnit 5 annotations and lifecycle
 * - Java Time API instead of Date
 * - Mockito for mocking
 * - AssertJ for fluent assertions
 * - Parameterized tests
 * - Nested test classes
 * - Modern record-based testing
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Messages API - Modern Tests")
class MessagesTest {
    
    private static final String TEST_API_KEY = "test-api-key";
    private static final String TEST_SECRET_KEY = "test-secret-key";
    private static final String TEST_API_URI = "https://api.test.com/";
    private static final String TEST_MSISDN = "50252017507";
    private static final String TEST_MESSAGE = "Test message content";
    
    @Mock
    private Messages messages;
    
    private Messages realMessages;
    
    @BeforeEach
    void setUp() {
        realMessages = new Messages(TEST_API_KEY, TEST_SECRET_KEY, TEST_API_URI);
    }
    
    @Nested
    @DisplayName("Message Query Tests")
    class MessageQueryTests {
        
        @Test
        @DisplayName("Should create message query with date range")
        void shouldCreateMessageQueryWithDateRange() {
            // Given
            var startDate = LocalDateTime.now().minusDays(7);
            var endDate = LocalDateTime.now();
            
            // When
            var query = Messages.MessageQuery.of(startDate, endDate);
            
            // Then
            assertThat(query.startDate()).isEqualTo(startDate);
            assertThat(query.endDate()).isEqualTo(endDate);
            assertThat(query.start()).isEqualTo(0);
            assertThat(query.limit()).isEqualTo(50);
            assertThat(query.msisdn()).isNull();
            assertThat(query.direction()).isEqualTo(MessageDirection.ALL);
        }
        
        @Test
        @DisplayName("Should create message query with custom parameters")
        void shouldCreateMessageQueryWithCustomParameters() {
            // Given
            var startDate = LocalDateTime.now().minusDays(7);
            var endDate = LocalDateTime.now();
            
            // When
            var query = new Messages.MessageQuery(
                    startDate, endDate, 10, 100, TEST_MSISDN, MessageDirection.MO, false
            );
            
            // Then
            assertThat(query.startDate()).isEqualTo(startDate);
            assertThat(query.endDate()).isEqualTo(endDate);
            assertThat(query.start()).isEqualTo(10);
            assertThat(query.limit()).isEqualTo(100);
            assertThat(query.msisdn()).isEqualTo(TEST_MSISDN);
            assertThat(query.direction()).isEqualTo(MessageDirection.MO);
        }
        
        @Test
        @DisplayName("Should validate and correct negative values")
        void shouldValidateAndCorrectNegativeValues() {
            // When
            var query = new Messages.MessageQuery(
                    null, null, -5, -10, null, null, false
            );
            
            // Then
            assertThat(query.start()).isEqualTo(0);
            assertThat(query.limit()).isEqualTo(50);
        }
        
        @Test
        @DisplayName("Should limit maximum query limit")
        void shouldLimitMaximumQueryLimit() {
            // When
            var query = new Messages.MessageQuery(
                    null, null, 0, 2000, null, null, false
            );
            
            // Then
            assertThat(query.limit()).isEqualTo(1000);
        }
    }
    
    @Nested
    @DisplayName("Send Message Request Tests")
    class SendMessageRequestTests {
        
        @Test
        @DisplayName("Should create send message request for contact")
        void shouldCreateSendMessageRequestForContact() {
            // When
            var request = Messages.SendMessageRequest.toContact(TEST_MESSAGE, TEST_MSISDN);
            
            // Then
            assertThat(request.message()).isEqualTo(TEST_MESSAGE);
            assertThat(request.msisdn()).hasValue(TEST_MSISDN);
            assertThat(request.tagNames()).isEmpty();
            assertThat(request.messageId()).isNull();
        }
        
        @Test
        @DisplayName("Should create send message request for groups")
        void shouldCreateSendMessageRequestForGroups() {
            // Given
            String[] tagNames = {"tag1", "tag2"};
            
            // When
            var request = Messages.SendMessageRequest.toGroups(TEST_MESSAGE, tagNames);
            
            // Then
            assertThat(request.message()).isEqualTo(TEST_MESSAGE);
            assertThat(request.msisdn()).isEmpty();
            assertThat(request.tagNames()).hasValue(tagNames);
            assertThat(request.messageId()).isNull();
        }
        
        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {" ", "  ", "\t", "\n"})
        @DisplayName("Should validate message content")
        void shouldValidateMessageContent(String invalidMessage) {
            // Then
            assertThatThrownBy(() -> 
                    new Messages.SendMessageRequest(invalidMessage, null, 
                            Optional.of(TEST_MSISDN), Optional.empty()))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Message cannot be null or blank");
        }
        
        @Test
        @DisplayName("Should require valid message content")
        void shouldRequireValidMessageContent() {
            // Then
            assertThatThrownBy(() -> 
                    Messages.SendMessageRequest.toContact(null, TEST_MSISDN))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
    
    @Nested
    @DisplayName("Message Operations Tests")
    @Disabled("These are integration tests that fail due to connection timeouts with the test endpoint.")
    class MessageOperationsTests {
        
        @Test
        @DisplayName("Should get message list with query")
        void shouldGetMessageListWithQuery() {
            // Given
            var query = Messages.MessageQuery.of(
                    LocalDateTime.now().minusDays(7), 
                    LocalDateTime.now()
            );
            
            // When
            var response = realMessages.getList(query);
            
            // Then
            assertThat(response).isNotNull();
            // In test environment, this will likely fail, but structure should be correct
        }
        
        @Test
        @DisplayName("Should get message list with LocalDateTime parameters")
        void shouldGetMessageListWithLocalDateTimeParameters() {
            // Given
            var startDate = LocalDateTime.now().minusDays(7);
            var endDate = LocalDateTime.now();
            
            // When
            var response = realMessages.getList(startDate, endDate, 0, 50, TEST_MSISDN);
            
            // Then
            assertThat(response).isNotNull();
        }
        
        @Test
        @DisplayName("Should send message to contact")
        void shouldSendMessageToContact() {
            // Given
            var request = Messages.SendMessageRequest.toContact(TEST_MESSAGE, TEST_MSISDN);
            
            // When
            var response = realMessages.sendToContact(request);
            
            // Then
            assertThat(response).isNotNull();
        }
        
        @Test
        @DisplayName("Should send message to groups")
        void shouldSendMessageToGroups() {
            // Given
            var request = Messages.SendMessageRequest.toGroups(TEST_MESSAGE, new String[]{"tag1", "tag2"});
            
            // When
            var response = realMessages.sendToGroups(request);
            
            // Then
            assertThat(response).isNotNull();
        }
        
        @Test
        @DisplayName("Should validate send to contact request")
        void shouldValidateSendToContactRequest() {
            // Given
            var request = Messages.SendMessageRequest.toGroups(TEST_MESSAGE, new String[]{"tag1"});
            
            // When/Then
            assertThatThrownBy(() -> realMessages.sendToContact(request))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("MSISDN is required");
        }
        
        @Test
        @DisplayName("Should validate send to groups request")
        void shouldValidateSendToGroupsRequest() {
            // Given
            var request = Messages.SendMessageRequest.toContact(TEST_MESSAGE, TEST_MSISDN);
            
            // When/Then
            assertThatThrownBy(() -> realMessages.sendToGroups(request))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Tag names are required");
        }
        
        @ParameterizedTest
        @ValueSource(ints = {0, 1, 2, 5, 10, 50, 100})
        @DisplayName("Should get message recipients with different page sizes")
        void shouldGetMessageRecipientsWithDifferentPageSizes(int limit) {
            // Given
            int messageId = 123;
            int page = 1;
            
            // When
            var response = realMessages.getMessageRecipients(messageId, page, limit);
            
            // Then
            assertThat(response).isNotNull();
        }
        
        @Test
        @DisplayName("Should clamp message recipients parameters")
        void shouldClampMessageRecipientsParameters() {
            // Given
            int messageId = 123;
            
            // When
            var response = realMessages.getMessageRecipients(messageId, -1, 2000);
            
            // Then
            assertThat(response).isNotNull();
            // We can't verify the parameters were clamped without mocking the underlying request,
            // but we ensure the call doesn't throw an exception.
        }
    }
    
    @Nested
    @DisplayName("Async Operations Tests")
    @Disabled("These are integration tests that fail due to connection timeouts with the test endpoint.")
    class AsyncOperationsTests {
        
        @Test
        @DisplayName("Should execute async getList operation")
        void shouldExecuteAsyncGetListOperation() {
            // Given
            var query = Messages.MessageQuery.of(
                    LocalDateTime.now().minusDays(7), 
                    LocalDateTime.now()
            );
            
            // When
            CompletableFuture<ApiResponse<List<MessageJson>>> future = 
                    realMessages.getListAsync(query);
            
            // Then
            assertThat(future).isNotNull();
            assertThatNoException().isThrownBy(() -> {
                var response = future.get();
                assertThat(response).isNotNull();
            });
        }
        
        @Test
        @DisplayName("Should execute async send to contact operation")
        void shouldExecuteAsyncSendToContactOperation() {
            // Given
            var request = Messages.SendMessageRequest.toContact(TEST_MESSAGE, TEST_MSISDN);
            
            // When
            CompletableFuture<ApiResponse<MessageJson>> future = 
                    realMessages.sendToContactAsync(request);
            
            // Then
            assertThat(future).isNotNull();
            assertThatNoException().isThrownBy(() -> {
                var response = future.get();
                assertThat(response).isNotNull();
            });
        }
        
        @Test
        @DisplayName("Should execute async send to groups operation")
        void shouldExecuteAsyncSendToGroupsOperation() {
            // Given
            var request = Messages.SendMessageRequest.toGroups(TEST_MESSAGE, new String[]{"tag1"});
            
            // When
            CompletableFuture<ApiResponse<MessageJson>> future = 
                    realMessages.sendToGroupsAsync(request);
            
            // Then
            assertThat(future).isNotNull();
            assertThatNoException().isThrownBy(() -> {
                var response = future.get();
                assertThat(response).isNotNull();
            });
        }
    }
    
    @Test
    @DisplayName("Should create messages instance with valid parameters")
    void shouldCreateMessagesInstanceWithValidParameters() {
        // When
        var messages = new Messages(TEST_API_KEY, TEST_SECRET_KEY, TEST_API_URI);
        
        // Then
        assertThat(messages).isNotNull();
        assertThat(messages.getApiKey()).isEqualTo(TEST_API_KEY);
        assertThat(messages.getApiSecretKey()).isEqualTo(TEST_SECRET_KEY);
        assertThat(messages.getApiUri()).isEqualTo(TEST_API_URI);
    }
}