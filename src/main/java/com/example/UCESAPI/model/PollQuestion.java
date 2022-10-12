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
@Entity(name = "poll_questions")
public class PollQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String question;

    @Enumerated(EnumType.STRING)
    QuestionThemes theme;

    @Column(name = "poll_response_type")
    @Enumerated(EnumType.STRING)
    PollResponseType pollResponseType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_poll_template")
    PollTemplate pollTemplate;
}
