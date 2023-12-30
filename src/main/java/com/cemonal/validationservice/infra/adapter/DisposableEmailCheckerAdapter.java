package com.cemonal.validationservice.infra.adapter;

import com.cemonal.validationservice.domain.model.Email;
import com.cemonal.validationservice.domain.port.DisposableEmailCheckerPort;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
public class DisposableEmailCheckerAdapter implements DisposableEmailCheckerPort {
    private static final Supplier<Set<String>> emailBlockList = DisposableEmailCheckerAdapter::loadBlocklist;

    private static Set<String> loadBlocklist() {
        try {
            return Files.lines(Path.of("src/main/resources/disposable_email_blocklist.conf"))
                    .filter(line -> !line.isBlank() && !line.trim().startsWith("//"))
                    .collect(Collectors.toCollection(HashSet::new));
        } catch (IOException e) {
            throw new RuntimeException("Error loading blocklist", e);
        }
    }

    @Override
    public boolean isDisposable(Email email) {
        return emailBlockList.get().contains(email.getDomain());
    }
}