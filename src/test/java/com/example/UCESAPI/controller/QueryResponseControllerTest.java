package com.example.UCESAPI.controller;
import com.example.UCESAPI.exception.AccessNotAllowedException;
import com.example.UCESAPI.model.QueryResponse;
import com.example.UCESAPI.model.User;
import com.example.UCESAPI.service.QueryResponseService;
import com.example.UCESAPI.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.example.UCESAPI.utils.ModelTestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class QueryResponseControllerTest {

    @Mock
    private QueryResponseService queryResponseService;

    @Mock
    private UserService userService;

    @InjectMocks
    private QueryResponseController queryResponseController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddQueryResponse() throws AccessNotAllowedException {
        // Arrange
        QueryResponse queryResponseToAdd = someQueryResponse();
        Authentication authentication = someAuthentication();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(userService.getByEmail(anyString())).thenReturn(someUser());
        when(queryResponseService.add(queryResponseToAdd)).thenReturn(queryResponseToAdd);

        // Act
        ResponseEntity<Object> response = queryResponseController.add(queryResponseToAdd, authentication);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(queryResponseService, times(1)).add(eq(queryResponseToAdd));
    }

    @Test
    void testAddQueryResponseNotAllowed() {
        // Arrange
        User authenticatedUser = someUser();
        authenticatedUser.setId(2);
        QueryResponse queryResponseToAdd = someQueryResponse();
        Authentication authentication = someAuthentication();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(userService.getByEmail(anyString())).thenReturn(authenticatedUser);

        // Act & Assert
        assertThrows(AccessNotAllowedException.class, () -> {
            queryResponseController.add(queryResponseToAdd, authentication);
        });
    }

    @Test
    void testDeleteQueryResponse() {
        // Arrange
        Integer queryResponseId = 1;

        // Act
        ResponseEntity<Object> response = queryResponseController.delete(queryResponseId);

        // Assert
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        verify(queryResponseService, times(1)).delete(eq(queryResponseId));
    }

}
