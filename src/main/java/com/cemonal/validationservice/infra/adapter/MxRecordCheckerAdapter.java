package com.cemonal.validationservice.infra.adapter;

import com.cemonal.validationservice.domain.port.EmailValidationPort;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

@Component
public class MxRecordCheckerAdapter implements EmailValidationPort {

  @Override
  public String getValidationName() {
    return "MX Record";
  }

  @Override
  public boolean validate(String email) {
    try {
      DirContext ctx = new InitialDirContext();
      Attributes attrs =
          ctx.getAttributes("dns:/" + extractDomainFromEmail(email), new String[] {"MX"});
      return attrs.get("MX") != null;
    } catch (NamingException e) {
      return false;
    }
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
