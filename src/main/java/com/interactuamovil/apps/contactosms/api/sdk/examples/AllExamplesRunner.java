package com.interactuamovil.apps.contactosms.api.sdk.examples;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class AllExamplesRunner {

    private static final Logger logger = LoggerFactory.getLogger(AllExamplesRunner.class);

    public static void main(String[] args) throws ConfigurationException {
        Configurations configs = new Configurations();
        Configuration config = configs.properties(new File("examples.properties"));

        String apiKey = config.getString("api_key");
        String apiSecretKey = config.getString("api_secret_key");
        String apiUri = config.getString("api_url");

        if (null == apiKey || null == apiSecretKey || null == apiUri) {
            throw new AssertionError(
                "Please add api configurations: api_key, api_secret_key"
                    + " and api_uri."
            );
        }

        List<BaseExample> examples = Arrays.asList(
            new AccountsExample(apiKey, apiSecretKey, apiUri, config),
            new ContactsExample(apiKey, apiSecretKey, apiUri, config),
            new TagsExample(apiKey, apiSecretKey, apiUri, config),
            new MessagesExample(apiKey, apiSecretKey, apiUri, config),
            new MessagesDeliveryStatusExample(apiKey, apiSecretKey, apiUri, config),
            new ModernMessagesExample(apiKey, apiSecretKey, apiUri, config)
        );

        for (BaseExample example : examples) {
            try {
                System.out.println("============================================================");
                System.out.printf("? Running Example: %s%n", example.getClass().getSimpleName());
                System.out.println("------------------------------------------------------------");
                example.configure();
                example.test();
                System.out.println("? Example finished successfully.");
            } catch (Exception e) {
                System.err.printf("? Example %s failed: %s%n", example.getClass().getSimpleName(), e.getMessage());
                logger.error("Exception in example " + example.getClass().getSimpleName(), e);
            }
            System.out.println("============================================================");
            System.out.println();
        }
    }
} 