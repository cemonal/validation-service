package com.cemonal.validationservice.domain.model;

import java.util.Map;

public record CaptchaToken(String token, Map<String, String> context) {
}
