package com.example.apiusage.service;

import java.time.LocalDate;

public class ApiUsageEvent {

    private Long companyId;
    private Long userId;
    private String apiName;
    private LocalDate timestamp;

    public ApiUsageEvent() {
        // Default constructor
    }

    public ApiUsageEvent(Long companyId, Long userId, String apiName, LocalDate timestamp) {
        this.companyId = companyId;
        this.userId = userId;
        this.apiName = apiName;
        this.timestamp = timestamp;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }
}
