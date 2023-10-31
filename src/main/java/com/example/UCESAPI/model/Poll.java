package com.example.UCESAPI.model;

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
@Entity(name = "polls")
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "poll_type")
    @Enumerated(EnumType.ORDINAL)
    public PollType pollType;

    @OneToOne
    @JoinColumn(name = "career_id")
    private Career career;

    @OneToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

}
