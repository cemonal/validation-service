package com.cemonal.validationservice.domain.config;

import com.cemonal.validationservice.domain.port.CaptchaValidationPort;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class CaptchaConfig {
  private final ApplicationContext applicationContext;

  public CaptchaConfig(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  @Bean
  public Map<String, CaptchaValidationPort> captchaValidationPortMap() {
    return applicationContext.getBeansOfType(CaptchaValidationPort.class).entrySet().stream()
        .collect(Collectors.toMap(entry -> entry.getKey().toLowerCase(), Map.Entry::getValue));
  }
}