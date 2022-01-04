package com.example.UCESAPI.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity(name = "subjects")
public class Subject {

    @Id
    private Integer id;
    private String name;
    @OneToOne
    private SubjectStatics statics;
    @OneToMany
    private List<Subject> correlatives;
    @OneToMany
    private List<Board> boards;

}
