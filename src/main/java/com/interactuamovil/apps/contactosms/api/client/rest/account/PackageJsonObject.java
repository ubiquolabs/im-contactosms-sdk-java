package com.interactuamovil.apps.contactosms.api.client.rest.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interactuamovil.apps.contactosms.api.utils.JsonDateTimeDeserializer;
import com.interactuamovil.apps.contactosms.api.utils.JsonDateTimeSerializer;
import com.interactuamovil.apps.contactosms.api.utils.JsonObject;

import java.util.Date;

/**
 * Created by javier on 3/02/16.
 * Package Json Object
 */
public class PackageJsonObject extends JsonObject {

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
