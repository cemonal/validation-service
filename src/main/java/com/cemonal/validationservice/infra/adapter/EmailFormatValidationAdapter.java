package com.cemonal.validationservice.infra.adapter;

import com.sanctionco.jmail.JMail;
import com.cemonal.validationservice.domain.model.Email;
import com.cemonal.validationservice.domain.port.EmailFormatCheckerPort;
import org.springframework.stereotype.Component;

@Component
public class EmailFormatValidationAdapter implements EmailFormatCheckerPort {
    @Override
    public boolean isValidFormat(Email email) {
        return JMail.isValid(email.getAddress());
    }
}