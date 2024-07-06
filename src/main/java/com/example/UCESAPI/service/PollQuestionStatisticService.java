package com.example.UCESAPI.service;

import com.example.UCESAPI.model.Poll;
import com.example.UCESAPI.model.PollQuestion;
import com.example.UCESAPI.model.PollQuestionStatistic;
import com.example.UCESAPI.repository.PollQuestionStatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PollQuestionStatisticService {
    private final PollQuestionStatisticRepository pollQuestionStatisticRepository;

    @Autowired
    public PollQuestionStatisticService(PollQuestionStatisticRepository pollQuestionStatisticRepository) {
        this.pollQuestionStatisticRepository = pollQuestionStatisticRepository;
    }

    public PollQuestionStatistic save(PollQuestionStatistic pollQuestionStatistic)
    {
        return this.pollQuestionStatisticRepository.save(pollQuestionStatistic);
    }

    public PollQuestionStatistic getByPollQuestion(PollQuestion pollQuestion)
    {
        return this.pollQuestionStatisticRepository.findByPollQuestion(pollQuestion);
    }

    public List<PollQuestionStatistic> getAllByPoll(Poll poll)
    {
        return this.pollQuestionStatisticRepository.findAllByPollId(poll.getId());
    }
}

