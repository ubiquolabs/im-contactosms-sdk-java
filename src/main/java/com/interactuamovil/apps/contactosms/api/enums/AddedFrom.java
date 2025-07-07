package com.interactuamovil.apps.contactosms.api.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Modern AddedFrom enum using Java 21 features
 * 
 * Updated to use:
 * - Pattern matching for switch expressions
 * - Stream API for utility methods
 * - Better categorization and validation
 */
public enum AddedFrom {
    
    WEB_FORM("WEB_FORM", "Added through web form", true),
    FILE_UPLOAD("FILE_UPLOAD", "Added through file upload", false),
    API("API", "Added through API", false),
    SUBSCRIPTION_REQUEST("SUBSCRIPTION_REQUEST", "Added through subscription request", true),
    SMS("SMS", "Added through SMS", true),
    IM_REACH_NOTIFICATION_API("IM_REACH_NOTIFICATION_API", "Added through IM Reach Notification API", false);
    
    private final String value;
    private final String description;
    private final boolean requiresUserAction;
    
    AddedFrom(String value, String description, boolean requiresUserAction) {
        this.value = value;
        this.description = description;
        this.requiresUserAction = requiresUserAction;
    }
    
    @JsonValue
    public String getValue() {
        return value;
    }
    
    public String getDescription() {
        return description;
    }
    
    /**
     * Check if this source requires user action for confirmation
     */
    public boolean requiresUserAction() {
        return requiresUserAction;
    }
    
    /**
     * Check if this source is automated
     */
    public boolean isAutomated() {
        return switch (this) {
            case FILE_UPLOAD, API, IM_REACH_NOTIFICATION_API -> true;
            case WEB_FORM, SUBSCRIPTION_REQUEST, SMS -> false;
        };
    }
    
    /**
     * Check if this source is user-initiated
     */
    public boolean isUserInitiated() {
        return switch (this) {
            case WEB_FORM, SUBSCRIPTION_REQUEST, SMS -> true;
            case FILE_UPLOAD, API, IM_REACH_NOTIFICATION_API -> false;
        };
    }
    
    /**
     * Get the trust level for this source
     */
    public TrustLevel getTrustLevel() {
        return switch (this) {
            case WEB_FORM, SUBSCRIPTION_REQUEST -> TrustLevel.HIGH;
            case SMS -> TrustLevel.MEDIUM;
            case API, IM_REACH_NOTIFICATION_API -> TrustLevel.MEDIUM;
            case FILE_UPLOAD -> TrustLevel.LOW;
        };
    }
    
    /**
     * Trust level for contact sources
     */
    public enum TrustLevel {
        LOW("Low trust - requires verification"),
        MEDIUM("Medium trust - moderate verification"),
        HIGH("High trust - minimal verification required");
        
        private final String description;
        
        TrustLevel(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    /**
     * Create AddedFrom from string value with null safety
     */
    @JsonCreator
    public static AddedFrom fromValue(String value) {
        return Optional.ofNullable(value)
                .flatMap(v -> Arrays.stream(values())
                        .filter(source -> source.value.equalsIgnoreCase(v))
                        .findFirst())
                .orElseThrow(() -> new IllegalArgumentException("Invalid AddedFrom: " + value));
    }
    
    /**
     * Get all automated sources
     */
    public static Stream<AddedFrom> getAutomatedSources() {
        return Arrays.stream(values())
                .filter(AddedFrom::isAutomated);
    }
    
    /**
     * Get all user-initiated sources
     */
    public static Stream<AddedFrom> getUserInitiatedSources() {
        return Arrays.stream(values())
                .filter(AddedFrom::isUserInitiated);
    }
    
    /**
     * Get sources by trust level
     */
    public static Stream<AddedFrom> getByTrustLevel(TrustLevel trustLevel) {
        return Arrays.stream(values())
                .filter(source -> source.getTrustLevel() == trustLevel);
    }
    
    /**
     * Check if value is a valid AddedFrom
     */
    public static boolean isValid(String value) {
        return Optional.ofNullable(value)
                .map(v -> Arrays.stream(values())
                        .anyMatch(source -> source.value.equalsIgnoreCase(v)))
                .orElse(false);
    }
}
