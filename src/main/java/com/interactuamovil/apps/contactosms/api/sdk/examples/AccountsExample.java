package com.interactuamovil.apps.contactosms.api.sdk.examples;

import com.interactuamovil.apps.contactosms.api.sdk.Accounts;
import com.interactuamovil.apps.contactosms.api.sdk.responses.AccountResponse;
import com.interactuamovil.apps.contactosms.api.utils.ApiResponse;
import org.apache.commons.configuration2.Configuration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Modern Accounts Example using Configuration2
 */
public class AccountsExample extends BaseExample {

    public AccountsExample(String apiKey, String apiSecretKey, String apiUri, Configuration config) {
        super(apiKey, apiSecretKey, apiUri, config);
    }

    @Override
    public void configure() {
        // Configuration setup if needed
    }

    @Override
    public void test() throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        Accounts accountsApi = new Accounts(getApiKey(), getApiSecretKey(), getApiUri());
        
        ApiResponse<AccountResponse> accountResponse = accountsApi.getStatus();
        
        if (accountResponse.isOk()) {
            AccountResponse account = accountResponse.getResponse();
            System.out.println("Account retrieved successfully");
            System.out.println("HTTP Code: " + accountResponse.getHttpCode());
        } else {
            System.err.println("Error: " + accountResponse.getErrorDescription());
        }
    }
}
