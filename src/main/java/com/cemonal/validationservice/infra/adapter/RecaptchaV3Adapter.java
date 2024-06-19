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

@Component("recaptchav3")
public class RecaptchaV3Adapter implements CaptchaValidationPort {

  private final RecaptchaConfig recaptchaConfig;
  private final RestTemplate restTemplate;
  private final InspectorLogPort inspectorLogPort;

  public RecaptchaV3Adapter(
      RecaptchaConfig recaptchaConfig,
      RestTemplate restTemplate,
      InspectorLogPort inspectorLogPort) {
    this.recaptchaConfig = recaptchaConfig;
    this.restTemplate = restTemplate;
    this.inspectorLogPort = inspectorLogPort;
  }

  @Override
  public CaptchaValidationResult validate(CaptchaToken token) {
    UUID requestId = UUID.randomUUID();
    String url = recaptchaConfig.getV3Endpoint();

    Map<String, String> params = createParams(token);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(params, headers);

    try {
      inspectorLogPort.logRequest(requestId, url, HttpMethod.POST, headers, params);
      ResponseEntity<Map<String, Object>> responseEntity =
          restTemplate.exchange(
              url, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<>() {});
      inspectorLogPort.logResponse(
          requestId,
          url,
          HttpMethod.POST,
          headers,
          responseEntity.getBody(),
          responseEntity.getStatusCode());
      return parseResponse(responseEntity.getBody(), recaptchaConfig.getV3ScoreThreshold());
    } catch (Exception e) {
      inspectorLogPort.logException(requestId, url, HttpMethod.POST, headers, e);
      return new CaptchaValidationResult(false, List.of("Exception occurred: " + e.getMessage()));
    }
  }

  private Map<String, String> createParams(CaptchaToken token) {
    Map<String, String> params = RecaptchaParam.getParamsWithDefaults();
    params.put(RecaptchaParam.SECRET.getParamName(), recaptchaConfig.getV3SecretKey());
    params.put(RecaptchaParam.RESPONSE.getParamName(), token.token());

    String action =
        token
            .context()
            .getOrDefault(
                RecaptchaParam.ACTION.getParamName(),
                RecaptchaParam.ACTION.getDefaultValue().orElse("default_action"));
    params.put(RecaptchaParam.ACTION.getParamName(), action);

    if (token.context().containsKey(RecaptchaParam.REMOTE_IP.getParamName())) {
      params.put(
          RecaptchaParam.REMOTE_IP.getParamName(),
          token.context().get(RecaptchaParam.REMOTE_IP.getParamName()));
    }

    return params;
  }

  private CaptchaValidationResult parseResponse(
      Map<String, Object> response, double scoreThreshold) {
    if (response == null || !Boolean.TRUE.equals(response.get("success"))) {
      return new CaptchaValidationResult(false, List.of("bad-request"));
    }

    List<String> messages = new ArrayList<>();
    boolean success = processErrors(response, messages);

    if (success) {
      double score = (double) response.get("score");
      if (score < scoreThreshold) {
        success = false;
        messages.add("Score below threshold");
      }
    }

    return new CaptchaValidationResult(success, messages);
  }

  private boolean processErrors(Map<String, Object> response, List<String> messages) {
    boolean success = Boolean.TRUE.equals(response.get("success"));
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
    return success;
  }
}
