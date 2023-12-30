package com.cemonal.validationservice.domain.handler.query;

import com.cemonal.validationservice.domain.port.QueryHandler;
import com.cemonal.validationservice.domain.query.EmailFormatQuery;
import com.cemonal.validationservice.domain.usecase.CheckEmailFormatUseCase;
import org.springframework.stereotype.Component;

/**
 * Handler for processing queries about the format of an email address.
 * Implements the QueryHandler interface for EmailFormatQuery.
 */
@Component
public class EmailFormatQueryHandler implements QueryHandler<EmailFormatQuery, Boolean> {
    private final CheckEmailFormatUseCase checkEmailFormatUseCase;

    /**
     * Constructs an EmailFormatQueryHandler with the specified CheckEmailFormatUseCase.
     *
     * @param checkEmailFormatUseCase The use case to be used for checking email format.
     */
    public EmailFormatQueryHandler(CheckEmailFormatUseCase checkEmailFormatUseCase) {
        this.checkEmailFormatUseCase = checkEmailFormatUseCase;
    }

    /**
     * Handles the given EmailFormatQuery.
     * Uses the CheckEmailFormatUseCase to determine if the email format is valid.
     *
     * @param query The query containing the email to be checked.
     * @return Boolean indicating whether the email format is valid or not.
     */
    @Override
    public Boolean handle(EmailFormatQuery query) {
        return checkEmailFormatUseCase.execute(query.email());
    }
}