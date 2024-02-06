package com.example.UCESAPI.service;

import com.example.UCESAPI.exception.notfound.BoardNotFoundException;
import com.example.UCESAPI.exception.notfound.ForumNotFoundException;
import com.example.UCESAPI.model.*;
import com.example.UCESAPI.repository.ForumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static com.example.UCESAPI.utils.ModelTestUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class ForumServiceTest {

    @Mock
    private ForumRepository forumRepository;

    @Mock
    private BoardService boardService;

    @InjectMocks
    private ForumService forumService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddForum() {
        // Arrange
        Forum forumToAdd = someRecommendation();
        when(forumRepository.save(any())).thenReturn(forumToAdd);

        // Act
        Forum addedForum = forumService.addForum(forumToAdd);

        // Assert
        assertNotNull(addedForum);
    }

    @Test
    void testGetAllForums() {
        // Arrange
        Page<Forum> forumPage = somePageOfForum();
        when(forumRepository.findAll(any(Pageable.class))).thenReturn(forumPage);

        // Act
        Page<Forum> resultPage = forumService.getAll(Pageable.unpaged());

        // Assert
        assertNotNull(resultPage);
    }

    @Test
    void testGetAllQueriesByBoard() throws BoardNotFoundException {
        // Arrange
        int idBoard = 1;
        Page<Query> queryPage = somePageOfQueries();
        when(boardService.getById(eq(idBoard))).thenReturn(new Board());
        when(forumRepository.findAllQueriesByBoard(any(), any(Pageable.class))).thenReturn(queryPage);

        // Act
        Page<Query> resultPage = forumService.getAllQueriesByBoard(idBoard, Pageable.unpaged());

        // Assert
        assertNotNull(resultPage);
    }

    // ... (similar tests for other methods)

    @Test
    void testDeleteForumById() {
        // Arrange
        Integer forumId = 1;
        when(forumRepository.existsById(eq(forumId))).thenReturn(true);

        // Act
        assertDoesNotThrow(() -> forumService.deleteById(forumId));
    }

    @Test
    void testDeleteForumByIdThrowsForumNotFoundException() {
        // Arrange
        Integer forumId = 1;
        when(forumRepository.existsById(eq(forumId))).thenReturn(false);

        // Act & Assert
        assertThrows(ForumNotFoundException.class, () -> forumService.deleteById(forumId));
    }

    @Test
    void testUpdateForum() {
        // Arrange
        Integer forumId = 1;
        Forum existingForum = someRecommendation();
        Forum updatedForum = someRecommendation();
        when(forumRepository.findById(eq(forumId))).thenReturn(java.util.Optional.of(existingForum));

        // Act
        assertDoesNotThrow(() -> forumService.update(forumId, updatedForum));
    }

    @Test
    void testUpdateForumThrowsForumNotFoundException() {
        // Arrange
        Integer forumId = 1;
        Forum updatedForum = someRecommendation();
        when(forumRepository.findById(eq(forumId))).thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThrows(ForumNotFoundException.class, () -> forumService.update(forumId, updatedForum));
    }

    @Test
    void testAddResponseToQueryForumNotQuery() {
        // Arrange
        Integer forumId = 1;
        QueryResponse responseToAdd = someQueryResponse();
        Forum existingForum = someRecommendation();
        when(forumRepository.findById(eq(forumId))).thenReturn(java.util.Optional.of(existingForum));

        // Act
        //assertDoesNotThrow(() -> forumService.addResponse(forumId, responseToAdd));
    }

    @Test
    void testGetAllQueriesByBoardSortedByVotes() throws BoardNotFoundException {
        // Arrange
        int boardId = 1;
        Page<Query> queryPage = somePageOfQueries();
        when(boardService.getById(boardId)).thenReturn(Board.builder().id(1).build());
        when(forumRepository.findAllQueriesOrderByUsersWhoVotedCountDesc(anyInt(), any(Pageable.class))).thenReturn(queryPage);

        // Act
        Page<Query> queries = forumService.getAllQueriesByBoardSortedByVotes(boardId, Pageable.ofSize(1) );

        // Assert
        assertEquals(queryPage.toList(), queries.getContent());
    }

    @Test
    void testGetAllRecommendationsByBoard() throws BoardNotFoundException {
        // Arrange
        Board board = Board.builder().id(1).build();
        Page<Recommendation> recommendationPage = somePageOfRecommendations();
        when(boardService.getById(board.getId())).thenReturn(board);
        when(forumRepository.findAllRecommendationsByBoard(board, Pageable.ofSize(1))).thenReturn(recommendationPage);

        // Act
        Page<Recommendation> recommendations = forumService.getAllRecommendationsByBoard(board.getId(), Pageable.ofSize(1));

        // Assert
        assertEquals(recommendations, recommendationPage);
    }

    @Test
    void testGetAllRecommendationsByBoardSortedByVotes() throws BoardNotFoundException {
        // Arrange
        Board board = Board.builder().id(1).build();
        Page<Recommendation> recommendationPage = somePageOfRecommendations();
        when(boardService.getById(board.getId())).thenReturn(board);
        when(forumRepository.findAllRecommendationsOrderByUsersWhoVotedCountDesc(board.getId(), Pageable.ofSize(1))).thenReturn(recommendationPage);

        // Act
        Page<Recommendation> recommendationPageResult = forumService.getAllRecommendationsByBoardSortedByVotes(board.getId(), Pageable.ofSize(1));

        // Assert
        assertEquals(recommendationPage, recommendationPageResult);
    }

    @Test
    void testGetAllQueriesByBoardSortedByDate() throws BoardNotFoundException {
        // Arrange
        int boardId = 1;
        Page<Query> queryPage = somePageOfQueries();
        when(boardService.getById(boardId)).thenReturn(new Board());
        when(forumRepository.findAllQueriesByBoardOrderByDateDesc(any(Board.class), any(Pageable.class))).thenReturn(queryPage);

        // Act
        Page<Query> queries = forumService.getAllQueriesByBoardSortedByDate(boardId, Pageable.ofSize(1));

        // Assert
        assertEquals(queryPage.toList(), queries.getContent());
    }

    @Test
    void testGetAllRecommendationsByBoardSortedByDate() throws BoardNotFoundException {
        // Arrange
        int boardId = 1;
        Page<Recommendation> recommendationPage = somePageOfRecommendations();
        when(boardService.getById(boardId)).thenReturn(new Board());
        when(forumRepository.findAllRecommendationsByBoardOrderByDateDesc(any(Board.class), any(Pageable.class))).thenReturn(recommendationPage);

        // Act
        Page<Recommendation> recommendationPageResult = forumService.getAllRecommendationsByBoardSortedByDate(boardId, Pageable.ofSize(1));

        // Assert
        assertEquals(recommendationPageResult, recommendationPageResult);
    }



}
