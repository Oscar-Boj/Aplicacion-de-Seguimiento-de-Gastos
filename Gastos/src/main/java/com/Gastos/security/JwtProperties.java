package com.Gastos.security;

public class JwtProperties {

    // Clave secreta para firmar el JWT
    public static final String SECRET = "your_secret_key"; // Debe ser un valor seguro y aleatorio

    // Tiempo de expiración del JWT en milisegundos
    public static final long EXPIRATION_TIME = 864_000_000; // 10 días

    // Prefijo del token en el encabezado HTTP
    public static final String HEADER_STRING = "Authorization";

    // Prefijo del token (ej. "Bearer ")
    public static final String TOKEN_PREFIX = "Bearer ";

    // Método para obtener el valor del secreto
    public static String getSecret() {
        return SECRET;
    }

    // Método para obtener el valor de la expiración
    public static long getExpirationTime() {
        return EXPIRATION_TIME;
    }

    // Método para obtener el valor del encabezado
    public static String getHeader() {
        return HEADER_STRING;
    }

    // Método para obtener el prefijo del token
    public static String getPrefix() {
        return TOKEN_PREFIX;
    }
}
