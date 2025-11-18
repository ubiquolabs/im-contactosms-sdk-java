package com.interactuamovil.apps.contactosms.api.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for JavaVersionDetector utility class
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("JavaVersionDetector - Version Detection Tests")
class JavaVersionDetectorTest {
    
    @Test
    @DisplayName("Should detect Java version correctly")
    void shouldDetectJavaVersion() {
        int version = JavaVersionDetector.getJavaVersion();
        assertThat(version).isGreaterThanOrEqualTo(8);
        assertThat(version).isLessThanOrEqualTo(25);
    }
    
    @Test
    @DisplayName("Should correctly identify Java 8")
    void shouldIdentifyJava8() {
        boolean isJava8 = JavaVersionDetector.isJava8();
        int version = JavaVersionDetector.getJavaVersion();
        
        if (version == 8) {
            assertThat(isJava8).isTrue();
        } else {
            assertThat(isJava8).isFalse();
        }
    }
    
    @Test
    @DisplayName("Should correctly identify Java 21 or higher")
    void shouldIdentifyJava21OrHigher() {
        boolean isJava21OrHigher = JavaVersionDetector.isJava21OrHigher();
        int version = JavaVersionDetector.getJavaVersion();
        
        if (version >= 21) {
            assertThat(isJava21OrHigher).isTrue();
        } else {
            assertThat(isJava21OrHigher).isFalse();
        }
    }
    
    @Test
    @DisplayName("Should correctly identify Java 9 or higher")
    void shouldIdentifyJava9OrHigher() {
        boolean isJava9OrHigher = JavaVersionDetector.isJava9OrHigher();
        int version = JavaVersionDetector.getJavaVersion();
        
        if (version >= 9) {
            assertThat(isJava9OrHigher).isTrue();
        } else {
            assertThat(isJava9OrHigher).isFalse();
        }
    }
    
    @Test
    @DisplayName("Should correctly identify Java 11 or higher")
    void shouldIdentifyJava11OrHigher() {
        boolean isJava11OrHigher = JavaVersionDetector.isJava11OrHigher();
        int version = JavaVersionDetector.getJavaVersion();
        
        if (version >= 11) {
            assertThat(isJava11OrHigher).isTrue();
        } else {
            assertThat(isJava11OrHigher).isFalse();
        }
    }
    
    @Test
    @DisplayName("Should correctly identify Java 15 or higher")
    void shouldIdentifyJava15OrHigher() {
        boolean isJava15OrHigher = JavaVersionDetector.isJava15OrHigher();
        int version = JavaVersionDetector.getJavaVersion();
        
        if (version >= 15) {
            assertThat(isJava15OrHigher).isTrue();
        } else {
            assertThat(isJava15OrHigher).isFalse();
        }
    }
    
    @Test
    @DisplayName("Should print current Java version for verification")
    void shouldPrintCurrentJavaVersion() {
        System.out.println("========================================");
        System.out.println("Java Version Detection Test");
        System.out.println("========================================");
        System.out.println("System Java Version: " + System.getProperty("java.version"));
        System.out.println("Detected Major Version: " + JavaVersionDetector.getJavaVersion());
        System.out.println("Is Java 8: " + JavaVersionDetector.isJava8());
        System.out.println("Is Java 9+: " + JavaVersionDetector.isJava9OrHigher());
        System.out.println("Is Java 11+: " + JavaVersionDetector.isJava11OrHigher());
        System.out.println("Is Java 15+: " + JavaVersionDetector.isJava15OrHigher());
        System.out.println("Is Java 21+: " + JavaVersionDetector.isJava21OrHigher());
        System.out.println("========================================");
        
        assertThat(JavaVersionDetector.getJavaVersion()).isNotNull();
    }
}

