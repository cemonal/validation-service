package com.cemonal.validationservice.infra.controller.v1;

import com.cemonal.validationservice.domain.handler.query.CaptchaValidationQueryHandler;
import com.cemonal.validationservice.domain.model.CaptchaValidationResult;
import com.cemonal.validationservice.domain.query.CaptchaValidationQuery;
import com.cemonal.validationservice.infra.config.RecaptchaConfig;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/recaptcha")
public class RecaptchaController {

  private final CaptchaValidationQueryHandler captchaValidationQueryHandler;
  private final RecaptchaConfig recaptchaConfig;

  public RecaptchaController(CaptchaValidationQueryHandler captchaValidationQueryHandler, RecaptchaConfig recaptchaConfig) {
    this.captchaValidationQueryHandler = captchaValidationQueryHandler;
    this.recaptchaConfig = recaptchaConfig;
  }

  @PostMapping("/validate")
  public ResponseEntity<CaptchaValidationResult> validateCaptcha(
      @RequestBody CaptchaValidationQuery query, HttpServletRequest request) {
    if (isRequestFromAllowedIp(request)) {
      return new ResponseEntity<>(new CaptchaValidationResult(true, null), HttpStatus.OK);
    }

    CaptchaValidationResult result = captchaValidationQueryHandler.handle(query);
    return new ResponseEntity<>(result, result.success() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
  }

  private boolean isRequestFromAllowedIp(HttpServletRequest request) {
    String clientIp = getClientIp(request);
    List<String> allowedIps = recaptchaConfig.getAllowedIps();
    return allowedIps.contains(clientIp) || allowedIps.contains("*");
  }

  private String getClientIp(HttpServletRequest request) {
    String ip = request.getHeader("X-Forwarded-For");
    if (ip == null || ip.isEmpty()) {
      ip = request.getRemoteAddr();
    }
    return ip.split(",")[0].trim(); // Get the first IP in case of multiple IPs
  }
}
