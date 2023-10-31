package com.example.UCESAPI.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity(name = "poll_answers")
public class PollAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "poll_question_id")
    private PollQuestion pollQuestion;

    @ManyToOne
    @JoinColumn(name = "poll_result_id")
    private PollResult pollResult;

    private Boolean boolResponse;

    private Integer rankResponse;
}
