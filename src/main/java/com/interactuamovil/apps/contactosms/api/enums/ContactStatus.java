
package com.interactuamovil.apps.contactosms.api.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 *
 * @author Kenny
 */
public enum ContactStatus {
    
    SUSCRIBED("SUSCRIBED"),
    CONFIRMED("CONFIRMED"),
    CANCELLED("CANCELLED"),
    INVITED("INVITED");
    
    
    
    private String contactStatus;
    
    private ContactStatus(String contactStatus){
        this.contactStatus = contactStatus;
    }
    
    public String getContactStatus(){
        return this.contactStatus;
    }
    
    @JsonCreator
    public static ContactStatus create(String value) {
        if(value == null) {
            throw new IllegalArgumentException();
        }
        ContactStatus s = ContactStatus.valueOf(value);
        return s;        
    }
}
