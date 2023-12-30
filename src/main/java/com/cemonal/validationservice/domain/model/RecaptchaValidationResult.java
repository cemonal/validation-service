package com.cemonal.validationservice.domain.model;

/**
 * Represents the result of a reCAPTCHA validation in the domain layer.
 * This class holds the outcome of reCAPTCHA validation process.
 */
public class RecaptchaValidationResult {
    private final boolean success;
    private final String message;
    private final int status;

    public RecaptchaValidationResult(boolean success, String message, int status) {
        this.success = success;
        this.message = message;
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}