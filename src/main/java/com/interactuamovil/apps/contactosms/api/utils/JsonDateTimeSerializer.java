/*
 * JsonDateTimeSerializer - Serializes Date objects to JSON format
 * Updated to use modern date handling without timezone offset manipulation
 */
package com.interactuamovil.apps.contactosms.api.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Serializes Date objects to JSON format using ISO datetime format.
 * 
 * Note: This serializer no longer applies timezone offset adjustments.
 * The API now handles timezone conversion server-side, so client-side
 * offset manipulation is no longer necessary.
 * 
 * @author sergeiw
 */
public class JsonDateTimeSerializer extends JsonSerializer<Date> {

    private static final Logger logger = LoggerFactory.getLogger(JsonDateTimeSerializer.class);
    
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");        
    
    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) 
            throws IOException, JsonProcessingException {        
        String formattedDate = dateFormat.format(date);        
        jsonGenerator.writeString(formattedDate);
    }
}
