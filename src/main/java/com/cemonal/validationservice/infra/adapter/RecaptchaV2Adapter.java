package com.cemonal.validationservice.infra.adapter;

import com.cemonal.validationservice.domain.model.CaptchaToken;
import com.cemonal.validationservice.domain.model.CaptchaValidationResult;
import com.cemonal.validationservice.domain.port.CaptchaValidationPort;
import com.cemonal.validationservice.domain.port.InspectorLogPort;
import com.cemonal.validationservice.infra.config.RecaptchaConfig;
import com.cemonal.validationservice.infra.enums.RecaptchaParam;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component("recaptchav2")
public class RecaptchaV2Adapter implements CaptchaValidationPort {

  private final RestTemplate restTemplate;
  private final RecaptchaConfig recaptchaConfig;
  private final InspectorLogPort inspectorLogPort;

  public RecaptchaV2Adapter(
      RestTemplate restTemplate,
      RecaptchaConfig recaptchaConfig,
      InspectorLogPort inspectorLogPort) {
    this.restTemplate = restTemplate;
    this.recaptchaConfig = recaptchaConfig;
    this.inspectorLogPort = inspectorLogPort;
  }

  @Override
  public CaptchaValidationResult validate(CaptchaToken token) {
    UUID requestId = UUID.randomUUID();
    String url = recaptchaConfig.getV2Endpoint();
    HttpMethod method = HttpMethod.POST;

    Map<String, String> params = new HashMap<>();
    params.put(RecaptchaParam.SECRET.getParamName(), recaptchaConfig.getV2SecretKey());
    params.put(RecaptchaParam.RESPONSE.getParamName(), token.token());

    if (token.context().containsKey(RecaptchaParam.REMOTE_IP.getParamName())) {
      params.put(
          RecaptchaParam.REMOTE_IP.getParamName(),
          token.context().get(RecaptchaParam.REMOTE_IP.getParamName()));
    }

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(params, headers);

    try {
      inspectorLogPort.logRequest(requestId, url, method, headers, params);
      ResponseEntity<Map<String, Object>> responseEntity =
          restTemplate.exchange(url, method, requestEntity, new ParameterizedTypeReference<>() {});
      inspectorLogPort.logResponse(
          requestId,
          url,
          method,
          headers,
          responseEntity.getBody(),
          responseEntity.getStatusCode());
      return parseResponse(responseEntity.getBody());
    } catch (Exception e) {
      inspectorLogPort.logException(requestId, url, method, headers, e);
      return new CaptchaValidationResult(false, List.of("Exception occurred: " + e.getMessage()));
    }
  }

  private CaptchaValidationResult parseResponse(Map<String, Object> response) {
    if (response == null || !response.containsKey("success")) {
      return new CaptchaValidationResult(false, List.of("bad-request"));
    }

    boolean success = Boolean.TRUE.equals(response.get("success"));
    List<String> messages = new ArrayList<>();

    if (!success && response.containsKey("error-codes")) {
      Object errorCodes = response.get("error-codes");
      if (errorCodes instanceof List<?>) {
        for (Object errorCode : (List<?>) errorCodes) {
          if (errorCode instanceof String) {
            messages.add((String) errorCode);
          }
        }
      }
    }

    return new CaptchaValidationResult(success, messages);
  }
}
