package com.example.UCESAPI.model;

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
@Entity(name = "subject_statistics")
public class SubjectStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "hours_per_week")
    private Float hoursPerWeek;

    //@JsonManagedReference(value = "professor-statistics")
    @JsonIgnoreProperties("statistics")
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Professor.class)
    @JoinTable(
            name = "subject_statistics_professor",
            joinColumns = {@JoinColumn(name = "id_subject_statistic")},
            inverseJoinColumns = {@JoinColumn(name = "id_professor")}
    )
    private List<Professor> professors;

    private Float difficulty;

}
