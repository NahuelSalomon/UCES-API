package com.example.UCESAPI.controller;

import com.example.UCESAPI.exception.UserNotExistsException;
import com.example.UCESAPI.model.User;
import com.example.UCESAPI.service.UserService;
import com.example.UCESAPI.utils.EntityResponse;
import com.example.UCESAPI.utils.EntityURLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;
    private final String USER_PATH = "user";

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<Object> add(@RequestBody User user) {
        User userCreated = this.userService.add(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(EntityURLBuilder.buildURL(USER_PATH, userCreated.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<User>> getAll(@RequestParam(value = "size", defaultValue = "10") Integer size, @RequestParam(value ="page", defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = this.userService.getAll(pageable);
        return EntityResponse.listResponse(userPage);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getById(@PathVariable Integer id) throws UserNotExistsException {
        User user = this.userService.getById(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Integer id) {
        this.userService.deleteById(id);
        return ResponseEntity.accepted().build();
    }

    @GetMapping(value = "/sayHello")
    public ResponseEntity<Object> sayHello() {
        return ResponseEntity.ok(new String("hello"));
    }



}
