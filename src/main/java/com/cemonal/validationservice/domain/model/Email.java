package com.cemonal.validationservice.domain.model;

public class Email {
    private final String address;
    private String status;
    private String statusCode;

    public Email(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getDomain() {
        return address.substring(address.lastIndexOf('@') + 1);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
}