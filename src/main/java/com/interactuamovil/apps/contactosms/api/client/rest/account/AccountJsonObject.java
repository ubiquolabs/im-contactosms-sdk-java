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
    @JsonProperty("sms_short_name")
    private String smsShortName;
    @JsonProperty("account_status")
    private String accountStatus;
    @JsonProperty("created_on")
    private Date createdOn;
    @JsonProperty("current_package")
    private Package currentPackage;


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

    @JsonSerialize(using = JsonDateTimeSerializer.class)
    public Date getCreatedOn() {
        return createdOn;
    }

    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Package getCurrentPackage() {
        return currentPackage;
    }

    public void setCurrentPackage(Package currentPackage) {
        this.currentPackage = currentPackage;
    }



    public static class Package {
        @JsonProperty("sms_limit")
        private Integer smsLimit;
        @JsonProperty("sms_extra_limit")
        private Integer smsExtraLimit;
        @JsonProperty("sms_used")
        private Integer smsUsed;
        @JsonProperty("sms_error")
        private Integer smsError;
        @JsonProperty("sms_sold")
        private Integer smsSold;
        @JsonProperty("valid_since")
        private Date validSince;
        @JsonProperty("valid_thru")
        private Date validThru;

        public Integer getSmsLimit() {
            return smsLimit;
        }

        public void setSmsLimit(Integer smsLimit) {
            this.smsLimit = smsLimit;
        }

        public Integer getSmsExtraLimit() {
            return smsExtraLimit;
        }

        public void setSmsExtraLimit(Integer smsExtraLimit) {
            this.smsExtraLimit = smsExtraLimit;
        }

        public Integer getSmsUsed() {
            return smsUsed;
        }

        public void setSmsUsed(Integer smsUsed) {
            this.smsUsed = smsUsed;
        }

        public Integer getSmsError() {
            return smsError;
        }

        public void setSmsError(Integer smsError) {
            this.smsError = smsError;
        }

        public Integer getSmsSold() {
            return smsSold;
        }

        public void setSmsSold(Integer smsSold) {
            this.smsSold = smsSold;
        }

        @JsonSerialize(using = JsonDateTimeSerializer.class)
        public Date getValidSince() {
            return validSince;
        }

        @JsonDeserialize(using= JsonDateTimeDeserializer.class)
        public void setValidSince(Date validSince) {
            this.validSince = validSince;
        }

        @JsonSerialize(using = JsonDateTimeSerializer.class)
        public Date getValidThru() {
            return validThru;
        }

        @JsonDeserialize(using= JsonDateTimeDeserializer.class)
        public void setValidThru(Date validThru) {
            this.validThru = validThru;
        }
    }
}
