package com.example.UCESAPI.controller;

import com.example.UCESAPI.exception.AccessNotAllowedException;
import com.example.UCESAPI.model.PollAnswer;
import com.example.UCESAPI.model.User;
import com.example.UCESAPI.service.PollAnswerService;
import com.example.UCESAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/poll_answers")
public class PollAnswerController {

    private final PollAnswerService pollAnswerService;
    private final UserService userService;
    final private String FORUM_PATH = "/api/poll_answers";
    @Autowired
    public PollAnswerController(PollAnswerService pollAnswerService, UserService userService)
    {
        this.pollAnswerService = pollAnswerService;
        this.userService = userService;
    }

    @PostMapping(value = "/all")
    @PreAuthorize(value ="hasRole('ROLE_STUDENT')")
    public ResponseEntity<Object> addRange(@RequestBody List<PollAnswer> pollAnswerList , Authentication authentication) throws AccessNotAllowedException {

        User authenticatedUser = this.userService.getByEmail(( (UserDetails) authentication.getPrincipal()).getUsername());

        for (PollAnswer p : pollAnswerList) {
            if(!p.getPollResult().getStudentUser().getId().equals(authenticatedUser.getId()))
            {
                throw new AccessNotAllowedException("You have not access to this resource");
            }
        }

        List<PollAnswer> pollAnswerListCreated = this.pollAnswerService.addAll(pollAnswerList);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

}
