package com.example.UCESAPI.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Entity(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "subject_name")
    private String name;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "statistics_id")
    private SubjectStatistics statistics;

    //@OneToMany
    //private List<Subject> correlatives;

    @JsonManagedReference(value = "board-subject")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "subject")
    private List<Board> boards;

    @JsonBackReference(value = "subject-career")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "career_id")
    private Career career;

}
