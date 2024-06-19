package com.cemonal.validationservice.domain.query;

import java.util.Map;

public record CaptchaValidationQuery(String provider, String token, Map<String, String> context) {}
