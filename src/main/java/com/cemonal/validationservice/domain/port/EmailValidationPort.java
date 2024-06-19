package com.cemonal.validationservice.domain.port;

public interface EmailValidationPort {
  String getValidationName();

  boolean validate(String email);
}
