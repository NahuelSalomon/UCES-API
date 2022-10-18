package com.example.UCESAPI.controller;

import com.example.UCESAPI.exception.notfound.PollNotFoundException;
import com.example.UCESAPI.exception.notfound.PollQuestionNotFoundException;
import com.example.UCESAPI.model.dto.poll.PollDto;
import com.example.UCESAPI.model.dto.poll.PollAnsweredDto;
import com.example.UCESAPI.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/poll")
public class PollController {

    PollService pollService;

    @Autowired
    PollController(PollService pollService){
        this.pollService = pollService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PollDto> getPollById(@PathVariable Integer id) throws PollNotFoundException {
        return ResponseEntity.ok(pollService.getPollById(id));
    }

    @PostMapping("/{idPoll}/answers/process")
    public ResponseEntity<Object> processPollAnswers(@PathVariable Integer idPoll, @RequestBody PollAnsweredDto pollAnswered) throws PollQuestionNotFoundException {
        pollService.processPollAnswers(idPoll, pollAnswered);
        return ResponseEntity.ok().build();
    }
}
