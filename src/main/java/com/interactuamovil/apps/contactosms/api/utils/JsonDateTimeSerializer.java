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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.log4j.Logger;

/**
 *
 * @author sergeiw
 */
public class JsonDateTimeSerializer extends JsonSerializer<Date> {

    private static final Logger logger = Logger.getLogger(JsonDateTimeSerializer.class);
    private static int offset = 0;
    
    SimpleDateFormat dateFormat;


    public JsonDateTimeSerializer(){
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Override
    public void serialize(Date t, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
//        System.out.print("asdf-" + offset + "-fdsa" + t);
        t.setTime(t.getTime() + offset*3600000);
        String sDate = dateFormat.format(t);        
        jg.writeString(sDate);
    }

    public static void setOffset(int hours){
        offset = hours;
    }
    
    
}
