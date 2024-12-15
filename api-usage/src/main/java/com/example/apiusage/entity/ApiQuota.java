package com.example.apiusage.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "api_quota", schema = "interview")
public class ApiQuota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "daily_quota", nullable = false)
    private int dailyQuota;

    @Column(name = "used_count", nullable = false)
    private int usedCount;

    @Column(name = "api_name", nullable = false)
    private String apiName;

    @Column(name = "date", nullable = false)
    private LocalDate callDate;

    public int getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(int usedCount) {
        this.usedCount = usedCount;
    }

    public LocalDate getCallDate() {
        return callDate;
    }

    public void setCallDate(LocalDate callDate) {
        this.callDate = callDate;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getDailyQuota() {
        return dailyQuota;
    }

    public void setDailyQuota(int dailyQuota) {
        this.dailyQuota = dailyQuota;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
