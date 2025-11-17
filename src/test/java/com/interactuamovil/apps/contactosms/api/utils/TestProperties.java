package com.interactuamovil.apps.contactosms.api.utils;

import java.io.InputStream;
import java.util.Properties;

public final class TestProperties {
    
    private static final Properties properties = new Properties();
    
    static {
        loadProperties();
    }
    
    private TestProperties() {
    }
    
    private static void loadProperties() {
        try {
            InputStream inputStream = TestProperties.class.getClassLoader()
                    .getResourceAsStream("test.properties");
            if (inputStream != null) {
                properties.load(inputStream);
                inputStream.close();
            }
        } catch (Exception e) {
            System.err.println("Warning: Could not load test.properties: " + e.getMessage());
        }
    }
    
    public static String getApiUrl() {
        return properties.getProperty("api_url", "https://api.test.com/");
    }
    
    public static String getApiKey() {
        return properties.getProperty("api_key", "test-api-key");
    }
    
    public static String getApiSecretKey() {
        return properties.getProperty("api_secret_key", "test-secret-key");
    }
    
    public static String getTestMsisdn() {
        return properties.getProperty("test.msisdn", "50212345678");
    }
}

