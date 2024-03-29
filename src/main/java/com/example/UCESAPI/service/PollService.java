package com.example.UCESAPI.service;

import com.example.UCESAPI.exception.notfound.PollNotFoundException;
import com.example.UCESAPI.model.Poll;
import com.example.UCESAPI.model.PollQuestion;
import com.example.UCESAPI.repository.PollAnswerRepository;
import com.example.UCESAPI.repository.PollQuestionRepository;
import com.example.UCESAPI.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PollService {

    private final PollRepository pollRepository;
    private final PollQuestionRepository pollQuestionRepository;
    private final PollAnswerRepository pollAnswerRepository;

    @Autowired
    PollService(PollRepository pollRepository, PollQuestionRepository pollQuestionRepository, PollAnswerRepository pollAnswerRepository){
        this.pollRepository = pollRepository;
        this.pollQuestionRepository = pollQuestionRepository;
        this.pollAnswerRepository = pollAnswerRepository;
    }

    public Poll add(Poll poll)
    {
        return this.pollRepository.save(poll);
    }

    public List<Poll> getAll(){
        return this.pollRepository.findAll();
    }

    public Poll getPollById(Integer pollId) throws PollNotFoundException {
        Optional<Poll> poll = pollRepository.findById(pollId);
        if (poll.isEmpty()){
            throw new PollNotFoundException();
        }
        return poll.get();
    }

    public Poll getPollByCareerId(Integer careerId) throws PollNotFoundException {
        List<PollQuestion> questions;
        Optional<Poll> poll = pollRepository.findByCareerId(careerId);
        if (poll.isEmpty()){
            throw new PollNotFoundException();
        }
        return poll.get();
    }

    public Poll getPollBySubjectId(Integer subjectId) throws PollNotFoundException {
        Optional<Poll> poll = pollRepository.findBySubjectId(subjectId);
        if(poll.isEmpty()){
            throw new PollNotFoundException();
        }
        return poll.get();
    }

    public void delete(Poll poll)
    {
        this.pollRepository.delete(poll);
    }

}
