package com.cemonal.validationservice.domain.usecase;

import com.cemonal.validationservice.domain.model.Email;
import com.cemonal.validationservice.domain.port.DisposableEmailCheckerPort;
import org.springframework.stereotype.Service;

/**
 * Use case for checking if an email is disposable.
 */
@Service
public class CheckDisposableEmailUseCase {
    private final DisposableEmailCheckerPort checkerPort;

    /**
     * Constructor for CheckDisposableEmailUseCase.
     *
     * @param checkerPort The port used to check if an email is disposable.
     */
    public CheckDisposableEmailUseCase(DisposableEmailCheckerPort checkerPort) {
        this.checkerPort = checkerPort;
    }

    /**
     * Executes the use case to check if the given email is disposable.
     *
     * @param email The email to be checked.
     * @return boolean indicating whether the email is disposable or not.
     */
    public boolean execute(Email email) {
        return checkerPort.isDisposable(email);
    }
}