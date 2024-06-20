package com.cemonal.validationservice.domain.model;

import java.util.List;

public record PhoneNumberValidationResult(boolean success, List<String> messages) {}
