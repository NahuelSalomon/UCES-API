package com.example.UCESAPI.repository;

import com.example.UCESAPI.model.PollQuestion;
import com.example.UCESAPI.model.PollQuestionStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PollQuestionStatisticRepository extends JpaRepository<PollQuestionStatistic,Integer> {
    PollQuestionStatistic findByPollQuestion(PollQuestion pollQuestion);
    @Query(value = "SELECT \n" +
            "statistics.id, \n" +
            "statistics.poll_question_id,\n" +
            "statistics.number_of_positive_response,\n" +
            "statistics.number_of_negative_response,\n" +
            "statistics.total_range_response,\n" +
            "statistics.number_of_responses\n" +
            "FROM poll_question_statistics statistics\n" +
            "INNER JOIN poll_questions questions\n" +
            "ON statistics.poll_question_id = questions.id\n" +
            "INNER JOIN polls\n" +
            "ON polls.id = questions.poll_id\n" +
            "WHERE polls.id = :pollId", nativeQuery = true)
    List<PollQuestionStatistic> findAllByPollId(@Param("pollId") Integer pollId);

}
