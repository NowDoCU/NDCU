package com.hotsix.semochang.component;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * author: pinest94
 * since: 2021-04-01
 */

@Component
public class LogAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("Server1");
    }
}
