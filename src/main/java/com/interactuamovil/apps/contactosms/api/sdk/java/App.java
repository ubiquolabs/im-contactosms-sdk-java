package com.interactuamovil.apps.contactosms.api.sdk.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * IM ContactoSMS SDK Java - Modern Application Entry Point
 * 
 * This is the main entry point for the IM ContactoSMS SDK Java application.
 * Updated to use Java 21 features and modern logging.
 */
public final class App {
    
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    
    private App() {
        // Private constructor to prevent instantiation
    }
    
    /**
     * Main method - Application entry point
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        logger.info("Starting IM ContactoSMS SDK Java Application");
        logger.info("Java Version: {}", System.getProperty("java.version"));
        logger.info("SDK Version: 4.2.3-SNAPSHOT");
        
        // Modern Java 21 text block for welcome message
        String welcomeMessage = """
                ===================================
                IM ContactoSMS SDK Java - v4.2.3
                Modern Java 21 Implementation
                ===================================
                """;
        
        System.out.println(welcomeMessage);
        
        // Example of Java 21 pattern matching (when available)
        String status = switch (args.length) {
            case 0 -> "No arguments provided - running in default mode";
            case 1 -> "Single argument: " + args[0];
            default -> "Multiple arguments provided: " + String.join(", ", args);
        };
        
        logger.info("Application status: {}", status);
        logger.info("IM ContactoSMS SDK Java Application initialized successfully");
    }
}
