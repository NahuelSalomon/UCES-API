package com.example.UCESAPI.service;

import com.example.UCESAPI.exception.notfound.ResponseQueryNotFoundException;
import com.example.UCESAPI.model.QueryResponse;
import com.example.UCESAPI.repository.ResponseQueryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static com.example.UCESAPI.utils.ModelTestUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class QueryResponseServiceTest {

    @Mock
    private ResponseQueryRepository responseRepository;

    @InjectMocks
    private QueryResponseService queryResponseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAdd() {
        // Arrange
        QueryResponse response = someQueryResponse();
        when(responseRepository.save(response)).thenReturn(response);

        // Act
        QueryResponse addedResponse = queryResponseService.add(response);

        // Assert
        assertNotNull(addedResponse);
        assertSame(response, addedResponse);
        verify(responseRepository, times(1)).save(response);
    }

    @Test
    void testGetAllByQuery() {
        // Arrange
        int queryId = 1;
        Page<QueryResponse> page =  someQueryResponsePage();
        Pageable pageable = somePageable();
        when(responseRepository.findResponseQueriesByQueryId(queryId, pageable)).thenReturn(page);

        // Act
        Page<QueryResponse> result = queryResponseService.getAllByQuery(queryId, pageable);

        // Assert
        assertNotNull(result);
        assertSame(page, result);
    }

    @Test
    void testGetByIdWhenResponseExists() throws ResponseQueryNotFoundException {
        // Arrange
        int responseId = 1;
        QueryResponse response = someQueryResponse();
        when(responseRepository.findById(responseId)).thenReturn(Optional.of(response));

        // Act
        QueryResponse result = queryResponseService.getById(responseId);

        // Assert
        assertNotNull(result);
        assertSame(response, result);
        verify(responseRepository, times(1)).findById(responseId);
    }

    @Test
    void testGetByIdWhenResponseDoesNotExist() {
        // Arrange
        int responseId = 1;
        when(responseRepository.findById(responseId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseQueryNotFoundException.class, () -> queryResponseService.getById(responseId));
        verify(responseRepository, times(1)).findById(responseId);
    }

    @Test
    void testDelete() {
        // Arrange
        int responseId = 1;

        // Act
        queryResponseService.delete(responseId);

        // Assert
        verify(responseRepository, times(1)).deleteById(responseId);
    }

}
