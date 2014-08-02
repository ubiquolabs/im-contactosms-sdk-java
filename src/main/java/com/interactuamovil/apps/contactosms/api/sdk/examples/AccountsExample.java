package com.interactuamovil.apps.contactosms.api.sdk.examples;

import com.interactuamovil.apps.contactosms.api.sdk.Accounts;
import com.interactuamovil.apps.contactosms.api.sdk.responses.AccountResponse;
import com.interactuamovil.apps.contactosms.api.utils.ApiResponse;
import org.apache.commons.configuration.Configuration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


class AccountsExample extends BaseExample {

    protected AccountsExample(String _apiKey, String _apiSecretKey, String _apiUri, Configuration _config) {
        super(_apiKey, _apiSecretKey, _apiUri, _config);
    }

    @Override
    public void configure() {

    }

    
    @Override
    public void test() throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        /*
        Accounts accountsApi = new Accounts(
            getApiKey(),
            getApiSecretKey(),
            getApiUri()
        );

        // Get account package information
        ApiResponse<AccountResponse> accountResponse = accountsApi.getStatus();

        if (accountResponse.isOk()) {
            throw new AssertionError(
                "Could not get account status: "
                + accountResponse.getErrorDescription()
            );
        }*/

    }

}
