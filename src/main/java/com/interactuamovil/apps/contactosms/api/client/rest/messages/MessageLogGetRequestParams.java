/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interactuamovil.apps.contactosms.api.client.rest.messages;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.interactuamovil.apps.contactosms.api.utils.JsonObject;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author sergeiw
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class MessageLogGetRequestParams extends JsonObject {
    
    public static final int DEFAULT_LIMIT = 200;
    public static final int DEFAULT_START = 0;
    public static final int DEFAULT_ORDER = 200;
    
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    static {
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }
    
    
    @JsonProperty("start")
    private Integer start = DEFAULT_START;
    
    @JsonProperty("limit")
    private Integer limit = DEFAULT_LIMIT;
    
    @JsonProperty("msisdn")
    private String msisdn;
    
    @JsonProperty("short_name")
    private String groupShortName;
    
    @JsonProperty("include_recipients")
    private boolean includeRecipients = Boolean.FALSE;
    
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT")
    @JsonProperty("start_date")
    private Date startDate;
    
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT")
    @JsonProperty("end_date")
    private Date endDate;
        
    
    public static MessageLogGetRequestParams fromJson(String json) throws IOException {
        return JsonObject.fromJson(json, MessageLogGetRequestParams.class);        
    }

    /*public static MessageLogGetRequestParams fromQuery(Query query) throws InvalidParameterException {
        
        MessageLogGetRequestParams params = new MessageLogGetRequestParams();
        
        params.setStartDate(RequestParametersHelper.getDate(query, "start_date", dateFormat));
        params.setEndDate(RequestParametersHelper.getDate(query, "end_date", dateFormat));
        
        Integer start = RequestParametersHelper.getInteger(query, "start", Boolean.TRUE, 0);
        if (start != null) {
            params.setStart(start);
        }
        
        Integer limit = RequestParametersHelper.getInteger(query, "limit", Boolean.TRUE, 1, 1000);
        if (limit != null) {
            params.setLimit(limit);
        }
        
        params.setMsisdn(RequestParametersHelper.getString(query, "msisdn"));
        params.setGroupShortName(RequestParametersHelper.getString(query, "short_name"));
        params.setIncludeRecipients(RequestParametersHelper.getBoolean(query, "include_recipients", Boolean.FALSE));
        
        return params;
    }*/

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("\nstart_date: %s", dateFormat.format(getStartDate())));
        sb.append(String.format("\nend_date: %s", dateFormat.format(getEndDate())));
        sb.append(String.format("\nstart: %d", getStart()));
        sb.append(String.format("\nlimit: %d", getLimit()));
        sb.append(String.format("\nmsisdn: %s", getMsisdn()));
        sb.append(String.format("\nshort_name: %s", getGroupShortName()));
        sb.append(String.format("\ninclude_recipients: %s", isIncludeRecipients()?"true":"false"));
        
        return sb.toString();
    }
    
    
    
    /**
     * @return the start
     */
    public Integer getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(Integer start) {
        this.start = start;
    }

    /**
     * @return the limit
     */
    public Integer getLimit() {
        return limit;
    }

    /**
     * @param limit the limit to set
     */
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    /**
     * @return the msisdn
     */
    public String getMsisdn() {
        return msisdn;
    }

    /**
     * @param msisdn the msisdn to set
     */
    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    /**
     * @return the groupShortName
     */
    public String getGroupShortName() {
        return groupShortName;
    }

    /**
     * @param groupShortName the groupShortName to set
     */
    public void setGroupShortName(String groupShortName) {
        this.groupShortName = groupShortName;
    }

    /**
     * @return the includeRecipients
     */
    public boolean isIncludeRecipients() {
        return includeRecipients;
    }

    /**
     * @param includeRecipients the includeRecipients to set
     */
    public void setIncludeRecipients(boolean includeRecipients) {
        this.includeRecipients = includeRecipients;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        if (startDate == null) {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            cal.set(Calendar.HOUR, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            startDate = cal.getTime();
        }
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        if (endDate == null) {
            endDate = Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime();
        }
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
}
