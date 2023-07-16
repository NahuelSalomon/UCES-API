package com.example.UCESAPI.controller;

import com.example.UCESAPI.exception.AccessNotAllowedException;
import com.example.UCESAPI.exception.notfound.ForumNotFoundException;
import com.example.UCESAPI.exception.notfound.UserNotFoundException;
import com.example.UCESAPI.model.Forum;
import com.example.UCESAPI.model.User;
import com.example.UCESAPI.model.UserType;
import com.example.UCESAPI.model.dto.user.UserResponseDto;
import com.example.UCESAPI.model.dto.user.UserUpdateRequestDto;
import com.example.UCESAPI.service.ForumService;
import com.example.UCESAPI.service.UserService;
import com.example.UCESAPI.model.mapper.CustomConversion;
import com.example.UCESAPI.utils.EntityURLBuilder;
import com.example.UCESAPI.utils.ResponseEntityMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;
    private final ForumService forumService;
    private final PasswordEncoder passwordEncoder;
    private final ConversionService conversionService;
    private final String USER_PATH = "users";

    @Autowired
    public UserController(UserService userService, ForumService forumService, PasswordEncoder passwordEncoder, ConversionService conversionService) {
        this.userService = userService;
        this.forumService = forumService;
        this.passwordEncoder = passwordEncoder;
        this.conversionService = conversionService;
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
    public ResponseEntity<List<UserResponseDto>> getAll(@RequestParam(value = "size", defaultValue = "10") Integer size, @RequestParam(value ="page", defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = this.userService.getAll(pageable);
        Page<UserResponseDto> userResponseDtoPage = userPage.map(CustomConversion::UserToUserResponseDto);
        return ResponseEntityMaker.paginatedListResponse(userResponseDtoPage);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Integer id) throws UserNotFoundException {
        User user = this.userService.getById(id);
        UserResponseDto userResponseDto = CustomConversion.UserToUserResponseDto(user);
        return ResponseEntity.ok(userResponseDto);
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

    @PutMapping(value = "/{id}")
    @PreAuthorize(value ="hasRole('ROLE_ADMINISTRATOR' ) OR hasAuthority('ROLE_STUDENT')")
    public ResponseEntity<Object> update(@PathVariable Integer id, @RequestBody UserUpdateRequestDto user, Authentication authentication) throws UserNotFoundException, AccessNotAllowedException {
        User userToUpdate = this.userService.getById(id);
        User authenticatedUser = this.userService.getByEmail(( (UserDetails) authentication.getPrincipal()).getUsername());

        if(userToUpdate.getId() == authenticatedUser.getId() || authenticatedUser.getUserType().equals(UserType.ROLE_ADMINISTRATOR))
        {
            userToUpdate.setImage(user.getImage());
            this.userService.update(id, userToUpdate);
            return ResponseEntity.accepted().build();
        }

        throw new AccessNotAllowedException("You have not access to this resource");
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

    @PutMapping(value = "/{id}/password/{password}")
    @PreAuthorize(value ="hasRole('ROLE_ADMINISTRATOR' ) OR hasAuthority('ROLE_STUDENT')")
    public ResponseEntity<Object> resetPassword(@PathVariable Integer id, @PathVariable String password,Authentication authentication) throws UserNotFoundException, AccessNotAllowedException {
        User user = this.userService.getById(id);
        User authenticatedUser = this.userService.getByEmail(( (UserDetails) authentication.getPrincipal()).getUsername());
        if(user.getId() == authenticatedUser.getId() || authenticatedUser.getUserType().equals(UserType.ROLE_ADMINISTRATOR))
        {
            user.setPassword(passwordEncoder.encode(password));
            this.userService.update(user.getId(),user);
            return ResponseEntity.accepted().build();
        }

        throw new AccessNotAllowedException("You have not access to this resource");
    }

    @PutMapping(value = "/{id}/forumsVoted/{idForum}")
    @PreAuthorize(value ="hasRole('ROLE_ADMINISTRATOR' ) OR hasAuthority('ROLE_STUDENT')")
    public ResponseEntity<Object> votedUnVoteForum(@PathVariable Integer id, @PathVariable Integer idForum,Authentication authentication) throws UserNotFoundException, AccessNotAllowedException, ForumNotFoundException {
        User user = this.userService.getById(id);
        User authenticatedUser = this.userService.getByEmail(( (UserDetails) authentication.getPrincipal()).getUsername());
        if(user.getId() == authenticatedUser.getId() || authenticatedUser.getUserType().equals(UserType.ROLE_ADMINISTRATOR))
        {
            Forum forum = this.forumService.getById(idForum);
            if(user.getForumsVoted().contains(forum))
            {
                forum.removeUserWhoVoted(user);
                user.removeVoteToForum(forum);

            } else {
                forum.addUserWhoVoted(user);
                user.addVoteToForum(forum);
            }

            this.userService.update(user.getId(),user);

            return ResponseEntity.accepted().build();
        }

        throw new AccessNotAllowedException("You have not access to this resource");
    }



}
