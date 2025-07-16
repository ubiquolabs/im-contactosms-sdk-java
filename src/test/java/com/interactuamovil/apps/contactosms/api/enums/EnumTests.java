package com.interactuamovil.apps.contactosms.api.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

/**
 * Modern tests for all enum classes using JUnit 5
 */
@DisplayName("Enum Tests - Modern Implementation")
class EnumTests {
    
    @Nested
    @DisplayName("ContactStatus Tests")
    class ContactStatusTests {
        
        @ParameterizedTest
        @EnumSource(ContactStatus.class)
        @DisplayName("Should have valid value for all statuses")
        void shouldHaveValidValueForAllStatuses(ContactStatus status) {
            // Then
            assertThat(status.getValue()).isNotNull();
            assertThat(status.getValue()).isNotBlank();
            assertThat(status.getDescription()).isNotNull();
            assertThat(status.getDescription()).isNotBlank();
        }
        
        @Test
        @DisplayName("Should identify active statuses correctly")
        void shouldIdentifyActiveStatusesCorrectly() {
            // Then
            assertThat(ContactStatus.SUBSCRIBED.isActive()).isTrue();
            assertThat(ContactStatus.CONFIRMED.isActive()).isTrue();
            assertThat(ContactStatus.PENDING.isActive()).isFalse();
            assertThat(ContactStatus.CANCELLED.isActive()).isFalse();
        }
    }
} 