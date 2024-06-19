package com.cemonal.validationservice.domain.handler.query;

import com.cemonal.validationservice.domain.model.EmailValidationResult;
import com.cemonal.validationservice.domain.port.QueryHandler;
import com.cemonal.validationservice.domain.query.EmailValidationQuery;
import com.cemonal.validationservice.domain.usecase.ValidateEmailUseCase;
import org.springframework.stereotype.Component;

@Component
public class EmailValidationQueryHandler
    implements QueryHandler<EmailValidationQuery, EmailValidationResult> {
  private final ValidateEmailUseCase validateEmailUseCase;

  public EmailValidationQueryHandler(ValidateEmailUseCase validateEmailUseCase) {
    this.validateEmailUseCase = validateEmailUseCase;
  }

  @Override
  public EmailValidationResult handle(EmailValidationQuery query) {
    return validateEmailUseCase.execute(query);
  }
}
