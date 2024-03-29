package com.example.UCESAPI.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity(name = "poll_questions")
public class PollQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "poll_id")
    private Poll poll;

    private String question;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "poll_response_type")
    @Enumerated(EnumType.  STRING)
    private PollResponseType pollResponseType;

}
