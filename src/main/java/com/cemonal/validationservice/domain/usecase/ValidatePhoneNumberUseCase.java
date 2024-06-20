package com.cemonal.validationservice.domain.usecase;

import com.cemonal.validationservice.domain.model.PhoneNumberValidationResult;
import com.cemonal.validationservice.domain.port.PhoneNumberValidationPort;
import java.util.Collections;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ValidatePhoneNumberUseCase {
  private final PhoneNumberValidationPort phoneNumberValidationPort;

  public ValidatePhoneNumberUseCase(PhoneNumberValidationPort phoneNumberValidationPort) {
    this.phoneNumberValidationPort = phoneNumberValidationPort;
  }

  public PhoneNumberValidationResult execute(String phoneNumber, String regionIso2) {
    if (!StringUtils.hasText(phoneNumber)) {
      throw new IllegalArgumentException("Phone number must not be empty");
    }

    boolean isValid = phoneNumberValidationPort.validate(phoneNumber, regionIso2);
    return new PhoneNumberValidationResult(
        isValid,
        Collections.singletonList(isValid ? "Valid phone number" : "Invalid phone number"));
  }
}
