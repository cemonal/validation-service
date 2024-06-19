package com.cemonal.validationservice.domain.model;

import java.util.List;

public record CaptchaValidationResult(boolean success, List<String> messages) {}
