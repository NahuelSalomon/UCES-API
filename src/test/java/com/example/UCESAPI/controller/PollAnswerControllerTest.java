package com.example.UCESAPI.controller;

import com.example.UCESAPI.exception.AccessNotAllowedException;
import com.example.UCESAPI.model.PollAnswer;
import com.example.UCESAPI.model.User;
import com.example.UCESAPI.service.PollAnswerService;
import com.example.UCESAPI.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.UCESAPI.utils.ModelTestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class PollAnswerControllerTest {

    @Mock
    private PollAnswerService pollAnswerService;

    @Mock
    private UserService userService;

    @InjectMocks
    private PollAnswerController pollAnswerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddRange() throws AccessNotAllowedException {
        // Arrange
        List<PollAnswer> pollAnswerList = somePollAnswerList();
        User user = someUser();


        when(userService.getByEmail(eq(user.getEmail()))).thenReturn(user);
        when(pollAnswerService.addAll(any())).thenReturn(pollAnswerList);

        // Act
        ResponseEntity<Object> response = pollAnswerController.addRange(pollAnswerList, someAuthentication());

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testAddRangeThrowsAccessNotAllowedException() {
        // Arrange
        List<PollAnswer> pollAnswerList = somePollAnswerList();
        User user = someUser();
        user.setId(2);

        //When
        when(userService.getByEmail(eq(user.getEmail()))).thenReturn(user);

        // Act y Assert
        assertThrows(AccessNotAllowedException.class, () -> pollAnswerController.addRange(pollAnswerList, someAuthentication()));
    }
}
