package com.example.UCESAPI.repository;

import com.example.UCESAPI.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface PollRepository extends JpaRepository<Poll, Integer> {
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "INSERT INTO polls_x_users (id_user, id_poll) VALUES (:userId, :pollId)")
    void saveUserWithPollAnswered(@Param("userId")Integer userId, @Param("pollId")Integer pollId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "SELECT case when (count(pxs) > 0) then true else false end  " +
                                        "FROM poll_x_users pxs WHERE pxs.id_poll = :pollId AND pxs.id_user = :userId")
    Boolean isPollAnsweredByUser(Integer pollId, Integer userId);

    Optional<Poll> findByCareerId(Integer id);
    Optional<Poll> findBySubjectId(Integer id);
}
