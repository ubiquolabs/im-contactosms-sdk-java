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
        
        String welcomeMessage = "===================================\n" +
                "IM ContactoSMS SDK Java - v4.2.3\n" +
                "Modern Java 21 Implementation\n" +
                "===================================\n";
        
        System.out.println(welcomeMessage);
        System.out.println("Starting IM ContactoSMS SDK Java Application");
        System.out.println("Java Version: " + System.getProperty("java.version"));
        System.out.println("SDK Version: 4.2.3-SNAPSHOT");
        
        String status;
        switch (args.length) {
            case 0:
                status = "No arguments provided - running in default mode";
                break;
            case 1:
                status = "Single argument: " + args[0];
                break;
            default:
                status = "Multiple arguments provided: " + String.join(", ", args);
                break;
        }
        
        System.out.println("Application status: " + status);
        System.out.println("IM ContactoSMS SDK Java Application initialized successfully");
        
        logger.info("Application status: {}", status);
        logger.info("IM ContactoSMS SDK Java Application initialized successfully");
    }
}
