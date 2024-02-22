package com.example.UCESAPI.auth.controller;

import com.example.UCESAPI.auth.utils.JWTUtils;
import com.example.UCESAPI.exception.UserAlreadyExistException;
import com.example.UCESAPI.model.User;
import com.example.UCESAPI.model.dto.LoginRequestDto;
import com.example.UCESAPI.model.dto.LoginResponseDto;
import com.example.UCESAPI.model.dto.user.UserInsertRequestDto;
import com.example.UCESAPI.model.dto.user.UserResponseDto;
import com.example.UCESAPI.service.ForumService;
import com.example.UCESAPI.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.Collections;

import static com.example.UCESAPI.utils.ModelTestUtil.someUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserAuthControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private JWTUtils jwtUtils;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserAuthController userAuthController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testRegisterSuccess() throws UserAlreadyExistException, IOException {
        // Arrange
        UserInsertRequestDto userDto = new UserInsertRequestDto();
        when(userService.register(userDto)).thenReturn(new LoginResponseDto("jwt-token"));

        // Act
        ResponseEntity<LoginResponseDto> response = userAuthController.register(userDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("jwt-token", response.getBody().getToken());
    }

    @Test
    void testRegisterUserAlreadyExists() throws UserAlreadyExistException, IOException {
        // Arrange
        UserInsertRequestDto userDto = new UserInsertRequestDto();
        when(userService.register(userDto)).thenThrow(new UserAlreadyExistException());

        // Act
        ResponseEntity<LoginResponseDto> response = userAuthController.register(userDto);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testRegisterInvalidImageFormat() throws UserAlreadyExistException, IOException {
        // Arrange
        UserInsertRequestDto userDto = new UserInsertRequestDto();
        when(userService.register(userDto)).thenThrow(new IOException());

        // Act
        ResponseEntity<LoginResponseDto> response = userAuthController.register(userDto);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testLoginSuccess() {
        // Arrange
        String token = "12374";
        LoginRequestDto loginRequestDto = new LoginRequestDto("email", "password");
        UserDetails userDetails = new org.springframework.security.core.userdetails.User("email", "password", Collections.emptyList());
        when(userService.login(loginRequestDto)).thenReturn(userDetails);
        when(jwtUtils.generateToken(userDetails)).thenReturn(token);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        // Act
        ResponseEntity<LoginResponseDto> response = userAuthController.login(loginRequestDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getToken());
    }

    @Test
    void testLoginInvalidCredentials() {
        // Arrange
        LoginRequestDto loginRequestDto = new LoginRequestDto("email", "password");
        when(userService.login(loginRequestDto)).thenThrow(new BadCredentialsException("Invalid credentials"));

        // Act
        ResponseEntity<LoginResponseDto> response = userAuthController.login(loginRequestDto);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testUserDetails() {
        // Arrange
        User user = someUser();
        user.setEmail("test@example.com");
        when(authentication.getPrincipal()).thenReturn(new org.springframework.security.core.userdetails.User("test@example.com", "password", Collections.emptyList()));
        when(userService.getByEmail("test@example.com")).thenReturn(user);

        // Act
        ResponseEntity<UserResponseDto> response = userAuthController.userDetails(authentication);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("test@example.com", response.getBody().getEmail());
    }

}
