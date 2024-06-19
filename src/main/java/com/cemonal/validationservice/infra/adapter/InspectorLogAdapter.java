package com.cemonal.validationservice.infra.adapter;

import com.cemonal.validationservice.domain.port.InspectorLogPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.UUID;

@Component
public class InspectorLogAdapter implements InspectorLogPort {

  private static final Logger logger = LoggerFactory.getLogger(InspectorLogAdapter.class);

  @Override
  public void logRequest(
      UUID requestId, String url, HttpMethod method, HttpHeaders headers, Object body) {
    logger.info(
        "Request ID: {}, HTTP Request - URL: {}, Method: {}, Headers: {}, Body: {}",
        requestId,
        url,
        method,
        headers,
        body);
  }

  @Override
  public void logResponse(
      UUID requestId,
      String url,
      HttpMethod method,
      HttpHeaders headers,
      Object body,
      HttpStatus status) {
    logger.info(
        "Request ID: {}, HTTP Response - URL: {}, Method: {}, Headers: {}, Body: {}, Status: {}",
        requestId,
        url,
        method,
        headers,
        body,
        status);
  }

  @Override
  public void logResponse(
      UUID requestId,
      String url,
      HttpMethod method,
      HttpHeaders headers,
      Object body,
      HttpStatusCode statusCode) {
    logger.info(
        "Request ID: {}, HTTP Response - URL: {}, Method: {}, Headers: {}, Body: {}, Status Code: {}",
        requestId,
        url,
        method,
        headers,
        body,
        statusCode);
  }

  @Override
  public void logException(
      UUID requestId, String url, HttpMethod method, HttpHeaders headers, Exception exception) {
    if (exception instanceof HttpStatusCodeException httpException) {
      logger.error(
          "Request ID: {}, HTTP Exception - URL: {}, Method: {}, Headers: {}, Status: {}, Response Body: {}",
          requestId,
          url,
          method,
          headers,
          httpException.getStatusCode(),
          httpException.getResponseBodyAsString(),
          exception);
    } else {
      logger.error(
          "Request ID: {}, HTTP Exception - URL: {}, Method: {}, Headers: {}, Exception: {}",
          requestId,
          url,
          method,
          headers,
          exception.getMessage(),
          exception);
    }
  }
}
