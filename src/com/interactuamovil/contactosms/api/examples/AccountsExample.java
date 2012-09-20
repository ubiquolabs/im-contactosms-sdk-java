package com.interactuamovil.contactosms.api.examples;

import com.interactuamovil.contactosms.api.Accounts;
import com.interactuamovil.contactosms.api.responses.AccountResponse;
import com.interactuamovil.contactosms.api.responses.Response;
import org.apache.commons.configuration.Configuration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


public class AccountsExample extends BaseExample {

    protected AccountsExample(String _apiKey, String _apiSecretKey, String _apiUri, Configuration _config) {
        super(_apiKey, _apiSecretKey, _apiUri, _config);
    }

    @Override
    public void configure() {

    }

    @Override
    public void test() throws IOException, InvalidKeyException, NoSuchAlgorithmException {

        Accounts accountsApi = new Accounts(
            getApiKey(),
            getApiSecretKey(),
            getApiUri()
        );

        // Get account package information
        Response<AccountResponse> accountResponse = accountsApi.getStatus();

        if (accountResponse.hasError()) {
            throw new AssertionError(
                "Could not get account status: "
                + accountResponse.getErrorMessage()
            );
        }

    }

}
