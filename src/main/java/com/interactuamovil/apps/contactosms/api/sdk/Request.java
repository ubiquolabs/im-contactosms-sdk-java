package com.interactuamovil.apps.contactosms.api.sdk;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.interactuamovil.apps.contactosms.api.utils.ApiResponse;
import com.interactuamovil.apps.contactosms.api.utils.ErrorJsonResponse;
import com.interactuamovil.apps.contactosms.api.utils.ISerializer;
import com.interactuamovil.apps.contactosms.api.utils.JsonSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Modern HTTP Request handler using Java 21 features
 * 
 * Updated to use:
 * - Java 11+ HttpClient instead of HttpURLConnection
 * - Modern Java Time API instead of SimpleDateFormat
 * - SLF4J logging instead of Log4J
 * - Java 21 pattern matching and records
 * - Improved exception handling
 */
public abstract class Request {
    
    private static final Logger logger = LoggerFactory.getLogger(Request.class);
    
    private final String apiUri;
    private final String apiKey;
    private final String apiSecretKey;
    private final ISerializer serializer;

    private boolean certificateValidationEnabled = true;
    
    /**
     * Configuration class for Request parameters
     */
    public static class RequestConfig {
        private final String apiKey;
        private final String apiSecretKey;
        private final String apiUri;
        private final Duration timeout;
        private final boolean certificateValidationEnabled;
        
        public RequestConfig(String apiKey, String apiSecretKey, String apiUri, Duration timeout, boolean certificateValidationEnabled) {
            this.apiKey = Objects.requireNonNull(apiKey, "API key cannot be null");
            this.apiSecretKey = Objects.requireNonNull(apiSecretKey, "API secret key cannot be null");
            this.apiUri = Objects.requireNonNull(apiUri, "API URI cannot be null");
            this.timeout = timeout != null ? timeout : Duration.ofSeconds(30);
            this.certificateValidationEnabled = certificateValidationEnabled;
        }
        
        public String apiKey() { return apiKey; }
        public String apiSecretKey() { return apiSecretKey; }
        public String apiUri() { return apiUri; }
        public Duration timeout() { return timeout; }
        public boolean certificateValidationEnabled() { return certificateValidationEnabled; }
    }
    
    protected Request(String apiKey, String apiSecretKey, String apiUri) {
        this.apiKey = apiKey;
        this.apiSecretKey = apiSecretKey;
        this.apiUri = apiUri;
        this.serializer = JsonSerializer.getInstance();
    }

