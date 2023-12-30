package com.cemonal.validationservice.domain.handler.query;

import com.cemonal.validationservice.domain.port.QueryHandler;
import com.cemonal.validationservice.domain.query.DisposableEmailQuery;
import com.cemonal.validationservice.domain.usecase.CheckDisposableEmailUseCase;
import org.springframework.stereotype.Component;

/**
 * Query handler for checking if an email is disposable.
 */
@Component
public class DisposableEmailQueryHandler implements QueryHandler<DisposableEmailQuery, Boolean> {
    private final CheckDisposableEmailUseCase checkDisposableEmailUseCase;

    /**
     * Constructor for DisposableEmailQueryHandler.
     *
     * @param checkDisposableEmailUseCase The use case for checking if an email is disposable.
     */
    public DisposableEmailQueryHandler(CheckDisposableEmailUseCase checkDisposableEmailUseCase) {
        this.checkDisposableEmailUseCase = checkDisposableEmailUseCase;
    }

    /**
     * Handles the given DisposableEmailQuery.
     *
     * @param query The query containing the email to be checked.
     * @return Boolean indicating whether the email is disposable or not.
     */
    @Override
    public Boolean handle(DisposableEmailQuery query) {
        return checkDisposableEmailUseCase.execute(query.email());
    }
}