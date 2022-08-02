package com.example.UCESAPI.controller;

import com.example.UCESAPI.exception.AccessNotAllowedException;
import com.example.UCESAPI.exception.notfound.UserNotFoundException;
import com.example.UCESAPI.model.User;
import com.example.UCESAPI.model.UserType;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;
    private final String USER_PATH = "users";

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
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<List<User>> getAll(@RequestParam(value = "size", defaultValue = "10") Integer size, @RequestParam(value ="page", defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = this.userService.getAll(pageable);
        return EntityResponse.pageResponse(userPage);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getById(@PathVariable Integer id) throws UserNotFoundException {
        User user = this.userService.getById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "/email/{email}")
    public ResponseEntity<User> getByEmail(@PathVariable String email) {
        User user = this.userService.getByEmail(email);
        if(user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<Object> deleteById(@PathVariable Integer id) {
        this.userService.deleteById(id);
        return ResponseEntity.accepted().build();
    }

    @PutMapping(value = "/{id}/confirmEmail")
    @PreAuthorize(value ="hasRole('ROLE_ADMINISTRATOR' ) OR hasAuthority('ROLE_STUDENT')")
    public ResponseEntity<Object> confirmEmail(@PathVariable Integer id, Authentication authentication) throws UserNotFoundException, AccessNotAllowedException {
        User user = this.userService.getById(id);
        User authenticatedUser = this.userService.getByEmail(( (UserDetails) authentication.getPrincipal()).getUsername());
        if(user.getId() == authenticatedUser.getId() || authenticatedUser.getUserType().equals(UserType.ROLE_ADMINISTRATOR))
        {
            user.setConfirmedEmail(true);
            this.userService.update(user.getId(),user);
            return ResponseEntity.accepted().build();
        }

        throw new AccessNotAllowedException("You have not access to this resource");
    }





}
