package com.interactuamovil.apps.contactosms.api.sdk;

import com.fasterxml.jackson.core.type.TypeReference;
import com.interactuamovil.apps.contactosms.api.client.rest.messages.MessageJson;
import com.interactuamovil.apps.contactosms.api.client.rest.messages.MessageRecipientsJson;
import com.interactuamovil.apps.contactosms.api.enums.MessageDirection;
import com.interactuamovil.apps.contactosms.api.utils.ApiResponse;
import com.interactuamovil.apps.contactosms.api.utils.JsonObjectCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * Modern Messages API client using Java 21 features
 * 
 * Updated to use:
 * - Java Time API instead of Date/SimpleDateFormat
 * - SLF4J logging instead of Log4J
 * - Records for data transfer
 * - Pattern matching and modern Java constructs
 * - Async operations with CompletableFuture
 * - Added delivery_status_enable parameter
 */
public final class Messages extends Request {
    
    private static final Logger logger = LoggerFactory.getLogger(Messages.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    /**
     * Message query parameters record
     */
    public record MessageQuery(
            LocalDateTime startDate,
            LocalDateTime endDate,
            int start,
            int limit,
            String msisdn,
            MessageDirection direction,
            boolean deliveryStatusEnabled
    ) {
        public MessageQuery {
            if (start < 0) start = 0;
            if (limit < 0) limit = 50;
            if (limit > 1000) limit = 1000; // Reasonable limit
        }
        
        public static MessageQuery of(LocalDateTime startDate, LocalDateTime endDate) {
            return new MessageQuery(startDate, endDate, 0, 50, null, MessageDirection.ALL, false);
        }
        
        public static MessageQuery ofWithDeliveryStatus(LocalDateTime startDate, LocalDateTime endDate) {
            return new MessageQuery(startDate, endDate, 0, 50, null, MessageDirection.ALL, true);
        }
        
        public static MessageQuery ofWithDeliveryStatus(LocalDateTime startDate, LocalDateTime endDate, 
                                                        int start, int limit) {
            return new MessageQuery(startDate, endDate, start, limit, null, MessageDirection.ALL, true);
        }
        
        public MessageQuery withDeliveryStatus(boolean enabled) {
            return new MessageQuery(startDate, endDate, start, limit, msisdn, direction, enabled);
        }
        
        public MessageQuery withPagination(int start, int limit) {
            return new MessageQuery(startDate, endDate, start, limit, msisdn, direction, deliveryStatusEnabled);
        }
        
        public MessageQuery withMsisdn(String msisdn) {
            return new MessageQuery(startDate, endDate, start, limit, msisdn, direction, deliveryStatusEnabled);
        }
        
        public MessageQuery withDirection(MessageDirection direction) {
            return new MessageQuery(startDate, endDate, start, limit, msisdn, direction, deliveryStatusEnabled);
        }
    }
    
    /**
     * Send message request record
     */
    public record SendMessageRequest(
            String message,
            String messageId,
            Optional<String> msisdn,
            Optional<String[]> tagNames
    ) {
        public SendMessageRequest {
            if (message == null || message.isBlank()) {
                throw new IllegalArgumentException("Message cannot be null or blank");
            }
        }
        
        public static SendMessageRequest toContact(String message, String msisdn) {
            return new SendMessageRequest(message, null, Optional.of(msisdn), Optional.empty());
        }
        
        public static SendMessageRequest toGroups(String message, String[] tagNames) {
            return new SendMessageRequest(message, null, Optional.empty(), Optional.of(tagNames));
        }
    }
    
    public Messages(String apiKey, String secretKey, String apiUri) {
        super(apiKey, secretKey, apiUri);
    }
    
    /**
     * Gets message list with modern query parameters including delivery status
     */
    public ApiResponse<List<MessageJson>> getList(Date startDate, Date endDate, int start, int limit, String msisdn) {
        return getList(startDate, endDate, start, limit, msisdn, MessageDirection.ALL, false);
    }

    /**
     * Gets log message list
     *
     * @param startDate The star date
     * @param endDate The end date
     * @param start the offset of the results
     * @param limit the limit of the result list
     * @param msisdn The msisdn
     * @param MessageDirection The messages direction
     * @return The messages list queried
     */
    public ApiResponse<List<MessageJson>> getList(Date startDate, Date endDate, int start, int limit, String msisdn, MessageDirection direction) {
        return getList(startDate, endDate, start, limit, msisdn, direction, false);
    }

    /**
     * Gets log message list
     *
     * @param startDate The star date
     * @param endDate The end date
     * @param start the offset of the results
     * @param limit the limit of the result list
     * @param msisdn The msisdn
     * @param deliveryStatusEnable enable delivery status on message response
     * @return The messages list queried
     */
    public ApiResponse<List<MessageJson>> getList(Date startDate, Date endDate, int start, int limit, String msisdn, boolean deliveryStatusEnable) {
        return getList(startDate, endDate, start, limit, msisdn, MessageDirection.ALL, deliveryStatusEnable);
    }


	/**
	 * Gets log message list
	 *
	 * @param startDate The star date
	 * @param endDate The end date
	 * @param start the offset of the results
	 * @param limit the limit of the result list
	 * @param msisdn The msisdn
	 * @return The messages list queried
	 */
    public ApiResponse<List<MessageJson>> getList(Date startDate, Date endDate, int start, int limit, String msisdn, MessageDirection direction, boolean deliveryStatusEnable) {
        Map<String, Serializable> urlParameters = new LinkedHashMap<String, Serializable>();
        ApiResponse<List<MessageJson>> response;
        List<MessageJson> messageResponse;


        if (!(startDate == null) && !(endDate == null)) {
            urlParameters.put("start_date", getDateFormat(startDate));
            urlParameters.put("end_date", getDateFormat(endDate));
        }
        if (start != -1)
            urlParameters.put("start", start);
        if (limit != -1)
            urlParameters.put("limit", limit);
        if (msisdn != null)
            urlParameters.put("msisdn", msisdn);
        if (direction != null)
            urlParameters.put("direction", direction.name());
        if (deliveryStatusEnable)
            urlParameters.put("delivery_status_enable", deliveryStatusEnable.toString());

        try {
            var rawResponse = doRequest("messages", "get", urlParameters, null, true);
            var response = new ApiResponse<List<MessageJson>>();
            
            // Copy response metadata
            response.setHttpCode(rawResponse.getHttpCode());
            response.setErrorCode(rawResponse.getErrorCode());
            response.setErrorDescription(rawResponse.getErrorDescription());
            response.setRawResponse(rawResponse.getRawResponse());
            
            if (rawResponse.isOk()) {
                var messageList = JsonObjectCollection.fromJson(
                        rawResponse.getRawResponse(), 
                        new TypeReference<List<MessageJson>>() {}
                );
                response.setResponse(messageList);
                logger.debug("Successfully retrieved {} messages (delivery status: {})", 
                           messageList.size(), query.deliveryStatusEnabled());
            }
            return response;
        } catch (Exception e) {
            logger.error("Error getting message list", e);
            return createErrorResponse("Failed to get message list: " + e.getMessage());
        }
    }
    
    /**
     * Convenience method with LocalDateTime parameters
     */
    public ApiResponse<List<MessageJson>> getList(LocalDateTime startDate, LocalDateTime endDate, 
                                                  int start, int limit, String msisdn) {
        return getList(new MessageQuery(startDate, endDate, start, limit, msisdn, MessageDirection.ALL, false));
    }
    
    /**
     * Convenience method with delivery status enabled
     */
    public ApiResponse<List<MessageJson>> getListWithDeliveryStatus(LocalDateTime startDate, LocalDateTime endDate, 
                                                                   int start, int limit, String msisdn) {
        return getList(new MessageQuery(startDate, endDate, start, limit, msisdn, MessageDirection.ALL, true));
    }
    
    /**
     * Convenience method with delivery status and direction
     */
    public ApiResponse<List<MessageJson>> getListWithDeliveryStatus(LocalDateTime startDate, LocalDateTime endDate, 
                                                                   int start, int limit, String msisdn, 
                                                                   MessageDirection direction) {
        return getList(new MessageQuery(startDate, endDate, start, limit, msisdn, direction, true));
    }
    
    /**
     * Async version of getList
     */
    public CompletableFuture<ApiResponse<List<MessageJson>>> getListAsync(MessageQuery query) {
        return doRequestAsync("messages", "get", buildQueryParams(query), null, true);
    }
    
    /**
     * Send message to contact using modern pattern
     */
    public ApiResponse<MessageJson> sendToContact(SendMessageRequest request) {
        logger.debug("Sending message to contact: {}", request.msisdn().orElse("unknown"));
        
        if (request.msisdn().isEmpty()) {
            throw new IllegalArgumentException("MSISDN is required for contact messages");
        }
        
        var params = new LinkedHashMap<String, Serializable>();
        params.put("msisdn", request.msisdn().get());
        params.put("message", request.message());
        
        Optional.ofNullable(request.messageId())
                .ifPresent(id -> params.put("id", id));
        
        return sendMessage("messages/send_to_contact", params);
    }
    
    /**
     * Send message to groups using modern pattern
     */
    public ApiResponse<MessageJson> sendToGroups(SendMessageRequest request) {
        logger.debug("Sending message to groups: {}", Arrays.toString(request.tagNames().orElse(new String[0])));
        
        if (request.tagNames().isEmpty()) {
            throw new IllegalArgumentException("Tag names are required for group messages");
        }
        
        var params = new LinkedHashMap<String, Serializable>();
        params.put("tags", request.tagNames().get());
        params.put("message", request.message());
        
        Optional.ofNullable(request.messageId())
                .ifPresent(id -> params.put("id", id));
        
        return sendMessage("messages/send", params);
    }
    
    /**
     * Async send to contact
     */
    public CompletableFuture<ApiResponse<MessageJson>> sendToContactAsync(SendMessageRequest request) {
        return CompletableFuture.supplyAsync(() -> sendToContact(request));
    }
    
    /**
     * Async send to groups
     */
    public CompletableFuture<ApiResponse<MessageJson>> sendToGroupsAsync(SendMessageRequest request) {
        return CompletableFuture.supplyAsync(() -> sendToGroups(request));
    }
    
    /**
     * Get message recipients with modern pagination
     */
    @SuppressWarnings("unchecked")
    public ApiResponse<List<MessageRecipientsJson>> getMessageRecipients(int messageId, int page, int limit) {
        logger.debug("Getting message recipients for messageId: {}, page: {}, limit: {}", messageId, page, limit);
        
        var validatedPage = Math.max(1, page);
        var validatedLimit = Math.clamp(limit, 1, 1000);
        
        var urlParams = Map.of(
                "message_id", messageId,
                "page", validatedPage,
                "limit", validatedLimit
        );
        
        try {
            var rawResponse = doRequest("messages/%s/recipients".formatted(messageId), "get", 
                                      new LinkedHashMap<>(urlParams), null, true);
            
            var response = new ApiResponse<List<MessageRecipientsJson>>();
            
            // Copy response metadata - USAR getHttpCode() NO getStatus()
            response.setHttpCode(rawResponse.getHttpCode());
            response.setErrorCode(rawResponse.getErrorCode());
            response.setErrorDescription(rawResponse.getErrorDescription());
            response.setRawResponse(rawResponse.getRawResponse());
            
            if (rawResponse.isOk()) {
                var recipients = JsonObjectCollection.fromJson(
                        rawResponse.getRawResponse(), 
                        new TypeReference<List<MessageRecipientsJson>>() {}
                );
                response.setResponse(recipients);
                logger.debug("Successfully retrieved {} message recipients", recipients.size());
            }
            return response;
        } catch (Exception e) {
            logger.error("Error getting message recipients", e);
            return createErrorResponse("Failed to get message recipients: " + e.getMessage());
        }
    }
    
    // Private helper methods
    @SuppressWarnings("unchecked")
    private ApiResponse<MessageJson> sendMessage(String endpoint, Map<String, Serializable> params) {
        try {
            var rawResponse = doRequest(endpoint, "post", null, params, false);
            var response = new ApiResponse<MessageJson>();
            
            // Copy response metadata - USAR getHttpCode() NO getStatus()
            response.setHttpCode(rawResponse.getHttpCode());
            response.setErrorCode(rawResponse.getErrorCode());
            response.setErrorDescription(rawResponse.getErrorDescription());
            response.setRawResponse(rawResponse.getRawResponse());
            
            if (rawResponse.isOk()) {
                var messageResponse = MessageJson.fromJson(rawResponse.getRawResponse());
                response.setResponse(messageResponse);
                logger.debug("Successfully sent message");
            }
            return response;
        } catch (Exception e) {
            logger.error("Error sending message", e);
            return createErrorResponse("Failed to send message: " + e.getMessage());
        }
    }
    
    private Map<String, Serializable> buildQueryParams(MessageQuery query) {
        var params = new LinkedHashMap<String, Serializable>();
        
        Optional.ofNullable(query.startDate())
                .ifPresent(date -> params.put("start_date", DATE_TIME_FORMATTER.format(date)));
        
        Optional.ofNullable(query.endDate())
                .ifPresent(date -> params.put("end_date", DATE_TIME_FORMATTER.format(date)));
        
        if (query.start() > 0) params.put("start", query.start());
        if (query.limit() > 0) params.put("limit", query.limit());
        
        Optional.ofNullable(query.msisdn())
                .filter(msisdn -> !msisdn.isBlank())
                .ifPresent(msisdn -> params.put("msisdn", msisdn));
        
        Optional.ofNullable(query.direction())
                .ifPresent(direction -> params.put("direction", direction.name()));
        
        // Add delivery_status_enable parameter
        if (query.deliveryStatusEnabled()) {
            params.put("delivery_status_enable", "true");
        }
        
        return params;
    }
    
    private <T> ApiResponse<T> createErrorResponse(String message) {
        var response = new ApiResponse<T>();
        response.setErrorCode(-1);
        response.setErrorDescription(message);
        return response;
    }
}