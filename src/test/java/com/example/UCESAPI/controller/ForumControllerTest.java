package com.example.UCESAPI.controller;

import com.example.UCESAPI.exception.AccessNotAllowedException;
import com.example.UCESAPI.exception.notfound.BoardNotFoundException;
import com.example.UCESAPI.exception.notfound.ForumNotFoundException;
import com.example.UCESAPI.model.Forum;
import com.example.UCESAPI.model.User;
import com.example.UCESAPI.model.dto.forum.QueryResponseDto;
import com.example.UCESAPI.model.dto.forum.RecommendationResponseDto;
import com.example.UCESAPI.service.ForumService;
import com.example.UCESAPI.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;
import static com.example.UCESAPI.utils.ModelTestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ForumControllerTest {

    @Mock
    private ForumService forumService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ForumController forumController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddForum() throws AccessNotAllowedException {
        // Arrange
        Forum forum = someRecommendation();
        User user = someUser();
        Authentication authentication = someAuthentication();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(userService.getByEmail(eq(user.getEmail()))).thenReturn(user);
        when(forumService.addForum(eq(forum))).thenReturn(forum);

        // Act
        ResponseEntity<Forum> response = forumController.add(forum, authentication);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testAddForumThrowsAccessNotAllowedException() {
        // Arrange
        Forum forum = someRecommendation();
        User user = someUser();
        user.setId(2);
        Authentication authentication = someAuthentication();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // When
        when(userService.getByEmail(eq(user.getEmail()))).thenReturn(user);

        // Act y Assert
        assertThrows(AccessNotAllowedException.class, () -> forumController.add(forum, authentication));
    }

    @Test
    void testGetAllQueriesByBoard() throws BoardNotFoundException {
        // Arrange
        int idBoard = 1;
        when(forumService.getAllQueriesByBoard(eq(idBoard), any())).thenReturn(somePageOfQueries());

        // Act
        ResponseEntity<List<QueryResponseDto>> response = forumController.getAllQueriesByBoard(idBoard, somePageable());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetAllRecommendationsByBoard() throws BoardNotFoundException {
        // Arrange
        int idBoard = 1;
        when(forumService.getAllRecommendationsByBoard(eq(idBoard), any())).thenReturn(somePageOfRecommendations());

        // Act
        ResponseEntity<List<RecommendationResponseDto>> response = forumController.getAllRecommendationsByBoard(idBoard, somePageable());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetAllQueriesByBoardSortedByVotes() throws BoardNotFoundException {
        // Arrange
        int idBoard = 1;
        when(forumService.getAllQueriesByBoardSortedByVotes(eq(idBoard), any())).thenReturn(somePageOfQueries());

        // Act
        ResponseEntity<List<QueryResponseDto>> response = forumController.getAllQueriesByBoardSortedByVotes(idBoard, somePageable());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetAllQueriesByBoardSortedByDates() throws BoardNotFoundException {
        // Arrange
        int idBoard = 1;
        when(forumService.getAllQueriesByBoardSortedByDate(eq(idBoard), any())).thenReturn(somePageOfQueries());

        // Act
        ResponseEntity<List<QueryResponseDto>> response = forumController.getAllQueriesByBoardSortedByDates(idBoard, somePageable());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetAllRecommendationsByBoardSortedByVotes() throws BoardNotFoundException {
        // Arrange
        int idBoard = 1;
        when(forumService.getAllRecommendationsByBoardSortedByVotes(eq(idBoard), any())).thenReturn(somePageOfRecommendations());

        // Act
        ResponseEntity<List<RecommendationResponseDto>> response = forumController.getAllRecommendationsByBoardSortedByVotes(idBoard, somePageable());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetAllRecommendationsByBoardSortedByDates() throws BoardNotFoundException {
        // Arrange
        int idBoard = 1;
        when(forumService.getAllRecommendationsByBoardSortedByDate(eq(idBoard), any())).thenReturn(somePageOfRecommendations());

        // Act
        ResponseEntity<List<RecommendationResponseDto>> response = forumController.getAllRecommendationsByBoardSortedByDates(idBoard, somePageable());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetById() throws ForumNotFoundException {
        // Arrange
        int forumId = 1;
        when(forumService.getById(eq(forumId))).thenReturn(someForum());

        // Act
        ResponseEntity<Forum> response = forumController.getById(forumId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteForum() throws ForumNotFoundException {
        // Arrange
        int forumId = 1;
        Mockito.doNothing().when(forumService).deleteById(eq(forumId));

        // Act
        ResponseEntity<Object> response = forumController.deleteForum(forumId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }



}
