package com.cemonal.validationservice.infra.adapter;

import com.cemonal.validationservice.domain.model.RecaptchaV3Data;
import com.cemonal.validationservice.domain.model.RecaptchaValidationResult;
import com.cemonal.validationservice.domain.port.RecaptchaV3ValidatorPort;
import com.cemonal.validationservice.infra.config.RecaptchaProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Adapter for validating Recaptcha V3 tokens.
 */
@Component
public class RecaptchaV3Adapter implements RecaptchaV3ValidatorPort {

    private final RecaptchaProperties recaptchaProperties;
    private final RestTemplate restTemplate;

    public RecaptchaV3Adapter(RecaptchaProperties recaptchaProperties, RestTemplate restTemplate) {
        this.recaptchaProperties = recaptchaProperties;
        this.restTemplate = restTemplate;
    }

    @Override
    public RecaptchaValidationResult validate(RecaptchaV3Data data) {
        try {
            String secretKey = recaptchaProperties.getV3SecretKey();
            String recaptchaEndpoint = recaptchaProperties.getV3Endpoint();
            String token = data.getToken();
            String action = data.getAction();

            Map<String, String> params = new HashMap<>();
            params.put("secret", secretKey);
            params.put("response", token);
            params.put("action", action);

            ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                    recaptchaEndpoint,
                    HttpMethod.POST,
                    new HttpEntity<>(params),
                    new ParameterizedTypeReference<>() {
                    });

            Map<String, Object> response = responseEntity.getBody();
            return parseResponse(response, recaptchaProperties.getV3ScoreThreshold());
        } catch (Exception e) {
            return new RecaptchaValidationResult(false, e.getMessage(), 500);
        }
    }

    private RecaptchaValidationResult parseResponse(Map<String, Object> response, double scoreThreshold) {
        if (response == null || !response.containsKey("success")) {
            return new RecaptchaValidationResult(false, "Failed to validate reCAPTCHA.", 500);
        }

        boolean success = (boolean) response.get("success");
        double score = (double) response.get("score");

        if (!success || score < scoreThreshold) {
            return new RecaptchaValidationResult(false, "reCAPTCHA validation failed.", 400);
        }

        return new RecaptchaValidationResult(true, "", 200);
    }
}