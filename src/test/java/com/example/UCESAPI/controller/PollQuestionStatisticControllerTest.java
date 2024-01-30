package com.example.UCESAPI.controller;

import com.example.UCESAPI.exception.notfound.PollNotFoundException;
import com.example.UCESAPI.model.Poll;
import com.example.UCESAPI.model.PollQuestionStatistic;
import com.example.UCESAPI.service.PollQuestionStatisticService;
import com.example.UCESAPI.service.PollService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


public class PollQuestionStatisticControllerTest {
    @Mock
    private PollQuestionStatisticService pollQuestionStatisticService;

    @Mock
    private PollService pollService;

    @InjectMocks
    private PollQuestionStatisticController pollQuestionStatisticController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllByPoll() throws PollNotFoundException {
        // Arrange
        Integer pollId = 1;
        Poll poll = new Poll(/* create a Poll object with necessary data */);
        List<PollQuestionStatistic> pollQuestionStatisticList = Arrays.asList(
                new PollQuestionStatistic(/* create a PollQuestionStatistic object with necessary data */),
                new PollQuestionStatistic(/* create another PollQuestionStatistic object with necessary data */)
        );
        when(pollService.getPollById(eq(pollId))).thenReturn(poll);
        when(pollQuestionStatisticService.getAllByPoll(eq(poll))).thenReturn(pollQuestionStatisticList);

        // Act
        ResponseEntity<List<PollQuestionStatistic>> response = pollQuestionStatisticController.getAllByPoll(pollId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pollQuestionStatisticList, response.getBody());
    }
}
