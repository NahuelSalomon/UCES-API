package com.example.UCESAPI.service;

import com.example.UCESAPI.auth.utils.JWTUtils;
import com.example.UCESAPI.exception.UserAlreadyExistException;
import com.example.UCESAPI.exception.notfound.UserNotFoundException;
import com.example.UCESAPI.model.User;
import com.example.UCESAPI.model.UserType;
import com.example.UCESAPI.model.dto.LoginRequestDto;
import com.example.UCESAPI.model.dto.LoginResponseDto;
import com.example.UCESAPI.model.dto.user.UserInsertRequestDto;
import com.example.UCESAPI.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import static com.example.UCESAPI.utils.ModelTestUtil.someUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTUtils jwtUtils;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUser() {
        // Arrange
        User user = someUser();
        when(userRepository.save(user)).thenReturn(user);

        // Act
        User result = userService.add(user);

        // Assert
        assertEquals(user, result);
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        Page<User> userPage = new PageImpl<>(Collections.singletonList(someUser()));
        when(userRepository.findAll(any(PageRequest.class))).thenReturn(userPage);

        // Act
        Page<User> result = userService.getAll(PageRequest.of(0, 10));

        // Assert
        assertEquals(userPage, result);
    }

    @Test
    void testGetUserById() throws UserNotFoundException {
        // Arrange
        User user = someUser();
        when(userRepository.findById(anyInt())).thenReturn(java.util.Optional.of(user));

        // Act
        User result = userService.getById(1);

        // Assert
        assertEquals(user, result);
    }

    @Test
    void testGetUserByIdThrowsNotFoundException() {
        // Arrange
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.getById(1));
    }

    @Test
    void testDeleteUserById() {
        // Arrange
        int userId = 1;

        // Act
        userService.deleteById(userId);

        // Assert
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testLoadUserByUsername() {
        // Arrange
        User user = someUser();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        // Act
        assertDoesNotThrow(() -> userService.loadUserByUsername(user.getEmail()));
    }

    @Test
    void testLoadUserByUsernameThrowsUsernameNotFoundException() {
        // Arrange
        String userEmail = "test@example.com";
        when(userRepository.findByEmail(userEmail)).thenReturn(null);

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(userEmail));
    }

    @Test
    public void testLoginWithValidCredentials() {
        Authentication successfulAuthentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any()))
                .thenReturn(successfulAuthentication);

        User simulatedUser = new User();
        simulatedUser.setEmail("usuario@dominio.com");
        simulatedUser.setPassword("contraseñaEncriptada"); // Se puede utilizar la contraseña encriptada simulada
        simulatedUser.setUserType(UserType.ROLE_STUDENT);

        when(userRepository.findByEmail(any()))
                .thenReturn(simulatedUser);

        LoginRequestDto loginRequestDto = new LoginRequestDto("usuario@dominio.com", "contraseñaValida");
        UserDetails userDetails = userService.login(loginRequestDto);

        assertNotNull(userDetails);

        assertEquals(simulatedUser.getEmail(), userDetails.getUsername());
        assertEquals(simulatedUser.getPassword(), userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority(UserType.ROLE_STUDENT.toString())));
    }

    @Test
    void testLogin() {
        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        when(userRepository.findByEmail(any()))
                .thenReturn(null);

        LoginRequestDto loginRequestDto = new LoginRequestDto("usuario@dominio.com", "contraseñaIncorrecta");

        assertThrows(BadCredentialsException.class, () -> userService.login(loginRequestDto));
    }



    @Test
    void testRegister() throws UserAlreadyExistException, IOException {
        // Arrange
        UserInsertRequestDto userInsertRequestDto = new UserInsertRequestDto();
        User newUser = someUser();
        when(userRepository.findByEmail(any())).thenReturn(null).thenReturn(newUser);
        when(userRepository.save(any(User.class))).thenReturn(newUser);
        when(jwtUtils.generateToken(any(UserDetails.class))).thenReturn("dummyToken");

        // Act
        LoginResponseDto result = userService.register(userInsertRequestDto);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getToken());
    }

    @Test
    void testRegisterThrowsUserAlreadyExistException() {
        // Arrange
        UserInsertRequestDto userInsertRequestDto = new UserInsertRequestDto();
        when(userRepository.findByEmail(userInsertRequestDto.getEmail())).thenReturn(someUser());

        // Act & Assert
        assertThrows(UserAlreadyExistException.class, () -> userService.register(userInsertRequestDto));
    }

    @Test
    public void testChangeStateSetTrue() {
        // Arrange
        User simulatedUser = someUser();
        simulatedUser.setActive(true);
        when(userRepository.findById(simulatedUser.getId())).thenReturn(Optional.of(simulatedUser));

        // Act
        userService.changeState(simulatedUser);

        // Assert
        assertFalse(simulatedUser.isActive());
        verify(userRepository, times(1)).save(simulatedUser);
    }

    @Test
    public void testChangeStateSetFalse() {
        // Arrange
        User simulatedUser = someUser();
        simulatedUser.setActive(false);
        when(userRepository.findById(simulatedUser.getId())).thenReturn(Optional.of(simulatedUser));

        // Act
        userService.changeState(simulatedUser);

        // Assert
        assertFalse(!simulatedUser.isActive());
        verify(userRepository, times(1)).save(simulatedUser);
    }


    @Test
    public void testGetByEmail() {
        // Arrange
        User simulatedUser = someUser();
        when(userRepository.findByEmail(simulatedUser.getEmail())).thenReturn(simulatedUser);

        // Act
        User resultUser = userService.getByEmail(simulatedUser.getEmail());

        // Assert
        assertEquals(simulatedUser, resultUser);
    }

    @Test
    public void testUpdate() throws UserNotFoundException {
        // Arrange
        User updatedUser = someUser();
        when(userRepository.findById(updatedUser.getId())).thenReturn(Optional.of(updatedUser));

        // Act
        userService.update(updatedUser.getId(), updatedUser);

        // Assert
        assertEquals(updatedUser.getFirstname(), updatedUser.getFirstname());
        verify(userRepository, times(1)).save(updatedUser);
    }


}
