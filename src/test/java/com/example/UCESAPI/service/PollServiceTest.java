package com.example.UCESAPI.service;

import com.example.UCESAPI.exception.notfound.PollNotFoundException;
import com.example.UCESAPI.model.Poll;
import com.example.UCESAPI.repository.PollAnswerRepository;
import com.example.UCESAPI.repository.PollQuestionRepository;
import com.example.UCESAPI.repository.PollRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.UCESAPI.utils.ModelTestUtil.somePoll;
import static com.example.UCESAPI.utils.ModelTestUtil.somePollList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PollServiceTest {

    @Mock
    private PollRepository pollRepository;

    @Mock
    private PollQuestionRepository pollQuestionRepository;

    @Mock
    private PollAnswerRepository pollAnswerRepository;

    @InjectMocks
    private PollService pollService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAdd() {
        // Arrange
        Poll poll = somePoll();
        when(pollRepository.save(poll)).thenReturn(poll);

        // Act
        Poll addedPoll = pollService.add(poll);

        // Assert
        assertNotNull(addedPoll);
        assertSame(poll, addedPoll);
        verify(pollRepository, times(1)).save(poll);
    }

    @Test
    void testGetAll() {
        // Arrange
        List<Poll> pollList = somePollList();
        when(pollRepository.findAll()).thenReturn(pollList);

        // Act
        List<Poll> result = pollService.getAll();

        // Assert
        assertNotNull(result);
        assertSame(pollList, result);
        verify(pollRepository, times(1)).findAll();
    }

    @Test
    void testGetPollByIdWhenPollExists() throws PollNotFoundException {
        // Arrange
        int pollId = 1;
        Poll poll = somePoll();
        when(pollRepository.findById(pollId)).thenReturn(Optional.of(poll));

        // Act
        Poll result = pollService.getPollById(pollId);

        // Assert
        assertNotNull(result);
        assertSame(poll, result);
        verify(pollRepository, times(1)).findById(pollId);
    }

    @Test
    void testGetPollByIdWhenPollDoesNotExist() {
        // Arrange
        int pollId = 1;
        when(pollRepository.findById(pollId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PollNotFoundException.class, () -> pollService.getPollById(pollId));
        verify(pollRepository, times(1)).findById(pollId);
    }

    // Similar tests for getPollByCareerId and getPollBySubjectId methods

    @Test
    void testDelete() {
        // Arrange
        Poll poll = somePoll();

        // Act
        pollService.delete(poll);

        // Assert
        verify(pollRepository, times(1)).delete(poll);
    }

    @Test
    void testGetPollByCareerIdWhenPollExists() throws PollNotFoundException {
        // Arrange
        int careerId = 1;
        Poll poll = somePoll();
        when(pollRepository.findByCareerId(careerId)).thenReturn(Optional.of(poll));

        // Act
        Poll result = pollService.getPollByCareerId(careerId);

        // Assert
        assertNotNull(result);
        assertSame(poll, result);
        verify(pollRepository, times(1)).findByCareerId(careerId);
    }

    @Test
    void testGetPollByCareerIdWhenPollDoesNotExist() {
        // Arrange
        int careerId = 1;
        when(pollRepository.findByCareerId(careerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PollNotFoundException.class, () -> pollService.getPollByCareerId(careerId));
        verify(pollRepository, times(1)).findByCareerId(careerId);
    }

    @Test
    void testGetPollBySubjectIdWhenPollExists() throws PollNotFoundException {
        // Arrange
        int subjectId = 1;
        Poll poll = somePoll();
        when(pollRepository.findBySubjectId(subjectId)).thenReturn(Optional.of(poll));

        // Act
        Poll result = pollService.getPollBySubjectId(subjectId);

        // Assert
        assertNotNull(result);
        assertSame(poll, result);
        verify(pollRepository, times(1)).findBySubjectId(subjectId);
    }

    @Test
    void testGetPollBySubjectIdWhenPollDoesNotExist() {
        // Arrange
        int subjectId = 1;
        when(pollRepository.findBySubjectId(subjectId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PollNotFoundException.class, () -> pollService.getPollBySubjectId(subjectId));
        verify(pollRepository, times(1)).findBySubjectId(subjectId);
    }

}
