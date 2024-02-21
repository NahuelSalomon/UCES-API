package com.example.UCESAPI.service;

import com.example.UCESAPI.exception.notfound.PollNotFoundException;
import com.example.UCESAPI.exception.notfound.UserNotFoundException;
import com.example.UCESAPI.model.Poll;
import com.example.UCESAPI.model.PollResult;
import com.example.UCESAPI.model.User;
import com.example.UCESAPI.repository.PollResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PollResultService {

    private final PollResultRepository pollResultRepository;
    private final UserService userService;
    private final PollService pollService;

    @Autowired
    public PollResultService(PollResultRepository pollResultRepository, UserService userService, PollService pollService)
    {
        this.pollResultRepository = pollResultRepository;
        this.userService = userService;
        this.pollService = pollService;
    }

    public PollResult add(PollResult pollResult)
    {
        return this.pollResultRepository.save(pollResult);
    }

    public PollResult getByPollAndStudentUser(Integer pollId, Integer studentUserId) throws UserNotFoundException, PollNotFoundException {
        Poll poll = this.pollService.getPollById(pollId);
        User user = this.userService.getById(studentUserId);
        return this.pollResultRepository.findByPollAndStudentUser(poll,user);
    }
}
