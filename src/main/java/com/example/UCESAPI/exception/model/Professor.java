package com.example.UCESAPI.exception.model;

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
@Entity(name = "professors")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Float ratings;

    //@JsonBackReference(value = "professor-statistics")
    @JsonIgnoreProperties("professors")
    @ManyToMany(mappedBy = "professors", targetEntity = SubjectStatistics.class)
    private List<SubjectStatistics> statistics;


}
