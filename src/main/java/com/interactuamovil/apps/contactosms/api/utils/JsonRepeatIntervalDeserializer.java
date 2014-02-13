/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interactuamovil.apps.contactosms.api.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.interactuamovil.apps.contactosms.api.enums.RepeatInterval;
import java.io.IOException;

/**
 *
 * @author sergeiw
 */
public class JsonRepeatIntervalDeserializer extends JsonDeserializer<RepeatInterval> {

    @Override
    public RepeatInterval deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        return RepeatInterval.valueOf(jp.getText());
    }
    
}
