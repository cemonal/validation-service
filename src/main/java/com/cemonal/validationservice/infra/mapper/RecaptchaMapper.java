package com.cemonal.validationservice.infra.mapper;

import com.cemonal.validationservice.domain.model.RecaptchaValidationResult;
import com.cemonal.validationservice.infra.dto.RecaptchaResponse;

/**
 * Mapper for converting RecaptchaValidationResult to RecaptchaResponse DTO.
 */
public class RecaptchaMapper {

    public static RecaptchaResponse toDto(RecaptchaValidationResult validationResult) {
        return new RecaptchaResponse(validationResult.isSuccess(), validationResult.getMessage(), validationResult.getStatus());
    }
}