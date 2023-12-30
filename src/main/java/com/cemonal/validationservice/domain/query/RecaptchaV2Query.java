package com.cemonal.validationservice.domain.query;

/**
 * Represents a query for validating a reCAPTCHA v2 token.
 * This query object contains the token provided by the client.
 */
public record RecaptchaV2Query(String token) {
}