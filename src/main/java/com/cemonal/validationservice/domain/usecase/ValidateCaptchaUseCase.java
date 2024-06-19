package com.cemonal.validationservice.domain.usecase;

import com.cemonal.validationservice.domain.model.CaptchaToken;
import com.cemonal.validationservice.domain.model.CaptchaValidationResult;
import com.cemonal.validationservice.domain.port.CaptchaValidationPort;
import com.cemonal.validationservice.domain.query.CaptchaValidationQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class ValidateCaptchaUseCase {

  private final Map<String, CaptchaValidationPort> captchaValidationPortMap;

  public ValidateCaptchaUseCase(Map<String, CaptchaValidationPort> captchaValidationPortMap) {
    this.captchaValidationPortMap = captchaValidationPortMap;
  }

  public CaptchaValidationResult execute(CaptchaValidationQuery query) {
    if (!StringUtils.hasText(query.provider())) {
      throw new IllegalArgumentException("Provider must not be empty");
    }
    if (!StringUtils.hasText(query.token())) {
      throw new IllegalArgumentException("Token must not be empty");
    }

    CaptchaValidationPort captchaValidationPort =
        captchaValidationPortMap.get(query.provider().toLowerCase());
    if (captchaValidationPort == null) {
      throw new IllegalArgumentException("Unsupported CAPTCHA provider: " + query.provider());
    }

    CaptchaToken captchaToken = new CaptchaToken(query.token(), query.context());
    return captchaValidationPort.validate(captchaToken);
  }
}
