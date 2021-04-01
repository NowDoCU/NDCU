package com.hotsix.semochang.utils;

import java.util.Random;

/**
 * author: pinest94
 * since: 2021-04-01
 */
public class VerificationKeyUtil {
    private static final int SIZE = 50;

    public static String getKey() {
        return init();
    }

    private static String init() {
        Random ran = new Random();
        StringBuilder sb = new StringBuilder();

        int num = 0;

        do {
            num = ran.nextInt(75) + 48;

            if((num >= 48 && num <= 57) || (num >= 65 && num <= 90) || (num >= 97 && num <= 122)) {
                sb.append((char)num);
            }else {
                continue;
            }
        } while (sb.length() < SIZE);

        return sb.toString();
    }
}
