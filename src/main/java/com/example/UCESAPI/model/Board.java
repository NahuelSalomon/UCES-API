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
@Entity(name = "boards")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "board_name")
    private String name;

    @JsonManagedReference(value = "forum-board")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "board")
    private List<Forum> list;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "board-subject")
    @JoinColumn(name = "subject_id")
    private Subject subject;
}
