package com.interactuamovil.apps.contactosms.api.sdk;

import com.fasterxml.jackson.core.type.TypeReference;
import com.interactuamovil.apps.contactosms.api.client.rest.contacts.ContactJsonObject;
import com.interactuamovil.apps.contactosms.api.client.rest.messages.ScheduledMessageJson;
import com.interactuamovil.apps.contactosms.api.enums.RepeatInterval;
import com.interactuamovil.apps.contactosms.api.utils.ApiResponse;
import com.interactuamovil.apps.contactosms.api.utils.JsonObjectCollection;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ScheduledMessages extends Request {
    public ScheduledMessages(String apiKey, String secretKey, String apiUri) {
        super(apiKey, secretKey, apiUri);
    }

    private String getDateFormat(Date d) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(d);
    }

    /**
     * Gets log message list
     *
     * @param startDate
     * @param endDate
     * @param start
     * @param limit
     * @param msisdn
     * @return
     *
    public ListResponse<MessageResponse> getList(Date startDate, Date endDate, int start, int limit, String msisdn) {
        Map<String, Serializable> urlParameters = new LinkedHashMap<String, Serializable>();
        ListResponse<MessageResponse> response = new ListResponse<MessageResponse>();
        List<MessageResponse> messageResponse;


        if (!(startDate == null) && !(endDate == null)) {
            urlParameters.put("start_date", getDateFormat(startDate));
            urlParameters.put("end_date", getDateFormat(endDate));
        }
        if (start != -1)
            urlParameters.put("start", start);
        if (limit != -1)
            urlParameters.put("limit", limit);
        if (msisdn != null)
            urlParameters.put("msisdn", msisdn);

        try {
            String serverResponse = doRequest("messages", "get", urlParameters, null, true);
            ObjectMapper mapper = new ObjectMapper();

            messageResponse = mapper.readValue(serverResponse, List.class);
            response.setResult(messageResponse);
        } catch (Exception e) {
            response.setHasError(true);
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }
    

    /**
     * Gets schedule messsages
     *
     * @return
     */
    public ApiResponse<List<ScheduledMessageJson>> getSchedule(Date since) {
        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();
        ApiResponse<List<ScheduledMessageJson>> response;
        List<ScheduledMessageJson> messageResponse;

        try {
            urlParams.put("since", getDateFormat(since));
            
            response = doRequest("messages/scheduled", "get", urlParams, null, true);
            if (response.isOk()) {
                messageResponse = JsonObjectCollection.fromJson(response.getRawResponse(), new TypeReference<List<ScheduledMessageJson>>() {});                
                response.setResponse(messageResponse);
            }
        } catch (Exception e) {
            response = new ApiResponse<List<ScheduledMessageJson>>();
            response.setErrorCode(-1);
            response.setErrorDescription(e.getMessage());
        }        
        return response;
    }

    
    /**
     * get a schedule messsage
     *
     * @param scheduledMessageId
     * @return
     */
    public ApiResponse<ScheduledMessageJson> getById(int scheduledMessageId) {        
        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();
        ApiResponse<ScheduledMessageJson> response;
        ScheduledMessageJson messageResponse;

        urlParams.put("scheduledMessageId", scheduledMessageId);

        try {
            response = doRequest("messages/scheduled/"+scheduledMessageId, "get", urlParams, null, false);
            if (response.isOk()) {
                messageResponse = ScheduledMessageJson.fromJson(response.getRawResponse());
                response.setResponse(messageResponse);
            }
        } catch (Exception e) {
            response = new ApiResponse<ScheduledMessageJson>();
            response.setErrorCode(-1);
            response.setErrorDescription(e.getMessage());
        }
        return response;
    }
    
    /**
     * Removes a schedule messsage
     *
     * @param scheduledMessageId
     * @return
     */
    public ApiResponse<ScheduledMessageJson> delete(int scheduledMessageId) {        
        Map<String, Serializable> urlParams = new LinkedHashMap<String, Serializable>();
        ApiResponse<ScheduledMessageJson> response;
        ScheduledMessageJson messageResponse;

        urlParams.put("scheduledMessageId", scheduledMessageId);

        try {
            response = doRequest("messages/scheduled/"+scheduledMessageId, "delete", urlParams, null, false);
            if (response.isOk()) {
                messageResponse = ScheduledMessageJson.fromJson(response.getRawResponse());
                response.setResponse(messageResponse);
            }
        } catch (Exception e) {
            response = new ApiResponse<ScheduledMessageJson>();
            response.setErrorCode(-1);
            response.setErrorDescription(e.getMessage());
        }
        return response;
    }

    /**
     * Adds a new schedule messsage
     *
     * @param startDate
     * @param endDate
     * @param message
     * @param time
     * @param frequency
     * @param groups
     * @return
     */
    public ApiResponse<ScheduledMessageJson> add(Date startDate, Date endDate, String eventName, String message, String time, RepeatInterval frequency, String repeatDays, String[] groups) {
        Map<String, Serializable> params = new LinkedHashMap<String, Serializable>();
        ApiResponse<ScheduledMessageJson> response;
        ScheduledMessageJson messageResponse;

        params.put("event_name", eventName);
        params.put("start_date", getDateFormat(startDate));
        params.put("end_date", getDateFormat(endDate));
        params.put("message", message);
        params.put("execution_time", time);
        params.put("repeat_interval", frequency);
        params.put("repeat_days", repeatDays);
        params.put("groups", groups);

        try {
            response = doRequest("messages/scheduled", "post", null, params, false);            
            if (response.isOk()) {
                messageResponse = ScheduledMessageJson.fromJson(response.getRawResponse());
                response.setResponse(messageResponse);
            }
        } catch (Exception e) {
            response = new ApiResponse<ScheduledMessageJson>();
            response.setErrorCode(-1);
            response.setErrorDescription(e.getMessage());
        }
        return response;
    }
    
}