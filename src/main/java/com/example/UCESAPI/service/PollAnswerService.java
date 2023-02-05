package com.example.UCESAPI.service;

import com.example.UCESAPI.exception.notfound.PollAnswerNotFoundException;
import com.example.UCESAPI.model.PollAnswer;
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

    @Autowired
    public PollAnswerService(PollAnswerRepository pollAnswerRepository)
    {
        this.pollAnswerRepository = pollAnswerRepository;
    }
    public PollAnswer add(PollAnswer pollAnswer) {
        return this.pollAnswerRepository.save(pollAnswer);
    }

    public List<PollAnswer> addAll(List<PollAnswer> pollAnswer) {
        return this.pollAnswerRepository.saveAll(pollAnswer);
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
