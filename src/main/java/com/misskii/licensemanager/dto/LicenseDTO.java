package com.misskii.licensemanager.dto;

public class LicenseDTO {
    private String userEmail;
    private String licenseValue;

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
}
