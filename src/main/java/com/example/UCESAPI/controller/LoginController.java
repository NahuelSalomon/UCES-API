package com.example.UCESAPI.controller;


import com.example.UCESAPI.model.User;
import com.example.UCESAPI.model.dto.LoginRequestDto;
import com.example.UCESAPI.model.response.LoginResponseDto;
import com.example.UCESAPI.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.UCESAPI.utils.Constants.JWT_SECRET;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/login")
public class LoginController {

    private final UserService userService;
    private ConversionService conversionService;

    @Autowired
    public LoginController(UserService userService, ConversionService conversionService) {
        this.userService = userService;
        this.conversionService = conversionService;
    }

    @PostMapping(value = "/")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        User user = userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        if (user!=null){
            // User dto = conversionService.convert(user,UserDto.class);
            return ResponseEntity.ok(LoginResponseDto.builder().token(this.generateToken(user)).build());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping(value = "/userDetails")
    public ResponseEntity<User> userDetails(Authentication auth) {
        return ResponseEntity.ok((User) auth.getPrincipal());
    }


    public String generateToken(User user) {
        try {
            String role = user.getUserType().toString();
            ObjectMapper objectMapper = new ObjectMapper();
            List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(role);
            String token = Jwts
                    .builder()
                    .setId("JWT")
                    .setSubject(user.getEmail())
                    .claim("user", objectMapper.writeValueAsString(user))
                    .claim("authorities",grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 10000000))
                    .signWith(SignatureAlgorithm.HS512, JWT_SECRET.getBytes()).compact();
            return  token;
        } catch(JsonProcessingException e) {
            return "dummy";
        }
    }

}
