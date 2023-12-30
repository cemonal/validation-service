package com.cemonal.validationservice.infra.dto;

public class RecaptchaV2Request {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}