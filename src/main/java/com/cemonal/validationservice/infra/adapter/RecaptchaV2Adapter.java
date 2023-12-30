package com.cemonal.validationservice.infra.adapter;

import com.cemonal.validationservice.domain.model.RecaptchaV2Data;
import com.cemonal.validationservice.domain.model.RecaptchaValidationResult;
import com.cemonal.validationservice.domain.port.RecaptchaV2ValidatorPort;
import com.cemonal.validationservice.infra.config.RecaptchaProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Component
public class RecaptchaV2Adapter implements RecaptchaV2ValidatorPort {

    private final RestTemplate restTemplate;
    private final RecaptchaProperties recaptchaProperties;

    public RecaptchaV2Adapter(RestTemplate restTemplate, RecaptchaProperties recaptchaProperties) {
        this.restTemplate = restTemplate;
        this.recaptchaProperties = recaptchaProperties;
    }

    @Override
    public RecaptchaValidationResult validate(RecaptchaV2Data request) {
        URI uri = UriComponentsBuilder.fromHttpUrl(recaptchaProperties.getV2Endpoint())
                .queryParam("secret", recaptchaProperties.getV2SecretKey())
                .queryParam("response", request.getToken())
                .build().toUri();

        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<>() {
                }
        );

        Map<String, Object> response = responseEntity.getBody();

        return parseResponse(response);
    }

    private RecaptchaValidationResult parseResponse(Map<String, Object> response) {
        if (response == null || !response.containsKey("success")) {
            return new RecaptchaValidationResult(false, "Failed to validate reCAPTCHA.", 500);
        }

        boolean success = Boolean.TRUE.equals(response.get("success"));
        String message = success ? "" : "reCAPTCHA validation failed.";
        int status = success ? 200 : 400;

        return new RecaptchaValidationResult(success, message, status);
    }
}