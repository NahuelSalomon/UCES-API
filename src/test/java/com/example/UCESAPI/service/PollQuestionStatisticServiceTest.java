package com.example.UCESAPI.service;

import static com.example.UCESAPI.utils.ModelTestUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import com.example.UCESAPI.model.Poll;
import com.example.UCESAPI.model.PollQuestion;
import com.example.UCESAPI.model.PollQuestionStatistic;
import com.example.UCESAPI.repository.PollQuestionStatisticRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PollQuestionStatisticServiceTest {
    @Mock
    private PollQuestionStatisticRepository pollQuestionStatisticRepository;

    @Mock
    private PollQuestionService pollQuestionService;

    @Mock
    private PollService pollService;

    @InjectMocks
    private PollQuestionStatisticService pollQuestionStatisticService;

    @BeforeEach
    void setUp() {
        // Resetear mocks antes de cada test
        MockitoAnnotations.openMocks(this);;
    }

    @Test
    void testSave() {
        // Arrange
        PollQuestionStatistic pollQuestionStatistic = somePollQuestionStatistic();
        when(pollQuestionStatisticRepository.save(any())).thenReturn(pollQuestionStatistic);

        // Act
        PollQuestionStatistic savedStatistic = pollQuestionStatisticService.save(pollQuestionStatistic);

        // Assert
        assertNotNull(savedStatistic);
        assertEquals(pollQuestionStatistic, savedStatistic);
    }

    @Test
    void testGetByPollQuestion() {
        // Arrange
        PollQuestion pollQuestion = somePollQuestion();
        PollQuestionStatistic pollQuestionStatistic = somePollQuestionStatistic();
        when(pollQuestionStatisticRepository.findByPollQuestion(any())).thenReturn(pollQuestionStatistic);

        // Act
        PollQuestionStatistic retrievedStatistic = pollQuestionStatisticService.getByPollQuestion(pollQuestion);

        // Assert
        assertNotNull(retrievedStatistic);
        assertEquals(pollQuestionStatistic, retrievedStatistic);
    }

    @Test
    void testGetAllByPoll() {
        // Arrange
        Poll poll = somePoll();
        List<PollQuestionStatistic> expectedStatistics = List.of(somePollQuestionStatistic());
        when(pollQuestionStatisticRepository.findAllByPollId(any())).thenReturn(expectedStatistics);

        // Act
        List<PollQuestionStatistic> retrievedStatistics = pollQuestionStatisticService.getAllByPoll(poll);

        // Assert
        assertNotNull(retrievedStatistics);
        assertEquals(expectedStatistics, retrievedStatistics);
    }

}
