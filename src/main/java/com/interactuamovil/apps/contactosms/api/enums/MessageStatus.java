
package com.interactuamovil.apps.contactosms.api.enums;

/**
 *
 * @author Kenny
 */
public enum MessageStatus {
    
    PENDING("PENDING"),
    PROCESSING("PROCESSING"),
    READY("READY"),
    SENT("SENT"),
    UNREAD("UNREAD"),
    READ("READ"),
    REPLIED("REPLIED"),
    FORWARDED("FORWARDED"),
    ERROR("ERROR");
    
    private String messageStatus;
    
    
    private MessageStatus(String messageStatus){
        this.messageStatus = messageStatus;
    }
    
    public String getMessageStatus(){
        return this.messageStatus;
    }
    
    
    
    
    
}
