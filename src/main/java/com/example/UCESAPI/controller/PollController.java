package com.example.UCESAPI.controller;

import com.example.UCESAPI.exception.notfound.PollNotFoundException;
import com.example.UCESAPI.exception.notfound.PollQuestionNotFoundException;
import com.example.UCESAPI.model.Poll;
import com.example.UCESAPI.model.PollQuestion;
import com.example.UCESAPI.model.Subject;
import com.example.UCESAPI.model.dto.poll.PollAnsweredDto;
import com.example.UCESAPI.service.PollService;
import com.example.UCESAPI.utils.EntityResponse;
import com.example.UCESAPI.utils.EntityURLBuilder;
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
@RequestMapping("/api/poll")
public class PollController {

    private final PollService pollService;
    private final String POLL_PATH = "poll";
    @Autowired
    PollController(PollService pollService){
        this.pollService = pollService;
    }

    @PostMapping("/")
    @PreAuthorize(value ="hasRole('ROLE_ADMINISTRATOR' )")
    public ResponseEntity<Poll> add(@RequestBody Poll poll) {
        Poll pollCreated = this.pollService.add(poll);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(UriComponentsBuilder.fromPath("/api/poll/{id}")
                        .buildAndExpand(pollCreated.getId())
                        .toUri())
                .contentType(MediaType.APPLICATION_JSON)
                .body(pollCreated);
    }

    @GetMapping("/")
    public ResponseEntity<List<Poll>> getAll() {
        List<Poll> pollList = this.pollService.getAll();
        return EntityResponse.listResponse(pollList);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Poll> getPollById(@PathVariable Integer id) throws PollNotFoundException {
        return ResponseEntity.ok(pollService.getPollById(id));
    }

    @GetMapping("/career/{careerId}")
    public ResponseEntity<Poll> getPollByCareerId(@PathVariable Integer careerId) throws PollNotFoundException {
        return ResponseEntity.ok(pollService.getPollByCareerId(careerId));
    }

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<Poll> getPollBySubjectId(@PathVariable Integer subjectId) throws PollNotFoundException {
        return ResponseEntity.ok(pollService.getPollBySubjectId(subjectId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value ="hasRole('ROLE_ADMINISTRATOR' )")
    public ResponseEntity<Object> delete(@PathVariable Integer id) throws PollNotFoundException {
        Poll poll = this.pollService.getPollById(id);
        this.pollService.delete(poll);
        return ResponseEntity.accepted().build();
    }

}
