package com.interactuamovil.apps.contactosms.api.sdk.examples;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


/**
 * @author Pablo Rosales
 * @copyright InteractúaMóvil 2012
 */
class ApiExamples {

    private static final Logger logger = Logger.getLogger(ApiExamples.class);

    private ApiExamples() {}

    public static void main(String[] args) throws ConfigurationException {

        Configuration config = new PropertiesConfiguration("examples.properties");

        String apiKey = config.getString("api_key");
        String apiSecretKey = config.getString("api_secret_key");
        String apiUri = config.getString("api_url");

        if (null == apiKey || null == apiSecretKey || null == apiUri) {
            throw new AssertionError(
                "Please add api configurations: api_key, api_secret_key"
                    + " and api_uri."
            );
        }

        String exampleToRun = getArgValue(args, "run");

        try {

            if ("accounts".equalsIgnoreCase(exampleToRun)) {

                // Test account
                AccountsExample accountsExample = new AccountsExample(
                    apiKey,
                    apiSecretKey,
                    apiUri,
                    config
                );
                accountsExample.configure();
                accountsExample.test();

            }
            else if ("contacts".equalsIgnoreCase(exampleToRun)) {

                // Test contacts
                ContactsExample contactsExample = new ContactsExample(
                    apiKey,
                    apiSecretKey,
                    apiUri,
                    config
                );
                contactsExample.configure();
                contactsExample.test();

            }
            else if ("groups".equalsIgnoreCase(exampleToRun)) {

                // Test groups
                GroupsExample groupExample = new GroupsExample(
                    apiKey,
                    apiSecretKey,
                    apiUri,
                    config
                );
                groupExample.configure();
                groupExample.test();

            }
            else if ("messages".equalsIgnoreCase(exampleToRun)) {

                // Test messages
                MessagesExample messagesExample = new MessagesExample(
                    apiKey,
                    apiSecretKey,
                    apiUri,
                    config
                );
                messagesExample.configure();
                messagesExample.test();

            }

        } catch (IOException e) {
            logger.debug(e.getMessage());
        } catch (InvalidKeyException e) {
            logger.debug(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            logger.debug(e.getMessage());
        }

    }

    public static String getArgValue(String[] args, String key) {
        String value = null;
        for (String arg : args) {
            String[] parts = arg.split("=", 2);
            if (parts[0].indexOf("--") == 0) {
                parts[0] = parts[0].substring(2).toLowerCase().trim();
                if (parts[0].equals(key) && parts.length > 1) {
                    value = parts[1];
                    return value;
                }
            }
        }
        return value;
    }

}
