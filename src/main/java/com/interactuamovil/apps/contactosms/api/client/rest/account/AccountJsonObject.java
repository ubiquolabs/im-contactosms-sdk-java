package com.interactuamovil.apps.contactosms.api.client.rest.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interactuamovil.apps.contactosms.api.utils.JsonDateTimeDeserializer;
import com.interactuamovil.apps.contactosms.api.utils.JsonDateTimeSerializer;
import com.interactuamovil.apps.contactosms.api.utils.JsonObject;

import java.util.Date;

/**
 * Created by javier on 1/02/16.
 * Account json object response
 */
public class AccountJsonObject extends JsonObject {

    @JsonProperty("account_id")
    private Integer accountId;
    @JsonProperty("account_name")
    private String accountName;
    @JsonProperty("client_name")
    private String clientName;
    @JsonProperty("valid_since")
    private Date validSince;
    @JsonProperty("sms_short_name")
    private String smsShortName;
    @JsonProperty("account_status")
    private String accountStatus;
    @JsonProperty("account_type")
    private String accountType;
    @JsonProperty("can_sell_extras")
    private Boolean canSellExtras;
    @JsonProperty("subscription_type")
    private String subscriptionType;
    @JsonProperty("default_subaccount_id")
    private Integer defaultSubaccountId;
    @JsonProperty("comments")
    private String comments;
    @JsonProperty("created_on")
    private Date createdOn;
    @JsonProperty("package")
    private String packageName;
    @JsonProperty("cycle")
    private String cycleName;
    @JsonProperty("current_package")
    private AccountBalanceJsonObject balance;



    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @JsonSerialize(using = JsonDateTimeSerializer.class)
    public Date getValidSince() {
        return validSince;
    }

    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    public void setValidSince(Date validSince) {
        this.validSince = validSince;
    }

    public String getSmsShortName() {
        return smsShortName;
    }

    public void setSmsShortName(String smsShortName) {
        this.smsShortName = smsShortName;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Boolean getCanSellExtras() {
        return canSellExtras;
    }

    public void setCanSellExtras(Boolean canSellExtras) {
        this.canSellExtras = canSellExtras;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public Integer getDefaultSubaccountId() {
        return defaultSubaccountId;
    }

    public void setDefaultSubaccountId(Integer defaultSubaccountId) {
        this.defaultSubaccountId = defaultSubaccountId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPackage() {
        return packageName;
    }

    public void setPackage(String packageName) {
        this.packageName = packageName;
    }

    public String getCycle() {
        return cycleName;
    }

    public void setCycle(String cycleName) {
        this.cycleName = cycleName;
    }

    @JsonSerialize(using = JsonDateTimeSerializer.class)
    public Date getCreatedOn() {
        return createdOn;
    }

    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public AccountBalanceJsonObject getBalance() {
        return balance;
    }

    public void setBalance(AccountBalanceJsonObject balance) {
        this.balance = balance;
    }
}
