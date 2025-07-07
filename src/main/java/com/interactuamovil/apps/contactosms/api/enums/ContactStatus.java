package com.interactuamovil.apps.contactosms.api.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Modern Contact Status enum using Java 21 features
 * 
 * Updated to use:
 * - Pattern matching for switch expressions
 * - Optional handling for null safety
 * - Stream API for utility methods
 * - Better JSON serialization
 */
public enum ContactStatus {
    
    PENDING("PENDING", "Contact is pending confirmation"),
    SUBSCRIBED("SUBSCRIBED", "Contact is subscribed and active"),
    CONFIRMED("CONFIRMED", "Contact has been confirmed"),
    CANCELLED("CANCELLED", "Contact has been cancelled"),
    INVITED("INVITED", "Contact has been invited");
    
    private final String value;
    private final String description;
    
    ContactStatus(String value, String description) {
        this.value = value;
        this.description = description;
    }
    
    @JsonValue
    public String getValue() {
        return value;
    }
    
    public String getDescription() {
        return description;
    }
    
    /**
     * Check if this status represents an active contact
     */
    public boolean isActive() {
        return switch (this) {
            case SUBSCRIBED, CONFIRMED -> true;
            case PENDING, CANCELLED, INVITED -> false;
        };
    }
    
    /**
     * Check if this status allows message sending
     */
    public boolean canReceiveMessages() {
        return switch (this) {
            case SUBSCRIBED, CONFIRMED -> true;
            case PENDING, CANCELLED, INVITED -> false;
        };
    }
    
    /**
     * Get the next logical status in the workflow
     */
    public Optional<ContactStatus> getNextStatus() {
        return switch (this) {
            case PENDING -> Optional.of(CONFIRMED);
            case INVITED -> Optional.of(SUBSCRIBED);
            case SUBSCRIBED, CONFIRMED, CANCELLED -> Optional.empty();
        };
    }
    
    /**
     * Create ContactStatus from string value with null safety
     */
    @JsonCreator
    public static ContactStatus fromValue(String value) {
        return Optional.ofNullable(value)
                .flatMap(v -> Arrays.stream(values())
                        .filter(status -> status.value.equalsIgnoreCase(v))
                        .findFirst())
                .orElseThrow(() -> new IllegalArgumentException("Invalid ContactStatus: " + value));
    }
    
    /**
     * Get all active statuses
     */
    public static Stream<ContactStatus> getActiveStatuses() {
        return Arrays.stream(values())
                .filter(ContactStatus::isActive);
    }
    
    /**
     * Get all statuses that can receive messages
     */
    public static Stream<ContactStatus> getMessageableStatuses() {
        return Arrays.stream(values())
                .filter(ContactStatus::canReceiveMessages);
    }
    
    /**
     * Check if value is a valid ContactStatus
     */
    public static boolean isValid(String value) {
        return Optional.ofNullable(value)
                .map(v -> Arrays.stream(values())
                        .anyMatch(status -> status.value.equalsIgnoreCase(v)))
                .orElse(false);
    }
}
