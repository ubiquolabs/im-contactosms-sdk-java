package com.interactuamovil.apps.contactosms.api.sdk.examples;

import org.apache.commons.configuration.Configuration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


abstract class BaseExample {

    private String apiKey;
    private String apiSecretKey;
    private String apiUri;
    private Configuration config;

    protected BaseExample(String _apiKey, String _apiSecretKey, String _apiUri,
                          Configuration _config) {
        apiKey = _apiKey;
        apiSecretKey = _apiSecretKey;
        apiUri = _apiUri;
        config = _config;
    }

    public abstract void configure();

    public String getApiKey() {
        return apiKey;
    }

    public String getApiSecretKey() {
        return apiSecretKey;
    }

    public String getApiUri() {
        return apiUri;
    }

    public abstract void test()
        throws IOException, InvalidKeyException, NoSuchAlgorithmException;

    public Configuration getConfig() {
        return config;
    }

}
