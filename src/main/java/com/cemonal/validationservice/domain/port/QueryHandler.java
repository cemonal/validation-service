package com.cemonal.validationservice.domain.port;

public interface QueryHandler<Query, Result> {
    Result handle(Query query);
}