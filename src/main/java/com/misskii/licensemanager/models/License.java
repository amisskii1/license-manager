package com.misskii.licensemanager.models;

import com.misskii.licensemanager.utils.LicenseKeyGenerator;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Licenses")
public class License {
    @Id
    @Column(name = "user_email")
    private String userEmail;
    @Column(name = "license_value")
    private String licenseValue;
    @Column(name = "license_status")
    private String licenseStatus;
    @Column(name = "trial_status")
    private String trialStatus;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "date_of_creation")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateOfCreation;
    @Column(name = "expired_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime expiredDate;

    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getLicenseValue() {
        return licenseValue;
    }

    public void setLicenseValue(String userEmail) {
        LicenseKeyGenerator keyGenerator = new LicenseKeyGenerator();
        try {
            this.licenseValue = keyGenerator.generateLicenseKey(userEmail);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getLicenseStatus() {
        return licenseStatus;
    }

    public void setLicenseStatus(String licenseStatus) {
        this.licenseStatus = licenseStatus;
    }

    public String getTrialStatus() {
        return trialStatus;
    }

    public void setTrialStatus(String trialStatus) {
        this.trialStatus = trialStatus;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public LocalDateTime getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(LocalDateTime expiredDate) {
        this.expiredDate = expiredDate;
    }
}
