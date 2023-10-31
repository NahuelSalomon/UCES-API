package com.example.UCESAPI.controller;

import com.example.UCESAPI.exception.notfound.PollNotFoundException;
import com.example.UCESAPI.exception.notfound.PollQuestionNotFoundException;
import com.example.UCESAPI.model.Poll;
import com.example.UCESAPI.model.dto.poll.PollAnsweredDto;
import com.example.UCESAPI.service.PollService;
import com.example.UCESAPI.utils.EntityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/poll")
public class PollController {

    private final PollService pollService;

    @Autowired
    PollController(PollService pollService){
        this.pollService = pollService;
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

    @PostMapping("/{idPoll}/answers/process")
    public ResponseEntity<Object> processPollAnswers(@PathVariable Integer idPoll, @RequestBody PollAnsweredDto pollAnswered) throws PollQuestionNotFoundException {
        pollService.processPollAnswers(idPoll, pollAnswered);
        return ResponseEntity.ok().build();
    }
}
