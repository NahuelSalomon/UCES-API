package com.example.UCESAPI.repository;

import com.example.UCESAPI.model.PollAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollAnswerRepository extends JpaRepository<PollAnswer, Integer> {
}
