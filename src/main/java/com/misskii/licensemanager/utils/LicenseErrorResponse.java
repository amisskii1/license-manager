package com.misskii.licensemanager.utils;

public class LicenseErrorResponse {
    private String errorMessage;
    private long timestamp;

    public LicenseErrorResponse(String errorMessage, long timestamp) {
        this.errorMessage = errorMessage;
        this.timestamp = timestamp;
    }

    public LicenseErrorResponse() {

    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
