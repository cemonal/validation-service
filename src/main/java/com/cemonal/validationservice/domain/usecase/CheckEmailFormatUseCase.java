package com.cemonal.validationservice.domain.usecase;

import com.cemonal.validationservice.domain.model.Email;
import com.cemonal.validationservice.domain.port.EmailFormatCheckerPort;
import org.springframework.stereotype.Service;

/**
 * Use case for checking the format of an email address.
 * This class encapsulates the logic to verify if the format of a given
 * email address is valid, using the EmailFormatCheckerPort.
 */
@Service
public class CheckEmailFormatUseCase {
    private final EmailFormatCheckerPort formatCheckerPort;

    /**
     * Constructor for CheckEmailFormatUseCase.
     *
     * @param formatCheckerPort The port used to check the format of email addresses.
     */
    public CheckEmailFormatUseCase(EmailFormatCheckerPort formatCheckerPort) {
        this.formatCheckerPort = formatCheckerPort;
    }

    /**
     * Executes the use case to check the format of the provided email address.
     *
     * @param email The email object to be validated.
     * @return boolean indicating whether the email format is valid or not.
     */
    public boolean execute(Email email) {
        return formatCheckerPort.isValidFormat(email);
    }
}
