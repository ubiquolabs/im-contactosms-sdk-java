package com.interactuamovil.apps.contactosms.api.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Modern Message Status enum using Java 21 features
 *
 * Updated to use:
 * - Pattern matching for switch expressions
 * - Sealed interfaces for status categories
 * - Stream API for utility methods
 * - Better categorization and state management
 */
public enum MessageStatus {
    
    WAITING_UPLOAD("WAITING_UPLOAD", "Message is waiting for upload", StatusCategory.PENDING),
    PENDING("PENDING", "Message is pending processing", StatusCategory.PENDING),
    PROCESSING("PROCESSING", "Message is being processed", StatusCategory.PROCESSING),
    READY("READY", "Message is ready to be sent", StatusCategory.READY),
    SENT("SENT", "Message has been sent", StatusCategory.SENT),
    UNREAD("UNREAD", "Message has been delivered but not read", StatusCategory.DELIVERED),
    READ("READ", "Message has been read", StatusCategory.DELIVERED),
    REPLIED("REPLIED", "Message has been replied to", StatusCategory.DELIVERED),
    FORWARDED("FORWARDED", "Message has been forwarded", StatusCategory.DELIVERED),
    ERROR("ERROR", "Message delivery failed", StatusCategory.ERROR);
    
    /**
     * Sealed interface for status categories
     */
    public sealed interface StatusCategory 
            permits StatusCategory.PendingCategory, StatusCategory.ProcessingCategory, 
                    StatusCategory.ReadyCategory, StatusCategory.SentCategory, 
                    StatusCategory.DeliveredCategory, StatusCategory.ErrorCategory {
        
        record PendingCategory() implements StatusCategory {}
        record ProcessingCategory() implements StatusCategory {}
        record ReadyCategory() implements StatusCategory {}
        record SentCategory() implements StatusCategory {}
        record DeliveredCategory() implements StatusCategory {}
        record ErrorCategory() implements StatusCategory {}
        
        StatusCategory PENDING = new PendingCategory();
        StatusCategory PROCESSING = new ProcessingCategory();
        StatusCategory READY = new ReadyCategory();
        StatusCategory SENT = new SentCategory();
        StatusCategory DELIVERED = new DeliveredCategory();
        StatusCategory ERROR = new ErrorCategory();
    }
    
    private final String value;
    private final String description;
    private final StatusCategory category;
    
    MessageStatus(String value, String description, StatusCategory category) {
        this.value = value;
        this.description = description;
        this.category = category;
    }
    
    @JsonValue
    public String getValue() {
        return value;
    }
    
    public String getDescription() {
        return description;
    }
    
    public StatusCategory getCategory() {
        return category;
    }
    
    /**
     * Check if message is in a final state
     */
    public boolean isFinal() {
        return switch (this) {
            case READ, REPLIED, FORWARDED, ERROR -> true;
            case WAITING_UPLOAD, PENDING, PROCESSING, READY, SENT, UNREAD -> false;
        };
    }
    
    /**
     * Check if message delivery was successful
     */
    public boolean isDelivered() {
        return switch (this) {
            case SENT, UNREAD, READ, REPLIED, FORWARDED -> true;
            case WAITING_UPLOAD, PENDING, PROCESSING, READY, ERROR -> false;
        };
    }
    
    /**
     * Check if message has been read by recipient
     */
    public boolean isRead() {
        return switch (this) {
            case READ, REPLIED, FORWARDED -> true;
            case WAITING_UPLOAD, PENDING, PROCESSING, READY, SENT, UNREAD, ERROR -> false;
        };
    }
    
    /**
     * Check if message failed
     */
    public boolean isFailed() {
        return this == ERROR;
    }
    
    /**
     * Get the next possible statuses in the workflow
     */
    public Set<MessageStatus> getNextPossibleStatuses() {
        return switch (this) {
            case WAITING_UPLOAD -> Set.of(PENDING, ERROR);
            case PENDING -> Set.of(PROCESSING, ERROR);
            case PROCESSING -> Set.of(READY, ERROR);
            case READY -> Set.of(SENT, ERROR);
            case SENT -> Set.of(UNREAD, ERROR);
            case UNREAD -> Set.of(READ, ERROR);
            case READ -> Set.of(REPLIED, FORWARDED);
            case REPLIED, FORWARDED, ERROR -> Set.of(); // Final states
        };
    }
    
    /**
     * Create MessageStatus from string value with null safety
     */
    @JsonCreator
    public static MessageStatus fromValue(String value) {
        return Optional.ofNullable(value)
                .flatMap(v -> Arrays.stream(values())
                        .filter(status -> status.value.equalsIgnoreCase(v))
                        .findFirst())
                .orElseThrow(() -> new IllegalArgumentException("Invalid MessageStatus: " + value));
    }
    
    /**
     * Get all statuses by category
     */
    public static Stream<MessageStatus> getByCategory(StatusCategory category) {
        return Arrays.stream(values())
                .filter(status -> status.category.equals(category));
    }
    
    /**
     * Get all delivered statuses
     */
    public static Stream<MessageStatus> getDeliveredStatuses() {
        return Arrays.stream(values())
                .filter(MessageStatus::isDelivered);
    }
    
    /**
     * Get all final statuses
     */
    public static Stream<MessageStatus> getFinalStatuses() {
        return Arrays.stream(values())
                .filter(MessageStatus::isFinal);
    }
    
    /**
     * Check if value is a valid MessageStatus
     */
    public static boolean isValid(String value) {
        return Optional.ofNullable(value)
                .map(v -> Arrays.stream(values())
                        .anyMatch(status -> status.value.equalsIgnoreCase(v)))
                .orElse(false);
    }
}
