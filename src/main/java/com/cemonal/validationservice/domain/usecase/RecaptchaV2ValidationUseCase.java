package com.cemonal.validationservice.domain.usecase;

import com.cemonal.validationservice.domain.model.RecaptchaV2Data;
import com.cemonal.validationservice.domain.model.RecaptchaValidationResult;
import com.cemonal.validationservice.domain.port.RecaptchaV2ValidatorPort;
import org.springframework.stereotype.Service;

/**
 * Use case for reCAPTCHA V2 validation.
 */
@Service
public class RecaptchaV2ValidationUseCase {

    private RecaptchaV2ValidatorPort recaptchaV2ValidatorPort;

    /**
     * Executes the reCAPTCHA V2 validation use case.
     *
     * @param recaptchaV2Data The data required for reCAPTCHA V2 validation.
     * @return The result of the reCAPTCHA V2 validation.
     */
    public RecaptchaValidationResult execute(RecaptchaV2Data recaptchaV2Data) {
        return recaptchaV2ValidatorPort.validate(recaptchaV2Data);
    }
}