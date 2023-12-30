package com.cemonal.validationservice.infra.controller.v1;

import com.cemonal.validationservice.domain.handler.query.DisposableEmailQueryHandler;
import com.cemonal.validationservice.domain.handler.query.EmailFormatQueryHandler;
import com.cemonal.validationservice.domain.handler.query.MxRecordQueryHandler;
import com.cemonal.validationservice.domain.model.Email;
import com.cemonal.validationservice.domain.query.DisposableEmailQuery;
import com.cemonal.validationservice.domain.query.EmailFormatQuery;
import com.cemonal.validationservice.domain.query.MxRecordQuery;
import com.cemonal.validationservice.infra.dto.EmailValidationResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling email validation requests.
 * Provides endpoints for individual checks (format, MX record, disposable status)
 * as well as an aggregated validation check with optional mailbox verification.
 */
@RestController
@RequestMapping("/api/v1/email")
public class EmailValidationController {
    private final EmailFormatQueryHandler emailFormatQueryHandler;
    private final MxRecordQueryHandler mxRecordQueryHandler;
    private final DisposableEmailQueryHandler disposableEmailQueryHandler;

    public EmailValidationController(EmailFormatQueryHandler emailFormatQueryHandler,
                                     MxRecordQueryHandler mxRecordQueryHandler,
                                     DisposableEmailQueryHandler disposableEmailQueryHandler) {
        this.emailFormatQueryHandler = emailFormatQueryHandler;
        this.mxRecordQueryHandler = mxRecordQueryHandler;
        this.disposableEmailQueryHandler = disposableEmailQueryHandler;
    }

    /**
     * Validates an email address by performing a series of checks:
     * format validation, MX record checking, disposable email detection.
     *
     * @param emailAddress The email address to be validated.
     * @return EmailValidationResponse indicating the result of the validation.
     */
    @GetMapping("/validate")
    public EmailValidationResponse validateEmail(@RequestParam(required = true) String emailAddress) {
        Email email = new Email(emailAddress);

        if (!emailFormatQueryHandler.handle(new EmailFormatQuery(email))) {
            return new EmailValidationResponse(false, "Invalid email format");
        }
        if (!mxRecordQueryHandler.handle(new MxRecordQuery(email))) {
            return new EmailValidationResponse(false, "No MX record found");
        }
        if (disposableEmailQueryHandler.handle(new DisposableEmailQuery(email))) {
            return new EmailValidationResponse(false, "Disposable email address detected");
        }

        return new EmailValidationResponse(true, "Email is valid");
    }

    /**
     * Validates the format of an email address.
     *
     * @param emailAddress The email address whose format is to be validated.
     * @return EmailValidationResponse indicating if the format is valid or not.
     */
    @GetMapping("/validate-format")
    public EmailValidationResponse validateFormat(@RequestParam(required = true) String emailAddress) {
        Email email = new Email(emailAddress);
        boolean isValid = emailFormatQueryHandler.handle(new EmailFormatQuery(email));
        return new EmailValidationResponse(isValid, isValid ? "Valid format" : "Invalid format");
    }

    /**
     * Checks for the presence of MX records for the domain of an email address.
     *
     * @param emailAddress The email address whose MX record is to be checked.
     * @return EmailValidationResponse indicating if MX records are found or not.
     */
    @GetMapping("/validate-mx")
    public EmailValidationResponse validateMxRecord(@RequestParam(required = true) String emailAddress) {
        Email email = new Email(emailAddress);
        boolean hasMxRecord = mxRecordQueryHandler.handle(new MxRecordQuery(email));
        return new EmailValidationResponse(hasMxRecord, hasMxRecord ? "MX record found" : "No MX record found");
    }

    /**
     * Checks if an email address is a disposable (temporary) email.
     *
     * @param emailAddress The email address to be checked for being disposable.
     * @return EmailValidationResponse indicating if the email is disposable or not.
     */
    @GetMapping("/validate-disposable")
    public EmailValidationResponse validateDisposable(@RequestParam(required = true) String emailAddress) {
        Email email = new Email(emailAddress);
        boolean isDisposable = disposableEmailQueryHandler.handle(new DisposableEmailQuery(email));
        return new EmailValidationResponse(!isDisposable, isDisposable ? "Disposable email address" : "Non-disposable email address");
    }
}
