package com.interactuamovil.contactosms.api.responses;

import java.util.List;

public class MessageResponse {

    class MessageRecipients {
        private String msisdn;

        public void setMsisdn(String s) {
            msisdn = s;
        }

        public String getMsisdn() {
            return msisdn;
        }
    }

    private int id;
    private int recepientsCount;
    private String message;
    private List<MessageRecipients> recipients;

    public void setId(int i) {
        id = i;
    }

    public void setRecepientsCount(int i) {
        recepientsCount = i;
    }

    public void setMessage(String s) {
        message = s;
    }

    public void setRecipients(List<MessageRecipients> mr) {
        recipients = mr;
    }

    public int getId() {
        return id;
    }

    public int getRecepientsCount() {
        return recepientsCount;
    }

    public String getMessage() {
        return message;
    }

    public List<MessageRecipients> getRecipients() {
        return recipients;
    }

}
