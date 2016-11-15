package com.interactuamovil.apps.contactosms.api.sdk;

import com.fasterxml.jackson.core.type.TypeReference;
import com.interactuamovil.apps.contactosms.api.client.rest.messages.MessageJson;
import com.interactuamovil.apps.contactosms.api.client.rest.messages.MessageRecipientsJson;
import com.interactuamovil.apps.contactosms.api.enums.MessageDirection;
import com.interactuamovil.apps.contactosms.api.utils.ApiResponse;
import com.interactuamovil.apps.contactosms.api.utils.JsonObjectCollection;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

public class Messages extends Request {
    public Messages(String apiKey, String secretKey, String apiUri) {
        super(apiKey, secretKey, apiUri);
    }

    private String getDateFormat(Date d) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(d);
    }

    /**
     * Gets log message list
     *
     * @param startDate The star date
     * @param endDate The end date
     * @param start the offset of the results
     * @param limit the limit of the result list
     * @param msisdn The msisdn
     * @return The messages list queried
     */
    public ApiResponse<List<MessageJson>> getList(Date startDate, Date endDate, int start, int limit, String msisdn) {
        return getList(startDate, endDate, start, limit, msisdn, MessageDirection.ALL);
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
         */
    public ApiResponse<List<MessageJson>> getList(Date startDate, Date endDate, int start, int limit, String msisdn, MessageDirection direction) {
        Map<String, Serializable> urlParameters = new LinkedHashMap<String, Serializable>();
        ApiResponse<List<MessageJson>> response;
        List<MessageJson> messageResponse;


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
        if (direction != null)
            urlParameters.put("direction", direction.name());

        try {
            response = doRequest("messages", "get", urlParameters, null, true);
            if (response.isOk()) {
                messageResponse = JsonObjectCollection.fromJson(response.getRawResponse(), new TypeReference<List<MessageJson>>() {});                
                response.setResponse(messageResponse);
            }            
        } catch (Exception e) {
            response = new ApiResponse<List<MessageJson>>();
            response.setErrorCode(-1);
            response.setErrorDescription(e.getMessage());
        }
        return response;
    }

    /**
     * Sends a message to a group array list
     *
     * @param tagNames
     * @param message
     * @return
     */
    public ApiResponse<MessageJson> sendToGroups(String[] tagNames, String message, String messageId) {
        Map<String, Serializable> params = new LinkedHashMap<String, Serializable>();
        ApiResponse<MessageJson> response;
        MessageJson messageResponse;

        params.put("tags", tagNames);
        params.put("message", message);
        params.put("id", messageId);

        try {
            response = doRequest("messages/send", "post", null, params, false);
            if (response.isOk()) {
                messageResponse = MessageJson.fromJson(response.getRawResponse());
                response.setResponse(messageResponse);
            }
        } catch (Exception e) {
            response = new ApiResponse<MessageJson>();
            response.setErrorCode(-1);
            response.setErrorDescription(e.getMessage());
        }
        return response;
    }

    /**
     * Sends a message to a specific contact
     *
     * @param msisdn The msisdn to send
     * @param message The text message
     * @return The message just sent
     */
    public ApiResponse<MessageJson> sendToContact(String msisdn, String message) {
        return sendToContact(msisdn, message, null);
    }


    /**
     * Sends a message to a contact
     * @param msisdn The msisdn
     * @param message The text message
     * @param messageId The message Id
     * @return The message just sent
     */
    public ApiResponse<MessageJson> sendToContact(String msisdn, String message, String messageId) {
        Map<String, Serializable> params = new LinkedHashMap<String, Serializable>();
        ApiResponse<MessageJson> response;
        MessageJson messageResponse;

        params.put("msisdn", msisdn);
        params.put("message", message);
        if (messageId != null) {
            params.put("id", messageId);
        }

        try {
            response = doRequest("messages/send_to_contact", "post", null, params, false);
            if (response.isOk()) {
                messageResponse = MessageJson.fromJson(response.getRawResponse());            
                response.setResponse(messageResponse);
            }
        } catch (Exception e) {
            response = new ApiResponse<MessageJson>();
            response.setErrorCode(-1);
            response.setErrorDescription(e.getMessage());
        }
        return response;
    }


    /**
     * Gets detailed information of the recipients' delivery status given a message
     * @param messageId The message Id returned by the sendToContact/sendToGroups endpoint
     * @param page The page. starts with 1.
     * @param limit The limit of the result set.
     * @return A list of recipients and their status.
     */
    public ApiResponse<List<MessageRecipientsJson>> getMessageRecipientsList(int messageId, int page, int limit) {
        Map<String, Serializable> urlParams = new HashMap<String, Serializable>(3);
        urlParams.put("message_id", messageId);
        urlParams.put("page", page);
        urlParams.put("limit", limit);
        ApiResponse<List<MessageRecipientsJson>> response;
        try {
            response =
                    doRequest("messages/%s/recipients", "get", urlParams, null, true);
        } catch (Exception e) {
            response = new ApiResponse<List<MessageRecipientsJson>>();
            response.setErrorCode(-1);
            response.setErrorDescription(e.getMessage());
        }
        return response;
    }

    /**
     * Gets schedule messsages
     *
     * @return
     *
    public ListResponse<ScheduleMessageResponse> getSchedule() {
        ListResponse<ScheduleMessageResponse> response = new ListResponse<ScheduleMessageResponse>();
        List<ScheduleMessageResponse> messageResponse;

        try {
            String serverResponse = doRequest("messages/scheduled", "get", null, null, false);
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
     * Removes a schedule messsage
     *
     * @param messageId
     * @return
     *
    public Response<ActionMessageResponse> deleteSchedule(int messageId) {
        Response<ActionMessageResponse> response = new Response<ActionMessageResponse>();
        Map<String, Serializable> params = new LinkedHashMap<String, Serializable>();
        ActionMessageResponse messageResponse;

        params.put("message_id", messageId);

        try {
            String serverResponse = doRequest("messages/scheduled", "delete", null, params, false);
            ObjectMapper mapper = new ObjectMapper();

            messageResponse = mapper.readValue(serverResponse, ActionMessageResponse.class);
            response.setResult(messageResponse);
        } catch (Exception e) {
            response.setError(true);
            response.setErrorMessage(e.getMessage());
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
     *
    public ApiResponse<ScheduledMessageJson> addSchedule(Date startDate, Date endDate, String eventName, String message, String time, String frequency, String repeatDays, String[] groups) {
        Map<String, Serializable> params = new LinkedHashMap<String, Serializable>();
        ApiResponse<ScheduledMessageJson> response = new ApiResponse<ScheduledMessageJson>();
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
            String serverResponse = doRequest("messages/scheduled", "post", null, params, false);
            messageResponse = ScheduledMessageJson.fromJson(serverResponse);
            response.setRawResponse(serverResponse);
            response.setResponse(messageResponse);            
        } catch (Exception e) {
            response.setErrorCode(-1);
            response.setErrorDescription(e.getMessage());
        }
        return response;
    }

    /**
     * Gets the inbox messages
     *
     * @param startDate
     * @param endDate
     * @param start
     * @param limit
     * @param msisdn
     * @param status
     * @return
     *
    public ListResponse<InboxMessageResponse> inbox(Date startDate, Date endDate, int start, int limit, String msisdn, int status) {
        ListResponse<InboxMessageResponse> response = new ListResponse<InboxMessageResponse>();
        Map<String, Serializable> params = new LinkedHashMap<String, Serializable>();
        List<InboxMessageResponse> messageResponse;
        int _status = decodeStatus(status);

        if (!(startDate == null || endDate == null)) {
            params.put("start_date", getDateFormat(startDate));
            params.put("end_date", getDateFormat(endDate));
        }
        if (start >= 0)
            params.put("start", start);
        if (limit >= 0)
            params.put("limit", limit);
        if (!(msisdn == null) && msisdn.length() > 3)
            params.put("msisdn", msisdn);
        if (_status > -1)
            params.put("status", _status);

        try {
            String serverResponse = doRequest("messages/messages_inbox", "get", params, null, true);
            ObjectMapper mapper = new ObjectMapper();

            messageResponse = mapper.readValue(serverResponse, List.class);
            response.setResult(messageResponse);
        } catch (Exception e) {
            response.setHasError(true);
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    private int decodeStatus(int type) {
        int result;

        switch (type) {
            case 0:
                result = 0;
                break;
            case 1:
                result = 1;
                break;
            case 2:
                result = 2;
                break;
            default:
                result = -1;
        }
        return result;
    }
    */
}