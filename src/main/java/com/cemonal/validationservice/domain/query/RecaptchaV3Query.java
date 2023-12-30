package com.cemonal.validationservice.domain.query;

/**
 * Represents a query for validating a reCAPTCHA v3 token.
 * This query object contains the token and the action provided by the client.
 * The action represents the specific user action being validated (e.g., login, signup).
 */
public record RecaptchaV3Query(String token, String action) {
}