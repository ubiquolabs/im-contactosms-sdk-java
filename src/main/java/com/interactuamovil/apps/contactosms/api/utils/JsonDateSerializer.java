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
import org.apache.log4j.Logger;

/**
 *
 * @author sergeiw
 */
public class JsonDateSerializer extends JsonSerializer<Date> {

    private static final Logger logger = Logger.getLogger(JsonDateSerializer.class);
    
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");        
    
    @Override
    public void serialize(Date t, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {        
        String sDate = dateFormat.format(t);        
        jg.writeString(sDate);
    }
    
    
    
}
