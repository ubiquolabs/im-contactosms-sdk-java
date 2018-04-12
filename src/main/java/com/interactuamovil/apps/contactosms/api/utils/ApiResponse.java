/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interactuamovil.apps.contactosms.api.utils;

/**
 *
 * @author sergeiw
 */
public class ApiResponse<T> {
    
    private int httpCode;
    private String httpDescription;
    private Integer errorCode;
    private String errorDescription;
    
    private String rawResponse;
    private T response;

    public ApiResponse() {
        httpCode = 200;
        httpDescription = "OK";
        
    }
    
    public boolean isOk() {
        return getHttpCode() == 200;
    }

    /**
     * @return the httpCode
     */
    public int getHttpCode() {
        return httpCode;
    }

    /**
     * @param httpCode the httpCode to set
     */
    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    /**
     * @return the httpDescription
     */
    public String getHttpDescription() {
        return httpDescription;
    }

    /**
     * @param httpDescription the httpDescription to set
     */
    public void setHttpDescription(String httpDescription) {
        this.httpDescription = httpDescription;
    }

    /**
     * @return the errorCode
     */
    public Integer getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return the errorDescription
     */
    public String getErrorDescription() {
        return errorDescription;
    }

    /**
     * @param errorDescription the errorDescription to set
     */
    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    /**
     * @return the rawResponse
     */
    public String getRawResponse() {
        return rawResponse;
    }

    /**
     * @param rawResponse the rawResponse to set
     */
    public void setRawResponse(String rawResponse) {
        this.rawResponse = rawResponse;
    }

    /**
     * @return the response
     */
    public T getResponse() {
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(T response) {
        this.response = response;
    }
    
    
    
}
