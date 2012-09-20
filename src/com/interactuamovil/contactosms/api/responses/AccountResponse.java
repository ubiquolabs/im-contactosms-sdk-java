package com.interactuamovil.contactosms.api.responses;

public class AccountResponse {

    private String name;
    private String sms_short_name;
    private String sms_subscription_type;
    private String sms_optin_keyword;
    private Integer messages_sent;
    private Integer messages_limit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSms_short_name() {
        return sms_short_name;
    }

    public void setSms_short_name(String sms_short_name) {
        this.sms_short_name = sms_short_name;
    }

    public String getSms_subscription_type() {
        return sms_subscription_type;
    }

    public void setSms_subscription_type(String sms_subscription_type) {
        this.sms_subscription_type = sms_subscription_type;
    }

    public String getSms_optin_keyword() {
        return sms_optin_keyword;
    }

    public void setSms_optin_keyword(String sms_optin_keyword) {
        this.sms_optin_keyword = sms_optin_keyword;
    }

    public Integer getMessages_sent() {
        return messages_sent;
    }

    public void setMessages_sent(Integer messages_sent) {
        this.messages_sent = messages_sent;
    }

    public Integer getMessages_limit() {
        return messages_limit;
    }

    public void setMessages_limit(Integer messages_limit) {
        this.messages_limit = messages_limit;
    }

}

