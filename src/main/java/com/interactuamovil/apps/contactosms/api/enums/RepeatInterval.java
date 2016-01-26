
package com.interactuamovil.apps.contactosms.api.enums;

/**
 *
 * @author Kenny
 */
public enum RepeatInterval {
    
    YEARLY("YEARLY"),
    MONTHLY("MONTHLY"),
    WEEKLY("WEEKLY"),
    DAILY("DAILY"),
    HOURLY("HOURLY"),
    ONCE("ONCE");
    
    
    private String repeatInterval;
    
    private RepeatInterval(String repeatInterval){
        this.repeatInterval= repeatInterval;
        
    }
    
    public String getRepeatInterval(){
        return this.repeatInterval;
    }
}
