package com.interactuamovil.apps.contactosms.api.client.rest.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interactuamovil.apps.contactosms.api.utils.JsonDateTimeDeserializer;
import com.interactuamovil.apps.contactosms.api.utils.JsonDateTimeSerializer;
import java.util.Date;

/**
 * Created by sergei on 4/27/17.
 */
public class AccountBalanceJsonObject {

    @JsonProperty("credit_limit")
    private Double creditLimit;
    @JsonProperty("charges")
    private Double charges;
    @JsonProperty("refunds")
    private Double refunds;
    @JsonProperty("balance")
    private Double balance;
    @JsonProperty("valid_since")
    private Date validSince;
    @JsonProperty("valid_thru")
    private Date validThru;

    public Double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Double getCharges() {
        return charges;
    }

    public void setCharges(Double charges) {
        this.charges = charges;
    }

    public Double getRefunds() {
        return refunds;
    }

    public void setRefunds(Double refunds) {
        this.refunds = refunds;
    }

    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    public Date getValidSince(){
        return this.validSince;
    }

    @JsonSerialize(using = JsonDateTimeSerializer.class)
    public void setValidSince(Date validSince){
        this.validSince = validSince;
    }

    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    public Date getValidThru(){
        return this.validThru;
    }

    @JsonSerialize(using = JsonDateTimeSerializer.class)
    public void setValidThru(Date validThru){
        this.validThru = validThru;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
