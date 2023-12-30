package com.cemonal.validationservice.infra.controller.v1;

import com.cemonal.validationservice.domain.handler.query.RecaptchaV2QueryHandler;
import com.cemonal.validationservice.domain.handler.query.RecaptchaV3QueryHandler;
import com.cemonal.validationservice.domain.model.RecaptchaValidationResult;
import com.cemonal.validationservice.domain.query.RecaptchaV2Query;
import com.cemonal.validationservice.domain.query.RecaptchaV3Query;
import com.cemonal.validationservice.infra.config.RecaptchaProperties;
import com.cemonal.validationservice.infra.dto.RecaptchaResponse;
import com.cemonal.validationservice.infra.dto.RecaptchaV2Request;
import com.cemonal.validationservice.infra.dto.RecaptchaV3Request;
import com.cemonal.validationservice.infra.mapper.RecaptchaMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for handling reCAPTCHA validation requests.
 */
@RestController
@RequestMapping("/api/v1/recaptcha")
public class RecaptchaController {
    private final RecaptchaV2QueryHandler v2QueryHandler;
    private final RecaptchaV3QueryHandler v3QueryHandler;
    private final RecaptchaProperties recaptchaProperties;

    /**
     * Constructs a RecaptchaController with specified query handlers and properties.
     * @param v2QueryHandler Handler for Recaptcha V2 queries.
     * @param v3QueryHandler Handler for Recaptcha V3 queries.
     * @param recaptchaProperties Configuration properties for reCAPTCHA.
     */
    public RecaptchaController(RecaptchaV2QueryHandler v2QueryHandler, RecaptchaV3QueryHandler v3QueryHandler,RecaptchaProperties recaptchaProperties) {
        this.v2QueryHandler = v2QueryHandler;
        this.v3QueryHandler = v3QueryHandler;
        this.recaptchaProperties = recaptchaProperties;
    }

    /**
     * Endpoint for validating reCAPTCHA V2 tokens.
     * Accepts a request containing the reCAPTCHA V2 token and processes it for validation.
     * If the client IP is allowed (as per the configured allowed IPs), the request is automatically considered valid.
     *
     * @param request The RecaptchaV2Request DTO containing the reCAPTCHA token.
     * @param httpServletRequest The HTTP request object to extract client IP for additional checks.
     * @return ResponseEntity containing RecaptchaResponse with validation status, message, and HTTP status code.
     */
    @PostMapping("/validate-v2")
    public ResponseEntity<RecaptchaResponse> validateV2(@RequestBody RecaptchaV2Request request, HttpServletRequest httpServletRequest) {
        if (isRequestFromAllowedIp(httpServletRequest)) {
            return ResponseEntity.ok(new RecaptchaResponse(true, "IP allowed", 200));
        }

        RecaptchaValidationResult result = v2QueryHandler.handle(new RecaptchaV2Query(request.getToken()));
        RecaptchaResponse response = RecaptchaMapper.toDto(result);
        return ResponseEntity.status(result.getStatus()).body(response);
    }

    /**
     * Endpoint for validating reCAPTCHA V3 tokens.
     * Accepts a request containing the reCAPTCHA V3 token and action, and processes it for validation.
     * Similar to V2 validation, this also considers the client IP against the allowed IPs for automatic validation.
     *
     * @param request The RecaptchaV3Request DTO containing the reCAPTCHA token and action.
     * @param httpServletRequest The HTTP request object for client IP extraction.
     * @return ResponseEntity with the outcome of the validation in RecaptchaResponse format.
     */
    @PostMapping("/validate-v3")
    public ResponseEntity<RecaptchaResponse> validateV3(@RequestBody RecaptchaV3Request request, HttpServletRequest httpServletRequest) {
        if (isRequestFromAllowedIp(httpServletRequest)) {
            return ResponseEntity.ok(new RecaptchaResponse(true, "IP allowed", 200));
        }

        RecaptchaValidationResult result = v3QueryHandler.handle(new RecaptchaV3Query(request.getToken(), request.getAction()));
        RecaptchaResponse response = RecaptchaMapper.toDto(result);
        return ResponseEntity.status(result.getStatus()).body(response);
    }

    /**
     * Checks if the request originates from an IP address that's explicitly allowed.
     * Allowed IP addresses are defined in RecaptchaProperties.
     *
     * @param request HTTP request to extract client IP address from.
     * @return true if the IP is in the list of allowed IPs; false otherwise.
     */
    private boolean isRequestFromAllowedIp(HttpServletRequest request) {
        String clientIp = getClientIp(request);
        List<String> allowedIps = recaptchaProperties.getAllowedIps();
        return allowedIps.contains(clientIp) || allowedIps.contains("*");
    }

    /**
     * Extracts the client IP address from an HTTP request.
     * Accounts for 'X-Forwarded-For' header to handle proxy servers.
     *
     * @param request HTTP request to extract the IP address from.
     * @return The IP address of the client.
     */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        return ip.split(",")[0].trim(); // Get the first IP in case of multiple IPs
    }
}