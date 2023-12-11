package com.example.UCESAPI.service;

import com.example.UCESAPI.model.Poll;
import com.example.UCESAPI.model.PollQuestion;
import com.example.UCESAPI.model.PollQuestionStatistic;
import com.example.UCESAPI.model.PollResult;
import com.example.UCESAPI.repository.PollQuestionRepository;
import com.example.UCESAPI.repository.PollResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PollQuestionService {

    private final PollQuestionRepository pollQuestionRepository;
    private final PollQuestionStatisticService pollQuestionStatisticService;

    @Autowired
    public PollQuestionService(PollQuestionRepository pollQuestionRepository, @Lazy PollQuestionStatisticService pollQuestionStatisticService)
    {
        this.pollQuestionRepository = pollQuestionRepository;
        this.pollQuestionStatisticService = pollQuestionStatisticService;
    }

    public PollQuestion add(PollQuestion pollQuestion)
    {
        PollQuestion pollQuestionCreated = this.pollQuestionRepository.save(pollQuestion);
        PollQuestionStatistic pollQuestionStatistic = PollQuestionStatistic.builder()
                                                                           .pollQuestion(pollQuestionCreated)
                                                                           .NumberOfPositiveResponse(0)
                                                                           .NumberOfNegativeResponse(0)
                                                                           .NumberOfResponses(0)
                                                                           .TotalRangeResponse(0)
                                                                           .build();
        this.pollQuestionStatisticService.save(pollQuestionStatistic);
        return pollQuestionCreated;
    }

    public List<PollQuestion> addAll(List<PollQuestion> pollQuestionList)
    {
        return this.pollQuestionRepository.saveAll(pollQuestionList);
    }

    public Page<PollQuestion> getAll(Pageable pageable)
    {
        return this.pollQuestionRepository.findAll(pageable);
    }

    public PollQuestion getById(Integer id)
    {
        return this.pollQuestionRepository.findById(id).orElseThrow();
    }

    public List<PollQuestion> getAllByPoll(Poll poll)
    {
        return this.pollQuestionRepository.findAllByPoll(poll);
    }

    public PollQuestion update(PollQuestion pollQuestionToUpdate)
    {
        PollQuestion pollQuestion = getById(Integer.valueOf(pollQuestionToUpdate.getId().intValue()));
        return this.pollQuestionRepository.save(pollQuestionToUpdate);
    }

    public void delete(PollQuestion pollQuestion)
    {
        this.pollQuestionRepository.delete(pollQuestion);
    }

}
