package com.example.UCESAPI.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity(name = "boards")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    /*@JsonManagedReference(value = "forum-board")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "board")
    private List<Forum> forumList;*/

    @OneToOne(fetch = FetchType.EAGER)
    //@JsonBackReference(value = "board-subject")
    @JoinColumn(name = "id_subject")
    private Subject subject;
}
