package com.example.UCESAPI.controller;

import com.example.UCESAPI.exception.notfound.PollNotFoundException;
import com.example.UCESAPI.model.Poll;
import com.example.UCESAPI.model.PollQuestionStatistic;
import com.example.UCESAPI.service.PollQuestionStatisticService;
import com.example.UCESAPI.service.PollService;
import com.example.UCESAPI.utils.EntityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/poll_question_statistics")
public class PollQuestionStatisticController {
    private final PollQuestionStatisticService pollQuestionStatisticService;
    private final PollService pollService;
    private final String POLL_RESULT_PATH = "poll_question_statistics";

    @Autowired
    public PollQuestionStatisticController(PollQuestionStatisticService pollQuestionStatisticService, PollService pollService) {
        this.pollQuestionStatisticService = pollQuestionStatisticService;
        this.pollService = pollService;
    }

    @GetMapping("/polls/{pollId}")
    public ResponseEntity<List<PollQuestionStatistic>> getAllByPoll(@PathVariable Integer pollId) throws PollNotFoundException {
        Poll poll = pollService.getPollById(pollId);
        List<PollQuestionStatistic> pollQuestionStatisticList = this.pollQuestionStatisticService.getAllByPoll(poll);
        return EntityResponse.listResponse(pollQuestionStatisticList);
    }

}
