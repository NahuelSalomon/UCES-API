package com.example.UCESAPI.service;

import com.example.UCESAPI.exception.notfound.PollNotFoundException;
import com.example.UCESAPI.mapper.PollDtoMapper;
import com.example.UCESAPI.model.Poll;
import com.example.UCESAPI.model.PollQuestion;
import com.example.UCESAPI.model.dto.PollDto;
import com.example.UCESAPI.repository.;
import com.example.UCESAPI.repository.PollQuestionRepository;
import com.example.UCESAPI.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PollService {

    private final PollRepository pollRepository;
    private final PollQuestionRepository pollQuestionRepository;

    @Autowired
    PollService(PollRepository pollRepository, PollQuestionRepository pollQuestionRepository){
    this.pollRepository = pollRepository;
    this.pollQuestionRepository = pollQuestionRepository;
    }

    public PollDto getPollById(Integer pollId) throws PollNotFoundException {
        List<PollQuestion> questions = new ArrayList<>();
        Optional<Poll> poll = pollRepository.findById(pollId);
        if (!poll.isPresent()){
            throw new PollNotFoundException();
        }
        questions = pollQuestionRepository.findAllByPollTemplate(poll.get().getPollTemplate());
        return PollDtoMapper.map(poll.get(), questions);
    }
}
