package com.cemonal.validationservice.infra.dto;

public class RecaptchaV3Request {
    private String token;
    private String action;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}