package com.cemonal.validationservice.domain.usecase;

import com.cemonal.validationservice.domain.model.RecaptchaV3Data;
import com.cemonal.validationservice.domain.model.RecaptchaValidationResult;
import com.cemonal.validationservice.domain.port.RecaptchaV3ValidatorPort;
import org.springframework.stereotype.Service;

/**
 * Use case for reCAPTCHA V3 validation.
 */
@Service
public class RecaptchaV3ValidationUseCase {

    private RecaptchaV3ValidatorPort recaptchaV3ValidatorPort;

    /**
     * Executes the reCAPTCHA V3 validation use case.
     *
     * @param recaptchaV3Data The data required for reCAPTCHA V3 validation.
     * @return The result of the reCAPTCHA V3 validation.
     */
    public RecaptchaValidationResult execute(RecaptchaV3Data recaptchaV3Data) {
        return recaptchaV3ValidatorPort.validate(recaptchaV3Data);
    }
}