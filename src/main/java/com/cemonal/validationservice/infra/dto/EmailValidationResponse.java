package com.cemonal.validationservice.infra.dto;

public class EmailValidationResponse {
    private final boolean status;
    private final String message;

    public EmailValidationResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}