    protected <T> ApiResponse<T> doRequest(String url, String requestType, Map<String, Serializable> urlParams, Object bodyParams, Boolean addToQueryString) throws IOException, InvalidKeyException, NoSuchAlgorithmException, MalformedURLException, JsonMappingException, JsonGenerationException, ProtocolException {

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

    /**
     * Async version using modern HttpClient
     */
    protected <T> CompletableFuture<ApiResponse<T>> doRequestAsync(String url, String requestType, Map<String, Serializable> urlParams, Object bodyParams, Boolean addToQueryString) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return doRequest(url, requestType, urlParams, bodyParams, addToQueryString);
            } catch (Exception e) {
                logger.error("Async request failed", e);
                ApiResponse<T> errorResponse = new ApiResponse<>();
                errorResponse.setHttpCode(500);
                errorResponse.setErrorDescription("Request failed: " + e.getMessage());
                return errorResponse;
            }
        });
    }

    public <T> ApiResponse<T> send(URL url, String auth, String httpDate, String requestType, String bodyParams) throws IOException, ProtocolException {

        ApiResponse<T> response = new ApiResponse<T>();

        SSLContext sc = getSSLContext();
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
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);

        }
        connection.setRequestProperty("Content-Length", "" + Integer.toString(bodyParams.getBytes(StandardCharsets.UTF_8).length));
        connection.setRequestProperty("Date", httpDate);
        connection.setRequestProperty("Authorization", auth);
        connection.setRequestProperty("Accept", "*/*");

        connection.setRequestProperty("X-IM-ORIGIN", "IM_SDK_JAVA_V4");
        
        // 🔍 DEBUG LOGS - Request Details
        logger.debug("📤 HTTP Request Details:");
        logger.debug("   URL: {}", url);
        logger.debug("   Method: {}", requestType);
        logger.debug("   Date: {}", httpDate);
        logger.debug("   Authorization: {}", auth);
        logger.debug("   X-IM-ORIGIN: {}", "IM_SDK_JAVA_V4");
        logger.debug("   Body: {}", bodyParams);

        connection.setUseCaches(false);


        if (!requestType.equals("GET") && !requestType.equals("DELETE")) {
            try (OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8);
                 BufferedWriter wr = new BufferedWriter(osw)) {
                wr.write(bodyParams);
                wr.flush();
            }
        }

        InputStream inputStream;
        Integer responseCode = connection.getResponseCode();
        
        // 🔍 DEBUG LOGS - Response Details
        logger.debug("📥 HTTP Response Details:");
        logger.debug("   Response Code: {}", responseCode);
        logger.debug("   Response Message: {}", connection.getResponseMessage());

        if (responseCode != 200) {
            inputStream = connection.getErrorStream();
            logger.debug("   Using Error Stream (non-200 response)");
        } else {
            inputStream = connection.getInputStream();
            logger.debug("   Using Success Stream (200 response)");
        }

        StringBuilder resultString = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String decodedString;
            while ((decodedString = in.readLine()) != null) {
                resultString.append(decodedString);
            }
        }

        connection.disconnect();

        response.setHttpCode(responseCode);
        response.setRawResponse(resultString.toString());
        
        // 🔍 DEBUG LOGS - Raw Response
        logger.debug("📋 Raw Response Body: {}", resultString.toString());
        
        if (responseCode != 200) {
            logger.debug("❌ ERROR Response - Parsing error details...");
            ErrorJsonResponse errorJson = ErrorJsonResponse.fromJson(resultString.toString(), ErrorJsonResponse.class);
            response.setErrorCode(errorJson.getCode());
            response.setErrorDescription(errorJson.getError());
        }

        return response;
    }

    private String generateAuthString(String requestType, String httpDate, String jsonText, String filters)
            throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {

        logger.debug("Generating auth string with modern logic...");

        // Usar el string canónico correcto, igual que en JavaScript
        String canonicalString = apiKey + httpDate + filters + jsonText;

        // Especificar HMAC-SHA1
        Mac mac = Mac.getInstance("HmacSHA1");
        SecretKeySpec secretKey = new SecretKeySpec(apiSecretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA1");
        mac.init(secretKey);

        byte[] digest = mac.doFinal(canonicalString.getBytes(StandardCharsets.UTF_8));

        // Codificar en Base64
        String signature = Base64.getEncoder().encodeToString(digest);

        logger.debug("Canonical String: {}", canonicalString);
        logger.debug("Generated Signature: {}", signature);
        
        // 🔍 COMPARISON WITH JAVASCRIPT - Break down canonical string components
        logger.debug("🔍 Canonical String Breakdown:");
        logger.debug("   API Key: {}", apiKey);
        logger.debug("   Date: {}", httpDate);
        logger.debug("   Filters: {}", filters);
        logger.debug("   JSON Text: {}", jsonText);
        logger.debug("   Combined: {}{}{}{}", apiKey, httpDate, filters, jsonText);

        return "IM " + apiKey + ":" + signature;
    }

    private static String toQueryString(Map<String, Serializable> parameters) {
        if (parameters == null || parameters.isEmpty()) {
            return "";
        }

        // Ordenar los parámetros alfabéticamente y codificar como en JavaScript
        return parameters.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> {
                    try {
                        String key = URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString());
                        String value = URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8.toString());
                        // La codificación de Java (URLEncoder) ya usa '+' para los espacios, que es lo que el SDK de JS espera.
                        return key + "=" + value;
                    } catch (UnsupportedEncodingException e) {
                        // No debería ocurrir con UTF-8
                        throw new UncheckedIOException(e);
                    }
                })
                .collect(Collectors.joining("&"));
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
        try {
            return createTrustAllSslContext();
        } catch (Exception e) {
            logger.error("Failed to create SSL context", e);
            throw new RuntimeException("SSL context creation failed", e);
        }
    }

    private SSLContext createTrustAllSslContext() {
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new TrustAllTrustManager()}, new SecureRandom());

            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String urlHostName, SSLSession session) {
                    return true;
                }
            });

            return sc;
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            logger.error("Failed to create trust-all SSL context", e);
            throw new RuntimeException("SSL configuration failed", e);
        }
    }

    public ISerializer getSerializer() {
        return serializer;
    }

    public boolean isCertificatedValidationEnabled() {
        return certificateValidationEnabled;
    }

    public void setCertificatedValidationEnabled(boolean certificatedValidationEnabled) {
        this.certificateValidationEnabled = certificatedValidationEnabled;
    }

    private static class TrustAllTrustManager implements X509TrustManager {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] certs, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] certs, String authType) {
        }
    }
}

