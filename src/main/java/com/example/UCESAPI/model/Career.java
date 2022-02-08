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
@Entity(name = "careers")
public class Career {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "career_name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "career")
    private List<Subject> subjects;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "statistics_id")
    private CareerStatistics statistics;



}
