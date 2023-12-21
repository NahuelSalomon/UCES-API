package com.example.UCESAPI.controller;
import com.example.UCESAPI.controller.BoardController;
import com.example.UCESAPI.exception.notfound.BoardNotFoundException;
import com.example.UCESAPI.exception.notfound.SubjectNotFoundException;
import com.example.UCESAPI.model.Board;
import com.example.UCESAPI.service.BoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BoardControllerTest {

    @Mock
    private BoardService boardService;

    @InjectMocks
    private BoardController boardController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetById() throws BoardNotFoundException {
        // Arrange
        Integer boardId = 1;
        Board board = new Board();
        when(boardService.getById(eq(boardId))).thenReturn(board);

        // Act
        ResponseEntity<Board> response = boardController.getById(boardId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(board, response.getBody());
    }

    @Test
    void testGetBySubject() throws SubjectNotFoundException {
        // Arrange
        Integer subjectId = 1;
        Board board = new Board();
        when(boardService.getBySubject(eq(subjectId))).thenReturn(board);

        // Act
        ResponseEntity<Board> response = boardController.getBySubject(subjectId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(board, response.getBody());
    }

}
