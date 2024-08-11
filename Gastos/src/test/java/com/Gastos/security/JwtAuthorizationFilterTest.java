package com.Gastos.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class JwtAuthorizationFilterTest {

    @InjectMocks
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    @Mock
    private JwtProperties jwtProperties;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(jwtProperties.getHeader()).thenReturn("Authorization");
        when(jwtProperties.getPrefix()).thenReturn("Bearer ");
        when(jwtProperties.getSecret()).thenReturn("SecretKeyToGenJWTs");
    }

    @Test
    void testDoFilterInternalWithValidToken() throws ServletException, IOException {
        String validToken = "validToken";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + validToken);

        // Mock the JWT parsing to return a valid subject
        Jws<Claims> claimsJws = mock(Jws.class);
        Claims claims = mock(Claims.class);
        when(claims.getSubject()).thenReturn("validUser");
        when(claimsJws.getBody()).thenReturn(claims);
        when(Jwts.parser().setSigningKey(anyString()).parseClaimsJws(validToken)).thenReturn(claimsJws);

        jwtAuthorizationFilter.doFilter(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternalWithInvalidToken() throws ServletException, IOException {
        String invalidToken = "invalidToken";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + invalidToken);

        // Mock the JWT parsing to throw an exception
        when(Jwts.parser().setSigningKey(anyString()).parseClaimsJws(invalidToken)).thenThrow(new RuntimeException("Invalid token"));

        jwtAuthorizationFilter.doFilter(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternalWithNoToken() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtAuthorizationFilter.doFilter(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, times(1)).doFilter(request, response);
    }
}
