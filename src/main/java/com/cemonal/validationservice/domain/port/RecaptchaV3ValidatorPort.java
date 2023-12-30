package com.cemonal.validationservice.domain.port;

import com.cemonal.validationservice.domain.model.RecaptchaV3Data;
import com.cemonal.validationservice.domain.model.RecaptchaValidationResult;

/**
 * Port interface for Recaptcha V3 validation.
 */
public interface RecaptchaV3ValidatorPort {
    /**
     * Validates a Recaptcha V3 token.
     *
     * @param request The Recaptcha V3 data containing the token and action to be validated.
     * @return RecaptchaValidationResult containing the validation results.
     */
    RecaptchaValidationResult validate(RecaptchaV3Data request);
}