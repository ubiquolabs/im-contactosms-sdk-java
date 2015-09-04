/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interactuamovil.apps.contactosms.api.utils;

import com.fasterxml.jackson.core.JsonParser;
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
public class JsonDateTimeDeserializer extends JsonDeserializer<Date> {

    private static final Logger logger = Logger.getLogger(JsonDateTimeDeserializer.class);
    
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");        
    
    @Override
    public Date deserialize(JsonParser jp, DeserializationContext dc) throws IOException {

//        System.out.println("asdf-" + jp.getText());

        try {
            return dateFormat.parse(jp.getText());
//            Date date = dateFormat.parse(jp.getText());
//            System.out.println("asdf-" + date);
//            return date;

        } catch (ParseException ex) {
            logger.error("Unable to deserialize date: " + jp.getText(), ex);
        }
        return null;
    }
    
}
