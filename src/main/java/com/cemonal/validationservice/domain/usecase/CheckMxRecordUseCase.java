package com.cemonal.validationservice.domain.usecase;

import com.cemonal.validationservice.domain.model.Email;
import com.cemonal.validationservice.domain.port.MxRecordCheckerPort;
import org.springframework.stereotype.Service;

/**
 * Use case for checking the presence of MX records for an email address domain.
 * This class encapsulates the logic to verify if the domain of a given
 * email address has MX (Mail Exchange) records, using the MxRecordCheckerPort.
 */
@Service
public class CheckMxRecordUseCase {
    private final MxRecordCheckerPort mxRecordCheckerPort;

    /**
     * Constructor for CheckMxRecordUseCase.
     *
     * @param mxRecordCheckerPort The port used to check for MX records of email addresses.
     */
    public CheckMxRecordUseCase(MxRecordCheckerPort mxRecordCheckerPort) {
        this.mxRecordCheckerPort = mxRecordCheckerPort;
    }

    /**
     * Executes the use case to check for MX records in the domain of the provided email address.
     *
     * @param email The email object whose domain will be checked for MX records.
     * @return boolean indicating whether the email domain has MX records or not.
     */
    public boolean execute(Email email) {
        return mxRecordCheckerPort.hasMxRecord(email);
    }
}
