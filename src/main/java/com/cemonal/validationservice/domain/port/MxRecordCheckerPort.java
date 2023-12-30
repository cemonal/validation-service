package com.cemonal.validationservice.domain.port;

import com.cemonal.validationservice.domain.model.Email;

public interface MxRecordCheckerPort {
    boolean hasMxRecord(Email email);
}