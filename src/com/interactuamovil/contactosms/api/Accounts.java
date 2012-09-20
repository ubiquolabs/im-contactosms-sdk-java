package com.interactuamovil.contactosms.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interactuamovil.contactosms.api.responses.AccountResponse;
import com.interactuamovil.contactosms.api.responses.Response;
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
     * @throws IOException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public Response<AccountResponse> getStatus() throws IOException, InvalidKeyException, NoSuchAlgorithmException {

        ObjectMapper mapper = new ObjectMapper();
        Response<AccountResponse> response = new Response<AccountResponse>();
        AccountResponse accountResponse;
        try {
            String serverResponse = doRequest("account/status", "get", null, null, false);
            accountResponse = mapper.readValue(serverResponse, AccountResponse.class);
            response.setResult(accountResponse);
        } catch (Exception e) {
            response.setError(true);
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

}