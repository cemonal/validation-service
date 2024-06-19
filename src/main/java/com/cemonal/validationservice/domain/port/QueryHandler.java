package com.cemonal.validationservice.domain.port;

public interface QueryHandler<Q, R> {
    R handle(Q query);
}