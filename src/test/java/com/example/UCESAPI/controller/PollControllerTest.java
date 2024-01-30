package com.example.UCESAPI.controller;

import com.example.UCESAPI.exception.notfound.PollNotFoundException;
import com.example.UCESAPI.model.Poll;
import com.example.UCESAPI.service.PollService;
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
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

public class PollControllerTest {

    @Mock
    private PollService pollService;

    @InjectMocks
    private PollController pollController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddPoll() {
        // Arrange
        Poll pollToAdd = new Poll();
        when(pollService.add(any(Poll.class))).thenReturn(pollToAdd);

        // Act
        ResponseEntity<Poll> response = pollController.add(pollToAdd);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(pollToAdd, response.getBody());
    }

    @Test
    void testGetAllPolls() {
        // Arrange
        List<Poll> pollList = Arrays.asList(new Poll());
        when(pollService.getAll()).thenReturn(pollList);

        // Act
        ResponseEntity<List<Poll>> response = pollController.getAll();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pollList, response.getBody());
    }

    @Test
    void testGetPollById() throws PollNotFoundException {
        // Arrange
        Integer pollId = 1;
        Poll poll = new Poll();
        when(pollService.getPollById(eq(pollId))).thenReturn(poll);

        // Act
        ResponseEntity<Poll> response = pollController.getPollById(pollId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(poll, response.getBody());
    }

    @Test
    void testGetPollByCareerId() throws PollNotFoundException {
        // Arrange
        Integer careerId = 1;
        Poll poll = new Poll();
        when(pollService.getPollByCareerId(eq(careerId))).thenReturn(poll);

        // Act
        ResponseEntity<Poll> response = pollController.getPollByCareerId(careerId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(poll, response.getBody());
    }

    @Test
    void testGetPollBySubjectId() throws PollNotFoundException {
        // Arrange
        Integer subjectId = 1;
        Poll poll = new Poll();
        when(pollService.getPollBySubjectId(eq(subjectId))).thenReturn(poll);

        // Act
        ResponseEntity<Poll> response = pollController.getPollBySubjectId(subjectId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(poll, response.getBody());
    }

    @Test
    void testDeletePoll() throws PollNotFoundException {
        // Arrange
        Integer pollId = 1;
        Poll poll = new Poll();
        when(pollService.getPollById(eq(pollId))).thenReturn(poll);

        // Act
        ResponseEntity<Object> response = pollController.delete(pollId);

        // Assert
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

}
