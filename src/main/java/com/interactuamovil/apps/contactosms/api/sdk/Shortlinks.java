package com.interactuamovil.apps.contactosms.api.sdk;

import com.fasterxml.jackson.core.type.TypeReference;
import com.interactuamovil.apps.contactosms.api.client.rest.shortlinks.ShortlinkJsonObject;
import com.interactuamovil.apps.contactosms.api.utils.ApiResponse;
import com.interactuamovil.apps.contactosms.api.utils.JsonObjectCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

public class Shortlinks extends Request {

    private static final Logger logger = LoggerFactory.getLogger(Shortlinks.class);
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public Shortlinks(String apiKey, String secretKey, String apiUri) {
        super(apiKey, secretKey, apiUri);
    }

    /**
     * Create a new shortlink
     *
     * @param longUrl The long URL to shorten (required)
     * @param name Optional name for the shortlink
     * @param status Optional status (ACTIVE or INACTIVE), defaults to ACTIVE
     * @return ApiResponse with the created shortlink
     */
    public ApiResponse<ShortlinkJsonObject> create(String longUrl, String name, String status) {
        ApiResponse<ShortlinkJsonObject> response;
        ShortlinkJsonObject shortlinkResponse;

        Map<String, Serializable> bodyParams = new LinkedHashMap<>();
        bodyParams.put("long_url", longUrl);
        
        if (name != null && !name.isEmpty()) {
            bodyParams.put("name", name);
        }
        
        if (status != null && !status.isEmpty()) {
            bodyParams.put("status", status);
        } else {
            bodyParams.put("status", "ACTIVE");
        }

        try {
            response = doRequest("short_link", "post", null, bodyParams, false);
            if (response.isOk()) {
                shortlinkResponse = ShortlinkJsonObject.fromJson(response.getRawResponse());
                response.setResponse(shortlinkResponse);
            }
        } catch (Exception e) {
            logger.error("Error creating shortlink", e);
            response = new ApiResponse<>();
            response.setErrorCode(-1);
            response.setErrorDescription(e.getMessage());
        }
        return response;
    }

    /**
     * Create a new shortlink with default ACTIVE status
     *
     * @param longUrl The long URL to shorten (required)
     * @param name Optional name for the shortlink
     * @return ApiResponse with the created shortlink
     */
    public ApiResponse<ShortlinkJsonObject> create(String longUrl, String name) {
        return create(longUrl, name, "ACTIVE");
    }

    /**
     * Create a new shortlink with only the URL
     *
     * @param longUrl The long URL to shorten (required)
     * @return ApiResponse with the created shortlink
     */
    public ApiResponse<ShortlinkJsonObject> create(String longUrl) {
        return create(longUrl, null, "ACTIVE");
    }

