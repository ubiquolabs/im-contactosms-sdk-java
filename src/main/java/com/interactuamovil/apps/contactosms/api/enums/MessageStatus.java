package com.interactuamovil.apps.contactosms.api.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
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
     * Interface for status categories (Java 8 compatible)
     */
    public interface StatusCategory {
        StatusCategory PENDING = new StatusCategory() {};
        StatusCategory PROCESSING = new StatusCategory() {};
        StatusCategory READY = new StatusCategory() {};
        StatusCategory SENT = new StatusCategory() {};
        StatusCategory DELIVERED = new StatusCategory() {};
        StatusCategory ERROR = new StatusCategory() {};
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
        switch (this) {
            case READ:
            case REPLIED:
            case FORWARDED:
            case ERROR:
                return true;
            case WAITING_UPLOAD:
            case PENDING:
            case PROCESSING:
            case READY:
            case SENT:
            case UNREAD:
                return false;
            default:
                return false;
        }
    }
    
    /**
     * Check if message delivery was successful
     */
    public boolean isDelivered() {
        switch (this) {
            case SENT:
            case UNREAD:
            case READ:
            case REPLIED:
            case FORWARDED:
                return true;
            case WAITING_UPLOAD:
            case PENDING:
            case PROCESSING:
            case READY:
            case ERROR:
                return false;
            default:
                return false;
        }
    }
    
    /**
     * Check if message has been read by recipient
     */
    public boolean isRead() {
        switch (this) {
            case READ:
            case REPLIED:
            case FORWARDED:
                return true;
            case WAITING_UPLOAD:
            case PENDING:
            case PROCESSING:
            case READY:
            case SENT:
            case UNREAD:
            case ERROR:
                return false;
            default:
                return false;
        }
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
        switch (this) {
            case WAITING_UPLOAD:
                return createSet(PENDING, ERROR);
            case PENDING:
                return createSet(PROCESSING, ERROR);
            case PROCESSING:
                return createSet(READY, ERROR);
            case READY:
                return createSet(SENT, ERROR);
            case SENT:
                return createSet(UNREAD, ERROR);
            case UNREAD:
                return createSet(READ, ERROR);
            case READ:
                return createSet(REPLIED, FORWARDED);
            case REPLIED:
            case FORWARDED:
            case ERROR:
                return Collections.emptySet();
            default:
                return Collections.emptySet();
        }
    }
    
    private static Set<MessageStatus> createSet(MessageStatus... statuses) {
        Set<MessageStatus> set = new HashSet<MessageStatus>();
        for (MessageStatus status : statuses) {
            set.add(status);
        }
        return Collections.unmodifiableSet(set);
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
