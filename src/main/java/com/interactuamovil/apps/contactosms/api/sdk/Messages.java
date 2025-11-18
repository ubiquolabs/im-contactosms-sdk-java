package com.interactuamovil.apps.contactosms.api.sdk;

import com.fasterxml.jackson.core.type.TypeReference;
import com.interactuamovil.apps.contactosms.api.client.rest.messages.MessageJson;
import com.interactuamovil.apps.contactosms.api.client.rest.messages.MessageRecipientsJson;
import com.interactuamovil.apps.contactosms.api.enums.MessageDirection;
import com.interactuamovil.apps.contactosms.api.utils.ApiResponse;
import com.interactuamovil.apps.contactosms.api.utils.JavaVersionDetector;
import com.interactuamovil.apps.contactosms.api.utils.JsonObjectCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
     * Message query parameters class (Java 8 compatible)
     */
    public static final class MessageQuery {
        private final LocalDateTime startDate;
        private final LocalDateTime endDate;
        private final int start;
        private final int limit;
        private final String msisdn;
        private final MessageDirection direction;
        private final boolean deliveryStatusEnabled;
        
        public MessageQuery(LocalDateTime startDate, LocalDateTime endDate, int start, int limit, 
                           String msisdn, MessageDirection direction, boolean deliveryStatusEnabled) {
            int validatedStart = start < 0 ? 0 : start;
            int validatedLimit = limit < 0 ? 50 : (limit > 1000 ? 1000 : limit);
            this.startDate = startDate;
            this.endDate = endDate;
            this.start = validatedStart;
            this.limit = validatedLimit;
            this.msisdn = msisdn;
            this.direction = direction;
            this.deliveryStatusEnabled = deliveryStatusEnabled;
        }
        
        public LocalDateTime startDate() { return startDate; }
        public LocalDateTime endDate() { return endDate; }
        public int start() { return start; }
        public int limit() { return limit; }
        public String msisdn() { return msisdn; }
        public MessageDirection direction() { return direction; }
        public boolean deliveryStatusEnabled() { return deliveryStatusEnabled; }
        
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
     * Send message request class (Java 8 compatible)
     */
    public static final class SendMessageRequest {
        private final String message;
        private final String messageId;
        private final Optional<String> msisdn;
        private final Optional<String[]> tagNames;
        
        public SendMessageRequest(String message, String messageId, Optional<String> msisdn, Optional<String[]> tagNames) {
            if (message == null || isBlank(message)) {
                throw new IllegalArgumentException("Message cannot be null or blank");
            }
            this.message = message;
            this.messageId = messageId;
            this.msisdn = msisdn;
            this.tagNames = tagNames;
        }
        
        public String message() { return message; }
        public String messageId() { return messageId; }
        public Optional<String> msisdn() { return msisdn; }
        public Optional<String[]> tagNames() { return tagNames; }
        
        public static SendMessageRequest toContact(String message, String msisdn) {
            String messageId = String.valueOf(System.currentTimeMillis());
            return new SendMessageRequest(message, messageId, Optional.of(msisdn), Optional.<String[]>empty());
        }
        
        public static SendMessageRequest toGroups(String message, String[] tagNames) {
            return new SendMessageRequest(message, null, Optional.<String>empty(), Optional.of(tagNames));
        }
    }
    
    public Messages(String apiKey, String secretKey, String apiUri) {
        super(apiKey, secretKey, apiUri);
    }
    
    private static boolean isBlank(String str) {
        if (str == null) {
            return true;
        }
        if (JavaVersionDetector.isJava11OrHigher()) {
            try {
                java.lang.reflect.Method isBlankMethod = String.class.getMethod("isBlank");
                return (Boolean) isBlankMethod.invoke(str);
            } catch (Exception e) {
                return str.trim().isEmpty();
            }
        } else {
            return str.trim().isEmpty();
        }
    }
    
    private static int clamp(int value, int min, int max) {
        if (JavaVersionDetector.isJava21OrHigher()) {
            try {
                java.lang.reflect.Method clampMethod = Math.class.getMethod("clamp", int.class, int.class, int.class);
                return (Integer) clampMethod.invoke(null, value, min, max);
            } catch (Exception e) {
                return Math.max(min, Math.min(max, value));
            }
        } else {
            return Math.max(min, Math.min(max, value));
        }
    }
    
    private static <K, V> Map<K, V> createMap(Object... keyValuePairs) {
        if (keyValuePairs.length % 2 != 0) {
            throw new IllegalArgumentException("Key-value pairs must be even");
        }
        if (JavaVersionDetector.isJava9OrHigher()) {
            try {
                java.lang.reflect.Method ofMethod = Map.class.getMethod("of", Object[].class);
                @SuppressWarnings("unchecked")
                Map<K, V> result = (Map<K, V>) ofMethod.invoke(null, (Object) keyValuePairs);
                return result;
            } catch (Exception e) {
                return createMapFallback(keyValuePairs);
            }
        } else {
            return createMapFallback(keyValuePairs);
        }
    }
    
    @SuppressWarnings("unchecked")
    private static <K, V> Map<K, V> createMapFallback(Object... keyValuePairs) {
        Map<K, V> map = new LinkedHashMap<K, V>();
        for (int i = 0; i < keyValuePairs.length; i += 2) {
            map.put((K) keyValuePairs[i], (V) keyValuePairs[i + 1]);
        }
        return Collections.unmodifiableMap(map);
    }
    
    private static String formatString(String format, Object... args) {
        if (JavaVersionDetector.isJava15OrHigher()) {
            try {
                java.lang.reflect.Method formattedMethod = String.class.getMethod("formatted", Object[].class);
                return (String) formattedMethod.invoke(format, (Object) args);
            } catch (Exception e) {
                return String.format(format, args);
            }
        } else {
            return String.format(format, args);
        }
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
     * @param direction The messages direction
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
        logger.debug("Getting message list with delivery status: {}", deliveryStatusEnable);
        
        LinkedHashMap<String, Serializable> urlParameters = new LinkedHashMap<String, Serializable>();
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
            urlParameters.put("delivery_status_enable", deliveryStatusEnable);

        try {
            ApiResponse<?> rawResponse = doRequest("messages", "get", urlParameters, null, true);
            response = new ApiResponse<List<MessageJson>>();
            
            // Copy response metadata
            response.setHttpCode(rawResponse.getHttpCode());
            response.setErrorCode(rawResponse.getErrorCode());
            response.setErrorDescription(rawResponse.getErrorDescription());
            response.setRawResponse(rawResponse.getRawResponse());
            
            if (rawResponse.isOk()) {
                List<MessageJson> messageList = JsonObjectCollection.fromJson(
                        rawResponse.getRawResponse(), 
                        new TypeReference<List<MessageJson>>() {}
                );
                response.setResponse(messageList);
                logger.debug("Successfully retrieved {} messages (delivery status: {})", 
                           messageList.size(), deliveryStatusEnable);
            }
            return response;
        } catch (Exception e) {
            logger.error("Error getting message list", e);
            return createErrorResponse("Failed to get message list: " + e.getMessage());
        }
    }
    
    /**
     * Get message list using MessageQuery record
     */
    public ApiResponse<List<MessageJson>> getList(MessageQuery query) {
        logger.debug("Getting message list with query: {}", query);
        
        LinkedHashMap<String, Serializable> urlParameters = new LinkedHashMap<String, Serializable>();
        
        if (query.startDate() != null && query.endDate() != null) {
            urlParameters.put("start_date", query.startDate().format(DATE_TIME_FORMATTER));
            urlParameters.put("end_date", query.endDate().format(DATE_TIME_FORMATTER));
        }
        if (query.start() >= 0)
            urlParameters.put("start", query.start());
        if (query.limit() > 0)
            urlParameters.put("limit", query.limit());
        if (query.msisdn() != null)
            urlParameters.put("msisdn", query.msisdn());
        if (query.direction() != null)
            urlParameters.put("direction", query.direction().name());
        if (query.deliveryStatusEnabled())
            urlParameters.put("delivery_status_enable", query.deliveryStatusEnabled());

        try {
            ApiResponse<?> rawResponse = doRequest("messages", "get", urlParameters, null, true);
            ApiResponse<List<MessageJson>> response = new ApiResponse<List<MessageJson>>();
            
            // Copy response metadata
            response.setHttpCode(rawResponse.getHttpCode());
            response.setErrorCode(rawResponse.getErrorCode());
            response.setErrorDescription(rawResponse.getErrorDescription());
            response.setRawResponse(rawResponse.getRawResponse());
            
            if (rawResponse.isOk()) {
                List<MessageJson> messageList = JsonObjectCollection.fromJson(
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
        
        if (!request.msisdn().isPresent()) {
            throw new IllegalArgumentException("MSISDN is required for contact messages");
        }
        
        LinkedHashMap<String, Serializable> params = new LinkedHashMap<String, Serializable>();
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
        
        if (!request.tagNames().isPresent()) {
            throw new IllegalArgumentException("Tag names are required for group messages");
        }
        
        LinkedHashMap<String, Serializable> params = new LinkedHashMap<String, Serializable>();
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
        
        int validatedPage = Math.max(1, page);
        int validatedLimit = clamp(limit, 1, 1000);
        
        LinkedHashMap<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();
        urlParams.put("message_id", messageId);
        urlParams.put("page", validatedPage);
        urlParams.put("limit", validatedLimit);
        
        try {
            ApiResponse<?> rawResponse = doRequest(formatString("messages/%s/recipients", messageId), "get", 
                                      urlParams, null, true);
            
            ApiResponse<List<MessageRecipientsJson>> response = new ApiResponse<List<MessageRecipientsJson>>();
            
            // Copy response metadata - USAR getHttpCode() NO getStatus()
            response.setHttpCode(rawResponse.getHttpCode());
            response.setErrorCode(rawResponse.getErrorCode());
            response.setErrorDescription(rawResponse.getErrorDescription());
            response.setRawResponse(rawResponse.getRawResponse());
            
            if (rawResponse.isOk()) {
                List<MessageRecipientsJson> recipients = JsonObjectCollection.fromJson(
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
            ApiResponse<?> rawResponse = doRequest(endpoint, "post", null, params, false);
            ApiResponse<MessageJson> response = new ApiResponse<MessageJson>();
            
            // Copy response metadata - USAR getHttpCode() NO getStatus()
            response.setHttpCode(rawResponse.getHttpCode());
            response.setErrorCode(rawResponse.getErrorCode());
            response.setErrorDescription(rawResponse.getErrorDescription());
            response.setRawResponse(rawResponse.getRawResponse());
            
            if (rawResponse.isOk()) {
                MessageJson messageResponse = MessageJson.fromJson(rawResponse.getRawResponse());
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
        LinkedHashMap<String, Serializable> params = new LinkedHashMap<String, Serializable>();
        
        Optional.ofNullable(query.startDate())
                .ifPresent(date -> params.put("start_date", DATE_TIME_FORMATTER.format(date)));
        
        Optional.ofNullable(query.endDate())
                .ifPresent(date -> params.put("end_date", DATE_TIME_FORMATTER.format(date)));
        
        if (query.start() > 0) params.put("start", query.start());
        if (query.limit() > 0) params.put("limit", query.limit());
        
        Optional.ofNullable(query.msisdn())
                .filter(msisdn -> !isBlank(msisdn))
                .ifPresent(msisdn -> params.put("msisdn", msisdn));
        
        Optional.ofNullable(query.direction())
                .ifPresent(direction -> params.put("direction", direction.name()));
        
        // Add delivery_status_enable parameter
        if (query.deliveryStatusEnabled()) {
            params.put("delivery_status_enable", "true");
        }
        
        return params;
    }
    
    /**
     * Get date format for API requests
     */
    private String getDateFormat(Date date) {
        if (date == null) return null;
        return DATE_TIME_FORMATTER.format(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
    }
    
    private <T> ApiResponse<T> createErrorResponse(String message) {
        ApiResponse<T> response = new ApiResponse<T>();
        response.setErrorCode(-1);
        response.setErrorDescription(message);
        return response;
    }
}