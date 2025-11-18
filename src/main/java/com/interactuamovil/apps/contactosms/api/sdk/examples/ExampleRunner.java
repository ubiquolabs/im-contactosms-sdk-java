package com.interactuamovil.apps.contactosms.api.sdk.examples;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public final class ExampleRunner {

    private static final List<String> SUPPORTED = createSupportedList();
    
    private static List<String> createSupportedList() {
        if (com.interactuamovil.apps.contactosms.api.utils.JavaVersionDetector.isJava9OrHigher()) {
            try {
                java.lang.reflect.Method ofMethod = java.util.List.class.getMethod("of", Object[].class);
                @SuppressWarnings("unchecked")
                List<String> result = (List<String>) ofMethod.invoke(null, (Object) new Object[]{
                    "accounts", "contacts", "tags", "messages", "messages-delivery-status", 
                    "modern-messages", "shortlinks"
                });
                return result;
            } catch (Exception e) {
                return createSupportedListFallback();
            }
        } else {
            return createSupportedListFallback();
        }
    }
    
    private static List<String> createSupportedListFallback() {
        return Collections.unmodifiableList(Arrays.asList(
            "accounts", "contacts", "tags", "messages", "messages-delivery-status", 
            "modern-messages", "shortlinks"
        ));
    }

    private ExampleRunner() {
    }

    public static void main(String[] args) throws ConfigurationException, IOException,
        InvalidKeyException, NoSuchAlgorithmException {
        if (args.length == 0) {
            throw new IllegalArgumentException("Example name required. Available: " + String.join(", ", SUPPORTED));
        }

        Configurations configs = new Configurations();
        Configuration config = configs.properties(new File("examples.properties"));

        String apiKey = config.getString("api_key");
        String apiSecretKey = config.getString("api_secret_key");
        String apiUri = config.getString("api_url");

        if (apiKey == null || apiSecretKey == null || apiUri == null) {
            throw new IllegalStateException("Configure api_key, api_secret_key, api_url in examples.properties");
        }

        BaseExample example = resolveExample(args[0], apiKey, apiSecretKey, apiUri, config);
        example.configure();
        String[] exampleArgs = args.length > 1 ? Arrays.copyOfRange(args, 1, args.length) : new String[0];
        example.test(exampleArgs);
    }

    private static BaseExample resolveExample(String name, String apiKey, String apiSecretKey,
                                              String apiUri, Configuration config) {
        String normalized = name.toLowerCase(Locale.ROOT);
        switch (normalized) {
            case "accounts":
                return new AccountsExample(apiKey, apiSecretKey, apiUri, config);
            case "contacts":
                return new ContactsExample(apiKey, apiSecretKey, apiUri, config);
            case "tags":
                return new TagsExample(apiKey, apiSecretKey, apiUri, config);
            case "messages":
                return new MessagesExample(apiKey, apiSecretKey, apiUri, config);
            case "messages-delivery-status":
                return new MessagesDeliveryStatusExample(apiKey, apiSecretKey, apiUri, config);
            case "modern-messages":
                return new ModernMessagesExample(apiKey, apiSecretKey, apiUri, config);
            case "shortlinks":
                return new ShortlinksExample(apiKey, apiSecretKey, apiUri, config);
            default:
                throw new IllegalArgumentException("Unknown example: " + name + ". Available: " + String.join(", ", SUPPORTED));
        }
    }
}

