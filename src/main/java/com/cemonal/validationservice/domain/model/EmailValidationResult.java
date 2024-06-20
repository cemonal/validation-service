package com.cemonal.validationservice.domain.model;

import java.util.List;

public record EmailValidationResult(boolean success, List<String> messages) {}
