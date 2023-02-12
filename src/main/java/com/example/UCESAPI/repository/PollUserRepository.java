package com.example.UCESAPI.repository;

import com.example.UCESAPI.model.Poll;
import com.example.UCESAPI.model.PollUser;
import com.example.UCESAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollUserRepository extends JpaRepository<PollUser, Integer> {

    PollUser findByPollAndUser(Poll poll, User user);

}
