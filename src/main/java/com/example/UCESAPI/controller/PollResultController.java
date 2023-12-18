package com.example.UCESAPI.controller;

import com.example.UCESAPI.exception.AccessNotAllowedException;
import com.example.UCESAPI.exception.notfound.PollAnswerResponseNotFoundException;
import com.example.UCESAPI.exception.notfound.PollNotFoundException;
import com.example.UCESAPI.exception.notfound.UserNotFoundException;
import com.example.UCESAPI.model.PollResult;
import com.example.UCESAPI.model.User;
import com.example.UCESAPI.model.dto.poll.PollResultDto;
import com.example.UCESAPI.model.mapper.CustomConversion;
import com.example.UCESAPI.service.PollResultService;
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

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/poll_results")
public class PollResultController {

    private final PollResultService pollResultService;
    private final UserService userService;
    private final String POLL_RESULT_PATH = "poll_results";

    @Autowired
    public PollResultController(PollResultService pollResultService, UserService userService)
    {
        this.pollResultService = pollResultService;
        this.userService = userService;
    }

    @PostMapping(value = "/")
    @PreAuthorize(value ="hasRole('ROLE_STUDENT' )")
    public ResponseEntity<PollResultDto> add(@RequestBody PollResult pollResult, Authentication authentication) throws AccessNotAllowedException {
        User authenticatedUser = this.userService.getByEmail(( (UserDetails) authentication.getPrincipal()).getUsername());

        if(!pollResult.getStudentUser().getId().equals(authenticatedUser.getId()))
        {
            throw new AccessNotAllowedException("You have not access to this resource");
        }

        PollResult pollResultCreated = this.pollResultService.add(pollResult);
        PollResultDto pollResultDto = pollResult != null ? CustomConversion.PollResultToPollResultDto(pollResultCreated) : null;
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(EntityURLBuilder.buildURL(POLL_RESULT_PATH,pollResultCreated.getId().intValue()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(pollResultDto);
    }

    @GetMapping("polls/{pollId}/users/{userId}")
    public ResponseEntity<PollResultDto> getByPollAndUser(@PathVariable Integer pollId,@PathVariable Integer userId) throws UserNotFoundException, PollNotFoundException {
        PollResult pollResult = this.pollResultService.getByPollAndStudentUser(pollId,userId);
        PollResultDto pollResultDto = pollResult != null ? CustomConversion.PollResultToPollResultDto(pollResult) : null;
        return ResponseEntity.ok(pollResultDto);
    }

}
