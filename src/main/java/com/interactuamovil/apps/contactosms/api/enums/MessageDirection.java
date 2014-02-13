
package com.interactuamovil.apps.contactosms.api.enums;

/**
 *
 * @author Kenny
 */
public enum MessageDirection {
    
    MO("MO"),
    MT("MT");
    
    
    
    private String messageDirection;
    
    private MessageDirection(String messageDirection){
        this.messageDirection = messageDirection;
    }
    
    public String getMessageDirection(){
        return this.messageDirection;
    }
}
