package com.example.UCESAPI.service;

import com.example.UCESAPI.exception.notfound.PollAnswerNotFoundException;
import com.example.UCESAPI.model.PollAnswer;
import com.example.UCESAPI.model.PollQuestion;
import com.example.UCESAPI.model.PollQuestionStatistic;
import com.example.UCESAPI.model.PollResponseType;
import com.example.UCESAPI.repository.PollAnswerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.UCESAPI.utils.ModelTestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PollAnswerServiceTest {

    @Mock
    private PollAnswerRepository pollAnswerRepository;

    @Mock
    private PollQuestionStatisticService pollQuestionStatisticService;

    @InjectMocks
    private PollAnswerService pollAnswerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAdd() {
        // Arrange
        PollAnswer simulatedAnswer = somePollAnswer();
        PollQuestionStatistic pollQuestionStatistic = somePollQuestionStatistic();
        when(pollQuestionStatisticService.getByPollQuestion(any())).thenReturn(pollQuestionStatistic);
        when(pollAnswerRepository.save(any())).thenReturn(simulatedAnswer);

        // Act
        PollAnswer result = pollAnswerService.add(simulatedAnswer);

        // Assert
        verify(pollAnswerRepository, times(1)).save(simulatedAnswer);
        assertEquals(simulatedAnswer, result);
    }

    @Test
    public void testAddRatingToFive() {
        // Arrange
        PollQuestion pollQuestion = somePollQuestion();
        pollQuestion.setPollResponseType(PollResponseType.RATING_TO_FIVE);
        PollAnswer simulatedAnswer = somePollAnswer();
        simulatedAnswer.setPollQuestion(pollQuestion);
        PollQuestionStatistic pollQuestionStatistic = somePollQuestionStatistic();
        when(pollQuestionStatisticService.getByPollQuestion(any())).thenReturn(pollQuestionStatistic);
        when(pollAnswerRepository.save(any())).thenReturn(simulatedAnswer);

        // Act
        PollAnswer result = pollAnswerService.add(simulatedAnswer);

        // Assert
        verify(pollAnswerRepository, times(1)).save(simulatedAnswer);
        assertEquals(simulatedAnswer, result);
    }

    @Test
    public void testAddBoolNegativeResponse() {
        // Arrange
        PollAnswer simulatedAnswer = somePollAnswer();
        simulatedAnswer.setBoolResponse(false);
        PollQuestionStatistic pollQuestionStatistic = somePollQuestionStatistic();
        when(pollQuestionStatisticService.getByPollQuestion(any())).thenReturn(pollQuestionStatistic);
        when(pollAnswerRepository.save(any())).thenReturn(simulatedAnswer);

        // Act
        PollAnswer result = pollAnswerService.add(simulatedAnswer);

        // Assert
        verify(pollAnswerRepository, times(1)).save(simulatedAnswer);
        assertEquals(simulatedAnswer, result);
    }

    @Test
    public void testAddAll() {
        // Arrange
        List<PollAnswer> simulatedAnswers = Arrays.asList(somePollAnswer());
        PollQuestionStatistic pollQuestionStatistic = somePollQuestionStatistic();
        when(pollQuestionStatisticService.getByPollQuestion(any())).thenReturn(pollQuestionStatistic);
        when(pollAnswerRepository.saveAll(any())).thenReturn(simulatedAnswers);

        // Act
        List<PollAnswer> result = pollAnswerService.addAll(simulatedAnswers);

        // Assert
        verify(pollAnswerRepository, times(1)).saveAll(simulatedAnswers);
        assertEquals(simulatedAnswers, result);
    }

    @Test
    public void testGetAll() {
        // Arrange
        List<PollAnswer> simulatedAnswers = Arrays.asList(somePollAnswer());
        when(pollAnswerRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(simulatedAnswers));

        // Act
        Page<PollAnswer> result = pollAnswerService.getAll(PageRequest.of(0, 10));

        // Assert
        verify(pollAnswerRepository, times(1)).findAll(any(Pageable.class));
        assertEquals(simulatedAnswers, result.getContent());
    }

    @Test
    public void testGetById() throws PollAnswerNotFoundException {
        // Arrange
        PollAnswer simulatedAnswer = somePollAnswer();
        when(pollAnswerRepository.findById(anyInt())).thenReturn(Optional.of(simulatedAnswer));

        // Act
        PollAnswer result = pollAnswerService.getById(1);

        // Assert
        verify(pollAnswerRepository, times(1)).findById(1);
        assertEquals(simulatedAnswer, result);
    }



    @Test
    public void testDeleteById() throws PollAnswerNotFoundException {
        // Llamar al método deleteById
        pollAnswerService.deleteById(1);

        // Verificar que se llamó al método deleteById en el PollAnswerRepository
        verify(pollAnswerRepository, times(1)).deleteById(1);
    }



    @Test
    public void testUpdate() throws PollAnswerNotFoundException {
        PollAnswer simulatedAnswer = new PollAnswer();
        PollAnswer updatedAnswer = new PollAnswer();

        // Configurar el PollAnswerRepository para devolver la respuesta simulada
        when(pollAnswerRepository.findById(anyInt())).thenReturn(Optional.of(simulatedAnswer));

        // Llamar al método update
        pollAnswerService.update(1, updatedAnswer);

        // Verificar que se llamó al método save en el PollAnswerRepository
        verify(pollAnswerRepository, times(1)).save(updatedAnswer);
    }

    @Test
    public void testGetByIdThrowsPollAnswerNotFoundException() {
        // Arrange
        when(pollAnswerRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PollAnswerNotFoundException.class, () -> pollAnswerService.getById(1));
    }

    /*@Test
    public void testDeleteByIdThrowsPollAnswerNotFoundException() {
        // Arrange
        //doThrow(PollAnswerNotFoundException.class).when(pollAnswerRepository).deleteById(anyInt());
        when(pollAnswerRepository.findById(anyInt())).thenReturn(Optional.empty());

        //doThrow(PollAnswerNotFoundException.class).when(pollAnswerRepository).findById(anyInt());

        // Act & Assert
        assertThrows(PollAnswerNotFoundException.class, () -> pollAnswerService.deleteById(1));
    }*/

    @Test
    public void testUpdateThrowsPollAnswerNotFoundException() {
        // Arrange
        when(pollAnswerRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PollAnswerNotFoundException.class, () -> pollAnswerService.update(1, somePollAnswer()));
    }


}
