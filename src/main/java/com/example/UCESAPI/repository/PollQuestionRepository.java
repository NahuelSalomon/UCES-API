package com.example.UCESAPI.repository;

import com.example.UCESAPI.model.Poll;
import com.example.UCESAPI.model.PollQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PollQuestionRepository extends JpaRepository<PollQuestion, Integer> {
    List<PollQuestion> findAllByPoll(Poll poll);
}
