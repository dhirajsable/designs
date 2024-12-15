package com.example.apiusage.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "api_usage", schema = "interview")
public class ApiUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "api_name", nullable = false)
    private String apiName;

    @Column(name = "date", nullable = false)
    private LocalDate callDate;

    @Column(name = "usage_count", nullable = false)
    private int usageCount;

    public ApiUsage(
            User user, Company company, String apiName, LocalDate callDate, int usageCount) {
        this.apiName = apiName;
        this.callDate = callDate;
        this.usageCount = usageCount;
        this.user = user;
        this.company = company;
    }

    public ApiUsage() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public LocalDate getCallDate() {
        return callDate;
    }

    public void setCallDate(LocalDate callDate) {
        this.callDate = callDate;
    }

    public int getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(int callCount) {
        this.usageCount = callCount;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Custom setter for userId
    public void setUserId(Long userId) {
        if (userId == null) {
            this.user = null;
        } else {
            User user = new User();
            user.setId(userId);
            this.user = user;
        }
    }

    public void setCompanyId(Long companyId) {
        if (companyId == null) {
            this.user = null;
        } else {
            Company company = new Company();
            company.setId(companyId);
            this.company = company;
        }
    }
}
