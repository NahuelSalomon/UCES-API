package com.example.UCESAPI.auth.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class JWTUtilsTest {

    @InjectMocks
    private JWTUtils jwtUtils;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(jwtUtils, "SECRET_KEY", "mySecretKey");
    }


    @Test
    public void testExtractUsername() {
        String token = jwtUtils.generateToken(new User("admin@example.com","123",Collections.emptyList()));
        String username = jwtUtils.extractUsername(token);
        assertEquals("admin@example.com", username);
    }

    @Test
    public void testExtractExpiration() {
        String token = jwtUtils.generateToken(new User("admin@example.com","123",Collections.emptyList()));
        Date expiration = jwtUtils.extractExpiration(token);
        assertNotNull(expiration);
    }

    @Test
    public void testGenerateToken() {
        UserDetails userDetails = new User("admin", "password", Collections.emptyList());
        String token = jwtUtils.generateToken(userDetails);
        assertNotNull(token);
    }

    @Test
    public void testGenerateTokenByEmail() {
        String email = "admin@example.com";
        String token = jwtUtils.generateTokenByEmail(email);
        assertNotNull(token);
    }

    @Test
    public void testValidateToken() {
        UserDetails userDetails = new User("admin", "password", Collections.emptyList());
        String token = jwtUtils.generateToken(userDetails);
        boolean isValid = jwtUtils.validateToken(token, userDetails);
        assertTrue(isValid);
    }

}
