/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interactuamovil.apps.contactosms.api.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author sergeiw
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorJsonResponse extends JsonObject {
    
    private Integer code;
    private String error;
    private String message;
    private Boolean success;

    public ErrorJsonResponse() {
    }

    public ErrorJsonResponse(Integer code, String error) {
        this.code = code;
        this.error = error;
    }
    
    public static ErrorJsonResponse create(Integer code, String error) {
        return new ErrorJsonResponse(code, error);
    }
    
    /**
     * @return the code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * @return the error
     */
    public String getError() {
        return error;
    }

    /**
     * @param error the error to set
     */
    public void setError(String error) {
        this.error = error;
    }
    
    /**
     * @return the message (alternative to error field)
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    /**
     * @return the success flag
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }
    
    /**
     * Gets the error message, checking both 'error' and 'message' fields
     * @return the error message
     */
    public String getErrorMessage() {
        if (error != null && !error.isEmpty()) {
            return error;
        }
        if (message != null && !message.isEmpty()) {
            return message;
        }
        return "Unknown error";
    }
}
