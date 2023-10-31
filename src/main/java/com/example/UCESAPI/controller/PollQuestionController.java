package com.example.UCESAPI.controller;

import com.example.UCESAPI.exception.notfound.PollAnswerResponseNotFoundException;
import com.example.UCESAPI.exception.notfound.PollNotFoundException;
import com.example.UCESAPI.model.Poll;
import com.example.UCESAPI.model.PollQuestion;
import com.example.UCESAPI.model.PollResult;
import com.example.UCESAPI.service.PollQuestionService;
import com.example.UCESAPI.service.PollResultService;
import com.example.UCESAPI.service.PollService;
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
    public ResponseEntity<Object> add(@RequestBody PollQuestion pollQuestion) {
        PollQuestion pollQuestionCreated = this.pollQuestionService.add(pollQuestion);
        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .location(EntityURLBuilder.buildURL(POLL_RESULT_PATH,pollQuestionCreated.getId().intValue()))
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    @GetMapping
    public ResponseEntity<List<PollQuestion>> getAll(@RequestParam(value = "size", defaultValue = "10") Integer size,
                                                   @RequestParam(value = "page", defaultValue = "0") Integer page)
    {
        Pageable pageable = PageRequest.of(page,size);
        Page<PollQuestion> pollResultPage = this.pollQuestionService.getAll(pageable);
        return EntityResponse.pageResponse(pollResultPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PollQuestion> getById(@PathVariable Integer id) throws PollAnswerResponseNotFoundException {
        PollQuestion pollQuestion = this.pollQuestionService.getById(id);
        return ResponseEntity.ok(pollQuestion);
    }

    @GetMapping("/polls/{id}")
    public ResponseEntity<List<PollQuestion>> getAllByPoll(@PathVariable Integer id) throws PollNotFoundException {
        Poll poll = this.pollService.getPollById(id);
        List<PollQuestion> pollQuestionList = this.pollQuestionService.getAllByPoll(poll);
        return ResponseEntity.ok(pollQuestionList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        PollQuestion pollQuestion = this.pollQuestionService.getById(id);
        this.pollQuestionService.delete(pollQuestion);
        return ResponseEntity.accepted().build();
    }


}
