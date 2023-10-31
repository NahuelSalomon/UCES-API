package com.example.UCESAPI.controller;

import com.example.UCESAPI.exception.notfound.PollAnswerResponseNotFoundException;
import com.example.UCESAPI.exception.notfound.PollNotFoundException;
import com.example.UCESAPI.exception.notfound.UserNotFoundException;
import com.example.UCESAPI.model.PollResult;
import com.example.UCESAPI.model.dto.poll.PollResultDto;
import com.example.UCESAPI.model.mapper.CustomConversion;
import com.example.UCESAPI.service.PollResultService;
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
@RequestMapping("/api/poll_results")
public class PollResultController {

    private final PollResultService pollResultService;
    private final String POLL_RESULT_PATH = "poll_results";

    @Autowired
    public PollResultController(PollResultService pollResultService)
    {
        this.pollResultService = pollResultService;
    }

    @PostMapping(value = "/")
    public ResponseEntity<PollResultDto> add(@RequestBody PollResult pollResult) {
        PollResult pollResultCreated = this.pollResultService.add(pollResult);
        PollResultDto pollResultDto = pollResult != null ? CustomConversion.PollResultToPollResultDto(pollResultCreated) : null;
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(EntityURLBuilder.buildURL(POLL_RESULT_PATH,pollResultCreated.getId().intValue()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(pollResultDto);
    }

    @GetMapping
    public ResponseEntity<List<PollResult>> getAll(@RequestParam(value = "size", defaultValue = "10") Integer size,
                                                           @RequestParam(value = "page", defaultValue = "0") Integer page)
    {
        Pageable pageable = PageRequest.of(page,size);
        Page<PollResult> pollResultPage = this.pollResultService.getAll(pageable);
        return EntityResponse.pageResponse(pollResultPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PollResult> getById(@PathVariable Integer id) throws PollAnswerResponseNotFoundException {
        PollResult pollResult = this.pollResultService.getById(id);
        return ResponseEntity.ok(pollResult);
    }

    @GetMapping("polls/{pollId}/users/{userId}")
    public ResponseEntity<PollResultDto> getByPollAndUser(@PathVariable Integer pollId,@PathVariable Integer userId) throws UserNotFoundException, PollNotFoundException {
        PollResult pollResult = this.pollResultService.getByPollAndStudentUser(pollId,userId);
        PollResultDto pollResultDto = pollResult != null ? CustomConversion.PollResultToPollResultDto(pollResult) : null;
        return ResponseEntity.ok(pollResultDto);
    }

}
