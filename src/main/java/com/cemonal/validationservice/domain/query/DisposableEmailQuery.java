package com.cemonal.validationservice.domain.query;

import com.cemonal.validationservice.domain.model.Email;

/**
 * A record to represent a query for checking if an email is disposable.
 */
public record DisposableEmailQuery(Email email) { }