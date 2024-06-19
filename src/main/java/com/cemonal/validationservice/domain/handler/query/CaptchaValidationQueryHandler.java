package com.cemonal.validationservice.domain.handler.query;

import com.cemonal.validationservice.domain.model.CaptchaValidationResult;
import com.cemonal.validationservice.domain.query.CaptchaValidationQuery;
import com.cemonal.validationservice.domain.usecase.ValidateCaptchaUseCase;
import org.springframework.stereotype.Component;

@Component
public class CaptchaValidationQueryHandler {

  private final ValidateCaptchaUseCase validationUseCase;

  public CaptchaValidationQueryHandler(ValidateCaptchaUseCase validationUseCase) {
    this.validationUseCase = validationUseCase;
  }

  public CaptchaValidationResult handle(CaptchaValidationQuery query) {
    return validationUseCase.execute(query);
  }
}
