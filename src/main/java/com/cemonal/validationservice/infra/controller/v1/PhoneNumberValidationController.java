package com.cemonal.validationservice.infra.controller.v1;

import com.cemonal.validationservice.domain.handler.query.PhoneNumberValidationQueryHandler;
import com.cemonal.validationservice.domain.model.PhoneNumberValidationResult;
import com.cemonal.validationservice.domain.query.PhoneNumberValidationQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/phone-number-validation")
public class PhoneNumberValidationController {

  private final PhoneNumberValidationQueryHandler phoneNumberValidationQueryHandler;

  public PhoneNumberValidationController(
      PhoneNumberValidationQueryHandler phoneNumberValidationQueryHandler) {
    this.phoneNumberValidationQueryHandler = phoneNumberValidationQueryHandler;
  }

  @PostMapping
  public ResponseEntity<PhoneNumberValidationResult> validatePhoneNumber(
      @RequestBody PhoneNumberValidationQuery query) {
    PhoneNumberValidationResult result = phoneNumberValidationQueryHandler.handle(query);
    HttpStatus status = result.success() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    return new ResponseEntity<>(result, status);
  }
}
