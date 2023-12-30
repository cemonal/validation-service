package com.cemonal.validationservice.domain.handler.query;

import com.cemonal.validationservice.domain.model.RecaptchaV3Data;
import com.cemonal.validationservice.domain.model.RecaptchaValidationResult;
import com.cemonal.validationservice.domain.query.RecaptchaV3Query;
import com.cemonal.validationservice.domain.usecase.RecaptchaV3ValidationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Handler for processing RecaptchaV3Query.
 * Uses the RecaptchaV3ValidationUseCase to perform the reCAPTCHA V3 validation.
 */
@Component
public class RecaptchaV3QueryHandler {

    private final RecaptchaV3ValidationUseCase validationUseCase;

    @Autowired
    public RecaptchaV3QueryHandler(RecaptchaV3ValidationUseCase validationUseCase) {
        this.validationUseCase = validationUseCase;
    }

    /**
     * Handles the given RecaptchaV3Query.
     *
     * @param query The query containing the token and action to be validated.
     * @return The result of the reCAPTCHA V3 validation.
     */
    public RecaptchaValidationResult handle(RecaptchaV3Query query) {
        RecaptchaV3Data data = new RecaptchaV3Data(query.token(), query.action());
        return validationUseCase.execute(data);
    }
}
