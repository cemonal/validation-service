package com.cemonal.validationservice.infra.adapter;

import com.cemonal.validationservice.domain.port.EmailValidationPort;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class DisposableEmailCheckerAdapter implements EmailValidationPort {

  private static final String BLOCKLIST_FILE_PATH =
      "src/main/resources/disposable_email_blocklist.conf";
  private static final Supplier<Set<String>> emailBlockListSupplier =
      DisposableEmailCheckerAdapter::loadBlocklist;

  private static Set<String> loadBlocklist() {
    try {
      return Files.lines(Path.of(BLOCKLIST_FILE_PATH))
          .filter(line -> !line.isBlank() && !line.trim().startsWith("//"))
          .collect(Collectors.toCollection(HashSet::new));
    } catch (IOException e) {
      throw new IllegalStateException("Error loading blocklist from " + BLOCKLIST_FILE_PATH, e);
    }
  }

  @Override
  public String getValidationName() {
    return "Disposable e-mail";
  }

  @Override
  public boolean validate(String email) {
    return !emailBlockListSupplier.get().contains(extractDomainFromEmail(email));
  }

  private String extractDomainFromEmail(String email) {
    if (email == null || email.isBlank()) {
      throw new IllegalArgumentException("Email address cannot be null or empty");
    }

    int atIndex = email.lastIndexOf('@');
    if (atIndex == -1 || atIndex == email.length() - 1) {
      throw new IllegalArgumentException("Invalid email address: " + email);
    }

    String domain = email.substring(atIndex + 1);

    if (!isValidDomain(domain)) {
      throw new IllegalArgumentException("Invalid domain extracted from email: " + domain);
    }

    return domain;
  }

  private boolean isValidDomain(String domain) {
    String domainRegex = "^[a-zA-Z0-9.-]+$";
    return domain.matches(domainRegex);
  }
}
