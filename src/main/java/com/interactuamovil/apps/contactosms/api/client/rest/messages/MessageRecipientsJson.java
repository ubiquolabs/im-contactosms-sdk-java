package com.interactuamovil.apps.contactosms.api.client.rest.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by javier on 2/11/16.
 *
 */
public class MessageRecipientsJson {
    @JsonProperty("msisdn")
    private String msisdn;
    @JsonProperty("status")
    private String status;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
