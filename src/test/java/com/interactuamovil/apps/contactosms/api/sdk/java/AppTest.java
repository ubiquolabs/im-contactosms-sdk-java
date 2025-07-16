package com.interactuamovil.apps.contactosms.api.sdk.java;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.EmptySource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.*;

/**
 * Modern unit tests for App class using JUnit 5
 * 
 * Features:
 * - JUnit 5 annotations and assertions
 * - AssertJ for fluent assertions
 * - Parameterized tests
 * - Display names for better readability
 * - Proper test lifecycle management
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("App - Main Application Tests")
class AppTest {
    
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outputStream;
    
    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }
    
    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }
    
    @Test
    @DisplayName("Should run main method without arguments successfully")
    void shouldRunMainWithoutArguments() {
        // When
        App.main(new String[]{});
        
        // Then
        String output = outputStream.toString();
        assertThat(output)
                .contains("IM ContactoSMS SDK Java - v4.2.3")
                .contains("Modern Java 21 Implementation")
                .contains("No arguments provided - running in default mode");
    }
    
    @Test
    @DisplayName("Should run main method with single argument")
    void shouldRunMainWithSingleArgument() {
        // Given
        String[] args = {"test-arg"};
        
        // When
        App.main(args);
        
        // Then
        String output = outputStream.toString();
        assertThat(output)
                .contains("Single argument: test-arg")
                .contains("IM ContactoSMS SDK Java - v4.2.3");
    }
    
    @Test
    @DisplayName("Should run main method with multiple arguments")
    void shouldRunMainWithMultipleArguments() {
        // Given
        String[] args = {"arg1", "arg2", "arg3"};
        
        // When
        App.main(args);
        
        // Then
        String output = outputStream.toString();
        assertThat(output)
                .contains("Multiple arguments provided: arg1, arg2, arg3")
                .contains("IM ContactoSMS SDK Java - v4.2.3");
    }
    
    @ParameterizedTest
    @DisplayName("Should handle various argument arrays")
    @ValueSource(ints = {0, 1, 2, 5, 10})
    void shouldHandleVariousArgumentCounts(int argCount) {
        // Given
        String[] args = new String[argCount];
        for (int i = 0; i < argCount; i++) {
            args[i] = "arg" + i;
        }
        
        // When
        App.main(args);
        
        // Then
        String output = outputStream.toString();
        assertThat(output)
                .contains("IM ContactoSMS SDK Java - v4.2.3")
                .contains("Application initialized successfully");
    }
    
    @Test
    @DisplayName("Should display Java version information")
    void shouldDisplayJavaVersionInformation() {
        // When
        App.main(new String[]{});
        
        // Then
        String output = outputStream.toString();
        assertThat(output)
                .contains("Java Version:")
                .contains("SDK Version: 4.2.3-SNAPSHOT");
    }
    
    @Test
    @DisplayName("Should use text blocks for welcome message")
    void shouldUseTextBlocksForWelcomeMessage() {
        // When
        App.main(new String[]{});
        
        // Then
        String output = outputStream.toString();
        assertThat(output)
                .contains("===================================")
                .contains("IM ContactoSMS SDK Java - v4.2.3")
                .contains("Modern Java 21 Implementation")
                .contains("===================================");
    }
    
    @Test
    @DisplayName("Should demonstrate pattern matching with switch expressions")
    void shouldDemonstratePatternMatchingWithSwitchExpressions() {
        // Test different argument scenarios to ensure switch expressions work
        
        // No arguments
        App.main(new String[]{});
        String output1 = outputStream.toString();
        assertThat(output1).contains("No arguments provided");
        
        // Reset output stream
        outputStream.reset();
        
        // Single argument
        App.main(new String[]{"single"});
        String output2 = outputStream.toString();
        assertThat(output2).contains("Single argument: single");
        
        // Reset output stream
        outputStream.reset();
        
        // Multiple arguments
        App.main(new String[]{"arg1", "arg2"});
        String output3 = outputStream.toString();
        assertThat(output3).contains("Multiple arguments provided: arg1, arg2");
    }
}
