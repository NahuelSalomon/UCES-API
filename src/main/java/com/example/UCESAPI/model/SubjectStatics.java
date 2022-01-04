package com.example.UCESAPI.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity(name = "subject_statics")
public class SubjectStatics {

    @Id
    private Integer id;
    private Float hoursPerWeek;
    @OneToMany
    private List<Professor> professors;
    private Float difficulty;

}
