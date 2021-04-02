package com.hotsix.semochang.utils;

import org.springframework.security.core.Authentication;

/**
 * author: pinest94
 * since: 2021-04-02
 */
public class ExceptionUtil {

    public static boolean isInValidAuthentication(Authentication authentication) {
        return authentication == null ? true : false;
    }
}
