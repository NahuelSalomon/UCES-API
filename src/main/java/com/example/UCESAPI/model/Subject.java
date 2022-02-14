package com.example.UCESAPI.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties("correlatives")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "subject_name")
    private String name;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "statistics_id")
    private SubjectStatistics statistics;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Subject.class)
    @JoinTable(
            name = "correlatives",
            joinColumns = {@JoinColumn(name = "subject_id")},
            inverseJoinColumns = {@JoinColumn(name = "correlative_id")}
    )
    private List<Subject> correlatives;

    @JsonManagedReference(value = "board-subject")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subject")
    private List<Board> boards;

    @JsonBackReference(value = "subject-career")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "career_id")
    private Career career;

}
