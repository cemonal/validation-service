package com.cemonal.validationservice.domain.handler.query;

import com.cemonal.validationservice.domain.model.PhoneNumberValidationResult;
import com.cemonal.validationservice.domain.port.QueryHandler;
import com.cemonal.validationservice.domain.query.PhoneNumberValidationQuery;
import com.cemonal.validationservice.domain.usecase.ValidatePhoneNumberUseCase;
import org.springframework.stereotype.Component;

@Component
public class PhoneNumberValidationQueryHandler
    implements QueryHandler<PhoneNumberValidationQuery, PhoneNumberValidationResult> {
  private final ValidatePhoneNumberUseCase validatePhoneNumberUseCase;

  public PhoneNumberValidationQueryHandler(ValidatePhoneNumberUseCase validatePhoneNumberUseCase) {
    this.validatePhoneNumberUseCase = validatePhoneNumberUseCase;
  }

  public PhoneNumberValidationResult handle(PhoneNumberValidationQuery query) {
    return validatePhoneNumberUseCase.execute(query.phoneNumber(), query.regionIso2());
  }
}
