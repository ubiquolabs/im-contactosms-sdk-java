package com.interactuamovil.apps.contactosms.api.sdk;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interactuamovil.apps.contactosms.api.utils.ApiResponse;
import com.interactuamovil.apps.contactosms.api.utils.ErrorJsonResponse;
import com.interactuamovil.apps.contactosms.api.utils.ISerializer;
import com.interactuamovil.apps.contactosms.api.utils.JsonSerializer;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import java.util.Locale;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;


abstract class Request {

    private String apiUri;
    private String apiKey;
    private String apiSecretKey;
    
    private ISerializer serializer;
    
    private boolean certificatedValidationEnabled = Boolean.TRUE;

    protected Request(String _apiKey, String _apiSecretKey, String _apiUri) {
        apiKey = _apiKey;
        apiSecretKey = _apiSecretKey;
        apiUri = _apiUri;
        if (!apiUri.endsWith("/")) {
            apiUri = apiUri + "/";
        }
        
        serializer = JsonSerializer.getInstance();
    }

    protected <T> ApiResponse<T> doRequest(String url, String requestType, Map<String, Serializable> urlParams, Object bodyParams, Boolean addToQueryString) throws IOException, InvalidKeyException, NoSuchAlgorithmException, MalformedURLException, JsonMappingException, UnsupportedEncodingException, JsonGenerationException, ProtocolException {

        String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";

        SimpleDateFormat httpDateFormat = new SimpleDateFormat(PATTERN_RFC1123, Locale.ENGLISH);
        httpDateFormat.applyPattern(PATTERN_RFC1123);
        httpDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        String httpDate = httpDateFormat.format(new Date());

        
        String jsonText = getSerializer().serialize(bodyParams);
        if (jsonText == null) {
            jsonText = "";
        }

        String filters = toQueryString(urlParams);
        if (addToQueryString) {
            url += '?' + filters;
        }

        String auth = generateAuthString(requestType, httpDate, jsonText, filters);

        URL completeUrl = new URL(apiUri + url);

        return send(completeUrl, auth, httpDate, requestType, jsonText);

    }

    public <T> ApiResponse<T> send(URL url, String auth, String httpDate, String requestType, String bodyParams) throws IOException, ProtocolException {

        ApiResponse<T> response = new ApiResponse<T>();
        
        SSLContext sc =  getSSLContext();
        HttpURLConnection connection;
        if (url.getProtocol().equalsIgnoreCase("https")) {
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            connection = (HttpsURLConnection) url.openConnection();
        } else {
            connection = (HttpURLConnection) url.openConnection();
        }

        requestType = requestType.toUpperCase();

        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod(requestType);
        connection.setDoInput(true);
        if (!requestType.equals("GET") && !requestType.equals("DELETE")) {
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            connection.setDoOutput(true);

        }
        connection.setRequestProperty("Content-Length", "" + Integer.toString(bodyParams.getBytes().length));
        connection.setRequestProperty("Date", httpDate);
        connection.setRequestProperty("Authorization", auth);
        connection.setRequestProperty("Accept", "*/*");
        
        connection.setRequestProperty("X-IM-ORIGIN", "IM_SDK_JAVA");
        //connection.setRequestProperty("X-IM-USERNAME", "java");
        
        connection.setUseCaches(false);
        

        if (!requestType.equals("GET") && !requestType.equals("DELETE")) {
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(bodyParams);
            wr.flush();
            wr.close();
        }

        InputStream inputStream;
        Integer responseCode = connection.getResponseCode();

        if (responseCode != 200) {
            inputStream = connection.getErrorStream();
        } else {
            inputStream = connection.getInputStream();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String decodedString, resultString = "";
        while ((decodedString = in.readLine()) != null) {
            resultString += decodedString;
        }

        in.close();
        connection.disconnect();

        response.setHttpCode(responseCode);     
        response.setRawResponse(resultString);
        if (responseCode != 200) {            
            ErrorJsonResponse errorJson = ErrorJsonResponse.fromJson(resultString, ErrorJsonResponse.class);
            response.setErrorCode(errorJson.getCode());
            response.setErrorDescription(errorJson.getError());            
        }
        return response;
    }

    private String generateAuthString(String request, String httpDate, String jsonText, String filters) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {

        if (jsonText == null || jsonText.equals("null"))
            jsonText = "";
        String canonicalString;
        if (request.equals("get"))
            canonicalString = getApiKey() + httpDate + filters; // + jsonText;
        else
            canonicalString = getApiKey() + httpDate + filters + jsonText;

        Mac mac = Mac.getInstance("HmacSHA1");
        byte[] keyBytes = getApiSecretKey().getBytes("UTF8");
        SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");
        mac.init(signingKey);

        byte[] signBytes = mac.doFinal(canonicalString.getBytes("UTF8"));

        String b64Mac = Base64.encode(signBytes);

        return "IM " + getApiKey() + ":" + b64Mac;
    }

    private static String toQueryString(Map<String, Serializable> parameters) throws UnsupportedEncodingException {
        String queryString = "";
        Boolean firstParam = true;

        if (parameters != null)
            if (parameters.size() > 0) {                        
                ArrayList<String> sortedList = new ArrayList<String>(parameters.keySet());
                Collections.sort(sortedList);                
                for (String key : sortedList) {
                    if (!firstParam)
                        queryString += "&";
                    queryString += key + "=" + URLEncoder.encode(parameters.get(key).toString(), "UTF-8");
                    firstParam = false;
                }
            }

        return queryString;
    }

    public String getApiUri() {
        return apiUri;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getApiSecretKey() {
        return apiSecretKey;
    }

    private SSLContext getSSLContext() {

        TrustManager[] trustAllCerts = new TrustManager[] {
            new TrustAllTrustManager()
        };
        
        if (!isCertificatedValidationEnabled()) {
            HostnameVerifier hv = new HostnameVerifier()
            {
                public boolean verify(String urlHostName, SSLSession session)
                {
                    System.out.println("Warning: URL Host: " + urlHostName + " vs. "
                            + session.getPeerHost());
                    return true;
                }
            };
            HttpsURLConnection.setDefaultHostnameVerifier(hv);
        }

        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
        }
        catch (NoSuchAlgorithmException ignored) { }
        catch (KeyManagementException ignored) { }

        return sc;

    }

    /**
     * @return the serializer
     */
    public ISerializer getSerializer() {
        return serializer;
    }

    /**
     * @return the certificatedValidationEnabled
     */
    public boolean isCertificatedValidationEnabled() {
        return certificatedValidationEnabled;
    }

    /**
     * @param certificatedValidationEnabled the certificatedValidationEnabled to set
     */
    public void setCertificatedValidationEnabled(boolean certificatedValidationEnabled) {
        this.certificatedValidationEnabled = certificatedValidationEnabled;
    }
    
    private static class TrustAllTrustManager implements javax.net.ssl.TrustManager,
            javax.net.ssl.X509TrustManager
    {
        public java.security.cert.X509Certificate[] getAcceptedIssuers()
        {
            return null;
        }
 
        public boolean isServerTrusted(
                java.security.cert.X509Certificate[] certs)
        {
            return true;
        }
 
        public boolean isClientTrusted(
                java.security.cert.X509Certificate[] certs)
        {
            return true;
        }
 
        public void checkServerTrusted(
                java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException
        {            
        }
 
        public void checkClientTrusted(
                java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException
        {
        }
    }    
    
}
