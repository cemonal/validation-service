package com.cemonal.validationservice.domain.query;

import com.cemonal.validationservice.domain.model.Email;

/**
 * Represents a query for checking the format of an email address.
 * This record encapsulates an Email object, which will be used to verify
 * the format of the email address.
 */
public record EmailFormatQuery(Email email) { }