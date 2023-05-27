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
@Entity(name = "poll_responses")

public class PollAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    Integer rating;

    /*@Column(name = "short_answer")
    String shortAnswer;*/

    @Column(name = "positive_answer")
    Boolean positiveAnswer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_professor")
    Professor professor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_poll_question")
    PollQuestion pollQuestion;
}
