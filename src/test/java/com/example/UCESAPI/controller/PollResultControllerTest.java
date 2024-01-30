package com.example.UCESAPI.controller;

import com.example.UCESAPI.exception.AccessNotAllowedException;
import com.example.UCESAPI.exception.notfound.PollNotFoundException;
import com.example.UCESAPI.exception.notfound.UserNotFoundException;
import com.example.UCESAPI.model.PollResult;
import com.example.UCESAPI.model.User;
import com.example.UCESAPI.model.dto.poll.PollResultDto;
import com.example.UCESAPI.model.mapper.CustomConversion;
import com.example.UCESAPI.service.PollResultService;
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
import static org.mockito.Mockito.*;

public class PollResultControllerTest {

    @Mock
    private PollResultService pollResultService;

    @Mock
    private UserService userService;

    @InjectMocks
    private PollResultController pollResultController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddPollResult() throws AccessNotAllowedException {
        // Arrange
        PollResult pollResultToAdd =  somePollResult();
        Authentication authentication = someAuthentication();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(userService.getByEmail(anyString())).thenReturn(someUser());
        when(pollResultService.add(any())).thenReturn(somePollResult());

        // Act
        ResponseEntity<PollResultDto> response = pollResultController.add(pollResultToAdd, authentication);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(pollResultService, times(1)).add(eq(pollResultToAdd));
    }

    @Test
    void testAddPollResultNotAllowed() throws AccessNotAllowedException {
        // Arrange
        User authenticatedUser = someUser();
        authenticatedUser.setId(2);
        PollResult pollResultToAdd =  somePollResult();
        Authentication authentication = someAuthentication();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(userService.getByEmail(anyString())).thenReturn(authenticatedUser);
        when(pollResultService.add(any())).thenReturn(somePollResult());

        // Act & Assert
        assertThrows(AccessNotAllowedException.class, () -> {
            pollResultController.add(pollResultToAdd, authentication);
        });
    }


    @Test
    void testGetByPollAndUser() throws UserNotFoundException, PollNotFoundException {
        // Arrange
        Integer pollId = 1;
        Integer userId = 1;
        PollResult pollResult = somePollResult();
        when(pollResultService.getByPollAndStudentUser(eq(pollId), eq(userId))).thenReturn(pollResult);

        // Act
        ResponseEntity<PollResultDto> response = pollResultController.getByPollAndUser(pollId, userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(CustomConversion.PollResultToPollResultDto(pollResult), response.getBody());
    }

    @Test
    void testGetByPollAndUserNullResponse() throws UserNotFoundException, PollNotFoundException {
        // Arrange
        Integer pollId = 1;
        Integer userId = 1;
        PollResult pollResult = somePollResult();
        when(pollResultService.getByPollAndStudentUser(eq(pollId), eq(userId))).thenReturn(null);

        // Act
        ResponseEntity<PollResultDto> response = pollResultController.getByPollAndUser(pollId, userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
