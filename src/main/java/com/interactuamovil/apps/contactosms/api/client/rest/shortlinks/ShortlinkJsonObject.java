package com.interactuamovil.apps.contactosms.api.client.rest.shortlinks;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.interactuamovil.apps.contactosms.api.utils.JsonObject;

import java.io.IOException;

public class ShortlinkJsonObject extends JsonObject {

    @JsonProperty(value = "_id")
    private String id;
    
    @JsonProperty(value = "url_id")
    private String urlId;
    
    @JsonProperty(value = "account_uid")
    private String accountUid;
    
    @JsonProperty(value = "name")
    private String name;
    
    @JsonProperty(value = "status")
    private String status;
    
    @JsonProperty(value = "base_url")
    private String baseUrl;
    
    @JsonProperty(value = "short_url")
    private String shortUrl;
    
    @JsonProperty(value = "long_url")
    private String longUrl;
    
    @JsonProperty(value = "url")
    private String url;
    
    @JsonProperty(value = "visits")
    private Integer visits;
    
    @JsonProperty(value = "unique_visits")
    private Integer uniqueVisits;
    
    @JsonProperty(value = "preview_visits")
    private Integer previewVisits;
    
    @JsonProperty(value = "created_by")
    private String createdBy;
    
    @JsonProperty(value = "created_on")
    private Long createdOn;
    
    @JsonProperty(value = "reference_type")
    private String referenceType;
    
    @JsonProperty(value = "expiration")
    private Boolean expiration;
    
    @JsonProperty(value = "expiration_date")
    private Long expirationDate;
    
    @JsonProperty(value = "study_uid")
    private String studyUid;
    
    @JsonProperty(value = "reference_uid")
    private String referenceUid;

    public static ShortlinkJsonObject fromJson(String json) throws IOException {
        return JsonObject.fromJson(json, ShortlinkJsonObject.class);
    }

    public ShortlinkJsonObject() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    public String getAccountUid() {
        return accountUid;
    }

    public void setAccountUid(String accountUid) {
        this.accountUid = accountUid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getLongUrl() {
        return longUrl != null ? longUrl : url;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getUrl() {
        return url != null ? url : longUrl;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getVisits() {
        return visits != null ? visits : 0;
    }

    public void setVisits(Integer visits) {
        this.visits = visits;
    }

    public Integer getUniqueVisits() {
        return uniqueVisits != null ? uniqueVisits : 0;
    }

    public void setUniqueVisits(Integer uniqueVisits) {
        this.uniqueVisits = uniqueVisits;
    }

    public Integer getPreviewVisits() {
        return previewVisits != null ? previewVisits : 0;
    }

    public void setPreviewVisits(Integer previewVisits) {
        this.previewVisits = previewVisits;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Long createdOn) {
        this.createdOn = createdOn;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public Boolean getExpiration() {
        return expiration != null ? expiration : false;
    }

    public void setExpiration(Boolean expiration) {
        this.expiration = expiration;
    }

    public Long getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Long expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getStudyUid() {
        return studyUid;
    }

    public void setStudyUid(String studyUid) {
        this.studyUid = studyUid;
    }

    public String getReferenceUid() {
        return referenceUid;
    }

    public void setReferenceUid(String referenceUid) {
        this.referenceUid = referenceUid;
    }
}

