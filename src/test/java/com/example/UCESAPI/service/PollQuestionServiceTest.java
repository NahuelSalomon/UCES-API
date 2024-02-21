package com.example.UCESAPI.service;
import com.example.UCESAPI.model.Poll;
import com.example.UCESAPI.model.PollQuestion;
import com.example.UCESAPI.model.PollQuestionStatistic;
import com.example.UCESAPI.repository.PollQuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.example.UCESAPI.utils.ModelTestUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PollQuestionServiceTest {

    @Mock
    private PollQuestionRepository pollQuestionRepository;

    @Mock
    private PollQuestionStatisticService pollQuestionStatisticService;

    @InjectMocks
    private PollQuestionService pollQuestionService;

    @BeforeEach
    void setUp() {
        // Resetear mocks antes de cada test
        MockitoAnnotations.openMocks(this);;
    }

    @Test
    void testAdd() {
        // Arrange
        PollQuestion pollQuestion = somePollQuestion();
        PollQuestionStatistic pollQuestionStatistic = somePollQuestionStatistic();
        when(pollQuestionRepository.save(any())).thenReturn(pollQuestion);
        when(pollQuestionStatisticService.save(any())).thenReturn(pollQuestionStatistic);

        // Act
        PollQuestion addedPollQuestion = pollQuestionService.add(pollQuestion);

        // Assert
        assertNotNull(addedPollQuestion);
        assertEquals(pollQuestion, addedPollQuestion);
        verify(pollQuestionRepository, times(1)).save(pollQuestion);
    }

    @Test
    void testAddAll() {
        // Arrange
        List<PollQuestion> pollQuestionList = List.of(somePollQuestion());
        List<PollQuestion> pollQuestionListCreated = List.of(somePollQuestion());
        PollQuestionStatistic pollQuestionStatistic = somePollQuestionStatistic();
        when(pollQuestionRepository.saveAll(pollQuestionList)).thenReturn(pollQuestionListCreated);
        when(pollQuestionStatisticService.save(any())).thenReturn(pollQuestionStatistic);

        // Act
        List<PollQuestion> addedPollQuestions = pollQuestionService.addAll(pollQuestionList);

        // Assert
        assertNotNull(addedPollQuestions);
        assertEquals(1, addedPollQuestions.size());
        assertSame(pollQuestionListCreated, addedPollQuestions);
        verify(pollQuestionRepository, times(1)).saveAll(pollQuestionList);
    }

    @Test
    void testGetAll() {
        // Arrange
        Pageable pageable = somePageable();
        Page<PollQuestion> pollQuestionPage = somePollQuestionPage();
        when(pollQuestionRepository.findAll(pageable)).thenReturn(pollQuestionPage);

        // Act
        Page<PollQuestion> result = pollQuestionService.getAll(pageable);

        // Assert
        assertNotNull(result);
        assertSame(pollQuestionPage, result);
        verify(pollQuestionRepository, times(1)).findAll(pageable);
    }

    @Test
    void testGetById() {
        // Arrange
        Integer id = 1;
        PollQuestion pollQuestion = somePollQuestion();
        when(pollQuestionRepository.findById(id)).thenReturn(Optional.of(pollQuestion));

        // Act
        PollQuestion result = pollQuestionService.getById(id);

        // Assert
        assertNotNull(result);
        assertSame(pollQuestion, result);
        verify(pollQuestionRepository, times(1)).findById(id);
    }

    @Test
    void testGetAllByPoll() {
        // Arrange
        Poll poll = new Poll();
        List<PollQuestion> pollQuestionList = somePollQuestionList();
        when(pollQuestionRepository.findAllByPoll(poll)).thenReturn(pollQuestionList);

        // Act
        List<PollQuestion> result = pollQuestionService.getAllByPoll(poll);

        // Assert
        assertNotNull(result);
        assertSame(pollQuestionList, result);
        verify(pollQuestionRepository, times(1)).findAllByPoll(poll);
    }

    @Test
    void testUpdate() {
        // Arrange
        PollQuestion pollQuestionToUpdate = somePollQuestion();
        PollQuestion existingPollQuestion = somePollQuestion();
        when(pollQuestionRepository.findById(any())).thenReturn(Optional.of(existingPollQuestion));
        when(pollQuestionRepository.save(pollQuestionToUpdate)).thenReturn(pollQuestionToUpdate);

        // Act
        PollQuestion result = pollQuestionService.update(pollQuestionToUpdate);

        // Assert
        assertNotNull(result);
        assertSame(pollQuestionToUpdate, result);
        verify(pollQuestionRepository, times(1)).findById(any());
        verify(pollQuestionRepository, times(1)).save(pollQuestionToUpdate);
    }

    @Test
    void testDelete() {
        // Arrange
        PollQuestion pollQuestion = somePollQuestion();

        // Act
        pollQuestionService.delete(pollQuestion);

        // Assert
        verify(pollQuestionRepository, times(1)).delete(pollQuestion);
    }

}
