package com.cemonal.validationservice.domain.model;

/**
 * Represents the data model for reCAPTCHA v3 validation request in the domain layer.
 * Encapsulates the necessary details for processing reCAPTCHA v3 requests.
 */
public class RecaptchaV3Data {
    private final String token;
    private final String action;

    public RecaptchaV3Data(String token, String action) {
        this.token = token;
        this.action = action;
    }

    public String getToken() {
        return token;
    }

    public String getAction() {
        return action;
    }
}