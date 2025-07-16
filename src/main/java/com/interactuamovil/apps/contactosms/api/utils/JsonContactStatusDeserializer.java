/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interactuamovil.apps.contactosms.api.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.interactuamovil.apps.contactosms.api.enums.ContactStatus;
import java.io.IOException;

/**
 *
 * @author sergeiw
 */
public class JsonContactStatusDeserializer extends JsonDeserializer<ContactStatus> {

    @Override
    public ContactStatus deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        return ContactStatus.fromValue(jp.getText());
    }
    
}
