package com.example.UCESAPI.controller;

import com.example.UCESAPI.exception.notfound.CareerNotFoundException;
import com.example.UCESAPI.exception.notfound.SubjectNotFoundException;
import com.example.UCESAPI.model.Board;
import com.example.UCESAPI.model.Subject;
import com.example.UCESAPI.service.BoardService;
import com.example.UCESAPI.service.SubjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SubjectControllerTest {

    @Mock
    private SubjectService subjectService;

    @Mock
    private BoardService boardService;

    @InjectMocks
    private SubjectController subjectController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddSubject() {
        // Arrange
        Subject subjectToAdd = new Subject(/* create a Subject object with necessary data */);
        when(subjectService.add(any(Subject.class))).thenReturn(subjectToAdd);
        when(boardService.addBoard(any(Board.class))).thenReturn(new Board(/* create a Board object with necessary data */));

        // Act
        ResponseEntity<Subject> response = subjectController.add(subjectToAdd);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(subjectService, times(1)).add(eq(subjectToAdd));
        verify(boardService, times(1)).addBoard(any(Board.class));
    }

    @Test
    void testGetAllSubjects() {
        // Arrange
        List<Subject> subjectList = Arrays.asList(new Subject(/* create a Subject object with necessary data */));
        Page<Subject> subjectPage = new PageImpl<>(subjectList);
        when(subjectService.getAll(any())).thenReturn(subjectPage);

        // Act
        ResponseEntity<List<Subject>> response = subjectController.getAll(10, 0);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subjectList, response.getBody());
    }

    @Test
    void testGetSubjectById() throws SubjectNotFoundException {
        // Arrange
        Integer subjectId = 1;
        Subject subject = new Subject(/* create a Subject object with necessary data */);
        when(subjectService.getById(eq(subjectId))).thenReturn(subject);

        // Act
        ResponseEntity<Subject> response = subjectController.getById(subjectId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subject, response.getBody());
    }

    @Test
    void testGetSubjectsByCareer() throws CareerNotFoundException {
        // Arrange
        Integer careerId = 1;
        List<Subject> subjectList = Arrays.asList(new Subject(/* create a Subject object with necessary data */));
        when(subjectService.getByCareer(eq(careerId))).thenReturn(subjectList);

        // Act
        ResponseEntity<List<Subject>> response = subjectController.getByCareer(careerId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subjectList, response.getBody());
    }

    @Test
    void testGetSubjectByNameAndCareerId() throws SubjectNotFoundException {
        // Arrange
        String subjectName = "ExampleSubject";
        Integer careerId = 1;
        Subject subject = new Subject(/* create a Subject object with necessary data */);
        when(subjectService.getByNameAndCareerId(eq(subjectName), eq(careerId))).thenReturn(subject);

        // Act
        ResponseEntity<Subject> response = subjectController.getByNameAndCareerId(subjectName, careerId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subject, response.getBody());
    }

    @Test
    void testDeleteSubjectById() {
        // Arrange
        Integer subjectId = 1;

        // Act
        ResponseEntity<Object> response = subjectController.deleteById(subjectId);

        // Assert
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        verify(subjectService, times(1)).deleteById(eq(subjectId));
    }

}
