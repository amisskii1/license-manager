package com.misskii.licensemanager.dto;

import java.time.LocalDateTime;

public class LicenseDTO {
    private String userEmail;
    private String licenseValue;
    private String licenseStatus;
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

    public void setLicenseValue(String licenseValue) {
        this.licenseValue = licenseValue;
    }

    public String getLicenseStatus() {
        return licenseStatus;
    }

    public void setLicenseStatus(String licenseStatus) {
        this.licenseStatus = licenseStatus;
    }

    public LocalDateTime getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(LocalDateTime expiredDate) {
        this.expiredDate = expiredDate;
    }
}
