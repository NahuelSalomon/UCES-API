package com.example.UCESAPI.repository;

import com.example.UCESAPI.model.Poll;
import com.example.UCESAPI.model.PollResult;
import com.example.UCESAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollResultRepository extends JpaRepository<PollResult,Integer> {
    PollResult findByPollAndStudentUser(Poll poll, User studentUser);
}
