/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interactuamovil.apps.contactosms.api.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import org.apache.log4j.Logger;

/**
 *
 * @author sergeiw
 */
public class JsonTimeDeserializer extends JsonDeserializer<Time> {

    private static final Logger logger = Logger.getLogger(JsonTimeSerializer.class);
    
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");        

    @Override
    public Time deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        Time t = Time.valueOf(String.format("%s:00",jp.getText()));
        return t;        
    }
    
}
