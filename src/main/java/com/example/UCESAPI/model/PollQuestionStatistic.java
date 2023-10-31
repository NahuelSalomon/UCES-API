package com.example.UCESAPI.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity(name = "poll_question_statistics")
public class PollQuestionStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "poll_question_id")
    private PollQuestion pollQuestion;


    @Column(name = "number_of_positive_response")
    private float NumberOfPositiveResponse;
    @Column(name = "number_of_negative_response")
    private float NumberOfNegativeResponse;

    @Column(name = "total_range_response")
    private float TotalRangeResponse;

    @Column(name = "number_of_responses")
    private int NumberOfResponses;
}
