package com.interactuamovil.contactosms.api;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interactuamovil.contactosms.api.responses.InboxMessage;
import java.io.IOException;

/**
 *
 * @author sergeiw
 */
public class InboxReceiver {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static InboxMessage parseInboxJson(String inboxJson) throws JsonMappingException, JsonParseException, IOException {
        InboxMessage messageResponse = mapper.readValue(inboxJson, InboxMessage.class);
        return messageResponse;
    }




}
