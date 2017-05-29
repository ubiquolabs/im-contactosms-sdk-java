package com.interactuamovil.apps.contactosms.api.client.rest.account;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
