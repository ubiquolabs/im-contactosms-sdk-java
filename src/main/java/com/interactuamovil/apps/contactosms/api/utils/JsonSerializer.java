/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interactuamovil.apps.contactosms.api.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author sergeiw
 */
public class JsonSerializer implements ISerializer {
    
    private static final Logger logger = LoggerFactory.getLogger(JsonSerializer.class);
    
    private static JsonFactory jsonFactory;
    private static ObjectMapper objectMapper;
    
    private static JsonSerializer instance;

    static {
        objectMapper = new ObjectMapper()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, Boolean.TRUE)
            .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, Boolean.TRUE);
        
        // Configure to NOT escape non-ASCII characters (like Python's ensure_ascii=False)
        objectMapper.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, false);
        
        jsonFactory = new JsonFactory(); 
                
    }
    
    public static JsonSerializer getInstance() {
        if (instance == null)
            instance = new JsonSerializer();
        return instance;
    }

    @Override
    public String serialize(Object o) throws IOException {
        try {
            if (o == null)
                return "";

            // Use Jackson ObjectMapper consistently (like Python's json.dumps with ensure_ascii=False)
            // This approach is more reliable than manual StringBuilder serialization
            String jsonString = objectMapper.writeValueAsString(o);
            
            logger.debug("{}:Json: {}", o.getClass().getCanonicalName(), jsonString);
            return jsonString;
        } catch (Exception e) {
            logger.error("Serialization failed", e);
            throw e;
        }
    }
    
    @Override
    public <T> T deserialize(String eventString, Class<T> type) throws IOException {        
        return objectMapper.readValue(eventString, type);
    }
    
    @Override
    public JsonNode deserialize(String eventString) throws IOException {
        return objectMapper.readTree(eventString);
    }
    
    @Override
    public <T> T  deserialize(JsonNode node, Class<T> type) throws IOException {
        return objectMapper.treeToValue(node, type);
    }

    public <T> T deserialize(String eventString, TypeReference<T> type) throws IOException {
        return objectMapper.readValue(eventString, type);
    }
}
