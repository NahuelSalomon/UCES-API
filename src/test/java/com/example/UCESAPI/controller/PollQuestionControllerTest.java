package com.example.UCESAPI.controller;
import com.example.UCESAPI.exception.notfound.PollNotFoundException;
import com.example.UCESAPI.model.Poll;
import com.example.UCESAPI.model.PollQuestion;
import com.example.UCESAPI.service.PollQuestionService;
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
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;


public class PollQuestionControllerTest {

    @Mock
    private PollQuestionService pollQuestionService;

    @Mock
    private PollService pollService;

    @InjectMocks
    private PollQuestionController pollQuestionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddPollQuestion() {
        // Arrange
        PollQuestion pollQuestionToAdd = new PollQuestion(/* create a PollQuestion object with necessary data */);
        when(pollQuestionService.add(any(PollQuestion.class))).thenReturn(pollQuestionToAdd);

        // Act
        ResponseEntity<Object> response = pollQuestionController.add(pollQuestionToAdd);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(pollQuestionToAdd, response.getBody());
    }

    @Test
    void testAddAllPollQuestions() {
        // Arrange
        List<PollQuestion> pollQuestionListToAdd = Arrays.asList(
                new PollQuestion(/* create a PollQuestion object with necessary data */),
                new PollQuestion(/* create another PollQuestion object with necessary data */)
        );
        when(pollQuestionService.addAll(anyList())).thenReturn(pollQuestionListToAdd);

        // Act
        ResponseEntity<Object> response = pollQuestionController.addAll(pollQuestionListToAdd);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(pollQuestionListToAdd, response.getBody());
    }

    @Test
    void testGetAllByPoll() throws PollNotFoundException {
        // Arrange
        Integer pollId = 1;
        Poll poll = new Poll(/* create a Poll object with necessary data */);
        List<PollQuestion> pollQuestionList = Arrays.asList(
                new PollQuestion(/* create a PollQuestion object with necessary data */),
                new PollQuestion(/* create another PollQuestion object with necessary data */)
        );
        when(pollService.getPollById(eq(pollId))).thenReturn(poll);
        when(pollQuestionService.getAllByPoll(eq(poll))).thenReturn(pollQuestionList);

        // Act
        ResponseEntity<List<PollQuestion>> response = pollQuestionController.getAllByPoll(pollId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pollQuestionList, response.getBody());
    }

    @Test
    void testDeletePollQuestion() {
        // Arrange
        Integer pollQuestionId = 1;
        PollQuestion pollQuestion = new PollQuestion(/* create a PollQuestion object with necessary data */);
        when(pollQuestionService.getById(eq(pollQuestionId))).thenReturn(pollQuestion);

        // Act
        ResponseEntity<Object> response = pollQuestionController.delete(pollQuestionId);

        // Assert
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        verify(pollQuestionService, times(1)).delete(eq(pollQuestion));
    }

}
