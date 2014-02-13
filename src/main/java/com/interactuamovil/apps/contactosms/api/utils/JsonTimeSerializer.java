/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interactuamovil.apps.contactosms.api.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import org.apache.log4j.Logger;

/**
 *
 * @author sergeiw
 */
public class JsonTimeSerializer extends JsonSerializer<Time> {

    private static final Logger logger = Logger.getLogger(JsonTimeSerializer.class);
    
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");        
    
    
    @Override
    public void serialize(Time t, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
        
        String sTime = dateFormat.format(t);
        jg.writeString(sTime);
        
    }
    
}
