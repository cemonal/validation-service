package com.cemonal.validationservice.domain.port;

import com.cemonal.validationservice.domain.model.RecaptchaV2Data;
import com.cemonal.validationservice.domain.model.RecaptchaValidationResult;

/**
 * Port interface for Recaptcha V2 validation.
 */
public interface RecaptchaV2ValidatorPort {
    /**
     * Validates a Recaptcha V2 token.
     *
     * @param request The Recaptcha V2 data containing the token to be validated.
     * @return RecaptchaValidationResult containing the validation results.
     */
    RecaptchaValidationResult validate(RecaptchaV2Data request);
}