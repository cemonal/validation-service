package com.cemonal.validationservice.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Object> handleIllegalArgumentException(
      IllegalArgumentException ex, WebRequest request) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("status", HttpStatus.BAD_REQUEST.value());
    body.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
    body.put("message", ex.getMessage());
    body.put("path", getPathFromRequest(request));
    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<Object> handleIllegalStateException(
      IllegalStateException ex, WebRequest request) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
    body.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    body.put("message", ex.getMessage());
    body.put("path", getPathFromRequest(request));
    return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(HttpStatusCodeException.class)
  public ResponseEntity<Object> handleHttpStatusCodeException(
      HttpStatusCodeException ex, WebRequest request) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("status", ex.getStatusCode().value());
    body.put("error", ex.getStatusText());
    body.put("message", ex.getResponseBodyAsString());
    body.put("path", getPathFromRequest(request));
    return new ResponseEntity<>(body, ex.getStatusCode());
  }

  @ExceptionHandler(IOException.class)
  public ResponseEntity<Object> handleIOException(IOException ex, WebRequest request) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
    body.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    body.put("message", "Error reading or writing data");
    body.put("details", ex.getMessage());
    body.put("path", getPathFromRequest(request));
    return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
    body.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    body.put("message", ex.getMessage());
    body.put("path", getPathFromRequest(request));
    return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private String getPathFromRequest(WebRequest request) {
    String description = request.getDescription(false);
    if (description.startsWith("uri=")) {
      return description.substring(4);
    }
    return description;
  }
}
