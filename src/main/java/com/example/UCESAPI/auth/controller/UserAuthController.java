package com.example.UCESAPI.auth.controller;

import com.example.UCESAPI.auth.utils.JWTUtils;
import com.example.UCESAPI.exception.UserAlreadyExistException;
import com.example.UCESAPI.model.User;
import com.example.UCESAPI.model.dto.LoginRequestDto;
import com.example.UCESAPI.model.dto.UserDto;
import com.example.UCESAPI.model.dto.UserResponseDto;
import com.example.UCESAPI.model.response.LoginResponseDto;
import com.example.UCESAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/auth")
public class UserAuthController {

    private final UserService userService;
    private final JWTUtils jwtUtils;

    @Autowired
    public UserAuthController(UserService userService, JWTUtils jwtUtils){
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponseDto> register(@RequestBody @Valid UserDto user){


        LoginResponseDto loginResponseDto;
        try{
            loginResponseDto = userService.register(user);
        }catch (UserAlreadyExistException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new LoginResponseDto("A User with the email already exists."));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(loginResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto){

        UserDetails userDetails;
        try {
            userDetails = userService.login(loginRequestDto);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponseDto("false"));
        }
        final String jwt = jwtUtils.generateToken(userDetails);

        return ResponseEntity.ok(new LoginResponseDto(jwt));
    }

    @GetMapping(value = "/userDetails")
    public ResponseEntity<UserResponseDto> userDetails(Authentication auth) {
        String email = ((UserDetails) auth.getPrincipal()).getUsername();
        User user = userService.getByEmail(email);
        UserResponseDto dto = UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .userType(user.getUserType())
                .active(user.isActive())
                .confirmedEmail(user.isConfirmedEmail())
                .build();

        return ResponseEntity.ok(dto);
    }

}
