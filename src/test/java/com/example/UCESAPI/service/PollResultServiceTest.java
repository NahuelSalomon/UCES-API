package com.example.UCESAPI.service;

import com.example.UCESAPI.exception.notfound.PollNotFoundException;
import com.example.UCESAPI.exception.notfound.UserNotFoundException;
import com.example.UCESAPI.model.Poll;
import com.example.UCESAPI.model.PollResult;
import com.example.UCESAPI.model.User;
import com.example.UCESAPI.repository.PollResultRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.example.UCESAPI.utils.ModelTestUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PollResultServiceTest {

    @Mock
    private PollResultRepository pollResultRepository;

    @Mock
    private UserService userService;

    @Mock
    private PollService pollService;

    @InjectMocks
    private PollResultService pollResultService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAdd() {
        // Arrange
        PollResult pollResult = somePollResult();
        when(pollResultRepository.save(pollResult)).thenReturn(pollResult);

        // Act
        PollResult addedPollResult = pollResultService.add(pollResult);

        // Assert
        assertNotNull(addedPollResult);
        assertSame(pollResult, addedPollResult);
        verify(pollResultRepository, times(1)).save(pollResult);
    }

    @Test
    void testGetByPollAndStudentUser() throws UserNotFoundException, PollNotFoundException {
        // Arrange
        int pollId = 1;
        int studentUserId = 1;
        Poll poll = somePoll();
        User user = someUser();
        PollResult pollResult = somePollResult();
        when(pollService.getPollById(pollId)).thenReturn(poll);
        when(userService.getById(studentUserId)).thenReturn(user);
        when(pollResultRepository.findByPollAndStudentUser(poll, user)).thenReturn(pollResult);

        // Act
        PollResult result = pollResultService.getByPollAndStudentUser(pollId, studentUserId);

        // Assert
        assertNotNull(result);
        assertSame(pollResult, result);
        verify(pollService, times(1)).getPollById(pollId);
        verify(userService, times(1)).getById(studentUserId);
        verify(pollResultRepository, times(1)).findByPollAndStudentUser(poll, user);
    }

}
