package com.cemonal.validationservice.infra.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum RecaptchaParam {
  SECRET("secret"),
  RESPONSE("response"),
  REMOTE_IP("remoteip"),
  ACTION("action", "default_action");

  private final String paramName;
  private final String defaultValue;

  RecaptchaParam(String paramName) {
    this(paramName, null);
  }

  RecaptchaParam(String paramName, String defaultValue) {
    this.paramName = paramName;
    this.defaultValue = defaultValue;
  }

  public String getParamName() {
    return paramName;
  }

  public Optional<String> getDefaultValue() {
    return Optional.ofNullable(defaultValue);
  }

  public static Map<String, String> getParamsWithDefaults() {
    Map<String, String> params = new HashMap<>();
    for (RecaptchaParam param : values()) {
      param.getDefaultValue().ifPresent(value -> params.put(param.getParamName(), value));
    }
    return params;
  }
}