    /**
     * List shortlinks with optional filters
     *
     * @param startDate Optional start date filter (yyyy-MM-dd format)
     * @param endDate Optional end date filter (yyyy-MM-dd format)
     * @param limit Optional limit for pagination
     * @param offset Optional offset for pagination
     * @param id Optional shortlink ID to filter by
     * @return ApiResponse with list of shortlinks
     */
    public ApiResponse<List<ShortlinkJsonObject>> getList(String startDate, String endDate, Integer limit, Integer offset, String id) {
        ApiResponse<List<ShortlinkJsonObject>> response;
        List<ShortlinkJsonObject> shortlinkResponse;

        Map<String, Serializable> urlParams = new LinkedHashMap<>();
        
        if (startDate != null && !startDate.isEmpty()) {
            urlParams.put("start_date", startDate);
        }
        
        if (endDate != null && !endDate.isEmpty()) {
            urlParams.put("end_date", endDate);
        }
        
        if (limit != null && limit > 0) {
            urlParams.put("limit", limit);
        }
        
        if (offset != null && offset >= 0) {
            urlParams.put("offset", offset);
        }
        
        if (id != null && !id.isEmpty()) {
            urlParams.put("id", id);
        }

        try {
            response = doRequest("short_link", "get", urlParams, null, true);
            if (response.isOk()) {
                String rawResponse = response.getRawResponse();
                
                if (rawResponse != null && rawResponse.trim().startsWith("{")) {
                    try {
                        Map<String, Object> responseMap = JsonObjectCollection.fromJson(rawResponse, new TypeReference<Map<String, Object>>() {});
                        if (responseMap != null && responseMap.containsKey("data") && responseMap.get("data") instanceof List) {
                            shortlinkResponse = JsonObjectCollection.fromJson(
                                getSerializer().serialize(responseMap.get("data")), 
                                new TypeReference<List<ShortlinkJsonObject>>() {}
                            );
                        } else {
                            shortlinkResponse = Collections.emptyList();
                        }
                    } catch (Exception e) {
                        shortlinkResponse = JsonObjectCollection.fromJson(rawResponse, new TypeReference<List<ShortlinkJsonObject>>() {});
                    }
                } else {
                    shortlinkResponse = JsonObjectCollection.fromJson(rawResponse, new TypeReference<List<ShortlinkJsonObject>>() {});
                }
                response.setResponse(shortlinkResponse);
            }
        } catch (Exception e) {
            logger.error("Error listing shortlinks", e);
            response = new ApiResponse<>();
            response.setErrorCode(-1);
            response.setErrorDescription(e.getMessage());
        }
        return response;
    }

    /**
     * List all shortlinks (no filters)
     *
     * @return ApiResponse with list of shortlinks
     */
    public ApiResponse<List<ShortlinkJsonObject>> getList() {
        return getList(null, null, null, null, null);
    }

    /**
     * Get shortlink by ID
     *
     * @param id The shortlink ID
     * @return ApiResponse with the shortlink
     */
    public ApiResponse<ShortlinkJsonObject> getById(String id) {
        ApiResponse<ShortlinkJsonObject> response;
        ShortlinkJsonObject shortlinkResponse;

        Map<String, Serializable> urlParams = new LinkedHashMap<>();
        urlParams.put("id", id);

        try {
            ApiResponse<List<ShortlinkJsonObject>> listResponse = getList(null, null, null, null, id);
            if (listResponse.isOk() && listResponse.getResponse() != null && !listResponse.getResponse().isEmpty()) {
                shortlinkResponse = listResponse.getResponse().get(0);
                response = new ApiResponse<>();
                response.setOk(true);
                response.setResponse(shortlinkResponse);
                response.setHttpCode(listResponse.getHttpCode());
                response.setRawResponse(listResponse.getRawResponse());
            } else {
                response = new ApiResponse<>();
                response.setOk(false);
                response.setErrorCode(listResponse.getErrorCode());
                response.setErrorDescription("Shortlink not found");
                response.setHttpCode(listResponse.getHttpCode());
            }
        } catch (Exception e) {
            logger.error("Error getting shortlink by ID", e);
            response = new ApiResponse<>();
            response.setErrorCode(-1);
            response.setErrorDescription(e.getMessage());
        }
        return response;
    }

    /**
     * Update shortlink status
     *
     * @param id The shortlink ID
     * @param status The new status (ACTIVE or INACTIVE)
     * @return ApiResponse with the updated shortlink
     */
    public ApiResponse<ShortlinkJsonObject> updateStatus(String id, String status) {
        ApiResponse<ShortlinkJsonObject> response;
        ShortlinkJsonObject shortlinkResponse;

        Map<String, Serializable> urlParams = new LinkedHashMap<>();
        urlParams.put("id", id);
        urlParams.put("status", status);

        try {
            response = doRequest("short_link", "put", urlParams, null, true);
            if (response.isOk()) {
                shortlinkResponse = ShortlinkJsonObject.fromJson(response.getRawResponse());
                response.setResponse(shortlinkResponse);
            }
        } catch (Exception e) {
            logger.error("Error updating shortlink status", e);
            response = new ApiResponse<>();
            response.setErrorCode(-1);
            response.setErrorDescription(e.getMessage());
        }
        return response;
    }
}

