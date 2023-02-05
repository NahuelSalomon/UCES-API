package com.example.UCESAPI.controller;

import com.example.UCESAPI.exception.notfound.PollAnswerNotFoundException;
import com.example.UCESAPI.exception.notfound.ProfessorNotFoundException;
import com.example.UCESAPI.model.PollAnswer;
import com.example.UCESAPI.service.PollAnswerService;
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
@RequestMapping("/api/pollAnswer")
public class PollAnswerController {

    PollAnswerService pollAnswerService;
    final private String FORUM_PATH = "/api/forums";
    @Autowired
    public PollAnswerController(PollAnswerService pollAnswerService)
    {
        this.pollAnswerService = pollAnswerService;
    }

    @PostMapping(value = "/")
    public ResponseEntity<Object> add(@RequestBody PollAnswer pollAnswer) {
        PollAnswer pollAnswerCreated = this.pollAnswerService.add(pollAnswer);
        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .location(EntityURLBuilder.buildURL(FORUM_PATH,pollAnswerCreated.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    @PostMapping(value = "/all")
    public ResponseEntity<Object> addRange(@RequestBody List<PollAnswer> pollAnswerList) {
        List<PollAnswer> pollAnswerListCreated = this.pollAnswerService.addAll(pollAnswerList);
        return  ResponseEntity
                .status(HttpStatus.CREATED)
                //.location(EntityURLBuilder.buildURL(FORUM_PATH,pollAnswerCreated.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<PollAnswer>> getAll(@RequestParam(value = "size", defaultValue = "10") Integer size, @RequestParam(value = "page", defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page,size);
        Page<PollAnswer> pollAnswerPage = this.pollAnswerService.getAll(pageable);
        return EntityResponse.pageResponse(pollAnswerPage);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PollAnswer> getById(@PathVariable Integer id) throws PollAnswerNotFoundException {
        PollAnswer pollAnswer = this.pollAnswerService.getById(id);
        return ResponseEntity.ok(pollAnswer);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Integer id) {
        this.pollAnswerService.deleteById(id);
        return ResponseEntity.accepted().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateById(@PathVariable Integer id, @RequestBody PollAnswer updated) throws PollAnswerNotFoundException {
        pollAnswerService.update(id, updated);
        return ResponseEntity.accepted().build();
    }

}
