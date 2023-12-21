package com.example.UCESAPI.controller;
import com.example.UCESAPI.auth.utils.JWTUtils;
import com.example.UCESAPI.model.SendEmailRequest;
import com.example.UCESAPI.model.User;
import com.example.UCESAPI.service.EmailSenderService;
import com.example.UCESAPI.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class EmailSenderControllerTest {

    @Mock
    private EmailSenderService emailSenderService;

    @Mock
    private UserService userService;

    @Mock
    private JWTUtils jwtUtils;

    @InjectMocks
    private EmailSenderController emailSenderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConfirmEmail() {
        // Arrange
        SendEmailRequest request = new SendEmailRequest();
        request.setEmail("test@example.com");
        request.setUrlToRedirect("http://example.com");

        User user = new User();
        when(userService.getByEmail(eq(request.getEmail()))).thenReturn(user);
        when(jwtUtils.generateTokenByEmail(eq(request.getEmail()))).thenReturn("testToken");

        // Act
        ResponseEntity<Object> response = emailSenderController.confirmEmail(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(emailSenderService, times(1)).sendEmail(eq(request.getEmail()), any(), any());
    }

    @Test
    void testConfirmEmailUserNotFound() {
        // Arrange
        SendEmailRequest request = new SendEmailRequest();
        request.setEmail("nonexistent@example.com");
        request.setUrlToRedirect("http://example.com");

        when(userService.getByEmail(eq(request.getEmail()))).thenReturn(null);

        // Act
        ResponseEntity<Object> response = emailSenderController.confirmEmail(request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(emailSenderService, never()).sendEmail(any(), any(), any());
    }

    @Test
    void testResetPassword() {
        // Arrange
        SendEmailRequest request = new SendEmailRequest();
        request.setEmail("test@example.com");
        request.setUrlToRedirect("http://example.com");

        User user = new User();
        when(userService.getByEmail(eq(request.getEmail()))).thenReturn(user);
        when(jwtUtils.generateTokenByEmail(eq(request.getEmail()))).thenReturn("testToken");

        // Act
        ResponseEntity<Object> response = emailSenderController.resetPassword(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(emailSenderService, times(1)).sendEmail(eq(request.getEmail()), any(), any());
    }

    @Test
    void testResetPasswordUserNotFound() {
        // Arrange
        SendEmailRequest request = new SendEmailRequest();
        request.setEmail("nonexistent@example.com");
        request.setUrlToRedirect("http://example.com");

        when(userService.getByEmail(eq(request.getEmail()))).thenReturn(null);

        // Act
        ResponseEntity<Object> response = emailSenderController.resetPassword(request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(emailSenderService, never()).sendEmail(any(), any(), any());
    }
}
