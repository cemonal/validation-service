package com.cemonal.validationservice.domain.handler.query;

import com.cemonal.validationservice.domain.port.QueryHandler;
import com.cemonal.validationservice.domain.query.MxRecordQuery;
import com.cemonal.validationservice.domain.usecase.CheckMxRecordUseCase;
import org.springframework.stereotype.Component;

/**
 * Handler for processing queries about the MX record of an email domain.
 * Implements the QueryHandler interface for MxRecordQuery.
 */
@Component
public class MxRecordQueryHandler implements QueryHandler<MxRecordQuery, Boolean> {
    private final CheckMxRecordUseCase checkMxRecordUseCase;

    /**
     * Constructs an MxRecordQueryHandler with the specified CheckMxRecordUseCase.
     *
     * @param checkMxRecordUseCase The use case to be used for checking MX records.
     */
    public MxRecordQueryHandler(CheckMxRecordUseCase checkMxRecordUseCase) {
        this.checkMxRecordUseCase = checkMxRecordUseCase;
    }

    /**
     * Handles the given MxRecordQuery.
     * Uses the CheckMxRecordUseCase to determine if the email domain has an MX record.
     *
     * @param query The query containing the email domain to be checked.
     * @return Boolean indicating whether the email domain has an MX record or not.
     */
    @Override
    public Boolean handle(MxRecordQuery query) {
        return checkMxRecordUseCase.execute(query.email());
    }
}