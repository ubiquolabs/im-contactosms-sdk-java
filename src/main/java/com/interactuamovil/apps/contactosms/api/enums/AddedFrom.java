
package com.interactuamovil.apps.contactosms.api.enums;

/**
 *
 * @author Kenny
 */
public enum AddedFrom {
    
    WEB_FORM("WEB_FORM"),
    FILE_UPLOAD("FILE_UPLOAD"),
    API("API"),
    SUBSCRIPTION_REQUEST("SUBSCRIPTION_REQUEST"),
    SMS("SMS"),
    IM_REACH_NOTIFICATION_API("IM_REACH_NOTIFICATION_API");
    
    
    
    
    private String addedFrom;
    private AddedFrom(String addedFrom){
        this.addedFrom = addedFrom;
    }
    
    public String getContactAddedFrom(){
        return this.addedFrom;
        
    }
}
