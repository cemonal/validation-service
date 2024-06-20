package com.cemonal.validationservice.infra.adapter;

import com.cemonal.validationservice.domain.port.PhoneNumberValidationPort;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.springframework.stereotype.Component;

@Component
public class PhoneNumberValidationAdapter implements PhoneNumberValidationPort {
  private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

  @Override
  public boolean validate(String phoneNumber, String regionIso2) {
    try {
      Phonenumber.PhoneNumber number = phoneNumberUtil.parse(phoneNumber, regionIso2);
      return phoneNumberUtil.isValidNumber(number);
    } catch (NumberParseException e) {
      return false;
    }
  }
}
