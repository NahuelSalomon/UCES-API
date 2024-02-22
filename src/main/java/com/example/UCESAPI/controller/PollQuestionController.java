package com.example.UCESAPI.controller;

import com.example.UCESAPI.exception.notfound.PollNotFoundException;
import com.example.UCESAPI.model.Poll;
import com.example.UCESAPI.model.PollQuestion;
import com.example.UCESAPI.service.PollQuestionService;
import com.example.UCESAPI.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/poll_question")
public class PollQuestionController {

    private final PollQuestionService pollQuestionService;
    private final PollService pollService;
    private final String POLL_RESULT_PATH = "poll_question";

    @Autowired
    public PollQuestionController(PollQuestionService pollQuestionService, PollService pollService)
    {
        this.pollQuestionService = pollQuestionService;
        this.pollService = pollService;
    }

    @PostMapping(value = "/")
    @PreAuthorize(value ="hasRole('ROLE_ADMINISTRATOR' )")
    public ResponseEntity<Object> add(@RequestBody PollQuestion pollQuestion) {
        PollQuestion pollQuestionCreated = this.pollQuestionService.add(pollQuestion);
        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .location(UriComponentsBuilder.fromPath("/api/poll_question/{id}")
                        .buildAndExpand(pollQuestionCreated.getId())
                        .toUri())
                .contentType(MediaType.APPLICATION_JSON)
                .body(pollQuestionCreated);
    }

    @PostMapping(value = "/all")
    @PreAuthorize(value ="hasRole('ROLE_ADMINISTRATOR' )")
    public ResponseEntity<Object> addAll(@RequestBody List<PollQuestion> pollQuestion) {
        List<PollQuestion> pollQuestionCreatedList = this.pollQuestionService.addAll(pollQuestion);
        return  ResponseEntity
                .status(HttpStatus.CREATED)
                //.location(EntityURLBuilder.buildURL(POLL_RESULT_PATH,pollQuestionCreated.getId().intValue()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(pollQuestionCreatedList);
    }

    @GetMapping("/polls/{id}")
    public ResponseEntity<List<PollQuestion>> getAllByPoll(@PathVariable Integer id) throws PollNotFoundException {
        Poll poll = this.pollService.getPollById(id);
        List<PollQuestion> pollQuestionList = this.pollQuestionService.getAllByPoll(poll);
        return ResponseEntity.ok(pollQuestionList);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value ="hasRole('ROLE_ADMINISTRATOR' )")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        PollQuestion pollQuestion = this.pollQuestionService.getById(id);
        this.pollQuestionService.delete(pollQuestion);
        return ResponseEntity.accepted().build();
    }

}
