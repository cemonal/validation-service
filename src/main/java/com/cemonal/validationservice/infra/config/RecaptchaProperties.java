package com.cemonal.validationservice.infra.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration class for reCAPTCHA properties.
 * This class holds the properties related to Google's reCAPTCHA service.
 * The properties are defined in the application's configuration file (e.g., application.yml)
 * and are prefixed with 'recaptcha'.
 */
@Configuration
@ConfigurationProperties(prefix = "recaptcha")
public class RecaptchaProperties {

    /**
     * The endpoint URL for reCAPTCHA v2 verification.
     */
    private String v2Endpoint;

    /**
     * The secret key for reCAPTCHA v2.
     */
    private String v2SecretKey;

    /**
     * The endpoint URL for reCAPTCHA v3 verification.
     */
    private String v3Endpoint;

    /**
     * The secret key for reCAPTCHA v3.
     */
    private String v3SecretKey;

    private List<String> allowedIps;


    /**
     * The score threshold for reCAPTCHA v3.
     * Requests with a score below this threshold are considered suspicious.
     */
    private double v3ScoreThreshold = 0.5; // Default value

    // Getters and Setters

    public String getV2Endpoint() {
        return v2Endpoint;
    }

    public void setV2Endpoint(String v2Endpoint) {
        this.v2Endpoint = v2Endpoint;
    }

    public String getV2SecretKey() {
        return v2SecretKey;
    }

    public void setV2SecretKey(String v2SecretKey) {
        this.v2SecretKey = v2SecretKey;
    }

    public String getV3Endpoint() {
        return v3Endpoint;
    }

    public void setV3Endpoint(String v3Endpoint) {
        this.v3Endpoint = v3Endpoint;
    }

    public String getV3SecretKey() {
        return v3SecretKey;
    }

    public void setV3SecretKey(String v3SecretKey) {
        this.v3SecretKey = v3SecretKey;
    }

    public double getV3ScoreThreshold() {
        return v3ScoreThreshold;
    }

    public void setV3ScoreThreshold(double v3ScoreThreshold) {
        this.v3ScoreThreshold = v3ScoreThreshold;
    }

    public List<String> getAllowedIps() {
        return allowedIps;
    }

    public void setAllowedIps(List<String> allowedIps) {
        this.allowedIps = allowedIps;
    }
}