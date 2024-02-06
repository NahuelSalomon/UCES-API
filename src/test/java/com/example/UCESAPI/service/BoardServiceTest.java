package com.example.UCESAPI.service;

import com.example.UCESAPI.exception.notfound.BoardNotFoundException;
import com.example.UCESAPI.exception.notfound.SubjectNotFoundException;
import com.example.UCESAPI.model.Board;
import com.example.UCESAPI.model.Subject;
import com.example.UCESAPI.repository.BoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


public class BoardServiceTest {

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private SubjectService subjectService;

    @InjectMocks
    private BoardService boardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddBoard() {
        // Arrange
        Board boardToAdd = new Board(/* create a Board object with necessary data */);
        when(boardRepository.save(any())).thenReturn(boardToAdd);

        // Act
        Board addedBoard = boardService.addBoard(boardToAdd);

        // Assert
        assertNotNull(addedBoard);
    }

    @Test
    void testGetAllBoards() {
        // Arrange
        List<Board> boardList = new ArrayList<>();
        boardList.add(new Board(/* create a Board object with necessary data */));
        Page<Board> boardPage = new PageImpl<>(boardList);
        when(boardRepository.findAll(any(Pageable.class))).thenReturn(boardPage);

        // Act
        Page<Board> resultPage = boardService.getAll(Pageable.unpaged());

        // Assert
        assertNotNull(resultPage);
        assertEquals(boardList.size(), resultPage.getContent().size());
    }

    @Test
    void testGetBoardById() throws BoardNotFoundException {
        // Arrange
        Integer boardId = 1;
        Board board = new Board(/* create a Board object with necessary data */);
        when(boardRepository.findById(eq(boardId))).thenReturn(Optional.of(board));

        // Act
        Board resultBoard = boardService.getById(boardId);

        // Assert
        assertNotNull(resultBoard);
    }

    @Test
    void testGetBoardBySubject() throws SubjectNotFoundException {
        // Arrange
        Integer subjectId = 1;
        Board board = new Board();
        Subject subject = new Subject();
        when(subjectService.getById(eq(subjectId))).thenReturn(subject);
        when(boardRepository.findBySubject(eq(subject))).thenReturn(board);

        // Act
        Board resultBoard = boardService.getBySubject(subjectId);

        // Assert
        assertNotNull(resultBoard);
    }

    @Test
    void testDeleteBoardById() {
        // Arrange
        Integer boardId = 1;
        when(boardRepository.existsById(eq(boardId))).thenReturn(true);

        // Act
        assertDoesNotThrow(() -> boardService.deleteById(boardId));
    }

    @Test
    void testDeleteBoardByIdThrowsBoardNotFoundException() {
        // Arrange
        Integer boardId = 1;
        when(boardRepository.existsById(eq(boardId))).thenReturn(false);

        // Act & Assert
        assertThrows(BoardNotFoundException.class, () -> boardService.deleteById(boardId));
    }

    @Test
    void testUpdateBoard() {
        // Arrange
        Integer boardId = 1;
        Board existingBoard = new Board();
        Board updatedBoard = new Board();
        when(boardRepository.findById(eq(boardId))).thenReturn(Optional.of(existingBoard));

        // Act
        assertDoesNotThrow(() -> boardService.update(boardId, updatedBoard));
    }

    @Test
    void testUpdateBoardThrowsBoardNotFoundException() {
        // Arrange
        Integer boardId = 1;
        Board updatedBoard = new Board();
        when(boardRepository.findById(eq(boardId))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BoardNotFoundException.class, () -> boardService.update(boardId, updatedBoard));
    }

}
