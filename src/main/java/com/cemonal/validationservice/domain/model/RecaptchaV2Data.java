package com.cemonal.validationservice.domain.model;

/**
 * Represents the data model for reCAPTCHA v2 validation request in the domain layer.
 * Encapsulates the necessary details for processing reCAPTCHA v2 requests.
 */
public class RecaptchaV2Data {
    private final String token;

    public RecaptchaV2Data(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}