package com.interactuamovil.apps.contactosms.api.sdk;

import com.fasterxml.jackson.core.type.TypeReference;
import com.interactuamovil.apps.contactosms.api.client.rest.shortlinks.ShortlinkJsonObject;
import com.interactuamovil.apps.contactosms.api.utils.ApiResponse;
import com.interactuamovil.apps.contactosms.api.utils.JavaVersionDetector;
import com.interactuamovil.apps.contactosms.api.utils.JsonObjectCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Shortlinks extends Request {

    private static final Logger logger = LoggerFactory.getLogger(Shortlinks.class);
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final Set<String> SHORTLINK_FIELDS;

    static {
        Set<String> fields = new HashSet<>();
        fields.add("_id");
        fields.add("url_id");
        fields.add("account_uid");
        fields.add("name");
        fields.add("status");
        fields.add("base_url");
        fields.add("short_url");
        fields.add("long_url");
        fields.add("url");
        fields.add("visits");
        fields.add("unique_visits");
        fields.add("preview_visits");
        fields.add("created_by");
        fields.add("created_on");
        fields.add("alias");
        fields.add("reference_type");
        fields.add("expiration");
        fields.add("expiration_date");
        fields.add("study_uid");
        fields.add("reference_uid");
        SHORTLINK_FIELDS = Collections.unmodifiableSet(fields);
    }

    public Shortlinks(String apiKey, String secretKey, String apiUri) {
        super(apiKey, secretKey, apiUri);
    }
    
    private static boolean isBlank(String str) {
        if (str == null) {
            return true;
        }
        if (JavaVersionDetector.isJava11OrHigher()) {
            try {
                java.lang.reflect.Method isBlankMethod = String.class.getMethod("isBlank");
                return (Boolean) isBlankMethod.invoke(str);
            } catch (Exception e) {
                return str.trim().isEmpty();
            }
        } else {
            return str.trim().isEmpty();
        }
    }
    
    private static <T> List<T> createList(T element) {
        if (JavaVersionDetector.isJava9OrHigher()) {
            try {
                java.lang.reflect.Method ofMethod = java.util.List.class.getMethod("of", Object[].class);
                @SuppressWarnings("unchecked")
                List<T> result = (List<T>) ofMethod.invoke(null, (Object) new Object[]{element});
                return result;
            } catch (Exception e) {
                return Collections.singletonList(element);
            }
        } else {
            return Collections.singletonList(element);
        }
    }

    /**
     * Create a new shortlink
     *
     * @param longUrl The long URL to shorten (required)
     * @param name Optional name for the shortlink
     * @param alias Optional alias without spaces (max 30 chars)
     * @param status Optional status (ACTIVE or INACTIVE), defaults to ACTIVE
     * @return ApiResponse with the created shortlink
     */
    public ApiResponse<ShortlinkJsonObject> create(String longUrl, String name, String alias, String status) {
        ApiResponse<ShortlinkJsonObject> response;
        Map<String, Serializable> bodyParams = new LinkedHashMap<>();
        bodyParams.put("long_url", longUrl);

        String normalizedName = normalizeName(name);
        if (normalizedName != null) {
            bodyParams.put("name", normalizedName);
        }
        
        if (status != null && !status.isEmpty()) {
            bodyParams.put("status", status);
        } else {
            bodyParams.put("status", "ACTIVE");
        }

        if (alias != null && !alias.isEmpty()) {
            bodyParams.put("alias", normalizeAlias(alias));
        }

        try {
            response = doRequest("short_link", "post", null, bodyParams, false);
            if (response.isOk()) {
                String raw = response.getRawResponse();
                if (raw != null && !isBlank(raw)) {
                    ShortlinkJsonObject shortlink = parseShortlink(raw);
                    if (shortlink != null) {
                        response.setResponse(shortlink);
                    } else {
                        response.setErrorDescription("Unable to parse shortlink response");
                    }
                }
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
     * @param alias Optional alias without spaces (max 30 chars)
     * @return ApiResponse with the created shortlink
     */
    public ApiResponse<ShortlinkJsonObject> createWithAlias(String longUrl, String name, String alias) {
        return create(longUrl, name, alias, "ACTIVE");
    }

    /**
     * Create a new shortlink with default ACTIVE status
     *
     * @param longUrl The long URL to shorten (required)
     * @return ApiResponse with the created shortlink
     */
    public ApiResponse<ShortlinkJsonObject> create(String longUrl, String name) {
        return create(longUrl, name, null, "ACTIVE");
    }

    /**
     * Create a new shortlink with only the URL
     *
     * @param longUrl The long URL to shorten (required)
     * @return ApiResponse with the created shortlink
     */
    public ApiResponse<ShortlinkJsonObject> create(String longUrl) {
        return create(longUrl, null, null, "ACTIVE");
    }

    /**
     * Create a new shortlink with default ACTIVE status
     *
     * @param longUrl The long URL to shorten (required)
     * @param name Optional name for the shortlink
     * @param status Optional status (ACTIVE or INACTIVE), defaults to ACTIVE
     * @return ApiResponse with the created shortlink
     */
    public ApiResponse<ShortlinkJsonObject> createWithStatus(String longUrl, String name, String status) {
        return create(longUrl, name, null, status);
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
            response = doRequest("short_link/", "get", urlParams, null, true);
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
                            ShortlinkJsonObject single = parseShortlink(rawResponse);
                            shortlinkResponse = single != null ? createList(single) : Collections.emptyList();
                        }
                    } catch (Exception e) {
                        ShortlinkJsonObject single = parseShortlink(rawResponse);
                        if (single != null) {
                            shortlinkResponse = createList(single);
                        } else {
                            shortlinkResponse = JsonObjectCollection.fromJson(rawResponse, new TypeReference<List<ShortlinkJsonObject>>() {});
                        }
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
        try {
            ApiResponse<List<ShortlinkJsonObject>> listResponse = getList(null, null, 1, null, id);
            if (listResponse.isOk() && listResponse.getResponse() != null && !listResponse.getResponse().isEmpty()) {
                ShortlinkJsonObject shortlink = listResponse.getResponse().get(0);
                ApiResponse<ShortlinkJsonObject> success = new ApiResponse<>();
                success.setHttpCode(listResponse.getHttpCode());
                success.setHttpDescription(listResponse.getHttpDescription());
                success.setRawResponse(listResponse.getRawResponse());
                success.setResponse(shortlink);
                return success;
            }
            ApiResponse<ShortlinkJsonObject> notFound = new ApiResponse<>();
            notFound.setHttpCode(listResponse.getHttpCode() == 200 ? 404 : listResponse.getHttpCode());
            notFound.setHttpDescription(listResponse.getHttpDescription());
            notFound.setErrorCode(Optional.ofNullable(listResponse.getErrorCode()).orElse(404));
            notFound.setErrorDescription("Shortlink not found");
            return notFound;
        } catch (Exception e) {
            logger.error("Error getting shortlink by ID", e);
            ApiResponse<ShortlinkJsonObject> error = new ApiResponse<>();
            error.setErrorCode(-1);
            error.setErrorDescription(e.getMessage());
            return error;
        }
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
        if ("ACTIVE".equalsIgnoreCase(status)) {
            throw new IllegalArgumentException("Shortlinks cannot be reactivated; only INACTIVE updates are supported.");
        }

        try {
            Map<String, Serializable> filters = new LinkedHashMap<>();
            filters.put("id", id);
            Map<String, Serializable> body = new LinkedHashMap<>();
            body.put("status", status.toUpperCase(Locale.ROOT));
            String resource = "short_link/" + id + "/status";
            response = doRequest(resource, "put", filters, body, true);
            if (response.isOk()) {
                String raw = response.getRawResponse();
                if (raw != null && !isBlank(raw)) {
                    ShortlinkJsonObject shortlink = parseShortlink(raw);
                    if (shortlink != null) {
                        response.setResponse(shortlink);
                    } else {
                        response.setErrorDescription("Unable to parse shortlink response");
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error updating shortlink status", e);
            response = new ApiResponse<>();
            response.setErrorCode(-1);
            response.setErrorDescription(e.getMessage());
        }
        return response;
    }

    private String normalizeAlias(String alias) {
        String trimmed = alias.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Alias cannot be empty");
        }
        if (trimmed.length() > 30) {
            throw new IllegalArgumentException("Alias must be 30 characters or fewer");
        }
        if (trimmed.chars().anyMatch(Character::isWhitespace)) {
            throw new IllegalArgumentException("Alias cannot contain whitespace");
        }
        return trimmed;
    }

    private String normalizeName(String name) {
        if (name == null) {
            return null;
        }
        String trimmed = name.trim();
        if (trimmed.isEmpty()) {
            return null;
        }
        if (trimmed.length() > 50) {
            throw new IllegalArgumentException("Name must be 50 characters or fewer");
        }
        return trimmed;
    }

    private ShortlinkJsonObject parseShortlink(String raw) {
        try {
            if (raw == null || isBlank(raw)) {
                return null;
            }
            if (raw.trim().startsWith("{") && raw.contains("\"message\"")) {
                Map<String, Object> root = JsonObjectCollection.fromJson(raw, new TypeReference<Map<String, Object>>() {});
                Map<String, Object> candidate = extractShortlinkMap(root);
                if (candidate != null && !candidate.isEmpty()) {
                    Map<String, Object> filtered = new LinkedHashMap<>();
                    for (String field : SHORTLINK_FIELDS) {
                        Object value = candidate.get(field);
                        if (value != null) {
                            filtered.put(field, value);
                        }
                    }
                    if (filtered.isEmpty()) {
                        filtered.putAll(candidate);
                    }
                    String normalized = getSerializer().serialize(filtered);
                    return ShortlinkJsonObject.fromJson(normalized);
                }
            }
            Map<String, Object> root = JsonObjectCollection.fromJson(raw, new TypeReference<Map<String, Object>>() {});
            Map<String, Object> candidate = extractShortlinkMap(root);
            if (candidate == null || candidate.isEmpty()) {
                return ShortlinkJsonObject.fromJson(raw);
            }
            Map<String, Object> filtered = new LinkedHashMap<>();
            for (String field : SHORTLINK_FIELDS) {
                Object value = candidate.get(field);
                if (value != null) {
                    filtered.put(field, value);
                }
            }
            if (filtered.isEmpty()) {
                filtered.putAll(candidate);
            }
            String normalized = getSerializer().serialize(filtered);
            return ShortlinkJsonObject.fromJson(normalized);
        } catch (Exception exception) {
            logger.error("Failed to parse shortlink payload", exception);
            return null;
        }
    }

    private Map<String, Object> extractShortlinkMap(Map<String, Object> root) {
        if (root == null || root.isEmpty()) {
            return null;
        }
        Object data = root.get("data");
        if (data instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> dataMap = (Map<String, Object>) data;
            return dataMap;
        }
        if (data instanceof List) {
            List<?> list = (List<?>) data;
            if (!list.isEmpty() && list.get(0) instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> first = (Map<String, Object>) list.get(0);
                return first;
            }
        }
        Map<String, Object> filtered = new LinkedHashMap<>();
        for (String field : SHORTLINK_FIELDS) {
            Object value = root.get(field);
            if (value != null) {
                filtered.put(field, value);
            }
        }
        return filtered.isEmpty() ? root : filtered;
    }
}

