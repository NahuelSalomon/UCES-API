package com.example.UCESAPI.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@SQLDelete(sql = "UPDATE professors SET soft_delete = true WHERE id=?")
@Where(clause = "soft_delete = false")
@Entity(name = "professors")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Float ratings;

    //@JsonBackReference(value = "professor-statistics")
    @JsonIgnoreProperties("professors")
    @ManyToMany(mappedBy = "professors", targetEntity = Subject.class)
    private List<Subject> subjects;

    @NonNull
    @Column(name = "soft_delete")
    private Boolean softDelete = Boolean.FALSE;
}
