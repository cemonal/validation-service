package com.cemonal.validationservice.domain.port;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.UUID;

public interface InspectorLogPort {
  void logRequest(UUID requestId, String url, HttpMethod method, HttpHeaders headers, Object body);

  void logResponse(
      UUID requestId,
      String url,
      HttpMethod method,
      HttpHeaders headers,
      Object body,
      HttpStatus status);

  void logResponse(
      UUID requestId,
      String url,
      HttpMethod method,
      HttpHeaders headers,
      Object body,
      HttpStatusCode statusCode);

  void logException(
      UUID requestId, String url, HttpMethod method, HttpHeaders headers, Exception exception);
}
