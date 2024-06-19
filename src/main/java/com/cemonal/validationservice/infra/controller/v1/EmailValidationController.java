package com.cemonal.validationservice.infra.controller.v1;

import com.cemonal.validationservice.domain.handler.query.EmailValidationQueryHandler;
import com.cemonal.validationservice.domain.model.EmailValidationResult;
import com.cemonal.validationservice.domain.query.EmailValidationQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/email-validation")
public class EmailValidationController {

  private final EmailValidationQueryHandler emailValidationQueryHandler;

  public EmailValidationController(EmailValidationQueryHandler emailValidationQueryHandler) {
    this.emailValidationQueryHandler = emailValidationQueryHandler;
  }

  @PostMapping
  public ResponseEntity<EmailValidationResult> validateEmail(
      @RequestBody EmailValidationQuery query) {
    EmailValidationResult result = emailValidationQueryHandler.handle(query);
    HttpStatus status = result.success() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    return new ResponseEntity<>(result, status);
  }
}
