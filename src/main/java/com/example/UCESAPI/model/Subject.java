package com.example.UCESAPI.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

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

    private String code;

    private String name;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_statistics")
    private SubjectStatistics statistics;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Subject.class)
    @JoinTable(
            name = "correlatives",
            joinColumns = {@JoinColumn(name = "id_subject")},
            inverseJoinColumns = {@JoinColumn(name = "id_correlative")}
    )
    private List<Subject> correlatives;

    @JsonIgnoreProperties("subjects")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Professor.class)
    @JoinTable(
            name = "subject_professor",
            joinColumns = {@JoinColumn(name = "id_subject")},
            inverseJoinColumns = {@JoinColumn(name = "id_professor")}
    )
    private List<Professor> professors;

    //@JsonBackReference(value = "subject-career")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_career")
    private Career career;

}
