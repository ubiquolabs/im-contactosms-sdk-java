package com.interactuamovil.apps.contactosms.api.sdk;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interactuamovil.apps.contactosms.api.sdk.responses.AccountResponse;
import com.interactuamovil.apps.contactosms.api.utils.ApiResponse;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Accounts extends Request {

    public Accounts(String apiKey, String secretKey, String apiUri) {
        super(apiKey, secretKey, apiUri);
    }

    /**
     * Returns the account general information
     *
     * @return Response object
     * @throws IOException exception IO
     * @throws InvalidKeyException InvalidKey
     * @throws NoSuchAlgorithmException No algorithm
     */
    public ApiResponse<AccountResponse> getStatus() throws IOException, InvalidKeyException, NoSuchAlgorithmException {

        ObjectMapper mapper = new ObjectMapper();
        ApiResponse<AccountResponse> response;
        AccountResponse accountResponse;
        try {
            response = doRequest("account/status", "get", null, null, false);
            if (response.isOk()) {
                accountResponse = AccountResponse.fromJson(response.getRawResponse());
                response.setResponse(accountResponse);
            }
        } catch (Exception e) {
            response = new ApiResponse<AccountResponse>();
            response.setErrorCode(-1);
            response.setErrorDescription(e.getMessage());
        }
        return response;
    }

}