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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;


/**
 *
 * @author sergeiw
 */
public class JsonDateDeserializer extends JsonDeserializer<Date> {

    private static final Logger logger = Logger.getLogger(JsonDateDeserializer.class);
    
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");        
    
    @Override
    public Date deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        try {
            return dateFormat.parse(jp.getText());
        } catch (ParseException ex) {
            logger.error("Unable to deserialize date: " + jp.getText(), ex);
        }
        return null;
    }
    
}
