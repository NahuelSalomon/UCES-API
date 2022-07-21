package com.example.UCESAPI.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    private String name;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_statistics")
    private SubjectStatistics statistics;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Subject.class)
    @JoinTable(
            name = "correlatives",
            joinColumns = {@JoinColumn(name = "id_subject")},
            inverseJoinColumns = {@JoinColumn(name = "id_correlative")}
    )
    private List<Subject> correlatives;

    /*@JsonManagedReference(value = "board-subject")
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "subject")
    private Board board;*/

    //@JsonBackReference(value = "subject-career")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_career")
    private Career career;

}
