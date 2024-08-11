package com.Gastos.security;

import java.util.Base64;

public class JwtProperties {

    public static final String SECRET = Base64.getEncoder().encodeToString("mySecretKey".getBytes());
    public static final long EXPIRATION_TIME = 864_000_000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    public static String getSecret() {
        return SECRET;
    }

    public static long getExpirationTime() {
        return EXPIRATION_TIME;
    }

    public static String getHeader() {
        return HEADER_STRING;
    }

    public static String getPrefix() {
        return TOKEN_PREFIX;
    }
}
