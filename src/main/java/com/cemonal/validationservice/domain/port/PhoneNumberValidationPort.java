package com.cemonal.validationservice.domain.port;

public interface PhoneNumberValidationPort {
  boolean validate(String phoneNumber, String regionIso2);
}
