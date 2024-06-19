package com.cemonal.validationservice.domain.port;

import com.cemonal.validationservice.domain.model.CaptchaToken;
import com.cemonal.validationservice.domain.model.CaptchaValidationResult;

public interface CaptchaValidationPort {
  CaptchaValidationResult validate(CaptchaToken token);
}
