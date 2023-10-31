package com.example.UCESAPI.service;

import com.example.UCESAPI.exception.notfound.PollAnswerNotFoundException;
import com.example.UCESAPI.model.PollAnswer;
import com.example.UCESAPI.model.PollQuestionStatistic;
import com.example.UCESAPI.model.PollResponseType;
import com.example.UCESAPI.repository.PollAnswerRepository;
import com.example.UCESAPI.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PollAnswerService {
    private final PollAnswerRepository pollAnswerRepository;
    private final PollQuestionStatisticService pollQuestionStatisticService;

    @Autowired
    public PollAnswerService(PollAnswerRepository pollAnswerRepository, PollQuestionStatisticService pollQuestionStatisticService)
    {
        this.pollAnswerRepository = pollAnswerRepository;
        this.pollQuestionStatisticService = pollQuestionStatisticService;
    }
    public PollAnswer add(PollAnswer pollAnswer) {
        updateStatisticValues(pollAnswer);
        return this.pollAnswerRepository.save(pollAnswer);
    }

    public List<PollAnswer> addAll(List<PollAnswer> pollAnswerList) {
        for (PollAnswer pollAnswer : pollAnswerList) {
            updateStatisticValues(pollAnswer);
        }
        return this.pollAnswerRepository.saveAll(pollAnswerList);
    }

    private void updateStatisticValues(PollAnswer pollAnswer)
    {
        PollQuestionStatistic pollQuestionStatistic = this.pollQuestionStatisticService.getByPollQuestion(pollAnswer.getPollQuestion());
        pollQuestionStatistic.setNumberOfResponses(pollQuestionStatistic.getNumberOfResponses() + 1);

        switch (pollAnswer.getPollQuestion().getPollResponseType()){
            case RATING_TO_FIVE:
                pollQuestionStatistic.setTotalRangeResponse(pollQuestionStatistic.getTotalRangeResponse() + pollAnswer.getRankResponse());
                break;
            case YES_NO_ANSWER:
                if(pollAnswer.getBoolResponse())
                {
                    pollQuestionStatistic.setNumberOfPositiveResponse(pollQuestionStatistic.getNumberOfPositiveResponse() + 1);
                }else {
                    pollQuestionStatistic.setNumberOfNegativeResponse(pollQuestionStatistic.getNumberOfNegativeResponse() + 1);
                }
                break;
        }

        this.pollQuestionStatisticService.save(pollQuestionStatistic);
    }

    public Page<PollAnswer> getAll(Pageable pageable) {
        return this.pollAnswerRepository.findAll(pageable);
    }

    public PollAnswer getById(Integer id) throws PollAnswerNotFoundException {
        return this.pollAnswerRepository.findById(id).orElseThrow(PollAnswerNotFoundException::new);
    }

    public void deleteById(Integer id) {
        this.pollAnswerRepository.deleteById(id);
    }

    public void update(Integer id, PollAnswer pollAnswerUpdated) throws PollAnswerNotFoundException {
        PollAnswer pollAnswer = this.getById(id);
        this.pollAnswerRepository.save(pollAnswerUpdated);
    }

}
