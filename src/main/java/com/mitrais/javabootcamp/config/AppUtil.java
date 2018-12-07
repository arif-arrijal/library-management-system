package com.mitrais.javabootcamp.config;

import static org.apache.commons.codec.digest.DigestUtils.sha512Hex;

public class AppUtil {
    public static String shaPassword(String rawPassword) {
        return sha512Hex("Auth" + rawPassword.trim());
    }
}
