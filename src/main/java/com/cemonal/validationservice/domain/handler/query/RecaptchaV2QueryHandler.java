package com.cemonal.validationservice.domain.handler.query;

import com.cemonal.validationservice.domain.model.RecaptchaV2Data;
import com.cemonal.validationservice.domain.model.RecaptchaValidationResult;
import com.cemonal.validationservice.domain.query.RecaptchaV2Query;
import com.cemonal.validationservice.domain.usecase.RecaptchaV2ValidationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Handler for processing RecaptchaV2Query.
 * Uses the RecaptchaV2ValidationUseCase to perform the reCAPTCHA V2 validation.
 */
@Component
public class RecaptchaV2QueryHandler {

    private final RecaptchaV2ValidationUseCase validationUseCase;

    @Autowired
    public RecaptchaV2QueryHandler(RecaptchaV2ValidationUseCase validationUseCase) {
        this.validationUseCase = validationUseCase;
    }

    /**
     * Handles the given RecaptchaV2Query.
     *
     * @param query The query containing the token to be validated.
     * @return The result of the reCAPTCHA V2 validation.
     */
    public RecaptchaValidationResult handle(RecaptchaV2Query query) {
        RecaptchaV2Data data = new RecaptchaV2Data(query.token());
        return validationUseCase.execute(data);
    }
}
