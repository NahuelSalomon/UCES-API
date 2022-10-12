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
@Entity(name = "polls")
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_career")
    Career career;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_subject")
    Subject subject;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_poll_template")
    PollTemplate pollTemplate;
}
