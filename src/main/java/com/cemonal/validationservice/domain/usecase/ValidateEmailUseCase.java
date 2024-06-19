package com.cemonal.validationservice.domain.usecase;

import com.cemonal.validationservice.domain.model.EmailValidationResult;
import com.cemonal.validationservice.domain.port.EmailValidationPort;
import com.cemonal.validationservice.domain.query.EmailValidationQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ValidateEmailUseCase {
  private final List<EmailValidationPort> validationPorts;

  public ValidateEmailUseCase(List<EmailValidationPort> validationPorts) {
    this.validationPorts = validationPorts;
  }

  public EmailValidationResult execute(EmailValidationQuery query) {
    if (!StringUtils.hasText(query.email())) {
      throw new IllegalArgumentException("Email must not be empty");
    }

    List<String> errors =
        validationPorts.stream()
            .filter(port -> !port.validate(query.email()))
            .map(port -> port.getValidationName() + " validation failed")
            .collect(Collectors.toList());

    boolean success = errors.isEmpty();
    return new EmailValidationResult(success, errors);
  }
}
