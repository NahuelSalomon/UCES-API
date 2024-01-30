package com.example.UCESAPI.controller;

import com.example.UCESAPI.exception.AccessNotAllowedException;
import com.example.UCESAPI.exception.notfound.ForumNotFoundException;
import com.example.UCESAPI.exception.notfound.UserNotFoundException;
import com.example.UCESAPI.model.Forum;
import com.example.UCESAPI.model.User;
import com.example.UCESAPI.model.UserType;
import com.example.UCESAPI.model.dto.user.UserResponseDto;
import com.example.UCESAPI.model.dto.user.UserUpdateRequestDto;
import com.example.UCESAPI.model.mapper.CustomConversion;
import com.example.UCESAPI.service.ForumService;
import com.example.UCESAPI.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.UCESAPI.utils.ModelTestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private ForumService forumService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ConversionService conversionService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        List<User> userList = Arrays.asList(someUser());
        Page<User> userPage = new PageImpl<>(userList);
        when(userService.getAll(any())).thenReturn(userPage);

        // Act
        ResponseEntity<List<UserResponseDto>> response = userController.getAll(10, 0);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetUserById() throws UserNotFoundException {
        // Arrange
        User user = someUser();
        when(userService.getById(eq(user.getId()))).thenReturn(user);

        // Act
        ResponseEntity<UserResponseDto> response = userController.getById(user.getId());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(CustomConversion.UserToUserResponseDto(user), response.getBody());
    }

    @Test
    void testGetUserByEmail() {
        // Arrange
        User user = someUser();
        String userEmail = user.getEmail();
        when(userService.getByEmail(eq(userEmail))).thenReturn(user);

        // Act
        ResponseEntity<UserResponseDto> response = userController.getByEmail(userEmail);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(CustomConversion.UserToUserResponseDto(user), response.getBody());
    }

    @Test
    void testGetUserByEmailNotFound() {
        // Arrange
        User user = someUser();
        String userEmail = user.getEmail();
        when(userService.getByEmail(eq(userEmail))).thenReturn(null);

        // Act
        ResponseEntity<UserResponseDto> response = userController.getByEmail(userEmail);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        //assertEquals(CustomConversion.UserToUserResponseDto(user), response.getBody());
    }

    // ... (continue with tests for other methods)

    @Test
    void testUnVotedForum() throws UserNotFoundException, AccessNotAllowedException, ForumNotFoundException {
        // Arrange
        Integer userId = 1;
        Integer forumId = 1;
        User user = someUser();
        Forum forum = someQuery();
        forum.setUsersWhoVoted(new ArrayList<>(List.of(user)));
        user.setForumsVoted(new ArrayList<>(List.of(forum)));
        Authentication authentication = someAuthentication();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(userService.getById(eq(userId))).thenReturn(user);
        when(userService.getByEmail(anyString())).thenReturn(user);
        when(forumService.getById(eq(forumId))).thenReturn(forum);

        // Act
        ResponseEntity<Object> response = userController.votedUnVoteForum(userId, forumId, authentication);

        // Assert
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        verify(userService, times(1)).update(eq(userId), any(User.class));
    }

    @Test
    void testVotedForum() throws UserNotFoundException, AccessNotAllowedException, ForumNotFoundException {
        // Arrange
        Integer userId = 1;
        Integer forumId = 1;
        User user = someUser();
        Forum forum = someQuery();
        forum.setUsersWhoVoted(new ArrayList<>());
        user.setForumsVoted(new ArrayList<>());
        Authentication authentication = someAuthentication();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(userService.getById(eq(userId))).thenReturn(user);
        when(userService.getByEmail(anyString())).thenReturn(user);
        when(forumService.getById(eq(forumId))).thenReturn(forum);

        // Act
        ResponseEntity<Object> response = userController.votedUnVoteForum(userId, forumId, authentication);

        // Assert
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        verify(userService, times(1)).update(eq(userId), any(User.class));
    }

    @Test
    void testVotedForumAdmin() throws UserNotFoundException, AccessNotAllowedException, ForumNotFoundException {
        // Arrange
        Integer userId = 1;
        Integer forumId = 1;
        User user = someUser();
        User authenticatedUser = someUser();
        authenticatedUser.setId(2);
        authenticatedUser.setUserType(UserType.ROLE_ADMINISTRATOR);
        Forum forum = someQuery();
        forum.setUsersWhoVoted(new ArrayList<>());
        user.setForumsVoted(new ArrayList<>());
        Authentication authentication = someAuthentication();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(userService.getById(eq(userId))).thenReturn(user);
        when(userService.getByEmail(anyString())).thenReturn(authenticatedUser);
        when(forumService.getById(eq(forumId))).thenReturn(forum);

        // Act
        ResponseEntity<Object> response = userController.votedUnVoteForum(userId, forumId, authentication);

        // Assert
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        verify(userService, times(1)).update(eq(userId), any(User.class));
    }


    @Test
    void testUpdateUser() throws UserNotFoundException, AccessNotAllowedException {
        // Arrange
        UserUpdateRequestDto updateUserRequestDto = new UserUpdateRequestDto();
        User userToUpdate = someUser();
        Authentication authentication = someAuthentication();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(userService.getById(eq(userToUpdate.getId()))).thenReturn(userToUpdate);
        when(userService.getByEmail(anyString())).thenReturn(userToUpdate);

        // Act
        ResponseEntity<Object> response = userController.update(userToUpdate.getId(), updateUserRequestDto, authentication);

        // Assert
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        verify(userService, times(1)).update(eq(userToUpdate.getId()), any(User.class));
    }

    @Test
    void testUpdateUserAdmin() throws UserNotFoundException, AccessNotAllowedException {
        // Arrange
        UserUpdateRequestDto updateUserRequestDto = new UserUpdateRequestDto();
        User userToUpdate = someUser();
        User authenticatedUser = someUser();
        authenticatedUser.setId(2);
        authenticatedUser.setUserType(UserType.ROLE_ADMINISTRATOR);
        Authentication authentication = someAuthentication();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(userService.getById(eq(userToUpdate.getId()))).thenReturn(userToUpdate);
        when(userService.getByEmail(anyString())).thenReturn(authenticatedUser);

        // Act
        ResponseEntity<Object> response = userController.update(userToUpdate.getId(), updateUserRequestDto, authentication);

        // Assert
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        verify(userService, times(1)).update(eq(userToUpdate.getId()), any(User.class));
    }

    @Test
    void testConfirmEmail() throws UserNotFoundException, AccessNotAllowedException {
        // Arrange
        User user = someUser();
        Authentication authentication = someAuthentication();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(userService.getById(eq(user.getId()))).thenReturn(user);
        when(userService.getByEmail(anyString())).thenReturn(user);

        // Act
        ResponseEntity<Object> response = userController.confirmEmail(user.getId(), authentication);

        // Assert
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        verify(userService, times(1)).update(eq(user.getId()), any(User.class));
    }

    @Test
    void testConfirmEmailAdmin() throws UserNotFoundException, AccessNotAllowedException {
        // Arrange
        User user = someUser();
        User authenticatedUser = someUser();
        authenticatedUser.setId(2);
        authenticatedUser.setUserType(UserType.ROLE_ADMINISTRATOR);
        Authentication authentication = someAuthentication();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(userService.getById(eq(user.getId()))).thenReturn(user);
        when(userService.getByEmail(anyString())).thenReturn(authenticatedUser);

        // Act
        ResponseEntity<Object> response = userController.confirmEmail(user.getId(), authentication);

        // Assert
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        verify(userService, times(1)).update(eq(user.getId()), any(User.class));
    }

    @Test
    void testResetPassword() throws UserNotFoundException, AccessNotAllowedException {
        // Arrange
        User user = someUser();
        Authentication authentication = someAuthentication();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(userService.getById(eq(user.getId()))).thenReturn(user);
        when(userService.getByEmail(anyString())).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // Act
        ResponseEntity<Object> response = userController.resetPassword(user.getId(), user.getPassword(), authentication);

        // Assert
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        verify(userService, times(1)).update(eq(user.getId()), any(User.class));
    }

    @Test
    void testResetPasswordAdmin() throws UserNotFoundException, AccessNotAllowedException {
        // Arrange
        User user = someUser();
        User authenticatedUser = someUser();
        authenticatedUser.setId(2);
        authenticatedUser.setUserType(UserType.ROLE_ADMINISTRATOR);
        Authentication authentication = someAuthentication();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(userService.getById(eq(user.getId()))).thenReturn(user);
        when(userService.getByEmail(anyString())).thenReturn(authenticatedUser);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // Act
        ResponseEntity<Object> response = userController.resetPassword(user.getId(), user.getPassword(), authentication);

        // Assert
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        verify(userService, times(1)).update(eq(user.getId()), any(User.class));
    }

    @Test
    void testChangeState() throws UserNotFoundException {
        // Arrange
        User user = someUser();
        when(userService.getById(eq(user.getId()))).thenReturn(user);

        // Act
        ResponseEntity<Object> response = userController.changeState(user.getId());

        // Assert
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        verify(userService, times(1)).changeState(any(User.class));
    }

    @Test
    void testUpdateUserThrowsAccessNotAllowedException() throws UserNotFoundException {
        // Arrange
        UserUpdateRequestDto updateUserRequestDto = new UserUpdateRequestDto();
        User userToUpdate = someUser();
        User authenticatedUser = someUser();
        authenticatedUser.setId(2);
        Authentication authentication = someAuthentication();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(userService.getById(eq(userToUpdate.getId()))).thenReturn(userToUpdate);
        when(userService.getByEmail(anyString())).thenReturn(authenticatedUser);

        // Act & Assert
        assertThrows(AccessNotAllowedException.class, () -> {
            userController.update(userToUpdate.getId(), updateUserRequestDto, authentication);
        });
    }

    @Test
    void testConfirmEmailThrowsAccessNotAllowedException() throws UserNotFoundException, AccessNotAllowedException {
        // Arrange
        User user = someUser();
        User authenticatedUser = someUser();
        authenticatedUser.setId(2);
        Authentication authentication = someAuthentication();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(userService.getById(eq(user.getId()))).thenReturn(user);
        when(userService.getByEmail(anyString())).thenReturn(authenticatedUser);

        // Act & Assert
        assertThrows(AccessNotAllowedException.class, () -> {
            userController.confirmEmail(user.getId(), authentication);
        });
    }

    @Test
    void testResetPasswordThrowsAccessNotAllowedException() throws UserNotFoundException, AccessNotAllowedException {
        // Arrange
        User user = someUser();
        User authenticatedUser = someUser();
        authenticatedUser.setId(2);
        Authentication authentication = someAuthentication();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(userService.getById(eq(user.getId()))).thenReturn(user);
        when(userService.getByEmail(anyString())).thenReturn(authenticatedUser);

        // Act & Assert
        assertThrows(AccessNotAllowedException.class, () -> {
            userController.resetPassword(user.getId(), user.getPassword(), authentication);
        });
    }

    @Test
    void testVotedUnVoteForumThrowsAccessNotAllowedException() throws UserNotFoundException, AccessNotAllowedException, ForumNotFoundException {
        // Arrange
        Integer userId = 1;
        Integer forumId = 1;
        User user = someUser();
        User authenticatedUser = someUser();
        authenticatedUser.setId(2);
        Forum forum = someQuery();
        forum.setUsersWhoVoted(new ArrayList<>());
        user.setForumsVoted(new ArrayList<>());
        Authentication authentication = someAuthentication();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(userService.getById(eq(userId))).thenReturn(user);
        when(userService.getByEmail(anyString())).thenReturn(authenticatedUser);
        when(forumService.getById(eq(forumId))).thenReturn(forum);

        // Act & Assert
        assertThrows(AccessNotAllowedException.class, () -> {
            userController.votedUnVoteForum(user.getId(), forum.getId(), authentication);
        });
    }


}
