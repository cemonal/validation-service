# Validation Service

Validation Service is a Spring Boot application designed to handle multiple validations using various providers. The application follows Hexagonal Architecture principles and leverages Java 17 features.

## Features

- Multiple validations:
    - Email format validation
    - Disposable email check
    - MX record check
    - reCAPTCHA v2 and v3 validation
    - Phone number validation
- Centralized configuration for validation providers.
- Logging of requests, responses, and exceptions.

## Technologies Used

- Java 17
- Spring Boot
- Maven
- Docker
- Hexagonal Architecture

## Installation

### Prerequisites

- Java 17
- Maven 3.6+
- Docker

### Steps

1. **Clone the repository**:
   ```sh
   git clone  https://github.com/cemonal/validation-service.git
   cd validation-service
   ```
2. **Build the project**:
   ```sh
   mvn clean install -DskipTests
   ```
3. **Build the Docker image**:
   ```sh
   docker build -t validation-service .
   ```
4. **Run the Docker container**:
   ```sh
   docker run -p 9090:8080 validation-service
   ```
## Configuration

The application uses `application.yml` for configuration:

```yaml
springdoc:
  api-docs:
    path: /api-docs
  swagger-ui:
    path: /swagger-ui.html
    enabled: true

recaptcha:
  v2Endpoint: 'https://www.google.com/recaptcha/api/siteverify'
  v2SecretKey: 'your-recaptcha-v2-secret-key'
  v3Endpoint: 'https://www.google.com/recaptcha/api/siteverify'
  v3SecretKey: 'your-recaptcha-v3-secret-key'
  v3ScoreThreshold: 0.5
  allowedIps:
    - '*'
```
### Endpoints

- **Email Validation Endpoint**
    - **URL**: `/api/v1/email-validation`
    - **Method**: `POST`
    - **Request Body**:
      ```json
      {
        "email": "test@example.com"
      }
      ```
    - **Response**:
      ```json
      {
        "success": true,
        "messages": []
      }
      ```

- **reCAPTCHA v2 Validation Endpoint**
    - **URL**: `/api/v1/recaptcha/validate`
    - **Method**: `POST`
    - **Request Body**:
      ```json
      {
        "provider": "recaptchav2",
        "token": "your-recaptcha-token",
        "context": {
          "remoteip": "user-ip-address"
        }
      }
      ```
    - **Response**:
      ```json
      {
        "success": true,
        "messages": []
      }
      ```

- **reCAPTCHA v3 Validation Endpoint**
    - **URL**: `/api/v1/recaptcha/validate`
    - **Method**: `POST`
    - **Request Body**:
      ```json
      {
        "provider": "recaptchav3",
        "token": "your-recaptcha-token",
        "context": {
          "action": "homepage"
        }
      }
      ```
    - **Response**:
      ```json
      {
        "success": true,
        "messages": []
      }
      ```
- **Phone Number Validation Endpoint**
    - **URL**: `/api/v1/phone-number-validation`
    - **Method**: `POST`
    - **Request Body**:
      ```json
      {
        "phoneNumber": "+14155552671",
        "regionIso2": "US"
      }
      ```
    - **Response**:
      ```json
      {
        "success": true,
        "messages": [
          "Valid phone number"
        ]
      }
      ```
### Swagger UI

The application includes Swagger UI for API documentation. You can access it at `/swagger-ui.html`.

## Contributing

We welcome contributions! If you would like to contribute, please fork the repository, create a new branch, make your changes, and open a pull request.

Thank you for your contributions!