package com.cemonal.validationservice.domain.query;

import com.cemonal.validationservice.domain.model.Email;

/**
 * Represents a query for checking the presence of MX (Mail Exchange) records
 * for the domain of an email address. This record encapsulates an Email object,
 * the domain of which will be checked for MX records.
 */
public record MxRecordQuery(Email email) {
}