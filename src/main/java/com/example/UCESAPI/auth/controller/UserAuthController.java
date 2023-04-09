package com.example.UCESAPI.auth.controller;

import com.example.UCESAPI.auth.utils.JWTUtils;
import com.example.UCESAPI.exception.UserAlreadyExistException;
import com.example.UCESAPI.model.User;
import com.example.UCESAPI.model.dto.LoginRequestDto;
import com.example.UCESAPI.model.dto.user.UserInsertRequestDto;
import com.example.UCESAPI.model.dto.user.UserResponseDto;
import com.example.UCESAPI.model.dto.LoginResponseDto;
import com.example.UCESAPI.service.UserService;
import com.example.UCESAPI.model.mapper.CustomConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

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
    public ResponseEntity<LoginResponseDto> register(@RequestBody @Valid UserInsertRequestDto user){


        LoginResponseDto loginResponseDto;
        try{
            loginResponseDto = userService.register(user);
        }catch (UserAlreadyExistException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new LoginResponseDto("A User with the email already exists."));
        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new LoginResponseDto("Image with incorrect format"));
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
        UserResponseDto dto = CustomConversion.UserToUserResponseDto(user);
        return ResponseEntity.ok(dto);
    }

}
