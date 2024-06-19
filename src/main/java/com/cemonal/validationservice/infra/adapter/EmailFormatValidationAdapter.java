package com.cemonal.validationservice.infra.adapter;

import com.sanctionco.jmail.JMail;
import com.cemonal.validationservice.domain.port.EmailValidationPort;
import org.springframework.stereotype.Component;

@Component
public class EmailFormatValidationAdapter implements EmailValidationPort {
  @Override
  public String getValidationName() {
    return "E-mail format";
  }

  @Override
  public boolean validate(String email) {
    return JMail.isValid(email);
  }
}
