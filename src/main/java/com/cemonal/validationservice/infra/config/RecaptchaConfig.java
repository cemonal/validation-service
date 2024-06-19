package com.cemonal.validationservice.infra.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "recaptcha")
public class RecaptchaConfig {

  private String v2Endpoint;

  private String v2SecretKey;

  private String v3Endpoint;

  private String v3SecretKey;

  private List<String> allowedIps;

  private double v3ScoreThreshold = 0.5; // Default value

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
